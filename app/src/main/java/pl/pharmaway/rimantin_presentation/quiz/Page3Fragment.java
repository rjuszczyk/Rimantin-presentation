package pl.pharmaway.rimantin_presentation.quiz;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.rimantin_presentation.R;
import pl.pharmaway.rimantin_presentation.util.AnimationEndLIstener;

public class Page3Fragment extends BaseFragment {

    @BindView(R.id.cloud1)
    View mCloud1;

    @BindView(R.id.cloud2)
    View mCloud2;

    @BindView(R.id.cloud3)
    View mCloud3;

    @BindView(R.id.cloud1_text)
    View mCloudText1;

    @BindView(R.id.cloud2_text)
    View mCloudText2;

    @BindView(R.id.cloud3_text)
    View mCloudText3;

    @BindView(R.id.women)
    View mWomen;

    @BindView(R.id.boxes)
    View mBoxes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page3, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        destroyed = false;
        mWomen.setAlpha(0);
        mBoxes.setAlpha(0);

        mWomen.animate().alpha(1).start();
        mBoxes.animate().setStartDelay(100).alpha(1).start();

        mCloud1.setScaleX(0);
        mCloud1.setScaleY(0);
        mCloud1.animate().setStartDelay(100).scaleX(1).start();
        mCloud1.animate().setStartDelay(100).scaleY(1).start();

        mCloud2.setScaleX(0);
        mCloud2.setScaleY(0);
        mCloud2.animate().setStartDelay(200).scaleX(1).start();
        mCloud2.animate().setStartDelay(200).scaleY(1).start();

        mCloud3.setScaleX(0);
        mCloud3.setScaleY(0);
        mCloud3.animate().setStartDelay(300).scaleX(1).start();
        mCloud3.animate().setStartDelay(300).scaleY(1).start();


        final Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
        pulse.setRepeatCount(Animation.INFINITE);

        mCloudText1.setScaleX(0);
        mCloudText1.setScaleY(0);
        mCloudText1.animate().setStartDelay(100).scaleX(1).scaleY(1).setListener(new AnimationEndLIstener() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mCloudText1.startAnimation(pulse);
            }
        }).start();

        mCloudText2.setScaleX(0);
        mCloudText2.setScaleY(0);
        mCloudText2.animate().setStartDelay(200).scaleX(1).scaleY(1).setListener(new AnimationEndLIstener() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mCloudText2.startAnimation(pulse);
            }
        }).start();

        mCloudText3.setScaleX(0);
        mCloudText3.setScaleY(0);
        mCloudText3.animate().setStartDelay(300).scaleX(1).scaleY(1).setListener(new AnimationEndLIstener() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mCloudText3.startAnimation(pulse);
            }
        }).start();

        super.onViewCreated(view, savedInstanceState);
    }


    boolean destroyed = false;
    @Override
    public void onDestroyView() {
        destroyed = true;
        super.onDestroyView();
    }

    @OnClick(R.id.next)
    void goToNext() {
        ((QuizActivity)getActivity()).goToNextPage();
    }

    @OnClick(R.id.main_page)
    void goToMainPage() {
        ((QuizActivity)getActivity()).backToMainPage();
    }
}
