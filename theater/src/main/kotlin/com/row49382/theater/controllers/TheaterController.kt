package com.row49382.theater.controllers

import com.row49382.theater.data.PerformanceRepository
import com.row49382.theater.data.SeatRepository
import com.row49382.theater.domain.Booking
import com.row49382.theater.domain.Performance
import com.row49382.theater.domain.Seat
import com.row49382.theater.services.BookingService
import com.row49382.theater.services.PerformanceService
import com.row49382.theater.services.TheaterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class TheaterController {

    @Autowired
    lateinit var theaterService: TheaterService

    @Autowired
    lateinit var bookingService: BookingService

    @Autowired
    lateinit var performanceService: PerformanceService

    @Autowired
    lateinit var seatRepository: SeatRepository

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @RequestMapping("seatBooking")
    fun homePage() : ModelAndView {
        val model = mapOf(
            "bean" to CheckAvailabilityBackingBean(),
            "performances" to performanceRepository.findAll(),
            "seatNums" to 1..36,
            "seatRows" to 'A'..'O')

        return ModelAndView("seatBooking", model)
    }

    @RequestMapping(value="checkAvailability", method=arrayOf(RequestMethod.POST))
    fun checkAvailability(bean: CheckAvailabilityBackingBean) : ModelAndView {
        val selectedSeat: Seat = bookingService.findSeat(bean.selectedSeatRow, bean.selectedSeatNum)!!
        val selectedPerformance = performanceService.findPerformanceById(bean.selectedPerformance!!)
        val result = bookingService.isSeatFree(selectedSeat, selectedPerformance)

        bean.performance = selectedPerformance
        bean.seat = selectedSeat
        bean.available = result

        if (!result) {
            bean.booking = bookingService.findBooking(selectedSeat, selectedPerformance)
        }

        val model = mapOf(
                "bean" to bean,
                "performances" to performanceRepository.findAll(),
                "seatNums" to 1..36,
                "seatRows" to 'A'..'O')

        return ModelAndView("seatBooking", model)
    }

    @RequestMapping("bootstrap")
    fun createInitialData(): ModelAndView {
        // create the data and save to db
        val seats = theaterService.seats
        seatRepository.saveAll(seats)

        return homePage()
    }

    @RequestMapping("/booking", method = arrayOf(RequestMethod.POST))
    fun bookerSeat(bean: CheckAvailabilityBackingBean) : ModelAndView {
        val booking: Booking = bookingService.reserveSeat(bean.seat!!, bean.performance!!, bean.customerName)
        return ModelAndView("bookingConfirmed", "booking", booking)
    }

    @RequestMapping("/booking")
    fun bookerSeatRedirect(): ModelAndView {
        return ModelAndView("redirect:/seatBooking")
    }
}

class CheckAvailabilityBackingBean {
    var selectedSeatNum: Int = 1
    var selectedSeatRow: Char = 'A'
    var customerName: String = ""
    var selectedPerformance: Long? = null
    var available: Boolean? = null
    var seat: Seat? = null
    var performance: Performance? = null
    var booking: Booking? = null
}