package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.StuTopicMapper;
import com.ruoyi.common.core.domain.entity.StuTopic;
import com.ruoyi.system.service.IStuTopicService;

/**
 * 学生选题Service业务层处理
 *
 * @author HY
 * @date 2023-03-07
 */
@Service
public class StuTopicServiceImpl implements IStuTopicService
{
    @Autowired
    private StuTopicMapper stuTopicMapper;

    /**
     * 查询学生选题
     *
     * @param id 学生选题主键
     * @return 学生选题
     */
    @Override
    public StuTopic selectStuTopicById(Long id)
    {
        return stuTopicMapper.selectStuTopicById(id);
    }

    /**
     * 查询学生选题列表
     *
     * @param stuTopic 学生选题
     * @return 学生选题
     */
    @Override
    public List<StuTopic> selectStuTopicList(StuTopic stuTopic)
    {
        return stuTopicMapper.selectStuTopicList(stuTopic);
    }

    /**
     * 新增学生选题
     *
     * @param stuTopic 学生选题
     * @return 结果
     */
    @Override
    public int insertStuTopic(StuTopic stuTopic)
    {
        stuTopicMapper.insertStuTopic(stuTopic);
        return stuTopicMapper.selectLastInsertId().intValue();
    }

    /**
     * 修改学生选题
     *
     * @param stuTopic 学生选题
     * @return 结果
     */
    @Override
    public int updateStuTopic(StuTopic stuTopic)
    {
        return stuTopicMapper.updateStuTopic(stuTopic);
    }

    /**
     * 批量删除学生选题
     *
     * @param ids 需要删除的学生选题主键
     * @return 结果
     */
    @Override
    public int deleteStuTopicByIds(Long[] ids)
    {
        return stuTopicMapper.deleteStuTopicByIds(ids);
    }

    /**
     * 删除学生选题信息
     *
     * @param id 学生选题主键
     * @return 结果
     */
    @Override
    public int deleteStuTopicById(Long id)
    {
        return stuTopicMapper.deleteStuTopicById(id);
    }
}
