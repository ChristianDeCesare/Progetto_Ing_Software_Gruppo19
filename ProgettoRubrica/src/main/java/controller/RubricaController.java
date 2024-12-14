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
import gestioneRubrica.Contatto;
import gestioneRubrica.Rubrica;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    
        //rendo invisibile il pannello del contatto
        contattoPane.setVisible(false);
        
        //creo la rubrica su cui lavorare
        this.rubricaPointer = new Rubrica();
        
         
       //lego le colonne della tabella ai campi nome e cognome dei contatti della rubrica 
        nomeClm.setCellValueFactory(s -> { return new SimpleStringProperty(s.getValue().getNome()); });
        cognomeClm.setCellValueFactory(new PropertyValueFactory("cognome"));  
        rubricaList.setItems(rubricaPointer.getContactList());
        
        //permetto la selezione multipla di contatti all'interno della tabella (CTRL + clickMouse)
        rubricaList.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        
        //rendo invisibile il tasto di reset della ricerca finchè il campo di ricerca è vuoto
        resetResearch.visibleProperty().bind(Bindings.createBooleanBinding(
                () -> (!researchField.getText().isEmpty()), researchField.textProperty()));
        
       
        //gestisco L'evento di selezione singola e multipla dei contatti e lo associo alla tabella
        EventHandler<MouseEvent> ClickHandler = event ->{
            if(!event.isControlDown())
                openContact(null); //apre lo studente

        };
        rubricaList.setOnMouseClicked(ClickHandler);
        
        
        //gestisco l'evento di chiusura visualizzazione contatto tramite  tasto esc associandolo alla tabella
        EventHandler<KeyEvent> escHandler = event -> { 
            if(event.getCode() == javafx.scene.input.KeyCode.ESCAPE) //controllo che il tasto pigiato sia quello di escape
                contattoPane.getChildren().clear(); //chiudo la schermata di visualizzazione del contatto
        };
        rubricaList.setOnKeyPressed(escHandler);
        
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