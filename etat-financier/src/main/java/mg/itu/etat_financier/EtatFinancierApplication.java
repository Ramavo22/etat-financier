package mg.itu.etat_financier;

import mg.itu.etat_financier.constant.Constante;
import mg.itu.etat_financier.dtos.BilanDetails;
import mg.itu.etat_financier.entity.CompteFinancier;
import mg.itu.etat_financier.service.AnalyseService;
import mg.itu.etat_financier.service.CompteFinancierService;
import mg.itu.etat_financier.service.TransactionFinanciereService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class EtatFinancierApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(EtatFinancierApplication.class, args);

//		CompteFinancierService compteFinancierService = context.getBean(CompteFinancierService.class);
//
//		List<CompteFinancier> compteFinanciers = compteFinancierService
//				.getAllByParentId(Constante.COMPTE_FINANCIER_PASSIF_COURANT);
//
//		for (CompteFinancier compteFinancier : compteFinanciers){
//			System.out.println(compteFinancier);
//		}

	}

}
