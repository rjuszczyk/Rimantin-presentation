package pl.pharmaway.rimantin_presentation.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import pl.pharmaway.rimantin_presentation.R;

/**
 * Created by Radek on 16.01.2017.
 */

public class AnimationOnClickListener implements View.OnClickListener {

    private final View.OnClickListener mlistener;

    public AnimationOnClickListener(View.OnClickListener listener) {
        mlistener = listener;
    }

    @Override
    public void onClick(final View v) {
        Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.click);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mlistener.onClick(v);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(animation);
    }
}
