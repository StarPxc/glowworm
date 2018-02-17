package cn.jihangyu.glowworm.userRemarkActivity.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserRemarkActivity {
    private Integer uraId;

    private String uId;

    private Integer aId;

    private String comment;

    private Date createTime;


}