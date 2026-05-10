package com.mh.backend.service.impl;

import com.mh.backend.dto.BookingDTO;
import com.mh.backend.dto.TravelerDTO;
import com.mh.backend.entity.Booking;
import com.mh.backend.entity.Tour;
import com.mh.backend.entity.Traveler;
import com.mh.backend.entity.User;
import com.mh.backend.repository.BookingRepository;
import com.mh.backend.repository.TourRepository;
import com.mh.backend.repository.TravelerRepository;
import com.mh.backend.repository.UserRepository;
import com.mh.backend.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TravelerRepository travelerRepository;

    @Override
    @Transactional
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        // Fetch User
        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + bookingDTO.getUserId()));

        // Fetch Tour
        Tour tour = tourRepository.findById(bookingDTO.getTourId())
                .orElseThrow(() -> new RuntimeException("Tour not found with ID: " + bookingDTO.getTourId()));

        // Validate capacity
        if (bookingDTO.getMembers() > tour.getCapacity()) {
            throw new RuntimeException("Number of travelers exceeds tour capacity: " + tour.getCapacity());
        }

        // Validate members count with travelers list
        if (bookingDTO.getTravelers() != null &&
                bookingDTO.getTravelers().size() != bookingDTO.getMembers()) {
            throw new RuntimeException("Number of traveler details must match members");
        }

        // Create booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTour(tour);
        booking.setMembers(bookingDTO.getMembers());
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setTransportation(bookingDTO.getTransportation());

        // Apply discount if no transportation
        double finalPrice = bookingDTO.getTotalPrice();
        if (bookingDTO.getTransportation() == null) {
            finalPrice = bookingDTO.getTotalPrice() * 0.9; // 10% discount
            System.out.println(">>> Discount applied. Original: " + bookingDTO.getTotalPrice() + " Final: " + finalPrice);
        }

        booking.setTotalPrice(finalPrice);

        // Save booking
        Booking savedBooking = bookingRepository.save(booking);

        // Save travelers
        if (bookingDTO.getTravelers() != null) {
            for (TravelerDTO travelerDTO : bookingDTO.getTravelers()) {
                Traveler traveler = new Traveler();
                traveler.setName(travelerDTO.getName());
                traveler.setAge(travelerDTO.getAge());
                traveler.setGender(travelerDTO.getGender());
                savedBooking.addTraveler(traveler);
                travelerRepository.save(traveler);
            }
            bookingRepository.save(savedBooking); // update with travelers
        }

        return convertToDTO(savedBooking);
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
        return convertToDTO(booking);
    }

    @Override
    public List<BookingDTO> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + bookingDTO.getUserId()));

        Tour tour = tourRepository.findById(bookingDTO.getTourId())
                .orElseThrow(() -> new RuntimeException("Tour not found with ID: " + bookingDTO.getTourId()));

        // Validate number of travelers against tour capacity
        if (bookingDTO.getMembers() > tour.getCapacity()) {
            throw new RuntimeException("Number of travelers exceeds tour capacity: " + tour.getCapacity());
        }

        booking.setUser(user);
        booking.setTour(tour);
        booking.setMembers(bookingDTO.getMembers());
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setTotalPrice(bookingDTO.getTotalPrice());
        booking.setTransportation(bookingDTO.getTransportation());

        Booking updatedBooking = bookingRepository.save(booking);
        return convertToDTO(updatedBooking);
    }

    @Override
    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
        bookingRepository.delete(booking); // Cascading will delete travelers
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        // Fetch all bookings from the repository
        List<Booking> bookings = bookingRepository.findAll();

        // Convert each booking entity to BookingDTO
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BookingDTO convertToDTO(Booking booking) {

        Tour tour = booking.getTour(); // 👈 IMPORTANT

        List<TravelerDTO> travelerDTOs = booking.getTravelers().stream()
                .map(traveler -> new TravelerDTO(
                        traveler.getId(),
                        traveler.getName(),
                        traveler.getAge(),
                        traveler.getGender(),
                        traveler.getBooking().getId()
                ))
                .collect(Collectors.toList());

        return new BookingDTO(
                booking.getId(),
                booking.getUser().getId(),
                tour.getId(),
                booking.getMembers(),
                booking.getBookingDate(),
                booking.getTotalPrice(),
                booking.getTransportation(),
                travelerDTOs

        );
    }


    //    private BookingDTO convertToDTO(Booking booking) {
    //        List<TravelerDTO> travelerDTOs = booking.getTravelers().stream()
    //                .map(traveler -> new TravelerDTO(
    //                        traveler.getId(),
    //                        traveler.getName(),
    //                        traveler.getAge(),
    //                        traveler.getGender(),
    //                        traveler.getBooking().getId()
    //                ))
    //                .collect(Collectors.toList());
    //
    //        return new BookingDTO(
    //                booking.getId(),
    //                booking.getUser().getId(),
    //                booking.getTour().getId(),
    //                booking.getMembers(),
    //                booking.getBookingDate(),
    //                booking.getTotalPrice(),
    //                booking.getTransportation(),
    //                travelerDTOs
    //        );
    //    }
}

//@Service
//public class BookingServiceImpl implements BookingService {
//
//    private final BookingRepository bookingRepository;
//    private final UserRepository userRepository;
//
//    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository) {
//        this.bookingRepository = bookingRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public Booking createBooking(Long userId, BookingRequest bookingRequest) {
//        // Fetch user by ID
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        Booking booking = new Booking();
//        booking.setUser(user);
//        booking.setMembers(bookingRequest.getMembers());
//        booking.setBookingDate(bookingRequest.getBookingDate());
//
//        return bookingRepository.save(booking);
//    }
//
//
//    @Override
//    public List<Booking> getAllBookings() {
//        return bookingRepository.findAll();
//    }
//
//    @Override
//    public Optional<Booking> getBookingById(Long id) {
//        return bookingRepository.findById(id);
//    }
//
//    @Override
//    public List<Booking> getBookingsByUserId(Long userId) {
//        return bookingRepository.findByUserId(userId);
//    }
//
//    @Override
//    public Booking updateBooking(Long id, Booking updatedBooking) {
//        return bookingRepository.findById(id).map(booking -> {
//            booking.setUser(updatedBooking.getUser());
//            booking.setMembers(updatedBooking.getMembers());
//            booking.setBookingDate(updatedBooking.getBookingDate());
//            return bookingRepository.save(booking);
//        }).orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
//    }
//
//    @Override
//    public void deleteBooking(Long id) {
//        bookingRepository.deleteById(id);
//    }
//}
