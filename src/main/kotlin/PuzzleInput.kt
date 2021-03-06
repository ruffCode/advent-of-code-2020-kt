import java.io.File

object PuzzleInput {
    operator fun invoke(fileName: String): File {
        val path = this.javaClass.getResource(fileName)?.path
            ?: throw IllegalStateException("missing input $fileName")
        return File(path)
    }

    fun File.toStingList() = readText().split(newLine)
}

val newLine = System.lineSeparator()
