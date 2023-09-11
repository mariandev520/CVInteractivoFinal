package com.example.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var userNameTextView: TextView
    private lateinit var userLocationTextView: TextView
    private lateinit var userPhoneTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var userAvatarImageView: ImageView
    private lateinit var nombreEditText: EditText
    private lateinit var telefonoEditText: EditText
    private lateinit var cambiarCvButton: Button
    private lateinit var openChatButton: Button
    private lateinit var enviarFormularioButton: Button

    private lateinit var callApi: CallApi
    private lateinit var utilities: Utilities

    private var cvNuevo = ""
    private var booleano = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        initializeButtons()
        initializeAPI()

        // Assign an initial value to cvNuevo
        updateCvValue()

        // Initialize the user view
        fetchDataFromApi()
    }

    private fun initializeViews() {
        userAvatarImageView = findViewById(R.id.userAvatarImageView)
        userNameTextView = findViewById(R.id.userNameTextView)
        userPhoneTextView = findViewById(R.id.userPhoneTextView)
        userLocationTextView = findViewById(R.id.userLocationTextView)
        userEmailTextView = findViewById(R.id.userEmailTextView)
        nombreEditText = findViewById(R.id.nombreEditText)
        telefonoEditText = findViewById(R.id.telefonoEditText)
    }

    private fun initializeButtons() {
        cambiarCvButton = findViewById(R.id.cambiarCvButton)
        openChatButton = findViewById(R.id.openChatButton)
        enviarFormularioButton = findViewById(R.id.enviarFormularioButton)

        cambiarCvButton.setOnClickListener {
            updateCvValue()
            fetchDataFromApi()
        }

        openChatButton.setOnClickListener {
            val phoneNumber = userPhoneTextView.text.toString()
            utilities.openWhatsappChat(phoneNumber)
        }

        enviarFormularioButton.setOnClickListener {
            submitForm()
        }
    }

    private fun initializeAPI() {
        callApi = CallApi()
        utilities = Utilities(this)
    }

    private fun updateCvValue() {
        val (newBoolean, newCv) = callApi.changeValue(booleano)
        booleano = newBoolean
        cvNuevo = newCv
    }

    private fun fetchDataFromApi() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val jsonResponse = callApi.fetchDataFromApi()
                val user = jsonResponse.getJSONArray("results").getJSONObject(0)

                val userName = "${user.getJSONObject("name").getString("first")} ${
                    user.getJSONObject("name").getString("last")
                }"
                val userEmail = user.getString("email")
                val userPhone = user.getString("phone")
                val userLocation =
                    "${user.getJSONObject("location").getString("city")}, ${
                        user.getJSONObject("location").getString("country")
                    }"

                val userImageUrl = user.getJSONObject("picture").getString("large")

                runOnUiThread {
                    userNameTextView.text = userName
                    userEmailTextView.text = userEmail
                    userPhoneTextView.text = userPhone
                    userLocationTextView.text = userLocation

                    // Update userAvatarImageView using Picasso
                    Picasso.get()
                        .load(userImageUrl)
                        .into(userAvatarImageView)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun submitForm() {
        val nombre = nombreEditText.text.toString()
        val telefono = telefonoEditText.text.toString()

        if (nombre.isEmpty() || telefono.isEmpty()) {
            utilities.showAlert("Please fill out all fields.")
            return
        }

        if (!utilities.validatePhoneNumber(telefono)) {
            utilities.showAlert("Please enter a valid phone number (between 7 and 11 digits).")
            return
        }

        utilities.showAlert("Thank you for contacting us, $nombre. We will get in touch with you soon.")

        // Clear the form
        nombreEditText.text.clear()
        telefonoEditText.text.clear()
    }
}


