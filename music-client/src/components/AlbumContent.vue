<template>
    <div class="content">
      <!-- <h1 class="title">
          <slot name="title">aa</slot> -->
          <hr/>
      <!-- </h1> -->
      <ul>
          <li>
              <div class="song-item">
                  <span class="item-index"></span>
                  <span class="item-title">歌曲名</span>
                  <span class="item-name">歌手</span>
                  <span class="item-intro">专辑</span>
              </div>
          </li>
          <li v-for="(item,index) in songList" :key="index">
              <div class="song-item" @click="toplay(item.id,item.url,item.pic,index,item.name,item.lyric)">
                  <span class="item-index">
                      {{index + 1}}
                  </span>
                  <span class="item-title">{{replaceFName(item.name)}}</span>
                  <span class="item-name">{{replaceLName(item.name)}}</span>
                  <span class="item-intro">{{item.introduction}}</span>
              </div>
          </li>
      </ul>
    </div>
</template>
<script>
import {mapGetters} from "vuex";
import {mixin} from "../mixins";
import {getCollectOfUserId,isadd,setPlayCount,addPlayCount} from '../api/index';

export default {
    name: 'album-content',
    mixins: [mixin],
    props:[
        'songList'
    ],
    computed:{
        ...mapGetters([
            'userId',           //当前登录用户id
            'playSongListId'
        ])
    },
    methods:{
      toplay: function(id,url,pic,index,name,lyric){
        if(this.$route.params.id){
        this.$store.commit('setPlaySongListId',this.$route.params.id);
        console.log("当前歌单id：哈哈哈哈"+this.playSongListId);
        let songListId = this.$route.params.id;
        if(this.userId){
        isadd(this.userId,songListId).then(res => {
          console.log("res=="+res);
          if(res){
            setPlayCount(this.userId,songListId).then(res1 => {
              if(res1){
                console.log("count成功加一");
              }else{
                console.log("count加一失败");
              }
            })
          }else{
            console.log("当前歌单id："+songListId);
            var params = new URLSearchParams();
            params.append('userId',this.userId);
            params.append('songListId',songListId);
            params.append('playCount',1);
            addPlayCount(params).then(res2 => {
              if(res2.code==1){
                console.log("播放量添加成功");
              }else{
                console.log("播放量添加失败");
              }
            })
          }
        })}}
            this.$store.commit('setId',id);
            this.$store.commit('setUrl',this.$store.state.configure.HOST+url);
            this.$store.commit('setPicUrl',this.$store.state.configure.HOST+pic);
            this.$store.commit('setListIndex',index);
            console.log(index);
            this.$store.commit('setTitle',this.replaceFName(name));
            this.$store.commit('setArtist',this.replaceLName(name));
            this.$store.commit('setLyric',this.parseLyric(lyric));
            this.$store.commit('setIsActive',false);
            this.$store.commit('setIsPlay',true);
            this.$store.commit('setListPlayList',this.songList);
            console.log(this.$store.state.song.listPlayList);
            if(this.loginIn){
                getCollectOfUserId(this.userId)
                    .then(res =>{
                        for(let item of res){
                            if(item.songId == id){
                                this.$store.commit('setIsActive',true);
                                break;
                            }
                        }
                    })
            }
        },
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/album-content.scss';
</style>