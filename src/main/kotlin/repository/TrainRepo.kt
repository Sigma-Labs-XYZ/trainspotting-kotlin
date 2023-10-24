package repository

import Sighting
import Train


interface TrainRepo {

    fun getAllTrains(): Any

    fun getTrain(id: String) : Train?

    fun getSightings(id: String) : List<Sighting>

}