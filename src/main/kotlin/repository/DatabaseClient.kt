package repository

import Sighting
import Train

class DatabaseClient : TrainRepo {

    override fun getAllTrains(): Any {
        return "HI"
    }

    override fun getTrain(id: Int): Train {
        TODO("Not yet implemented")
    }

    override fun getSightings(id: Int): List<Sighting> {
        TODO("Not yet implemented")
    }

}