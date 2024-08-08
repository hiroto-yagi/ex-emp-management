package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;

/**
 * 従業員情報一覧を全件検索用Service.
 */
@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * 従業員情報一覧を全件検索.
     * @return 従業員情報検索結果
     */
    public List<Employee> showList() {
        return employeeRepository.findAll();
    }

    /**
     * 個人の従業員情報を取得.
     * @param id 従業員ID
     * @return 従業員情報
     */
    public Employee showDetail(Integer id) {
        return employeeRepository.load(id);
    }
}
