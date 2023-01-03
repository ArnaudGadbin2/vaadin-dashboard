package com.example.demo;

import com.example.demo.security.SecurityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PermitAll
@Route(value= "/modify", layout=MainLayout.class)
public class ModifyView extends VerticalLayout {

    public ModifyView() {
        final SecurityService securityService = new SecurityService();

        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("New Widget");

        ComboBox<String> comboBox = new ComboBox<>("Widget Type");
        comboBox.setItems("Clock", "Weather", "Gender", "Steam");
        dialog.add(comboBox);

        TextField timer = new TextField("Timer(seconds)");
        dialog.add(timer);

        TextField arg = new TextField("Modifier");
        dialog.add(arg);

        Button saveButton = new Button("Confirm", e -> MainLayout.addWidget(comboBox.getValue(), Integer.parseInt(timer.getValue()), arg.getValue()));
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);

        Button button = new Button("New Widget", e -> dialog.open());

        add(dialog, button);
    }
}