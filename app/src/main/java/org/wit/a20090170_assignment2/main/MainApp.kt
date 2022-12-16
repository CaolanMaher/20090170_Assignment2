package org.wit.a20090170_assignment2.main

import android.app.Application
import com.github.ajalt.timberkt.Timber
import org.wit.a20090170_assignment2.models.*
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var rentalCars: RentalCarStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        rentalCars = RentalCarFireStore()


        i("App Started")
    }
}