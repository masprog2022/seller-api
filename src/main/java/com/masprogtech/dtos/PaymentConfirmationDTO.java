package com.masprogtech.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentConfirmationDTO {

    @JsonProperty("id")
    private String id;
    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("status")
    private String status;

    @JsonProperty("status_reason")
    private String statusReason;

    @JsonProperty("status_datetime")
    private LocalDateTime statusDatetime;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("processor")
    private String processor;

    @JsonProperty("customer")
    private String customer;

    public PaymentConfirmationDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public LocalDateTime getStatusDatetime() {
        return statusDatetime;
    }

    public void setStatusDatetime(LocalDateTime statusDatetime) {
        this.statusDatetime = statusDatetime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "PaymentConfirmationDTO{" +
                "customer='" + customer + '\'' +
                ", id='" + id + '\'' +
                ", amount='" + amount + '\'' +
                ", status='" + status + '\'' +
                ", statusReason='" + statusReason + '\'' +
                ", statusDatetime='" + statusDatetime + '\'' +
                ", currency='" + currency + '\'' +
                ", referenceId='" + referenceId + '\'' +
                ", processor='" + processor + '\'' +
                '}';
    }
}
