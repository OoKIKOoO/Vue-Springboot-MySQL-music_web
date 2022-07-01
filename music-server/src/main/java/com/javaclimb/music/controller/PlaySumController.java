package com.javaclimb.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.ListSong;
import com.javaclimb.music.domain.PlaySum;
import com.javaclimb.music.service.PlaySumService;
import com.javaclimb.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 歌单播放量controller
 */
@RestController
@RequestMapping("/playSum")
public class PlaySumController {
    @Autowired
    private PlaySumService PlaySumService;

    /**
     * 添加数据
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addPlaySum(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        //获取前端传来的参数
        String userId = request.getParameter("userId").trim();  //用户id
        String songListId = request.getParameter("songListId").trim(); //歌单id
        String playCount = request.getParameter("playCount").trim();; //播放量
        PlaySum playSum = new PlaySum();
        playSum.setUserId(Integer.parseInt(userId));
        playSum.setSongListId(Integer.parseInt(songListId));
        playSum.setPlayCount(Integer.parseInt(playCount));
        boolean flag = PlaySumService.insert(playSum);
        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "播放量添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "播放量添加失败");
        return jsonObject;

    }


    /**
     * 根据用户id 和歌单id修改count
     */
    @RequestMapping(value = "/playCountadd", method = RequestMethod.GET)
    public Object playCountadd(HttpServletRequest request) {
        String userId = request.getParameter("userId").trim();  //用户id
        String songListId = request.getParameter("songListId").trim(); //歌单id
        boolean flag = PlaySumService.updateSum(Integer.parseInt(userId), Integer.parseInt(songListId));
        if (flag) {
            return true;
        }
        return false;
    }

    /**
     * 根据用户id和歌曲id查询整个对象
     */
    @RequestMapping(value = "/isadd", method = RequestMethod.GET)
    public Object isadd(HttpServletRequest request) {
        String userId = request.getParameter("userId").trim();  //用户id
        String songListId = request.getParameter("songListId").trim(); //歌单id
        if(PlaySumService.isadd(Integer.parseInt(userId), Integer.parseInt(songListId))==null){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 根据用户id查询整个对象
     */
    @RequestMapping(value = "/selectByUserId", method = RequestMethod.GET)
    public Object selectByUserId(HttpServletRequest request) {
        String userId = request.getParameter("userId").trim();  //用户id
        if(PlaySumService.selectByUserId(Integer.parseInt(userId))==null){
            return false;
        }else {
            return PlaySumService.selectByUserId(Integer.parseInt(userId));
        }
    }



//    /**
//     * 查询所有
//     */
//    @RequestMapping(value = "/allPlaySum",method = RequestMethod.GET)
//    public Object allPlaySum(HttpServletRequest request){
//        return pla.allConsumer();
//    }

}
