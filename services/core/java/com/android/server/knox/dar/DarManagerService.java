package com.android.server.knox.dar;

import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.app.UserSwitchObserver;
import android.app.admin.PasswordMetrics;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.UserInfo;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;

import com.android.internal.os.BackgroundThread;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;
import com.android.internal.widget.LockscreenCredential;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.knox.dar.ddar.core.DualDarDoManagerImpl;
import com.android.server.knox.dar.ddar.core.DualDarDoManagerImpl$$ExternalSyntheticLambda1;
import com.android.server.knox.dar.sdp.SDPLog;
import com.android.server.knox.dar.sdp.SDPLogFile;
import com.android.server.knox.dar.sdp.SDPLogger;
import com.android.server.knox.dar.sdp.security.BytesUtil;
import com.android.server.knox.zt.devicetrust.AppMonitor;
import com.android.server.knox.zt.devicetrust.EndpointMonitorImpl;
import com.android.server.pm.UserManagerInternal;

import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.dar.VirtualLockUtils;
import com.samsung.android.knox.dar.ddar.DualDarManager;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DarManagerService extends IDarManagerService.Stub {
    public static final UserInfo NULL_USER = new UserInfo(-10000, (String) null, (String) null, 0);
    public final ActivityManagerInternal mActivityManagerInternal;
    public final Context mContext;
    public DarHandler mDarHandler = null;
    public final DualDarDoManagerImpl mDualDarDoManagerImpl;
    public final EndpointMonitorImpl mEndpointMonitorImpl;
    public final DarManagerService$$ExternalSyntheticLambda1 mEscrowTokenStateChangeCallback;
    public final Injector mInjector;
    public final KeyProtector mKeyProtector;
    public final LockPatternUtils mLockPatternUtils;
    public UserManager mUserManager;
    public final AnonymousClass1 mUserSwitchObserver;
    public final VirtualLockImpl mVirtualLockImpl;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DarHandler extends Handler {
        public DarHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            UserInfo userInfo;
            int i = message.what;
            boolean z = false;
            DarManagerService darManagerService = DarManagerService.this;
            if (i != 118) {
                if (i != 120) {
                    if (i != 150) {
                        Log.e("DarManagerService", "msg : ignore unknown message");
                        return;
                    } else {
                        Log.d("DarManagerServiceHandler", "MSG_UNLOCK_SECURE_FOLDER_WITH_TOKEN ");
                        darManagerService.handleUnlockSecureFolderWithToken(message.arg1);
                        return;
                    }
                }
                Log.d("DarManagerServiceHandler", " MSG_HANDLE_LOCKED_BOOT_COMPLETE ");
                int i2 = message.arg1;
                darManagerService.getClass();
                if (StorageManager.isFileEncrypted()) {
                    if (SemPersonaManager.isKnoxId(i2)) {
                        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                i2, "Locked boot completed for user ", "DarManagerService");
                    }
                    if (SemPersonaManager.isSecureFolderId(i2)) {
                        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                i2,
                                "Locked boot completed for SecureFolder user ",
                                "DarManagerService");
                        int intForUser =
                                Settings.Secure.getIntForUser(
                                        darManagerService.mContext.getContentResolver(),
                                        "automatic_data_decryption",
                                        0,
                                        i2);
                        UserInfo profileParent =
                                darManagerService.getUserManager().getProfileParent(i2);
                        if (profileParent == null
                                || !darManagerService
                                        .getUserManager()
                                        .isUserUnlockingOrUnlocked(profileParent.id)) {
                            StringBuilder sb = new StringBuilder("Parent ");
                            sb.append(profileParent != null ? profileParent.id : 0);
                            sb.append(" is not ready to unlock secure folder user ");
                            sb.append(i2);
                            Log.d("DarManagerService", sb.toString());
                            return;
                        }
                        if (darManagerService.getUserManager().isUserUnlockingOrUnlocked(i2)
                                || intForUser != 1) {
                            return;
                        }
                        Log.d("DarManagerService", "Unlock secure folder user " + i2);
                        darManagerService.checkSystemPermission();
                        darManagerService.mDarHandler.sendMessage(
                                darManagerService.mDarHandler.obtainMessage(150, i2, 0));
                        return;
                    }
                    return;
                }
                return;
            }
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder(" MSG_SET_RESET_TOKEN_FOR_LEGACY : user : "),
                    message.arg1,
                    "DarManagerServiceHandler");
            String str = (String) message.obj;
            int i3 = message.arg1;
            darManagerService.getClass();
            Log.i("DarManagerService", "Set reset token for Legacy User " + i3);
            Log.d("DarManagerService", "token : " + str + ", userId : " + i3);
            darManagerService.mInjector.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    userInfo = darManagerService.getUserManager().getUserInfo(i3);
                } finally {
                    darManagerService.mInjector.getClass();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Exception e) {
                Log.e(
                        "DarManagerService",
                        "exception occurred during getUserInfo for Legacy user " + i3,
                        e);
                e.printStackTrace();
                darManagerService.mInjector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                userInfo = null;
            }
            if (userInfo == null) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        i3, "handle reset Token getUserInfo failed. ", "DarManagerService");
                return;
            }
            boolean isSecureFolder = userInfo.isSecureFolder();
            if (isSecureFolder) {
                GestureWakeup$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Identified as legacy type container user : "),
                        userInfo.id,
                        "DARUtil");
            }
            if (!isSecureFolder) {
                Log.d(
                        "DarManagerService",
                        String.format(
                                "On created - User %d workspace identified as new-fashioned",
                                Integer.valueOf(i3)));
                return;
            }
            Log.d(
                    "DarManagerService",
                    String.format(
                            "On created - User %d workspace identified as old-fashioned",
                            Integer.valueOf(i3)));
            boolean isEmpty = SecureUtil.isEmpty(str);
            byte[] generateSeed =
                    isEmpty
                            ? SecureUtil.sSecureRandom.generateSeed(32)
                            : str.getBytes(Charset.forName("UTF-8"));
            if (isEmpty) {
                Log.d(
                        "DarManagerService",
                        String.format(
                                "On created - Save reset token via protector for Legacy user %d has"
                                    + " been deployed : %s",
                                Integer.valueOf(i3),
                                Boolean.valueOf(
                                        darManagerService.saveResetTokenViaProtectorForLegacy(
                                                generateSeed, i3))));
            }
            boolean resetTokenForLegacy =
                    darManagerService.setResetTokenForLegacy(generateSeed, i3);
            SecureUtil.clear(generateSeed);
            Log.d(
                    "DarManagerService",
                    String.format(
                            "On created - Set reset token for Legacy user %d has been deployed :"
                                + " %s",
                            Integer.valueOf(i3), Boolean.valueOf(resetTokenForLegacy)));
            if (isEmpty) {
                return;
            }
            try {
                z =
                        EnterpriseKnoxManager.getInstance()
                                .getKnoxContainerManager(darManagerService.mContext, i3)
                                .getPasswordPolicy()
                                .enforcePwdChange();
            } catch (Exception e2) {
                Log.e(
                        "DarManagerService",
                        "Unexpected exception while enforce password for Legacy user " + i3,
                        e2);
            }
            Log.d(
                    "DarManagerService",
                    String.format(
                            "On created - Password enforcement for Legacy user %d has been deployed"
                                + " : %s",
                            Integer.valueOf(i3), Boolean.valueOf(z)));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public final Context mContext;
        public final DarDatabaseCache mDarDatabaseCache;
        public final LockPatternUtils mLockPatternUtils;
        public LockPatternUtils.EscrowTokenStateChangeCallback mEscrowTokenStateChangeCallback =
                null;
        public IDualDARPolicy mDualDARPolicyService = null;

        public Injector(Context context) {
            this.mContext = context;
            this.mDarDatabaseCache = new DarDatabaseCache(context);
            this.mLockPatternUtils = new LockPatternUtils(context);
        }

        public static void enforceCallerKnoxCoreOrSelf(String str) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            if (callingUid != 5250 && callingPid != Process.myPid()) {
                throw new SecurityException(
                        AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                ArrayUtils$$ExternalSyntheticOutline0.m(
                                        callingPid,
                                        callingUid,
                                        "Security Exception Occurred while pid[",
                                        "] with uid[",
                                        "] trying to access methodName ["),
                                str,
                                "] in [DarManagerService] service"));
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.knox.dar.DarManagerService$1] */
    public DarManagerService(Injector injector) {
        this.mKeyProtector = null;
        this.mUserManager = null;
        LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback =
                new LockPatternUtils
                        .EscrowTokenStateChangeCallback() { // from class:
                                                            // com.android.server.knox.dar.DarManagerService$$ExternalSyntheticLambda1
                    public final void onEscrowTokenActivated(long j, int i) {
                        DarManagerService darManagerService = DarManagerService.this;
                        if (darManagerService.isDualDarDoSupported()) {
                            DualDarDoManagerImpl dualDarDoManagerImpl =
                                    darManagerService.mDualDarDoManagerImpl;
                            dualDarDoManagerImpl.getClass();
                            if (DualDarManager.isOnDeviceOwnerEnabled()
                                    && dualDarDoManagerImpl.getInnerAuthUserId(0) == i) {
                                Log.d(
                                        "DualDarManagerImpl",
                                        String.format(
                                                "Token(%x) activated for user %d",
                                                Long.valueOf(j), Integer.valueOf(i)));
                                if (dualDarDoManagerImpl.mHasTokenSetForInner) {
                                    dualDarDoManagerImpl.mHasTokenSetForInner = false;
                                    Binder.withCleanCallingIdentity(
                                            new DualDarDoManagerImpl$$ExternalSyntheticLambda1(
                                                    dualDarDoManagerImpl));
                                }
                            }
                        }
                    }
                };
        this.mUserSwitchObserver =
                new UserSwitchObserver() { // from class:
                                           // com.android.server.knox.dar.DarManagerService.1
                    public final void onLockedBootComplete(int i) {
                        DirEncryptService$$ExternalSyntheticOutline0.m(
                                i, "onLockedBootComplete: ", "DarManagerService");
                        DarManagerService.this.mDarHandler.sendMessage(
                                DarManagerService.this.mDarHandler.obtainMessage(120, i, 0));
                    }
                };
        Log.i("DarManagerService", "DarManagerService init");
        Context context = injector.mContext;
        this.mContext = context;
        this.mInjector = injector;
        if (KeyProtector.sInstance == null) {
            synchronized (KeyProtector.class) {
                try {
                    if (KeyProtector.sInstance == null) {
                        KeyProtector.sInstance = new KeyProtector();
                    }
                } finally {
                }
            }
        }
        this.mKeyProtector = KeyProtector.sInstance;
        this.mUserManager = (UserManager) injector.mContext.getSystemService("user");
        this.mLockPatternUtils = injector.mLockPatternUtils;
        injector.mEscrowTokenStateChangeCallback = escrowTokenStateChangeCallback;
        systemReady();
        this.mVirtualLockImpl = new VirtualLockImpl(injector);
        this.mDualDarDoManagerImpl = new DualDarDoManagerImpl(injector);
        Log.i("DarManagerService_DUAL_DAR", "prepare DualDAR DO Service");
        this.mActivityManagerInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mEndpointMonitorImpl = new EndpointMonitorImpl(context);
        Log.i("DarManagerService_SDP", "prepare EndpointMonitor Service");
    }

    public static byte[] fileRead(String str) {
        FileInputStream fileInputStream;
        byte[] bArr;
        byte[] bArr2 = null;
        if (str == null || str.isEmpty()) {
            return null;
        }
        File file = new File(str);
        if (!file.exists()) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                bArr = new byte[fileInputStream.available()];
                fileInputStream.read(bArr);
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            e = e;
            e.printStackTrace();
            return bArr2;
        }
        try {
            fileInputStream.close();
            return bArr;
        } catch (Exception e2) {
            e = e2;
            bArr2 = bArr;
            e.printStackTrace();
            return bArr2;
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

    public final void addBlockedClearablePackages(int i, String str) {
        if (isDualDarDoSupported()) {
            Injector injector = this.mDualDarDoManagerImpl.mInjector;
            injector.getClass();
            Injector.enforceCallerKnoxCoreOrSelf("addBlockedClearablePackages");
            DarDatabaseCache darDatabaseCache = injector.mDarDatabaseCache;
            String internal = darDatabaseCache.getInternal(i, "pkg_blocked_clearable");
            if (internal == null) {
                internal = "empty";
            }
            if (internal.equals("empty")) {
                darDatabaseCache.putInternal(i, "pkg_blocked_clearable", str);
            } else {
                darDatabaseCache.putInternal(
                        i,
                        "pkg_blocked_clearable",
                        AnyMotionDetector$$ExternalSyntheticOutline0.m(internal, ",", str));
            }
        }
    }

    public final int addEngine(SdpCreationParam sdpCreationParam, String str, String str2) {
        isSdpSupported();
        return -10;
    }

    public final int allow(String str, String str2) {
        isSdpSupported();
        return -10;
    }

    public final boolean checkDeviceIntegrity(Certificate[] certificateArr) {
        IntegrityStatus integrityStatus;
        try {
            integrityStatus =
                    new AttestedCertParser((X509Certificate) certificateArr[0]).mKnoxIngetrity;
        } catch (CertificateParsingException e) {
            e.printStackTrace();
        }
        if (integrityStatus != null
                && integrityStatus.mWarranty == 0
                && integrityStatus.mTrustBoot == 0) {
            return true;
        }
        EngineeringModeManager engineeringModeManager = new EngineeringModeManager(this.mContext);
        boolean z =
                engineeringModeManager.isConnected() && engineeringModeManager.getStatus(66) == 1;
        Log.d("DarManagerService", "EM Token status : " + z);
        if (z) {
            Log.d(
                    "DarManagerService",
                    "Failed in device integrity check. But, EM Token is allowed. Continue - ");
            return true;
        }
        return false;
    }

    public final void checkSystemPermission() {
        this.mInjector.getClass();
        if (Binder.getCallingUid() == 1000) {
            return;
        }
        Log.e("DarManagerService", "Require system permission.");
        StringBuilder sb = new StringBuilder("Security Exception Occurred in pid[");
        this.mInjector.getClass();
        sb.append(Binder.getCallingPid());
        sb.append("] with uid[");
        this.mInjector.getClass();
        sb.append(Binder.getCallingUid());
        sb.append("]");
        throw new SecurityException(sb.toString());
    }

    public final boolean clearResetPasswordToken(int i) {
        boolean clearResetPasswordTokenInternal;
        if (!isVirtualLockSupported()) {
            return false;
        }
        VirtualLockImpl virtualLockImpl = this.mVirtualLockImpl;
        virtualLockImpl.getClass();
        SDPLog.d("VirtualLockImpl", "Clear Reset password token for user " + i);
        if (VirtualLockUtils.isVirtualUserId(i)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                clearResetPasswordTokenInternal =
                        virtualLockImpl.clearResetPasswordTokenInternal(
                                i, virtualLockImpl.mDarDatabaseCache.getLong(i));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            clearResetPasswordTokenInternal = false;
        }
        if (clearResetPasswordTokenInternal && isDualDarDoSupported()) {
            DualDarDoManagerImpl dualDarDoManagerImpl = this.mDualDarDoManagerImpl;
            dualDarDoManagerImpl.getClass();
            if (DualDarManager.isOnDeviceOwnerEnabled()
                    && dualDarDoManagerImpl.getInnerAuthUserId(0) == i
                    && dualDarDoManagerImpl.mHasTokenSetForInner) {
                dualDarDoManagerImpl.mHasTokenSetForInner = false;
                Binder.withCleanCallingIdentity(
                        new DualDarDoManagerImpl$$ExternalSyntheticLambda1(dualDarDoManagerImpl));
            }
        }
        return clearResetPasswordTokenInternal;
    }

    public final int createEncPkgDir(int i, String str) {
        isSdpSupported();
        return 0;
    }

    public final int deleteToeknFromTrusted(String str) {
        isSdpSupported();
        return -10;
    }

    public final int disallow(String str, String str2) {
        isSdpSupported();
        return -10;
    }

    public boolean doesSpecificKeyExist(String str, int i) {
        if (SecureUtil.isEmpty(str)) {
            return false;
        }
        this.mKeyProtector.getClass();
        String attach = KeyProtector.attach(i, str);
        boolean checkSecretKey = KeyProtector.checkSecretKey(attach);
        if (checkSecretKey) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "Key exists in keystore(", attach, ")", "KeyProtector");
        }
        return checkSecretKey;
    }

    public final void dump(
            final FileDescriptor fileDescriptor,
            final PrintWriter printWriter,
            final String[] strArr) {
        RandomAccessFile randomAccessFile;
        if (DumpUtils.checkDumpPermission(this.mContext, "DarManagerService", printWriter)) {
            isSdpSupported();
            if (isDualDarDoSupported()) {
                printWriter.println("dualdar_dump");
                Injector injector = this.mInjector;
                FunctionalUtils.ThrowingRunnable throwingRunnable =
                        new FunctionalUtils.ThrowingRunnable(
                                fileDescriptor, printWriter, strArr) { // from class:
                            // com.android.server.knox.dar.DarManagerService$$ExternalSyntheticLambda0
                            public final /* synthetic */ PrintWriter f$2;

                            {
                                this.f$2 = printWriter;
                            }

                            /* JADX WARN: Code restructure failed: missing block: B:37:0x0163, code lost:

                               if (r4 >= 300000) goto L42;
                            */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void runOrThrow() {
                                /*
                                    Method dump skipped, instructions count: 530
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.android.server.knox.dar.DarManagerService$$ExternalSyntheticLambda0.runOrThrow():void");
                            }
                        };
                injector.getClass();
                Binder.withCleanCallingIdentity(throwingRunnable);
            }
            if (strArr == null || strArr.length <= 0) {
                return;
            }
            if (Arrays.asList(strArr).contains("-a") || Arrays.asList(strArr).contains("sdplog")) {
                printWriter.println("sdplog_dump");
                printWriter.println(
                        "-------------------------------------------------- START DUMP"
                            + " --------------------------------------------------");
                boolean z = SDPLog.DEBUG;
                boolean z2 = SDPLogger.DEBUG;
                if (((ReentrantLock) SDPLogFile.FILE_LOCK).tryLock()) {
                    String str = null;
                    try {
                        randomAccessFile = new RandomAccessFile("/data/log/sdp_log", "r");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        try {
                            SDPLogFile.check(randomAccessFile);
                            randomAccessFile.seek(17L);
                            int i = 0;
                            while (true) {
                                String readLine = randomAccessFile.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                int i2 = i + 1;
                                if (i >= 6000) {
                                    str = "Dump line count reached to the limit: 6000";
                                    break;
                                } else {
                                    printWriter.println(readLine);
                                    i = i2;
                                }
                            }
                            randomAccessFile.close();
                            ((ReentrantLock) SDPLogFile.FILE_LOCK).unlock();
                            if (str != null) {
                                printWriter.println(str);
                            }
                            printWriter.println();
                        } catch (SecurityException e2) {
                            String str2 = "Failed to dump: " + e2.getMessage();
                            if (str2 != null) {
                                Log.e("SDPLogFile", str2);
                            }
                            throw new IOException(str2);
                        }
                    } finally {
                    }
                } else {
                    Log.e(
                            "SDPLogFile",
                            "Failed to dump: Maybe target file is already being used...");
                    printWriter.println("Target file busy");
                }
                printWriter.println(
                        "-------------------------------------------------- END DUMP"
                            + " --------------------------------------------------");
            }
        }
    }

    public final int exists(String str) {
        isSdpSupported();
        return -10;
    }

    public boolean generateAndSaveSpecificKey(String str, int i) {
        boolean z;
        byte[] generateSeed = SecureUtil.sSecureRandom.generateSeed(32);
        try {
            if (!SecureUtil.isEmpty(str)) {
                boolean saveSpecificKeyViaProtector =
                        saveSpecificKeyViaProtector(generateSeed, str, i);
                SecureUtil.record(saveSpecificKeyViaProtector);
                if (saveSpecificKeyViaProtector) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            SecureUtil.clear(generateSeed);
        }
    }

    public final int getAvailableUserId() {
        if (isVirtualLockSupported()) {
            return this.mVirtualLockImpl.getAvailableUserId();
        }
        return -10000;
    }

    public final List getBlockedClearablePackages(int i) {
        if (!isDualDarDoSupported()) {
            return null;
        }
        DualDarDoManagerImpl dualDarDoManagerImpl = this.mDualDarDoManagerImpl;
        Injector injector = dualDarDoManagerImpl.mInjector;
        injector.getClass();
        Injector.enforceCallerKnoxCoreOrSelf("getBlockedClearablePackages");
        ArrayList arrayList = new ArrayList();
        String internal = injector.mDarDatabaseCache.getInternal(i, "pkg_blocked_clearable");
        if (internal == null) {
            internal = "empty";
        }
        if (!internal.equals("empty")) {
            arrayList.addAll(Arrays.asList(internal.split(",")));
        }
        arrayList.addAll(dualDarDoManagerImpl.mNonClearablePackages);
        return arrayList;
    }

    public final SdpEngineInfo getEngineInfo(String str) {
        isSdpSupported();
        return null;
    }

    public final int getInnerAuthUserId(int i) {
        if (isDualDarDoSupported()) {
            return this.mDualDarDoManagerImpl.getInnerAuthUserId(i);
        }
        return -10000;
    }

    public final int getMainUserId(int i) {
        if (!isDualDarDoSupported()) {
            return -10000;
        }
        DarDatabaseCache darDatabaseCache = this.mDualDarDoManagerImpl.mInjector.mDarDatabaseCache;
        darDatabaseCache.getClass();
        try {
            String internal = darDatabaseCache.getInternal(i, "ddar.inner.main.userid");
            if (internal != null) {
                return Integer.parseInt(internal);
            }
            return -10000;
        } catch (NumberFormatException unused) {
            return -10000;
        }
    }

    public final List getPackageListForDualDarPolicy(String str) {
        if (!isDualDarDoSupported()) {
            return null;
        }
        this.mDualDarDoManagerImpl.mInjector.getClass();
        Injector.enforceCallerKnoxCoreOrSelf("getPackageListForDualDarPolicy");
        try {
            IPackageManager packageManager = AppGlobals.getPackageManager();
            if (packageManager != null) {
                return packageManager.getPackageListForDualDarPolicy(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public final int getPasswordMinimumLengthForInner() {
        if (isDualDarDoSupported()) {
            return this.mDualDarDoManagerImpl.getPasswordMinimumLengthForInner();
        }
        return 0;
    }

    public final int getReservedUserIdForSystem() {
        if (isVirtualLockSupported()) {
            return this.mVirtualLockImpl.getReservedUserIdForSystem();
        }
        return -10000;
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0084, code lost:

       if (r2 == null) goto L37;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0090  */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v9, types: [java.io.FileInputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] getSpecificKeyViaProtector(java.lang.String r9, int r10) {
        /*
            Method dump skipped, instructions count: 219
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.knox.dar.DarManagerService.getSpecificKeyViaProtector(java.lang.String,"
                    + " int):byte[]");
    }

    public final double getSupportedSDKVersion() {
        isSdpSupported();
        return 0.0d;
    }

    public final UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = (UserManager) this.mContext.getSystemService("user");
        }
        return this.mUserManager;
    }

    public void handleUnlockSecureFolderWithToken(int i) {
        long j;
        if (SemPersonaManager.isSecureFolderId(i)) {
            checkSystemPermission();
            byte[] specificKeyViaProtector = getSpecificKeyViaProtector("SdpTokenHandle", i);
            if (specificKeyViaProtector != null) {
                ByteOrder byteOrder = BytesUtil.DEFAULT_BYTE_ORDER;
                ByteBuffer allocate = ByteBuffer.allocate(8);
                allocate.order(BytesUtil.DEFAULT_BYTE_ORDER);
                allocate.put(specificKeyViaProtector);
                allocate.flip();
                j = allocate.getLong();
            } else {
                Log.d("DarManagerService", "get SecureFolder Token Handle Failed");
                j = 0;
            }
            checkSystemPermission();
            byte[] specificKeyViaProtector2 = getSpecificKeyViaProtector("SdpResetToken", i);
            this.mInjector.getClass();
            LockSettingsInternal lockSettingsInternal =
                    (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
            if (lockSettingsInternal != null) {
                try {
                    lockSettingsInternal.unlockUserWithToken(j, specificKeyViaProtector2, i);
                } catch (Exception e) {
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                            e,
                            "Unexpected failure while unlock secure folder with token",
                            "DarManagerService");
                }
            }
            SecureUtil.clear(specificKeyViaProtector2);
            Locale locale = Locale.US;
            Log.d(
                    "DarManagerService",
                    "SecureFolder user "
                            + i
                            + " has been unlocked [ res : "
                            + getUserManager().isUserUnlocked(i)
                            + " ]");
        }
    }

    public final boolean isDarSupported() {
        return true;
    }

    public final boolean isDefaultPathUser(int i) {
        isSdpSupported();
        return false;
    }

    public final boolean isDeviceRootKeyInstalled() {
        long clearCallingIdentity;
        Injector injector;
        boolean z;
        if ("1".equals(SystemProperties.get("ro.hardware.virtual_device"))) {
            Log.d(
                    "DarManagerService",
                    "Will be Failed in device integrity check. But, running on VirtualDevice."
                        + " Continue .. ");
            return true;
        }
        boolean z2 = false;
        if (!SystemProperties.get("ro.security.keystore.keytype").contains("sakm")) {
            this.mInjector.getClass();
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    DeviceRootKeyServiceManager deviceRootKeyServiceManager =
                            new DeviceRootKeyServiceManager(this.mContext);
                    if (deviceRootKeyServiceManager.isAliveDeviceRootKeyService()) {
                        z2 = deviceRootKeyServiceManager.isExistDeviceRootKey(1);
                    } else {
                        Log.e("DarManagerService", "DRK service is not ready...");
                    }
                    injector = this.mInjector;
                } catch (Exception e) {
                    e.printStackTrace();
                    injector = this.mInjector;
                }
                injector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                SecureUtil.record(z2);
                return z2;
            } catch (Throwable th) {
                throw th;
            }
        }
        Log.d("DarManagerService", "Check SAK instead for JDM with GRDM or KnoxVault2");
        this.mInjector.getClass();
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z =
                        new AttestationUtils()
                                        .generateKeyPair(
                                                "KnoxTestKey",
                                                SecureUtil.sSecureRandom.generateSeed(8))
                                != null;
                if (z) {
                    try {
                        Log.d("DarManagerService", "Generated keypair is protected by SAK");
                        AttestationUtils.deleteKey();
                    } catch (Exception e2) {
                        e = e2;
                        z2 = z;
                        Log.e("DarManagerService", "Failed while check SAK : " + e.toString());
                        e.printStackTrace();
                        this.mInjector.getClass();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        z = z2;
                        SecureUtil.record(z);
                        return z;
                    }
                }
            } finally {
                this.mInjector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e3) {
            e = e3;
        }
        SecureUtil.record(z);
        return z;
    }

    public final boolean isDualDarDoSupported() {
        return this.mDualDarDoManagerImpl != null;
    }

    public final boolean isInnerAuthRequired(int i) {
        if (!isDualDarDoSupported()) {
            return false;
        }
        DualDarDoManagerImpl dualDarDoManagerImpl = this.mDualDarDoManagerImpl;
        dualDarDoManagerImpl.getClass();
        if (!DualDarManager.isOnDeviceOwner(i)) {
            return false;
        }
        Injector injector = dualDarDoManagerImpl.mInjector;
        EnterprisePartitionManager.getInstance(injector.mContext).getClass();
        boolean dualDARLockstate = EnterprisePartitionManager.getDualDARLockstate();
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "isInnerLayerLockedState - result : ", "DualDarManagerImpl", dualDARLockstate);
        if (!dualDARLockstate) {
            if (!dualDarDoManagerImpl.mHasTokenSetForInner) {
                return false;
            }
            if (!injector.mLockPatternUtils.hasPendingEscrowToken(
                    dualDarDoManagerImpl.getInnerAuthUserId(i))) {
                dualDarDoManagerImpl.mHasTokenSetForInner = false;
                return false;
            }
        }
        return true;
    }

    public final boolean isKnoxKeyInstallable() {
        Injector injector;
        AttestationUtils attestationUtils;
        byte[] generateSeed;
        if ("1".equals(SystemProperties.get("ro.hardware.virtual_device"))) {
            Log.d(
                    "DarManagerService",
                    "Will be Failed in device integrity check. But, running on VirtualDevice."
                        + " Continue - ");
            return true;
        }
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            try {
                attestationUtils = new AttestationUtils();
                generateSeed = SecureUtil.sSecureRandom.generateSeed(8);
            } catch (Exception e) {
                e.printStackTrace();
                injector = this.mInjector;
            }
            if (generateSeed == null) {
                throw new NullPointerException("challenge == null");
            }
            if (attestationUtils.generateKeyPair(
                                    new AttestParameterSpec(
                                            generateSeed,
                                            true,
                                            false,
                                            new KeyGenParameterSpec.Builder("KnoxTestKey", 4)
                                                    .setDigests(
                                                            "NONE", "SHA-1", "SHA-224", "SHA-256",
                                                            "SHA-384", "SHA-512")
                                                    .build()))
                            != null
                    && (z =
                            checkDeviceIntegrity(
                                    AttestationUtils.getCertificateChain("KnoxTestKey")))) {
                AttestationUtils.deleteKey();
            }
            injector = this.mInjector;
            injector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z;
        } catch (Throwable th) {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int isLicensed() {
        isSdpSupported();
        return -10;
    }

    public final boolean isResetPasswordTokenActive(int i) {
        if (!isVirtualLockSupported()) {
            return false;
        }
        VirtualLockImpl virtualLockImpl = this.mVirtualLockImpl;
        virtualLockImpl.getClass();
        if (!VirtualLockUtils.isVirtualUserId(i)) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return virtualLockImpl.mLockPatternUtils.isEscrowTokenActive(
                    virtualLockImpl.mDarDatabaseCache.getLong(i), i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isSDPEnabled(int i) {
        isSdpSupported();
        return false;
    }

    public final boolean isSdpSupported() {
        Log.e("DarManagerService_SDP", "SDP not supported");
        return false;
    }

    public final boolean isSdpSupportedSecureFolder(int i) {
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        UserManager userManager = this.mUserManager;
        UserInfo userInfo = userManager != null ? userManager.getUserInfo(i) : null;
        this.mInjector.getClass();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (userInfo == null) {
            userInfo = NULL_USER;
        }
        return (userInfo == null
                        || !userInfo.isSecureFolder()
                        || userInfo.isSdpNotSupportedSecureFolder())
                ? false
                : true;
    }

    public final boolean isSensitive(String str) {
        isSdpSupported();
        return false;
    }

    public final boolean isVirtualLockSupported() {
        return this.mVirtualLockImpl != null;
    }

    public final int lock(String str) {
        isSdpSupported();
        return -10;
    }

    public final int migrate(String str) {
        isSdpSupported();
        return -10;
    }

    public final void onBiometricsAuthenticated(int i) {
        isSdpSupported();
    }

    public final void onDeviceOwnerLocked(int i) {
        isSdpSupported();
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

    public final void registerClient(int i, ISdpListener iSdpListener) {
        isSdpSupported();
    }

    public final int registerListener(String str, ISdpListener iSdpListener) {
        isSdpSupported();
        return -10;
    }

    public final int removeEngine(String str) {
        isSdpSupported();
        return -10;
    }

    public final void reportApplicationBinding(
            final long j, final int i, final int i2, final String str, final String str2) {
        if (AppMonitor.get().isOn()) {
            if (Binder.getCallingPid() != i) {
                Log.e("DarManagerService", "reportApplicationBinding: pid is not matched");
            } else if (this.mActivityManagerInternal.getPackageNameByPid(i) == null) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                        i,
                        "reportApplicationBinding: Package is not valid with pid : ",
                        "DarManagerService");
            } else {
                BackgroundThread.getHandler()
                        .post(
                                new Runnable() { // from class:
                                                 // com.android.server.knox.dar.DarManagerService.2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AppMonitor.get()
                                                .reportApplicationBinding(j, i, i2, str, str2);
                                    }
                                });
            }
        }
    }

    public final int reserveUserIdForSystem() {
        if (!isVirtualLockSupported()) {
            return -10000;
        }
        VirtualLockImpl virtualLockImpl = this.mVirtualLockImpl;
        virtualLockImpl.mInjector.getClass();
        Injector.enforceCallerKnoxCoreOrSelf("reserveUserIdForSystem");
        int reservedUserIdForSystem = virtualLockImpl.getReservedUserIdForSystem();
        if (virtualLockImpl.getReservedUserIdForSystem() != -10000) {
            return reservedUserIdForSystem;
        }
        int availableUserId = virtualLockImpl.getAvailableUserId();
        DarDatabaseCache darDatabaseCache = virtualLockImpl.mDarDatabaseCache;
        darDatabaseCache.getClass();
        darDatabaseCache.putInternal(0, "vl.reserved.userid", String.valueOf(availableUserId));
        return availableUserId;
    }

    public final int resetPassword(String str, String str2, String str3) {
        isSdpSupported();
        return -10;
    }

    public final boolean resetPasswordWithToken(String str, final byte[] bArr, final int i) {
        boolean z = false;
        if (!isVirtualLockSupported()) {
            return false;
        }
        VirtualLockImpl virtualLockImpl = this.mVirtualLockImpl;
        virtualLockImpl.getClass();
        SDPLog.d("VirtualLockImpl", "Reset password with token for user " + i);
        if (VirtualLockUtils.isVirtualUserId(i)) {
            if (str == null) {
                str = "";
            }
            final long j = virtualLockImpl.mDarDatabaseCache.getLong(i);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                final LockscreenCredential createPin =
                        PasswordMetrics.isNumericOnly(str)
                                ? LockscreenCredential.createPin(str)
                                : LockscreenCredential.createPasswordOrNone(str);
                if (virtualLockImpl.mLockSettingsInternal == null) {
                    virtualLockImpl.mLockSettingsInternal =
                            (LockSettingsInternal)
                                    LocalServices.getService(LockSettingsInternal.class);
                }
                z =
                        ((Boolean)
                                        Optional.ofNullable(virtualLockImpl.mLockSettingsInternal)
                                                .map(
                                                        new Function() { // from class:
                                                                         // com.android.server.knox.dar.VirtualLockImpl$$ExternalSyntheticLambda0
                                                            @Override // java.util.function.Function
                                                            public final Object apply(Object obj) {
                                                                return Boolean.valueOf(
                                                                        ((LockSettingsInternal) obj)
                                                                                .setLockCredentialWithToken(
                                                                                        createPin,
                                                                                        j, bArr,
                                                                                        i));
                                                            }
                                                        })
                                                .orElse(Boolean.FALSE))
                                .booleanValue();
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return z;
    }

    public boolean saveResetTokenViaProtectorForLegacy(byte[] bArr, int i) {
        this.mKeyProtector.getClass();
        boolean protect = KeyProtector.protect(i, "SdpResetToken", bArr);
        SecureUtil.record(protect);
        return protect;
    }

    public boolean saveSpecificKeyViaProtector(byte[] bArr, String str, int i) {
        boolean z;
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!SecureUtil.isAnyoneEmptyHere(bArr, str)) {
                this.mKeyProtector.getClass();
                boolean protect = KeyProtector.protect(i, str, bArr);
                SecureUtil.record(protect);
                if (protect) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean saveTokenHandleViaProtectorForLegacy(long j, int i) {
        ByteOrder byteOrder = BytesUtil.DEFAULT_BYTE_ORDER;
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(BytesUtil.DEFAULT_BYTE_ORDER);
        allocate.putLong(j);
        byte[] array = allocate.array();
        this.mKeyProtector.getClass();
        boolean protect = KeyProtector.protect(i, "SdpTokenHandle", array);
        SecureUtil.record(protect);
        return protect;
    }

    public final int saveTokenIntoTrusted(String str, String str2) {
        isSdpSupported();
        return -10;
    }

    public final boolean setDualDarInfo(int i, int i2) {
        if (!isDualDarDoSupported()) {
            return false;
        }
        this.mDualDarDoManagerImpl.mInjector.getClass();
        Injector.enforceCallerKnoxCoreOrSelf("setDualDarInfo");
        if (((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)) != null) {
            return ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class))
                    .setDualDarInfo(i, i2);
        }
        return false;
    }

    public final void setInnerAuthUserId(int i, int i2) {
        if (isDualDarDoSupported()) {
            Injector injector = this.mDualDarDoManagerImpl.mInjector;
            injector.getClass();
            Injector.enforceCallerKnoxCoreOrSelf("setInnerAuthUserId");
            DarDatabaseCache darDatabaseCache = injector.mDarDatabaseCache;
            darDatabaseCache.getClass();
            darDatabaseCache.putInternal(i2, "ddar.inner.auth.userid", String.valueOf(i));
        }
    }

    public final void setMainUserId(int i, int i2) {
        if (isDualDarDoSupported()) {
            Injector injector = this.mDualDarDoManagerImpl.mInjector;
            injector.getClass();
            Injector.enforceCallerKnoxCoreOrSelf("setMainUserId");
            DarDatabaseCache darDatabaseCache = injector.mDarDatabaseCache;
            darDatabaseCache.getClass();
            darDatabaseCache.putInternal(i2, "ddar.inner.main.userid", String.valueOf(i));
        }
    }

    public final int setPassword(String str, String str2) {
        isSdpSupported();
        return -10;
    }

    public final boolean setResetPasswordToken(byte[] bArr, int i) {
        boolean z;
        if (!isVirtualLockSupported()) {
            return false;
        }
        VirtualLockImpl virtualLockImpl = this.mVirtualLockImpl;
        DarDatabaseCache darDatabaseCache = virtualLockImpl.mDarDatabaseCache;
        SDPLog.d("VirtualLockImpl", "Set reset password token for user " + i);
        if (!VirtualLockUtils.isVirtualUserId(i)) {
            z = false;
        } else {
            if (bArr == null || bArr.length < 32) {
                throw new IllegalArgumentException("token must be at least 32-byte long");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                virtualLockImpl.clearResetPasswordTokenInternal(i, darDatabaseCache.getLong(i));
                long addEscrowToken =
                        virtualLockImpl.mLockPatternUtils.addEscrowToken(
                                bArr, i, virtualLockImpl.mInjector.mEscrowTokenStateChangeCallback);
                darDatabaseCache.putInternal(
                        i, "vl.rst.token.handle", String.valueOf(addEscrowToken));
                z = addEscrowToken != 0;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (z && isDualDarDoSupported()) {
            DualDarDoManagerImpl dualDarDoManagerImpl = this.mDualDarDoManagerImpl;
            dualDarDoManagerImpl.getClass();
            if (DualDarManager.isOnDeviceOwnerEnabled()
                    && dualDarDoManagerImpl.getInnerAuthUserId(0) == i
                    && !dualDarDoManagerImpl.mHasTokenSetForInner) {
                dualDarDoManagerImpl.mHasTokenSetForInner = true;
                Binder.withCleanCallingIdentity(
                        new DualDarDoManagerImpl$$ExternalSyntheticLambda1(dualDarDoManagerImpl));
            }
        }
        return z;
    }

    public boolean setResetTokenForLegacy(byte[] bArr, int i) {
        Injector injector;
        SDPLog.i("Set reset token for Legacy user " + i);
        SDPLog.p(KnoxCustomManagerService.SPCM_KEY_TOKEN, bArr, "userId", Integer.valueOf(i));
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            try {
                long addEscrowToken =
                        this.mLockPatternUtils.addEscrowToken(
                                bArr, i, (LockPatternUtils.EscrowTokenStateChangeCallback) null);
                if (addEscrowToken != 0
                        && saveTokenHandleViaProtectorForLegacy(addEscrowToken, i)) {
                    z = true;
                }
                if (SecureUtil.isFailed(Boolean.valueOf(z))) {
                    this.mLockPatternUtils.removeEscrowToken(addEscrowToken, i);
                }
                injector = this.mInjector;
            } catch (Exception e) {
                e.printStackTrace();
                SDPLog.e(e, null, "Unexpected exception while set reset token for Legacy");
                injector = this.mInjector;
            }
            injector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            SDPLog.d(null, "Result of set reset token for Legacy : " + z);
            return z;
        } catch (Throwable th) {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean setSensitive(int i, String str) {
        isSdpSupported();
        return false;
    }

    public final int startMonitoring(
            int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener) {
        checkSystemPermission();
        EndpointMonitorImpl endpointMonitorImpl = this.mEndpointMonitorImpl;
        if (endpointMonitorImpl != null) {
            return endpointMonitorImpl.startMonitoring(i, i2, bundle, iEndpointMonitorListener);
        }
        return -6;
    }

    public final int startTracing(
            int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener) {
        checkSystemPermission();
        return startMonitoring(i, i2, bundle, iEndpointMonitorListener);
    }

    public final int stopMonitoring(int i, int i2) {
        checkSystemPermission();
        EndpointMonitorImpl endpointMonitorImpl = this.mEndpointMonitorImpl;
        if (endpointMonitorImpl != null) {
            return endpointMonitorImpl.stopMonitoring(i, i2);
        }
        return -6;
    }

    public final int stopTracing(int i, int i2) {
        checkSystemPermission();
        return stopMonitoring(i, i2);
    }

    public final void systemReady() {
        Log.i("DarManagerService", "systemReady for DarManagerService");
        HandlerThread handlerThread = new HandlerThread("DarManagerService", 10);
        handlerThread.start();
        this.mDarHandler = new DarHandler(handlerThread.getLooper());
        prepareSecuredDataKey(0);
        synchronized (DarManagerService.class) {
        }
    }

    public final int unlock(String str, String str2) {
        isSdpSupported();
        return -10;
    }

    public final int unlockViaTrusted(String str, String str2) {
        isSdpSupported();
        return -10;
    }

    public final void unregisterClient(int i, ISdpListener iSdpListener) {
        isSdpSupported();
    }

    public final int unregisterListener(String str, ISdpListener iSdpListener) {
        isSdpSupported();
        return -10;
    }
}
