package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Entity.StudentDetails;
import com.example.Repository.StudentRepo;

@Service
public class StudentServiceImp implements StudentService {

	@Autowired
	private StudentRepo repo;
	@Autowired
	private EmailHelper emailHelper;

	@Override
	public String saveStudent(StudentDetails student) {
		// TODO Auto-generated method stub
		try {
			StudentDetails details = repo.save(student);
			emailHelper.processAndSendMail(details);
			return "Email Send Successfull";
		} catch (Exception e) {
			return "Email not Send.";
		}
	}

	@Override
	public List<StudentDetails> getAllStudent() {
		// TODO Auto-generated method stub
		System.out.println("Service Method Service Class....");
		return (List<StudentDetails>) repo.findAll();
	}

	@Override
	public StudentDetails getSingleStudent(Long id) {
		// TODO Auto-generated method stub
		Optional<StudentDetails> st = repo.findById(id);
		StudentDetails student = st.get();
		return student;
	}

	@Override
	public void DeleteStudent(Long id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
		
	}

	@Override
	public StudentDetails UpdateStudent(StudentDetails student) {
		// TODO Auto-generated method stub

		return repo.save(student);
	}

}
