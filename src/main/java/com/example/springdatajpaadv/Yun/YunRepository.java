package com.example.springdatajpaadv.Yun;

import com.example.springdatajpaadv.course.Course;
import com.example.springdatajpaadv.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YunRepository extends JpaRepository<Yun, Long> {


    @Query(value = "SELECT row_number() over () AS id,\n" +
            "       A.first_name || ' ' || A.last_name AS full_name,\n" +
            "       A.age AS age,\n" +
            "       A.email AS email,\n" +
            "       B.card_number AS card_number,\n" +
            "       C.name AS book_name,\n" +
            "       C.created_at AS created_at\n" +
            "FROM student A\n" +
            "         LEFT JOIN\n" +
            "     student_id_card B\n" +
            "     ON A.id = B.student_id\n" +
            "         LEFT JOIN\n" +
            "     book C\n" +
            "     ON A.id = C.student_id", nativeQuery = true)
    List<Yun> findYun();
}
