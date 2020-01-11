import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class StrToRgbColorKtTest{

    @Test
    fun returnNullIfInvalidString(){
        val invalidLine = "not a rgb color"
        assertNull(invalidLine.toRgbColor)
    }

    @Test
    fun valueAreCorrectlySetInTheColor(){
        assertEquals(255.toUByte(),"1 0 0 rg".toRgbColor?.redByte)
        assertEquals(255.toUByte(),"0 1 0 rg".toRgbColor?.greenByte)
        assertEquals(255.toUByte(),"0 0 1 rg".toRgbColor?.blueByte)
        val color = "0.1 0.5 1 rg".toRgbColor
        assertNotNull(color)
        assertEquals(25.toUByte(),color?.redByte)
        assertEquals(127.toUByte(),color?.greenByte)
        assertEquals(255.toUByte(),color?.blueByte)
    }

}