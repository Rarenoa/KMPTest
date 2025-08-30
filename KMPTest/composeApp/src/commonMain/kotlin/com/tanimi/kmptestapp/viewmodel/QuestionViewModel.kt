package com.tanimi.kmptestapp.viewmodel

import com.tanimi.kmptestapp.data.entity.AnswerHistory
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

class QuestionViewModel(
    private val httpClientService: HTTPClientService = HTTPClientService()
) : BaseViewModel() {

    private val answerHistoryDao = getRoomDatabase(getRoomDatabaseBuilder()).getAnswerHistoryDao()
    private var _answerHistoryList = MutableStateFlow(emptyList<AnswerHistory>())
    val answerHistory: StateFlow<List<AnswerHistory>> = _answerHistoryList.asStateFlow()

    init {
        viewModelScope.launch {
            _answerHistoryList.value = answerHistoryDao.getAll()
        }
    }

    fun sendQuestion(question: String) {
        viewModelScope.launch {
            val answer = httpClientService.sendQuestion(question)
            answerHistoryDao.insert(AnswerHistory(
                id = getUuid(),
                question = question,
                answer = answer,
                created = dateTimeStringNow()
            ))
        }
    }
}