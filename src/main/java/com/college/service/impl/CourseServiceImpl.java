package com.college.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.college.model.Course;
import com.college.repository.CourseRepository;
import com.college.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
	public static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

	@Autowired
	private CourseRepository courseRepository;

	@Override
	public Page<Course> findAllCourse(Optional<Integer> page, Optional<Integer> size) {
		logger.info("Querying db to get course list");
		Pageable firstPageWithTenCourses = PageRequest.of(page.isPresent() ? page.get() : 0,
				size.isPresent() ? size.get() : 10, Sort.by("name"));
		Page<Course> courses = courseRepository.findAll(firstPageWithTenCourses);
		return courses;
	}

	@Override
	public Course getCourseById(Long id) {
		logger.info("Querying db to get course by id");
		Course courseDb = null;
		Optional<Course> courseO = courseRepository.findById(id);
		if (courseO.isPresent()) {
			courseDb = courseO.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found for id: " + id);
		}
		return courseDb;
	}

	@Override
	public Course createCourse(Course course) {
		logger.info("Querying db to save course");
		Course newCourse = courseRepository.save(course);
		return newCourse;
	}

	@Override
	public Course updateCourse(Long id, Course course) {
		logger.info("Querying db to update course by id");
		Course courseDb = null;
		Optional<Course> courseO = courseRepository.findById(id);
		if (courseO.isPresent()) {
			courseDb = courseO.get();
		} else {
			throw new RuntimeException("Course not found for id: " + id);
		}
		if (null != course.getName()) {
			courseDb.setName(course.getName());
		}
		Course updatedCourse = courseRepository.save(courseDb);
		return updatedCourse;
	}

	@Override
	public void deleteCourseById(Long id) {
		logger.info("Querying db to delete course by id");
		Optional<Course> courseO = courseRepository.findById(id);
		if (courseO.isPresent()) {
			courseRepository.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found for id: " + id);
		}
	}
}
