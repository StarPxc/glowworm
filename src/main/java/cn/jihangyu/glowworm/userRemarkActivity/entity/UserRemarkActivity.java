package cn.jihangyu.glowworm.userRemarkActivity.entity;

import lombok.Data;

import java.util.Date;
@Data
public class UserRemarkActivity {
    private Integer uraId;

    private Integer uId;

    private Integer aId;

    private String comment;

    private Date createTime;

}