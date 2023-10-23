package repository

import Train
import org.junit.jupiter.api.Test

class LocalTrainRepoTest {
    private fun setup(): MutableList<Train> {
        return mutableListOf(
            Train("Fake-One", "Tomas", "Pink", "Oink23A"),
            Train("Fake-Two", "Owl", "Brown", "Toot42B")
        )
    }

    private val repoToTest = LocalTrainRepo(setup())

    @Test
    fun testGetAllTrains() {
        assert(repoToTest.getAllTrains().size == 2)
        assert(repoToTest.getAllTrains()[0].id == "Fake-One")
        assert(repoToTest.getAllTrains()[0].name == "Tomas")
        assert(repoToTest.getAllTrains()[0].colour == "Pink")
        assert(repoToTest.getAllTrains()[0].trainNumber == "Oink23A")
    }

    @Test
    fun testGetTrain() {
        val trainReturned = repoToTest.getTrain("Fake-Two")
        assert(trainReturned!!.colour == "Brown")
        assert(trainReturned.name == "Owl")
    }
}