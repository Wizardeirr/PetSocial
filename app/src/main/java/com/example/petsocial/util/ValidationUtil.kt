package com.example.petsocial.util

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast

object ValidationUtil {
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target.toString()).matches()
        }
    }

    internal fun isValidPassword(password: String): Resource<Boolean> {
        if (password.length <= 6){
            Resource.error("Lütfen şifrenizi 6 dan büyük olsun",null)
        }
        return Resource.success(true)
    }


    fun isValidePatname(target: CharSequence?) : Boolean {
        return true
    }
    fun showToast(context:Context){
        Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
    }
}