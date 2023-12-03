package com.mobdeve.s16.paguio.anthony.sakuravn;

public class Dialogue {

    // I'll do this muna cuz not sure how to edit the GameEngine

    private String speakerTxt;
    private String contentTxt;

    public Dialogue(String speakerTxt, String contentTxt) {
        this.speakerTxt = speakerTxt;
        this.contentTxt = contentTxt;
    }
    public String getSpeakerTxt() {
        return speakerTxt;
    }
    public String getContentTxt() {
        return contentTxt;
    }
}
