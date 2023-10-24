
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

val mapper = ObjectMapper()

val app: HttpHandler = routes(
    "/train" bind GET to {
        val trainRepo = LocalTrainRepo()
        val allTrains = trainRepo.getAllTrains()
        val trainsAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allTrains)
        Response(OK).body(trainsAsString)
    },
    "/train{id}" bind GET to {
        val trainRepo = LocalTrainRepo()
        val train = mapper.writeValueAsString(trainRepo.getTrain(it.path("id")))
        Response(OK).body(train)
    },
    "/train{id}/sightings" bind GET to {
        Response(OK).body("ping sightings get")
    },
    "/sightings" bind Method.POST to {
        val trainRepo = LocalTrainRepo()
        val sighting = trainRepo.getSightingFromJson(it.body.toString())


        Response(OK).body("ping sightings post")
    }
)

fun main() {
    val printingApp: HttpHandler = PrintRequest().then(app)

    val server = printingApp.asServer(SunHttp(9000)).start()

    println("Server started on " + server.port())
}

