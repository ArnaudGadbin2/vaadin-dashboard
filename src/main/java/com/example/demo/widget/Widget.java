package com.example.demo.widget;

import com.example.demo.rest.Clock;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

public class Widget <T> extends VerticalLayout{

    private final WebClient webClient = null;

    public String name;

    private int timer;

    private String modifier;

    private String toShow;


    public Widget(String name, int timer, String arg) {
        this.name = name;
        this.modifier = arg;
        this.timer = timer * 1000;
    }

    public String getName() {
        return name;
    }

    public String getToShow() {
        return toShow;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public int getTimer() {
        return timer;
    }

    public String getSteamGame(String game) {
        switch (game) {
            case "CSGO":
                return "730";
            case "Vampire Survivors":
                return "1794680";
            case "Dark Souls 3":
                return "374320";
            case "Mass Effect":
                return "1328670";
            case "PUBG":
                return "578080";
            case "Dota 2":
                return "570";
            case "Apex Legends":
                return "1172470";
            case "Warzone":
                return "1938090";
            case "GTA 5":
                return "271590";
            default:
                return "0";
        }
    }

    public void APICall() {
        switch (name) {
            case "Clock":
                WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri("https://timeapi.io/api/Time/current/zone?timeZone="+getModifier());
                Clock res = spec.retrieve().bodyToMono(Clock.class).block();
                toShow = getModifier() + ": " + res.getTime() + ":" + res.getSeconds() + "\n";
                break;
            case "Weather":
                WebClient.RequestHeadersSpec<?> weath =  WebClient.create().get().uri("https://api.openweathermap.org/data/2.5/weather?q="+getModifier()+",fr&APPID=9ba88132de7df76aabe5cf83054bf3e0");
                JSONObject wt = weath.retrieve().bodyToMono(JSONObject.class).block();
                LinkedHashMap map = (LinkedHashMap) wt.get("main");
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.FLOOR);
                Double temp = new Double(df.format((Double)(map.get("temp")) - 273.15));
                toShow = getModifier() + ": " + temp + "C°\n";
                break;
            case "Gender":
                WebClient.RequestHeadersSpec<?> crypt =  WebClient.create().get().uri("https://api.genderize.io/?name="+getModifier());
                JSONObject c = crypt.retrieve().bodyToMono(JSONObject.class).block();
                toShow = getModifier() + ": " + c.get("gender") + "\n";
            case "Steam":
                WebClient.RequestHeadersSpec<?> steam =  WebClient.create().get().uri("https://api.steampowered.com/ISteamUserStats/GetNumberOfCurrentPlayers/v1/?appid="+getSteamGame(getModifier()));
                JSONObject ssteam = steam.retrieve().bodyToMono(JSONObject.class).block();
                LinkedHashMap steamMain = (LinkedHashMap) ssteam.get("response");
                toShow = getModifier() + ": " + steamMain.get("player_count") + " players\n";
            default:
                break;
        }
    }
}
