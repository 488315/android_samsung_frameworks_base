package android.chre.flags;

/* loaded from: classes.dex */
public interface FeatureFlags {
    boolean abortIfNoContextHubFound();

    boolean btSocketHalSupported();

    boolean bugFixHalReliableMessageRecord();

    boolean bugFixRemoveExitCallInHal();

    boolean efwXportInContextHub();

    boolean efwXportRewindOnError();

    boolean fixApiCheck();

    boolean halHandleNanoappQueryTestMode();

    boolean offloadApi();

    boolean offloadImplementation();

    boolean reconnectHostEndpointsAfterHalRestart();

    boolean reduceLockingContextHubTransactionManager();

    boolean refactorHalXportAgnostic();

    boolean reliableMessage();

    boolean reliableMessageDuplicateDetectionService();

    boolean reliableMessageRetrySupportService();

    boolean reliableMessageTestModeBehavior();

    boolean removeOldContextHubApis();
}
