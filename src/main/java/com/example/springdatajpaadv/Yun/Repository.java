package com.example.springdatajpaadv.Yun;

import com.example.springdatajpaadv.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Repository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT row_number() over () AS id,\n" +
            "       A.first_name || ' ' || A.last_name AS fullName,\n" +
            "       A.age AS age,\n" +
            "       A.email AS email,\n" +
            "       B.card_number AS cardNumber,\n" +
            "       C.name AS bookName,\n" +
            "       C.created_at AS createdAt\n" +
            "FROM student A\n" +
            "         LEFT JOIN\n" +
            "     student_id_card B\n" +
            "     ON A.id = B.student_id\n" +
            "         LEFT JOIN\n" +
            "     book C\n" +
            "     ON A.id = C.student_id", nativeQuery = true)
    List<Total> findYun();
}
