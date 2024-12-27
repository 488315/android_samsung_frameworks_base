package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.knox.kiosk.KioskMode;

public final class KioskModeWrapper {
    public final KioskMode mKioskMode;

    public KioskModeWrapper(Context context) {
        this.mKioskMode = KioskMode.getInstance(context);
    }
}
