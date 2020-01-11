data class RgbColor(val red:Float, var green:Float, val blue:Float) {

    constructor(red:UByte, green:UByte, blue:UByte) :
            this(red.toFloat()/255.0f,green.toFloat()/255.0f,blue.toFloat()/255.0f)

    constructor(packet:Int):this(packet.redComponent,packet.greenComponent,packet.blueComponent)

    val redByte:UByte = (red * 255.0f).toByte().toUByte()
    val greenByte:UByte = (green * 255.0f).toByte().toUByte()
    val blueByte:UByte = (blue * 255.0f).toByte().toUByte()

}

private val Int.redComponent:UByte
    get() = ((this and 0x00FF0000) ushr 16).toUByte()

private val Int.greenComponent:UByte
    get() = ((this and 0x0000FF00) ushr  8).toUByte()

private val Int.blueComponent:UByte
    get() = (this and 0x000000FF).toUByte()