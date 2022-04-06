package com.example.springdatajpaadv.student;

import com.example.springdatajpaadv.book.Book;
import com.example.springdatajpaadv.card.StudentIdCard;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Student")
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_email_uk",
                        columnNames = "email"
                )
        }
)
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "age",
            nullable = false
    )
    private Integer age;

    /* BiDirection relationship 1:1 */
    @OneToOne(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private StudentIdCard studentIdCard;

    /* BiDirection relationship 1:m */
    @OneToMany(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            /* LAZY - query with no join Book, but select when call Student.getBooks() */
            fetch = FetchType.EAGER
    )
    private List<Book> books;
}