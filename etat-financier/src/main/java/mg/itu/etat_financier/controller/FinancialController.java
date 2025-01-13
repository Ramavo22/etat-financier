package mg.itu.etat_financier.controller;

import mg.itu.etat_financier.service.FinancialData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/financial")
@CrossOrigin(origins = "http://localhost:5173")
public class FinancialController {

    private final FinancialData financialData;

    @Autowired
    public FinancialController(FinancialData financialData) {
        this.financialData = financialData;
    }

    @GetMapping("/data")
    public ResponseEntity<String> getFinancialData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        JSONArray result = financialData.getFinancialDataAsJSON(startDate, endDate);
        return ResponseEntity.ok(result.toString());
    }
}
