package com.android.server.backup.restore;

import android.app.IBackupAgent;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IFullBackupRestoreObserver;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.provider.Settings;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.BackupManagerYuva;
import com.android.server.backup.BackupRestoreTask;
import com.android.server.backup.FileMetadata;
import com.android.server.backup.KeyValueAdbRestoreEngine;
import com.android.server.backup.OperationStorage;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.fullbackup.FullBackupObbConnection;
import com.android.server.backup.utils.BackupEligibilityRules;
import com.android.server.backup.utils.BytesReadListener;
import com.android.server.backup.utils.FullBackupRestoreObserverUtils;
import com.android.server.backup.utils.RestoreUtils;
import com.android.server.backup.utils.TarBackupReader;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/* loaded from: classes.dex */
public class FullRestoreEngine extends RestoreEngine {
  public static BackupManagerYuva mBackupManagerYuva;
  public IBackupAgent mAgent;
  public String mAgentPackage;
  public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
  public final boolean mAllowApks;
  public final HashMap mApkCount;
  public long mAppVersion;
  public final BackupEligibilityRules mBackupEligibilityRules;
  public final UserBackupManagerService mBackupManagerService;
  public final byte[] mBuffer;
  public final HashSet mClearedPackages;
  public final RestoreDeleteObserver mDeleteObserver;
  public final int mEphemeralOpToken;
  public final boolean mIsAdbRestore;
  public final HashMap mManifestSignatures;
  public final IBackupManagerMonitor mMonitor;
  public final BackupRestoreTask mMonitorTask;
  public FullBackupObbConnection mObbConnection;
  public IFullBackupRestoreObserver mObserver;
  public final PackageInfo mOnlyPackage;
  public final OperationStorage mOperationStorage;
  public final HashMap mPackageInstallers;
  public final HashMap mPackagePolicies;
  public ParcelFileDescriptor[] mPipes;
  public boolean mPipesClosed;
  public final Object mPipesLock;
  public boolean mPrivilegeApp;
  public FileMetadata mReadOnlyParent;
  public final HashMap mSessionFlag;
  public final HashMap mSessionId;
  public ApplicationInfo mTargetApp;
  public final int mUserId;
  public byte[] mWidgetData;
  public boolean restorePass;

  public static /* synthetic */ void lambda$restoreOneFile$0(long j) {}

  public FullRestoreEngine(
      UserBackupManagerService userBackupManagerService,
      OperationStorage operationStorage,
      BackupRestoreTask backupRestoreTask,
      IFullBackupRestoreObserver iFullBackupRestoreObserver,
      IBackupManagerMonitor iBackupManagerMonitor,
      PackageInfo packageInfo,
      boolean z,
      int i,
      boolean z2,
      BackupEligibilityRules backupEligibilityRules) {
    this.mDeleteObserver = new RestoreDeleteObserver();
    this.mObbConnection = null;
    this.mPackagePolicies = new HashMap();
    this.mPackageInstallers = new HashMap();
    this.mApkCount = new HashMap();
    this.mSessionFlag = new HashMap();
    this.mSessionId = new HashMap();
    this.mManifestSignatures = new HashMap();
    this.mClearedPackages = new HashSet();
    this.mPipes = null;
    this.mPipesLock = new Object();
    this.mWidgetData = null;
    this.restorePass = false;
    this.mPrivilegeApp = false;
    this.mReadOnlyParent = null;
    this.mBackupManagerService = userBackupManagerService;
    this.mOperationStorage = operationStorage;
    this.mEphemeralOpToken = i;
    this.mMonitorTask = backupRestoreTask;
    this.mObserver = iFullBackupRestoreObserver;
    this.mMonitor = iBackupManagerMonitor;
    this.mOnlyPackage = packageInfo;
    this.mAllowApks = z;
    this.mBuffer = new byte[32768];
    BackupAgentTimeoutParameters agentTimeoutParameters =
        userBackupManagerService.getAgentTimeoutParameters();
    Objects.requireNonNull(agentTimeoutParameters, "Timeout parameters cannot be null");
    this.mAgentTimeoutParameters = agentTimeoutParameters;
    this.mIsAdbRestore = z2;
    this.mUserId = userBackupManagerService.getUserId();
    this.mBackupEligibilityRules = backupEligibilityRules;
    if (UserBackupManagerService.isYuvaSupported()) {
      Slog.d("BackupManagerService", "Backup Manager Yuva is Supported");
      mBackupManagerYuva = BackupManagerYuva.getInstanceBackupYuva();
    }
  }

  public FullRestoreEngine() {
    this.mDeleteObserver = new RestoreDeleteObserver();
    this.mObbConnection = null;
    this.mPackagePolicies = new HashMap();
    this.mPackageInstallers = new HashMap();
    this.mApkCount = new HashMap();
    this.mSessionFlag = new HashMap();
    this.mSessionId = new HashMap();
    this.mManifestSignatures = new HashMap();
    this.mClearedPackages = new HashSet();
    this.mPipes = null;
    this.mPipesLock = new Object();
    this.mWidgetData = null;
    this.restorePass = false;
    this.mPrivilegeApp = false;
    this.mReadOnlyParent = null;
    this.mIsAdbRestore = false;
    this.mAllowApks = false;
    this.mEphemeralOpToken = 0;
    this.mUserId = 0;
    this.mBackupEligibilityRules = null;
    this.mAgentTimeoutParameters = null;
    this.mBuffer = null;
    this.mBackupManagerService = null;
    this.mOperationStorage = null;
    this.mMonitor = null;
    this.mMonitorTask = null;
    this.mOnlyPackage = null;
  }

  public void setPrivilegeApp(boolean z) {
    this.mPrivilegeApp = z;
  }

  public boolean getRestorePass() {
    return this.restorePass;
  }

  public IBackupAgent getAgent() {
    return this.mAgent;
  }

  public byte[] getWidgetData() {
    return this.mWidgetData;
  }

