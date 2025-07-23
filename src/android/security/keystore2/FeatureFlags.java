package android.security.keystore2;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean attestModules();

    boolean disableLegacyKeystoreGet();

    boolean disableLegacyKeystorePutV2();

    boolean importPreviouslyEmulatedKeys();

    boolean useBlobStateColumn();

    boolean walDbJournalmodeV3();
}
