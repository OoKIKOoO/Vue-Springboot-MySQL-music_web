package com.javaclimb.music.service;

import com.javaclimb.music.domain.Rank;

/**
 * 评价service接口
 */
public interface RankService {
    /**
     *增加
     */
    public boolean insert(Rank rank);

    /**
     * 查总分
     */
    public int selectScoreSum(Integer songListId);

    /**
     * 查总评分人数
     */
    public int selectRankNum(Integer songListId);

    /**
     * 计算平均分
     */
    public int rankOfSongListId(Integer songListId);

    /**
     * 根据用户id、歌单id查询评分
     */
    public Integer selectUserRank(Integer consumerId,Integer songListId);
}
