package com.javaclimb.music.dao;

import com.javaclimb.music.domain.PlaySum;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌单里面的歌曲Dao
 */
@Repository
public interface PlaySumMapper {
    /**
     *增加
     */
    public int insert(PlaySum playSum);

    /**
     *修改
     */
    public int update(PlaySum playSum);

    /**
     * 根据用户id 歌单id修改
     */
    public int updateSum(Integer userId,Integer songListId);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据用户id和歌单id查询
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
















