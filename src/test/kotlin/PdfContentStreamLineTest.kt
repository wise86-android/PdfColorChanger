import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class PdfContentStreamLineTest {

    @Test
    fun notColorLine() {
        val line = PdfContentStreamLine.buildFrom("not color string")

        assertFalse(line.containsColor())
        assertNull(line.getColor())
        assertEquals("not color string", line.toString())
    }

    @Test
    fun simpleRedColorLine() {
        val redColorLine = PdfContentStreamLine.buildFrom("1 0 0 rg")

        assertTrue(redColorLine.containsColor())
        assertEquals(RgbColor(255u, 0u, 0u), redColorLine.getColor())
        assertEquals("1 0 0 rg", redColorLine.toString())
    }

    @Test
    fun mixedColorLine() {
        val mixedColorLine = PdfContentStreamLine.buildFrom("mixed 1 0 0 rg line")

        assertTrue(mixedColorLine.containsColor())
        assertEquals(RgbColor(255u, 0u, 0u), mixedColorLine.getColor())
        assertEquals("mixed 1 0 0 rg line", mixedColorLine.toString())
    }

    @Test
    fun changeColorToSimpleColorLine() {
        val redColorLine = PdfContentStreamLine.buildFrom("1 0 0 rg")
        val blueColor = RgbColor(0u, 0u, 255u)

        val blueColorLine = redColorLine.changeColor(blueColor)

        assertEquals(blueColor, blueColorLine.getColor())
        assertEquals("0 0 1 rg", blueColorLine.toString())
    }

    @Test
    fun changeColorToNotColorLine() {
        val notColorLine = PdfContentStreamLine.buildFrom("not color line")

        val stillNotColorLine = notColorLine.changeColor(RgbColor(0u, 0u, 0u))

        assertEquals(notColorLine, stillNotColorLine)
    }

    @Disabled
    @Test
    fun changeColorToColorLineWithPrefix() {
        val greenColorLine = PdfContentStreamLine.buildFrom("prefix 0 1 0 rg")

        val redColor = RgbColor(255u, 0u, 0u)
        val redColorLine = greenColorLine.changeColor(redColor)

        assertEquals(redColor, redColorLine.getColor())
        assertEquals("prefix 1 0 0 rg", redColorLine.toString())
    }
}