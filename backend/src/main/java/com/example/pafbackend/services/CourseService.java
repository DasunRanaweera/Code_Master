package com.example.pafbackend.services;

import com.example.pafbackend.models.Course;
import com.example.pafbackend.models.Lesson;
import com.example.pafbackend.repositories.CourseRepository;
import com.example.pafbackend.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private LessonRepository lessonRepository;
    
    public Course createCourse(Course course) {
        course.setCreatedAt(new Date());
        course.setUpdatedAt(new Date());
        return courseRepository.save(course);
    }
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Optional<Course> getCourseById(String id) {
        return courseRepository.findById(id);
    }
    
    public List<Course> getCoursesByCreator(String creatorId) {
        return courseRepository.findByCreatorId(creatorId);
    }
    
    public List<Course> getCoursesByTopic(String topic) {
        return courseRepository.findByTopicsContaining(topic);
    }
    
    public Course updateCourse(String id, Course courseDetails) {
        return courseRepository.findById(id)
                .map(course -> {
                    course.setTitle(courseDetails.getTitle());
                    course.setDescription(courseDetails.getDescription());
                    course.setTopics(courseDetails.getTopics());
                    course.setLearningObjectives(courseDetails.getLearningObjectives());
                    course.setUpdatedAt(new Date());
                    return courseRepository.save(course);
                }).orElse(null);
    }
    
    public boolean deleteCourse(String id) {
        if (courseRepository.existsById(id)) {
            // Delete all lessons associated with this course
            List<Lesson> lessons = lessonRepository.findByCourseId(id);
            lessonRepository.deleteAll(lessons);
            // Delete the course
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Course addLessonToCourse(String courseId, Lesson lesson) {
        return courseRepository.findById(courseId)
                .map(course -> {
                    lesson.setCourseId(courseId);
                    lesson.setCreatedAt(new Date());
                    lesson.setUpdatedAt(new Date());
                    
                    // Set the order index if not specified
                    if (lesson.getOrderIndex() <= 0) {
                        lesson.setOrderIndex(course.getLessons().size() + 1);
                    }
                    
                    Lesson savedLesson = lessonRepository.save(lesson);
                    course.getLessons().add(savedLesson);
                    course.setUpdatedAt(new Date());
                    return courseRepository.save(course);
                }).orElse(null);
    }
}