package pl.pharmaway.rimantin_presentation.quiz;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.sprylab.android.widget.TextureVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.rimantin_presentation.R;

import static pl.pharmaway.rimantin_presentation.dialog.ChooseElementDialog.TAG;

public class Page4Fragment extends BaseFragment {

    @BindView(R.id.page4_top)
    View mTop;

    @BindView(R.id.black_bg)
    View mBlackBg;

    @BindView(R.id.video_view)
    TextureVideoView mVideoView;

    @BindView(R.id.video_container)
    View mVideoContainer;

    @BindView(R.id.frame1)
    View mFrame1;

    @BindView(R.id.play)
    View mPlay;


    private MediaPlayer mMediaPlayer = null;
    private float _20dp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page4, container, false);
        ButterKnife.bind(this, view);
        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initVideoView();
        _20dp = getResources().getDisplayMetrics().density * 20;
        mTop.setScaleX(0);
        mTop.setScaleY(0);
        mTop.animate().setStartDelay(0).scaleX(1).start();
        mTop.animate().setStartDelay(0).scaleY(1).start();

//        final Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse_in);
//        mText.startAnimation(pulse);

//        mText.setScaleX(0);
//        mText.setScaleY(0);
//        mText.animate().setStartDelay(200).scaleX(1).start();
//        mText.animate().setStartDelay(200).scaleY(1).start();

        super.onViewCreated(view, savedInstanceState);
    }

    private void initVideoView() {
        mVideoView.setVideoPath(getVideoPath());
        MediaController mediaController = new MediaController(getContext()) {
            public boolean dispatchKeyEvent(KeyEvent event)
            {
                Log.d(TAG, "dispatchKeyEvent() called with: event = [" + event + "]");
                return false;
            }
        };
        mVideoContainer.setScaleX(0.42f);
        mVideoContainer.setScaleY(0.42f);
        mediaController.setVisibility(View.GONE);
        mVideoView.setMediaController(mediaController);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                mVideoView.animate().alpha(1).setStartDelay(400).start();
                mVideoContainer.setScaleX(0.42f);
                mVideoContainer.setScaleY(0.42f);

                mMediaPlayer = mp;

//                mVideoView.seekTo(1400);
//                mVideoView.stopPlayback();
            }
        });
        mVideoContainer.setOnClickListener(new View.OnClickListener() {
            boolean first = true;
            @Override
            public void onClick(View v) {
                if(mMediaPlayer != null) {
                    if(mVideoView.isPlaying()) {
                        pausePlaying();
                    } else {
                        if(first) {
                            //mMediaPlayer.seekTo(0);
                        }
                        first = false;
                        mVideoContainer.animate().translationY(0).scaleX(1).scaleY(1).start();

                        mFrame1.animate().alpha(0).start();
                        mPlay.animate().alpha(0).start();
                        mBlackBg.animate().alpha(1).start();
                        mNext.animate().alpha(0).start();
                        mMainPage.animate().alpha(0).start();
                        startVideoPlayback();
                    }
                }
            }
        });
    }

    @OnClick(R.id.next)
    void goToNext() {
        ((QuizActivity)getActivity()).goToNextPage();
    }

    @OnClick(R.id.main_page)
    void goToMainPage() {
        ((QuizActivity)getActivity()).backToMainPage();
    }

    @BindView(R.id.next)
    View mNext;

    @BindView(R.id.main_page)
    View mMainPage;

    private void pausePlaying() {
        mPlay.animate().alpha(1).start();
        mVideoView.pause();
        mVideoContainer.animate().translationY(_20dp).scaleX(0.42f).scaleY(0.42f).start();
        mBlackBg.animate().alpha(0).start();
        mNext.animate().alpha(1).start();
        mMainPage.animate().alpha(1).start();

        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("[quiz] close Video")
                .putContentType("Video")
                .putContentId("2"));
    }

    @Override
    public boolean handledBackPressed() {
        if(mVideoView.isPlaying()) {
            pausePlaying();
            return true;
        } else {
            return super.handledBackPressed();
        }
    }

    private void startVideoPlayback() {

        // TODO: Use your own attributes to track content views in your app
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("[quiz] open Video")
                .putContentType("Video")
                .putContentId("1"));



        mVideoView.start();
    }

    private String getVideoPath() {
        return "android.resource://" + getActivity().getPackageName() + "/" + R.raw.movie;
    }
}
