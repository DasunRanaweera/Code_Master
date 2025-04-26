package com.example.pafbackend.repositories;

import com.example.pafbackend.models.Lesson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends MongoRepository<Lesson, String> {
    List<Lesson> findByCourseId(String courseId);
    List<Lesson> findByCourseIdOrderByOrderIndexAsc(String courseId);
}