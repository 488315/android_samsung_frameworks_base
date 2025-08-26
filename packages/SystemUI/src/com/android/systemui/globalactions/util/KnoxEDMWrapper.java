package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.knox.EnterpriseDeviceManager;

/* loaded from: classes2.dex */
public class KnoxEDMWrapper {
    public final EnterpriseDeviceManager mEDM;

    public KnoxEDMWrapper(Context context) {
        this.mEDM = EnterpriseDeviceManager.getInstance(context);
    }
}
