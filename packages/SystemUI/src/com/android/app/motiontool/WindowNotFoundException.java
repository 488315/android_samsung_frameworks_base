package com.android.app.motiontool;

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
