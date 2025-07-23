package android.server;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean allowNetworkTimeUpdateService();

    boolean allowRemovingVpnService();

    boolean earlySystemConfigInit();

    boolean enableThemeService();

    boolean migrateWristOrientation();

    boolean removeAppIntegrityManagerService();

    boolean removeGameManagerServiceFromWear();

    boolean removeTextService();

    boolean removeWearableSensingServiceFromWear();

    boolean telemetryApisService();

    boolean wearGestureApi();
}
