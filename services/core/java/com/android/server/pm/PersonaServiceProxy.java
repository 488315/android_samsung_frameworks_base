package com.android.server.pm;

import android.app.KeyguardManager;
import android.app.trust.TrustManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.ContainerServiceInfo;
import com.android.server.ContainerServiceWrapper;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.samsung.android.knox.SemPersonaManager;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PersonaServiceProxy {
    public Object mContainerServiceLock;
    public HashMap mContainerServices;
    public Context mContext;
    public boolean mIsDoEnabled;
    public KeyguardManager mKeyguardManager;
    public LockPatternUtils mLockPatternUtils;
    public AnonymousClass2 mPackageBroadcastReceiver;
    public SemPersonaManager mPersonaManager;
    public TrustManager mTrustManager;
    public AnonymousClass2 mUserBroadcastReceiver;
    public UserManager mUserManager;

    /* JADX WARN: Removed duplicated region for block: B:21:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* renamed from: -$$Nest$mfindAndConnectToContainerService, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m773$$Nest$mfindAndConnectToContainerService(com.android.server.pm.PersonaServiceProxy r9, int r10) {
        /*
            java.lang.String r0 = "package:com.samsung.android.knox.containercore not found user:"
            java.lang.String r1 = "Finding container service in user - "
            java.lang.String r2 = "PersonaManagerService::Proxy"
            com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0.m(r10, r1, r2)
            android.os.UserManager r1 = r9.mUserManager
            if (r1 != 0) goto L15
            java.lang.String r9 = "Cannot find UMS"
            android.util.Log.e(r2, r9)
            goto Lce
        L15:
            boolean r1 = r1.isUserRunning(r10)
            if (r1 != 0) goto L25
            java.lang.String r9 = "scanContainerService() user<"
            java.lang.String r0 = "> is not running"
            com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0.m(r10, r9, r0, r2)
            goto Lce
        L25:
            java.lang.String r1 = "com.samsung.android.knox.containercore"
            java.lang.String r3 = "findContainerService("
            java.lang.String r4 = " appInfo.uid:"
            java.lang.String r5 = "package"
            android.os.IBinder r5 = android.os.ServiceManager.getService(r5)
            android.content.pm.IPackageManager r5 = android.content.pm.IPackageManager.Stub.asInterface(r5)
            r6 = 128(0x80, double:6.32E-322)
            r8 = 0
            android.content.pm.ApplicationInfo r6 = r5.getApplicationInfo(r1, r6, r10)     // Catch: android.os.RemoteException -> L7f
            boolean r1 = r5.isPackageAvailable(r1, r10)     // Catch: android.os.RemoteException -> L7f
            if (r1 == 0) goto Lb6
            if (r6 != 0) goto L47
            goto Lb6
        L47:
            android.os.Bundle r0 = r6.metaData     // Catch: android.os.RemoteException -> L7f
            if (r0 != 0) goto L4d
            goto Lc9
        L4d:
            java.lang.String r1 = r6.packageName     // Catch: android.os.RemoteException -> L7f
            java.lang.String r5 = "containerService.class"
            java.lang.String r5 = r0.getString(r5)     // Catch: android.os.RemoteException -> L7f
            java.lang.String r7 = "containerService.category"
            java.lang.String r0 = r0.getString(r7)     // Catch: android.os.RemoteException -> L7f
            if (r5 == 0) goto Lc9
            if (r1 == 0) goto Lc9
            if (r0 != 0) goto L62
            goto Lc9
        L62:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L7f
            r7.<init>(r4)     // Catch: android.os.RemoteException -> L7f
            int r4 = r6.uid     // Catch: android.os.RemoteException -> L7f
            r7.append(r4)     // Catch: android.os.RemoteException -> L7f
            java.lang.String r4 = r7.toString()     // Catch: android.os.RemoteException -> L7f
            android.util.Log.i(r2, r4)     // Catch: android.os.RemoteException -> L7f
            int r4 = r6.uid     // Catch: android.os.RemoteException -> L7f
            r6 = 5250(0x1482, float:7.357E-42)
            if (r4 == r6) goto L81
            java.lang.String r10 = "core app does not have core uid"
            android.util.Log.e(r2, r10)     // Catch: android.os.RemoteException -> L7f
            goto Lc9
        L7f:
            r10 = move-exception
            goto Lc6
        L81:
            android.content.ComponentName r4 = new android.content.ComponentName     // Catch: android.os.RemoteException -> L7f
            r4.<init>(r1, r5)     // Catch: android.os.RemoteException -> L7f
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L7f
            r1.<init>(r3)     // Catch: android.os.RemoteException -> L7f
            r1.append(r10)     // Catch: android.os.RemoteException -> L7f
            java.lang.String r3 = ") found "
            r1.append(r3)     // Catch: android.os.RemoteException -> L7f
            java.lang.String r3 = r4.flattenToShortString()     // Catch: android.os.RemoteException -> L7f
            r1.append(r3)     // Catch: android.os.RemoteException -> L7f
            java.lang.String r3 = " category:"
            r1.append(r3)     // Catch: android.os.RemoteException -> L7f
            r1.append(r0)     // Catch: android.os.RemoteException -> L7f
            java.lang.String r1 = r1.toString()     // Catch: android.os.RemoteException -> L7f
            android.util.Log.i(r2, r1)     // Catch: android.os.RemoteException -> L7f
            com.android.server.ContainerServiceInfo r1 = new com.android.server.ContainerServiceInfo     // Catch: android.os.RemoteException -> L7f
            r1.<init>()     // Catch: android.os.RemoteException -> L7f
            r1.userid = r10     // Catch: android.os.RemoteException -> L7f
            r1.name = r4     // Catch: android.os.RemoteException -> L7f
            r1.category = r0     // Catch: android.os.RemoteException -> L7f
            r8 = r1
            goto Lc9
        Lb6:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L7f
            r1.<init>(r0)     // Catch: android.os.RemoteException -> L7f
            r1.append(r10)     // Catch: android.os.RemoteException -> L7f
            java.lang.String r10 = r1.toString()     // Catch: android.os.RemoteException -> L7f
            android.util.Log.e(r2, r10)     // Catch: android.os.RemoteException -> L7f
            goto Lc9
        Lc6:
            r10.printStackTrace()
        Lc9:
            if (r8 == 0) goto Lce
            r9.maybeConnectContainerService(r8)
        Lce:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PersonaServiceProxy.m773$$Nest$mfindAndConnectToContainerService(com.android.server.pm.PersonaServiceProxy, int):void");
    }

    public final boolean isKnoxProfileExist() {
        boolean z = true;
        List<UserInfo> users = this.mUserManager.getUsers(true);
        if (users != null) {
            for (UserInfo userInfo : users) {
                GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("Profile id - "), userInfo.id, "PersonaManagerService::Proxy");
                if (SemPersonaManager.isKnoxId(userInfo.id)) {
                    Log.d("PersonaManagerService::Proxy", "Knox Profile exist on device");
                    break;
                }
            }
        }
        z = false;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("isKnoxProfileExist status - ", "PersonaManagerService::Proxy", z);
        return z;
    }

    public final void maybeConnectContainerService(ContainerServiceInfo containerServiceInfo) {
        StringBuilder sb = new StringBuilder("connectContainerService is called for ");
        sb.append(containerServiceInfo.toString());
        sb.append(", mIsDoEnabled - ");
        RCPManagerService$$ExternalSyntheticOutline0.m("PersonaManagerService::Proxy", sb, this.mIsDoEnabled);
        if (!this.mIsDoEnabled && !isKnoxProfileExist()) {
            Log.d("PersonaManagerService::Proxy", "Not starting Container service...");
            return;
        }
        Log.d("PersonaManagerService::Proxy", "Starting Container service because either Do/Po exist on device...");
        synchronized (this.mContainerServiceLock) {
            try {
                if (!this.mContainerServices.containsKey(containerServiceInfo)) {
                    Log.i("PersonaManagerService::Proxy", "Service don't exist on cache...");
                } else {
                    if (((ContainerServiceWrapper) this.mContainerServices.get(containerServiceInfo)).mBound) {
                        Log.e("PersonaManagerService::Proxy", "Container service already bound " + containerServiceInfo.toString());
                        return;
                    }
                    this.mContainerServices.remove(containerServiceInfo);
                }
                ContainerServiceWrapper containerServiceWrapper = new ContainerServiceWrapper(this.mContext, this, containerServiceInfo);
                if (containerServiceWrapper.connect()) {
                    this.mContainerServices.put(containerServiceInfo, containerServiceWrapper);
                    return;
                }
                Log.e("PersonaManagerService::Proxy", "Failed to bind " + containerServiceInfo.toString());
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
