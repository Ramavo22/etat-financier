package mg.itu.etat_financier.service;

import mg.itu.etat_financier.entity.CompteFinancier;
import mg.itu.etat_financier.repo.CompteFinancierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompteFinancierService {

    @Autowired
    CompteFinancierRepo compteFinancierRepo;

    public List<CompteFinancier> findAll() {
        return compteFinancierRepo.findAll();
    }

    public List<CompteFinancier> getAllByParentId(Long parentId){
        return compteFinancierRepo.findByParent(parentId);
    }

    public List<CompteFinancier> getALlByTypeCompteId(Long typeCompteId){
        return compteFinancierRepo.findByTypeCompteFinancier(typeCompteId);
    }


}
