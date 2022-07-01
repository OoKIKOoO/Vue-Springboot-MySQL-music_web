<template>
    <div>
        <div class="comment">
            <h2>评论</h2>
            <div class="comment-msg">
                <div class="comment-img">
                    <img :src="attachImageUrl(avator)">
                </div>
                <el-input class="comment-input" type="textarea" :rows="2" placeholder="请输入内容" v-model="textarea">
                </el-input>
            </div>
            <el-button type="primary" class="sub-btn" @click="postComment">评论</el-button>
        </div>
        <p>精彩评论：共{{commentList.length}}条评论</p>
        <ul class="popular" v-for="(item,index) in commentList" :key="index">
            <li>
                <div class="popular-img">
                    <img :src="attachImageUrl(item.userpic)">
                </div>
                <div class="popular-msg">
                    <ul>
                        <li class="name">{{item.username}}</li>
                        <li class="time">{{item.createTime}}</li>
                        <li class="content">{{item.content}}</li>
                    </ul>
                </div>
                <div class="up" ref="up" @click="postUp(item.id,item.up,index)">
                    <svg class="icon">
                        <use xlink:href="#icon-zan"></use>
                    </svg>
                    {{item.up}}
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
import {mixin} from '../mixins';
import {mapGetters} from 'vuex';
import {setComment,setLike,getAllComment,getUserOfId,updateComment} from '../api/index';

export default {
    name: 'comment',
    mixins: [mixin],
    props: [
        'playId',       //歌曲或歌单id
        'type'          //0歌曲、1歌单
    ],
    computed:{
        ...mapGetters([
            'id',               //歌曲或歌单id
            'loginIn',          //用户是否已登录
            'userId',           //当前登录用户id
            'avator',           //当前登录用户头像
            'username',         //当前用户名
            'avator'            //当前用户头像
        ])
    },
    data(){
        return{
            textarea: '',        //存放输入的评论内容
            commentList: [],    //存放评论列表
            userPic: [],        //用户的头像
            userName: [],       //用户的昵称
        }
    },
    mounted(){
        this.getComment();
        
    },
    methods: {
        //提交评论
        postComment(){
            if(this.loginIn){
                let params = new URLSearchParams();
                if(this.type == 0){
                    params.append('songId',this.playId);
                }else{
                    params.append('songListId',this.playId);
                }
                params.append('userId',this.userId);
                params.append('type',this.type);
                params.append('content',this.textarea);
                params.append('username',this.username);
                params.append('userpic',this.avator);
                setComment(params)
                    .then(res => {
                        if(res.code == 1){
                            this.notify('评论成功','success');
                            this.textarea='';
                            this.getComment();
                        }else{
                            this.notify('评论失败','error');
                        }
                    })
                    .catch(err =>{
                        this.notify('评论失败','error');
                    })
            }else{
                this.rank = null;
                this.notify('请先登录','warning');
            }
        },
        //评论列表
        getComment(){
                let params = new URLSearchParams();
                params.append('userId',this.userId);
                params.append('username',this.username);
                params.append('userpic',this.avator);
            updateComment(params).then(res1 => {
                  console.log("res!!!!!::"+res1);
                  
                }).catch(err =>{
                    this.notify('评论更新失败','error');
                }); 
            getAllComment(this.type,this.playId)
              .then(res => {
                      // this.commentList.push = res[res.length-1];
                      this.commentList = res;
                      // console.log(this.commentList);
                        
                  })
                  .catch(err =>{
                      this.notify('评论加载失败','error');
                  });
                  
            
            
        },
        
        //给某一个评论点赞
        postUp(id,up,index){
            if(this.loginIn){
                let params = new URLSearchParams();
                params.append('id',id);
                params.append('up',up+1);
                setLike(params)
                    .then(res => {
                        if(res.code == 1){
                            this.$refs.up[index].children[0].style.color = '#2796cd';
                            this.getComment();
                        }else{
                            this.notify('点赞失败','error');
                        }
                    })
                    .catch(err =>{
                        this.notify('点赞失败','error');
                    })
            }else{
                this.rank = null;
                this.notify('请先登录','warning');
            }
        }
    }
}
</script>
<style lang="scss" scoped>
@import '../assets/css/comment.scss';
.sub-btn{
  background-color: #c60021;
  border-color: #c60021;
}
</style>
