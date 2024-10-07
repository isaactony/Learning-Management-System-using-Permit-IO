package com.learningmanagementsystems.LMSWithPermitIO.repository;

import com.learningmanagementsystems.LMSWithPermitIO.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // You can add custom query methods here if needed, like findByName, etc.
}
