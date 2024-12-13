/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package GestioneRubrica;

import gestioneRubrica.Contatto;
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
public class ContattoTest {
    private Contatto contatto; //contatto da sfruttare per i test
    public ContattoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        System.out.println("Inizializzazione contatto standard");
        // Prima di ogni test, creiamo un nuovo contatto
        contatto = new Contatto();
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    
    @Test
    public void testSetAndGetNome() {
        System.out.println("setNome e getNome");
        // Testiamo i metodi set e get per il nome
        contatto.setNome("Marco");
        assertEquals("Marco", contatto.getNome(), "Il nome non è stato impostato correttamente.");
    }

    
    @Test
    void testSetAndGetCognome() {
        // Testiamo i metodi set e get per il cognome
        contatto.setCognome("Rossi");
        assertEquals("Rossi", contatto.getCognome(), "Il cognome non è stato impostato correttamente.");
    }

    
    @Test
    void testSetAndGetNumeri() {
        // Testiamo i numeri di telefono
        contatto.setNumero1("1234567890");
        contatto.setNumero2("0987654321");
        contatto.setNumero3("1122334455");

        String[] numeri = contatto.getNumeri();
        assertEquals("1234567890", numeri[0], "Il primo numero non è stato impostato correttamente.");
        assertEquals("0987654321", numeri[1], "Il secondo numero non è stato impostato correttamente.");
        assertEquals("1122334455", numeri[2], "Il terzo numero non è stato impostato correttamente.");
    }

    
    /**
     * Test of setEmail1 method, of class Contatto.
     */
    @Test
    public void testSetEmail1() {
        System.out.println("setEmail1");
        String email = "";
        Contatto instance = new Contatto();
        instance.setEmail1(email);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEmail2 method, of class Contatto.
     */
    @Test
    public void testSetEmail2() {
        System.out.println("setEmail2");
        String email = "";
        Contatto instance = new Contatto();
        instance.setEmail2(email);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEmail3 method, of class Contatto.
     */
    @Test
    public void testSetEmail3() {
        System.out.println("setEmail3");
        String email = "";
        Contatto instance = new Contatto();
        instance.setEmail3(email);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNome method, of class Contatto.
     */
    @Test
    public void testGetNome() {
        System.out.println("getNome");
        Contatto instance = new Contatto();
        String expResult = "";
        String result = instance.getNome();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCognome method, of class Contatto.
     */
    @Test
    public void testGetCognome() {
        System.out.println("getCognome");
        Contatto instance = new Contatto();
        String expResult = "";
        String result = instance.getCognome();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumeri method, of class Contatto.
     */
    @Test
    public void testGetNumeri() {
        System.out.println("getNumeri");
        Contatto instance = new Contatto();
        String[] expResult = null;
        String[] result = instance.getNumeri();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEmails method, of class Contatto.
     */
    @Test
    public void testGetEmails() {
        System.out.println("getEmails");
        Contatto instance = new Contatto();
        String[] expResult = null;
        String[] result = instance.getEmails();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class Contatto.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Contatto c = null;
        Contatto instance = new Contatto();
        int expResult = 0;
        int result = instance.compareTo(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
