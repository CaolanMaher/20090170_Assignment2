package org.wit.a20090170_assignment2.main

import android.app.Application
import com.github.ajalt.timberkt.Timber
import org.wit.a20090170_assignment2.models.RentalCarModel
import timber.log.Timber.i

class MainApp : Application() {

    val rentalCars = ArrayList<RentalCarModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("App Started")
    }
}