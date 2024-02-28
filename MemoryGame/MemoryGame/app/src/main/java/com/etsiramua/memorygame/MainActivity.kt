package com.etsiramua.memorygame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.etsiramua.memorygame.R.color.*

class MainActivity : AppCompatActivity() {
    private val images = arrayOf(
        R.drawable.memo1,
        R.drawable.memo2,
        R.drawable.memo3,
        R.drawable.memo1,
        R.drawable.memo2,
        R.drawable.memo3,
    )

    private var firstImage: ImageView? = null
    private var secondImage: ImageView? = null

    private lateinit var imageViews: Array<ImageView>

    private var successCount: Int = 0
    private var failCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memoryGameReset()

        findViewById<Button>(R.id.restartButton).setOnClickListener {
            memoryGameReset()
        }
    }

    private fun memoryGameReset() {
        setUpTextViews()

        images.shuffle()

        imageViews = arrayOf(
            findViewById(R.id.imageView1),
            findViewById(R.id.imageView2),
            findViewById(R.id.imageView3),
            findViewById(R.id.imageView4),
            findViewById(R.id.imageView5),
            findViewById(R.id.imageView6),
        )

        imageViews.forEach { imageView ->
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.card_background)
        }

        imageViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                imageView.setImageResource(images[index])

                if (firstImage == null) {

                    firstImage = imageView
                    findViewById<TextView>(R.id.successTextView).setTextColor(resources.getColor(
                        gray))
                    findViewById<TextView>(R.id.failTextView).setTextColor(resources.getColor(
                        gray))
                } else if (secondImage == null && imageView != firstImage) {

                    secondImage = imageView

                    if (firstImage!!.drawable.constantState == secondImage!!.drawable.constantState) {
                        // Images match
                        Handler().postDelayed({
                            firstImage!!.visibility = View.INVISIBLE
                            secondImage!!.visibility = View.INVISIBLE

                            firstImage = null
                            secondImage = null

                            successCount++

                            val successText = getString(R.string.successText)
                            val successValue = "$successText $successCount"
                            findViewById<TextView>(R.id.successTextView).text = successValue
                            findViewById<TextView>(R.id.successTextView).setTextColor(resources.getColor(green))
                        }, 500)
                    } else {
                        // Images don't match
                        Handler().postDelayed({
                            firstImage!!.setImageResource(R.drawable.card_background)
                            secondImage!!.setImageResource(R.drawable.card_background)

                            firstImage = null
                            secondImage = null

                            failCount++
                            val failText = getString(R.string.failText)
                            val failValue = "$failText $failCount"
                            findViewById<TextView>(R.id.failTextView).text = failValue
                            findViewById<TextView>(R.id.failTextView).setTextColor(resources.getColor(
                                red))
                        }, 500)
                    }
                }
            }
        }

    }

    private fun setUpTextViews() {
        successCount = 0
        failCount = 0

        findViewById<TextView>(R.id.successTextView).text = getString(R.string.successTextInit)
        findViewById<TextView>(R.id.failTextView).text = getString(R.string.failTextInit)

        findViewById<TextView>(R.id.successTextView).setTextColor(resources.getColor(
            gray))
        findViewById<TextView>(R.id.failTextView).setTextColor(resources.getColor(
            gray))
    }
}