package com.pausemedia.hippo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.pausemedia.hippo.databinding.ActivityPromptBinding;

public class PromptActivity extends AppCompatActivity {

    ActivityPromptBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prompt);
    }
}
