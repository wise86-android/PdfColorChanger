import java.text.DecimalFormat
import java.util.regex.Pattern

data class PdfContentStreamLine internal constructor(
    private val prefix: String,
    private val color: RgbColor?,
    private val suffix: String
) {

    companion object {
        fun buildFrom(rawLineContent: String): PdfContentStreamLine {
            val colorLineMatcher = Pattern.compile("(.*?)([1|0]\\.?\\d*) ([1|0]\\.?\\d*) ([1|0]\\.?\\d*)(\\s[rg].*)")
            val regexResult = colorLineMatcher.matcher(rawLineContent)
            if (!regexResult.find())
                return PdfContentStreamLine(prefix = rawLineContent, color = null, suffix = "")

            val prefix = regexResult.group(1)
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
