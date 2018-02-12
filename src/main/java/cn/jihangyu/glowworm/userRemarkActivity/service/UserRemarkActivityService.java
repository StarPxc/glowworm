package cn.jihangyu.glowworm.userRemarkActivity.service;

import cn.jihangyu.glowworm.userRemarkActivity.entity.UserRemarkActivity;

import java.util.List;

/**
 * @author Ethanp
 * @version V1.0
 * @Package cn.jihangyu.glowworm.userRemarkActivity.service
 * @Description: TODO
 * @date 2018/2/5 16:38
 */
public interface UserRemarkActivityService {
    /**
     *添加用户评论活动的一条记录
     * @param userRemarkActivity
     * @return
     */
    UserRemarkActivity addComment(UserRemarkActivity userRemarkActivity) throws Exception;

    /**
     * 根据id删除用户评论活动的一条记录
     * @param id
     */
    void deleteUserRemarkActivityById(Integer id);

    /**
     *根据用户id和活动id查看用户评论活动的记录
     * @param uId 用户id
     * @param aId 活动id
     * @return
     */
    List<UserRemarkActivity> selectUserRemarkActivitysByUIdAndAId(Integer uId, Integer aId);
}
