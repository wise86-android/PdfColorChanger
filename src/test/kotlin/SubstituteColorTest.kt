/*
 * Copyright 2020 Giovanni Visentini
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted (subject to the limitations in the disclaimer
 * below) provided that the following conditions are met:
 *
 *      * Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *
 *      * Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *
 *      * Neither the name of the copyright holder nor the names of its
 *      contributors may be used to endorse or promote products derived from this
 *      software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY
 * THIS LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SubstituteColorTest {

    @Test
    fun changeAColor() {
        val input = "0 0 0 rg"
        val expected = "1 1 1 rg"
        val changeRules = mapOf(
            RgbColor(0.0f, 0.0f, 0.0f) to RgbColor(1.0f, 1.0f, 1.0f)
        )
        val (out, _) = changeColors(input, changeRules)
        assertEquals(expected, out)
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
        assertEquals(expected, out)
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
        assertEquals(5u, stat.imageColors)
        assertEquals(3, stat.unknownColor.size)
        assertTrue(stat.unknownColor.contains(RgbColor(0.0f, 1.0f, 0.0f)))
        assertTrue(stat.unknownColor.contains(RgbColor(0.0f, 1.0f, 0.5f)))
        assertTrue(stat.unknownColor.contains(RgbColor(0.0f, 0.0f, 1.0f)))

    }

    @Test
    fun changeStrokeColor() {
        val input = "0 0 0 RG"
        val expected = "1 1 1 RG"
        val changeRules = mapOf(
            RgbColor(0.0f, 0.0f, 0.0f) to RgbColor(1.0f, 1.0f, 1.0f)
        )
        val (out, _) = changeColors(input, changeRules)
        assertEquals(expected, out)
    }

    @Test
    fun changeColorKeepingOtherCommands() {
        val input = "0 0 0 RG OtherCommands"
        val expected = "1 1 1 RG OtherCommands"
        val changeRules = mapOf(
            RgbColor(0.0f, 0.0f, 0.0f) to RgbColor(1.0f, 1.0f, 1.0f)
        )
        val (out, _) = changeColors(input, changeRules)
        assertEquals(expected, out)
    }

}