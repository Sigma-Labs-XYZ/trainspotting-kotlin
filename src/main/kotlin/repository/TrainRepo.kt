package repository

import Sighting
import Train


interface TrainRepo {

    fun getAllTrains(): Any

    fun getTrain(id: String) : Train

    fun postSighting(sighting: Sighting)

    fun getSightings(id: String): List<Sighting>

    fun getSightingFromJson(json: String): Sighting

}