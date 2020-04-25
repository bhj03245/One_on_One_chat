package com.example.chatproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ScheduleAdapter extends BaseAdapter {
    private ArrayList<S_item> s_list; //ArrayList 클래스의 객체 변수 선언


    public ScheduleAdapter(){ //s_list 객체 생성하는 기본 생성자
        s_list = new ArrayList();
    }


    public void add(String s_time, String s_content)
    {
        s_list.add(new S_item(s_time, s_content)); //리스트 뷰의 항목에 날짜와 내용 추가
        notifyDataSetChanged(); //실시간으로 새로고침하는 함수
    }

    public void remove(int position){
        s_list.remove(position); //리스트 뷰의 항목을 삭제
        notifyDataSetChanged(); //실시간으로 새로고침하는 함수
    }
    public int getCount(){ //ArrayList 크기 반환 메소드
        return s_list.size();
    }
    public Object getItem(int position){ //ArrayList 항목 반환 메소드
        return s_list.get(position);
    }
    public long getItemId(int position){ //ArrayList 항목의 위치 반환 메소드
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent ){
        final Context context = parent.getContext();
        TextView textLeft=null; //날짜를 보여주는 TextView
        TextView textRight=null; //내용을 보여주는 TextView

        if(convertView == null){ //항목을 확장시켜주기 위한 처리문
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_item,parent,false);
        }

        textLeft = (TextView) convertView.findViewById(R.id.time); //xml상의 TextView와 textLeft 연동
        textRight = (TextView) convertView.findViewById(R.id.contents); //xml상의 TextView와 textRight 연동

        S_item item = s_list.get(position); //항목의 위치를 가져오는 객체 생성
        textLeft.setText(item.getDate()); //S_item클래스의 getDate()메소드를 통해 textLeft의 값 설정
        textRight.setText(item.getContent()); //S_item클래스의 getDate()메소드를 통해 textRight의 값 설정

        return convertView;
    }
}
