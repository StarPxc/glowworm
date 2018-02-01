package cn.jihangyu.glowworm.common.resp;

import lombok.Data;

/**
 * @author Ethanp
 * @version V1.0
 * @Package com.guohe.bike.common.resp
 * @Description: TODO
 * @date 2018/1/23 16:46
 */
@Data
public class ApiResult <T>{
    private int code;
    private String msg;
    private T data;
}
