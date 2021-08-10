package com.bwap.weatherapp.WeatherApp.views;

import com.vaadin.server.ClassResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI(path = "")
public class MainView extends UI {

    private VerticalLayout mainLayout;
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        mainLayout();
        setHeader();
        setLogo();
    }

    private void setLogo() {
        HorizontalLayout logo = new HorizontalLayout();
        logo.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Image image = new Image("logo", new ClassResource("/logo.png"));
        logo.setWidth("240px");
        logo.setHeight("240px");
        logo.addComponent(image);
        mainLayout.addComponents(logo);
    }

    private void mainLayout() {
         mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(mainLayout);
    }
    private void setHeader(){
        HorizontalLayout header = new HorizontalLayout();
        header.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label title = new Label("Testing Spring on Weather APP by Donchenko B");
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_BOLD);
        title.addStyleName(ValoTheme.LABEL_COLORED);
        header.addComponent(title);

        mainLayout.addComponent(header);
    }
}
