package com.example.chatproject;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ChatActivity extends AppCompatActivity {

    ListView m_ListView;    // 메세지를 위한 리스트뷰 생성
    CustomAdapter m_Adapter;    // 커스텀 어댑터 생성
    EditText edit;  // 메세지 입력을 위한 에디트텍스트 생성
    Button btn;     // 메세지 전송위한 버튼 생성
    String sendMsg; // 작성한 메세지를 보내기 위한 String 변수 선언
    Intent userintent;  // user의 id를 받아오기 위한 인텐트 선언
    Intent usrName;     // user의 이름을 받아오기 위한 인텐트 선언

    private String userId=null;     // user의 id를 받아오기 위한 String 변수 선언
    private String userName =null;  // user의 이름을 받아오기 위한 String 변수 선언

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); // firebaseDatabase의 데이터를 읽고 쓰기 위해 인스턴스를 받아올 객채 생성
    private DatabaseReference databaseReference = firebaseDatabase.getReference();  // Database의 데이터를 읽고 쓰기 위해 인스턴스를 받아올 객체 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        btn=(Button)findViewById(R.id.button1);
        edit=(EditText)findViewById(R.id.editText1);
        m_Adapter = new CustomAdapter();
        m_ListView = (ListView) findViewById(R.id.listView1);
        m_ListView.setAdapter(m_Adapter);


        userintent = getIntent();   // LoginActivity로 부터 입력한 id를 받아오는 인텐트
        userId = userintent.getExtras().getString("Email"); // 받아온 값을 userId에 저장

        usrName = getIntent();      //LoginActivity로 부터 name을 받아오는 인텐트
        userName = usrName.getExtras().getString("name");   // 받아온 값을 userName에 저장

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {    // send 버튼 눌렀을 시
                String time = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date()); // 시간을 HH:mm 형식으로 저장할 String 변수 생성
                ChatData chatData = new ChatData(userName, userId ,edit.getText().toString(), time);    // ChatData 객체 생성
                chatData.setTime(time); // 메세지 보낼때의 시간 설정
                chatData.setUserId(userId); // 메세지 보낼때의 userId를 설정
                chatData.setUserName(userName); // 메세지 보낼때의 userName을 설정
                sendMsg=edit.getText().toString();  // 메세지 받아오기
                databaseReference.child("message").push().setValue(chatData); // firebase에서 message 차일드 생성
                edit.setText("");   // 메세지 보낸 후 텍스트창 비움
                m_Adapter.notifyDataSetChanged();   // 메세지 전송 후 바로 refresh
            }
        });

        databaseReference.child("message").addChildEventListener(new ChildEventListener() { // message 차일드 항목으로 firebase에 저장
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  // child 항목으로부터 값을 받아옴
                ChatData chatData = dataSnapshot.getValue(ChatData.class);  // dataSnapshot이란 전달매체를 통해 chatData의 값을 받아옴
                String str = chatData.getMessage(); // fireBase로부터 받아온 메세지 저장
                String time = chatData.getTime();   // fireBase로부터 받아온 시간 저장
                String getName = chatData.getUserName();    // fireBase로부터 받아온 user 이름 저장
                String getId = chatData.getUserId();    // fireBase로부터 받아온 user ID 저장

                if(userId.equals(getId)) {  // 메세지 보냄
                    m_Adapter.add(getName, getId, str, 1, time);    // 커스텀리스트뷰에 메세지를 보냈을때 메세지박스와 내용이 추가
                    m_Adapter.notifyDataSetChanged();   // 실시간으로 리스트뷰의 데이터를 업데이트
                }
                else {
                    m_Adapter.add(getName, getId, str, 0, time);    // 커스텀리스트뷰에 메세지를 받았을때 메세지박스와 내용이 추가
                    m_Adapter.notifyDataSetChanged();   // 실시간으로 리스트뷰의 데이터를 업데이트
                }
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
}