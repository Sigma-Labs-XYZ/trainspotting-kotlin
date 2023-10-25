
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.format.Jackson.auto
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
        try {
            val trainsLensResponse = Body.auto<List<Train>>().toLens()
            val allTrains = trainRepo.getAllTrains()
            trainsLensResponse.inject(allTrains, Response(OK))

        } catch (e: Exception){
            Response(OK).body(e.message.toString())
        }

    },
    "/train/{id}" bind GET to {
        try {
            val idLensResponse = Body.auto<Train>().toLens()
            val output = trainRepo.getTrain(it.path("id").toString())
            idLensResponse.inject(output, Response(OK))
        } catch (e: Exception) {
            //put in specific exception before this (not found one) 404
            // return an error code and change response from ok to something else 500
            Response(OK).body(e.message.toString())
        }
    },
    "/train/{id}/sightings" bind GET to {
        try {
            val sightingsLensResponse = Body.auto<List<Sighting>>().toLens()
            val sightings = trainRepo.getSightings(it.path("id").toString())
            sightingsLensResponse.inject(sightings, Response(OK))
        } catch (e: Exception){
            Response(OK).body(e.message.toString())
        }
    },
    "/sightings" bind Method.POST to {
        Response(OK).body("ping sightings post")
    }
)

fun main() {
    val printingApp: HttpHandler = PrintRequest().then(app)

    val server = printingApp.asServer(SunHttp(9000)).start()

    println("Server started on " + server.port())
}

