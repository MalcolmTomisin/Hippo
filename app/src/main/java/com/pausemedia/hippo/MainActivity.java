package com.pausemedia.hippo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pausemedia.hippo.databinding.ActivityMainBinding;
import com.pausemedia.hippo.model.Script;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Script scriptModel;
    private final int REQ_CODE = 100;
    FirebaseAuth auth;
    FirebaseUser myUser;
    public static final String myScript = "SCRIPT";
    private static int countToExit = 0;

    @Override
    public void onBackPressed() {
        if (countToExit > 0){
            countToExit--;
            binding.imageView.setImageDrawable(getDrawable(R.drawable.ic_microphone));
            return;
        }
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main );
        scriptModel = new ViewModelProvider(this).get(Script.class);
        scriptModel.getReport().observe(this, script -> {
            binding.textView.setText(script);
        });
        auth = FirebaseAuth.getInstance();
        myUser = auth.getCurrentUser();
        assert myUser != null;
        scriptModel.postReport("Hello " + myUser.getDisplayName());
        binding.imageView.setOnClickListener(view -> {
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
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQ_CODE:
                if (resultCode== RESULT_OK && data != null){
                    countToExit = 1;
                    ArrayList result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String report = (String) result.get(0);
                    String finalReport = report.concat(". \n" + myUser.getDisplayName());
                    scriptModel.postReport(finalReport);
                    binding.imageView.setImageDrawable(getDrawable(R.drawable.ic_icons_checkmark));
                    binding.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            FragmentManager manager = getSupportFragmentManager();
//                            FragmentTransaction transaction = manager.beginTransaction();
//                            ReportFragment blankFragment = new ReportFragment();
//                            transaction.add(R.id.fragment_prompt, blankFragment);
//                            transaction.commit();
                            Intent scriptIntent = new Intent(MainActivity.this, PromptActivity.class);
                            scriptIntent.putExtra(myScript,finalReport);
                            startActivity(scriptIntent);
                        }
                    });
                }
                break;
        }
    }
}
