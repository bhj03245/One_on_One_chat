package com.example.chatproject;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    dbHelper helper;
    SQLiteDatabase db;
    int version = 2;
    String sql;
    String sql2;
    Cursor cursor;
    Cursor cursor2;
    private EditText id;
    private EditText password;
    private Button join;
    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        helper = new dbHelper(LoginActivity.this,dbHelper.tableName,null,version); //dbHelper 생성

        try{
            db = helper.getWritableDatabase(); //데이터베이스 쓰기 실행
        }catch(SQLiteException ex){
            db = helper.getReadableDatabase(); //데이터베이스 읽기 실행
        }


        id = (EditText)findViewById(R.id.id);
        password = (EditText)findViewById(R.id.password);

        join = (Button)findViewById(R.id.join);
        login = (Button)findViewById(R.id.login);

        join.setOnClickListener(new View.OnClickListener() { //회원 가입 신청을 누를 경우 회원가입 액티비티가 시작한다.
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, 1000);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sEmail = id.getText().toString();
                String sPw = password.getText().toString();
                String sName = null;

                sql = "SELECT id FROM " + dbHelper.tableName + " WHERE id = '" + sEmail + "'";
                cursor = db.rawQuery(sql, null);
                // 테이블에서 id와 sEmail값이 같을 경우 id조회가 되지 않았을 경우에 존재하지 않는 아이디입니다를 출력
                if(cursor.getCount()!=1){
                    Toast toast = Toast.makeText(LoginActivity.this, "존재하지 않는 아이디입니다.",Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }


                sql = "SELECT pw FROM " + dbHelper.tableName + " WHERE id = '" + sEmail + "'";
                cursor = db.rawQuery(sql, null);
                cursor.moveToNext();
                //테이블에서 id와 sEmail값이 같을 경우의 pw를 조회가 되지 않는다면 비밀번호가 틀리셨습니다 출력
                if(!sPw.equals(cursor.getString(0))) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 틀리셨습니다.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);    // 비밀번호가 맞을경우 인텐트로 Email정보를 메인액티비티로 전송
                    intent.putExtra("Email",sEmail);
                    startActivity(intent);
                    finish();
                }
                cursor.close();

                if(cursor2!=null && cursor2.getCount()!=0) {
                    sql2 = "SELECT name FROM " + dbHelper.tableName + " WHERE id = '" + sEmail + "'";
                    cursor2=db.rawQuery(sql2, null);
                    sName = cursor2.getString(1);
                    Intent nameintent = new Intent(getApplicationContext(), ChatActivity.class);    // 이름이 Email이랑 맞을경우 이름정보를 챗액티비티로 전송
                    nameintent.putExtra("name", sName);
                    startActivity(nameintent);
                    finish();
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){  // 회원가입한 email 정보를 받아옴
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000 && resultCode==RESULT_OK){
            id.setText(data.getStringExtra("email"));
        }
    }

}