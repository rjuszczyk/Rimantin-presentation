package pl.pharmaway.rimantin_presentation.quiz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.rimantin_presentation.R;
import pl.pharmaway.rimantin_presentation.util.AnimationOnClickListener;

public class Page2Fragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page2, container, false);;
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.back)
    void onBackButton() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.next)
    void goToPage3() {
        ((QuizActivity)getActivity()).goToNextPage();
    }
}
