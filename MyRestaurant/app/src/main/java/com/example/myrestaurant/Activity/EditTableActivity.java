package com.example.myrestaurant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.DAO.TableDAO;
import com.example.myrestaurant.R;
import com.google.android.material.textfield.TextInputLayout;

public class EditTableActivity extends AppCompatActivity {
    TextInputLayout txtEditTableName;
    Button btnEditTableEdit;
    TableDAO tableDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_table);

        //thuộc tính view
        txtEditTableName = (TextInputLayout)findViewById(R.id.txtEditTableName);
        btnEditTableEdit = (Button)findViewById(R.id.btnEditTableEdit);

        //khởi tạo dao mở kết nối csdl
        tableDAO = new TableDAO(this);
        int tableId = getIntent().getIntExtra("tableId",0); //lấy maban từ bàn đc chọn

        btnEditTableEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = txtEditTableName.getEditText().getText().toString();

                if(tableName != null || tableName.equals("")) {
                    boolean ktra = tableDAO.EditTableName(tableId, tableName);
                    Intent intent = new Intent();
                    intent.putExtra("editresult",ktra);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}
