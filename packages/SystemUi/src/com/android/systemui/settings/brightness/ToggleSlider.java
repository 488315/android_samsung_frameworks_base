package com.android.systemui.settings.brightness;

import android.view.MotionEvent;
import com.android.settingslib.RestrictedLockUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ToggleSlider {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Listener {
    }

    String getTag();

    int getValue();

    void initBrightnessIconResources();

    boolean mirrorTouchEvent(MotionEvent motionEvent);

    void setEnforcedAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin);

    void setMax(int i);

    void setOnChangedListener(BrightnessController brightnessController);

    void setValue(int i);

    void updateDualSeekBar();

    void updateHighBrightnessModeEnter(boolean z);

    void updateOutdoorMode(boolean z);

    void updateSystemBrightnessEnabled(boolean z);

    void updateUsingHighBrightnessDialog(boolean z);
}
