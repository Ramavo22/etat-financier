package mg.itu.etat_financier.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.itu.etat_financier.entity.CompteFinancier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilanDetails {
    CompteFinancier compteFinancier;
    Double total;
}
