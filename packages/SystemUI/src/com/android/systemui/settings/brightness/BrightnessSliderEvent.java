package com.android.systemui.settings.brightness;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public enum BrightnessSliderEvent implements UiEventLogger.UiEventEnum {
    BRIGHTNESS_SLIDER_STARTED_TRACKING_TOUCH(1472),
    BRIGHTNESS_SLIDER_STOPPED_TRACKING_TOUCH(1473);

    private final int mId;

    BrightnessSliderEvent(int i) {
        this.mId = i;
    }

    public final int getId() {
        return this.mId;
    }
}
