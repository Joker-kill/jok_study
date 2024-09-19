package com.jok.zxserver.domain.VO;

import lombok.Data;

/**
 * @Author JOKER
 * create time 2024/9/16 21:57
 */
@Data
public class MessageListItemVO {
    private String name;
    private String userId;
    private String avatar;
    private Integer notReadCount;
    private String lastMessage;
    private String lastDate;
}
