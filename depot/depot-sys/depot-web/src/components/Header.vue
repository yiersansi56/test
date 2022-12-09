<template>
  <div style="display: flex;line-height: 60px;width: 100%" >
    <div style="margin-top: 8px;padding-left: 10px">
      <i :class="icon" style="font-size: 20px;cursor: pointer "@click="collapse"></i>
    </div>
    <div style="flex: 1;text-align: center;font-size: 40px;color: #333333;font-family: 华文中宋;height: 40px;margin-left: 260px">
      <span >欢迎来到疫情物资管理系统</span>
    </div>
    <img :src="imgUrl" class="picture" style="border-radius: 50%;
        width: 50px;height: 50px;margin:8px 20px;z-index:1;cursor: pointer"
        @click="toUser" >
    <el-dropdown>
      <span style="margin-right: 5px">{{user.name}}
      <i class="el-icon-arrow-down" style="margin-left: -5px"></i>
      </span>
      <el-dropdown-menu slot="dropdown" class="cai">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logOut">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
    name: "Header",
    data(){
        return{
          imgUrl:'',
          user:JSON.parse(sessionStorage.getItem('userData')),
        }

    },
    created() {
        this.$router.push("/Personal")
    },
  mounted() {

    this.imgUrl = this.user.imgUrl
    console.log(this.user.imgUrl)
  },
  methods:{
      toUser(){
        this.$router.push("/Personal")
      },
      logOut(){
        this.$confirm('是否退出您的账号?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //用户点击确认按钮
              this.$router.push("/")
              sessionStorage.clear()
              this.$message({
                message:'恭喜您，退出成功',
                type:'success'
              })

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消退出'
          });
        });



      },
      collapse(){
        this.$emit('doCollapse')
      }
    },
    props:{
      icon:String
    }
}
</script>

<style scoped>


</style>