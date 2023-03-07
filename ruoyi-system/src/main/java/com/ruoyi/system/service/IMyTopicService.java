package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.MyTopic;
import com.ruoyi.common.core.domain.entity.StuTopic;

/**
 * 我的选题Service接口
 *
 * @author HY
 * @date 2023-02-24
 */
public interface IMyTopicService
{
    /**
     * 查询我的选题
     *
     * @param id 我的选题主键
     * @return 我的选题
     */
    public MyTopic selectMyTopicById(Long id);

    /**
     * 查询我的选题列表
     *
     * @param myTopic 我的选题
     * @return 我的选题集合
     */
    public List<StuTopic> selectMyTopicList(MyTopic myTopic);

    /**
     * 新增我的选题
     *
     * @param myTopic 我的选题
     * @return 结果
     */
    public int insertMyTopic(MyTopic myTopic);

    /**
     * 修改我的选题
     *
     * @param myTopic 我的选题
     * @return 结果
     */
    public int updateMyTopic(MyTopic myTopic);

    /**
     * 批量删除我的选题
     *
     * @param ids 需要删除的我的选题主键集合
     * @return 结果
     */
    public int deleteMyTopicByIds(Long[] ids);

    /**
     * 删除我的选题信息
     *
     * @param id 我的选题主键
     * @return 结果
     */
    public int deleteMyTopicById(Long id);
}
