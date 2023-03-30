package com.example.myrestaurant.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    ImageView imgSignupBack;
    Button btnSignupNext;
    TextView txtSignupTitle;
    TextInputLayout txtSignupFullname, txtSignupUsername, txtSignupEmail, txtSignupPhone, txtSignupPassword;

    public static final String BUNDLE = "BUNDLE";
    private static final Pattern PASSWORD_PATTERN =
        Pattern.compile("^" +
                //"(?=.*[@#$%^&+=])" +   // at least 1 special character
                "(?=\\S+$)" +            // no white spaces
                ".{6,}" +                // at least 4 characters
                "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        // Lấy đối tượng view
        imgSignupBack = (ImageView)findViewById(R.id.imgSignupBack);
        btnSignupNext = (Button)findViewById(R.id.btnSignupNext);
        txtSignupTitle = (TextView)findViewById(R.id.txtSignupTitle);
        txtSignupFullname = (TextInputLayout)findViewById(R.id.txtSignupFullname);
        txtSignupUsername = (TextInputLayout)findViewById(R.id.txtSignupUsername);
        txtSignupEmail = (TextInputLayout)findViewById(R.id.txtSignupEmail);
        txtSignupPhone = (TextInputLayout)findViewById(R.id.txtSignupPhone);
        txtSignupPassword = (TextInputLayout)findViewById(R.id.txtSignupPassword);

        btnSignupNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kiểm tra validate false => phải thỏa đk validate
                if(!validateFullName() | !validateUserName() | !validateEmail() | !validatePhone() | !validatePassWord()){
                    return;
                }
                String fullname = txtSignupFullname.getEditText().getText().toString();
                String user = txtSignupUsername.getEditText().getText().toString();
                String email = txtSignupEmail.getEditText().getText().toString();
                String phone = txtSignupPhone.getEditText().getText().toString();
                String pass = txtSignupPassword.getEditText().getText().toString();

                byBundleNextSignupScreen(fullname, user, email, phone, pass);
            }
        });
    }

    // Quay về màn hình trước
    public void backFromRegister(View view) {
        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.layoutRegister), "transition_signup");

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
            startActivity(intent,options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    // truyền dữ liệu qua trang đk 2 bằng bundle
    public void byBundleNextSignupScreen(String fullname, String user, String email, String phone, String pass) {
        Intent intent = new Intent(getApplicationContext(), Register2Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("fullname", fullname);
        bundle.putString("username", user);
        bundle.putString("email", email);
        bundle.putString("phone", phone);
        bundle.putString("password", pass);
        intent.putExtra(BUNDLE, bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    // Validate field
    private boolean validateFullName(){
        String val = txtSignupFullname.getEditText().getText().toString().trim();

        if(val.isEmpty()) {
            txtSignupFullname.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            txtSignupFullname.setError(null);
            txtSignupFullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName() {
        String val = txtSignupUsername.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";

        if(val.isEmpty()) {
            txtSignupUsername.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if(val.length()>50) {
            txtSignupUsername.setError("Must be less than 50 characters");
            return false;
        } else if(!val.matches(checkspaces)) {
            txtSignupUsername.setError("No spacing!");
            return false;
        } else {
            txtSignupUsername.setError(null);
            txtSignupUsername.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = txtSignupEmail.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if(val.isEmpty()) {
            txtSignupEmail.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if(!val.matches(checkspaces)){
            txtSignupEmail.setError("Email is invalid!");
            return false;
        } else {
            txtSignupEmail.setError(null);
            txtSignupEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone() {
        String val = txtSignupPhone.getEditText().getText().toString().trim();

        if(val.isEmpty()) {
            txtSignupPhone.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if(val.length() != 10) {
            txtSignupPhone.setError("Phone number is invalid!");
            return false;
        } else {
            txtSignupPhone.setError(null);
            txtSignupPhone.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = txtSignupPassword.getEditText().getText().toString().trim();

        if(val.isEmpty()) {
            txtSignupPassword.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if(!PASSWORD_PATTERN.matcher(val).matches()) {
            txtSignupPassword.setError("Password must be at least 6 characters!");
            return false;
        } else {
            txtSignupPassword.setError(null);
            txtSignupPassword.setErrorEnabled(false);
            return true;
        }
    }
}
