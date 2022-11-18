package org.wit.a20090170_assignment2.models

interface RentalCarStore {
    fun findAll(): List<RentalCarModel>
    fun create(rentalCar: RentalCarModel)
    fun update(rentalCar: RentalCarModel)
}