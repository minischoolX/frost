package com.leotenebris.frostweb.AddOns;
/**
import android.content.Intent;
*/

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.leotenebris.frostweb.R;

public class AddOnsFragment extends Fragment {
    private SharedPreferences sharedPreferences;

    public AddOnsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_ons, container, false);

        // Initialize views
        LinearLayout adBlockerCard = rootView.findViewById(R.id.adBlockerCard);
        ImageView adBlockerImg = rootView.findViewById(R.id.adBlockerImg);
        TextView adBlockerTitle = rootView.findViewById(R.id.adBlockerTitle);
        Switch adBlockerEnabled = rootView.findViewById(R.id.adBlockerEnabled);
        TextView adBlockerDescription = rootView.findViewById(R.id.adBlockerDescription);

        LinearLayout videoEnhancerCard = rootView.findViewById(R.id.videoEnhancerCard);
        ImageView videoEnhancerImg = rootView.findViewById(R.id.videoEnhancerImg);
        TextView videoEnhancerTitle = rootView.findViewById(R.id.videoEnhancerTitle);
        Switch videoEnhancerEnabled = rootView.findViewById(R.id.videoEnhancerEnabled);
        TextView videoEnhancerDescription = rootView.findViewById(R.id.videoEnhancerdescription);
        TextView videoEnhancerInfo = rootView.findViewById(R.id.videoEnhancerInfo);
        ImageView videoEnhancerReloadImg = rootView.findViewById(R.id.videoEnhancerReloadImg);

        LinearLayout readAloudCard = rootView.findViewById(R.id.readAloudCard);
        ImageView readAloudImg = rootView.findViewById(R.id.readAloudImg);
        TextView readAloudTitle = rootView.findViewById(R.id.readAloudTitle);
        Switch readAloudEnabled = rootView.findViewById(R.id.readAloudEnabled);
        TextView readAloudDescription = rootView.findViewById(R.id.readAloudDescription);

        // Initialize SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("AddOnsPrefs", getActivity().MODE_PRIVATE);

        videoEnhancerTitle.setVisibility(View.GONE);
        // Set initial switch state from SharedPreferences
        adBlockerEnabled.setChecked(sharedPreferences.getBoolean("adBlocker", true));
        videoEnhancerEnabled.setChecked(sharedPreferences.getBoolean("videoEnhancer", true));
        readAloudEnabled.setChecked(sharedPreferences.getBoolean("readAloud", true));
        // Set switch change listeners
        adBlockerEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Store the new switch state in SharedPreferences
                sharedPreferences.edit().putBoolean("adBlocker", isChecked).apply();
            }
        });

        videoEnhancerEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Store the new switch state in SharedPreferences
                sharedPreferences.edit().putBoolean("videoEnhancer", isChecked).apply();

                if (isChecked) {
                    
                    videoEnhancerInfo.setVisibility(View.VISIBLE);
                    videoEnhancerInfo.setText("Video Enhancer is configured with default settings. You can click anywhere on this card to modify your preferences.");
                    //videoEnhancerReloadImg.setVisibility(View.VISIBLE);
                    //videoEnhancerReloadImg.setImageResource(R.drawable.video_enhancer_reload);
                    videoEnhancerCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (v.getId() != R.id.videoEnhancerEnabled) {
                                // Handle the click event for the LinearLayout (excluding the Switch)
                                // Add your custom logic here
                                // Perform the desired action when the Video Enhancer card is clicked
                                Toast.makeText(getActivity(), "Video Enhancer card clicked!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    videoEnhancerInfo.setVisibility(View.GONE);
                    //videoEnhancerReloadImg.setVisibility(View.GONE);
                    videoEnhancerImg.setOnClickListener(null);
                }
                
            }
        });

        readAloudEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Store the new switch state in SharedPreferences
                sharedPreferences.edit().putBoolean("readAloud", isChecked).apply();
            }
        });

        
//        videoEnhancerInfo.setVisibility(View.GONE);
//        videoEnhancerInfo.setVisibility(View.VISIBLE);
//        videoEnhancerInfo.setText("Video Enhancer");
//        videoEnhancerReloadImg.setVisibility(View.GONE);
//        videoEnhancerReloadImg.setVisibility(View.VISIBLE);
//        videoEnhancerReloadImg.setImageResource(R.drawable.video_enhancer_reload);
        

        return rootView;
    }
}
