package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Collection<Student> findByAge(int age);
    Collection <Student> findByAgeBetween (int max, int min);
    @Query(value = "select count(*) from student s", nativeQuery = true)
    Integer amountOfStudents ();
    @Query (value = "SELECT round(avg(age),2) FROM student s",nativeQuery = true)
    Double averageAge();
    @Query (value = "select * from student s order by id desc limit 5",nativeQuery = true)
    List<Student> getLastStudents();

}
