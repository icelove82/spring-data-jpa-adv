package com.example.springdatajpaadv;

import com.example.springdatajpaadv.student.Student;
import com.example.springdatajpaadv.student.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringDataJpaAdvApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaAdvApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            studentRepository.saveAll(
                    List.of(
                            Student.builder()
                                    .firstName("Maria")
                                    .lastName("Jones")
                                    .email("maria.jones@amigoscode.edu")
                                    .age(21)
                                    .build(),
                            Student.builder()
                                    .firstName("Maria")
                                    .lastName("Jones")
                                    .email("maria2.jones@amigoscode.edu")
                                    .age(25)
                                    .build(),
                            Student.builder()
                                    .firstName("Ahmed")
                                    .lastName("Ali")
                                    .email("ahmed.ali@amigoscode.edu")
                                    .age(19)
                                    .build()

                    ));

            studentRepository
                    .findByEmail("ahmed.ali@amigoscode.edu")
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("Student with Email: 'ahmed.ali@amigoscode.edu' not found")
            );

            /* Normal JPA */
            System.out.println("1. Normal JPA");
            studentRepository
                    .findByFirstNameAndAgeGreaterThanEqual("Maria", 21)
                    .forEach(System.out::println);

            /* Custom JPQL */
            System.out.println("2. Custom JPQL");
            studentRepository
                    .findByCustomJPQL("Maria", 21)
                    .forEach(System.out::println);

            /* Native SQL */
            System.out.println("3. Native SQL");
            studentRepository
                    .findByNativeSQL("Maria", 21)
                    .forEach(System.out::println);

            /* Native SQL Param */
            System.out.println("4. Native SQL Param");
            studentRepository
                    .findByNativeParams(21, "Maria")
                    .forEach(System.out::println);

            System.out.println("Delete one");
            System.out.println(
                    studentRepository.deleteOneById(2L)
            );
        };
    }

}
