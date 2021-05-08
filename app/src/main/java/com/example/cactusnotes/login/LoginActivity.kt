package com.example.cactusnotes.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.ActivityLoginBinding
import com.example.cactusnotes.login.validation.NotEmptyValidator
import com.example.cactusnotes.signup.SignupActivity
import com.example.cactusnotes.validation.validate

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

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
                    // sendLoginRequest()
                }
            }

            createAccountButton.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(intent)
            }
        }
    }
}