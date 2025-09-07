package com.raylabs.mathverse.data.local.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class StudentTest {

    @Test
    public void testStudentFields() {
        Student s = new Student();
        s.nim = "12345";
        s.name = "Alice";
        s.program = "Math";
        s.nohp = "08123456789";
        s.email = "alice@example.com";

        assertEquals("12345", s.nim);
        assertEquals("Alice", s.name);
        assertEquals("Math", s.program);
        assertEquals("08123456789", s.nohp);
        assertEquals("alice@example.com", s.email);
    }

    @Test
    public void testStudentEqualityManual() {
        Student s1 = new Student();
        s1.nim = "123";
        s1.name = "Bob";

        Student s2 = new Student();
        s2.nim = "123";
        s2.name = "Bob";

        // Karena belum override equals(), perbandingan referensi berbeda
        assertNotEquals(s1, s2);
    }
}