package com.fmrnz;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.florescu.android.rangeseekbar.RangeSeekBar;


/**
 * Created by eurysinfosystems on 16/05/18.
 */

public class filterActivity extends BaseActivity {

    ImageView upImage,downImage,upImage1,downImage1,upImage2,downImage2,upImage3,downImage3,upImage4,downImage4,upImage5,downImage5,upImage6,downImage6,upImage7,downImage7,upImage8,downImage8,upImage9,downImage9;
    LinearLayout upArrow,downArrow,content,upArrow1,downArrow1,content1,upArrow2,downArrow2,content2,upArrow3,downArrow3,content3,upArrow4,downArrow4,content4,upArrow5,downArrow5,content5,upArrow6,downArrow6,content6,upArrow7,downArrow7,content7,upArrow8,downArrow8,content8,upArrow9,downArrow9,content9;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.close);

//        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.up_arrow);

//        setupToolBar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }

        upArrow = (LinearLayout) findViewById(R.id.upLayout);
        downArrow = (LinearLayout) findViewById(R.id.downlayout);
        content = (LinearLayout)findViewById(R.id.content);

        upImage = (ImageView)findViewById(R.id.upImage);
        downImage = (ImageView)findViewById(R.id.downImage);

        upArrow1 = (LinearLayout) findViewById(R.id.upLayout1);
        downArrow1 = (LinearLayout) findViewById(R.id.downlayout1);
        content1 = (LinearLayout)findViewById(R.id.content1);

        upImage1 = (ImageView)findViewById(R.id.upImage1);
        downImage1 = (ImageView)findViewById(R.id.downImage1);

        upArrow2= (LinearLayout) findViewById(R.id.upLayout2);
        downArrow2 = (LinearLayout) findViewById(R.id.downlayout2);
        content2 = (LinearLayout)findViewById(R.id.content2);

        upImage2 = (ImageView)findViewById(R.id.upImage2);
        downImage2 = (ImageView)findViewById(R.id.downImage2);

        upArrow3 = (LinearLayout) findViewById(R.id.upLayout3);
        downArrow3 = (LinearLayout) findViewById(R.id.downlayout3);
        content3 = (LinearLayout)findViewById(R.id.content3);

        upImage3 = (ImageView)findViewById(R.id.upImage3);
        downImage3 = (ImageView)findViewById(R.id.downImage3);

        upArrow4 = (LinearLayout) findViewById(R.id.upLayout4);
        downArrow4 = (LinearLayout) findViewById(R.id.downlayout4);
        content4 = (LinearLayout)findViewById(R.id.content4);

        upImage4 = (ImageView)findViewById(R.id.upImage4);
        downImage4 = (ImageView)findViewById(R.id.downImage4);

        upArrow5 = (LinearLayout) findViewById(R.id.upLayout5);
        downArrow5 = (LinearLayout) findViewById(R.id.downlayout5);
        content5 = (LinearLayout)findViewById(R.id.content5);

        upImage5 = (ImageView)findViewById(R.id.upImage5);
        downImage5 = (ImageView)findViewById(R.id.downImage5);

        upArrow6 = (LinearLayout) findViewById(R.id.upLayout6);
        downArrow6 = (LinearLayout) findViewById(R.id.downlayout6);
        content6 = (LinearLayout)findViewById(R.id.content6);

        upImage6 = (ImageView)findViewById(R.id.upImage6);
        downImage6 = (ImageView)findViewById(R.id.downImage6);

        upArrow7 = (LinearLayout) findViewById(R.id.upLayout7);
        downArrow7 = (LinearLayout) findViewById(R.id.downlayout7);
        content7 = (LinearLayout)findViewById(R.id.content7);

        upImage7 = (ImageView)findViewById(R.id.upImage7);
        downImage7 = (ImageView)findViewById(R.id.downImage7);

        upArrow8 = (LinearLayout) findViewById(R.id.upLayout8);
        downArrow8 = (LinearLayout) findViewById(R.id.downlayout8);
        content8 = (LinearLayout)findViewById(R.id.content8);

        upImage8 = (ImageView)findViewById(R.id.upImage8);
        downImage8 = (ImageView)findViewById(R.id.downImage8);

        upArrow9 = (LinearLayout) findViewById(R.id.upLayout9);
        downArrow9 = (LinearLayout) findViewById(R.id.downlayout9);
        content9 = (LinearLayout)findViewById(R.id.content9);

        upImage9 = (ImageView)findViewById(R.id.upImage9);
        downImage9 = (ImageView)findViewById(R.id.downImage9);

        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setVisibility(View.VISIBLE);
               upArrow.setVisibility(View.VISIBLE);
                downArrow.setVisibility(View.GONE);


            }
        });

        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setVisibility(View.GONE);
                downArrow.setVisibility(View.VISIBLE);
                upArrow.setVisibility(View.GONE);


            }
        });

        downArrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content1.setVisibility(View.VISIBLE);
                upArrow1.setVisibility(View.VISIBLE);
                downArrow1.setVisibility(View.GONE);


            }
        });

        upArrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content1.setVisibility(View.GONE);
                downArrow1.setVisibility(View.VISIBLE);
                upArrow1.setVisibility(View.GONE);


            }
        });

        downArrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content2.setVisibility(View.VISIBLE);
                upArrow2.setVisibility(View.VISIBLE);
                downArrow2.setVisibility(View.GONE);


            }
        });

        upArrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content2.setVisibility(View.GONE);
                downArrow2.setVisibility(View.VISIBLE);
                upArrow2.setVisibility(View.GONE);


            }
        });

        downArrow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content3.setVisibility(View.VISIBLE);
                upArrow3.setVisibility(View.VISIBLE);
                downArrow3.setVisibility(View.GONE);


            }
        });

        upArrow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content3.setVisibility(View.GONE);
                downArrow3.setVisibility(View.VISIBLE);
                upArrow3.setVisibility(View.GONE);


            }
        });

        downArrow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content4.setVisibility(View.VISIBLE);
                upArrow4.setVisibility(View.VISIBLE);
                downArrow4.setVisibility(View.GONE);


            }
        });

        upArrow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content4.setVisibility(View.GONE);
                downArrow4.setVisibility(View.VISIBLE);
                upArrow4.setVisibility(View.GONE);


            }
        });

        downArrow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content5.setVisibility(View.VISIBLE);
                upArrow5.setVisibility(View.VISIBLE);
                downArrow5.setVisibility(View.GONE);


            }
        });

        upArrow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content5.setVisibility(View.GONE);
                downArrow5.setVisibility(View.VISIBLE);
                upArrow5.setVisibility(View.GONE);


            }
        });

        downArrow6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content6.setVisibility(View.VISIBLE);
                upArrow6.setVisibility(View.VISIBLE);
                downArrow6.setVisibility(View.GONE);


            }
        });

        upArrow6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content6.setVisibility(View.GONE);
                downArrow6.setVisibility(View.VISIBLE);
                upArrow6.setVisibility(View.GONE);


            }
        });

        downArrow7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content7.setVisibility(View.VISIBLE);
                upArrow7.setVisibility(View.VISIBLE);
                downArrow7.setVisibility(View.GONE);


            }
        });

        upArrow7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content7.setVisibility(View.GONE);
                downArrow7.setVisibility(View.VISIBLE);
                upArrow7.setVisibility(View.GONE);


            }
        });

        downArrow8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content8.setVisibility(View.VISIBLE);
                upArrow8.setVisibility(View.VISIBLE);
                downArrow8.setVisibility(View.GONE);


            }
        });

        upArrow8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content8.setVisibility(View.GONE);
                downArrow8.setVisibility(View.VISIBLE);
                upArrow8.setVisibility(View.GONE);


            }
        });

        downArrow9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content9.setVisibility(View.VISIBLE);
                upArrow9.setVisibility(View.VISIBLE);
                downArrow9.setVisibility(View.GONE);


            }
        });

        upArrow9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content9.setVisibility(View.GONE);
                downArrow9.setVisibility(View.VISIBLE);
                upArrow9.setVisibility(View.GONE);


            }
        });

        RangeSeekBar rangeSeekbar = (RangeSeekBar) findViewById(R.id.rangeSeekbar);
        rangeSeekbar.setNotifyWhileDragging(true);
        rangeSeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

            }
        });

        RangeSeekBar rangeSeekbar1 = (RangeSeekBar) findViewById(R.id.rangeSeekbar1);
        rangeSeekbar1.setNotifyWhileDragging(true);
        rangeSeekbar1.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

            }
        });

        RangeSeekBar rangeSeekbar2 = (RangeSeekBar) findViewById(R.id.rangeSeekbar2);
        rangeSeekbar2.setNotifyWhileDragging(true);
        rangeSeekbar2.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

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
