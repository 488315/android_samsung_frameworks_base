package com.android.systemui.biometrics.ui.binder;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.biometrics.shared.model.BiometricModalities;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BiometricViewBinderKt {
    public static final String access$asDefaultHelpMessage(BiometricModalities biometricModalities, Context context) {
        return biometricModalities.getHasFingerprint() ? context.getString(R.string.fingerprint_dialog_touch_sensor) : "";
    }
}
