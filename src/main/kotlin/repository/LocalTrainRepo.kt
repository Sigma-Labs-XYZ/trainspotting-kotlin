package repository

import Train

class LocalTrainRepo(trains : List<Train>) : TrainRepo {

    private val trainInfo = trains
    override fun getAllTrains(): List<Train> {
        return trainInfo
    }

    override fun getTrain(id: String?): Train? {
        for (train in trainInfo) {
            if (train.id == id) (
                return train
            )
        }
        return null
    }
}