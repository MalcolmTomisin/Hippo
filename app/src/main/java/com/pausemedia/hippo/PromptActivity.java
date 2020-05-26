package com.pausemedia.hippo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.pausemedia.hippo.databinding.ActivityPromptBinding;

public class PromptActivity extends AppCompatActivity {
    String finalReport;
    ActivityPromptBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prompt);
        Intent intent = getIntent();
        finalReport = intent.getStringExtra(MainActivity.myScript);
        binding.textView2.setText(finalReport);
        binding.delete.setOnClickListener(view -> PromptActivity.this.finish());
        binding.share.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, finalReport);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });
    }
}
