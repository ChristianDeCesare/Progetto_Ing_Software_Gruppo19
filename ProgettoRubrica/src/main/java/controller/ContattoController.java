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

//Tutti i metodi sono documentati, (anche quelli privati) per fornire una breve
//spiegazione del loro utilizzo
import gestioneRubrica.Avviso;
import gestioneRubrica.Contatto;
import gestioneRubrica.Rubrica;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;


public class ContattoController implements Initializable {

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
        //inizializzazione stringhe nei campi di testo
        nameField.setText("");
        surnameField.setText("");
        number1Field.setText("");
        number2Field.setText("");
        number3Field.setText("");
        email1Field.setText("");
        email2Field.setText("");
        email3Field.setText("");

        //inizializzazione puntatore contatto e rubrica
        contactPointer = null;
        rubricaPointer = null;

        //creazione binding tra pulsante conferma e campi di testo nome e cognome
        confirmButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> (nameField.getText().isEmpty() && surnameField.getText().isEmpty()), nameField.textProperty(), surnameField.textProperty()));

        //gestione eventi da tastiera
        contactPane.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ENTER) //gestione evento tasto ENTER
                confirmButton.fire(); // Simula un click sul bottone confirm

            else if(event.getCode() == javafx.scene.input.KeyCode.ESCAPE && exitButton.isVisible())  //gestione evento tasto ESCAPE
                exitButton.fire(); // Simula un click sul bottone exit
            });
    
    }
    
    
    /**
     * @brief Imposta il controller con i riferimenti e inizializza i componenti.
     *
     * Questo metodo permette di configurare il controller e i suoi componenti
     * per il funzionamento corretto dell'interfaccia utente.
     * 
     *  @param r il riferimento alla rubrica.
     * 
     * @pre r non deve essere null.
     * 
     * @post Il controller conterrà il riferimento alla rubrica su cui lavorare.
     */
    public void setController(Rubrica r) {
       
        //salvataggio puntatore alla rubrica
    this.rubricaPointer = r;
    
    //definizione tipo di controller
    typeController = false;
    
    //reso il bottone di modifica invisibile
    modifyButton.setVisible(false);
    
    Platform.runLater(() -> { 
        nameField.requestFocus();   //Cambia il focus all'apertura della schermata
        nameField.positionCaret(0); //posizione il cursore all'inizio del textField
        });
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
     * @post il controller conterrà le informazioni del contatto selezionato
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
        
        //invocato metodo disableModify con attributo "true"
        disableModify(true);
        
        //rendo invisibile il bottone di uscita
        exitButton.setVisible(false);
        
        
        
    }

    /**
     * @brief Gestisce l'interazione con i campi di testo
     *
     * Il metodo gestisce l'interazione con i campi di testo in modo che si renda
     * impossibile la loro modifica
     * 
     * @param disable Permette di definire l'abilitazione o la disabilitazione dei campi:
     *                -true: disabilita
     *                -false: abilita
     *        
     */
    private void disableModify(boolean disable) {
       
        this.nameField.setEditable(!disable);
        this.surnameField.setEditable(!disable);
        this.number1Field.setEditable(!disable);
        this.number2Field.setEditable(!disable);
        this.number3Field.setEditable(!disable);
        this.email1Field.setEditable(!disable);
        this.email2Field.setEditable(!disable);
        this.email3Field.setEditable(!disable);
        this.modifyButton.setVisible(disable);
        this.confirmButton.setVisible(!disable);

    }
    
    /**
     * @brief Controllo numero di telefono
     * 
     * Questo metodo permette il controllo di una stringa per accertarsi sia un numero di telefono
     * 
     * @param number La stringa da controllare
     * @return Il risultato del controllo, [@code false} in caso negativo, ovvero se la stringa non è un numero di telefono; {@code true} altrimenti
     */
    private boolean numberControl(String number){
        
        
          if(number.isEmpty()){ //controllo che la stringa sia vuota
            return true;
        }
        
        
        if(number.length() != 10) //controllo della lunghezza della stringa
            return false;
        
        
        for(int i = 0 ; i < 10 ; i++){ //controllo che i caratteri della stringa siano numeri
            if(!Character.isDigit(number.charAt(i)))
                return false;
        }
        
        return true;
    
    
    }
    
    /**
     * @brief Controllo nominativo inserito
     * 
     * Questo metodo permette di controllare che le stringhe nome e cognome siano un nominativo valido, ovvero contenenti solo lettere
     * 
     * @param name La stringa nome da controllare
     * @param surname La stringa cognome da controllare
     * 
     * @pre Almeno una tra le stringhe passate non deve essere vuota
     * @pre Le stringhe non devono essere null
     * 
     * @post Ritorno risultato del controllo
     * 
     * @return Il risultato del controllo
     */
    private boolean nominativeControl(String name, String surname){
        if (!name.isEmpty() && !Character.isLetter(name.charAt(0))) //controllo primo carattere del nome
            return false;
        return !( !surname.isEmpty() && !Character.isLetter(surname.charAt(0)) ); //controllo primo carattere del cognome
    }

    /**
     * @brief Conferma le modifiche al contatto.
     *
     *        Questo metodo viene invocato quando l'utente preme il bottone di conferma.
     *        Le modifiche ai dati del contatto vengono validate e salvate.
     *
     * @param c l'evento che ha generato l'azione di conferma.
     */
    @FXML
    private void confirm(javafx.event.ActionEvent c) {
        if(!typeController) //controllo tipo di controller
            confAdd();
        else
            confMod();
    }
    
   /*
    *  @brief Gestisce l'aggiunta di un nuovo contatto alla rubrica.
    * 
    * Questo metodo verifica la validità dei dati inseriti dall'utente, 
    * controllando i campi nominativi e recapiti. Se i controlli hanno esito positivo, 
    * crea un nuovo oggetto `Contatto` e lo aggiunge alla rubrica.
    * In caso di errore nei controlli o durante l'aggiunta, vengono mostrati messaggi di avviso.
    */
    private void confAdd(){
      
        boolean flag = true;
           

        
        if (!nominativeControl(nameField.getText(), surnameField.getText())) {
            Avviso.errore("Errore","Errore Nominativi","Nominativi inseriti erroneamente");
            flag = false;
        }

                
    
        if (!(numberControl(number1Field.getText())
        && numberControl(number2Field.getText())
        && numberControl(number3Field.getText())
        && mailControl(email1Field.getText())
        && mailControl(email2Field.getText())
        && mailControl(email3Field.getText()))){
            Avviso.errore("Errore","Errore Recapiti","Recapiti inseriti erroneamente");
            flag = false;
        }

        if (flag) {
            contactPointer = new Contatto();
            contactPointer.setNome(nameField.getText());
            contactPointer.setCognome(surnameField.getText());
            contactPointer.setEmail1(email1Field.getText());
            contactPointer.setEmail2(email2Field.getText());
            contactPointer.setEmail3(email3Field.getText());
            contactPointer.setNumero1(number1Field.getText());
            contactPointer.setNumero2(number2Field.getText());
            contactPointer.setNumero3(number3Field.getText());
                        
            switch(rubricaPointer.aggiungiContatto(contactPointer)){
                case 0: goBack(null); break;
                case 1: Avviso.info("Attenzione", "Contatto Già Esistente", "Il nominativo inserito risulta già presente in rubrica"); break;
                case 2: Avviso.errore("Errore", "Errore Aggiunta", "Non è stato possibile aggiungere il contatto alla rubrica"); break;
                default: Avviso.errore("Errore" , "Valore Di Ritorno Non Riconosciuto", "Il valore di controllo non è stato riconosciuto");
            }
        }    
    }
    /**
     * @brief Abilita modifiche al contatto
     *
     * Questo metodo viene invocato quando l'utente preme il bottone di modifica durante la visualizzazione del contatto.
     * I campi di testo venogno abilitati alla modifica.
     *
     * @param c L'evento che ha generato l'azione di conferma.
     */
    @FXML
    private void modify(javafx.event.ActionEvent c) {
        
        //viene invocato il metodo disable modify con attributo "false"
        disableModify(false);
        
          Platform.runLater(() -> { //Cambia il focus all'apertura della schermata
        nameField.requestFocus();
        nameField.positionCaret(nameField.getText().length()); //posizione il cursore alla fine del testo del textField
        });
          
    }

    private void confMod(){  
        //controllo che il puntatore al contatto non sia null   
        if (contactPointer == null) { 
            System.out.println("Nessun contatto selezionato.");
            return;
        }   
        
        //controllo nominativi
        if (!nominativeControl(nameField.getText(), surnameField.getText())) { 
                Avviso.errore("Errore", "Errore Nominativi","Nominativi modificati erroneamente");
                return;
            }
        
        //controllo recapiti
        if (!(numberControl(number1Field.getText()) 
            && numberControl(number2Field.getText())
            && numberControl(number3Field.getText())
            && mailControl(email1Field.getText())
            && mailControl(email2Field.getText())
            && mailControl(email3Field.getText()))){
    
            Avviso.errore("Errore", "Errore Recapiti","Recapiti modificati erroneamente");
            return;
        }
            
        //creo contatto temporaneo per controllo omonimia
        Contatto temp = new Contatto();
        temp.setNome(nameField.getText());
        temp.setCognome(surnameField.getText());

        //controllo la modifica effettuata
        if(!(contactPointer.getNome().equals(nameField.getText()) && contactPointer.getCognome().equals(surnameField.getText()))
            && rubricaPointer.getContactList().contains(temp)){
            Avviso.info("Avviso", "Omonimia", "Contatto già esistente in rubrica");
            return;
        }       

        //modifico contatto e riordino rubrica
        contactPointer.setNome(nameField.getText());
        contactPointer.setCognome(surnameField.getText());
        contactPointer.setEmail1(email1Field.getText());
        contactPointer.setEmail2(email2Field.getText());
        contactPointer.setEmail3(email3Field.getText());
        contactPointer.setNumero1(number1Field.getText());
        contactPointer.setNumero2(number2Field.getText());
        contactPointer.setNumero3(number3Field.getText());

        Collections.sort(rubricaPointer.getContactList());
        //disabilito tutti i campi una volta assegnati i loro valori
        disableModify(true);


       tablePointer.refresh();    
    

    }
    
        /**
     * @brief Chiude l'interfaccia del controller
     *
     * Questo metodo viene invocato quando l'utente preme il bottone per uscire dall'interfaccia.
     * Come conseguenza l'interfaccia viene chiusa.
     *
     * @param event L'evento che ha generato l'azione di chiusura.
     */
    @FXML
    private void goBack(javafx.event.ActionEvent event) {
        
        //get della finestra di visualizzazione, con conseguente casting della finestra come Stage per invocare il metodo di chiusura
      ((javafx.stage.Stage) exitButton.getScene().getWindow()).close();

    }
    
    /**
     * @brief Controllo indirizzo posta elettronica
     * 
     * Questo metodo permette di controllare che la stringa passata sia concorde con le regole generali
     * di un indirizzo mail
     * 
     * @param mail La stringa da controllare
     * @return Il risultato del controllo
     */
    private boolean mailControl(String mail){
        if (mail.isEmpty()) //controllo che la stringa sia vuota
            return true;
        
        //genero una stringa contenente le regole generali di un indirizzo e-mail
        String emailRegex = "^[a-zA-Z-.0-9]+@[a-z]+[.]+[a-zA-Z]{2,}$"; 
       
        //creo un oggetto di tipo Pattern per rendere utilizzabile e confrontabile la stringa emailRegex
        Pattern pattern = Pattern.compile(emailRegex);
      
        //controllo che la stringa segua le regole definite in emailRegex
        return pattern.matcher(mail).matches();
    }
}
