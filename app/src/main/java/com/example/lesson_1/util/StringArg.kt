package com.example.lesson_1.util

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object StringArg : ReadWriteProperty<Bundle, String?> {
    override fun getValue(thisRef: Bundle, property: KProperty<*>) =
        thisRef.getString(property.name)

    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: String?) =
        thisRef.putString(property.name, value)
}