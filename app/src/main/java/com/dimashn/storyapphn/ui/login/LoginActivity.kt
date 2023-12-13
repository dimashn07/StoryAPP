package com.dimashn.storyapphn.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dimashn.storyapphn.R
import com.dimashn.storyapphn.customview.MyButton
import com.dimashn.storyapphn.customview.MyEmailEditText
import com.dimashn.storyapphn.customview.PasswordEditText
import com.dimashn.storyapphn.data.Result
import com.dimashn.storyapphn.data.model.User
import com.dimashn.storyapphn.ui.main.MainActivity
import com.dimashn.storyapphn.databinding.ActivityLoginBinding
import com.dimashn.storyapphn.ui.register.RegisterActivity
import com.dimashn.storyapphn.util.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var myButton: MyButton
    private lateinit var myPasswordEditText: PasswordEditText
    private lateinit var myEmailEditText: MyEmailEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        myButton = binding.loginButton
        myPasswordEditText = binding.passwordEditText
        myEmailEditText = binding.emailEditText
        setMyButtonEnable()

        myPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        myEmailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun setupAction() {
        myButton.setOnClickListener {
            if (valid()) {
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                loginViewModel.userLogin(email, password).observe(this) {
                    when (it) {
                        is Result.Success -> {
                            showLoadingIndicator(false)
                            val response = it.data
                            saveUserData(
                                User(
                                    response.loginResult?.name.toString(),
                                    response.loginResult?.token.toString(),
                                    true
                                )
                            )
                            startActivity(Intent(this, MainActivity::class.java))
                            finishAffinity()
                        }
                        is Result.Loading -> showLoadingIndicator(true)
                        is Result.Error -> {
                            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                            showLoadingIndicator(false)
                        }
                    }
                }
            }else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.check_input),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.tvSignup.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val elements = listOf(
            binding.titleTextView,
            binding.messageTextView,
            binding.emailTextView,
            binding.emailEditTextLayout,
            binding.passwordTextView,
            binding.passwordEditTextLayout,
            binding.loginButton,
            binding.tvDontHaveAcc,
            binding.tvSignup
        )

        val animatorSet = AnimatorSet()
        val animations = elements.map {
            ObjectAnimator.ofFloat(it, View.ALPHA, 1f).apply {
                duration = 100
            }
        }

        animatorSet.playSequentially(animations)
        animatorSet.start()
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }

    private fun saveUserData(user: User) {
        loginViewModel.saveUser(user)
    }

    private fun showLoadingIndicator(isLoad: Boolean) {
        binding.progressBar.visibility = if (isLoad) View.VISIBLE else View.GONE
    }

    private fun valid(): Boolean {
        return binding.emailEditText.error == null &&
                binding.passwordEditText.error == null &&
                !binding.emailEditText.text.isNullOrEmpty() &&
                !binding.passwordEditText.text.isNullOrEmpty()
    }

    private fun setMyButtonEnable() {
        myButton.isEnabled = valid()
    }

}