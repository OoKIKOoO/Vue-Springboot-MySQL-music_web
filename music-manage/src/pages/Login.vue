<template>
  <div class="login-wrap">
    <div class="ms-title">music 后台管理登录</div>
    <div class="ms-login">
      <!-- rules:指定检验规则 -->
      <el-form ref="ruleForm" :model="ruleForm" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="ruleForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="ruleForm.password" placeholder="密码" type="password"></el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm">登录</el-button>
        </div>
      </el-form>
      
    </div>
  </div>
</template>

<script>
import {mixin} from '../mixins/index'
import {getLoginStatus} from '../api/index';
export default {
  mixins:[mixin],
  data() {
    return {
      ruleForm:{
        username: "admin",
        password: "admin"
      },
      rules: {
        username: [
          {required:true, message:"请输入用户名", trigger:"blur"}
        ],
        password: [
          {required:true, message:"请输入密码", trigger:"blur"}
        ]
      }
    }
  },
  methods:{
    submitForm(){
      let params = new URLSearchParams();
      params.append("name",this.ruleForm.username);
      params.append("password",this.ruleForm.password);
      getLoginStatus(params)
      .then((res) =>{
        if(res.code === 1){
          sessionStorage.setItem('userName',this.ruleForm.username)
          this.notify("登录成功","success");
          this.$router.push("/Info")
        }else{
          this.notify("登陆失败","error");
        }
      })
    }
  }
}
</script>

<style scoped>
/* #FFCF55 */
.login-wrap {
  position: relative;
  background: #F3F5F5;
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
  width: 100%;
  height: 100%;
}
.ms-title {
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -230px;
  text-align: center;
  font-size: 30px;
  font-weight: 600;
  color: #6E5BF0;
}
.ms-login {
  position: absolute;
  width: 300px;
  height: 160px;
  left: 50%;
  top: 40%;
  margin: 0 0 0 -190px;
  box-shadow: #fff 1px 1px 20px 1px ;
  border-radius: 5px;
  background: #fff;
  padding: 40px;
}
.login-btn {
  text-align: center;
}
.login-btn button {
  width: 100%;
  height: 36px;
}
</style>
