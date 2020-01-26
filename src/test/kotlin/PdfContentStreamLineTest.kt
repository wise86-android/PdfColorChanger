import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class PdfContentStreamLineTest {

    @Test
    fun buildNotColorLineFromString() {
        val actual = PdfContentStreamLine.buildFrom("not color line")
        assertEquals(NOT_COLOR_LINE, actual)
    }

    @Test
    fun notColorLineToString() {
        assertEquals("not color line", NOT_COLOR_LINE.toString())
    }

    @Test
    fun notColorLineHasNullColor() {
        assertNull(NOT_COLOR_LINE.getColor())
    }

    @Test
    fun buildRedColorLineFromString() {
        val actual = PdfContentStreamLine.buildFrom("1 0 0 rg")
        assertEquals(RED_COLOR_LINE, actual)
    }

    @Test
    fun redColorLineToString() {
        assertEquals("1 0 0 rg", RED_COLOR_LINE.toString())
    }

    @Test
    fun redColorLineHasRedColor() {
        assertEquals(RED_COLOR, RED_COLOR_LINE.getColor())
    }

    @Test
    fun changeColorToSimpleColorLine() {
        val actual = RED_COLOR_LINE.changeColor(BLUE_COLOR)
        assertEquals(BLUE_COLOR_LINE, actual)
    }

    @Test
    fun changeColorToNotColorLine() {
        val actual = NOT_COLOR_LINE.changeColor(RgbColor(0u, 0u, 0u))
        assertEquals(NOT_COLOR_LINE, actual)
    }

    @Test
    fun buildColorLineWithPrefixFromString() {
        val actual = PdfContentStreamLine.buildFrom("prefix 1 0 0 rg")
        val expected = PdfContentStreamLine(prefix = "prefix ", color = RED_COLOR, suffix = "")
        assertEquals(expected, actual)
    }

    @Test
    fun buildMixedColorLine() {
        val actual = PdfContentStreamLine.buildFrom("mixed 1 0 0 rg line")

        val expected = PdfContentStreamLine(prefix = "mixed ", color = RED_COLOR, suffix = " line")
        assertEquals(expected, actual)
    }

    @Test
    fun changeColorToMixedColorLine() {
        val line = PdfContentStreamLine(prefix = "mixed ", color = RED_COLOR, suffix = " line")

        val actual = line.changeColor(BLUE_COLOR)

        val expected = PdfContentStreamLine(prefix = "mixed ", color = BLUE_COLOR, suffix = " line")
        assertEquals(expected, actual)

    }

    @Test
    fun mixedColorLineToString() {
        val line = PdfContentStreamLine(prefix = "mixed ", color = RED_COLOR, suffix = " line")
        assertEquals("mixed 1 0 0 rg line", line.toString())
    }

    companion object {
        private val RED_COLOR = RgbColor(255u, 0u, 0u)
        private val BLUE_COLOR = RgbColor(0u, 0u, 255u)
        private val RED_COLOR_LINE = PdfContentStreamLine(prefix = "", color = RED_COLOR, suffix = "")
        private val BLUE_COLOR_LINE = PdfContentStreamLine(prefix = "", color = BLUE_COLOR, suffix = "")
        private val NOT_COLOR_LINE = PdfContentStreamLine(prefix = "not color line", color = null, suffix = "")
    }
}