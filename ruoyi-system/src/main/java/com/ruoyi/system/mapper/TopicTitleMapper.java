package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.entity.TopicTitle;
import org.apache.ibatis.annotations.Mapper;

/**
 * 题目Mapper接口
 *
 * @author HY
 * @date 2023-02-22
 */
@Mapper
public interface TopicTitleMapper
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
     * 删除题目
     *
     * @param id 题目主键
     * @return 结果
     */
    public int deleteTopicTitleById(Long id);

    /**
     * 批量删除题目
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTopicTitleByIds(Long[] ids);
}
