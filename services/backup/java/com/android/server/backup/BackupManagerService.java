package com.android.server.backup;

import android.app.backup.IBackupManager;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IBackupObserver;
import android.app.backup.IFullBackupRestoreObserver;
import android.app.backup.IMemorySaverBackupRestoreObserver;
import android.app.backup.IRestoreSession;
import android.app.backup.ISelectBackupTransportCallback;
import android.app.compat.CompatChanges;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.DumpUtils;
import com.android.server.SystemConfig;
import com.android.server.SystemService;
import com.android.server.backup.utils.RandomAccessFileUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public class BackupManagerService extends IBackupManager.Stub {
  static final String DUMP_RUNNING_USERS_MESSAGE = "Backup Manager is running for users:";
  public static BackupManagerService sInstance;
  public final Context mContext;
  public int mDefaultBackupUserId;
  public final boolean mGlobalDisable;
  public final Handler mHandler;
  public boolean mHasFirstUserUnlockedSinceBoot;
  public final Object mStateLock = new Object();
  public final Set mTransportWhitelist;
  public final UserManager mUserManager;
  public final BroadcastReceiver mUserRemovedReceiver;
  public final SparseArray mUserServices;

  public boolean isSubUserSupported() {
    return true;
  }

  public static BackupManagerService getInstance() {
    BackupManagerService backupManagerService = sInstance;
    Objects.requireNonNull(backupManagerService);
    return backupManagerService;
  }

  /* renamed from: com.android.server.backup.BackupManagerService$1 */
  public class C07951 extends BroadcastReceiver {
    public C07951() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      final int intExtra;
      if (!"android.intent.action.USER_REMOVED".equals(intent.getAction())
          || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000)) <= 0) {
        return;
      }
      BackupManagerService.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.backup.BackupManagerService$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              BackupManagerService.C07951.this.lambda$onReceive$0(intExtra);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onReceive$0(int i) {
      BackupManagerService.this.onRemovedNonSystemUser(i);
    }
  }

  public BackupManagerService(Context context) {
    C07951 c07951 = new C07951();
    this.mUserRemovedReceiver = c07951;
    this.mHasFirstUserUnlockedSinceBoot = false;
    this.mContext = context;
    this.mGlobalDisable = isBackupDisabled();
    HandlerThread handlerThread = new HandlerThread("backup", 10);
    handlerThread.start();
    this.mHandler = new Handler(handlerThread.getLooper());
    this.mUserManager = UserManager.get(context);
    this.mUserServices = new SparseArray();
    Set backupTransportWhitelist = SystemConfig.getInstance().getBackupTransportWhitelist();
    this.mTransportWhitelist =
        backupTransportWhitelist == null ? Collections.emptySet() : backupTransportWhitelist;
    context.registerReceiver(c07951, new IntentFilter("android.intent.action.USER_REMOVED"));
    UserHandle mainUser = getUserManager().getMainUser();
    this.mDefaultBackupUserId = mainUser != null ? mainUser.getIdentifier() : 0;
    Slog.d("BackupManagerService", "Default backup user id = " + this.mDefaultBackupUserId);
  }

  public Handler getBackupHandler() {
    return this.mHandler;
  }

  public boolean isBackupDisabled() {
    return SystemProperties.getBoolean("ro.backup.disable", false);
  }

  public int binderGetCallingUserId() {
    return Binder.getCallingUserHandle().getIdentifier();
  }

  public int binderGetCallingUid() {
    return Binder.getCallingUid();
  }

  public File getSuppressFileForUser(int i) {
    return new File(UserBackupManagerFiles.getBaseStateDir(i), "backup-suppress");
  }

  public File getRememberActivatedFileForNonSystemUser(int i) {
    return UserBackupManagerFiles.getStateFileInSystemDir("backup-remember-activated", i);
  }

  public File getActivatedFileForUser(int i) {
    return UserBackupManagerFiles.getStateFileInSystemDir("backup-activated", i);
  }

  public final void onRemovedNonSystemUser(int i) {
    Slog.i("BackupManagerService", "Removing state for non system user " + i);
    if (FileUtils.deleteContentsAndDir(UserBackupManagerFiles.getStateDirInSystemDir(i))) {
      return;
    }
    Slog.w("BackupManagerService", "Failed to delete state dir for removed user: " + i);
  }

  public final void createFile(File file) {
    if (file.exists()) {
      return;
    }
    file.getParentFile().mkdirs();
    if (file.createNewFile()) {
      return;
    }
    Slog.w("BackupManagerService", "Failed to create file " + file.getPath());
  }

  public final void deleteFile(File file) {
    if (file.exists() && !file.delete()) {
      Slog.w("BackupManagerService", "Failed to delete file " + file.getPath());
    }
  }

  public final void deactivateBackupForUserLocked(int i) {
    if (i == 0 || i == this.mDefaultBackupUserId) {
      createFile(getSuppressFileForUser(i));
    } else {
      deleteFile(getActivatedFileForUser(i));
    }
  }

  public final void activateBackupForUserLocked(int i) {
    if (i == 0 || i == this.mDefaultBackupUserId) {
      deleteFile(getSuppressFileForUser(i));
    } else {
      createFile(getActivatedFileForUser(i));
    }
  }

  public boolean isUserReadyForBackup(int i) {
    enforceCallingPermissionOnUserId(i, "isUserReadyForBackup()");
    return this.mUserServices.get(i) != null;
  }

  public final boolean isBackupActivatedForUser(int i) {
    if (getSuppressFileForUser(0).exists()) {
      return false;
    }
    boolean z = i == this.mDefaultBackupUserId;
    if (i == 0 && !z) {
      return false;
    }
    if (z && getSuppressFileForUser(i).exists()) {
      return false;
    }
    return z || getActivatedFileForUser(i).exists();
  }

  public Context getContext() {
    return this.mContext;
  }

  public UserManager getUserManager() {
    return this.mUserManager;
  }

  public void postToHandler(Runnable runnable) {
    this.mHandler.post(runnable);
  }

  public void startServiceForUser(int i) {
    if (this.mGlobalDisable) {
      Slog.i("BackupManagerService", "Backup service not supported");
      return;
    }
    if (!isBackupActivatedForUser(i)) {
      Slog.i("BackupManagerService", "Backup not activated for user " + i);
      return;
    }
    if (this.mUserServices.get(i) != null) {
      Slog.i("BackupManagerService", "userId " + i + " already started, so not starting again");
      return;
    }
    Slog.i("BackupManagerService", "Starting service for user: " + i);
    startServiceForUser(
        i,
        UserBackupManagerService.createAndInitializeService(
            i, this.mContext, this, this.mTransportWhitelist));
  }

  public void startServiceForUser(int i, UserBackupManagerService userBackupManagerService) {
    this.mUserServices.put(i, userBackupManagerService);
    Trace.traceBegin(64L, "backup enable");
    userBackupManagerService.initializeBackupEnableState();
    Trace.traceEnd(64L);
  }

  public void stopServiceForUser(int i) {
    UserBackupManagerService userBackupManagerService =
        (UserBackupManagerService) this.mUserServices.removeReturnOld(i);
    if (userBackupManagerService != null) {
      userBackupManagerService.tearDownService();
      KeyValueBackupJob.cancel(i, this.mContext);
      FullBackupJob.cancel(i, this.mContext);
    }
  }

  public SparseArray getUserServices() {
    return this.mUserServices;
  }

  public void onStopUser(final int i) {
    postToHandler(
        new Runnable() { // from class:
                         // com.android.server.backup.BackupManagerService$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            BackupManagerService.this.lambda$onStopUser$0(i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onStopUser$0(int i) {
    if (this.mGlobalDisable) {
      return;
    }
    Slog.i("BackupManagerService", "Stopping service for user: " + i);
    stopServiceForUser(i);
  }

  public final void enforcePermissionsOnUser(int i) {
    if (i == 0 || getUserManager().getUserInfo(i).isManagedProfile()) {
      int binderGetCallingUid = binderGetCallingUid();
      if (binderGetCallingUid != 1000 && binderGetCallingUid != 0) {
        throw new SecurityException("No permission to configure backup activity");
      }
      return;
    }
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.BACKUP", "No permission to configure backup activity");
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.INTERACT_ACROSS_USERS_FULL",
        "No permission to configure backup activity");
  }

  public void setBackupServiceActive(int i, boolean z) {
    enforcePermissionsOnUser(i);
    if (i != 0) {
      try {
        File rememberActivatedFileForNonSystemUser = getRememberActivatedFileForNonSystemUser(i);
        createFile(rememberActivatedFileForNonSystemUser);
        RandomAccessFileUtils.writeBoolean(rememberActivatedFileForNonSystemUser, z);
      } catch (IOException e) {
        Slog.e("BackupManagerService", "Unable to persist backup service activity", e);
      }
    }
    if (this.mGlobalDisable) {
      Slog.i("BackupManagerService", "Backup service not supported");
      return;
    }
    synchronized (this.mStateLock) {
      StringBuilder sb = new StringBuilder();
      sb.append("Making backup ");
      sb.append(z ? "" : "in");
      sb.append("active");
      Slog.i("BackupManagerService", sb.toString());
      if (z) {
        try {
          activateBackupForUserLocked(i);
        } catch (IOException unused) {
          Slog.e("BackupManagerService", "Unable to persist backup service activity");
        }
        if (getUserManager().isUserUnlocked(i)) {
          long clearCallingIdentity = Binder.clearCallingIdentity();
          try {
            startServiceForUser(i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
          } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
          }
        }
      } else {
        try {
          deactivateBackupForUserLocked(i);
        } catch (IOException unused2) {
          Slog.e("BackupManagerService", "Unable to persist backup service inactivity");
        }
        onStopUser(i);
      }
    }
  }

  public boolean isBackupServiceActive(int i) {
    boolean z;
    if (CompatChanges.isChangeEnabled(158482162L, Binder.getCallingUid())) {
      this.mContext.enforceCallingOrSelfPermission(
          "android.permission.BACKUP", "isBackupServiceActive");
    }
    synchronized (this.mStateLock) {
      z = !this.mGlobalDisable && isBackupActivatedForUser(i);
    }
    return z;
  }

  public void dataChangedForUser(int i, String str) {
    if (isUserReadyForBackup(i)) {
      dataChanged(i, str);
    }
  }

  public void dataChanged(String str) {
    dataChangedForUser(binderGetCallingUserId(), str);
  }

  public void dataChanged(int i, String str) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "dataChanged()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.dataChanged(str);
    }
  }

  public void initializeTransportsForUser(int i, String[] strArr, IBackupObserver iBackupObserver) {
    if (isUserReadyForBackup(i)) {
      initializeTransports(i, strArr, iBackupObserver);
    }
  }

  public void initializeTransports(int i, String[] strArr, IBackupObserver iBackupObserver) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "initializeTransports()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.initializeTransports(strArr, iBackupObserver);
    }
  }

  public void clearBackupDataForUser(int i, String str, String str2) {
    if (isUserReadyForBackup(i)) {
      clearBackupData(i, str, str2);
    }
  }

  public void clearBackupData(int i, String str, String str2) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "clearBackupData()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.clearBackupData(str, str2);
    }
  }

  public void clearBackupData(String str, String str2) {
    clearBackupDataForUser(binderGetCallingUserId(), str, str2);
  }

  public void agentConnectedForUser(int i, String str, IBinder iBinder) {
    if (isUserReadyForBackup(i)) {
      agentConnected(i, str, iBinder);
    }
  }

  public void agentConnected(String str, IBinder iBinder) {
    agentConnectedForUser(binderGetCallingUserId(), str, iBinder);
  }

  public void agentConnected(int i, String str, IBinder iBinder) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "agentConnected()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.agentConnected(str, iBinder);
    }
  }

  public void agentDisconnectedForUser(int i, String str) {
    if (isUserReadyForBackup(i)) {
      agentDisconnected(i, str);
    }
  }

  public void agentDisconnected(String str) {
    agentDisconnectedForUser(binderGetCallingUserId(), str);
  }

  public void agentDisconnected(int i, String str) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "agentDisconnected()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.agentDisconnected(str);
    }
  }

  public void restoreAtInstallForUser(int i, String str, int i2) {
    if (isUserReadyForBackup(i)) {
      restoreAtInstall(i, str, i2);
    }
  }

  public void restoreAtInstall(String str, int i) {
    restoreAtInstallForUser(binderGetCallingUserId(), str, i);
  }

  public void restoreAtInstall(int i, String str, int i2) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "restoreAtInstall()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.restoreAtInstall(str, i2);
    }
  }

  public void setFrameworkSchedulingEnabledForUser(int i, boolean z) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "setFrameworkSchedulingEnabledForUser()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.setFrameworkSchedulingEnabled(z);
    }
  }

  public void setBackupEnabledForUser(int i, boolean z) {
    if (isUserReadyForBackup(i)) {
      setBackupEnabled(i, z);
    }
  }

  public void setBackupEnabled(boolean z) {
    setBackupEnabledForUser(binderGetCallingUserId(), z);
  }

  public void setBackupEnabled(int i, boolean z) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "setBackupEnabled()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.setBackupEnabled(z);
    }
  }

  public void setAutoRestoreForUser(int i, boolean z) {
    if (isUserReadyForBackup(i)) {
      setAutoRestore(i, z);
    }
  }

  public void setAutoRestore(boolean z) {
    setAutoRestoreForUser(binderGetCallingUserId(), z);
  }

  public void setAutoRestore(int i, boolean z) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "setAutoRestore()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.setAutoRestore(z);
    }
  }

  public boolean isBackupEnabledForUser(int i) {
    return isUserReadyForBackup(i) && isBackupEnabled(i);
  }

  public boolean isBackupEnabled() {
    return isBackupEnabledForUser(binderGetCallingUserId());
  }

  public boolean isBackupEnabled(int i) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "isBackupEnabled()");
    return serviceForUserIfCallerHasPermission != null
        && serviceForUserIfCallerHasPermission.isBackupEnabled();
  }

  public boolean setBackupPassword(String str, String str2) {
    UserBackupManagerService serviceForUserIfCallerHasPermission;
    return isUserReadyForBackup(binderGetCallingUserId())
        && (serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(0, "setBackupPassword()"))
            != null
        && serviceForUserIfCallerHasPermission.setBackupPassword(str, str2);
  }

  public boolean hasBackupPassword() {
    UserBackupManagerService serviceForUserIfCallerHasPermission;
    return isUserReadyForBackup(binderGetCallingUserId())
        && (serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(0, "hasBackupPassword()"))
            != null
        && serviceForUserIfCallerHasPermission.hasBackupPassword();
  }

  public void backupNowForUser(int i) {
    if (isUserReadyForBackup(i)) {
      backupNow(i);
    }
  }

  public void backupNow() {
    backupNowForUser(binderGetCallingUserId());
  }

  public void backupNow(int i) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "backupNow()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.backupNow();
    }
  }

  public void adbBackup(
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
      String[] strArr) {
    UserBackupManagerService serviceForUserIfCallerHasPermission;
    if (isUserReadyForBackup(i)
        && (serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(i, "adbBackup()"))
            != null) {
      serviceForUserIfCallerHasPermission.adbBackup(
          parcelFileDescriptor, z, z2, z3, z4, z5, z6, z7, z8, strArr);
    }
  }

  public void fullTransportBackupForUser(int i, String[] strArr) {
    if (isUserReadyForBackup(i)) {
      fullTransportBackup(i, strArr);
    }
  }

  public void fullTransportBackup(int i, String[] strArr) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "fullTransportBackup()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.fullTransportBackup(strArr);
    }
  }

  public void adbRestore(int i, ParcelFileDescriptor parcelFileDescriptor) {
    UserBackupManagerService serviceForUserIfCallerHasPermission;
    if (isUserReadyForBackup(i)
        && (serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(i, "adbRestore()"))
            != null) {
      serviceForUserIfCallerHasPermission.adbRestore(parcelFileDescriptor);
    }
  }

  public void acknowledgeFullBackupOrRestoreForUser(
      int i,
      int i2,
      boolean z,
      String str,
      String str2,
      IFullBackupRestoreObserver iFullBackupRestoreObserver) {
    if (isUserReadyForBackup(i)) {
      acknowledgeAdbBackupOrRestore(i, i2, z, str, str2, iFullBackupRestoreObserver);
    }
  }

  public void acknowledgeAdbBackupOrRestore(
      int i,
      int i2,
      boolean z,
      String str,
      String str2,
      IFullBackupRestoreObserver iFullBackupRestoreObserver) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "acknowledgeAdbBackupOrRestore()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.acknowledgeAdbBackupOrRestore(
          i2, z, str, str2, iFullBackupRestoreObserver);
    }
  }

  public void fullRestoreCustomized(
      int i,
      String str,
      boolean z,
      String str2,
      IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) {
    try {
      UserBackupManagerService serviceForUserIfCallerHasPermission =
          getServiceForUserIfCallerHasPermission(i, "fullRestoreCustomized()");
      if (serviceForUserIfCallerHasPermission != null) {
        serviceForUserIfCallerHasPermission.fullRestoreCustomized(
            str, z, str2, iMemorySaverBackupRestoreObserver);
      }
    } catch (Exception unused) {
      Slog.i("BackupManagerService", "userId " + i + " fullRestoreCustomized error ");
    }
  }

  public void fullBackupCustomized(
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
      IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) {
    try {
      UserBackupManagerService serviceForUserIfCallerHasPermission =
          getServiceForUserIfCallerHasPermission(i, "fullBackupCustomized()");
      if (serviceForUserIfCallerHasPermission != null) {
        serviceForUserIfCallerHasPermission.fullBackupCustomized(
            str,
            z,
            z2,
            z3,
            z4,
            z5,
            z6,
            z7,
            strArr,
            z8,
            str2,
            z9,
            iMemorySaverBackupRestoreObserver);
      }
    } catch (Exception unused) {
      Slog.i("BackupManagerService", "userId " + i + " fullbackupCustomized error");
    }
  }

  public void acknowledgeFullBackupOrRestore(
      int i,
      boolean z,
      String str,
      String str2,
      IFullBackupRestoreObserver iFullBackupRestoreObserver) {
    acknowledgeFullBackupOrRestoreForUser(
        binderGetCallingUserId(), i, z, str, str2, iFullBackupRestoreObserver);
  }

  public Map semBackupPackage(
      ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(binderGetCallingUserId(), "semBackupPackage()");
    if (serviceForUserIfCallerHasPermission != null) {
      return serviceForUserIfCallerHasPermission.semBackupPackage(
          parcelFileDescriptor, strArr, str, i);
    }
    return null;
  }

  public Map semBackupPackagePath(
      ParcelFileDescriptor parcelFileDescriptor,
      String[] strArr,
      String str,
      int i,
      String[] strArr2) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(binderGetCallingUserId(), "semBackupPackagePath()");
    if (serviceForUserIfCallerHasPermission != null) {
      return serviceForUserIfCallerHasPermission.semBackupPackagePath(
          parcelFileDescriptor, strArr, str, i, strArr2);
    }
    return null;
  }

  public void semRestorePackage(ParcelFileDescriptor parcelFileDescriptor, String str) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(binderGetCallingUserId(), "semRestorePackage()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.semRestorePackage(parcelFileDescriptor, str);
    }
  }

  public boolean semIsBackupEnabled() {
    int binderGetCallingUserId = binderGetCallingUserId();
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(binderGetCallingUserId, "semIsBackupEnabled()");
    if (isUserReadyForBackup(binderGetCallingUserId)
        && serviceForUserIfCallerHasPermission != null) {
      return serviceForUserIfCallerHasPermission.semIsBackupEnabled();
    }
    return false;
  }

  public void semSetBackupEnabled(boolean z) {
    int binderGetCallingUserId = binderGetCallingUserId();
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(binderGetCallingUserId, "semSetBackupEnabled()");
    if (isUserReadyForBackup(binderGetCallingUserId)
        && serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.semSetBackupEnabled(z);
    }
  }

  public void semSetAutoRestoreEnabled(boolean z) {
    int binderGetCallingUserId = binderGetCallingUserId();
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(
            binderGetCallingUserId, "semSetAutoRestoreEnabled()");
    if (isUserReadyForBackup(binderGetCallingUserId)
        && serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.semSetAutoRestoreEnabled(z);
    }
  }

  public boolean semCancelBackupAndRestore() {
    Slog.i("BackupManagerService", "semCancelBackupAndRestore Start");
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(
            binderGetCallingUserId(), "semCancelBackupAndRestore()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.semCancelBackupAndRestore();
      return true;
    }
    Slog.e("BackupManagerService", "Fail - UserBackupManagerService null");
    return false;
  }

  public boolean semSetTimeoutBackupAndRestore(int i) {
    Slog.i("BackupManagerService", "semSetTimeoutBackupAndRestore Start");
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(
            binderGetCallingUserId(), "semSetTimeoutBackupAndRestore()");
    if (serviceForUserIfCallerHasPermission != null) {
      return serviceForUserIfCallerHasPermission.semSetTimeoutBackupAndRestore(i);
    }
    Slog.e("BackupManagerService", "Fail - UserBackupManagerService null");
    return false;
  }

  public boolean semSetTransportFlagsForAdbBackup(int i) {
    Slog.i("BackupManagerService", "semSetTransportFlagsForAdbBackup Start");
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(
            binderGetCallingUserId(), "semSetTransportFlagsForAdbBackup()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.semSetTransportFlagsForAdbBackup(i);
      return true;
    }
    Slog.e("BackupManagerService", "Fail - UserBackupManagerService null");
    return false;
  }

  public boolean semDisableDataExtractionRule(boolean z) {
    Slog.i("BackupManagerService", "semDisableDataExtractionRule Start, set  = " + z);
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(
            binderGetCallingUserId(), "semDisableDataExtractionRule()");
    if (serviceForUserIfCallerHasPermission != null) {
      return serviceForUserIfCallerHasPermission.semDisableDataExtractionRule(z);
    }
    Slog.e("BackupManagerService", "Fail - UserBackupManagerService null");
    return false;
  }

  public String getCurrentTransportForUser(int i) {
    if (isUserReadyForBackup(i)) {
      return getCurrentTransport(i);
    }
    return null;
  }

  public String getCurrentTransport() {
    return getCurrentTransportForUser(binderGetCallingUserId());
  }

  public String getCurrentTransport(int i) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "getCurrentTransport()");
    if (serviceForUserIfCallerHasPermission == null) {
      return null;
    }
    return serviceForUserIfCallerHasPermission.getCurrentTransport();
  }

  public ComponentName getCurrentTransportComponentForUser(int i) {
    if (isUserReadyForBackup(i)) {
      return getCurrentTransportComponent(i);
    }
    return null;
  }

  public ComponentName getCurrentTransportComponent(int i) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "getCurrentTransportComponent()");
    if (serviceForUserIfCallerHasPermission == null) {
      return null;
    }
    return serviceForUserIfCallerHasPermission.getCurrentTransportComponent();
  }

  public String[] listAllTransportsForUser(int i) {
    if (isUserReadyForBackup(i)) {
      return listAllTransports(i);
    }
    return null;
  }

  public String[] listAllTransports(int i) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "listAllTransports()");
    if (serviceForUserIfCallerHasPermission == null) {
      return null;
    }
    return serviceForUserIfCallerHasPermission.listAllTransports();
  }

  public String[] listAllTransports() {
    return listAllTransportsForUser(binderGetCallingUserId());
  }

  public ComponentName[] listAllTransportComponentsForUser(int i) {
    if (isUserReadyForBackup(i)) {
      return listAllTransportComponents(i);
    }
    return null;
  }

  public ComponentName[] listAllTransportComponents(int i) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "listAllTransportComponents()");
    if (serviceForUserIfCallerHasPermission == null) {
      return null;
    }
    return serviceForUserIfCallerHasPermission.listAllTransportComponents();
  }

  public String[] getTransportWhitelist() {
    if (!isUserReadyForBackup(binderGetCallingUserId())) {
      return null;
    }
    String[] strArr = new String[this.mTransportWhitelist.size()];
    Iterator it = this.mTransportWhitelist.iterator();
    int i = 0;
    while (it.hasNext()) {
      strArr[i] = ((ComponentName) it.next()).flattenToShortString();
      i++;
    }
    return strArr;
  }

  public void updateTransportAttributesForUser(
      int i,
      ComponentName componentName,
      String str,
      Intent intent,
      String str2,
      Intent intent2,
      CharSequence charSequence) {
    if (isUserReadyForBackup(i)) {
      updateTransportAttributes(i, componentName, str, intent, str2, intent2, charSequence);
    }
  }

  public void updateTransportAttributes(
      int i,
      ComponentName componentName,
      String str,
      Intent intent,
      String str2,
      Intent intent2,
      CharSequence charSequence) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "updateTransportAttributes()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.updateTransportAttributes(
          componentName, str, intent, str2, intent2, charSequence);
    }
  }

  public String selectBackupTransportForUser(int i, String str) {
    if (isUserReadyForBackup(i)) {
      return selectBackupTransport(i, str);
    }
    return null;
  }

  public String selectBackupTransport(String str) {
    return selectBackupTransportForUser(binderGetCallingUserId(), str);
  }

  public String selectBackupTransport(int i, String str) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "selectBackupTransport()");
    if (serviceForUserIfCallerHasPermission == null) {
      return null;
    }
    return serviceForUserIfCallerHasPermission.selectBackupTransport(str);
  }

  public void selectBackupTransportAsyncForUser(
      int i,
      ComponentName componentName,
      ISelectBackupTransportCallback iSelectBackupTransportCallback) {
    if (isUserReadyForBackup(i)) {
      selectBackupTransportAsync(i, componentName, iSelectBackupTransportCallback);
    } else if (iSelectBackupTransportCallback != null) {
      try {
        iSelectBackupTransportCallback.onFailure(-2001);
      } catch (RemoteException unused) {
      }
    }
  }

  public void selectBackupTransportAsync(
      int i,
      ComponentName componentName,
      ISelectBackupTransportCallback iSelectBackupTransportCallback) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "selectBackupTransportAsync()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.selectBackupTransportAsync(
          componentName, iSelectBackupTransportCallback);
    }
  }

  public Intent getConfigurationIntentForUser(int i, String str) {
    if (isUserReadyForBackup(i)) {
      return getConfigurationIntent(i, str);
    }
    return null;
  }

  public Intent getConfigurationIntent(String str) {
    return getConfigurationIntentForUser(binderGetCallingUserId(), str);
  }

  public Intent getConfigurationIntent(int i, String str) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "getConfigurationIntent()");
    if (serviceForUserIfCallerHasPermission == null) {
      return null;
    }
    return serviceForUserIfCallerHasPermission.getConfigurationIntent(str);
  }

  public String getDestinationStringForUser(int i, String str) {
    if (isUserReadyForBackup(i)) {
      return getDestinationString(i, str);
    }
    return null;
  }

  public String getDestinationString(String str) {
    return getDestinationStringForUser(binderGetCallingUserId(), str);
  }

  public String getDestinationString(int i, String str) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "getDestinationString()");
    if (serviceForUserIfCallerHasPermission == null) {
      return null;
    }
    return serviceForUserIfCallerHasPermission.getDestinationString(str);
  }

  public Intent getDataManagementIntentForUser(int i, String str) {
    if (isUserReadyForBackup(i)) {
      return getDataManagementIntent(i, str);
    }
    return null;
  }

  public Intent getDataManagementIntent(String str) {
    return getDataManagementIntentForUser(binderGetCallingUserId(), str);
  }

  public Intent getDataManagementIntent(int i, String str) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "getDataManagementIntent()");
    if (serviceForUserIfCallerHasPermission == null) {
      return null;
    }
    return serviceForUserIfCallerHasPermission.getDataManagementIntent(str);
  }

  public CharSequence getDataManagementLabelForUser(int i, String str) {
    if (isUserReadyForBackup(i)) {
      return getDataManagementLabel(i, str);
    }
    return null;
  }

  public CharSequence getDataManagementLabel(int i, String str) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "getDataManagementLabel()");
    if (serviceForUserIfCallerHasPermission == null) {
      return null;
    }
    return serviceForUserIfCallerHasPermission.getDataManagementLabel(str);
  }

  public IRestoreSession beginRestoreSessionForUser(int i, String str, String str2) {
    if (isUserReadyForBackup(i)) {
      return beginRestoreSession(i, str, str2);
    }
    return null;
  }

  public IRestoreSession beginRestoreSession(int i, String str, String str2) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "beginRestoreSession()");
    if (serviceForUserIfCallerHasPermission == null) {
      return null;
    }
    return serviceForUserIfCallerHasPermission.beginRestoreSession(str, str2);
  }

  public void opCompleteForUser(int i, int i2, long j) {
    if (isUserReadyForBackup(i)) {
      opComplete(i, i2, j);
    }
  }

  public void opComplete(int i, long j) {
    opCompleteForUser(binderGetCallingUserId(), i, j);
  }

  public void opComplete(int i, int i2, long j) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "opComplete()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.opComplete(i2, j);
    }
  }

  public long getAvailableRestoreTokenForUser(int i, String str) {
    if (isUserReadyForBackup(i)) {
      return getAvailableRestoreToken(i, str);
    }
    return 0L;
  }

  public long getAvailableRestoreToken(int i, String str) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "getAvailableRestoreToken()");
    if (serviceForUserIfCallerHasPermission == null) {
      return 0L;
    }
    return serviceForUserIfCallerHasPermission.getAvailableRestoreToken(str);
  }

  public boolean isAppEligibleForBackupForUser(int i, String str) {
    return isUserReadyForBackup(i) && isAppEligibleForBackup(i, str);
  }

  public boolean isAppEligibleForBackup(int i, String str) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "isAppEligibleForBackup()");
    return serviceForUserIfCallerHasPermission != null
        && serviceForUserIfCallerHasPermission.isAppEligibleForBackup(str);
  }

  public String[] filterAppsEligibleForBackupForUser(int i, String[] strArr) {
    if (isUserReadyForBackup(i)) {
      return filterAppsEligibleForBackup(i, strArr);
    }
    return null;
  }

  public String[] filterAppsEligibleForBackup(int i, String[] strArr) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "filterAppsEligibleForBackup()");
    if (serviceForUserIfCallerHasPermission == null) {
      return null;
    }
    return serviceForUserIfCallerHasPermission.filterAppsEligibleForBackup(strArr);
  }

  public int requestBackupForUser(
      int i,
      String[] strArr,
      IBackupObserver iBackupObserver,
      IBackupManagerMonitor iBackupManagerMonitor,
      int i2) {
    if (isUserReadyForBackup(i)) {
      return requestBackup(i, strArr, iBackupObserver, iBackupManagerMonitor, i2);
    }
    return -2001;
  }

  public int requestBackup(
      String[] strArr,
      IBackupObserver iBackupObserver,
      IBackupManagerMonitor iBackupManagerMonitor,
      int i) {
    return requestBackup(
        binderGetCallingUserId(), strArr, iBackupObserver, iBackupManagerMonitor, i);
  }

  public int requestBackup(
      int i,
      String[] strArr,
      IBackupObserver iBackupObserver,
      IBackupManagerMonitor iBackupManagerMonitor,
      int i2) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "requestBackup()");
    if (serviceForUserIfCallerHasPermission == null) {
      return -2001;
    }
    return serviceForUserIfCallerHasPermission.requestBackup(
        strArr, iBackupObserver, iBackupManagerMonitor, i2);
  }

  public void cancelBackupsForUser(int i) {
    if (isUserReadyForBackup(i)) {
      cancelBackups(i);
    }
  }

  public void cancelBackups() {
    cancelBackupsForUser(binderGetCallingUserId());
  }

  public void cancelBackups(int i) {
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(i, "cancelBackups()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.cancelBackups();
    }
  }

  public UserHandle getUserForAncestralSerialNumber(long j) {
    if (this.mGlobalDisable) {
      return null;
    }
    int identifier = Binder.getCallingUserHandle().getIdentifier();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      int[] profileIds = getUserManager().getProfileIds(identifier, false);
      Binder.restoreCallingIdentity(clearCallingIdentity);
      for (int i : profileIds) {
        UserBackupManagerService userBackupManagerService =
            (UserBackupManagerService) this.mUserServices.get(i);
        if (userBackupManagerService != null
            && userBackupManagerService.getAncestralSerialNumber() == j) {
          return UserHandle.of(i);
        }
      }
      return null;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public void setAncestralSerialNumber(long j) {
    UserBackupManagerService serviceForUserIfCallerHasPermission;
    if (this.mGlobalDisable
        || (serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(
                    Binder.getCallingUserHandle().getIdentifier(), "setAncestralSerialNumber()"))
            == null) {
      return;
    }
    serviceForUserIfCallerHasPermission.setAncestralSerialNumber(j);
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (DumpUtils.checkDumpAndUsageStatsPermission(
        this.mContext, "BackupManagerService", printWriter)) {
      dumpWithoutCheckingPermission(fileDescriptor, printWriter, strArr);
    }
  }

  public void dumpWithoutCheckingPermission(
      FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (!isUserReadyForBackup(binderGetCallingUserId())) {
      printWriter.println("Inactive");
      return;
    }
    int i = 0;
    if (strArr != null) {
      for (String str : strArr) {
        if ("-h".equals(str)) {
          printWriter.println("'dumpsys backup' optional arguments:");
          printWriter.println("  -h       : this help text");
          printWriter.println("  a[gents] : dump information about defined backup agents");
          printWriter.println("  transportclients : dump information about transport clients");
          printWriter.println("  transportstats : dump transport statts");
          printWriter.println(
              "  users    : dump the list of users for which backup service is running");
          return;
        }
        if ("users".equals(str.toLowerCase())) {
          printWriter.print(DUMP_RUNNING_USERS_MESSAGE);
          while (i < this.mUserServices.size()) {
            printWriter.print(" " + this.mUserServices.keyAt(i));
            i++;
          }
          printWriter.println();
          return;
        }
      }
    }
    while (i < this.mUserServices.size()) {
      UserBackupManagerService serviceForUserIfCallerHasPermission =
          getServiceForUserIfCallerHasPermission(this.mUserServices.keyAt(i), "dump()");
      if (serviceForUserIfCallerHasPermission != null) {
        serviceForUserIfCallerHasPermission.dump(fileDescriptor, printWriter, strArr);
      }
      i++;
    }
  }

  public boolean beginFullBackup(int i, FullBackupJob fullBackupJob) {
    UserBackupManagerService serviceForUserIfCallerHasPermission;
    return isUserReadyForBackup(i)
        && (serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(i, "beginFullBackup()"))
            != null
        && serviceForUserIfCallerHasPermission.beginFullBackup(fullBackupJob);
  }

  public void endFullBackup(int i) {
    UserBackupManagerService serviceForUserIfCallerHasPermission;
    if (isUserReadyForBackup(i)
        && (serviceForUserIfCallerHasPermission =
                getServiceForUserIfCallerHasPermission(i, "endFullBackup()"))
            != null) {
      serviceForUserIfCallerHasPermission.endFullBackup();
    }
  }

  public void excludeKeysFromRestore(String str, List list) {
    int identifier = Binder.getCallingUserHandle().getIdentifier();
    if (!isUserReadyForBackup(identifier)) {
      Slog.w(
          "BackupManagerService",
          "Returning from excludeKeysFromRestore as backup for user"
              + identifier
              + " is not initialized yet");
      return;
    }
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(identifier, "excludeKeysFromRestore()");
    if (serviceForUserIfCallerHasPermission != null) {
      serviceForUserIfCallerHasPermission.excludeKeysFromRestore(str, list);
    }
  }

  public void reportDelayedRestoreResult(String str, List list) {
    int identifier = Binder.getCallingUserHandle().getIdentifier();
    if (!isUserReadyForBackup(identifier)) {
      Slog.w(
          "BackupManagerService",
          "Returning from reportDelayedRestoreResult as backup for user"
              + identifier
              + " is not initialized yet");
      return;
    }
    UserBackupManagerService serviceForUserIfCallerHasPermission =
        getServiceForUserIfCallerHasPermission(identifier, "reportDelayedRestoreResult()");
    if (serviceForUserIfCallerHasPermission != null) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        serviceForUserIfCallerHasPermission.reportDelayedRestoreResult(str, list);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public UserBackupManagerService getServiceForUserIfCallerHasPermission(int i, String str) {
    enforceCallingPermissionOnUserId(i, str);
    UserBackupManagerService userBackupManagerService =
        (UserBackupManagerService) this.mUserServices.get(i);
    if (userBackupManagerService == null) {
      Slog.w("BackupManagerService", "Called " + str + " for unknown user: " + i);
    }
    return userBackupManagerService;
  }

  public void enforceCallingPermissionOnUserId(int i, String str) {
    if (Binder.getCallingUserHandle().getIdentifier() != i) {
      this.mContext.enforceCallingOrSelfPermission(
          "android.permission.INTERACT_ACROSS_USERS_FULL", str);
    }
  }

  public class Lifecycle extends SystemService {
    public Lifecycle(Context context) {
      this(context, new BackupManagerService(context));
    }

    public Lifecycle(Context context, BackupManagerService backupManagerService) {
      super(context);
      BackupManagerService.sInstance = backupManagerService;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
      publishService("backup", BackupManagerService.sInstance);
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(final SystemService.TargetUser targetUser) {
      BackupManagerService.sInstance.postToHandler(
          new Runnable() { // from class:
                           // com.android.server.backup.BackupManagerService$Lifecycle$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              BackupManagerService.Lifecycle.lambda$onUserUnlocking$0(
                  SystemService.TargetUser.this);
            }
          });
    }

    public static /* synthetic */ void lambda$onUserUnlocking$0(
        SystemService.TargetUser targetUser) {
      BackupManagerService.sInstance.updateDefaultBackupUserIdIfNeeded();
      BackupManagerService.sInstance.startServiceForUser(targetUser.getUserIdentifier());
      BackupManagerService.sInstance.mHasFirstUserUnlockedSinceBoot = true;
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(SystemService.TargetUser targetUser) {
      BackupManagerService.sInstance.onStopUser(targetUser.getUserIdentifier());
    }

    public void publishService(String str, IBinder iBinder) {
      publishBinderService(str, iBinder);
    }
  }

  public final void updateDefaultBackupUserIdIfNeeded() {
    UserHandle mainUser;
    if (this.mHasFirstUserUnlockedSinceBoot
        || this.mDefaultBackupUserId != 0
        || (mainUser = getUserManager().getMainUser()) == null
        || this.mDefaultBackupUserId == mainUser.getIdentifier()) {
      return;
    }
    int i = this.mDefaultBackupUserId;
    this.mDefaultBackupUserId = mainUser.getIdentifier();
    if (!isBackupActivatedForUser(i)) {
      stopServiceForUser(i);
    }
    Slog.i(
        "BackupManagerService",
        "Default backup user changed from " + i + " to " + this.mDefaultBackupUserId);
  }
}
