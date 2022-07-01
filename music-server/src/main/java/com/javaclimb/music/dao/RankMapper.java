package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Rank;
import org.springframework.stereotype.Repository;

/**
 * 评价Dao
 */
@Repository
public interface RankMapper {
    /**
     *增加
     */
    public int insert(Rank rank);

    /**
     * 查总分
     */
    public int selectScoreSum(Integer songListId);

    /**
     * 查总评分人数
     */
    public int selectRankNum(Integer songListId);

    /**
     * 根据用户id、歌单id查询评分
     */
    public Integer selectUserRank(Integer consumerId,Integer songListId);
}
















