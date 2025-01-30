package com.android.server.knox.dar;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.UserSwitchObserver;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IZtdListener;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;
import com.android.server.LocalServices;
import com.android.server.knox.dar.ddar.core.DualDarDoManagerImpl;
import com.android.server.knox.dar.sdp.SDPLog;
import com.android.server.knox.dar.sdp.SdpManagerImpl;
import com.android.server.knox.dar.sdp.security.BytesUtil;
import com.android.server.knox.p010zt.devicetrust.EndpointMonitorImpl;
import com.android.server.pm.UserManagerInternal;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.dar.sdp.ISdpListener;
import com.samsung.android.knox.ddar.IDualDARPolicy;
import com.samsung.android.knox.sdp.core.SdpCreationParam;
import com.samsung.android.knox.sdp.core.SdpEngineInfo;
import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;
import com.samsung.android.security.keystore.AttestParameterSpec;
import com.samsung.android.security.keystore.AttestationUtils;
import com.samsung.android.service.DeviceRootKeyService.DeviceRootKeyServiceManager;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/* loaded from: classes2.dex */
public class DarManagerService extends IDarManagerService.Stub {
  public static final UserInfo NULL_USER = new UserInfo(-10000, (String) null, (String) null, 0);
  public static boolean mSystemReady = false;
  public final Context mContext;
  public final DarDatabaseCache mDarDatabaseCache;
  public DarHandler mDarHandler;
  public DualDarDoManagerImpl mDualDarDoManagerImpl;
  public EndpointMonitorImpl mEndpointMonitorImpl;
  public LockPatternUtils.EscrowTokenStateChangeCallback mEscrowTokenStateChangeCallback;
  public final Injector mInjector;
  public KeyProtector mKeyProtector;
  public final LockPatternUtils mLockPatternUtils;
  public SdpManagerImpl mSdpManagerImpl;
  public UserManager mUserManager;
  public UserSwitchObserver mUserSwitchObserver;
  public final VirtualLockImpl mVirtualLockImpl;

  public boolean isDarSupported() {
    return true;
  }

  public DarManagerService(Context context) {
    this(new Injector(context));
  }

  public DarManagerService(Injector injector) {
    this.mKeyProtector = null;
    this.mUserManager = null;
    this.mDarHandler = null;
    this.mEscrowTokenStateChangeCallback =
        new LockPatternUtils
            .EscrowTokenStateChangeCallback() { // from class:
                                                // com.android.server.knox.dar.DarManagerService$$ExternalSyntheticLambda1
          public final void onEscrowTokenActivated(long j, int i) {
            DarManagerService.this.lambda$new$0(j, i);
          }
        };
    this.mUserSwitchObserver =
        new UserSwitchObserver() { // from class: com.android.server.knox.dar.DarManagerService.1
          public void onLockedBootComplete(int i) {
            Log.i("DarManagerService", "onLockedBootComplete: " + i);
            DarManagerService.this.mDarHandler.sendMessage(
                DarManagerService.this.mDarHandler.obtainMessage(120, i, 0));
          }
        };
    Log.i("DarManagerService", "DarManagerService init");
    this.mContext = injector.getContext();
    this.mInjector = injector;
    this.mKeyProtector = injector.getKeyProtector();
    this.mUserManager = injector.getUserManager();
    this.mLockPatternUtils = injector.getLockPatternUtils();
    this.mDarDatabaseCache = injector.getDarDatabaseCache();
    injector.setEscrowTokenStateChangeCallback(this.mEscrowTokenStateChangeCallback);
    systemReady();
    this.mVirtualLockImpl = new VirtualLockImpl(injector);
    this.mDualDarDoManagerImpl = new DualDarDoManagerImpl(injector);
    prepareDualDARService();
    this.mEndpointMonitorImpl = new EndpointMonitorImpl();
    prepareEndpointMonitorService();
  }

  public void systemReady() {
    Log.i("DarManagerService", "systemReady for DarManagerService");
    HandlerThread handlerThread = new HandlerThread("DarManagerService", 10);
    handlerThread.start();
    this.mDarHandler = new DarHandler(handlerThread.getLooper());
    prepareSecuredDataKey(0);
    setSystemReady();
  }

  public class Injector {
    public final Context mContext;
    public final DarDatabaseCache mDarDatabaseCache;
    public final LockPatternUtils mLockPatternUtils;
    public LockPatternUtils.EscrowTokenStateChangeCallback mEscrowTokenStateChangeCallback = null;
    public ILockSettings mLockSettingsService = null;
    public IDualDARPolicy mDualDARPolicyService = null;

    public Injector(Context context) {
      this.mContext = context;
      this.mDarDatabaseCache = new DarDatabaseCache(context);
      this.mLockPatternUtils = new LockPatternUtils(context);
    }

    public long binderClearCallingIdentity() {
      return Binder.clearCallingIdentity();
    }

    public void binderRestoreCallingIdentity(long j) {
      Binder.restoreCallingIdentity(j);
    }

