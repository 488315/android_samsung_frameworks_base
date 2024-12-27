package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.knox.SemEnterpriseDeviceManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SemEnterpriseDeviceManagerWrapper {
    public final SemEnterpriseDeviceManager mSemEnterpriseDeviceManager;

    public SemEnterpriseDeviceManagerWrapper(Context context) {
        this.mSemEnterpriseDeviceManager = SemEnterpriseDeviceManager.getInstance(context);
    }
}
