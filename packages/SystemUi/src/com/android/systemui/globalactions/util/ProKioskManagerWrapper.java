package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.knox.custom.ProKioskManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ProKioskManagerWrapper {
    public final LogWrapper mLogWrapper;
    public final ProKioskManager mProKioskManager = ProKioskManager.getInstance();
    public boolean mProKioskOptionShown = false;

    public ProKioskManagerWrapper(Context context, LogWrapper logWrapper) {
        this.mLogWrapper = logWrapper;
    }
}
