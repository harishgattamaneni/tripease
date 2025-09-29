package com.example.tripease.Transformers;

import com.example.tripease.DTO.Request.BookingRequest;
import com.example.tripease.DTO.Response.BookingResponse;
import com.example.tripease.DTO.Response.CabResponse;
import com.example.tripease.DTO.Response.CustomerResponse;
import com.example.tripease.Model.Booking;
import com.example.tripease.Model.Cab;
import com.example.tripease.Model.Customer;
import com.example.tripease.Model.Driver;

import static com.example.tripease.Enum.TripStatus.Ongoing;

public class BookingTransformer {

    public static Booking bookingRequestToBooking(BookingRequest bookingRequest,double perKmInDistance){
        return Booking.builder()
                .pickup(bookingRequest.getPickup())
                .destination(bookingRequest.getDestination())
                .tripDistanceInKm(bookingRequest.getTripDistanceInKm())
                .tripStatus(Ongoing)
                .billAmount((bookingRequest.getTripDistanceInKm())*perKmInDistance)
                .build();
    }

    public static BookingResponse bookingToBookingResponse(Booking booking, Customer customer,
                                                           Cab cab, Driver driver){
        return BookingResponse.builder()
                .pickup(booking.getPickup())
                .destination(booking.getDestination())
                .tripDistanceInKm(booking.getTripDistanceInKm())
                .tripStatus(booking.getTripStatus())
                .billAmount(booking.getBillAmount())
                .bookedAt(booking.getBookedAt())
                .lastUpdatedAt(booking.getLastUpdatedAt())
                .cabResponse(CabTransformer.cabToCabResponse(cab,driver))
                .customerResponse(CustomerTransformer.customerToCutomerResponse(customer))
                .build();
    }
}
