package com.android.systemui.biometrics.ui.binder;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.biometrics.shared.model.BiometricModalities;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class BiometricViewBinderKt {
    public static final String access$asDefaultHelpMessage(BiometricModalities biometricModalities, Context context) {
        return biometricModalities.getHasFingerprint() ? context.getString(R.string.fingerprint_dialog_touch_sensor) : "";
    }
}
