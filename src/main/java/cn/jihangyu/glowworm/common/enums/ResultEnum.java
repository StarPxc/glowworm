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
    OBJECT_FIND_ERROR(504,"对象不存在"),
    OBJECT_DELETE_ERROR(505,"删除对象失败"),
    OBJECT_UPDATE_ERROR(506,"更新对象失败"),
    ;
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
