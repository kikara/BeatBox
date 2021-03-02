package com.bignerdranch.android.beatbox

import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.lang.Exception


private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"

private const val MAX_SOUNDS = 5

class BeatBox(private val assets: AssetManager) {

    val sounds: List<Sound>
    private val soundPool = SoundPool.Builder()
            .setMaxStreams(MAX_SOUNDS)
            .build()


    init {
        sounds = loadSounds()
    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    private fun loadSounds(): List<Sound> {

        val soundNames: Array<String>

        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        } catch (e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()
        soundNames.forEach {fileName ->
            val assetPath = "$SOUNDS_FOLDER/$fileName"
            val sound = Sound(assetPath)

            try {
                load(sound)
                sounds.add(sound)
            }
            catch (e: Exception) {
                Log.e(TAG, "Could not load sound $fileName")
            }
        }
        return sounds
    }

    fun load(sound:Sound) {
        val afd = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }

    fun release() {
        soundPool.release()
    }
}