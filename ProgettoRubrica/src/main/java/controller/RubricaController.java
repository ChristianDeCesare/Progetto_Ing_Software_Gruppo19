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

import com.mycompany.progettorubrica.App;
import gestioneRubrica.Avviso;
import gestioneRubrica.Contatto;
import gestioneRubrica.Rubrica;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.stage.FileChooser;
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
     * Campo di testo per la ricerca dei contatti tramite sottostringa all'interno della rubrica.
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
    *quando la scena associata a questo controller viene caricata.
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
                try {
                    openContact(null); //apre lo studente
            } catch (IOException ex) {
                Logger.getLogger(RubricaController.class.getName()).log(Level.SEVERE, null, ex);
            }

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
     * @brief Metodo per la gestione dell'aggiunta di un nuovo contatto all'interno della rubrica.
     * 
     * Questo metodo permette l'apertura di un pop-up per le operazioni di 
     * creazione e inserimento di un nuovo contatto nella rubrica 
     * 
     * @param event L'evento che ha generato l'azione di aggiunta del contatto
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
     * @brief Ricerca dei contatti all'interno della rubrica
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
    
    /**
     * @brief Metodo reset ricerca
     * 
     * Questo metodo permette il reset della ricerca, mostrando nuovamente la rubrica "originale" 
     * e svuotando la barra ricerca
     * 
     * @param event L'evento che ha generato l'operazione di reset 
     */
    @FXML
    private void resetResearch(javafx.event.ActionEvent event) {
        
        //resetta il testo del campo di ricerca
        researchField.setText("");
        
        //resetta la visualizzazione della lista
        rubricaList.setItems(rubricaPointer.getContactList());
        
        Platform.runLater(() -> { //Cambia il focus all'apertura della schermata
        researchField.requestFocus();
        researchField.positionCaret(0); //posizione il cursore all'inizio del textField
        });
    }

    
      
    /**
     * @brief Metodo delete contatto/i
     * 
     * Questo metodo permette la rimozione di uno o più contatti dalla rubrica.
     * Nel momento in cui viene premuto il tasto delete, avendo selezionato 
     * uno o più contatti dalla rubrica, viene mostrato a video un messaggio di conferma eliminazione,
     * l'utente può scegliere se confermare o annullare l'operazione.
     * 
     * @param event L'evento che ha generato l'operazione di eliminazione 
     */
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
     * @param event evento che attiva l'apertura del contatto
     * 
     * @throws IOException
     */
    private void openContact(javafx.event.ActionEvent event) throws IOException {
    
        Contatto temp = (Contatto) rubricaList.getSelectionModel().getSelectedItem(); //Carica il contatto selezionato dalla tabella
                
        if(temp == null) //controllo che il contatto non sia null
            return;
        
        contattoPane.setVisible(true); //rendo visibile il pannello del contatto

        //carico il nodo Parent dal file fxml del contatto
        FXMLLoader loader = App.getFXML("Contatto"); 
        StackPane contactPane = loader.load();

        //creo e inizializzo il controller
        ContattoController controller = loader.getController();
        controller.setController(temp, rubricaPointer, rubricaList);

        //Ripulisco il pannello del contatto e visualizzo l'interfaccia del contatto
        contattoPane.getChildren().clear();
        contattoPane.getChildren().add(contactPane);
  
    }

    /**
     * @brief Esporta tutti i contatti della Rubrica all'interno di un file.
     * 
     * @param event evento che attiva l'esportazione
     * 
     * @throws IOException
     */
    @FXML
    private void exportRubrica(javafx.event.ActionEvent event) throws IOException {
    
        if(rubricaPointer.getContactList().isEmpty()){ //controllo che la rubrica non sia vuota
            Avviso.info("Avviso","Rubrica Vuota","Non è possibile esportare una rubrica vuota");
            return;
        }
        //creo un oggetto di tipo FileChooser
         FileChooser fileChooser = new FileChooser();

        //indico il tipo di estensione con la quale esportare la rubrica
         FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv");
         fileChooser.getExtensionFilters().add(filter);

         //mostro a schermo l'interfaccia di esportazione del file
         File file = fileChooser.showSaveDialog(null); 

         //controllo che il file non sia null
         if(file != null){


             rubricaPointer.esportaRubrica(file.getAbsolutePath()); //esporto la rubrica usando come nome il path completo

             Avviso.info("Avviso", null,"La rubrica è stata esportata con successo");


         }else{

             System.out.println("Esportazione annullata");

         }
    }

    /**
     * @brief Importa contatti nella Rubrica da un file.
     * 
     * @param event evento che attiva l'importazione
     * 
     * @throws IOException
     */
    @FXML
    private void importRubrica(javafx.event.ActionEvent event) throws IOException {
    
    FileChooser fileChooser = new FileChooser(); //creo l'oggetto FileChooser
        
    //indico le estensioni accettabili
    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv"); 
    fileChooser.getExtensionFilters().add(filter);


    //mostro a schermo la finestra di scelta del file
    File file = fileChooser.showOpenDialog(null);
        //controllo che il file non sia null
        if (file != null) { 
            
            try {
                
       //creo la rubrica da importare
        Rubrica nuovaRubrica = rubricaPointer.importaRubrica(file.getAbsolutePath());
        
        if(nuovaRubrica.getContactList().isEmpty()){//controllo che il file contenga una rubrica non vuota
            Avviso.info("Avviso", null, "Il file scelto contiene una rubrica vuota");
            return;
        }
        //indico la nuova rubrica come quella principale
        rubricaPointer = nuovaRubrica;

        //lego la tabella alla nuova lista di contatti
        rubricaList.setItems(rubricaPointer.getContactList());

            
        Avviso.info("Avviso", null, "La rubrica è stata importata con successo");
    } catch (IOException e) {
        
        System.err.println("Errore durante l'importazione del file: " + e.getMessage());
     
    }

        } else {
    
                System.out.println("Selezione del file annullata.");
            }
    
     
    }

    
/**
 * @brief Chiude la finestra attualmente visualizzata.
 * 
 * Questo metodo, in seguito al click sull'apposito pulsante, recupera la finestra 
 * associata alla scena corrente e la chiude utilizzando il metodo close() della classe Stage.
 * 
 * @param event L'evento che attiva la chiusura della schermata corrente.
 *              
 */
    @FXML
    private void goBack(javafx.event.ActionEvent event) {
        
        //get della finestra di visualizzazione, con conseguente casting della finestra come Stage per invocare il metodo di chiusura
      ((javafx.stage.Stage) exitButton.getScene().getWindow()).close();

    }
    
}