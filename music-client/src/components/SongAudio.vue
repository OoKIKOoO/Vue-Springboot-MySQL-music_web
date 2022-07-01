<template>
    <div class="song-audio">
        <audio ref="player" 
            :src="url" 
            controls = "controls" 
            preload = "true"
            @canplay="startPlay"
            @ended="ended"
            @timeupdate="timeupdate"
        ></audio>
    </div>
</template>
<script>
import {mapGetters} from 'vuex';
export default {
    name: 'song-audio',
    data(){
      return{
        aaa:''
      }
    },
    computed: {
        ...mapGetters([
            'id',               //歌曲id
            'url',              //歌曲地址
            'isPlay',           //播放状态
            'listOfSongs',     //当前歌曲列表
            'curTime',         //当前音乐的播放位置
            'changeTime',      //指定播放时刻
            'autoNext',        //用于自动触发播放下一首
            'volume',           //音量
            'playModel',
            'playModelSVG'
        ])
    },
    mounted(){
    //   setTimeout(function(){
    //     let player = this.$refs.player;
    //     player.pause();
    //     this.$store.commit('setIsPlay',false);
    //   },1000)
    //   // let player = this.$refs.player;
    //   // this.$store.commit('setDuration',player.duration);
    //   // //开始播放
    //   // player.pause();
        console.log("mounted==="+this.isPlay);
        this.$store.commit('setPlayModel',0);

        this.$store.commit('setPlayModelSVG','#953');

      // this.aaa = this.url

      // this.$store.commit('setXurl',this.url);
      // this.$store.commit('setIsPlay',true);
      // // this.$store.commit('setUrl','');
      // console.log(this.aaa);
      // console.log(this.Xurl);
      // console.log(this.isPlay);
      // console.log(this.url);
    },
    watch:{
        //监听播放还是暂停
        isPlay(){
            this.togglePlay();
        },
        //跳转到指定播放时刻
        changeTime(){
            this.$refs.player.currentTime = this.changeTime;
        },
        //改变音量
        volume(val){
            this.$refs.player.volume = val;
        }
    },
    methods:{
        //获取链接后准备播放play
        startPlay(){
            let player = this.$refs.player;
            this.$store.commit('setDuration',player.duration);
            //开始播放
            console.log(this.isPlay);
            player.play();
            this.$store.commit('setIsPlay',true);
            console.log(this.isPlay);

        },
        //播放完成之后触发
        ended(){
          this.$store.commit('setIsPlay',false);
          this.$store.commit('setCurTime',0);
          this.$store.commit('setAutoNext',!this.autoNext);
          console.log("isplay: "+this.isPlay);
        },
        //开始、暂停
        togglePlay() {
            let player = this.$refs.player;
            if(this.isPlay){
                player.play();
            }else{
                player.pause();
            }
        },
        //音乐播放时记录音乐的播放位置
        timeupdate(){
            this.$store.commit('setCurTime',this.$refs.player.currentTime);
        }
    }
}
</script>

<style>
    .song-audio {
        display: none;
    }
</style>