package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.entity.MyTopic;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.entity.TopicTitle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 对象 stu_topic
 *
 * @author ruoyi
 * @date 2023-02-24
 */
public class StuTopic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 学生id */
    @Excel(name = "学生id")
    private String studentId;

    /** 选题id */
    @Excel(name = "选题id")
    private String titleId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    private String remark;

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
    private TopicTitle title;

    @Excels({
        @Excel(name = "文件类型", targetAttr = "fileType", type = Excel.Type.EXPORT),
        @Excel(name = "文件名称", targetAttr = "fileName", type = Excel.Type.EXPORT)
    })
    private List<MyTopic> myTopics;

    public SysUser getStudent() {
        return student;
    }

    public void setStudent(SysUser student) {
        this.student = student;
    }

    public TopicTitle getTitle() {
        return title;
    }

    public void setTitle(TopicTitle title) {
        this.title = title;
    }

    public List<MyTopic> getMyTopics() {
        return myTopics;
    }

    public void setMyTopics(List<MyTopic> myTopics) {
        this.myTopics = myTopics;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setStudentId(String studentId)
    {
        this.studentId = studentId;
    }

    public String getStudentId()
    {
        return studentId;
    }
    public void setTitleId(String titleId)
    {
        this.titleId = titleId;
    }

    public String getTitleId()
    {
        return titleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("studentId", getStudentId())
                .append("titleId", getTitleId())
                .toString();
    }
}
