package com.example.cactusnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.sign_up_tool_bar_name)

        binding.signUpButton.setOnClickListener {
            val username = binding.usernameInputLayout.editText!!.text.toString()
            val password = binding.passwordInputLayout.editText!!.text.toString()
            val email = binding.emailInputLayout.editText!!.text.toString()

            val passwordValidator = PasswordValidator()
            val passwordError = passwordValidator.validate(password)
            if (passwordError == null) {
                binding.passwordInputLayout.error = null
                binding.passwordInputLayout.isErrorEnabled = false
            } else {
                binding.passwordInputLayout.error = getString(passwordError)
            }

            val emailValidator = EmailValidator()
            val emailError = emailValidator.validate(email)
            if (emailError == null) {
                binding.emailInputLayout.error = null
                binding.emailInputLayout.isErrorEnabled = false
            } else {
                binding.emailInputLayout.error = getString(emailError)
            }

            val usernameValidator = UsernameValidator()
            val usernameError = usernameValidator.validate(username)
            if (usernameError == null) {
                binding.usernameInputLayout.error = null
                binding.usernameInputLayout.isErrorEnabled = false
            } else {
                binding.usernameInputLayout.error = getString(usernameError)
            }
        }
    }
}
