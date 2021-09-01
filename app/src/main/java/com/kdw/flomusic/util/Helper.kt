package com.kdw.flomusic.util

class Helper {

    companion object {

        //밀리초로 변환하는 함수
        private fun convert(timestamp: String): Long {
            var time: Long = 0L

            val temp = timestamp.split(":").map {
                it.toLong()
            }

            //temp[0]: minute, temp[1]: second, temp[2]: millisecond
            time = temp[0]*60*1000 + temp[1]*1000 + temp[2];
            return time
        }
    }

}