package com.raylabs.mathverse.ui.studentid;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raylabs.mathverse.R;
import com.raylabs.mathverse.data.local.database.DBHelper;
import com.raylabs.mathverse.util.GeneralHelper;


public class ListStudentsActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_students);

        //binding
        RecyclerView rvListStudents = findViewById(R.id.rv_list);

        //init helper
        dbHelper = new DBHelper(this);

        //show the list
        adapter = new StudentAdapter(dbHelper.getAllStudents(), this, new StudentAdapter.onClickItemListener() {
            @Override
            public void onClickDelete(String nim) {
                showConfirmAlert(nim);
            }

            @Override
            public void onClickEdit(String nim) {
                startActivityForResult(new Intent(ListStudentsActivity.this, UpdateActivity.class).putExtra("nim", nim), 0);
            }
        });
        rvListStudents.setLayoutManager(new LinearLayoutManager(this));
        rvListStudents.setAdapter(adapter);
    }

    private void deleteStudent(String studentId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsAffected = db.delete("students", "nim=?", new String[]{String.valueOf(studentId)});
        Log.d("DB", "deleteStudent: " + rowsAffected);
        // TODO: Handle the result, display a toast or something
        GeneralHelper.showMessage(this, "Hapus Berhasil");

        adapter.notifyDataSetChanged();
        db.close();
    }

    private void showConfirmAlert(String nim) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi Hapus");
        builder.setMessage("Apakah yakin akan dihapus?");

        builder.setPositiveButton("YA", (dialog, which) -> {
            deleteStudent(nim);
            adapter.setStudentList(dbHelper.getAllStudents());
        });

        builder.setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            adapter.setStudentList(dbHelper.getAllStudents());
        }
    }
}