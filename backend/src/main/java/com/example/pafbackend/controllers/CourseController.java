package com.example.pafbackend.controllers;

import com.example.pafbackend.models.Course;
import com.example.pafbackend.models.Lesson;
import com.example.pafbackend.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/creator/{creatorId}")
    public List<Course> getCoursesByCreator(@PathVariable String creatorId) {
        return courseService.getCoursesByCreator(creatorId);
    }

    @GetMapping("/topic/{topic}")
    public List<Course> getCoursesByTopic(@PathVariable String topic) {
        return courseService.getCoursesByTopic(topic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable String id, @RequestBody Course courseDetails) {
        Course updatedCourse = courseService.updateCourse(id, courseDetails);
        if (updatedCourse != null) {
            return ResponseEntity.ok(updatedCourse);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable String id) {
        if (courseService.deleteCourse(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<Course> addLessonToCourse(@PathVariable String courseId, @RequestBody Lesson lesson) {
        Course updatedCourse = courseService.addLessonToCourse(courseId, lesson);
        if (updatedCourse != null) {
            return ResponseEntity.ok(updatedCourse);
        }
        return ResponseEntity.notFound().build();
    }
}