package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.StuDist;
import com.ruoyi.system.mapper.StuDistMapper;
import com.ruoyi.system.service.IStuDistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StuDistServiceImpl implements IStuDistService {
    private static final Logger log = LoggerFactory.getLogger(StuDistServiceImpl.class);

    @Resource
    private StuDistMapper distMapper;

    @Override
    public List<StuDist> selectDistList(StuDist dist) {
        return distMapper.selectDistList(dist);
    }

    @Override
    public int insertDist(StuDist[] dists) {
        //新增学生分配关系
        int i = 0;
        for (StuDist dist : dists){
            i += distMapper.insertDist(dist);
        }
        return i;
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
