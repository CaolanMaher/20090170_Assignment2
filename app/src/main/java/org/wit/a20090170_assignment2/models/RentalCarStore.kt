package org.wit.a20090170_assignment2.models

interface RentalCarStore {
    fun findAll(): List<RentalCarModel>
    fun create(rentalCar: RentalCarModel)
    fun update(rentalCar: RentalCarModel)
    fun delete(rentalCar: RentalCarModel)
    fun getCarsForUser(userId: String): List<RentalCarModel>
}