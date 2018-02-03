package cn.jihangyu.glowworm.activity.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Activity {
    private Integer aId;

    private String aTitle;

    private String aIntroduction;

    private Date aStartTime;

    private Date aEndTime;

    private String aState;

    private String aImgs;

    private Integer totalNumberOfRegistration;


}