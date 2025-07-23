package com.android.app.motiontool;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WindowNotFoundException extends Exception {
    private final String windowId;

    public WindowNotFoundException(String str) {
        this.windowId = str;
    }

    public final String getWindowId() {
        return this.windowId;
    }
}
