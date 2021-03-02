package com.bignerdranch.android.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(private val beatBox: BeatBox): BaseObservable() {
    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it)
        }
    }


    var sound: Sound? = null
        set(sound) {
            field = sound
//            Оповещает класс привязки о том что все Bindable свойства обновлены
            notifyChange()
        }
    @get: Bindable
    val title: String?
        get() = sound?.name

}