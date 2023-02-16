package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;

public class StuDist extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private SysDept dept;

    private Long studentId;

    private SysUser student;

    private Long teacherId;

    private SysUser teacher;

    public StuDist() {
    }

    public StuDist(Long id, Long studentId, SysUser student, Long teacherId, SysUser teacher) {
        this.id = id;
        this.studentId = studentId;
        this.student = student;
        this.teacherId = teacherId;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public SysUser getStudent() {
        return student;
    }

    public void setStudent(SysUser student) {
        this.student = student;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public SysUser getTeacher() {
        return teacher;
    }

    public void setTeacher(SysUser teacher) {
        this.teacher = teacher;
    }


    @Override
    public String toString() {
        return "StuDist{" +
                "id=" + id +
                ", dept=" + dept +
                ", studentId=" + studentId +
                ", student=" + student +
                ", teacherId=" + teacherId +
                ", teacher=" + teacher +
                '}';
    }

}
