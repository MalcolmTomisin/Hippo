package com.pausemedia.hippo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.pausemedia.hippo.databinding.FragmentPromptBinding;
import com.pausemedia.hippo.model.Script;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PromptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PromptFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseUser myUser;
    private FragmentPromptBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PromptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PromptFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PromptFragment newInstance(String param1, String param2) {
        PromptFragment fragment = new PromptFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPromptBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Script scriptModel = new ViewModelProvider(requireActivity()).get(Script.class);
        scriptModel.getReport().observe(requireActivity(), script -> {
            binding.textView2.setText(script);
        });
        binding.delete.setOnClickListener(view ->
                requireActivity().getSupportFragmentManager().popBackStack());
        binding.share.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, binding.textView2.getText());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });
    }
}
