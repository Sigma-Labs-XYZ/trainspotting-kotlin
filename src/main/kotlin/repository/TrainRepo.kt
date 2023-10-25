package repository

import Sighting
import Train


interface TrainRepo {

    fun getAllTrains(): Any

    fun getTrain(id: Int) : Train

    fun getSightings(id: Int) : List<Sighting>

}