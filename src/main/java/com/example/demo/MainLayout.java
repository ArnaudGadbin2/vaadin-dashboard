package com.example.demo;

import com.example.demo.widget.Widget;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import java.util.ArrayList;


public class MainLayout extends AppLayout {

    private static ArrayList<Widget> widgets = new ArrayList<>();

    public static void addWidget(String name, int timer, String arg) {
        widgets.add(new Widget(name, timer, arg));
    }

    public static ArrayList<Widget> getWidgets() {
        return widgets;
    }

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Dashboard");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink MainLink = new RouterLink("Dashboard", MainView.class);
        RouterLink ModifyLink = new RouterLink("Modify", ModifyView.class);
        MainLink.setHighlightCondition(HighlightConditions.sameLocation());
        MainLink.add(VaadinIcon.HOME.create());
        ModifyLink.setHighlightCondition(HighlightConditions.sameLocation());
        ModifyLink.add(VaadinIcon.COG.create());

        addToDrawer(new VerticalLayout(
                MainLink,
                ModifyLink
        ));
    }
}