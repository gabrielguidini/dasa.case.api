package br.com.dasa.utils;

import br.com.dasa.dto.PaymentDTO;
import br.com.dasa.model.Payment;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentUtils {

    public PaymentDTO convertPaymentToDto (Payment payment) {
        return PaymentDTO.builder()
                .idPagamento(payment.getIdPagamento())
                .tipoPagamento(payment.getTipoPagamento())
                .valorTotal(payment.getValorTotal())
                .build();
    }
}