  /* JADX WARN: Can't wrap try/catch for region: R(19:54|55|56|(3:58|59|(17:(1:62)(2:298|(1:300)(15:301|64|65|66|(1:293)|70|(8:74|75|(3:77|(1:81)|80)|82|(1:84)|85|86|(1:88))|(2:96|97)|98|(1:100)|(14:102|(1:104)(1:290)|105|106|107|108|109|110|111|(10:113|114|115|116|117|118|119|120|121|122)(6:237|238|239|240|(3:242|243|244)(4:246|247|248|(1:250)(9:251|(4:253|254|255|256)|261|262|263|264|265|266|267))|245)|123|(7:166|167|168|169|170|(2:171|(4:173|(1:175)(1:193)|176|(1:190)(3:178|(3:180|181|183)(1:189)|184))(2:194|195))|191)(1:125)|(7:147|148|149|150|151|(1:153)|(2:155|156))(1:127)|128)(1:292)|(5:130|(5:133|(1:135)(1:144)|136|(2:139|140)(1:138)|131)|145|141|(1:143))|146|(2:31|(1:33))(1:39)|(2:35|36)(2:37|38)))|63|64|65|66|(1:68)|293|70|(9:72|74|75|(0)|82|(0)|85|86|(0))|(3:94|96|97)|98|(0)|(0)(0)|(0)|146|(0)(0)|(0)(0))(2:302|(4:304|(8:306|(1:308)|309|310|311|312|(1:314)(2:318|(5:320|321|322|(1:324)(1:326)|325))|315)(3:331|(1:333)(1:335)|334)|316|317)(1:336)))(1:342)|337|65|66|(0)|293|70|(0)|(0)|98|(0)|(0)(0)|(0)|146|(0)(0)|(0)(0)) */
  /* JADX WARN: Code restructure failed: missing block: B:294:0x066d, code lost:

     r0 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:295:0x066e, code lost:

     r3 = "BackupManagerService";
  */
  /* JADX WARN: Code restructure failed: missing block: B:296:0x0668, code lost:

     r0 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:297:0x0669, code lost:

     r3 = "BackupManagerService";
  */
  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Not initialized variable reg: 29, insn: 0x0477: MOVE (r31 I:??[OBJECT, ARRAY]) = (r29 I:??[OBJECT, ARRAY]), block:B:285:0x0474 */
  /* JADX WARN: Not initialized variable reg: 29, insn: 0x051e: MOVE (r31 I:??[OBJECT, ARRAY]) = (r29 I:??[OBJECT, ARRAY]), block:B:284:0x051c */
  /* JADX WARN: Removed duplicated region for block: B:100:0x0376  */
  /* JADX WARN: Removed duplicated region for block: B:102:0x0379 A[Catch: NullPointerException -> 0x0668, IOException -> 0x066d, TRY_LEAVE, TryCatch #43 {IOException -> 0x066d, NullPointerException -> 0x0668, blocks: (B:66:0x02a1, B:98:0x0370, B:102:0x0379, B:290:0x038c), top: B:65:0x02a1 }] */
  /* JADX WARN: Removed duplicated region for block: B:125:0x05ce  */
  /* JADX WARN: Removed duplicated region for block: B:127:0x0626  */
  /* JADX WARN: Removed duplicated region for block: B:130:0x0635 A[Catch: NullPointerException -> 0x0664, IOException -> 0x0666, TryCatch #44 {IOException -> 0x0666, NullPointerException -> 0x0664, blocks: (B:151:0x0603, B:153:0x0610, B:155:0x0615, B:130:0x0635, B:133:0x0643, B:135:0x0649, B:136:0x064c, B:138:0x0659, B:144:0x064b, B:141:0x065c, B:143:0x0660), top: B:150:0x0603 }] */
  /* JADX WARN: Removed duplicated region for block: B:147:0x05d3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:166:0x0560 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:28:0x06a9  */
  /* JADX WARN: Removed duplicated region for block: B:292:0x062d  */
  /* JADX WARN: Removed duplicated region for block: B:31:0x06b2  */
  /* JADX WARN: Removed duplicated region for block: B:35:0x06c6  */
  /* JADX WARN: Removed duplicated region for block: B:37:0x06c8  */
  /* JADX WARN: Removed duplicated region for block: B:39:0x06c3  */
  /* JADX WARN: Removed duplicated region for block: B:44:0x0684  */
  /* JADX WARN: Removed duplicated region for block: B:68:0x02a7 A[Catch: NullPointerException -> 0x02b0, IOException -> 0x02b7, TRY_ENTER, TRY_LEAVE, TryCatch #1 {IOException -> 0x02b7, blocks: (B:68:0x02a7, B:72:0x02c4, B:86:0x0324, B:88:0x0328, B:90:0x031d, B:92:0x0321, B:94:0x0349, B:96:0x0351, B:104:0x0383, B:316:0x027d, B:322:0x023a, B:324:0x0242, B:325:0x0247, B:326:0x0245, B:331:0x0257, B:333:0x0275, B:334:0x027a, B:335:0x0278, B:336:0x0284), top: B:56:0x013f }] */
  /* JADX WARN: Removed duplicated region for block: B:72:0x02c4 A[Catch: NullPointerException -> 0x02b0, IOException -> 0x02b7, TRY_ENTER, TRY_LEAVE, TryCatch #1 {IOException -> 0x02b7, blocks: (B:68:0x02a7, B:72:0x02c4, B:86:0x0324, B:88:0x0328, B:90:0x031d, B:92:0x0321, B:94:0x0349, B:96:0x0351, B:104:0x0383, B:316:0x027d, B:322:0x023a, B:324:0x0242, B:325:0x0247, B:326:0x0245, B:331:0x0257, B:333:0x0275, B:334:0x027a, B:335:0x0278, B:336:0x0284), top: B:56:0x013f }] */
  /* JADX WARN: Removed duplicated region for block: B:77:0x02df A[Catch: NullPointerException -> 0x02b0, NameNotFoundException | IOException -> 0x031d, TryCatch #33 {NameNotFoundException | IOException -> 0x031d, blocks: (B:75:0x02c8, B:77:0x02df, B:80:0x02f9, B:81:0x02ef, B:82:0x02fe, B:85:0x030e), top: B:74:0x02c8 }] */
  /* JADX WARN: Removed duplicated region for block: B:84:0x030d  */
  /* JADX WARN: Removed duplicated region for block: B:88:0x0328 A[Catch: NullPointerException -> 0x02b0, IOException -> 0x02b7, TryCatch #1 {IOException -> 0x02b7, blocks: (B:68:0x02a7, B:72:0x02c4, B:86:0x0324, B:88:0x0328, B:90:0x031d, B:92:0x0321, B:94:0x0349, B:96:0x0351, B:104:0x0383, B:316:0x027d, B:322:0x023a, B:324:0x0242, B:325:0x0247, B:326:0x0245, B:331:0x0257, B:333:0x0275, B:334:0x027a, B:335:0x0278, B:336:0x0284), top: B:56:0x013f }] */
  /* JADX WARN: Removed duplicated region for block: B:94:0x0349 A[Catch: NullPointerException -> 0x02b0, IOException -> 0x02b7, TryCatch #1 {IOException -> 0x02b7, blocks: (B:68:0x02a7, B:72:0x02c4, B:86:0x0324, B:88:0x0328, B:90:0x031d, B:92:0x0321, B:94:0x0349, B:96:0x0351, B:104:0x0383, B:316:0x027d, B:322:0x023a, B:324:0x0242, B:325:0x0247, B:326:0x0245, B:331:0x0257, B:333:0x0275, B:334:0x027a, B:335:0x0278, B:336:0x0284), top: B:56:0x013f }] */
  /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean restoreOneFile(
      InputStream inputStream,
      boolean z,
      byte[] bArr,
      PackageInfo packageInfo,
      boolean z2,
      int i,
      IBackupManagerMonitor iBackupManagerMonitor) {
    FileMetadata fileMetadata;
    String str;
    boolean z3;
    NullPointerException nullPointerException;
    IOException iOException;
    BackupManagerYuva backupManagerYuva;
    boolean z4;
    BackupManagerYuva backupManagerYuva2;
    FileMetadata readTarHeaders;
    FileMetadata fileMetadata2;
    String str2;
    TarBackupReader tarBackupReader;
    String str3;
    byte[] bArr2;
    long restoreAgentTimeoutMillis;
    long j;
    TarBackupReader tarBackupReader2;
    PackageInfo packageInfo2;
    String str4;
    boolean z5;
    boolean z6;
    String str5;
    PackageInfo packageInfo3;
    PackageInfo packageInfo4;
    boolean z7;
    PackageInfo packageInfo5;
    String str6;
    FileMetadata fileMetadata3;
    TarBackupReader tarBackupReader3;
    RestorePolicy restorePolicy;
    RestorePolicy restorePolicy2;
    String str7;
    if (!isRunning()) {
      Slog.w("BackupManagerService", "Restore engine used after halting");
      return false;
    }
    BytesReadListener bytesReadListener =
        new BytesReadListener() { // from class:
                                  // com.android.server.backup.restore.FullRestoreEngine$$ExternalSyntheticLambda0
          @Override // com.android.server.backup.utils.BytesReadListener
          public final void onBytesRead(long j2) {
            FullRestoreEngine.lambda$restoreOneFile$0(j2);
          }
        };
    TarBackupReader tarBackupReader4 =
        new TarBackupReader(inputStream, bytesReadListener, iBackupManagerMonitor);
    boolean z8 = true;
    try {
      readTarHeaders = tarBackupReader4.readTarHeaders();
    } catch (IOException e) {
      e = e;
      fileMetadata = null;
      str = "BackupManagerService";
      z3 = true;
    } catch (NullPointerException e2) {
      e = e2;
      fileMetadata = null;
      str = "BackupManagerService";
      z3 = true;
    }
    if (readTarHeaders != null) {
      this.restorePass = true;
      String str8 = readTarHeaders.packageName;
      if (!str8.equals(this.mAgentPackage)) {
        if (packageInfo != null) {
          try {
            if (!str8.equals(packageInfo.packageName)) {
              Slog.w(
                  "BackupManagerService", "Expected data for " + packageInfo + " but saw " + str8);
              setResult(-3);
              setRunning(false);
              return false;
            }
          } catch (IOException e3) {
            iOException = e3;
            fileMetadata = null;
            str = "BackupManagerService";
            z3 = true;
            Slog.w(str, "io exception on restore socket read: " + iOException.getMessage());
            backupManagerYuva2 = mBackupManagerYuva;
            if (backupManagerYuva2 != null) {}
            setResult(-3);
            if (fileMetadata != null) {}
            if (fileMetadata == null) {}
          } catch (NullPointerException e4) {
            nullPointerException = e4;
            fileMetadata = null;
            str = "BackupManagerService";
            z3 = true;
            Slog.w(str, "NullPointerException  exception on restore ", nullPointerException);
            backupManagerYuva = mBackupManagerYuva;
            if (backupManagerYuva != null) {}
            if (fileMetadata != null) {}
            if (fileMetadata == null) {}
          }
        }
        if (!this.mPackagePolicies.containsKey(str8)) {
          this.mPackagePolicies.put(str8, RestorePolicy.IGNORE);
        }
        if (this.mAgent != null) {
          Slog.d("BackupManagerService", "Saw new package; finalizing old one");
          tearDownPipes();
          tearDownAgent(this.mTargetApp, this.mIsAdbRestore);
          this.mTargetApp = null;
          this.mAgentPackage = null;
        }
      }
      if (readTarHeaders.path.equals("_manifest")) {
        Signature[] readAppManifestAndReturnSignatures =
            tarBackupReader4.readAppManifestAndReturnSignatures(readTarHeaders);
        this.mAppVersion = readTarHeaders.version;
        RestorePolicy chooseRestorePolicy =
            tarBackupReader4.chooseRestorePolicy(
                this.mBackupManagerService.getPackageManager(),
                z2,
                readTarHeaders,
                readAppManifestAndReturnSignatures,
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class),
                this.mUserId,
                this.mBackupEligibilityRules,
                this.mPrivilegeApp);
        this.mManifestSignatures.put(
            readTarHeaders.packageName, readAppManifestAndReturnSignatures);
        this.mPackagePolicies.put(str8, chooseRestorePolicy);
        this.mPackageInstallers.put(str8, readTarHeaders.installerPackageName);
        if (UserBackupManagerService.mSplitRestoreFlag.booleanValue()) {
          this.mApkCount.put(str8, Integer.valueOf(readTarHeaders.splitCount + 1));
          this.mSessionFlag.put(str8, Boolean.FALSE);
        }
        tarBackupReader4.skipTarPadding(readTarHeaders.size);
        this.mObserver = FullBackupRestoreObserverUtils.sendOnRestorePackage(this.mObserver, str8);
      } else if (readTarHeaders.path.equals("_meta")) {
        tarBackupReader4.readMetadata(readTarHeaders);
        this.mWidgetData = tarBackupReader4.getWidgetData();
        tarBackupReader4.getMonitor();
        tarBackupReader4.skipTarPadding(readTarHeaders.size);
      } else {
        int i2 =
            AbstractC08011.$SwitchMap$com$android$server$backup$restore$RestorePolicy[
                ((RestorePolicy) this.mPackagePolicies.get(str8)).ordinal()];
        try {
          try {
          } catch (IOException e5) {
            iOException = e5;
            str = "BackupManagerService";
          }
        } catch (NullPointerException e6) {
          nullPointerException = e6;
          str = "BackupManagerService";
        }
        if (i2 != 1) {
          try {
          } catch (IOException e7) {
            iOException = e7;
            z3 = true;
            str = "BackupManagerService";
          } catch (NullPointerException e8) {
            nullPointerException = e8;
            z3 = true;
            str = "BackupManagerService";
            fileMetadata = null;
            Slog.w(str, "NullPointerException  exception on restore ", nullPointerException);
            backupManagerYuva = mBackupManagerYuva;
            if (backupManagerYuva != null) {}
            if (fileMetadata != null) {}
            if (fileMetadata == null) {}
          }
          if (i2 == 2) {
            if (readTarHeaders.domain.equals("a")) {
              Slog.d(
                  "BackupManagerService", "APK file; installing; copying " + readTarHeaders.path);
              String str9 = (String) this.mPackageInstallers.get(str8);
              RestoreUtils.setPrivilegeApp(this.mPrivilegeApp);
              if (UserBackupManagerService.mSplitRestoreFlag.booleanValue()) {
                if (!((Boolean) this.mSessionFlag.get(str8)).booleanValue()) {
                  int createSession =
                      RestoreUtils.createSession(this.mBackupManagerService.getContext(), str9);
                  this.mSessionFlag.put(str8, Boolean.TRUE);
                  this.mSessionId.put(str8, Integer.valueOf(createSession));
                }
                fileMetadata3 = readTarHeaders;
                try {
                  boolean writeSession =
                      RestoreUtils.writeSession(
                          this.mBackupManagerService.getContext(),
                          inputStream,
                          readTarHeaders,
                          str9,
                          bytesReadListener,
                          ((Integer) this.mSessionId.get(str8)).intValue());
                  HashMap hashMap = this.mApkCount;
                  hashMap.put(str8, Integer.valueOf(((Integer) hashMap.get(str8)).intValue() - 1));
                  if (!writeSession) {
                    Slog.e("BackupManagerService", "APK file; copy error");
                    this.mPackagePolicies.put(str8, RestorePolicy.IGNORE);
                  } else if (((Integer) this.mApkCount.get(str8)).intValue() == 0) {
                    tarBackupReader3 = tarBackupReader4;
                    boolean installApkSplitSupport =
                        RestoreUtils.installApkSplitSupport(
                            inputStream,
                            this.mBackupManagerService.getContext(),
                            this.mDeleteObserver,
                            this.mManifestSignatures,
                            this.mPackagePolicies,
                            fileMetadata3,
                            str9,
                            bytesReadListener,
                            this.mUserId,
                            ((Integer) this.mSessionId.get(str8)).intValue());
                    HashMap hashMap2 = this.mPackagePolicies;
                    if (installApkSplitSupport) {
                      restorePolicy2 = RestorePolicy.ACCEPT;
                    } else {
                      restorePolicy2 = RestorePolicy.IGNORE;
                    }
                    hashMap2.put(str8, restorePolicy2);
                  }
                  tarBackupReader3 = tarBackupReader4;
                } catch (IOException e9) {
                  iOException = e9;
                  str = "BackupManagerService";
                  z3 = true;
                  fileMetadata = null;
                  Slog.w(str, "io exception on restore socket read: " + iOException.getMessage());
                  backupManagerYuva2 = mBackupManagerYuva;
                  if (backupManagerYuva2 != null) {}
                  setResult(-3);
                  if (fileMetadata != null) {}
                  if (fileMetadata == null) {}
                } catch (NullPointerException e10) {
                  nullPointerException = e10;
                  str = "BackupManagerService";
                  z3 = true;
                  fileMetadata = null;
                  Slog.w(str, "NullPointerException  exception on restore ", nullPointerException);
                  backupManagerYuva = mBackupManagerYuva;
                  if (backupManagerYuva != null) {}
                  if (fileMetadata != null) {}
                  if (fileMetadata == null) {}
                }
              } else {
                fileMetadata3 = readTarHeaders;
                tarBackupReader3 = tarBackupReader4;
                boolean installApk =
                    RestoreUtils.installApk(
                        inputStream,
                        this.mBackupManagerService.getContext(),
                        this.mDeleteObserver,
                        this.mManifestSignatures,
                        this.mPackagePolicies,
                        fileMetadata3,
                        str9,
                        bytesReadListener,
                        this.mUserId);
                HashMap hashMap3 = this.mPackagePolicies;
                if (installApk) {
                  restorePolicy = RestorePolicy.ACCEPT;
                } else {
                  restorePolicy = RestorePolicy.IGNORE;
                }
                hashMap3.put(str8, restorePolicy);
              }
              tarBackupReader3.skipTarPadding(fileMetadata3.size);
              return true;
            }
            String str10 = str8;
            fileMetadata2 = readTarHeaders;
            tarBackupReader = tarBackupReader4;
            this.mPackagePolicies.put(str10, RestorePolicy.IGNORE);
            str2 = str10;
          } else {
            if (i2 == 3) {
              if (readTarHeaders.domain.equals("a")) {
                Slog.d("BackupManagerService", "apk present but ACCEPT");
              } else {
                str7 = str8;
                fileMetadata2 = readTarHeaders;
                tarBackupReader = tarBackupReader4;
                str3 = str7;
                if (isRestorableFile(fileMetadata2) || !isCanonicalFilePath(fileMetadata2.path)) {
                  z8 = false;
                }
                if (z8 && this.mAgent == null) {
                  try {
                    this.mTargetApp =
                        this.mBackupManagerService
                            .getPackageManager()
                            .getApplicationInfoAsUser(str3, 0, this.mUserId);
                    if (!this.mClearedPackages.contains(str3)) {
                      boolean shouldForceClearAppDataOnFullRestore =
                          shouldForceClearAppDataOnFullRestore(this.mTargetApp.packageName);
                      if (this.mTargetApp.backupAgentName == null
                          || shouldForceClearAppDataOnFullRestore) {
                        Slog.d(
                            "BackupManagerService",
                            "Clearing app data preparatory to full restore");
                        this.mBackupManagerService.clearApplicationDataBeforeRestore(str3);
                      }
                      this.mClearedPackages.add(str3);
                    }
                    setUpPipes();
                    this.mAgent =
                        this.mBackupManagerService.bindToAgentSynchronous(
                            this.mTargetApp,
                            "k".equals(fileMetadata2.domain) ? 2 : 3,
                            this.mBackupEligibilityRules.getBackupDestination());
                    this.mAgentPackage = str3;
                  } catch (PackageManager.NameNotFoundException | IOException unused) {
                    BackupManagerYuva backupManagerYuva3 = mBackupManagerYuva;
                    if (backupManagerYuva3 != null) {
                      backupManagerYuva3.setMemorySaverRestoreFail();
                    }
                  }
                  if (this.mAgent == null) {
                    Slog.e("BackupManagerService", "Unable to create agent for " + str3);
                    tearDownPipes();
                    this.mPackagePolicies.put(str3, RestorePolicy.IGNORE);
                    z8 = false;
                  }
                }
                if (z8 && !str3.equals(this.mAgentPackage)) {
                  Slog.e(
                      "BackupManagerService",
                      "Restoring data for " + str3 + " but agent is for " + this.mAgentPackage);
                  z8 = false;
                }
                if (shouldSkipReadOnlyDir(fileMetadata2)) {
                  z8 = false;
                }
                if (z8) {
                  long j2 = fileMetadata2.size;
                  if (str3.equals("com.android.sharedstoragebackup")) {
                    restoreAgentTimeoutMillis =
                        this.mAgentTimeoutParameters.getSharedBackupAgentTimeoutMillis();
                  } else {
                    restoreAgentTimeoutMillis =
                        this.mAgentTimeoutParameters.getRestoreAgentTimeoutMillis(
                            this.mTargetApp.uid);
                  }
                  try {
                    try {
                      try {
                        this.mBackupManagerService.prepareOperationTimeout(
                            i, restoreAgentTimeoutMillis, this.mMonitorTask, 1);
                        try {
                          try {
                            if (!"obb".equals(fileMetadata2.domain)) {
                              z7 = z8;
                              j = j2;
                              PackageInfo packageInfo6 = str3;
                              try {
                                try {
                                  try {
                                    if ("k".equals(fileMetadata2.domain)) {
                                      StringBuilder sb = new StringBuilder();
                                      sb.append("Restoring key-value file for ");
                                      String str11 = packageInfo6;
                                      sb.append(str11);
                                      sb.append(" : ");
                                      sb.append(fileMetadata2.path);
                                      Slog.d("BackupManagerService", sb.toString());
                                      fileMetadata2.version = this.mAppVersion;
                                      UserBackupManagerService userBackupManagerService =
                                          this.mBackupManagerService;
                                      new Thread(
                                              new KeyValueAdbRestoreEngine(
                                                  userBackupManagerService,
                                                  userBackupManagerService.getDataDir(),
                                                  fileMetadata2,
                                                  this.mPipes[0],
                                                  this.mAgent,
                                                  i),
                                              "restore-key-value-runner")
                                          .start();
                                      packageInfo5 = str11;
                                    } else {
                                      PackageInfo packageInfo7 = packageInfo6;
                                      try {
                                        if (this.mTargetApp.processName.equals("system")) {
                                          Slog.d(
                                              "BackupManagerService",
                                              "system process agent - spinning a thread");
                                          new Thread(
                                                  new RestoreFileRunnable(
                                                      this.mBackupManagerService,
                                                      this.mAgent,
                                                      fileMetadata2,
                                                      this.mPipes[0],
                                                      i),
                                                  "restore-sys-runner")
                                              .start();
                                          packageInfo5 = packageInfo7;
                                        } else {
                                          if (this.mBackupManagerService
                                              .getDataExtractionRuleStatus()) {
                                            try {
                                              this.mAgent.doDisableDataExtractionRules(true);
                                            } catch (NullPointerException e11) {
                                              nullPointerException = e11;
                                              z3 = true;
                                              str = "BackupManagerService";
                                              fileMetadata = null;
                                              Slog.w(
                                                  str,
                                                  "NullPointerException  exception on restore ",
                                                  nullPointerException);
                                              backupManagerYuva = mBackupManagerYuva;
                                              if (backupManagerYuva != null) {}
                                              if (fileMetadata != null) {}
                                              if (fileMetadata == null) {}
                                            }
                                          }
                                          str4 = "BackupManagerService";
                                          tarBackupReader2 = tarBackupReader;
                                          try {
                                            try {
                                              packageInfo2 = packageInfo7;
                                            } catch (NullPointerException e12) {
                                              nullPointerException = e12;
                                              str = str4;
                                              fileMetadata = null;
                                              z3 = true;
                                              Slog.w(
                                                  str,
                                                  "NullPointerException  exception on restore ",
                                                  nullPointerException);
                                              backupManagerYuva = mBackupManagerYuva;
                                              if (backupManagerYuva != null) {
                                                backupManagerYuva.setMemorySaverRestoreFail();
                                              }
                                              if (fileMetadata != null) {}
                                              if (fileMetadata == null) {}
                                            }
                                            try {
                                              this.mAgent.doRestoreFile(
                                                  this.mPipes[0],
                                                  fileMetadata2.size,
                                                  fileMetadata2.type,
                                                  fileMetadata2.domain,
                                                  fileMetadata2.path,
                                                  fileMetadata2.mode,
                                                  fileMetadata2.mtime,
                                                  i,
                                                  this.mBackupManagerService
                                                      .getBackupManagerBinder());
                                            } catch (RemoteException unused2) {
                                              str = str4;
                                              Slog.e(str, "Agent crashed during full restore");
                                              z5 = false;
                                              z6 = false;
                                              if (z5) {}
                                              if (z6) {}
                                              z8 = z5;
                                              if (!z8) {}
                                              fileMetadata = fileMetadata2;
                                              if (fileMetadata != null) {}
                                              if (fileMetadata == null) {}
                                            } catch (IOException unused3) {
                                              str = str4;
                                              Slog.d(str, "Couldn't establish restore");
                                              z5 = false;
                                              z6 = false;
                                              if (z5) {}
                                              if (z6) {}
                                              z8 = z5;
                                              if (!z8) {}
                                              fileMetadata = fileMetadata2;
                                              if (fileMetadata != null) {}
                                              if (fileMetadata == null) {}
                                            }
                                          } catch (RemoteException unused4) {
                                            packageInfo2 = packageInfo7;
                                          } catch (IOException unused5) {
                                            packageInfo2 = packageInfo7;
                                          }
                                        }
                                      } catch (IOException unused6) {
                                        packageInfo2 = packageInfo7;
                                        tarBackupReader2 = tarBackupReader;
                                        str = "BackupManagerService";
                                        Slog.d(str, "Couldn't establish restore");
                                        z5 = false;
                                        z6 = false;
                                        if (z5) {}
                                        if (z6) {}
                                        z8 = z5;
                                        if (!z8) {}
                                        fileMetadata = fileMetadata2;
                                        if (fileMetadata != null) {}
                                        if (fileMetadata == null) {}
                                      }
                                    }
                                    packageInfo2 = packageInfo5;
                                    str4 = "BackupManagerService";
                                    tarBackupReader2 = tarBackupReader;
                                  } catch (IOException unused7) {
                                    packageInfo2 = packageInfo;
                                    str = "BackupManagerService";
                                    tarBackupReader2 = tarBackupReader;
                                  }
                                } catch (RemoteException unused8) {
                                  packageInfo2 = packageInfo;
                                  str4 = "BackupManagerService";
                                  tarBackupReader2 = tarBackupReader;
                                }
                              } catch (IOException unused9) {
                                tarBackupReader2 = tarBackupReader;
                                packageInfo2 = packageInfo6;
                              }
                            } else {
                              Slog.d(
                                  "BackupManagerService",
                                  "Restoring OBB file for " + str3 + " : " + fileMetadata2.path);
                              z7 = z8;
                              j = j2;
                              try {
                                str6 = str3;
                              } catch (RemoteException unused10) {
                                str4 = "BackupManagerService";
                                tarBackupReader2 = tarBackupReader;
                                packageInfo2 = str3;
                                str = str4;
                                try {
                                  Slog.e(str, "Agent crashed during full restore");
                                  z5 = false;
                                  z6 = false;
                                  if (z5) {}
                                  if (z6) {}
                                  z8 = z5;
                                  if (!z8) {}
                                  fileMetadata = fileMetadata2;
                                } catch (IOException e13) {
                                  iOException = e13;
                                  fileMetadata = null;
                                  z3 = true;
                                  Slog.w(
                                      str,
                                      "io exception on restore socket read: "
                                          + iOException.getMessage());
                                  backupManagerYuva2 = mBackupManagerYuva;
                                  if (backupManagerYuva2 != null) {}
                                  setResult(-3);
                                  if (fileMetadata != null) {}
                                  if (fileMetadata == null) {}
                                } catch (NullPointerException e14) {
                                  nullPointerException = e14;
                                  fileMetadata = null;
                                  z3 = true;
                                  Slog.w(
                                      str,
                                      "NullPointerException  exception on restore ",
                                      nullPointerException);
                                  backupManagerYuva = mBackupManagerYuva;
                                  if (backupManagerYuva != null) {}
                                  if (fileMetadata != null) {}
                                  if (fileMetadata == null) {}
                                }
                                if (fileMetadata != null) {}
                                if (fileMetadata == null) {}
                              } catch (IOException unused11) {
                                str = "BackupManagerService";
                                tarBackupReader2 = tarBackupReader;
                                packageInfo2 = str3;
                                try {
                                  Slog.d(str, "Couldn't establish restore");
                                  z5 = false;
                                  z6 = false;
                                  if (z5) {}
                                  if (z6) {}
                                  z8 = z5;
                                  if (!z8) {}
                                  fileMetadata = fileMetadata2;
                                } catch (IOException e15) {
                                  e = e15;
                                  fileMetadata = null;
                                  z3 = true;
                                  iOException = e;
                                  Slog.w(
                                      str,
                                      "io exception on restore socket read: "
                                          + iOException.getMessage());
                                  backupManagerYuva2 = mBackupManagerYuva;
                                  if (backupManagerYuva2 != null) {}
                                  setResult(-3);
                                  if (fileMetadata != null) {}
                                  if (fileMetadata == null) {}
                                } catch (NullPointerException e16) {
                                  e = e16;
                                  fileMetadata = null;
                                  z3 = true;
                                  nullPointerException = e;
                                  Slog.w(
                                      str,
                                      "NullPointerException  exception on restore ",
                                      nullPointerException);
                                  backupManagerYuva = mBackupManagerYuva;
                                  if (backupManagerYuva != null) {}
                                  if (fileMetadata != null) {}
                                  if (fileMetadata == null) {}
                                }
                                if (fileMetadata != null) {}
                                if (fileMetadata == null) {}
                              }
                              try {
                                this.mObbConnection.restoreObbFile(
                                    str6,
                                    this.mPipes[0],
                                    fileMetadata2.size,
                                    fileMetadata2.type,
                                    fileMetadata2.path,
                                    fileMetadata2.mode,
                                    fileMetadata2.mtime,
                                    i,
                                    this.mBackupManagerService.getBackupManagerBinder());
                                tarBackupReader2 = tarBackupReader;
                                packageInfo2 = str6;
                                str4 = "BackupManagerService";
                              } catch (RemoteException unused12) {
                                str4 = "BackupManagerService";
                                tarBackupReader2 = tarBackupReader;
                                packageInfo2 = str6;
                                str = str4;
                                Slog.e(str, "Agent crashed during full restore");
                                z5 = false;
                                z6 = false;
                                if (z5) {}
                                if (z6) {}
                                z8 = z5;
                                if (!z8) {}
                                fileMetadata = fileMetadata2;
                                if (fileMetadata != null) {}
                                if (fileMetadata == null) {}
                              } catch (IOException unused13) {
                                str = "BackupManagerService";
                                tarBackupReader2 = tarBackupReader;
                                packageInfo2 = str6;
                                Slog.d(str, "Couldn't establish restore");
                                z5 = false;
                                z6 = false;
                                if (z5) {}
                                if (z6) {}
                                z8 = z5;
                                if (!z8) {}
                                fileMetadata = fileMetadata2;
                                if (fileMetadata != null) {}
                                if (fileMetadata == null) {}
                              }
                            }
                            z5 = z7;
                            str = str4;
                            z6 = true;
                          } catch (RemoteException unused14) {
                            tarBackupReader2 = tarBackupReader;
                            packageInfo2 = packageInfo4;
                            str4 = "BackupManagerService";
                          }
                        } catch (IOException unused15) {
                          str = "BackupManagerService";
                          tarBackupReader2 = tarBackupReader;
                          packageInfo2 = packageInfo3;
                        }
                      } catch (IOException unused16) {
                        j = j2;
                        tarBackupReader2 = tarBackupReader;
                        packageInfo2 = str3;
                      }
                    } catch (RemoteException unused17) {
                      j = j2;
                    }
                  } catch (IOException unused18) {
                    j = j2;
                  }
                  if (z5) {
                    bArr2 = bArr;
                    z3 = true;
                  } else {
                    try {
                      z3 = true;
                      try {
                        try {
                          FileOutputStream fileOutputStream =
                              new FileOutputStream(this.mPipes[1].getFileDescriptor());
                          boolean z9 = true;
                          long j3 = j;
                          while (true) {
                            if (j3 <= 0) {
                              bArr2 = bArr;
                              break;
                            }
                            bArr2 = bArr;
                            int read =
                                inputStream.read(
                                    bArr2, 0, j3 > ((long) bArr2.length) ? bArr2.length : (int) j3);
                            if (read <= 0) {
                              break;
                            }
                            j3 -= read;
                            if (z9) {
                              try {
                                fileOutputStream.write(bArr2, 0, read);
                              } catch (IOException e17) {
                                Slog.e(str, "Failed to write to restore pipe: " + e17.getMessage());
                                z9 = false;
                              }
                            }
                          }
                          tarBackupReader2.skipTarPadding(fileMetadata2.size);
                          z6 = this.mBackupManagerService.waitUntilOperationComplete(i);
                        } catch (IOException e18) {
                          e = e18;
                          iOException = e;
                          fileMetadata = null;
                          Slog.w(
                              str,
                              "io exception on restore socket read: " + iOException.getMessage());
                          backupManagerYuva2 = mBackupManagerYuva;
                          if (backupManagerYuva2 != null) {
                            backupManagerYuva2.setMemorySaverRestoreFail();
                          }
                          setResult(-3);
                          if (fileMetadata != null) {}
                          if (fileMetadata == null) {}
                        }
                      } catch (NullPointerException e19) {
                        e = e19;
                        nullPointerException = e;
                        fileMetadata = null;
                        Slog.w(
                            str,
                            "NullPointerException  exception on restore ",
                            nullPointerException);
                        backupManagerYuva = mBackupManagerYuva;
                        if (backupManagerYuva != null) {}
                        if (fileMetadata != null) {}
                        if (fileMetadata == null) {}
                      }
                    } catch (IOException e20) {
                      e = e20;
                      z3 = true;
                    } catch (NullPointerException e21) {
                      e = e21;
                      z3 = true;
                    }
                  }
                  if (z6) {
                    try {
                      StringBuilder sb2 = new StringBuilder();
                      sb2.append("Agent failure restoring ");
                      str5 = packageInfo2;
                      sb2.append(str5);
                      sb2.append("; ending restore");
                      Slog.w(str, sb2.toString());
                      this.mBackupManagerService.getBackupHandler().removeMessages(18);
                      tearDownPipes();
                      tearDownAgent(this.mTargetApp, false);
                      fileMetadata = null;
                    } catch (IOException e22) {
                      e = e22;
                      fileMetadata = null;
                    } catch (NullPointerException e23) {
                      e = e23;
                      fileMetadata = null;
                    }
                    try {
                      this.mAgent = null;
                      this.mPackagePolicies.put(str5, RestorePolicy.IGNORE);
                      BackupManagerYuva backupManagerYuva4 = mBackupManagerYuva;
                      if (backupManagerYuva4 != null) {
                        backupManagerYuva4.setMemorySaverRestoreFail();
                      }
                      if (packageInfo != null) {
                        setResult(-2);
                        setRunning(false);
                        return false;
                      }
                    } catch (IOException e24) {
                      e = e24;
                      iOException = e;
                      Slog.w(
                          str, "io exception on restore socket read: " + iOException.getMessage());
                      backupManagerYuva2 = mBackupManagerYuva;
                      if (backupManagerYuva2 != null) {}
                      setResult(-3);
                      if (fileMetadata != null) {}
                      if (fileMetadata == null) {}
                    } catch (NullPointerException e25) {
                      e = e25;
                      nullPointerException = e;
                      Slog.w(
                          str, "NullPointerException  exception on restore ", nullPointerException);
                      backupManagerYuva = mBackupManagerYuva;
                      if (backupManagerYuva != null) {}
                      if (fileMetadata != null) {}
                      if (fileMetadata == null) {}
                    }
                  }
                  z8 = z5;
                } else {
                  bArr2 = bArr;
                  z3 = true;
                }
                if (!z8) {
                  long j4 = (fileMetadata2.size + 511) & (-512);
                  for (long j5 = 0; j4 > j5; j5 = 0) {
                    long read2 =
                        inputStream.read(
                            bArr2, 0, j4 > ((long) bArr2.length) ? bArr2.length : (int) j4);
                    if (read2 <= 0) {
                      break;
                    }
                    j4 -= read2;
                  }
                  BackupManagerYuva backupManagerYuva5 = mBackupManagerYuva;
                  if (backupManagerYuva5 != null) {
                    backupManagerYuva5.setMemorySaverRestoreFail();
                  }
                }
                fileMetadata = fileMetadata2;
                if (fileMetadata != null) {
                  tearDownPipes();
                  z4 = false;
                  setRunning(false);
                  if (z) {
                    tearDownAgent(this.mTargetApp, this.mIsAdbRestore);
                  }
                } else {
                  z4 = false;
                }
                return fileMetadata == null ? z3 : z4;
              }
            } else {
              Slog.e("BackupManagerService", "Invalid policy from manifest");
              this.mPackagePolicies.put(str8, RestorePolicy.IGNORE);
            }
            str7 = str8;
            fileMetadata2 = readTarHeaders;
            z8 = false;
            tarBackupReader = tarBackupReader4;
            str3 = str7;
            if (isRestorableFile(fileMetadata2)) {}
            z8 = false;
            if (z8) {
              this.mTargetApp =
                  this.mBackupManagerService
                      .getPackageManager()
                      .getApplicationInfoAsUser(str3, 0, this.mUserId);
              if (!this.mClearedPackages.contains(str3)) {}
              setUpPipes();
              this.mAgent =
                  this.mBackupManagerService.bindToAgentSynchronous(
                      this.mTargetApp,
                      "k".equals(fileMetadata2.domain) ? 2 : 3,
                      this.mBackupEligibilityRules.getBackupDestination());
              this.mAgentPackage = str3;
              if (this.mAgent == null) {}
            }
            if (z8) {
              Slog.e(
                  "BackupManagerService",
                  "Restoring data for " + str3 + " but agent is for " + this.mAgentPackage);
              z8 = false;
            }
            if (shouldSkipReadOnlyDir(fileMetadata2)) {}
            if (z8) {}
            if (!z8) {}
            fileMetadata = fileMetadata2;
            if (fileMetadata != null) {}
            if (fileMetadata == null) {}
          }
        } else {
          str2 = str8;
          fileMetadata2 = readTarHeaders;
          tarBackupReader = tarBackupReader4;
        }
        z8 = false;
        str3 = str2;
        if (isRestorableFile(fileMetadata2)) {}
        z8 = false;
        if (z8) {}
        if (z8) {}
        if (shouldSkipReadOnlyDir(fileMetadata2)) {}
        if (z8) {}
        if (!z8) {}
        fileMetadata = fileMetadata2;
        if (fileMetadata != null) {}
        if (fileMetadata == null) {}
      }
    }
    fileMetadata2 = readTarHeaders;
    z3 = true;
    fileMetadata = fileMetadata2;
    if (fileMetadata != null) {}
    if (fileMetadata == null) {}
  }

  /* renamed from: com.android.server.backup.restore.FullRestoreEngine$1 */
  public abstract /* synthetic */ class AbstractC08011 {
    public static final /* synthetic */ int[]
        $SwitchMap$com$android$server$backup$restore$RestorePolicy;

    static {
      int[] iArr = new int[RestorePolicy.values().length];
      $SwitchMap$com$android$server$backup$restore$RestorePolicy = iArr;
      try {
        iArr[RestorePolicy.IGNORE.ordinal()] = 1;
      } catch (NoSuchFieldError unused) {
      }
      try {
        $SwitchMap$com$android$server$backup$restore$RestorePolicy[
                RestorePolicy.ACCEPT_IF_APK.ordinal()] =
            2;
      } catch (NoSuchFieldError unused2) {
      }
      try {
        $SwitchMap$com$android$server$backup$restore$RestorePolicy[RestorePolicy.ACCEPT.ordinal()] =
            3;
      } catch (NoSuchFieldError unused3) {
      }
    }
  }

  public boolean shouldSkipReadOnlyDir(FileMetadata fileMetadata) {
    if (isValidParent(this.mReadOnlyParent, fileMetadata)) {
      return true;
    }
    if (isReadOnlyDir(fileMetadata)) {
      this.mReadOnlyParent = fileMetadata;
      Slog.w(
          "BackupManagerService",
          "Skipping restore of "
              + fileMetadata.path
              + " and its contents as read-only dirs are currently not supported.");
      return true;
    }
    this.mReadOnlyParent = null;
    return false;
  }

  public static boolean isValidParent(FileMetadata fileMetadata, FileMetadata fileMetadata2) {
    return fileMetadata != null
        && fileMetadata2.packageName.equals(fileMetadata.packageName)
        && fileMetadata2.domain.equals(fileMetadata.domain)
        && fileMetadata2.path.startsWith(getPathWithTrailingSeparator(fileMetadata.path));
  }

  public static String getPathWithTrailingSeparator(String str) {
    String str2 = File.separator;
    if (str.endsWith(str2)) {
      return str;
    }
    return str + str2;
  }

  public static boolean isReadOnlyDir(FileMetadata fileMetadata) {
    return fileMetadata.type == 2 && (fileMetadata.mode & ((long) OsConstants.S_IWUSR)) == 0;
  }

  public final void setUpPipes() {
    synchronized (this.mPipesLock) {
      this.mPipes = ParcelFileDescriptor.createPipe();
      this.mPipesClosed = false;
    }
  }

  public final void tearDownPipes() {
    ParcelFileDescriptor[] parcelFileDescriptorArr;
    synchronized (this.mPipesLock) {
      if (!this.mPipesClosed && (parcelFileDescriptorArr = this.mPipes) != null) {
        try {
          parcelFileDescriptorArr[0].close();
          this.mPipes[1].close();
          this.mPipesClosed = true;
        } catch (IOException e) {
          Slog.w("BackupManagerService", "Couldn't close agent pipes", e);
        }
      }
    }
  }

  public final void tearDownAgent(ApplicationInfo applicationInfo, boolean z) {
    if (this.mAgent != null) {
      if (z) {
        try {
          int generateRandomIntegerToken = this.mBackupManagerService.generateRandomIntegerToken();
          long fullBackupAgentTimeoutMillis =
              this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis();
          AdbRestoreFinishedLatch adbRestoreFinishedLatch =
              new AdbRestoreFinishedLatch(
                  this.mBackupManagerService, this.mOperationStorage, generateRandomIntegerToken);
          this.mBackupManagerService.prepareOperationTimeout(
              generateRandomIntegerToken, fullBackupAgentTimeoutMillis, adbRestoreFinishedLatch, 1);
          if (this.mTargetApp.processName.equals("system")) {
            new Thread(
                    new AdbRestoreFinishedRunnable(
                        this.mAgent, generateRandomIntegerToken, this.mBackupManagerService),
                    "restore-sys-finished-runner")
                .start();
          } else {
            this.mAgent.doRestoreFinished(
                generateRandomIntegerToken, this.mBackupManagerService.getBackupManagerBinder());
          }
          adbRestoreFinishedLatch.await();
        } catch (RemoteException unused) {
          Slog.d("BackupManagerService", "Lost app trying to shut down");
        }
      }
      this.mBackupManagerService.tearDownAgentAndKill(applicationInfo);
      this.mAgent = null;
    }
  }

  public void handleTimeout() {
    tearDownPipes();
    setResult(-2);
    setRunning(false);
  }

  public final boolean isRestorableFile(FileMetadata fileMetadata) {
    if (this.mBackupEligibilityRules.getBackupDestination() == 1) {
      return true;
    }
    if ("c".equals(fileMetadata.domain)) {
      return false;
    }
    return ("r".equals(fileMetadata.domain) && fileMetadata.path.startsWith("no_backup/"))
        ? false
        : true;
  }

  public static boolean isCanonicalFilePath(String str) {
    return (str.contains("..") || str.contains("//")) ? false : true;
  }

  public final boolean shouldForceClearAppDataOnFullRestore(String str) {
    String stringForUser =
        Settings.Secure.getStringForUser(
            this.mBackupManagerService.getContext().getContentResolver(),
            "packages_to_clear_data_before_full_restore",
            this.mUserId);
    if (TextUtils.isEmpty(stringForUser)) {
      return false;
    }
    return Arrays.asList(stringForUser.split(KnoxVpnFirewallHelper.DELIMITER)).contains(str);
  }
}
