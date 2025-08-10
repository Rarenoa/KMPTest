package com.tanimi.kmptestapp.viewmodel

import com.tanimi.kmptestapp.service.HTTPClientService
import com.tanimi.kmptestapp.viewmodel.base.BaseViewModel
import kotlinx.coroutines.launch

class LineApiViewModel(
    private val httpClientService: HTTPClientService = HTTPClientService()
) : BaseViewModel() {

    fun sendLineMessage(message: String) {
        viewModelScope.launch {
            httpClientService.sendLineMessage(message)
        }
    }
}