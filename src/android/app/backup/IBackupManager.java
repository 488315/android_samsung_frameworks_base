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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBackupManager)) {
                return (IBackupManager) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dataChangedForUser(i3, string);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dataChanged(string2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i4 = parcel.readInt();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearBackupDataForUser(i4, string3, string4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearBackupData(string5, string6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i5 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    IBackupObserver iBackupObserverAsInterface = IBackupObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    initializeTransportsForUser(i5, strArrCreateStringArray, iBackupObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i6 = parcel.readInt();
                    String string7 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreAtInstallForUser(i6, string7, i7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string8 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreAtInstall(string8, i8);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i9 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBackupEnabledForUser(i9, z);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i10 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFrameworkSchedulingEnabledForUser(i10, z2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBackupEnabled(z3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i11 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoRestoreForUser(i11, z4);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoRestore(z5);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsBackupEnabledForUser = isBackupEnabledForUser(i12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBackupEnabledForUser);
                    return true;
                case 14:
                    boolean zIsBackupEnabled = isBackupEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBackupEnabled);
                    return true;
                case 15:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean backupPassword = setBackupPassword(string9, string10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(backupPassword);
                    return true;
                case 16:
                    boolean zHasBackupPassword = hasBackupPassword();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasBackupPassword);
                    return true;
                case 17:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    backupNowForUser(i13);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    backupNow();
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i14 = parcel.readInt();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    boolean z7 = parcel.readBoolean();
                    boolean z8 = parcel.readBoolean();
                    boolean z9 = parcel.readBoolean();
                    boolean z10 = parcel.readBoolean();
                    boolean z11 = parcel.readBoolean();
                    boolean z12 = parcel.readBoolean();
                    boolean z13 = parcel.readBoolean();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    adbBackup(i14, parcelFileDescriptor, z6, z7, z8, z9, z10, z11, z12, z13, strArrCreateStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i15 = parcel.readInt();
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    fullTransportBackupForUser(i15, strArrCreateStringArray3);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i16 = parcel.readInt();
                    String string11 = parcel.readString();
                    boolean z14 = parcel.readBoolean();
                    String string12 = parcel.readString();
                    IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserverAsInterface = IMemorySaverBackupRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    fullRestoreCustomized(i16, string11, z14, string12, iMemorySaverBackupRestoreObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i17 = parcel.readInt();
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    adbRestore(i17, parcelFileDescriptor2);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    boolean z15 = parcel.readBoolean();
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    IFullBackupRestoreObserver iFullBackupRestoreObserverAsInterface = IFullBackupRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acknowledgeFullBackupOrRestoreForUser(i18, i19, z15, string13, string14, iFullBackupRestoreObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i20 = parcel.readInt();
                    boolean z16 = parcel.readBoolean();
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    IFullBackupRestoreObserver iFullBackupRestoreObserverAsInterface2 = IFullBackupRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acknowledgeFullBackupOrRestore(i20, z16, string15, string16, iFullBackupRestoreObserverAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int i21 = parcel.readInt();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String string17 = parcel.readString();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string18 = parcel.readString();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    updateTransportAttributesForUser(i21, componentName, string17, intent, string18, intent2, charSequence);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String currentTransportForUser = getCurrentTransportForUser(i22);
                    parcel2.writeNoException();
                    parcel2.writeString(currentTransportForUser);
                    return true;
                case 27:
                    String currentTransport = getCurrentTransport();
                    parcel2.writeNoException();
                    parcel2.writeString(currentTransport);
                    return true;
                case 28:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName currentTransportComponentForUser = getCurrentTransportComponentForUser(i23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentTransportComponentForUser, 1);
                    return true;
                case 29:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] strArrListAllTransportsForUser = listAllTransportsForUser(i24);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrListAllTransportsForUser);
                    return true;
                case 30:
                    String[] strArrListAllTransports = listAllTransports();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrListAllTransports);
                    return true;
                case 31:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName[] componentNameArrListAllTransportComponentsForUser = listAllTransportComponentsForUser(i25);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(componentNameArrListAllTransportComponentsForUser, 1);
                    return true;
                case 32:
                    String[] transportWhitelist = getTransportWhitelist();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(transportWhitelist);
                    return true;
                case 33:
                    int i26 = parcel.readInt();
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strSelectBackupTransportForUser = selectBackupTransportForUser(i26, string19);
                    parcel2.writeNoException();
                    parcel2.writeString(strSelectBackupTransportForUser);
                    return true;
                case 34:
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strSelectBackupTransport = selectBackupTransport(string20);
                    parcel2.writeNoException();
                    parcel2.writeString(strSelectBackupTransport);
                    return true;
                case 35:
                    int i27 = parcel.readInt();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ISelectBackupTransportCallback iSelectBackupTransportCallbackAsInterface = ISelectBackupTransportCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    selectBackupTransportAsyncForUser(i27, componentName2, iSelectBackupTransportCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int i28 = parcel.readInt();
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent configurationIntentForUser = getConfigurationIntentForUser(i28, string21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configurationIntentForUser, 1);
                    return true;
                case 37:
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent configurationIntent = getConfigurationIntent(string22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configurationIntent, 1);
                    return true;
                case 38:
                    int i29 = parcel.readInt();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String destinationStringForUser = getDestinationStringForUser(i29, string23);
                    parcel2.writeNoException();
                    parcel2.writeString(destinationStringForUser);
                    return true;
                case 39:
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String destinationString = getDestinationString(string24);
                    parcel2.writeNoException();
                    parcel2.writeString(destinationString);
                    return true;
                case 40:
                    int i30 = parcel.readInt();
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent dataManagementIntentForUser = getDataManagementIntentForUser(i30, string25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dataManagementIntentForUser, 1);
                    return true;
                case 41:
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent dataManagementIntent = getDataManagementIntent(string26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dataManagementIntent, 1);
                    return true;
                case 42:
                    int i31 = parcel.readInt();
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CharSequence dataManagementLabelForUser = getDataManagementLabelForUser(i31, string27);
                    parcel2.writeNoException();
                    if (dataManagementLabelForUser != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(dataManagementLabelForUser, parcel2, 1);
                        return true;
                    }
                    parcel2.writeInt(0);
                    return true;
                case 43:
                    int i32 = parcel.readInt();
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IRestoreSession iRestoreSessionBeginRestoreSessionForUser = beginRestoreSessionForUser(i32, string28, string29);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iRestoreSessionBeginRestoreSessionForUser);
                    return true;
                case 44:
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    opCompleteForUser(i33, i34, j);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int i35 = parcel.readInt();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    opComplete(i35, j2);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int i36 = parcel.readInt();
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBackupServiceActive(i36, z17);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsBackupServiceActive = isBackupServiceActive(i37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBackupServiceActive);
                    return true;
                case 48:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserReadyForBackup = isUserReadyForBackup(i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserReadyForBackup);
                    return true;
                case 49:
                    int i39 = parcel.readInt();
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long availableRestoreTokenForUser = getAvailableRestoreTokenForUser(i39, string30);
                    parcel2.writeNoException();
                    parcel2.writeLong(availableRestoreTokenForUser);
                    return true;
                case 50:
                    int i40 = parcel.readInt();
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAppEligibleForBackupForUser = isAppEligibleForBackupForUser(i40, string31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAppEligibleForBackupForUser);
                    return true;
                case 51:
                    int i41 = parcel.readInt();
                    String[] strArrCreateStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    String[] strArrFilterAppsEligibleForBackupForUser = filterAppsEligibleForBackupForUser(i41, strArrCreateStringArray4);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrFilterAppsEligibleForBackupForUser);
                    return true;
                case 52:
                    int i42 = parcel.readInt();
                    String[] strArrCreateStringArray5 = parcel.createStringArray();
                    IBackupObserver iBackupObserverAsInterface2 = IBackupObserver.Stub.asInterface(parcel.readStrongBinder());
                    IBackupManagerMonitor iBackupManagerMonitorAsInterface = IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iRequestBackupForUser = requestBackupForUser(i42, strArrCreateStringArray5, iBackupObserverAsInterface2, iBackupManagerMonitorAsInterface, i43);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRequestBackupForUser);
                    return true;
                case 53:
                    String[] strArrCreateStringArray6 = parcel.createStringArray();
                    IBackupObserver iBackupObserverAsInterface3 = IBackupObserver.Stub.asInterface(parcel.readStrongBinder());
                    IBackupManagerMonitor iBackupManagerMonitorAsInterface2 = IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iRequestBackup = requestBackup(strArrCreateStringArray6, iBackupObserverAsInterface3, iBackupManagerMonitorAsInterface2, i44);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRequestBackup);
                    return true;
                case 54:
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelBackupsForUser(i45);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    cancelBackups();
                    parcel2.writeNoException();
                    return true;
                case 56:
                    int i46 = parcel.readInt();
                    String string32 = parcel.readString();
                    boolean z18 = parcel.readBoolean();
                    boolean z19 = parcel.readBoolean();
                    boolean z20 = parcel.readBoolean();
                    boolean z21 = parcel.readBoolean();
                    boolean z22 = parcel.readBoolean();
                    boolean z23 = parcel.readBoolean();
                    boolean z24 = parcel.readBoolean();
                    String[] strArrCreateStringArray7 = parcel.createStringArray();
                    boolean z25 = parcel.readBoolean();
                    String string33 = parcel.readString();
                    boolean z26 = parcel.readBoolean();
                    IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserverAsInterface2 = IMemorySaverBackupRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    fullBackupCustomized(i46, string32, z18, z19, z20, z21, z22, z23, z24, strArrCreateStringArray7, z25, string33, z26, iMemorySaverBackupRestoreObserverAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    UserHandle userForAncestralSerialNumber = getUserForAncestralSerialNumber(j3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userForAncestralSerialNumber, 1);
                    return true;
                case 58:
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setAncestralSerialNumber(j4);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    boolean zIsSubUserSupported = isSubUserSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSubUserSupported);
                    return true;
                case 60:
                    ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String[] strArrCreateStringArray8 = parcel.createStringArray();
                    String string34 = parcel.readString();
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map mapSemBackupPackage = semBackupPackage(parcelFileDescriptor3, strArrCreateStringArray8, string34, i47);
                    parcel2.writeNoException();
                    parcel2.writeMap(mapSemBackupPackage);
                    return true;
                case 61:
                    ParcelFileDescriptor parcelFileDescriptor4 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String[] strArrCreateStringArray9 = parcel.createStringArray();
                    String string35 = parcel.readString();
                    int i48 = parcel.readInt();
                    String[] strArrCreateStringArray10 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Map mapSemBackupPackagePath = semBackupPackagePath(parcelFileDescriptor4, strArrCreateStringArray9, string35, i48, strArrCreateStringArray10);
                    parcel2.writeNoException();
                    parcel2.writeMap(mapSemBackupPackagePath);
                    return true;
                case 62:
                    ParcelFileDescriptor parcelFileDescriptor5 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semRestorePackage(parcelFileDescriptor5, string36);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    boolean zSemIsBackupEnabled = semIsBackupEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsBackupEnabled);
                    return true;
                case 64:
                    boolean z27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semSetBackupEnabled(z27);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    boolean z28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semSetAutoRestoreEnabled(z28);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    boolean zSemCancelBackupAndRestore = semCancelBackupAndRestore();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemCancelBackupAndRestore);
                    return true;
                case 67:
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSemSetTimeoutBackupAndRestore = semSetTimeoutBackupAndRestore(i49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemSetTimeoutBackupAndRestore);
                    return true;
                case 68:
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSemSetTransportFlagsForAdbBackup = semSetTransportFlagsForAdbBackup(i50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemSetTransportFlagsForAdbBackup);
                    return true;
                case 69:
                    boolean z29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zSemDisableDataExtractionRule = semDisableDataExtractionRule(z29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemDisableDataExtractionRule);
                    return true;
                case 70:
                    boolean z30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semDisableRestrictedModeForAdbBackUp(z30);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    String string37 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    excludeKeysFromRestore(string37, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    String string38 = parcel.readString();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(BackupRestoreEventLogger.DataTypeResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportDelayedRestoreResult(string38, arrayListCreateTypedArrayList);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void dataChanged(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void clearBackupDataForUser(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void clearBackupData(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void initializeTransportsForUser(int i, String[] strArr, IBackupObserver iBackupObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStrongInterface(iBackupObserver);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void restoreAtInstallForUser(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void restoreAtInstall(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setBackupEnabledForUser(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setFrameworkSchedulingEnabledForUser(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setBackupEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setAutoRestoreForUser(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setAutoRestore(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isBackupEnabledForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isBackupEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean setBackupPassword(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean hasBackupPassword() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void backupNowForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void backupNow() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void adbBackup(int i, ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeBoolean(z4);
                    parcelObtain.writeBoolean(z5);
                    parcelObtain.writeBoolean(z6);
                    parcelObtain.writeBoolean(z7);
                    parcelObtain.writeBoolean(z8);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void fullTransportBackupForUser(int i, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void fullRestoreCustomized(int i, String str, boolean z, String str2, IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iMemorySaverBackupRestoreObserver);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void adbRestore(int i, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void acknowledgeFullBackupOrRestoreForUser(int i, int i2, boolean z, String str, String str2, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iFullBackupRestoreObserver);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void acknowledgeFullBackupOrRestore(int i, boolean z, String str, String str2, IFullBackupRestoreObserver iFullBackupRestoreObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iFullBackupRestoreObserver);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void updateTransportAttributesForUser(int i, ComponentName componentName, String str, Intent intent, String str2, Intent intent2, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(intent2, 0);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String getCurrentTransportForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String getCurrentTransport() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public ComponentName getCurrentTransportComponentForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String[] listAllTransportsForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String[] listAllTransports() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public ComponentName[] listAllTransportComponentsForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName[]) parcelObtain2.createTypedArray(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String[] getTransportWhitelist() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String selectBackupTransportForUser(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String selectBackupTransport(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void selectBackupTransportAsyncForUser(int i, ComponentName componentName, ISelectBackupTransportCallback iSelectBackupTransportCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStrongInterface(iSelectBackupTransportCallback);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public Intent getConfigurationIntentForUser(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Intent) parcelObtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public Intent getConfigurationIntent(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Intent) parcelObtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String getDestinationStringForUser(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String getDestinationString(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public Intent getDataManagementIntentForUser(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Intent) parcelObtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public Intent getDataManagementIntent(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Intent) parcelObtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public CharSequence getDataManagementLabelForUser(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public IRestoreSession beginRestoreSessionForUser(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IRestoreSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void opCompleteForUser(int i, int i2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void opComplete(int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setBackupServiceActive(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isBackupServiceActive(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isUserReadyForBackup(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public long getAvailableRestoreTokenForUser(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isAppEligibleForBackupForUser(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public String[] filterAppsEligibleForBackupForUser(int i, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public int requestBackupForUser(int i, String[] strArr, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStrongInterface(iBackupObserver);
                    parcelObtain.writeStrongInterface(iBackupManagerMonitor);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public int requestBackup(String[] strArr, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStrongInterface(iBackupObserver);
                    parcelObtain.writeStrongInterface(iBackupManagerMonitor);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void cancelBackupsForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void cancelBackups() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void fullBackupCustomized(int i, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, String[] strArr, boolean z8, String str2, boolean z9, IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeBoolean(z4);
                    parcelObtain.writeBoolean(z5);
                    parcelObtain.writeBoolean(z6);
                    parcelObtain.writeBoolean(z7);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeBoolean(z8);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z9);
                    parcelObtain.writeStrongInterface(iMemorySaverBackupRestoreObserver);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public UserHandle getUserForAncestralSerialNumber(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserHandle) parcelObtain2.readTypedObject(UserHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setAncestralSerialNumber(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isSubUserSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public Map semBackupPackage(ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public Map semBackupPackagePath(ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i, String[] strArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr2);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void semRestorePackage(ParcelFileDescriptor parcelFileDescriptor, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean semIsBackupEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void semSetBackupEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void semSetAutoRestoreEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean semCancelBackupAndRestore() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean semSetTimeoutBackupAndRestore(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean semSetTransportFlagsForAdbBackup(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean semDisableDataExtractionRule(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void semDisableRestrictedModeForAdbBackUp(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void excludeKeysFromRestore(String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void reportDelayedRestoreResult(String str, List<BackupRestoreEventLogger.DataTypeResult> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
