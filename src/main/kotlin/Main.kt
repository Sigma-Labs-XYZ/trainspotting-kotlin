
import com.fasterxml.jackson.databind.ObjectMapper
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


var mapper = ObjectMapper()
val trainRepo = LocalTrainRepo()

val app: HttpHandler = routes(
    "/train" bind GET to {
        val allTrains = trainRepo.getAllTrains()
        val trainsAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allTrains)
        Response(OK).body(trainsAsString)
    },
    "/train/{id}" bind GET to {
        val train = mapper.writeValueAsString(trainRepo.getTrain(it.path("id").toString()))
        Response(OK).body(train)
    },
    "/train/{id}/sightings" bind GET to {
        val sightings = trainRepo.getSightingsJson(it.path("id").toString())
        val jsonSightings = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sightings)

        Response(OK).body(jsonSightings)
    },
    "/sightings" bind Method.POST to {
        val sighting = trainRepo.getSightingFromJson(it.body.toString())
        trainRepo.postSighting(sighting)
        Response(OK).body("ping sightings post")
    }
)

fun main() {
    val printingApp: HttpHandler = PrintRequest().then(app)

    val server = printingApp.asServer(SunHttp(9000)).start()

    println("Server started on " + server.port())
}

