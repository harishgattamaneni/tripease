package com.example.tripease.Service;

import com.example.tripease.DTO.DetailsDto;
import com.example.tripease.DTO.Request.BookingRequest;
import com.example.tripease.DTO.Response.BookingResponse;
import com.example.tripease.Enum.TripStatus;
import com.example.tripease.Exception.*;
import com.example.tripease.Model.Booking;
import com.example.tripease.Model.Cab;
import com.example.tripease.Model.Customer;
import com.example.tripease.Model.Driver;
import com.example.tripease.Repository.BookingRepository;
import com.example.tripease.Repository.CabRepository;
import com.example.tripease.Repository.CustomerRepository;
import com.example.tripease.Repository.DriverRepository;
import com.example.tripease.Transformers.BookingTransformer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.*;

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
    CustomerService customerService;

    @Autowired
    JavaMailSender javaMailSender;

    public BookingResponse bookCab(BookingRequest bookingRequest, int customerId) {
        Booking check_booking = bookingRepository.findLatestById(customerId);
        if(check_booking!=null && check_booking.getTripStatus()== TripStatus.Ongoing){
            throw new tripNotEnded("Trip for the specified customer has not yet ended");
        }
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
        String text = String.format("""
    Dear %s,

    Congratulations! Your cab has been successfully booked.

    📍 Trip Details:
       - Pickup Location: %s
       - Destination: %s
       - Distance: %.2f km
       - Estimated Bill Amount: ₹%.2f
       - Booking Time: %s

    🚖 Cab Details:
       - Cab Number: %s
       - Cab Model: %s
       - Per Km Rate: ₹%d

    👨‍✈️ Driver Details:
       - Driver ID: %d
       - Name: %s
       - Age: %d
       - Email: %s

    Thank you for choosing TripEase. We wish you a safe and pleasant journey!

    Regards,  
    TripEase Team
    """,
                bookingResponse.getCustomerResponse().getName(),
                bookingResponse.getPickup(),
                bookingResponse.getDestination(),
                bookingResponse.getTripDistanceInKm(),
                bookingResponse.getBillAmount(),
                bookingResponse.getBookedAt(),
                bookingResponse.getCabResponse().getCabNumber(),
                bookingResponse.getCabResponse().getCabModel(),
                bookingResponse.getCabResponse().getPerKmRate(),
                bookingResponse.getCabResponse().getDriverResponse().getDriverId(),
                bookingResponse.getCabResponse().getDriverResponse().getName(),
                bookingResponse.getCabResponse().getDriverResponse().getAge(),
                bookingResponse.getCabResponse().getDriverResponse().getEmailId()
        );

        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("benduapparao0@gmail.com");
        simpleMailMessage.setTo(bookingResponse.getCustomerResponse().getEmailId());
        simpleMailMessage.setSubject("Cab booked");
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);
    }

    public String cancelBooking(int bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if(optionalBooking.isEmpty()){
            throw new BookingNotFoundException("There is no booking found with given booking id");
        }
        Booking booking = optionalBooking.get();
        if(booking.getTripStatus()==TripStatus.Completed){
            throw new TripCannotBeCancelled("The booking has already ended");
        }
        booking.setTripStatus(TripStatus.Cancelled);
        int driver_id = bookingRepository.findByBookingId(booking.getBookingId());
        int cab_id = driverRepository.getCabId(driver_id);
        Cab cab = cabRepository.findById(cab_id).get();
        cab.setAvailable(true);
        cabRepository.save(cab);
        return "Booking successfully cancelled";
    }

    public Booking pickupNearest(@Valid BookingRequest bookingRequest) {
        List<Booking> bookings = bookingRepository.pickupNearest(bookingRequest.getPickup());
        Optional<Booking> nearestBooking = Optional.empty();
        for (Booking booking : bookings) {
            int driver_id = bookingRepository.findDriverIdByBookingId(booking.getBookingId());
            Optional<Driver> driver = driverRepository.findById(driver_id);
            if (driver.isPresent()) {
                nearestBooking = Optional.of(booking);
                break;
            }
        }
        if(nearestBooking.isEmpty()){
            throw new NoNearestBooking("there is no recent trip to this particular location");
        }
        return nearestBooking.get();
    }

    public List<DetailsDto> bookingDetails(int customerId) {
        return bookingRepository.getBookingDetails(customerId);
    }
}
