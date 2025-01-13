package mg.itu.etat_financier.controller;

import mg.itu.etat_financier.constant.Constante;
import mg.itu.etat_financier.dtos.TransactionDto;
import mg.itu.etat_financier.entity.CompteFinancier;
import mg.itu.etat_financier.entity.TransactionFinanciere;
import mg.itu.etat_financier.service.CompteFinancierService;
import mg.itu.etat_financier.service.TransactionFinanciereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class DonneeController {

    @Autowired
    CompteFinancierService compteFinancierService;

    @Autowired
    TransactionFinanciereService transactionFinanciereService;

    @GetMapping("/menuData")
    public ResponseEntity<HashMap<String, Object>> getData(){
        HashMap<String, Object> datas = new HashMap<>();
        List<CompteFinancier> actifCourant = compteFinancierService
                .getAllByParentId(Constante.COMPTE_FINANCIER_ACTIF_COURANT_ID);
        List<CompteFinancier> actifNonCourant = compteFinancierService
                .getAllByParentId(Constante.COMPTE_FINANCIER_ACTIF_NON_COURANT);
        List<CompteFinancier> passifCourant = compteFinancierService
                .getAllByParentId(Constante.COMPTE_FINANCIER_PASSIF_COURANT);
        List<CompteFinancier> passifNonCourant = compteFinancierService
                .getAllByParentId(Constante.COMPTE_FINANCIER_PASSIF_NON_COURANT);
        List<CompteFinancier> capitauxPropre = compteFinancierService
                .getAllByParentId(Constante.COMPTE_FINANCIER_CAPITAUX_PROPRES);


        List<CompteFinancier> charges = compteFinancierService
                .getALlByTypeCompteId(Constante.TYPE_COMPTE_CHARGES);
        List<CompteFinancier> produits = compteFinancierService
                .getALlByTypeCompteId(Constante.TYPE_COMPTE_PRODUITS);

        datas.put("actifCourant", actifCourant);
        datas.put("actifNonCourant", actifNonCourant);
        datas.put("passifCourant", passifCourant);
        datas.put("passifNonCourant", passifNonCourant);
        datas.put("capitauxPropre", capitauxPropre);
        datas.put("charges", charges);
        datas.put("produits", produits);

        HashMap<String, Object> dtos = new HashMap<>();
        dtos.put("status","success");
        dtos.put("datas", datas);
        dtos.put("error",null);

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/transaction")
    public ResponseEntity<HashMap<String,Object>> insererController(@RequestBody TransactionDto transactionDto){
        HashMap<String, Object> datas = new HashMap<>();
        TransactionFinanciere transactionFinanciere = new TransactionFinanciere();
        System.out.println(transactionDto);

        CompteFinancier compteFinancier = new CompteFinancier();
        compteFinancier.setId(transactionDto.getCompteId().longValue());

        transactionFinanciere.setCompteFinancier(compteFinancier);
        transactionFinanciere.setMontant(transactionDto.getMontant());
        transactionFinanciere.setDateTransaction(LocalDate.parse(transactionDto.getDate()));
        transactionFinanciere.setDescription(transactionDto.getDescription());

        try {
            transactionFinanciereService.save(transactionFinanciere);
            datas.put("status","success");
            HashMap<String, Object> dtos = new HashMap<>();
            dtos.put("messages","transaction réussi");
            datas.put("datas",dtos);
            return ResponseEntity.ok(datas);
        }catch (Exception e){
            datas.put("status","error");
            datas.put("error","erreur:" + e.getMessage());
            return ResponseEntity.ok(datas);
        }
    }





}
