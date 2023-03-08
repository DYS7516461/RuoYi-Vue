<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="选题名称" prop="titleName" v-if="queryForm.titleName">
        <el-input
          v-model="queryParams.titleName"
          placeholder="选题名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status" v-if="queryForm.status">
        <el-select v-model="queryParams.status" placeholder="状态" clearable >
          <el-option
            v-for="dict in dict.type.topic_check_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
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
          v-hasPermi="['topic:title:add']"
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
          v-hasPermi="['topic:title:edit']"
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
          v-hasPermi="['topic:title:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['topic:title:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="titleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="选题ID" align="center" prop="id" v-if="false"/>
      <el-table-column label="选题名称" align="center" prop="titleName" v-if="tableColumn.titleName" />
      <el-table-column label="教师ID" align="center" prop="teacherId" v-if="false"/>
      <el-table-column label="教师名称" align="center" prop="teacher.nickName" v-if="tableColumn.teacher"/>
      <el-table-column label="学生申请状态" align="center" prop="status" v-if="tableColumn.stuTopicStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.student_apply_topic" :value="scope.row.stuTopic.status"/>
        </template>
      </el-table-column>
      <el-table-column label="学生申请备注" align="center" prop="stuTopic.remark" v-if="tableColumn.stuTopicRemark"/>
      <el-table-column label="状态" align="center" prop="status" v-if="tableColumn.status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.topic_check_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="tableColumn.remark"/>
      <el-table-column label="审核" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            type="success"
            size="small"
            plain
            @click="handleCheckPass(scope.row)"
            v-hasPermi="['topic:title:pass']"
            v-if="checkButton.pass"
          >通过</el-button>
          <el-button
            type="danger"
            size="small"
            plain
            @click="handleCheckFail(scope.row)"
            v-hasPermi="['topic:title:fail']"
            v-if="checkButton.fail"
          >退回</el-button>
          <el-button
            type="primary"
            size="small"
            plain
            @click="handleApply(scope.row)"
            v-hasPermi="['topic:title:Apply']"
            v-if="checkButton.Apply"
          >申请</el-button>
          <el-button
            type="success"
            size="small"
            plain
            @click="handleApprove(scope.row)"
            v-hasPermi="['topic:title:approve']"
            v-if="checkButton.approve"
          >批准</el-button>
          <el-button
            type="danger"
            size="small"
            plain
            @click="handleReject(scope.row)"
            v-hasPermi="['topic:title:reject']"
            v-if="checkButton.reject"
          >拒绝</el-button>
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

    <!-- 添加或修改选题对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>

      <!--教师候选列表-->
      <el-dialog :title="title" :visible.sync="teacherListOpen" width="1000px" append-to-body>
        <div>
          <el-row>
            <el-col  :span="6" :xs="24">
              <div class="head-container">
                <el-input
                  v-model="teacherDeptName"
                  placeholder="请输入部门名称"
                  clearable
                  size="small"
                  prefix-icon="el-icon-search"
                  style="margin-bottom: 20px"
                />
              </div>
              <div class="head-container">
                <el-tree
                  :data="teacherDeptOptions"
                  :props="defaultProps"
                  :expand-on-click-node="false"
                  :filter-node-method="filterNode"
                  ref="teacherTree"
                  node-key="id"
                  default-expand-all
                  highlight-current
                  @node-click="teacherHandleNodeClick"
                />
              </div>
            </el-col>
            <el-col  :span="18" :xs="24">
              <el-form ref="teacherQueryForm" :model="form" :rules="rules" size="small" :inline="true" label-width="80px">
                <el-form-item label="分组" prop="roleId">
                  <el-select @change="teacherRoleChange"
                             v-model="teacherQueryParams.roleId"
                             placeholder="分组"
                             style="width: 240px"
                  >
                    <el-option
                      v-for="role in roleIdAndName"
                      :key="role.roleId"
                      :label="role.roleName"
                      :value="role.roleId"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" size="mini" @click="handleTeacherSave">确认</el-button>
                  <el-button size="mini" @click="handleTeacherClear">清空</el-button>
                </el-form-item>
              </el-form>
              <el-table v-loading="loading" :data="teacherList" highlight-current-row @current-change="handleCurrentChangeTeacher" @row-dblclick="handleRowDblclickTeacher">
                <el-table-column type="index" width="50" align="center" />
                <el-table-column label="ID" align="center" key="id" prop="id" v-if="false" :show-overflow-tooltip="true" />
                <el-table-column label="部门" align="center" key="dept.deptId" prop="dept.deptId" v-if="false" :show-overflow-tooltip="true" />
                <el-table-column label="部门" align="center" key="dept.deptName" prop="dept.deptName" v-if="true" :show-overflow-tooltip="true" />
                <el-table-column label="教师编号" align="center" key="userId" prop="userId" v-if="true" :show-overflow-tooltip="true" />
                <el-table-column label="教师名称" align="center" key="nickName" prop="nickName" v-if="true" :show-overflow-tooltip="true" />
              </el-table>
              <pagination
                v-show="teacherTotal>0"
                :total="teacherTotal"
                :page.sync="teacherQueryParams.pageNum"
                :limit.sync="teacherQueryParams.pageSize"
                @pagination="getTeacherList"
              />
            </el-col>
          </el-row>
        </div>
      </el-dialog>


      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="选题名称" prop="titleName">
          <el-input v-model="form.titleName" placeholder="请输入选题名称" />
        </el-form-item>
        <el-form-item label="教师ID" prop="teacherId" v-if="false">
          <el-input v-model="form.teacherId" placeholder="请输入教师ID" />
        </el-form-item>
        <el-form-item label="教师名称" prop="teacher.nickName">
          <el-input v-model="form.teacher.nickName" placeholder="请输入教师名称" @focus="openTeacherList"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { listTitle, getTitle, delTitle, addTitle, updateTitle } from "@/api/topic/title";
