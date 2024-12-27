package com.android.server.devicepolicy;

import android.content.Context;
import com.android.internal.util.FunctionalUtils;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda101 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda101(int i, Context context, List list) {
        this.$r8$classId = 2;
        this.f$2 = list;
        this.f$0 = context;
        this.f$1 = i;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda101(DevicePolicyManagerService devicePolicyManagerService, int i, Object obj, int i2) {
        this.$r8$classId = i2;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = i;
        this.f$2 = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void runOrThrow() {
        /*
            r3 = this;
            int r0 = r3.$r8$classId
            switch(r0) {
                case 0: goto L77;
                case 1: goto L58;
                default: goto L5;
            }
        L5:
            java.lang.Object r0 = r3.f$2
            java.util.List r0 = (java.util.List) r0
            java.lang.Object r1 = r3.f$0
            android.content.Context r1 = (android.content.Context) r1
            int r3 = r3.f$1
            boolean r2 = r0.isEmpty()
            if (r2 != 0) goto L37
            java.util.List r1 = com.android.server.devicepolicy.DevicePolicyManagerService.listPolicyExemptAppsUnchecked(r1)
            r2 = r1
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L37
            java.util.HashSet r2 = new java.util.HashSet
            r2.<init>(r0)
            r2.addAll(r1)
            int r1 = r2.size()
            java.lang.String[] r1 = new java.lang.String[r1]
            java.lang.Object[] r1 = r2.toArray(r1)
            java.lang.String[] r1 = (java.lang.String[]) r1
            goto L38
        L37:
            r1 = 0
        L38:
            if (r1 != 0) goto L47
            int r1 = r0.size()
            java.lang.String[] r1 = new java.lang.String[r1]
            java.lang.Object[] r0 = r0.toArray(r1)
            r1 = r0
            java.lang.String[] r1 = (java.lang.String[]) r1
        L47:
            android.app.IActivityManager r0 = android.app.ActivityManager.getService()     // Catch: android.os.RemoteException -> L4f
            r0.updateLockTaskPackages(r3, r1)     // Catch: android.os.RemoteException -> L4f
            goto L57
        L4f:
            r3 = move-exception
            java.lang.String r0 = "DevicePolicyManager"
            java.lang.String r1 = "Remote Exception: "
            android.util.Slog.wtf(r0, r1, r3)
        L57:
            return
        L58:
            java.lang.Object r0 = r3.f$0
            com.android.server.devicepolicy.DevicePolicyManagerService r0 = (com.android.server.devicepolicy.DevicePolicyManagerService) r0
            int r1 = r3.f$1
            java.lang.Object r3 = r3.f$2
            com.android.server.devicepolicy.ActiveAdmin r3 = (com.android.server.devicepolicy.ActiveAdmin) r3
            android.os.UserManager r2 = r0.mUserManager
            boolean r2 = r2.isManagedProfile(r1)
            if (r2 == 0) goto L71
            r0.maybeSetDefaultRestrictionsForAdminLocked(r1, r3)
            r2 = 1
            r0.ensureUnknownSourcesRestrictionForProfileOwnerLocked(r1, r3, r2)
        L71:
            java.lang.String r3 = "android.app.action.PROFILE_OWNER_CHANGED"
            r0.sendOwnerChangedBroadcast(r1, r3)
            return
        L77:
            java.lang.Object r0 = r3.f$0
            com.android.server.devicepolicy.DevicePolicyManagerService r0 = (com.android.server.devicepolicy.DevicePolicyManagerService) r0
            int r1 = r3.f$1
            java.lang.Object r3 = r3.f$2
            java.util.List r3 = (java.util.List) r3
            com.android.server.devicepolicy.DevicePolicyManagerService$Injector r0 = r0.mInjector
            android.content.Context r0 = r0.mContext
            java.lang.Class<android.net.ConnectivityManager> r2 = android.net.ConnectivityManager.class
            java.lang.Object r0 = r0.getSystemService(r2)
            android.net.ConnectivityManager r0 = (android.net.ConnectivityManager) r0
            android.os.UserHandle r1 = android.os.UserHandle.of(r1)
            r2 = 0
            r0.setProfileNetworkPreferences(r1, r3, r2, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda101.runOrThrow():void");
    }
}
