package com.dimashn.storyapphn.ui.detailstory

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.dimashn.storyapphn.data.model.Story
import com.dimashn.storyapphn.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater )
        setContentView(binding.root)
        supportActionBar?.hide()

        setupView()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setupView() {
        val detail = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DETAIL, Story::class.java) as Story
        }else {
            intent.getParcelableExtra<Story>(EXTRA_DETAIL) as Story
        }

        binding.apply {
            tvNameDetail.text = detail.name
            tvDesc.text = detail.description
        }
        Glide.with(this)
            .load(detail.photoUrl)
            .into(binding.imgStoryDetail)
    }
}