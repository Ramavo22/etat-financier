package mg.itu.etat_financier.repo;

import mg.itu.etat_financier.entity.CompteFinancier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompteFinancierRepo extends JpaRepository<CompteFinancier,Long> {

    @Query("SELECT cf FROM CompteFinancier cf WHERE cf.parent.id = :compteFinancierId")
    public List<CompteFinancier> findByParent(@Param("compteFinancierId") Long compteFinancierId);

    @Query("SELECT cf FROM CompteFinancier cf WHERE cf.typeCompte.id = :typeCompteId")
    public List<CompteFinancier> findByTypeCompteFinancier(@Param("typeCompteId")Long id);
}
