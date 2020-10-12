package com.developerartemmotuznyi.pikabufeed.presentation.ui.feed

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.developerartemmotuznyi.pikabufeed.domain.model.Post
import com.developerartemmotuznyi.pikabufeed.domain.usecase.FetchSavedPostUseCase
import com.developerartemmotuznyi.pikabufeed.domain.usecase.GedPostsUseCase
import com.developerartemmotuznyi.pikabufeed.domain.usecase.RemovePostUseCase
import com.developerartemmotuznyi.pikabufeed.domain.usecase.SavePostUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FeedViewModel @ViewModelInject constructor(
    private val getPostsUseCase: GedPostsUseCase,
    private val fetchSavedPostUseCase: FetchSavedPostUseCase,
    private val removePostUseCase: RemovePostUseCase,
    private val savedPostUseCase: SavePostUseCase
) : ViewModel() {

    private val _updatedPost = MutableLiveData(-1L)

    val allPosts: LiveData<List<Post>> = _updatedPost.switchMap {
        liveData {
            getPostsUseCase().handle(
                {
                    emit(it)
                },
                {
                    emit(emptyList<Post>())
                })
        }

    }
    val savedPosts: LiveData<List<Post>> = liveData {
        fetchSavedPostUseCase().catch { emit(emptyList()) }.collect(::emit)
    }

    fun updateStatePost(post: Post) {
        if (post.isSaved) {
            removePost(post)
        } else {
            savedPost(post)
        }
    }

    fun refresh() {
        _updatedPost.postValue(-1)
    }

    private fun savedPost(post: Post) {
        viewModelScope.launch {
            savedPostUseCase(post).handle({
                _updatedPost.postValue(post.id)
            }, {})
        }
    }

    private fun removePost(post: Post) {
        viewModelScope.launch {
            removePostUseCase(post).handle({
                _updatedPost.postValue(post.id)
            }, {})
        }
    }


}
