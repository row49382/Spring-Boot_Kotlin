package com.row49382.theater.services

import com.row49382.theater.data.PerformanceRepository
import com.row49382.theater.domain.Performance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class PerformanceService {

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    fun findPerformanceById(performanceId: Long): Performance {
        return performanceRepository.findById(performanceId).get()
    }
}