<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学生姓名" prop="studentId" >
        <el-select v-model="queryParams.studentId" placeholder="学生姓名" clearable >
          <el-option
            v-for="student in this.studentSelect"
            :key="student.value"
            :label="student.label"
            :value="student.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="选题id" prop="titleId">
        <el-select v-model="queryParams.titleId" placeholder="选题id" clearable >
          <el-option
            v-for="title in this.titleSelect"
            :key="title.value"
            :label="title.label"
            :value="title.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['topic:myTopic:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['topic:myTopic:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['topic:myTopic:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['topic:myTopic:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="topicList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键Id" align="center" prop="id" v-if="false"/>
      <el-table-column label="选题id" align="center" prop="titleId" v-if="false"/>
      <el-table-column label="学生id" align="center" prop="studentId" v-if="false"/>
      <el-table-column label="附件类型" align="center" prop="fileType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.topic_file_type" :value="scope.row.fileType"/>
        </template>
      </el-table-column>
      <el-table-column label="附件名称" align="center" prop="fileName" />
      <el-table-column label="附件路径" align="center" prop="filePath" v-if="false"/>
      <el-table-column label="附件状态" align="center" prop="fileStatus" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.topic_file_status" :value="scope.row.fileStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="上传类型" align="center" prop="uploadType" v-if="false"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-upload"
            @click="handleUpload(scope.row)"
            v-hasPermi="['topic:myTopic:upload']"
          >上传</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleDownload(scope.row)"
            v-hasPermi="['topic:myTopic:download']"
          >下载</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-folder-checked"
            @click="handleArchive(scope.row)"
            v-hasPermi="['topic:myTopic:archive']"
          >归档</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改我的选题对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="附件类型" prop="fileType" v-if="columnFlag['fileType']">
          <el-select v-model="form.fileType" placeholder="附件类型" clearable>
            <el-option
              v-for="dict in dict.type.topic_file_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="附件名称" prop="fileName" v-if="columnFlag['fileName']">
          <el-input v-model="form.fileName" placeholder="请输入附件名称" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 附件导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".doc, .docm, .docx, .dot, .dotx"
        :headers="upload.headers"
        :action="upload.url + '?topicId=' + upload.topicId"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的附件
          </div>
          <span>仅允许上传.doc, .docm, .docx, .dot, .dotx格式文件。</span>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listTopic, getTopic, delTopic, addTopic, updateTopic } from "@/api/topic/myTopic";
import { listTitle} from "@/api/topic/title";
import {listDist} from "@/api/topic/stuDist";
import {listTopic as listStuTopic} from "@/api/topic/stuTopic";
import {getInfo} from "@/api/login";
import {getToken} from "@/utils/auth";

