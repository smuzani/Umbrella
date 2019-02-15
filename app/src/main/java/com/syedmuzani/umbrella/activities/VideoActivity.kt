package com.syedmuzani.umbrella.activities

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.MediaController
import android.widget.VideoView
import com.syedmuzani.umbrella.R
import org.jetbrains.anko.*

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VideoActivityUI().setContentView(this)
        val vid: VideoView = find (VideoActivityUI.ID_VIDEO)
        playVideo(vid)
    }

    private fun playVideo(mVideoView: VideoView) {
        mVideoView.setVideoURI(Uri.parse("android.resource://" + packageName +"/"+ R.raw.nope))
        mVideoView.setMediaController(MediaController(this))
        mVideoView.requestFocus()
        mVideoView.start()
        mVideoView.setOnCompletionListener { toast("Video completed") }
    }

    class VideoActivityUI : AnkoComponent<VideoActivity> {
        companion object {
            val ID_VIDEO = 11
        }

        override fun createView(ui: AnkoContext<VideoActivity>) = with(ui) {
            frameLayout {
                videoView {
                    id = ID_VIDEO
                }.lparams(matchParent, matchParent)
            }
        }
    }
}
