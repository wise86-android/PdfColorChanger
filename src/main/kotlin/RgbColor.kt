data class RgbColor(val red:Float, var green:Float, val blue:Float) {

    constructor(red:UByte, green:UByte, blue:UByte) :
            this(red.toFloat()/255.0f,green.toFloat()/255.0f,blue.toFloat()/255.0f)

    constructor(packet:Int):this(packet.redComponent,packet.greenComponent,packet.blueComponent)

    val redByte:UByte = (red * 255.0f).toByte().toUByte()
    val greenByte:UByte = (green * 255.0f).toByte().toUByte()
    val blueByte:UByte = (blue * 255.0f).toByte().toUByte()

    override fun toString(): String {
        return "RgbColor: (red=$redByte,green=$greenByte,blue:$blueByte)/(red:$red,green:$green,blue:$blue)"
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

private val Int.redComponent:UByte
    get() = ((this and 0x00FF0000) ushr 16).toUByte()

private val Int.greenComponent:UByte
    get() = ((this and 0x0000FF00) ushr  8).toUByte()

private val Int.blueComponent:UByte
    get() = (this and 0x000000FF).toUByte()