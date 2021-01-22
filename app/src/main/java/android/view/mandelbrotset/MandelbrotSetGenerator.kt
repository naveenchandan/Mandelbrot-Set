package android.view.mandelbrotset

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log


class MandelbrotSetGenerator {

    fun generateMandelbrotSet(width: Float, height: Float): Bitmap {
        val startTime = System.currentTimeMillis()
        val bitmap = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
        for (y in 0 until bitmap.height) {
            for (x in 0 until bitmap.width) {
                bitmap.setPixel(x, y, Color.RED)
                val mappedX = map(x.toFloat(), 0.0f, width, START_X, END_X)
                val mappedY = map(y.toFloat(), 0.0f, height, START_Y, END_Y)
                var count = 0
                var xx = 0.0f
                var yy = 0.0f
                while (count < MAX_ITERATIONS) {
                    val x1 = xx * xx - yy * yy + mappedX
                    yy = 2 * xx * yy + mappedY
                    xx = x1
                    count++
                    if ((xx * xx) + (yy * yy) > 4.0f) {
                        break
                    }
                }
                if (count < MAX_ITERATIONS) {
                    val hue = map(count.toFloat(), 0.0f, MAX_ITERATIONS.toFloat(), 0.0f, 360.0f)
                    val color = Color.HSVToColor(floatArrayOf(hue, 1.0f, 1.0f))
                    bitmap.setPixel(x, y, Color.argb(255, color, color, color))
                } else {
                    bitmap.setPixel(x, y, Color.BLACK)
                }
            }
        }
        Log.d(
            "MandelbrotSetGenerator",
            "generateMandelbrotSet: total time: ${System.currentTimeMillis() - startTime}"
        )
        return bitmap
    }

    private companion object {
        const val START_X = -3.0F
        const val END_X = 1.0F
        const val START_Y = -2.0F
        const val END_Y = 2.0F
        const val MAX_ITERATIONS = 100
    }

    private fun map(
        value: Float,
        startValue1: Float,
        endValue1: Float,
        startValue2: Float,
        endValue2: Float
    ): Float =
        (value - startValue1) / (endValue1 - startValue1) * (endValue2 - startValue2) + startValue2
}