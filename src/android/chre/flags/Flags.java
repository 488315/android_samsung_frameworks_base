package android.chre.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ABORT_IF_NO_CONTEXT_HUB_FOUND = "android.chre.flags.abort_if_no_context_hub_found";
    public static final String FLAG_BT_SOCKET_HAL_SUPPORTED = "android.chre.flags.bt_socket_hal_supported";
    public static final String FLAG_BUG_FIX_HAL_RELIABLE_MESSAGE_RECORD = "android.chre.flags.bug_fix_hal_reliable_message_record";
    public static final String FLAG_BUG_FIX_REMOVE_EXIT_CALL_IN_HAL = "android.chre.flags.bug_fix_remove_exit_call_in_hal";
    public static final String FLAG_EFW_XPORT_IN_CONTEXT_HUB = "android.chre.flags.efw_xport_in_context_hub";
    public static final String FLAG_EFW_XPORT_REWIND_ON_ERROR = "android.chre.flags.efw_xport_rewind_on_error";
    public static final String FLAG_FIX_API_CHECK = "android.chre.flags.fix_api_check";
    public static final String FLAG_HAL_HANDLE_NANOAPP_QUERY_TEST_MODE = "android.chre.flags.hal_handle_nanoapp_query_test_mode";
    public static final String FLAG_OFFLOAD_API = "android.chre.flags.offload_api";
    public static final String FLAG_OFFLOAD_IMPLEMENTATION = "android.chre.flags.offload_implementation";
    public static final String FLAG_RECONNECT_HOST_ENDPOINTS_AFTER_HAL_RESTART = "android.chre.flags.reconnect_host_endpoints_after_hal_restart";
    public static final String FLAG_REDUCE_LOCKING_CONTEXT_HUB_TRANSACTION_MANAGER = "android.chre.flags.reduce_locking_context_hub_transaction_manager";
    public static final String FLAG_REFACTOR_HAL_XPORT_AGNOSTIC = "android.chre.flags.refactor_hal_xport_agnostic";
    public static final String FLAG_RELIABLE_MESSAGE = "android.chre.flags.reliable_message";
    public static final String FLAG_RELIABLE_MESSAGE_DUPLICATE_DETECTION_SERVICE = "android.chre.flags.reliable_message_duplicate_detection_service";
    public static final String FLAG_RELIABLE_MESSAGE_RETRY_SUPPORT_SERVICE = "android.chre.flags.reliable_message_retry_support_service";
    public static final String FLAG_RELIABLE_MESSAGE_TEST_MODE_BEHAVIOR = "android.chre.flags.reliable_message_test_mode_behavior";
    public static final String FLAG_REMOVE_OLD_CONTEXT_HUB_APIS = "android.chre.flags.remove_old_context_hub_apis";

    public static boolean abortIfNoContextHubFound() {
        return FEATURE_FLAGS.abortIfNoContextHubFound();
    }

    public static boolean btSocketHalSupported() {
        return FEATURE_FLAGS.btSocketHalSupported();
    }

    public static boolean bugFixHalReliableMessageRecord() {
        return FEATURE_FLAGS.bugFixHalReliableMessageRecord();
    }

    public static boolean bugFixRemoveExitCallInHal() {
        return FEATURE_FLAGS.bugFixRemoveExitCallInHal();
    }

    public static boolean efwXportInContextHub() {
        return FEATURE_FLAGS.efwXportInContextHub();
    }

    public static boolean efwXportRewindOnError() {
        return FEATURE_FLAGS.efwXportRewindOnError();
    }

    public static boolean fixApiCheck() {
        return FEATURE_FLAGS.fixApiCheck();
    }

    public static boolean halHandleNanoappQueryTestMode() {
        return FEATURE_FLAGS.halHandleNanoappQueryTestMode();
    }

    public static boolean offloadApi() {
        return FEATURE_FLAGS.offloadApi();
    }

    public static boolean offloadImplementation() {
        return FEATURE_FLAGS.offloadImplementation();
    }

    public static boolean reconnectHostEndpointsAfterHalRestart() {
        return FEATURE_FLAGS.reconnectHostEndpointsAfterHalRestart();
    }

    public static boolean reduceLockingContextHubTransactionManager() {
        return FEATURE_FLAGS.reduceLockingContextHubTransactionManager();
    }

    public static boolean refactorHalXportAgnostic() {
        return FEATURE_FLAGS.refactorHalXportAgnostic();
    }

    public static boolean reliableMessage() {
        return FEATURE_FLAGS.reliableMessage();
    }

    public static boolean reliableMessageDuplicateDetectionService() {
        return FEATURE_FLAGS.reliableMessageDuplicateDetectionService();
    }

    public static boolean reliableMessageRetrySupportService() {
        return FEATURE_FLAGS.reliableMessageRetrySupportService();
    }

    public static boolean reliableMessageTestModeBehavior() {
        return FEATURE_FLAGS.reliableMessageTestModeBehavior();
    }

    public static boolean removeOldContextHubApis() {
        return FEATURE_FLAGS.removeOldContextHubApis();
    }
}
