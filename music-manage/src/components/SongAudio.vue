<template>
  <div class="song-audio">
    <audio id="player"
    :src="url"
    controls="controls"
    preload = "true"
    @canplay="startPlay"
    @ended="ended"
    ref="audio"
    >
      
    </audio>
    
  </div>
</template>

<script>
import { mapGetters} from 'vuex';
export default {
  name:'song-audio',
  computed:{
    ...mapGetters([
      'id',
      'url',
      'isPlay'
    ])
  },
  watch:{
    // 监听播放还是暂停
    isPlay:function(){
      this.togglePlay();
    }
  },
  methods: {
      // 获取链接后准备播放
      startPlay(){
        let player = document.querySelector('#player');
        // 开始播放
        player.play();
      },
      // 播放完成后触发
      ended(){
        this.isPlay = false;
      },
      // 开始、暂停
      togglePlay(){
        let player = document.querySelector('#player');
        if(this.isPlay){
          player.play();
        }else{
          player.pause();
        }
      }
  },
  // mounted(){
  //     let player = document.querySelector('#player');
    
  //   if(this.url == '/song/1651128642024myMusicTest1S.m4a'){
  //     player.play();
  //     player.pause();
  //     this.isPlay = false;
  //   }
  // }
}
</script>
<style>
    .song-audio {
        display: none;
    }
</style>