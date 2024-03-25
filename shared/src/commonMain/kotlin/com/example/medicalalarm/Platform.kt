package com.example.medicalalarm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform