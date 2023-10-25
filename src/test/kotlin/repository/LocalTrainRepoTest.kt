package repository

import Train
import org.junit.jupiter.api.Test

class LocalTrainRepoTest {
    private fun setup(): MutableList<Train> {
        return mutableListOf(
            Train(1, "Tomas", "Pink", "Oink23A"),
            Train(2, "Owl", "Brown", "Toot42B")
        )
    }

    private val repoToTest = LocalTrainRepo()

    @Test
    fun testGetAllTrains() {
        repoToTest.setTrainInfo(setup())
        assert(repoToTest.getAllTrains().size == 2)
        assert(repoToTest.getAllTrains()[0].id == 1)
        assert(repoToTest.getAllTrains()[0].name == "Tomas")
        assert(repoToTest.getAllTrains()[0].colour == "Pink")
        assert(repoToTest.getAllTrains()[0].trainNumber == "Oink23A")
    }

    @Test
    fun testGetTrain() {
        repoToTest.setTrainInfo(setup())
        val trainReturned = repoToTest.getTrain(2)
        assert(trainReturned.colour == "Brown")
        assert(trainReturned.name == "Owl")
    }
}