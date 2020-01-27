import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class PdfContentStreamLineTest {

    @Test
    fun buildNotColorLine() {
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
    fun buildRedColorLine() {
        val actual = PdfContentStreamLine.buildFrom("1 0 0 rg")
        assertEquals(RED_COLOR_LINE, actual)
    }

    @Test
    fun redStrokeLineToString() {
        val actual = PdfContentStreamLine.buildFrom("1 0 0 RG")
        assertEquals(STROKE_RED_COLOR_LINE, actual)
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
        val actual = NOT_COLOR_LINE.changeColor(RgbColor(0f, 0f, 0f))
        assertEquals(NOT_COLOR_LINE, actual)
    }

    @Test
    fun buildColorLineWithPrefix() {
        val actual = PdfContentStreamLine.buildFrom("prefix 1 0 0 rg")
        val expected = PdfContentStreamLine(prefix = "prefix ", color = RED_COLOR, suffix = " rg")
        assertEquals(expected, actual)
    }

    @Test
    fun buildMixedColorLine() {
        val actual = PdfContentStreamLine.buildFrom("mixed 1 0 0 rg line")

        val expected = PdfContentStreamLine(prefix = "mixed ", color = RED_COLOR, suffix = " rg line")
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
        val line = PdfContentStreamLine(prefix = "mixed ", color = RED_COLOR, suffix = " rg line")
        assertEquals("mixed 1 0 0 rg line", line.toString())
    }

    @Test
    fun buildColorLineWithDecimals() {
        val actual = PdfContentStreamLine.buildFrom("0.1 0.5 1 rg")
        assertEquals(PdfContentStreamLine(prefix = "", color = RgbColor(0.1f, 0.5f, 1f), suffix = " rg"), actual)
    }

    @Test
    fun lineWithDecimalsToString() {
        val line = PdfContentStreamLine(prefix = "", color = RgbColor(0.1f, 0.5f, 1f), suffix = " rg")
        assertEquals("0.1 0.5 1 rg", line.toString())
    }

    companion object {
        private val RED_COLOR = RgbColor(1f, 0f, 0f)
        private val BLUE_COLOR = RgbColor(0f, 0f, 1f)
        private val STROKE_RED_COLOR_LINE = PdfContentStreamLine(prefix = "", color = RED_COLOR, suffix = " RG")
        private val RED_COLOR_LINE = PdfContentStreamLine(prefix = "", color = RED_COLOR, suffix = " rg")
        private val BLUE_COLOR_LINE = PdfContentStreamLine(prefix = "", color = BLUE_COLOR, suffix = " rg")
        private val NOT_COLOR_LINE = PdfContentStreamLine(prefix = "not color line", color = null, suffix = "")
    }
}