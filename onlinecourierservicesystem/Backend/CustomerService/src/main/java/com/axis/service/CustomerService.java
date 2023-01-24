package com.axis.service;

import java.util.List;

import com.axis.model.Customer;

public interface CustomerService {
	
	public Customer addCustomer(Customer customer);
	public List<Customer> getAllCustomers();
	public Customer getCustomerById(int id);
	public Customer updateCustomer(int id,Customer customer);
	public String deleteCustomer(int id);

}
