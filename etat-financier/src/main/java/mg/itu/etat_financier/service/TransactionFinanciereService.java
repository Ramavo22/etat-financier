package mg.itu.etat_financier.service;

import mg.itu.etat_financier.dtos.BilanDetails;
import mg.itu.etat_financier.entity.CompteFinancier;
import mg.itu.etat_financier.entity.TransactionFinanciere;
import mg.itu.etat_financier.exception.ArrayLengthNotMatchingException;
import mg.itu.etat_financier.repo.TransactionFinanciereRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionFinanciereService {

    @Autowired
    private TransactionFinanciereRepo transactionFinanciereRepo;


    public void ajouterTransactionFinanciere(List<Integer> compteFinancierId, List<Double> montants, List<String> descriptions, LocalDate dateTransaction) {
        /*
        *   Verification si les lignes sont correctes
        * */
        int compteLength = compteFinancierId.size();
        int montantLength = montants.size();
        int descriptionLength = descriptions.size();

        if(compteLength == montantLength && compteLength == descriptionLength) {
            int i = 0;
            while (i<compteLength) {

                Integer compteId = compteFinancierId.get(i);
                CompteFinancier compteFinancier = new CompteFinancier();
                compteFinancier.setId(compteId.longValue());

                Double montant = montants.get(i);

                String description = descriptions.get(i);

                TransactionFinanciere transactionFinanciere = new TransactionFinanciere();
                transactionFinanciere.setCompteFinancier(compteFinancier);
                transactionFinanciere.setMontant(montant);
                transactionFinanciere.setDescription(description);
                transactionFinanciere.setDateTransaction(dateTransaction);

                save(transactionFinanciere);

                i++;
            }
        }
        else{
            throw new ArrayLengthNotMatchingException("the arrays use in this function had not the same length");
        }
    }
    private void save(TransactionFinanciere transactionFinanciere) {
        transactionFinanciereRepo.save(transactionFinanciere);
    }


    public List<BilanDetails> getBilan(){
        return transactionFinanciereRepo.getBilanDetails();
    }
}
