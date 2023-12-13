package com.dimashn.storyapphn.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dimashn.storyapphn.databinding.ActivityMainBinding
import com.dimashn.storyapphn.ui.adapter.LoadingStateAdapter
import com.dimashn.storyapphn.ui.adapter.StoryAdapter
import com.dimashn.storyapphn.ui.maps.MapsActivity
import com.dimashn.storyapphn.ui.story.AddStoryActivity
import com.dimashn.storyapphn.ui.login.LoginActivity
import com.dimashn.storyapphn.util.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var mainViewModel: MainViewModel
    private var isLoad: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Story"
            show()
        }

        setupViewModel()
        setupView()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        val menu = binding.menu

        binding.logOut.setOnClickListener {
            showLoadingIndicator(true)
            mainViewModel.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        binding.addStory.setOnClickListener{
            startActivity(Intent(this, AddStoryActivity::class.java))
            menu.close(false)
        }
        binding.maps.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
            menu.close(false)
        }
    }

    private fun setupView() {
        storyAdapter = StoryAdapter()

        mainViewModel.getUser().observe(this@MainActivity){ user ->
            if (user.isLogin){
                setStory()
                setupSwipeToRefresh()
            }
            else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        with(binding.rvStory) {
            setHasFixedSize(true)
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter{
                    storyAdapter.retry()
                })
        }
    }

    private fun setStory() {
        mainViewModel.getStory().observe(this@MainActivity) {
            storyAdapter.submitData(lifecycle, it)
            showLoadingIndicator(false)
        }
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)

        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun showLoadingIndicator(isLoad: Boolean) {
        this.isLoad = isLoad
        binding.progressBar.visibility = if (isLoad) View.VISIBLE else View.GONE
    }

    private fun setupSwipeToRefresh() {
        binding.swipeToRefresh.setOnRefreshListener {
            storyAdapter.refresh()
            setStory()
            binding.swipeToRefresh.isRefreshing = false
        }
    }
}