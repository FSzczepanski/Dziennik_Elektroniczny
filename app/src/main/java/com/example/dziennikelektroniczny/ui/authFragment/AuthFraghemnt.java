package com.example.dziennikelektroniczny.ui.authFragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dziennikelektroniczny.R;
import com.example.dziennikelektroniczny.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;

public class AuthFraghemnt extends Fragment {

    private AuthFraghemntViewModel mViewModel;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.auth_fraghemnt_fragment, container, false);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser, root);

        register(root);
        login(root);



        return root;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuthFraghemntViewModel.class);


    }


    public void updateUI(FirebaseUser account, View view) {

        if (account != null) {
            Log.d(TAG, "signIn:success");
            NavController navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.my_nav_host_fragment);
            navController.navigate(R.id.action_authFraghemnt_to_mainPageFragment);

        } else {
            Log.w(TAG, "signIn:failure");
            Toast.makeText(getContext(), "Zaloguj się",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void register(View view) {
        Button registerButton = view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm(view)) {
                    return;
                }
                EditText emailEdit = view.findViewById(R.id.loginEmail);
                EditText passwordEdit = view.findViewById(R.id.loginPassword);
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                createAccount(email,password);

            }
        });

    }


    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            NavController navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.my_nav_host_fragment);
                            navController.navigate(R.id.action_authFraghemnt_to_mainPageFragment);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void login(View view) {
        Button loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm(view)) {
                    return;
                }

                EditText emailEdit = view.findViewById(R.id.loginEmail);
                EditText passwordEdit = view.findViewById(R.id.loginPassword);
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();


                signIn(email,password);



            }
        });

    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);



        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            NavController navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.my_nav_host_fragment);
                            navController.navigate(R.id.action_authFraghemnt_to_mainPageFragment);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    private boolean validateForm(View view) {
        boolean valid = true;

        EditText emailEdit = view.findViewById(R.id.loginEmail);
        EditText passwordEdit = view.findViewById(R.id.loginPassword);
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Podaj prawidłową wartość email",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Podaj prawidłowe hasło",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }
}