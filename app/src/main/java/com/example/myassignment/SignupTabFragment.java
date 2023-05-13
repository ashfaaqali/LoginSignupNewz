package com.example.myassignment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    static String countryCode, countryName;
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
                //SWITCHES FRAGMENT TO LOGIN
                getFragmentManager().beginTransaction().replace(R.id.constraintLayout, new LoginTabFragment()).addToBackStack(null).commit();
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, email, pass, phone;
                name = nameEditText.getText().toString();
                email = emailEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                pass = passEditText.getText().toString();
                countryCode = countryCodePicker.getSelectedCountryCode();
                countryName = countryCodePicker.getSelectedCountryName();

                if (name.isEmpty()) nameEditText.setError("Name Cannot Be Empty");
                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) emailEditText.setError("Fix Email");
                if (phone.isEmpty()) phoneEditText.setError("Phone Cannot Be Empty");
                if (pass.length()<8) passEditText.setError("Password Should Have At Least 8 Digits");
                if (!checkBox.isChecked()) {
                    Toast.makeText(getContext(), "Agree With Terms And Conditions", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                // Sign in success
                                if (task.isSuccessful()) Toast.makeText(getContext(), "Account Created, Login To Continue",
                                            Toast.LENGTH_SHORT).show();
                                // If sign in fails, displaying a message to the user.
                                else Toast.makeText(getContext(), "Some Error Occurred",
                                            Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return root;
    }
}
