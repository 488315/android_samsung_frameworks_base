package com.android.internal.hidden_from_bootclasspath.android.provider.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DEVICE_CONFIG_WRITABLE_NAMESPACES_API = "android.provider.flags.device_config_writable_namespaces_api";
    public static final String FLAG_DUMP_IMPROVEMENTS = "android.provider.flags.dump_improvements";
    public static final String FLAG_MMD_DEVICE_CONFIG = "android.provider.flags.mmd_device_config";
    public static final String FLAG_NEW_STORAGE_PUBLIC_API = "android.provider.flags.new_storage_public_api";
    public static final String FLAG_NEW_STORAGE_WRITER_SYSTEM_API = "android.provider.flags.new_storage_writer_system_api";
    public static final String FLAG_STAGE_FLAGS_FOR_BUILD = "android.provider.flags.stage_flags_for_build";

    public static boolean deviceConfigWritableNamespacesApi() {
        return FEATURE_FLAGS.deviceConfigWritableNamespacesApi();
    }

    public static boolean dumpImprovements() {
        return FEATURE_FLAGS.dumpImprovements();
    }

    public static boolean mmdDeviceConfig() {
        return FEATURE_FLAGS.mmdDeviceConfig();
    }

    public static boolean newStoragePublicApi() {
        return FEATURE_FLAGS.newStoragePublicApi();
    }

    public static boolean newStorageWriterSystemApi() {
        return FEATURE_FLAGS.newStorageWriterSystemApi();
    }

    public static boolean stageFlagsForBuild() {
        return FEATURE_FLAGS.stageFlagsForBuild();
    }
}
