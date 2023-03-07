package com.ruoyi.web.controller.topic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.MyTopic;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.domain.entity.StuTopic;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.service.IMyTopicService;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * 我的选题Controller
 *
 * @author HY
 * @date 2023-02-24
 */
@RestController
@RequestMapping("/topic/mytopic")
public class MyTopicController extends BaseController
{
    @Autowired
    private IMyTopicService myTopicService;

    /**
     * 查询我的选题列表
     */
    @PreAuthorize("@ss.hasPermi('topic:myTopic:list')")
    @GetMapping("/list")
    public TableDataInfo list(MyTopic myTopic)
    {
        startPage();
        List<StuTopic> list = myTopicService.selectMyTopicList(myTopic);
        return getDataTable(list);
    }

    /**
     * 导出我的选题列表
     */
    @PreAuthorize("@ss.hasPermi('topic:myTopic:export')")
    @Log(title = "我的选题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MyTopic myTopic)
    {
        List<StuTopic> list = myTopicService.selectMyTopicList(myTopic);
        ExcelUtil<StuTopic> util = new ExcelUtil<>(StuTopic.class);
        util.exportExcel(response, list, "我的选题数据");
    }

    /**
     * 获取我的选题详细信息
     */
    @PreAuthorize("@ss.hasPermi('topic:myTopic:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(myTopicService.selectMyTopicById(id));
    }

    /**
     * 新增我的选题内容
     */
    @PreAuthorize("@ss.hasPermi('topic:myTopic:add')")
    @Log(title = "我的选题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MyTopic myTopic)
    {
        return toAjax(myTopicService.insertMyTopic(myTopic));
    }

    /**
     * 修改我的选题
     */
    @PreAuthorize("@ss.hasPermi('topic:myTopic:edit')")
    @Log(title = "我的选题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MyTopic myTopic)
    {
        return toAjax(myTopicService.updateMyTopic(myTopic));
    }

    /**
     * 删除我的选题
     */
    @PreAuthorize("@ss.hasPermi('topic:myTopic:remove')")
    @Log(title = "我的选题", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(myTopicService.deleteMyTopicByIds(ids));
    }

    /**
     * 上传我的选题附件
     */
    @PreAuthorize("@ss.hasPermi('topic:myTopic:upload')")
    @Log(title = "我的选题", businessType = BusinessType.EXPORT)
    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file, Long topicId) throws IOException {
        if (topicId != null) {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String projectPath = System.getProperty("user.dir");
            long timeStamp = new Date().getTime();
            String path = projectPath + "/profile/upload/" + timeStamp + suffixName;
            File dest = new File(path);
            if (!dest.exists()) { // 判断路径是否存在
                dest.mkdirs(); // 创建路径
            }
            file.transferTo(dest);
            MyTopic myTopic = new MyTopic();
            myTopic.setId(topicId);
            myTopic.setFilePath(path);
            myTopicService.updateMyTopic(myTopic);
            return AjaxResult.success("附件上传成功！");
        }else {
            return AjaxResult.error("附件上传失败！");
        }
    }

    /**
     * 下载我的选题附件
     */
    @PreAuthorize("@ss.hasPermi('topic:myTopic:download')")
    @PostMapping ("/download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam("filePath") String filePath, @RequestParam("fileName") String fileName) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            fileName = file.getName().lastIndexOf(".") > -1 ? fileName + file.getName().substring(file.getName().lastIndexOf(".")) : file.getName();
            System.out.println(fileName);
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);

            try (InputStream inputStream = new FileInputStream(file);
                 OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
            }
        } else {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("文件不存在");
        }
    }
}

