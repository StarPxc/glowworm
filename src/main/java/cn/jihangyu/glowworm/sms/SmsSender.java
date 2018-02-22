package cn.jihangyu.glowworm.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

public interface SmsSender {
    SendSmsResponse sendSms(String phone, String signName, String templateCode, String to_username, String bookname) throws ClientException;
}
