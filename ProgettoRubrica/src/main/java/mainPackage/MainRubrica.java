/**
 * @file MainRubrica
 * 
 * @brief Classe principale per l'applicazione in JavaFX.
 *
 *Questa classe rappresenta l'ingresso dell'applicazione JavaFX e carica
 *        l'interfaccia utente da un file FXML.
 */




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPackage;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainRubrica extends Application {

    /**
     * @brief Metodo principale di avvio dell'applicazione JavaFX.
     *
     *Questo metodo viene invocato automaticamente dal framework JavaFX 
     *        e si occupa di configurare e mostrare la finestra principale dell'applicazione.
     * 
     * @param stage La finestra principale dell'applicazione (Stage).
     * 
     * @throws Exception Se si verifica un errore durante il caricamento del file FXML.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("file.fxml"));
        
        // Creazione della scena con il layout caricato
        Scene scene = new Scene(root);
        
        // Configurazione dello stage principale
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @brief Metodo main per avviare l'applicazione.
     *
     *Questo metodo invoca il metodo launch di JavaFX per iniziare l'esecuzione
     *        dell'applicazione.
     * 
     * @param args Gli argomenti della riga di comando passati all'applicazione.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}