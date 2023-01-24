package com.axis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axis.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}