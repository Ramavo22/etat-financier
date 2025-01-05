package mg.itu.etat_financier.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFinanciere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "compte_financier_id")
    CompteFinancier compteFinancier;

    @Column(nullable = false)
    Double montant;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    LocalDate dateTransaction;
}
