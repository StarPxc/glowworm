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
    OBJECT_NULL_ERROR(501,"对象不能为空")
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
