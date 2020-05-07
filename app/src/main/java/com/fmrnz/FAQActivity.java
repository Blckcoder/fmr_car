package com.fmrnz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FAQActivity extends BaseActivity {

    TextView howText;
    Button readFaq;

    String mimeType = "text/html";
    String encoding = "utf-8";
    String htmlText =
            "<p><b>Question : </b>Is there a restriction on how much I can drive?</p>"
                    + "<p<b>Answer : </b>All of Find My Ride NZ’s vehicles have unlimited kilometers</p>"
                    + "<p><b>Question : </b>How does the pick-up and drop off work?</p>"
                    + "<p><b>Answer : </b>Before picking up your car, we will organise to meet at your desired location to deliver your vehicle and take a pre-authorization on your debit/credit card. Once your rental period has been concluded, we will have a pre-determined location spot, which is convenient for you. The vehicle will be returned, and the payment authorized. There is no extra cost for this.</p>"
                    + "<p><b>Question : </b>What are your pick-up and drop-off times?</p>"
                    + "<p><b>Answer : </b>We work at your convenience, no matter what time you require the vehicle to pick up or drop off, Find My Ride NZ will accommodate to you. There is no extra cost for this.</p>"
                    + "<p><b>Question : </b>Do you allow smoking in your vehicles?</p>"
                    + "<p><b>Answer : </b> We have a strict NO SMOKING policy. This must be adhered to at all times when within the vehicle.</p>"
                    + "<p><b>Question : </b>Will my overseas license work in New Zealand?</p>"
                    + "<p><b>Answer : </b>As long as the license is written in English, you are able to. If not you must apply for an International Driver’s License BEFORE coming to New Zealand</p>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ActionBar ab =getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        readFaq = (Button) findViewById(R.id.readFaq);

//        String htmlAsString = getString(R.string.html);
//        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString);
//
//        howText.setText(htmlAsSpanned);



//        WebView webView = (WebView) findViewById(R.id.webView);
//        webView.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);


        readFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent faqIntent = new Intent(Intent.ACTION_VIEW);
                faqIntent.setData(Uri.parse("http://findmyridenz.com/faq/"));
                startActivity(faqIntent);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
