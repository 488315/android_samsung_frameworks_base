package com.android.server.locksettings;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.security.keystore2.AndroidKeyStoreLoadStoreParameter;
import android.util.Slog;

import com.android.internal.widget.RebootEscrowListener;
import com.android.server.pm.UserManagerInternal;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RebootEscrowManager {
    public static final String REBOOT_ESCROW_ARMED_KEY = "reboot_escrow_armed_count";
    public final Callbacks mCallbacks;
    public final Handler mHandler;
    public final Injector mInjector;
    public final RebootEscrowKeyStoreManager mKeyStoreManager;
    public AnonymousClass1 mNetworkCallback;
    public RebootEscrowKey mPendingRebootEscrowKey;
    public RebootEscrowListener mRebootEscrowListener;
    public boolean mRebootEscrowReady;
    public boolean mRebootEscrowWanted;
    public final LockSettingsStorage mStorage;
    public final UserManager mUserManager;
    public PowerManager.WakeLock mWakeLock;
    public int mLoadEscrowDataErrorCode = 0;
    public boolean mRebootEscrowTimedOut = false;
    public boolean mLoadEscrowDataWithRetry = false;
    public final Object mKeyGenerationLock = new Object();
    public final RebootEscrowEventLog mEventLog = new RebootEscrowEventLog();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Callbacks {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public final Context mContext;
        public final RebootEscrowKeyStoreManager mKeyStoreManager =
                new RebootEscrowKeyStoreManager();
        public RebootEscrowProviderInterface mRebootEscrowProvider;
        public final LockSettingsStorage mStorage;
        public final UserManagerInternal mUserManagerInternal;

        public Injector(
                Context context,
                LockSettingsStorage lockSettingsStorage,
                UserManagerInternal userManagerInternal) {
            this.mContext = context;
            this.mStorage = lockSettingsStorage;
            this.mUserManagerInternal = userManagerInternal;
        }

        public final RebootEscrowProviderInterface createRebootEscrowProviderIfNeeded() {
            RebootEscrowProviderInterface rebootEscrowProviderHalImpl;
            int i;
            ServiceInfo serviceInfo;
            if (this.mRebootEscrowProvider == null) {
                if (serverBasedResumeOnReboot()) {
                    Slog.i("RebootEscrowManager", "Using server based resume on reboot");
                    Context context = this.mContext;
                    RebootEscrowProviderServerBasedImpl.Injector injector =
                            new RebootEscrowProviderServerBasedImpl.Injector();
                    injector.mServiceConnection = null;
                    ResumeOnRebootServiceProvider resumeOnRebootServiceProvider =
                            new ResumeOnRebootServiceProvider(context, context.getPackageManager());
                    Intent intent = new Intent();
                    intent.setAction("android.service.resumeonreboot.ResumeOnRebootService");
                    String str =
                            SystemProperties.get(
                                    "persist.sys.resume_on_reboot_provider_package", "");
                    if (str.isEmpty()) {
                        String str2 = ResumeOnRebootServiceProvider.PROVIDER_PACKAGE;
                        if (str2 != null && !str2.equals("")) {
                            intent.setPackage(str2);
                        }
                        i = 1048580;
                    } else {
                        Slog.i("ResumeOnRebootServiceProvider", "Using test app: ".concat(str));
                        intent.setPackage(str);
                        i = 4;
                    }
                    Iterator<ResolveInfo> it =
                            resumeOnRebootServiceProvider
                                    .mPackageManager
                                    .queryIntentServices(intent, i)
                                    .iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            serviceInfo = null;
                            break;
                        }
                        ResolveInfo next = it.next();
                        ServiceInfo serviceInfo2 = next.serviceInfo;
                        if (serviceInfo2 != null
                                && "android.permission.BIND_RESUME_ON_REBOOT_SERVICE"
                                        .equals(serviceInfo2.permission)) {
                            serviceInfo = next.serviceInfo;
                            break;
                        }
                    }
                    ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection
                            resumeOnRebootServiceConnection =
                                    serviceInfo == null
                                            ? null
                                            : new ResumeOnRebootServiceProvider
                                                    .ResumeOnRebootServiceConnection(
                                                    resumeOnRebootServiceProvider.mContext,
                                                    serviceInfo.getComponentName());
                    injector.mServiceConnection = resumeOnRebootServiceConnection;
                    if (resumeOnRebootServiceConnection == null) {
                        Slog.e(
                                "RebootEscrowProviderServerBased",
                                "Failed to resolve resume on reboot server service.");
                    }
                    rebootEscrowProviderHalImpl =
                            new RebootEscrowProviderServerBasedImpl(this.mStorage, injector);
                } else {
                    Slog.i("RebootEscrowManager", "Using HAL based resume on reboot");
                    rebootEscrowProviderHalImpl = new RebootEscrowProviderHalImpl();
                }
                this.mRebootEscrowProvider =
                        rebootEscrowProviderHalImpl.hasRebootEscrowSupport()
                                ? rebootEscrowProviderHalImpl
                                : null;
            }
            return this.mRebootEscrowProvider;
        }

        public int getLoadEscrowTimeoutMillis() {
            return 180000;
        }

        public int getWakeLockTimeoutMillis() {
            return getLoadEscrowTimeoutMillis() + 5000;
        }

        public final boolean serverBasedResumeOnReboot() {
            if (this.mContext
                    .getPackageManager()
                    .hasSystemFeature("android.hardware.reboot_escrow")) {
                return DeviceConfig.getBoolean("ota", "server_based_ror_enabled", false);
            }
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class RebootEscrowEvent {
        public final int mEventId;
        public final Integer mUserId;
        public final long mTimestamp = SystemClock.uptimeMillis();
        public final long mWallTime = System.currentTimeMillis();

        public RebootEscrowEvent(int i, Integer num) {
            this.mEventId = i;
            this.mUserId = num;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class RebootEscrowEventLog {
        public final RebootEscrowEvent[] mEntries = new RebootEscrowEvent[16];
        public int mNextIndex = 0;
    }

    public RebootEscrowManager(
            Injector injector,
            Callbacks callbacks,
            LockSettingsStorage lockSettingsStorage,
            Handler handler) {
        this.mInjector = injector;
        this.mCallbacks = callbacks;
        this.mStorage = lockSettingsStorage;
        this.mUserManager = (UserManager) injector.mContext.getSystemService("user");
        this.mKeyStoreManager = injector.mKeyStoreManager;
        this.mHandler = handler;
    }

    public final void clearMetricsStorage() {
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.removeKey(REBOOT_ESCROW_ARMED_KEY, 0);
        lockSettingsStorage.removeKey("reboot_escrow_key_stored_timestamp", 0);
        lockSettingsStorage.removeKey("reboot_escrow_key_vbmeta_digest", 0);
        lockSettingsStorage.removeKey("reboot_escrow_key_other_vbmeta_digest", 0);
        lockSettingsStorage.removeKey("reboot_escrow_key_provider", 0);
    }

    public final void clearRebootEscrowIfNeeded() {
        this.mRebootEscrowWanted = false;
        setRebootEscrowReady(false);
        Injector injector = this.mInjector;
        RebootEscrowProviderInterface createRebootEscrowProviderIfNeeded =
                injector.createRebootEscrowProviderIfNeeded();
        if (createRebootEscrowProviderIfNeeded == null) {
            Slog.w("RebootEscrowManager", "RebootEscrowProvider is unavailable for clear request");
        } else {
            createRebootEscrowProviderIfNeeded.clearRebootEscrowKey();
        }
        injector.mRebootEscrowProvider = null;
        clearMetricsStorage();
        Iterator it = this.mUserManager.getUsers().iterator();
        while (it.hasNext()) {
            int i = ((UserInfo) it.next()).id;
            LockSettingsStorage lockSettingsStorage = this.mStorage;
            lockSettingsStorage.deleteFile(lockSettingsStorage.getRebootEscrowFile(i));
        }
        RebootEscrowEventLog rebootEscrowEventLog = this.mEventLog;
        rebootEscrowEventLog.getClass();
        RebootEscrowEvent rebootEscrowEvent = new RebootEscrowEvent(3, null);
        int i2 = rebootEscrowEventLog.mNextIndex;
        RebootEscrowEvent[] rebootEscrowEventArr = rebootEscrowEventLog.mEntries;
        rebootEscrowEventArr[i2] = rebootEscrowEvent;
        rebootEscrowEventLog.mNextIndex = (i2 + 1) % rebootEscrowEventArr.length;
    }

    public final void compareAndSetLoadEscrowDataErrorCode(Handler handler, int i, int i2) {
        if (i == this.mLoadEscrowDataErrorCode) {
            setLoadEscrowDataErrorCode(i2, handler);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadRebootEscrowDataWithRetry(
            final android.os.Handler r17,
            int r18,
            final java.util.List r19,
            final java.util.List r20) {
        /*
            Method dump skipped, instructions count: 482
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.locksettings.RebootEscrowManager.loadRebootEscrowDataWithRetry(android.os.Handler,"
                    + " int, java.util.List, java.util.List):void");
    }

    public final void onEscrowRestoreComplete(boolean z, int i, Handler handler) {
        ConnectivityManager connectivityManager;
        int i2 = this.mStorage.getInt(-1, 0, REBOOT_ESCROW_ARMED_KEY);
        int i3 =
                Settings.Global.getInt(
                                this.mInjector.mContext.getContentResolver(), "boot_count", 0)
                        - i2;
        if (z || (i2 != -1 && i3 <= 5)) {
            reportMetricOnRestoreComplete(z, i, handler);
        }
        synchronized (this.mKeyStoreManager.mKeyStoreLock) {
            try {
                KeyStore keyStore = KeyStore.getInstance("AndroidKeystore");
                keyStore.load(new AndroidKeyStoreLoadStoreParameter(120));
                keyStore.deleteEntry("reboot_escrow_key_store_encryption_key");
            } catch (IOException | GeneralSecurityException e) {
                Slog.e(
                        "RebootEscrowKeyStoreManager",
                        "Unable to delete encryption key in keystore.",
                        e);
            }
        }
        this.mInjector.mRebootEscrowProvider = null;
        clearMetricsStorage();
        AnonymousClass1 anonymousClass1 = this.mNetworkCallback;
        if (anonymousClass1 != null
                && (connectivityManager =
                                (ConnectivityManager)
                                        this.mInjector.mContext.getSystemService(
                                                ConnectivityManager.class))
                        != null) {
            connectivityManager.unregisterNetworkCallback(anonymousClass1);
        }
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

    public final void onGetRebootEscrowKeyFailed(List list, int i, Handler handler) {
        Slog.w(
                "RebootEscrowManager",
                "Had reboot escrow data for users, but no key; removing escrow storage.");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int i2 = ((UserInfo) it.next()).id;
            LockSettingsStorage lockSettingsStorage = this.mStorage;
            lockSettingsStorage.deleteFile(lockSettingsStorage.getRebootEscrowFile(i2));
        }
        onEscrowRestoreComplete(false, i, handler);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x005b, code lost:

       if (r0.equals(r4) != false) goto L21;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reportMetricOnRestoreComplete(boolean r13, int r14, android.os.Handler r15) {
        /*
            r12 = this;
            com.android.server.locksettings.RebootEscrowManager$Injector r0 = r12.mInjector
            boolean r0 = r0.serverBasedResumeOnReboot()
            r1 = 2
            r2 = 1
            if (r0 == 0) goto Lc
            r6 = r1
            goto Ld
        Lc:
            r6 = r2
        Ld:
            r0 = 0
            com.android.server.locksettings.LockSettingsStorage r3 = r12.mStorage
            java.lang.String r4 = "reboot_escrow_key_stored_timestamp"
            r11 = 0
            java.lang.String r0 = r3.getString(r4, r0, r11)
            boolean r4 = android.text.TextUtils.isEmpty(r0)
            r7 = -1
            if (r4 == 0) goto L22
            r4 = r7
            goto L26
        L22:
            long r4 = java.lang.Long.parseLong(r0)
        L26:
            long r9 = java.lang.System.currentTimeMillis()
            int r0 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r0 == 0) goto L38
            int r0 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r0 <= 0) goto L38
            long r9 = r9 - r4
            int r0 = (int) r9
            int r0 = r0 / 1000
        L36:
            r8 = r0
            goto L3a
        L38:
            r0 = -1
            goto L36
        L3a:
            java.lang.String r0 = "ro.boot.vbmeta.digest"
            java.lang.String r0 = android.os.SystemProperties.get(r0)
            java.lang.String r4 = "reboot_escrow_key_vbmeta_digest"
            java.lang.String r5 = ""
            java.lang.String r4 = r3.getString(r4, r5, r11)
            java.lang.String r7 = "reboot_escrow_key_other_vbmeta_digest"
            java.lang.String r3 = r3.getString(r7, r5, r11)
            boolean r5 = r3.isEmpty()
            if (r5 == 0) goto L61
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L5f
        L5d:
            r9 = r11
            goto L6f
        L5f:
            r9 = r1
            goto L6f
        L61:
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L68
            goto L5d
        L68:
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L5f
            r9 = r2
        L6f:
            if (r13 != 0) goto L74
            r12.compareAndSetLoadEscrowDataErrorCode(r15, r11, r2)
        L74:
            java.lang.String r0 = "Reporting RoR recovery metrics, success: "
            java.lang.String r1 = ", service type: "
            java.lang.String r2 = ", error code: "
            java.lang.StringBuilder r0 = com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0.m(r6, r0, r1, r2, r13)
            int r1 = r12.mLoadEscrowDataErrorCode
            java.lang.String r2 = "RebootEscrowManager"
            com.android.server.SystemServiceManager$$ExternalSyntheticOutline0.m(r0, r1, r2)
            int r5 = r12.mLoadEscrowDataErrorCode
            r3 = 238(0xee, float:3.34E-43)
            r10 = -1
            r4 = r13
            r7 = r14
            com.android.internal.util.FrameworkStatsLog.write(r3, r4, r5, r6, r7, r8, r9, r10)
            r12.setLoadEscrowDataErrorCode(r11, r15)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.locksettings.RebootEscrowManager.reportMetricOnRestoreComplete(boolean,"
                    + " int, android.os.Handler):void");
    }

    public final void setLoadEscrowDataErrorCode(final int i, Handler handler) {
        this.mInjector.getClass();
        if (DeviceConfig.getBoolean("ota", "wait_for_internet_ror", false)) {
            handler.post(
                    new Runnable() { // from class:
                                     // com.android.server.locksettings.RebootEscrowManager$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            RebootEscrowManager.this.mLoadEscrowDataErrorCode = i;
                        }
                    });
        } else {
            this.mLoadEscrowDataErrorCode = i;
        }
    }

    public final void setRebootEscrowReady(final boolean z) {
        if (this.mRebootEscrowReady != z) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.locksettings.RebootEscrowManager$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            RebootEscrowManager rebootEscrowManager = RebootEscrowManager.this;
                            rebootEscrowManager.mRebootEscrowListener.onPreparedForReboot(z);
                        }
                    });
        }
        this.mRebootEscrowReady = z;
    }
}
