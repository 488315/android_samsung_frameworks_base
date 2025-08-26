package com.android.internal.hidden_from_bootclasspath.com.android.art.flags;

import android.os.flagging.AconfigPackage;
import android.util.Log;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    private static final String TAG = "FeatureFlagsImplExport";
    private static boolean alwaysEnableProfileCode = false;
    private static boolean artServiceV3 = false;
    private static boolean executableMethodFileOffsets = false;
    private static boolean executableMethodFileOffsetsV2 = false;
    private static volatile boolean isCached = false;

    private void init() {
        try {
            AconfigPackage aconfigPackageLoad = AconfigPackage.load("com.android.art.flags");
            artServiceV3 = aconfigPackageLoad.getBooleanFlagValue("art_service_v3", false);
            alwaysEnableProfileCode = aconfigPackageLoad.getBooleanFlagValue("always_enable_profile_code", false);
            executableMethodFileOffsets = true;
            executableMethodFileOffsetsV2 = aconfigPackageLoad.getBooleanFlagValue("executable_method_file_offsets_v2", false);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } catch (LinkageError e2) {
            Log.w(TAG, e2.toString());
        }
        isCached = true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.art.flags.FeatureFlags
    public boolean alwaysEnableProfileCode() {
        if (!isCached) {
            init();
        }
        return alwaysEnableProfileCode;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.art.flags.FeatureFlags
    public boolean artServiceV3() {
        if (!isCached) {
            init();
        }
        return artServiceV3;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.art.flags.FeatureFlags
    public boolean executableMethodFileOffsets() {
        if (!isCached) {
            init();
        }
        return executableMethodFileOffsets;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.art.flags.FeatureFlags
    public boolean executableMethodFileOffsetsV2() {
        if (!isCached) {
            init();
        }
        return executableMethodFileOffsetsV2;
    }
}
