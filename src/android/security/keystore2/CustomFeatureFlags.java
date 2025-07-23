package android.security.keystore2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ATTEST_MODULES, Flags.FLAG_DISABLE_LEGACY_KEYSTORE_GET, Flags.FLAG_DISABLE_LEGACY_KEYSTORE_PUT_V2, Flags.FLAG_IMPORT_PREVIOUSLY_EMULATED_KEYS, Flags.FLAG_USE_BLOB_STATE_COLUMN, Flags.FLAG_WAL_DB_JOURNALMODE_V3, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.security.keystore2.FeatureFlags
    public boolean attestModules() {
        return getValue(Flags.FLAG_ATTEST_MODULES, new Predicate() { // from class: android.security.keystore2.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).attestModules();
            }
        });
    }

    @Override // android.security.keystore2.FeatureFlags
    public boolean disableLegacyKeystoreGet() {
        return getValue(Flags.FLAG_DISABLE_LEGACY_KEYSTORE_GET, new Predicate() { // from class: android.security.keystore2.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableLegacyKeystoreGet();
            }
        });
    }

    @Override // android.security.keystore2.FeatureFlags
    public boolean disableLegacyKeystorePutV2() {
        return getValue(Flags.FLAG_DISABLE_LEGACY_KEYSTORE_PUT_V2, new Predicate() { // from class: android.security.keystore2.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableLegacyKeystorePutV2();
            }
        });
    }

    @Override // android.security.keystore2.FeatureFlags
    public boolean importPreviouslyEmulatedKeys() {
        return getValue(Flags.FLAG_IMPORT_PREVIOUSLY_EMULATED_KEYS, new Predicate() { // from class: android.security.keystore2.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).importPreviouslyEmulatedKeys();
            }
        });
    }

    @Override // android.security.keystore2.FeatureFlags
    public boolean useBlobStateColumn() {
        return getValue(Flags.FLAG_USE_BLOB_STATE_COLUMN, new Predicate() { // from class: android.security.keystore2.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useBlobStateColumn();
            }
        });
    }

    @Override // android.security.keystore2.FeatureFlags
    public boolean walDbJournalmodeV3() {
        return getValue(Flags.FLAG_WAL_DB_JOURNALMODE_V3, new Predicate() { // from class: android.security.keystore2.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).walDbJournalmodeV3();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ATTEST_MODULES, Flags.FLAG_DISABLE_LEGACY_KEYSTORE_GET, Flags.FLAG_DISABLE_LEGACY_KEYSTORE_PUT_V2, Flags.FLAG_IMPORT_PREVIOUSLY_EMULATED_KEYS, Flags.FLAG_USE_BLOB_STATE_COLUMN, Flags.FLAG_WAL_DB_JOURNALMODE_V3);
    }
}
