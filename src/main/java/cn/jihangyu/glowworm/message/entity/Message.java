package cn.jihangyu.glowworm.message.entity;

import lombok.Data;

@Data
public class Message {
    private Integer id;

    private String fromUid;

    private String toUid;

    private Integer bid;

    private String letter;

    private String pass;


}