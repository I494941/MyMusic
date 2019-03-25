package com.wjf.mymusic.ui.myDemo.eventbusActivity.bean;

/**
 * Created by wjf on 2019/3/25.
 */
public class MessageEvent {

    private String message;
    public  MessageEvent(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
