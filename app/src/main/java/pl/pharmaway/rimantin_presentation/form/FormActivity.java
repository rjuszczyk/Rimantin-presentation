package pl.pharmaway.rimantin_presentation.form;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pharmaway.rimantin_presentation.R;
import pl.pharmaway.rimantin_presentation.dialog.ChooseAgentDialog;
import pl.pharmaway.rimantin_presentation.dialog.ChooseCityDialog;
import pl.pharmaway.rimantin_presentation.dialog.ChoosePharmacyDialog;
import pl.pharmaway.rimantin_presentation.entry.EntryActivity;
import pl.pharmaway.rimantin_presentation.model.PharmacyDataRow;
import pl.pharmaway.rimantin_presentation.model.UserData;
import pl.pharmaway.rimantin_presentation.sending.SendActivity;
import pl.pharmaway.rimantin_presentation.util.KeyboardUtil;

public class FormActivity extends AppCompatActivity implements
    ChooseCityDialog.CityDialogListener,
        ChooseAgentDialog.AgentDialogListener,
        ChoosePharmacyDialog.PharmacyDialogListener
{

    public static void start(Context context, UserData userData) {
        Intent intent = new Intent(context, FormActivity.class);
        intent.putExtra("user_data", userData);
        context.startActivity(intent);
    }

    UserData userData = null;
    @BindView(R.id.przedst)
    TextView mPrzedstawicielMedyczny;

    @BindView(R.id.miasto)
    TextView mMiasto;

    @BindView(R.id.apteka)
    TextView mApteka;



    @BindView(R.id.dalej)
    View mQuiz;
    @OnClick(R.id.dalej)
    void onDalej(View v) {
        if(mParticipantNumber.isEnabled() && mParticipantNumber.getText().length()>0 ) {
            String participantNumberStr = mParticipantNumber.getText().toString();
            int participantNumber = Integer.decode(participantNumberStr);
            userData.setParticipantNumber(participantNumber);

            SendActivity.start(this, userData);
            finish();

        } else {
            Toast.makeText(this, "Uzupełnij wszystkie dane!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.main_page)
    void goToMainPage() {
        EntryActivity.start(this);
        finish();
    }

    @BindView(R.id.participantNumber)
    EditText mParticipantNumber;

    String imie_przedstawiciela;
    String nazwisko_przedstawiciela;
    String miasto;
    String nazwa_apteki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userData = (UserData) getIntent().getSerializableExtra("user_data");

        if(savedInstanceState == null) {

        }

        setContentView(R.layout.activity_form);

        ButterKnife.bind(this);


        KeyboardUtil keyboardUtil = new KeyboardUtil(this, findViewById(R.id.content));
        //enable it
        keyboardUtil.enable();

        mMiasto.setEnabled(false);
        mApteka.setEnabled(false);

        mParticipantNumber.setEnabled(false);

        mPrzedstawicielMedyczny.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ChooseAgentDialog.create().show(getSupportFragmentManager(), "tag");
            }
        });

        mMiasto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ChooseCityDialog.create(imie_przedstawiciela, nazwisko_przedstawiciela).show(getSupportFragmentManager(), "tag");
            }
        });

        mApteka.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ChoosePharmacyDialog.create(imie_przedstawiciela, nazwisko_przedstawiciela, miasto).show(getSupportFragmentManager(), "tag");
            }
        });

        mParticipantNumber.addTextChangedListener(mTextWatcher);
    }

    boolean isParticipantNumberProper() {
        String number = mParticipantNumber.getText().toString();
        if(number.length()>0) {
            try {
                Integer.decode(number);
                return true;
            } catch (NumberFormatException f) {
                return false;
            }
        } else {
            return false;
        }
    }

    private void checkConditions() {
        if(mParticipantNumber.isEnabled() && isParticipantNumberProper()) {
            mQuiz.setAlpha(1);

        } else {
            mQuiz.setAlpha(0.5f);
        }
    }

    CompoundButton.OnCheckedChangeListener mOnCheckCahangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            checkConditions();
        }
    };

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkConditions();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void onCitySelected(String city) {
        miasto = city;
        mMiasto.setText(miasto);

        mApteka.setEnabled(true);
        mApteka.setText("Apteka");
        mParticipantNumber.setEnabled(false);
    }

    @Override
    public void onAgentSelected(String imie_przedstawiciela, String nazwisko_przedstawiciela) {
        this.imie_przedstawiciela = imie_przedstawiciela;
        this.nazwisko_przedstawiciela = nazwisko_przedstawiciela;
        mPrzedstawicielMedyczny.setText(imie_przedstawiciela + " " + nazwisko_przedstawiciela);
        mMiasto.setEnabled(true);

        mMiasto.setText("Miasto");
        mApteka.setText("Apteka");

        mApteka.setEnabled(false);
        mParticipantNumber.setEnabled(false);

    }

    @Override
    public void onPharmacySelected(String pharmacyName, PharmacyDataRow row) {
        nazwa_apteki = pharmacyName;
        mApteka.setText(nazwa_apteki);
        mParticipantNumber.setEnabled(true);
        userData.setRow(row);
    }
}