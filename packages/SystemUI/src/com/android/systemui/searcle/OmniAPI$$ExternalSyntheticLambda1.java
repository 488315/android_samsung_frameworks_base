package com.android.systemui.searcle;

import android.util.Log;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
