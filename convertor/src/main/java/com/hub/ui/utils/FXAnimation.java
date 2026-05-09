package com.hub.ui.utils;

import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.util.Duration;

public class FXAnimation {
    public static void fadeIn(Parent node) {
        FadeTransition ft = new FadeTransition(Duration.millis(250), node);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }
}