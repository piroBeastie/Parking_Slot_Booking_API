package com.example.parking.repository;

import com.example.parking.model.Booking;
import com.example.parking.model.BookingStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByUserId(String userId);

    List<Booking> findByParkingSlotIdAndStatus(
            String parkingSlotId,
            BookingStatus status
    );

    List<Booking> findByStatusAndStartTimeBetween(
            BookingStatus status,
            LocalDateTime from,
            LocalDateTime to
    );
}
