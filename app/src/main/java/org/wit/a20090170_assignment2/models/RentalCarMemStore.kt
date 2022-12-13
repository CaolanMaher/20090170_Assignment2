package org.wit.a20090170_assignment2.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    // a simple local incrementing ID system
    return lastId++
}

class RentalCarMemStore : RentalCarStore {
    val rentalCars = ArrayList<RentalCarModel>()

    override fun findAll(): List<RentalCarModel> {
        return rentalCars
    }

    override fun create(rentalCar: RentalCarModel) {
        rentalCar.id = getId()
        rentalCars.add(rentalCar)
        logAll()
    }

    override fun update(rentalCar: RentalCarModel) {
        var foundRentalCar: RentalCarModel? = rentalCars.find{ r -> r.id == rentalCar.id }
        if(foundRentalCar != null) {
            foundRentalCar.brand = rentalCar.brand
            foundRentalCar.year = rentalCar.year
            foundRentalCar.image = rentalCar.image
            foundRentalCar.lat = rentalCar.lat
            foundRentalCar.lng = rentalCar.lng
            foundRentalCar.zoom = rentalCar.zoom
        }
    }

    override fun delete(rentalCar: RentalCarModel) {
        var foundRentalCar: RentalCarModel? = rentalCars.find{ r -> r.id == rentalCar.id }
        if(foundRentalCar != null) {
            rentalCars.remove(foundRentalCar)
        }
    }

    override fun getCarsForUser(userId: String): List<RentalCarModel> {
        TODO("Not yet implemented")
    }

    fun logAll() {
        rentalCars.forEach{ i("${it}") }
    }
}