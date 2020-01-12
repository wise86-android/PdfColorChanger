import java.text.DecimalFormat
import java.util.regex.Pattern

private const val normalizerFloatRegExp = "(1|0.?\\d*)"
private val colorLineMatcher = Pattern.compile("$normalizerFloatRegExp $normalizerFloatRegExp $normalizerFloatRegExp rg")

val CharSequence.isColorLine:Boolean
get() = colorLineMatcher.matcher(this).matches()

val CharSequence.toRgbColor:RgbColor?
get() {
    val match = colorLineMatcher.matcher(this)
    return if(match.find()){
        val r = match.group(1).toFloat()
        val g = match.group(2).toFloat()
        val b = match.group(3).toFloat()
        RgbColor(r,g,b)
    }else{
        null
    }
}

private val floatFormatter = DecimalFormat("#.#####")
internal val RgbColor.toColorLine: CharSequence
    get(){
        return String.format("%s %s %s rg",
            floatFormatter.format(red),
            floatFormatter.format(green),
            floatFormatter.format(blue))
    }