    public void binderWithCleanCallingIdentity(FunctionalUtils.ThrowingRunnable throwingRunnable) {
      Binder.withCleanCallingIdentity(throwingRunnable);
    }

    public int binderGetCallingUid() {
      return Binder.getCallingUid();
    }

    public int binderGetCallingPid() {
      return Binder.getCallingPid();
    }

    public ActivityManager getActivityManager() {
      return (ActivityManager) this.mContext.getSystemService("activity");
    }

    public Context getContext() {
      return this.mContext;
    }

    public DarDatabaseCache getDarDatabaseCache() {
      return this.mDarDatabaseCache;
    }

    public DevicePolicyManager getDevicePolicyManager() {
      return (DevicePolicyManager) this.mContext.getSystemService("device_policy");
    }

    public KeyProtector getKeyProtector() {
      return KeyProtector.getInstance();
    }

    public LockPatternUtils getLockPatternUtils() {
      return this.mLockPatternUtils;
    }

    public UserManager getUserManager() {
      return (UserManager) this.mContext.getSystemService("user");
    }

    public UserManagerInternal getUserManagerInternal() {
      return (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
    }

    public void enforceCallerKnoxCoreOrSelf(String str) {
      int binderGetCallingPid = binderGetCallingPid();
      int binderGetCallingUid = binderGetCallingUid();
      if (binderGetCallingUid == 5250 || binderGetCallingPid == Process.myPid()) {
        return;
      }
      throw new SecurityException(
          "Security Exception Occurred while pid["
              + binderGetCallingPid
              + "] with uid["
              + binderGetCallingUid
              + "] trying to access methodName ["
              + str
              + "] in [DarManagerService] service");
    }

    public LockPatternUtils.EscrowTokenStateChangeCallback getEscrowTokenStateChangeCallback() {
      return this.mEscrowTokenStateChangeCallback;
    }

    public void setEscrowTokenStateChangeCallback(
        LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
      this.mEscrowTokenStateChangeCallback = escrowTokenStateChangeCallback;
    }

    public Optional getDualDARPolicyService() {
      if (this.mDualDARPolicyService == null) {
        this.mDualDARPolicyService =
            IDualDARPolicy.Stub.asInterface(ServiceManager.getService("DualDARPolicy"));
      }
      return Optional.ofNullable(this.mDualDARPolicyService);
    }

    public LockSettingsInternal getLockSettingsService() {
      return (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(long j, int i) {
    if (isDualDarDoSupported()) {
      getDualDarManager().onEscrowTokenActivated(j, i);
    }
  }

  public final void setSystemReady() {
    synchronized (DarManagerService.class) {
      mSystemReady = true;
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r6v4, types: [boolean] */
  public boolean isDeviceRootKeyInstalled() {
    if (isSakmSupported()) {
      Log.d("DarManagerService", "Check SAK instead for JDM with GRDM or KnoxVault2");
      return SecureUtil.record(isSakInstalled());
    }
    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
    boolean z = false;
    try {
      try {
        DeviceRootKeyServiceManager deviceRootKeyServiceManager =
            new DeviceRootKeyServiceManager(this.mContext);
        if (!deviceRootKeyServiceManager.isAliveDeviceRootKeyService()) {
          Log.e("DarManagerService", "DRK service is not ready...");
        } else {
          z = deviceRootKeyServiceManager.isExistDeviceRootKey(1);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
      this = SecureUtil.record(z);
      return this;
    } catch (Throwable th) {
      this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
      throw th;
    }
  }

  public final boolean isSakmSupported() {
    return SystemProperties.get("ro.security.keystore.keytype").contains("sakm");
  }

  public final boolean isSakInstalled() {
    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
    try {
      try {
        AttestationUtils attestationUtils = new AttestationUtils();
        r4 =
            attestationUtils.generateKeyPair("KnoxTestKey", SecureUtil.generateRandomBytes(8))
                != null;
        if (r4) {
          Log.d("DarManagerService", "Generated keypair is protected by SAK");
          attestationUtils.deleteKey("KnoxTestKey");
        }
      } catch (Exception e) {
        Log.e("DarManagerService", "Failed while check SAK : " + e.toString());
        e.printStackTrace();
      }
      return r4;
    } finally {
      this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:4:0x0014, code lost:

     if (r2 == 1) goto L8;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean isEmTokenAllowed() {
    boolean z;
    EngineeringModeManager engineeringModeManager = new EngineeringModeManager(this.mContext);
    if (engineeringModeManager.isConnected()) {
      int status = engineeringModeManager.getStatus(66);
      z = true;
    }
    z = false;
    Log.d("DarManagerService", "EM Token status : " + z);
    return z;
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r7v3, types: [com.android.server.knox.dar.DarManagerService$Injector] */
  public boolean isKnoxKeyInstallable() {
    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
    boolean z = false;
    try {
      try {
        AttestationUtils attestationUtils = new AttestationUtils();
        if (attestationUtils.generateKeyPair(
                    new AttestParameterSpec.Builder(
                            "KnoxTestKey", SecureUtil.generateRandomBytes(8))
                        .setVerifiableIntegrity(true)
                        .build())
                != null
            && (z = checkDeviceIntegrity(attestationUtils.getCertificateChain("KnoxTestKey")))) {
          attestationUtils.deleteKey("KnoxTestKey");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return z;
    } finally {
      this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
    }
  }

  public final boolean checkDeviceIntegrity(Certificate[] certificateArr) {
    IntegrityStatus integrityStatus;
    try {
      integrityStatus =
          new AttestedCertParser((X509Certificate) certificateArr[0]).getIntegrityStatus();
    } catch (CertificateParsingException e) {
      e.printStackTrace();
    }
    if (integrityStatus != null
        && integrityStatus.getWarranty() == 0
        && integrityStatus.getTrustBoot() == 0) {
      return true;
    }
    if (isEmTokenAllowed()) {
      Log.d(
          "DarManagerService",
          "Failed in device integrity check. But, EM Token is allowed. Continue - ");
      return true;
    }
    return false;
  }

  public final void checkSystemPermission() {
    if (this.mInjector.binderGetCallingUid() == 1000) {
      return;
    }
    Log.e("DarManagerService", "Require system permission.");
    throw new SecurityException(
        "Security Exception Occurred in pid["
            + this.mInjector.binderGetCallingPid()
            + "] with uid["
            + this.mInjector.binderGetCallingUid()
            + "]");
  }

  public final UserManager getUserManager() {
    if (this.mUserManager == null) {
      this.mUserManager = (UserManager) this.mContext.getSystemService("user");
    }
    return this.mUserManager;
  }

  public final UserInfo getUserInfo(int i) {
    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
    UserManager userManager = this.mUserManager;
    UserInfo userInfo = userManager != null ? userManager.getUserInfo(i) : null;
    this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
    return userInfo != null ? userInfo : NULL_USER;
  }

  public class DarHandler extends Handler {
    public DarHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 118) {
        Log.d(
            "DarManagerServiceHandler", " MSG_SET_RESET_TOKEN_FOR_LEGACY : user : " + message.arg1);
        DarManagerService.this.handleSetResetTokenForLegacy(message.arg1, (String) message.obj);
        return;
      }
      if (i == 120) {
        Log.d("DarManagerServiceHandler", " MSG_HANDLE_LOCKED_BOOT_COMPLETE ");
        DarManagerService.this.handleLockedBootCompleted(message.arg1);
      } else if (i == 150) {
        Log.d("DarManagerServiceHandler", "MSG_UNLOCK_SECURE_FOLDER_WITH_TOKEN ");
        DarManagerService.this.handleUnlockSecureFolderWithToken(message.arg1);
      } else {
        Log.e("DarManagerService", "msg : ignore unknown message");
      }
    }
  }

  public void dump(
      final FileDescriptor fileDescriptor, final PrintWriter printWriter, final String[] strArr) {
    if (DumpUtils.checkDumpPermission(this.mContext, "DarManagerService", printWriter)) {
      if (isSdpSupported()) {
        printWriter.println("sdp_dump");
        getSdpManager().dump(fileDescriptor, printWriter, strArr);
      }
      if (isDualDarDoSupported()) {
        printWriter.println("dualdar_dump");
        this.mInjector.binderWithCleanCallingIdentity(
            new FunctionalUtils
                .ThrowingRunnable() { // from class:
                                      // com.android.server.knox.dar.DarManagerService$$ExternalSyntheticLambda0
              public final void runOrThrow() {
                DarManagerService.this.lambda$dump$1(fileDescriptor, printWriter, strArr);
              }
            });
      }
      if (isDarSupported()) {
        if (strArr != null
            && strArr.length > 0
            && (Arrays.asList(strArr).contains("-a") || Arrays.asList(strArr).contains("sdplog"))) {
          printWriter.println("sdplog_dump");
          printWriter.println(
              "-------------------------------------------------- START DUMP"
                  + " --------------------------------------------------");
          SDPLog.dump(printWriter);
          printWriter.println(
              "-------------------------------------------------- END DUMP"
                  + " --------------------------------------------------");
        }
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$dump$1(
      FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    getDualDarManager().dump(this.mContext, fileDescriptor, printWriter, strArr);
  }

  public static byte[] fileRead(String str) {
    byte[] bArr = null;
    if (str == null || str.isEmpty()) {
      return null;
    }
    File file = new File(str);
    if (!file.exists()) {
      return null;
    }
    try {
      FileInputStream fileInputStream = new FileInputStream(file);
      try {
        byte[] bArr2 = new byte[fileInputStream.available()];
        fileInputStream.read(bArr2);
        try {
          fileInputStream.close();
          return bArr2;
        } catch (Exception e) {
          e = e;
          bArr = bArr2;
          e.printStackTrace();
          return bArr;
        }
      } catch (Throwable th) {
        try {
          fileInputStream.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
        throw th;
      }
    } catch (Exception e2) {
      e = e2;
      e.printStackTrace();
      return bArr;
    }
  }

  public static boolean fileWrite(String str, byte[] bArr) {
    boolean z = false;
    if (str != null && !str.isEmpty() && bArr != null) {
      try {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
        try {
          fileOutputStream.write(bArr);
          fileOutputStream.flush();
          z = true;
          fileOutputStream.close();
        } finally {
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return z;
  }

  public byte[] getSpecificKeyViaProtector(String str, int i) {
    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
    try {
      return this.mKeyProtector.release(str, i);
    } finally {
      this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
    }
  }

  public void prepareSecuredDataKey(int i) {
    if (doesSpecificKeyExist("SdpSecureDataKey", i)) {
      return;
    }
    Log.d(
        "DarManagerService",
        String.format(
            "Generate secure data key for user %d [ res : %b ]",
            Integer.valueOf(i),
            Boolean.valueOf(generateAndSaveSpecificKey("SdpSecureDataKey", i))));
  }

  public boolean doesSpecificKeyExist(String str, int i) {
    if (SecureUtil.isEmpty(str)) {
      return false;
    }
    return this.mKeyProtector.exists(str, i);
  }

  public boolean generateAndSaveSpecificKey(String str, int i) {
    boolean z;
    byte[] generateRandomBytes = SecureUtil.generateRandomBytes(32);
    try {
      if (!SecureUtil.isEmpty(str)) {
        if (SecureUtil.record(saveSpecificKeyViaProtector(generateRandomBytes, str, i))) {
          z = true;
          return z;
        }
      }
      z = false;
      return z;
    } finally {
      SecureUtil.clear(generateRandomBytes);
    }
  }

  public boolean saveSpecificKeyViaProtector(byte[] bArr, String str, int i) {
    boolean z;
    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
    try {
      if (!SecureUtil.isAnyoneEmptyHere(bArr, str)) {
        if (SecureUtil.record(this.mKeyProtector.protect(bArr, str, i))) {
          z = true;
          return z;
        }
      }
      z = false;
      return z;
    } finally {
      this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
    }
  }

  public void saveSecuredEscrowData(int i) {
    if (i != 0) {
      return;
    }
    Log.i("DarManagerService", "Save secured escrow data for user " + i);
    File file = new File(Environment.getDataSystemDeDirectory(i), "spblob");
    File file2 = new File(file, String.format("%016x.%s", 0L, "e0"));
    File file3 = new File(file, String.format("%016x.%s", 0L, "p1"));
    if (!file2.exists() || !file3.exists()) {
      Log.d(
          "DarManagerService",
          String.format(
              "Escrow data doesn't exist [ %b/%b ]",
              Boolean.valueOf(file2.exists()), Boolean.valueOf(file3.exists())));
      return;
    }
    byte[] fileRead = fileRead(file2.getAbsolutePath());
    byte[] fileRead2 = fileRead(file3.getAbsolutePath());
    if (SecureUtil.isAnyoneEmptyHere(fileRead, fileRead2)) {
      return;
    }
    byte[] specificKeyViaProtector = getSpecificKeyViaProtector("SdpSecureDataKey", i);
    if (SecureUtil.isEmpty(specificKeyViaProtector)) {
      Log.e(
          "DarManagerService",
          new Exception("Unexpected failure while get secure data key").getMessage());
      return;
    }
    byte[] encryptFast = this.mKeyProtector.encryptFast(specificKeyViaProtector, fileRead);
    byte[] encryptFast2 = this.mKeyProtector.encryptFast(specificKeyViaProtector, fileRead2);
    Log.i(
        "DarManagerService",
        String.format(
            "Escrow data for SYSTEM user %d got secured [ Res : %b/%b ]",
            Integer.valueOf(i),
            Boolean.valueOf(fileWrite(file2.getAbsolutePath() + ".bku", encryptFast)),
            Boolean.valueOf(fileWrite(file3.getAbsolutePath() + ".bku", encryptFast2))));
  }

  public Bundle getSecuredEscrowData(int i) {
    if (i != 0) {
      return null;
    }
    Log.i("DarManagerService", "Get secured escrow data for user " + i);
    File file = new File(Environment.getDataSystemDeDirectory(i), "spblob");
    File file2 = new File(file, String.format("%016x.%s", 0L, "e0.bku"));
    File file3 = new File(file, String.format("%016x.%s", 0L, "p1.bku"));
    byte[] fileRead = fileRead(file2.getAbsolutePath());
    byte[] fileRead2 = fileRead(file3.getAbsolutePath());
    if (SecureUtil.isAnyoneEmptyHere(fileRead, fileRead2)) {
      return null;
    }
    byte[] specificKeyViaProtector = getSpecificKeyViaProtector("SdpSecureDataKey", i);
    if (SecureUtil.isEmpty(specificKeyViaProtector)) {
      Log.e(
          "DarManagerService",
          new Exception("Unexpected failure while get secure data key").getMessage());
      return null;
    }
    byte[] decryptFast = this.mKeyProtector.decryptFast(specificKeyViaProtector, fileRead);
    byte[] decryptFast2 = this.mKeyProtector.decryptFast(specificKeyViaProtector, fileRead2);
    Bundle bundle = new Bundle();
    bundle.putByteArray("e0", decryptFast);
    bundle.putByteArray("p1", decryptFast2);
    Log.d(
        "DarManagerService",
        String.format(
            "Secured escrow data for user %d prepared [ Res : %b/%b ]",
            Integer.valueOf(i),
            Boolean.valueOf(!SecureUtil.isEmpty(decryptFast)),
            Boolean.valueOf(!SecureUtil.isEmpty(decryptFast2))));
    return bundle;
  }

  public void setResetTokenForLegacyContainer(int i, String str) {
    checkSystemPermission();
    Message obtainMessage = this.mDarHandler.obtainMessage(118, i, 0);
    obtainMessage.obj = str;
    this.mDarHandler.sendMessage(obtainMessage);
  }

  public final void handleSetResetTokenForLegacy(int i, String str) {
    UserInfo userInfo;
    boolean z;
    Log.i("DarManagerService", "Set reset token for Legacy User " + i);
    Log.d("DarManagerService", "token : " + str + ", userId : " + i);
    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
    try {
      try {
        userInfo = getUserManager().getUserInfo(i);
      } catch (Exception e) {
        Log.e("DarManagerService", "exception occurred during getUserInfo for Legacy user " + i, e);
        e.printStackTrace();
        this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        userInfo = null;
      }
      if (userInfo == null) {
        Log.d("DarManagerService", "handle reset Token getUserInfo failed. " + i);
        return;
      }
      if (!DarUtil.isLegacyContainerUser(userInfo)) {
        Log.d(
            "DarManagerService",
            String.format(
                "On created - User %d workspace identified as new-fashioned", Integer.valueOf(i)));
        return;
      }
      Log.d(
          "DarManagerService",
          String.format(
              "On created - User %d workspace identified as old-fashioned", Integer.valueOf(i)));
      boolean isEmpty = SecureUtil.isEmpty(str);
      byte[] generateRandomBytes =
          isEmpty ? SecureUtil.generateRandomBytes(32) : str.getBytes(Charset.forName("UTF-8"));
      if (isEmpty) {
        Log.d(
            "DarManagerService",
            String.format(
                "On created - Save reset token via protector for Legacy user %d has been deployed :"
                    + " %s",
                Integer.valueOf(i),
                Boolean.valueOf(saveResetTokenViaProtectorForLegacy(generateRandomBytes, i))));
      }
      boolean resetTokenForLegacy = setResetTokenForLegacy(generateRandomBytes, i);
      SecureUtil.clear(generateRandomBytes);
      Log.d(
          "DarManagerService",
          String.format(
              "On created - Set reset token for Legacy user %d has been deployed : %s",
              Integer.valueOf(i), Boolean.valueOf(resetTokenForLegacy)));
      if (isEmpty) {
        return;
      }
      try {
        z =
            EnterpriseKnoxManager.getInstance()
                .getKnoxContainerManager(this.mContext, i)
                .getPasswordPolicy()
                .enforcePwdChange();
      } catch (Exception e2) {
        Log.e(
            "DarManagerService",
            "Unexpected exception while enforce password for Legacy user " + i,
            e2);
        z = false;
      }
      Log.d(
          "DarManagerService",
          String.format(
              "On created - Password enforcement for Legacy user %d has been deployed : %s",
              Integer.valueOf(i), Boolean.valueOf(z)));
    } finally {
      this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
    }
  }

  public boolean saveResetTokenViaProtectorForLegacy(byte[] bArr, int i) {
    return SecureUtil.record(this.mKeyProtector.protect(bArr, "SdpResetToken", i));
  }

  public boolean setResetTokenForLegacy(byte[] bArr, int i) {
    SDPLog.m43i("Set reset token for Legacy user " + i);
    SDPLog.m46p(KnoxCustomManagerService.SPCM_KEY_TOKEN, bArr, "userId", Integer.valueOf(i));
    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
    boolean z = false;
    try {
      try {
        long addEscrowToken =
            this.mLockPatternUtils.addEscrowToken(
                bArr, i, (LockPatternUtils.EscrowTokenStateChangeCallback) null);
        if (addEscrowToken != 0 && saveTokenHandleViaProtectorForLegacy(addEscrowToken, i)) {
          z = true;
        }
        if (SecureUtil.isFailed(Boolean.valueOf(z))) {
          this.mLockPatternUtils.removeEscrowToken(addEscrowToken, i);
        }
      } catch (Exception e) {
        e.printStackTrace();
        SDPLog.m41e("Unexpected exception while set reset token for Legacy", e);
      }
      this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
      SDPLog.m38d("Result of set reset token for Legacy : " + z);
      return z;
    } catch (Throwable th) {
      this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
      throw th;
    }
  }

  public boolean saveTokenHandleViaProtectorForLegacy(long j, int i) {
    return SecureUtil.record(
        this.mKeyProtector.protect(BytesUtil.longToBytes(j), "SdpTokenHandle", i));
  }

  public long getSecureFolderTokenHandleViaProtector(int i) {
    checkSystemPermission();
    byte[] specificKeyViaProtector = getSpecificKeyViaProtector("SdpTokenHandle", i);
    if (specificKeyViaProtector != null) {
      return BytesUtil.bytesToLong(specificKeyViaProtector);
    }
    Log.d("DarManagerService", "get SecureFolder Token Handle Failed");
    return 0L;
  }

  public byte[] getSecureFolderResetTokenViaProtector(int i) {
    checkSystemPermission();
    return getSpecificKeyViaProtector("SdpResetToken", i);
  }

  public void registerUserSwitchObserver() {
    try {
      ActivityManagerNative.getDefault()
          .registerUserSwitchObserver(this.mUserSwitchObserver, "DarManagerService");
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  public final void handleLockedBootCompleted(int i) {
    if (StorageManager.isFileEncryptedNativeOrEmulated()) {
      if (SemPersonaManager.isKnoxId(i)) {
        Log.d("DarManagerService", "Locked boot completed for user " + i);
      }
      if (SemPersonaManager.isSecureFolderId(i)) {
        Log.d("DarManagerService", "Locked boot completed for SecureFolder user " + i);
        int intForUser =
            Settings.Secure.getIntForUser(
                this.mContext.getContentResolver(), "automatic_data_decryption", 0, i);
        UserInfo profileParent = getUserManager().getProfileParent(i);
        if (profileParent == null
            || !getUserManager().isUserUnlockingOrUnlocked(profileParent.id)) {
          StringBuilder sb = new StringBuilder();
          sb.append("Parent ");
          sb.append(profileParent != null ? profileParent.id : 0);
          sb.append(" is not ready to unlock secure folder user ");
          sb.append(i);
          Log.d("DarManagerService", sb.toString());
          return;
        }
        if (getUserManager().isUserUnlockingOrUnlocked(i) || intForUser != 1) {
          return;
        }
        Log.d("DarManagerService", "Unlock secure folder user " + i);
        unlockSecureFolderWithToken(i);
      }
    }
  }

  public void unlockSecureFolderWithToken(int i) {
    checkSystemPermission();
    this.mDarHandler.sendMessage(this.mDarHandler.obtainMessage(150, i, 0));
  }

  public void handleUnlockSecureFolderWithToken(int i) {
    if (SemPersonaManager.isSecureFolderId(i)) {
      long secureFolderTokenHandleViaProtector = getSecureFolderTokenHandleViaProtector(i);
      byte[] secureFolderResetTokenViaProtector = getSecureFolderResetTokenViaProtector(i);
      LockSettingsInternal lockSettingsService = this.mInjector.getLockSettingsService();
      if (lockSettingsService != null) {
        try {
          lockSettingsService.unlockUserWithToken(
              secureFolderTokenHandleViaProtector, secureFolderResetTokenViaProtector, i);
        } catch (Exception e) {
          Log.e(
              "DarManagerService", "Unexpected failure while unlock secure folder with token" + e);
        }
      }
      SecureUtil.clear(secureFolderResetTokenViaProtector);
      Log.d(
          "DarManagerService",
          String.format(
              Locale.US,
              "SecureFolder user %d has been unlocked [ res : %b ]",
              Integer.valueOf(i),
              Boolean.valueOf(getUserManager().isUserUnlocked(i))));
    }
  }

  public final VirtualLockImpl getVirtualLock() {
    return this.mVirtualLockImpl;
  }

  public final boolean isVirtualLockSupported() {
    return getVirtualLock() != null;
  }

  public int reserveUserIdForSystem() {
    if (isVirtualLockSupported()) {
      return getVirtualLock().reserveUserIdForSystem();
    }
    return -10000;
  }

  public int getReservedUserIdForSystem() {
    if (isVirtualLockSupported()) {
      return getVirtualLock().getReservedUserIdForSystem();
    }
    return -10000;
  }

  public int getAvailableUserId() {
    if (isVirtualLockSupported()) {
      return getVirtualLock().getAvailableUserId();
    }
    return -10000;
  }

  public boolean setResetPasswordToken(byte[] bArr, int i) {
    if (!isVirtualLockSupported()) {
      return false;
    }
    boolean resetPasswordToken = getVirtualLock().setResetPasswordToken(bArr, i);
    if (!resetPasswordToken || !isDualDarDoSupported()) {
      return resetPasswordToken;
    }
    getDualDarManager().onSetResetPasswordToken(i);
    return resetPasswordToken;
  }

  public boolean clearResetPasswordToken(int i) {
    if (!isVirtualLockSupported()) {
      return false;
    }
    boolean clearResetPasswordToken = getVirtualLock().clearResetPasswordToken(i);
    if (!clearResetPasswordToken || !isDualDarDoSupported()) {
      return clearResetPasswordToken;
    }
    getDualDarManager().onClearResetPasswordToken(i);
    return clearResetPasswordToken;
  }

  public boolean isResetPasswordTokenActive(int i) {
    if (isVirtualLockSupported()) {
      return getVirtualLock().isResetPasswordTokenActive(i);
    }
    return false;
  }

  public boolean resetPasswordWithToken(String str, byte[] bArr, int i) {
    if (isVirtualLockSupported()) {
      return getVirtualLock().resetPasswordWithToken(str, bArr, i);
    }
    return false;
  }

  public SdpManagerImpl getSdpManager() {
    return this.mSdpManagerImpl;
  }

  public boolean isSDPEnabled(int i) {
    if (isSdpSupported()) {
      return getSdpManager().isSDPEnabled(i);
    }
    return false;
  }

  public boolean isSdpSupported() {
    Log.e("DarManagerService_SDP", "SDP not supported");
    return false;
  }

  public boolean isSdpSupportedSecureFolder(int i) {
    UserInfo userInfo = getUserInfo(i);
    return (userInfo == null
            || !userInfo.isSecureFolder()
            || userInfo.isSdpNotSupportedSecureFolder())
        ? false
        : true;
  }

  public int unlock(String str, String str2) {
    if (isSdpSupported()) {
      return getSdpManager().unlock(str, str2);
    }
    return -10;
  }

  public int lock(String str) {
    if (isSdpSupported()) {
      return getSdpManager().lock(str);
    }
    return -10;
  }

  public int setPassword(String str, String str2) {
    if (isSdpSupported()) {
      return getSdpManager().setPassword(str, str2);
    }
    return -10;
  }

  public int resetPassword(String str, String str2, String str3) {
    if (isSdpSupported()) {
      return getSdpManager().resetPassword(str, str2, str3);
    }
    return -10;
  }

  public int migrate(String str) {
    if (isSdpSupported()) {
      return getSdpManager().migrate(str);
    }
    return -10;
  }

  public int registerListener(String str, ISdpListener iSdpListener) {
    if (isSdpSupported()) {
      return getSdpManager().registerListener(str, iSdpListener);
    }
    return -10;
  }

  public int unregisterListener(String str, ISdpListener iSdpListener) {
    if (isSdpSupported()) {
      return getSdpManager().unregisterListener(str, iSdpListener);
    }
    return -10;
  }

  public int isLicensed() {
    if (isSdpSupported()) {
      return getSdpManager().isLicensed();
    }
    return -10;
  }

  public int exists(String str) {
    if (isSdpSupported()) {
      return getSdpManager().exists(str);
    }
    return -10;
  }

  public int allow(String str, String str2) {
    if (isSdpSupported()) {
      return getSdpManager().allow(str, str2);
    }
    return -10;
  }

  public int disallow(String str, String str2) {
    if (isSdpSupported()) {
      return getSdpManager().disallow(str, str2);
    }
    return -10;
  }

  public double getSupportedSDKVersion() {
    if (isSdpSupported()) {
      return getSdpManager().getSupportedSDKVersion();
    }
    return 0.0d;
  }

  public int addEngine(SdpCreationParam sdpCreationParam, String str, String str2) {
    if (isSdpSupported()) {
      return getSdpManager().addEngine(sdpCreationParam, str, str2);
    }
    return -10;
  }

  public int removeEngine(String str) {
    if (isSdpSupported()) {
      return getSdpManager().removeEngine(str);
    }
    return -10;
  }

  public SdpEngineInfo getEngineInfo(String str) {
    if (isSdpSupported()) {
      return getSdpManager().getEngineInfo(str);
    }
    return null;
  }

  public boolean setSensitive(int i, String str) {
    if (isSdpSupported()) {
      return getSdpManager().setSensitive(i, str);
    }
    return false;
  }

  public boolean isSensitive(String str) {
    if (isSdpSupported()) {
      return getSdpManager().isSensitive(str);
    }
    return false;
  }

  public int createEncPkgDir(int i, String str) {
    if (isSdpSupported()) {
      return getSdpManager().createEncPkgDir(i, str);
    }
    return 0;
  }

  public int saveTokenIntoTrusted(String str, String str2) {
    if (isSdpSupported()) {
      return getSdpManager().saveTokenIntoTrusted(str, str2);
    }
    return -10;
  }

  public int deleteToeknFromTrusted(String str) {
    if (isSdpSupported()) {
      return getSdpManager().deleteToeknFromTrusted(str);
    }
    return -10;
  }

  public int unlockViaTrusted(String str, String str2) {
    if (isSdpSupported()) {
      return getSdpManager().unlockViaTrusted(str, str2);
    }
    return -10;
  }

  public void onBiometricsAuthenticated(int i) {
    if (isSdpSupported()) {
      getSdpManager().onBiometricsAuthenticated(i);
    }
  }

  public void onDeviceOwnerLocked(int i) {
    if (isSdpSupported()) {
      getSdpManager().onDeviceOwnerLocked(i);
    }
  }

  public void registerClient(int i, ISdpListener iSdpListener) {
    if (isSdpSupported()) {
      getSdpManager().registerClient(i, iSdpListener);
    }
  }

  public void unregisterClient(int i, ISdpListener iSdpListener) {
    if (isSdpSupported()) {
      getSdpManager().unregisterClient(i, iSdpListener);
    }
  }

  public boolean isDefaultPathUser(int i) {
    if (isSdpSupported()) {
      return getSdpManager().isDefaultPathUser(i);
    }
    return false;
  }

  public void handleDeviceOwnerChanged() {
    checkSystemPermission();
  }

  public final boolean isDualDarDoSupported() {
    return getDualDarManager() != null;
  }

  public final DualDarDoManagerImpl getDualDarManager() {
    return this.mDualDarDoManagerImpl;
  }

  public final void prepareDualDARService() {
    Log.i("DarManagerService_DUAL_DAR", "prepare DualDAR DO Service");
  }

  public boolean setDualDarInfo(int i, int i2) {
    if (isDualDarDoSupported()) {
      return getDualDarManager().setDualDarInfo(i, i2);
    }
    return false;
  }

  public boolean isInnerAuthRequired(int i) {
    if (isDualDarDoSupported()) {
      return getDualDarManager().isInnerAuthRequired(i);
    }
    return false;
  }

  public void setInnerAuthUserId(int i, int i2) {
    if (isDualDarDoSupported()) {
      getDualDarManager().setInnerAuthUserId(i, i2);
    }
  }

  public int getInnerAuthUserId(int i) {
    if (isDualDarDoSupported()) {
      return getDualDarManager().getInnerAuthUserId(i);
    }
    return -10000;
  }

  public void setMainUserId(int i, int i2) {
    if (isDualDarDoSupported()) {
      getDualDarManager().setMainUserId(i, i2);
    }
  }

  public int getMainUserId(int i) {
    if (isDualDarDoSupported()) {
      return getDualDarManager().getMainUserId(i);
    }
    return -10000;
  }

  public void addBlockedClearablePackages(int i, String str) {
    if (isDualDarDoSupported()) {
      getDualDarManager().addBlockedClearablePackages(i, str);
    }
  }

  public List getBlockedClearablePackages(int i) {
    if (isDualDarDoSupported()) {
      return getDualDarManager().getBlockedClearablePackages(i);
    }
    return null;
  }

  public List getPackageListForDualDarPolicy(String str) {
    if (isDualDarDoSupported()) {
      return getDualDarManager().getPackageListForDualDarPolicy(str);
    }
    return null;
  }

  public int getPasswordMinimumLengthForInner() {
    if (isDualDarDoSupported()) {
      return getDualDarManager().getPasswordMinimumLengthForInner();
    }
    return 0;
  }

  public final boolean isEndpointMonitoringSupported() {
    return getEndpointMonitor() != null;
  }

  public final EndpointMonitorImpl getEndpointMonitor() {
    return this.mEndpointMonitorImpl;
  }

  public final void prepareEndpointMonitorService() {
    Log.i("DarManagerService_SDP", "prepare EndpointMonitor Service");
  }

  public int startTracing(
      int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener) {
    checkSystemPermission();
    if (isEndpointMonitoringSupported()) {
      return getEndpointMonitor().startTracing(i, i2, bundle, iEndpointMonitorListener);
    }
    return -6;
  }

  public int stopTracing(int i, int i2) {
    checkSystemPermission();
    if (isEndpointMonitoringSupported()) {
      return getEndpointMonitor().stopTracing(i, i2);
    }
    return -6;
  }

  public void startMonitoringFiles(
      int i, int[] iArr, List list, List list2, IZtdListener iZtdListener) {
    checkSystemPermission();
    if (isEndpointMonitoringSupported()) {
      getEndpointMonitor().startMonitoringFiles(i, iArr, list, list2, iZtdListener);
    }
  }

  public void stopMonitoringFiles(int i) {
    checkSystemPermission();
    if (isEndpointMonitoringSupported()) {
      getEndpointMonitor().stopMonitoringFiles(i);
    }
  }

  public void startMonitoringDomains(int i, int[] iArr, List list, IZtdListener iZtdListener) {
    checkSystemPermission();
    if (isEndpointMonitoringSupported()) {
      getEndpointMonitor().startMonitoringDomains(i, iArr, list, iZtdListener);
    }
  }

  public void stopMonitoringDomains(int i) {
    checkSystemPermission();
    if (isEndpointMonitoringSupported()) {
      getEndpointMonitor().stopMonitoringDomains(i);
    }
  }
}
