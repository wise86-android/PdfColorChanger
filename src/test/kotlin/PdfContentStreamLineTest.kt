import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
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
    fun notColorLineDoNotContainsColor() {
        assertFalse(NOT_COLOR_LINE.containsColor())
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
    fun redColorLineDoNotContainsColor() {
        assertTrue(RED_COLOR_LINE.containsColor())
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
        val expected = PdfContentStreamLine(prefix = "prefix ", color = RED_COLOR)
        assertEquals(expected, actual)
    }

    @Disabled
    @Test
    fun buildMixedColorLine() {
        val mixedColorLine = PdfContentStreamLine.buildFrom("mixed 1 0 0 rg line")

        assertTrue(mixedColorLine.containsColor())
        assertEquals(RED_COLOR, mixedColorLine.getColor())
        assertEquals("mixed 1 0 0 rg line", mixedColorLine.toString())
    }

    @Disabled
    @Test
    fun changeColorToColorLineWithPrefix() {
        val greenColorLine = PdfContentStreamLine.buildFrom("prefix 0 1 0 rg")

        val redColor = RED_COLOR
        val redColorLine = greenColorLine.changeColor(redColor)

        assertEquals(redColor, redColorLine.getColor())
        assertEquals("prefix 1 0 0 rg", redColorLine.toString())

    }

    companion object {
        private val RED_COLOR = RgbColor(255u, 0u, 0u)
        private val BLUE_COLOR = RgbColor(0u, 0u, 255u)
        private val RED_COLOR_LINE = PdfContentStreamLine(prefix = "", color = RED_COLOR)
        private val BLUE_COLOR_LINE = PdfContentStreamLine(prefix = "", color = BLUE_COLOR)
        private val NOT_COLOR_LINE = PdfContentStreamLine(prefix = "not color line", color = null)
    }
}