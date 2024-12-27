package com.android.server.dreams;

import com.android.internal.logging.UiEventLogger;

public enum DreamUiEventLogger$DreamUiEventEnum implements UiEventLogger.UiEventEnum {
    DREAM_START("DREAM_START"),
    DREAM_STOP("DREAM_STOP");

    private final int mId;

    DreamUiEventLogger$DreamUiEventEnum(String str) {
        this.mId = r2;
    }

    public final int getId() {
        return this.mId;
    }
}
