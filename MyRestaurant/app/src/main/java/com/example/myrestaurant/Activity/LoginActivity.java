package com.example.myrestaurant.Activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.DAO.StaffDAO;
import com.example.myrestaurant.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    Button btnLoginLogin, btnLoginRegister;
    TextInputLayout txtLoginUsername, txtLoginPassword;
    StaffDAO staffDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        //thuộc tính view
        txtLoginUsername = (TextInputLayout)findViewById(R.id.txtLoginUsername);
        txtLoginPassword = (TextInputLayout)findViewById(R.id.txtLoginPassword);
        btnLoginLogin = (Button)findViewById(R.id.btnLoginLogin);
        btnLoginRegister = (Button)findViewById(R.id.btnLoginRegister);

        staffDAO = new StaffDAO(this);    //khởi tạo kết nối csdl

        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUserName() | !validatePassWord()) {
                    return;
                }

                String user = txtLoginUsername.getEditText().getText().toString();
                String pass = txtLoginPassword.getEditText().getText().toString();
                int ktra = staffDAO.CheckLogin(user, pass);
                int permissionId = staffDAO.SelectStaffPermission(ktra);
                if(ktra != 0) {
                    // lưu mã quyền vào shareprefer
                    SharedPreferences sharedPreferences = getSharedPreferences("savepermission", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putInt("permissionId", permissionId);
                    editor.commit();

                    //gửi dữ liệu user qua trang chủ
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    intent.putExtra("username", txtLoginUsername.getEditText().getText().toString());
                    intent.putExtra("staffId",ktra);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"Login failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Hàm quay lại màn hình chính
    public void backFromLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        //tạo animation cho thành phần
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.layoutLogin), "transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
            startActivity(intent,options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    //Hàm chuyển qua trang đăng ký
    public void callRegisterFromLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //region Validate field
    private boolean validateUserName(){
        String val = txtLoginUsername.getEditText().getText().toString().trim();

        if(val.isEmpty()) {
            txtLoginUsername.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            txtLoginUsername.setError(null);
            txtLoginUsername.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord() {
        String val = txtLoginPassword.getEditText().getText().toString().trim();

        if(val.isEmpty()) {
            txtLoginPassword.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            txtLoginPassword.setError(null);
            txtLoginPassword.setErrorEnabled(false);
            return true;
        }
    }
}
