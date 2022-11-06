package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.EmloyeeMapper;
import com.itheima.domain.Employee;
import com.itheima.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmloyeeMapper, Employee> implements EmployeeService {
}
