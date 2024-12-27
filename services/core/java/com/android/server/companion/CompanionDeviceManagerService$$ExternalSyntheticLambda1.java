package com.android.server.companion;

import android.content.pm.PackageInfo;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class CompanionDeviceManagerService$$ExternalSyntheticLambda1
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ CompanionDeviceManagerService f$0;
    public final /* synthetic */ PackageInfo f$1;

    public /* synthetic */ CompanionDeviceManagerService$$ExternalSyntheticLambda1(
            CompanionDeviceManagerService companionDeviceManagerService, PackageInfo packageInfo) {
        this.f$0 = companionDeviceManagerService;
        this.f$1 = packageInfo;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x009c A[Catch: IllegalArgumentException -> 0x00a4, TryCatch #0 {IllegalArgumentException -> 0x00a4, blocks: (B:13:0x0085, B:15:0x0091, B:21:0x009c, B:25:0x00a6), top: B:12:0x0085 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a6 A[Catch: IllegalArgumentException -> 0x00a4, TRY_LEAVE, TryCatch #0 {IllegalArgumentException -> 0x00a4, blocks: (B:13:0x0085, B:15:0x0091, B:21:0x009c, B:25:0x00a6), top: B:12:0x0085 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0064 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void runOrThrow() {
        /*
            r8 = this;
            com.android.server.companion.CompanionDeviceManagerService r0 = r8.f$0
            android.content.pm.PackageInfo r8 = r8.f$1
            r0.getClass()
            java.lang.String r1 = "Update SpAccess:"
            if (r8 != 0) goto Ld
            goto Lbe
        Ld:
            java.lang.String[] r2 = r8.requestedPermissions
            java.lang.String r3 = "android.permission.RUN_IN_BACKGROUND"
            boolean r3 = com.android.internal.util.ArrayUtils.contains(r2, r3)
            r4 = 1
            r5 = 0
            if (r3 != 0) goto L24
            java.lang.String r3 = "android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND"
            boolean r2 = com.android.internal.util.ArrayUtils.contains(r2, r3)
            if (r2 == 0) goto L22
            goto L24
        L22:
            r2 = r5
            goto L25
        L24:
            r2 = r4
        L25:
            java.lang.String r3 = "CDM_CompanionDeviceManagerService"
            if (r2 == 0) goto L31
            android.os.PowerExemptionManager r1 = r0.mPowerExemptionManager
            java.lang.String r2 = r8.packageName
            r1.addToPermanentAllowList(r2)
            goto L7d
        L31:
            java.lang.String r2 = "deviceidle"
            android.os.IBinder r2 = android.os.ServiceManager.getService(r2)
            android.os.IDeviceIdleController r2 = android.os.IDeviceIdleController.Stub.asInterface(r2)
            java.lang.String r6 = r8.packageName     // Catch: android.os.RemoteException -> L5c
            boolean r2 = r2.isPowerSaveWhitelistApp(r6)     // Catch: android.os.RemoteException -> L5c
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L5d
            r6.<init>(r1)     // Catch: android.os.RemoteException -> L5d
            java.lang.String r1 = r8.packageName     // Catch: android.os.RemoteException -> L5d
            r6.append(r1)     // Catch: android.os.RemoteException -> L5d
            java.lang.String r1 = " Unrestricted:"
            r6.append(r1)     // Catch: android.os.RemoteException -> L5d
            r6.append(r2)     // Catch: android.os.RemoteException -> L5d
            java.lang.String r1 = r6.toString()     // Catch: android.os.RemoteException -> L5d
            android.util.Slog.i(r3, r1)     // Catch: android.os.RemoteException -> L5d
            goto L62
        L5c:
            r2 = r5
        L5d:
            java.lang.String r1 = "RemoteException when calling isPowerSaveWhitelistApp"
            android.util.Slog.w(r3, r1)
        L62:
            if (r2 != 0) goto L7d
            java.lang.String r1 = "Update SpAccess: remove from whitelist"
            android.util.Slog.i(r3, r1)     // Catch: java.lang.UnsupportedOperationException -> L71
            android.os.PowerExemptionManager r1 = r0.mPowerExemptionManager     // Catch: java.lang.UnsupportedOperationException -> L71
            java.lang.String r2 = r8.packageName     // Catch: java.lang.UnsupportedOperationException -> L71
            r1.removeFromPermanentAllowList(r2)     // Catch: java.lang.UnsupportedOperationException -> L71
            goto L7d
        L71:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r8.packageName
            java.lang.String r6 = " can't be removed from power save whitelist. It might due to the package is whitelisted by the system."
            com.android.server.ProfileService$1$$ExternalSyntheticOutline0.m(r1, r2, r6, r3)
        L7d:
            android.content.Context r1 = r0.getContext()
            android.net.NetworkPolicyManager r1 = android.net.NetworkPolicyManager.from(r1)
            java.lang.String[] r2 = r8.requestedPermissions     // Catch: java.lang.IllegalArgumentException -> La4
            java.lang.String r6 = "android.permission.USE_DATA_IN_BACKGROUND"
            java.lang.String r7 = "android.permission.REQUEST_COMPANION_USE_DATA_IN_BACKGROUND"
            boolean r6 = com.android.internal.util.ArrayUtils.contains(r2, r6)     // Catch: java.lang.IllegalArgumentException -> La4
            if (r6 != 0) goto L99
            boolean r2 = com.android.internal.util.ArrayUtils.contains(r2, r7)     // Catch: java.lang.IllegalArgumentException -> La4
            if (r2 == 0) goto L98
            goto L99
        L98:
            r4 = r5
        L99:
            r2 = 4
            if (r4 == 0) goto La6
            android.content.pm.ApplicationInfo r4 = r8.applicationInfo     // Catch: java.lang.IllegalArgumentException -> La4
            int r4 = r4.uid     // Catch: java.lang.IllegalArgumentException -> La4
            r1.addUidPolicy(r4, r2)     // Catch: java.lang.IllegalArgumentException -> La4
            goto Lb5
        La4:
            r1 = move-exception
            goto Lae
        La6:
            android.content.pm.ApplicationInfo r4 = r8.applicationInfo     // Catch: java.lang.IllegalArgumentException -> La4
            int r4 = r4.uid     // Catch: java.lang.IllegalArgumentException -> La4
            r1.removeUidPolicy(r4, r2)     // Catch: java.lang.IllegalArgumentException -> La4
            goto Lb5
        Lae:
            java.lang.String r1 = r1.getMessage()
            android.util.Slog.e(r3, r1)
        Lb5:
            java.lang.String r1 = r8.packageName
            android.content.pm.ApplicationInfo r8 = r8.applicationInfo
            int r8 = r8.uid
            r0.exemptFromAutoRevoke(r8, r1)
        Lbe:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.companion.CompanionDeviceManagerService$$ExternalSyntheticLambda1.runOrThrow():void");
    }
}
