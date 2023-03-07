package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.entity.MyTopic;
import com.ruoyi.common.core.domain.entity.TopicTitle;
import com.ruoyi.common.core.domain.entity.StuTopic;
import org.apache.ibatis.annotations.Mapper;

/**
 * 我的选题Mapper接口
 *
 * @author HY
 * @date 2023-02-24
 */
@Mapper
public interface MyTopicMapper
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
     * 删除我的选题
     *
     * @param id 我的选题主键
     * @return 结果
     */
    public int deleteMyTopicById(Long id);

    /**
     * 批量删除我的选题
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMyTopicByIds(Long[] ids);

    /**
     * 批量删除题目
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTopicTitleByIds(Long[] ids);

    /**
     * 批量新增题目
     *
     * @param topicTitleList 题目列表
     * @return 结果
     */
    public int batchTopicTitle(List<TopicTitle> topicTitleList);


    /**
     * 通过我的选题主键删除题目信息
     *
     * @param id 我的选题ID
     * @return 结果
     */
    public int deleteTopicTitleById(Long id);
}

