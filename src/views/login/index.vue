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
  
    <el-form-item  prop="pass">
      <label>username</label>
      <el-input v-model="ruleForm.username" type="text" autocomplete="off" />
    </el-form-item>
    <el-form-item  prop="checkPass">
      <label>password</label>
      <el-input
        v-model="ruleForm.password"
        type="password"
        autocomplete="off"
      />
    </el-form-item>
    <el-form-item  prop="age" v-show="model==='register'">
      <label>confirm</label>
      <el-input v-model="ruleForm.passwords" type="password" />
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
  import { reactive, ref } from 'vue'
  import type { FormInstance } from 'element-plus'

  const MenuData=reactive([
    {txt:"登录",current:true,type:"login"},
    {txt:"注册",current:false,type:"register"}
  ]);

  let model=ref("login")

  let clickMenu=(item)=>{
      MenuData.forEach((elemt)=>{
        elemt.current=false
      })
      item.current=true;
      model.value=item.type;

  }

const ruleFormRef = ref<FormInstance>()

const checkAge = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('Please input the age'))
  }
  setTimeout(() => {
    if (!Number.isInteger(value)) {
      callback(new Error('Please input digits'))
    } else {
      if (value < 18) {
        callback(new Error('Age must be greater than 18'))
      } else {
        callback()
      }
    }
  }, 1000)
}

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password'))
  } else {
    if (ruleForm.checkPass !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('checkPass', () => null)
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password again'))
  } else if (value !== ruleForm.pass) {
    callback(new Error("Two inputs don't match!"))
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
  pass: [{ validator: validatePass, trigger: 'blur' }],
  checkPass: [{ validator: validatePass2, trigger: 'blur' }],
  age: [{ validator: checkAge, trigger: 'blur' }],
})

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      console.log('submit!')
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