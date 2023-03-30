package com.example.myrestaurant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.DAO.StaffDAO;
import com.example.myrestaurant.Model.Staff;
import com.example.myrestaurant.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.regex.Pattern;

public class AddStaffActivity extends AppCompatActivity implements View.OnClickListener {
    private static final Pattern PASSWORD_PATTERN =
        Pattern.compile("^" +
                //"(?=.*[@#$%^&+=])" +   // at least 1 special character
                "(?=\\S+$)" +            // no white spaces
                ".{6,}" +                // at least 4 characters
                "$");

    ImageView imgAddStaffBack;
    TextView txtAddStaffTitle;
    TextInputLayout txtAddStaffFullname, txtAddStaffUsername, txtAddStaffEmail, txtAddStaffPhone, txtAddStaffPassword;
    RadioGroup rgAddStaffGender, rgAddStaffPermission;
    RadioButton rbAddStaffMale, rbAddStaffFemale, rbAddStaffOther, rbAddStaffManager, rbAddStaffStaff;
    DatePicker dpAddStaffDob;
    Button btnAddStaffAddstaff;
    StaffDAO staffDAO;
    String fullname, username, email, phone, password, gender, dob;
    int staffId = 0, permission = 0;
    long ktra = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_staff);

        // Lấy đối tượng trong view
        txtAddStaffTitle = (TextView)findViewById(R.id.txtAddStaffTitle);
        imgAddStaffBack = (ImageView)findViewById(R.id.imgAddStaffBack);
        txtAddStaffFullname = (TextInputLayout)findViewById(R.id.txtAddStaffFullname);
        txtAddStaffUsername = (TextInputLayout)findViewById(R.id.txtAddStaffUserame);
        txtAddStaffEmail = (TextInputLayout)findViewById(R.id.txtAddStaffEmail);
        txtAddStaffPhone = (TextInputLayout)findViewById(R.id.txtAddStaffPhone);
        txtAddStaffPassword = (TextInputLayout)findViewById(R.id.txtAddStaffPassword);
        rgAddStaffGender = (RadioGroup)findViewById(R.id.rgAddStaffGender);
        rgAddStaffPermission = (RadioGroup)findViewById(R.id.rgAddStaffPermission);
        rbAddStaffMale = (RadioButton)findViewById(R.id.rbAddStaffMale);
        rbAddStaffFemale = (RadioButton)findViewById(R.id.rbAddStaffFemale);
        rbAddStaffOther = (RadioButton)findViewById(R.id.rbAddStaffOther);
        rbAddStaffManager = (RadioButton)findViewById(R.id.rbAddStaffManager);
        rbAddStaffStaff = (RadioButton)findViewById(R.id.rbAddStaffStaff);
        dpAddStaffDob = (DatePicker)findViewById(R.id.dpAddStaffDob);
        btnAddStaffAddstaff = (Button)findViewById(R.id.btnAddStaffAdd);

        staffDAO = new StaffDAO(this);

        // Hiển thị trang sửa nếu được chọn từ context menu sửa
        staffId = getIntent().getIntExtra("staffId", 0);   //lấy staffId từ display staff
        if(staffId != 0) {
            txtAddStaffTitle.setText("Edit Staff");
            Staff staff = staffDAO.SelectStaffById(staffId);

            // Hiển thị thông tin từ csdl
            txtAddStaffFullname.getEditText().setText(staff.getStaffFullname());
            txtAddStaffUsername.getEditText().setText(staff.getUsername());
            txtAddStaffEmail.getEditText().setText(staff.getEmail());
            txtAddStaffPhone.getEditText().setText(staff.getPhone());
            txtAddStaffPassword.getEditText().setText(staff.getPassword());

            // Hiển thị giới tính từ csdl
            String gender = staff.getGender();
            if(gender.equals("Male")) {
                rbAddStaffMale.setChecked(true);
            } else if(gender.equals("Female")) {
                rbAddStaffFemale.setChecked(true);
            } else {
                rbAddStaffOther.setChecked(true);
            }

            if(staff.getPermissionId() == 1) {
                rbAddStaffManager.setChecked(true);
            } else {
                rbAddStaffStaff.setChecked(true);
            }

            // Hiển thị date of birth từ csdl
            String date = staff.getDob();
            String[] items = date.split("/");
            int day = Integer.parseInt(items[0]);
            int month = Integer.parseInt(items[1]) - 1;
            int year = Integer.parseInt(items[2]);
            dpAddStaffDob.updateDate(year, month, day);
            btnAddStaffAddstaff.setText("Edit Staff");
        }

        btnAddStaffAddstaff.setOnClickListener(this);
        imgAddStaffBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String function;
        switch (id){
            case R.id.btnAddStaffAdd:
                if(/*!validateAge() |*/ !validateEmail() | !validateFullName() | !validateGender() | !validatePassWord() | !validatePermission() | !validatePhone() | !validateUserName()) {
                    return;
                }
                // Lấy dữ liệu từ view
                fullname = txtAddStaffFullname.getEditText().getText().toString();
                username = txtAddStaffUsername.getEditText().getText().toString();
                email = txtAddStaffEmail.getEditText().getText().toString();
                phone = txtAddStaffPhone.getEditText().getText().toString();
                password = txtAddStaffPassword.getEditText().getText().toString();

                switch (rgAddStaffGender.getCheckedRadioButtonId()){
                    case R.id.rbAddStaffMale: gender = "Male"; break;
                    case R.id.rbAddStaffFemale: gender = "Female"; break;
                    case R.id.rbAddStaffOther: gender = "Other"; break;
                }
                switch (rgAddStaffPermission.getCheckedRadioButtonId()){
                    case R.id.rbAddStaffManager: permission = 1; break;
                    case R.id.rbAddStaffStaff: permission = 2; break;
                }

                dob = dpAddStaffDob.getDayOfMonth() + "/" + (dpAddStaffDob.getMonth() + 1) +"/"+ dpAddStaffDob.getYear();

                //truyền dữ liệu vào obj nhanvienDTO
                Staff staff = new Staff();
                staff.setStaffFullname(fullname);
                staff.setUsername(username);
                staff.setEmail(email);
                staff.setPhone(phone);
                staff.setPassword(password);
                staff.setGender(gender);
                staff.setDob(dob);
                staff.setPermissionId(permission);

                if(staffId != 0) {
                    ktra = staffDAO.EditStaff(staff, staffId);
                    function = "edit";
                } else {
                    ktra = staffDAO.AddStaff(staff);
                    function = "addstaff";
                }
                //Thêm, sửa nv dựa theo obj nhanvienDTO
                Intent intent = new Intent();
                intent.putExtra("ketquaktra", ktra);
                intent.putExtra("function", function);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.imgAddStaffBack:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }

    // Validate fields
    private boolean validateFullName(){
        String val = txtAddStaffFullname.getEditText().getText().toString().trim();

        if(val.isEmpty()) {
            txtAddStaffFullname.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            txtAddStaffFullname.setError(null);
            txtAddStaffFullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = txtAddStaffUsername.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";

        if(val.isEmpty()) {
            txtAddStaffUsername.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if(val.length()>50){
            txtAddStaffUsername.setError("Must be less than 50 characters");
            return false;
        } else if(!val.matches(checkspaces)) {
            txtAddStaffUsername.setError("No spacing!");
            return false;
        } else {
            txtAddStaffUsername.setError(null);
            txtAddStaffUsername.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = txtAddStaffEmail.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if(val.isEmpty()) {
            txtAddStaffEmail.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if(!val.matches(checkspaces)) {
            txtAddStaffEmail.setError("Email is invalid!");
            return false;
        } else {
            txtAddStaffEmail.setError(null);
            txtAddStaffEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone() {
        String val = txtAddStaffPhone.getEditText().getText().toString().trim();

        if(val.isEmpty()) {
            txtAddStaffPhone.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if(val.length() != 10) {
            txtAddStaffPhone.setError("Phone number is invalid!");
            return false;
        } else {
            txtAddStaffPhone.setError(null);
            txtAddStaffPhone.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord() {
        String val = txtAddStaffPassword.getEditText().getText().toString().trim();

        if(val.isEmpty()) {
            txtAddStaffPassword.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if(!PASSWORD_PATTERN.matcher(val).matches()) {
            txtAddStaffPassword.setError("Password at least 6 characters!");
            return false;
        } else {
            txtAddStaffPassword.setError(null);
            txtAddStaffPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGender() {
        if(rgAddStaffGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please choose gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePermission() {
        if(rgAddStaffPermission.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,"Please choose permission", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

//    private boolean validateAge() {
//        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//        int userAge = dpAddStaffDob.getYear();
//        int isAgeValid = currentYear - userAge;
//
//        if(isAgeValid < 10) {
//            Toast.makeText(this, "You are not old enough to register!",Toast.LENGTH_SHORT).show();
//            return false;
//        } else {
//            return true;
//        }
//    }
}
