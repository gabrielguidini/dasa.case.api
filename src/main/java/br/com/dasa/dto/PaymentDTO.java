package br.com.dasa.dto;

import br.com.dasa.model.enums.PaymentEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@NotNull
@Getter
@Setter
public class PaymentDTO {
    private Long idPagamento;
    private PaymentEnum tipoPagamento;
    private Double valorTotal;

    public PaymentDTO(Long idPagamento, PaymentEnum tipoPagamento, Double valorTotal) {
        this.idPagamento = idPagamento;
        this.tipoPagamento = tipoPagamento;
        this.valorTotal = valorTotal;
    }

    public PaymentDTO() {
    }
}