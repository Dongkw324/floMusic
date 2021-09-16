package com.kdw.flomusic.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kdw.flomusic.MainActivity
import com.kdw.flomusic.R
import com.kdw.flomusic.databinding.FragmentLyricsBinding
import com.kdw.flomusic.viewmodel.MusicViewModel

class LyricsFragment : Fragment() {

    private val musicViewModel: MusicViewModel by activityViewModels()

    private var lyricsBinding: FragmentLyricsBinding? = null
    private val binding get() = lyricsBinding!!

    private lateinit var mContext: Context
    private lateinit var mActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lyricsBinding = FragmentLyricsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLyrics()
    }

    private fun setLyrics() {
        musicViewModel.musicData.observe(viewLifecycleOwner, {
            binding.lyricsContent.removeAllViews()

            binding.musicTitleLyric.text = it.title
            binding.singerNameLyrics.text = it.singer
        })

        musicViewModel.lyrics.observe(viewLifecycleOwner, { lyrics ->
            for(i in lyrics.indices) {
                val textView = View.inflate(mActivity, R.layout.lyrics_elements, null) as TextView
                textView.text = lyrics[i].lyric

                textView.setOnClickListener {
                    if(musicViewModel.getToggleBtn()) {
                        musicViewModel.seekPosition(lyrics[i].timeStamp)
                    } else {
                        mActivity.supportFragmentManager.popBackStack()
                    }
                }

                binding.lyricsManager.addView(textView)
            }
        })

        musicViewModel.currentLyrics.observe(viewLifecycleOwner, {
            val num = binding.lyricsManager.childCount

            for(i in 0 until num) {
                val lyricsText = binding.lyricsManager[i] as TextView
                if(i == it) {
                    lyricsText.setTextColor(ContextCompat.getColor(mContext, R.color.current_lyrics))
                } else {
                    lyricsText.setTextColor(ContextCompat.getColor(mContext, R.color.not_current_lyrics))
                }
            }
        })

        musicViewModel.lyricsToggleBtn.observe(viewLifecycleOwner, {
            if(it) {
                binding.lyricsToggleBtn.setBackgroundColor(ContextCompat.getColor(mContext, R.color.clicked_visible))
            } else {
                binding.lyricsToggleBtn.setBackgroundColor(ContextCompat.getColor(mContext, R.color.unclicked_visible))
            }
        })

        binding.lyricsCloseBtn.setOnClickListener {
            mActivity.supportFragmentManager.popBackStack()
        }

        binding.lyricsToggleBtn.setOnClickListener {
            musicViewModel.setToggleBtn()
        }
    }

    override fun onAttach(context: Context) {
        mContext = context
        mActivity = requireActivity() as MainActivity

        super.onAttach(context)
    }

    override fun onDestroy() {
        lyricsBinding = null
        super.onDestroy()
    }
}