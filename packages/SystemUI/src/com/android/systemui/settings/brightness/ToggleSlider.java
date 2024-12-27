package com.android.systemui.settings.brightness;

import android.view.MotionEvent;
import com.android.settingslib.RestrictedLockUtils;

public interface ToggleSlider {

    public interface Listener {
    }

    int getMax();

    int getValue();

    boolean mirrorTouchEvent(MotionEvent motionEvent);

    void setEnforcedAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin);

    void setMax(int i);

    void setOnChangedListener(Listener listener);

    void setValue(int i);
}
