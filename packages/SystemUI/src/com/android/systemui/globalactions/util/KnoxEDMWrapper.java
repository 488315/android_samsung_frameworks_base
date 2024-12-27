package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.knox.EnterpriseDeviceManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KnoxEDMWrapper {
    public final EnterpriseDeviceManager mEDM;

    public KnoxEDMWrapper(Context context) {
        this.mEDM = EnterpriseDeviceManager.getInstance(context);
    }
}
