package com.college.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.college.model.Course;
import com.college.model.Student;
import com.college.repository.CourseRepository;
import com.college.repository.StudentRepository;

@RestController
public class StudentCourseController {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@PostMapping("/student/{studentId}/course")
	public ResponseEntity<Course> addCourse(@PathVariable(value = "studentId") Long studentId,
			@RequestBody Course courseRequest) throws Exception {
		Course courseDb = null;
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new Exception("Not found Student with id = " + studentId));
		Long courseId = courseRequest.getcId();
		if (courseId != null) {
			try {
				Optional<Course> courseO = courseRepository.findById(courseId);
				if (courseO.isPresent()) {
					courseDb = courseO.get();
				} else {
					new Exception("Not found Course with id = " + courseId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			student.addCourse(courseDb);
			studentRepository.save(student);
		}
		student.addCourse(courseRequest);
		courseDb = courseRepository.save(courseRequest);

		return new ResponseEntity<>(courseDb, HttpStatus.OK);
	}

	@PostMapping("/student/{studentId}/courses")
	public ResponseEntity<String> addCourses(@PathVariable(value = "studentId") Long studentId,
			@RequestBody Set<Course> courseRequestSet) throws Exception {
		Course courseDb = null;
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new Exception("Not found Student with id = " + studentId));
		Set<Course> courseDbSet = new HashSet<>();
		Set<Course> newCourseSet = new HashSet<>();
		for (Course courseRequest : courseRequestSet) {
			Long courseId = courseRequest.getcId();
			if (courseId != null) {
				try {
					Optional<Course> courseO = courseRepository.findById(courseId);
					if (courseO.isPresent()) {
						courseDb = courseO.get();
						courseDbSet.add(courseDb);
					} else {
						new Exception("Not found Course with id = " + courseId);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				newCourseSet.add(courseRequest);
			}
		}
		if (!courseDbSet.isEmpty()) {
			student.addCourses(courseDbSet);
			studentRepository.save(student);
		}
		student.addCourses(newCourseSet);
		courseRepository.saveAll(newCourseSet);
		return new ResponseEntity<>("Successfully Mapped", HttpStatus.OK);
	}

	@DeleteMapping("/students/{studentId}/courses/{courseId}")
	public ResponseEntity<HttpStatus> deleteCourseFromStudent(@PathVariable(value = "studentId") Long studentId,
			@PathVariable(value = "courseId") Long courseId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new RuntimeException("Not found Student with id = " + studentId));
		student.removeCourse(courseId);
		studentRepository.save(student);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/students/{studentId}/courses")
	public ResponseEntity<HttpStatus> deleteCoursesFromStudent(@PathVariable(value = "studentId") Long studentId,
			@RequestBody Set<Long> courseIdSet) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new RuntimeException("Not found Student with id = " + studentId));
		student.removeCourses(courseIdSet);
		studentRepository.save(student);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
