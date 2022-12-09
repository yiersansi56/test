<template>
  <div>
  <!--搜索框和添加按钮    -->
    <div style="margin-bottom: 5px">
      <el-input v-model="name"
                placeholder="请输入物品名"
                suffix-icon="el-icon-search"
                @keyup.enter.native="loadPost"
                style="width: 200px"></el-input>
      <el-select v-model="storage" placeholder="请选择仓库" style="margin-left: 5px;width: 150px">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="goodstype" placeholder="请选择分类" style="margin-left: 5px;width: 150px">
        <el-option
            v-for="item in goodstypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-button type="primary"
                 style="margin-left: 5px"
                 @click="loadPost">查询</el-button>
      <el-button type="success" @click="reset">重置</el-button>
      <el-button type="primary" @click="add" v-if="user.roleId!=2">新增</el-button>
      <el-button type="primary" @click="inGood" v-if="user.roleId!=2">入库</el-button>
      <el-button type="primary" @click="outGood" v-if="user.roleId!=2">出库</el-button>
    </div>
      <!--数据表格    -->
    <el-table :data="tableData"
              highlight-current-row
              height="790"
              @current-change="selectCurrentChange"
              style="width: 100%;font-size: 15px;border-radius: 5px;margin-top: 10px"
              :header-cell-style="{background: 'lightblue' ,color: '#555555' }"
              border>
      <el-table-column prop="id" label="物资编号" align="center" width="190" >
      </el-table-column>
      <el-table-column prop="name" label="物品名" align="center" width="150">
      </el-table-column>
      <el-table-column prop="storage" label="仓库" align="center" width="150" :formatter="formatStorage">
      </el-table-column>
      <el-table-column prop="goodstype" label="分类" align="center" :formatter="formatGoodsType" width="150" >
      </el-table-column>
      <el-table-column prop="count" label="数量" align="center" width="100" v-if="user.roleId!=2">
      </el-table-column>
      <el-table-column prop="remark" label="备注" align="center" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="operate" label="操作" align="center" width="190">
        <template slot-scope="scope">

          <el-button title="编辑"  icon="el-icon-edit" type="success" @click="modify(scope.row)"  v-if="user.roleId!=2" circle></el-button>
          <el-button title="删除" icon="el-icon-delete" type="danger" @click="deleteById(scope.row.id)"  v-if="user.roleId!=2" circle></el-button>
          <el-button type="primary" @click="apply(scope.row)" v-if="user.roleId==2">申请</el-button>
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
        title="提示"
        :visible.sync="addOrModDialogVisible"
        width="30%"
        center>
      <el-form ref="addOrModForm"
               :model="addOrModForm"
               :rules="addOrModRules"
               label-width="80px">
        <el-form-item label="物品名" prop="name">
          <el-col :span="20">
            <el-input v-model="addOrModForm.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="仓库" prop="storage">
          <el-col :span="20">
            <el-select v-model="addOrModForm.storage" placeholder="请选择仓库" style="margin-left: 5px;width: 150px">
              <el-option
                  v-for="item in storageData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="分类" prop="goodstype">
          <el-col :span="20">
            <el-select v-model="addOrModForm.goodstype" placeholder="请选择分类" style="margin-left: 5px;width: 150px">
              <el-option
                  v-for="item in goodstypeData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="20">
            <el-input v-model="addOrModForm.count"></el-input>
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

    <!--入库提示框  -->
    <el-dialog
        title="提示"
        :visible.sync="inDialogVisible"
        width="30%"
        center>
      <el-dialog
          width="70%"
          title="用户选择"
          :visible.sync="innerVisible"
          append-to-body>
        <SelectUser @doSelectUser="doSelectUser"></SelectUser>
      <span slot="footer" class="dialog-footer">
        <el-button @click="innerVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmUser">确 定</el-button>
      </span>
      </el-dialog>
      <el-form ref="inForm"
               :model="inForm"
               :rules="inRules"
               label-width="80px">
        <el-form-item label="物品名">
          <el-col :span="20">
            <el-input v-model="inForm.goodsname" readonly></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="申请人">
          <el-col :span="20">
            <el-input v-model="inForm.username" readonly @click.native="selectUser"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="20">
            <el-input v-model="inForm.count"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="inForm.remark"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="inDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="doInOrOutGoods">确 定</el-button>
      </span>
    </el-dialog>

    <!--申请提示框  -->
    <el-dialog
        title="提示"
        :visible.sync="applyDialogVisible"
        width="30%"
        center>
      <el-form ref="applyForm"
               :rules="addOrModRules"
               :model="applyForm"
               label-width="80px">
        <el-form-item label="选择">
        <el-radio v-model="applyForm.radio" label="1">入库</el-radio>
        <el-radio v-model="applyForm.radio" label="2">出库</el-radio>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="20">
            <el-input v-model="applyForm.count"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="申请理由" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="applyForm.apply"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="applyDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="subApply">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>





<script>

