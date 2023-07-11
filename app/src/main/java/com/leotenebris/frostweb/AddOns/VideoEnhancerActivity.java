package com.leotenebris.frostweb.AddOns;

import android.os.Bundle;

import com.leotenebris.frostweb.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class VideoEnhancerActivity extends AppCompatActivity {
    static VideoEnhancerActivity videoEnhancerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        videoEnhancerActivity = this;

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new VideoEnhancerFragment()).commit();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getResources().getString(R.string.video_enhancer));
    }
}
