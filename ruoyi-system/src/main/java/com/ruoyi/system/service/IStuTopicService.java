package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.StuTopic;

/**
 * 学生选题Service接口
 *
 * @author ruoyi
 * @date 2023-03-07
 */
public interface IStuTopicService
{
    /**
     * 查询学生选题
     *
     * @param id 学生选题主键
     * @return 学生选题
     */
    public StuTopic selectStuTopicById(Long id);

    /**
     * 查询学生选题列表
     *
     * @param stuTopic 学生选题
     * @return 学生选题集合
     */
    public List<StuTopic> selectStuTopicList(StuTopic stuTopic);

    /**
     * 新增学生选题
     *
     * @param stuTopic 学生选题
     * @return 结果
     */
    public int insertStuTopic(StuTopic stuTopic);

    /**
     * 修改学生选题
     *
     * @param stuTopic 学生选题
     * @return 结果
     */
    public int updateStuTopic(StuTopic stuTopic);

    /**
     * 批量删除学生选题
     *
     * @param ids 需要删除的学生选题主键集合
     * @return 结果
     */
    public int deleteStuTopicByIds(Long[] ids);

    /**
     * 删除学生选题信息
     *
     * @param id 学生选题主键
     * @return 结果
     */
    public int deleteStuTopicById(Long id);
}
