package com.neogov.automationtestdemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.neogov.automationtestdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.emailError.observe(this) { error ->
            binding.emailInput.error = error
        }

        viewModel.passwordError.observe(this) { error ->
            binding.passwordInput.error = error
        }

        viewModel.loginResult.observe(this) { result ->
            binding.emailInput.setText("")
            binding.passwordInput.setText("")
           // Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupListeners() {
        binding.emailInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                viewModel.clearEmailError()
            }
        }

        binding.passwordInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                viewModel.clearPasswordError()
            }
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            viewModel.onLoginClicked(email, password)
        }
    }
}