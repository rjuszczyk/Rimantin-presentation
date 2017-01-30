package pl.pharmaway.rimantin_presentation.regulamin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import pl.pharmaway.rimantin_presentation.R;

/**
 * Created by Radek on 26.01.2017.
 */

public class RegulaminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulamin);
        WebView mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        mWebView.setHorizontalScrollBarEnabled(false);

        mWebView.getSettings().setJavaScriptEnabled(true);

        //mWebView.loadUrl("http://pharmawayjn.nazwa.pl/MedycynaRodzinna/gardimax/regulamin.html");
        mWebView.loadUrl("https://docs.google.com/viewer?url=http://pharmaway.pl/app/rimantin-presentation/rimantin.pdf");
    }
}