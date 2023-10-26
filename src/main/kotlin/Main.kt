
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import repository.LocalTrainRepo
import com.fasterxml.jackson.databind.JsonMappingException
import org.http4k.core.Status.Companion.BAD_REQUEST
import repository.DatabaseClient
import java.rmi.NoSuchObjectException

val trainRepo = LocalTrainRepo()
val dataRepo = DatabaseClient()
val errorLens = Body.auto<String>().toLens()


val app: HttpHandler = routes(
    "/train" bind GET to {
        try {
            val trainsLensResponse = Body.auto<List<Train>>().toLens()
            val allTrains = dataRepo.getAllTrains()
            trainsLensResponse.inject(allTrains, Response(OK))
        } catch (e: NoSuchElementException) {
            errorLens.inject(e.message.toString(), Response(NOT_FOUND))
        } catch (e: Exception) {
            errorLens.inject(e.message.toString(), Response(INTERNAL_SERVER_ERROR))
        }
    },
    "/train/{id}" bind GET to {
        try {
            val idLensResponse = Body.auto<Train>().toLens()
            val output = dataRepo.getTrain(it.path("id").toString().toInt())
            idLensResponse.inject(output, Response(OK))
        } catch (e: NoSuchObjectException) {
            errorLens.inject(e.message.toString(), Response(NOT_FOUND))
        } catch (e: Exception) {
            errorLens.inject(e.message.toString(), Response(INTERNAL_SERVER_ERROR))
        }
    },
    "/train/{id}/sightings" bind GET to {
        try {
            val sightingsLensResponse = Body.auto<List<Sighting>>().toLens()
            val sightings = dataRepo.getSightings(it.path("id").toString().toInt())
            sightingsLensResponse.inject(sightings, Response(OK))
        } catch (e: NoSuchElementException) {
            errorLens.inject(e.message.toString(), Response(NOT_FOUND))
        } catch (e: Exception) {
            errorLens.inject(e.message.toString(), Response(INTERNAL_SERVER_ERROR))
        }
    },
    "/sightings" bind Method.POST to {
        try{
            val content = it.bodyString()
            val sighting = trainRepo.getSightingFromJson(content)
            trainRepo.postSighting(sighting)
            Response(OK).body("successful sightings post")
        } catch(e: JsonMappingException) {
            Response(BAD_REQUEST).body("Invalid JSON string")
        }
    }
)

fun main() {
    val printingApp: HttpHandler = PrintRequest().then(app)

    val server = printingApp.asServer(SunHttp(9000)).start()

    println("Server started on " + server.port())
}

