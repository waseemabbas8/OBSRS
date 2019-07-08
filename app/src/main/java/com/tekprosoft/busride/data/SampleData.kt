package com.tekprosoft.busride.data

import com.tekprosoft.busride.model.Seat

class SampleData {

    companion object {

        fun getCities(): List<String> {
            val cities = arrayListOf<String>()
            cities.add("Karachi")
            cities.add("Lahore")
            cities.add("Faisalabad")
            cities.add("Rawalpindi")
            cities.add("Gujranwala")
            cities.add("Peshawar")
            cities.add("Multan")
            cities.add("Hyderabad")
            cities.add("Islamabad")
            cities.add("Quetta")
            cities.add("Bahawalpur")
            cities.add("Sargodha")
            cities.add("Sialkot")
            cities.add("Sukkur")
            cities.add("Larkana")
            cities.add("Sheikhupura")
            cities.add("Rahim Yar Khan")
            cities.add("Jhang")
            cities.add("Dera Ghazi Khan")
            cities.add("Gujrat")
            cities.add("Sahiwal")
            cities.add("Mardan")
            cities.add("Kasur")
            cities.add("Okara")
            cities.add("Mingora")
            cities.add("Nawabshah")
            cities.add("Chiniot")
            cities.add("Kohat")

            return cities
        }

        fun getLeftSeats(busId: String): List<Seat>{
            val seats = arrayListOf<Seat>()
            for (x in 1..20){
                seats.add(Seat(busId, x.toString()))
            }
            return seats
        }

        fun getRightSeats(busId: String): List<Seat>{
            val seats = arrayListOf<Seat>()
            for (x in 21..40){
                seats.add(Seat(busId, x.toString()))
            }
            return seats
        }
    }
}