package com.android.server.dreams;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
