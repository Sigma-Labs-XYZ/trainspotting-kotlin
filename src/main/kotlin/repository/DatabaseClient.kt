package repository

import Sighting
import Train

class DatabaseClient : TrainRepo {

    override fun getAllTrains(): Any {
        return "HI"
    }

    override fun getTrain(id: String): Train? {
        TODO("Not yet implemented")
    }

    override fun getSightingsJson(id: String): List<Sighting> {
        TODO("Not yet implemented")
    }

    override fun postSighting(sighting: Sighting) {
        TODO("Not yet implemented")
    }

    override fun getSightingFromJson(json: String): Sighting {
        TODO("Not yet implemented")
    }

}