package com.example.chatproject;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper { //테이블 이름을 저장하는 변수 선언

    public static final String tableName = "Users";

    public dbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name , factory, version);
    }

    @Override
    public  void onCreate(SQLiteDatabase db){ //데이터베이스 실행
        createTable(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

    public void createTable(SQLiteDatabase db) { //회원가입에 필요한 아이디, 비밀번호, 이름, 성에 대한 테이블 생성
        String sql = "CREATE TABLE " + tableName + "(id text, pw text, name text, sex text)";
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
        }
    }
    public void insertUser(SQLiteDatabase db, String id, String pw, String name, String sex){ //회원가입창에서 입력한 정보를 테이블에 추가한다.
        db.beginTransaction();
        try{
            String sql = " INSERT INTO " + tableName + "(id,pw,name,sex)" +"VALUES('" + id +"', '" + pw + "','" + name + "', '" + sex +"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }
}

