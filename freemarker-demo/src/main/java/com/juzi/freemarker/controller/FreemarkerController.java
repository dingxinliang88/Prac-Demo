package com.juzi.freemarker.controller;

import com.juzi.freemarker.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;


/**
 * @author codejuzi
 */
@Controller
public class FreemarkerController {

    @GetMapping("/stu")
    public String testStudentFtl(Model model) {

        // 设置值
        model.addAttribute("name", "Freemarker");

        Student student = new Student();
        student.setName("张三");
        student.setAge(18);

        model.addAttribute("stu", student);
        return "student-info";
    }

    @GetMapping("/lm")
    public String listAndMap(Model model) {

        // list
        Student stu1 = new Student();
        stu1.setName("Tom");
        stu1.setAge(18);
        stu1.setMoney(200.0);

        Student stu2 = new Student("Jerry", 19, 250.0);
        List<Student> studentList = new ArrayList<>() {{
            add(stu1);
            add(stu2);
        }};
        model.addAttribute("stuList", studentList);

        // map
        Map<String, Student> studentMap = new HashMap<>() {{
            put("stu1", stu1);
            put("stu2", stu2);
        }};
        model.addAttribute("stuMap", studentMap);

        return "list-map";
    }

    @GetMapping("/if")
    public String stuIf(Model model) {

        // list
        Student stu1 = new Student("Tom", 18, 200.8);

        Student stu2 = new Student("Jerry", 19, 250.0);
        List<Student> studentList = new ArrayList<>() {{
            add(stu1);
            add(stu2);
        }};
        model.addAttribute("stuList", studentList);

        return "stu-if";
    }

    @GetMapping("/cal")
    public String cal() {
        return "cal";
    }

    @GetMapping("/op")
    public String operation(Model model) {
        Date now = new Date();
        model.addAttribute("date1", now);
        model.addAttribute("date2", now);
        return "op";
    }

    @GetMapping("/logic")
    public String logic() {
        return "logic";
    }

    @GetMapping("/null")
    public String dealNull(Model model) {

        model.addAttribute("stuList", null);

        return "null-deal";
    }


    @GetMapping("/json")
    public String parseJson(Model model) {
        // list
        Student stu = new Student("Jerry", 19, 250.0);
        List<Student> studentList = new ArrayList<>() {{
            add(stu);
        }};
        model.addAttribute("stuList", studentList);

        Date date = new Date();
        model.addAttribute("today", date);
        // 3.1 添加数值
        model.addAttribute("point", 102920122);

        return "parse-json";
    }
}
