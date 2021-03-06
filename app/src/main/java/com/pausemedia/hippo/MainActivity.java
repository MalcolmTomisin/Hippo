package com.pausemedia.hippo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pausemedia.hippo.databinding.ActivityMainBinding;
import com.pausemedia.hippo.fragments.MainFragment;
import com.pausemedia.hippo.model.Script;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Script scriptModel;
    FirebaseAuth auth;
    FirebaseUser myUser;
    public static int checkIfEntrance = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        switch (item.getItemId()){
            case R.id.sign_out:
                builder.setMessage("Do you want to sign out?");
                builder.setTitle("Sign out!");
                builder.setIcon(R.drawable.ic_logo);
                builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                    AuthUI.getInstance()
                            .signOut(this)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    startActivity(new Intent(MainActivity.this, FirebaseActivity.class));
                                    finish();
                                }
                            });
                });
                builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main );
        scriptModel = new ViewModelProvider(this).get(Script.class);
        auth = FirebaseAuth.getInstance();
        myUser = auth.getCurrentUser();
        assert myUser != null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        //scriptModel.postReport("Hello " + myUser.getDisplayName());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        fragmentTransaction.add(R.id.fragment_container, mainFragment);
        fragmentTransaction.commit();
    }

}
