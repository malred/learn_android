package com.malugy.model;

import java.util.ArrayList;
import java.util.List;

public class LessonResult {
    private String msg;
    private int status;
    private List<LessonInfo> lessonInfoList = new ArrayList<>();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<LessonInfo> getLessonInfoList() {
        return lessonInfoList;
    }

    public void setLessonInfoList(List<LessonInfo> lessonInfoList) {
        this.lessonInfoList = lessonInfoList;
    }
}
