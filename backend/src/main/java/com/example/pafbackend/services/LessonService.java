package com.example.pafbackend.services;

import com.example.pafbackend.models.Lesson;
import com.example.pafbackend.repositories.CourseRepository;
import com.example.pafbackend.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Lesson createLesson(Lesson lesson) {
        lesson.setCreatedAt(new Date());
        lesson.setUpdatedAt(new Date());
        return lessonRepository.save(lesson);
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Optional<Lesson> getLessonById(String id) {
        return lessonRepository.findById(id);
    }

    public List<Lesson> getLessonsByCourse(String courseId) {
        return lessonRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
    }

    public Lesson updateLesson(String id, Lesson lessonDetails) {
        return lessonRepository.findById(id)
                .map(lesson -> {
                    lesson.setTitle(lessonDetails.getTitle());
                    lesson.setContent(lessonDetails.getContent());
                    lesson.setOrderIndex(lessonDetails.getOrderIndex());
                    lesson.setType(lessonDetails.getType());
                    lesson.setUpdatedAt(new Date());
                    return lessonRepository.save(lesson);
                }).orElse(null);
    }

    public boolean deleteLesson(String id) {
        if (lessonRepository.existsById(id)) {
            Optional<Lesson> lessonOpt = lessonRepository.findById(id);
            if (lessonOpt.isPresent()) {
                Lesson lesson = lessonOpt.get();
                String courseId = lesson.getCourseId();
                courseRepository.findById(courseId).ifPresent(course -> {
                    course.getLessons().removeIf(l -> l.getId().equals(id));
                    course.setUpdatedAt(new Date());
                    courseRepository.save(course);
                });
            }
            lessonRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void reorderLessons(String courseId, List<String> lessonIds) {
        for (int i = 0; i < lessonIds.size(); i++) {
            final int orderIndex = i + 1;
            String lessonId = lessonIds.get(i);
            lessonRepository.findById(lessonId).ifPresent(lesson -> {
                lesson.setOrderIndex(orderIndex);
                lesson.setUpdatedAt(new Date());
                lessonRepository.save(lesson);
            });
        }
    }
}
