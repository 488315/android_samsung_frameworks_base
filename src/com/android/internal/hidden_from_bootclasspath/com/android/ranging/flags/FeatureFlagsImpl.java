package com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags;

import android.os.flagging.AconfigPackage;
import android.util.Log;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    private static final String TAG = "FeatureFlagsImplExport";
    private static volatile boolean isCached = false;
    private static boolean rangingCsEnabled = false;
    private static boolean rangingRttEnabled = false;
    private static boolean rangingStackEnabled = false;
    private static boolean rangingStackUpdates25q4 = false;

    private void init() {
        try {
            AconfigPackage aconfigPackageLoad = AconfigPackage.load("com.android.ranging.flags");
            rangingCsEnabled = true;
            rangingRttEnabled = aconfigPackageLoad.getBooleanFlagValue("ranging_rtt_enabled", false);
            rangingStackEnabled = true;
            rangingStackUpdates25q4 = aconfigPackageLoad.getBooleanFlagValue("ranging_stack_updates_25q4", false);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } catch (LinkageError e2) {
            Log.w(TAG, e2.toString());
        }
        isCached = true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags.FeatureFlags
    public boolean rangingCsEnabled() {
        if (!isCached) {
            init();
        }
        return rangingCsEnabled;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags.FeatureFlags
    public boolean rangingRttEnabled() {
        if (!isCached) {
            init();
        }
        return rangingRttEnabled;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags.FeatureFlags
    public boolean rangingStackEnabled() {
        if (!isCached) {
            init();
        }
        return rangingStackEnabled;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.ranging.flags.FeatureFlags
    public boolean rangingStackUpdates25q4() {
        if (!isCached) {
            init();
        }
        return rangingStackUpdates25q4;
    }
}
