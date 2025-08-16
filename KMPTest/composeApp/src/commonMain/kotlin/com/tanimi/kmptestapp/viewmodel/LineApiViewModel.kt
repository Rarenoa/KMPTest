package com.tanimi.kmptestapp.viewmodel

import com.tanimi.kmptestapp.data.entity.MessageHistory
import com.tanimi.kmptestapp.data.getRoomDatabase
import com.tanimi.kmptestapp.dateTimeStringNow
import com.tanimi.kmptestapp.getRoomDatabaseBuilder
import com.tanimi.kmptestapp.getUuid
import com.tanimi.kmptestapp.service.serverAccess.HTTPClientService
import com.tanimi.kmptestapp.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LineApiViewModel(
    private val httpClientService: HTTPClientService = HTTPClientService()
) : BaseViewModel() {

    private val messageHistoryDao = getRoomDatabase(getRoomDatabaseBuilder()).getMessageHistoryDao()
    private var _messageHistoryList = MutableStateFlow(emptyList<MessageHistory>())
    val messageHistory: StateFlow<List<MessageHistory>> = _messageHistoryList.asStateFlow()

    init {
        viewModelScope.launch {
            _messageHistoryList.value = messageHistoryDao.getAll()
        }
    }

    fun sendLineMessage(message: String) {
        viewModelScope.launch {
            httpClientService.sendLineMessage(message)
        }
    }

    fun saveMessage(message: String) {
        val history = MessageHistory(
            id = getUuid(),
            message = message,
            created = dateTimeStringNow()
        )
        viewModelScope.launch {
            messageHistoryDao.insert(history)
        }
    }
}