package org.wit.a20090170_assignment2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.a20090170_assignment2.databinding.CardRentalCarBinding
import org.wit.a20090170_assignment2.models.RentalCarModel

class RentalCarAdapter constructor(private val rentalCars: List<RentalCarModel>) : RecyclerView.Adapter<RentalCarAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardRentalCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val rentalCar = rentalCars[holder.adapterPosition]
        holder.bind(rentalCar)
    }

    override fun getItemCount(): Int = rentalCars.size

    class MainHolder(private val binding : CardRentalCarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rentalCar: RentalCarModel) {
            binding.rentalCarBrand.text = rentalCar.brand
            binding.rentalCarYear.text = rentalCar.year.toString()
        }
    }
}