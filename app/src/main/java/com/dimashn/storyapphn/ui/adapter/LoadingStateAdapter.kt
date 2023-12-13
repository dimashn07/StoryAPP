package com.dimashn.storyapphn.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dimashn.storyapphn.databinding.ItemLoadingBinding

class LoadingStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingStateViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class LoadingStateViewHolder(private val binding: ItemLoadingBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            when (loadState) {
                is LoadState.Error -> {
                    errorMsg.text = loadState.error.localizedMessage
                    progressBar.isVisible = false
                    retryButton.isVisible = true
                    errorMsg.isVisible = true
                }
                is LoadState.Loading -> {
                    progressBar.isVisible = true
                    retryButton.isVisible = false
                    errorMsg.isVisible = false
                }
                else -> {
                    progressBar.isVisible = false
                    retryButton.isVisible = false
                    errorMsg.isVisible = false
                }
            }
        }
    }
}
