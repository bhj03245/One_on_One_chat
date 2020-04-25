package com.example.chatproject;

public class ChatData {     // user의 id, name, message, time을 받아오기 위한 ChatData 클래스 생성
    private String userId;
    private String userName;
    private String message;
    private String time;

    public ChatData() { }

    public ChatData(String userName, String userId, String message, String time) {
        this.userName = userName;
        this.userId = userId;
        this.message = message;
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }   // 메세지 받은 userName을 저장

    public void setUserName(String userName) {
        this.userName = userName;
    }  // 메세지 보낸 userName을 저장

    public String getUserId(){
        return userId;
    }   // 메세지 받은 userId를 저장

    public void setUserId(String userId){
        this.userId = userId;
    }   // 메세지 보낸 userId를 저장

    public String getMessage() {
        return message;
    }   // 받은 message를 저장

    public void setMessage(String message) {
        this.message = message;
    }   // 보낸 message를 저장

    public String getTime(){
        return time;
    }   // 메세지 받은 time을 저장

    public String setTime(String time){ // 메세지 보낸 time을 저장
        this.time = time;
        return time;
    }
}