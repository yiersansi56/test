<template>
  <div>
  <div id="app">
    <div id="admin">
      <div class="pos">
        <h1 class="login">用户登录</h1>
        <el-form
            :model="loginForm"
            status-icon
            :rules="loginRules"
            ref="loginForm"

            class="demo-loginForm">
          <div class="input">
          <el-form-item  prop="username">
            <label>账号：</label>
            <el-input
                prefix-icon="el-icon-user"
                style="width: 300px"
                name="username"
                ref="username"
                type="text"
                v-model="loginForm.username"
            ></el-input>
          </el-form-item>
          </div>
          <div class="input">
          <el-form-item prop="password">
            <label>密码：</label>
            <el-input
                prefix-icon="el-icon-menu"
                style="width: 300px;"
                show-password
                name="password"
                ref="password"
                type="password"
                v-model="loginForm.password"
                class="demo-inputForm"
            ></el-input>
          </el-form-item></div>
          <div class="check">
          <el-form-item  prop="check">
            <label>验证码：</label>
            <el-input
                style="width: 160px "
                type="text"
                placeholder="点击图片更换验证码"
                name="check"
                ref="check"
                v-model="loginForm.checkCode"></el-input>
            <el-image style="margin-bottom: -15px;margin-left: 15px"
                      @click="changeCode()"
                      :src="imgUrl"></el-image>
          </el-form-item>
          </div>
          <el-form-item
              style="margin-bottom: -2px">
            <el-checkbox
                style="margin-top: -20px"
                v-model="remember">记住密码</el-checkbox>
          </el-form-item>
          <el-form-item style="margin-top: 10px">
            <div class="button">
            <el-button type="primary"
                       @click="submitForm">提交</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>

      <a class="drop" style="cursor: pointer;"
         @click="toRegister"><h4>注册</h4></a>
      <a class="drops" style="cursor: pointer;"
        @click="ForgetPwdBtn"><h4>忘记密码</h4></a>

  </div>



<div class="forget">
    <el-dialog
        title="修改密码"
        :visible.sync="ForgetPwDialogVisible"
        center>
      <el-form ref="ForgetPwdForm"
               :model="ForgetPwdForm"
               :rules="ForgetRules"
               label-width="80px">

        <el-form-item prop="username">
          <label >账号:</label>
          <el-col :span="20">
            <el-input v-model="ForgetPwdForm.username"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item  prop="email">
          <label>邮箱:</label>
          <el-col :span="20">
            <el-input v-model="ForgetPwdForm.email"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item  prop="password">
          <label>密码:</label>
          <el-col :span="20">
            <el-input type="password" v-model="ForgetPwdForm.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item  prop="password1">
          <label style="margin-left: -380px">确认密码:</label>
          <el-col :span="20">
            <el-input type="password" v-model="ForgetPwdForm.password1"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="ForgetPwDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="subForgetPaw">确 定</el-button>
          </span>
    </el-dialog>
</div>
  </div>

</template>


