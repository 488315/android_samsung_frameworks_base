package android.chre.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ABORT_IF_NO_CONTEXT_HUB_FOUND, Flags.FLAG_BT_SOCKET_HAL_SUPPORTED, Flags.FLAG_BUG_FIX_HAL_RELIABLE_MESSAGE_RECORD, Flags.FLAG_BUG_FIX_REMOVE_EXIT_CALL_IN_HAL, Flags.FLAG_EFW_XPORT_IN_CONTEXT_HUB, Flags.FLAG_EFW_XPORT_REWIND_ON_ERROR, Flags.FLAG_FIX_API_CHECK, Flags.FLAG_HAL_HANDLE_NANOAPP_QUERY_TEST_MODE, Flags.FLAG_OFFLOAD_API, Flags.FLAG_OFFLOAD_IMPLEMENTATION, Flags.FLAG_RECONNECT_HOST_ENDPOINTS_AFTER_HAL_RESTART, Flags.FLAG_REDUCE_LOCKING_CONTEXT_HUB_TRANSACTION_MANAGER, Flags.FLAG_REFACTOR_HAL_XPORT_AGNOSTIC, Flags.FLAG_RELIABLE_MESSAGE, Flags.FLAG_RELIABLE_MESSAGE_DUPLICATE_DETECTION_SERVICE, Flags.FLAG_RELIABLE_MESSAGE_RETRY_SUPPORT_SERVICE, Flags.FLAG_RELIABLE_MESSAGE_TEST_MODE_BEHAVIOR, Flags.FLAG_REMOVE_OLD_CONTEXT_HUB_APIS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean abortIfNoContextHubFound() {
        return getValue(Flags.FLAG_ABORT_IF_NO_CONTEXT_HUB_FOUND, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).abortIfNoContextHubFound();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean btSocketHalSupported() {
        return getValue(Flags.FLAG_BT_SOCKET_HAL_SUPPORTED, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).btSocketHalSupported();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean bugFixHalReliableMessageRecord() {
        return getValue(Flags.FLAG_BUG_FIX_HAL_RELIABLE_MESSAGE_RECORD, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bugFixHalReliableMessageRecord();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean bugFixRemoveExitCallInHal() {
        return getValue(Flags.FLAG_BUG_FIX_REMOVE_EXIT_CALL_IN_HAL, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bugFixRemoveExitCallInHal();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean efwXportInContextHub() {
        return getValue(Flags.FLAG_EFW_XPORT_IN_CONTEXT_HUB, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).efwXportInContextHub();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean efwXportRewindOnError() {
        return getValue(Flags.FLAG_EFW_XPORT_REWIND_ON_ERROR, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).efwXportRewindOnError();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean fixApiCheck() {
        return getValue(Flags.FLAG_FIX_API_CHECK, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixApiCheck();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean halHandleNanoappQueryTestMode() {
        return getValue(Flags.FLAG_HAL_HANDLE_NANOAPP_QUERY_TEST_MODE, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).halHandleNanoappQueryTestMode();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean offloadApi() {
        return getValue(Flags.FLAG_OFFLOAD_API, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).offloadApi();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean offloadImplementation() {
        return getValue(Flags.FLAG_OFFLOAD_IMPLEMENTATION, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).offloadImplementation();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reconnectHostEndpointsAfterHalRestart() {
        return getValue(Flags.FLAG_RECONNECT_HOST_ENDPOINTS_AFTER_HAL_RESTART, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reconnectHostEndpointsAfterHalRestart();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reduceLockingContextHubTransactionManager() {
        return getValue(Flags.FLAG_REDUCE_LOCKING_CONTEXT_HUB_TRANSACTION_MANAGER, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reduceLockingContextHubTransactionManager();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean refactorHalXportAgnostic() {
        return getValue(Flags.FLAG_REFACTOR_HAL_XPORT_AGNOSTIC, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).refactorHalXportAgnostic();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessage() {
        return getValue(Flags.FLAG_RELIABLE_MESSAGE, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reliableMessage();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageDuplicateDetectionService() {
        return getValue(Flags.FLAG_RELIABLE_MESSAGE_DUPLICATE_DETECTION_SERVICE, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reliableMessageDuplicateDetectionService();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageRetrySupportService() {
        return getValue(Flags.FLAG_RELIABLE_MESSAGE_RETRY_SUPPORT_SERVICE, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reliableMessageRetrySupportService();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageTestModeBehavior() {
        return getValue(Flags.FLAG_RELIABLE_MESSAGE_TEST_MODE_BEHAVIOR, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reliableMessageTestModeBehavior();
            }
        });
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean removeOldContextHubApis() {
        return getValue(Flags.FLAG_REMOVE_OLD_CONTEXT_HUB_APIS, new Predicate() { // from class: android.chre.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeOldContextHubApis();
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
        return Arrays.asList(Flags.FLAG_ABORT_IF_NO_CONTEXT_HUB_FOUND, Flags.FLAG_BT_SOCKET_HAL_SUPPORTED, Flags.FLAG_BUG_FIX_HAL_RELIABLE_MESSAGE_RECORD, Flags.FLAG_BUG_FIX_REMOVE_EXIT_CALL_IN_HAL, Flags.FLAG_EFW_XPORT_IN_CONTEXT_HUB, Flags.FLAG_EFW_XPORT_REWIND_ON_ERROR, Flags.FLAG_FIX_API_CHECK, Flags.FLAG_HAL_HANDLE_NANOAPP_QUERY_TEST_MODE, Flags.FLAG_OFFLOAD_API, Flags.FLAG_OFFLOAD_IMPLEMENTATION, Flags.FLAG_RECONNECT_HOST_ENDPOINTS_AFTER_HAL_RESTART, Flags.FLAG_REDUCE_LOCKING_CONTEXT_HUB_TRANSACTION_MANAGER, Flags.FLAG_REFACTOR_HAL_XPORT_AGNOSTIC, Flags.FLAG_RELIABLE_MESSAGE, Flags.FLAG_RELIABLE_MESSAGE_DUPLICATE_DETECTION_SERVICE, Flags.FLAG_RELIABLE_MESSAGE_RETRY_SUPPORT_SERVICE, Flags.FLAG_RELIABLE_MESSAGE_TEST_MODE_BEHAVIOR, Flags.FLAG_REMOVE_OLD_CONTEXT_HUB_APIS);
    }
}
