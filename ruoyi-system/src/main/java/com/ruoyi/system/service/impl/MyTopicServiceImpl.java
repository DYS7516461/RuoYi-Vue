package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.MyTopic;
import com.ruoyi.common.core.domain.entity.TopicTitle;
import com.ruoyi.common.core.domain.entity.StuTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.mapper.MyTopicMapper;
import com.ruoyi.system.service.IMyTopicService;

/**
 * 我的选题Service业务层处理
 *
 * @author HY
 * @date 2023-02-24
 */
@Service
public class MyTopicServiceImpl implements IMyTopicService
{
    @Autowired
    private MyTopicMapper myTopicMapper;

    /**
     * 查询我的选题
     *
     * @param id 我的选题主键
     * @return 我的选题
     */
    @Override
    public MyTopic selectMyTopicById(Long id)
    {
        return myTopicMapper.selectMyTopicById(id);
    }

    /**
     * 查询我的选题列表
     *
     * @param myTopic 我的选题
     * @return 我的选题
     */
    @Override
    public List<StuTopic> selectMyTopicList(MyTopic myTopic)
    {
        return myTopicMapper.selectMyTopicList(myTopic);
    }

    /**
     * 新增我的选题
     *
     * @param myTopic 我的选题
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMyTopic(MyTopic myTopic)
    {
        int rows = myTopicMapper.insertMyTopic(myTopic);
        insertTopicTitle(myTopic);
        return rows;
    }

    /**
     * 修改我的选题
     *
     * @param myTopic 我的选题
     * @return 结果
     */
    @Transactional
    @Override
    public int updateMyTopic(MyTopic myTopic)
    {
//        myTopicMapper.deleteTopicTitleById(myTopic.getId());
//        insertTopicTitle(myTopic);
        return myTopicMapper.updateMyTopic(myTopic);
    }

    /**
     * 批量删除我的选题
     *
     * @param ids 需要删除的我的选题主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMyTopicByIds(Long[] ids)
    {
        myTopicMapper.deleteTopicTitleByIds(ids);
        return myTopicMapper.deleteMyTopicByIds(ids);
    }

    /**
     * 删除我的选题信息
     *
     * @param id 我的选题主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMyTopicById(Long id)
    {
        myTopicMapper.deleteTopicTitleById(id);
        return myTopicMapper.deleteMyTopicById(id);
    }

    /**
     * 新增题目信息
     *
     * @param myTopic 我的选题对象
     */
    public void insertTopicTitle(MyTopic myTopic)
    {
        List<TopicTitle> topicTitleList = myTopic.getTopicTitleList();
        Long id = myTopic.getId();
        if (StringUtils.isNotNull(topicTitleList))
        {
            List<TopicTitle> list = new ArrayList<TopicTitle>();
            for (TopicTitle topicTitle : topicTitleList)
            {
                topicTitle.setId(id);
                list.add(topicTitle);
            }
            if (list.size() > 0)
            {
                myTopicMapper.batchTopicTitle(list);
            }
        }
    }
}
