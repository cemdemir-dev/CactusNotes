package com.example.cactusnotes.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.ActivityLoginBinding
import com.example.cactusnotes.login.validation.NotEmptyValidator
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    private val passwordValidator = NotEmptyValidator(R.string.password_is_required)
    private val identifierValidator = NotEmptyValidator(R.string.e_mail_or_username_required)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.login_tool_bar_name)

        binding.loginButton.setOnClickListener {
            if (validate(binding.identifierInputLayout)
                and validate(binding.passwordInputLayout)
            ) {
                // sendLoginRequest()
            }
        }
    }

    private fun validate(textInputLayout: TextInputLayout): Boolean {
        val validator = textInputLayout.validator()
        val field = textInputLayout.editText!!.text.toString()
        val error = validator.validate(field)

        return if (error == null) {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
            true
        } else {
            textInputLayout.error = getString(error)
            false
        }
    }

    private fun TextInputLayout.validator() = when (this) {
        binding.identifierInputLayout -> identifierValidator
        binding.passwordInputLayout -> passwordValidator
        else -> throw IllegalArgumentException("No validators are specified for the given TextInputLayout")
    }

}