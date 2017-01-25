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

public class Page7Fragment extends BaseFragment {

    @BindView(R.id.page7_left)
    View mLeft;

    @BindView(R.id.page7_right1)
    View mRight1;

    @BindView(R.id.page7_right2)
    View mRight2;

    @BindView(R.id.page7_right3)
    View mRight3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page7, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        float _200dp = getResources().getDisplayMetrics().density * 200;

//        mText.setAlpha(0);
//        mText.animate().alpha(1).start();

        mLeft.setTranslationX(-_200dp);
        mLeft.setAlpha(0);
        mLeft.animate().setStartDelay(100).alpha(1).translationX(0).start();

        mRight1.setTranslationX(_200dp);
        mRight1.setAlpha(0);
        mRight1.animate().setStartDelay(200).alpha(1).translationX(0).start();

        mRight2.setTranslationX(_200dp);
        mRight2.setAlpha(0);
        mRight2.animate().setStartDelay(300).alpha(1).translationX(0).start();

        mRight3.setTranslationX(_200dp);
        mRight3.setAlpha(0);
        mRight3.animate().setStartDelay(400).alpha(1).translationX(0).start();

        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.next)
    void goToNext() {
        ((QuizActivity)getActivity()).goToForm();
    }

    @OnClick(R.id.main_page)
    void goToMainPage() {
        ((QuizActivity)getActivity()).backToMainPage();
    }
}
