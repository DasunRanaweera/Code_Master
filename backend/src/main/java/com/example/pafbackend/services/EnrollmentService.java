package com.example.pafbackend.services;

import com.example.pafbackend.models.Enrollment;
import com.example.pafbackend.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    public Enrollment enrollUserInCourse(String userId, String courseId) {
        Optional<Enrollment> existingEnrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
        
        if (existingEnrollment.isPresent()) {
            return existingEnrollment.get();
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(userId);
        enrollment.setCourseId(courseId);
        enrollment.setEnrolledAt(new Date());
        enrollment.setLastAccessedAt(new Date());
        enrollment.setStatus(Enrollment.EnrollmentStatus.ENROLLED);
        enrollment.setProgress(0);
        
        return enrollmentRepository.save(enrollment);
    }
    
    public List<Enrollment> getUserEnrollments(String userId) {
        return enrollmentRepository.findByUserId(userId);
    }
    
    public List<Enrollment> getCourseEnrollments(String courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }
    
    public Optional<Enrollment> getEnrollment(String id) {
        return enrollmentRepository.findById(id);
    }
    
    public Optional<Enrollment> getUserCourseEnrollment(String userId, String courseId) {
        return enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
    }
    
    public Enrollment updateEnrollmentProgress(String userId, String courseId, int progress) {
        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
        
        if (enrollmentOpt.isPresent()) {
            Enrollment enrollment = enrollmentOpt.get();
            enrollment.setProgress(progress);
            enrollment.setLastAccessedAt(new Date());
            
            if (progress >= 100) {
                enrollment.setStatus(Enrollment.EnrollmentStatus.COMPLETED);
            }
            
            return enrollmentRepository.save(enrollment);
        }
        
        return null;
    }
    
    public Enrollment updateEnrollmentStatus(String id, Enrollment.EnrollmentStatus status) {
        return enrollmentRepository.findById(id)
                .map(enrollment -> {
                    enrollment.setStatus(status);
                    enrollment.setLastAccessedAt(new Date());
                    return enrollmentRepository.save(enrollment);
                }).orElse(null);
    }
    
    public boolean deleteEnrollment(String id) {
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}