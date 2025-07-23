package com.android.internal.hidden_from_bootclasspath.android.content.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_BIND_PACKAGE_ISOLATED_PROCESS = "android.content.flags.enable_bind_package_isolated_process";
    public static final String FLAG_INTENT_SAVE_TO_XML_PACKAGE = "android.content.flags.intent_save_to_xml_package";

    public static boolean enableBindPackageIsolatedProcess() {
        return FEATURE_FLAGS.enableBindPackageIsolatedProcess();
    }

    public static boolean intentSaveToXmlPackage() {
        return FEATURE_FLAGS.intentSaveToXmlPackage();
    }
}
