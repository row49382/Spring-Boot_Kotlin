package com.row49382.theater.domain

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class Seat(
        @Id @GeneratedValue(strategy=GenerationType.AUTO)
        val id: Long,
        // used to escape hibernate ROW keyword
        @Column(name = "\"row\"")
        val row: Char,
        val num: Int,
        val price: BigDecimal,
        val description: String) {
    override fun toString(): String = "Seat $row-$num $$price ($description)"
}