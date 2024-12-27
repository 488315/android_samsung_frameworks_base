package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.knox.custom.ProKioskManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ProKioskManagerWrapper {
    public final LogWrapper mLogWrapper;
    public final ProKioskManager mProKioskManager = ProKioskManager.getInstance();
    public boolean mProKioskOptionShown = false;

    public ProKioskManagerWrapper(Context context, LogWrapper logWrapper) {
        this.mLogWrapper = logWrapper;
    }
}
