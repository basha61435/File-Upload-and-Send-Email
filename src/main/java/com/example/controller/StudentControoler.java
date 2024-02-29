package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Entity.StudentDetails;
import com.example.service.StudentService;

@RestController
public class StudentControoler {
	@Autowired
	private StudentService studentService;

//	@GetMapping("/")
//	public String home() {
//		return "index.html";
//	}

	@PostMapping("/saveStudentDetails")
	public StudentDetails saveStudent(@RequestBody StudentDetails student) {
		return studentService.saveStudent(student);
	}

	@GetMapping("/getAllStudent")
	public List<StudentDetails> getAllStudentsDetails() {
		System.out.println("Service Method from Controller....");
		return (List<StudentDetails>) studentService.getAllStudent();
	}

	@GetMapping("/getSingleStudent/{id}")
	public StudentDetails getStudentDetails(@PathVariable Long id) {
		return studentService.getSingleStudent(id);
	}

	@DeleteMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable Long id) {
		studentService.DeleteStudent(id);
		return "Recoard is Deleted";
	}

	@PatchMapping("/updateStudent/{id}")
	public StudentDetails updateStudent(@RequestBody StudentDetails student, @PathVariable Long id) {
		StudentDetails student1 = studentService.getSingleStudent(id);
		student1.setAddress(student.getAddress());
		student1.setAge(student.getAge());
		return studentService.UpdateStudent(student1);
	}
}
