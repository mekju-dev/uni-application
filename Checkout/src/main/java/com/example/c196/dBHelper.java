package com.example.c196;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatatypeMismatchException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.c196.ui.assessments.Assessment;
import com.example.c196.ui.courses.Course;
import com.example.c196.ui.courses.addCoursesActivity;
import com.example.c196.ui.terms.Term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "school.db";
    public static final String TABLE_NAME = "Assessments_Table";
    public static final String TABLE_NAME2 = "Courses_Table";
    public static final String TABLE_NAME3 = "Terms_Table";

    //Assessment Columns
    public static final String AssessmentTitle = "AssessmentTitle";
    public static final String AssessmentType = "AssessmentType";
    public static final String AssessmentStartDate = "AssessmentStartDate";

    public static final String AssessmentEndDate = "AssessmentEndDate";
    public static final String AssessmentCourse = "AssessmentCourse";

    //Course Columns
    public static final String CourseTitle = "CourseTitle";
    public static final String CourseStatus = "CourseStatus";
    public static final String CourseInstructor = "CourseInstructor";
    public static final String CourseInstructorPhone = "CourseInstructorPhone";
    public static final String CourseInstructorEmail = "CourseInstructorEmail";
    public static final String TermFromCourses = "Term";
    public static final String Start_Date_Course = "Start_Date";
    public static final String End_Date_Course = "End_Date";
    public static final String Note = "Note";

    //TermsColumns
    public static final String ID = "ID";
    public static final String Term = "Term";
    public static final String Start_Date_Term = "Start_Date";
    public static final String End_Date_Term = "End_Date";


    public dBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,AssessmentTitle TEXT,AssessmentType TEXT,AssessmentCourse TEXT,AssessmentStartDate DATE,AssessmentEndDate DATE, FOREIGN KEY(AssessmentCourse) REFERENCES Courses_Table(CourseTitle))");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,CourseTitle TEXT UNIQUE,CourseStatus TEXT,CourseInstructor TEXT,CourseInstructorPhone TEXT,CourseInstructorEmail TEXT,Term TEXT,Start_Date DATE,End_Date DATE, Note TEXT, FOREIGN KEY(Term) REFERENCES Terms_Table(TERM))");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME3 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TERM TEXT UNIQUE,START_DATE DATE,END_DATE DATE)");

