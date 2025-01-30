package com.android.app.motiontool;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
