package com.example.tripease.Service;

import com.example.tripease.DTO.Request.DriverRequest;
import com.example.tripease.DTO.Response.DriverResponse;
import com.example.tripease.Model.Booking;
import com.example.tripease.Model.Driver;
import com.example.tripease.Repository.BookingRepository;
import com.example.tripease.Repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.example.tripease.Transformers.DriverTransformer.driverRequestToDriver;
import static com.example.tripease.Transformers.DriverTransformer.driverToDriverResponse;


@Service
public class DriverService {
    @Autowired
    DriverRepository driverRepository;

    @Autowired
    BookingRepository bookingRepository;

    public DriverResponse addDriver(DriverRequest driverRequest) {
        Driver driver = driverRequestToDriver(driverRequest);
        return driverToDriverResponse(driverRepository.save(driver));
    }

    public double getTotalAmount(int driverid, Date date1, Date date2) {
        List<Booking> li=bookingRepository.findBookingsByDriverId(driverid,date1,date2);
        double totalAmount=0;
        for (Booking booking : li) {
            totalAmount += booking.getBillAmount();
        }
        return totalAmount;
    }
}
