package com.example.chatsocket;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class ControllerModal {


    @FXML
    private Button unirmeGrupo;
    @FXML
    private Button crearGrupo;



    EchoServerMultihilo listaGrupos= new EchoServerMultihilo();

    @FXML
    public void initialize() {


        unirmeGrupo.setOnAction(e->{
            System.out.println("prueba");
        });

        crearGrupo.setOnAction(e->{
            System.out.println();

        });
    }




    public void crearGrupos (){

    }




}
