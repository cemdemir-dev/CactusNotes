package com.example.cactusnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.databinding.ActivitySignupBinding
import com.google.android.material.textfield.TextInputLayout
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
            validate(binding.passwordInputLayout)
            validate(binding.emailInputLayout)
            validate(binding.usernameInputLayout)
        }
        api.register(RegisterRequest())
    }

    private fun validate(textInputLayout: TextInputLayout) {
        val validator = textInputLayout.validator()
        val field = textInputLayout.editText!!.text.toString()
        val error = validator.validate(field)

        if (error == null) {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
        } else {
            textInputLayout.error = getString(error)
        }
    }

    private fun TextInputLayout.validator() = when (this) {
        binding.usernameInputLayout -> UsernameValidator()
        binding.emailInputLayout -> EmailValidator()
        binding.passwordInputLayout -> PasswordValidator()
        else -> throw IllegalArgumentException("No validators are specified for the given TextInputLayout")
    }
}
