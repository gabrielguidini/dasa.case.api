package br.com.dasa.dto;

import br.com.dasa.model.enums.PaymentEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NotNull
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PaymentDTO {
    private UUID idPagamento = UUID.randomUUID();
    private PaymentEnum tipoPagamento;
    private Double valorTotal;
}