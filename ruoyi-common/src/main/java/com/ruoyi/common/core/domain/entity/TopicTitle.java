package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;

public class TopicTitle extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String titleName;

    private String teacherId;

    public SysUser getTeacher() {
        return teacher;
    }

    public void setTeacher(SysUser teacher) {
        this.teacher = teacher;
    }

    private SysUser teacher;

    private String status;

    private String remark;

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
