package com.dimashn.storyapphn.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dimashn.storyapphn.ui.detailstory.DetailStoryActivity
import com.dimashn.storyapphn.data.model.Story
import com.dimashn.storyapphn.databinding.ItemStoryBinding
import com.dimashn.storyapphn.util.DateFormatter
import com.dimashn.storyapphn.util.StoryDiffUtil
import java.util.TimeZone

class StoryAdapter : PagingDataAdapter<Story, StoryAdapter.StoryViewHolder>(StoryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { holder.bind(it) }
    }

    inner class StoryViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(binding.ivStoryPhoto)
                tvStoryTitle.text = story.name
                tvItemCreatedAt.text = DateFormatter.formatDate(story.createdAt, TimeZone.getDefault().id)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailStoryActivity::class.java).apply {
                    putExtra(DetailStoryActivity.EXTRA_DETAIL, story)
                }
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivStoryPhoto, "image"),
                        Pair(binding.tvStoryTitle, "name"),
                    )
                it.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }
}
