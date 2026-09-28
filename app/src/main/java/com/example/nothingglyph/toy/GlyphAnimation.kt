package com.example.nothingglyph.toy

data class GlyphFrameData(
    val pixels: List<Int>,
    val durationMs: Int,
) {
    init {
        require(pixels.isNotEmpty()) { "Glyph frame cannot be empty." }
        val side = kotlin.math.sqrt(pixels.size.toDouble()).toInt()
        require(side * side == pixels.size) { "Glyph frame must be square." }
    }

    val size: Int = kotlin.math.sqrt(pixels.size.toDouble()).toInt()

    fun asPreviewText(): String {
        return pixels.chunked(size).joinToString(separator = "\n") { row ->
            row.joinToString(separator = "") { value ->
                if (value > 0) "#" else "."
            }
        }
    }
}

data class GlyphAnimation(
    val frames: List<GlyphFrameData>,
) {
    init {
        require(frames.isNotEmpty()) { "Glyph animation must contain at least one frame." }
    }

    val firstFrame: GlyphFrameData = frames.first()
}
