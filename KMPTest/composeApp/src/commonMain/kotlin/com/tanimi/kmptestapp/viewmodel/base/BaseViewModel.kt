package com.tanimi.kmptestapp.viewmodel.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class BaseViewModel: ViewModel() {
    protected val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
}