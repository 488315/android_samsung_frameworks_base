package com.android.server.biometrics.sensors.fingerprint;

import android.hardware.biometrics.fingerprint.PointerContext;

public interface Udfps {
    void onPointerDown(PointerContext pointerContext);

    void onPointerUp(PointerContext pointerContext);

    void onUdfpsUiEvent(int i);

    void setIgnoreDisplayTouches(boolean z);
}
