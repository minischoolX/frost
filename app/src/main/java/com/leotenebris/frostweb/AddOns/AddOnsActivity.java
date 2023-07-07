package com.leotenebris.frostweb.Settings;

import android.os.Bundle;

import com.leotenebris.frostweb.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AddOnsActivity extends AppCompatActivity {
    static AddOnsActivity addOnsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addOnsActivity = this;

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new AddOnsFragment()).commit();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getResources().getString(R.string.addon));
    }
}
