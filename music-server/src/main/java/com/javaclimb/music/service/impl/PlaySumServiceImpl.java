package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.ListSongMapper;
import com.javaclimb.music.dao.PlaySumMapper;
import com.javaclimb.music.domain.ListSong;
import com.javaclimb.music.domain.PlaySum;
import com.javaclimb.music.service.ListSongService;
import com.javaclimb.music.service.PlaySumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌单里面的歌曲service实现类
 */
@Service
public class PlaySumServiceImpl implements PlaySumService {
    @Autowired
    private PlaySumMapper playSumMapper;
    /**
     * 增加
     *
     * @param playSum
     */
    @Override
    public boolean insert(PlaySum playSum) {
        return playSumMapper.insert(playSum)>0;
    }

    /**
     * 修改
     *
     * @param playSum
     */
    @Override
    public boolean update(PlaySum playSum) {
        return playSumMapper.update(playSum)>0;
    }

    /**
     * 根据用户id 歌单id修改
     *
     * @param userId
     * @param songListId
     */
    @Override
    public boolean updateSum(Integer userId,Integer songListId) {
        return playSumMapper.updateSum(userId,songListId)>0;
    };

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return playSumMapper.delete(id)>0;
    }

    /**
     * 根据歌曲id和歌单id查询
     */
    @Override
    public PlaySum isadd(Integer userId,Integer songListId){
        return playSumMapper.isadd(userId,songListId);
    }

    /**
     * 根据用户id查询整个对象
     *
     * @param userId
     */
    @Override
    public List<PlaySum> selectByUserId(Integer userId) {
        return playSumMapper.selectByUserId(userId);
    }

    /**
     * 查询所有
     */
    @Override
    public List<PlaySum> allPlaySum() {
        return playSumMapper.allPlaySum();
    }

}
