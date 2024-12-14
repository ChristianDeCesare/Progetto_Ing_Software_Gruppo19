/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package GestioneRubrica;

import gestioneRubrica.Rubrica;
import gestioneRubrica.Contatto;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.util.Collections.list;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Acer
 */
public class RubricaTest {
    private Rubrica rubrica;
    private Contatto contatto1;
    private Contatto contatto2;
    private Contatto contatto3;
    
    public RubricaTest() {
    }
    
  
    
    @BeforeEach
    public void setUp() {
        
        // Prima di ogni test, creiamo una rubrica e ci aggiungiamo dentro 2 contatti
        
        rubrica= new Rubrica();
        contatto1 = new Contatto();
        contatto1.setNome("Lorenzo");
        contatto1.setCognome("Cibellis");
        contatto1.setNumero1("1111111111");
        contatto1.setNumero2("2222222222");
        rubrica.aggiungiContatto(contatto1);
        
        contatto2 = new Contatto();
        contatto2.setNome("Christian");
        contatto2.setCognome("De Cesare");
        contatto2.setNumero1("3333333333");
        contatto2.setEmail1("chris@gmail.com");
        rubrica.aggiungiContatto(contatto2);
    }
    
   

    /**
     * Test del metodo aggiungiContatto, della classe Rubrica.
     */
    @Test
    public void testAggiungiContatto() {
        
        System.out.println("aggiungiContatto Test");
        Rubrica rubrica1 = new Rubrica();
        
        rubrica1.aggiungiContatto(contatto1);
         
        //verifica se la rubrica contiene il contatto inserito in precedenza
        assertTrue(rubrica1.getContactList().contains(contatto1));  
            
    }

    /**
     * Test del metodo rimuoviContatto, della classe Rubrica.
     */
    @Test
    public void testRimuoviContatto() {
        
        System.out.println("aggiungiContatto Test");
        Rubrica rubrica1 = new Rubrica();
        
        // crea una lista che conterrà il contatto o i contatti che dovranno essere eliminati
        ObservableList<Contatto> list;
        list = FXCollections.observableArrayList();  
        list.add(contatto1);
        
        rubrica1.aggiungiContatto(contatto1);
        rubrica1.rimuoviContatto(list);
        
        // verifica se il contatto eliminato precedentemente sia ancora presente all'interno della rubrica
        assertFalse(rubrica1.getContactList().contains(contatto1));  
    }

    /**
     * Test del metodo getContactList, della classe Rubrica.
     */
    @Test
    public void testGetContactList() {
        
        Rubrica rubrica1 = new Rubrica();
        rubrica1.aggiungiContatto(contatto1);
        rubrica1.aggiungiContatto(contatto2);
        
        assertEquals(rubrica.getContactList(), rubrica1.getContactList());
    }

    /**
     * Test del metodo ricercaContatti, della classe Rubrica.
     */
    @Test
    void testRicercaContattiConNomeEsatto() {
       
        System.out.println("RicercaContatti Test");

        // Creazione di una nuova rubrica con dati di esempio
        Rubrica rubricaTest1 = new Rubrica();

        Contatto contatto1 = new Contatto();
        contatto1.setNome("Lorenzo");
        contatto1.setCognome("Cibellis");
        rubricaTest1.aggiungiContatto(contatto1);

        Contatto contatto2 = new Contatto();
        contatto2.setNome("Mario");
        contatto2.setCognome("Rossi");
        rubricaTest1.aggiungiContatto(contatto2);
        
        
        // Stringa che si vuole ricercare all'interno della rubrica
        String s="Lor";
        
        Rubrica temp = new Rubrica();
        
        temp = rubricaTest1.ricercaContatti(s);
        
        assertNotNull(temp);
        // Verifica che all'interno della rubrica temporanea ci sia solo 1 contatto
        assertEquals(1, temp.getContactList().size()); 
        // Verifica che il nome del contatto contenuto all'interno della lista temporanea sia quello atteso
        assertTrue(temp.getContactList().stream().anyMatch(c -> c.getNome().equals("Lorenzo")));
    
    }


    /**
     * Test del metodo importaRubrica, della classe Rubrica.
     */
    @Test
public void testImportaRubrica() throws Exception {
    
    System.out.println("importaRubrica Test");

    // Creazione di un file di test temporaneo
    String nomefile = "rubrica_test.csv";
    
    // All'interno del file temporaneo vengono inseriti 2 contatti
    try (PrintWriter writer = new PrintWriter(nomefile)) {
        writer.println("RUBRICA");
        writer.println("Cognome;Nome;Numero1;Numero2;Numero3;Email1;Email2;Email3");
        writer.println("De Cesare;Christian;1234567890;;;christian@example.com;;");
        writer.println("Cibellis;Lorenzo;0987654321;1122334455;;lorenzo@example.com;lorenzo.secondo@example.com;");
    }

    // Istanza della rubrica da testare
    Rubrica instance = new Rubrica();

    // Esecuzione del metodo
    Rubrica result = instance.importaRubrica(nomefile);

    // Verifica dei risultati
    assertNotNull(result);
    assertEquals(2, result.getContactList().size());
 
    // Eliminazione del file di test temporaneo
    Files.deleteIfExists(Paths.get(nomefile));
}


    /**
     * Test del metodo esportaRubrica, della classe Rubrica.
     */
    @Test
    public void testEsportaRubrica() throws Exception {
    
    System.out.println("esportaRubrica Test");

    // Nome del file temporaneo
    String nomefile = "rubrica_export_test.csv";

    // Creazione di una rubrica con dati di esempio
    Rubrica instance = new Rubrica();
    contatto1.setNumero3("");
    contatto1.setEmail1("");
    contatto1.setEmail2("");
    contatto1.setEmail3("");
    
    contatto2.setNumero2("");
    contatto2.setNumero3("");
    contatto2.setEmail2("");
    contatto2.setEmail3("");
    
    instance.aggiungiContatto(contatto1);
    instance.aggiungiContatto(contatto2);
    
    
    // Esecuzione del metodo di esportazione
    instance.esportaRubrica(nomefile);

    // Lettura del file esportato per verificare il contenuto
    List<String> lines = Files.readAllLines(Paths.get(nomefile));

    // Verifica delle righe del file
    assertEquals("RUBRICA", lines.get(0));
    assertEquals("COGNOME;NOME;NUMERO 1;NUMERO 2;NUMERO 3;EMAIL 1;EMAIL 2; EMAIL3", lines.get(1));
    assertEquals("Cibellis;Lorenzo;1111111111;2222222222;;;;", lines.get(2));
    assertEquals("De Cesare;Christian;3333333333;;;chris@gmail.com;;", lines.get(3));

    // Pulizia del file temporaneo
    Files.deleteIfExists(Paths.get(nomefile));
    }
    
}
