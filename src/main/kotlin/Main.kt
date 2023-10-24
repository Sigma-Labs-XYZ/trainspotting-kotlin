
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import repository.LocalTrainRepo
import java.io.BufferedReader


var mapper = ObjectMapper()
val trainRepo = LocalTrainRepo()

val app: HttpHandler = routes(
    "/train" bind GET to {
        try {
            val allTrains = trainRepo.getAllTrains()
            val trainsAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allTrains)
            Response(OK).body(trainsAsString)
        } catch (e: Exception){
            Response(OK).body(e.message.toString())
        }

    },
    "/train/{id}" bind GET to {
        try {
            val train = mapper.writeValueAsString(trainRepo.getTrain(it.path("id").toString()))
            Response(OK).body(train)
        } catch (e: Exception) {
            Response(OK).body(e.message.toString())
        }
    },
    "/train/{id}/sightings" bind GET to {
        try {
            mapper= mapper.registerModule(JavaTimeModule())
            val sightings = trainRepo.getSightings(it.path("id").toString())
            val jsonSightings = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sightings)
            Response(OK).body(jsonSightings)
        } catch (e: Exception){
            Response(OK).body(e.message.toString())
        }
    },
    "/sightings" bind Method.POST to {
        val content = it.bodyString()
        println(content)
        val sighting = trainRepo.getSightingFromJson(content)
        trainRepo.postSighting(sighting)

        Response(OK).body("ping sightings post")
    }
)

fun main() {
    val printingApp: HttpHandler = PrintRequest().then(app)

    val server = printingApp.asServer(SunHttp(9000)).start()

    println("Server started on " + server.port())
}

