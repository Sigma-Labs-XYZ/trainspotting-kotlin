package repository

import Sighting
import Train


interface TrainRepo {

    fun getAllTrains(): Any

    fun getTrain(id: String?) : Train?

    fun postSighting(sighting: Sighting)

    fun getTrainFromJson(json: String): Train

}