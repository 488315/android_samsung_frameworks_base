package android.app.backup;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.backup.IBackupManager;
import android.app.backup.IBackupObserver;
import android.app.backup.ISelectBackupTransportCallback;
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

/* loaded from: classes.dex */
public class BackupManager {

    @SystemApi
    public static final int ERROR_AGENT_FAILURE = -1003;

    @SystemApi
    public static final int ERROR_BACKUP_CANCELLED = -2003;

    @SystemApi
    public static final int ERROR_BACKUP_NOT_ALLOWED = -2001;

    @SystemApi
    public static final int ERROR_PACKAGE_NOT_FOUND = -2002;

    @SystemApi
    public static final int ERROR_TRANSPORT_ABORTED = -1000;

    @SystemApi
    public static final int ERROR_TRANSPORT_INVALID = -2;

    @SystemApi
    public static final int ERROR_TRANSPORT_PACKAGE_REJECTED = -1002;

    @SystemApi
    public static final int ERROR_TRANSPORT_QUOTA_EXCEEDED = -1005;

    @SystemApi
    public static final int ERROR_TRANSPORT_UNAVAILABLE = -1;
    public static final String EXTRA_BACKUP_SERVICES_AVAILABLE = "backup_services_available";

    @SystemApi
    public static final int FLAG_NON_INCREMENTAL_BACKUP = 1;
    public static final long IS_BACKUP_SERVICE_ACTIVE_ENFORCE_PERMISSION_IN_SERVICE = 158482162;

    @SystemApi
    public static final String PACKAGE_MANAGER_SENTINEL = "@pm@";

    @SystemApi
    public static final int SUCCESS = 0;
    private static final String TAG = "BackupManager";
    public static IBackupManager sService;
    private Context mContext;

    private static void checkServiceBinder() {
        if (sService == null) {
            sService = IBackupManager.Stub.asInterface(ServiceManager.getService(Context.BACKUP_SERVICE));
        }
    }

    public BackupManager(Context context) {
        this.mContext = context;
    }

