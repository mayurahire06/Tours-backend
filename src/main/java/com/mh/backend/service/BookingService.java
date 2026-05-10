package com.mh.backend.service;

import com.mh.backend.dto.BookingDTO;
import java.util.List;

public interface BookingService {

    /**
     * Creates a new booking with the provided details, including associated travelers.
     *
     * @param bookingDTO The DTO containing booking details (userId, tourId, members, bookingDate, totalPrice, transportation, travelers).
     * @return The created BookingDTO with the assigned ID and associated travelers.
     * @throws RuntimeException if the user or tour is not found, or if the number of travelers exceeds tour capacity.
     */
    BookingDTO createBooking(BookingDTO bookingDTO);

    /**
     * Retrieves a booking by its ID, including associated travelers.
     *
     * @param id The ID of the booking to retrieve.
     * @return The BookingDTO containing the booking details and associated travelers.
     * @throws RuntimeException if the booking is not found.
     */
    BookingDTO getBookingById(Long id);

    /**
     * Retrieves all bookings for a given user ID.
     *
     * @param userId The ID of the user whose bookings are to be retrieved.
     * @return A list of BookingDTOs for the specified user.
     */
    List<BookingDTO> getBookingsByUserId(Long userId);

    /**
     * Updates an existing booking with the provided details.
     *
     * @param id The ID of the booking to update.
     * @param bookingDTO The DTO containing updated booking details.
     * @return The updated BookingDTO.
     * @throws RuntimeException if the booking, user, or tour is not found, or if the number of travelers exceeds tour capacity.
     */
    BookingDTO updateBooking(Long id, BookingDTO bookingDTO);

    /**
     * Deletes a booking by its ID, including associated travelers.
     *
     * @param id The ID of the booking to delete.
     * @throws RuntimeException if the booking is not found.
     */
    void deleteBooking(Long id);

    List<BookingDTO> getAllBookings();
}
