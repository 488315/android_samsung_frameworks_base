package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.knox.EnterpriseDeviceManager;

public final class KnoxEDMWrapper {
    public final EnterpriseDeviceManager mEDM;

    public KnoxEDMWrapper(Context context) {
        this.mEDM = EnterpriseDeviceManager.getInstance(context);
    }
}
