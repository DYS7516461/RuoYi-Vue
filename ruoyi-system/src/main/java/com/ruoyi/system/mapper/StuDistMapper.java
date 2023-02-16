package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.StuDist;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StuDistMapper {

    /**
     * 查询学生分配列表
     *
     * @param dist 学生分配信息
     * @return 学生分配集合
     */
    public List<StuDist> selectDistList(StuDist dist);

    /**
     * 新增学生分配
     *
     * @param dist 学生分配信息
     * @return 结果
     */
    public int insertDist(StuDist dist);

    /**
     * 修改学生分配
     *
     * @param dist 学生分配信息
     * @return 结果
     */
    public int updateDist(StuDist[] dist);

    /**
     * 删除学生分配
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDistByIds(Long[] ids);
}
