package com.kdw.flomusic.viewmodel


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.kdw.flomusic.api.ApiService
import com.kdw.flomusic.model.Lyrics
import com.kdw.flomusic.model.Song
import com.kdw.flomusic.util.Constants
import com.kdw.flomusic.util.Helper
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MusicViewModel(application: Application) : AndroidViewModel(application) {
    private val _musicData = MutableLiveData<Song>()
    private val _exoPlayer = MutableLiveData<SimpleExoPlayer>()
    private val _currentLyrics = MutableLiveData<Int>()
    private val _lyrics = MutableLiveData<List<Lyrics>>()
    private val _lyricsToggleBtn = MutableLiveData<Boolean>()
    private val music: ApiService by inject(ApiService::class.java)

    val musicData : LiveData<Song> = _musicData
    val exoPlayer : LiveData<SimpleExoPlayer> = _exoPlayer
    val currentLyrics: LiveData<Int> = _currentLyrics
    val lyrics: LiveData<List<Lyrics>> = _lyrics
    val lyricsToggleBtn: LiveData<Boolean> = _lyricsToggleBtn

    init {
        _lyricsToggleBtn.value = false
        _lyrics.value = mutableListOf()

        val context = getApplication<Application>().applicationContext
        _exoPlayer.postValue(SimpleExoPlayer.Builder(context).build())
    }

    fun getMusic() {
        music.getSong(Constants.songJson).enqueue(object: Callback<Song>{
            override fun onResponse(call: Call<Song>, response: Response<Song>) {
                if(response.body() != null) {
                    _musicData.value = response.body()
                    setLyrics()
                    setMusicFile()
                } else {
                    Log.e("fail", "Uploading Fail")
                }
            }

            override fun onFailure(call: Call<Song>, t: Throwable) {
                Log.e("fail", "Uploading Fail")
            }

        })
    }

    fun setLyrics() {
        _lyrics.value = Helper.setLyrics(_musicData.value!!.lyrics)
        _currentLyrics.postValue(-1)
    }

    fun setMusicFile() {
        _exoPlayer.value!!.addMediaItem(MediaItem.fromUri(_musicData.value!!.file))
        _exoPlayer.value!!.prepare()
    }

    fun releasePlayer() {
        _exoPlayer.value!!.release()
    }

    fun setCurrentLyrics(currentTime: Long) {
        _currentLyrics.value = Helper.checkCurrentLyrics(_lyrics.value!!, currentTime)
    }

    fun getLyrics(index: Int) : String {
        if(_lyrics.value == null || _lyrics.value!!.isEmpty() || index >= _lyrics.value!!.size){
            return ""
        }

        return if(index == -1){
            _lyrics.value!![0].lyric
        } else {
            _lyrics.value!![index].lyric
        }
    }

    fun seekPosition(index: Long) {
        _exoPlayer.value!!.seekTo(index)
    }
}
