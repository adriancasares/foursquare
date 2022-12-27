package com.adriancasares.foursquare.base.util;

import net.kyori.adventure.text.format.TextColor;

public class FSColor {

    public static final TextColor GRAY = TextColor.color(0x808080);
    public static final TextColor AQUA = TextColor.color(0x59D7FF);
    public static final TextColor GOLD = TextColor.color(0xFFC147);
    public static final TextColor PURPLE = TextColor.color(0xB57BFF);
    public static final TextColor PINK = TextColor.color(0xFF4ED8);

    private TextColor primary;
    private TextColor textAccent;
    private TextColor textColor;

    private FSColor(TextColor primary, TextColor textAccent, TextColor textColor) {
        this.primary = primary;
        this.textAccent = textAccent;
        this.textColor = textColor;
    }


    public static final FSColor SPECTATOR = new FSColor(GRAY, GRAY, TextColor.color(0xE7D9D4));
    public static final FSColor TEAM1 = new FSColor(AQUA, AQUA, TextColor.color(0xC1EAFF));
    public static final FSColor TEAM2 = new FSColor(GOLD, GOLD, TextColor.color(0xFFE9AC));
    public static final FSColor TEAM3 = new FSColor(PURPLE, PURPLE, TextColor.color(0xECD8FF));
    public static final FSColor TEAM4 = new FSColor(PINK, PINK, TextColor.color(0xFFC9DF));

    public TextColor getPrimary() {
        return primary;
    }

    public TextColor getTextAccent() {
        return textAccent;
    }

    public TextColor getTextColor() {
        return textColor;
    }
}
