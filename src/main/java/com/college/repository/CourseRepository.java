package com.college.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.college.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
