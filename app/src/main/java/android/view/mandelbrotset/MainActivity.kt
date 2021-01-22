package android.view.mandelbrotset

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cl_parent.doOnPreDraw {
            MandelbrotSetGenerator().apply {
                lifecycleScope.launch(Dispatchers.Default) {
                    val bitmap = generateMandelbrotSet(it.width.toFloat(), it.width.toFloat())
                    lifecycleScope.launch {
                        image_mandelbrot.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
}