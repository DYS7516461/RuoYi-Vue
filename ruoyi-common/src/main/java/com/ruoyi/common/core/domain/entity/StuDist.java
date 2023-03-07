package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;

public class StuDist extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long deptId;

    private String deptName;

    private Long studentId;

    private String studentName;

    public SysUser getStudent() {
        return student;
    }

    public void setStudent(SysUser student) {
        this.student = student;
    }

    private SysUser student;

    private Long teacherId;

    private String teacherName;

    public SysUser getTeacher() {
        return teacher;
    }

    public void setTeacher(SysUser teacher) {
        this.teacher = teacher;
    }

    private SysUser teacher;

    public StuDist() {
    }

    public StuDist(Long id, Long deptId, String deptName, Long studentId, String studentName, Long teacherId, String teacherName) {
        this.id = id;
        this.deptId = deptId;
        this.deptName = deptName;
        this.studentId = studentId;
        this.studentName = studentName;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }


    @Override
    public String toString() {
        return "StuDist{" +
                "id=" + id +
                ", deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
