<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name"
                placeholder="请输入分类名"
                suffix-icon="el-icon-search"
                @keyup.enter.native="loadPost"
                style="width: 200px"></el-input>
      <el-button type="primary"
                 style="margin-left: 5px"
                 @click="loadPost">查询</el-button>
      <el-button type="success" @click="reset">重置</el-button>
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <el-table :data="tableData"
              style="width: 100%;font-size: 15px;border-radius: 5px;margin-top: 10px"
              :header-cell-style="{background: 'lightblue' ,color: '#555555' }"
              border>
      <el-table-column
          type="index"
          label="编号"
          align="center"
          width="60">
      </el-table-column>
      <el-table-column prop="name" label="分类名" align="center">
      </el-table-column>
      <el-table-column prop="remark" label="备注" align="center">
      </el-table-column>
      <el-table-column prop="operate" label="操作" align="center" width="200px">
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
        :page-sizes="[5, 10, 20, 30]"
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
        <el-form-item label="分类名" prop="name">
          <el-col :span="20">
            <el-input v-model="addOrModForm.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="addOrModForm.remark"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addOrModDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>



export default {
  name: "GoodstypeManage",

  beforeMount() {
    this.loadPost();
  },
  methods:{

      //点击修改按钮
      modify(row){
        this.addOrModDialogVisible = true
        this.$nextTick(()=>{
          this.addOrModForm = row
        })

      },
      //根据id删除
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
            url:this.$httpUrl+"/goodstype/deleteById?id="+id
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
      //做修改操作
      doMod(){
        this.$axios({
          method:'POST',
          url:this.$httpUrl+'/goodstype/update',
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
      //做插入操作
      doSave(){
        this.$axios({
          method:'POST',
          url:this.$httpUrl+'/goodstype/save',
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

      //点击对话框的确定按钮分析是添加还是修改
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
      //点击添加按钮
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
      loadPost(){
        this.$axios({
          method:'POST',
          url:this.$httpUrl+'/goodstype/listPage',
          data: {
            pageSize:this.pageSize,
            pageNum:this.pageNum,
            param:{
              name:this.name,
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

      },
      resetaddOrModForm() {
        this.$refs.addOrModForm.resetFields();
      },




  },
  data() {
    return {

      tableData: [],
      pageNum:1,
      pageSize:20,
      total:0,
      name:'',

      addOrModDialogVisible:false,
      addOrModForm:{
        id:'',
        name:'',
        remark:''
      },
      addRules: {
        name: [
          {required: true, message: '请输入分类名', trigger: 'blur'},
        ]
      }


    }

  }


}
</script>

<style scoped>

</style>