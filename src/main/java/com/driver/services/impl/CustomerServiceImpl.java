package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database

		List<Customer> customerList = new ArrayList<>();
		customerList.add(customer);

		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer = customerRepository2.findById(customerId).get();

		customerRepository2.delete(customer);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query

		TripBooking tripBooking = new TripBooking();

		//Here I am fetching the driverlist, so that I can check which driver has lowest driverId
		List<Driver> driverList = driverRepository2.findAll();

		//checking for lowest driverid
		int minDriverId = Integer.MAX_VALUE;
		for(Driver driver : driverList){
			if(driver.getDriverId() < minDriverId){
				minDriverId = driver.getDriverId();
			}
		}

		//Setting the driver to the trip
		for(Driver driver : driverList){
			if(driver.getDriverId() == minDriverId){
				tripBooking.setDriver(driver);
				List<TripBooking> tripBookingList = driver.getTripBookingList();
				tripBookingList.add(tripBooking);
			}
		}


		Cab cab = tripBooking.getDriver().getCab();
		if(!cab.getAvailable()){
			throw new Exception("No cab Available");
		}

		List<Customer> customerList = customerRepository2.findAll();
		for(Customer customer : customerList){
			if(customer.getCustomerId() == customerId){
				List<TripBooking> tripBookingList = customer.getTripBookingList();
				tripBookingList.add(tripBooking);
			}
		}

		tripBookingRepository2.save(tripBooking);


		return tripBooking;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly

	}
}
