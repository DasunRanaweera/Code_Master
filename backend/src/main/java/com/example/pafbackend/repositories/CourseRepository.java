package com.example.pafbackend.repositories;

import com.example.pafbackend.models.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    List<Course> findByCreatorId(String creatorId);
    List<Course> findByTopicsContaining(String topic);
}