//FOREIGN KEY (customer_id)
//    REFERENCES Customers(id)
    }

    /**
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean limitAssessmentReached(String course) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE AssessmentCourse = ?", new String[]{course});

        if (result.getCount() > 4) {
            return false;
        } else {
            return true;
        }
    }

    public String insertAssessment(String title, String type, String course, String startDate, String endDate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE CourseTitle = ?", new String[]{course});

        if (result.getCount() > 5) {
            return "This course has 5 assessments. Delete one before proceeding.";
        }

        if (result.moveToNext()) {
            try {
                contentValues.put(AssessmentTitle, title);
                contentValues.put(AssessmentType, type);
                contentValues.put(AssessmentCourse, course);
                contentValues.put(AssessmentStartDate, startDate);
                contentValues.put(AssessmentEndDate, endDate);
                sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
            } catch (SQLException mSQLException) {
                if (mSQLException instanceof SQLiteConstraintException) {
                    return "SQLite Constraint Exception. Course not unique";
                } else if (mSQLException instanceof SQLiteDatatypeMismatchException) {
                    return "SQLite Data Type Mismatch Exception";
                } else {
                    return "SQLite ERROR";
                }
            }
            return "Success";
        } else {
            return "ERROR, no such course name exists!";
        }
    }


    public String updateAssessment(String id, String title, String type, String course, String startDate, String endDate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE CourseTitle = ?", new String[]{course});

        if (result.moveToNext()) {

            try {
                contentValues.put(AssessmentTitle, title);
                contentValues.put(AssessmentType, type);
                contentValues.put(AssessmentCourse, course);
                contentValues.put(AssessmentStartDate, startDate);
                contentValues.put(AssessmentEndDate, endDate);

                sqLiteDatabase.update(TABLE_NAME, contentValues, "id=?", new String[]{id});
            } catch (SQLException mSQLException) {
                if (mSQLException instanceof SQLiteConstraintException) {
                    return "SQLite Constraint Exception. Course not unique";
                } else if (mSQLException instanceof SQLiteDatatypeMismatchException) {
                    return "SQLite Data Type Mismatch Exception";
                } else {
                    return "SQLite ERROR";
                }
            }
            return "Success";
        } else {
            return "ERROR, no such course name exists";
        }

    }

    public boolean deleteAssessment(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_NAME, "id=?", new String[]{id});


        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String updateCourse(String id, String name, String status, String instructor, String phone, String email, String term, String start, String end, String note) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME3 + " WHERE TERM = ?", new String[]{term});
        if (result.moveToNext()) {
            try {
                contentValues.put(CourseTitle, name);
                contentValues.put(CourseStatus, status);
                contentValues.put(CourseInstructor, instructor);
                contentValues.put(CourseInstructorPhone, phone);
                contentValues.put(CourseInstructorEmail, email);
                contentValues.put(TermFromCourses, term);
                contentValues.put(Start_Date_Course, start);
                contentValues.put(End_Date_Course, end);
                contentValues.put(Note, note);

                sqLiteDatabase.update(TABLE_NAME2, contentValues, "id=?", new String[]{id});
            } catch (SQLException mSQLException) {
                if (mSQLException instanceof SQLiteConstraintException) {
                    return "SQLite Constraint Exception. Course not unique";
                } else if (mSQLException instanceof SQLiteDatatypeMismatchException) {
                    return "SQLite Data Type Mismatch Exception";
                } else {
                    return "SQLite ERROR";
                }
            }
            return "Success";
        } else {
            return "Fail. Course must be assigned to existing Term";
        }
    }

    public boolean deleteCourse(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_NAME2, "id=?", new String[]{id});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteAssociatedAsssessments(String course) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_NAME, "AssessmentCourse=?", new String[]{course});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //IT WORKS BUT ON THE WRONG VALUE/ WRONG LOGICAL IMPLEMENTATION
    public String insertCourse(String courseTitle, String courseStatus, String courseInstructor, String courseInstructorPhone, String courseInstructorEmail, String courseName, String course_Start_Date, String course_End_Date, String note) throws SQLiteDatatypeMismatchException, SQLiteConstraintException {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME3 + " WHERE TERM = ?", new String[]{courseName});
        if (result.moveToNext()) {
            try {
                contentValues.put(CourseTitle, courseTitle);
                contentValues.put(CourseStatus, courseStatus);
                contentValues.put(CourseInstructor, courseInstructor);
                contentValues.put(CourseInstructorPhone, courseInstructorPhone);
                contentValues.put(CourseInstructorEmail, courseInstructorEmail);
                contentValues.put(TermFromCourses, courseName);
                contentValues.put(Start_Date_Course, course_Start_Date);
                contentValues.put(End_Date_Course, course_End_Date);
                contentValues.put(Note, note);
                //sqLiteDatabase.execSQL("SELECT * FROM " + TABLE_NAME3 + " WHERE TERM = " + courseName);
                sqLiteDatabase.insertOrThrow(TABLE_NAME2, null, contentValues);
            } catch (SQLException mSQLException) {
                if (mSQLException instanceof SQLiteConstraintException) {
                    return "SQLite Constraint Exception. Course not unique";
                } else if (mSQLException instanceof SQLiteDatatypeMismatchException) {
                    return "SQLite Data Type Mismatch Exception";
                } else {
                    return "SQLite ERROR";
                }
            }
            return "Success";
        } else {
            return "Fail. Course must be assigned to existing Term";
        }
    }

    public String updateTerm(String id, String term, String start, String end) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(Term, term);
            contentValues.put(Start_Date_Term, start);
            contentValues.put(End_Date_Term, end);

            sqLiteDatabase.update(TABLE_NAME3, contentValues, "id=?", new String[]{id});
        } catch (SQLException mSQLException) {
            if (mSQLException instanceof SQLiteConstraintException) {
                return "SQLite Constraint Exception. Term name must be unique.";
            } else if (mSQLException instanceof SQLiteDatatypeMismatchException) {
                return "SQLite Data Type Mismatch Exception";
            } else {
                return "SQLite ERROR";
            }
        }
        return "Success";
    }

    public String deleteTerm(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("SELECT Courses_Table.Term FROM Courses_Table, Terms_Table WHERE Courses_Table.Term = Terms_Table.TERM", null);
        if (!result.moveToNext()) {
            try {
                sqLiteDatabase.delete(TABLE_NAME3, "id=?", new String[]{id});
            } catch (SQLException mSQLException) {
                if (mSQLException instanceof SQLiteConstraintException) {
                    return "SQLite Constraint Exception. Term name must be unique.";
                } else if (mSQLException instanceof SQLiteDatatypeMismatchException) {
                    return "SQLite Data Type Mismatch Exception";
                } else {
                    return "SQLite ERROR";
                }
            }
            return "Success";
        } else {
            return "Failed to delete. Term has courses";
        }
    }


    public String insertTerm(String termName, String term_Start_Date, String term_End_Date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(Term, termName);
            contentValues.put(Start_Date_Term, term_Start_Date);
            contentValues.put(End_Date_Term, term_End_Date);

            sqLiteDatabase.insertOrThrow(TABLE_NAME3, null, contentValues);
        } catch (SQLException mSQLException) {
            if (mSQLException instanceof SQLiteConstraintException) {
                return "SQLite Constraint Exception. Term name must be unique.";
            } else if (mSQLException instanceof SQLiteDatatypeMismatchException) {
                return "SQLite Data Type Mismatch Exception";
            } else {
                return "SQLite ERROR";
            }
        }
        return "Success";
    }

    public Cursor getAllAssessments() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //ContentValues contentValues = new ContentValues();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }

    public Cursor getAllCourses() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //ContentValues contentValues = new ContentValues();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME2, null);
        return result;
    }

    public Cursor getAllTerms() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //ContentValues contentValues = new ContentValues();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME3, null);
        return result;
    }

    public ArrayList<String> getAllTermNames() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //ContentValues contentValues = new ContentValues();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME3, null);
        ArrayList<String> list = new ArrayList<>();
        while (result.moveToNext()) {
            list.add(result.getString(1));
        }
        return list;
    }

    public ArrayList<String> getAllCourseNames() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //ContentValues contentValues = new ContentValues();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME2, null);
        ArrayList<String> list = new ArrayList<>();
        while (result.moveToNext()) {
            list.add(result.getString(1));
        }
        return list;
    }

    public ArrayList<Course> getAllCoursesFromTerm(String term) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM Courses_Table WHERE TERM = ?", new String[]{term});
        ArrayList<Course> list = new ArrayList<>();
        while (result.moveToNext()) {
            Course course = new Course(result.getString(0), result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getString(8), result.getString(9));
            list.add(course);
        }
        return list;
    }

    public ArrayList<Assessment> getAllAssessmentsFromCourse(String course) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM Assessments_Table WHERE AssessmentCourse = ?", new String[]{course});
        ArrayList<Assessment> list = new ArrayList<>();
        while (result.moveToNext()) {
            Assessment temp = new Assessment(result.getString(0), result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
            list.add(temp);
        }
        return list;
    }

    public void clearData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME2 + ";");
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME3 + ";");
    }
}