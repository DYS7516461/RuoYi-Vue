package com.ruoyi.system.service;


import java.util.List;

import com.ruoyi.common.core.domain.entity.TopicTitle;
/**
 * 题目Service接口
 *
 * @author HY
 * @date 2023-02-22
 */
public interface ITopicTitleService
{
    /**
     * 查询题目
     *
     * @param id 题目主键
     * @return 题目
     */
    public TopicTitle selectTopicTitleById(Long id);

    /**
     * 查询题目列表
     *
     * @param topicTitle 题目
     * @return 题目集合
     */
    public List<TopicTitle> selectTopicTitleList(TopicTitle topicTitle);

    /**
     * 新增题目
     *
     * @param topicTitle 题目
     * @return 结果
     */
    public int insertTopicTitle(TopicTitle topicTitle);

    /**
     * 修改题目
     *
     * @param topicTitle 题目
     * @return 结果
     */
    public int updateTopicTitle(TopicTitle topicTitle);

    /**
     * 批量删除题目
     *
     * @param ids 需要删除的题目主键集合
     * @return 结果
     */
    public int deleteTopicTitleByIds(Long[] ids);

    /**
     * 删除题目信息
     *
     * @param id 题目主键
     * @return 结果
     */
    public int deleteTopicTitleById(Long id);
}