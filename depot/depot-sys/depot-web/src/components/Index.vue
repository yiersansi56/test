<template xmlns="">


    <el-container class="body" style="height: 100%;width: 100%;overflow: clip">
      <el-header style="text-align: right; font-size: 10px;height: 100%;border-bottom: #b9bebe 1px solid;padding: 0;
      ;letter-spacing:3px;
      ;box-shadow: 0 5px 15px -5px rgba(0,0,0,.3);">
        <Header @doCollapse="doCollapse" :icon="icon" :key="timer"></Header>
      </el-header>

      <el-container style="height: 100%; border: 1px solid #eee">
        <el-aside :width="aside_width" style="background-color: rgb(238, 241, 246);height: 100%;margin-left: -1px;">
          <Aside :isCollapse="isCollapse"    ></Aside>
        </el-aside>
<div class="main" style="background-color: white;"  >
      <el-main  style="height: 100%;width: 100%;padding: 20px;box-sizing: border-box">
        <router-view @doFresh="doFresh"/>
      </el-main>
</div>
    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";
export default {
  name: "Index",
  components: { Header, Aside},
  data(){
    return{
      timer:'',
      isCollapse:false,
      aside_width:'200px',
      icon:'el-icon-s-fold'
    }
  },
  methods:{
    doCollapse(){
      this.isCollapse = !this.isCollapse
      if(!this.isCollapse){// 展开
        this.aside_width = '200px'
        this.icon = 'el-icon-s-fold'
      }else {//收起
        this.aside_width = '64px'
        this.icon = 'el-icon-s-unfold'
      }

    },
    doFresh(){
      this.timer = new Date().getTime()
    },


  }

}
</script>

<style scoped>

</style>