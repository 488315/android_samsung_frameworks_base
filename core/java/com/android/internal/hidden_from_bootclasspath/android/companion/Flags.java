package com.android.internal.hidden_from_bootclasspath.android.companion;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ASSOCIATION_TAG = "android.companion.association_tag";
    public static final String FLAG_COMPANION_TRANSPORT_APIS =
            "android.companion.companion_transport_apis";
    public static final String FLAG_DEVICE_PRESENCE = "android.companion.device_presence";
    public static final String FLAG_NEW_ASSOCIATION_BUILDER =
            "android.companion.new_association_builder";
    public static final String FLAG_ONGOING_PERM_SYNC = "android.companion.ongoing_perm_sync";
    public static final String FLAG_PERM_SYNC_USER_CONSENT =
            "android.companion.perm_sync_user_consent";
    public static final String FLAG_UNPAIR_ASSOCIATED_DEVICE =
            "android.companion.unpair_associated_device";

    public static boolean associationTag() {
        return FEATURE_FLAGS.associationTag();
    }

    public static boolean companionTransportApis() {
        return FEATURE_FLAGS.companionTransportApis();
    }

    public static boolean devicePresence() {
        return FEATURE_FLAGS.devicePresence();
    }

    public static boolean newAssociationBuilder() {
        return FEATURE_FLAGS.newAssociationBuilder();
    }

    public static boolean ongoingPermSync() {
        return FEATURE_FLAGS.ongoingPermSync();
    }

    public static boolean permSyncUserConsent() {
        return FEATURE_FLAGS.permSyncUserConsent();
    }

    public static boolean unpairAssociatedDevice() {
        return FEATURE_FLAGS.unpairAssociatedDevice();
    }
}
