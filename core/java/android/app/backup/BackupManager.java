package android.app.backup;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.util.Pair;

import java.util.List;
import java.util.Map;

public class BackupManager {

    @SystemApi public static final int ERROR_AGENT_FAILURE = -1003;

    @SystemApi public static final int ERROR_BACKUP_CANCELLED = -2003;

    @SystemApi public static final int ERROR_BACKUP_NOT_ALLOWED = -2001;

    @SystemApi public static final int ERROR_PACKAGE_NOT_FOUND = -2002;

    @SystemApi public static final int ERROR_TRANSPORT_ABORTED = -1000;

    @SystemApi public static final int ERROR_TRANSPORT_INVALID = -2;

    @SystemApi public static final int ERROR_TRANSPORT_PACKAGE_REJECTED = -1002;

    @SystemApi public static final int ERROR_TRANSPORT_QUOTA_EXCEEDED = -1005;

    @SystemApi public static final int ERROR_TRANSPORT_UNAVAILABLE = -1;
    public static final String EXTRA_BACKUP_SERVICES_AVAILABLE = "backup_services_available";

    @SystemApi public static final int FLAG_NON_INCREMENTAL_BACKUP = 1;
    public static final long IS_BACKUP_SERVICE_ACTIVE_ENFORCE_PERMISSION_IN_SERVICE = 158482162;

    @SystemApi public static final String PACKAGE_MANAGER_SENTINEL = "@pm@";

    @SystemApi public static final int SUCCESS = 0;
    private static final String TAG = "BackupManager";
    public static IBackupManager sService;
    private Context mContext;

    private static void checkServiceBinder() {
        if (sService == null) {
            sService =
                    IBackupManager.Stub.asInterface(
                            ServiceManager.getService(Context.BACKUP_SERVICE));
        }
    }

    public BackupManager(Context context) {
        this.mContext = context;
    }

