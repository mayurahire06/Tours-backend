package com.mh.backend.controller;

import com.mh.backend.dto.BookingDTO;
import com.mh.backend.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
        return ResponseEntity.ok(createdBooking);
    }

//    @GetMapping
//    public ResponseEntity<BookingDTO> getBookingById() {
//        List<BookingDTO> bookingDTO = bookingService.getAllBookings();
//        return ResponseEntity.ok(bookingDTO);
//    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        BookingDTO booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByUserId(@PathVariable Long userId) {
        List<BookingDTO> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {
        BookingDTO updatedBooking = bookingService.updateBooking(id, bookingDTO);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}

//@RestController
//@RequestMapping("/api/bookings")
//public class BookingController {
//
//    private final BookingService bookingService;
//
//    public BookingController(BookingService bookingService) {
//        this.bookingService = bookingService;
//    }
//
//    // Create a new booking
//    @PostMapping("/user/{userId}")
//    public ResponseEntity<?> createBooking(@PathVariable Long userId, @RequestBody BookingRequest bookingRequest) {
//        try {
//            Booking savedBooking = bookingService.createBooking(userId, bookingRequest);
//            return ResponseEntity.ok(savedBooking);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // Get all bookings
//    @GetMapping
//    public ResponseEntity<List<Booking>> getAllBookings() {
//        return ResponseEntity.ok(bookingService.getAllBookings());
//    }
//
//    // Get booking by id
//    @GetMapping("/{id}")
//    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
//        return bookingService.getBookingById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // Get bookings by user id
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long userId) {
//        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
//    }
//
//    // Update booking
//    @PutMapping("/{id}")
//    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
//        Booking updatedBooking = bookingService.updateBooking(id, booking);
//        return ResponseEntity.ok(updatedBooking);
//    }
//
//    // Delete booking
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
//        bookingService.deleteBooking(id);
//        return ResponseEntity.noContent().build();
//    }
//}
