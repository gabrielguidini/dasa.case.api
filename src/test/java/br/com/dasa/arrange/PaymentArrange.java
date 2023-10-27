package br.com.dasa.arrange;

import br.com.dasa.model.Payment;
import br.com.dasa.model.enums.PaymentEnum;

public class PaymentArrange {

    public PaymentArrange() {}

    public static Payment getValidPayment(){
        return Payment.builder()
                .idPagamento(1L)
                .valorTotal(200d)
                .tipoPagamento(PaymentEnum.DINHEIRO)
                .build();
    }
}
