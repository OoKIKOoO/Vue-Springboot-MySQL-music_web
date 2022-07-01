package com.javaclimb.music.domain;

import java.io.Serializable;

/**
 * 歌单里面的歌曲
 */
public class PlaySum implements Serializable {

    private Integer id;     //主键

    private Integer userId; //用户id

    private Integer songListId; //歌单id

    private Integer playCount; //播放次数


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSongListId() {
        return songListId;
    }

    public void setSongListId(Integer songListId) {
        this.songListId = songListId;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }
}
