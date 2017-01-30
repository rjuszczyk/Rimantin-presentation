package pl.pharmaway.rimantin_presentation.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import butterknife.ButterKnife;
import pl.pharmaway.rimantin_presentation.BaseActivity;
import pl.pharmaway.rimantin_presentation.R;
import pl.pharmaway.rimantin_presentation.form.FormActivity;
import pl.pharmaway.rimantin_presentation.model.UserData;

public class QuizActivity extends BaseActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, QuizActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
    int timeSpentHere = 0;
    boolean[] visitedPages = new boolean[]{false, false, false, false};
    int current = -1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            timeSpentHere = savedInstanceState.getInt("timeSpentHere");
            visitedPages = savedInstanceState.getBooleanArray("visitedPages");
            current = savedInstanceState.getInt("current");
        }

        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new Page1Fragment())
                    .commit();
        }
    }

    long inTime;
    @Override
    protected void onResume() {
        inTime = System.currentTimeMillis();
        super.onResume();
    }

    @Override
    protected void onPause() {
        timeSpentHere += (System.currentTimeMillis() - inTime);
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("timeSpentHere", timeSpentHere);
        outState.putBooleanArray("visitedPages", visitedPages);
        outState.putInt("current", current);
    }

    int findNextNontVisited(int current) {
        int next = current+1;
        ;//next= next;
//        while (next != visitedPages.length) {// && visitedPages[next]) {
//            next++;
//        }
        return next;
    }

    public void goToPage2() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new Page2Fragment())
                .addToBackStack(null)
                .commit();
    }

    public void goToPage3() {
        visitedPages[0] = true;
        current = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new Page3Fragment())
                .addToBackStack("backStack")
                .commit();
    }


    void goToForm() {
        timeSpentHere += (System.currentTimeMillis() - inTime);
        UserData userData = new UserData();
        userData.setTimeSpendInApp(timeSpentHere);
        FormActivity.start(this, userData);
    }

    void backToMainPage() {

        onBackPressed();
        //getSupportFragmentManager().popBackStack("backStack", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void goToNextPage() {
        int next = findNextNontVisited(current);
        switch (next) {
            case 0:
                goToPage3();
                return;
            case 1:
                goToPage4();
                return;
            case 2:
                goToPage5();
                return;
            case 3:
                goToPage6();
                return;
            default:
                goToPage7();
                return;
        }
    }

    public void goToPage4() {
        visitedPages[1] = true;
        current = 1;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new Page4Fragment())
                .addToBackStack("backStack")
                .commit();
    }

    public void goToPage5() {
        visitedPages[2] = true;
        current = 2;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new Page5Fragment())
                .addToBackStack("backStack")
                .commit();
    }

    public void goToPage6() {
        visitedPages[3] = true;
        current = 3;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new Page7Fragment())
                .addToBackStack("backStack")
                .commit();
    }

    public void goToPage7() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new Page7Fragment())
                .addToBackStack("backStack")
                .commit();
    }

    @Override
    public void onBackPressed() {
        current--;
        if(current<-1)current=-1;

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if(fragment instanceof BaseFragment) {
                BaseFragment baseFragment = (BaseFragment) fragment;
                if(baseFragment.handledBackPressed()) {
                    return;
                }
            }
        }
        super.onBackPressed();
    }
}