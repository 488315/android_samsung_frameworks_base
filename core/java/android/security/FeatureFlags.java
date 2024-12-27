package android.security;

public interface FeatureFlags {
    boolean asmOptSystemIntoEnforcement();

    boolean asmRestrictionsEnabled();

    boolean asmToastsEnabled();

    boolean binaryTransparencySepolicyHash();

    boolean blockNullActionIntents();

    boolean certificateTransparencyConfiguration();

    boolean contentUriPermissionApis();

    boolean deprecateFsvSig();

    boolean dumpAttestationVerifications();

    boolean enforceIntentFilterMatch();

    boolean extendEcmToAllSettings();

    boolean extendVbChainToUpdatedApk();

    boolean fixUnlockedDeviceRequiredKeysV2();

    boolean frpEnforcement();

    boolean fsverityApi();

    boolean keyinfoUnlockedDeviceRequired();

    boolean mgf1DigestSetterV2();

    boolean reportPrimaryAuthAttempts();

    boolean significantPlaces();

    boolean unlockedStorageApi();
}
