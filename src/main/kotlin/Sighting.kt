import java.time.LocalDateTime

data class Sighting(
    var id: Int,
    val station: String,
    val train: Train,
    val timestamp: LocalDateTime
)
