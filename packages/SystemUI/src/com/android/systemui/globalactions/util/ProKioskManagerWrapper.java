package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.knox.custom.ProKioskManager;

public final class ProKioskManagerWrapper {
    public final LogWrapper mLogWrapper;
    public final ProKioskManager mProKioskManager = ProKioskManager.getInstance();
    public boolean mProKioskOptionShown = false;

    public ProKioskManagerWrapper(Context context, LogWrapper logWrapper) {
        this.mLogWrapper = logWrapper;
    }
}
