package com.bank.authservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleTestController {

    @GetMapping("/api/v1/customer/dashboard")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String customerDashboard() {
        return "Welcome to customer";
    }

    @GetMapping("/api/v1/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "Welcome to admin";
    }

    @GetMapping("/api/v1/employee/dashboard")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public String employeeDashboard() {
        return "Welcome to employee";
    }
}
