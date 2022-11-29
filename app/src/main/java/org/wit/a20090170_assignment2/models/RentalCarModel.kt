package org.wit.a20090170_assignment2.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class RentalCarModel(
    var id: Long = 0,
    var brand: String = "",
    var year: Int = 0,
    var registration: String = "1-A-1111",
    var rate: Double = 0.0,
    var isAvailable: String = "N",
    var dateRented: LocalDate = LocalDate.parse("1111-11-11"),
    var dateReturn: LocalDate = LocalDate.parse("1111-11-11"),
    var fuelSource: String = "",
    var image: Uri = Uri.EMPTY,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable