package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.knox.kiosk.KioskMode;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KioskModeWrapper {
    public final KioskMode mKioskMode;

    public KioskModeWrapper(Context context) {
        this.mKioskMode = KioskMode.getInstance(context);
    }
}
