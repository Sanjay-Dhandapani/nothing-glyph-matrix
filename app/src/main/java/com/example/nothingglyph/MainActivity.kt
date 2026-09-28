package com.example.nothingglyph

import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nothingglyph.toy.GlyphBitmapRenderer
import com.example.nothingglyph.toy.GlyphAnimationLoader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val previewImage = findViewById<ImageView>(R.id.previewImage)
        val previewText = findViewById<TextView>(R.id.previewText)
        val animation = GlyphAnimationLoader.load(this)
        val bitmap: Bitmap = GlyphBitmapRenderer.render(animation.firstFrame)

        previewImage.setImageBitmap(bitmap)
        previewText.text = animation.firstFrame.asPreviewText()

        findViewById<Button>(R.id.openToysButton).setOnClickListener {
            openGlyphToysManager()
        }

        findViewById<Button>(R.id.refreshButton).setOnClickListener {
            val refreshedAnimation = GlyphAnimationLoader.load(this)
            previewImage.setImageBitmap(GlyphBitmapRenderer.render(refreshedAnimation.firstFrame))
            previewText.text = refreshedAnimation.firstFrame.asPreviewText()
            Toast.makeText(this, R.string.preview_refreshed, Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGlyphToysManager() {
        runCatching {
            startActivity(
                Intent().setComponent(
                    ComponentName(
                        "com.nothing.thirdparty",
                        "com.nothing.thirdparty.matrix.toys.manager.ToysManagerActivity",
                    ),
                ),
            )
        }.onFailure {
            Toast.makeText(this, R.string.manager_not_available, Toast.LENGTH_LONG).show()
        }
    }
}
