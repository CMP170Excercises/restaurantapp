package com.example.myrestaurant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.DAO.TableDAO;
import com.example.myrestaurant.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddTableActivity extends AppCompatActivity {
    TextInputLayout txtAddTableName;
    Button btnAddTableCreate;
    TableDAO tableDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_table);

        //region Lấy đối tượng trong view
        txtAddTableName = (TextInputLayout)findViewById(R.id.txtAddTableName);
        btnAddTableCreate = (Button)findViewById(R.id.btnAddTableCreate);

        tableDAO = new TableDAO(this);
        btnAddTableCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTableName = txtAddTableName.getEditText().getText().toString();
                if(sTableName != null || sTableName.equals("")) {
                    boolean ktra = tableDAO.AddTable(sTableName);
                    //trả về result cho displaytable
                    Intent intent = new Intent();
                    intent.putExtra("addresult", ktra);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    //validate dữ liệu
    private boolean validateName(){
        String val = txtAddTableName.getEditText().getText().toString().trim();
        if(val.isEmpty()) {
            txtAddTableName.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            txtAddTableName.setError(null);
            txtAddTableName.setErrorEnabled(false);
            return true;
        }
    }
}
