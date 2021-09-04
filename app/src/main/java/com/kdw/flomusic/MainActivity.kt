package com.kdw.flomusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.kdw.flomusic.databinding.ActivityMainBinding
import com.kdw.flomusic.fragment.MusicFragment

class MainActivity : FragmentActivity() {

    private var _binding: ActivityMainBinding ?= null
    private val binding get() = _binding!!

    private var pressedTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.beginTransaction().add(R.id.music_view_fragment, MusicFragment())
                .disallowAddToBackStack().commit()
        }
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
                super.onBackPressed()
            }
        }
    }
}