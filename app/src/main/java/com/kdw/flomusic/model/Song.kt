package com.kdw.flomusic.model

data class Song(
    val singer: String = "",
    val album: String = "",
    val title: String = "",
    val duration: Long = -1,
    val image: String = "",
    val file: String = "",
    val lyrics: String = ""
)
