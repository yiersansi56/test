<template>
  <div class="login">
    <div class="login-con">
      <ul class="menu-tab">
        <li v-for="(v) in MenuData" :class="{current:v.current}" :key="v.type" @click="clickMenu(v)">
       {{v.txt}}
        </li>
   </ul>
    </div>
  </div>
</template>

<script lang="ts" setup >
//创建复杂类型
  import {reactive} from "vue"

  const MenuData=reactive([
    {txt:"登录",current:true,type:"login"},
    {txt:"注册",current:false,type:"login"}
  ]);
  let clickMenu=(item)=>{
      MenuData.forEach((elemt)=>{
        elemt.current=false
      })
      item.current=true
  }
</script>


<style lang="scss">
  .login{
    background-color: pink;
    height:100%;
    width: 100%;
  }
  html,#app,body{
    height: 100%;
  }
  .menu-tab{
    text-align: center;
    li{
      height: 36px;
     
      display: inline-block;
      width: 88px;
      line-height: 36px;
      font-size: 14px;
      color: white;
      border-radius: 3px;
      cursor: pointer;
    }
  }
  .current{
    background-color:rgba(255,255,255,0.5) ;
  }
</style>