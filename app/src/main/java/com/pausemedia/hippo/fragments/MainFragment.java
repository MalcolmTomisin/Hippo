package com.pausemedia.hippo.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pausemedia.hippo.MainActivity;
import com.pausemedia.hippo.R;
import com.pausemedia.hippo.databinding.FragmentMainBinding;
import com.pausemedia.hippo.model.Script;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment {

    private static int countToExit = 0;
    private final int REQ_CODE = 100;
    Script scriptModel;
    FirebaseAuth auth;
    FirebaseUser myUser;
    private FragmentMainBinding binding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        myUser = auth.getCurrentUser();
        assert myUser != null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (countToExit > 0){
                    countToExit--;
                    String retry = "Alright, Let's try that again " + myUser.getDisplayName();
                    binding.textView.setText(retry);
                    binding.imageView.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_microphone));
                    binding.imageView.setOnClickListener(view -> startSpeechToText());
                    return;
                }
                requireActivity().finishAffinity();
                System.exit(0);
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        scriptModel = new ViewModelProvider(requireActivity()).get(Script.class);
        scriptModel.getReport().observe(requireActivity(), script -> {
            binding.textView.setText(script);
        });
        if (MainActivity.checkIfEntrance == 0) scriptModel.postReport("Hello " + myUser.getDisplayName());
                binding.imageView.setOnClickListener(view -> {
            startSpeechToText();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQ_CODE:
                if (resultCode== RESULT_OK && data != null){
                    countToExit = 1;
                    ArrayList result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String report = (String) result.get(0);
                    String finalReport = report.concat(". \n" + myUser.getDisplayName());
                    scriptModel.postReport(finalReport);
                    binding.imageView.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_icons_checkmark));
                    binding.imageView.setOnClickListener(view -> {
                        MainActivity.checkIfEntrance = 1 ;
                        PromptFragment promptFragment = new PromptFragment();
                        FragmentTransaction ft =
                                requireActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container, promptFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    });
                }
                break;
        }
    }

    private void startSpeechToText(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");

        try{
            startActivityForResult(intent, REQ_CODE);
        } catch (ActivityNotFoundException e){
            Toast.makeText(requireContext(),
                    "Sorry your device not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
