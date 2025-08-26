package com.android.internal.hidden_from_bootclasspath.com.android.org.conscrypt.flags;

import android.os.flagging.AconfigPackage;
import android.util.Log;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    private static final String TAG = "FeatureFlagsImplExport";
    private static boolean certificateTransparencyCheckservertrustedApi = false;
    private static volatile boolean isCached = false;
    private static boolean spake2plusApi = false;

    private void init() {
        try {
            AconfigPackage aconfigPackageLoad = AconfigPackage.load("com.android.org.conscrypt.flags");
            certificateTransparencyCheckservertrustedApi = aconfigPackageLoad.getBooleanFlagValue("certificate_transparency_checkservertrusted_api", false);
            spake2plusApi = aconfigPackageLoad.getBooleanFlagValue("spake2plus_api", false);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } catch (LinkageError e2) {
            Log.w(TAG, e2.toString());
        }
        isCached = true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.org.conscrypt.flags.FeatureFlags
    public boolean certificateTransparencyCheckservertrustedApi() {
        if (!isCached) {
            init();
        }
        return certificateTransparencyCheckservertrustedApi;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.org.conscrypt.flags.FeatureFlags
    public boolean spake2plusApi() {
        if (!isCached) {
            init();
        }
        return spake2plusApi;
    }
}
