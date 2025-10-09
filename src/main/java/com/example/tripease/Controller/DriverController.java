package com.example.tripease.Controller;

import com.example.tripease.DTO.Request.DriverRequest;
import com.example.tripease.DTO.Response.DriverResponse;
import com.example.tripease.Service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    DriverService driverService;

    @PostMapping("/add")
    public DriverResponse addDriver(@Valid @RequestBody DriverRequest driverRequest){
        return driverService.addDriver(driverRequest);
    }

    @GetMapping("totalamount/{driverid}/{date1}/{date2}")
    public Double getTotalAmount(@PathVariable("driverid") int driverid,
                                 @PathVariable("date1") @DateTimeFormat(pattern = "d-M-yyyy") Date date1,
                                 @PathVariable("date2") @DateTimeFormat(pattern = "d-M-yyyy") Date date2){
        return driverService.getTotalAmount(driverid,date1,date2);
    }
}
