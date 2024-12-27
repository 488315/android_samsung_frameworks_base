package com.android.server.knox.dar.ddar.nativedaemon;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.DarUtil;
import com.android.server.knox.dar.EnterprisePartitionManager;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.knox.dar.ddar.DualDarDoPolicyChecker;
import com.android.server.om.SemSamsungThemeUtils;
import com.android.server.pm.Installer;
import com.android.server.pm.PersonaServiceHelper;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;
import com.samsung.android.knox.dar.ddar.securesession.Wiper;
import com.samsung.android.knox.ddar.Secret;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DualDARDaemonProxy extends IProxyAgentService implements INativeDaemonConnectorCallbacks {
    public static Context mContext;
    public static DualDARDaemonProxy mInstance;
    public DualDarDaemonConnector mConnector;
    public Thread mConnectorThread = null;
    public NativeDaemonEvent mEvent = null;
    public ActivityManager mAm = null;
    public final Object mLock = new Object();
    public boolean isDaemonConnectionFailed = false;
    public Handler mHandler = null;
    public final List mBlockedClearablePackages = new ArrayList();
    public final List mDualDARDOPolicyPackages = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ClearUserDataObserver extends IPackageDataObserver.Stub {
        public boolean finished;
        public String packageName;
        public boolean success;

        public final void onRemoveCompleted(String str, boolean z) {
            synchronized (this) {
                this.finished = true;
                this.packageName = str;
                this.success = z;
                notifyAll();
            }
        }
    }

    public DualDARDaemonProxy(Context context) {
        DDLog.d("DualDARDaemonProxy", "DualDARDaemonProxy() called", new Object[0]);
        mContext = context;
        if (PersonaServiceHelper.isDualDAREnabled()) {
            startConnectorThread();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00cc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00bc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void fetchDumpStateInfo(com.samsung.android.knox.ddar.FileInfo r7) {
        /*
            Method dump skipped, instructions count: 219
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.ddar.nativedaemon.DualDARDaemonProxy.fetchDumpStateInfo(com.samsung.android.knox.ddar.FileInfo):void");
    }

    public static synchronized DualDARDaemonProxy getInstance(Context context) {
        DualDARDaemonProxy dualDARDaemonProxy;
        synchronized (DualDARDaemonProxy.class) {
            try {
                if (mInstance == null) {
                    mInstance = new DualDARDaemonProxy(context);
                }
                dualDARDaemonProxy = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return dualDARDaemonProxy;
    }

    public static boolean setDualDARPOPolicy(int i, int i2) {
        boolean z;
        EnterprisePartitionManager enterprisePartitionManager = EnterprisePartitionManager.getInstance(mContext);
        enterprisePartitionManager.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            StorageManager storageManager = (StorageManager) enterprisePartitionManager.mContext.getSystemService(StorageManager.class);
            if (storageManager != null) {
                z = storageManager.setDualDARPolicy(i, i2);
            } else {
                Log.e("EnterprisePartitionManager", "StorageManager instance is NULL");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = false;
            }
            if (z) {
                return true;
            }
            DDLog.e("DualDARDaemonProxy", "setDualDARPolicy failed!", new Object[0]);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean setEviction(int i, boolean z) {
        boolean z2;
        EnterprisePartitionManager.getInstance(mContext).getClass();
        StringBuilder sb = new StringBuilder("setEviction(user:");
        sb.append(i);
        sb.append(" evict:");
        sb.append(z);
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, ")", "EnterprisePartitionManager");
        synchronized (EnterprisePartitionManager.mInstallLock) {
            try {
                Installer installer = EnterprisePartitionManager.mPackageTasker;
                installer.getClass();
                try {
                    z2 = installer.mInstalld.setEviction(i, z);
                } catch (Exception e) {
                    Installer.InstallerException.from(e);
                    throw null;
                }
            } catch (Installer.InstallerException unused) {
                z2 = false;
            }
        }
        if (z2) {
            return true;
        }
        DDLog.e("DualDARDaemonProxy", "setEviction failed!", new Object[0]);
        return false;
    }

    public static void setSystemPropertyBoolean(boolean z) {
        if (TextUtils.isEmpty("persist.sys.knox.dualdard")) {
            DDLog.e("DualDARDaemonProxy", "Invalid property", new Object[0]);
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemProperties.set("persist.sys.knox.dualdard", Boolean.toString(z));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean clearApplicationUserDataForPackages(int i, List list) {
        if (i != 0 || !SemPersonaManager.isDarDualEncryptionEnabled(i)) {
            DDLog.d("DualDARDaemonProxy", "clearApplicationUserDataForPackages failed! : (reason) DualDAR at DO user", new Object[0]);
            return false;
        }
        if (((ArrayList) this.mDualDARDOPolicyPackages).isEmpty()) {
            DDLog.d("DualDARDaemonProxy", "clearApplicationUserDataForPackages - there is no package to clear", new Object[0]);
            return true;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Iterator it = ((ArrayList) list).iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    if (clearUserData(str)) {
                        DDLog.d("DualDARDaemonProxy", "clearApplicationUserData success! : " + str, new Object[0]);
                    } else {
                        DDLog.e("DualDARDaemonProxy", "clearApplicationUserData failed! : " + str, new Object[0]);
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                DDLog.d("DualDARDaemonProxy", "clear app user data for initialize DualDAR at DO. success", new Object[0]);
                return true;
            } catch (Exception e) {
                DDLog.e("DualDARDaemonProxy", "clearApplicationUserDataForPackages exception", new Object[0]);
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean clearSecret(int i) {
        DualDarDaemonConnector dualDarDaemonConnector = this.mConnector;
        if (dualDarDaemonConnector == null) {
            DDLog.e("DualDARDaemonProxy", "clearSecret failed! Error: native interface not yet connected failed", new Object[0]);
            return false;
        }
        try {
            NativeDaemonEvent executeSync = dualDarDaemonConnector.executeSync("key", "evict", Integer.valueOf(i), "all");
            this.mEvent = executeSync;
            if (!executeSync.isClassOk()) {
                if (!this.mEvent.isClassContinue()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean clearUserData(String str) {
        boolean z;
        if (str != null) {
            IPackageDataObserver clearUserDataObserver = new ClearUserDataObserver();
            clearUserDataObserver.finished = false;
            clearUserDataObserver.success = false;
            try {
                if (this.mAm == null) {
                    this.mAm = (ActivityManager) mContext.getSystemService("activity");
                }
                DDLog.d("DualDARDaemonProxy", str.concat(" try to clear application User Data"), new Object[0]);
                if (this.mAm.clearApplicationUserData(str, clearUserDataObserver)) {
                    synchronized (clearUserDataObserver) {
                        while (!clearUserDataObserver.finished) {
                            try {
                                clearUserDataObserver.wait(100L);
                            } catch (InterruptedException e) {
                                DDLog.e("DualDARDaemonProxy", "InterruptedException occur", new Object[0]);
                                e.printStackTrace();
                            }
                        }
                        z = clearUserDataObserver.success && clearUserDataObserver.packageName.equalsIgnoreCase(str);
                    }
                    return z;
                }
                DDLog.e("DualDARDaemonProxy", "Couldn't clear application user data for package: ".concat(str), new Object[0]);
            } catch (Exception e2) {
                DDLog.e("DualDARDaemonProxy", e2.getMessage(), new Object[0]);
            }
        }
        return false;
    }

    public final String getClientLibraryVersion(String str) {
        DualDarDaemonConnector dualDarDaemonConnector = this.mConnector;
        if (dualDarDaemonConnector == null) {
            DDLog.e("DualDARDaemonProxy", "startClientLibrary failed! Error: native interface not yet connected failed", new Object[0]);
            return "";
        }
        try {
            NativeDaemonEvent executeSync = dualDarDaemonConnector.executeSync("vendor_lib", "version", 0, str);
            DDLog.d("DualDARDaemonProxy", "getClientLibraryVersion() got response from executeSync", new Object[0]);
            int i = executeSync.mResponseCode;
            String str2 = executeSync.mMessage;
            if (i >= 0) {
                return str2;
            }
            DDLog.e("DualDARDaemonProxy", "startClientLibrary failed! Error code: " + executeSync.mCode + " message: " + str2, new Object[0]);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public final boolean loadClientLibrary(int i, String str) {
        DualDarDaemonConnector dualDarDaemonConnector = this.mConnector;
        if (dualDarDaemonConnector == null) {
            DDLog.e("DualDARDaemonProxy", "loadClientLibrary failed! Error: native interface not yet connected failed", new Object[0]);
            return false;
        }
        try {
            NativeDaemonEvent executeSync = dualDarDaemonConnector.executeSync("vendor_lib", "load", Integer.valueOf(i), str);
            this.mEvent = executeSync;
            if (!executeSync.isClassOk()) {
                if (!this.mEvent.isClassContinue()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0211, code lost:
    
        return r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle onMessage(int r11, java.lang.String r12, android.os.Bundle r13) {
        /*
            Method dump skipped, instructions count: 626
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.ddar.nativedaemon.DualDARDaemonProxy.onMessage(int, java.lang.String, android.os.Bundle):android.os.Bundle");
    }

    public final boolean pushSecret(int i, String str, List list) {
        if (this.mConnector == null) {
            DDLog.e("DualDARDaemonProxy", "pushSecret failed! Error: native interface not yet connected failed", new Object[0]);
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Secret secret = (Secret) it.next();
            try {
                byte[] decryptMessageFrom = ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(str, secret.data);
                if (decryptMessageFrom == null) {
                    DDLog.e("DualDARDaemonProxy", "pushSecret failed ! decData is null", new Object[0]);
                    return false;
                }
                StringBuilder sb = new StringBuilder(decryptMessageFrom.length * 2);
                for (byte b : decryptMessageFrom) {
                    sb.append(String.format("%02x", Byte.valueOf(b)));
                }
                String sb2 = sb.toString();
                sb.delete(0, sb.length());
                this.mEvent = this.mConnector.executeSync("key", "install", Integer.valueOf(i), secret.alias, sb2);
                Wiper.wipe(decryptMessageFrom);
                if (!this.mEvent.isClassOk()) {
                    DDLog.e("DualDARDaemonProxy", "pushSecret failed !", new Object[0]);
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void setDualDARPolicyForManagedDevice(Bundle bundle, Bundle bundle2) {
        int i;
        ApplicationInfo applicationInfo;
        String str;
        NativeDaemonEvent nativeDaemonEvent;
        int i2 = bundle.getInt("user_id");
        int i3 = bundle.getInt("CRYPTO_TYPE");
        boolean z = false;
        DDLog.d("DualDARDaemonProxy", ArrayUtils$$ExternalSyntheticOutline0.m(i2, i3, "setDualDARDOPolicy for user ", " type "), new Object[0]);
        String str2 = "/data/media/" + i2;
        EnterprisePartitionManager.getInstance(mContext).getClass();
        if (EnterprisePartitionManager.setDualDARPolicyDirRecursively(i2, i3, str2)) {
            EnterprisePartitionManager enterprisePartitionManager = EnterprisePartitionManager.getInstance(mContext);
            String m = VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "/data/user/");
            String str3 = "/data/user_de/" + i2;
            enterprisePartitionManager.getClass();
            if (EnterprisePartitionManager.setDualDARPolicyDir(i2, i3, m)) {
                if (!EnterprisePartitionManager.setDualDARPolicyDir(i2, 9, str3)) {
                    DDLog.e("DualDARDaemonProxy", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("setDualDARPolicyDir user failed! : ", str3), new Object[0]);
                }
                DarManagerService darManagerService = (DarManagerService) ServiceManager.getService("dar");
                if (darManagerService != null) {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        List<String> packageListForDualDarPolicy = darManagerService.getPackageListForDualDarPolicy("pkg_clearable_system");
                        List<String> packageListForDualDarPolicy2 = darManagerService.getPackageListForDualDarPolicy("pkg_not_system");
                        List<String> packageListForDualDarPolicy3 = darManagerService.getPackageListForDualDarPolicy("pkg_not_clearable_system");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        DarManagerService darManagerService2 = (DarManagerService) ServiceManager.getService("dar");
                        List blockedClearablePackages = darManagerService2 != null ? darManagerService2.getBlockedClearablePackages(i2) : null;
                        if (blockedClearablePackages != null) {
                            ((ArrayList) this.mBlockedClearablePackages).clear();
                            ((ArrayList) this.mBlockedClearablePackages).addAll(blockedClearablePackages);
                        }
                        List list = this.mBlockedClearablePackages;
                        if (list != null) {
                            Iterator it = ((ArrayList) list).iterator();
                            while (it.hasNext()) {
                                DDLog.d("DualDARDaemonProxy", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("refreshNonClearablePackagesList = ", (String) it.next()), new Object[0]);
                            }
                        }
                        if (packageListForDualDarPolicy == null || packageListForDualDarPolicy.isEmpty()) {
                            DDLog.e("DualDARDaemonProxy", "Clearable system package list is Empty.", new Object[0]);
                        } else {
                            for (String str4 : packageListForDualDarPolicy) {
                                if (((ArrayList) this.mBlockedClearablePackages).isEmpty() || !((ArrayList) this.mBlockedClearablePackages).contains(str4)) {
                                    arrayList2.add(str4);
                                } else {
                                    DDLog.d("DualDARDaemonProxy", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("1. Skip ddar policy for the pkg ", str4), new Object[0]);
                                    arrayList.add(str4);
                                }
                            }
                        }
                        if (packageListForDualDarPolicy2 == null || packageListForDualDarPolicy2.isEmpty()) {
                            DDLog.e("DualDARDaemonProxy", "Not system package list is Empty.", new Object[0]);
                        } else {
                            for (String str5 : packageListForDualDarPolicy2) {
                                if (((ArrayList) this.mBlockedClearablePackages).isEmpty() || !((ArrayList) this.mBlockedClearablePackages).contains(str5)) {
                                    arrayList2.add(str5);
                                } else {
                                    DDLog.e("DualDARDaemonProxy", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("2. Skip ddar policy for the pkg ", str5), new Object[0]);
                                    arrayList.add(str5);
                                }
                            }
                        }
                        if (packageListForDualDarPolicy3 == null || packageListForDualDarPolicy3.isEmpty()) {
                            DDLog.e("DualDARDaemonProxy", "Not clearable system package list is Empty.", new Object[0]);
                        } else {
                            for (String str6 : packageListForDualDarPolicy3) {
                                DDLog.e("DualDARDaemonProxy", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("3. Skip ddar policy for the pkg ", str6), new Object[0]);
                                arrayList.add(str6);
                            }
                        }
                        EnterprisePartitionManager enterprisePartitionManager2 = EnterprisePartitionManager.getInstance(mContext);
                        Iterator it2 = arrayList2.iterator();
                        while (it2.hasNext()) {
                            String str7 = (String) it2.next();
                            String m2 = AccessibilityManagerService$$ExternalSyntheticOutline0.m(i2, "/data/user/", "/", str7);
                            String str8 = "/data/user_de/" + i2 + "/" + str7;
                            enterprisePartitionManager2.getClass();
                            if (!EnterprisePartitionManager.setDualDARPolicyDirRecursively(i2, i3, m2)) {
                                PackageManager packageManager = mContext.getPackageManager();
                                if (packageManager != null) {
                                    try {
                                        PackageInfo packageInfo = packageManager.getPackageInfo(str7, 0);
                                        List list2 = SemSamsungThemeUtils.disableOverlayList;
                                        if (packageInfo != null && (applicationInfo = packageInfo.applicationInfo) != null && (str = applicationInfo.sourceDir) != null && str.startsWith("/data/overlays/current_locale_apks/files") && "zipped-overlay".equals(packageInfo.overlayCategory)) {
                                            DDLog.e("DualDARDaemonProxy", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("setDualDARPolicyDirRecursively failure exceptional! : ", m2), new Object[0]);
                                            arrayList.add(str7);
                                        }
                                    } catch (PackageManager.NameNotFoundException e) {
                                        i = 0;
                                        DDLog.e("DualDARDaemonProxy", e.toString(), new Object[0]);
                                    }
                                }
                                i = 0;
                                DDLog.e("DualDARDaemonProxy", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("setDualDARPolicyDirRecursively failed! : ", m2), new Object[i]);
                                DDLog.e("DualDARDaemonProxy", "setDualDARPolicyForPackages failed!", new Object[i]);
                                z = i;
                                break;
                            }
                            if (!EnterprisePartitionManager.setDualDARPolicyDirRecursively(i2, 9, str8)) {
                                DDLog.e("DualDARDaemonProxy", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("setDualDARPolicyDirRecursively failed! : ", str8), new Object[0]);
                            }
                            ((ArrayList) this.mDualDARDOPolicyPackages).add(str7);
                        }
                        DualDarDoPolicyChecker dualDarDoPolicyChecker = DualDarDoPolicyChecker.getInstance(mContext);
                        dualDarDoPolicyChecker.getClass();
                        Iterator it3 = arrayList.iterator();
                        while (it3.hasNext()) {
                            ((ArrayList) dualDarDoPolicyChecker.skippedPackages).add((String) it3.next());
                        }
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                DDLog.d("DualDARDaemonProxy", "setDualDARDOPolicy res : true", new Object[0]);
                z = true;
            } else {
                DDLog.e("DualDARDaemonProxy", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("setDualDARPolicyDir user failed! : ", m), new Object[0]);
                DDLog.e("DualDARDaemonProxy", "Failed to set the policy to data package folder...", new Object[0]);
            }
        } else {
            DDLog.e("DualDARDaemonProxy", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("setDualDARPolicyDirRecursively failed! : ", str2), new Object[0]);
            DDLog.e("DualDARDaemonProxy", "Failed to set the policy to shared folders...", new Object[0]);
        }
        bundle2.putBoolean("dual_dar_response", z);
        if (z || (nativeDaemonEvent = this.mEvent) == null) {
            return;
        }
        bundle2.putString("dual_dar_response_message", nativeDaemonEvent.mMessage);
    }

    public final boolean startClientLibrary(int i) {
        DualDarDaemonConnector dualDarDaemonConnector = this.mConnector;
        if (dualDarDaemonConnector == null) {
            DDLog.e("DualDARDaemonProxy", "startClientLibrary failed! Error: native interface not yet connected failed", new Object[0]);
            return false;
        }
        try {
            NativeDaemonEvent executeSync = dualDarDaemonConnector.executeSync("vendor_lib", "start", Integer.valueOf(i), 16);
            this.mEvent = executeSync;
            if (!executeSync.isClassOk()) {
                if (!this.mEvent.isClassContinue()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void startConnectorThread() {
        DDLog.d("DualDARDaemonProxy", "startConnectorThread() ", new Object[0]);
        Looper.getMainLooper();
        this.mConnector = new DualDarDaemonConnector(this);
        Thread thread = new Thread(this.mConnector, "DualDARDaemonProxy");
        this.mConnectorThread = thread;
        thread.start();
    }

    public final void startDualDARDaemon(Bundle bundle) {
        try {
            synchronized (this.mLock) {
                try {
                    if (!DarUtil.isDaemonRunning()) {
                        DDLog.i("DualDARDaemonProxy", "start newly dualdard daemon ! ", new Object[0]);
                        setSystemPropertyBoolean(true);
                    }
                    startConnectorThread();
                    Handler handler = new Handler(Looper.getMainLooper());
                    this.mHandler = handler;
                    handler.postDelayed(new Runnable() { // from class: com.android.server.knox.dar.ddar.nativedaemon.DualDARDaemonProxy.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            synchronized (DualDARDaemonProxy.this.mLock) {
                                DDLog.e("DualDARDaemonProxy", "coudn't connect with daemon!", new Object[0]);
                                DualDARDaemonProxy dualDARDaemonProxy = DualDARDaemonProxy.this;
                                dualDARDaemonProxy.isDaemonConnectionFailed = true;
                                dualDARDaemonProxy.mLock.notify();
                            }
                        }
                    }, 10000L);
                    this.mLock.wait();
                    if (this.isDaemonConnectionFailed) {
                        DDLog.e("DualDARDaemonProxy", "failed to start newly dualdard daemon ! ", new Object[0]);
                        bundle.putBoolean("dual_dar_response", false);
                        setSystemPropertyBoolean(false);
                    } else {
                        bundle.putBoolean("dual_dar_response", true);
                    }
                    Handler handler2 = this.mHandler;
                    if (handler2 != null) {
                        handler2.removeCallbacksAndMessages(null);
                    }
                    this.isDaemonConnectionFailed = false;
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final boolean unloadClientLibrary(int i) {
        DualDarDaemonConnector dualDarDaemonConnector = this.mConnector;
        if (dualDarDaemonConnector == null) {
            DDLog.e("DualDARDaemonProxy", "unloadClientLibrary failed! Error: native interface not yet connected failed", new Object[0]);
            return false;
        }
        try {
            NativeDaemonEvent executeSync = dualDarDaemonConnector.executeSync("vendor_lib", "unload", Integer.valueOf(i));
            this.mEvent = executeSync;
            if (!executeSync.isClassOk()) {
                if (!this.mEvent.isClassContinue()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
