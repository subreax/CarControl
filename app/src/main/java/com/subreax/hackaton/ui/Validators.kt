package com.subreax.hackaton.ui

object Validators {
    private val emailRegex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")

    fun isEmailCorrect(email: String): Boolean {
        return email.matches(emailRegex)
    }
}