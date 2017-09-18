package com.wengelef.kleanmvp.data

import android.content.SharedPreferences

fun SharedPreferences.commit(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.commit()
}