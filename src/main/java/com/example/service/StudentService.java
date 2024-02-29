package com.example.service;

import java.util.List;

import com.example.Entity.StudentDetails;

public interface StudentService {
	public StudentDetails saveStudent(StudentDetails student);

	public List<StudentDetails> getAllStudent();

	public StudentDetails getSingleStudent(Long id);

	public void DeleteStudent(Long id);

	public StudentDetails UpdateStudent(StudentDetails student);
	

}