import SelectUser from "@/components/user/SelectUser";
export default {
  name: "GoodsManage",
  components:{SelectUser},

  beforeMount() {
    this.loadGoodsType()
    this.loadStorage()
    this.loadPost()
  },
  methods:{

    //点击申请按钮
      apply(row){
          this.applyDialogVisible = true
          this.applyForm.goods = row.id
          this.applyForm.goodsname = row.name
          this.applyForm.userId = this.user.id
      },
    subApply(){
      this.$axios({
        method:'POST',
        url:this.$httpUrl+'/approval/save',
        data: this.applyForm
      }).then(res=>res.data).then(res=>{
        if(res.code == 200){

          this.$message({
            message: '操作成功',
            type: 'success'
          });

          this.applyDialogVisible = false
          this.loadPost()
          this.resetApplyForm()

        }else{
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      })
    },

      doSelectUser(val){
        //console.log(val)
        this.tempUser = val
      },

      confirmUser(){
          this.inForm.username = this.tempUser.name
          this.inForm.userid = this.tempUser.id
          this.innerVisible = false
      },

      selectUser(){
          this.innerVisible = true
      },

      formatStorage(row){
          let temp = this.storageData.find(item=>{
            return item.id == row.storage
          })

        return temp && temp.name
      },
      formatGoodsType(row){
        let temp = this.goodstypeData.find(item=>{
          return item.id == row.goodstype
        })

        return temp && temp.name
      },

      // 修改按钮弹出对话框
      modify(row){
        this.addOrModDialogVisible = true

        this.$nextTick(()=>{
          this.addOrModForm = row
        })

      },
      //删除
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
            url:this.$httpUrl+"/goods/deleteById?id="+id
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
      // 做修改
      doMod(){
        this.$axios({
          method:'POST',
          url:this.$httpUrl+'/goods/update',
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
      // 做添加
      doSave(){
        this.$axios({
          method:'POST',
          url:this.$httpUrl+'/goods/save',
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

      //点击对话框的确定按钮并判断去做修改还是添加
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

      //选择记录
      selectCurrentChange(val){
            this.currentRow = val
      },
      // //点击入库按钮
      inGood(){
        if(!this.currentRow.id){
          this.$message({
            message:'请选择一条记录',
            type:'info'
          })
          return;
        }
        this.inDialogVisible = true
        this.$nextTick(()=>{
          this.resetInForm()
        })

        this.inForm.goodsname = this.currentRow.name
        this.inForm.goods = this.currentRow.id
        this.inForm.adminId = this.user.id
        this.inForm.action = '1'

      },
      //点击出库按钮
      outGood(){
        if(!this.currentRow.id){
          this.$message({
            message:'请选择一条记录',
            type:'info'
          })
          return;
        }
        this.inDialogVisible = true
        this.$nextTick(()=>{
          this.resetInForm()
        })

        this.inForm.goodsname = this.currentRow.name
        this.inForm.goods = this.currentRow.id
        this.inForm.adminId = this.user.id
        this.inForm.action = '2'
      },
    //做出入库操作方法
    doInOrOutGoods(){
      this.$axios({
        method:'POST',
        url:this.$httpUrl+'/record/save',
        data: this.inForm
      }).then(res=>res.data).then(res=>{
        if(res.code == 200){

          this.$message({
            message: '操作成功',
            type: 'success'
          });

          this.inDialogVisible = false
          this.loadPost()
          this.resetInForm()

        }else{
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      })


    },
      //重置入库表单
      resetInForm(){
        this.$refs.inForm.resetFields();
      },
      resetApplyForm(){
        this.$refs.applyForm.resetFields();
      },
      //重置新增修改表单
      resetaddOrModForm() {
        this.$refs.addOrModForm.resetFields();
      },
      //重置搜索框
      reset(){
        this.name = ''
        this.storage = ''
        this.goodstype = ''
      },
      //  新增按钮
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
          url:this.$httpUrl+'/goods/listPage',
          data: {
            pageSize:this.pageSize,
            pageNum:this.pageNum,
            param:{
              name:this.name,
              storage: this.storage+'',
              goodstype: this.goodstype+''
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
      //加载获取仓库名
      loadStorage(){
        this.$axios({
          method:'GET',
          url:this.$httpUrl+'/storage/list',
        }).then(res=>res.data).then(res=>{
          if(res.code == 200){

            this.storageData = res.data

          }else{
            alert('获取数据失败')
          }
        })
      },
      //加载获取商品类型
      loadGoodsType(){
      this.$axios({
        method:'GET',
        url:this.$httpUrl+'/goodstype/list',
      }).then(res=>res.data).then(res=>{
        if(res.code == 200){

          this.goodstypeData = res.data

        }else{
          alert('获取数据失败')
        }
      })
    },






  },
  data() {
    let checkCount = (rule, value, callback) => {
      if(value>999999 ){
        callback(new Error('数量输⼊过⼤'));
      }else{
        callback();
      }
    };
    return {


      tempUser:{},
      user:JSON.parse(sessionStorage.getItem('userData')),
      currentRow:{},
      goodstypeData:[],
      storageData:[],
      tableData: [],
      pageNum:1,
      pageSize:20,
      total:0,
      name:'',
      storage:'',
      goodstype: '',

      applyDialogVisible:false,
      inDialogVisible:false,
      addOrModDialogVisible:false,
      innerVisible:false,
      inForm:{
        goods:'',
        goodsname:'',
        count:'',
        username:'',
        userid:'',
        adminId:'',
        remark:'',
        action:"1"
      },
      addOrModForm:{
        id:'',
        name:'',
        storage:'',
        goodstype:'',
        count:'',
        remark:''
      },
      applyForm:{
        apply:'',
        goods:'',
        goodsname:'',
        count:'',
        username:'',
        userId:'',
        radio:'1'
      },
      inRules:{

      },
      addOrModRules: {
        name: [
          {required: true, message: '请输入物品名', trigger: 'blur'},
        ],
        goodstype: [
          {required: true, message: '请选择分类', trigger: 'blur'},
        ],
        storage: [
          {required: true, message: '请选择仓库名', trigger: 'blur'},
        ],
        count: [
          {required: true, message: '请输⼊数量', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,4}$/,message: '数量必须为正整数字',trigger: "blur"},
          {validator:checkCount,trigger: 'blur'}
        ],
      }


    }

  }


}
</script>

<style scoped>

</style>