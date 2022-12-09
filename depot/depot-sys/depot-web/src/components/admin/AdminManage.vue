<template>

  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name"
                placeholder="请输入名字"
                suffix-icon="el-icon-search"
                @keyup.enter.native="loadPost"
                style="width: 200px"></el-input>
      <el-select v-model="sex"
                 style="margin-left: 5px;width: 150px"
                 filterable placeholder="请选择性别">
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary"
                 style="margin-left: 5px"
                 @click="loadPost">查询</el-button>
      <el-button type="success" @click="reset">重置</el-button>
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <el-table :data="tableData"
              style="width: 100%;font-size: 15px; border-radius: 13px"
              :header-cell-style="{background: 'lightblue' ,color: '#555555', }">

      <el-table-column

          type="index"
          label="编号"
          align="center"
          width="60"
     >
      </el-table-column>

      <el-table-column prop="username" label="用户名" align="center" >
      </el-table-column>
      <el-table-column prop="name" label="姓名" align="center">
      </el-table-column>
      <el-table-column prop="age" label="年龄" align="center">
      </el-table-column>
      <el-table-column prop="sex" label="性别" align="center">
        <template slot-scope="scope">
          {{scope.row.sex === 1 ? '男' : '女'}}
        </template>
      </el-table-column>
      <el-table-column prop="roleId" label="角色" align="center">
        <template slot-scope="scope">
          {{scope.row.roleId === 0 ? '超级管理员' : (scope.row.roleId === 1 ? '管理员' : '用户')}}
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话" align="center">
      </el-table-column>
      <el-table-column prop="operate" label="操作" align="center">
        <template slot-scope="scope">
          <el-button title="编辑"  icon="el-icon-edit" type="success" @click="modify(scope.row)" circle></el-button>
          <el-button title="删除" icon="el-icon-delete" type="danger" @click="deleteById(scope.row.id)" circle></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页条    -->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

    <!--新增或修改对话框  -->
    <el-dialog
        title="添加"
        :visible.sync="addOrModDialogVisible"
        width="30%"
        center>
      <el-form ref="addOrModForm"
               :model="addOrModForm"
               :rules="addRules"
               label-width="80px">
        <el-form-item label="账号" prop="username">
          <el-col :span="20">
            <el-input v-model="addOrModForm.username"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-col :span="20">
            <el-input v-model="addOrModForm.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="20">
            <el-input v-model="addOrModForm.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-col :span="20">
            <el-input v-model="addOrModForm.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="addOrModForm.sex">
            <el-radio  label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-col :span="20">
            <el-input v-model="addOrModForm.phone"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addOrModDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
        </span>
    </el-dialog>
    <div class="new">
      <img class="picture1" src="../../assets/imgs/3d.png">
      <img class="picture2" src="../../assets/imgs/person2.png">
    </div>
  </div>
</template>

<script>



