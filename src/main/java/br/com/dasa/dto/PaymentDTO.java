package br.com.dasa.dto;

import br.com.dasa.model.enums.PaymentEnum;
import jakarta.validation.constraints.NotNull;

@NotNull
public record PaymentDTO(
        Long id_pagamento,
        PaymentEnum tipo_pagamento,
        Double valor_total
) {
}
