package com.ruoyi.web.controller.topic;


import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.domain.entity.StuTopic;
import com.ruoyi.system.service.IStuTopicService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生选题Controller
 *
 * @author ruoyi
 * @date 2023-03-07
 */
@RestController
@RequestMapping("/topic/stuTopic")
public class StuTopicController extends BaseController
{
    @Autowired
    private IStuTopicService stuTopicService;

    /**
     * 查询学生选题列表
     */
    @PreAuthorize("@ss.hasAnyPermi('topic:stuTopic:list,topic:myTopic:list')")
    @GetMapping("/list")
    public TableDataInfo list(StuTopic stuTopic)
    {
        startPage();
        List<StuTopic> list = stuTopicService.selectStuTopicList(stuTopic);
        return getDataTable(list);
    }

    /**
     * 导出学生选题列表
     */
    @PreAuthorize("@ss.hasPermi('topic:stuTopic:export')")
    @Log(title = "学生选题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StuTopic stuTopic)
    {
        List<StuTopic> list = stuTopicService.selectStuTopicList(stuTopic);
        ExcelUtil<StuTopic> util = new ExcelUtil<StuTopic>(StuTopic.class);
        util.exportExcel(response, list, "学生选题数据");
    }

    /**
     * 获取学生选题详细信息
     */
    @PreAuthorize("@ss.hasPermi('topic:stuTopic:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(stuTopicService.selectStuTopicById(id));
    }

    /**
     * 新增学生选题
     */
    @PreAuthorize("@ss.hasAnyPermi('topic:stuTopic:add,topic:title:Apply,topic:title:approve,topic:title:reject')")
    @Log(title = "学生选题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StuTopic stuTopic)
    {
        return success(stuTopicService.insertStuTopic(stuTopic));
    }

    /**
     * 修改学生选题
     */
    @PreAuthorize("@ss.hasAnyPermi('topic:stuTopic:edit,topic:title:Apply,topic:title:approve,topic:title:reject')")
    @Log(title = "学生选题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StuTopic stuTopic)
    {
        return toAjax(stuTopicService.updateStuTopic(stuTopic));
    }

    /**
     * 删除学生选题
     */
    @PreAuthorize("@ss.hasPermi('topic:stuTopic:remove')")
    @Log(title = "学生选题", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(stuTopicService.deleteStuTopicByIds(ids));
    }
}

