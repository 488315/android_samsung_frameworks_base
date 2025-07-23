package com.android.internal.hidden_from_bootclasspath.android.provider.flags;

import android.os.flagging.AconfigPackage;
import android.util.Log;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    private static final String TAG = "FeatureFlagsImplExport";
    private static boolean deviceConfigWritableNamespacesApi = false;
    private static boolean dumpImprovements = false;
    private static volatile boolean isCached = false;
    private static boolean mmdDeviceConfig = false;
    private static boolean newStoragePublicApi = false;
    private static boolean newStorageWriterSystemApi = false;
    private static boolean stageFlagsForBuild = false;

    private void init() {
        try {
            AconfigPackage load = AconfigPackage.load("android.provider.flags");
            dumpImprovements = load.getBooleanFlagValue("dump_improvements", false);
            mmdDeviceConfig = load.getBooleanFlagValue("mmd_device_config", false);
            newStoragePublicApi = load.getBooleanFlagValue("new_storage_public_api", false);
            newStorageWriterSystemApi = load.getBooleanFlagValue("new_storage_writer_system_api", false);
            stageFlagsForBuild = load.getBooleanFlagValue("stage_flags_for_build", false);
            deviceConfigWritableNamespacesApi = load.getBooleanFlagValue("device_config_writable_namespaces_api", false);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } catch (LinkageError e2) {
            Log.w(TAG, e2.toString());
        }
        isCached = true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.flags.FeatureFlags
    public boolean deviceConfigWritableNamespacesApi() {
        if (!isCached) {
            init();
        }
        return deviceConfigWritableNamespacesApi;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.flags.FeatureFlags
    public boolean dumpImprovements() {
        if (!isCached) {
            init();
        }
        return dumpImprovements;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.flags.FeatureFlags
    public boolean mmdDeviceConfig() {
        if (!isCached) {
            init();
        }
        return mmdDeviceConfig;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.flags.FeatureFlags
    public boolean newStoragePublicApi() {
        if (!isCached) {
            init();
        }
        return newStoragePublicApi;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.flags.FeatureFlags
    public boolean newStorageWriterSystemApi() {
        if (!isCached) {
            init();
        }
        return newStorageWriterSystemApi;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.provider.flags.FeatureFlags
    public boolean stageFlagsForBuild() {
        if (!isCached) {
            init();
        }
        return stageFlagsForBuild;
    }
}
