package com.android.systemui.biometrics.ui.binder;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.biometrics.shared.model.BiometricModalities;

public abstract class BiometricViewBinderKt {
    public static final String access$asDefaultHelpMessage(BiometricModalities biometricModalities, Context context) {
        return biometricModalities.getHasFingerprint() ? context.getString(R.string.fingerprint_dialog_touch_sensor) : "";
    }
}
