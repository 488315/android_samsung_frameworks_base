package com.android.systemui.searcle;

import android.util.Log;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class OmniAPI$$ExternalSyntheticLambda1 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        try {
            boolean z = true;
            if (OmniAPI.mContext.getPackageManager().getApplicationEnabledSetting("com.google.android.googlequicksearchbox") > 1) {
                z = false;
            }
            OmniAPI.mIsOmniPackageEnabled = z;
        } catch (IllegalArgumentException unused) {
            OmniAPI.mIsOmniPackageEnabled = false;
            Log.w("OmniAPI", "requestUpdateOmniPackageInfo getApplicationEnabledSetting IllegalArgumentException");
        }
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("requestUpdateOmniPackageInfo mIsOmniPackageEnabled = "), OmniAPI.mIsOmniPackageEnabled, "OmniAPI");
    }
}
