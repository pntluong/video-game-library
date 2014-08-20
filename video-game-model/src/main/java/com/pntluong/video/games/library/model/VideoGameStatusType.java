package com.pntluong.video.games.library.model;

/**
 * Created by ptluong on 1/06/2014.
 */
public enum VideoGameStatusType {
    COMPLETED("Completed"),
    PLAYING("Playing"),
    BACKLOG("Backlog");

    private final String value;

    VideoGameStatusType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
