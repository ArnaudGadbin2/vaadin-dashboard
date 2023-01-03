package com.example.demo;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@Push
@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
public class AppShell extends SpringBootServletInitializer implements AppShellConfigurator {
}
