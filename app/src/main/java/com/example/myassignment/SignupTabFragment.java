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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class SignupTabFragment extends Fragment {
    EditText nameEditText, emailEditText, phoneEditText, passEditText;
    TextView signInTextView;
    CheckBox checkBox;
    CountryCodePicker countryCodePicker;
    Button regButton;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();

        nameEditText = (EditText) root.findViewById(R.id.nameEditText);
        emailEditText = (EditText) root.findViewById(R.id.email);
        phoneEditText = (EditText) root.findViewById(R.id.editTextPhone2);
        passEditText = (EditText) root.findViewById(R.id.pass);
        countryCodePicker = (CountryCodePicker) root.findViewById(R.id.countryCodePicker);
        checkBox = (CheckBox) root.findViewById(R.id.checkbox);
        regButton = (Button) root.findViewById(R.id.regButton);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        signInTextView = (TextView) root.findViewById(R.id.signInTextView);


        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.constraintLayout, new LoginTabFragment()).addToBackStack(null).commit();

            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, email, pass, phone, countryCode;
                name = nameEditText.getText().toString();
                email = emailEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                pass = passEditText.getText().toString();
                countryCode = countryCodePicker.getSelectedCountryCode().toString();

                if (TextUtils.isEmpty(name)){
                    nameEditText.setError("Name cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    emailEditText.setError("Email cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    phoneEditText.setError("Phone cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    passEditText.setError("Password cannot be empty");
                    return;
                }
                    progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, pass)
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
