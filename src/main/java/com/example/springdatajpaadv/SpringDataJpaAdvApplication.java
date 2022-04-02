package com.example.springdatajpaadv;

import com.example.springdatajpaadv.card.StudentIdCard;
import com.example.springdatajpaadv.card.StudentIdCardRepository;
import com.example.springdatajpaadv.student.Student;
import com.example.springdatajpaadv.student.StudentRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootApplication
public class SpringDataJpaAdvApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaAdvApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository studentRepository,
            StudentIdCardRepository cardRepository) {

        return args -> {
            // 1
            basicJpaAction(studentRepository);

            // 2
            sortPagingJpaAction(studentRepository);

            // 3
            Faker faker = new Faker();

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            Integer age = faker.number().numberBetween(17, 55);

            cardRepository.save(
                    StudentIdCard.builder()
                            .cardNumber("123456789")
                            .student(Student.builder()
                                    .firstName(firstName)
                                    .lastName(lastName)
                                    .email(email)
                                    .age(age)
                                    .build())
                            .build()
            );

            cardRepository.findById(1L)
                    .ifPresent(System.out::println);

            studentRepository.findById(1L)
                    .ifPresent(System.out::println);

            studentRepository.deleteById(24L);

        };
    }

    private void sortPagingJpaAction(StudentRepository studentRepository) {
        /* Fake data for test */
        createRandomData(studentRepository);

        /* Sorting */
        Sort sort = Sort.by("firstName").ascending()
                .and(Sort.by("age").descending());

        studentRepository
                .findAll(sort)
                .forEach(s -> System.out.println(s.getFirstName() + " " + s.getAge()));

        /* Paging */
        PageRequest pageRequest = PageRequest.of(
                1,
                5,
                sort
        );

        Page<Student> page = studentRepository
                .findAll(pageRequest);
        page.forEach(System.out::println);
    }

    private void basicJpaAction(StudentRepository studentRepository) {
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
    }

    private void createRandomData(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            Integer age = faker.number().numberBetween(17, 55);

            studentRepository.save(
                    Student.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .email(email)
                            .age(age)
                            .build()
            );
        }
    }

}
