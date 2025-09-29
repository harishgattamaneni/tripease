package com.example.tripease.Service;

import com.example.tripease.DTO.Request.BookingRequest;
import com.example.tripease.DTO.Response.BookingResponse;
import com.example.tripease.Exception.CabNotFoundException;
import com.example.tripease.Exception.CustomerNotFoundException;
import com.example.tripease.Model.Booking;
import com.example.tripease.Model.Cab;
import com.example.tripease.Model.Customer;
import com.example.tripease.Model.Driver;
import com.example.tripease.Repository.BookingRepository;
import com.example.tripease.Repository.CabRepository;
import com.example.tripease.Repository.CustomerRepository;
import com.example.tripease.Repository.DriverRepository;
import com.example.tripease.Transformers.BookingTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CabRepository cabRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public BookingResponse bookCab(BookingRequest bookingRequest, int customerId) {
        Optional<Customer> optionalCustomer= customerRepository.findById(customerId);
        if(optionalCustomer.isEmpty()){
            throw new CustomerNotFoundException("Invalid CustomerId");
        }
        Customer customer = optionalCustomer.get();
        Cab availableCab = cabRepository.getAvailableCabRandomly();
        if(availableCab==null){
            throw new CabNotFoundException("No Cab is available");
        }
        //change if needed
        Driver availableDriver = driverRepository.getDriverByCabId(availableCab.getCabId());
        Booking booking = BookingTransformer.bookingRequestToBooking(bookingRequest,availableCab.getPerKmRate());
        availableCab.setAvailable(false);
        Booking savedBooking = bookingRepository.save(booking);
        customer.getBookings().add(savedBooking);
        availableDriver.getBookings().add(savedBooking);

        Customer savedCustomer = customerRepository.save(customer);
        Driver savedDriver = driverRepository.save(availableDriver);
        BookingResponse bookingResponse = BookingTransformer.bookingToBookingResponse(savedBooking,savedCustomer,availableCab,savedDriver);
        sendEmail(bookingResponse);
        return bookingResponse;

    }
    private void sendEmail(BookingResponse bookingResponse){
        String text = "Congrats"+bookingResponse.getCustomerResponse().getName()+"/n you cab is booked and your cab details are here"+bookingResponse.getCabResponse();
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("benduapparao0@gmail.com");
        simpleMailMessage.setTo(bookingResponse.getCustomerResponse().getEmailId());
        simpleMailMessage.setSubject("Cab booked");
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);
    }
}
