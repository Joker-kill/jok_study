package com.jok.zxserver.domain.VO;

import com.jok.zxserver.domain.entity.Message;
import lombok.Data;


import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/28 20:08
 */


@Data
public class MessageVO {

    private String name;

    private String avatar;

    private String date;

    private String userId;

    private Integer notReadCount;

    private List<Message> messageList;
}
