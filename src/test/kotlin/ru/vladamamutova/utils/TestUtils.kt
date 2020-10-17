package ru.vladamamutova.utils

import com.google.gson.Gson

class TestUtils {
    companion object {
        fun <T> jsonToObject(json: String?, classOf: Class<T>?): T {
            return Gson().fromJson(json, classOf)
        }

        fun objectToJson(obj: Any?): String? {
            return Gson().toJson(obj)
        }
    }
}