<script>
import '../assets/global.css'
export default {
  name: "Login",

  mounted() {
    this.changeCode()
    this.getCookie()
  },

  methods:{
    ForgetPwdBtn(){
      this.ForgetPwDialogVisible = true
      this.resetForgetPwdForm()
    },
    subForgetPaw(){
      this.$refs.ForgetPwdForm.validate(valid => {
            if (valid) {
              if (this.ForgetPwdForm.password != this.ForgetPwdForm.password1) {
                this.$message({
                  message: '两次输入的密码不相同',
                  type: 'warning'
                })
              }else{
                this.$axios({
                  method: "POST",
                  url: this.$httpUrl+'/user/findPwd',
                  data: this.ForgetPwdForm
                }).then(res=>res.data).then(res => {
                  if (res.code == 200) {

                    this.resetForgetPwdForm()
                    this.$message({
                      message: '恭喜你，修改成功',
                      type: 'success'
                    })
                  } else {
                    this.$message({
                      message: '修改失败,请输入正确的账号或邮箱',
                      type: 'warning'
                    })
                  }

                })
              }

            }
      })
    },

    submitForm() {

        this.$axios({
          method: "POST",
          url: this.$httpUrl+'/user/checkCode',
          data: {
            checkCode:this.loginForm.checkCode
          }
        }).then(res=>res.data).then(res => {
          if (res.code == 200) {
            //验证码正确
            this.login();
          } else {

            this.$message({
              message: '验证码错误',
              type: 'error'
            })
          }

        })
    },

        login() {
          this.$refs.loginForm.validate(valid => {
            if (valid) {
              this.$axios({
                method: "POST",
                url: this.$httpUrl+'/user/login',
                data: this.loginForm
              }).then(res=>res.data).then(res => {
                if (res.code == 200) {
                  if (this.remember == true) {

                    this.setCookie(this.loginForm.username, this.loginForm.password, 7)
                  } else {

                    this.setCookie('', '', -1)// 清除cookie
                  }

                  //存储
                  sessionStorage.setItem("userData", JSON.stringify(res.data.user));

                  this.$store.commit("setMenu",res.data.menu)

                  //登录成功跳转主页
                  this.$router.replace('/Index')

                  this.$message({
                    message: '恭喜你，登录成功',
                    type: 'success'
                  })
                } else {
                  this.$message({
                    message: '登录失败,请输入正确的用户名或密码',
                    type: 'warning'
                  })
                }

              })
            }
          })
        },

    getCookie() {
      if (document.cookie.length > 0) {
        var arr = document.cookie.split(';')
        for (var i = 0; i < arr.length; i++) {
          var userKey = arr[i].split('=')
          if (userKey[0].trim() == 'username') {
            this.loginForm.username = userKey[1]
          } else if (userKey[0].trim() == 'password') {
            this.loginForm.password = userKey[1]
          } else if (userKey[0].trim() == 'remember') {
            this.remember = Boolean(userKey[1])
          }
        }
      }
    },
    // 存储
    setCookie(username, password, days) {
      var curDate = new Date()
      curDate.setTime(curDate.getTime() + 24 * 60 * 60 * 1000 * days) // 设置cookie存储的有效时间
      window.document.cookie = 'username' + '=' + username + ';path=/;expires=' + curDate.toGMTString()
      window.document.cookie = 'password' + '=' + password + ';path=/;expires=' + curDate.toGMTString()
      window.document.cookie = 'remember' + '=' + this.remember + ';path=/;expires=' + curDate.toGMTString()
    },

    changeCode(){
      this.imgUrl = this.$httpUrl+'/user/newCode?'+new Date().getUTCMilliseconds();
    },

    toRegister(){
      this.$router.replace('/Register')
    },
    resetForgetPwdForm(){
      this.$refs.ForgetPwdForm.resetFields();
    },


  },

  data(){
    let checkDuplicate =(rule,value,callback)=>{
      this.$axios.get(this.$httpUrl+"/user/findByUsername?username="+this.ForgetPwdForm.username).then(res=>{
        if(res.data.code == 200){
          callback()
        }else{
          callback(new Error('账号不正确'));
        }
      })
    };
    return {

        ForgetPwDialogVisible:false,
        remember:false,
        imgUrl: this.$httpUrl+'/user/newCode',

        loginForm: {
            username: "",
            password: "",
            checkCode:"",
        },
        ForgetPwdForm:{
            username: "",
            email: "",
            password:'',
            password1:''
        },
        ForgetRules:{
            username: [
              { required: true,message:'请输入用户名', trigger: "blur"},
              {validator:checkDuplicate,trigger: 'blur'}
            ],
            email: [
              { required: true,message:'请输入邮箱',  trigger: "blur"}
            ],
            password: [
              { required: true,message:'请输入密码',  trigger: "blur"}
            ],
            password1: [
              { required: true,message:'请再次输入密码',  trigger: "blur"}
            ],
        },
        loginRules: {
            username: [
              { required: true,message:'请输入用户名', trigger: "blur", }
            ],
            password: [
              { required: true,message:'请输入密码',  trigger: "blur"}
            ],
        },

    };
  }


}


</script>


<style scoped>

#app,body,html {
  background-image: linear-gradient(to right,lightyellow,lavender);
  background-size:100% 100%;
  width:100%;
  height:100%;
  position:fixed;
  margin: 0;
  padding: 0;
  box-sizing: border-box;


}
#admin {
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
  border-radius: 53% 47% 75% 25% / 21% 39% 61% 79%  ;
}
#admin::before{
  content: '';
  position: absolute;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: white;
  top:80px;
  left: 100px;
}
#admin::after{
  content: '';
  position: absolute;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: white;
  top:110px;
  left: 130px;
}

#admin:hover{
  border-radius:38% 62% 60% 40% / 56% 43% 57% 44% ;
  transition: 0.8s;
}
.pos label{
  color: lightskyblue;
}
.login {
color: lightblue;
  opacity: 0.5;
  font-size: 60px;
  transform: rotateX(6deg);
  margin-bottom: 30px;
  margin-left: 20px;
  font-family: 华文中宋;
}
.pos {
  padding-top: 100px;
  margin-left: -100px;
  text-align: center;
}
.input{
  border-radius: 20%;
}
.demo-loginForm {
  font-size: 20px;

}
.drop{
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
  margin-left: -70px;
  margin-top: 650px;
}
.drop:hover{
  border-radius:84% 16% 68% 32% / 18% 61% 39% 82%  ;
  transition: 0.8s;
}
.drop::before{
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background-color: white;
}
.drop::after{
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: whitesmoke;
}
h4{
  color: lightblue;
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
  margin-left: 0;
  margin-top: 540px;
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
.forget{
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
  margin-left: -70px;
  margin-top: 650px;

}
.forget label{
  margin-left: -350px;
  color: lightcoral;
}
.forget .el-button--primary,.el-button--small{
  display: inline-block;
  width: 100px;
  height: 50px;
  border-radius: 40px;
  background-color: rgba(200,254,254,0.5) ;
  border: 2px solid white;
  color: #666666;
}
.forget .el-button--primary:hover,.el-button--small:hover{
  background-color: rgba(200,229,254,0.4) ;
  height:45px ;
  width: 110px;
}
</style>