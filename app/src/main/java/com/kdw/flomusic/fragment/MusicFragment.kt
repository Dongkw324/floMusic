package com.kdw.flomusic.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.kdw.flomusic.MainActivity
import com.kdw.flomusic.R
import com.kdw.flomusic.databinding.FragmentMusicPlayerBinding
import com.kdw.flomusic.viewmodel.MusicViewModel


class MusicFragment : Fragment() {

    private val musicViewModel: MusicViewModel by activityViewModels()

    private var musicBinding : FragmentMusicPlayerBinding?= null
    private val binding get() = musicBinding!!

    private lateinit var mContext: Context
    private lateinit var mActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        musicBinding = FragmentMusicPlayerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        musicViewModel.musicData.observe(viewLifecycleOwner, {
            binding.musicTitle.text = it.title
            binding.singerName.text = it.singer
            binding.albumName.text = it.album

            Glide.with(this)
                    .load(it.image)
                    .into(binding.albumImage)
        })

        checkCurrentLyrics()

    }

    private fun checkCurrentLyrics() {
        musicViewModel.currentLyrics.observe(viewLifecycleOwner, {

            binding.currentLyrics.text = musicViewModel.getLyrics(it)
            binding.nextLyrics.text = musicViewModel.getLyrics(it+1)

            if(it == -1) {
                binding.currentLyrics.setTextColor(mContext.getColor(R.color.not_current_lyrics))
            } else {
                binding.currentLyrics.setTextColor(mContext.getColor(R.color.current_lyrics))
            }

            binding.lyricsView.setOnClickListener {
                mActivity.changeFragment()
            }
        })
    }

    override fun onAttach(context: Context) {
        mContext = context
        mActivity = requireActivity() as MainActivity

        super.onAttach(context)

    }

    override fun onDestroy() {
        musicBinding = null
        super.onDestroy()
    }
}