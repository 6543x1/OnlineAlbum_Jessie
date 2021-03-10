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

    @Insert("insert into share (shareType,shareUser,shareCode,shareImageID,shareFid) values(#{shareType},#{shareUser},#{shareCode},#{ShareImageID},#{shareFid})")
    void newShare(Share share);

    @Delete("delete from share where shareCode=#{shareCode}")
    void deleteShare(String shareCode);

    @Select("select * from share where shareImageID=#{shareImageID}")
    Share checkShareExisted(int shareImageID);

}
