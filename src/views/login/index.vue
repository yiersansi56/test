<template>
  <div class="login">
    <div class="login-con">
      <ul class="menu-tab">
        <li v-for="(v) in MenuData" :class="{current:v.current}" :key="v.type" @click="clickMenu(v)">
       {{v.txt}}
        </li>
      </ul>
    <!--表单部分-->
    <div style="weight:500px;height:400px;margin-top:50px;">
    <el-form
    ref="ruleFormRef"
    :model="ruleForm"
    status-icon
    :rules="rules"
    class="demo-ruleForm"
  >
  
    <el-form-item  prop="username">
      <label>username</label>
      <el-input v-model="ruleForm.username" type="text" autocomplete="off" />
    </el-form-item>
    <el-form-item  prop="password">
      <label>password</label>
      <el-input
        v-model="ruleForm.password"
        type="password"
        autocomplete="off"
        minlength="6"
        maxlength="15"
      />
    </el-form-item>
    <el-form-item  prop="passwords" v-show="model==='register'">
      <label>confirm</label>
       <el-input
        v-model="ruleForm.passwords"
        type="password"
        
        minlength="6"
        maxlength="15"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" class="login-button" @click="submitForm(ruleFormRef)"
        >{{model==="login"?'登录':'注册'}}</el-button
      >
      <el-button type="primary" class="reset-button" @click="resetForm(ruleFormRef)" style="margin-left:0px"
       >Reset</el-button
      >
    </el-form-item>
  </el-form>
    </div>
    </div>
  </div>
</template>

<script lang="ts" setup >
//创建复杂类型
  import { reactive, ref ,onMounted} from 'vue'
  import type { FormInstance } from 'element-plus'
  import * as ck from "../../utils/verfifcation.js"
  import link from "../../api/Link.js"
  import apiUrl from "../../api/url.js"
  import {useRouter} from "vue-router"
  let router=useRouter()
  const MenuData=reactive([
    {txt:"登录",current:true,type:"login"},
    {txt:"注册",current:false,type:"register"}
  ]);

  onMounted(()=>{
    console.log(process.env.VUE_APP_API)
  })
  let model=ref("login")

  let clickMenu=(item)=>{
      MenuData.forEach((elemt)=>{
        elemt.current=false
      })
      item.current=true;
      model.value=item.type;

  }

const ruleFormRef = ref<FormInstance>()

const checkUser = (rule: any, value: any, callback: any) => {
 
 if (!value) {
    return callback(new Error('Please input the username'))
  }else if(ck.CKUsername(value)){
    return callback(new Error("用户名格式不正确！"))
  }else{
    callback()
  }
}





const validatePass = (rule: any, value: any, callback: any) => {
  
  if (value === '') {
    callback(new Error('密码不能为空'))
  } else if(ck.CKPass(value)){
    callback(new Error('密码格式有误必须在6至15位的字母+数字'))
    }else{
    callback()
  }
}

const validatePass2 = (rule: any, value: any, callback: any) => {
  if(model.value==="login"){
    callback()
  }

  if (value === '') {
    callback(new Error('重复密码不能为空'))
  } else if (value !== ruleForm.password) {
    callback(new Error("两次密码必须相同"))
  } else {
    callback()
  }
}

const ruleForm = reactive({
  username:'',
  password:'',
  passwords:'',
  pass: '',
  checkPass: '',
  age: '',
})

const rules = reactive({
  password: [{ validator: validatePass, trigger: 'blur' }],
  passwords: [{ validator: validatePass2, trigger: 'blur' }],
  username: [{ validator: checkUser, trigger: 'blur' }],
})

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
     if(model.value==="login"){
       console.log("登录");
     }else{

       let data={
         name:ruleForm.username,
         pwd:ruleForm.password
       }
  link(apiUrl.register,"POST",data).then((ok:any)=>{
  console.log(ok);
})
     }



    } else {
      console.log('error submit!')
      return false
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>


<style lang="scss">
  .login{
    background-image:linear-gradient(to top,#ffffff,lightblue);
    background-repeat: no-repeat;
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
  .demo-ruleForm{
    width:30%;
    margin: 60px auto;
    label{
      font-size: 18px;
      font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
  }
  .login-button,.reset-button{
    margin-top: 20px;
    width:100%;
    background-color: honeydew;
  }
  .login-button:hover,.reset-button:hover{
    background-color: lightgreen;
  }
</style>