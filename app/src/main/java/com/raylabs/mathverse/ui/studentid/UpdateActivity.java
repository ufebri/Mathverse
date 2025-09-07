package com.raylabs.mathverse.ui.studentid;

import android.content.ContentValues;
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
import com.raylabs.mathverse.data.local.entity.Student;
import com.raylabs.mathverse.util.GeneralHelper;

public class UpdateActivity extends AppCompatActivity implements TextWatcher {

    private TextInputEditText etNim, etName, etProgram, etHp, etEmail;
    private DBHelper dbHelper;
    private String nim;
    private Student student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //binding
        etNim = findViewById(R.id.et_nim);
        etName = findViewById(R.id.et_fullName);
        etProgram = findViewById(R.id.et_program);
        etHp = findViewById(R.id.et_phoneNumber);
        etEmail = findViewById(R.id.et_email);
        Button btnUpdate = findViewById(R.id.btn_update);

        dbHelper = new DBHelper(this);

        nim = getIntent().getStringExtra("nim");

        student = dbHelper.getStudentById(nim);

        //SetText
        etNim.setText(student.nim);
        etName.setText(student.name);
        etProgram.setText(student.program);
        etHp.setText(student.nohp);
        etEmail.setText(student.email);

        etEmail.addTextChangedListener(this);

        btnUpdate.setOnClickListener(v -> updateStudent(nim));
    }

    private void updateStudent(String studentId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        String email = etEmail.getText().toString();

        values.put("email", email != null ? email : "");

        int rowsAffected = db.update("students", values, "nim=?", new String[]{String.valueOf(studentId)});
        Log.d("DB", "updateStudent: " + rowsAffected);
        // TODO: Handle the result, display a toast or something
        GeneralHelper.showMessage(this, "Update Data Berhasil!");

        db.close();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}