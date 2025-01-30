package com.android.systemui.util.wrapper;

import android.content.Context;
import android.os.Trace;
import com.android.internal.view.RotationPolicy;
import com.android.systemui.util.settings.SecureSettings;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RotationPolicyWrapperImpl implements RotationPolicyWrapper {
    public final Context context;
    public final SecureSettings secureSettings;

    public RotationPolicyWrapperImpl(Context context, SecureSettings secureSettings) {
        this.context = context;
        this.secureSettings = secureSettings;
    }

    public final void setRotationLock(boolean z) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        Context context = this.context;
        if (!isTagEnabled) {
            RotationPolicy.setRotationLock(context, z);
            return;
        }
        Trace.traceBegin(4096L, "RotationPolicyWrapperImpl#setRotationLock");
        try {
            RotationPolicy.setRotationLock(context, z);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }
}
