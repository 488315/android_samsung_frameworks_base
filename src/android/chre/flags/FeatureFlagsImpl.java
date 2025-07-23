package android.chre.flags;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.chre.flags.FeatureFlags
    public boolean abortIfNoContextHubFound() {
        return false;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean btSocketHalSupported() {
        return false;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean bugFixHalReliableMessageRecord() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean bugFixRemoveExitCallInHal() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean efwXportInContextHub() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean efwXportRewindOnError() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean fixApiCheck() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean halHandleNanoappQueryTestMode() {
        return false;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean offloadApi() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean offloadImplementation() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reconnectHostEndpointsAfterHalRestart() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reduceLockingContextHubTransactionManager() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean refactorHalXportAgnostic() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessage() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageDuplicateDetectionService() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageRetrySupportService() {
        return true;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageTestModeBehavior() {
        return false;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean removeOldContextHubApis() {
        return false;
    }
}
