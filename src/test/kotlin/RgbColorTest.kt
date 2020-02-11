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

    @Test
    fun theColorCanBeInitializedWithCMYBValues(){
        val color = RgbColor(0.37f,0.73f,0.26f,0.8f)
        assertEquals(32.toUByte(), color.redByte)
        assertEquals(14.toUByte(), color.greenByte)
        assertEquals(38.toUByte(), color.blueByte)
    }

}