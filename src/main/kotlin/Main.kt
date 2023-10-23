
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

val app: HttpHandler = routes(
    "/train" bind GET to {
        Response(OK).body("pong train")
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
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}