    public void dataChanged() {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.dataChanged(this.mContext.getPackageName());
            } catch (RemoteException e) {
                Log.d(TAG, "dataChanged() couldn't connect");
            }
        }
    }

    public static void dataChanged(String packageName) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.dataChanged(packageName);
            } catch (RemoteException e) {
                Log.e(TAG, "dataChanged(pkg) couldn't connect");
            }
        }
    }

    public static void dataChangedForUser(int userId, String packageName) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.dataChangedForUser(userId, packageName);
            } catch (RemoteException e) {
                Log.e(TAG, "dataChanged(userId,pkg) couldn't connect");
            }
        }
    }

    @Deprecated
    public int requestRestore(RestoreObserver observer) {
        return requestRestore(observer, null);
    }

    @SystemApi
    @Deprecated
    public int requestRestore(RestoreObserver observer, BackupManagerMonitor monitor) {
        Log.w(
                TAG,
                "requestRestore(): Since Android P app can no longer request restoring of its"
                        + " backup.");
        return -1;
    }

    @SystemApi
    public RestoreSession beginRestoreSession() {
        checkServiceBinder();
        if (sService == null) {
            return null;
        }
        try {
            IRestoreSession binder =
                    sService.beginRestoreSessionForUser(this.mContext.getUserId(), null, null);
            if (binder == null) {
                return null;
            }
            RestoreSession session = new RestoreSession(this.mContext, binder);
            return session;
        } catch (RemoteException e) {
            Log.e(TAG, "beginRestoreSession() couldn't connect");
            return null;
        }
    }

    @SystemApi
    public void setBackupEnabled(boolean isEnabled) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.setBackupEnabled(isEnabled);
            } catch (RemoteException e) {
                Log.e(TAG, "setBackupEnabled() couldn't connect");
            }
        }
    }

    @SystemApi
    public void setFrameworkSchedulingEnabled(boolean isEnabled) {
        checkServiceBinder();
        if (sService == null) {
            Log.e(TAG, "setFrameworkSchedulingEnabled() couldn't connect");
            return;
        }
        try {
            sService.setFrameworkSchedulingEnabledForUser(this.mContext.getUserId(), isEnabled);
        } catch (RemoteException e) {
            Log.e(TAG, "setFrameworkSchedulingEnabled() couldn't connect");
        }
    }

    @SystemApi
    public boolean isBackupEnabled() {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.isBackupEnabled();
            } catch (RemoteException e) {
                Log.e(TAG, "isBackupEnabled() couldn't connect");
                return false;
            }
        }
        return false;
    }

    @SystemApi
    public boolean isBackupServiceActive(UserHandle user) {
        if (!CompatChanges.isChangeEnabled(
                IS_BACKUP_SERVICE_ACTIVE_ENFORCE_PERMISSION_IN_SERVICE)) {
            this.mContext.enforceCallingOrSelfPermission(
                    Manifest.permission.BACKUP, "isBackupServiceActive");
        }
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.isBackupServiceActive(user.getIdentifier());
            } catch (RemoteException e) {
                Log.e(TAG, "isBackupEnabled() couldn't connect");
                return false;
            }
        }
        return false;
    }

    @SystemApi
    public void setAutoRestore(boolean isEnabled) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.setAutoRestore(isEnabled);
            } catch (RemoteException e) {
                Log.e(TAG, "setAutoRestore() couldn't connect");
            }
        }
    }

    @SystemApi
    public String getCurrentTransport() {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getCurrentTransport();
            } catch (RemoteException e) {
                Log.e(TAG, "getCurrentTransport() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @SystemApi
    public ComponentName getCurrentTransportComponent() {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getCurrentTransportComponentForUser(this.mContext.getUserId());
            } catch (RemoteException e) {
                Log.e(TAG, "getCurrentTransportComponent() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @SystemApi
    public String[] listAllTransports() {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.listAllTransports();
            } catch (RemoteException e) {
                Log.e(TAG, "listAllTransports() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @SystemApi
    @Deprecated
    public void updateTransportAttributes(
            ComponentName transportComponent,
            String name,
            Intent configurationIntent,
            String currentDestinationString,
            Intent dataManagementIntent,
            String dataManagementLabel) {
        updateTransportAttributes(
                transportComponent,
                name,
                configurationIntent,
                currentDestinationString,
                dataManagementIntent,
                (CharSequence) dataManagementLabel);
    }

    @SystemApi
    public void updateTransportAttributes(
            ComponentName transportComponent,
            String name,
            Intent configurationIntent,
            String currentDestinationString,
            Intent dataManagementIntent,
            CharSequence dataManagementLabel) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.updateTransportAttributesForUser(
                        this.mContext.getUserId(),
                        transportComponent,
                        name,
                        configurationIntent,
                        currentDestinationString,
                        dataManagementIntent,
                        dataManagementLabel);
            } catch (RemoteException e) {
                Log.e(TAG, "describeTransport() couldn't connect");
            }
        }
    }

    @SystemApi
    @Deprecated
    public String selectBackupTransport(String transport) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.selectBackupTransport(transport);
            } catch (RemoteException e) {
                Log.e(TAG, "selectBackupTransport() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @SystemApi
    public void selectBackupTransport(
            ComponentName transport, SelectBackupTransportCallback listener) {
        SelectTransportListenerWrapper wrapper;
        checkServiceBinder();
        if (sService != null) {
            if (listener == null) {
                wrapper = null;
            } else {
                try {
                    wrapper = new SelectTransportListenerWrapper(this.mContext, listener);
                } catch (RemoteException e) {
                    Log.e(TAG, "selectBackupTransportAsync() couldn't connect");
                    return;
                }
            }
            sService.selectBackupTransportAsyncForUser(
                    this.mContext.getUserId(), transport, wrapper);
        }
    }

    @SystemApi
    public void backupNow() {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.backupNow();
            } catch (RemoteException e) {
                Log.e(TAG, "backupNow() couldn't connect");
            }
        }
    }

    @SystemApi
    public long getAvailableRestoreToken(String packageName) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getAvailableRestoreTokenForUser(
                        this.mContext.getUserId(), packageName);
            } catch (RemoteException e) {
                Log.e(TAG, "getAvailableRestoreToken() couldn't connect");
                return 0L;
            }
        }
        return 0L;
    }

    @SystemApi
    public boolean isAppEligibleForBackup(String packageName) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.isAppEligibleForBackupForUser(
                        this.mContext.getUserId(), packageName);
            } catch (RemoteException e) {
                Log.e(TAG, "isAppEligibleForBackup(pkg) couldn't connect");
                return false;
            }
        }
        return false;
    }

    @SystemApi
    public int requestBackup(String[] packages, BackupObserver observer) {
        return requestBackup(packages, observer, null, 0);
    }

    @SystemApi
    public int requestBackup(
            String[] packages, BackupObserver observer, BackupManagerMonitor monitor, int flags) {
        BackupObserverWrapper observerWrapper;
        checkServiceBinder();
        if (sService != null) {
            BackupManagerMonitorWrapper monitorWrapper = null;
            if (observer == null) {
                observerWrapper = null;
            } else {
                try {
                    observerWrapper = new BackupObserverWrapper(this.mContext, observer);
                } catch (RemoteException e) {
                    Log.e(TAG, "requestBackup() couldn't connect");
                    return -1;
                }
            }
            if (monitor != null) {
                monitorWrapper = new BackupManagerMonitorWrapper(monitor);
            }
            return sService.requestBackup(packages, observerWrapper, monitorWrapper, flags);
        }
        return -1;
    }

    @SystemApi
    public void cancelBackups() {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.cancelBackups();
            } catch (RemoteException e) {
                Log.e(TAG, "cancelBackups() couldn't connect.");
            }
        }
    }

    public UserHandle getUserForAncestralSerialNumber(long ancestralSerialNumber) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getUserForAncestralSerialNumber(ancestralSerialNumber);
            } catch (RemoteException e) {
                Log.e(TAG, "getUserForAncestralSerialNumber() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @SystemApi
    public void setAncestralSerialNumber(long ancestralSerialNumber) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.setAncestralSerialNumber(ancestralSerialNumber);
            } catch (RemoteException e) {
                Log.e(TAG, "setAncestralSerialNumber() couldn't connect");
            }
        }
    }

    @SystemApi
    public Intent getConfigurationIntent(String transportName) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getConfigurationIntentForUser(
                        this.mContext.getUserId(), transportName);
            } catch (RemoteException e) {
                Log.e(TAG, "getConfigurationIntent() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @SystemApi
    public String getDestinationString(String transportName) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getDestinationStringForUser(
                        this.mContext.getUserId(), transportName);
            } catch (RemoteException e) {
                Log.e(TAG, "getDestinationString() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @SystemApi
    public Intent getDataManagementIntent(String transportName) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getDataManagementIntentForUser(
                        this.mContext.getUserId(), transportName);
            } catch (RemoteException e) {
                Log.e(TAG, "getDataManagementIntent() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @SystemApi
    @Deprecated
    public String getDataManagementLabel(String transportName) {
        CharSequence label = getDataManagementIntentLabel(transportName);
        if (label == null) {
            return null;
        }
        return label.toString();
    }

    @SystemApi
    public CharSequence getDataManagementIntentLabel(String transportName) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getDataManagementLabelForUser(
                        this.mContext.getUserId(), transportName);
            } catch (RemoteException e) {
                Log.e(TAG, "getDataManagementIntentLabel() couldn't connect");
                return null;
            }
        }
        return null;
    }

    public Map<String, Object> semBackupPackage(
            ParcelFileDescriptor fd, String[] packageNames, String password, int flag) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.semBackupPackage(fd, packageNames, password, flag);
            } catch (RemoteException e) {
                Log.e(TAG, "semBackupPackage() couldn't connect");
                return null;
            }
        }
        Log.e(TAG, "could not get backup service");
        return null;
    }

    public Map<String, Object> semBackupPackagePath(
            ParcelFileDescriptor fd,
            String[] packageNames,
            String password,
            int flag,
            String[] paths) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.semBackupPackagePath(fd, packageNames, password, flag, paths);
            } catch (RemoteException e) {
                Log.e(TAG, "semBackupPackagePath() couldn't connect");
                return null;
            }
        }
        Log.e(TAG, "could not get backup service");
        return null;
    }

    public void semRestorePackage(ParcelFileDescriptor fd, String password) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.semRestorePackage(fd, password);
                return;
            } catch (RemoteException e) {
                Log.e(TAG, "semRestorePackage() couldn't connect");
                return;
            }
        }
        Log.e(TAG, "could not get backup service");
    }

    public boolean isSubUserSupported() {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.isSubUserSupported();
            } catch (RemoteException e) {
                Log.e(TAG, "isSubUserSupported() couldn't connect");
                return false;
            }
        }
        Log.e(TAG, "could not get backup service");
        return false;
    }

    public boolean semIsBackupEnabled() {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.semIsBackupEnabled();
            } catch (RemoteException e) {
                Log.e(TAG, "semIsBackupEnabled() couldn't connect");
                return false;
            }
        }
        return false;
    }

    public void semSetBackupEnabled(boolean enabled) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.semSetBackupEnabled(enabled);
            } catch (RemoteException e) {
                Log.e(TAG, "semSetBackupEnabled() couldn't connect");
            }
        }
    }

    public void semSetAutoRestoreEnabled(boolean enabled) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.semSetAutoRestoreEnabled(enabled);
            } catch (RemoteException e) {
                Log.e(TAG, "semSetAutoRestoreEnabled() couldn't connect");
            }
        }
    }

    public boolean semCancelBackupAndRestore() {
        checkServiceBinder();
        if (sService == null) {
            return false;
        }
        try {
            boolean result = sService.semCancelBackupAndRestore();
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "semCancelBackupAndRestore() couldn't connect");
            return false;
        }
    }

    public boolean semSetTimeoutBackupAndRestore(int timeoutMin) {
        checkServiceBinder();
        if (sService == null) {
            return false;
        }
        try {
            boolean result = sService.semSetTimeoutBackupAndRestore(timeoutMin);
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "semSetTimeoutBackupAndRestore() couldn't connect");
            return false;
        }
    }

    public boolean semSetTransportFlagsForAdbBackup(int transportFlags) {
        checkServiceBinder();
        if (sService == null) {
            return false;
        }
        try {
            boolean result = sService.semSetTransportFlagsForAdbBackup(transportFlags);
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "semSetTransportFlagsForAdbBackup couldn't connect");
            return false;
        }
    }

    public boolean semDisableDataExtractionRule(boolean disabled) {
        checkServiceBinder();
        if (sService == null) {
            return false;
        }
        try {
            boolean result = sService.semDisableDataExtractionRule(disabled);
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "semDisableDataExtractionRule() couldn't connect");
            return false;
        }
    }

    @SystemApi
    public void excludeKeysFromRestore(String packageName, List<String> keys) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.excludeKeysFromRestore(packageName, keys);
            } catch (RemoteException e) {
                Log.e(TAG, "excludeKeysFromRestore() couldn't connect");
            }
        }
    }

    @SystemApi
    public BackupRestoreEventLogger getBackupRestoreEventLogger(BackupAgent backupAgent) {
        BackupRestoreEventLogger logger = backupAgent.getBackupRestoreEventLogger();
        if (logger == null) {
            throw new IllegalStateException(
                    "Attempting to get logger on an uninitialised BackupAgent");
        }
        return backupAgent.getBackupRestoreEventLogger();
    }

    @SystemApi
    public BackupRestoreEventLogger getDelayedRestoreLogger() {
        return new BackupRestoreEventLogger(1);
    }

    @SystemApi
    public void reportDelayedRestoreResult(BackupRestoreEventLogger logger) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.reportDelayedRestoreResult(
                        this.mContext.getPackageName(), logger.getLoggingResults());
            } catch (RemoteException e) {
                Log.w(TAG, "reportDelayedRestoreResult() couldn't connect");
            }
        }
    }

    private class BackupObserverWrapper extends IBackupObserver.Stub {
        static final int MSG_FINISHED = 3;
        static final int MSG_RESULT = 2;
        static final int MSG_UPDATE = 1;
        final Handler mHandler;
        final BackupObserver mObserver;

        BackupObserverWrapper(Context context, BackupObserver observer) {
            this.mHandler =
                    new Handler(context.getMainLooper()) { // from class:
                        // android.app.backup.BackupManager.BackupObserverWrapper.1
                        @Override // android.os.Handler
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case 1:
                                    Pair<String, BackupProgress> obj = (Pair) msg.obj;
                                    BackupObserverWrapper.this.mObserver.onUpdate(
                                            obj.first, obj.second);
                                    break;
                                case 2:
                                    BackupObserverWrapper.this.mObserver.onResult(
                                            (String) msg.obj, msg.arg1);
                                    break;
                                case 3:
                                    BackupObserverWrapper.this.mObserver.backupFinished(msg.arg1);
                                    break;
                                default:
                                    Log.w(BackupManager.TAG, "Unknown message: " + msg);
                                    break;
                            }
                        }
                    };
            this.mObserver = observer;
        }

        @Override // android.app.backup.IBackupObserver
        public void onUpdate(String currentPackage, BackupProgress backupProgress) {
            this.mHandler.sendMessage(
                    this.mHandler.obtainMessage(1, Pair.create(currentPackage, backupProgress)));
        }

        @Override // android.app.backup.IBackupObserver
        public void onResult(String currentPackage, int status) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(2, status, 0, currentPackage));
        }

        @Override // android.app.backup.IBackupObserver
        public void backupFinished(int status) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, status, 0));
        }
    }

    private class SelectTransportListenerWrapper extends ISelectBackupTransportCallback.Stub {
        private final Handler mHandler;
        private final SelectBackupTransportCallback mListener;

        SelectTransportListenerWrapper(Context context, SelectBackupTransportCallback listener) {
            this.mHandler = new Handler(context.getMainLooper());
            this.mListener = listener;
        }

        @Override // android.app.backup.ISelectBackupTransportCallback
        public void onSuccess(final String transportName) {
            this.mHandler.post(
                    new Runnable() { // from class:
                        // android.app.backup.BackupManager.SelectTransportListenerWrapper.1
                        @Override // java.lang.Runnable
                        public void run() {
                            SelectTransportListenerWrapper.this.mListener.onSuccess(transportName);
                        }
                    });
        }

        @Override // android.app.backup.ISelectBackupTransportCallback
        public void onFailure(final int reason) {
            this.mHandler.post(
                    new Runnable() { // from class:
                        // android.app.backup.BackupManager.SelectTransportListenerWrapper.2
                        @Override // java.lang.Runnable
                        public void run() {
                            SelectTransportListenerWrapper.this.mListener.onFailure(reason);
                        }
                    });
        }
    }
}
