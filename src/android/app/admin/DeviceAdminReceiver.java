package android.app.admin;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class DeviceAdminReceiver extends BroadcastReceiver {
    public static final String ACTION_AFFILIATED_PROFILE_TRANSFER_OWNERSHIP_COMPLETE = "android.app.action.AFFILIATED_PROFILE_TRANSFER_OWNERSHIP_COMPLETE";
    public static final String ACTION_BUGREPORT_FAILED = "android.app.action.BUGREPORT_FAILED";
    public static final String ACTION_BUGREPORT_SHARE = "android.app.action.BUGREPORT_SHARE";
    public static final String ACTION_BUGREPORT_SHARING_DECLINED = "android.app.action.BUGREPORT_SHARING_DECLINED";
    public static final String ACTION_CHOOSE_PRIVATE_KEY_ALIAS = "android.app.action.CHOOSE_PRIVATE_KEY_ALIAS";
    public static final String ACTION_COMPLIANCE_ACKNOWLEDGEMENT_REQUIRED = "android.app.action.COMPLIANCE_ACKNOWLEDGEMENT_REQUIRED";
    public static final String ACTION_DEVICE_ADMIN_DISABLED = "android.app.action.DEVICE_ADMIN_DISABLED";
    public static final String ACTION_DEVICE_ADMIN_DISABLE_REQUESTED = "android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED";
    public static final String ACTION_DEVICE_ADMIN_ENABLED = "android.app.action.DEVICE_ADMIN_ENABLED";
    public static final String ACTION_LOCK_TASK_ENTERING = "android.app.action.LOCK_TASK_ENTERING";
    public static final String ACTION_LOCK_TASK_EXITING = "android.app.action.LOCK_TASK_EXITING";
    public static final String ACTION_NETWORK_LOGS_AVAILABLE = "android.app.action.NETWORK_LOGS_AVAILABLE";
    public static final String ACTION_NOTIFY_PENDING_SYSTEM_UPDATE = "android.app.action.NOTIFY_PENDING_SYSTEM_UPDATE";
    public static final String ACTION_OPERATION_SAFETY_STATE_CHANGED = "android.app.action.OPERATION_SAFETY_STATE_CHANGED";
    public static final String ACTION_PASSWORD_CHANGED = "android.app.action.ACTION_PASSWORD_CHANGED";
    public static final String ACTION_PASSWORD_EXPIRING = "android.app.action.ACTION_PASSWORD_EXPIRING";
    public static final String ACTION_PASSWORD_FAILED = "android.app.action.ACTION_PASSWORD_FAILED";
    public static final String ACTION_PASSWORD_SUCCEEDED = "android.app.action.ACTION_PASSWORD_SUCCEEDED";
    public static final String ACTION_PROFILE_PROVISIONING_COMPLETE = "android.app.action.PROFILE_PROVISIONING_COMPLETE";
    public static final String ACTION_RECOVERY_PASSWORD_REQUESTED = "android.app.action.ACTION_RECOVERY_PASSWORD_REQUESTED";
    public static final String ACTION_SECURITY_LOGS_AVAILABLE = "android.app.action.SECURITY_LOGS_AVAILABLE";
    public static final String ACTION_TRANSFER_OWNERSHIP_COMPLETE = "android.app.action.TRANSFER_OWNERSHIP_COMPLETE";
    public static final String ACTION_USER_ADDED = "android.app.action.USER_ADDED";
    public static final String ACTION_USER_REMOVED = "android.app.action.USER_REMOVED";
    public static final String ACTION_USER_STARTED = "android.app.action.USER_STARTED";
    public static final String ACTION_USER_STOPPED = "android.app.action.USER_STOPPED";
    public static final String ACTION_USER_SWITCHED = "android.app.action.USER_SWITCHED";
    public static final int BUGREPORT_FAILURE_FAILED_COMPLETING = 0;
    public static final int BUGREPORT_FAILURE_FILE_NO_LONGER_AVAILABLE = 1;
    public static final String DEVICE_ADMIN_META_DATA = "android.app.device_admin";
    public static final String EXTRA_BUGREPORT_FAILURE_REASON = "android.app.extra.BUGREPORT_FAILURE_REASON";
    public static final String EXTRA_BUGREPORT_HASH = "android.app.extra.BUGREPORT_HASH";
    public static final String EXTRA_CHOOSE_PRIVATE_KEY_ALIAS = "android.app.extra.CHOOSE_PRIVATE_KEY_ALIAS";
    public static final String EXTRA_CHOOSE_PRIVATE_KEY_RESPONSE = "android.app.extra.CHOOSE_PRIVATE_KEY_RESPONSE";
    public static final String EXTRA_CHOOSE_PRIVATE_KEY_SENDER_UID = "android.app.extra.CHOOSE_PRIVATE_KEY_SENDER_UID";
    public static final String EXTRA_CHOOSE_PRIVATE_KEY_URI = "android.app.extra.CHOOSE_PRIVATE_KEY_URI";
    public static final String EXTRA_DISABLE_WARNING = "android.app.extra.DISABLE_WARNING";
    public static final String EXTRA_LOCK_TASK_PACKAGE = "android.app.extra.LOCK_TASK_PACKAGE";
    public static final String EXTRA_NETWORK_LOGS_COUNT = "android.app.extra.EXTRA_NETWORK_LOGS_COUNT";
    public static final String EXTRA_NETWORK_LOGS_TOKEN = "android.app.extra.EXTRA_NETWORK_LOGS_TOKEN";
    public static final String EXTRA_OPERATION_SAFETY_REASON = "android.app.extra.OPERATION_SAFETY_REASON";
    public static final String EXTRA_OPERATION_SAFETY_STATE = "android.app.extra.OPERATION_SAFETY_STATE";
    public static final String EXTRA_SYSTEM_UPDATE_RECEIVED_TIME = "android.app.extra.SYSTEM_UPDATE_RECEIVED_TIME";
    public static final String EXTRA_TRANSFER_OWNERSHIP_ADMIN_EXTRAS_BUNDLE = "android.app.extra.TRANSFER_OWNERSHIP_ADMIN_EXTRAS_BUNDLE";
    private static final boolean LOCAL_LOGV = false;
    private static final String TAG = "DevicePolicy";
    private DevicePolicyManager mManager;
    private ComponentName mWho;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BugreportFailureCode {
    }

    public void onBugreportFailed(Context context, Intent intent, int i) {
    }

    public void onBugreportShared(Context context, Intent intent, String str) {
    }

    public void onBugreportSharingDeclined(Context context, Intent intent) {
    }

    public String onChoosePrivateKeyAlias(Context context, Intent intent, int i, Uri uri, String str) {
        return null;
    }

    public CharSequence onDisableRequested(Context context, Intent intent) {
        return null;
    }

    public void onDisabled(Context context, Intent intent) {
    }

    public void onEnabled(Context context, Intent intent) {
    }

    public void onLockTaskModeEntering(Context context, Intent intent, String str) {
    }

    public void onLockTaskModeExiting(Context context, Intent intent) {
    }

    public void onNetworkLogsAvailable(Context context, Intent intent, long j, int i) {
    }

    public void onOperationSafetyStateChanged(Context context, int i, boolean z) {
    }

    @Deprecated
    public void onPasswordChanged(Context context, Intent intent) {
    }

    @Deprecated
    public void onPasswordExpiring(Context context, Intent intent) {
    }

    @Deprecated
    public void onPasswordFailed(Context context, Intent intent) {
    }

    @Deprecated
    public void onPasswordSucceeded(Context context, Intent intent) {
    }

    public void onProfileProvisioningComplete(Context context, Intent intent) {
    }

    @Deprecated
    public void onReadyForUserInitialization(Context context, Intent intent) {
    }

    public void onRecoveryPasswordRequested(Context context, Intent intent) {
    }

    public void onSecurityLogsAvailable(Context context, Intent intent) {
    }

    public void onSystemUpdatePending(Context context, Intent intent, long j) {
    }

    public void onTransferAffiliatedProfileOwnershipComplete(Context context, UserHandle userHandle) {
    }

    public void onTransferOwnershipComplete(Context context, PersistableBundle persistableBundle) {
    }

    public void onUserAdded(Context context, Intent intent, UserHandle userHandle) {
    }

    public void onUserRemoved(Context context, Intent intent, UserHandle userHandle) {
    }

    public void onUserStarted(Context context, Intent intent, UserHandle userHandle) {
    }

    public void onUserStopped(Context context, Intent intent, UserHandle userHandle) {
    }

    public void onUserSwitched(Context context, Intent intent, UserHandle userHandle) {
    }

    public DevicePolicyManager getManager(Context context) {
        DevicePolicyManager devicePolicyManager = this.mManager;
        if (devicePolicyManager != null) {
            return devicePolicyManager;
        }
        DevicePolicyManager devicePolicyManager2 = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        this.mManager = devicePolicyManager2;
        return devicePolicyManager2;
    }

    public ComponentName getWho(Context context) {
        ComponentName componentName = this.mWho;
        if (componentName != null) {
            return componentName;
        }
        ComponentName componentName2 = new ComponentName(context, getClass());
        this.mWho = componentName2;
        return componentName2;
    }

    public void onPasswordChanged(Context context, Intent intent, UserHandle userHandle) {
        onPasswordChanged(context, intent);
    }

    public void onPasswordFailed(Context context, Intent intent, UserHandle userHandle) {
        onPasswordFailed(context, intent);
    }

    public void onPasswordSucceeded(Context context, Intent intent, UserHandle userHandle) {
        onPasswordSucceeded(context, intent);
    }

    public void onPasswordExpiring(Context context, Intent intent, UserHandle userHandle) {
        onPasswordExpiring(context, intent);
    }

    private void onOperationSafetyStateChanged(Context context, Intent intent) {
        if (!hasRequiredExtra(intent, EXTRA_OPERATION_SAFETY_REASON) || !hasRequiredExtra(intent, EXTRA_OPERATION_SAFETY_STATE)) {
            Log.w(TAG, "Igoring intent that's missing required extras");
            return;
        }
        int intExtra = intent.getIntExtra(EXTRA_OPERATION_SAFETY_REASON, -1);
        if (!DevicePolicyManager.isValidOperationSafetyReason(intExtra)) {
            Log.wtf(TAG, "Received invalid reason on " + intent.getAction() + ": " + intExtra);
            return;
        }
        onOperationSafetyStateChanged(context, intExtra, intent.getBooleanExtra(EXTRA_OPERATION_SAFETY_STATE, false));
    }

    public void onComplianceAcknowledgementRequired(Context context, Intent intent) {
        getManager(context).acknowledgeDeviceCompliant();
    }

    private boolean hasRequiredExtra(Intent intent, String str) {
        if (intent.hasExtra(str)) {
            return true;
        }
        Log.wtf(TAG, "Missing '" + str + "' on intent " + intent);
        return false;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_PASSWORD_CHANGED.equals(action)) {
            onPasswordChanged(context, intent, (UserHandle) intent.getParcelableExtra(Intent.EXTRA_USER, UserHandle.class));
            return;
        }
        if (ACTION_PASSWORD_FAILED.equals(action)) {
            onPasswordFailed(context, intent, (UserHandle) intent.getParcelableExtra(Intent.EXTRA_USER, UserHandle.class));
            return;
        }
        if (ACTION_PASSWORD_SUCCEEDED.equals(action)) {
            onPasswordSucceeded(context, intent, (UserHandle) intent.getParcelableExtra(Intent.EXTRA_USER, UserHandle.class));
            return;
        }
        if (ACTION_DEVICE_ADMIN_ENABLED.equals(action)) {
            onEnabled(context, intent);
            return;
        }
        if (ACTION_DEVICE_ADMIN_DISABLE_REQUESTED.equals(action)) {
            CharSequence charSequenceOnDisableRequested = onDisableRequested(context, intent);
            if (charSequenceOnDisableRequested != null) {
                getResultExtras(true).putCharSequence(EXTRA_DISABLE_WARNING, charSequenceOnDisableRequested);
                return;
            }
            return;
        }
        if (ACTION_DEVICE_ADMIN_DISABLED.equals(action)) {
            onDisabled(context, intent);
            return;
        }
        if (ACTION_PASSWORD_EXPIRING.equals(action)) {
            onPasswordExpiring(context, intent, (UserHandle) intent.getParcelableExtra(Intent.EXTRA_USER, UserHandle.class));
            return;
        }
        if (ACTION_PROFILE_PROVISIONING_COMPLETE.equals(action)) {
            onProfileProvisioningComplete(context, intent);
            return;
        }
        if (ACTION_CHOOSE_PRIVATE_KEY_ALIAS.equals(action)) {
            setResultData(onChoosePrivateKeyAlias(context, intent, intent.getIntExtra(EXTRA_CHOOSE_PRIVATE_KEY_SENDER_UID, -1), (Uri) intent.getParcelableExtra(EXTRA_CHOOSE_PRIVATE_KEY_URI, Uri.class), intent.getStringExtra(EXTRA_CHOOSE_PRIVATE_KEY_ALIAS)));
            return;
        }
        if (ACTION_LOCK_TASK_ENTERING.equals(action)) {
            onLockTaskModeEntering(context, intent, intent.getStringExtra(EXTRA_LOCK_TASK_PACKAGE));
            return;
        }
        if (ACTION_LOCK_TASK_EXITING.equals(action)) {
            onLockTaskModeExiting(context, intent);
            return;
        }
        if (ACTION_NOTIFY_PENDING_SYSTEM_UPDATE.equals(action)) {
            onSystemUpdatePending(context, intent, intent.getLongExtra(EXTRA_SYSTEM_UPDATE_RECEIVED_TIME, -1L));
            return;
        }
        if (ACTION_BUGREPORT_SHARING_DECLINED.equals(action)) {
            onBugreportSharingDeclined(context, intent);
            return;
        }
        if (ACTION_BUGREPORT_SHARE.equals(action)) {
            onBugreportShared(context, intent, intent.getStringExtra(EXTRA_BUGREPORT_HASH));
            return;
        }
        if (ACTION_BUGREPORT_FAILED.equals(action)) {
            onBugreportFailed(context, intent, intent.getIntExtra(EXTRA_BUGREPORT_FAILURE_REASON, 0));
            return;
        }
        if (ACTION_SECURITY_LOGS_AVAILABLE.equals(action)) {
            onSecurityLogsAvailable(context, intent);
            return;
        }
        if (ACTION_NETWORK_LOGS_AVAILABLE.equals(action)) {
            onNetworkLogsAvailable(context, intent, intent.getLongExtra(EXTRA_NETWORK_LOGS_TOKEN, -1L), intent.getIntExtra(EXTRA_NETWORK_LOGS_COUNT, 0));
            return;
        }
        if (ACTION_USER_ADDED.equals(action)) {
            onUserAdded(context, intent, (UserHandle) intent.getParcelableExtra(Intent.EXTRA_USER, UserHandle.class));
            return;
        }
        if (ACTION_USER_REMOVED.equals(action)) {
            onUserRemoved(context, intent, (UserHandle) intent.getParcelableExtra(Intent.EXTRA_USER, UserHandle.class));
            return;
        }
        if (ACTION_USER_STARTED.equals(action)) {
            onUserStarted(context, intent, (UserHandle) intent.getParcelableExtra(Intent.EXTRA_USER, UserHandle.class));
            return;
        }
        if (ACTION_USER_STOPPED.equals(action)) {
            onUserStopped(context, intent, (UserHandle) intent.getParcelableExtra(Intent.EXTRA_USER, UserHandle.class));
            return;
        }
        if (ACTION_USER_SWITCHED.equals(action)) {
            onUserSwitched(context, intent, (UserHandle) intent.getParcelableExtra(Intent.EXTRA_USER, UserHandle.class));
            return;
        }
        if (ACTION_TRANSFER_OWNERSHIP_COMPLETE.equals(action)) {
            onTransferOwnershipComplete(context, (PersistableBundle) intent.getParcelableExtra(EXTRA_TRANSFER_OWNERSHIP_ADMIN_EXTRAS_BUNDLE, PersistableBundle.class));
            return;
        }
        if (ACTION_AFFILIATED_PROFILE_TRANSFER_OWNERSHIP_COMPLETE.equals(action)) {
            onTransferAffiliatedProfileOwnershipComplete(context, (UserHandle) intent.getParcelableExtra(Intent.EXTRA_USER, UserHandle.class));
        } else if (ACTION_OPERATION_SAFETY_STATE_CHANGED.equals(action)) {
            onOperationSafetyStateChanged(context, intent);
        } else if (ACTION_COMPLIANCE_ACKNOWLEDGEMENT_REQUIRED.equals(action)) {
            onComplianceAcknowledgementRequired(context, intent);
        }
    }
}
