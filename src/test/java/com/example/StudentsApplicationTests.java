package com.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.Entity.StudentDetails;
import com.example.Repository.StudentRepo;

@SpringBootTest
class StudentsApplicationTests {

	
	@Autowired
	private StudentRepo studentRepo;
//	@Test
	public void saveTest()
	{
		StudentDetails student=new StudentDetails();
//		student.setId(30L);
		student.setName(" Basha");
		student.setEmail("basha123@gmail.com");
		student.setAddress("Ndl");
		student.setAge(25);
		student.setPassword("basha123");
		studentRepo.save(student);
//		assertNotNull(studentRepo.findById(30L).get());
	}
	

}
