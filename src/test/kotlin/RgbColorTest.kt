import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RgbColorTest{

    @Test
    fun byteToFloatIsCorrectlyDone(){
        assertEquals(1.0f,RgbColor(255.toUByte(),0.toUByte(),0.toUByte()).red)
        assertEquals(1.0f,RgbColor(0.toUByte(),255.toUByte(),0.toUByte()).green)
        assertEquals(1.0f,RgbColor(0.toUByte(),0.toUByte(),255.toUByte()).blue)
        assertEquals(128.0f/255.0f,RgbColor(128.toUByte(),0.toUByte(),0.toUByte()).red)
    }

    @Test
    fun floatToUByteIsCorrectlyDone(){
        val color = RgbColor(123.toUByte(),234.toUByte(),12.toUByte())
        assertEquals(123.toUByte(),color.redByte)
        assertEquals(234.toUByte(),color.greenByte)
        assertEquals(12.toUByte(),color.blueByte)
    }

    @Test
    fun intToRgbIsCorrectlyDone(){
        val color = RgbColor(0x00AB12CD)
        assertEquals(0xAB.toUByte(),color.redByte)
        assertEquals(0x12.toUByte(),color.greenByte)
        assertEquals(0xCD.toUByte(),color.blueByte)
    }


}