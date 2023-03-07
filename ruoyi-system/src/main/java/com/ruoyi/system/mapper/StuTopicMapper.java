package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.StuTopic;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生选题Mapper接口
 *
 * @author HY
 * @date 2023-03-07
 */
@Mapper
public interface StuTopicMapper
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
     * 返回插入的最后一条数据的id
     *
     * @return 结果
     */
    public Long selectLastInsertId();

    /**
     * 修改学生选题
     *
     * @param stuTopic 学生选题
     * @return 结果
     */
    public int updateStuTopic(StuTopic stuTopic);

    /**
     * 删除学生选题
     *
     * @param id 学生选题主键
     * @return 结果
     */
    public int deleteStuTopicById(Long id);

    /**
     * 批量删除学生选题
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStuTopicByIds(Long[] ids);
}
