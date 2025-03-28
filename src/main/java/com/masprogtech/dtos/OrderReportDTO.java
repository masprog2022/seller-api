package com.masprogtech.dtos;

public class OrderReportDTO {

    private String month;
    private Long pending;
    private Long completed;

    public OrderReportDTO(String month, Long pending, Long completed) {
        this.month = month;
        this.pending = pending;
        this.completed = completed;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getPending() {
        return pending;
    }

    public void setPending(Long pending) {
        this.pending = pending;
    }

    public Long getCompleted() {
        return completed;
    }

    public void setCompleted(Long completed) {
        this.completed = completed;
    }
}
