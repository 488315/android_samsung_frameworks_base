package com.android.server.enterprise.vpn.knoxvpn;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.app.IProcessObserver;
import android.content.Context;
import android.os.Binder;
import android.os.Debug;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.android.server.enterprise.vpn.knoxvpn.profile.VpnPackageInfo;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo;

import com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KnoxVpnProcessManager {
    public static final boolean DBG = Debug.semIsProductDev();
    public static volatile KnoxVpnProcessManager instance = null;
    public static IActivityManager mActivityManagerNative = null;
    public final Context mContext;
    public final KnoxVpnEngineService mKnoxVpnEngineService;
    public final KnoxVpnHelper mKnoxVpnHelper;
    public boolean mObserverStatus = false;
    public final AnonymousClass1 mProcessObserver = new IProcessObserver.Stub() { // from class:
                // com.android.server.enterprise.vpn.knoxvpn.KnoxVpnProcessManager.1
                public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
                    KnoxVpnProcessManager.m567$$Nest$monApplicationStart(
                            KnoxVpnProcessManager.this, i2);
                }

                public final void onForegroundServicesChanged(int i, int i2, int i3) {}

                /* JADX WARN: Removed duplicated region for block: B:56:0x00ac A[Catch: all -> 0x0071, Exception -> 0x0074, TryCatch #1 {Exception -> 0x0074, blocks: (B:34:0x005c, B:36:0x0064, B:38:0x0068, B:41:0x0079, B:43:0x0081, B:54:0x00a6, B:56:0x00ac, B:57:0x00bf, B:58:0x00a1), top: B:33:0x005c, outer: #0 }] */
                /* JADX WARN: Removed duplicated region for block: B:57:0x00bf A[Catch: all -> 0x0071, Exception -> 0x0074, TRY_LEAVE, TryCatch #1 {Exception -> 0x0074, blocks: (B:34:0x005c, B:36:0x0064, B:38:0x0068, B:41:0x0079, B:43:0x0081, B:54:0x00a6, B:56:0x00ac, B:57:0x00bf, B:58:0x00a1), top: B:33:0x005c, outer: #0 }] */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onProcessDied(int r14, int r15) {
                    /*
                        Method dump skipped, instructions count: 255
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.android.server.enterprise.vpn.knoxvpn.KnoxVpnProcessManager.AnonymousClass1.onProcessDied(int,"
                                + " int):void");
                }

                public final void onProcessStarted(int i, int i2, int i3, String str, String str2) {
                    KnoxVpnProcessManager.m567$$Nest$monApplicationStart(
                            KnoxVpnProcessManager.this, i2);
                }
            };
    public final VpnProfileConfig mVpnConfig;

    /* renamed from: -$$Nest$monApplicationStart, reason: not valid java name */
    public static void m567$$Nest$monApplicationStart(
            KnoxVpnProcessManager knoxVpnProcessManager, int i) {
        KnoxVpnHelper knoxVpnHelper;
        synchronized (knoxVpnProcessManager) {
            if (knoxVpnProcessManager.mVpnConfig != null
                    && (knoxVpnHelper = knoxVpnProcessManager.mKnoxVpnHelper) != null
                    && knoxVpnProcessManager.mKnoxVpnEngineService != null) {
                String profileOwningTheUidFromCache =
                        knoxVpnHelper.getProfileOwningTheUidFromCache(i);
                if (profileOwningTheUidFromCache == null) {
                    return;
                }
                int userId = UserHandle.getUserId(i);
                VpnProfileInfo profileEntry =
                        knoxVpnProcessManager.mVpnConfig.getProfileEntry(
                                profileOwningTheUidFromCache);
                if (profileEntry == null) {
                    return;
                }
                if (profileEntry.vpnConnectionType != 1) {
                    return;
                }
                if (profileEntry.activateState == 0) {
                    return;
                }
                IKnoxVpnService binderInterfaceForProfile =
                        knoxVpnProcessManager.mKnoxVpnEngineService.getBinderInterfaceForProfile(
                                profileOwningTheUidFromCache);
                if (binderInterfaceForProfile == null) {
                    return;
                }
                try {
                    int state = binderInterfaceForProfile.getState(profileOwningTheUidFromCache);
                    if (state != 1 && state != 5) {
                        Log.d(
                                "KnoxVpnProcessManager",
                                "on-demand profile is not going to be restarted due to current"
                                    + " state "
                                        + state);
                        return;
                    }
                    for (String str :
                            knoxVpnProcessManager
                                    .mContext
                                    .getPackageManager()
                                    .getPackagesForUid(i)) {
                        knoxVpnProcessManager.mKnoxVpnHelper.getClass();
                        if (profileEntry.getPackage(KnoxVpnHelper.getPersonifiedName(userId, str))
                                != null) {
                            if (DBG) {
                                Log.d(
                                        "KnoxVpnProcessManager",
                                        "onApplicationStart : profileName : "
                                                + profileOwningTheUidFromCache
                                                + " / packageName : "
                                                + str
                                                + "/uid: "
                                                + i);
                            }
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(Integer.valueOf(i));
                            knoxVpnProcessManager.mKnoxVpnEngineService.startVpnForPerApplication(
                                    profileOwningTheUidFromCache, arrayList, true);
                        }
                    }
                } catch (RemoteException unused) {
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.enterprise.vpn.knoxvpn.KnoxVpnProcessManager$1] */
    public KnoxVpnProcessManager(KnoxVpnEngineService knoxVpnEngineService) {
        this.mContext = null;
        this.mVpnConfig = null;
        this.mKnoxVpnHelper = null;
        this.mKnoxVpnEngineService = null;
        Context context = knoxVpnEngineService.mContext;
        this.mContext = context;
        this.mKnoxVpnEngineService = knoxVpnEngineService;
        this.mVpnConfig = VpnProfileConfig.getInstance();
        this.mKnoxVpnHelper = KnoxVpnHelper.getInstance(context);
    }

    public final boolean isProcessObserverRegistered() {
        return this.mObserverStatus;
    }

    public final boolean processRunCheck(VpnProfileInfo vpnProfileInfo) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
                    ((ActivityManager) this.mContext.getSystemService("activity"))
                            .getRunningAppProcesses();
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            if (runningAppProcesses != null) {
                for (int i = 0; i < runningAppProcesses.size(); i++) {
                    if (DBG) {
                        Log.d(
                                "KnoxVpnProcessManager",
                                "runningApplicationProcess uid being added is "
                                        + runningAppProcesses.get(i).uid);
                    }
                    hashSet.add(String.valueOf(runningAppProcesses.get(i).uid));
                }
            }
            for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.mPackageMap.values()) {
                Log.d(
                        "KnoxVpnProcessManager",
                        "runningApplicationProcess VpnPackageInfo uid being added is "
                                + vpnPackageInfo.getUid());
                hashSet2.add(String.valueOf(vpnPackageInfo.getUid()));
            }
            hashSet.retainAll(hashSet2);
            if (hashSet.size() > 0) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
    }

    public final void registerProcessObserver() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.d("KnoxVpnProcessManager", "registerProcessObserver");
                if (mActivityManagerNative == null) {
                    mActivityManagerNative = ActivityManagerNative.getDefault();
                }
                mActivityManagerNative.registerProcessObserver(this.mProcessObserver);
                this.mObserverStatus = true;
            } catch (Exception e) {
                Log.e(
                        "KnoxVpnProcessManager",
                        "Error occured while trying to register a process observer " + e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unregisterProcessObserver() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.d("KnoxVpnProcessManager", "unregisterProcessObserver");
                if (mActivityManagerNative == null) {
                    mActivityManagerNative = ActivityManagerNative.getDefault();
                }
                mActivityManagerNative.unregisterProcessObserver(this.mProcessObserver);
                this.mObserverStatus = false;
            } catch (Exception e) {
                Log.e(
                        "KnoxVpnProcessManager",
                        "unregistering a process observer which is not registered yet " + e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
