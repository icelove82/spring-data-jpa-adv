package com.example.springdatajpaadv.book;

import com.example.springdatajpaadv.student.Student;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Book")
@Table(name = "book")
public class Book {

    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String name;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createAt;

    /* Relation m:1 default fetch LAZY */
    @ManyToOne(
            cascade = CascadeType.ALL,
            /* LAZY - query with no join Student, but select when call Book.getStudent() */
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "student_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "student_id_fk"
            )
    )
    @ToString.Exclude
    private Student student;
}
