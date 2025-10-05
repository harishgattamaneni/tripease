package com.example.tripease.Controller;

import com.example.tripease.DTO.Request.CustomerRequest;
import com.example.tripease.DTO.Response.CustomerResponse;
import com.example.tripease.Enum.Gender;
import com.example.tripease.Service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/customer")
public class CustomerController {
    Logger log= LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @PostMapping("/endTrip/CustomerId/{customerId}")
    public ResponseEntity<CustomerResponse> endTrip(@PathVariable("customerId") int customerId){
        return new ResponseEntity<>(customerService.endTrip(customerId),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerResponse> addCustomer(@Valid @RequestBody CustomerRequest customerRequest){
        return new ResponseEntity<>(customerService.addCustomer(customerRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get/customer-id/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id") int customerId){
        return new ResponseEntity<>(customerService.getCustomer(customerId),HttpStatus.OK);
    }

    @GetMapping("/get/gender/{gender}")
    public List<CustomerResponse> getAllByGender(@PathVariable("gender") Gender gender){
        return customerService.getAllByGender(gender);
    }

    @GetMapping("/get/gender/{gender}/age/{age}")
    public List<CustomerResponse> getAllByGenderAndAge(@PathVariable("gender") Gender gender,
                                                       @PathVariable("age") int age){
        return customerService.getAllByGenderAndAge(gender,age);
    }

    @GetMapping("/get-by-age-greater-than/gender/{gender}/age/{age}")
    public List<CustomerResponse> getAllByGenderAndAgeGreaterThan(@PathVariable("gender") Gender gender,
                                                       @PathVariable("age") int age){
        return customerService.getAllByGenderAndAgeGreaterThan(gender,age);
    }
}
