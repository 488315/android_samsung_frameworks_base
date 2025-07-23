package android.security.keystore2;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ATTEST_MODULES = "android.security.keystore2.attest_modules";
    public static final String FLAG_DISABLE_LEGACY_KEYSTORE_GET = "android.security.keystore2.disable_legacy_keystore_get";
    public static final String FLAG_DISABLE_LEGACY_KEYSTORE_PUT_V2 = "android.security.keystore2.disable_legacy_keystore_put_v2";
    public static final String FLAG_IMPORT_PREVIOUSLY_EMULATED_KEYS = "android.security.keystore2.import_previously_emulated_keys";
    public static final String FLAG_USE_BLOB_STATE_COLUMN = "android.security.keystore2.use_blob_state_column";
    public static final String FLAG_WAL_DB_JOURNALMODE_V3 = "android.security.keystore2.wal_db_journalmode_v3";

    public static boolean attestModules() {
        return FEATURE_FLAGS.attestModules();
    }

    public static boolean disableLegacyKeystoreGet() {
        return FEATURE_FLAGS.disableLegacyKeystoreGet();
    }

    public static boolean disableLegacyKeystorePutV2() {
        return FEATURE_FLAGS.disableLegacyKeystorePutV2();
    }

    public static boolean importPreviouslyEmulatedKeys() {
        return FEATURE_FLAGS.importPreviouslyEmulatedKeys();
    }

    public static boolean useBlobStateColumn() {
        return FEATURE_FLAGS.useBlobStateColumn();
    }

    public static boolean walDbJournalmodeV3() {
        return FEATURE_FLAGS.walDbJournalmodeV3();
    }
}
