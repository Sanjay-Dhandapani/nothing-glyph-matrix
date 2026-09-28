package com.example.nothingglyph.toy

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Messenger
import android.os.SystemClock
import com.nothing.ketchum.Glyph
import com.nothing.ketchum.GlyphMatrixFrame
import com.nothing.ketchum.GlyphMatrixManager
import com.nothing.ketchum.GlyphMatrixObject
import com.nothing.ketchum.GlyphToy

class CustomGlyphToyService : Service() {

    private val handler = Handler(Looper.getMainLooper()) { message ->
        if (message.what == GlyphToy.MSG_GLYPH_TOY) {
            val bundle = message.data
            val event = bundle.getString(GlyphToy.MSG_GLYPH_TOY_DATA)
            if (event == GlyphToy.EVENT_AOD) {
                startAnimation()
            }
            true
        } else {
            false
        }
    }

    private val messenger = Messenger(handler)
    private var glyphManager: GlyphMatrixManager? = null
    private var animation: GlyphAnimation? = null
    private var animationIndex = 0
    private val animationTicker = object : Runnable {
        override fun run() {
            val localAnimation = animation ?: return
            val frame = localAnimation.frames[animationIndex]
            showFrame(frame)
            animationIndex = (animationIndex + 1) % localAnimation.frames.size
            handler.postDelayed(this, frame.durationMs.toLong())
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        initGlyphManager()
        return messenger.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        handler.removeCallbacks(animationTicker)
        glyphManager?.turnOff()
        glyphManager?.closeSession()
        glyphManager?.unInit()
        glyphManager = null
        return super.onUnbind(intent)
    }

    private fun initGlyphManager() {
        if (glyphManager != null) {
            return
        }

        glyphManager = GlyphMatrixManager.getInstance(applicationContext)
        glyphManager?.init(object : GlyphMatrixManager.Callback {
            override fun onServiceConnected(componentName: ComponentName) {
                glyphManager?.register(Glyph.DEVICE_25111p)
                glyphManager?.openSession()
                animation = GlyphAnimationLoader.load(applicationContext)
                startAnimation()
            }

            override fun onServiceDisconnected(componentName: ComponentName) = Unit
        })
    }

    private fun startAnimation() {
        val localAnimation = animation ?: GlyphAnimationLoader.load(applicationContext).also {
            animation = it
        }

        handler.removeCallbacks(animationTicker)
        animationIndex = 0
        showFrame(localAnimation.frames.first())
        animationIndex = 1 % localAnimation.frames.size
        handler.postAtTime(animationTicker, SystemClock.uptimeMillis() + localAnimation.frames.first().durationMs)
    }

    private fun showFrame(frameData: GlyphFrameData) {
        val bitmap = GlyphBitmapRenderer.render(frameData)
        val glyphObject = GlyphMatrixObject.Builder()
            .setImageSource(bitmap)
            .setScale(100)
            .setOrientation(0)
            .setPosition(0, 0)
            .build()

        val frame = GlyphMatrixFrame.Builder()
            .addTop(glyphObject)
            .build(applicationContext)

        glyphManager?.setMatrixFrame(frame.render())
    }
}
