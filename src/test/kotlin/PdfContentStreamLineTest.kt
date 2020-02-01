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