export default {
  name: "AdminManage",

  beforeMount() {
    // this.loadGet();
    this.loadPost();
  },
  methods:{

    modify(row){
      this.addOrModDialogVisible = true

      this.$nextTick(()=>{
        this.addOrModForm = row
        if(row.sex == 1){
          this.addOrModForm.sex = '1'
        }else this.addOrModForm.sex = '0'
      })

    },
    deleteById(id){
      this.$confirm('此操作将永久删除该数据, 是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //用户点击确认按钮
        //发送ajax请求，添加数据
        this.$axios({
          method:"GET",
          url:this.$httpUrl+"/user/deleteById?id="+id
        }).then(resp =>{
          if(resp.data.code == 200){

            this.loadPost();
            //弹出消息提示
            this.$message({
              message:'恭喜你，删除成功',
              type:'success'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    doMod(){
      this.$axios({
        method:'POST',
        url:this.$httpUrl+'/user/update',
        data: this.addOrModForm
      }).then(res=>res.data).then(res=>{
        if(res.code == 200){

          this.$message({
            message: '操作成功',
            type: 'success'
          });

          this.addOrModDialogVisible = false
          this.loadPost()
          this.resetaddOrModForm()

        }else{
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      })
    },
    doSave(){
      this.$axios({
        method:'POST',
        url:this.$httpUrl+'/user/save',
        data: this.addOrModForm
      }).then(res=>res.data).then(res=>{
        if(res.code == 200){

          this.$message({
            message: '操作成功',
            type: 'success'
          });

          this.addOrModDialogVisible = false
          this.loadPost()
          this.resetaddOrModForm()

        }else{
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      })


    },

    save(){

      this.$refs.addOrModForm.validate((valid) => {
        if (valid) {
          if(this.addOrModForm.id){
            this.doMod()
          }else{
            this.doSave()
          }

        } else {
          console.log('error submit!!');
          return false;
        }
      });



    },
    add(){
      this.addOrModForm.id = ''
      this.addOrModDialogVisible = true
      this.$nextTick(()=>{
        this.resetaddOrModForm()
      })

    },
    handleSizeChange(val) {
      //console.log(`每页 ${val} 条`);
      this.pageNum = 1
      this.pageSize = val
      this.loadPost();
    },
    handleCurrentChange(val) {
      //console.log(`当前页: ${val}`);
      this.pageNum = val
      this.loadPost();
    },
    loadGet(){
      this.$axios({
        method:'GET',
        url:this.$httpUrl+'/user/list',
      }).then(res=>{
        console.log(res.data)
      })

    },
    loadPost(){
      this.$axios({
        method:'POST',
        url:this.$httpUrl+'/user/listPageC1',
        data: {
          pageSize:this.pageSize,
          pageNum:this.pageNum,
          param:{
            name:this.name,
            sex:this.sex,
            roleId:'1'

          }
        }
      }).then(res=>res.data).then(res=>{

        if(res.code == 200){

          this.tableData = res.data
          this.total = res.total

        }else{
          alert('获取数据失败')
        }
      })

    },
    reset(){
      this.name = ''
      this.sex = ''

    },
    resetaddOrModForm() {
      this.$refs.addOrModForm.resetFields();
    },




  },
  data() {
    let checkAge = (rule, value, callback) => {
      if(value>150){
        callback(new Error('年龄输⼊过⼤'));
      }else{
        callback();
      }
    };
    let checkDuplicate =(rule,value,callback)=>{
      if(this.addOrModForm.id){
        return callback();
      }
      this.$axios.get(this.$httpUrl+"/user/findByUsername?username="+this.addOrModForm.username).then(res=>{
        if(res.data.code != 200){

          callback()
        }else{

          callback(new Error('账号已经存在'));
        }
      })
    };

    return {

      tableData: [],
      pageNum:1,
      pageSize:20,
      total:0,
      name:'',
      sex:'',
      sexs:[{
        value: '1',
        label: '男'
      }, {
        value: '0',
        label: '女'
      }
      ],
      addOrModDialogVisible:false,
      addOrModForm:{
        id:'',
        username:'',
        name:'',
        password:'',
        age:'',
        phone:'',
        sex:'1',
        roleId:'1'
      },
      addRules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'},
          {validator:checkDuplicate,trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请输入名字', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'}
        ],
        age: [
          {required: true, message: '请输⼊年龄', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,3}$/,message: '年龄必须为正整数字',trigger: "blur"},
          {validator:checkAge,trigger: 'blur'}
        ],
        phone: [
          {required: true,message: "⼿机号不能为空",trigger: "blur"},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输⼊正确的⼿机号码", trigger:
                "blur"}
        ]
      }


    }

  }


}
</script>

<style scoped>
.new{
  width: 87%;
  height: 200px;
  position: absolute;
  bottom: 0;
  background-image: linear-gradient(to bottom,lavender,lightcyan,white);
  border-radius: 50%;
  margin-bottom: -100px;
}
.new .picture1{
  width: 100px;
  height: 200px;
  margin-top: -120px;
  position: absolute;
  margin-left: 200px;
}
.new .picture2{
  width: 100px;
  height: 200px;
  margin-top: -120px;
  position: absolute;
  margin-left: 400px;
}
</style>