package br.com.dasa.model;

import br.com.dasa.dto.PaymentDTO;
import br.com.dasa.model.enums.PaymentEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "pagamento")
@Data
@Getter
@Setter
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_pagamento;
    @NotNull
    private Double valor_total;
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private PaymentEnum tipo_pagamento;

    @JsonCreator
    public Payment(PaymentDTO paymentDTO){
        this.id_pagamento = paymentDTO.id_pagamento();
        this.tipo_pagamento = paymentDTO.tipo_pagamento();
        this.valor_total = paymentDTO.valor_total();
    }

    public Payment(){}
}
