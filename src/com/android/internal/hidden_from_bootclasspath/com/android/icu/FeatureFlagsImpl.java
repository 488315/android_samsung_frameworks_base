package com.android.internal.hidden_from_bootclasspath.com.android.icu;

import android.os.flagging.AconfigPackage;
import android.util.Log;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    private static final String TAG = "FeatureFlagsImplExport";
    private static boolean icu25q2Api = false;
    private static boolean icuVApi = false;
    private static volatile boolean isCached = false;
    private static boolean telephonyLookupMccExtension = false;

    private void init() {
        try {
            AconfigPackage load = AconfigPackage.load("com.android.icu");
            icu25q2Api = load.getBooleanFlagValue("icu_25q2_api", false);
            icuVApi = true;
            telephonyLookupMccExtension = load.getBooleanFlagValue("telephony_lookup_mcc_extension", false);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } catch (LinkageError e2) {
            Log.w(TAG, e2.toString());
        }
        isCached = true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.icu.FeatureFlags
    public boolean icu25q2Api() {
        if (!isCached) {
            init();
        }
        return icu25q2Api;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.icu.FeatureFlags
    public boolean icuVApi() {
        if (!isCached) {
            init();
        }
        return icuVApi;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.icu.FeatureFlags
    public boolean telephonyLookupMccExtension() {
        if (!isCached) {
            init();
        }
        return telephonyLookupMccExtension;
    }
}
