package com.example.myassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;

public class LoggedIn extends AppCompatActivity {
    TextView email, name, phone,country, activeFrom;
    Button logoutButton;
    CountryCodePicker ccp;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        email = findViewById(R.id.email);
        country = findViewById(R.id.country);
        logoutButton = findViewById(R.id.logoutButton);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user == null){
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginTabFragment.class);
            startActivity(intent);
        } else
        email.setText(user.getEmail());
        country.setText("(+"+SignupTabFragment.countryCode+") "+SignupTabFragment.countryName);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}