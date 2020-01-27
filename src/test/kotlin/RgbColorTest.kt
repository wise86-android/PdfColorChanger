import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RgbColorTest {

    @Test
    fun byteToFloatIsCorrectlyDone() {
        assertEquals(1.0f, RgbColor(255u, 0u, 0u).red)
        assertEquals(1.0f, RgbColor(0u, 255u, 0u).green)
        assertEquals(1.0f, RgbColor(0u, 0u, 255u).blue)
        assertEquals(128.0f / 255.0f, RgbColor(128u, 0u, 0u).red)
    }

    @Test
    fun floatToUByteIsCorrectlyDone() {
        val color = RgbColor(123u, 234u, 12u)
        assertEquals(123.toUByte(), color.redByte)
        assertEquals(234.toUByte(), color.greenByte)
        assertEquals(12.toUByte(), color.blueByte)
    }

    @Test
    fun intToRgbIsCorrectlyDone() {
        val color = RgbColor(0x00AB12CD)
        assertEquals(0xAB.toUByte(), color.redByte)
        assertEquals(0x12u.toUByte(), color.greenByte)
        assertEquals(0xCDu.toUByte(), color.blueByte)
    }

    @Test
    fun theByteRepresentationIsUsedForEquals() {
        val color = RgbColor(0u, 0u, 0u)
        val similarColor = RgbColor(0.001f, 0.0001f, 0.00001f)
        assertEquals(color, similarColor)
        assertEquals(color.hashCode(), similarColor.hashCode())
    }

}