package cn.jihangyu.glowworm.user.entity;

import lombok.Data;

@Data
public class User {
    private Integer uId;

    private String uNickname;

    private String uProvince;

    private String uCity;

    private String uGender;

    private String uEmail;

    private Integer appointmentBId;

    private String uPhone;

    private String uSelfIntroduction;


}