package repository

import Sighting
import Train


interface TrainRepo {

    fun getAllTrains(): List<Train>

    fun getTrain(id: Int) : Train

    fun postSighting(sighting: Sighting)

    fun getSightingFromJson(json: String): Sighting

    fun getSightings(id: Int) : List<Sighting>

}