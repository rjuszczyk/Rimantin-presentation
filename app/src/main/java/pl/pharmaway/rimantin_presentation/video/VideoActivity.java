package pl.pharmaway.rimantin_presentation.video;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;

import com.sprylab.android.widget.TextureVideoView;

import pl.pharmaway.rimantin_presentation.BaseActivity;
import pl.pharmaway.rimantin_presentation.R;

public class VideoActivity extends BaseActivity {

    private static final String TAG = VideoActivity.class.getName();

    private TextureVideoView  mVideoView;
    private MediaPlayer mMediaPlayer = null;
    private View mContainer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);



        mVideoView = (TextureVideoView) findViewById(R.id.video_view);
        mContainer = findViewById(R.id.container);
        initVideoView();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mVideoView != null) {
            mVideoView.stopPlayback();
            mVideoView = null;
        }
    }

    private void initVideoView() {
        mVideoView.setVideoPath(getVideoPath());
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);
        mVideoView.setMediaController(mediaController);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                mVideoView.setScaleX(0.5f);
                mVideoView.setScaleY(0.5f);
                mMediaPlayer = mp;

                mp.seekTo(1000);
            }
        });
        mContainer.setOnClickListener(new View.OnClickListener() {
            boolean first = true;
            @Override
            public void onClick(View v) {
                if(mMediaPlayer != null) {
                    if(mVideoView.isPlaying()) {
                        pausePlaying();
                    } else {
                        if(first) {
                            mMediaPlayer.seekTo(0);
                        }
                        first = false;
                        mVideoView.animate().scaleX(1).scaleY(1).start();
                        startVideoPlayback();
                    }
                }
            }
        });
    }

    private void pausePlaying() {
        mVideoView.pause();
        mVideoView.animate().scaleX(0.5f).scaleY(0.5f).start();
    }

    @Override
    public void onBackPressed() {
        if(mVideoView.isPlaying()) {
            pausePlaying();
        } else {
            super.onBackPressed();
        }
    }

    private void startVideoPlayback() {
        // "forces" anti-aliasing - but increases time for taking frames - so keep it disabled
        // mVideoView.setScaleX(1.00001f);
        mVideoView.start();
    }

    private String getVideoPath() {
        return "android.resource://" + getPackageName() + "/" + R.raw.movie;
    }
}
