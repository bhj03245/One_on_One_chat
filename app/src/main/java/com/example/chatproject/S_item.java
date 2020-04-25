package com.example.chatproject;


public class S_item { //ArrayList에 들어갈 항목에 대한 클래스
    private String date; //날짜에 대한 string 변수
    private String content; //일정에 대한 string 변수

    public S_item(){} // S_item클래스의 기본 생성자

    public S_item(String s_date, String s_content) //date와 content에 대한 S_item클래스의 생성자
    {
        this.date = s_date;
        this.content = s_content;
    }

    public String getDate() { //날짜 값을 가져 오는 메소드
        return date;
    }
    public String getContent() { //일정 값을 가져 오는 메소드
        return content;
    }
    public void setDate(String s_date){ //날짜 값을 설정하는 메소드
        this.date = s_date;
    }
    public void setContent(String s_content) { //내용 값을 설정하는 메소드
        this.content = s_content;
    }
}