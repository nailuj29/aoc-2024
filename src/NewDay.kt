import java.io.File

const val DAY = 5

fun main() {
    val template = File("src/template.kt").readText()

    template.replace("{day}", DAY.toString())

    File("src/Day${DAY.toString().padStart(2, '0')}.kt").writeText(template)
}