package com.jok.zxserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jok.zxserver.domain.DO.LastMessage;
import com.jok.zxserver.domain.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/21 20:19
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 查询一对聊天对象之间的聊天记录
     * @param userId
     * @param friendId
     * @return 一对聊天对象之间的聊天记录列表
     */
    @Select("select * from message " +
            " where (sender_id =#{userId} and receiver_id = #{friendId}) or(sender_id =#{friendId} " +
            "and receiver_id = #{userId}) " +
            "order by date asc ")
    List<Message> selectMessageListByRelation(@Param("userId") String userId, @Param("friendId") String friendId);

    /**
     * 根据用户id和好友id查询未读消息数量
     * @param userId
     * @param friendId
     * @return 未对消息数量
     */
    @Select("select count(id) from message "+
            " where sender_id =#{friendId} and receiver_id = #{userId} and status = 0" )
    Integer selectNotReadCount(@Param("userId") String userId, @Param("friendId") String friendId);

    /**
     * 更新消息状态
     * @param receiverId
     * @param senderId
     * @param status
     * @return 更新影响行数
     */
    @Update("update message set status = #{status} where receiver_id = #{receiverId} and sender_id =#{senderId}")
    int updateStatus(@Param("receiverId") String receiverId,@Param("senderId")String senderId,@Param("status") Integer status);

    @Select("select count(id) from message where receiver_id=#{userId}")
    Integer selectAllNotReadCountByUserId(@Param("userId") String userId);

    @Select("SELECT " +
            "m1.content, " +
            "m2.fid as user_id, " +
            "m2.max_date as date, " +
            "m1.`status`, " +
            "m1.type " +
            "FROM message AS m1 " +
            "INNER JOIN (" +
            "    SELECT" +
            "        CASE " +
            "            WHEN sender_id = #{userId} THEN sender_id " +
            "            ELSE receiver_id " +
            "        END AS mid, " +
            "        CASE " +
            "            WHEN receiver_id = #{userId} THEN sender_id " +
            "            ELSE receiver_id " +
            "        END AS fid, " +
            "        MAX(date) AS max_date " +
            "    FROM message " +
            "    WHERE sender_id = #{userId} OR receiver_id = #{userId} " +
            "    GROUP BY " +
            "        CASE " +
            "            WHEN sender_id = #{userId} THEN receiver_id " +
            "            ELSE sender_id " +
            "        END, " +
            "        CASE " +
            "            WHEN receiver_id = #{userId} THEN receiver_id " +
            "            ELSE sender_id " +
            "        END " +
            ") AS m2 " +
            "ON m1.date = m2.max_date AND (m1.sender_id = m2.mid OR m1.receiver_id = m2.mid)" +
            "ORDER BY m2.max_date DESC")
    List<LastMessage> selectLastMessagesByUserId(@Param("userId") String userId);


}
