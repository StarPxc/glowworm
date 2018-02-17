package cn.jihangyu.glowworm.user.service;


import cn.jihangyu.glowworm.cache.CommonCacheUtil;
import cn.jihangyu.glowworm.common.enums.ResultEnum;
import cn.jihangyu.glowworm.common.execption.GlowwormExecption;
import cn.jihangyu.glowworm.common.utils.MD5Util;
import cn.jihangyu.glowworm.user.dao.UserMapper;
import cn.jihangyu.glowworm.user.entity.User;
import cn.jihangyu.glowworm.user.entity.UserElement;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.user.service
 * @Description: TODO 用户业务
 * @date 2018/2/2 9:06
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    @Lazy
    private UserMapper userMapper;
    @Autowired
    private CommonCacheUtil cacheUtil;
    private static ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private static Lock rlock = lock.readLock();
    private static Lock wlock = lock.writeLock();

    /**
     * 生成token
      * @param openid
     * @return
     */
    private String generateToken(String openid) {
        String source = openid +":" + System.currentTimeMillis();
        return MD5Util.getMD5(source);
    }

    @Override
    public User findUserById(String id) {
        User user;
        rlock.lock();
        try {
            user= userMapper.selectByPrimaryKey(id);
        }finally {
            rlock.unlock();
        }
        if(user==null){
            throw new GlowwormExecption(ResultEnum.NO_USER);
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        if(user==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        }
        wlock.lock();
        try {
           userMapper.updateByPrimaryKeySelective(user);
        }catch (GlowwormExecption e){
            throw new GlowwormExecption(ResultEnum.OBJECT_UPDATE_ERROR);
        }finally {
            wlock.unlock();
        }

    }

    @Override
    public void deleteUserById(String id) {
        if(id==null){
            throw new GlowwormExecption(ResultEnum.OBJECT_NULL_ERROR);
        }
        wlock.lock();
        try {
            int code= userMapper.deleteByPrimaryKey(id);
            if(code!=1){
                throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
            }

        }catch (GlowwormExecption e){
            throw new GlowwormExecption(ResultEnum.OBJECT_FIND_ERROR);
        }
        catch (Exception e){
            log.error("【删除对象失败】",e);
            throw new GlowwormExecption(ResultEnum.OBJECT_DELETE_ERROR);

        }finally {
            wlock.unlock();
        }
    }

    @Override
    public String login(String code) throws IOException {
        String openid="";
        //用code换取openid
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid=wx3e09d2514a3f1cd5&secret=d7e5c9d67ea52022ddfa47385fea35d6&js_code="+code+"&grant_type=authorization_code");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        httpClient.close();
        String wxInfo=response.toString();
        log.info(wxInfo);
        JSONObject jsonObject=JSON.parseObject(wxInfo);
        if(jsonObject.containsKey("openid")){
            openid= (String) jsonObject.get("openid");
        }else {
            throw new GlowwormExecption(ResultEnum.IDENTITY_AUTHENTICATION_FAILURE);
        }

        //查找用户是否存在
        User user=userMapper.selectByPrimaryKey(openid);
        if(user==null){
            User newUser=new User();
            newUser.setUId(openid);
           userMapper.insertSelective(newUser);
        }
        String token= generateToken(openid);//生成token
        UserElement ue=new UserElement();
        ue.setToken(token);
        ue.setUserId(openid);
        ue.setRole("user");//管理员权限直接在数据库去修改，默认都是普通用户
        cacheUtil.putTokenWhenLogin(ue);//把token 存入缓存
        return token;
    }


}
