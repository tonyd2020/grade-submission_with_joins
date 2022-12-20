package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Optional;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exceptions.CourseNotFoundException;
import com.ltp.gradesubmission.exceptions.GradeNotFoundException;
import com.ltp.gradesubmission.exceptions.StudentNotFoundException;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {


    GradeRepository gradeRepository;
    StudentRepository studentRepository;
    CourseRepository courseRepository;
    
    @Override
    public Grade getGrade(Long studentId, Long courseId) {
        Optional<Grade> grade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        if(grade.isPresent()){
            return grade.get();
        } else {
         throw new GradeNotFoundException(studentId, courseId);
        }
    }

    @Override
    public Grade saveGrade(Grade grade, Long studentId, Long courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if(student.isPresent()){
            grade.setStudent(student.get());
        } else {
            throw new StudentNotFoundException(studentId);
        }
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent()){
            grade.setCourse(course.get());;
        } else {
            throw new CourseNotFoundException(courseId);
        }


        return gradeRepository.save(grade);
    }

    @Override
    public Grade updateGrade(String score, Long studentId, Long courseId) {
        Optional<Grade> grade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        if(grade.isPresent()){
            Grade foundGrade = grade.get();
            foundGrade.setScore(score);
            return gradeRepository.save(foundGrade);
        } else {
            throw new GradeNotFoundException(studentId, courseId);
        }

    }

    @Override
    public void deleteGrade(Long studentId, Long courseId) {
        gradeRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public List<Grade> getStudentGrades(Long studentId) {
        return (List<Grade>) gradeRepository.findByStudentId(studentId);
    }

    @Override
    public List<Grade> getCourseGrades(Long courseId) {
        return gradeRepository.findByCourseId(courseId);
    }

    @Override
    public List<Grade> getAllGrades() {
        return (List<Grade>) gradeRepository.findAll();
    }

}
