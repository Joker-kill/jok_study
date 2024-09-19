package com.jok.zxserver.domain.common.enums;

/**
 * @Author JOKER
 * create time 2024/8/13 13:15
 */
public enum BooKingStatus {
    NOT_ACCEPT(1),
    ACCEPTED(2),
    NOT_ACCEPT_USER_REVOKE(3),
    REFUSED(4),
    ACCEPTED_REVOKE(5),
    DELETED(6),
    COMPLETE(7);
    private Integer status;
    private BooKingStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }
    public static BooKingStatus getEnumByStatus(Integer status) {
        for (BooKingStatus bs : BooKingStatus.values()) {
            if (bs.getStatus().equals(status)) {
                return bs;
            }
        }
        return null;
    }
}
