package mg.itu.etat_financier.controller;

import mg.itu.etat_financier.service.BilanService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bilan")
public class BilanController {

    @Autowired
    private BilanService bilanService;

    /**
     * Endpoint pour récupérer les données du bilan en JSON
     * @return ResponseEntity contenant le bilan au format JSON
     */
    @GetMapping
    public ResponseEntity<String> getBilanData() {
        try {
            // Appeler le service pour obtenir les données du bilan
            JSONObject bilan = bilanService.getBilanJSON();

            // Retourner les données en réponse HTTP avec un statut 200 (OK)
            return ResponseEntity.ok(bilan.toString());
        } catch (Exception e) {
            // Gérer les erreurs et retourner une réponse avec un statut 500 (erreur interne du serveur)
            return ResponseEntity.status(500).body("Erreur lors de la récupération du bilan: " + e.getMessage());
        }
    }
}
