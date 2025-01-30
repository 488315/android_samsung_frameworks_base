package android.app.backup;

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
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public interface IBackupManager extends IInterface {
  void acknowledgeFullBackupOrRestore(
      int i,
      boolean z,
      String str,
      String str2,
      IFullBackupRestoreObserver iFullBackupRestoreObserver)
      throws RemoteException;

  void acknowledgeFullBackupOrRestoreForUser(
      int i,
      int i2,
      boolean z,
      String str,
      String str2,
      IFullBackupRestoreObserver iFullBackupRestoreObserver)
      throws RemoteException;

  void adbBackup(
      int i,
      ParcelFileDescriptor parcelFileDescriptor,
      boolean z,
      boolean z2,
      boolean z3,
      boolean z4,
      boolean z5,
      boolean z6,
      boolean z7,
      boolean z8,
      String[] strArr)
      throws RemoteException;

  void adbRestore(int i, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

  void agentConnected(String str, IBinder iBinder) throws RemoteException;

  void agentConnectedForUser(int i, String str, IBinder iBinder) throws RemoteException;

  void agentDisconnected(String str) throws RemoteException;

  void agentDisconnectedForUser(int i, String str) throws RemoteException;

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

  void fullBackupCustomized(
      int i,
      String str,
      boolean z,
      boolean z2,
      boolean z3,
      boolean z4,
      boolean z5,
      boolean z6,
      boolean z7,
      String[] strArr,
      boolean z8,
      String str2,
      boolean z9,
      IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver)
      throws RemoteException;

  void fullRestoreCustomized(
      int i,
      String str,
      boolean z,
      String str2,
      IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver)
      throws RemoteException;

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

  void initializeTransportsForUser(int i, String[] strArr, IBackupObserver iBackupObserver)
      throws RemoteException;

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

  void reportDelayedRestoreResult(String str, List<BackupRestoreEventLogger.DataTypeResult> list)
      throws RemoteException;

  int requestBackup(
      String[] strArr,
      IBackupObserver iBackupObserver,
      IBackupManagerMonitor iBackupManagerMonitor,
      int i)
      throws RemoteException;

  int requestBackupForUser(
      int i,
      String[] strArr,
      IBackupObserver iBackupObserver,
      IBackupManagerMonitor iBackupManagerMonitor,
      int i2)
      throws RemoteException;

  void restoreAtInstall(String str, int i) throws RemoteException;

  void restoreAtInstallForUser(int i, String str, int i2) throws RemoteException;

  String selectBackupTransport(String str) throws RemoteException;

  void selectBackupTransportAsyncForUser(
      int i,
      ComponentName componentName,
      ISelectBackupTransportCallback iSelectBackupTransportCallback)
      throws RemoteException;

  String selectBackupTransportForUser(int i, String str) throws RemoteException;

  Map semBackupPackage(
      ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i)
      throws RemoteException;

  Map semBackupPackagePath(
      ParcelFileDescriptor parcelFileDescriptor,
      String[] strArr,
      String str,
      int i,
      String[] strArr2)
      throws RemoteException;

  boolean semCancelBackupAndRestore() throws RemoteException;

  boolean semDisableDataExtractionRule(boolean z) throws RemoteException;

  boolean semIsBackupEnabled() throws RemoteException;

  void semRestorePackage(ParcelFileDescriptor parcelFileDescriptor, String str)
      throws RemoteException;

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

  void updateTransportAttributesForUser(
      int i,
      ComponentName componentName,
      String str,
      Intent intent,
      String str2,
      Intent intent2,
      CharSequence charSequence)
      throws RemoteException;

  public static class Default implements IBackupManager {
    @Override // android.app.backup.IBackupManager
    public void dataChangedForUser(int userId, String packageName) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void dataChanged(String packageName) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void clearBackupDataForUser(int userId, String transportName, String packageName)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void clearBackupData(String transportName, String packageName) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void initializeTransportsForUser(
        int userId, String[] transportNames, IBackupObserver observer) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void agentConnectedForUser(int userId, String packageName, IBinder agent)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void agentConnected(String packageName, IBinder agent) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void agentDisconnectedForUser(int userId, String packageName) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void agentDisconnected(String packageName) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void restoreAtInstallForUser(int userId, String packageName, int token)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void restoreAtInstall(String packageName, int token) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void setBackupEnabledForUser(int userId, boolean isEnabled) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void setFrameworkSchedulingEnabledForUser(int userId, boolean isEnabled)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void setBackupEnabled(boolean isEnabled) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void setAutoRestoreForUser(int userId, boolean doAutoRestore) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void setAutoRestore(boolean doAutoRestore) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public boolean isBackupEnabledForUser(int userId) throws RemoteException {
      return false;
    }

    @Override // android.app.backup.IBackupManager
    public boolean isBackupEnabled() throws RemoteException {
      return false;
    }

    @Override // android.app.backup.IBackupManager
    public boolean setBackupPassword(String currentPw, String newPw) throws RemoteException {
      return false;
    }

    @Override // android.app.backup.IBackupManager
    public boolean hasBackupPassword() throws RemoteException {
      return false;
    }

    @Override // android.app.backup.IBackupManager
    public void backupNowForUser(int userId) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void backupNow() throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void adbBackup(
        int userId,
        ParcelFileDescriptor fd,
        boolean includeApks,
        boolean includeObbs,
        boolean includeShared,
        boolean doWidgets,
        boolean allApps,
        boolean allIncludesSystem,
        boolean doCompress,
        boolean doKeyValue,
        String[] packageNames)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void fullTransportBackupForUser(int userId, String[] packageNames)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void fullRestoreCustomized(
        int userId,
        String filePath,
        boolean password,
        String EncPassword,
        IMemorySaverBackupRestoreObserver observer)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void adbRestore(int userId, ParcelFileDescriptor fd) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void acknowledgeFullBackupOrRestoreForUser(
        int userId,
        int token,
        boolean allow,
        String curPassword,
        String encryptionPassword,
        IFullBackupRestoreObserver observer)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void acknowledgeFullBackupOrRestore(
        int token,
        boolean allow,
        String curPassword,
        String encryptionPassword,
        IFullBackupRestoreObserver observer)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void updateTransportAttributesForUser(
        int userId,
        ComponentName transportComponent,
        String name,
        Intent configurationIntent,
        String currentDestinationString,
        Intent dataManagementIntent,
        CharSequence dataManagementLabel)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public String getCurrentTransportForUser(int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public String getCurrentTransport() throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public ComponentName getCurrentTransportComponentForUser(int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public String[] listAllTransportsForUser(int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public String[] listAllTransports() throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public ComponentName[] listAllTransportComponentsForUser(int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public String[] getTransportWhitelist() throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public String selectBackupTransportForUser(int userId, String transport)
        throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public String selectBackupTransport(String transport) throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public void selectBackupTransportAsyncForUser(
        int userId, ComponentName transport, ISelectBackupTransportCallback listener)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public Intent getConfigurationIntentForUser(int userId, String transport)
        throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public Intent getConfigurationIntent(String transport) throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public String getDestinationStringForUser(int userId, String transport) throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public String getDestinationString(String transport) throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public Intent getDataManagementIntentForUser(int userId, String transport)
        throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public Intent getDataManagementIntent(String transport) throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public CharSequence getDataManagementLabelForUser(int userId, String transport)
        throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public IRestoreSession beginRestoreSessionForUser(
        int userId, String packageName, String transportID) throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public void opCompleteForUser(int userId, int token, long result) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void opComplete(int token, long result) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void setBackupServiceActive(int whichUser, boolean makeActive) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public boolean isBackupServiceActive(int whichUser) throws RemoteException {
      return false;
    }

    @Override // android.app.backup.IBackupManager
    public boolean isUserReadyForBackup(int userId) throws RemoteException {
      return false;
    }

    @Override // android.app.backup.IBackupManager
    public long getAvailableRestoreTokenForUser(int userId, String packageName)
        throws RemoteException {
      return 0L;
    }

    @Override // android.app.backup.IBackupManager
    public boolean isAppEligibleForBackupForUser(int userId, String packageName)
        throws RemoteException {
      return false;
    }

    @Override // android.app.backup.IBackupManager
    public String[] filterAppsEligibleForBackupForUser(int userId, String[] packages)
        throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public int requestBackupForUser(
        int userId,
        String[] packages,
        IBackupObserver observer,
        IBackupManagerMonitor monitor,
        int flags)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.backup.IBackupManager
    public int requestBackup(
        String[] packages, IBackupObserver observer, IBackupManagerMonitor monitor, int flags)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.backup.IBackupManager
    public void cancelBackupsForUser(int userId) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void cancelBackups() throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void fullBackupCustomized(
        int userId,
        String filePath,
        boolean includeApks,
        boolean includeObbs,
        boolean includeShared,
        boolean doWidgets,
        boolean allApps,
        boolean allIncludesSystem,
        boolean doCompress,
        String[] packageNames,
        boolean password,
        String EncPassword,
        boolean doKeyValue,
        IMemorySaverBackupRestoreObserver observer)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public UserHandle getUserForAncestralSerialNumber(long ancestralSerialNumber)
        throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public void setAncestralSerialNumber(long ancestralSerialNumber) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public boolean isSubUserSupported() throws RemoteException {
      return false;
    }

    @Override // android.app.backup.IBackupManager
    public Map semBackupPackage(
        ParcelFileDescriptor fd, String[] packageNames, String password, int flag)
        throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public Map semBackupPackagePath(
        ParcelFileDescriptor fd, String[] packageNames, String password, int flag, String[] paths)
        throws RemoteException {
      return null;
    }

    @Override // android.app.backup.IBackupManager
    public void semRestorePackage(ParcelFileDescriptor fd, String password)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public boolean semIsBackupEnabled() throws RemoteException {
      return false;
    }

    @Override // android.app.backup.IBackupManager
    public void semSetBackupEnabled(boolean enabled) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void semSetAutoRestoreEnabled(boolean enabled) throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public boolean semCancelBackupAndRestore() throws RemoteException {
      return false;
    }

    @Override // android.app.backup.IBackupManager
    public boolean semSetTimeoutBackupAndRestore(int timeoutMin) throws RemoteException {
      return false;
    }

    @Override // android.app.backup.IBackupManager
    public boolean semSetTransportFlagsForAdbBackup(int transportFlags) throws RemoteException {
      return false;
    }

    @Override // android.app.backup.IBackupManager
    public boolean semDisableDataExtractionRule(boolean disabled) throws RemoteException {
      return false;
    }

    @Override // android.app.backup.IBackupManager
    public void excludeKeysFromRestore(String packageName, List<String> keys)
        throws RemoteException {}

    @Override // android.app.backup.IBackupManager
    public void reportDelayedRestoreResult(
        String packageName, List<BackupRestoreEventLogger.DataTypeResult> results)
        throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IBackupManager {
    public static final String DESCRIPTOR = "android.app.backup.IBackupManager";
    static final int TRANSACTION_acknowledgeFullBackupOrRestore = 28;
    static final int TRANSACTION_acknowledgeFullBackupOrRestoreForUser = 27;
    static final int TRANSACTION_adbBackup = 23;
    static final int TRANSACTION_adbRestore = 26;
    static final int TRANSACTION_agentConnected = 7;
    static final int TRANSACTION_agentConnectedForUser = 6;
    static final int TRANSACTION_agentDisconnected = 9;
    static final int TRANSACTION_agentDisconnectedForUser = 8;
    static final int TRANSACTION_backupNow = 22;
    static final int TRANSACTION_backupNowForUser = 21;
    static final int TRANSACTION_beginRestoreSessionForUser = 47;
    static final int TRANSACTION_cancelBackups = 59;
    static final int TRANSACTION_cancelBackupsForUser = 58;
    static final int TRANSACTION_clearBackupData = 4;
    static final int TRANSACTION_clearBackupDataForUser = 3;
    static final int TRANSACTION_dataChanged = 2;
    static final int TRANSACTION_dataChangedForUser = 1;
    static final int TRANSACTION_excludeKeysFromRestore = 74;
    static final int TRANSACTION_filterAppsEligibleForBackupForUser = 55;
    static final int TRANSACTION_fullBackupCustomized = 60;
    static final int TRANSACTION_fullRestoreCustomized = 25;
    static final int TRANSACTION_fullTransportBackupForUser = 24;
    static final int TRANSACTION_getAvailableRestoreTokenForUser = 53;
    static final int TRANSACTION_getConfigurationIntent = 41;
    static final int TRANSACTION_getConfigurationIntentForUser = 40;
    static final int TRANSACTION_getCurrentTransport = 31;
    static final int TRANSACTION_getCurrentTransportComponentForUser = 32;
    static final int TRANSACTION_getCurrentTransportForUser = 30;
    static final int TRANSACTION_getDataManagementIntent = 45;
    static final int TRANSACTION_getDataManagementIntentForUser = 44;
    static final int TRANSACTION_getDataManagementLabelForUser = 46;
    static final int TRANSACTION_getDestinationString = 43;
    static final int TRANSACTION_getDestinationStringForUser = 42;
    static final int TRANSACTION_getTransportWhitelist = 36;
    static final int TRANSACTION_getUserForAncestralSerialNumber = 61;
    static final int TRANSACTION_hasBackupPassword = 20;
    static final int TRANSACTION_initializeTransportsForUser = 5;
    static final int TRANSACTION_isAppEligibleForBackupForUser = 54;
    static final int TRANSACTION_isBackupEnabled = 18;
    static final int TRANSACTION_isBackupEnabledForUser = 17;
    static final int TRANSACTION_isBackupServiceActive = 51;
    static final int TRANSACTION_isSubUserSupported = 63;
    static final int TRANSACTION_isUserReadyForBackup = 52;
    static final int TRANSACTION_listAllTransportComponentsForUser = 35;
    static final int TRANSACTION_listAllTransports = 34;
    static final int TRANSACTION_listAllTransportsForUser = 33;
    static final int TRANSACTION_opComplete = 49;
    static final int TRANSACTION_opCompleteForUser = 48;
    static final int TRANSACTION_reportDelayedRestoreResult = 75;
    static final int TRANSACTION_requestBackup = 57;
    static final int TRANSACTION_requestBackupForUser = 56;
    static final int TRANSACTION_restoreAtInstall = 11;
    static final int TRANSACTION_restoreAtInstallForUser = 10;
    static final int TRANSACTION_selectBackupTransport = 38;
    static final int TRANSACTION_selectBackupTransportAsyncForUser = 39;
    static final int TRANSACTION_selectBackupTransportForUser = 37;
    static final int TRANSACTION_semBackupPackage = 64;
    static final int TRANSACTION_semBackupPackagePath = 65;
    static final int TRANSACTION_semCancelBackupAndRestore = 70;
    static final int TRANSACTION_semDisableDataExtractionRule = 73;
    static final int TRANSACTION_semIsBackupEnabled = 67;
    static final int TRANSACTION_semRestorePackage = 66;
    static final int TRANSACTION_semSetAutoRestoreEnabled = 69;
    static final int TRANSACTION_semSetBackupEnabled = 68;
    static final int TRANSACTION_semSetTimeoutBackupAndRestore = 71;
    static final int TRANSACTION_semSetTransportFlagsForAdbBackup = 72;
    static final int TRANSACTION_setAncestralSerialNumber = 62;
    static final int TRANSACTION_setAutoRestore = 16;
    static final int TRANSACTION_setAutoRestoreForUser = 15;
    static final int TRANSACTION_setBackupEnabled = 14;
    static final int TRANSACTION_setBackupEnabledForUser = 12;
    static final int TRANSACTION_setBackupPassword = 19;
    static final int TRANSACTION_setBackupServiceActive = 50;
    static final int TRANSACTION_setFrameworkSchedulingEnabledForUser = 13;
    static final int TRANSACTION_updateTransportAttributesForUser = 29;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IBackupManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IBackupManager)) {
        return (IBackupManager) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
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
          return "agentConnectedForUser";
        case 7:
          return "agentConnected";
        case 8:
          return "agentDisconnectedForUser";
        case 9:
          return "agentDisconnected";
        case 10:
          return "restoreAtInstallForUser";
        case 11:
          return "restoreAtInstall";
        case 12:
          return "setBackupEnabledForUser";
        case 13:
          return "setFrameworkSchedulingEnabledForUser";
        case 14:
          return "setBackupEnabled";
        case 15:
          return "setAutoRestoreForUser";
        case 16:
          return "setAutoRestore";
        case 17:
          return "isBackupEnabledForUser";
        case 18:
          return "isBackupEnabled";
        case 19:
          return "setBackupPassword";
        case 20:
          return "hasBackupPassword";
        case 21:
          return "backupNowForUser";
        case 22:
          return "backupNow";
        case 23:
          return "adbBackup";
        case 24:
          return "fullTransportBackupForUser";
        case 25:
          return "fullRestoreCustomized";
        case 26:
          return "adbRestore";
        case 27:
          return "acknowledgeFullBackupOrRestoreForUser";
        case 28:
          return "acknowledgeFullBackupOrRestore";
        case 29:
          return "updateTransportAttributesForUser";
        case 30:
          return "getCurrentTransportForUser";
        case 31:
          return "getCurrentTransport";
        case 32:
          return "getCurrentTransportComponentForUser";
        case 33:
          return "listAllTransportsForUser";
        case 34:
          return "listAllTransports";
        case 35:
          return "listAllTransportComponentsForUser";
        case 36:
          return "getTransportWhitelist";
        case 37:
          return "selectBackupTransportForUser";
        case 38:
          return "selectBackupTransport";
        case 39:
          return "selectBackupTransportAsyncForUser";
        case 40:
          return "getConfigurationIntentForUser";
        case 41:
          return "getConfigurationIntent";
        case 42:
          return "getDestinationStringForUser";
        case 43:
          return "getDestinationString";
        case 44:
          return "getDataManagementIntentForUser";
        case 45:
          return "getDataManagementIntent";
        case 46:
          return "getDataManagementLabelForUser";
        case 47:
          return "beginRestoreSessionForUser";
        case 48:
          return "opCompleteForUser";
        case 49:
          return "opComplete";
        case 50:
          return "setBackupServiceActive";
        case 51:
          return "isBackupServiceActive";
        case 52:
          return "isUserReadyForBackup";
        case 53:
          return "getAvailableRestoreTokenForUser";
        case 54:
          return "isAppEligibleForBackupForUser";
        case 55:
          return "filterAppsEligibleForBackupForUser";
        case 56:
          return "requestBackupForUser";
        case 57:
          return "requestBackup";
        case 58:
          return "cancelBackupsForUser";
        case 59:
          return "cancelBackups";
        case 60:
          return "fullBackupCustomized";
        case 61:
          return "getUserForAncestralSerialNumber";
        case 62:
          return "setAncestralSerialNumber";
        case 63:
          return "isSubUserSupported";
        case 64:
          return "semBackupPackage";
        case 65:
          return "semBackupPackagePath";
        case 66:
          return "semRestorePackage";
        case 67:
          return "semIsBackupEnabled";
        case 68:
          return "semSetBackupEnabled";
        case 69:
          return "semSetAutoRestoreEnabled";
        case 70:
          return "semCancelBackupAndRestore";
        case 71:
          return "semSetTimeoutBackupAndRestore";
        case 72:
          return "semSetTransportFlagsForAdbBackup";
        case 73:
          return "semDisableDataExtractionRule";
        case 74:
          return "excludeKeysFromRestore";
        case 75:
          return "reportDelayedRestoreResult";
        default:
          return null;
      }
    }

    @Override // android.os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              String _arg1 = data.readString();
              data.enforceNoDataAvail();
              dataChangedForUser(_arg0, _arg1);
              reply.writeNoException();
              return true;
            case 2:
              String _arg02 = data.readString();
              data.enforceNoDataAvail();
              dataChanged(_arg02);
              reply.writeNoException();
              return true;
            case 3:
              int _arg03 = data.readInt();
              String _arg12 = data.readString();
              String _arg2 = data.readString();
              data.enforceNoDataAvail();
              clearBackupDataForUser(_arg03, _arg12, _arg2);
              reply.writeNoException();
              return true;
            case 4:
              String _arg04 = data.readString();
              String _arg13 = data.readString();
              data.enforceNoDataAvail();
              clearBackupData(_arg04, _arg13);
              reply.writeNoException();
              return true;
            case 5:
              int _arg05 = data.readInt();
              String[] _arg14 = data.createStringArray();
              IBackupObserver _arg22 = IBackupObserver.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              initializeTransportsForUser(_arg05, _arg14, _arg22);
              reply.writeNoException();
              return true;
            case 6:
              int _arg06 = data.readInt();
              String _arg15 = data.readString();
              IBinder _arg23 = data.readStrongBinder();
              data.enforceNoDataAvail();
              agentConnectedForUser(_arg06, _arg15, _arg23);
              reply.writeNoException();
              return true;
            case 7:
              String _arg07 = data.readString();
              IBinder _arg16 = data.readStrongBinder();
              data.enforceNoDataAvail();
              agentConnected(_arg07, _arg16);
              reply.writeNoException();
              return true;
            case 8:
              int _arg08 = data.readInt();
              String _arg17 = data.readString();
              data.enforceNoDataAvail();
              agentDisconnectedForUser(_arg08, _arg17);
              reply.writeNoException();
              return true;
            case 9:
              String _arg09 = data.readString();
              data.enforceNoDataAvail();
              agentDisconnected(_arg09);
              reply.writeNoException();
              return true;
            case 10:
              int _arg010 = data.readInt();
              String _arg18 = data.readString();
              int _arg24 = data.readInt();
              data.enforceNoDataAvail();
              restoreAtInstallForUser(_arg010, _arg18, _arg24);
              reply.writeNoException();
              return true;
            case 11:
              String _arg011 = data.readString();
              int _arg19 = data.readInt();
              data.enforceNoDataAvail();
              restoreAtInstall(_arg011, _arg19);
              reply.writeNoException();
              return true;
            case 12:
              int _arg012 = data.readInt();
              boolean _arg110 = data.readBoolean();
              data.enforceNoDataAvail();
              setBackupEnabledForUser(_arg012, _arg110);
              reply.writeNoException();
              return true;
            case 13:
              int _arg013 = data.readInt();
              boolean _arg111 = data.readBoolean();
              data.enforceNoDataAvail();
              setFrameworkSchedulingEnabledForUser(_arg013, _arg111);
              reply.writeNoException();
              return true;
            case 14:
              boolean _arg014 = data.readBoolean();
              data.enforceNoDataAvail();
              setBackupEnabled(_arg014);
              reply.writeNoException();
              return true;
            case 15:
              int _arg015 = data.readInt();
              boolean _arg112 = data.readBoolean();
              data.enforceNoDataAvail();
              setAutoRestoreForUser(_arg015, _arg112);
              reply.writeNoException();
              return true;
            case 16:
              boolean _arg016 = data.readBoolean();
              data.enforceNoDataAvail();
              setAutoRestore(_arg016);
              reply.writeNoException();
              return true;
            case 17:
              int _arg017 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result = isBackupEnabledForUser(_arg017);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 18:
              boolean _result2 = isBackupEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 19:
              String _arg018 = data.readString();
              String _arg113 = data.readString();
              data.enforceNoDataAvail();
              boolean _result3 = setBackupPassword(_arg018, _arg113);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 20:
              boolean _result4 = hasBackupPassword();
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 21:
              int _arg019 = data.readInt();
              data.enforceNoDataAvail();
              backupNowForUser(_arg019);
              reply.writeNoException();
              return true;
            case 22:
              backupNow();
              reply.writeNoException();
              return true;
            case 23:
              int _arg020 = data.readInt();
              ParcelFileDescriptor _arg114 =
                  (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
              boolean _arg25 = data.readBoolean();
              boolean _arg3 = data.readBoolean();
              boolean _arg4 = data.readBoolean();
              boolean _arg5 = data.readBoolean();
              boolean _arg6 = data.readBoolean();
              boolean _arg7 = data.readBoolean();
              boolean _arg8 = data.readBoolean();
              boolean _arg9 = data.readBoolean();
              String[] _arg10 = data.createStringArray();
              data.enforceNoDataAvail();
              adbBackup(
                  _arg020, _arg114, _arg25, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9,
                  _arg10);
              reply.writeNoException();
              return true;
            case 24:
              int _arg021 = data.readInt();
              String[] _arg115 = data.createStringArray();
              data.enforceNoDataAvail();
              fullTransportBackupForUser(_arg021, _arg115);
              reply.writeNoException();
              return true;
            case 25:
              int _arg022 = data.readInt();
              String _arg116 = data.readString();
              boolean _arg26 = data.readBoolean();
              String _arg32 = data.readString();
              IMemorySaverBackupRestoreObserver _arg42 =
                  IMemorySaverBackupRestoreObserver.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              fullRestoreCustomized(_arg022, _arg116, _arg26, _arg32, _arg42);
              reply.writeNoException();
              return true;
            case 26:
              int _arg023 = data.readInt();
              ParcelFileDescriptor _arg117 =
                  (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
              data.enforceNoDataAvail();
              adbRestore(_arg023, _arg117);
              reply.writeNoException();
              return true;
            case 27:
              int _arg024 = data.readInt();
              int _arg118 = data.readInt();
              boolean _arg27 = data.readBoolean();
              String _arg33 = data.readString();
              String _arg43 = data.readString();
              IFullBackupRestoreObserver _arg52 =
                  IFullBackupRestoreObserver.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              acknowledgeFullBackupOrRestoreForUser(
                  _arg024, _arg118, _arg27, _arg33, _arg43, _arg52);
              reply.writeNoException();
              return true;
            case 28:
              int _arg025 = data.readInt();
              boolean _arg119 = data.readBoolean();
              String _arg28 = data.readString();
              String _arg34 = data.readString();
              IFullBackupRestoreObserver _arg44 =
                  IFullBackupRestoreObserver.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              acknowledgeFullBackupOrRestore(_arg025, _arg119, _arg28, _arg34, _arg44);
              reply.writeNoException();
              return true;
            case 29:
              int _arg026 = data.readInt();
              ComponentName _arg120 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              String _arg29 = data.readString();
              Intent _arg35 = (Intent) data.readTypedObject(Intent.CREATOR);
              String _arg45 = data.readString();
              Intent _arg53 = (Intent) data.readTypedObject(Intent.CREATOR);
              CharSequence _arg62 =
                  (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
              data.enforceNoDataAvail();
              updateTransportAttributesForUser(
                  _arg026, _arg120, _arg29, _arg35, _arg45, _arg53, _arg62);
              reply.writeNoException();
              return true;
            case 30:
              int _arg027 = data.readInt();
              data.enforceNoDataAvail();
              String _result5 = getCurrentTransportForUser(_arg027);
              reply.writeNoException();
              reply.writeString(_result5);
              return true;
            case 31:
              String _result6 = getCurrentTransport();
              reply.writeNoException();
              reply.writeString(_result6);
              return true;
            case 32:
              int _arg028 = data.readInt();
              data.enforceNoDataAvail();
              ComponentName _result7 = getCurrentTransportComponentForUser(_arg028);
              reply.writeNoException();
              reply.writeTypedObject(_result7, 1);
              return true;
            case 33:
              int _arg029 = data.readInt();
              data.enforceNoDataAvail();
              String[] _result8 = listAllTransportsForUser(_arg029);
              reply.writeNoException();
              reply.writeStringArray(_result8);
              return true;
            case 34:
              String[] _result9 = listAllTransports();
              reply.writeNoException();
              reply.writeStringArray(_result9);
              return true;
            case 35:
              int _arg030 = data.readInt();
              data.enforceNoDataAvail();
              ComponentName[] _result10 = listAllTransportComponentsForUser(_arg030);
              reply.writeNoException();
              reply.writeTypedArray(_result10, 1);
              return true;
            case 36:
              String[] _result11 = getTransportWhitelist();
              reply.writeNoException();
              reply.writeStringArray(_result11);
              return true;
            case 37:
              int _arg031 = data.readInt();
              String _arg121 = data.readString();
              data.enforceNoDataAvail();
              String _result12 = selectBackupTransportForUser(_arg031, _arg121);
              reply.writeNoException();
              reply.writeString(_result12);
              return true;
            case 38:
              String _arg032 = data.readString();
              data.enforceNoDataAvail();
              String _result13 = selectBackupTransport(_arg032);
              reply.writeNoException();
              reply.writeString(_result13);
              return true;
            case 39:
              int _arg033 = data.readInt();
              ComponentName _arg122 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              ISelectBackupTransportCallback _arg210 =
                  ISelectBackupTransportCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              selectBackupTransportAsyncForUser(_arg033, _arg122, _arg210);
              reply.writeNoException();
              return true;
            case 40:
              int _arg034 = data.readInt();
              String _arg123 = data.readString();
              data.enforceNoDataAvail();
              Intent _result14 = getConfigurationIntentForUser(_arg034, _arg123);
              reply.writeNoException();
              reply.writeTypedObject(_result14, 1);
              return true;
            case 41:
              String _arg035 = data.readString();
              data.enforceNoDataAvail();
              Intent _result15 = getConfigurationIntent(_arg035);
              reply.writeNoException();
              reply.writeTypedObject(_result15, 1);
              return true;
            case 42:
              int _arg036 = data.readInt();
              String _arg124 = data.readString();
              data.enforceNoDataAvail();
              String _result16 = getDestinationStringForUser(_arg036, _arg124);
              reply.writeNoException();
              reply.writeString(_result16);
              return true;
            case 43:
              String _arg037 = data.readString();
              data.enforceNoDataAvail();
              String _result17 = getDestinationString(_arg037);
              reply.writeNoException();
              reply.writeString(_result17);
              return true;
            case 44:
              int _arg038 = data.readInt();
              String _arg125 = data.readString();
              data.enforceNoDataAvail();
              Intent _result18 = getDataManagementIntentForUser(_arg038, _arg125);
              reply.writeNoException();
              reply.writeTypedObject(_result18, 1);
              return true;
            case 45:
              String _arg039 = data.readString();
              data.enforceNoDataAvail();
              Intent _result19 = getDataManagementIntent(_arg039);
              reply.writeNoException();
              reply.writeTypedObject(_result19, 1);
              return true;
            case 46:
              int _arg040 = data.readInt();
              String _arg126 = data.readString();
              data.enforceNoDataAvail();
              CharSequence _result20 = getDataManagementLabelForUser(_arg040, _arg126);
              reply.writeNoException();
              if (_result20 != null) {
                reply.writeInt(1);
                TextUtils.writeToParcel(_result20, reply, 1);
                return true;
              }
              reply.writeInt(0);
              return true;
            case 47:
              int _arg041 = data.readInt();
              String _arg127 = data.readString();
              String _arg211 = data.readString();
              data.enforceNoDataAvail();
              IRestoreSession _result21 = beginRestoreSessionForUser(_arg041, _arg127, _arg211);
              reply.writeNoException();
              reply.writeStrongInterface(_result21);
              return true;
            case 48:
              int _arg042 = data.readInt();
              int _arg128 = data.readInt();
              long _arg212 = data.readLong();
              data.enforceNoDataAvail();
              opCompleteForUser(_arg042, _arg128, _arg212);
              reply.writeNoException();
              return true;
            case 49:
              int _arg043 = data.readInt();
              long _arg129 = data.readLong();
              data.enforceNoDataAvail();
              opComplete(_arg043, _arg129);
              reply.writeNoException();
              return true;
            case 50:
              int _arg044 = data.readInt();
              boolean _arg130 = data.readBoolean();
              data.enforceNoDataAvail();
              setBackupServiceActive(_arg044, _arg130);
              reply.writeNoException();
              return true;
            case 51:
              int _arg045 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result22 = isBackupServiceActive(_arg045);
              reply.writeNoException();
              reply.writeBoolean(_result22);
              return true;
            case 52:
              int _arg046 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result23 = isUserReadyForBackup(_arg046);
              reply.writeNoException();
              reply.writeBoolean(_result23);
              return true;
            case 53:
              int _arg047 = data.readInt();
              String _arg131 = data.readString();
              data.enforceNoDataAvail();
              long _result24 = getAvailableRestoreTokenForUser(_arg047, _arg131);
              reply.writeNoException();
              reply.writeLong(_result24);
              return true;
            case 54:
              int _arg048 = data.readInt();
              String _arg132 = data.readString();
              data.enforceNoDataAvail();
              boolean _result25 = isAppEligibleForBackupForUser(_arg048, _arg132);
              reply.writeNoException();
              reply.writeBoolean(_result25);
              return true;
            case 55:
              int _arg049 = data.readInt();
              String[] _arg133 = data.createStringArray();
              data.enforceNoDataAvail();
              String[] _result26 = filterAppsEligibleForBackupForUser(_arg049, _arg133);
              reply.writeNoException();
              reply.writeStringArray(_result26);
              return true;
            case 56:
              int _arg050 = data.readInt();
              String[] _arg134 = data.createStringArray();
              IBackupObserver _arg213 = IBackupObserver.Stub.asInterface(data.readStrongBinder());
              IBackupManagerMonitor _arg36 =
                  IBackupManagerMonitor.Stub.asInterface(data.readStrongBinder());
              int _arg46 = data.readInt();
              data.enforceNoDataAvail();
              int _result27 = requestBackupForUser(_arg050, _arg134, _arg213, _arg36, _arg46);
              reply.writeNoException();
              reply.writeInt(_result27);
              return true;
            case 57:
              String[] _arg051 = data.createStringArray();
              IBackupObserver _arg135 = IBackupObserver.Stub.asInterface(data.readStrongBinder());
              IBackupManagerMonitor _arg214 =
                  IBackupManagerMonitor.Stub.asInterface(data.readStrongBinder());
              int _arg37 = data.readInt();
              data.enforceNoDataAvail();
              int _result28 = requestBackup(_arg051, _arg135, _arg214, _arg37);
              reply.writeNoException();
              reply.writeInt(_result28);
              return true;
            case 58:
              int _arg052 = data.readInt();
              data.enforceNoDataAvail();
              cancelBackupsForUser(_arg052);
              reply.writeNoException();
              return true;
            case 59:
              cancelBackups();
              reply.writeNoException();
              return true;
            case 60:
              int _arg053 = data.readInt();
              String _arg136 = data.readString();
              boolean _arg215 = data.readBoolean();
              boolean _arg38 = data.readBoolean();
              boolean _arg47 = data.readBoolean();
              boolean _arg54 = data.readBoolean();
              boolean _arg63 = data.readBoolean();
              boolean _arg72 = data.readBoolean();
              boolean _arg82 = data.readBoolean();
              String[] _arg92 = data.createStringArray();
              boolean _arg102 = data.readBoolean();
              String _arg11 = data.readString();
              boolean _arg1210 = data.readBoolean();
              IMemorySaverBackupRestoreObserver _arg137 =
                  IMemorySaverBackupRestoreObserver.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              fullBackupCustomized(
                  _arg053, _arg136, _arg215, _arg38, _arg47, _arg54, _arg63, _arg72, _arg82, _arg92,
                  _arg102, _arg11, _arg1210, _arg137);
              reply.writeNoException();
              return true;
            case 61:
              long _arg054 = data.readLong();
              data.enforceNoDataAvail();
              UserHandle _result29 = getUserForAncestralSerialNumber(_arg054);
              reply.writeNoException();
              reply.writeTypedObject(_result29, 1);
              return true;
            case 62:
              long _arg055 = data.readLong();
              data.enforceNoDataAvail();
              setAncestralSerialNumber(_arg055);
              reply.writeNoException();
              return true;
            case 63:
              boolean _result30 = isSubUserSupported();
              reply.writeNoException();
              reply.writeBoolean(_result30);
              return true;
            case 64:
              ParcelFileDescriptor _arg056 =
                  (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
              String[] _arg138 = data.createStringArray();
              String _arg216 = data.readString();
              int _arg39 = data.readInt();
              data.enforceNoDataAvail();
              Map _result31 = semBackupPackage(_arg056, _arg138, _arg216, _arg39);
              reply.writeNoException();
              reply.writeMap(_result31);
              return true;
            case 65:
              ParcelFileDescriptor _arg057 =
                  (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
              String[] _arg139 = data.createStringArray();
              String _arg217 = data.readString();
              int _arg310 = data.readInt();
              String[] _arg48 = data.createStringArray();
              data.enforceNoDataAvail();
              Map _result32 = semBackupPackagePath(_arg057, _arg139, _arg217, _arg310, _arg48);
              reply.writeNoException();
              reply.writeMap(_result32);
              return true;
            case 66:
              ParcelFileDescriptor _arg058 =
                  (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
              String _arg140 = data.readString();
              data.enforceNoDataAvail();
              semRestorePackage(_arg058, _arg140);
              reply.writeNoException();
              return true;
            case 67:
              boolean _result33 = semIsBackupEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result33);
              return true;
            case 68:
              boolean _arg059 = data.readBoolean();
              data.enforceNoDataAvail();
              semSetBackupEnabled(_arg059);
              reply.writeNoException();
              return true;
            case 69:
              boolean _arg060 = data.readBoolean();
              data.enforceNoDataAvail();
              semSetAutoRestoreEnabled(_arg060);
              reply.writeNoException();
              return true;
            case 70:
              boolean _result34 = semCancelBackupAndRestore();
              reply.writeNoException();
              reply.writeBoolean(_result34);
              return true;
            case 71:
              int _arg061 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result35 = semSetTimeoutBackupAndRestore(_arg061);
              reply.writeNoException();
              reply.writeBoolean(_result35);
              return true;
            case 72:
              int _arg062 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result36 = semSetTransportFlagsForAdbBackup(_arg062);
              reply.writeNoException();
              reply.writeBoolean(_result36);
              return true;
            case 73:
              boolean _arg063 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result37 = semDisableDataExtractionRule(_arg063);
              reply.writeNoException();
              reply.writeBoolean(_result37);
              return true;
            case 74:
              String _arg064 = data.readString();
              List<String> _arg141 = data.createStringArrayList();
              data.enforceNoDataAvail();
              excludeKeysFromRestore(_arg064, _arg141);
              reply.writeNoException();
              return true;
            case 75:
              String _arg065 = data.readString();
              List<BackupRestoreEventLogger.DataTypeResult> _arg142 =
                  data.createTypedArrayList(BackupRestoreEventLogger.DataTypeResult.CREATOR);
              data.enforceNoDataAvail();
              reportDelayedRestoreResult(_arg065, _arg142);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IBackupManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.app.backup.IBackupManager
      public void dataChangedForUser(int userId, String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void dataChanged(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void clearBackupDataForUser(int userId, String transportName, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(transportName);
          _data.writeString(packageName);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void clearBackupData(String transportName, String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(transportName);
          _data.writeString(packageName);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void initializeTransportsForUser(
          int userId, String[] transportNames, IBackupObserver observer) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeStringArray(transportNames);
          _data.writeStrongInterface(observer);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void agentConnectedForUser(int userId, String packageName, IBinder agent)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          _data.writeStrongBinder(agent);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void agentConnected(String packageName, IBinder agent) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeStrongBinder(agent);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void agentDisconnectedForUser(int userId, String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void agentDisconnected(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void restoreAtInstallForUser(int userId, String packageName, int token)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          _data.writeInt(token);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void restoreAtInstall(String packageName, int token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(token);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void setBackupEnabledForUser(int userId, boolean isEnabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeBoolean(isEnabled);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void setFrameworkSchedulingEnabledForUser(int userId, boolean isEnabled)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeBoolean(isEnabled);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void setBackupEnabled(boolean isEnabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(isEnabled);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void setAutoRestoreForUser(int userId, boolean doAutoRestore) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeBoolean(doAutoRestore);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void setAutoRestore(boolean doAutoRestore) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(doAutoRestore);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public boolean isBackupEnabledForUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public boolean isBackupEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public boolean setBackupPassword(String currentPw, String newPw) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(currentPw);
          _data.writeString(newPw);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public boolean hasBackupPassword() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void backupNowForUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void backupNow() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void adbBackup(
          int userId,
          ParcelFileDescriptor fd,
          boolean includeApks,
          boolean includeObbs,
          boolean includeShared,
          boolean doWidgets,
          boolean allApps,
          boolean allIncludesSystem,
          boolean doCompress,
          boolean doKeyValue,
          String[] packageNames)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          try {
            _data.writeTypedObject(fd, 0);
            try {
              _data.writeBoolean(includeApks);
            } catch (Throwable th) {
              th = th;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th2) {
            th = th2;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th3) {
          th = th3;
        }
        try {
          _data.writeBoolean(includeObbs);
          try {
            _data.writeBoolean(includeShared);
            try {
              _data.writeBoolean(doWidgets);
              try {
                _data.writeBoolean(allApps);
              } catch (Throwable th4) {
                th = th4;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th5) {
              th = th5;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th6) {
            th = th6;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeBoolean(allIncludesSystem);
            try {
              _data.writeBoolean(doCompress);
              try {
                _data.writeBoolean(doKeyValue);
                try {
                  _data.writeStringArray(packageNames);
                } catch (Throwable th7) {
                  th = th7;
                }
              } catch (Throwable th8) {
                th = th8;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th9) {
              th = th9;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th10) {
            th = th10;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th11) {
          th = th11;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
        try {
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          _reply.recycle();
          _data.recycle();
        } catch (Throwable th12) {
          th = th12;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
      }

      @Override // android.app.backup.IBackupManager
      public void fullTransportBackupForUser(int userId, String[] packageNames)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeStringArray(packageNames);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void fullRestoreCustomized(
          int userId,
          String filePath,
          boolean password,
          String EncPassword,
          IMemorySaverBackupRestoreObserver observer)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(filePath);
          _data.writeBoolean(password);
          _data.writeString(EncPassword);
          _data.writeStrongInterface(observer);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void adbRestore(int userId, ParcelFileDescriptor fd) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeTypedObject(fd, 0);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void acknowledgeFullBackupOrRestoreForUser(
          int userId,
          int token,
          boolean allow,
          String curPassword,
          String encryptionPassword,
          IFullBackupRestoreObserver observer)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeInt(token);
          _data.writeBoolean(allow);
          _data.writeString(curPassword);
          _data.writeString(encryptionPassword);
          _data.writeStrongInterface(observer);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void acknowledgeFullBackupOrRestore(
          int token,
          boolean allow,
          String curPassword,
          String encryptionPassword,
          IFullBackupRestoreObserver observer)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(token);
          _data.writeBoolean(allow);
          _data.writeString(curPassword);
          _data.writeString(encryptionPassword);
          _data.writeStrongInterface(observer);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void updateTransportAttributesForUser(
          int userId,
          ComponentName transportComponent,
          String name,
          Intent configurationIntent,
          String currentDestinationString,
          Intent dataManagementIntent,
          CharSequence dataManagementLabel)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeTypedObject(transportComponent, 0);
          _data.writeString(name);
          _data.writeTypedObject(configurationIntent, 0);
          _data.writeString(currentDestinationString);
          _data.writeTypedObject(dataManagementIntent, 0);
          if (dataManagementLabel != null) {
            _data.writeInt(1);
            TextUtils.writeToParcel(dataManagementLabel, _data, 0);
          } else {
            _data.writeInt(0);
          }
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public String getCurrentTransportForUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public String getCurrentTransport() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public ComponentName getCurrentTransportComponentForUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
          ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public String[] listAllTransportsForUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
          String[] _result = _reply.createStringArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public String[] listAllTransports() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
          String[] _result = _reply.createStringArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public ComponentName[] listAllTransportComponentsForUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
          ComponentName[] _result =
              (ComponentName[]) _reply.createTypedArray(ComponentName.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public String[] getTransportWhitelist() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
          String[] _result = _reply.createStringArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public String selectBackupTransportForUser(int userId, String transport)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(transport);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public String selectBackupTransport(String transport) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(transport);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void selectBackupTransportAsyncForUser(
          int userId, ComponentName transport, ISelectBackupTransportCallback listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeTypedObject(transport, 0);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(39, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public Intent getConfigurationIntentForUser(int userId, String transport)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(transport);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
          Intent _result = (Intent) _reply.readTypedObject(Intent.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public Intent getConfigurationIntent(String transport) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(transport);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
          Intent _result = (Intent) _reply.readTypedObject(Intent.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public String getDestinationStringForUser(int userId, String transport)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(transport);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public String getDestinationString(String transport) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(transport);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public Intent getDataManagementIntentForUser(int userId, String transport)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(transport);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
          Intent _result = (Intent) _reply.readTypedObject(Intent.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public Intent getDataManagementIntent(String transport) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(transport);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
          Intent _result = (Intent) _reply.readTypedObject(Intent.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public CharSequence getDataManagementLabelForUser(int userId, String transport)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(transport);
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
          CharSequence _result =
              (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public IRestoreSession beginRestoreSessionForUser(
          int userId, String packageName, String transportID) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          _data.writeString(transportID);
          this.mRemote.transact(47, _data, _reply, 0);
          _reply.readException();
          IRestoreSession _result = IRestoreSession.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void opCompleteForUser(int userId, int token, long result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeInt(token);
          _data.writeLong(result);
          this.mRemote.transact(48, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void opComplete(int token, long result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(token);
          _data.writeLong(result);
          this.mRemote.transact(49, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void setBackupServiceActive(int whichUser, boolean makeActive) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(whichUser);
          _data.writeBoolean(makeActive);
          this.mRemote.transact(50, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public boolean isBackupServiceActive(int whichUser) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(whichUser);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public boolean isUserReadyForBackup(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(52, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public long getAvailableRestoreTokenForUser(int userId, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          this.mRemote.transact(53, _data, _reply, 0);
          _reply.readException();
          long _result = _reply.readLong();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public boolean isAppEligibleForBackupForUser(int userId, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          this.mRemote.transact(54, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public String[] filterAppsEligibleForBackupForUser(int userId, String[] packages)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeStringArray(packages);
          this.mRemote.transact(55, _data, _reply, 0);
          _reply.readException();
          String[] _result = _reply.createStringArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public int requestBackupForUser(
          int userId,
          String[] packages,
          IBackupObserver observer,
          IBackupManagerMonitor monitor,
          int flags)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeStringArray(packages);
          _data.writeStrongInterface(observer);
          _data.writeStrongInterface(monitor);
          _data.writeInt(flags);
          this.mRemote.transact(56, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public int requestBackup(
          String[] packages, IBackupObserver observer, IBackupManagerMonitor monitor, int flags)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStringArray(packages);
          _data.writeStrongInterface(observer);
          _data.writeStrongInterface(monitor);
          _data.writeInt(flags);
          this.mRemote.transact(57, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void cancelBackupsForUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(58, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void cancelBackups() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(59, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void fullBackupCustomized(
          int userId,
          String filePath,
          boolean includeApks,
          boolean includeObbs,
          boolean includeShared,
          boolean doWidgets,
          boolean allApps,
          boolean allIncludesSystem,
          boolean doCompress,
          String[] packageNames,
          boolean password,
          String EncPassword,
          boolean doKeyValue,
          IMemorySaverBackupRestoreObserver observer)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(filePath);
          try {
            _data.writeBoolean(includeApks);
          } catch (Throwable th) {
            th = th;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th2) {
          th = th2;
        }
        try {
          _data.writeBoolean(includeObbs);
          try {
            _data.writeBoolean(includeShared);
            try {
              _data.writeBoolean(doWidgets);
              try {
                _data.writeBoolean(allApps);
                try {
                  _data.writeBoolean(allIncludesSystem);
                  try {
                    _data.writeBoolean(doCompress);
                    try {
                      _data.writeStringArray(packageNames);
                      try {
                        _data.writeBoolean(password);
                      } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                      }
                    } catch (Throwable th4) {
                      th = th4;
                      _reply.recycle();
                      _data.recycle();
                      throw th;
                    }
                  } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                  }
                } catch (Throwable th6) {
                  th = th6;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th7) {
                th = th7;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th8) {
              th = th8;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th9) {
            th = th9;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeString(EncPassword);
            try {
              _data.writeBoolean(doKeyValue);
              _data.writeStrongInterface(observer);
              this.mRemote.transact(60, _data, _reply, 0);
              _reply.readException();
              _reply.recycle();
              _data.recycle();
            } catch (Throwable th10) {
              th = th10;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th11) {
            th = th11;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th12) {
          th = th12;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
      }

      @Override // android.app.backup.IBackupManager
      public UserHandle getUserForAncestralSerialNumber(long ancestralSerialNumber)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeLong(ancestralSerialNumber);
          this.mRemote.transact(61, _data, _reply, 0);
          _reply.readException();
          UserHandle _result = (UserHandle) _reply.readTypedObject(UserHandle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void setAncestralSerialNumber(long ancestralSerialNumber) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeLong(ancestralSerialNumber);
          this.mRemote.transact(62, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public boolean isSubUserSupported() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(63, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public Map semBackupPackage(
          ParcelFileDescriptor fd, String[] packageNames, String password, int flag)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(fd, 0);
          _data.writeStringArray(packageNames);
          _data.writeString(password);
          _data.writeInt(flag);
          this.mRemote.transact(64, _data, _reply, 0);
          _reply.readException();
          ClassLoader cl = getClass().getClassLoader();
          Map _result = _reply.readHashMap(cl);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public Map semBackupPackagePath(
          ParcelFileDescriptor fd, String[] packageNames, String password, int flag, String[] paths)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(fd, 0);
          _data.writeStringArray(packageNames);
          _data.writeString(password);
          _data.writeInt(flag);
          _data.writeStringArray(paths);
          this.mRemote.transact(65, _data, _reply, 0);
          _reply.readException();
          ClassLoader cl = getClass().getClassLoader();
          Map _result = _reply.readHashMap(cl);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void semRestorePackage(ParcelFileDescriptor fd, String password)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(fd, 0);
          _data.writeString(password);
          this.mRemote.transact(66, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public boolean semIsBackupEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(67, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void semSetBackupEnabled(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(68, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void semSetAutoRestoreEnabled(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(69, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public boolean semCancelBackupAndRestore() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(70, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public boolean semSetTimeoutBackupAndRestore(int timeoutMin) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(timeoutMin);
          this.mRemote.transact(71, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public boolean semSetTransportFlagsForAdbBackup(int transportFlags) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(transportFlags);
          this.mRemote.transact(72, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public boolean semDisableDataExtractionRule(boolean disabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(disabled);
          this.mRemote.transact(73, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void excludeKeysFromRestore(String packageName, List<String> keys)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeStringList(keys);
          this.mRemote.transact(74, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.backup.IBackupManager
      public void reportDelayedRestoreResult(
          String packageName, List<BackupRestoreEventLogger.DataTypeResult> results)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeTypedList(results, 0);
          this.mRemote.transact(75, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 74;
    }
  }
}
