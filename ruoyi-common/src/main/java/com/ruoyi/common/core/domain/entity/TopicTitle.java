package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.BaseEntity;

public class TopicTitle extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "题目编号", cellType = Excel.ColumnType.NUMERIC, prompt = "题目编号")
    private Long id;

    @Excel(name = "题目名称")
    private String titleName;

    @Excel(name = "教师编号")
    private String teacherId;

    @Excels({
            @Excel(name = "教师姓名", targetAttr = "nickName", type = Excel.Type.EXPORT),
    })
    private SysUser teacher;

    @Excel(name = "题目状态", readConverterExp = "0=待审核,1=审核通过,2=审核未通过")
    private String status;

    @Excel(name = "备注")
    private String remark;

    public Long getStuTopicId() {
        return stuTopicId;
    }

    public void setStuTopicId(Long stuTopicId) {
        this.stuTopicId = stuTopicId;
    }

    /** 待审批学生选题id */
    @Excel(name = "待审批学生选题id")
    private Long stuTopicId;

    public StuTopic getStuTopic() {
        return stuTopic;
    }

    public void setStuTopic(StuTopic stuTopic) {
        this.stuTopic = stuTopic;
    }

    private StuTopic stuTopic;

    private String orderByColumn;

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }

    private String isAsc;

    public TopicTitle() {
    }

    public TopicTitle(Long id, String titleName, String teacherId,SysUser teacher, String status, String remark) {
        this.id = id;
        this.titleName = titleName;
        this.teacherId = teacherId;
        this.teacher = teacher;
        this.status = status;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

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


    public SysUser getTeacher() {
        return teacher;
    }

    public void setTeacher(SysUser teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "TopicTitle [id=" + id +
                ", titleName=" + titleName +
                ", teacherId=" + teacherId +
                ", status=" + status +
                ", remark=" + remark +
                ", teacher=" + teacher +
                "]";
    }
}
