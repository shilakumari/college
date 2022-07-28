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

import com.college.model.Student;
import com.college.service.StudentService;

@RestController
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

	@GetMapping("/student/{page}/{size}")
	public ResponseEntity<Page<Student>> getAllStudent(@PathVariable(required = true) Optional<Integer> page,
			@PathVariable(required = true) Optional<Integer> size) {
		logger.info("Request for fetching student list");
		Page<Student> findAllStudent = studentService.findAllStudent(page, size);
		return new ResponseEntity<Page<Student>>(findAllStudent, HttpStatus.OK);
	}

	@GetMapping("/student/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable(required = true) Long id) {
		logger.info("Request for fetching student by id");
		Student studentById = studentService.getStudentById(id);
		return new ResponseEntity<Student>(studentById, HttpStatus.OK);
	}

	@PostMapping("/student")
	public ResponseEntity<Student> createStudent(@RequestBody Student student) {
		logger.info("Request for saving student");
		Student createStudent = studentService.createStudent(student);
		return new ResponseEntity<Student>(createStudent, HttpStatus.CREATED);
	}

	@PutMapping("/student/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable(required = true) Long id, @RequestBody Student student) {
		logger.info("Request for updating student by id");
		Student updateStudent = studentService.updateStudent(id, student);
		return new ResponseEntity<Student>(updateStudent, HttpStatus.OK);
	}

	@DeleteMapping("/student/{id}")
	public ResponseEntity<?> deleteStudentById(@PathVariable(required = true) Long id) {
		logger.info("Request for deleting student by id");
		studentService.deleteStudentById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
