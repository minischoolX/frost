package com.leotenebris.frostweb.AddOns;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.leotenebris.frostweb.R;

import android.content.SharedPreferences;

public class VideoEnhancerFragment extends Fragment {

    public VideoEnhancerFragment() {
        // Required empty public constructor
    }

    private LinearLayout[] headerViews = new LinearLayout[5];
    private LinearLayout[] contentViews = new LinearLayout[5];
    private Switch squareAvatarsSwitch;
    private SharedPreferences sharedPreferences;
    private JsManager jsManager;
    private String attachPrefs;
    private String detachPrefs;

    // Fragment lifecycle method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_enhancer, container, false);

        jsManager = JsManager.getInstance();

        headerViews[0] = rootView.findViewById(R.id.header_view_1);
        contentViews[0] = rootView.findViewById(R.id.content_view_1);
        squareAvatarsSwitch = rootView.findViewById(R.id.switchSquareAvatars);

        headerViews[1] = rootView.findViewById(R.id.header_view_2);
        contentViews[1] = rootView.findViewById(R.id.content_view_2);
    
        headerViews[2] = rootView.findViewById(R.id.header_view_3);
        contentViews[2] = rootView.findViewById(R.id.content_view_3);
    
        headerViews[3] = rootView.findViewById(R.id.header_view_4);
        contentViews[3] = rootView.findViewById(R.id.content_view_4);
        
        headerViews[4] = rootView.findViewById(R.id.header_view_5);
        contentViews[4] = rootView.findViewById(R.id.content_view_5);
      
        for (int i = 0; i < headerViews.length; i++) {
            final int index = i; // Store the current index in a final variable for use in the click listener
        
            setExpandableClickListener(headerViews[i], contentViews[i], index);
        }

        // Initialize SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("videoEnhancer", getActivity().MODE_PRIVATE);

        // Set initial switch state from SharedPreferences
        squareAvatarsSwitch.setChecked(sharedPreferences.getBoolean("square-avatars", false));

        sharedPreferences.edit().putString("lang_code", "en").apply();
        sharedPreferences.edit().putString("user-api-key", "").apply();

        squareAvatarsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("square-avatars", isChecked).apply();
                jsManager.setModified("videoEnhancer", true);
                //modifiedMap.put(key, value);
            }
        });

        return rootView;
    }

    // Method to set the click listener and handle expand/collapse behavior for an expandable view
    private void setExpandableClickListener(LinearLayout headerView, LinearLayout contentView, int index) {
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentView.getVisibility() == View.VISIBLE) {
                    contentView.setVisibility(View.GONE);
                } else {
                    contentView.setVisibility(View.VISIBLE);

                  // Collapse other expandable views if expanded
                    for (int i = 0; i < headerViews.length; i++) {
                        if (i != index) {
                            collapseIfExpanded(contentViews[i]);
                        }
                    }
                }
            }
        });
    }
    
    // Method to collapse a specific expandable view if it is expanded
    private void collapseIfExpanded(LinearLayout contentView) {
        if (contentView.getVisibility() == View.VISIBLE) {
            contentView.setVisibility(View.GONE);
        }
    }
}
