package org.wit.a20090170_assignment2.models

import java.time.LocalDate

data class RentalCarModel(
    //var id: Long = 0,
    var brand: String = "",
    var year: Int = 0,
    var registration: String = "1-A-1111",
    var rate: Double = 0.0,
    var isAvailable: String = "N",
    var dateRented: LocalDate = LocalDate.parse("1111-11-11"),
    var dateReturn: LocalDate = LocalDate.parse("1111-11-11"),
    var fuelSource: String = ""
)