package repository

import Train


interface TrainRepo {

    fun getAllTrains(): Any

    fun getTrain(id: String?) : Train?

}