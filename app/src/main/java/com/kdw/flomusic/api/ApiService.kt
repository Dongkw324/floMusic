package com.kdw.flomusic.api

import com.kdw.flomusic.model.Song
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{songJson}")
    fun getSong(@Path("songJson") songJson: String): Call<Song>

}