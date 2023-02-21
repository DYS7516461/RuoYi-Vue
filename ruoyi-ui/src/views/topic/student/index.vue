<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            node-key="id"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="学生名称" prop="studentName">
            <el-input
              v-model="queryParams.studentName"
              placeholder="请输入学生名称"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="教师名称" prop="teacherName">
            <el-input
              v-model="queryParams.teacherName"
              placeholder="请输入教师名称"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
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
              v-hasPermi="['system:user:add']"
            >分配</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:user:edit']"
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
              v-hasPermi="['system:user:remove']"
            >删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="studentList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="ID" align="center" key="id" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true" />
          <el-table-column label="部门" align="center" key="deptName" prop="deptName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="学生编号" align="center" key="studentId" prop="studentId" v-if="columns[2].visible" :show-overflow-tooltip="true"/>
          <el-table-column label="学生名称" align="center" key="studentName" prop="studentName" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="教师编号" align="center" key="teacherId" prop="teacherId" v-if="columns[4].visible" :show-overflow-tooltip="true" />
          <el-table-column label="指导老师" align="center" key="teacherName" prop="teacherName" v-if="columns[5].visible" :show-overflow-tooltip="true" />
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <div>
        <el-row>
          <el-col  :span="6" :xs="24">
            <div class="head-container">
              <el-input
                v-model="formDeptName"
                placeholder="请输入部门名称"
                clearable
                size="small"
                prefix-icon="el-icon-search"
                style="margin-bottom: 20px"
              />
            </div>
            <div class="head-container">
              <el-tree
                :data="formDeptOptions"
                :props="defaultProps"
                :expand-on-click-node="false"
                :filter-node-method="filterNode"
                ref="formTree"
                node-key="id"
                default-expand-all
                highlight-current
                @node-click="formHandleNodeClick"
              />
            </div>
          </el-col>
          <el-col  :span="18" :xs="24">
            <el-form ref="formQueryForm" :model="form" :rules="rules" size="small" :inline="true" label-width="80px">
              <el-form-item label="分组" prop="roleId">
                <el-select @change="formRoleChange"
                  v-model="formQueryParams.roleId"
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
                <el-button type="primary" size="mini" @click="handleTeacherSave">保存</el-button>
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
              v-show="formTotal>0"
              :total="formTotal"
              :page.sync="formQueryParams.pageNum"
              :limit.sync="formQueryParams.pageSize"
              @pagination="getTeacherList"
            />
          </el-col>
        </el-row>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listDist, addDist, updateDist, delDist, deptTreeSelect } from "@/api/topic/stuDist";
