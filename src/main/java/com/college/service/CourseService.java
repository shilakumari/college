package com.college.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.college.model.Course;

public interface CourseService {

	Page<Course> findAllCourse(Optional<Integer> page, Optional<Integer> size);

	Course getCourseById(Long id);

	Course createCourse(Course course);

	Course updateCourse(Long id, Course course);

	void deleteCourseById(Long id);
	
}
