package com.example.demo.student;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class StudentServiice {
    private final StudentRepository studentRepository;
    @Autowired
    public StudentServiice( StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
	public List<Student> getStudents(){
		return studentRepository.findAll();
    }
    public void postStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if( studentOptional.isPresent()) {
            throw new IllegalStateException("Email taken");
        }
        studentRepository.save(student);

        }
    public void deleteStudent(Long id) {
        if(studentRepository.existsById(id)){
            studentRepository.deleteById(id);

        }
        else{
            throw new IllegalStateException("Student with id does not exist");
        }
    }
    @Transactional
    public void updateStudent(Long id, String name, String email) {
       Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalStateException( "student with id does not exist"));
       if(name != null && name.length() > 0 && !Objects.equals(student.getName() , name)){
        student.setName(name);
       }
       if(email != null && email.length() > 0 && !Objects.equals(student.getEmail() , email)){
        Optional <Student> studeOptional = studentRepository.findStudentByEmail(email);
        if(studeOptional.isPresent()){
            throw new IllegalStateException("Duplicated emal address");
        }
        student.setEmail(email);
       }

    }
    }

    


