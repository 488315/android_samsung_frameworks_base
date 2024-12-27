package com.android.internal.hidden_from_bootclasspath.android.os;

public interface FeatureFlags {
    boolean adpfFmqEagerSend();

    boolean adpfGpuReportActualWorkDuration();

    boolean adpfHwuiGpu();

    boolean adpfMeasureDuringInputEventBoost();

    boolean adpfObtainviewBoost();

    boolean adpfPlatformPowerEfficiency();

    boolean adpfPreferPowerEfficiency();

    boolean adpfUseFmqChannel();

    boolean adpfUseFmqChannelFixed();

    boolean allowPrivateProfile();

    boolean allowThermalHeadroomThresholds();

    boolean androidOsBuildVanillaIceCream();

    boolean batteryPartStatusApi();

    boolean batterySaverSupportedCheckApi();

    boolean batteryServiceSupportCurrentAdbCommand();

    boolean bugreportModeMaxValue();

    boolean disallowCellularNullCiphersRestriction();

    boolean messageQueueTailTracking();

    boolean perfettoSdkTracing();

    boolean removeAppProfilerPssCollection();

    boolean securityStateService();

    boolean stateOfHealthPublic();

    boolean storageLifetimeApi();

    boolean strictModeRestrictedNetwork();

    boolean telemetryApisFrameworkInitialization();
}
