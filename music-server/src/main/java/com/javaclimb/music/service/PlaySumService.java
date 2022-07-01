package com.javaclimb.music.service;

import com.javaclimb.music.domain.PlaySum;

import java.util.List;

/**
 * 歌单里面的歌曲service接口
 */
public interface PlaySumService {
    /**
     *增加
     */
    public boolean insert(PlaySum playSum);

    /**
     *修改
     */
    public boolean update(PlaySum playSum);

    /**
     * 根据用户id 歌单id修改
     */
    public boolean updateSum(Integer userId,Integer songListId);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据歌曲id和歌单id查询
     */
    public PlaySum isadd(Integer userId,Integer songListId);

    /**
     * 根据用户id查询整个对象
     */
    public List<PlaySum> selectByUserId(Integer userId);

    /**
     * 查询所有
     */
    public List<PlaySum> allPlaySum();

}
