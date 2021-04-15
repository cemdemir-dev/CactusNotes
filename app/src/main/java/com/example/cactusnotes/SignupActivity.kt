package com.example.cactusnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    fun String.containsLowerCase() = any { it.isLowerCase() }
    fun String.containsUpperCase() = any { it.isUpperCase() }
    fun String.containsDigit() = any { it.isDigit() }
    fun Char.isSpecial() = !isDigit() && !isLetter()
    fun String.containsSpecialChars() = any { it.isSpecial() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.tool_bar_name)

        binding.signUpButton.setOnClickListener {
            val username = binding.usernameInputLayout.editText!!.text.toString()
            val password = binding.passwordInputLayout.editText!!.text.toString()
            val email = binding.emailInputLayout.editText!!.text.toString()

            when {
                password.isBlank() -> {
                    binding.passwordInputLayout.error = getString(R.string.password_is_required)
                }
                !password.containsLowerCase() || !password.containsDigit() ||
                        !password.containsUpperCase() || !password.containsSpecialChars() -> {
                    binding.passwordInputLayout.error = getString(R.string.password_must_contain)
                }
                password.length < 8 -> {
                    binding.passwordInputLayout.error = getString(R.string.password_too_short)
                }
                password.length > 39 -> {
                    binding.passwordInputLayout.error = getString(R.string.password_too_long)
                }
                else -> {
                    binding.passwordInputLayout.error = null
                    binding.passwordInputLayout.isErrorEnabled = false
                }
            }

            when {
                email == "" -> {
                    binding.emailInputLayout.error = getString(R.string.e_mail_required)
                }
                !email.contains("@") && !email.contains(".") -> {
                    binding.emailInputLayout.error = getString(R.string.e_mail_invalid)
                }
                !email.contains(".") -> {
                    binding.emailInputLayout.error = getString(R.string.e_mail_invalid)
                }
                email.length < 6 -> {
                    binding.emailInputLayout.error = getString(R.string.e_mail_invalid)
                }
                email.length > 49 -> {
                    binding.emailInputLayout.error = getString(R.string.e_mail_invalid)
                }
                else -> {
                    binding.emailInputLayout.error = null
                    binding.emailInputLayout.isErrorEnabled = false
                }
            }

            when {
                username == "" -> {
                    binding.usernameInputLayout.error = getString(R.string.username_is_required)
                }
                username.length < 3 -> {
                    binding.usernameInputLayout.error = getString(R.string.username_too_short)
                }
                username.length > 19 -> {
                    binding.usernameInputLayout.error = getString(R.string.username_too_long)
                }
                !username.all { it.isLowerCase() || it.isDigit() || it == '_' } -> {
                    binding.usernameInputLayout.error =
                        getString(R.string.username_can_only_include)
                }
                else -> {
                    binding.usernameInputLayout.error = null
                    binding.usernameInputLayout.isErrorEnabled = false
                }
            }
        }
    }
}
