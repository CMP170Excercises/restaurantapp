package com.example.myrestaurant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.DAO.PermissionDAO;
import com.example.myrestaurant.DAO.StaffDAO;
import com.example.myrestaurant.Model.Staff;
import com.example.myrestaurant.R;

import java.util.Calendar;

public class Register2Activity extends AppCompatActivity {
    RadioGroup rgSignupGender;
    DatePicker dpSignupDob;
    Button btnSignupNext;
    String fullname, user, email, phone, pass, gender;
    StaffDAO staffDAO;
    PermissionDAO permissionDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register_2);

        //lấy đối tượng view
        rgSignupGender = (RadioGroup)findViewById(R.id.rgSignupGender);
        dpSignupDob = (DatePicker)findViewById(R.id.dpSignupDob);
        btnSignupNext = (Button)findViewById(R.id.btnSignupNext);

        //lấy dữ liệu từ bundle của register1
        Bundle bundle = getIntent().getBundleExtra(RegisterActivity.BUNDLE);
        if(bundle != null) {
            fullname = bundle.getString("fullname");
            user = bundle.getString("username");
            email = bundle.getString("email");
            phone = bundle.getString("phone");
            pass = bundle.getString("password");
        }
        //khởi tạo kết nối csdl
        staffDAO = new StaffDAO(this);
        permissionDAO = new PermissionDAO(this);

        btnSignupNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(/*!validateAge() | */!validateGender()) {
                    return;
                }

                //lấy các thông tin còn lại
                switch (rgSignupGender.getCheckedRadioButtonId()){
                    case R.id.rbSignupMale:
                        gender = "Male"; break;
                    case R.id.rbSignupFemale:
                        gender = "Female"; break;
                    case R.id.rbSignupOther:
                        gender = "Other"; break;
                }
                String dob = dpSignupDob.getDayOfMonth() + "/" + (dpSignupDob.getMonth() + 1) + "/" + dpSignupDob.getYear();

                //truyền dữ liệu vào obj nhanvienDTO
                Staff staff = new Staff();
                staff.setStaffFullname(fullname);
                staff.setUsername(user);
                staff.setEmail(email);
                staff.setPhone(phone);
                staff.setPassword(pass);
                staff.setGender(gender);
                staff.setDob(dob);

                //nếu nhân viên đầu tiên đăng ký sẽ có quyền quản lý
                if(!staffDAO.CheckExist()) {
                    permissionDAO.AddPermission("Manager");
                    permissionDAO.AddPermission("Staff");
                    staff.setPermissionId(1);
                } else {
                    staff.setPermissionId(2);
                }

                //Thêm nv dựa theo obj nhanvienDTO
                long ktra = staffDAO.AddStaff(staff);
                if(ktra != 0) {
                    Toast.makeText(Register2Activity.this, getResources().getString(R.string.add_sucessful), Toast.LENGTH_SHORT).show();
                    callLoginFromRegister();
                } else {
                    Toast.makeText(Register2Activity.this, getResources().getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Hàm quay về màn hình trước
    public void backFromRegister2(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    //Hàm chuyển màn hình khi hoàn thành
    public void callLoginFromRegister(){
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //region Validate field
    private boolean validateGender() {
        if(rgSignupGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this,"Please choose the gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

//    private boolean validateAge() {
//        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//        int userAge = dpSignupDob.getYear();
//        int isAgeValid = currentYear - userAge;
//
//        if(isAgeValid < 10) {
//            Toast.makeText(this, "You are not old enough to register!", Toast.LENGTH_SHORT).show();
//            return false;
//        } else {
//            return true;
//        }
//    }
}
