package com.example.chatproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ScheduleActivity extends AppCompatActivity {

    CalendarView cal; //캘린더뷰 변수 선언
    ListView lv; // 리스트뷰 변수 선언
    ScheduleAdapter s_adapter; //ScheduleAdapter 클래스 변수 선언
    String s_year, s_month, s_day, s_ymd, s_content; //년, 월, 일, 일정의 내용 받는 변수 선언
    String date, content; //S_item의 클래스에서 날짜와 내용을 받는 변수 선언
    ArrayList<S_item> items; //S_item의 ArrayList 변수 선언
    S_item item; //S_item 클래스 변수 선언

    long nowDate; //long형의 nowDate 변수 선언
    Date nDate; //Date클래스의 nDate 변수선언
    String s_nDate; //string형의 s_nDate 변수선언
    SimpleDateFormat fDate = new SimpleDateFormat("yyyy년 MM월 dd일"); //날짜 형식의 설정

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        items = new ArrayList<S_item>(); //items ArrayList 객체 생성
        item = new S_item(date, content); //item S_item 객체 생성
        cal = (CalendarView) findViewById(R.id.Calendar); //xml상의 Calendar와 cal 연동
        lv = (ListView) findViewById(R.id.listview); //xml상의 listView와 lv 연동
        s_adapter = new ScheduleAdapter(); //s_adpater ScheduleAdapter 객체 생성
        lv.setAdapter(s_adapter); //리스트뷰와 어뎁터 연동

        nowDate = System.currentTimeMillis(); //시스템상에서 날짜 받아오기
        nDate = new Date(nowDate); //받아온 날짜 Date클래스에 적용
        s_nDate = fDate.format(nDate); //원하는 형식으로 s_nDate에 값 저장



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "항목을 길게 누르면 삭제 할 수 있습니다!",
                        Toast.LENGTH_LONG).show();
            }//리스트뷰의 항목을 터치하면 토스트메시지 출력
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                s_adapter.remove(position); //리스트뷰의 항목 삭제
                ref.child("schedule").removeValue(); //Database상에서의 값 삭제

                return true;
            } //리스트뷰의 항목을 길게 누르면 항목 리스트 항목 삭제
        });


        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                s_year = Integer.toString(year);
                if(month < 10){
                    s_month = "0"+Integer.toString(month+1);
                }
                else
                    s_month = Integer.toString(month+1);
                if (dayOfMonth < 10) {

                    s_day = "0"+Integer.toString(dayOfMonth);

                }else{
                    s_day = Integer.toString(dayOfMonth);
                }
                s_ymd = s_year + "년" + " "+ s_month + "월" + " " +
                        s_day + "일";
            }//형식을 맞추기 위한 if/else문과 선택된 날짜의 값을 s_ymd변수에 저장
        });


        ref.child("schedule").addChildEventListener(new ChildEventListener() { // schedule 차일드 항목으로 firebase에 저장
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { // child 항목으로부터 값을 받아옴
                S_item item = dataSnapshot.getValue(S_item.class); // dataSnapshot이란 전달매체를 통해 item의 값을 받아옴
                date = item.getDate(); //S_item 클래스의 메소드를 통해 날짜 값 가져오기
                content = item.getContent(); //S_item 클래스의 메소드를 통해 내용 값 가져오기
                s_adapter.add(date, content); //ScheduleAdapter 클래스의 add메소드를 이용하여 리스트 뷰에 항목 추가
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //옵션메뉴의 메뉴확장을 위한 메소드
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //옵션메뉴를 선택시 이벤트 처리를 위한 메소드

        final EditText editAdd = new EditText(this); //Dialog에 넣을 EditText 생성
        final S_item s_item = new S_item(s_ymd, s_content); //S_item클래스 객체 생성
        switch (item.getItemId()) {
            case R.id.menu_plus: //plus 버튼이 눌러졌을 경우

                AlertDialog.Builder adBuilder = new AlertDialog.Builder(this); //Dialog 생성
                adBuilder.setMessage("일정 내용을 입력하세요!"); //메시지 입력
                adBuilder.setView(editAdd); //EditText 보이게 설정
                adBuilder.setPositiveButton("추가", //추가 버튼 설정
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(s_ymd==null) //캘린더에서 날짜가 선택되지 않았을 경우
                                    s_ymd = s_nDate;
                                s_content = editAdd.getText().toString(); //EditText에 입력한 내용을 s_content에 저장
                                s_item.setDate(s_ymd); //s_ymd 값을 S_item 클래스의 setDate메소드를 통해 날짜값 설정
                                s_item.setContent(s_content); //s_content 값을 S_item 클래스의 setContent 메소드를 통해 내용 설정
                                ref.child("schedule").push().setValue(s_item); //Database상에서의 데이터값 저장

                            }
                        });
                adBuilder.setNegativeButton("취소", //취소 버튼 설정
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); //Dialog 닫기
                            }
                        });
                adBuilder.show(); //Dialog 보이게 설정
                return true;
            case R.id.anniversary: //하트 버튼이 눌러졌을 경우

                if(s_ymd==null) //캘린더에서 날짜가 선택되지 않았을 경우
                    Toast.makeText(getApplicationContext(),
                            "날짜를 선택해주세요!", Toast.LENGTH_LONG).show();
                else{
                    s_content = "♡1일♡";
                    s_item.setDate(s_ymd);  //s_ymd 값을 S_item 클래스의 setDate메소드를 통해 날짜값 설정
                    s_item.setContent(s_content); //s_content 값을 S_item 클래스의 setContent 메소드를 통해 내용 설정
                    ref.child("schedule").push().setValue(s_item); //Database상에서의 데이터값 저장
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



