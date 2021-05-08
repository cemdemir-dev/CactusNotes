package com.example.cactusnotes.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.R
import com.example.cactusnotes.api.NotesApi
import com.example.cactusnotes.databinding.ActivitySignupBinding
import com.example.cactusnotes.login.LoginActivity
import com.example.cactusnotes.signup.data.RegisterRequest
import com.example.cactusnotes.signup.data.RegisterResponse
import com.example.cactusnotes.signup.validation.EmailValidator
import com.example.cactusnotes.signup.validation.PasswordValidator
import com.example.cactusnotes.signup.validation.UsernameValidator
import com.example.cactusnotes.validation.validate
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    private val api: NotesApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://apps.cactus.school")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NotesApi::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.sign_up_tool_bar_name)

        binding.apply {
            signUpButton.setOnClickListener {
                val isAllValid = emailInputLayout.validate(EmailValidator()) and
                        passwordInputLayout.validate(PasswordValidator()) and
                        usernameInputLayout.validate(UsernameValidator())

                if (isAllValid) {
                    sendRegisterRequest()
                }
            }

            alreadyHaveAnAccountButton.setOnClickListener {
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun sendRegisterRequest() {
        val request = RegisterRequest(
            binding.emailInputLayout.editText!!.text.toString(),
            binding.usernameInputLayout.editText!!.text.toString(),
            binding.passwordInputLayout.editText!!.text.toString()
        )

        api.register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                when (response.code()) {
                    in 200..299 -> registerSuccess()
                    in 400..499 -> clientSideError(response)
                    in 500..599 -> serverSideError()
                    else -> Log.e("SignupActivity", "Unexpected error code")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Snackbar.make(
                    binding.signUpButton,
                    R.string.couldnt_connect_to_servers,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun registerSuccess() {
        Snackbar.make(
            binding.signUpButton,
            R.string.registered,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun clientSideError(response: Response<RegisterResponse>) {
        val responseObj = JSONObject(response.errorBody()!!.string())

        val clientSideErrorMessage = responseObj.getJSONArray("message")
            .getJSONObject(0)
            .getJSONArray("messages")
            .getJSONObject(0)
            .getString("message")

        Snackbar.make(
            binding.signUpButton,
            clientSideErrorMessage,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun serverSideError() {
        Snackbar.make(
            binding.signUpButton,
            R.string.some_error_occurred,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
