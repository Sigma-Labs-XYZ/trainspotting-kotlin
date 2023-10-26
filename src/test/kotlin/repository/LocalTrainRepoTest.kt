package repository

import Train
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

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

    @Test
    fun getSightingFromJsonTest() {
        val testTime = LocalDateTime.now()
        val jsonString = "{\n" +
                "    \"station\": {\"name\" : \"Liverpool Street\"},\n" +
                "    \"train\": {\n" +
                "      \"name\": \"Thomas\",\n" +
                "      \"colour\": \"Blue\",\n" +
                "      \"trainNumber\": \"T1182A\"\n" +
                "    },\n" +
                "    \"timestamp\": \"$testTime\"\n" +
                "  }"

        println(jsonString)
        val sighting = repoToTest.getSightingFromJson(jsonString)
        println(sighting)
        val train = Train(-1, "Thomas", "Blue", "T1182A")

        assertEquals(repoToTest.getSightingsInfo().size, sighting.id)
        assertEquals(-1, sighting.station.id)
        assertEquals(train, sighting.train)
        assertEquals(testTime, sighting.timestamp)
    }

    @Test
    fun testAddSightings() {
        val sightingsListSize = repoToTest.getSightingsInfo().size
        val jsonString = "{\n" +
                "    \"station\": {\"name\" : \"Liverpool Street\"},\n" +
                "    \"train\": {\n" +
                "      \"name\": \"Thomas\",\n" +
                "      \"colour\": \"Blue\",\n" +
                "      \"trainNumber\": \"T1192A\"\n" +
                "    },\n" +
                "    \"timestamp\": \"${LocalDateTime.now()}\"\n" +
                "  }"

        val sighting = repoToTest.getSightingFromJson(jsonString)

        repoToTest.postSighting(sighting)

        val allSightings = repoToTest.getSightingsInfo()

        assertEquals(sightingsListSize + 1, allSightings.size)
    }
}