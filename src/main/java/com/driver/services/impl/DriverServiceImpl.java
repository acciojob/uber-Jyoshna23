package com.driver.services.impl;

import com.driver.model.Cab;
import com.driver.repository.CabRepository;
import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Driver;
import com.driver.repository.DriverRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepository3;

	@Autowired
	CabRepository cabRepository3;

	@Override
	public void register(String mobile, String password){
		//Save a driver in the database having given details and a cab with ratePerKm as 10 and availability as True by default.
		Cab cab = new Cab();
		cab.setPerKmRate(10);
		cab.setAvailable(true);

		Driver driver = new Driver();
		driver.setCab(cab);

		List<Driver> driverList = new ArrayList<>();
		driverList.add(driver);

		driverRepository3.save(driver);
	}

	@Override
	public void removeDriver(int driverId){
		// Delete driver without using deleteById function
		Driver driver = driverRepository3.findById(driverId).get();
		driverRepository3.delete(driver);

	}

	@Override
	public void updateStatus(int driverId){
		//Set the status of respective car to unavailable


		//---Here I am fetching the details of the driver with driver id
		Driver driver = driverRepository3.findById(driverId).get();

		//Here I am fetching the cab details of that particular driver
		Cab cab = driver.getCab();

		//Setting its availability to false
		cab.setAvailable(false);
	}
}
