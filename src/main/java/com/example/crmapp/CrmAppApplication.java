package com.example.crmapp;

import com.example.crmapp.model.CourseCategory;
import com.example.crmapp.model.Tag;
import com.example.crmapp.repository.CourseCategoryRepository;
import com.example.crmapp.repository.TagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrmAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrmAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(CourseCategoryRepository categoryRepository,
                                      TagRepository tagRepository) {
        return args -> {
            System.out.println("--- LOADING TEST DATA ---");

            // Create Categories
            CourseCategory cat1 = new CourseCategory();
            cat1.setName("Programming");
            categoryRepository.save(cat1);

            CourseCategory cat2 = new CourseCategory();
            cat2.setName("Design");
            categoryRepository.save(cat2);

            Tag tag1 = new Tag();
            tag1.setName("Java");
            tagRepository.save(tag1);

            Tag tag2 = new Tag();
            tag2.setName("Spring");
            tagRepository.save(tag2);

            Tag tag3 = new Tag();
            tag3.setName("UX");
            tagRepository.save(tag3);

            System.out.println("--- TEST DATA LOADED ---");
        };
    }
}
