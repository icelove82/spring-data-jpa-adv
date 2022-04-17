package com.example.springdatajpaadv.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);

    List<Student> findByFirstNameAndAgeGreaterThanEqual(String firstName, Integer age);

    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
    List<Student> findByCustomJPQL(String firstName, Integer age);

    @Query(value = "SELECT * FROM student WHERE first_name  = ?1 AND age >= ?2", nativeQuery = true)
    List<Student> findByNativeSQL(String firstName, Integer age);

    @Query(value = "SELECT * FROM student WHERE first_name  = :name AND age >= :age", nativeQuery = true)
    List<Student> findByNativeParams(
            @Param("age") Integer age,
            @Param("name") String firstName
    );

    @Transactional
    @Modifying
    @Query("DELETE FROM Student s WHERE s.id = ?1")
    int deleteOneById(Long id);
}
