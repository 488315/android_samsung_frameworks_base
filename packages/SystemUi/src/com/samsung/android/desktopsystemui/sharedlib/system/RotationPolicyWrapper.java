package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import com.android.internal.view.RotationPolicy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class RotationPolicyWrapper {
    private static final RotationPolicyWrapper sInstance = new RotationPolicyWrapper();

    private RotationPolicyWrapper() {
    }

    public static RotationPolicyWrapper getInstance() {
        return sInstance;
    }

    public void setRotationLock(boolean z) {
        RotationPolicy.setRotationLock(AppGlobals.getInitialApplication(), z);
    }
}
