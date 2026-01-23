package com.example.parking.controller;

import com.example.parking.model.Booking;
import com.example.parking.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestParam String parkingSlotId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime,
            Authentication authentication) {

        String userId = authentication.getPrincipal().toString();

        Booking booking = bookingService.createBooking(userId, parkingSlotId, startTime, endTime);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<Booking> cancelBooking(
            @PathVariable String bookingId,
            Authentication authentication) {

        String userId = authentication.getPrincipal().toString();

        return ResponseEntity.ok(
                bookingService.cancelBooking(bookingId, userId)
        );
    }
}
