package com.example.myrestaurant.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.DAO.DishDAO;
import com.example.myrestaurant.Model.Dish;
import com.example.myrestaurant.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddMenuActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAddMenuAddDish;
    RelativeLayout layoutDishState;
    ImageView imgAddMenuAddImage, imgAddMenuBack;
    TextView txtAddMenuTitle;
    TextInputLayout txtAddMenuDishName, txtAddMenuPrice, txtAddMenuCategory;
    RadioGroup rgAddMenuState;
    RadioButton rbAddMenuDishStill, rbAddMenuOutOfDish;
    DishDAO dishDAO;
    String categoryName, sDishName, sPrice, sState;
    Bitmap bitmapold;
    int categoryId;
    int dishId = 0;

    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                Uri uri = result.getData().getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgAddMenuAddImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_menu);

        //Lấy đối tượng view
        imgAddMenuAddImage = (ImageView)findViewById(R.id.imgAddMenuAddImage);
        imgAddMenuAddImage.setTag(R.drawable.drinkfood);
        imgAddMenuBack = (ImageView)findViewById(R.id.imgAddMenuBack);
        txtAddMenuDishName = (TextInputLayout)findViewById(R.id.txtAddMenuName);
        txtAddMenuPrice = (TextInputLayout)findViewById(R.id.txtAddMenuPrice);
        txtAddMenuCategory = (TextInputLayout)findViewById(R.id.txtAddMenuCategory);
        btnAddMenuAddDish = (Button)findViewById(R.id.btnAddMenuAddDish);
        txtAddMenuTitle = (TextView)findViewById(R.id.txtAddmenuTitle);
        layoutDishState = (RelativeLayout)findViewById(R.id.layoutState);
        rgAddMenuState = (RadioGroup)findViewById(R.id.rgAddMenuState);
        rbAddMenuDishStill = (RadioButton)findViewById(R.id.rbAddMenuStill);
        rbAddMenuOutOfDish = (RadioButton)findViewById(R.id.rbAddMenuOutOf);
        //endregion

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId", -1);
        categoryName = intent.getStringExtra("categoryName");
        dishDAO = new DishDAO(this);  //khởi tạo đối tượng dao kết nối csdl
        txtAddMenuCategory.getEditText().setText(categoryName);

        BitmapDrawable olddrawable = (BitmapDrawable)imgAddMenuAddImage.getDrawable();
        bitmapold = olddrawable.getBitmap();

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        dishId = getIntent().getIntExtra("dishId", 0);
        if(dishId != 0) {
            txtAddMenuTitle.setText("Edit Menu");
            Dish dish = dishDAO.SelectDishById(dishId);

            txtAddMenuDishName.getEditText().setText(dish.getDishName());
            txtAddMenuPrice.getEditText().setText(dish.getPrice());

            byte[] menuimage = dish.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage, 0, menuimage.length);
            imgAddMenuAddImage.setImageBitmap(bitmap);

            layoutDishState.setVisibility(View.VISIBLE);
            String state = dish.getState();
            if(state.equals("true")) {
                rbAddMenuDishStill.setChecked(true);
            } else {
                rbAddMenuOutOfDish.setChecked(true);
            }

            btnAddMenuAddDish.setText("Add Dish");
        }

        imgAddMenuAddImage.setOnClickListener(this);
        btnAddMenuAddDish.setOnClickListener(this);
        imgAddMenuBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean ktra;
        String function;
        switch (id){
            case R.id.imgAddMenuAddImage:
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*");
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);
                resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG,getResources().getString(R.string.choseimg)));
                break;

            case R.id.imgAddMenuBack:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;

            case R.id.btnAddMenuAddDish:
                // check validation
                if(!validateImage() | !validateName() | !validatePrice()) {
                    return;
                }

                sDishName = txtAddMenuDishName.getEditText().getText().toString();
                sPrice = txtAddMenuPrice.getEditText().getText().toString();
                switch (rgAddMenuState.getCheckedRadioButtonId()){
                    case R.id.rbAddMenuStill: sState = "true";   break;
                    case R.id.rbAddMenuOutOf: sState = "false";  break;
                }

                Dish dish = new Dish();
                dish.setCategoryId(categoryId);
                dish.setDishName(sDishName);
                dish.setPrice(sPrice);
                dish.setState(sState);
                dish.setImage(imageViewtoByte(imgAddMenuAddImage));
                if(dishId != 0) {
                    ktra = dishDAO.EditDish(dish, dishId);
                    function = "editdish";
                } else {
                    ktra = dishDAO.AddDish(dish);
                    function = "adddish";
                }

                //Thêm, sửa món dựa theo obj dishcategory
                Intent intent = new Intent();
                intent.putExtra("ktra",ktra);
                intent.putExtra("function", function);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    //Chuyển ảnh bitmap về mảng byte lưu vào csdl
    private byte[] imageViewtoByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //region Validate field
    private boolean validateImage(){
        BitmapDrawable drawable = (BitmapDrawable)imgAddMenuAddImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold) {
            Toast.makeText(getApplicationContext(), "Please choose the image", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateName() {
        String val = txtAddMenuDishName.getEditText().getText().toString().trim();
        if(val.isEmpty()) {
            txtAddMenuDishName.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            txtAddMenuDishName.setError(null);
            txtAddMenuDishName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePrice() {
        String val = txtAddMenuPrice.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            txtAddMenuPrice.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if(!val.matches(("\\d+(?:\\.\\d+)?"))) {
            txtAddMenuPrice.setError("The price is invalid!");
            return false;
        } else {
            txtAddMenuPrice.setError(null);
            txtAddMenuPrice.setErrorEnabled(false);
            return true;
        }
    }
}
