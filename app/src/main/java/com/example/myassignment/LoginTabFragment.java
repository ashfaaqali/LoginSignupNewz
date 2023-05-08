package com.example.myassignment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;

public class LoginTabFragment extends Fragment {
    FloatingActionButton fb, google;

    EditText emailEditText, passEditText;
    Button loginButton;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    TextView regTextView;

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = (EditText) root.findViewById(R.id.email);
        passEditText = (EditText) root.findViewById(R.id.pass);
        loginButton = (Button) root.findViewById(R.id.loginButton);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        regTextView = (TextView) root.findViewById(R.id.regTextView);

        regTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.constraintLayout, new SignupTabFragment()).addToBackStack(null).commit();

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass;
                email = emailEditText.getText().toString();
                pass = passEditText.getText().toString();

                if (TextUtils.isEmpty(email)){
                    emailEditText.setError("Email cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    passEditText.setError("Password cannot be empty");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getContext(), "Account Created!",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        return root;
    }
}
