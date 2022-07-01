<template>
  <div class="header">
    <!-- 折叠图片 -->
    <div class="collapse-btn" @click="collapseChange">
      <i class="el-icon-menu"></i>
    </div>
    <div class="logo">music后台管理</div>
    <div class="header-right">
      <div class="btn-fullscreen" @click="handleFullScreen">
        <el-tooltip :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
          <i class="el-icon-rank"></i>
        </el-tooltip>
      </div>
      <div class="user-avator">
        <img src="../assets/img/admin.jpg" />
      </div>
      <el-dropdown class="user-name" trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          {{userName}}
          <i class="el-icon-caret-bottom"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import bus from '../assets/js/bus'
export default {
  data(){
    return{
      collapse:false,
      fullscreen:false
    }
  },
  computed:{
    userName(){
      return sessionStorage.getItem('userName');
    }
  },
  methods:{
    collapseChange(){
      this.collapse = !this.collapse;
      bus.$emit('collapse',this.collapse);
    },
    // 全屏事件
    handleFullScreen(){ 
      if(this.fullscreen){
        if(document.exitFullscreen){
          document.exitFullscreen();
        }else if(document.webkitCancelFullScreen){  //safari, Chrom
          document.webkitCancelFullScreen();
        }else if(document.mozCancelFullScreen){ //firefox
          document.mozCancelFullScreen();
        }else if(document.msExitFullScreen){  //IE
          document/msExitFullScreen();
        }
      }else{
          let element = document.documentElement;
          if(element.requestFullscreen){
              element.requestFullscreen();
          }else if(element.webkitRequestFullScreen){      //safari 、Chrome
              element.webkitRequestFullScreen();
          }else if(element.mozRequestFullScreen){         //firefox
              element.mozRequestFullScreen();
          }else if (element.msRequestFullScreen){         //ie
              element.msRequestFullScreen();
          }
      }
      this.fullscreen = !this.fullscreen;
    },
    handleCommand(command){
      if(command === "logout"){
        sessionStorage.removeItem('userName');
        this.$router.push("/");
      }
    }
  }
}
</script>

<style>
.header{
  position: relative;
  width: 100%;
  height: 70px;
  background-color: #253041;
  box-sizing: border-box;
  font-size: 22px;
  color: #fff;
}
.collapse-btn{
  float: left;
  padding: 0 21px;
  cursor: pointer;
  line-height: 70px;
}
.header .logo{
  float: left;
  line-height: 70px;
}
.header-right{
  float: right;
  padding-right: 50px;
  display: flex;
  height: 70px;
  align-items: center;
}
.btn-fullscreen{
  transform: rotate(45deg);
  margin-right: 5px;
  font-size: 24px;
}
.user-avator{
  margin-left: 20px;
}
.user-avator img{
  display: block;
  widows: 40px;
  height: 40px;
  border-radius: 50%;
}
.user-name{
  margin-left: 10px;
}
.el-dropdown-link{
  color: #fff;
  cursor: pointer;
}
</style>