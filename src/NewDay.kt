import java.io.File
import java.nio.file.Files
import java.nio.file.Path

const val DAY = 7

fun main() {
    var template = File("src/template.kt").readText()

    template = template.replace("{day}", DAY.toString())

    if (Files.exists(Path.of("src/Day${DAY.toString().padStart(2, '0')}.kt"))) { return }

    File("src/Day${DAY.toString().padStart(2, '0')}.kt").writeText(template)
}