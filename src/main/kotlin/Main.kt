
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import repository.LocalTrainRepo
import com.fasterxml.jackson.databind.ObjectMapper


// Registering the Kotlin module with the ObjectMapper instance
val mapper = ObjectMapper()


val app: HttpHandler = routes(
    "/train" bind GET to {

        val trainRepo = LocalTrainRepo()
        val allTrains = trainRepo.getAllTrains()
        Response(OK).body(allTrains.toString())
    },
    "/train{id}" bind GET to {
        Response(OK).body("ping train id")
    },
    "/train{id}/sightings" bind GET to {
        Response(OK).body("ping sightings get")
    },
    "/sightings" bind Method.POST to {
        Response(OK).body("ping sightings post")
    }
)

fun main(args: Array<String>) {
    val printingApp: HttpHandler = PrintRequest().then(app)

    val server = printingApp.asServer(SunHttp(9000)).start()

    println("Server started on " + server.port())
}

