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

public class Page5Fragment extends BaseFragment {

    @BindView(R.id.pic1)
    View mPic1;

    @BindView(R.id.pic2)
    View mPic2;

    @BindView(R.id.pic3)
    View mPic3;

    @BindView(R.id.text5)
    View mText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page5, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mText.setAlpha(0);

        mText.animate().alpha(1).start();

        mPic1.setScaleX(0);
        mPic1.setScaleY(0);
        mPic1.animate().setStartDelay(100).scaleX(1).start();
        mPic1.animate().setStartDelay(100).scaleY(1).start();

        mPic2.setScaleX(0);
        mPic2.setScaleY(0);
        mPic2.animate().setStartDelay(200).scaleX(1).start();
        mPic2.animate().setStartDelay(200).scaleY(1).start();

        mPic3.setScaleX(0);
        mPic3.setScaleY(0);
        mPic3.animate().setStartDelay(300).scaleX(1).start();
        mPic3.animate().setStartDelay(300).scaleY(1).start();

        super.onViewCreated(view, savedInstanceState);
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
