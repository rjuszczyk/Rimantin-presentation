package pl.pharmaway.rimantin_presentation.quiz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.rimantin_presentation.R;

public class Page6Fragment extends BaseFragment {

    @BindView(R.id.page6_left)
    View mLeft;

    @BindView(R.id.page6_center)
    View mCenter;

    @BindView(R.id.page6_right)
    View mRight;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page6, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        float _200dp = getResources().getDisplayMetrics().density * 200;


        mLeft.setTranslationX(-_200dp);
        mLeft.setAlpha(0);
        mLeft.animate().setStartDelay(200).alpha(1).translationX(0).start();

        mCenter.setScaleX(0);
        mCenter.setScaleY(0);
        mCenter.animate().setStartDelay(100).scaleX(1).start();
        mCenter.animate().setStartDelay(100).scaleY(1).start();

        mRight.setTranslationX(_200dp);
        mRight.setAlpha(0);
        mRight.animate().setStartDelay(200).alpha(1).translationX(0).start();

        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.next)
    void goToNExt() {
        ((QuizActivity)getActivity()).goToNextPage();
    }

    @OnClick(R.id.main_page)
    void goToMainPage() {
        ((QuizActivity)getActivity()).backToMainPage();
    }
}
