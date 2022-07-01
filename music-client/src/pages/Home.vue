<template>
  <div class="home">
    <swiper />
    <div
      class="section"
      v-for="(item,index) in songsList"
      :key="index"
    >
      <div class="section-title">{{item.name}}</div>
      <content-list :contentList="item.list"></content-list>
    </div>
  </div>
</template>

<script>
import Swiper from "../components/Swiper";
import contentList from '../components/ContentList';
import { mapGetters } from 'vuex';
import { getAllSinger, getAllSongList, selectByUserId, selectBySongListId, getSongListOfLikeStyle,selectOther } from '../api/index';
export default {
  name: 'home',
  components: {
    Swiper,
    contentList,

  },
  computed: {
    ...mapGetters([
      'userId',
      'No3',
      'stySongList',
      'otherList'
    ])
  },
  data() {
    return {
      songsList: [
        { name: "推荐歌单", list: [] },
        { name: "歌手", list: [] },
      ],
      No3style: [],
      songlistbox:[],
      ooo:[]
    }
  },
  created() {
    this.getSongList();
    this.getSinger();
  },
  methods: {
    getSongList() {                      //获取推荐前十条歌单
      if (this.userId) {
        selectByUserId(this.userId).then((res) => {   //查询用户歌单播放量前三数据
          for (let item of res) {
            selectBySongListId(item.songListId).then((res1) => {    //根据前三歌单id查风格
              for (let itemStyle of res1) {
                this.No3style.push(itemStyle.style);
                this.$store.commit('setNo3',this.No3style);
              }
            })
          }
        }).catch((err) => {
          console.log(err);
        })
        let itemStyle = [...new Set(this.No3)]
        var arrr = [];
        let i =0,j=0;
        for(let style of itemStyle){            //遍历风格喜好
          getSongListOfLikeStyle(style).then((res) => {             //根据风格喜好推荐歌单
            arrr.push(res)
            this.$store.commit('setStySongList',arrr);
            if(j>=1){
              i++;
            this.songlistbox = this.stySongList[i-1].concat(this.stySongList[i]);
            this.$store.commit('setStySongList',this.songlistbox);
            }
            j++;
          }).catch((err) => {
            console.log(err);
          })
        }
        j=0;
        let arrr2=[]
        this.songsList[0].list = this.stySongList;
        // console.log("mmmm"+this.songsList[0].list);
        let otherLength = 10-this.songsList[0].list.length;
        getAllSongList().then((res) => {
          for(let i in res){
            if(res[i].style!=this.No3[0]&&res[i].style!=this.No3[1]&&res[i].style!=this.No3[2]){
              if(otherLength){
              arrr2.push(res[i]);
              this.$store.commit('setOtherList',arrr2);
              console.log("otherList"+this.otherList);
              otherLength--;
              }
            }
          }
          console.log(this.songsList[0].list);
          this.ooo = this.songsList[0].list.concat(this.otherList);
          this.songsList[0].list = this.ooo
        }).catch((err) => {
          console.log(err);
        })
        // selectOther(this.No3[0],this.No3[1],this.No3[2]).then((res) => {
        //   console.log(res);
        //   this.ooo = res.slice(0,10-this.stySongList.length)
        //   console.log("噼噼啪啪铺"+this.ooo);
        console.log(this.No3);
        // })

      } else {
        getAllSongList().then((res) => {
          this.songsList[0].list = res.slice(0, 10);
        }).catch((err) => {
          console.log(err);
        })
      }

    },
    getSinger() {                      //获取前十名歌手
      getAllSinger().then((res) => {
        this.songsList[1].list = res.slice(0, 10);
      }).catch((err) => {
        console.log(err);
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import "../assets/css/home.scss";
</style>
