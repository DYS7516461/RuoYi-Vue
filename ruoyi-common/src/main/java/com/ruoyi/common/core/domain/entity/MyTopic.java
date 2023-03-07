package com.ruoyi.common.core.domain.entity;

import java.util.List;

import com.ruoyi.common.annotation.Excels;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 我的选题对象 my_topic
 *
 * @author HY
 * @date 2023-02-24
 */
public class MyTopic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键Id */
    private Long id;

    /** 选题id */
    @Excel(name = "选题id")
    private Long titleId;

    /** 学生id */
    @Excel(name = "学生id")
    private Long studentId;

    /** 附件类型 */
    @Excel(name = "附件类型")
    private String fileType;

    /** 文件名 */
    @Excel(name = "文件名")
    private String fileName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 文件状态 */
    @Excel(name = "文件状态")
    private String fileStatus;

    /** 上传类型 */
    @Excel(name = "上传类型")
    private String uploadType;

    @Excels({
        @Excel(name = "学生编号", targetAttr = "userId", type = Excel.Type.EXPORT),
        @Excel(name = "学生账号", targetAttr = "userName", type = Excel.Type.EXPORT),
        @Excel(name = "学生名称", targetAttr = "nickName", type = Excel.Type.EXPORT)
    })
    private SysUser student;

    @Excels({
        @Excel(name = "选题编号", targetAttr = "titleId", type = Excel.Type.EXPORT),
        @Excel(name = "选题名称", targetAttr = "titleName", type = Excel.Type.EXPORT)
    })
    private TopicTitle topicTitle;

    /** 题目信息 */
    private List<TopicTitle> topicTitleList;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTitleId(Long titleId)
    {
        this.titleId = titleId;
    }

    public Long getTitleId()
    {
        return titleId;
    }
    public void setStudentId(Long studentId)
    {
        this.studentId = studentId;
    }

    public Long getStudentId()
    {
        return studentId;
    }
    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public String getFileType()
    {
        return fileType;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return fileName;
    }
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getFilePath()
    {
        return filePath;
    }
    public void setFileStatus(String fileStatus)
    {
        this.fileStatus = fileStatus;
    }

    public String getFileStatus()
    {
        return fileStatus;
    }
    public void setUploadType(String uploadType)
    {
        this.uploadType = uploadType;
    }

    public String getUploadType()
    {
        return uploadType;
    }

    public List<TopicTitle> getTopicTitleList()
    {
        return topicTitleList;
    }

    public void setTopicTitleList(List<TopicTitle> topicTitleList)
    {
        this.topicTitleList = topicTitleList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("titleId", getTitleId())
                .append("studentId", getStudentId())
                .append("fileType", getFileType())
                .append("fileName", getFileName())
                .append("filePath", getFilePath())
                .append("fileStatus", getFileStatus())
                .append("uploadType", getUploadType())
                .append("topicTitleList", getTopicTitleList())
                .toString();
    }
}

