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

import com.college.model.Student;
import com.college.repository.StudentRepository;
import com.college.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	public static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public Page<Student> findAllStudent(Optional<Integer> page, Optional<Integer> size) {
		logger.info("Querying db to get student list");
		Pageable firstPageWithTenStudents = PageRequest.of(page.isPresent() ? page.get() : 0,
				size.isPresent() ? size.get() : 10, Sort.by("name"));
		Page<Student> students = studentRepository.findAll(firstPageWithTenStudents);
		return students;
	}

	@Override
	public Student getStudentById(Long id) {
		logger.info("Querying db to get student by id");
		Student studentDb = null;
		Optional<Student> studentO = studentRepository.findById(id);
		if (studentO.isPresent()) {
			studentDb = studentO.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found for id: " + id);
		}
		return studentDb;
	}

	@Override
	public Student createStudent(Student student) {
		logger.info("Querying db to save student");
		Student newStudent = studentRepository.save(student);
		return newStudent;
	}

	@Override
	public Student updateStudent(Long id, Student student) {
		logger.info("Querying db to update student by id");
		Student studentDb = null;
		Optional<Student> studentO = studentRepository.findById(id);
		if (studentO.isPresent()) {
			studentDb = studentO.get();
		} else {
			throw new RuntimeException("Student not found for id: " + id);
		}
		if (null != student.getAge()) {
			studentDb.setAge(student.getAge());
		}
		if (null != student.getName()) {
			studentDb.setName(student.getName());
		}
		Student updatedStudent = studentRepository.save(studentDb);
		return updatedStudent;
	}

	@Override
	public void deleteStudentById(Long id) {
		logger.info("Querying db to delete student by id");
		Optional<Student> studentO = studentRepository.findById(id);
		if (studentO.isPresent()) {
			studentRepository.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found for id: "+id);
		}
	}

}
