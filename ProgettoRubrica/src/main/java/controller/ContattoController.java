/**
 * @file ContattoController.java
 * 
 * @brief Controller per la gestione delle operazioni sui contatti.
 *
 *    Questa classe si occupa della gestione dell'interfaccia utente per interagire
 *        con un oggetto {@link Contatto.java}. Permette di  modificare, confermare, eliminare e
 *        gestire le operazioni sui dati del contatto.
 *        Estende la classe {@link Controller.java} da cui ne eredita i metodi {@code display} e {@code goBack }.
 * 
 * @see GestioneRubrica.Rubrica
 * @see Controller.Controller
 * @see GestioneRubrica.Contatto
 */


package controller;


import gestioneRubrica.Contatto;
import gestioneRubrica.Rubrica;
import java.awt.Button;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;


public class ContattoController extends Controller implements Initializable {

    // Attributi
   /**
     * Pannello di base sul quale è costruito il Controller.
     */
    @FXML
    private StackPane contactPane;
    
    /**
     * Campo di testo per il nome del contatto.
     */
    @FXML
    private javafx.scene.control.TextField nameField;

    /**
     * Campo di testo per il cognome del contatto.
     */
    @FXML
    private javafx.scene.control.TextField surnameField;

    /**
     * Campo di testo per il primo numero di telefono del contatto.
     */
    @FXML
    private javafx.scene.control.TextField number1Field;

    /**
     * Campo di testo per il secondo numero di telefono del contatto.
     */
    @FXML
    private javafx.scene.control.TextField number2Field;

    /**
     * Campo di testo per il terzo numero di telefono del contatto.
     */
    @FXML
    private javafx.scene.control.TextField number3Field;

    /**
     * Campo di testo per il primo indirizzo email del contatto.
     */
    @FXML
    private javafx.scene.control.TextField email1Field;

    /**
     * Campo di testo per il secondo indirizzo email del contatto.
     */
    @FXML
    private javafx.scene.control.TextField email2Field;

    /**
     * Campo di testo per il terzo indirizzo email del contatto.
     */
    @FXML
    private javafx.scene.control.TextField email3Field;

    /**
     * Bottone per abilitare la modifica del contatto.
     */
    @FXML
    private javafx.scene.control.Button modifyButton;

    /**
     * Bottone per confermare l'operazione di aggiunta o modifica del contatto.
     */
    @FXML
    private javafx.scene.control.Button confirmButton;

    /**
     * Bottone per uscire dalla vista corrente.
     */
    @FXML
    private javafx.scene.control.Button exitButton;

    /**
     * Puntatore alla rubrica a cui appartiene il contatto.
     */
    private Rubrica rubricaPointer;
    
    /**
     * Puntatore al contatto su cui il controller lavora.
     */
    private Contatto contactPointer;
    
    /**
     * Tipo del controller: 
     * -false se il controller gestisce l'aggiunta del contatto alla rubrica;
     * -true se il controller gestisce la visualizzazione e modifica del contatto.
     */
    private boolean typeController;
    
    /**
     * puntatore alla tabella in cui il contatto è mostrato.
     */
    private TableView<Contatto> tablePointer;

    /**
    * @brief Inizializza il controller al caricamento della scena.
    * 
    * Questo metodo viene chiamato automaticamente dal framework JavaFX 
    * quando la scena associata a questo controller viene caricata.
    * 
    * @param location La posizione del file FXML associato al controller (può essere null se non fornito).
    * @param resources Le risorse internazionalizzate utilizzate per la scena (può essere null se non presenti).
    * 
    * */
     @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
    
    
    /**
     * @brief Imposta il controller con i riferimenti e inizializza i componenti.
     *
     *        Questo metodo permette di configurare il controller e i suoi componenti
     *        per il funzionamento corretto dell'interfaccia utente.
     * 
     *  @param r il riferimento alla rubrica.
     * 
     * @pre r non deve essere null.
     * 
     * @post Il controller conterrà il riferimento alla rubrica su cui lavorare.
     */
    public void setController(Rubrica r) {
        // Da implementare
    }

