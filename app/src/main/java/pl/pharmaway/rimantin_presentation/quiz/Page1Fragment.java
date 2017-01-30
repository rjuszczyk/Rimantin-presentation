package pl.pharmaway.rimantin_presentation.quiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.rimantin_presentation.R;

public class Page1Fragment extends BaseFragment {
    @BindView(R.id.versionNumber)
    TextView mVersionNumber;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Multilac-1-min.prefs", Context.MODE_PRIVATE);
        int v=sharedPreferences.getInt("version",0);
        mVersionNumber.setText("v="+v);
    }

    @OnClick(R.id.next)
    void goToPage2() {
        ((QuizActivity)getActivity()).goToPage2();
    }
}
