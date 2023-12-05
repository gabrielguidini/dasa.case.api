package br.com.dasa.dto;

import br.com.dasa.model.enums.PaymentEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NotNull
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PaymentDTO {
    private Long idPagamento;
    private PaymentEnum tipoPagamento;
    private Double valorTotal;
}