export default {
  name: "Topic",
  dicts: ['topic_file_type','topic_file_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedTopicTitle: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 我的选题表格数据
      topicList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentId: null,
        studentName: null,
        titleId: null,
        titleName: null
      },
      columnFlag: {
        fileType: true,
        fileName: true
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      studentSelect: [],
      titleSelect: [],

      //上传附件
      upload: {
        // 是否显示弹出层（附件上传）
        open: false,
        // 弹出层标题（附件上传）
        title: "",
        // 是否禁用上传
        isUploading: false,
        //当前对象
        topicId: null,
        // 是否更新已经存在的附件
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/topic/mytopic/upload"
      }
    };
  },
  created() {
    this.init();
    // getInfo().then(response => {
    //   //初始化当前用户
    //   this.queryParams.studentId = response["user"]["userId"];
    //   this.queryParams.studentName = response["user"]["nickName"];
    //
    //   this.handleQuery();
    // });

    // this.getList();
  },
  methods: {
    init() {
      let roles = this.$store.state.user.roles;
      console.log(roles);
      if (roles.indexOf("teacher") > -1) {
        //初始化学生下拉框
        listDist({
          pageNum: 1,
          pageSize: 1000,
          teacherName: this.$store.state.user.name
        }).then(response => {
          if (response.rows.length === 0) {
            this.$message.error("当前教师未分配学生，请联系上级领导！");
            return;
          }
          this.studentSelect = response.rows.map(item => {
            return {
              value: item.studentId,
              label: item.student.nickName
            };
          });
          //初始化题目下拉框
          getInfo().then(response => {
            //初始化当前用户
            listTitle({
              pageNum: 1,
              pageSize: 1000,
              teacherId: response["user"]["userId"],
              status: 1
            }).then(response => {
              if (response.rows.length === 0) {
                this.$message.error("当前教师无毕设题目，请先申请题目！");
                return;
              }
              this.titleSelect = response.rows.map(item => {
                return {
                  value: item.id,
                  label: item.titleName
                };
              });
              this.handleQuery();
            });
          });

        });



      }else if (roles.indexOf("student") > -1) {
        getInfo().then(response => {
          //初始化当前用户
          this.studentSelect = [{
            value: response["user"]["userId"],
            label: response["user"]["nickName"]
          }];

          this.queryParams.studentId = response["user"]["userId"];
          // this.queryParams.studentName = response["user"]["nickName"];

          //查询当前用户的题目
          listStuTopic({
            pageNum: 1,
            pageSize: 1000,
            studentId: response["user"]["userId"]
          }).then(response => {
            if (response.rows.length === 0) {
              this.$message.error("当前学生未选题，请先选题！");
              return;
            }
            listTitle({
              pageNum: 1,
              pageSize: 1000,
              id: response.rows[0].titleId
            }).then(response => {
              if (response.rows.length === 0) {
                this.$message.error("当前学生选题不存在，请联系管理员！");
                return;
              }
              this.titleSelect = response.rows.map(item => {
                return {
                  value: item.id,
                  label: item.titleName
                };
              });
              this.queryParams.titleId = response.rows[0].id;
              this.handleQuery();
            });
          });

        });
      }
    },
    /** 查询我的选题列表 */
    getList() {
      this.loading = true;
      listTopic(this.queryParams).then(response => {
        this.topicList = (response.rows.length > 0 && response.rows[0].myTopics !== undefined) ? response.rows[0].myTopics : [];
        this.total = response.total;
        // this.queryParams.titleId = (response.rows.length > 0 && response.rows[0].titleId !== undefined) ? response.rows[0].titleId : null;
        // this.queryParams.titleName = (response.rows.length > 0 && response.rows[0].title !== undefined && response.rows[0].title.titleName !== undefined) ? response.rows[0].title.titleName : null;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        titleId: null,
        studentId: null,
        fileType: null,
        fileName: null,
        filePath: null,
        fileStatus: null,
        uploadType: null
      };
      this.topicTitleList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      console.log(this.queryParams);
      this.reset();
      this.columnFlag = {
        fileType: true,
        fileName: true
      };
      this.open = true;
      this.title = "添加我的选题附件";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      console.log(row)
      this.reset();
      const id = row.id || this.ids
      getTopic(id).then(response => {
        this.form = response.data;
        this.topicTitleList = response.data.topicTitleList;
        if (this.form.fileStatus.toString() === '6'){        //已归档
          this.$modal.msgWarning("该附件已归档，不可修改");
        }else {
          this.columnFlag = {
            fileType: false,
            fileName: true
          };
          this.open = true;
          this.title = "修改我的选题附件";
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.studentId = this.queryParams.studentId;
          this.form.titleId = this.queryParams.titleId;
          this.form.topicTitleList = this.topicTitleList;
          console.log(this.form)
          if (this.form.id != null) {
            updateTopic(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTopic(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除我的选题编号为"' + ids + '"的数据项？').then(function() {
        return delTopic(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 题目序号 */
    rowTopicTitleIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 复选框选中数据 */
    handleTopicTitleSelectionChange(selection) {
      this.checkedTopicTitle = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('topic/mytopic/export', {
        ...this.queryParams
      }, `mytopic_${new Date().getTime()}.xlsx`)
    },
    /** 上传按钮操场 */
    handleUpload(row) {
      this.upload.title = "上传附件";
      this.upload.open = true;
      console.log(row)
      this.upload.topicId = row.id;
      this.upload.fileStatus = row.fileStatus;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
      //更新附件状态
      if (this.upload.fileStatus === '0' || this.upload.fileStatus === null || this.upload.fileStatus === '') {
        //未上传改为已上传
        this.upload.fileStatus = '1';
      }else if(this.upload.fileStatus === '2'){
        //已查看待批改 改为 已批改待更正
        this.upload.fileStatus = '3';
      }else if(this.upload.fileStatus === '4'){
        //已查看待更正 改为 已更正待查看
        this.upload.fileStatus = '5';
      }
      updateTopic({
        id: this.upload.topicId,
        fileStatus: this.upload.fileStatus
      }).then(response => {
        this.$modal.msgSuccess("更新附件状态成功");
      });
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    /** 下载按钮操作 */
    handleDownload(row) {
      console.log(row)
      if (row.fileStatus === '0' || row.fileStatus === null || row.fileStatus === '') {
        this.$modal.msgWarning("该附件未上传，不可下载");
      }
      this.download('topic/mytopic/download',
        {
          id: row.id,
          fileName: row.fileName,
          filePath: row.filePath
        },
        row.fileName + row.filePath.substring(row.filePath.lastIndexOf(".")));
        //更新附件状态
        if (row.fileStatus === '1') {
          //已上传 改为 已查看待批改
          row.fileStatus = '2';
        }else if(row.fileStatus === '3'){
          // 已批改待更正 改为 已查看待更正
          row.fileStatus = '4';
        }else if(row.fileStatus === '5'){
          // 已更正待查看 改为 已查看待批改
          row.fileStatus = '2';
        }
        updateTopic({
          id: row.id,
          fileStatus: row.fileStatus
        }).then(response => {
          this.$modal.msgSuccess("更新附件状态成功");
        });
    },

    /** 归档 */
    handleArchive(row) {
      console.log(row)
      this.$modal.confirm('是否确认归档名称为《"' + row.fileName + '"》的附件？').then(function() {
        //更新附件状态
        updateTopic({
          id: row.id,
          fileStatus: '6'
        });
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("归档成功");
      }).catch(() => {});

    },
  }
};
</script>
