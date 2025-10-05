package com.example.tripease.Transformers;

import com.example.tripease.DTO.Request.CustomerRequest;
import com.example.tripease.DTO.Response.CustomerResponse;
import com.example.tripease.Model.Customer;

public class CustomerTransformer {
    //Transformers
    public static Customer customerRequesToCustomer(CustomerRequest customerRequest){
        return Customer.builder()
                .name(customerRequest.getName())
                .age(customerRequest.getAge())
                .emailId(customerRequest.getEmailId())
                .gender(customerRequest.getGender())
                .build();
    }
    //Transformers
    public static CustomerResponse customerToCutomerResponse(Customer customer){
        return CustomerResponse.builder()
                .name(customer.getName())
                .age(customer.getAge())
                .emailId(customer.getEmailId())
                .build();
    }
}
