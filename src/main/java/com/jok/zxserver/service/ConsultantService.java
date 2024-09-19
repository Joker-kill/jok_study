package com.jok.zxserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jok.zxserver.domain.VO.ConsultantVO;
import com.jok.zxserver.domain.entity.Consultant;

import java.util.Date;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/4 19:24
 */

public interface ConsultantService extends IService<Consultant> {
    List<ConsultantVO> getOnlineConsultantList();
    List<ConsultantVO> getOnlineConsultantList(Date date);
    ConsultantVO getConsultantInfoById(Integer consultantId);
}
