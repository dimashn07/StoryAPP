package com.dimashn.storyapphn.ui.register

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
import com.dimashn.storyapphn.customview.InputNameEditText
import com.dimashn.storyapphn.customview.MyButton
import com.dimashn.storyapphn.customview.MyEmailEditText
import com.dimashn.storyapphn.customview.PasswordEditText
import com.dimashn.storyapphn.data.Result
import com.dimashn.storyapphn.databinding.ActivityRegisterBinding
import com.dimashn.storyapphn.ui.login.LoginActivity
import com.dimashn.storyapphn.util.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var myButton: MyButton
    private lateinit var myPasswordEditText: PasswordEditText
    private lateinit var myEmailEditText: MyEmailEditText
    private lateinit var myNameEditText: InputNameEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        myButton = binding.signupButton
        myPasswordEditText = binding.passwordEditText
        myEmailEditText = binding.emailEditText
        myNameEditText= binding.nameEditText
        setMyButtonEnable()

        myPasswordEditText.addTextChangedListener(object : TextWatcher { //dari sini
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        myEmailEditText.addTextChangedListener(object : TextWatcher { //dari sini
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        myNameEditText.addTextChangedListener(object : TextWatcher { //dari sini
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
        binding.tvLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        myButton.setOnClickListener{
            if (valid()) {
                val name = binding.nameEditText.text.toString()
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                registerViewModel.userRegister(name, email, password).observe(this) {
                    when (it) {
                        is Result.Success -> {
                            showLoadingIndicator(false)
                            Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finishAffinity()
                        }
                        is Result.Loading -> showLoadingIndicator(true)
                        is Result.Error -> {
                            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                            showLoadingIndicator(false)
                        }
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.check_input),
                    Toast.LENGTH_SHORT
                ).show()
            }
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
            binding.nameTextView,
            binding.nameEditTextLayout,
            binding.emailTextView,
            binding.emailEditTextLayout,
            binding.passwordTextView,
            binding.passwordEditTextLayout,
            binding.signupButton,
            binding.tvHaveAcc,
            binding.tvLogin
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
        registerViewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]
    }

    private fun showLoadingIndicator(isLoad: Boolean) {
        binding.progressBar.visibility = if (isLoad) View.VISIBLE else View.GONE
    }

    private fun valid(): Boolean {
        val emailValid = binding.emailEditText.error == null && !binding.emailEditText.text.isNullOrEmpty()
        val passwordValid = binding.passwordEditText.error == null && !binding.passwordEditText.text.isNullOrEmpty()
        val nameValid = binding.nameEditText.error == null && !binding.nameEditText.text.isNullOrEmpty()

        return emailValid && passwordValid && nameValid
    }

    private fun setMyButtonEnable() {
        myButton.isEnabled = valid()
    }
}