package com.pausemedia.hippo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import com.pausemedia.hippo.databinding.ActivityMainBinding;
import com.pausemedia.hippo.model.Script;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Script scriptModel;
    private final int REQ_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main );
        scriptModel = new ViewModelProvider(this).get(Script.class);
        scriptModel.getReport().observe(this, script -> {
            binding.textView.setText(script);
        });
        binding.imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");

                try{
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQ_CODE:
                if (resultCode== RESULT_OK && data != null){
                    ArrayList result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    scriptModel.setReport((String) result.get(0));
                }
                break;
        }
    }
}
