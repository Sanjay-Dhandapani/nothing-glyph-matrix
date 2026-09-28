package com.example.nothingglyph.toy

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

object GlyphBitmapRenderer {

    fun render(frameData: GlyphFrameData): Bitmap {
        val bitmap = Bitmap.createBitmap(frameData.size, frameData.size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        frameData.pixels.chunked(frameData.size).forEachIndexed { y, row ->
            row.forEachIndexed { x, cell ->
                paint.color = colorFor(cell)
                canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
            }
        }

        return bitmap
    }

    private fun colorFor(value: Int): Int {
        val brightness = value.coerceIn(0, 255)
        if (brightness <= 0) {
            return Color.TRANSPARENT
        }

        return when {
            brightness < 40 -> Color.rgb(32, 0, 8)
            brightness < 90 -> Color.rgb(92, 0, 18)
            brightness < 170 -> Color.rgb(194, 18, 38)
            brightness < 235 -> Color.rgb(255, 84, 90)
            else -> Color.rgb(255, 240, 242)
        }
    }
}
