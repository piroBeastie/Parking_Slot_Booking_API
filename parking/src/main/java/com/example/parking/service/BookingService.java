package com.example.parking.service;

import com.example.parking.model.Booking;
import com.example.parking.model.BookingStatus;
import com.example.parking.model.ParkingSlot;
import com.example.parking.model.User;
import com.example.parking.repository.BookingRepository;
import com.example.parking.repository.ParkingSlotRepository;
import com.example.parking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ParkingSlotRepository parkingSlotRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, ParkingSlotRepository parkingSlotRepository, EmailService emailService, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.parkingSlotRepository = parkingSlotRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public Booking createBooking(String userId, String parkingSlotId, LocalDateTime startTime, LocalDateTime endTime){
        if(startTime.isAfter(endTime)|| startTime.isEqual(endTime)){
            throw new RuntimeException("Invalid booking time range");
        }

        ParkingSlot slot = parkingSlotRepository.findById(parkingSlotId)
                .orElseThrow(()-> new RuntimeException("Parking Slot not found"));

        if(!slot.isAvailable()){
            throw new RuntimeException("Parking Slot not available");
        }

        long minutes = Duration.between(startTime, endTime).toMinutes();
        long hours = (long) Math.ceil(minutes / 60.0);
        if (hours == 0) hours = 1;

        double totalPrice = hours * slot.getPricePerHour();

        slot.setAvailable(false);
        parkingSlotRepository.save(slot);

        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setParkingSlotId(parkingSlotId);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.BOOKED);

        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));

        emailService.sendEmail(
                user.getEmail(),
                "Parking Slot Booked",
                "Your booking is confirmed.\nSlot: " + slot.getSlotNumber() +
                        "\nFrom: " + startTime +
                        "\nTo: " + endTime +
                        "\nTotal: ₹" + totalPrice
        );

        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(String bookingId, String userId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()-> new RuntimeException("Booking not found"));

        if(!booking.getUserId().equals(userId)){
            throw new RuntimeException("You are not allowed to cancel this booking");
        }

        if (booking.getStatus() != BookingStatus.BOOKED) {
            throw new RuntimeException("Booking cannot be cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        ParkingSlot slot = parkingSlotRepository.findById(booking.getParkingSlotId())
                .orElseThrow(() -> new RuntimeException("Parking slot not found"));

        slot.setAvailable(true);
        parkingSlotRepository.save(slot);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        emailService.sendEmail(
                user.getEmail(),
                "Booking Cancelled",
                "Your booking has been cancelled.\nBooking ID: " + bookingId
        );

        return bookingRepository.save(booking);
    }
}
