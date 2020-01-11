import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class IsValidColorLineKtTest{

    @Test
    fun validLineIsDetected(){
        assertTrue("0.73333 0.8 0 rg".isColorLine)
    }

    @Test
    fun validLineMustHave3Number(){
        assertFalse("rg".isColorLine)
        assertFalse("0 rg".isColorLine)
        assertFalse("0 0 rg".isColorLine)
        assertTrue("0 0 0 rg".isColorLine)
    }

    @Test
    fun validLineTheNumberCanBeFloat(){
        assertTrue("0 0.12345 1 rg".isColorLine)
    }

    @Test
    fun theNumberAreBetween0And1(){
        assertFalse("0 2 0 rg".isColorLine)
        assertFalse("1.1 0 0 rg".isColorLine)
        assertFalse("0 0 -0.1 rg".isColorLine)
    }


}