package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;

public class topic extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String titleId;

    private String titleName;

    private String teacherId;

    private String status;

    private String remark;

    public topic() {
    }

    public topic(Long id, String titleId, String titleName, String teacherId, String status, String remark) {
        this.id = id;
        this.titleId = titleId;
        this.titleName = titleName;
        this.teacherId = teacherId;
        this.status = status;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
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
        return "topic [id=" + id +
                ", titleId=" + titleId +
                ", titleName=" + titleName +
                ", teacherId=" + teacherId +
                ", status=" + status +
                ", remark=" + remark + "]";
    }
}
