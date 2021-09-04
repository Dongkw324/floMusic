package com.kdw.flomusic.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kdw.flomusic.databinding.FragmentLyricsBinding
import com.kdw.flomusic.databinding.LyricsElementsBinding

class LyricsFragment : Fragment() {

    private var lyricsBinding: FragmentLyricsBinding? = null
    private val binding get() = lyricsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lyricsBinding = FragmentLyricsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lyricsBinding = null
    }
}