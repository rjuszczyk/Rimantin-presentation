package pl.pharmaway.rimantin_presentation.sending;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.rimantin_presentation.R;
import pl.pharmaway.rimantin_presentation.database.DatabaseModule;
import pl.pharmaway.rimantin_presentation.entry.EntryActivity;
import pl.pharmaway.rimantin_presentation.model.UserData;
import pl.pharmaway.rimantin_presentation.network.NetworkModule;

/**
 * This activity try to send user data to the server. If user data is send successfully then it
 * navigates to next activity. If not user can decide if it should try again or postpone sending
 * user data. It will be possible to send it in one of entry screens.
 */
public class SendActivity extends AppCompatActivity implements SendingView{

    @SuppressWarnings("unused")
    private static final String TAG = SendActivity.class.getSimpleName();

    @Inject
    SendingPresenter mSendingPresenter;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.retry)
    Button mRetry;

    @BindView(R.id.goNext)
    Button mContinue;

    @BindView(R.id.error)
    View mError;

    @BindView(R.id.sending_data)
    View mSendingData;

    private UserData mUserData;

    public static void start(Context context, UserData userData) {
        Intent intent = new Intent(context, SendActivity.class);
        intent.putExtra("user_data", userData);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserData = (UserData) getIntent().getSerializableExtra("user_data");

        setContentView(R.layout.activity_send);
        ButterKnife.bind(this);

        DaggerSendingComponent.builder()
                .sendingModule(new SendingModule(this, mUserData))
                .databaseModule(new DatabaseModule(this))
                .networkModule(new NetworkModule())
                .build()
                .inject(this);

        mSendingPresenter.start();
    }

    @Override
    protected void onDestroy() {
        mSendingPresenter.stop();
        super.onDestroy();
    }

    @OnClick(R.id.retry)
    void onRetry() {
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("[sending] retry send")
                .putContentType("Action")
                .putContentId("8"));
        mSendingPresenter.retry();
    }

    @OnClick(R.id.goNext)
    void onGoNext() {
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("[sending] send later")
                .putContentType("Action")
                .putContentId("7"));
        goToNext();
    }

    @Override
    public void showProgress(boolean isShown) {
        if(isShown) {
            mProgressBar.setVisibility(View.VISIBLE);
            mSendingData.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mSendingData.setVisibility(View.GONE);
        }
    }

    @Override
    public void goToNext() {
        EntryActivity.start(this);
        finish();
    }

    @Override
    public void showRetry(boolean isShown) {
        if(isShown) {
            mRetry.setVisibility(View.VISIBLE);
            mError.setVisibility(View.VISIBLE);
        } else {
            mRetry.setVisibility(View.GONE);
            mError.setVisibility(View.GONE);
        }
    }

    @Override
    public void showGoToNextButton(boolean isShown) {
        if(isShown) {
            mContinue.setVisibility(View.VISIBLE);
        } else {
            mContinue.setVisibility(View.GONE);
        }
    }
}