package com.kdw.flomusic

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.kdw.flomusic.databinding.ActivityMainBinding
import com.kdw.flomusic.fragment.LyricsFragment
import com.kdw.flomusic.fragment.MusicFragment
import com.kdw.flomusic.util.OtherFunctions
import com.kdw.flomusic.viewmodel.MusicViewModel

class MainActivity : FragmentActivity() {

    private val musicViewModel: MusicViewModel by viewModels()

    private var _binding: ActivityMainBinding ?= null
    private val binding get() = _binding!!

    private var pressedTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        OtherFunctions.makeStatusTransparent(this)

        initMusic()
    }

    private fun initMusic() {

        musicViewModel.exoPlayer.observe(this@MainActivity, {
            binding.musicPlayerController.player = it
            binding.musicPlayerController.showTimeoutMs = 0
        })

        binding.musicPlayerController.setProgressUpdateListener { position, _->
            musicViewModel.setCurrentLyrics(position)
        }

        if(supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.beginTransaction().add(R.id.music_view_fragment, MusicFragment())
                    .disallowAddToBackStack().commit()
        }

        musicViewModel.getMusic()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onBackPressed() {
        if(pressedTime == 0) {
            Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            pressedTime = System.currentTimeMillis().toInt()
        } else {
            val seconds = System.currentTimeMillis().toInt() - pressedTime

            if(seconds > 2000) {
                Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                pressedTime = 0
            } else {
                musicViewModel.releasePlayer()
                super.onBackPressed()
            }
        }
    }

    fun changeFragment() {
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom, R.anim.slide_in_bottom, R.anim.slide_out_top)
                .replace(R.id.music_view_fragment, LyricsFragment())
                .addToBackStack(null)
                .commit()
    }
}