package com.ruoyi.web.controller.topic;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.StuDist;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.IStuDistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/topic/dist")
public class stuDistController extends BaseController {

    @Autowired
    private IStuDistService distService;
    
    @Autowired
    private ISysDeptService deptService;


    /**
     * 获取学生分配列表
     */
    @PreAuthorize("@ss.hasPermi('topic:dist:list')")
    @GetMapping("/list")
    public TableDataInfo list(StuDist dist)
    {
        startPage();
        List<StuDist> list = distService.selectDistList(dist);
        return getDataTable(list);
    }

    /**
     * 新增学生分配关系
     */
    @PreAuthorize("@ss.hasPermi('topic:dist:add')")
    @Log(title = "学生分配", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody StuDist dist)
    {
        return toAjax(distService.insertDist(dist));
    }

    /**
     * 修改学生分配关系
     */
    @PreAuthorize("@ss.hasPermi('topic:dist:edit')")
    @Log(title = "学生分配", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody StuDist[] dists)
    {
        return toAjax(distService.updateDist(dists));
    }

    /**
     * 删除学生分配关系
     */
    @PreAuthorize("@ss.hasPermi('topic:dist:remove')")
    @Log(title = "学生分配", businessType = BusinessType.DELETE)
    @DeleteMapping("/{distIds}")
    public AjaxResult remove(@PathVariable Long[] distIds)
    {
        return toAjax(distService.deleteDistByIds(distIds));
    }

    /**
     * 获取部门树列表
     */
    @PreAuthorize("@ss.hasPermi('topic:dist:list')")
    @GetMapping("/deptTree")
    public AjaxResult deptTree(SysDept dept)
    {
        return success(deptService.selectDeptTreeList(dept));
    }

}
