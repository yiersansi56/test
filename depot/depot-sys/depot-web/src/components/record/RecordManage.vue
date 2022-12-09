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

    </div>
      <!--数据表格    -->
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
      <el-table-column prop="goodsname" label="物品名" align="center">
      </el-table-column>
      <el-table-column prop="storagename" label="仓库" align="center">
      </el-table-column>
      <el-table-column prop="goodstypename" label="分类" align="center">
      </el-table-column>
      <el-table-column prop="adminname" label="操作人" align="center">
      </el-table-column>
      <el-table-column prop="username" label="申请人/补货人" align="center">
      </el-table-column>
      <el-table-column prop="count" label="数量" align="center">
      </el-table-column>
      <el-table-column prop="createtime" label="操作时间" align="center">
      </el-table-column>
      <el-table-column prop="remark" label="备注" align="center">
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
  </div>
</template>

<script>



export default {
  name: "RecordManage",

  beforeMount() {

    this.loadGoodsType()
    this.loadStorage()
    this.loadPost()
  },
  methods:{

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
            url:this.$httpUrl+"/record/deleteById?id="+id
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
          url:this.$httpUrl+'/record/listPage',
          data: {
            pageSize:this.pageSize,
            pageNum:this.pageNum,
            param:{
              name:this.name,
              storage: this.storage+'',
              goodstype: this.goodstype+'',
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




  },
  data() {

    return {

      user:JSON.parse(sessionStorage.getItem('userData')),
      goodstypeData:[],
      storageData:[],
      tableData: [],
      pageNum:1,
      pageSize:50,
      total:0,
      name:'',
      storage:'',
      goodstype: '',

      addOrModDialogVisible:false,
      addOrModForm:{
        id:'',
        name:'',
        storage:'',
        goodstype:'',
        count:'',
        remark:''
      },



    }

  }


}
</script>

<style scoped>

</style>