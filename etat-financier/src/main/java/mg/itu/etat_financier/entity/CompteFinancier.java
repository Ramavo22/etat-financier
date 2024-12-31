package mg.itu.etat_financier.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class CompteFinancier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "type_compte")
    private TypeCompte typeCompte;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CompteFinancier parent;

    // Getters and setters
}
