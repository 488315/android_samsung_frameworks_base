package com.android.internal.hidden_from_bootclasspath.android.companion;

public interface FeatureFlags {
    boolean associationTag();

    boolean companionTransportApis();

    boolean devicePresence();

    boolean newAssociationBuilder();

    boolean ongoingPermSync();

    boolean permSyncUserConsent();

    boolean unpairAssociatedDevice();
}
