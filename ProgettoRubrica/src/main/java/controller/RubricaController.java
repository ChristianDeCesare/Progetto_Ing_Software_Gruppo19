/**
 * @file RubricaController.java
 * 
 * @brief Classe Controller per la gestione dell'interfaccia dell'applicazione "Rubrica".
 * 
 * Questa classe gestisce le interazioni con l'utente e le azioni sulla Rubrica.
 *
 * @see GestioneRubrica.Rubrica
 * @see Controller.Controller
 */



package controller;

import gestioneRubrica.Rubrica;
import java.awt.Button;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RubricaController implements Initializable {

    /**
     *  Puntatore all'istanza di Rubrica gestita.
     */
    private Rubrica rubricaPointer;

    /**
     *  Tabella per visualizzare la lista dei contatti nella Rubrica.
     */
    private TableView rubricaList;

    /**
     *  Pulsante per rimuovere un contatto selezionato dalla Rubrica.
     */
    private Button removeButton;

    /**
     * Pulsante per avviare la ricerca di contatti in rubrica
     */
    private Button researchButton;

    /**
     * Pulsante per aggiungere un nuovo contatto alla Rubrica.
     */
    private Button addButton;

    /**
     * Campo di testo per cercare contatti nella Rubrica.
     */
    private TextField researchField;

    /**
     *  Pulsante per importare contatti nella Rubrica.
     */
    private Button importButton;

    /**
     *  Pulsante per esportare contatti dalla Rubrica.
     */
    private Button exportButton;

    /**
     * @brief Pulsante per uscire dall'applicazione.
     */
    private Button exitButton;

    /**
    * @brief Inizializza il controller al caricamento della scena.
    * 
    *Questo metodo viene chiamato automaticamente dal framework JavaFX 
    *        quando la scena associata a questo controller viene caricata.
    * 
    * @param location La posizione del file FXML associato al controller (può essere null se non fornito).
    * @param resources Le risorse utilizzate per la scena (può essere null se non presenti).
    * 
    * */
     @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
    
    
    /**
     * @brief Imposta la tabella con una nuova istanza di Rubrica.
     * 
     * @param r la nuova Rubrica da visualizzare nella tabella
     * 
     * @pre r deve essere diverso da null.
     * 
     * @post il controller conterrà il puntatore alla rubrica r.
     */
    private void setRubricaList(Rubrica r) {
    
    }

    /**
     * @brief Metodo gestione aggiunta contatto
     * 
     * Questo metodo permette l'apertura di un pop-up per le operazioni di creazione e inserimento di un nuovo contatto nella tabella 
     * 
     * @param event L'evento che ha generato l'azione di aggiunta
     * 
     * @throws IOException Eccezione per la gestione di errori durante l'apertura della finestra di aggiunta del contatto
     */
    @FXML
    private void add(javafx.event.ActionEvent event) throws IOException {
        
        //Operazioni per apertura finestra di creazione
        FXMLLoader f = App.getFXML("Contatto");
        Parent root = f.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        //Creazione del controller del contatto
        ContattoController controller = f.getController(); 
        if (controller != null) { //controllo effettiva creazione del controller
            controller.setController(this.rubricaPointer); 
        } else {
            System.out.println("Il controller è nullo");
        }
       
        stage.show(); //visualizzazione pop-up di aggiunta
        
    }
    /**
     * @brief Ricerca contatti
     * 
     * Questo metodo permette la ricerca dei contatti tramite una stringa di testo
     * 
     * @param event L'evento che ha generato l'operazione di ricerca
     */
    @FXML
    private void research(javafx.event.ActionEvent event) {
    
    if(!researchField.getText().isEmpty()) //controllo che la barra ricerca sia vuota
        
            rubricaList.setItems(rubricaPointer.ricercaContatti(researchField.getText()).getContactList()); //visualizzo tutta la rubrica
    
        else
        
            rubricaList.setItems(rubricaPointer.getContactList()); //visualizzo la sottoRubrica
 
    }

    @FXML
    private void delete(javafx.event.ActionEvent event) {
    
    //carico la lista di contatti da eliminare
    ObservableList<Contatto> temp = rubricaList.getSelectionModel().getSelectedItems();
    
    //controllo che la lista non sia nè null nè vuota
    if(temp != null && temp.isEmpty())
        return;

    //mostro il segnale di avviso e aspetto il risultato della conferma
    if (Avviso.conferma("Attenzione", "Conferma Rimozione","Sei sicuro di voler eliminare il/i contatto/i?")) {
        
        //rimuovo il contatto e ripulisco il pannello del contatto
        rubricaPointer.rimuoviContatto(temp);
        contattoPane.getChildren().clear();
    
        }
        
    }

    /**
     * @brief Apre i dettagli di un contatto selezionato.
     * 
     * @param e evento che attiva l'apertura del contatto
     */
    private void openContact(ActionEvent e) {
    
    }

    /**
     * @brief Esporta i contatti della Rubrica in un file.
     * 
     * @param e evento che attiva l'esportazione
     */
    private void exportRubrica(ActionEvent e) {
    
    }

    /**
     * @brief Importa contatti nella Rubrica da un file.
     * 
     * @param e evento che attiva l'importazione
     */
    private void importRubrica(ActionEvent e) {
    
    }

   
}