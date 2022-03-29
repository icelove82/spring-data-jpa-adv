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
                                    .firstName("Ahmed")
                                    .lastName("Ali")
                                    .email("ahmed.ali@amigoscode.edu")
                                    .age(19)
                                    .build()

                    ));
            System.out.println("Adding Maria and Ahmed");

            System.out.print("Number of students: ");
            System.out.println(studentRepository.count());

            studentRepository
                    .findById(2L)
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("Student with ID 2 not found")
            );

            studentRepository
                    .findById(3L)
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("Student with ID 3 not found")
            );

            System.out.println("Select all students");
            studentRepository
                    .findAll()
                    .forEach(System.out::println);

            System.out.println("Delete Maria");
            studentRepository.deleteById(1L);

            System.out.print("Number of students: ");
            System.out.println(studentRepository.count());
        };
    }

}
