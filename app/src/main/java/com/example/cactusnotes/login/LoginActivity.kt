package com.example.cactusnotes.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.ActivityLoginBinding
import com.example.cactusnotes.login.validation.NotEmptyValidator

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    val passwordValidator = NotEmptyValidator(R.string.password_is_required)
    val identifierValidator = NotEmptyValidator(R.string.e_mail_or_username_required)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.login_tool_bar_name)
    }
}
