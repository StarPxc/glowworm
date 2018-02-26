package cn.jihangyu.glowworm.activityEnd.entity;

import cn.jihangyu.glowworm.activity.entity.Activity;
import lombok.Data;

@Data
public class ActivityEnd {
    private Integer endId;

    private Integer aId;

    private String endImgs;

    private String summary;

    private Integer totalPeopleNumber;

    private Activity activity;

}