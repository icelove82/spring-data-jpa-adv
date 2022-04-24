package com.example.springdatajpaadv;

import com.example.springdatajpaadv.Yun.YunRepository;
import com.example.springdatajpaadv.book.Book;
import com.example.springdatajpaadv.book.BookRepository;
import com.example.springdatajpaadv.card.StudentIdCard;
import com.example.springdatajpaadv.card.StudentIdCardRepository;
import com.example.springdatajpaadv.course.Course;
import com.example.springdatajpaadv.enrolment.Enrolment;
import com.example.springdatajpaadv.enrolment.EnrolmentId;
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

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringDataJpaAdvApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaAdvApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository studentRepository,
            StudentIdCardRepository cardRepository,
            BookRepository bookRepository,
            YunRepository yunRepository) {

        return args -> {

            /*

            // 1
            basicJpaAction(studentRepository);

            // 2
            sortPagingJpaAction(studentRepository);

            // 3
            OneToOneAction(studentRepository, cardRepository);

            // 4
            OneToManyAction(studentRepository, bookRepository);

            // 5
            ManyToManyAction(studentRepository);

            */

            ManyToManyAction(studentRepository);

            yunRepository.findYun()
                    .forEach(System.out::println);

        };
    }

    private void ManyToManyAction(StudentRepository studentRepository) {
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
        Integer age = faker.number().numberBetween(17, 55);

        Student s = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        // book1
        Book b1 = Book.builder()
                .name("Clean Code")
                .createAt(LocalDateTime.now())
                .student(s)
                .build();

        // book2
        Book b2 = Book.builder()
                .name("Think and Grow Rich")
                .createAt(LocalDateTime.now().minusDays(3))
                .student(s)
                .build();

        // book3
        Book b3 = Book.builder()
                .name("Spring Data JPA")
                .createAt(LocalDateTime.now().minusYears(1))
                .student(s)
                .build();

        s.setBooks(List.of(b1, b2, b3));

        s.setStudentIdCard(StudentIdCard.builder()
                .cardNumber("777777777")
                .student(s)
                .build());

        // enrolment1
        s.addEnrolment(
                Enrolment.builder()
                        .id(EnrolmentId.builder()
                                .studentId(1L)
                                .courseId(1L)
                                .build())
                        .student(s)
                        .course(Course.builder()
                                .name("C++")
                                .department("IT")
                                .build())
                        .createAt(LocalDateTime.now())
                        .build());

        // enrolment2
        s.addEnrolment(
                Enrolment.builder()
                        .id(EnrolmentId.builder()
                                .studentId(1L)
                                .courseId(2L)
                                .build())
                        .student(s)
                        .course(
                                Course.builder()
                                        .name("Java")
                                        .department("IT")
                                        .build())
                        .createAt(LocalDateTime.now().minusDays(18))
                        .build());

        studentRepository.save(
                s
        );
    }

    private void OneToManyAction(StudentRepository studentRepository, BookRepository bookRepository) {
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
        Integer age = faker.number().numberBetween(17, 55);

        Student s = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        // book1
        Book b1 = Book.builder()
                .name("Clean Code")
                .createAt(LocalDateTime.now())
                .student(s)
                .build();

        // book2
        Book b2 = Book.builder()
                .name("Think and Grow Rich")
                .createAt(LocalDateTime.now().minusDays(3))
                .student(s)
                .build();

        // book3
        Book b3 = Book.builder()
                .name("Spring Data JPA")
                .createAt(LocalDateTime.now().minusYears(1))
                .student(s)
                .build();

        s.setBooks(List.of(b1, b2, b3));

        s.setStudentIdCard(StudentIdCard.builder()
                .cardNumber("888888888")
                .student(s)
                .build());

        studentRepository.save(
                s
        );

        bookRepository.save(
                Book.builder()
                        .name("YUN")
                        .createAt(LocalDateTime.now())
                        .student(
                                Student.builder()
                                        .firstName("Yun")
                                        .lastName("Myeonghun")
                                        .email("yun.myeonghun@gmail.com")
                                        .age(40)
                                        .build()
                        )
                        .build()
        );

        studentRepository
                .findAll()
                .forEach(
                        student -> {
                            student.getBooks().forEach(book -> {
                                System.out.println(student.getFirstName() + " -- " + book.getName());
                            });
                        }
                );
    }

    private void OneToOneAction(StudentRepository studentRepository, StudentIdCardRepository cardRepository) {
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
