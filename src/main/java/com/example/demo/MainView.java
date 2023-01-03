package com.example.demo;

import com.example.demo.security.SecurityService;
import com.example.demo.widget.Widget;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * The main view contains a button and a click listener.
 */

@PermitAll
@Route(value= "", layout=MainLayout.class)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    ArrayList<Widget> widgets = MainLayout.getWidgets();

    final SecurityService securityService = new SecurityService();

    UI ui;

    Grid<Widget> grid = new Grid<>();

    @PostConstruct
    public void init() {
        ui = UI.getCurrent();
    }

    public void stopWidget(ExecutorService executor, Widget widget) {
        executor.shutdownNow();
        widgets.remove(widget);
        ui.getPage().reload();
    }

    public void runWidgets() {
        for (Widget widget : widgets) {
            ExecutorService executor = Executors.newFixedThreadPool(10);
            Dialog dialog = new Dialog();

            dialog.setHeaderTitle("Modify Widget");

            TextField arg = new TextField("Modifier");
            dialog.add(arg);

            Button saveButton = new Button("Confirm", e -> widget.setModifier(arg.getValue()));
            Button cancelButton = new Button("Cancel", e -> dialog.close());
            Button remove = new Button("Remove", e -> stopWidget(executor, widget));

            dialog.getFooter().add(cancelButton);
            dialog.getFooter().add(saveButton);
            dialog.getFooter().add(remove);

            Button button = new Button("Modify "+widget.getName()+ " "+widget.getModifier(), e -> dialog.open());

            executor.execute(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        widget.APICall();
                        ui.access(() -> grid.setItems(widgets));
                        try {
                            Thread.sleep(widget.getTimer());
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            ui.access(() -> add(dialog, button));
        }
    }

    public MainView() {
        init();
        grid.addColumn(Widget::getToShow);
        runWidgets();

        add(grid);
        add(new Button("Log out", e -> securityService.logout()));
    }
    private Button buildDeleteButton() {
        Button button = new Button("Remove Widget", e -> widgets.remove(0));
        return button;
    }
}
