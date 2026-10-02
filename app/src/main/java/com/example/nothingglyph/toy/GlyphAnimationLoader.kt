package com.example.nothingglyph.toy

import android.content.Context
import org.json.JSONObject

object GlyphAnimationLoader {

    private const val ASSET_FILE = "spider_mask.json"

    fun load(context: Context): GlyphAnimation {
        val jsonText = context.assets.open(ASSET_FILE).bufferedReader().use { it.readText() }
        val root = JSONObject(jsonText)
        val framesJson = root.getJSONArray("frames")
        val frames = buildList {
            for (index in 0 until framesJson.length()) {
                val frameJson = framesJson.getJSONObject(index)
                val duration = tuneDuration(frameJson.getInt("d"), index, framesJson.length())
                val pixelsArray = frameJson.getJSONArray("p")
                val pixels = buildList {
                    for (pixelIndex in 0 until pixelsArray.length()) {
                        add(pixelsArray.getInt(pixelIndex))
                    }
                }
                add(GlyphFrameData(pixels = pixels, durationMs = duration))
            }
        }

        return GlyphAnimation(frames)
    }

    private fun tuneDuration(sourceDurationMs: Int, frameIndex: Int, frameCount: Int): Int {
        val phase = when {
            frameIndex == 0 || frameIndex == frameCount - 1 -> 1.18f
            frameIndex == 2 -> 0.72f
            else -> 0.82f
        }

        return (sourceDurationMs * phase).toInt().coerceAtLeast(120)
    }
}
