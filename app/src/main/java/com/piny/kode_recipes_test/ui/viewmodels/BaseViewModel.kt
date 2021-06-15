package com.piny.kode_recipes_test.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.piny.kode_recipes_test.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest

open class BaseViewModel<T> : ViewModel() {
    protected val _state: MutableStateFlow<Result<T>> =
        MutableStateFlow(Result.Empty())
    val state: StateFlow<Result<T>> = _state

    @ExperimentalCoroutinesApi
    protected val _isLoading = _state.mapLatest { it.isLoading() }
        .asLiveData() //for ui progress bar (waiting for AS 4.3 release :( )
    @ExperimentalCoroutinesApi
    val isLoading: LiveData<Boolean> = _isLoading
}