     /**
     * @brief Configura il controller con i riferimenti ai dati e alla GUI.
     *
     * @param c il contatto da associare al controller.
     * @param r la rubrica a cui il controller farà riferimento.
     * @param table la tabella di visualizzazione dei contatti.
     *
     * Questo metodo esegue le seguenti operazioni:
     * - Salva i riferimenti alla rubrica, alla tabella e al contatto.
     * - Popola i campi della GUI con i dati del contatto specificato.
     * - Nasconde i pulsanti di conferma e uscita.
     * - Disabilita la possibilità di modificare i dati iniziali tramite `disableModify(true)`.
     * 
     * @pre c, table ed r non devono essere null.
     * 
     * @post il controller conterrà le informazioni di c.
     */
    public void setController(Contatto c, Rubrica r, TableView<Contatto> table) {
        
        //salvataggio puntatore rubrica
        rubricaPointer=r;
        
        //definizione tipo di controller
        typeController = true;
        
        //salvataggio puntatore alla tabella di visualizzazione del contatto
        this.tablePointer = table;
        
        
        // Memorizza il contatto passato al controller
        this.contactPointer = c;

        // Popola i campi della GUI con i dati del contatto
        nameField.setText(c.getNome());
        surnameField.setText(c.getCognome());
        String[] numeri = c.getNumeri();
        number1Field.setText(numeri[0]);
        number2Field.setText(numeri[1]);
        number3Field.setText(numeri[2]);
        String[] emails = c.getEmails();
        email1Field.setText(emails[0]);
        email2Field.setText(emails[1]);
        email3Field.setText(emails[2]);
        
        //reso il bottone di conferma invisibile
        confirmButton.setVisible(false);
        
        //reso il bottone di uscita invisibile e inutilizzabile
        exitButton.setVisible(false);
        exitButton.setDisable(false);
        
        //invocato metodo disableModify con attributo "true"
        disableModify(true);
        
        
        
    }

    /**
     * @brief Gestisce l'interazione con i campi di testo
     *
     * Il metodo gestisce l'interazione con i campi di testo in modo che si renda
     * possibile/impossibile la loro modifica
     * 
     * @param disable Permette di definire l'abilitazione o la disabilitazione dei campi:
     *                -true: disabilita
     *                -false: abilita
     *        
     */
    public void disableModify(boolean disable) {
       
        this.nameField.setEditable(!disable);
        this.surnameField.setEditable(!disable);
        this.number1Field.setEditable(!disable);
        this.number2Field.setEditable(!disable);
        this.number3Field.setEditable(!disable);
        this.email1Field.setEditable(!disable);
        this.email2Field.setEditable(!disable);
        this.email3Field.setEditable(!disable);

    }

    /**
     * @brief Conferma le modifiche al contatto.
     *
     *        Questo metodo viene invocato quando l'utente preme il bottone di conferma.
     *        Le modifiche ai dati del contatto vengono validate e salvate.
     *
     * @param c l'evento che ha generato l'azione di conferma.
     */
    private void confirm(ActionEvent c) {
        // Da implementare
    }

    /**
     * @brief Abilita la modifica dei dati del contatto.
     *
     *        Questo metodo viene invocato quando l'utente preme il bottone di modifica.
     *        I campi di input diventano modificabili per permettere all'utente di,eventualmente,
     *        modificare i dati.
     *
     * @param c l'evento che ha generato l'azione di modifica.
     */
    private void modify(ActionEvent c) {
        
        //si rende invisibile il bottone di modifica
        modifyButton.setVisible(false);
        
        //si rende visibile il bottone di conferma
        confirmButton.setVisible(true);
        
        //viene invocato il metodo disable modify con attributo "false"
        disableModify(false);
        
    }

    /**
     * @brief Elimina il contatto corrente.
     *
     *        Questo metodo viene invocato quando l'utente preme il bottone per eliminare uno o più contatti.
     *        Il contatto o i contatti selezionati vengono rimossi dalla lista dei contatti gestita dall'applicazione.
     *
     * @param c l'evento che ha generato l'azione di eliminazione.
     */
    private void delete(ActionEvent c) {
        // Da implementare
    }
}
