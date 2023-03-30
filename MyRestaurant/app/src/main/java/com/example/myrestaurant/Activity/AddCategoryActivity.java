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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.DAO.DishCategoryDAO;
import com.example.myrestaurant.Model.DishCategory;
import com.example.myrestaurant.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAddCategoryCreate;
    ImageView imgAddCategoryBack, imgAddCategoryAddImage;
    TextView txtAddCategoryTitle;
    TextInputLayout txtAddCategoryName;
    DishCategoryDAO dishCategoryDAO;
    int categoryId = 0;
    Bitmap bitmapold;   //Bitmap dạng ảnh theo ma trận các pixel

    //dùng result launcher do activityforresult ko dùng đc nữa
    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                Uri uri = result.getData().getData();
                try{
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgAddCategoryAddImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_category);

        dishCategoryDAO = new DishCategoryDAO(this);  //khởi tạo đối tượng dao kết nối csdl

        //region Lấy đối tượng view
        btnAddCategoryCreate = (Button)findViewById(R.id.btnAddcategoryCreate);
        txtAddCategoryName = (TextInputLayout)findViewById(R.id.txtAddcategoryName);
        imgAddCategoryBack = (ImageView)findViewById(R.id.imgAddCategoryBack);
        imgAddCategoryAddImage = (ImageView)findViewById(R.id.imgAddcategoryAddImage);
        txtAddCategoryTitle = (TextView)findViewById(R.id.txtAddcategoryTitle);
        //endregion

        BitmapDrawable olddrawable = (BitmapDrawable)imgAddCategoryAddImage.getDrawable();
        bitmapold = olddrawable.getBitmap();

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        categoryId = getIntent().getIntExtra("categoryId",0);
        if(categoryId != 0){
            txtAddCategoryTitle.setText(getResources().getString(R.string.editcategory));
            DishCategory dishCategory = dishCategoryDAO.SelectCategoryById(categoryId);

            //Hiển thị lại thông tin từ csdl
            txtAddCategoryName.getEditText().setText(dishCategory.getCategoryName());

            byte[] categoryimage = dishCategory.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
            imgAddCategoryAddImage.setImageBitmap(bitmap);

            btnAddCategoryCreate.setText("Edit Category");
        }

        imgAddCategoryBack.setOnClickListener(this);
        imgAddCategoryAddImage.setOnClickListener(this);
        btnAddCategoryCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean ktra;
        String function;
        switch (id){
            case R.id.imgAddCategoryBack:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right); //animation
                break;

            case R.id.imgAddcategoryAddImage:
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*"); //lấy những mục chứa hình ảnh
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);   //lấy mục hiện tại đang chứa hình
                resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG,getResources().getString(R.string.choseimg)));    //mở intent chọn hình ảnh
                break;

            case R.id.btnAddcategoryCreate:
                if(!validateImage() | !validateName()) {
                    return;
                }

                String sCatName = txtAddCategoryName.getEditText().getText().toString();
                DishCategory dishCategory = new DishCategory();
                dishCategory.setCategoryName(sCatName);
                dishCategory.setImage(imageViewtoByte(imgAddCategoryAddImage));
                if(categoryId != 0) {
                    ktra = dishCategoryDAO.EditCategory(dishCategory, categoryId);
                    function = "editcategory";
                } else {
                    ktra = dishCategoryDAO.AddCategory(dishCategory);
                    function = "addcategory";
                }

                //Thêm, sửa loại dựa theo obj loaimonDTO
                Intent intent = new Intent();
                intent.putExtra("ktra", ktra);
                intent.putExtra("function", function);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    //Chuyển ảnh bitmap về mảng byte lưu vào csdl
    private byte[] imageViewtoByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //region validate fields
    private boolean validateImage() {
        BitmapDrawable drawable = (BitmapDrawable)imgAddCategoryAddImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold) {
            Toast.makeText(getApplicationContext(),"Please to choose an image",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateName() {
        String val = txtAddCategoryName.getEditText().getText().toString().trim();
        if(val.isEmpty()) {
            txtAddCategoryName.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            txtAddCategoryName.setError(null);
            txtAddCategoryName.setErrorEnabled(false);
            return true;
        }
    }
}
