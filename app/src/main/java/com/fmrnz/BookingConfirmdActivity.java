package com.fmrnz;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class BookingConfirmdActivity extends BaseActivity implements View.OnClickListener {

    String bookingID;
    TextView bookID;
    Button donebtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confim);
        bookingID = getIntent().getStringExtra("BookingID");

        setUpView();
        setUpListners();
        setUpData();
    }

    private void setUpView(){
//        ActionBar ab =getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }
        bookID = (TextView)findViewById(R.id.bookID);
        donebtn = (Button)findViewById(R.id.donebtn);
    }

    private void setUpListners(){
        donebtn.setOnClickListener(this);
    }

    private void setUpData(){
        bookID.setText(bookingID);
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BookingConfirmdActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.donebtn:
                Intent intent = new Intent(BookingConfirmdActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
                break;
        }
    }
}