import { listUser } from "@/api/system/user";
import { listRole } from "@/api/system/role";
import { getToken } from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Student",
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组(id)
      ids: [],
      // 选中数组(studentId)
      studentIds: [],
      // 未选择禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      //弹出层表单-总条数
      formTotal: 0,
      //弹出层表单-选中教师id
      formTeacherId: null,
      // 用户表格数据
      studentList: null,
      // 教师表格数据
      teacherList: null,
      // 弹出层标题
      title: "",
      //弹出层角色下拉框
      roleIdAndName: [],
      //弹出层查询表单
      formQueryParams: {
        pageNum: 1,
        pageSize: 10,
        userType: '02',
        roleId: undefined
      },
      //弹出层表单-部门名称
      formDeptName: undefined,
      // 弹出层表单-部门树选项
      formDeptOptions: undefined,
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      //弹出层表单类型
      formType: "", // add, edit
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentName: undefined,
        teacherName: undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `ID`, visible: false },
        { key: 1, label: `部门`, visible: true },
        { key: 2, label: `学生编号`, visible: true },
        { key: 3, label: `学生名称`, visible: true },
        { key: 4, label: `教师编号`, visible: true },
        { key: 5, label: `教师名称`, visible: true }
      ],
      // 表单校验
      rules: {

      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    },
    // 根据名称筛选部门树
    formDeptName(val) {
      this.$refs.formTree.filter(val);
    }
  },
  created() {
    this.getList();
    this.getDeptTree();
  },
  methods: {
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listDist(this.queryParams).then(response => {
          this.studentList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
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
        this.formDeptOptions = response.data;
      });
    },
    // 弹出表单-节点单击事件
    formHandleNodeClick(data) {
      this.formQueryParams.deptId = data.id;
      this.formHandleQuery();
    },
    /** 弹出表单-搜索按钮操作 */
    formHandleQuery() {
      this.formQueryParams.pageNum = 1;
      this.getTeacherList();
    },

    // 取消按钮
    cancel() {
      this.open = false;
      // this.reset();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.queryParams.deptId = undefined;
      this.$refs.tree.setCurrentKey(null);
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.studentIds = selection.map(item => item.studentId);
      this.single = selection.length === 0;
      this.multiple = !selection.length;
    },
    //保存学生-指导老师关系
    distSave(){
      if (this.studentIds.length > 0 && this.formTeacherId != null) {
        let params = [];
        this.studentIds.forEach(item => {
          params.push({
            studentId: item,
            teacherId: this.formTeacherId
          });
        });
        if (this.formType === "add"){
          addDist(params).then(response => {
            this.$message({
              message: "分配成功",
              type: "success"
            });
            this.open = false;
            this.getList();
          });
        }else if (this.formType === "edit"){
          updateDist(params).then(response => {
            this.$message({
              message: "分配成功",
              type: "success"
            });
            this.open = false;
            this.getList();
          });
        }


      }
    },
    // 弹出表单表格行选择事件
    handleCurrentChangeTeacher(row) {
      this.formTeacherId = (row != null && row.userId != null) ? row.userId: null;
    },
    // 弹出表单表格保存按钮
    handleTeacherSave() {
      if (this.formTeacherId == null){
        this.$message({
          message: "请选择教师",
          type: "warning"
        });
        return;
      }else {
        this.distSave();
      }
    },
    //弹出表单教师列表行双击事件
    handleRowDblclickTeacher(row) {
      this.formTeacherId = row.userId;
      this.distSave();
    },


    //弹出表单角色下拉框change事件
    formRoleChange(value) {
      this.formQueryParams.roleId = value;
      this.formHandleQuery();
    },
    //弹出表单表格数据获取
    getTeacherList() {
      this.loading = true;
      listUser(this.formQueryParams).then(response => {
          this.teacherList = response.rows;
          this.formTotal = response.total;
          this.loading = false;
        }
      );
    },
    /** 分配按钮操作 */
    handleAdd() {
      // 判断所选学生是否已分配指导老师
      let flag = false;     //默认未分配指导老师
      // 判断是否选择学生
      if (this.studentIds.length === 0) {
        this.$message({
          message: "请选择学生！",
          type: "warning"
        });
        return;
      }else{        //已选择学生，判断是否已分配指导老师
        for (let i = 0; i < this.ids.length; i++) {
          if (this.ids[i] !== null) {
            flag = true;
            break;
          }
        }
        if (flag) {
          this.$message({
            message: "所选学生已分配指导老师！",
            type: "warning"
          });
          return;
        }

      }

      // 未分配指导老师，弹出分配指导老师窗口
      // this.reset();

      listRole({
        pageNum: 1,
        pageSize: 10,
        roleName: '指导老师'
      }).then(response => {
        this.roleIdAndName = response.rows;
        // 设置弹出表单角色下拉框默认值
        this.formQueryParams.roleId = this.roleIdAndName[0].roleId;
        this.formType = "add";
        this.open = true;
        this.title = "分配指导老师";
        this.getFormDeptTree();
        this.getTeacherList();
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      // 判断所选学生是否已分配指导老师
      let flag = true;     //默认未分配指导老师
      // 判断是否已分配指导老师
      for (let i = 0; i < this.ids.length; i++) {
        if (this.ids[i] == null) {
          flag = false;
          break;
        }
      }
      if (!flag) {
        this.$message({
          message: "所选学生未分配指导老师，请先分配指导老师！",
          type: "warning"
        });
        return;
      }else{
        listRole({
          pageNum: 1,
          pageSize: 10,
          roleName: '指导老师'
        }).then(response => {
          this.roleIdAndName = response.rows;
          // 设置弹出表单角色下拉框默认值
          this.formQueryParams.roleId = this.roleIdAndName[0].roleId;
          this.formType = "edit";
          this.open = true;
          this.title = "修改指导老师";
          this.getFormDeptTree();
          this.getTeacherList();
        });
      }
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.studentId != undefined) {
            updateUser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addUser(this.form).then(response => {
              this.$modal.msgSuccess("分配成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      let distIds = row.id || this.ids;
      let ids = [];
      for (let i = 0;i < distIds.length;i++){
        if(distIds[i] != null) ids.push(distIds[i]);
      }
      distIds = ids;
      const stuIds = row.studentId || this.studentIds;
      console.log(distIds);
      console.log(stuIds);
      this.$modal.confirm('是否确认删除编号为"' + stuIds + '"的学生的指导老师？').then(function() {
        return delDist(distIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
