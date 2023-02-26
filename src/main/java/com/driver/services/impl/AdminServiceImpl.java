package com.driver.services.impl;

import java.util.List;

import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.AdminRepository;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository1;

	@Autowired
	DriverRepository driverRepository1;

	@Autowired
	CustomerRepository customerRepository1;

	@Override
	public void adminRegister(Admin admin) {
		//Save the admin in the database


		adminRepository1.save(admin);
	}

	@Override
	public Admin updatePassword(Integer adminId, String password) {
		//		//Update the password of admin with given id


		//First am fetching the details of the admin with admin id

		Admin admin = null;
		try {
			admin = adminRepository1.findById(adminId).get();
			if (admin == null) {
				throw new Exception("admin not found in the database.");
			}
			admin.setPassword(password);

			adminRepository1.save(admin);

		} catch (Exception e) {
			System.out.println("Admin Id is null");
		}

		return admin;

	}

	@Override
	public void deleteAdmin(int adminId){
		// Delete admin without using deleteById function

		Admin admin = adminRepository1.findById(adminId).get();

		adminRepository1.delete(admin);
	}

	@Override
	public List<Driver> getListOfDrivers() {
		//Find the list of all drivers


		// Here I created an abstract method List<Driver> findAll() in Driver repository
		List<Driver> driverList = driverRepository1.findAll();
		return driverList;
	}

	@Override
	public List<Customer> getListOfCustomers() {
		//Find the list of all customers

		List<Customer> customerList = customerRepository1.findAll();
		return customerList;
	}

}

