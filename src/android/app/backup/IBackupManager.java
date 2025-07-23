package android.app.backup;

import android.app.backup.BackupRestoreEventLogger;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IBackupObserver;
import android.app.backup.IFullBackupRestoreObserver;
import android.app.backup.IMemorySaverBackupRestoreObserver;
import android.app.backup.IRestoreSession;
import android.app.backup.ISelectBackupTransportCallback;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public interface IBackupManager extends IInterface {

    public static class Default implements IBackupManager {
        @Override // android.app.backup.IBackupManager
        public void acknowledgeFullBackupOrRestore(int i, boolean z, String str, String str2, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void acknowledgeFullBackupOrRestoreForUser(int i, int i2, boolean z, String str, String str2, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void adbBackup(int i, ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, String[] strArr) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void adbRestore(int i, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public void backupNow() throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void backupNowForUser(int i) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public IRestoreSession beginRestoreSessionForUser(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public void cancelBackups() throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void cancelBackupsForUser(int i) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void clearBackupData(String str, String str2) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void clearBackupDataForUser(int i, String str, String str2) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void dataChanged(String str) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void dataChangedForUser(int i, String str) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void excludeKeysFromRestore(String str, List<String> list) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public String[] filterAppsEligibleForBackupForUser(int i, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public void fullBackupCustomized(int i, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, String[] strArr, boolean z8, String str2, boolean z9, IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void fullRestoreCustomized(int i, String str, boolean z, String str2, IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void fullTransportBackupForUser(int i, String[] strArr) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public long getAvailableRestoreTokenForUser(int i, String str) throws RemoteException {
            return 0L;
        }

        @Override // android.app.backup.IBackupManager
        public Intent getConfigurationIntent(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public Intent getConfigurationIntentForUser(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public String getCurrentTransport() throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public ComponentName getCurrentTransportComponentForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public String getCurrentTransportForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public Intent getDataManagementIntent(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public Intent getDataManagementIntentForUser(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public CharSequence getDataManagementLabelForUser(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public String getDestinationString(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public String getDestinationStringForUser(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public String[] getTransportWhitelist() throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public UserHandle getUserForAncestralSerialNumber(long j) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public boolean hasBackupPassword() throws RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public void initializeTransportsForUser(int i, String[] strArr, IBackupObserver iBackupObserver) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public boolean isAppEligibleForBackupForUser(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public boolean isBackupEnabled() throws RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public boolean isBackupEnabledForUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public boolean isBackupServiceActive(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public boolean isSubUserSupported() throws RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public boolean isUserReadyForBackup(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public ComponentName[] listAllTransportComponentsForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public String[] listAllTransports() throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public String[] listAllTransportsForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public void opComplete(int i, long j) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void opCompleteForUser(int i, int i2, long j) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void reportDelayedRestoreResult(String str, List<BackupRestoreEventLogger.DataTypeResult> list) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public int requestBackup(String[] strArr, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.backup.IBackupManager
        public int requestBackupForUser(int i, String[] strArr, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.app.backup.IBackupManager
        public void restoreAtInstall(String str, int i) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void restoreAtInstallForUser(int i, String str, int i2) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public String selectBackupTransport(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public void selectBackupTransportAsyncForUser(int i, ComponentName componentName, ISelectBackupTransportCallback iSelectBackupTransportCallback) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public String selectBackupTransportForUser(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public Map semBackupPackage(ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public Map semBackupPackagePath(ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i, String[] strArr2) throws RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public boolean semCancelBackupAndRestore() throws RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public boolean semDisableDataExtractionRule(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public void semDisableRestrictedModeForAdbBackUp(boolean z) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public boolean semIsBackupEnabled() throws RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public void semRestorePackage(ParcelFileDescriptor parcelFileDescriptor, String str) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void semSetAutoRestoreEnabled(boolean z) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void semSetBackupEnabled(boolean z) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public boolean semSetTimeoutBackupAndRestore(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public boolean semSetTransportFlagsForAdbBackup(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public void setAncestralSerialNumber(long j) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void setAutoRestore(boolean z) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void setAutoRestoreForUser(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void setBackupEnabled(boolean z) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void setBackupEnabledForUser(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public boolean setBackupPassword(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public void setBackupServiceActive(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void setFrameworkSchedulingEnabledForUser(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void updateTransportAttributesForUser(int i, ComponentName componentName, String str, Intent intent, String str2, Intent intent2, CharSequence charSequence) throws RemoteException {
        }
    }

    void acknowledgeFullBackupOrRestore(int i, boolean z, String str, String str2, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException;

    void acknowledgeFullBackupOrRestoreForUser(int i, int i2, boolean z, String str, String str2, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException;

    void adbBackup(int i, ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, String[] strArr) throws RemoteException;

    void adbRestore(int i, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void backupNow() throws RemoteException;

    void backupNowForUser(int i) throws RemoteException;

    IRestoreSession beginRestoreSessionForUser(int i, String str, String str2) throws RemoteException;

    void cancelBackups() throws RemoteException;

    void cancelBackupsForUser(int i) throws RemoteException;

    void clearBackupData(String str, String str2) throws RemoteException;

    void clearBackupDataForUser(int i, String str, String str2) throws RemoteException;

    void dataChanged(String str) throws RemoteException;

    void dataChangedForUser(int i, String str) throws RemoteException;

    void excludeKeysFromRestore(String str, List<String> list) throws RemoteException;

    String[] filterAppsEligibleForBackupForUser(int i, String[] strArr) throws RemoteException;

    void fullBackupCustomized(int i, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, String[] strArr, boolean z8, String str2, boolean z9, IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) throws RemoteException;

    void fullRestoreCustomized(int i, String str, boolean z, String str2, IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) throws RemoteException;

    void fullTransportBackupForUser(int i, String[] strArr) throws RemoteException;

    long getAvailableRestoreTokenForUser(int i, String str) throws RemoteException;

    Intent getConfigurationIntent(String str) throws RemoteException;

    Intent getConfigurationIntentForUser(int i, String str) throws RemoteException;

    String getCurrentTransport() throws RemoteException;

    ComponentName getCurrentTransportComponentForUser(int i) throws RemoteException;

    String getCurrentTransportForUser(int i) throws RemoteException;

    Intent getDataManagementIntent(String str) throws RemoteException;

    Intent getDataManagementIntentForUser(int i, String str) throws RemoteException;

    CharSequence getDataManagementLabelForUser(int i, String str) throws RemoteException;

    String getDestinationString(String str) throws RemoteException;

    String getDestinationStringForUser(int i, String str) throws RemoteException;

    String[] getTransportWhitelist() throws RemoteException;

    UserHandle getUserForAncestralSerialNumber(long j) throws RemoteException;

    boolean hasBackupPassword() throws RemoteException;

    void initializeTransportsForUser(int i, String[] strArr, IBackupObserver iBackupObserver) throws RemoteException;

    boolean isAppEligibleForBackupForUser(int i, String str) throws RemoteException;

    boolean isBackupEnabled() throws RemoteException;

    boolean isBackupEnabledForUser(int i) throws RemoteException;

    boolean isBackupServiceActive(int i) throws RemoteException;

    boolean isSubUserSupported() throws RemoteException;

    boolean isUserReadyForBackup(int i) throws RemoteException;

    ComponentName[] listAllTransportComponentsForUser(int i) throws RemoteException;

    String[] listAllTransports() throws RemoteException;

    String[] listAllTransportsForUser(int i) throws RemoteException;

    void opComplete(int i, long j) throws RemoteException;

    void opCompleteForUser(int i, int i2, long j) throws RemoteException;

    void reportDelayedRestoreResult(String str, List<BackupRestoreEventLogger.DataTypeResult> list) throws RemoteException;

    int requestBackup(String[] strArr, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int i) throws RemoteException;

    int requestBackupForUser(int i, String[] strArr, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int i2) throws RemoteException;

    void restoreAtInstall(String str, int i) throws RemoteException;

    void restoreAtInstallForUser(int i, String str, int i2) throws RemoteException;

    String selectBackupTransport(String str) throws RemoteException;

    void selectBackupTransportAsyncForUser(int i, ComponentName componentName, ISelectBackupTransportCallback iSelectBackupTransportCallback) throws RemoteException;

    String selectBackupTransportForUser(int i, String str) throws RemoteException;

    Map semBackupPackage(ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i) throws RemoteException;

    Map semBackupPackagePath(ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i, String[] strArr2) throws RemoteException;

    boolean semCancelBackupAndRestore() throws RemoteException;

    boolean semDisableDataExtractionRule(boolean z) throws RemoteException;

    void semDisableRestrictedModeForAdbBackUp(boolean z) throws RemoteException;

    boolean semIsBackupEnabled() throws RemoteException;

    void semRestorePackage(ParcelFileDescriptor parcelFileDescriptor, String str) throws RemoteException;

    void semSetAutoRestoreEnabled(boolean z) throws RemoteException;

    void semSetBackupEnabled(boolean z) throws RemoteException;

    boolean semSetTimeoutBackupAndRestore(int i) throws RemoteException;

    boolean semSetTransportFlagsForAdbBackup(int i) throws RemoteException;

    void setAncestralSerialNumber(long j) throws RemoteException;

    void setAutoRestore(boolean z) throws RemoteException;

    void setAutoRestoreForUser(int i, boolean z) throws RemoteException;

    void setBackupEnabled(boolean z) throws RemoteException;

    void setBackupEnabledForUser(int i, boolean z) throws RemoteException;

    boolean setBackupPassword(String str, String str2) throws RemoteException;

    void setBackupServiceActive(int i, boolean z) throws RemoteException;

    void setFrameworkSchedulingEnabledForUser(int i, boolean z) throws RemoteException;

    void updateTransportAttributesForUser(int i, ComponentName componentName, String str, Intent intent, String str2, Intent intent2, CharSequence charSequence) throws RemoteException;

    public static abstract class Stub extends Binder implements IBackupManager {
        public static final String DESCRIPTOR = "android.app.backup.IBackupManager";
        static final int TRANSACTION_acknowledgeFullBackupOrRestore = 24;
        static final int TRANSACTION_acknowledgeFullBackupOrRestoreForUser = 23;
        static final int TRANSACTION_adbBackup = 19;
        static final int TRANSACTION_adbRestore = 22;
        static final int TRANSACTION_backupNow = 18;
        static final int TRANSACTION_backupNowForUser = 17;
        static final int TRANSACTION_beginRestoreSessionForUser = 43;
        static final int TRANSACTION_cancelBackups = 55;
        static final int TRANSACTION_cancelBackupsForUser = 54;
        static final int TRANSACTION_clearBackupData = 4;
        static final int TRANSACTION_clearBackupDataForUser = 3;
        static final int TRANSACTION_dataChanged = 2;
        static final int TRANSACTION_dataChangedForUser = 1;
        static final int TRANSACTION_excludeKeysFromRestore = 71;
        static final int TRANSACTION_filterAppsEligibleForBackupForUser = 51;
        static final int TRANSACTION_fullBackupCustomized = 56;
        static final int TRANSACTION_fullRestoreCustomized = 21;
        static final int TRANSACTION_fullTransportBackupForUser = 20;
        static final int TRANSACTION_getAvailableRestoreTokenForUser = 49;
        static final int TRANSACTION_getConfigurationIntent = 37;
        static final int TRANSACTION_getConfigurationIntentForUser = 36;
        static final int TRANSACTION_getCurrentTransport = 27;
        static final int TRANSACTION_getCurrentTransportComponentForUser = 28;
        static final int TRANSACTION_getCurrentTransportForUser = 26;
        static final int TRANSACTION_getDataManagementIntent = 41;
        static final int TRANSACTION_getDataManagementIntentForUser = 40;
        static final int TRANSACTION_getDataManagementLabelForUser = 42;
        static final int TRANSACTION_getDestinationString = 39;
        static final int TRANSACTION_getDestinationStringForUser = 38;
        static final int TRANSACTION_getTransportWhitelist = 32;
        static final int TRANSACTION_getUserForAncestralSerialNumber = 57;
        static final int TRANSACTION_hasBackupPassword = 16;
        static final int TRANSACTION_initializeTransportsForUser = 5;
        static final int TRANSACTION_isAppEligibleForBackupForUser = 50;
        static final int TRANSACTION_isBackupEnabled = 14;
        static final int TRANSACTION_isBackupEnabledForUser = 13;
        static final int TRANSACTION_isBackupServiceActive = 47;
        static final int TRANSACTION_isSubUserSupported = 59;
        static final int TRANSACTION_isUserReadyForBackup = 48;
        static final int TRANSACTION_listAllTransportComponentsForUser = 31;
        static final int TRANSACTION_listAllTransports = 30;
        static final int TRANSACTION_listAllTransportsForUser = 29;
        static final int TRANSACTION_opComplete = 45;
        static final int TRANSACTION_opCompleteForUser = 44;
        static final int TRANSACTION_reportDelayedRestoreResult = 72;
        static final int TRANSACTION_requestBackup = 53;
        static final int TRANSACTION_requestBackupForUser = 52;
        static final int TRANSACTION_restoreAtInstall = 7;
        static final int TRANSACTION_restoreAtInstallForUser = 6;
        static final int TRANSACTION_selectBackupTransport = 34;
        static final int TRANSACTION_selectBackupTransportAsyncForUser = 35;
        static final int TRANSACTION_selectBackupTransportForUser = 33;
        static final int TRANSACTION_semBackupPackage = 60;
        static final int TRANSACTION_semBackupPackagePath = 61;
        static final int TRANSACTION_semCancelBackupAndRestore = 66;
        static final int TRANSACTION_semDisableDataExtractionRule = 69;
        static final int TRANSACTION_semDisableRestrictedModeForAdbBackUp = 70;
        static final int TRANSACTION_semIsBackupEnabled = 63;
        static final int TRANSACTION_semRestorePackage = 62;
        static final int TRANSACTION_semSetAutoRestoreEnabled = 65;
        static final int TRANSACTION_semSetBackupEnabled = 64;
        static final int TRANSACTION_semSetTimeoutBackupAndRestore = 67;
        static final int TRANSACTION_semSetTransportFlagsForAdbBackup = 68;
        static final int TRANSACTION_setAncestralSerialNumber = 58;
        static final int TRANSACTION_setAutoRestore = 12;
        static final int TRANSACTION_setAutoRestoreForUser = 11;
        static final int TRANSACTION_setBackupEnabled = 10;
        static final int TRANSACTION_setBackupEnabledForUser = 8;
        static final int TRANSACTION_setBackupPassword = 15;
        static final int TRANSACTION_setBackupServiceActive = 46;
        static final int TRANSACTION_setFrameworkSchedulingEnabledForUser = 9;
        static final int TRANSACTION_updateTransportAttributesForUser = 25;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 71;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBackupManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBackupManager)) {
                return (IBackupManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dataChangedForUser";
                case 2:
                    return "dataChanged";
                case 3:
                    return "clearBackupDataForUser";
                case 4:
                    return "clearBackupData";
                case 5:
                    return "initializeTransportsForUser";
                case 6:
                    return "restoreAtInstallForUser";
                case 7:
                    return "restoreAtInstall";
                case 8:
                    return "setBackupEnabledForUser";
                case 9:
                    return "setFrameworkSchedulingEnabledForUser";
                case 10:
                    return "setBackupEnabled";
                case 11:
                    return "setAutoRestoreForUser";
                case 12:
                    return "setAutoRestore";
                case 13:
                    return "isBackupEnabledForUser";
                case 14:
                    return "isBackupEnabled";
                case 15:
                    return "setBackupPassword";
                case 16:
                    return "hasBackupPassword";
                case 17:
                    return "backupNowForUser";
                case 18:
                    return "backupNow";
                case 19:
                    return "adbBackup";
                case 20:
                    return "fullTransportBackupForUser";
                case 21:
                    return "fullRestoreCustomized";
                case 22:
                    return "adbRestore";
                case 23:
                    return "acknowledgeFullBackupOrRestoreForUser";
                case 24:
                    return "acknowledgeFullBackupOrRestore";
                case 25:
                    return "updateTransportAttributesForUser";
                case 26:
                    return "getCurrentTransportForUser";
                case 27:
                    return "getCurrentTransport";
                case 28:
                    return "getCurrentTransportComponentForUser";
                case 29:
                    return "listAllTransportsForUser";
                case 30:
                    return "listAllTransports";
                case 31:
                    return "listAllTransportComponentsForUser";
                case 32:
                    return "getTransportWhitelist";
                case 33:
                    return "selectBackupTransportForUser";
                case 34:
                    return "selectBackupTransport";
                case 35:
                    return "selectBackupTransportAsyncForUser";
                case 36:
                    return "getConfigurationIntentForUser";
                case 37:
                    return "getConfigurationIntent";
                case 38:
                    return "getDestinationStringForUser";
                case 39:
                    return "getDestinationString";
                case 40:
                    return "getDataManagementIntentForUser";
                case 41:
                    return "getDataManagementIntent";
                case 42:
                    return "getDataManagementLabelForUser";
                case 43:
                    return "beginRestoreSessionForUser";
                case 44:
                    return "opCompleteForUser";
                case 45:
                    return "opComplete";
                case 46:
                    return "setBackupServiceActive";
                case 47:
                    return "isBackupServiceActive";
                case 48:
                    return "isUserReadyForBackup";
                case 49:
                    return "getAvailableRestoreTokenForUser";
                case 50:
                    return "isAppEligibleForBackupForUser";
                case 51:
                    return "filterAppsEligibleForBackupForUser";
                case 52:
                    return "requestBackupForUser";
                case 53:
                    return "requestBackup";
                case 54:
                    return "cancelBackupsForUser";
                case 55:
                    return "cancelBackups";
                case 56:
                    return "fullBackupCustomized";
                case 57:
                    return "getUserForAncestralSerialNumber";
                case 58:
                    return "setAncestralSerialNumber";
                case 59:
                    return "isSubUserSupported";
                case 60:
                    return "semBackupPackage";
                case 61:
                    return "semBackupPackagePath";
                case 62:
                    return "semRestorePackage";
                case 63:
                    return "semIsBackupEnabled";
                case 64:
                    return "semSetBackupEnabled";
                case 65:
                    return "semSetAutoRestoreEnabled";
                case 66:
                    return "semCancelBackupAndRestore";
                case 67:
                    return "semSetTimeoutBackupAndRestore";
                case 68:
                    return "semSetTransportFlagsForAdbBackup";
                case 69:
                    return "semDisableDataExtractionRule";
                case 70:
                    return "semDisableRestrictedModeForAdbBackUp";
                case 71:
                    return "excludeKeysFromRestore";
                case 72:
                    return "reportDelayedRestoreResult";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dataChangedForUser(readInt, readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dataChanged(readString2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearBackupDataForUser(readInt2, readString3, readString4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearBackupData(readString5, readString6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    IBackupObserver asInterface = IBackupObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    initializeTransportsForUser(readInt3, createStringArray, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    String readString7 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreAtInstallForUser(readInt4, readString7, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString8 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreAtInstall(readString8, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBackupEnabledForUser(readInt7, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt8 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFrameworkSchedulingEnabledForUser(readInt8, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBackupEnabled(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt9 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoRestoreForUser(readInt9, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoRestore(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isBackupEnabledForUser = isBackupEnabledForUser(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackupEnabledForUser);
                    return true;
                case 14:
                    boolean isBackupEnabled = isBackupEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackupEnabled);
                    return true;
                case 15:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean backupPassword = setBackupPassword(readString9, readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(backupPassword);
                    return true;
                case 16:
                    boolean hasBackupPassword = hasBackupPassword();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasBackupPassword);
                    return true;
                case 17:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    backupNowForUser(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    backupNow();
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt12 = parcel.readInt();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    boolean readBoolean7 = parcel.readBoolean();
                    boolean readBoolean8 = parcel.readBoolean();
                    boolean readBoolean9 = parcel.readBoolean();
                    boolean readBoolean10 = parcel.readBoolean();
                    boolean readBoolean11 = parcel.readBoolean();
                    boolean readBoolean12 = parcel.readBoolean();
                    boolean readBoolean13 = parcel.readBoolean();
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    adbBackup(readInt12, parcelFileDescriptor, readBoolean6, readBoolean7, readBoolean8, readBoolean9, readBoolean10, readBoolean11, readBoolean12, readBoolean13, createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt13 = parcel.readInt();
                    String[] createStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    fullTransportBackupForUser(readInt13, createStringArray3);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt14 = parcel.readInt();
                    String readString11 = parcel.readString();
                    boolean readBoolean14 = parcel.readBoolean();
                    String readString12 = parcel.readString();
                    IMemorySaverBackupRestoreObserver asInterface2 = IMemorySaverBackupRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    fullRestoreCustomized(readInt14, readString11, readBoolean14, readString12, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt15 = parcel.readInt();
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    adbRestore(readInt15, parcelFileDescriptor2);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    boolean readBoolean15 = parcel.readBoolean();
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    IFullBackupRestoreObserver asInterface3 = IFullBackupRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acknowledgeFullBackupOrRestoreForUser(readInt16, readInt17, readBoolean15, readString13, readString14, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt18 = parcel.readInt();
                    boolean readBoolean16 = parcel.readBoolean();
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    IFullBackupRestoreObserver asInterface4 = IFullBackupRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acknowledgeFullBackupOrRestore(readInt18, readBoolean16, readString15, readString16, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt19 = parcel.readInt();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString17 = parcel.readString();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString18 = parcel.readString();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    updateTransportAttributesForUser(readInt19, componentName, readString17, intent, readString18, intent2, charSequence);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String currentTransportForUser = getCurrentTransportForUser(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeString(currentTransportForUser);
                    return true;
                case 27:
                    String currentTransport = getCurrentTransport();
                    parcel2.writeNoException();
                    parcel2.writeString(currentTransport);
                    return true;
                case 28:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName currentTransportComponentForUser = getCurrentTransportComponentForUser(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentTransportComponentForUser, 1);
                    return true;
                case 29:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] listAllTransportsForUser = listAllTransportsForUser(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(listAllTransportsForUser);
                    return true;
                case 30:
                    String[] listAllTransports = listAllTransports();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(listAllTransports);
                    return true;
                case 31:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName[] listAllTransportComponentsForUser = listAllTransportComponentsForUser(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listAllTransportComponentsForUser, 1);
                    return true;
                case 32:
                    String[] transportWhitelist = getTransportWhitelist();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(transportWhitelist);
                    return true;
                case 33:
                    int readInt24 = parcel.readInt();
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String selectBackupTransportForUser = selectBackupTransportForUser(readInt24, readString19);
                    parcel2.writeNoException();
                    parcel2.writeString(selectBackupTransportForUser);
                    return true;
                case 34:
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String selectBackupTransport = selectBackupTransport(readString20);
                    parcel2.writeNoException();
                    parcel2.writeString(selectBackupTransport);
                    return true;
                case 35:
                    int readInt25 = parcel.readInt();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ISelectBackupTransportCallback asInterface5 = ISelectBackupTransportCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    selectBackupTransportAsyncForUser(readInt25, componentName2, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt26 = parcel.readInt();
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent configurationIntentForUser = getConfigurationIntentForUser(readInt26, readString21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configurationIntentForUser, 1);
                    return true;
                case 37:
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent configurationIntent = getConfigurationIntent(readString22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configurationIntent, 1);
                    return true;
                case 38:
                    int readInt27 = parcel.readInt();
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String destinationStringForUser = getDestinationStringForUser(readInt27, readString23);
                    parcel2.writeNoException();
                    parcel2.writeString(destinationStringForUser);
                    return true;
                case 39:
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String destinationString = getDestinationString(readString24);
                    parcel2.writeNoException();
                    parcel2.writeString(destinationString);
                    return true;
                case 40:
                    int readInt28 = parcel.readInt();
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent dataManagementIntentForUser = getDataManagementIntentForUser(readInt28, readString25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dataManagementIntentForUser, 1);
                    return true;
                case 41:
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent dataManagementIntent = getDataManagementIntent(readString26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dataManagementIntent, 1);
                    return true;
                case 42:
                    int readInt29 = parcel.readInt();
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CharSequence dataManagementLabelForUser = getDataManagementLabelForUser(readInt29, readString27);
                    parcel2.writeNoException();
                    if (dataManagementLabelForUser != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(dataManagementLabelForUser, parcel2, 1);
                        return true;
                    }
                    parcel2.writeInt(0);
                    return true;
                case 43:
                    int readInt30 = parcel.readInt();
                    String readString28 = parcel.readString();
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IRestoreSession beginRestoreSessionForUser = beginRestoreSessionForUser(readInt30, readString28, readString29);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(beginRestoreSessionForUser);
                    return true;
                case 44:
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    opCompleteForUser(readInt31, readInt32, readLong);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int readInt33 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    opComplete(readInt33, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int readInt34 = parcel.readInt();
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBackupServiceActive(readInt34, readBoolean17);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isBackupServiceActive = isBackupServiceActive(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackupServiceActive);
                    return true;
                case 48:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserReadyForBackup = isUserReadyForBackup(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserReadyForBackup);
                    return true;
                case 49:
                    int readInt37 = parcel.readInt();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long availableRestoreTokenForUser = getAvailableRestoreTokenForUser(readInt37, readString30);
                    parcel2.writeNoException();
                    parcel2.writeLong(availableRestoreTokenForUser);
                    return true;
                case 50:
                    int readInt38 = parcel.readInt();
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAppEligibleForBackupForUser = isAppEligibleForBackupForUser(readInt38, readString31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppEligibleForBackupForUser);
                    return true;
                case 51:
                    int readInt39 = parcel.readInt();
                    String[] createStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    String[] filterAppsEligibleForBackupForUser = filterAppsEligibleForBackupForUser(readInt39, createStringArray4);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(filterAppsEligibleForBackupForUser);
                    return true;
                case 52:
                    int readInt40 = parcel.readInt();
                    String[] createStringArray5 = parcel.createStringArray();
                    IBackupObserver asInterface6 = IBackupObserver.Stub.asInterface(parcel.readStrongBinder());
                    IBackupManagerMonitor asInterface7 = IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int requestBackupForUser = requestBackupForUser(readInt40, createStringArray5, asInterface6, asInterface7, readInt41);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestBackupForUser);
                    return true;
                case 53:
                    String[] createStringArray6 = parcel.createStringArray();
                    IBackupObserver asInterface8 = IBackupObserver.Stub.asInterface(parcel.readStrongBinder());
                    IBackupManagerMonitor asInterface9 = IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int requestBackup = requestBackup(createStringArray6, asInterface8, asInterface9, readInt42);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestBackup);
                    return true;
                case 54:
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelBackupsForUser(readInt43);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    cancelBackups();
                    parcel2.writeNoException();
                    return true;
                case 56:
                    int readInt44 = parcel.readInt();
                    String readString32 = parcel.readString();
                    boolean readBoolean18 = parcel.readBoolean();
                    boolean readBoolean19 = parcel.readBoolean();
                    boolean readBoolean20 = parcel.readBoolean();
                    boolean readBoolean21 = parcel.readBoolean();
                    boolean readBoolean22 = parcel.readBoolean();
                    boolean readBoolean23 = parcel.readBoolean();
                    boolean readBoolean24 = parcel.readBoolean();
                    String[] createStringArray7 = parcel.createStringArray();
                    boolean readBoolean25 = parcel.readBoolean();
                    String readString33 = parcel.readString();
                    boolean readBoolean26 = parcel.readBoolean();
                    IMemorySaverBackupRestoreObserver asInterface10 = IMemorySaverBackupRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    fullBackupCustomized(readInt44, readString32, readBoolean18, readBoolean19, readBoolean20, readBoolean21, readBoolean22, readBoolean23, readBoolean24, createStringArray7, readBoolean25, readString33, readBoolean26, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    UserHandle userForAncestralSerialNumber = getUserForAncestralSerialNumber(readLong3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userForAncestralSerialNumber, 1);
                    return true;
                case 58:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setAncestralSerialNumber(readLong4);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    boolean isSubUserSupported = isSubUserSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSubUserSupported);
                    return true;
                case 60:
                    ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String[] createStringArray8 = parcel.createStringArray();
                    String readString34 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map semBackupPackage = semBackupPackage(parcelFileDescriptor3, createStringArray8, readString34, readInt45);
                    parcel2.writeNoException();
                    parcel2.writeMap(semBackupPackage);
                    return true;
                case 61:
                    ParcelFileDescriptor parcelFileDescriptor4 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String[] createStringArray9 = parcel.createStringArray();
                    String readString35 = parcel.readString();
                    int readInt46 = parcel.readInt();
                    String[] createStringArray10 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Map semBackupPackagePath = semBackupPackagePath(parcelFileDescriptor4, createStringArray9, readString35, readInt46, createStringArray10);
                    parcel2.writeNoException();
                    parcel2.writeMap(semBackupPackagePath);
                    return true;
                case 62:
                    ParcelFileDescriptor parcelFileDescriptor5 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semRestorePackage(parcelFileDescriptor5, readString36);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    boolean semIsBackupEnabled = semIsBackupEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsBackupEnabled);
                    return true;
                case 64:
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semSetBackupEnabled(readBoolean27);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    boolean readBoolean28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semSetAutoRestoreEnabled(readBoolean28);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    boolean semCancelBackupAndRestore = semCancelBackupAndRestore();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semCancelBackupAndRestore);
                    return true;
                case 67:
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean semSetTimeoutBackupAndRestore = semSetTimeoutBackupAndRestore(readInt47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semSetTimeoutBackupAndRestore);
                    return true;
                case 68:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean semSetTransportFlagsForAdbBackup = semSetTransportFlagsForAdbBackup(readInt48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semSetTransportFlagsForAdbBackup);
                    return true;
                case 69:
                    boolean readBoolean29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean semDisableDataExtractionRule = semDisableDataExtractionRule(readBoolean29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semDisableDataExtractionRule);
                    return true;
                case 70:
                    boolean readBoolean30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semDisableRestrictedModeForAdbBackUp(readBoolean30);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    String readString37 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    excludeKeysFromRestore(readString37, createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    String readString38 = parcel.readString();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(BackupRestoreEventLogger.DataTypeResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportDelayedRestoreResult(readString38, createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBackupManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.backup.IBackupManager
            public void dataChangedForUser(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void dataChanged(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void clearBackupDataForUser(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void clearBackupData(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void initializeTransportsForUser(int i, String[] strArr, IBackupObserver iBackupObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongInterface(iBackupObserver);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void restoreAtInstallForUser(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void restoreAtInstall(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setBackupEnabledForUser(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setFrameworkSchedulingEnabledForUser(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setBackupEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setAutoRestoreForUser(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setAutoRestore(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isBackupEnabledForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isBackupEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean setBackupPassword(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean hasBackupPassword() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void backupNowForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void backupNow() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void adbBackup(int i, ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    obtain.writeBoolean(z5);
                    obtain.writeBoolean(z6);
                    obtain.writeBoolean(z7);
                    obtain.writeBoolean(z8);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void fullTransportBackupForUser(int i, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void fullRestoreCustomized(int i, String str, boolean z, String str2, IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iMemorySaverBackupRestoreObserver);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void adbRestore(int i, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void acknowledgeFullBackupOrRestoreForUser(int i, int i2, boolean z, String str, String str2, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iFullBackupRestoreObserver);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void acknowledgeFullBackupOrRestore(int i, boolean z, String str, String str2, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iFullBackupRestoreObserver);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void updateTransportAttributesForUser(int i, ComponentName componentName, String str, Intent intent, String str2, Intent intent2, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent2, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String getCurrentTransportForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String getCurrentTransport() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public ComponentName getCurrentTransportComponentForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String[] listAllTransportsForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String[] listAllTransports() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public ComponentName[] listAllTransportComponentsForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName[]) obtain2.createTypedArray(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String[] getTransportWhitelist() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String selectBackupTransportForUser(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String selectBackupTransport(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void selectBackupTransportAsyncForUser(int i, ComponentName componentName, ISelectBackupTransportCallback iSelectBackupTransportCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongInterface(iSelectBackupTransportCallback);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public Intent getConfigurationIntentForUser(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Intent) obtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public Intent getConfigurationIntent(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Intent) obtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String getDestinationStringForUser(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String getDestinationString(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public Intent getDataManagementIntentForUser(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Intent) obtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public Intent getDataManagementIntent(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Intent) obtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public CharSequence getDataManagementLabelForUser(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public IRestoreSession beginRestoreSessionForUser(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return IRestoreSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void opCompleteForUser(int i, int i2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void opComplete(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setBackupServiceActive(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isBackupServiceActive(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isUserReadyForBackup(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public long getAvailableRestoreTokenForUser(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isAppEligibleForBackupForUser(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String[] filterAppsEligibleForBackupForUser(int i, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public int requestBackupForUser(int i, String[] strArr, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongInterface(iBackupObserver);
                    obtain.writeStrongInterface(iBackupManagerMonitor);
                    obtain.writeInt(i2);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public int requestBackup(String[] strArr, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongInterface(iBackupObserver);
                    obtain.writeStrongInterface(iBackupManagerMonitor);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void cancelBackupsForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void cancelBackups() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void fullBackupCustomized(int i, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, String[] strArr, boolean z8, String str2, boolean z9, IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    obtain.writeBoolean(z5);
                    obtain.writeBoolean(z6);
                    obtain.writeBoolean(z7);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z8);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z9);
                    obtain.writeStrongInterface(iMemorySaverBackupRestoreObserver);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public UserHandle getUserForAncestralSerialNumber(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserHandle) obtain2.readTypedObject(UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setAncestralSerialNumber(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isSubUserSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public Map semBackupPackage(ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public Map semBackupPackagePath(ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i, String[] strArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr2);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void semRestorePackage(ParcelFileDescriptor parcelFileDescriptor, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean semIsBackupEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void semSetBackupEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void semSetAutoRestoreEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean semCancelBackupAndRestore() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean semSetTimeoutBackupAndRestore(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean semSetTransportFlagsForAdbBackup(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean semDisableDataExtractionRule(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void semDisableRestrictedModeForAdbBackUp(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void excludeKeysFromRestore(String str, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void reportDelayedRestoreResult(String str, List<BackupRestoreEventLogger.DataTypeResult> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
