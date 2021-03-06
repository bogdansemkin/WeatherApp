package com.bwap.weatherapp.WeatherApp.views;

import com.bwap.weatherapp.WeatherApp.controller.WeatherService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import java.util.ArrayList;

@SpringUI(path = "")
public class MainView extends UI {

    @Autowired
    private WeatherService weatherService;
    private HorizontalLayout timelocation;
    private HorizontalLayout textTimeLocation;
    private VerticalLayout mainLayout;
    private NativeSelect<String> unitSelect;
    private TextField cityTextField;
    private Button searchButton;
    private HorizontalLayout dashboard;
    private Label location;
    private Label currentTemp;
    private HorizontalLayout mainDescriptionLayout;
    private Label weatherDescription;
    private Label maxWeather;
    private Label minWeather;
    private Label Humidity;
    private Label Pressure;
    private Label Wind;
    private Label FeelsLike;
    private Image iconImage;
    private Label timeLabel;
    private Label textTimeTabel;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        mainLayout();
        setHeader();
      //  setLogo();
        setForm();
        timeLocation();
        timeTextLocation();
        DashboardTitle();
        dashboardDetails();
        searchButton.addClickListener(clickEvent -> {
            if(!cityTextField.getValue().equals("")){
                try {
                    updateUI();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Notification.show("Please Enter the City name");
            }
        });
    }

    private void setForm() {
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        formLayout.setSpacing(true);
        formLayout.setMargin(true);
        //Section Component
        unitSelect = new NativeSelect<>();
        ArrayList<String> items = new ArrayList<>();
        items.add("C");
        items.add("F");

        unitSelect.setItems(items);
        unitSelect.setValue(items.get(0));

        formLayout.addComponent(unitSelect);

        //cityTextField
        cityTextField = new TextField();
        cityTextField.setWidth("80%");
        formLayout.addComponent(cityTextField);

        //Search Button
        searchButton = new Button();
        searchButton.setIcon(VaadinIcons.SEARCH);
        formLayout.addComponent(searchButton);

        mainLayout.addComponents(formLayout);
    }

    private void timeTextLocation(){
        textTimeLocation = new HorizontalLayout();
        textTimeLocation.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        //text time label
        textTimeTabel = new Label("Local time");
        textTimeTabel.addStyleName(ValoTheme.LABEL_H2);
        textTimeTabel.addStyleName(ValoTheme.LABEL_LIGHT);
        textTimeLocation.addComponents(textTimeTabel);
    }

    private void timeLocation(){
      
        timelocation = new HorizontalLayout();
        timelocation.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        //time label

        timeLabel = new Label("18:57:02");
        timeLabel.addStyleName(ValoTheme.LABEL_H1);
        timeLabel.addStyleName(ValoTheme.LABEL_BOLD);

        timelocation.addComponents(timeLabel);
    }

    private void DashboardTitle(){

        dashboard = new HorizontalLayout();
        dashboard.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);


        //city location
        location = new Label("Currently in Moscow");
        location.addStyleName(ValoTheme.LABEL_H2);
        location.addStyleName(ValoTheme.LABEL_LIGHT);

        //current TEMP
        currentTemp = new Label("10C");
        currentTemp.setStyleName(ValoTheme.LABEL_BOLD);
        currentTemp.setStyleName(ValoTheme.LABEL_H1);

        dashboard.addComponents(location, iconImage, currentTemp);

    }

    private void dashboardDetails(){
        mainDescriptionLayout = new HorizontalLayout();
        mainDescriptionLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //description layout
        VerticalLayout descriptionLayout = new VerticalLayout();
        descriptionLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //Weather description
        weatherDescription = new Label("Description : Clear Sky");
        weatherDescription.setStyleName(ValoTheme.LABEL_SUCCESS);
        descriptionLayout.addComponents(weatherDescription);

        //Min Weather
        minWeather = new Label("Min: 53");
        descriptionLayout.addComponents(minWeather);
        //minWeather.setStyleName(ValoTheme.);

        //Max Weather
        maxWeather = new Label("Max: 153");
        descriptionLayout.addComponents(maxWeather);

        VerticalLayout pressureLayout = new VerticalLayout();
        pressureLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Pressure = new Label("Pressure: 213Pa");
        pressureLayout.addComponents(Pressure);

        Humidity = new Label("Humidity: 213Pa");
        pressureLayout.addComponents(Humidity);

        Wind = new Label("Wind: 213Pa");
        pressureLayout.addComponents(Wind);

        FeelsLike = new Label("FeelsLike: 213Pa");
        pressureLayout.addComponents(FeelsLike);

        //mainLayout.addComponents(mainDescriptionLayout, pressureLayout);
        mainDescriptionLayout.addComponents(descriptionLayout, pressureLayout);
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
        iconImage = new Image();
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

        mainLayout.addComponents(header);
    }
    private void updateUI() throws JSONException {
        String city = cityTextField.getValue();
        String defaultUnit;
        weatherService.setCityName(city);
        if(unitSelect.getValue().equals("F")){
            weatherService.setUnit("imperials");
            unitSelect.setValue("F");
            defaultUnit = "\u00b0"+ "F";
        } else {
            weatherService.setUnit("metric");
            defaultUnit = "\u00b0" + "C";
            unitSelect.setValue("C");

        }

        location.setValue("Currently in " + city);
        JSONObject mainObject = weatherService.returnMain();
        int temp = mainObject.getInt("temp");
        currentTemp.setValue(temp + defaultUnit);

        //getting Icon from API
        String iconCode = null;
        String weatherDescriptionNew = null;
        JSONArray jsonArray = weatherService.returnWeatherArray();
        for ( int i = 0; i < jsonArray.length(); i ++){
            JSONObject weatherObject = jsonArray.getJSONObject(i);
            iconCode = weatherObject.getString("icon");
            weatherDescriptionNew = weatherObject.getString("description");

        }

        iconImage.setSource(new ExternalResource("https://openweathermap.org/img/wn/"+iconCode+"@2x.png"));

        weatherDescription.setValue("Description: " + weatherDescriptionNew);
        minWeather.setValue("Min Temp: " + weatherService.returnMain().getInt("temp_min") + unitSelect.getValue());
        maxWeather.setValue("Max Temp: " + weatherService.returnMain().getInt("temp_max") + unitSelect.getValue());
        Pressure.setValue("Pressure: " +weatherService.returnMain().getInt("pressure"));
        Humidity.setValue("Humadity: " + weatherService.returnMain().get("humidity"));
        Wind.setValue("Wind: " +weatherService.returnWind().getInt("speed"));
        FeelsLike.setValue("Feels like: " + weatherService.returnMain().getDouble("feels_like"));



        mainLayout.addComponents(dashboard,textTimeLocation,timelocation,mainDescriptionLayout);
    }
}
