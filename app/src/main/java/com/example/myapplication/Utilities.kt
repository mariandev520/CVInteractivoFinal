package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AlertDialog

class Utilities(private val context: Context) {

    fun validatePhoneNumber(telefono: String): Boolean {
        val regexPhoneNumber = Regex("^[0-9]{7,11}$")
        return regexPhoneNumber.matches(telefono)
    }

    fun openWhatsappChat(phoneNumber: String) {
        val cleanedPhoneNumber = phoneNumber.replace("[()-\\s]".toRegex(), "")
        val whatsappUrl = "https://wa.me/$cleanedPhoneNumber"

        // Open the URL in a new browser window
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(whatsappUrl)
        context.startActivity(intent)
    }

    fun showAlert(message: String) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}