package com.kdw.flomusic.util

import com.kdw.flomusic.model.Lyrics

class Helper {

    companion object {

        fun setLyrics(lyrics: String) : MutableList<Lyrics>{
            val lyricManager = mutableListOf<Lyrics>()
            val splitLyrics = lyrics.split('\n').toList()

            for(i in splitLyrics.indices) {
                val lyricString = splitLyrics[i].split("[", "]").filter { it.isNotEmpty() }
                lyricManager.add(Lyrics(convert(lyricString[0]), lyricString[1]))
            }

            return lyricManager

        }

        //밀리초로 변환하는 함수
        private fun convert(timestamp: String): Long {
            var time = 0L

            val temp = timestamp.split(":").map {
                it.toLong()
            }

            //temp[0]: minute, temp[1]: second, temp[2]: millisecond
            time = temp[0]*60*1000 + temp[1]*1000 + temp[2];
            return time
        }

        fun checkCurrentLyrics(lyrics: List<Lyrics>, msTime: Long): Int {
            var idx = -1

            for(i in lyrics.indices) {
                if(lyrics[i].timeStamp <= msTime) {
                    if(i == lyrics.size-1) {
                        idx = i
                        break
                    } else if(lyrics[i+1].timeStamp > msTime) {
                        idx = i
                        break
                    }
                }
            }

            return idx
        }
    }

}