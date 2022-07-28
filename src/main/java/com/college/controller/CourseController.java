package com.college.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.college.model.Course;
import com.college.service.CourseService;

@RestController
public class CourseController {
	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/course/{page}/{size}")
	public ResponseEntity<Page<Course>> getAllCourse(@PathVariable(required = true) Optional<Integer> page,
			@PathVariable(required = true) Optional<Integer> size) {
		logger.info("Request for fetching course list");
		Page<Course> findAllCourse = courseService.findAllCourse(page, size);
		return new ResponseEntity<Page<Course>>(findAllCourse, HttpStatus.OK);
	}

	@GetMapping("/course/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable(required = true) Long id) {
		logger.info("Request for fetching course by id");
		Course courseById = courseService.getCourseById(id);
		return new ResponseEntity<Course>(courseById, HttpStatus.OK);
	}

	@PostMapping("/course")
	public ResponseEntity<Course> createCourse(@RequestBody Course course) {
		logger.info("Request for saving course");
		Course createCourse = courseService.createCourse(course);
		return new ResponseEntity<Course>(createCourse, HttpStatus.CREATED);
	}

	@PutMapping("/course/{id}")
	public ResponseEntity<Course> updateCourse(@PathVariable(required = true) Long id, @RequestBody Course course) {
		logger.info("Request for updating course by id");
		Course updateCourse = courseService.updateCourse(id, course);
		return new ResponseEntity<Course>(updateCourse, HttpStatus.OK);
	}

	@DeleteMapping("/course/{id}")
	public ResponseEntity<?> deleteCourseById(@PathVariable(required = true) Long id) {
		logger.info("Request for deleting course by id");
		courseService.deleteCourseById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
