package com.Jessie.OnlineAlbum.dao;

import com.Jessie.OnlineAlbum.entity.Share;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareDAO
{
    @Select("select * from share where shareCode=#{shareCode}")
    Share getShare(String shareCode);

    @Select("select * from share where shareUser=#{shareUser}")
    List<Share> getUserShare(String shareUser);

    @Insert("insert into share (shareType,shareUser,shareCode,shareData) values(#{shareType},#{shareUser},#{shareCode},#{shareData})")
    void newShare(Share share);

    @Delete("delete from share where shareCode=#{shareCode}")
    void deleteShare(String shareCode);

    @Delete("delete from share where shareData=#{shareData}")
    void deleteShareByData(int shareData);

    @Select("select * from share where shareData=#{shareData}")
    Share checkShareAvailable(int shareData);

}
