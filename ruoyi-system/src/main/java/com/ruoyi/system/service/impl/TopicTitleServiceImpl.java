package com.ruoyi.system.service.impl;


import com.ruoyi.common.core.domain.entity.TopicTitle;
import com.ruoyi.system.mapper.TopicTitleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.ITopicTitleService;

import java.util.List;

/**
 * 题目Service业务层处理
 *
 * @author HY
 * @date 2023-02-22
 */
@Service
public class TopicTitleServiceImpl implements ITopicTitleService
{
    @Autowired
    private TopicTitleMapper topicTitleMapper;

    /**
     * 查询题目
     *
     * @param id 题目主键
     * @return 题目
     */
    @Override
    public TopicTitle selectTopicTitleById(Long id)
    {
        return topicTitleMapper.selectTopicTitleById(id);
    }


    /**
     * 查询题目列表
     *
     * @param topicTitle 题目
     * @return 题目
     */
    @Override
    public List<TopicTitle> selectTopicTitleList(TopicTitle topicTitle)
    {
        return topicTitleMapper.selectTopicTitleList(topicTitle);
    }

    /**
     * 新增题目
     *
     * @param topicTitle 题目
     * @return 结果
     */
    @Override
    public int insertTopicTitle(TopicTitle topicTitle)
    {
        return topicTitleMapper.insertTopicTitle(topicTitle);
    }

    /**
     * 修改题目
     *
     * @param topicTitle 题目
     * @return 结果
     */
    @Override
    public int updateTopicTitle(TopicTitle topicTitle)
    {
        return topicTitleMapper.updateTopicTitle(topicTitle);
    }

    /**
     * 批量删除题目
     *
     * @param ids 需要删除的题目主键
     * @return 结果
     */
    @Override
    public int deleteTopicTitleByIds(Long[] ids)
    {
        return topicTitleMapper.deleteTopicTitleByIds(ids);
    }

    /**
     * 删除题目信息
     *
     * @param id 题目主键
     * @return 结果
     */
    @Override
    public int deleteTopicTitleById(Long id)
    {
        return topicTitleMapper.deleteTopicTitleById(id);
    }
}
