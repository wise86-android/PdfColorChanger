import kotlin.math.roundToInt

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

data class RgbColor(val red: Float, var green: Float, val blue: Float) {

    constructor(red: UByte, green: UByte, blue: UByte) :
            this(red.toFloat() / 255.0f, green.toFloat() / 255.0f, blue.toFloat() / 255.0f)

    constructor(cyan:Float,magenta:Float,yellow:Float,black:Float):
        this((1.0f-cyan)*(1.0f-black),(1.0f-magenta)*(1.0f-black),(1.0f-yellow)*(1.0f-black))

    constructor(packet: Int) : this(packet.redComponent, packet.greenComponent, packet.blueComponent)

    val redByte: UByte = (red * 255.0f).roundToInt().toUByte()
    val greenByte: UByte = (green * 255.0f).roundToInt().toUByte()
    val blueByte: UByte = (blue * 255.0f).roundToInt().toUByte()

    override fun toString(): String {
        val hexValue = String.format("%02X%02X%02X",redByte.toByte(),greenByte.toByte(),blueByte.toByte())
        return "RgbColor: $hexValue (red=$redByte,green=$greenByte,blue:$blueByte)/(red:$red,green:$green,blue:$blue)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RgbColor

        if (redByte != other.redByte) return false
        if (greenByte != other.greenByte) return false
        if (blueByte != other.blueByte) return false

        return true
    }

    override fun hashCode(): Int {
        var result = redByte.hashCode()
        result = 31 * result + greenByte.hashCode()
        result = 31 * result + blueByte.hashCode()
        return result
    }
}

private val Int.redComponent: UByte
    get() = ((this and 0x00FF0000) ushr 16).toUByte()

private val Int.greenComponent: UByte
    get() = ((this and 0x0000FF00) ushr 8).toUByte()

private val Int.blueComponent: UByte
    get() = (this and 0x000000FF).toUByte()