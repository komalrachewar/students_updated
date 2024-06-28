package com.komal.students.controller;

import com.komal.students.dao.Student;
import com.komal.students.service.StudentService;
import com.komal.students.util.Countries;
import com.komal.students.util.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    Countries countries;

    @Autowired
    Courses courses;

    @RequestMapping("/list")
    public String getStudentList(Model model) {
        model.addAttribute("students", studentService.getAllData());
        return "studentList";
    }

    @RequestMapping("/addStudent")
    public String addStudent(Model model) {
        Student student = new Student();
        model.addAttribute("countries", countries.getCountries());
        model.addAttribute("courses", courses.getCourses());
        model.addAttribute("student", student);
        model.addAttribute("isUpdate", false);
        return "addUpdateStudent";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student, Model model) {
        studentService.saveData(student);
        model.addAttribute("message", "Student added successfully");
        model.addAttribute("alertClass", "alert-success");
        return "redirect:/list";
    }

    @RequestMapping("/updateStudent/{id}")
    public String updateStudent(@PathVariable("id") Long id, Model model) {
        Optional<Student> studentOPtional = studentService.getById(id);
        Student student = studentOPtional.orElse(new Student());
        model.addAttribute("student", student);
        model.addAttribute("isUpdate", true);
        model.addAttribute("countries", countries.getCountries());
        model.addAttribute("courses", courses.getCourses());
        return "addUpdateStudent";
    }

    @RequestMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") Long id, Model model){
        studentService.deleteById(id);
        model.addAttribute("message", "Student Deleted");
        return "redirect:/list";
    }
}
