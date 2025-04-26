package com.example.pafbackend.controllers;

import com.example.pafbackend.models.Enrollment;
import com.example.pafbackend.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping
    public Enrollment enrollUserInCourse(@RequestBody Map<String, String> enrollmentRequest) {
        String userId = enrollmentRequest.get("userId");
        String courseId = enrollmentRequest.get("courseId");
        return enrollmentService.enrollUserInCourse(userId, courseId);
    }

    @GetMapping("/user/{userId}")
    public List<Enrollment> getUserEnrollments(@PathVariable String userId) {
        return enrollmentService.getUserEnrollments(userId);
    }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> getCourseEnrollments(@PathVariable String courseId) {
        return enrollmentService.getCourseEnrollments(courseId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollment(@PathVariable String id) {
        return enrollmentService.getEnrollment(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}/course/{courseId}")
    public ResponseEntity<Enrollment> getUserCourseEnrollment(@PathVariable String userId, @PathVariable String courseId) {
        return enrollmentService.getUserCourseEnrollment(userId, courseId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/progress")
    public ResponseEntity<Enrollment> updateEnrollmentProgress(@RequestBody Map<String, Object> progressUpdate) {
        String userId = (String) progressUpdate.get("userId");
        String courseId = (String) progressUpdate.get("courseId");
        int progress = Integer.parseInt(progressUpdate.get("progress").toString());
        
        Enrollment updatedEnrollment = enrollmentService.updateEnrollmentProgress(userId, courseId, progress);
        if (updatedEnrollment != null) {
            return ResponseEntity.ok(updatedEnrollment);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Enrollment> updateEnrollmentStatus(@PathVariable String id, @RequestBody Map<String, String> statusUpdate) {
        Enrollment.EnrollmentStatus status = Enrollment.EnrollmentStatus.valueOf(statusUpdate.get("status"));
        Enrollment updatedEnrollment = enrollmentService.updateEnrollmentStatus(id, status);
        if (updatedEnrollment != null) {
            return ResponseEntity.ok(updatedEnrollment);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnrollment(@PathVariable String id) {
        if (enrollmentService.deleteEnrollment(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}