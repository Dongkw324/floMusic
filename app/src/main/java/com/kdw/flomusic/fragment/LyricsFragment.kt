package com.kdw.flomusic.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.kdw.flomusic.MainActivity
import com.kdw.flomusic.databinding.FragmentLyricsBinding
import com.kdw.flomusic.databinding.LyricsElementsBinding
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

        musicViewModel.musicData.observe(viewLifecycleOwner, {
            binding.lyricsContent.removeAllViews()

            binding.musicTitleLyric.text = it.title
            binding.singerNameLyrics.text = it.singer
        })


        binding.lyricsCloseBtn.setOnClickListener {
            mActivity.supportFragmentManager.popBackStack()
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