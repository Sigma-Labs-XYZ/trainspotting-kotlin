package repository

import Train
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class LocalTrainRepoTest {
    private fun setup(): MutableList<Train> {
        return mutableListOf(
            Train("Fake-One", "Tomas", "Pink", "Oink23A"),
            Train("Fake-Two", "Owl", "Brown", "Toot42B")
        )
    }

    private val repoToTest = LocalTrainRepo()

    @Test
    fun testGetAllTrains() {
        repoToTest.setTrainInfo(setup())
        assert(repoToTest.getAllTrains().size == 2)
        assert(repoToTest.getAllTrains()[0].id == "Fake-One")
        assert(repoToTest.getAllTrains()[0].name == "Tomas")
        assert(repoToTest.getAllTrains()[0].colour == "Pink")
        assert(repoToTest.getAllTrains()[0].trainNumber == "Oink23A")
    }

    @Test
    fun testGetTrain() {
        repoToTest.setTrainInfo(setup())
        val trainReturned = repoToTest.getTrain("Fake-Two")
        assert(trainReturned!!.colour == "Brown")
        assert(trainReturned.name == "Owl")
    }

    @Test
    fun testGetTrainFromJson() {
        val jsonString = "{\n" +
                "    \"id\": \"string\",\n" +
                "    \"station\": {\n" +
                "      \"id\": \"string\",\n" +
                "      \"name\": \"Liverpool Street\"\n" +
                "    },\n" +
                "    \"train\": {\n" +
                "      \"id\": \"FSE34-fSFes2\",\n" +
                "      \"name\": \"Thomas\",\n" +
                "      \"colour\": \"Blue\",\n" +
                "      \"trainNumber\": \"T1192A\"\n" +
                "    },\n" +
                "    \"timestamp\": \"string\"\n" +
                "  }"
        val jsonTrain = repoToTest.getAllTrains()[0]

        assertEquals("FSE34-fSFes2", jsonTrain.id)
        assertEquals("Thomas", jsonTrain.name)
        assertEquals("Blue", jsonTrain.colour)
        assertEquals("T1192A", jsonTrain.trainNumber)

    }

    @Test
    fun getSightingFromJsonTest() {
        val testTime = LocalDateTime.now()
        val jsonString = "{\n" +
                "    \"station\": \"Liverpool Street\",\n" +
                "    \"train\": {\n" +
                "      \"id\": \"FSE34-fSFes2\",\n" +
                "      \"name\": \"Thomas\",\n" +
                "      \"colour\": \"Blue\",\n" +
                "      \"trainNumber\": \"T1192A\"\n" +
                "    },\n" +
                "    \"timestamp\": \"$testTime\"\n" +
                "  }"

        val sighting = repoToTest.getSightingFromJson(jsonString)
        val train = Train("FSE34-fSFes2", "Thomas", "Blue", "T1192A")

        assertEquals(repoToTest.getSightings().size, sighting.id)
        assertEquals("Liverpool Street", sighting.station)
        assertEquals(train, sighting.train)
        assertEquals(testTime, sighting.timestamp)
    }

    @Test
    fun testAddSightings() {
        val sightingsListSize = repoToTest.getSightings().size
        val jsonString = "{\n" +
                "    \"station\": \"Liverpool Street\",\n" +
                "    \"train\": {\n" +
                "      \"id\": \"FSE34-fSFes2\",\n" +
                "      \"name\": \"Thomas\",\n" +
                "      \"colour\": \"Blue\",\n" +
                "      \"trainNumber\": \"T1192A\"\n" +
                "    },\n" +
                "    \"timestamp\": \"${LocalDateTime.now()}\"\n" +
                "  }"

        val sighting = repoToTest.getSightingFromJson(jsonString)

        repoToTest.postSighting(sighting)

        val allSightings = repoToTest.getSightings()

        assertEquals(sightingsListSize + 1, allSightings.size)
    }
}