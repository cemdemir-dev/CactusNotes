package com.example.cactusnotes.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.R
import com.example.cactusnotes.api.NotesApi
import com.example.cactusnotes.databinding.ActivityLoginBinding
import com.example.cactusnotes.login.data.LoginRequest
import com.example.cactusnotes.login.data.LoginResponse
import com.example.cactusnotes.login.validation.NotEmptyValidator
import com.example.cactusnotes.signup.SignupActivity
import com.example.cactusnotes.validation.validate
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    private val api: NotesApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://apps.cactus.school")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NotesApi::class.java)
    }

    private val passwordValidator = NotEmptyValidator(R.string.password_is_required)
    private val identifierValidator = NotEmptyValidator(R.string.e_mail_or_username_required)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.login_tool_bar_name)

        binding.apply {
            loginButton.setOnClickListener {
                if (identifierInputLayout.validate(identifierValidator)
                    and passwordInputLayout.validate(passwordValidator)
                ) {
                    sendLoginRequest()
                }
            }

            createAccountButton.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun sendLoginRequest() {
        val request = LoginRequest(
            binding.identifierInputLayout.editText!!.text.toString(),
            binding.passwordInputLayout.editText!!.text.toString()
        )

        api.register(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                when (response.code()) {
                    400 -> badRequest()
                }
            }
        }
        )

    }

    private fun badRequest() {
        Snackbar.make(
            binding.loginButton,
            R.string.invalid_credentials,
            Snackbar.LENGTH_LONG
        ).show()
    }
}