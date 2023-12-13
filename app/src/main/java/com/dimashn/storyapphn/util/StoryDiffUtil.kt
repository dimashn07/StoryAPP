package com.dimashn.storyapphn.util

import androidx.recyclerview.widget.DiffUtil
import com.dimashn.storyapphn.data.model.Story

class StoryDiffUtil : DiffUtil.ItemCallback<Story>() {

    override fun areItemsTheSame(oldItemPosition: Story, newItemPosition: Story): Boolean {
        return oldItemPosition === newItemPosition
    }

    override fun areContentsTheSame(oldItemPosition: Story, newItemPosition: Story): Boolean {
        return oldItemPosition == newItemPosition
    }
}