package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.knox.kiosk.KioskMode;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KioskModeWrapper {
    public final KioskMode mKioskMode;

    public KioskModeWrapper(Context context) {
        this.mKioskMode = KioskMode.getInstance(context);
    }
}
