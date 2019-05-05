package com.row49382.theater.services

import com.row49382.theater.data.BookingRepository
import com.row49382.theater.data.SeatRepository
import com.row49382.theater.domain.Booking
import com.row49382.theater.domain.Performance
import com.row49382.theater.domain.Seat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookingService() {

    @Autowired
    lateinit var bookingRepository: BookingRepository

    @Autowired
    lateinit var seatRepository: SeatRepository

    fun isSeatFree(seat: Seat, performance: Performance) : Boolean {
        val doesBookingExist = bookingRepository.findAll().any { it.seat == seat && it.performance == performance }
        return !doesBookingExist
    }

    fun findSeat(row: Char, num: Int): Seat? {
        return seatRepository.findAll().firstOrNull { it.num == num && it.row == row }
    }

    fun reserveSeat(seat: Seat, performance: Performance, customerName: String): Booking {
        val bookingToReserve = Booking(0, customerName)
        bookingToReserve.seat = seat
        bookingToReserve.performance = performance

        bookingRepository.save(bookingToReserve)

        return bookingToReserve
    }

    fun findBooking(selectedSeat: Seat, selectedPerformance: Performance): Booking? {
        return bookingRepository.findAll().firstOrNull { it.seat == selectedSeat && it.performance == selectedPerformance }
    }
}