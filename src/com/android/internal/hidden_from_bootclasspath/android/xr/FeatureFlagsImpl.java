package com.android.internal.hidden_from_bootclasspath.android.xr;

import android.os.flagging.AconfigPackage;
import android.util.Log;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    private static final String TAG = "FeatureFlagsImplExport";
    private static volatile boolean isCached = false;
    private static boolean xrManifestEntries = false;

    private void init() {
        try {
            xrManifestEntries = AconfigPackage.load("android.xr").getBooleanFlagValue("xr_manifest_entries", false);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } catch (LinkageError e2) {
            Log.w(TAG, e2.toString());
        }
        isCached = true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.xr.FeatureFlags
    public boolean xrManifestEntries() {
        if (!isCached) {
            init();
        }
        return xrManifestEntries;
    }
}
