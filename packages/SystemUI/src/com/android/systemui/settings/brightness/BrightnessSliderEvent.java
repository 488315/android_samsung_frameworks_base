package com.android.systemui.settings.brightness;

import com.android.internal.logging.UiEventLogger;

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
