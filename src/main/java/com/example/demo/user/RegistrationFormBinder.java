package com.example.demo.user;

import com.example.demo.security.SecurityConfig;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;


public class RegistrationFormBinder extends InMemoryUserDetailsManager{

    private RegistrationForm registrationForm;
    /**
     * Flag for disabling first run for password validation
     */
    private boolean enablePasswordValidation;

    public RegistrationFormBinder(RegistrationForm registrationForm) {
        this.registrationForm = registrationForm;
    }

    /**
     * Method to add the data binding and validation logics
     * to the registration form
     */
    public void addBindingAndValidation() {

        BeanValidationBinder<UserDetails> binder = new BeanValidationBinder<>(UserDetails.class);
        binder.bindInstanceFields(registrationForm);

        binder.forField(registrationForm.getPasswordField())
                .withValidator(this::passwordValidator).bind("password");

        // The second password field is not connected to the Binder, but we
        // want the binder to re-check the password validator when the field
        // value changes. The easiest way is just to do that manually.
        registrationForm.getPasswordConfirmField().addValueChangeListener(e -> {
            // The user has modified the second field, now we can validate and show errors.
            // See passwordValidator() for how this flag is used.
            enablePasswordValidation = true;

            binder.validate();
        });

        binder.setStatusLabel(registrationForm.getErrorMessageField());

        registrationForm.getSubmitButton().addClickListener(event -> {
            try {
                UserDetails userBean = new UserDetails();
                binder.writeBean(userBean);

                createUser(new User(userBean.getUsername(),
                        userBean.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
                ));
                User guy = (User) loadUserByUsername(userBean.getUsername());


                showSuccess(guy);
            } catch (ValidationException exception) {}
        });
    }

    /**
     * Method to validate that:
     * <p>
     * 1) Password is at least 8 characters long
     * <p>
     * 2) Values in both fields match each other
     */
    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {

        if (pass1 == null || pass1.length() < 4) {
            return ValidationResult.error("Password should be at least 4 characters long");
        }

        if (!enablePasswordValidation) {
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = registrationForm.getPasswordConfirmField().getValue();

        if (pass1 != null && pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }

    /**
     * We call this method when form submission has succeeded
     */
    private void showSuccess(User userBean) {
        Notification notification =
                Notification.show("Data saved, welcome " + userBean.getUsername() + " with "+userBean.getPassword());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        UI.getCurrent().getPage().setLocation("/login");
    }

}
