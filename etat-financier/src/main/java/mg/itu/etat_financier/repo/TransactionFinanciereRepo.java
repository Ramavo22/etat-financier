package mg.itu.etat_financier.repo;

import mg.itu.etat_financier.dtos.BilanDetails;
import mg.itu.etat_financier.entity.TransactionFinanciere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionFinanciereRepo extends JpaRepository<TransactionFinanciere, Integer> {



    @Query("""
            SELECT
            new mg.itu.etat_financier.dtos.BilanDetails(
                    tf.compteFinancier,
                    SUM(tf.montant)
                )
            FROM TransactionFinanciere tf
            GROUP BY tf.compteFinancier, tf.compteFinancier.id
            ORDER BY tf.compteFinancier.id
            """)
    public List<BilanDetails> getBilanDetails();
}
