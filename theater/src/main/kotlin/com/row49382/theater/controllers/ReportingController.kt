package com.row49382.theater.controllers

import com.row49382.theater.services.ReportingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import javax.websocket.server.PathParam
import kotlin.reflect.full.declaredMemberFunctions

@Controller
@RequestMapping("/reports")
class ReportingController {

    @Autowired
    lateinit var reportingService: ReportingService

    private fun getListOfReports(): List<String> {
        return reportingService::class.declaredMemberFunctions.map { it.name }
    }

    @RequestMapping("")
    fun main(): ModelAndView {
        return ModelAndView("reports", mapOf("reports" to getListOfReports()))
    }

    @RequestMapping("/getReport")
    fun getReports(@PathParam("report") report: String): ModelAndView {
        val matchedReport = reportingService::class.declaredMemberFunctions.firstOrNull { it.name == report }
        val result = matchedReport?.call(reportingService) ?: ""

        return ModelAndView("reports", mapOf("reports" to getListOfReports(), "result" to result))
    }
}