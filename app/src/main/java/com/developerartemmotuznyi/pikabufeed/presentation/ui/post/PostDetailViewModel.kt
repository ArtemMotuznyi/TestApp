package com.developerartemmotuznyi.pikabufeed.presentation.ui.post

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developerartemmotuznyi.pikabufeed.domain.model.Post
import com.developerartemmotuznyi.pikabufeed.domain.usecase.GetPostUseCase
import com.developerartemmotuznyi.pikabufeed.domain.usecase.RemovePostUseCase
import com.developerartemmotuznyi.pikabufeed.domain.usecase.SavePostUseCase
import kotlinx.coroutines.launch


class PostDetailViewModel @ViewModelInject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val removePostUseCase: RemovePostUseCase,
    private val savePostUseCase: SavePostUseCase
) : ViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = _post

    fun updateStatePost(post: Post) {
        if (post.isSaved) {
            removePost(post)
        } else {
            savedPost(post)
        }
    }

    fun loadPost(id: Long) {
        viewModelScope.launch {
            getPostUseCase(id).handle({
                _post.postValue(it)
            }, {})
        }
    }

    private fun savedPost(post: Post) {
        viewModelScope.launch {
            savePostUseCase(post).handle({
                loadPost(post.id)
            }, {})
        }
    }

    private fun removePost(post: Post) {
        viewModelScope.launch {
            removePostUseCase(post).handle({
                loadPost(post.id)
            }, {})
        }
    }
}
