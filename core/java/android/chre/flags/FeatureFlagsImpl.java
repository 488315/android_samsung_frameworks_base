package android.chre.flags;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.chre.flags.FeatureFlags
    public boolean abortIfNoContextHubFound() {
        return false;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean bugFixReduceLockHoldingPeriod() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean contextHubCallbackUuidEnabled() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean flagLogNanoappLoadMetrics() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean metricsReporterInTheDaemon() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reconnectHostEndpointsAfterHalRestart() {
        return false;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reduceLockHoldingPeriod() {
        return false;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessage() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageDuplicateDetectionService() {
        return false;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageImplementation() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageRetrySupportService() {
        return false;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageTestModeBehavior() {
        return false;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean removeApWakeupMetricReportLimit() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean waitForPreloadedNanoappStart() {
        return false;
    }
}
