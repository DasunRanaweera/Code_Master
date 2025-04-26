package com.example.pafbackend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "courses")
public class Course {
    @Id
    private String id;
    private String title;
    private String description;
    private String creatorId;
    private List<String> topics;
    private List<String> learningObjectives;
    private List<Lesson> lessons;
    private Date createdAt;
    private Date updatedAt;

    public Course() {
        this.lessons = new ArrayList<>();
        this.topics = new ArrayList<>();
        this.learningObjectives = new ArrayList<>();
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Course(String id, String title, String description, String creatorId, 
                  List<String> topics, List<String> learningObjectives, List<Lesson> lessons) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.topics = topics;
        this.learningObjectives = learningObjectives;
        this.lessons = lessons;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getLearningObjectives() {
        return learningObjectives;
    }

    public void setLearningObjectives(List<String> learningObjectives) {
        this.learningObjectives = learningObjectives;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}