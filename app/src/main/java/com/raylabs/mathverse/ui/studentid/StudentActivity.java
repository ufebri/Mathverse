package com.raylabs.mathverse.ui.studentid;


import static com.raylabs.mathverse.util.GeneralHelper.showMessage;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import com.raylabs.mathverse.R;
import com.raylabs.mathverse.data.local.database.DBHelper;

public class StudentActivity extends AppCompatActivity implements TextWatcher {

    private TextInputEditText etNim, etName, etProgram, etHp, etEmail;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        //binding
        etNim = findViewById(R.id.et_nim);
        etName = findViewById(R.id.et_fullName);
        etProgram = findViewById(R.id.et_program);
        etHp = findViewById(R.id.et_phoneNumber);
        etEmail = findViewById(R.id.et_email);
        Button btnSave = findViewById(R.id.btn_save);
        Button btnListStudents = findViewById(R.id.btn_list);

        //init helper
        dbHelper = new DBHelper(this);

        //Klik Simpan
        btnSave.setOnClickListener(v -> {
            if (isDataValid()) {
                addStudent();
            } else {
                showMessage(this, "NIM, Nama, Program Studi, dan Nomor Handphone Wajib Di Isi!");
            }
        });

        //Klik ke menu Daftar Siswa
        btnListStudents.setOnClickListener(v -> startActivity(new Intent(this, ListStudentsActivity.class)));

        //listener
        etNim.addTextChangedListener(this);
        etName.addTextChangedListener(this);
        etProgram.addTextChangedListener(this);
        etHp.addTextChangedListener(this);
        etEmail.addTextChangedListener(this);
    }

    private boolean isDataValid() {
        return etNim.length() > 0 && etName.length() > 0 && etProgram.length() > 0 && etHp.length() > 0;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        isDataValid();
    }

    //Tambah Data
    private void addStudent() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        try {

            String nim = etNim.getText().toString();
            String name = etName.getText().toString();
            String program = etProgram.getText().toString();
            String noHp = etHp.getText().toString();
            String email = etEmail.getText().toString();

            values.put("nim", nim != null ? nim : "");
            values.put("name", name != null ? name : "");
            values.put("program", program != null ? program : "");
            values.put("nohp", noHp != null ? noHp : "");
            values.put("email", email != null ? email : "");
        } catch (Exception e) {
            Log.d("DB", "addStudent: " + e.getLocalizedMessage());
        }

        long newRowId = db.insert("students", null, values);

        // TODO: Handle the result, display a toast or something
        showMessage(this, "Simpan Berhasil");
        Log.d("DB", "addStudent: " + newRowId);

        db.close();
        clearText();
    }

    //hapus field
    private void clearText() {
        try {
            if (isDataValid()) {
                etNim.getText().clear();
                etName.getText().clear();
                etProgram.getText().clear();
                etHp.getText().clear();
                etEmail.getText().clear();
            }
        } catch (Exception e) {
            Log.d("DB", "clearText: " + e.getLocalizedMessage());
        }
    }
}