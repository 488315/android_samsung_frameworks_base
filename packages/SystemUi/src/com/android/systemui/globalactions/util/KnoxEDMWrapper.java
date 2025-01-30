package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.knox.EnterpriseDeviceManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KnoxEDMWrapper {
    public final EnterpriseDeviceManager mEDM;

    public KnoxEDMWrapper(Context context) {
        this.mEDM = EnterpriseDeviceManager.getInstance(context);
    }
}
