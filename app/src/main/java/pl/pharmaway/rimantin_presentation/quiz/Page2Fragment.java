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

        view.findViewById(R.id.zaufanie).setOnClickListener(new AnimationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuizActivity)getActivity()).goToPage3();
            }
        }));

        view.findViewById(R.id.techonologia).setOnClickListener(new AnimationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuizActivity)getActivity()).goToPage4();
            }
        }));

        view.findViewById(R.id.badania).setOnClickListener(new AnimationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuizActivity)getActivity()).goToPage5();
            }
        }));

        view.findViewById(R.id.pacjent).setOnClickListener(new AnimationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuizActivity)getActivity()).goToPage6();
            }
        }));

        return view;
    }

    @OnClick(R.id.next)
    void goToPage3() {
        ((QuizActivity)getActivity()).goToNextPage();
    }
}
