/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tg.univlome.epl.boutique.client2;

import java.time.LocalDate;
import java.time.Month;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Queue;
import tg.univlome.epl.boutique.entites.Categorie;
import tg.univlome.epl.boutique.entites.Produit;
import tg.univlome.epl.boutique.messages.ProduitMessage;
import tg.univlome.epl.boutique.messages.TypeMessage;

/**
 *
 * @author steeltitanunbrk
 */

@Named
@RequestScoped
public class indexBean {
    
    private String message;
    
    @Inject
    @JMSConnectionFactory("jms/boutiqueConnectionFactory")
    private JMSContext context;
    
    @Resource(lookup = "jms/produitQueue")
    private Queue queue;

    public indexBean() {
    }

    public String getMessage() {
        return message;
    }
    
    @PostConstruct
    public void init() {
        Categorie cat = new Categorie(1, "Boissons", "Gamme d'eau, de jus, de liqueur");
        Produit produit = new Produit(1, "Coca-Cola", 300, LocalDate.of(2025, Month.MARCH, 1), cat);
        ProduitMessage msg = new ProduitMessage(TypeMessage.AJOUTER);
        msg.setEntite(produit);
        this.context.createProducer().send(this.queue, msg);
        this.message = "Message envoyé !";
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
