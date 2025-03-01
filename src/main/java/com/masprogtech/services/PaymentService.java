package com.masprogtech.services;

import com.masprogtech.dtos.PaymentConfirmationDTO;

public interface PaymentService {
    String processPayment(Long orderId);

    void confirmPayment(PaymentConfirmationDTO confirmationDTO);

}
