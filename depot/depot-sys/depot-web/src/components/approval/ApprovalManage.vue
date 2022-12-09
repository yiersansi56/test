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
      <el-select v-model="Status" placeholder="请选择状态" style="margin-left: 5px;width: 150px">
        <el-option
            v-for="item in OpStatus"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary"
                 style="margin-left: 5px"
                 @click="loadPost">查询</el-button>
      <el-button type="success" @click="reset">重置</el-button>

    </div>
      <!--数据表格    -->
    <el-table :data="tableData"
              class="record"
              style="width: 100%;font-size: 15px;;border-radius: 5px;margin-top: 10px"
              :header-cell-style="{background: 'lightblue' ,color: '#555555' }"
              :row-class-name="tableRowClassName"
               >
      <el-table-column
          type="index"
          label="编号"

          width="60">
      </el-table-column>
      <el-table-column prop="goodsname" label="物品名" align="center">
      </el-table-column>
      <el-table-column prop="storagename" label="仓库" align="center">
      </el-table-column>
      <el-table-column prop="goodstypename" label="分类" align="center">
      </el-table-column>
      <el-table-column prop="username" label="申请人" align="center">
      </el-table-column>
      <el-table-column prop="count" label="数量" align="center">
      </el-table-column>
      <el-table-column prop="createtime" label="申请时间" align="center" width="180px">
      </el-table-column>
      <el-table-column prop="status" label="当前状态" align="center">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.status === -1 ? 'danger' : (scope.row.status === 1 ? 'success' : 'primary')"
              disable-transitions>{{scope.row.status === 0 ? '等待审批' :
              (scope.row.status === 1 ? '审批通过' : '审批未通过')}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="apply" label="申请理由" align="center">
      </el-table-column>
      <el-table-column prop="reply" label="回复" align="center">
      </el-table-column>
      <el-table-column prop="operate" label="操作" align="center" v-if="user.roleId!=2">
        <template slot-scope="scope">
          <el-button id="applyBtn" type="success" @click="apply(scope.row)" v-if="scope.row.isValid==0">审核</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
        title="提示"
        :visible.sync="applyDialogVisible"
        width="30%"
        center>
      <el-form ref="applyForm"
               :model="applyForm"
               label-width="80px">
        <el-form-item label="操作">
          <el-radio-group v-model="applyForm.status">
            <el-radio :label="0">等待审批</el-radio>
            <el-radio :label="-1">驳回审批</el-radio>
            <el-radio :label="1">通过审批</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="回复" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="applyForm.reply"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="applyDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="subReply">确 定</el-button>
      </span>
    </el-dialog>

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
  </div>
</template>

<script>



export default {
  name: "ApprovalManage",

  beforeMount() {

    this.loadGoodsType()
    this.loadStorage()
    this.loadPost()
  },
  methods:{


    subReply(){

      this.applyForm.isValid = 1

      this.$axios({
        method:'POST',
        url:this.$httpUrl+'/approval/update',
        data: this.applyForm
      }).then(res=>res.data).then(res=>{
        if(res.code == 200){

          this.$message({
            message: '操作成功',
            type: 'success'
          });

          this.addRecordForm.status = this.applyForm.status
          this.addRecordForm.remark = this.applyForm.reply
          this.applyDialogVisible = false
          this.loadPost()
          this.addRecord()
          this.resetApplyForm()

        }else{
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      })
    },

    addRecord(){
      this.$axios({
        method:'POST',
        url:this.$httpUrl+'/record/saveApproval',
        data: this.addRecordForm
      }).then(res=>res.data).then(res=>{
        if(res.code == 200){



        }else{
        }
      })
    },
    
    //点击审批按钮
    apply(row){

      this.addRecordForm.goods = row.goods
      this.addRecordForm.adminId = this.user.id
      this.addRecordForm.userid = row.userId
      this.addRecordForm.goodsname = row.name
      this.addRecordForm.count = row.count
      this.applyDialogVisible = true
      this.applyForm.id = row.id
      this.applyForm.status = row.status
      this.applyForm.adminId = this.user.id
      this.applyForm.goods = row.goods

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
          url:this.$httpUrl+'/approval/listPage',
          data: {
            pageSize:this.pageSize,
            pageNum:this.pageNum,
             param:{
                name:this.name,
                storage: this.storage+'',
                goodstype: this.goodstype+'',
                status: this.Status+'',
                roleId:this.user.roleId+'',
                userId:this.user.id+''
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
        this.storage = ''
        this.goodstype = ''
        this.Status = ''

      },


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

    resetApplyForm(){
      this.$refs.applyForm.resetFields();
    },

    tableRowClassName({row, rowIndex}) {
      if (row.status === 0) {
        return 'warning-row';
      } else if (row.status === 1) {
        return 'success-row';
      }else if (row.status === -1) {
        return 'error-row';
      }
      return '';
    }




  },
  data() {

    return {

      user:JSON.parse(sessionStorage.getItem('userData')),
      goodstypeData:[],
      storageData:[],
      tableData: [],
      Status:'',
      pageNum:1,
      pageSize:50,
      total:0,
      name:'',
      storage:'',
      goodstype: '',

      applyDialogVisible:false,
      addOrModDialogVisible:false,
      addOrModForm:{
        id:'',
        name:'',
        storage:'',
        goodstype:'',
        count:'',
        remark:''
      },
      applyForm:{
        id:'',
        goods:'',
        status:'',
        reply:'',
        adminId: '',
        isValid:''
      },
      addRecordForm:{
        goods:'',
        goodsname:'',
        count:'',
        username:'',
        userid:'',
        adminId:'',
        remark:'',
        status:''
      },
      OpStatus:[{
        value: '1',
        label: '通过审批'
      }, {
        value: '0',
        label: '等待审批'
      }, {
        value: '-1',
        label: '审批未通过'
      }]



    }

  }


}
</script>

<style scoped>

</style>