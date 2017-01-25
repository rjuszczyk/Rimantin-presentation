package pl.pharmaway.rimantin_presentation.quiz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import pl.pharmaway.rimantin_presentation.BaseActivity;
import pl.pharmaway.rimantin_presentation.R;
import pl.pharmaway.rimantin_presentation.view.AngleFrameLayout;

public class BaseFragment extends Fragment implements BaseActivity.AngleChangedListener {
    private AngleFrameLayout mAngleFrameLayout;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAngleFrameLayout = (AngleFrameLayout) view.findViewById(R.id.angle_text);
        final Animation number1animation = AnimationUtils.loadAnimation(getContext(), R.anim.pulse_in);
//
        if (mAngleFrameLayout != null) {
            mAngleFrameLayout.startAnimation(number1animation);
        }
//        if(mAngleFrameLayout != null) {
//            ((BaseActivity)getActivity()).register(this);
//        }
    }

    @Override
    public void onDestroyView() {
//        if(mAngleFrameLayout != null) {
//            ((BaseActivity)getActivity()).unregister(this);
//        }
        super.onDestroyView();
    }

    @Override
    public void onAngleChanged(float a) {
        mAngleFrameLayout.setAngle(a);
    }

    public boolean handledBackPressed() {
        return false;
    }
}
