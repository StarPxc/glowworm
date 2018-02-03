package cn.jihangyu.glowworm.activity.entity;

import java.util.Date;

public class Activity {
    private Integer aId;

    private String aTitle;

    private String aIntroduction;

    private Date aStartTime;

    private Date aEndTime;

    private String aState;

    private String aImgs;

    private Integer totalNumberOfRegistration;

    public Integer getaId() {
        return aId;
    }

    public void setaId(Integer aId) {
        this.aId = aId;
    }

    public String getaTitle() {
        return aTitle;
    }

    public void setaTitle(String aTitle) {
        this.aTitle = aTitle == null ? null : aTitle.trim();
    }

    public String getaIntroduction() {
        return aIntroduction;
    }

    public void setaIntroduction(String aIntroduction) {
        this.aIntroduction = aIntroduction == null ? null : aIntroduction.trim();
    }

    public Date getaStartTime() {
        return aStartTime;
    }

    public void setaStartTime(Date aStartTime) {
        this.aStartTime = aStartTime;
    }

    public Date getaEndTime() {
        return aEndTime;
    }

    public void setaEndTime(Date aEndTime) {
        this.aEndTime = aEndTime;
    }

    public String getaState() {
        return aState;
    }

    public void setaState(String aState) {
        this.aState = aState == null ? null : aState.trim();
    }

    public String getaImgs() {
        return aImgs;
    }

    public void setaImgs(String aImgs) {
        this.aImgs = aImgs == null ? null : aImgs.trim();
    }

    public Integer getTotalNumberOfRegistration() {
        return totalNumberOfRegistration;
    }

    public void setTotalNumberOfRegistration(Integer totalNumberOfRegistration) {
        this.totalNumberOfRegistration = totalNumberOfRegistration;
    }
}