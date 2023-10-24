package repository

import Sighting
import Train


interface TrainRepo {

    fun getAllTrains(): Any

    fun getTrain(id: String) : Train

    fun getSightingsJson(id: String) : List<Sighting>

    fun postSighting(sighting: Sighting)

    fun getSightingFromJson(json: String): Sighting

}