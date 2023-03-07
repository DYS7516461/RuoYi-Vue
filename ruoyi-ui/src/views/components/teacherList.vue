<template>
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
</template>

<script>
export default {

  name: "teacherList",
  data() {
    return {
      open: false,
      title: "教师列表",
      loading: false,
      teacherList: [],
      formTotal: 0,
      //弹出层角色下拉框
      roleIdAndName: [],
      formQueryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: 0,
        roleId: 0,
        userId: "",
        nickName: ""
      },
      formDeptName: "",
      formDeptOptions: [],
      defaultProps: {
        children: "children",
        label: "deptName"
      },
      form: {
        roleId: 0
      },
      rules: {
        roleId: [
          {required: true, message: "请选择分组", trigger: "change"}
        ]
      }
    };
  },
  methods: {
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 获取教师列表
    getTeacherList() {
      this.loading = true;
      this.$http
        .post("/system/user/list", this.formQueryParams)
        .then(res => {
          this.loading = false;
          if (res.code === 0) {
            this.teacherList = res.data.list;
            this.formTotal = res.data.total;
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch(err => {
          this.loading = false;
          this.$message.error(err);
        });
    },
    // 获取部门列表
    getDeptList() {
      this.$http
        .get("/system/dept/list")
        .then(res => {
          if (res.code === 0) {
            this.formDeptOptions = res.data;
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch(err => {
          this.$message.error(err);
        });
    },
    // 获取角色列表
    getRoleList() {
      this.$http
        .get("/system/role/list")
        .then(res => {
          if (res.code === 0) {
            this.roleIdAndName = res.data;
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch(err => {
          this.$message.error(err);
        });
    },
    // 保存教师
    handleTeacherSave() {
      this.$refs["formQueryForm"].validate(valid => {
        if (valid) {
          this.$emit("teacherSave", this.form);
        }
      });
    },
    // 选择部门
    formHandleNodeClick(data) {
      this.formDeptName = data.deptName;
      this.formQueryParams.deptId = data.id;
      this.getTeacherList();
    },
    // 选择分组
    formRoleChange() {
      this.getTeacherList();
    },
    // 搜索教师
    handleSearchTeacher() {
      this.formQueryParams.pageNum = 1;
      this.getTeacherList();
    },
    // 双击教师
    handleRowDblclickTeacher(row) {
      this.form.roleId = row.roleId;
    },
    // 选择教师
    handleCurrentChangeTeacher(row) {
      this.form.roleId = row.role
    },
  }
};
</script>

<style scoped>

</style>
