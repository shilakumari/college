package com.college.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private Integer age;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "students_courses", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_c_id"))
	private Set<Course> courses = new HashSet<Course>();

	public Student() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public void addCourse(Course course) {
		this.courses.add(course);
		course.getStudents().add(this);
	}

	public void addCourses(Set<Course> courseSet) {
		this.courses.addAll(courseSet);
		for (Course course : courseSet) {
			course.getStudents().add(this);
		}
	}

	public void removeCourse(Long courseId) {
		Course course = this.courses.stream().filter(c -> c.getcId() == courseId).findFirst().orElse(null);
		if (course != null) {
			this.courses.remove(course);
			course.getStudents().remove(this);
		}
	}

	public void removeCourses(Set<Long> courseIdSet) {
		Set<Course> courseSet = this.courses.stream().filter(c -> courseIdSet.contains(c.getcId()))
				.collect(Collectors.toSet());
		if (courseSet != null && !courseSet.isEmpty()) {
			this.courses.removeAll(courseSet);
			for (Course course : courseSet) {
				course.getStudents().remove(this);
			}
		}
	}
}
