package com.college.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.college.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
