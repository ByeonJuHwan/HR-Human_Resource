package com.sohwakmo.hr.repository;

import com.sohwakmo.hr.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrgRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByOrderByPartDepartmentAscPartTeamAscEmployeePositionDesc();

}
