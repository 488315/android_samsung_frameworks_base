package com.android.systemui.biometrics.p003ui.binder;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.model.BiometricModalities;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class BiometricViewBinderKt {
    public static final String access$asDefaultHelpMessage(BiometricModalities biometricModalities, Context context) {
        return biometricModalities.fingerprintProperties != null ? context.getString(R.string.fingerprint_dialog_touch_sensor) : "";
    }
}
