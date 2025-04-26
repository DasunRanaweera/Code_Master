package com.example.pafbackend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "enrollments")
public class Enrollment {
    @Id
    private String id;
    private String userId;
    private String courseId;
    private Date enrolledAt;
    private Date lastAccessedAt;
    private EnrollmentStatus status;
    private Integer progress;

    public enum EnrollmentStatus {
        ENROLLED, COMPLETED, DROPPED
    }

    public Enrollment() {
        this.enrolledAt = new Date();
        this.lastAccessedAt = new Date();
        this.status = EnrollmentStatus.ENROLLED;
        this.progress = 0;
    }

    public Enrollment(String id, String userId, String courseId) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.enrolledAt = new Date();
        this.lastAccessedAt = new Date();
        this.status = EnrollmentStatus.ENROLLED;
        this.progress = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Date getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(Date enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public Date getLastAccessedAt() {
        return lastAccessedAt;
    }

    public void setLastAccessedAt(Date lastAccessedAt) {
        this.lastAccessedAt = lastAccessedAt;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}