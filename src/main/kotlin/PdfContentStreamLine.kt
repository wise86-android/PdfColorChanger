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

import java.text.DecimalFormat
import java.util.regex.Pattern

data class PdfContentStreamLine internal constructor(
    private val prefix: String,
    private val color: RgbColor?,
    private val suffix: String
) {

    companion object {
        fun buildFrom(rawLineContent: String): PdfContentStreamLine {
            val floatMatch = "0\\.\\d+|0|1|1\\.0"
            val colorLineMatcher = Pattern.compile("^(.*?\\s)?($floatMatch)\\s($floatMatch)\\s($floatMatch)(\\s[rg|RG].*)")
            val regexResult = colorLineMatcher.matcher(rawLineContent)
            if (!regexResult.find())
                return PdfContentStreamLine(prefix = rawLineContent, color = null, suffix = "")

            val prefix = regexResult.group(1) ?: ""
            val r = regexResult.group(2).toFloat()
            val g = regexResult.group(3).toFloat()
            val b = regexResult.group(4).toFloat()
            val suffix = regexResult.group(5)
            val color = RgbColor(r, g, b)
            return PdfContentStreamLine(prefix, color, suffix)
        }
    }

    fun getColor(): RgbColor? {
        return this.color
    }

    fun changeColor(newColor: RgbColor): PdfContentStreamLine {
        if (this.color == null)
            return this.copy()

        return this.copy(color = newColor)
    }

    override fun toString(): String {
        val colorToString = color?.let {
            val floatFormatter = DecimalFormat("#.#####")
            String.format(
                "%s %s %s",
                floatFormatter.format(it.red),
                floatFormatter.format(it.green),
                floatFormatter.format(it.blue)
            )
        } ?: ""
        return prefix + colorToString + suffix

    }

}
