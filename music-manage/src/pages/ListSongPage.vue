<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" size="mini" placeholder="请输入歌曲名" class="handle-input"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加歌曲</el-button>
      </div>
    </div>
    <el-table size="mini" border style="width:100%" height="800px" :data="this.tableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="name" label="歌手-歌名" align="center"></el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
      background
      layout="total,prev,pager,next"
      :current-page.sync="currentPage"
      :page-size="pageSize"
      :total="tableData.length"
      @current-change="handleCurrentChange">
      </el-pagination>
    </div>

    <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="400px" center>
      <!-- <el-form :model="registerForm" ref="registerForm" label-width="80px" action="" id="tf">
        <el-form-item prop="singerName" label="歌手名字:" size="mini">
          <el-input v-model="registerForm.singerName" placeholder="歌手名字" type="name"></el-input>
        </el-form-item>
        <el-form-item prop="songName" label="歌曲名字:" size="mini">
          <el-input v-model="registerForm.songName" placeholder="歌曲名字" type="name"></el-input>
        </el-form-item>
      </el-form> -->
      <el-button type="primary" size="mini" @click="addSelct()">批量添加</el-button>

      <el-table size="mini" border style="width:100%" height="800px" :data="songData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column prop="name" label="歌手-歌名" align="center"></el-table-column>
        <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="" @click="addSong(scope.row.id)">添加</el-button>
        </template>
      </el-table-column>
      </el-table>
        <div class="pagination">
        <el-pagination
        background
        layout="total,prev,pager,next"
        :current-page.sync="currentPage"
        :page-size="15"
        :total="tableSongData.length"
        @current-change="handleCurrentChange">
        </el-pagination>
        </div>
    </el-dialog>

    <el-dialog title="删除歌曲" :visible.sync="delVisible" width="400px" center>
      <div align="center">删除不可恢复，确认删除？</div>
      <span slot="footer">
        <el-button size="mini" @click="delVisible = false">取消</el-button>
        <el-button size="mini" @click="isDelAll">确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {mixin} from '../mixins/index';
import {listSongDetail,songOfSongId,songOfSongName,listSongAdd,delListSong,allSong} from '../api/index';



export default {
  mixins: [mixin],
  data(){
    return{
      centerDialogVisible: false,    //添加弹窗是否显示
      delVisible: false,    //删除弹窗是否显示
      registerForm:{    // 添加表单
        singerName: '',     //歌手名字
        songName: ''        //歌曲名字
      },
      tableData: [],
      tempData: [],
      tableSongData: [],
      select_word: '',
      pageSize: 15,    //分页每页大小
      currentPage: 1,   //当前页
      idx: -1,          //当前选择项
      multipleSelection: [],   //哪些项已经打勾
      songListId: '',          //歌单id,
      mulLength:0
    }
  },
  computed:{
    data(){
      // console.log(this.tableData);
      return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
    },
    songData(){
      return this.tableSongData.slice((this.currentPage - 1) * 15, this.currentPage * 15);
    },
  },
  created(){
    this.songListId = this.$route.query.id;
    this.getData();
  },
  watch:{
    //搜索框里面的内容发生变化的时候，搜索结果table列表的内容跟着它的内容发生变化
    select_word:function(){
      if(this.select_word == ''){
        this.tableData = this.tempData;
      }else{
        this.tableData = [];
        for(let item of this.tempData){
          if(item.name.includes(this.select_word)){
            this.tableData.push(item);
          }
        }
      }
    },
    centerDialogVisible:function(){
      if(this.centerDialogVisible == true){
        this.multipleSelection = [];
        this.tableSongData = [];
        allSong().then(res => {
          this.tableSongData = res.reverse();
        })
      }
    },
  },
  methods:{
    // 切换播放歌曲
    setSongUrl(url,name){
      this.$store.commit('setUrl',this.$store.state.HOST + url);
      if(this.toggleName != name && this.toggleName != false){
        this.$store.commit('setIsPlay',true);
        this.toggleName = name;
        this.toggle = true;
        return;
      }
      if(this.isPlay){
        this.$store.commit('setIsPlay',false);
        this.toggleName = name;
        this.toggle = false;

      }else{
        this.$store.commit('setIsPlay',true);
        this.toggleName = name;
        this.toggle = true;
      }
    },
    // 解析歌词
    parseLyric(text){
      let lines = text.split('\n');
      let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
      let result = []
      for(let item of lines){
        let value = item.replace(pattern,'');
        result.push(value)
      }
      return result;
    },
    // 是否批量删除
    isDelAll(){
      if(this.multipleSelection.length != 0){
        this.delAll()
      }else{
        this.deleteRow()
      }
    },
    //删除一条歌曲
    deleteRow(len){
      delListSong(this.idx,this.songListId)
      .then(res => {
        if(res){
          if(len==1){
            this.getData()
          }
          this.notify("删除成功","success");
        }else{
            this.notify("删除失败","error");
        }
      })
      .catch(err => {
        console.log(err);
      });
      this.delVisible = false;
    },
    // 获取当前页
    handleCurrentChange(val){
      this.currentPage = val;
    },
    // 查询所有歌手
    getData(){
      this.tempData = [];
      this.tableData = [];
      // console.log("归零之后:"+this.tableData);
      listSongDetail(this.songListId).then(res => {
        // console.log("获取到res:"+res);
        for(let item of res){
          // console.log("进入item循环："+item);
          this.getSong(item.songId);
        }
      })
    },
    //根据歌曲id查询歌曲对象，放到tempData和tableData里面
    getSong(id){
      songOfSongId(id)
      .then(res => {
          this.tempData.push(res);
          this.tableData.push(res);
          // console.log("push循环："+this.tableData);
      })
      .catch(err => {
          console.log(err);
      });
    },
    //添加歌曲前的准备，获取到歌曲id        
    getSongId(){
      let _this = this;
      var songOfName = _this.registerForm.singerName+"-"+_this.registerForm.songName;
      songOfSongName(songOfName).then(
          res => {
              _this.addSong(res[0].id)
          }
      )
    },
    // 添加歌曲
    addSong(songId,len){
      let _this = this;
      let params = new URLSearchParams();
      params.append('songId',songId);
      params.append('songListId',this.songListId);
      console.log("噼噼啪啪铺"+len);
      listSongAdd(params)
      .then(res => {
          if(res.code == 1){
            if(len==1){
            this.getData()
            }
            console.log("么么么么"+len);
              this.notify("添加成功","success");
          }else{
              this.notify("添加失败","error");
          }
      })
      .catch(err => {
          console.log(err);
      });
      _this.centerDialogVisible = false;
    },
  },
}
</script>

<style scope>
  .handle-box{
    margin-bottom: 20px;
  }
  .song-img{
    width: 100%;
    height: 90px;
    border-radius: 5px;
    margin-bottom: 5px;
    overflow: hidden;
  }
  .singerImg{
    width: 100%;
    height: 100%;
  }
  .handle-input{
    width: 300px;
    display: inline-block;
  }
  .pagination{
    display: flex;
    justify-content: center;
  }
  .crumbs {
  margin-bottom: 20px;
  }
  .play {
    position: absolute;
    z-index: 100;
    width: 80px;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    top: 18px;
    left: 15px;
  }

  .icon {
    width: 2em;
    height: 2em;
    color: white;
    fill: currentColor;
    overflow: hidden;
  }
</style>