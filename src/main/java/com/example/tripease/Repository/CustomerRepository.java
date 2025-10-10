package com.example.tripease.Repository;

import com.example.tripease.DTO.CustomerLoyaltyScore;
import com.example.tripease.Enum.Gender;
import com.example.tripease.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    List<Customer> findByGender(Gender gender);

    Customer findByCustomerId(int CustomerId);

    List<Customer> findByGenderAndAge(Gender gender, int age);

    @Query("select c from Customer c where c.gender= :gender and c.age> :age")//HQL - hybernate quesry language
    List<Customer> findByGenderAndAgeGreaterThan(@Param("gender") Gender gender,
                                                 @Param("age") int age);

    @Query(value="select customer_info.customer_id," +
            "customer_info.first_name," +
            "avg(booking.trip_distance_in_km) as customer_avg_distance " +
            "from customer_info " +
            "join booking on customer_info.customer_id=booking.customer_id " +
            "where booking.trip_status='COMPLETED' " +
            "group by customer_info.customer_id,customer_info.first_name " +
            "having avg(booking.trip_distance_in_km)>(select avg(booking.trip_distance_in_km) from booking where trip_status='COMPLETED') " +
            "order by customer_avg_distance DESC",nativeQuery = true)
    List<CustomerLoyaltyScore> getCustomerLoyaltyScore();

    @Query(value = "select customer_info.first_name " +
            "from customer_info " +
            "left join booking on customer_info.customer_id=booking.customer_id " +
            "group by customer_info.first_name,customer_info.customer_id " +
            "having count(booking.booking_id)=0", nativeQuery = true)
    List<String> customersNeverBooked();

//    @Query(value = "select * from customer_info where gender= :gender and age> :age",
//            nativeQuery = true)//HQL - hybernate quesry language
//    List<Customer> findByGenderAndAgeGreaterThan(@Param("gender") String gender,
//                                                 @Param("age") int age);


}
