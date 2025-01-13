package mg.itu.etat_financier.controller;

import mg.itu.etat_financier.service.AnalyseService;
import mg.itu.etat_financier.service.BilanService;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/analyse")
public class AnalyseController {
    @Autowired
    private AnalyseService analyse;

    @GetMapping
    public ResponseEntity<HashMap<String,Double>> getAnalyseData() {
        HashMap<String,Double> data = new HashMap<>();

        data.put("margeNette", analyse.margeNette());
        data.put("roa", analyse.retourActif());
        data.put("roe", analyse.retourCapitauxPropre());
        data.put("ratioLiquiditeGeneral", analyse.ratioLiquiditeGeneral());
        data.put("ratioLiquiditeReduit", analyse.ratioLiquiditeReduite());
        data.put("ratioEndettementGlobal", analyse.ratioEndettementGlobal());

        return ResponseEntity.ok(data);
    }

}
