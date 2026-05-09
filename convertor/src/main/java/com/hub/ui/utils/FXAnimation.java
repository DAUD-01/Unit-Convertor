package com.hub.ui.utils;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class FXAnimation {

    public static void fadeIn(Node node) {

        FadeTransition ft = new FadeTransition(
                Duration.millis(400),
                node);

        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
    }

    public static void slideIn(Node node) {

        TranslateTransition tt = new TranslateTransition(
                Duration.millis(350),
                node);

        node.setTranslateX(40);

        tt.setFromX(40);
        tt.setToX(0);

        tt.play();
    }
}