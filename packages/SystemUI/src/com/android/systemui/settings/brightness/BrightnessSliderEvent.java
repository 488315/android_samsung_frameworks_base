package com.android.systemui.settings.brightness;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
