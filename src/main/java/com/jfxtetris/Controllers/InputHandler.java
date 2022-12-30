package com.jfxtetris.Controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class InputHandler {

    BooleanProperty upPressed = new SimpleBooleanProperty();
    BooleanProperty downPressed = new SimpleBooleanProperty();
    BooleanProperty leftPressed = new SimpleBooleanProperty();
    BooleanProperty rightPressed = new SimpleBooleanProperty();
    BooleanProperty spacePressed = new SimpleBooleanProperty();
    BooleanProperty tPressed = new SimpleBooleanProperty();
    BooleanProperty bPressed = new SimpleBooleanProperty();
    BooleanProperty pPressed = new SimpleBooleanProperty();
    Scene referenceScene;

    public InputHandler(Scene ref){
        referenceScene = ref;

        ref.setOnKeyPressed(e-> {

            if(e.getCode() == KeyCode.UP){
                upPressed.set(true);
            }
            if(e.getCode() == KeyCode.DOWN){
                downPressed.set(true);
            }
            if(e.getCode() == KeyCode.LEFT){
                leftPressed.set(true);
            }
            if(e.getCode() == KeyCode.RIGHT){
                rightPressed.set(true);
            }
            if(e.getCode() == KeyCode.SPACE){
                spacePressed.set(true);
            }
            if(e.getCode() == KeyCode.T){
                tPressed.set(true);
            }
            if(e.getCode() == KeyCode.B){
                bPressed.set(true);
            }
            if(e.getCode() == KeyCode.P){
                pPressed.set(true);
            }

        });

        ref.setOnKeyReleased(e-> {

            if(e.getCode() == KeyCode.UP){
                upPressed.set(false);
            }
            if(e.getCode() == KeyCode.DOWN){
                downPressed.set(false);
            }
            if(e.getCode() == KeyCode.LEFT){
                leftPressed.set(false);
            }
            if(e.getCode() == KeyCode.RIGHT){
                rightPressed.set(false);
            }
            if(e.getCode() == KeyCode.SPACE){
                spacePressed.set(false);
            }
            if(e.getCode() == KeyCode.T){
                tPressed.set(false);
            }

            if(e.getCode() == KeyCode.B){
                bPressed.set(false);
            }
            if(e.getCode() == KeyCode.P){
                pPressed.set(false);
            }
        });

    }


}
