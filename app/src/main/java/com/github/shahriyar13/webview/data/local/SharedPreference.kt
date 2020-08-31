package com.github.shahriyar13.webview.data.local

import com.github.shahriyar13.webview.App
import com.securepreferences.SecurePreferences

class SharedPreference {
    companion object {
        private val sharedPreferences = SecurePreferences(App.getContext())
        fun save(key: Key, value: String?) {
            sharedPreferences.edit().putUnencryptedString(key.name, value).apply()
        }

        fun save(key: Key, value: Boolean?) {
            sharedPreferences.edit().putUnencryptedString(key.name, value.toString()).apply()
        }

        fun getString(key: Key): String =
            sharedPreferences.getEncryptedString(key.name, "")

        fun getString(key: Key, default: String): String =
            sharedPreferences.getEncryptedString(key.name, default)

        fun getBoolean(key: Key): Boolean =
            sharedPreferences.getEncryptedString(key.name, "false")!!.toBoolean()
    }

    enum class Key {
        Address,
        ShowError
    }
}