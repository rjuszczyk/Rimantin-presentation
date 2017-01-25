package pl.pharmaway.rimantin_presentation.entry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.rimantin_presentation.R;
import pl.pharmaway.rimantin_presentation.database.DatabaseModule;
import pl.pharmaway.rimantin_presentation.network.NetworkModule;
import pl.pharmaway.rimantin_presentation.quiz.QuizActivity;
import pl.pharmaway.rimantin_presentation.updateData.UpdateDataView;

/**
 * This activity is used to check if there is already downloaded data, if yes then it instantly
 * navigates to next activity, if not then update process is started.
 */
public class EntryActivity extends AppCompatActivity implements UpdateDataView, EntryView {

    public static void start(Context context) {
        Intent intent = new Intent(context, EntryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @SuppressWarnings("unused")
    private static final String TAG = EntryActivity.class.getSimpleName();

    @Inject
    EntryPresenter mEntryPresenter;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.retry)
    Button mRetry;

    @BindView(R.id.error)
    View mError;

    @BindView(R.id.sending_data)
    View mSendingData;

    @BindView(R.id.downloading_data)
    View mDownloadingData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_entry);
        ButterKnife.bind(this);

        EntryComponent entryComponent = DaggerEntryComponent.builder()
                .networkModule(new NetworkModule())
                .databaseModule(new DatabaseModule(this))
                .entryModule(new EntryModule(this, this))
                .build();
        entryComponent.inject(this);

        mEntryPresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEntryPresenter.stop();
    }

    @OnClick(R.id.retry)
    void onRetry() {
        mEntryPresenter.start();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRetry.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSuccess() {
        goToNextScreen();
    }

    @Override
    public void showFailed(ErrorType errorType) {
        mRetry.setVisibility(View.VISIBLE);
        mError.setVisibility(View.VISIBLE);
        mDownloadingData.setVisibility(View.GONE);
        mSendingData.setVisibility(View.GONE);
    }

    @Override
    public void goToNextScreen() {
        QuizActivity.start(this);
        finish();
    }

    @Override
    public void showSendingData() {
        mProgressBar.setVisibility(View.VISIBLE);
        mSendingData.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDownloadingData() {
        mDownloadingData.setVisibility(View.VISIBLE);
    }


}