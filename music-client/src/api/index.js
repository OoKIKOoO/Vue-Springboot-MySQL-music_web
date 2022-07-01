import Axios from "axios";
import {get,post} from "./http";

//============歌手相关================
//查询歌手
export const getAllSinger =() => get(`singer/allSinger`);
//根据性别查询歌手
export const getSingerOfSex = (sex) => get(`singer/singerOfSex?sex=${sex}`);

//============歌曲相关================
//根据歌手id查询歌曲
export const songOfSingerId =(id) => get(`song/singer/detail?singerId=${id}`);
//根据歌曲id查询歌曲对象
export const songOfSongId =(id) => get(`song/detail?songId=${id}`);
//根据歌曲名字模糊查询歌曲
export const likeSongOfName =(keywords) => get(`song/likeSongOfName?songName=${keywords}`);

//============歌单相关================
//查询歌单
export const getAllSongList =() => get(`songList/allSongList`);
//返回标题包含文字的歌单列表
export const getSongListOfLikeTitle = (keywords) => get(`songList/likeTitle?title=${keywords}`);
//根据风格模糊查询歌单列表
export const getSongListOfLikeStyle = (style) => get(`songList/likeStyle?style=${style}`);
// 根据歌单id查询歌单
export const selectBySongListId = (userId) => get(`songList/selectBySongListId?id=${userId}`);
// 筛选已推荐歌单
export const selectOther = (style1,style2,style3) => get(`songList/selectOther?style1=${style1}&style2=${style2}&style3=${style3}`);



//============歌单的歌曲相关============
//根据歌单id查询歌曲列表
export const listSongDetail = (songListId) => get(`listSong/detail?songListId=${songListId}`);

//============用户相关================
//查询用户
export const getAllConsumer =() => get(`consumer/allConsumer`);
//注册
export const SignUp =(params) => post(`/consumer/add`,params);
//登录
export const loginIn =(params) => post(`/consumer/login`,params);
//根据用户id查询该用户的详细信息
export const getUserOfId =(id) => get(`/consumer/selectByPrimaryKey?id=${id}`);
//更新用户信息
export const updateUserMsg =(params) => post(`/consumer/update`,params);

//下载音乐
export const download = (url)  => Axios({
    method: 'get',
    url: url,
    responseType: 'blob'
});

//===========评价======================
//提交评分
export const setRank =(params) => post(`/rank/add`,params);
//获取指定歌单的平均分
export const getRankOfSongListId = (songListId) => get(`/rank?songListId=${songListId}`);
// 搜索评分

export const getUserRank = (consumerId,songListId) => get(`/isrank?consumerId=${consumerId}&songListId=${songListId}`);


//===========评论======================
//提交评论
export const setComment =(params) => post(`/comment/add`,params);
//点赞
export const setLike =(params) => post(`/comment/like`,params);
//返回当前歌单或歌曲的评论列表
export const getAllComment = (type,id) => {
    if(type == 0){              //歌曲
        return get(`/comment/commentOfSongId?songId=${id}`);
    }else{                      //歌单
        return get(`/comment/commentOfSongListId?songListId=${id}`);
    }
}
// 更新评论
export const updateComment = (params) => post(`/comment/updateComment`,params);



//===============收藏===================
//新增收藏
export const setCollect =(params) => post(`/collect/add`,params);
// 取消收藏
export const delCollect = (userId,songId) => get(`/collect/delete?userId=${userId}&songId=${songId}`);
//指定用户的收藏列表
export const getCollectOfUserId = (userId) => get(`/collect/collectOfUserId?userId=${userId}`);


// ===========================歌单播放量相关==========================
//新增
export const addPlayCount = (params) => post(`/playSum/add`,params);
// 新增用户当前歌单的播放量
export const setPlayCount = (userId,songListId) => get(`/playSum/playCountadd?userId=${userId}&songListId=${songListId}`);
// 查询用户歌单播放量前三数据
export const selectByUserId = (userId) => get(`/playSum/selectByUserId?userId=${userId}`);
//查询是否已有数据
export const isadd = (userId,songListId) => get(`/playSum/isadd?userId=${userId}&songListId=${songListId}`);
