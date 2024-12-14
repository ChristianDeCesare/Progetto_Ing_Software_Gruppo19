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

import gestioneRubrica.Avviso;
import gestioneRubrica.Rubrica;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RubricaController implements Initializable {

    /**
     * Pannello di base sul quale è visualizzato il contatto.
     */
    @FXML
    private StackPane contattoPane;
    
    /**
     * Tabella visualizzazione dei contatti.
     */
    @FXML
    private TableView<Contatto> rubricaList;
    
    /**
     * Colonna dei cognomi della tabella.
     */
    @FXML
    private TableColumn<Contatto, String> cognomeClm;
    
    /**
     * Colonna dei nomi della tabella.
     */
    @FXML
    private TableColumn<Contatto, String> nomeClm;

    /**
     * Bottone di apertura pop-up per aggiunta contatto.
     */
    @FXML
    private javafx.scene.control.Button addButton;
    
    /**
     * Bottone per la rimozione del/dei contatto/i.
     */
    @FXML
    private javafx.scene.control.Button removeButton;
    
    /**
     * Bottone per l'importazione di rubrica da file esterno.
     */
    @FXML
    private javafx.scene.control.Button importButton;
    
    /**
     * Bottone per l'esportazione della rubrica su file esterno.
     */
    @FXML
    private javafx.scene.control.Button exportButton;
    
    /**
     * Bottone per la ricerca di contatti all'interno della rubrica.
     */
    @FXML
    private javafx.scene.control.Button researchButton;
    
    /**
     * Bottone per resettare la ricerca della rubrica.
     */
    @FXML
    private javafx.scene.control.Button resetResearch;
    
    /**
     * Campo di testa per la ricerca dei contatti tramite sottostringa nella rubrica.
     */
    @FXML
    private javafx.scene.control.TextField researchField;
    
    /**
     * Bottone per chiusura dell'interfaccia grafica.
     */
    @FXML
    private javafx.scene.control.Button exitButton;
            
    /**
     * Puntatore alla rubrica gestita.
     */
    private Rubrica rubricaPointer;
    
    /**
     * Puntatore al controller visualizzato sul "pannello del contatto".
     */
    ContattoController contattoController;

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