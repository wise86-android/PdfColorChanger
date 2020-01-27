import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SubstituteColorTest{

    @Test
    fun changeAColor(){
        val input = "0 0 0 rg"
        val expected = "1 1 1 rg"
        val changeRules = mapOf(
            RgbColor(0.0f,0.0f,0.0f) to RgbColor(1.0f,1.0f,1.0f)
        )
        val (out , _) = changeColors(input,changeRules)
        assertEquals(expected,out)
    }

    @Test
    fun aNonColorTextIsKeeped() {
        val input = "do Not Change ME"
        val changeRules = mapOf(
            RgbColor(0.0f, 0.0f, 0.0f) to RgbColor(1.0f, 1.0f, 1.0f)
        )
        val (out, _) = changeColors(input, changeRules)
        assertEquals(input, out)
    }

    @Test
    fun changeAreDoneInMultipleLines() {
        val input = """"
               |do Not Change ME
               |0 0 0 rg
               |1 0 0 rg
               |do Not Change ME
               """.trimMargin()
        val expected = """"
               |do Not Change ME
               |1 1 1 rg
               |0 1 1 rg
               |do Not Change ME
               """.trimMargin()

        val changeRules = mapOf(
            RgbColor(0.0f, 0.0f, 0.0f) to RgbColor(1.0f, 1.0f, 1.0f),
            RgbColor(1.0f, 0.0f, 0.0f) to RgbColor(0.0f, 1.0f, 1.0f)
        )
        val (out, _) = changeColors(input, changeRules)
        assertEquals(expected,out)
    }

    @Test
    fun substitutionStatAreCorrectlyComputed() {
        val input = """"
               |0 0 0 rg
               |1 0 0 rg
               |0 0 0 rg
               |0 1 0 rg
               |0 1 0 rg
               |0 0 1 rg
               |0 1 0.5 rg
               """.trimMargin()
        val changeRules = mapOf(
            RgbColor(0.0f, 0.0f, 0.0f) to RgbColor(1.0f, 1.0f, 1.0f),
            RgbColor(1.0f, 0.0f, 0.0f) to RgbColor(0.0f, 1.0f, 1.0f)
        )
        val (_, stat) = changeColors(input, changeRules)
        assertEquals(5u,stat.imageColors)
        assertEquals(3,stat.unknownColor.size)
        assertTrue(stat.unknownColor.contains(RgbColor(0.0f,1.0f,0.0f)))
        assertTrue(stat.unknownColor.contains(RgbColor(0.0f,1.0f,0.5f)))
        assertTrue(stat.unknownColor.contains(RgbColor(0.0f,0.0f,1.0f)))

    }

    @Test
    fun changeStrokeColor(){
        val input = "0 0 0 RG"
        val expected = "1 1 1 RG"
        val changeRules = mapOf(
            RgbColor(0.0f,0.0f,0.0f) to RgbColor(1.0f,1.0f,1.0f)
        )
        val (out , _) = changeColors(input,changeRules)
        assertEquals(expected,out)
    }

    @Test
    fun changeColorKeepingOtherCommands(){
        val input = "0 0 0 RG OtherCommands"
        val expected = "1 1 1 RG OtherCommands"
        val changeRules = mapOf(
            RgbColor(0.0f,0.0f,0.0f) to RgbColor(1.0f,1.0f,1.0f)
        )
        val (out , _) = changeColors(input,changeRules)
        assertEquals(expected,out)
    }

}