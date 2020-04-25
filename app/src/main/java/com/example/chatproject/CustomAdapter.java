package com.example.chatproject;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<CustomAdapter.ListContents> m_List = new ArrayList(); // ArrayList를 CustomAdapter 형으로 생성

    public class ListContents{  // ListContents 클래스 정의
        String userName;    // user 이름 선언
        String userId;      // user 아이디 선언
        String msg;         // msg 메세지 선언
        int type;           // 메세지 받는쪽과 보내는쪽 구분을 위한 int형 변수 선언
        String time;        // time 시간 선언
        ListContents(String _userName, String _userId, String _msg,int _type,String _time) // 값을 가져오는 생성자
        {
            this.userName = _userName;
            this.userId = _userId;
            this.msg = _msg;
            this.type = _type;
            this.time = _time;
        }
    }

    public CustomAdapter() {
        m_List = new ArrayList();
    }
    public void add(String _userName,String _userId ,String _msg,int _type,String _time) {  // 리스트뷰에 항목을 추가할 메소드
        m_List.add(new ListContents(_userName, _userId, _msg,_type,_time));
    }

    public int getCount() {
        return m_List.size();
    }   // 리스트뷰의 크기를 반환할 메소드
    public Object getItem(int position) {
        return m_List.get(position);
    }   // 리스트뷰의 항목 위치를 반환하는 메소드
    public long getItemId(int position) {
        return position;
    }   // 리스트뷰의 아이템위치 메소드
    public View getView(int position, View convertView, ViewGroup parent) {     // 뷰를 받아오는 메소드
        final int pos = position;
        final Context context = parent.getContext();

        TextView text = null;
        CustomHolder holder = null; // custom holder 객체 생성
        LinearLayout layout = null;
        View viewRight = null;  // 오른쪽 메세지 뷰 생성
        View viewLeft = null;   // 왼쪽 메세지 뷰 생성
        TextView stime = null;  // 보내는 시간 선언
        TextView gtime = null;  // 받는 시간 선언
        ImageView limage = null;    // 상대방 사진 ImageView 선언
        ImageView rimage = null;    // 자신의 사진 ImageView 선언
        TextView sName = null;      // 보내는 사람 이름 선언
        TextView gName = null;      // 받는 사람 이름 선언

        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_chatitem, parent, false);
            layout = (LinearLayout) convertView.findViewById(R.id.layout);
            text = (TextView) convertView.findViewById(R.id.text);
            viewRight = (View) convertView.findViewById(R.id.imageViewright);
            viewLeft = (View) convertView.findViewById(R.id.imageViewleft);
            stime = (TextView)convertView.findViewById(R.id.sendTime);
            gtime = (TextView)convertView.findViewById(R.id.getTime);
            limage = (ImageView)convertView.findViewById(R.id.leftImage);
            rimage = (ImageView)convertView.findViewById(R.id.rightImage);
            sName = (TextView)convertView.findViewById(R.id.sName);
            gName = (TextView)convertView.findViewById(R.id.gName);

            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.m_TextView = text;
            holder.layout = layout;
            holder.viewRight = viewRight;
            holder.viewLeft = viewLeft;
            holder.stime = stime;
            holder.gtime = gtime;
            holder.limage = limage;
            holder.rimage = rimage;
            holder.sName = sName;
            holder.gName = gName;
            convertView.setTag(holder);
        }
        else {
            holder = (CustomHolder) convertView.getTag();
            text = holder.m_TextView;
            layout = holder.layout;
            viewRight = holder.viewRight;
            viewLeft = holder.viewLeft;
            stime = holder.stime;
            gtime = holder.gtime;
            limage = holder.limage;
            rimage = holder.rimage;
            sName = holder.sName;
            gName = holder.gName;
        }

        // Text 등록
        text.setText(m_List.get(position).msg);
        stime.setText(m_List.get(position).time);
        gtime.setText(m_List.get(position).time);
        sName.setText(m_List.get(position).userId);
        gName.setText(m_List.get(position).userId);

        if( m_List.get(position).type == 0 ) {     // 메세지를 받았을경우 뷰 설정
            text.setBackgroundResource(R.drawable.inbox2);
            layout.setGravity(Gravity.LEFT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
            stime.setVisibility(View.GONE);
            gtime.setVisibility(View.VISIBLE);
            limage.setVisibility(View.VISIBLE);
            rimage.setVisibility(View.GONE);
            sName.setVisibility(View.VISIBLE);
            gName.setVisibility(View.GONE);
        }else if(m_List.get(position).type == 1) {   // 메세지를 보냈을경우 뷰 설정
            text.setBackgroundResource(R.drawable.outbox2);
            layout.setGravity(Gravity.RIGHT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
            stime.setVisibility(View.VISIBLE);
            gtime.setVisibility(View.GONE);
            limage.setVisibility(View.GONE);
            rimage.setVisibility(View.VISIBLE);
            sName.setVisibility(View.GONE);
            gName.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private class CustomHolder {    // 커스텀홀더 클래스 생성
        TextView m_TextView;
        LinearLayout layout;
        View viewRight;
        View viewLeft;
        TextView stime;
        TextView gtime;
        ImageView limage;
        ImageView rimage;
        TextView gName;
        TextView sName;
    }
}
