package com.sohwakmo.hr.repository;

import com.sohwakmo.hr.domain.PaymentState;
import com.sohwakmo.hr.domain.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Integer> {
    List<Vacation> findByEmployeeNo(String loginUser);

    // 진행중 and EmployeeNo List
    List<Vacation> findByEmployeeNoAndState(String no, PaymentState state);

    List<Vacation> findByEmployeeNoAndEffectiveDateContaining(String employeeNo, String formatedNow);
}
