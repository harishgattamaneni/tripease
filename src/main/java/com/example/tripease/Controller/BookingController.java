package com.example.tripease.Controller;

import com.example.tripease.DTO.Request.BookingRequest;
import com.example.tripease.DTO.Response.BookingResponse;
import com.example.tripease.Service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/bookCab/customer/{customerId}")
    public BookingResponse bookCab(@Valid @RequestBody BookingRequest bookingRequest,
                                   @PathVariable("customerId") int customerId){
        return bookingService.bookCab(bookingRequest,customerId);
    }

    @PostMapping("/cancel-booking/bookingId/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable("bookingId") int bookingId){
        return new ResponseEntity<>(bookingService.cancelBooking(bookingId), HttpStatus.OK);
    }

}

