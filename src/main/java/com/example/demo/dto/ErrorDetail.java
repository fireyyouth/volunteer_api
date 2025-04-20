package com.example.demo.dto;

public class ErrorDetail {
    private String detail;
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ErrorDetail(String detail) {
        this.detail = detail;
    }
}
