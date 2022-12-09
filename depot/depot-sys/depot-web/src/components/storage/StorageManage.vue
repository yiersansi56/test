<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name"
                placeholder="请输入仓库名"
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
      <el-table-column prop="name" label="仓库名" align="center">
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
        <el-form-item label="仓库名" prop="name">
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
<!--    <div-->
<!--        class="echart"-->
<!--        id="mychart"-->
<!--        :style="{ float: 'left', width: '90%', height: '400px' ,bottom:'0%',position:'absolute'}"-->
<!--    ></div>-->
  </div>
</template>

<script>
import * as echarts from "echarts";


export default {
  name: "StorageManage",

  beforeMount() {
    this.loadPost();
  },
  mounted() {
    this.initEcharts();

  },
  methods:{
    initEcharts() {
      const option = {
        xAxis: {
          data: ['Animals', 'Fruits', 'Cars']
        },
        yAxis: {},
        dataGroupId: '',
        animationDurationUpdate: 500,
        series: {
          type: 'bar',
          id: 'sales',
          data: [
            {
              value: 5,
              groupId: 'animals'
            },
            {
              value: 2,
              groupId: 'fruits'
            },
            {
              value: 4,
              groupId: 'cars'
            }
          ],
          universalTransition: {
            enabled: true,
            divideShape: 'clone'
          }
        }
      };
      const drilldownData = [
        {
          dataGroupId: 'animals',
          data: [
            ['Cats', 4],
            ['Dogs', 2],
            ['Cows', 1],
            ['Sheep', 2],
            ['Pigs', 1]
          ]
        },
        {
          dataGroupId: 'fruits',
          data: [
            ['Apples', 4],
            ['Oranges', 2]
          ]
        },
        {
          dataGroupId: 'cars',
          data: [
            ['Toyota', 4],
            ['Opel', 2],
            ['Volkswagen', 2]
          ]
        }
      ];

      const myChart = echarts.init(document.getElementById("mychart"));// 图标初始化
      myChart.setOption(option);// 渲染页面
      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        myChart.resize();

      });
      myChart.on('click', function (event) {
        if (event.data) {
          var subData = drilldownData.find(function (data) {
            return data.dataGroupId === event.data.groupId;
          });
          if (!subData) {
            return;
          }
          myChart.setOption({
            xAxis: {
              data: subData.data.map(function (item) {
                return item[0];
              })
            },
            series: {
              type: 'bar',
              id: 'sales',
              dataGroupId: subData.dataGroupId,
              data: subData.data.map(function (item) {
                return item[1];
              }),
              universalTransition: {
                enabled: true,
                divideShape: 'clone'
              }
            },
            graphic: [
              {
                type: 'text',
                left: 50,
                top: 20,
                style: {
                  text: 'Back',
                  fontSize: 18
                },
                onclick: function () {
                  myChart.setOption(option);
                }
              }
            ]
          });
        }
      });
    },
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
            url:this.$httpUrl+"/storage/deleteById?id="+id
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
          url:this.$httpUrl+'/storage/update',
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
          url:this.$httpUrl+'/storage/save',
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
    //点击添加按钮
      add(){
        this.addOrModDialogVisible = true
        this.$nextTick(()=>{
          this.addOrModForm.id = ''
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
          url:this.$httpUrl+'/storage/listPage',
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
          {required: true, message: '请输入仓库名', trigger: 'blur'},
        ]
      }


    }

  }


}
</script>

<style scoped>

</style>