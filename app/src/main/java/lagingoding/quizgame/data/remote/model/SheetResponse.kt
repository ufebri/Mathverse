package lagingoding.quizgame.data.remote.model

data class SheetResponse(
    val range: String,
    val majorDimension: String,
    val values: List<List<String>>
)