package repository

import Train

class DatabaseClient : TrainRepo {

    override fun getAllTrains(): Any {
        return "HI"
    }

    override fun getTrain(id: String?): Train {
        TODO("Not yet implemented")
    }

}