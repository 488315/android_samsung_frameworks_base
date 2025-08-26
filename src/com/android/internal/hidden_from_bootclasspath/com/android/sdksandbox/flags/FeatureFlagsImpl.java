package com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags;

import android.os.flagging.AconfigPackage;
import android.util.Log;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    private static final String TAG = "FeatureFlagsImplExport";
    private static volatile boolean isCached = false;
    private static boolean sandboxActivitySdkBasedContext = false;
    private static boolean sandboxClientImportanceListener = false;
    private static boolean sdkSandboxInstrumentationInfo = false;
    private static boolean sdkSandboxUidToAppUidApi = false;
    private static boolean selinuxInputSelector = false;
    private static boolean selinuxSdkSandboxAudit = false;

    private void init() {
        try {
            AconfigPackage aconfigPackageLoad = AconfigPackage.load("com.android.sdksandbox.flags");
            sandboxActivitySdkBasedContext = aconfigPackageLoad.getBooleanFlagValue("sandbox_activity_sdk_based_context", false);
            sandboxClientImportanceListener = aconfigPackageLoad.getBooleanFlagValue("sandbox_client_importance_listener", false);
            sdkSandboxInstrumentationInfo = aconfigPackageLoad.getBooleanFlagValue("sdk_sandbox_instrumentation_info", false);
            sdkSandboxUidToAppUidApi = true;
            selinuxInputSelector = aconfigPackageLoad.getBooleanFlagValue("selinux_input_selector", false);
            selinuxSdkSandboxAudit = aconfigPackageLoad.getBooleanFlagValue("selinux_sdk_sandbox_audit", false);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } catch (LinkageError e2) {
            Log.w(TAG, e2.toString());
        }
        isCached = true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.FeatureFlags
    public boolean sandboxActivitySdkBasedContext() {
        if (!isCached) {
            init();
        }
        return sandboxActivitySdkBasedContext;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.FeatureFlags
    public boolean sandboxClientImportanceListener() {
        if (!isCached) {
            init();
        }
        return sandboxClientImportanceListener;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.FeatureFlags
    public boolean sdkSandboxInstrumentationInfo() {
        if (!isCached) {
            init();
        }
        return sdkSandboxInstrumentationInfo;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.FeatureFlags
    public boolean sdkSandboxUidToAppUidApi() {
        if (!isCached) {
            init();
        }
        return sdkSandboxUidToAppUidApi;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.FeatureFlags
    public boolean selinuxInputSelector() {
        if (!isCached) {
            init();
        }
        return selinuxInputSelector;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.sdksandbox.flags.FeatureFlags
    public boolean selinuxSdkSandboxAudit() {
        if (!isCached) {
            init();
        }
        return selinuxSdkSandboxAudit;
    }
}
