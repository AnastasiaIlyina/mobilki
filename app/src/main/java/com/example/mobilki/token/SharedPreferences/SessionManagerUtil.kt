package com.example.mobilki.token.SharedPreferences

import android.content.Context
import java.util.*


object SessionManagerUtil {

    private const val SESSION_PREFERENCES =
        "ke.co.skyline-design.session_manager.SESSION_PREFERENCES"
    private const val SESSION_EXPIRY_TIME =
        "ke.co.skyline-design.session_manager.SESSION_EXPIRY_TIME"

    fun startUserSession(context: Context, expiresIn: Int) {
        val calendar = Calendar.getInstance()
        val userLoggedInTime = calendar.time
        calendar.time = userLoggedInTime
        calendar.add(Calendar.SECOND, expiresIn)
        val expiryTime = calendar.time
        val editor = context.getSharedPreferences(SESSION_PREFERENCES, 0).edit()
        editor.putLong(SESSION_EXPIRY_TIME, expiryTime.time)
        editor.apply()
    }


    fun isSessionActive(currentTime: Date, context: Context): Boolean {
        val sessionExpiresAt = Date(getExpiryDateFromPreferences(context)!!)
        return !currentTime.after(sessionExpiresAt)
    }


    // получение времени истечения сессии из SharedPreferences
    private fun getExpiryDateFromPreferences(context: Context): Long? {
        return context.getSharedPreferences(SESSION_PREFERENCES, 0).getLong(SESSION_EXPIRY_TIME, 0)
    }

    fun endUserSession(context: Context) {
        clearStoredData(context)
    }

    private fun clearStoredData(context: Context) {
        val editor = context.getSharedPreferences(SESSION_PREFERENCES, 0).edit()
        editor.clear()
        editor.apply()
    }

    fun printToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences(
            "ke.co.skyline-design.session_manager.SESSION_PREFERENCES",
            Context.MODE_PRIVATE
        )
        val token = sharedPreferences.getLong("ke.co.skyline-design.session_manager.SESSION_EXPIRY_TIME", 0)
        println("Token: $token")
    }

}

