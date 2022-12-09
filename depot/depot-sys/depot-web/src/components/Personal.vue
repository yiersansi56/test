<template>

<div>
<div class="left" style="float: left;width: 30%;height: 840px;float: left;margin-top: 20px;margin-left: 70px;border-radius: 30px; position: relative">
  <div style="width: 90%;height: 30%;background: transparent;margin: 20px auto;border-bottom: 1px solid #333333;">
    <img :src="user.imgUrl"
         style="border-radius:50%;height: 150px;width:150px;border:1px solid #333333;display: flex;position: absolute;margin-top: 40px;margin-left: 150px">
  </div>
  <div style="text-indent: 0px;width: 82%;margin-top: 120px;margin-left: 30px"> <DateUtils></DateUtils> </div>
  <div class="background" ></div>
</div>
<div>
  <div style="width: 60%;height:840px;overflow: hidden;
       margin: 20px 40px;border-radius: 30px;
       float: left;background: rgba(150, 245, 230, 0.5);position: relative">
    <!-- 个人信息-->
    <div style="text-align: center;height: 100vh;padding: 0px;margin-top: 60px;margin-left:50px;width: 90%;position: absolute">
      <label class="l1">个人资料</label>
      <div  class="person">
      <el-descriptions :column="1" size="50" border  >
            <el-descriptions-item >
                <template slot="label">
                    <i class="el-icon-s-custom"></i>
                    账号
                </template>
                {{user.username}}
            </el-descriptions-item >
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    电话
                </template>
                {{user.phone}}
            </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              姓名
            </template>
            {{user.name}}
          </el-descriptions-item>


            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-location-outline"></i>
                    性别
                </template>
                <el-tag
                        :type="user.sex === '1' ? 'primary' : 'danger'"
                        disable-transitions><i :class="user.sex==1?'el-icon-male':'el-icon-female'"></i>{{user.sex==1?"男":"女"}}</el-tag>
            </el-descriptions-item>
      </el-descriptions>
      </div>

      <el-divider direction="vertical" class="scale"></el-divider>

      <div class="person1">
      <el-descriptions  :column="1" size="50" border >
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-tickets"></i>
                    角色
                </template>
                <el-tag
                        type="success"
                        disable-transitions>{{user.roleId==0?"超级管理员":(user.roleId==1?"管理员":"用户")}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-s-data"></i>
                  年龄
                </template>
                {{user.age}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-house"></i>
                地址
              </template>
              {{user.address}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-receiving"></i>
                邮箱
              </template>
              {{user.email}}
            </el-descriptions-item>
        </el-descriptions>
      </div>
      <div class="sure">
      <el-button type="success" @click="modifyInfo"
                 class="button1"
                 style="width: 140px;height: 40px;
                  font-size: 14px;margin-right: 400px;
                  border-radius: 20px;background-color: aliceblue;color: #b9bebe">修改信息</el-button>
      <el-button type="success" @click="modifyBtn"
                 class="button2" style="width: 140px;height: 40px;
                 font-size: 14px ;border-radius: 20px;
                 background-color: aliceblue;
                 color: #b9bebe;">修改密码</el-button>
      </div>
    </div>
  </div>
  <!--    修改个人信息对话框-->
  <el-dialog
      title="修改个人信息"
      :visible.sync="ModDialogVisible"
      width="30%"
      center>
    <el-form ref="ModInfoForm"
             :model="ModInfoForm"
             label-width="80px">
      <el-form-item  prop="upload">
        <el-upload
            style="width: 80px;height: 80px;border-radius: 50%; margin:10px 140px;;box-sizing: border-box;background-size:cover"
            class="avatar-uploader"
            action="http://localhost:8082/user/toUploadAvatar"
            :show-file-list="false"
            accept=".jpg,.png,.gif"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
          <img v-if="ModInfoForm.imgUrl" :src="ModInfoForm.imgUrl" class="avatar" title="更换头像" style="width: 80px;height: 80px">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="账号" prop="username">
        <el-col :span="20">
          <el-input v-model="ModInfoForm.username"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-col :span="20">
          <el-input v-model="ModInfoForm.name"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-col :span="20">
          <el-input v-model="ModInfoForm.age"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="ModInfoForm.sex" >
          <el-radio  label="1">男</el-radio>
          <el-radio label="0">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-col :span="20">
          <el-input v-model="ModInfoForm.phone"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-col :span="20">
          <el-input v-model="ModInfoForm.address"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-col :span="20">
          <el-input v-model="ModInfoForm.email"></el-input>
        </el-col>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
          <el-button class="button1" @click="cancel">取 消</el-button>
          <el-button class="button1" type="primary" @click="doSave">确 定</el-button>
          </span>
  </el-dialog>
  <!--    修改密码对话框-->
  <el-dialog
      title="修改密码"
      :visible.sync="ModPwDialogVisible"
      width="30%"
      center>
    <el-form ref="ModPwdForm"
             :model="ModPwdForm"
             :rules="ModRules"
             label-width="80px">
      <el-form-item label="旧密码" prop="oldPwd">
        <el-col :span="20">
          <el-input v-model="ModPwdForm.oldPwd"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="新密码" prop="newPwd">
        <el-col :span="20">
          <el-input v-model="ModPwdForm.newPwd"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="确认密码" prop="newPwd1">
        <el-col :span="20">
          <el-input v-model="ModPwdForm.newPwd1"></el-input>
        </el-col>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="modifyPwd">确 定</el-button>
          </span>
  </el-dialog>
</div>

</div>

</template>


<script>
    import DateUtils from "./DateUtils";
    export default {
        name: "Personal",
        components: {DateUtils},

        mounted() {
          this.ModInfoForm.imgUrl = this.user.imgUrl
          this.imageUrl = this.user.imgUrl
        },

      data() {
        let checkDuplicate =(rule,value,callback)=>{
          this.$axios.get(this.$httpUrl+"/user/findPwd?username="+this.user.username+"&pwd="+this.ModPwdForm.oldPwd).then(res=>{
            if(res.data.code == 200){
              callback()
            }else{

              callback(new Error('旧密码不正确'));
            }
          })
        };

            return {


              imageUrl:'',
              ModPwDialogVisible:false,
              ModDialogVisible:false,
              user:JSON.parse(sessionStorage.getItem('userData')),
              ModRules:{
                oldPwd: [
                  { required: true,message:'请输入旧密码', trigger: "blur" },
                  {min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur'},
                  {validator:checkDuplicate,trigger: 'blur'}
                ],
                newPwd: [
                  { required: true,message:'请输入新密码',  trigger: "blur"}
                ],
                newPwd1: [
                  { required: true,message:'请再输入新密码',  trigger: "blur"}
                ],
              },
              ModInfoForm:{
                id:'',
                username:'',
                name:'',
                age:'',
                phone:'',
                sex:'1',
                address:'',
                email:'',
                imgUrl:'',
                roleId:''
              },
              ModPwdForm:{
                oldPwd:'',
                newPwd:'',
                newPwd1:''
              },
            }
        },




        methods:{
          //点击修改密码按钮
          modifyBtn(){
            this.ModPwDialogVisible=true
            this.resetModPwdForm()

          },
          //点击取消修改按钮
          cancel(){
            this.ModDialogVisible = false
            this.ModPwDialogVisible = false
            this.$nextTick(()=>{
              this.resetModInfoForm()
              this.resetModPwdForm()
            })

          },
          //修改密码
          modifyPwd(){
            this.$refs.ModPwdForm.validate(valid => {
              if (valid) {
                if (this.ModPwdForm.newPwd != this.ModPwdForm.newPwd1) {
                  this.$message({
                    message: '两次输入的密码不相同',
                    type: 'warning'
                  })
                } else {
                  this.$axios({
                    method: "POST",
                    url: this.$httpUrl+'/user/modifyPwd',
                    data: {
                      id:this.user.id,
                      password:this.ModPwdForm.newPwd+''
                    }
                  }).then(res=>res.data).then(res => {
                    if (res.code == 200) {
                      //注册成功
                      this.ModPwDialogVisible=false
                      this.$nextTick(()=>{
                        this.resetModInfoForm()
                      })
                      this.$message({
                        message: '恭喜你，修改成功',
                        type: 'success'
                      })

                    } else {
                      this.$message({
                        message: '修改失败',
                        type: 'error'
                      })
                    }

                  })
                }
              }
            })
          },
          //修改个人信息
          modifyInfo(){
            this.ModDialogVisible = true
            this.ModInfoForm = this.user
            this.ModInfoForm.imgUrl = this.user.imgUrl
            this.ModInfoForm.sex = this.user.sex+''
            
          },
          //保存个人信息
            doSave(){
              this.$axios({
                method:'POST',
                url:this.$httpUrl+'/user/modify',
                data: this.ModInfoForm
              }).then(res=>res.data).then(res=>{
                if(res.code == 200){

                  this.$message({
                    message: '操作成功',
                    type: 'success'
                  });

                  this.$emit('doFresh')
                  this.ModDialogVisible = false
                  sessionStorage.setItem("userData",JSON.stringify(res.data))


                }else{
                  this.$message({
                    message: '操作失败',
                    type: 'error'
                  });
                }
              })
            },

            //处理上传头像成功后
            handleAvatarSuccess(res, file) {
              this.ModInfoForm.imgUrl = URL.createObjectURL(file.raw);
              this.ModInfoForm.imgUrl = res.imgUrl
            },
            beforeAvatarUpload(file) {
              //在头像上传之前需要做的判断，如判断文件格式
              const isJPG = file.type === 'image/jpeg';
              const isLt2M = file.size / 1024 / 1024 < 2;
            },

          resetModInfoForm() {
            this.$refs.ModInfoForm.resetFields();
          },
          resetModPwdForm(){
            this.$refs.ModPwdForm.resetFields();
          }


        },

    }
</script>

<style scoped>

    .avatar-uploader {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .avatar-uploader:hover {
      border-color: #409EFF;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      line-height: 178px;
      text-align: center;
    }
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }
    .background{
      clear: both;
      background-image: url("../assets/imgs/person.jpg");
      background-size: 100%;
      width: 95%;
      height: 300px;
      position: absolute;
      bottom: 0px;
      border-radius: 10px;
      margin-left: 10px;
    }
    .left{
      background: rgba(242, 165, 230, 0.2);
      backdrop-filter: blur(8px);
    }
    .person {
      left:-4%;
      top:15%;
      display: inline-block;
      width: 10%;
      position: absolute;
    }
    .person1{
      left: 52%;
      top: 15%;
      display: inline-block;
      width: 40%;
      position: absolute;
    }
    .l1{
      position: absolute;
      top: -2%;
      left: 26%;
      font-size: 70px;
      display: inline-block;
      letter-spacing: 40px;
      opacity: 0.6;

    }
    .sure{
      position: absolute;
      bottom: 25%;
      left:0%;
      width:100%;
    }


    .scale{
      height: 650px;
      font-size: 100px;
      width: 10px;
      background: linear-gradient(to right, transparent,lightgreen , transparent);
      border: 0;
      padding-top: 1px;
      position: absolute;
      left: 48%;
      top:8%;
      display: inline-block;
    }

</style>