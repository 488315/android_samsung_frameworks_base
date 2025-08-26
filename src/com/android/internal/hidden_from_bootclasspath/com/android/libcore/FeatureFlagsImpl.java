package com.android.internal.hidden_from_bootclasspath.com.android.libcore;

import android.os.flagging.AconfigPackage;
import android.util.Log;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    private static final String TAG = "FeatureFlagsImplExport";
    private static boolean appinfo = false;
    private static boolean hpkePublicApi = false;
    private static boolean hpkeVApis = false;
    private static volatile boolean isCached = false;
    private static boolean madviseApi = false;
    private static boolean nativeMetrics = false;
    private static boolean openjdk21Stringconcat = false;
    private static boolean openjdk21V1Apis = false;
    private static boolean openjdk21V2Apis = false;
    private static boolean postCleanupApis = false;
    private static boolean readOnlyDynamicCodeLoad = false;
    private static boolean vApis = false;

    private void init() {
        try {
            AconfigPackage aconfigPackageLoad = AconfigPackage.load("com.android.libcore");
            hpkePublicApi = aconfigPackageLoad.getBooleanFlagValue("hpke_public_api", false);
            hpkeVApis = true;
            madviseApi = aconfigPackageLoad.getBooleanFlagValue("madvise_api", false);
            nativeMetrics = aconfigPackageLoad.getBooleanFlagValue("native_metrics", false);
            openjdk21Stringconcat = aconfigPackageLoad.getBooleanFlagValue("openjdk21_stringconcat", false);
            openjdk21V1Apis = aconfigPackageLoad.getBooleanFlagValue("openjdk_21_v1_apis", false);
            openjdk21V2Apis = aconfigPackageLoad.getBooleanFlagValue("openjdk_21_v2_apis", false);
            postCleanupApis = aconfigPackageLoad.getBooleanFlagValue("post_cleanup_apis", false);
            readOnlyDynamicCodeLoad = aconfigPackageLoad.getBooleanFlagValue("read_only_dynamic_code_load", false);
            vApis = true;
            appinfo = aconfigPackageLoad.getBooleanFlagValue("appinfo", false);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } catch (LinkageError e2) {
            Log.w(TAG, e2.toString());
        }
        isCached = true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean appinfo() {
        if (!isCached) {
            init();
        }
        return appinfo;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean hpkePublicApi() {
        if (!isCached) {
            init();
        }
        return hpkePublicApi;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean hpkeVApis() {
        if (!isCached) {
            init();
        }
        return hpkeVApis;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean madviseApi() {
        if (!isCached) {
            init();
        }
        return madviseApi;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean nativeMetrics() {
        if (!isCached) {
            init();
        }
        return nativeMetrics;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean openjdk21Stringconcat() {
        if (!isCached) {
            init();
        }
        return openjdk21Stringconcat;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean openjdk21V1Apis() {
        if (!isCached) {
            init();
        }
        return openjdk21V1Apis;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean openjdk21V2Apis() {
        if (!isCached) {
            init();
        }
        return openjdk21V2Apis;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean postCleanupApis() {
        if (!isCached) {
            init();
        }
        return postCleanupApis;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean readOnlyDynamicCodeLoad() {
        if (!isCached) {
            init();
        }
        return readOnlyDynamicCodeLoad;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.libcore.FeatureFlags
    public boolean vApis() {
        if (!isCached) {
            init();
        }
        return vApis;
    }
}
