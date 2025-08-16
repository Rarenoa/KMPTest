package com.tanimi.kmptestapp.service.ocr

interface OCRService {
    suspend fun executeOCR(image: ByteArray): String
}

