package com.kdw.flomusic.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kdw.flomusic.databinding.FragmentMusicPlayerBinding


class MusicFragment : Fragment() {

    private var musicBinding : FragmentMusicPlayerBinding?= null
    private val binding get() = musicBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        musicBinding = FragmentMusicPlayerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        musicBinding = null
    }
}