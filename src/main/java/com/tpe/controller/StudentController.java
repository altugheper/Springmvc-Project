package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller // http://127.0.0.1:8080/students //Controller oldugunu belirtiyorum
@RequestMapping("/students") // students endpointi ile gelen requestler için bu sınıfa bakılacağını belirtiyorum
// Studentsla Gelen requestleri bu classin icinde methodlarla maple
public class StudentController {

    @Autowired
    private StudentService studentService; // field injection

    @GetMapping("/hi") // students a geldikten sonra students in icinde hi varsa asagidakini uygula
    public ModelAndView sayHi() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message","Merhaba");
        mav.addObject("messagebody","Ben bir Öğrenci Yönetim Sistemiyim");
        mav.setViewName("hi"); // WEB-INF/views/hi.jsp
        return mav;
    }

    @GetMapping("/new") // students/new
    public String sendStudentForm(@ModelAttribute("student") Student student){
        return "studentForm"; // @ModelAttribute = Modele student isminde bir attribute ekledik
    }

    @PostMapping("/saveStudent") // students/saveStudent
    public String createStudent(@ModelAttribute Student student){
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping
    public ModelAndView getStudents(){
        List<Student> list = studentService.getAllStudent();
        ModelAndView mav = new ModelAndView();
        mav.addObject("students",list);
        mav.addObject("students");
        return mav;
    }

    // http://localhost:8080/springmvc/students/update?id=1
    @GetMapping("/update")
    public String showFormForUpdate(@RequestParam("id")Long id, Model model){
        Student student = studentService.findStudentById(id);
        model.addAttribute(student);
        return "studentForm"; // StudentForm.jsp sayfasini client tarafina gonderiyorum
    }

    // http://localhost:8080/springmvc/students/delete/1
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id){ // Tek parametre ise @PathVariable, birden fazla parametre ise @RequestParam
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
    }

