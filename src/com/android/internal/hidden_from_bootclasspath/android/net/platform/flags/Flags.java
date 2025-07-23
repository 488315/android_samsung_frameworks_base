package com.android.internal.hidden_from_bootclasspath.android.net.platform.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_DEPRECATE_NETWORK_POLICY_CALLBACK = "android.net.platform.flags.deprecate_network_policy_callback";
    public static final String FLAG_MDNS_IMPROVEMENT_FOR_25Q2 = "android.net.platform.flags.mdns_improvement_for_25q2";
    public static final String FLAG_POWERED_OFF_FINDING_PLATFORM = "android.net.platform.flags.powered_off_finding_platform";
    public static final String FLAG_REGISTER_NSD_OFFLOAD_ENGINE = "android.net.platform.flags.register_nsd_offload_engine";
    public static final String FLAG_VPN_TYPE_OEM_SERVICE_AND_LEGACY = "android.net.platform.flags.vpn_type_oem_service_and_legacy";

    public static boolean deprecateNetworkPolicyCallback() {
        return FEATURE_FLAGS.deprecateNetworkPolicyCallback();
    }

    public static boolean mdnsImprovementFor25q2() {
        return FEATURE_FLAGS.mdnsImprovementFor25q2();
    }

    public static boolean poweredOffFindingPlatform() {
        return FEATURE_FLAGS.poweredOffFindingPlatform();
    }

    public static boolean registerNsdOffloadEngine() {
        return FEATURE_FLAGS.registerNsdOffloadEngine();
    }

    public static boolean vpnTypeOemServiceAndLegacy() {
        return FEATURE_FLAGS.vpnTypeOemServiceAndLegacy();
    }
}
