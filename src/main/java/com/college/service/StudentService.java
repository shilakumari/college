package com.college.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.college.model.Student;

public interface StudentService {

	Page<Student> findAllStudent(Optional<Integer> page, Optional<Integer> size);

	Student createStudent(Student student);

	Student getStudentById(Long id);

	Student updateStudent(Long id, Student student);

	void deleteStudentById(Long id);

}