    public void dataChanged() {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.dataChanged(this.mContext.getPackageName());
            } catch (RemoteException unused) {
                Log.d(TAG, "dataChanged() couldn't connect");
            }
        }
    }

    public static void dataChanged(String str) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.dataChanged(str);
            } catch (RemoteException unused) {
                Log.e(TAG, "dataChanged(pkg) couldn't connect");
            }
        }
    }

    public static void dataChangedForUser(int i, String str) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.dataChangedForUser(i, str);
            } catch (RemoteException unused) {
                Log.e(TAG, "dataChanged(userId,pkg) couldn't connect");
            }
        }
    }

    @Deprecated
    public int requestRestore(RestoreObserver restoreObserver) {
        return requestRestore(restoreObserver, null);
    }

    @SystemApi
    @Deprecated
    public int requestRestore(RestoreObserver restoreObserver, BackupManagerMonitor backupManagerMonitor) {
        Log.w(TAG, "requestRestore(): Since Android P app can no longer request restoring of its backup.");
        return -1;
    }

    @SystemApi
    public RestoreSession beginRestoreSession() {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                IRestoreSession iRestoreSessionBeginRestoreSessionForUser = iBackupManager.beginRestoreSessionForUser(this.mContext.getUserId(), null, null);
                if (iRestoreSessionBeginRestoreSessionForUser != null) {
                    return new RestoreSession(this.mContext, iRestoreSessionBeginRestoreSessionForUser);
                }
            } catch (RemoteException unused) {
                Log.e(TAG, "beginRestoreSession() couldn't connect");
            }
        }
        return null;
    }

    @SystemApi
    public void setBackupEnabled(boolean z) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.setBackupEnabled(z);
            } catch (RemoteException unused) {
                Log.e(TAG, "setBackupEnabled() couldn't connect");
            }
        }
    }

    @SystemApi
    public void setFrameworkSchedulingEnabled(boolean z) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            Log.e(TAG, "setFrameworkSchedulingEnabled() couldn't connect");
            return;
        }
        try {
            iBackupManager.setFrameworkSchedulingEnabledForUser(this.mContext.getUserId(), z);
        } catch (RemoteException unused) {
            Log.e(TAG, "setFrameworkSchedulingEnabled() couldn't connect");
        }
    }

    @SystemApi
    public boolean isBackupEnabled() {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return false;
        }
        try {
            return iBackupManager.isBackupEnabled();
        } catch (RemoteException unused) {
            Log.e(TAG, "isBackupEnabled() couldn't connect");
            return false;
        }
    }

    @SystemApi
    public boolean isBackupServiceActive(UserHandle userHandle) {
        if (!CompatChanges.isChangeEnabled(IS_BACKUP_SERVICE_ACTIVE_ENFORCE_PERMISSION_IN_SERVICE)) {
            this.mContext.enforceCallingOrSelfPermission(Manifest.permission.BACKUP, "isBackupServiceActive");
        }
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return false;
        }
        try {
            return iBackupManager.isBackupServiceActive(userHandle.getIdentifier());
        } catch (RemoteException unused) {
            Log.e(TAG, "isBackupEnabled() couldn't connect");
            return false;
        }
    }

    @SystemApi
    public void setAutoRestore(boolean z) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.setAutoRestore(z);
            } catch (RemoteException unused) {
                Log.e(TAG, "setAutoRestore() couldn't connect");
            }
        }
    }

    @SystemApi
    public String getCurrentTransport() {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return null;
        }
        try {
            return iBackupManager.getCurrentTransport();
        } catch (RemoteException unused) {
            Log.e(TAG, "getCurrentTransport() couldn't connect");
            return null;
        }
    }

    @SystemApi
    public ComponentName getCurrentTransportComponent() {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return null;
        }
        try {
            return iBackupManager.getCurrentTransportComponentForUser(this.mContext.getUserId());
        } catch (RemoteException unused) {
            Log.e(TAG, "getCurrentTransportComponent() couldn't connect");
            return null;
        }
    }

    @SystemApi
    public String[] listAllTransports() {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return null;
        }
        try {
            return iBackupManager.listAllTransports();
        } catch (RemoteException unused) {
            Log.e(TAG, "listAllTransports() couldn't connect");
            return null;
        }
    }

    @SystemApi
    @Deprecated
    public void updateTransportAttributes(ComponentName componentName, String str, Intent intent, String str2, Intent intent2, String str3) {
        updateTransportAttributes(componentName, str, intent, str2, intent2, (CharSequence) str3);
    }

    @SystemApi
    public void updateTransportAttributes(ComponentName componentName, String str, Intent intent, String str2, Intent intent2, CharSequence charSequence) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.updateTransportAttributesForUser(this.mContext.getUserId(), componentName, str, intent, str2, intent2, charSequence);
            } catch (RemoteException unused) {
                Log.e(TAG, "describeTransport() couldn't connect");
            }
        }
    }

    @SystemApi
    @Deprecated
    public String selectBackupTransport(String str) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return null;
        }
        try {
            return iBackupManager.selectBackupTransport(str);
        } catch (RemoteException unused) {
            Log.e(TAG, "selectBackupTransport() couldn't connect");
            return null;
        }
    }

    @SystemApi
    public void selectBackupTransport(ComponentName componentName, SelectBackupTransportCallback selectBackupTransportCallback) {
        SelectTransportListenerWrapper selectTransportListenerWrapper;
        checkServiceBinder();
        if (sService != null) {
            if (selectBackupTransportCallback == null) {
                selectTransportListenerWrapper = null;
            } else {
                try {
                    selectTransportListenerWrapper = new SelectTransportListenerWrapper(this, this.mContext, selectBackupTransportCallback);
                } catch (RemoteException unused) {
                    Log.e(TAG, "selectBackupTransportAsync() couldn't connect");
                    return;
                }
            }
            sService.selectBackupTransportAsyncForUser(this.mContext.getUserId(), componentName, selectTransportListenerWrapper);
        }
    }

    @SystemApi
    public void backupNow() {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.backupNow();
            } catch (RemoteException unused) {
                Log.e(TAG, "backupNow() couldn't connect");
            }
        }
    }

    @SystemApi
    public long getAvailableRestoreToken(String str) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return 0L;
        }
        try {
            return iBackupManager.getAvailableRestoreTokenForUser(this.mContext.getUserId(), str);
        } catch (RemoteException unused) {
            Log.e(TAG, "getAvailableRestoreToken() couldn't connect");
            return 0L;
        }
    }

    @SystemApi
    public boolean isAppEligibleForBackup(String str) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return false;
        }
        try {
            return iBackupManager.isAppEligibleForBackupForUser(this.mContext.getUserId(), str);
        } catch (RemoteException unused) {
            Log.e(TAG, "isAppEligibleForBackup(pkg) couldn't connect");
            return false;
        }
    }

    @SystemApi
    public int requestBackup(String[] strArr, BackupObserver backupObserver) {
        return requestBackup(strArr, backupObserver, null, 0);
    }

    @SystemApi
    public int requestBackup(String[] strArr, BackupObserver backupObserver, BackupManagerMonitor backupManagerMonitor, int i) {
        BackupObserverWrapper backupObserverWrapper;
        checkServiceBinder();
        if (sService == null) {
            return -1;
        }
        BackupManagerMonitorWrapper backupManagerMonitorWrapper = null;
        if (backupObserver == null) {
            backupObserverWrapper = null;
        } else {
            try {
                backupObserverWrapper = new BackupObserverWrapper(this, this.mContext, backupObserver);
            } catch (RemoteException unused) {
                Log.e(TAG, "requestBackup() couldn't connect");
                return -1;
            }
        }
        if (backupManagerMonitor != null) {
            backupManagerMonitorWrapper = new BackupManagerMonitorWrapper(backupManagerMonitor);
        }
        return sService.requestBackup(strArr, backupObserverWrapper, backupManagerMonitorWrapper, i);
    }

    @SystemApi
    public void cancelBackups() {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.cancelBackups();
            } catch (RemoteException unused) {
                Log.e(TAG, "cancelBackups() couldn't connect.");
            }
        }
    }

    public UserHandle getUserForAncestralSerialNumber(long j) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return null;
        }
        try {
            return iBackupManager.getUserForAncestralSerialNumber(j);
        } catch (RemoteException unused) {
            Log.e(TAG, "getUserForAncestralSerialNumber() couldn't connect");
            return null;
        }
    }

    @SystemApi
    public void setAncestralSerialNumber(long j) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.setAncestralSerialNumber(j);
            } catch (RemoteException unused) {
                Log.e(TAG, "setAncestralSerialNumber() couldn't connect");
            }
        }
    }

    @SystemApi
    public Intent getConfigurationIntent(String str) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return null;
        }
        try {
            return iBackupManager.getConfigurationIntentForUser(this.mContext.getUserId(), str);
        } catch (RemoteException unused) {
            Log.e(TAG, "getConfigurationIntent() couldn't connect");
            return null;
        }
    }

    @SystemApi
    public String getDestinationString(String str) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return null;
        }
        try {
            return iBackupManager.getDestinationStringForUser(this.mContext.getUserId(), str);
        } catch (RemoteException unused) {
            Log.e(TAG, "getDestinationString() couldn't connect");
            return null;
        }
    }

    @SystemApi
    public Intent getDataManagementIntent(String str) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return null;
        }
        try {
            return iBackupManager.getDataManagementIntentForUser(this.mContext.getUserId(), str);
        } catch (RemoteException unused) {
            Log.e(TAG, "getDataManagementIntent() couldn't connect");
            return null;
        }
    }

    @SystemApi
    @Deprecated
    public String getDataManagementLabel(String str) {
        CharSequence dataManagementIntentLabel = getDataManagementIntentLabel(str);
        if (dataManagementIntentLabel == null) {
            return null;
        }
        return dataManagementIntentLabel.toString();
    }

    @SystemApi
    public CharSequence getDataManagementIntentLabel(String str) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return null;
        }
        try {
            return iBackupManager.getDataManagementLabelForUser(this.mContext.getUserId(), str);
        } catch (RemoteException unused) {
            Log.e(TAG, "getDataManagementIntentLabel() couldn't connect");
            return null;
        }
    }

    public Map<String, Object> semBackupPackage(ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                return iBackupManager.semBackupPackage(parcelFileDescriptor, strArr, str, i);
            } catch (RemoteException unused) {
                Log.e(TAG, "semBackupPackage() couldn't connect");
                return null;
            }
        }
        Log.e(TAG, "could not get backup service");
        return null;
    }

    public Map<String, Object> semBackupPackagePath(ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i, String[] strArr2) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                return iBackupManager.semBackupPackagePath(parcelFileDescriptor, strArr, str, i, strArr2);
            } catch (RemoteException unused) {
                Log.e(TAG, "semBackupPackagePath() couldn't connect");
                return null;
            }
        }
        Log.e(TAG, "could not get backup service");
        return null;
    }

    public void semRestorePackage(ParcelFileDescriptor parcelFileDescriptor, String str) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.semRestorePackage(parcelFileDescriptor, str);
                return;
            } catch (RemoteException unused) {
                Log.e(TAG, "semRestorePackage() couldn't connect");
                return;
            }
        }
        Log.e(TAG, "could not get backup service");
    }

    public boolean isSubUserSupported() {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                return iBackupManager.isSubUserSupported();
            } catch (RemoteException unused) {
                Log.e(TAG, "isSubUserSupported() couldn't connect");
                return false;
            }
        }
        Log.e(TAG, "could not get backup service");
        return false;
    }

    public boolean semIsBackupEnabled() {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return false;
        }
        try {
            return iBackupManager.semIsBackupEnabled();
        } catch (RemoteException unused) {
            Log.e(TAG, "semIsBackupEnabled() couldn't connect");
            return false;
        }
    }

    public void semSetBackupEnabled(boolean z) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.semSetBackupEnabled(z);
            } catch (RemoteException unused) {
                Log.e(TAG, "semSetBackupEnabled() couldn't connect");
            }
        }
    }

    public void semSetAutoRestoreEnabled(boolean z) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.semSetAutoRestoreEnabled(z);
            } catch (RemoteException unused) {
                Log.e(TAG, "semSetAutoRestoreEnabled() couldn't connect");
            }
        }
    }

    public boolean semCancelBackupAndRestore() {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return false;
        }
        try {
            return iBackupManager.semCancelBackupAndRestore();
        } catch (RemoteException unused) {
            Log.e(TAG, "semCancelBackupAndRestore() couldn't connect");
            return false;
        }
    }

    public boolean semSetTimeoutBackupAndRestore(int i) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return false;
        }
        try {
            return iBackupManager.semSetTimeoutBackupAndRestore(i);
        } catch (RemoteException unused) {
            Log.e(TAG, "semSetTimeoutBackupAndRestore() couldn't connect");
            return false;
        }
    }

    public boolean semSetTransportFlagsForAdbBackup(int i) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return false;
        }
        try {
            return iBackupManager.semSetTransportFlagsForAdbBackup(i);
        } catch (RemoteException unused) {
            Log.e(TAG, "semSetTransportFlagsForAdbBackup couldn't connect");
            return false;
        }
    }

    public boolean semDisableDataExtractionRule(boolean z) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager == null) {
            return false;
        }
        try {
            return iBackupManager.semDisableDataExtractionRule(z);
        } catch (RemoteException unused) {
            Log.e(TAG, "semDisableDataExtractionRule() couldn't connect");
            return false;
        }
    }

    public void semDisableRestrictedModeForAdbBackUp(boolean z) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.semDisableRestrictedModeForAdbBackUp(z);
            } catch (RemoteException unused) {
                Log.e(TAG, "semSetRestrictedModeForAdbBackUp() couldn't connect");
            }
        }
    }

    @SystemApi
    public void excludeKeysFromRestore(String str, List<String> list) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.excludeKeysFromRestore(str, list);
            } catch (RemoteException unused) {
                Log.e(TAG, "excludeKeysFromRestore() couldn't connect");
            }
        }
    }

    @SystemApi
    public BackupRestoreEventLogger getBackupRestoreEventLogger(BackupAgent backupAgent) {
        if (backupAgent.getBackupRestoreEventLogger() == null) {
            throw new IllegalStateException("Attempting to get logger on an uninitialised BackupAgent");
        }
        return backupAgent.getBackupRestoreEventLogger();
    }

    @SystemApi
    public BackupRestoreEventLogger getDelayedRestoreLogger() {
        return new BackupRestoreEventLogger(1);
    }

    @SystemApi
    public void reportDelayedRestoreResult(BackupRestoreEventLogger backupRestoreEventLogger) {
        checkServiceBinder();
        IBackupManager iBackupManager = sService;
        if (iBackupManager != null) {
            try {
                iBackupManager.reportDelayedRestoreResult(this.mContext.getPackageName(), backupRestoreEventLogger.getLoggingResults());
            } catch (RemoteException unused) {
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

        BackupObserverWrapper(final BackupManager backupManager, Context context, BackupObserver backupObserver) {
            this.mHandler = new Handler(context.getMainLooper()) { // from class: android.app.backup.BackupManager.BackupObserverWrapper.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    int i = message.what;
                    if (i == 1) {
                        Pair pair = (Pair) message.obj;
                        BackupObserverWrapper.this.mObserver.onUpdate((String) pair.first, (BackupProgress) pair.second);
                    } else if (i == 2) {
                        BackupObserverWrapper.this.mObserver.onResult((String) message.obj, message.arg1);
                    } else {
                        if (i == 3) {
                            BackupObserverWrapper.this.mObserver.backupFinished(message.arg1);
                            return;
                        }
                        Log.w(BackupManager.TAG, "Unknown message: " + message);
                    }
                }
            };
            this.mObserver = backupObserver;
        }

        @Override // android.app.backup.IBackupObserver
        public void onUpdate(String str, BackupProgress backupProgress) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(1, Pair.create(str, backupProgress)));
        }

        @Override // android.app.backup.IBackupObserver
        public void onResult(String str, int i) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(2, i, 0, str));
        }

        @Override // android.app.backup.IBackupObserver
        public void backupFinished(int i) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(3, i, 0));
        }
    }

    private class SelectTransportListenerWrapper extends ISelectBackupTransportCallback.Stub {
        private final Handler mHandler;
        private final SelectBackupTransportCallback mListener;

        SelectTransportListenerWrapper(BackupManager backupManager, Context context, SelectBackupTransportCallback selectBackupTransportCallback) {
            this.mHandler = new Handler(context.getMainLooper());
            this.mListener = selectBackupTransportCallback;
        }

        @Override // android.app.backup.ISelectBackupTransportCallback
        public void onSuccess(final String str) {
            this.mHandler.post(new Runnable() { // from class: android.app.backup.BackupManager.SelectTransportListenerWrapper.1
                @Override // java.lang.Runnable
                public void run() {
                    SelectTransportListenerWrapper.this.mListener.onSuccess(str);
                }
            });
        }

        @Override // android.app.backup.ISelectBackupTransportCallback
        public void onFailure(final int i) {
            this.mHandler.post(new Runnable() { // from class: android.app.backup.BackupManager.SelectTransportListenerWrapper.2
                @Override // java.lang.Runnable
                public void run() {
                    SelectTransportListenerWrapper.this.mListener.onFailure(i);
                }
            });
        }
    }
}
