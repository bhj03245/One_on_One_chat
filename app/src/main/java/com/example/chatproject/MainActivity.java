package com.example.chatproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView Chat = null;
    ImageView Calendar = null;
    Intent calendarIntent;
    Intent userintent;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userintent = getIntent();
        userId = userintent.getExtras().getString("Email");


        Chat = (ImageView)findViewById(R.id.chat);
        Calendar = (ImageView)findViewById(R.id.chllendar);

        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Chat 이미지뷰를 누를 경우 intent를 사용하여 ChatActivity로 전환한다.
                Intent userintent = new Intent(getApplicationContext(), ChatActivity.class);
                userintent.putExtra("Email",userId); //userId값 보내기
                startActivity(userintent);
            }
        });

        Calendar.setOnClickListener(new View.OnClickListener() { //Calendar 이미지뷰를 누를 경우 intent를 사용하여 ScheduleActivity로 전환한다.
            @Override
            public void onClick(View v) {
                calendarIntent = new Intent(getApplicationContext(), ScheduleActivity.class);
                startActivity(calendarIntent);
            }
        });
    }
}
