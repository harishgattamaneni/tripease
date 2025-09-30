package com.example.tripease.Service;

import com.example.tripease.DTO.Request.CustomerRequest;
import com.example.tripease.DTO.Response.CustomerResponse;
import com.example.tripease.Enum.Gender;
import com.example.tripease.Enum.TripStatus;
import com.example.tripease.Exception.CustomerNotFoundException;
import com.example.tripease.Exception.DriverNotFoundException;
import com.example.tripease.Exception.tripAlreadyEnded;
import com.example.tripease.Model.Booking;
import com.example.tripease.Model.Cab;
import com.example.tripease.Model.Customer;
import com.example.tripease.Model.Driver;
import com.example.tripease.Repository.BookingRepository;
import com.example.tripease.Repository.CustomerRepository;
import com.example.tripease.Repository.DriverRepository;
import com.example.tripease.Transformers.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    DriverRepository driverRepository;


    public CustomerResponse getCustomer(int customerId) {
        Optional<Customer> optionalCustomer= customerRepository.findById(customerId);
        if(optionalCustomer.isEmpty()){
            throw new CustomerNotFoundException("Invalid customer id");
        }
        Customer customer = optionalCustomer.get();
        return CustomerTransformer.customerToCutomerResponse(customer);
    }

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {

        //save in the db
        Customer savedCustomer = customerRepository.save(CustomerTransformer.customerRequesToCustomer(customerRequest));
        return CustomerTransformer.customerToCutomerResponse(savedCustomer);
    }

    public List<CustomerResponse> getAllByGender(Gender gender) {
        List<Customer> customers = customerRepository.findByGender(gender);
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer : customers) {
            customerResponses.add(CustomerTransformer.customerToCutomerResponse(customer));
        }
        return customerResponses;
    }

    public List<CustomerResponse> getAllByGenderAndAge(Gender gender, int age) {
        List<Customer> customers = customerRepository.findByGenderAndAge(gender,age);
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer : customers) {
            customerResponses.add(CustomerTransformer.customerToCutomerResponse(customer));
        }
        return customerResponses;
    }

    public List<CustomerResponse> getAllByGenderAndAgeGreaterThan(Gender gender, int age) {
        List<Customer> customers = customerRepository.findByGenderAndAgeGreaterThan(gender,age);
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer : customers) {
            customerResponses.add(CustomerTransformer.customerToCutomerResponse(customer));
        }
        return customerResponses;
    }

    public CustomerResponse endTrip(int customerId) {
        Booking booking = bookingRepository.findLatestById(customerId);
        if(booking.getTripStatus()==TripStatus.Completed){
            throw new tripAlreadyEnded("Trip has Already ended for the current customer");
        }
        booking.setTripStatus(TripStatus.Completed);
        int driverId = bookingRepository.findDriverIdByBookingId(booking.getBookingId());
        Optional<Driver> optionalDriver = driverRepository.getDriverByDriverId(driverId);
        if(optionalDriver.isEmpty()){
            throw new DriverNotFoundException("driver not found");
        }
        Driver driver = optionalDriver.get();
        Cab cab= driver.getCab();
        cab.setAvailable(true);
        driverRepository.save(driver);
        bookingRepository.save(booking);
        return CustomerTransformer.customerToCutomerResponse(customerRepository.findByCustomerId(customerId));
    }
}
