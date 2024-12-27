package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.knox.SemEnterpriseDeviceManager;

public final class SemEnterpriseDeviceManagerWrapper {
    public final SemEnterpriseDeviceManager mSemEnterpriseDeviceManager;

    public SemEnterpriseDeviceManagerWrapper(Context context) {
        this.mSemEnterpriseDeviceManager = SemEnterpriseDeviceManager.getInstance(context);
    }
}
