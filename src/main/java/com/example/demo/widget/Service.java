package com.example.demo.widget;

import java.util.ArrayList;

public class Service {
    private ArrayList<Widget> widgets = new ArrayList<>();

    public Service(Widget...widgets) {
        for (Widget widget: widgets) {
            addWidget(widget);
        }
    }

    private String name;

    private boolean isSubscribed = false;

    public void addWidget(Widget widget) {
        widgets.add(widget);
    }

    public ArrayList<Widget> getWidgets() {
        return widgets;
    }

    public void removeWidget(Widget widget) {
        widgets.remove(widget);
    }

    public String getName() {
        return name;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }
}
