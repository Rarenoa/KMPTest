package com.tanimi.kmptestapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform