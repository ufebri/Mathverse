package com.raylabs.mathverse.data.local.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.raylabs.mathverse.data.local.entity.Student;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase";
    private static final int DATABASE_VERSION = 1;

    // Table creation SQL statement
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE students (" +
            "nim TEXT PRIMARY KEY," +
            "name TEXT," +
            "program TEXT," +
            "nohp TEXT," +
            "email TEXT)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades, if needed
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Define a projection that specifies which columns from the database you will actually use
            String[] projection = {"nim", "name", "program", "nohp", "email"};

            // Perform a query on the students table
            cursor = db.query("students", projection, null, null, null, null, null);

            // Iterate over the cursor and create Student objects
            while (cursor.moveToNext()) {
                String nim = cursor.getString(cursor.getColumnIndexOrThrow("nim"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String program = cursor.getString(cursor.getColumnIndexOrThrow("program"));
                String noHp = cursor.getString(cursor.getColumnIndexOrThrow("nohp"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

                Student student = new Student();
                student.nim = nim;
                student.name = name;
                student.program = program;
                student.nohp = noHp;
                student.email = email;

                students.add(student);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return students;
    }


    public Student getStudentById(String studentId) {
        Student student = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Define a projection that specifies which columns from the database you will actually use
            String[] projection = {"nim", "name", "program", "nohp", "email"};

            // Perform a query on the students table for a specific ID
            cursor = db.query("students", projection, "nim=?", new String[]{String.valueOf(studentId)}, null, null, null);

            // Check if the cursor contains data
            if (cursor.moveToFirst()) {
                String nim = cursor.getString(cursor.getColumnIndexOrThrow("nim"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String program = cursor.getString(cursor.getColumnIndexOrThrow("program"));
                String noHp = cursor.getString(cursor.getColumnIndexOrThrow("nohp"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

                student = new Student();
                student.nim = nim;
                student.name = name;
                student.program = program;
                student.nohp = noHp;
                student.email = email;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return student;
    }
}
