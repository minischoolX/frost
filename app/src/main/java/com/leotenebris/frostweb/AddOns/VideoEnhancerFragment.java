package com.leotenebris.frostweb.AddOns;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leotenebris.frostweb.R;

public class VideoEnhancerFragment extends Fragment {

    public VideoEnhancerFragment() {
        // Required empty public constructor
    }

    private LinearLayout[] headerViews = new LinearLayout[5];
    private LinearLayout[] contentViews = new LinearLayout[5];
  
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_enhancer, container, false);

        headerViews[0] = findViewById(R.id.header_view_1);
        contentViews[0] = findViewById(R.id.content_view_1);
        

        headerViews[1] = findViewById(R.id.header_view_2);
        contentViews[1] = findViewById(R.id.content_view_2);
    
        headerViews[2] = findViewById(R.id.header_view_3);
        contentViews[2] = findViewById(R.id.content_view_3);
    
        headerViews[3] = findViewById(R.id.header_view_4);
        contentViews[3] = findViewById(R.id.content_view_4);
        
        headerViews[4] = findViewById(R.id.header_view_5);
        contentViews[4] = findViewById(R.id.content_view_5);
      
        for (int i = 0; i < headerViews.length; i++) {
            final int index = i; // Store the current index in a final variable for use in the click listener
        
            setExpandableClickListener(headerViews[i], contentViews[i], index);
        }

        

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
