package repository

import Sighting
import Train

class DatabaseClient : TrainRepo {

    override fun getAllTrains(): Any {
        return "HI"
    }

    override fun getTrain(id: String): Train {
        TODO("Not yet implemented")
    }

    override fun getSightings(id: String): List<Sighting> {
        TODO("Not yet implemented")
    }

}