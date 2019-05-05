package com.row49382.theater.data

import com.row49382.theater.domain.Booking
import com.row49382.theater.domain.Performance
import com.row49382.theater.domain.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository : JpaRepository<Seat, Long>
interface PerformanceRepository : JpaRepository<Performance, Long>
interface BookingRepository : JpaRepository<Booking, Long>