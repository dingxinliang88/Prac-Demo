package com.juzi.freemarker;

import com.juzi.freemarker.model.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author codejuzi
 */
@SpringBootTest
public class FreemarkerTest {

    @Resource
    private Configuration configuration;

    @Test
    public void testFreemarker() throws IOException, TemplateException {
        Template template = configuration.getTemplate("list-map.ftl");
        Map<String, Object> data = getData();
        String path = "/Users/codejuzi/Documents/CodeWorkSpace/Practice/Prac-Demo/freemarker-demo/src/main/resources/student-info.html";
        template.process(data, new FileWriter(path));
    }

    public Map<String, Object> getData() {
        Map<String, Object> map = new HashMap<>();

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
        map.put("stuList", studentList);

        // map
        Map<String, Student> studentMap = new HashMap<>() {{
            put("stu1", stu1);
            put("stu2", stu2);
        }};
        map.put("stuMap", studentMap);

        return map;
    }
}
