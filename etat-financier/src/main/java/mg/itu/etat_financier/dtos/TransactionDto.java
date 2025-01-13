package mg.itu.etat_financier.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Integer compteId;
    private double montant;
    private String date;
    private boolean estDebit;
    private String description;
}
