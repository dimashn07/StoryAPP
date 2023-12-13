package com.dimashn.storyapphn.data

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dimashn.storyapphn.data.model.Story
import com.dimashn.storyapphn.data.network.api.ApiService
import com.dimashn.storyapphn.data.preference.UserPreferences
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class StoryPagingSource(private val apiService: ApiService, private val pref: UserPreferences) : PagingSource<Int, Story>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val token = "Bearer ${pref.getUserData().first().token}"

            val responseData = apiService.getStory(token, page, params.loadSize)
            val data = responseData.listStory

            LoadResult.Page(
                data = data,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException){
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let {
                val anchorPage = state.closestPageToPosition(position)
                val nextPage = anchorPage?.nextKey
                nextPage?.takeIf { it > 1 } ?: anchorPage?.prevKey?.takeIf { it >= 1 }
            }
        }
    }

}