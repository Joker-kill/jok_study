package com.jok.zxserver.domain.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @Author JOKER
 * create time 2024/8/21 13:34
 */
@Data
public class WebsocketVO implements Serializable {
    private String type;
    private HashMap<String,String> data;
}
