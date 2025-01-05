package mg.itu.etat_financier;

import mg.itu.etat_financier.dtos.BilanDetails;
import mg.itu.etat_financier.service.AnalyseService;
import mg.itu.etat_financier.service.TransactionFinanciereService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class EtatFinancierApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(EtatFinancierApplication.class, args);

		AnalyseService analyseService = context.getBean(AnalyseService.class);

		Double margeNette = analyseService.margeNette();
		Double roa = analyseService.retourActif();
		Double roe = analyseService.retourCapitauxPropre();
		Double ratioLiquiditeGeneral = analyseService.ratioLiquiditeGeneral();
		Double ratioLiquiditeReduit = analyseService.ratioLiquiditeReduite();
		Double ratioEndettementGlobal = analyseService.ratioEndettementGlobal();

		System.out.println("Résultats des analyses financières :");
		System.out.println("-------------------------------------");
		System.out.printf("Marge Nette : %.2f%%%n", margeNette != null ? margeNette : 0);
		System.out.printf("Retour sur Actif (ROA) : %.2f%%%n", roa != null ? roa : 0);
		System.out.printf("Retour sur Capitaux Propres (ROE) : %.2f%%%n", roe != null ? roe : 0);
		System.out.printf("Ratio de Liquidité Générale : %.2f%n", ratioLiquiditeGeneral != null ? ratioLiquiditeGeneral : 0);
		System.out.printf("Ratio de Liquidité Réduite : %.2f%n", ratioLiquiditeReduit != null ? ratioLiquiditeReduit : 0);
		System.out.printf("Ratio d'Endettement Global : %.2f%%%n", ratioEndettementGlobal != null ? ratioEndettementGlobal : 0);
		System.out.println("-------------------------------------");

	}

}
