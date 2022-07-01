<template>
    <div class="song">
      <div class="song-container">
        <el-image class="song-pic" :src="picUrl" />
        <ul class="song-info">
          <li>歌手：{{ artist }}</li>
          <li>歌曲：{{ title }}</li>
        </ul>
      </div>
      <div class="song-lyric">
        <!-- 有歌词 -->
        <ul :style="{top:this.lrcTop}" class="has-lyric" v-if="lyr.length" key="index">
            <li v-for="(item,index) in lyr" :key="index">
                {{item[1]}}
            </li>
        </ul>
        <!-- 没有歌词 -->
        <div v-else class="no-lyric" key="no-lyric">
            <span>暂无歌词</span>
        </div>
        </div>
        <div class="comment">
          <comment :playId="id" :type="0"></comment>
        </div>
    </div>
</template>
<script>
import {mixin} from '../mixins';
import {mapGetters} from 'vuex';
import Comment from "@/components/Comment.vue";

export default {
    name: 'lyric',
    mixins: [mixin],
    components:{
      Comment,
    },
    data(){
        return {
            lyr: [],         //当前歌曲的歌词
            lrcTop:'80px'   //歌词滑动
        }
    },
    computed: {
        ...mapGetters([
            'curTime',      //当前歌曲播放到的位置
            'id',           //当前播放的歌曲id
            'lyric',        //歌词
            'listIndex',    //当前歌曲在歌单中的位置
            'listOfSongs',  //当前歌单列表
            'artist',        //歌手
            'title',         //歌名
            'picUrl',        //歌曲图片
            'id',            //歌曲id
            'listPlayList'
        ])
    },
    created(){
        this.lyr = this.lyric;
        console.log(this.lyr);
    },
    watch:{
        id:function(){
            this.lyr = this.parseLyric(this.listPlayList[this.listIndex].lyric)
        },
        curTime: function(){
            if(this.lyr.length>0){
                for(let i=0;i<this.lyr.length;i++){
                    if(this.curTime>=this.lyr[i][0]){
                        for(let j=0;j<this.lyr.length;j++){
                            document.querySelectorAll('.has-lyric li')[j].style.color = '#000';
                            document.querySelectorAll('.has-lyric li')[j].style.fontSize = '15px';
                        }
                        if(i>=0){
                            document.querySelectorAll('.has-lyric li')[i].style.color = '#c50021';
                            document.querySelectorAll('.has-lyric li')[i].style.fontSize = '25px';
                            this.lrcTop = -i * 40 + 50 + "px";

                        }
                    }
                }
            }
        }
    }
}
</script>
<style lang="scss" scoped>
@import '../assets/css/lyric.scss';
.song-container {
  position: absolute;
  float: left;
  top: 120px;
  left: 150px;
  display: flex;
  flex-direction: column;

  .song-pic {
    height: 300px;
    width: 300px;
    border: 4px solid white;
    border-radius: 12px;
  }

  .song-info {
    width: 300px;
    li {
      width: 100%;
      line-height: 40px;
      font-size: 18px;
      // padding-left: 10%;
    }
  }
}
.song-lyric {
    position: relative;
    height: 300px;
    width: 1000px;
    padding: 30px 0;
    overflow: hidden;
    // text-align: center;
    .has-lyric {
      position: absolute;
      transition: all 1s;
      li {
        width: 100%;
        height: 40px;
        text-align: center;
        font-size: 14px;
        line-height: 40px;
      }
    }
    .no-lyric {
      position: absolute;
      margin: 100px 0;

      span {
        font-size: 18px;
        text-align: center;
      }
    }
  }
</style>