import {deptTreeSelect} from "@/api/topic/stuDist";
import {listTopic, addTopic, updateTopic} from "@/api/topic/stuTopic";
import {listUser} from "@/api/system/user";
import {listRole} from "@/api/system/role";
import {getInfo} from "@/api/login";

export default {
  name: "Title",
  dicts: ['topic_check_status','student_apply_topic'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 选题表格数据
      titleList: [],
      //教师列表
      teacherList: [],
      //table表格列显示控制
      tableColumn: {
        titleName: true,
        teacher: true,
        status: true,
        createTime: true,
        updateTime: true,
        remark: true,
        stuTopicStatus: true,
        stuTopicRemark: true
      },
      checkButton: {
        pass: true,
        fail: true,
        Apply: true,
        approve: true,
        reject: true
      },
      //查询表单显示控制
      queryForm: {
        titleName: true,
        teacher: true,
        status: true
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否显示教师弹出层
      teacherListOpen: false,
      //弹出层表单类型
      formType: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderByColumn: "status",
        isAsc: "asc",
        titleName: undefined,
        teacher:{
          userName: undefined,
          nickName: undefined
        },
        status: undefined
      },
      // 表单参数
      form: {
        teacher:{
          userName: undefined,
          nickName: undefined
        }
      },
      // 表单校验
      rules: {
        titleName: [
          { required: true, message: "选题名称不能为空", trigger: "blur" }
        ],
        teacherId: [
          { required: true, message: "教师ID不能为空", trigger: "blur" }
        ],
        teacher:{
          nickName: [
            { required: true, message: "教师名称不能为空", trigger: "blur" },
            { required: true, message: "教师名称不能为空", trigger: "change" }
          ]
        },
      },
      //教师列表弹出层
      teacherListTitle: "",
      teacherDeptName: "",
      teacherDeptOptions: undefined,
      defaultProps: {
        children: "children",
        label: "label"
      },
      roleIdAndName: [],
      teacherTotal: 0,
      teacherQueryParams: {
        pageNum: 1,
        pageSize: 10,
        userType: "02",
        deptId: undefined,
        roleId: undefined,
        userId: undefined,
        nickName: undefined
      },
      teacherListSelectRow: undefined,
      user:undefined
    };
  },
  created() {
    this.init();
    this.getList();
    //获取当前用户信息
    getInfo().then(response => {
      //初始化当前用户
      this.user = response["user"]
    });
  },
  methods: {
    /** 初始化 */
    init(){
      let roles = this.$store.state.user.roles;
      console.log(roles)
      if(roles.indexOf("admin") > -1 || roles.indexOf("GraduationLeader") > -1){
        //table表格列显示控制
        this.tableColumn.stuTopicStatus = false;
        this.tableColumn.stuTopicRemark = false;
        // this.checkButton.pass =  true;
        // this.checkButton.fail =  true;
      }if (roles.indexOf("teacher") > -1) {
        // this.checkButton.approve = true;
        // this.checkButton.reject = true;
      }if (roles.indexOf("student") > -1) {
        this.tableColumn.status = false;
        this.tableColumn.remark = false;
        this.queryForm.status = false;
        this.queryParams.status = "1";
        // this.checkButton.Apply = true;
      }

      console.log(this.checkButton)
      console.log(this.tableColumn)
    },
    /** 查询选题列表 */
    getList() {
      this.loading = true;
      listTitle(this.queryParams).then(response => {
        this.titleList = response.rows;
        this.total = response.total;
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
        id: undefined,
        titleName: undefined,
        teacherId: undefined,
        teacher:{
          userName: undefined,
          nickName: undefined
        },
        status: "0",
        remark: undefined
      };
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

    // 教师列表弹窗

    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.handleQuery();
    },
    /** 弹出表单-查询部门下拉树结构 */
    getFormDeptTree() {
      deptTreeSelect().then(response => {
        this.teacherDeptOptions = response.data;
      });
    },
    // 弹出表单-节点单击事件
    teacherHandleNodeClick(data) {
      this.teacherQueryParams.deptId = data.id;
      this.teacherHandleQuery();
    },
    /** 弹出表单-搜索按钮操作 */
    teacherHandleQuery() {
      this.teacherQueryParams.pageNum = 1;
      this.getTeacherList();
    },
    //弹出表单角色下拉框change事件
    teacherRoleChange(value) {
      this.teacherQueryParams.roleId = value;
      this.teacherHandleQuery();
    },
    //弹出表单表格数据获取
    getTeacherList() {
      this.loading = true;
      listUser(this.teacherQueryParams).then(response => {
          this.teacherList = response.rows;
          this.teacherTotal = response.total;
          this.loading = false;
        }
      );
    },
    // 弹出表单表格行选择事件
    handleCurrentChangeTeacher(row) {
      this.teacherListSelectRow = row;
    },
    // 弹出表单表格保存按钮
    handleTeacherSave() {
      if (this.teacherListSelectRow.userId == null){
        this.$message({
          message: "请选择教师",
          type: "warning"
        });
      }else {
        this.closeTeacherList();
        this.form.teacherId = this.teacherListSelectRow.userId;
        this.form.teacher.nickName = this.teacherListSelectRow.nickName;
      }
    },
    // 弹出表单表格清空按钮
    handleTeacherClear() {
      this.closeTeacherList();
      this.form.teacherId = null;
      this.form.teacher.userName = null;
    },
    //弹出表单教师列表行双击事件
    handleRowDblclickTeacher(row) {
      this.closeTeacherList();
      this.form.teacherId = row.userId;
      this.form.teacher.nickName = row.nickName;
    },
    //打开教师列表弹窗
    openTeacherList(){
      listRole({
        pageNum: 1,
        pageSize: 10,
        roleName: '指导老师'
      }).then(response => {
        this.roleIdAndName = response.rows;
        // 设置弹出表单角色下拉框默认值
        this.teacherQueryParams.roleId = this.roleIdAndName[0].roleId;
        this.teacherListTitle = "修改指导老师";
        this.teacherListOpen = true;
        this.getFormDeptTree()
        this.getTeacherList();
      });

    },
    // 关闭教师列表弹窗
    closeTeacherList(){
      this.teacherListOpen = false;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.formType = "add";
      this.form.status = "0";
      this.open = true;
      this.title = "添加选题";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const Id = row.id || this.ids
      getTitle(Id).then(response => {
        this.form = response.data;
        this.formType = "update";
        this.open = true;
        this.title = "修改选题";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id !== undefined) {
            updateTitle(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTitle(this.form).then(response => {
              this.$modal.msgSuccess("添加成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const Ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除选题编号为"' + Ids + '"的数据项？').then(function() {
        return delTitle(Ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('topic/title/export', {
        ...this.queryParams
      }, `topicTitle_${new Date().getTime()}.xlsx`)
    },
    //通过按钮
    handleCheckPass(row){
      row.status = "1";
      updateTitle(row).then(response => {
        this.$modal.msgSuccess(`选题《${row.titleName}》通过审核`);
        this.open = false;
        this.getList();
      });
    },
    //退回按钮
    handleCheckFail(row){

      this.$prompt('请输入退回原因：', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValidator: (value) => {
          if (value == null || value === '') {
            return '退回原因不能为空';
          }
        },
      }).then(({ value }) => {
        row.status = "2";
        row.remark = value;
        updateTitle(row).then(response => {
          this.$modal.msgWarning(`选题《${row.titleName}》已退回`);
          this.open = false;
          this.getList();
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消操作'
        });
      });
    },
    /** 申请按钮操作 */
    handleApply(row) {
      if (row.status !== '1'){
        this.$message({
          message: "该选题还未通过审核，请先审核！",
          type: "warning"
        });
        return;
      }else if (row.stuTopicId === 0){
        this.$message({
          message: "该选题正在申请中，请耐心等待教师审批！",
          type: "warning"
        });
        return;
      }
    addTopic({
      titleId: row.id,
      studentId: this.user.userId,
      status: "0",
      remark: "学生"+this.user.nickName+"申请选题《"+row.titleName+"》"
    }).then(response => {
      //更新学生选题关系id
      updateTitle({
        id: row.id,
        stuTopicId: response.data
      }).then(response => {
        this.$modal.msgSuccess("申请成功");
        this.open = false;
        this.getList();
      });
    });
    },
    /** 批准按钮操作 */
    handleApprove(row) {
      if (row.status !== '1'){
        this.$message({
          message: "该选题还未通过审核，请先审核！",
          type: "warning"
        });
        return;
      }else if (row.stuTopicId == null){
        this.$message({
          message: "该选题还未申请，请先申请！",
          type: "warning"
        });
        return;
      }
      updateTopic({
        id: row.stuTopicId,
        status: "1",
        remark: "学生"+this.user.nickName+"申请选题《"+row.titleName+"》已批准"
      }).then(response => {
        this.$modal.msgSuccess("批准成功");
        this.open = false;
        this.getList();
      });
    },
    /** 拒绝按钮操作 */
    handleReject(row) {
      if (row.status !== '1'){
        this.$message({
          message: "该选题还未通过审核，请先审核！",
          type: "warning"
        });
        return;
      }else if (row.stuTopicId == null){
        this.$message({
          message: "该选题还未申请，请先申请！",
          type: "warning"
        });
        return;
      }
      updateTopic({
        id: row.stuTopicId,
        status: "2",
        remark: "学生"+this.user.nickName+"申请选题《"+row.titleName+"》已拒绝"
      }).then(response => {
        this.$modal.msgSuccess("拒绝成功");
        this.open = false;
        this.getList();
      });
    }
  }
};
</script>
