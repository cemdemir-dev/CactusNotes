package com.example.cactusnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.databinding.ActivitySignupBinding
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    val api: SignupAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://apps.cactus.school")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SignupAPI::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.sign_up_tool_bar_name)

        binding.signUpButton.setOnClickListener {
            if (validate(binding.emailInputLayout)
                and validate(binding.passwordInputLayout)
                and validate(binding.usernameInputLayout)
            ) {
                sendRegisterRequest()
            }
        }
    }

    private fun validate(textInputLayout: TextInputLayout): Boolean {
        val validator = textInputLayout.validator()
        val field = textInputLayout.editText!!.text.toString()
        val error = validator.validate(field)

        if (error == null) {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
            return true
        } else {
            textInputLayout.error = getString(error)
            return false
        }
    }

    private fun TextInputLayout.validator() = when (this) {
        binding.usernameInputLayout -> UsernameValidator()
        binding.emailInputLayout -> EmailValidator()
        binding.passwordInputLayout -> PasswordValidator()
        else -> throw IllegalArgumentException("No validators are specified for the given TextInputLayout")
    }

    fun sendRegisterRequest() {
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
                println("onResponse")
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

                Snackbar.make(binding., R.string.text_label, Snackbar.LENGTH_SHORT)
                    .show()
            }
        })
    }
}
