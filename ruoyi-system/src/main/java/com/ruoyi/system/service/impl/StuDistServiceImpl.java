package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.StuDist;
import com.ruoyi.system.mapper.StuDistMapper;
import com.ruoyi.system.service.IStuDistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StuDistServiceImpl implements IStuDistService {
    private static final Logger log = LoggerFactory.getLogger(StuDistServiceImpl.class);

    @Autowired
    private StuDistMapper distMapper;

    @Override
    public List<StuDist> selectDistList(StuDist dist) {
        return distMapper.selectDistList(dist);
    }

    @Override
    public int insertDist(StuDist dist) {
        //新增学生分配关系
        return distMapper.insertDist(dist);
    }

    @Override
    public int updateDist(StuDist[] dists) {
        return distMapper.updateDist(dists);
    }

    @Override
    public int deleteDistByIds(Long[] ids) {
        return distMapper.deleteDistByIds(ids);
    }
}
