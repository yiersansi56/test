<template>

  <el-menu
      background-color="white"
      text-color="#333333"
      active-text-color="#ffd04b"
      style="height: 100vh"
      :collapse="isCollapse"
      :collapse-transition="false"
      class="menu"
      router
      default-active="/Personal">

      <el-menu-item index="/Personal" style="background-color: rgba(244,244,244,0.4);border-radius: 20px;margin-top: 10px">
        <i class="el-icon-s-home"></i>
        <span slot="title" >个人中心</span>
      </el-menu-item>
<div class="menu">
      <el-menu-item :index="'/'+item.menuclick" v-for="(item,i) in menu" :key="i" style="background-color: rgba(244,244,244,0.4);border-radius: 20px;margin-top: 10px">
        <i :class="item.menuicon"></i>
        <span slot="title">{{ item.menuname }}</span>
      </el-menu-item>
</div>



  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  mounted() {

  },
  methods:{

  },
  data(){
    return{

      user : JSON.parse(sessionStorage.getItem('userData'))


    }
  },
  computed:{
      "menu":{
            get(){
              return this.$store.state.menu
            }
      }
  },


  props:{
    isCollapse:Boolean
  }
}
</script>

<style scoped>
.menu{
  background-image: url("../assets/imgs/menu.jpg") ;
  background-repeat: no-repeat;
  background-size: 900%;
}
</style>