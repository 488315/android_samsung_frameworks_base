package com.android.server.biometrics.sensors.fingerprint;

import android.hardware.biometrics.fingerprint.PointerContext;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface Udfps {
    void onPointerDown(PointerContext pointerContext);

    void onPointerUp(PointerContext pointerContext);

    void onUdfpsUiEvent(int i);

    void setIgnoreDisplayTouches(boolean z);
}
