package android.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ASM_OPT_SYSTEM_INTO_ENFORCEMENT,
                            Flags.FLAG_ASM_RESTRICTIONS_ENABLED,
                            Flags.FLAG_ASM_TOASTS_ENABLED,
                            Flags.FLAG_BINARY_TRANSPARENCY_SEPOLICY_HASH,
                            Flags.FLAG_BLOCK_NULL_ACTION_INTENTS,
                            Flags.FLAG_CERTIFICATE_TRANSPARENCY_CONFIGURATION,
                            Flags.FLAG_CONTENT_URI_PERMISSION_APIS,
                            Flags.FLAG_DEPRECATE_FSV_SIG,
                            Flags.FLAG_DUMP_ATTESTATION_VERIFICATIONS,
                            Flags.FLAG_ENFORCE_INTENT_FILTER_MATCH,
                            Flags.FLAG_EXTEND_ECM_TO_ALL_SETTINGS,
                            Flags.FLAG_EXTEND_VB_CHAIN_TO_UPDATED_APK,
                            Flags.FLAG_FIX_UNLOCKED_DEVICE_REQUIRED_KEYS_V2,
                            Flags.FLAG_FRP_ENFORCEMENT,
                            Flags.FLAG_FSVERITY_API,
                            Flags.FLAG_KEYINFO_UNLOCKED_DEVICE_REQUIRED,
                            Flags.FLAG_MGF1_DIGEST_SETTER_V2,
                            Flags.FLAG_REPORT_PRIMARY_AUTH_ATTEMPTS,
                            Flags.FLAG_SIGNIFICANT_PLACES,
                            Flags.FLAG_UNLOCKED_STORAGE_API,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.security.FeatureFlags
    public boolean asmOptSystemIntoEnforcement() {
        return getValue(
                Flags.FLAG_ASM_OPT_SYSTEM_INTO_ENFORCEMENT,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).asmOptSystemIntoEnforcement();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean asmRestrictionsEnabled() {
        return getValue(
                Flags.FLAG_ASM_RESTRICTIONS_ENABLED,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).asmRestrictionsEnabled();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean asmToastsEnabled() {
        return getValue(
                Flags.FLAG_ASM_TOASTS_ENABLED,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda17
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).asmToastsEnabled();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean binaryTransparencySepolicyHash() {
        return getValue(
                Flags.FLAG_BINARY_TRANSPARENCY_SEPOLICY_HASH,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).binaryTransparencySepolicyHash();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean blockNullActionIntents() {
        return getValue(
                Flags.FLAG_BLOCK_NULL_ACTION_INTENTS,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).blockNullActionIntents();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean certificateTransparencyConfiguration() {
        return getValue(
                Flags.FLAG_CERTIFICATE_TRANSPARENCY_CONFIGURATION,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).certificateTransparencyConfiguration();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean contentUriPermissionApis() {
        return getValue(
                Flags.FLAG_CONTENT_URI_PERMISSION_APIS,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).contentUriPermissionApis();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean deprecateFsvSig() {
        return getValue(
                Flags.FLAG_DEPRECATE_FSV_SIG,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).deprecateFsvSig();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean dumpAttestationVerifications() {
        return getValue(
                Flags.FLAG_DUMP_ATTESTATION_VERIFICATIONS,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).dumpAttestationVerifications();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean enforceIntentFilterMatch() {
        return getValue(
                Flags.FLAG_ENFORCE_INTENT_FILTER_MATCH,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enforceIntentFilterMatch();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean extendEcmToAllSettings() {
        return getValue(
                Flags.FLAG_EXTEND_ECM_TO_ALL_SETTINGS,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).extendEcmToAllSettings();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean extendVbChainToUpdatedApk() {
        return getValue(
                Flags.FLAG_EXTEND_VB_CHAIN_TO_UPDATED_APK,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).extendVbChainToUpdatedApk();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean fixUnlockedDeviceRequiredKeysV2() {
        return getValue(
                Flags.FLAG_FIX_UNLOCKED_DEVICE_REQUIRED_KEYS_V2,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fixUnlockedDeviceRequiredKeysV2();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean frpEnforcement() {
        return getValue(
                Flags.FLAG_FRP_ENFORCEMENT,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda19
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).frpEnforcement();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean fsverityApi() {
        return getValue(
                Flags.FLAG_FSVERITY_API,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fsverityApi();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean keyinfoUnlockedDeviceRequired() {
        return getValue(
                Flags.FLAG_KEYINFO_UNLOCKED_DEVICE_REQUIRED,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).keyinfoUnlockedDeviceRequired();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean mgf1DigestSetterV2() {
        return getValue(
                Flags.FLAG_MGF1_DIGEST_SETTER_V2,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).mgf1DigestSetterV2();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean reportPrimaryAuthAttempts() {
        return getValue(
                Flags.FLAG_REPORT_PRIMARY_AUTH_ATTEMPTS,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reportPrimaryAuthAttempts();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean significantPlaces() {
        return getValue(
                Flags.FLAG_SIGNIFICANT_PLACES,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).significantPlaces();
                    }
                });
    }

    @Override // android.security.FeatureFlags
    public boolean unlockedStorageApi() {
        return getValue(
                Flags.FLAG_UNLOCKED_STORAGE_API,
                new Predicate() { // from class:
                                  // android.security.CustomFeatureFlags$$ExternalSyntheticLambda18
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).unlockedStorageApi();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_ASM_OPT_SYSTEM_INTO_ENFORCEMENT,
                Flags.FLAG_ASM_RESTRICTIONS_ENABLED,
                Flags.FLAG_ASM_TOASTS_ENABLED,
                Flags.FLAG_BINARY_TRANSPARENCY_SEPOLICY_HASH,
                Flags.FLAG_BLOCK_NULL_ACTION_INTENTS,
                Flags.FLAG_CERTIFICATE_TRANSPARENCY_CONFIGURATION,
                Flags.FLAG_CONTENT_URI_PERMISSION_APIS,
                Flags.FLAG_DEPRECATE_FSV_SIG,
                Flags.FLAG_DUMP_ATTESTATION_VERIFICATIONS,
                Flags.FLAG_ENFORCE_INTENT_FILTER_MATCH,
                Flags.FLAG_EXTEND_ECM_TO_ALL_SETTINGS,
                Flags.FLAG_EXTEND_VB_CHAIN_TO_UPDATED_APK,
                Flags.FLAG_FIX_UNLOCKED_DEVICE_REQUIRED_KEYS_V2,
                Flags.FLAG_FRP_ENFORCEMENT,
                Flags.FLAG_FSVERITY_API,
                Flags.FLAG_KEYINFO_UNLOCKED_DEVICE_REQUIRED,
                Flags.FLAG_MGF1_DIGEST_SETTER_V2,
                Flags.FLAG_REPORT_PRIMARY_AUTH_ATTEMPTS,
                Flags.FLAG_SIGNIFICANT_PLACES,
                Flags.FLAG_UNLOCKED_STORAGE_API);
    }
}
