package cn.jihangyu.glowworm.common.enums;

/**
 * @author Ethanp
 * @version V1.0
 * @Package com.db.fiftysql.common.enums
 * @Description: TODO
 * @date 2018/1/29 10:30
 */
public enum ResultEnum {
    UNKONW_ERROR(500,"未知错误"),
    SUCCSEE(200,"成功"),
    OBJECT_NULL_ERROR(501,"对象不能为空"),
    OBJECT_ADD_ERROR(502,"添加对象失败"),
    OBJECT_ALL_Field_NULL(503,"对象所有属性为空"),
    OBJECT_FIND_ERROR(504,"查询对象失败"),
    OBJECT_DELETE_ERROR(505,"删除对象失败"),
    OBJECT_UPDATE_ERROR(506,"更新对象失败"),
    NO_USER(507,"用户不存在"),
    NO_ACTIVITY(508,"活动不存在"),
    REDIS_INITIALIZATION_ERROR(507,"redis初始化失败"),
    NO_LOGIN(508,"未登录"),
    FILE_ERROR(509,"上传文件异常" ),
    FILE_FORMAT_ERROR(510,"文件格式错误" ), FILE_TOO_BIG(511,"文件过大" ),
    CURRENT_USER_ERROR(511,"获取当前用户失败"),
    NO_AUTHORITY(512,"没有权限"),
    NO_ID(513, "缺少id"),
    //关于书
    BOOK_ORDERD(601,"图书已被预定"),
    BOOK_NOT_ORDERD(602,"图书未被预定"),
    ORDER_ERROR(603,"预定图书失败"),

    RETURN_ERROR(604,"归还图书失败"), IDENTITY_AUTHENTICATION_FAILURE(514,"身份验证失败" ),
    //关于图片
    IMG_ERROR(701,"获取图片失败"), IMG_HAS_EXISTED(702, "图片已经存在"),

    //关于消息
    MESSAGE_EXIST(801,"消息记录已存在"),
    //关于活动
    HAS_JOIN(901,"已报名" );
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
