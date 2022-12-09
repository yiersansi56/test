<template>
  <div id="app">
    <div id="register">
      <div class="pos">
        <h1 class="register1">用户注册</h1>
        <el-form
            :model="registerForm"
            status-icon
            ref="registerForm"
            :rules="registerRules"
            label-width="100px">
          <div class="ipt">
          <el-form-item  prop="username">
            <label>账号：</label>
            <el-input
                type="type"
                prefix-icon="el-icon-user"
                placeholder="输入注册账号"
                v-model="registerForm.username"
                style="width: 250px"></el-input>
          </el-form-item></div>

          <div class="ipt">
          <el-form-item  prop="name">
            <label>姓名：</label>
            <el-input
                type="type"
                prefix-icon="el-icon-user"
                placeholder="输入注册账号"
                v-model="registerForm.name"
                style="width: 250px"></el-input>
          </el-form-item></div>

          <div class="ipt">
          <el-form-item  prop="password">
            <label>密码：</label>
            <el-input type="password"
                      placeholder="输入密码"
                      prefix-icon="el-icon-menu"
                      v-model="registerForm.password"
                      style="width: 250px"></el-input>
          </el-form-item></div>

          <div class="passwords">
          <el-form-item  prop="password1">
            <label>确认密码：</label>
            <el-input type="password"
                      prefix-icon="el-icon-menu"
                      placeholder="再次输入密码"
                      v-model="registerForm.password1"
                      style="width: 250px"></el-input>
          </el-form-item>
          </div>

          <div class="check1">
          <el-form-item  prop="check">
            <label>验证码：</label>
            <el-input
                style="width: 160px "
                type="text"
                placeholder="点击图片更换验证码"
                name="check"
                ref="check"
                v-model="registerForm.checkCode"></el-input>
            <el-image style="margin-bottom: -15px;margin-left: 5px"

                      @click="changeCode()"
                      id="checkImg"
                      :src="imgUrl"></el-image>
          </el-form-item>
          </div>

          <el-form-item>
            <div class="btn">
            <el-button type="primary" @click="submitForm" >注册</el-button></div>

          </el-form-item>
        </el-form>
      </div>
    </div>

    <a class="drops" style="cursor: pointer;"
       @click="toLogin"><h4>登录</h4></a>
  </div>
</template>

<script>
export default {
  name: "Register",
  methods:{

    submitForm() {
      this.$axios({
        method: "post",
        url: this.$httpUrl+'/user/checkCode',
        data: {
          checkCode:this.registerForm.checkCode
        }
      }).then(res=>res.data).then(res => {
        if (res.code == 200) {
          //验证码正确
          this.doRegister();
        } else {
          this.$message({
            message: '验证码错误',
            type: 'error'
          })
        }

      })
    },

    doRegister(){
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          if (this.registerForm.password != this.registerForm.password1) {
            this.$message({
              message: '两次输入的密码不相同',
              type: 'warning'
            })
          } else {
            this.$axios({
              method: "POST",
              url: this.$httpUrl+'/user/save',
              data: this.registerForm
            }).then(res=>res.data).then(res => {
              if (res.code == 200) {
                //注册成功

                this.$message({
                  message: '恭喜你，注册成功',
                  type: 'success'
                })

                this.resetRegisterForm()

              } else {
                this.$message({
                  message: '注册失败,出现未知错误',
                  type: 'error'
                })
              }

            })
          }
        }
      })
    },
    changeCode(){
      this.imgUrl = this.$httpUrl+'/user/newCode?'+new Date().getUTCMilliseconds();
    },

    toLogin(){
      this.$router.replace('/')
    },

    resetRegisterForm() {
      this.$refs.registerForm.resetFields();
      this.registerForm.checkCode=''
    },


  },
  data(){
    let checkDuplicate =(rule,value,callback)=>{

      this.$axios.get(this.$httpUrl+"/user/findByUsername?username="+this.registerForm.username).then(res=>{
        if(res.data.code != 200){
          callback()
        }else{

          callback(new Error('账号已经存在'));
        }
      })
    };
    return{

      imgUrl: this.$httpUrl+'/user/newCode',
      registerForm:{
        username:'',
        name:'',
        password:'',
        password1:'',
        checkCode:'',
        roleId:'2'
      },
      registerRules: {
        username: [
          { required: true,message:'用户名不能为空，请输入用户名', trigger: "blur" },
          {min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur'},
          {validator:checkDuplicate,trigger: 'blur'}
        ],
        name:[
            { required: true,message:'姓名不能为空，请输入姓名', trigger: "blur" },
          {min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur'},
        ],
        password: [
          { required: true,message:'密码不能为空，请输入密码',  trigger: "blur"}
        ],
        password1: [
          { required: true,message:'确认密码不能为空，请输入密码',  trigger: "blur"}
        ]
      },
    }
  }
}
</script>

<style scoped>
#app,body,html {
  background-image: linear-gradient(to right,lightskyblue , lemonchiffon);
  background-size:100% 100%;
  width:100%;
  height:100%;
  position:fixed;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
#register {
  box-sizing: border-box;
  padding-left: 20px;
  transition: 0.8s;
  display: inline-block;
  position: relative;
  margin-left: 600px;
  margin-top:180px;
  width: 700px;
  height: 600px;
  box-shadow: inset 20px 20px 20px rgba(0, 0, 0, 0.05),
  25px 35px 20px rgba(0,0,0,0.05),
  25px 30px 30px rgba(0,0,0,0.05),
  inset -20px -20px 25px rgba(255, 255, 255, 0.9);
  border-radius:62% 38% 54% 46% / 18% 56% 44% 82%   ;
}
#register::before{
  content: '';
  position: absolute;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: white;
  top:80px;
  left: 100px;
}
#register::after{
  content: '';
  position: absolute;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: white;
  top:110px;
  left: 130px;
}

#register:hover{
  border-radius:39% 61% 36% 64% / 47% 40% 60% 53% ;
  transition: 0.8s;
}
.register1 {
  opacity: 0.5;
  color: lemonchiffon;
  font-size: 60px;
  transform: rotateX(6deg);
  margin-top: 70px;
  margin-left: 200px;
  font-family: 华文中宋;
  margin-bottom: 20px;

}
.pos {
  width: 450px;
  height: 350px;
  position: absolute;
  top: 25px;
  left: 25px;
}

.ipt{
  margin-left: 40px;
 }
label{
  color: lemonchiffon;
}

h4{
  color: lemonchiffon;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 40px;
  font-size: 23px;
}
.drops{
  display: inline-block;
  width:120px;
  height: 120px;
  box-shadow: inset 5px 10px 10px rgba(0, 0, 0, 0.05),
  5px 10px 5px rgba(0,0,0,0.01),
  10px 10px 10px rgba(0,0,0,0.05),
  inset -10px -10px 15px rgba(254, 254, 254, 0.9);
  border-radius:38% 62% 41% 59% / 49% 30% 70% 51%   ;
  transition: 0.8s;
  position: absolute;
  margin-left: -20px;
  margin-top: 600px;
}
.drops:hover{
  border-radius:84% 16% 68% 32% / 18% 61% 39% 82%  ;
  transition: 0.8s;
}
.drops::before{
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background-color: white;
}
.drops::after{
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: whitesmoke;
}
</style>