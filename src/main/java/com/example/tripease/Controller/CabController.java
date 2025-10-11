package com.example.tripease.Controller;

import com.example.tripease.DTO.PopularCabModelDto;
import com.example.tripease.DTO.RateRangeDto;
import com.example.tripease.DTO.Request.CabRequest;
import com.example.tripease.DTO.Response.CabResponse;
import com.example.tripease.Service.CabService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/cab")
public class CabController {
    @Autowired
    CabService cabService;

    @PostMapping("/register/driver/{driverId}")
    public CabResponse registerCab(@Valid @RequestBody CabRequest cabRequest,
                                   @PathVariable("driverId") int driverId){
        return cabService.registerCab(cabRequest,driverId);
    }

    @GetMapping("/popularCarModel/days/{days}")
    public ResponseEntity<PopularCabModelDto> getPopularCarModel(@PathVariable("days") int days){
        return new ResponseEntity<>(cabService.getPopularCarModel(days), HttpStatus.OK);
    }

    @GetMapping("/rate-range/{minRate}/{maxRate}")
    public ResponseEntity<List<RateRangeDto>> getRateRange(@PathVariable("minRate") int minRate, @PathVariable("maxRate") int maxRate){
        return new ResponseEntity<>(cabService.getRateRange(minRate,maxRate),HttpStatus.OK);
    }
}
