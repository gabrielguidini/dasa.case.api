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
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPagamento;
    @NotNull
    private Double valorTotal;
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private PaymentEnum tipoPagamento;

    @JsonCreator
    public Payment(PaymentDTO paymentDTO){
        this.idPagamento = paymentDTO.getIdPagamento();
        this.tipoPagamento = paymentDTO.getTipoPagamento();
        this.valorTotal = paymentDTO.getValorTotal();
    }

    public Payment(){}
}
