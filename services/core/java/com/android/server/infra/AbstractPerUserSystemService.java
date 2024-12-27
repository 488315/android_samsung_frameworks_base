package com.android.server.infra;

import android.content.ComponentName;
import android.content.pm.ServiceInfo;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class AbstractPerUserSystemService {
    public boolean mDisabled;
    public final Object mLock;
    public final AbstractMasterSystemService mMaster;
    public ServiceInfo mServiceInfo;
    public boolean mSetupComplete;
    public final String mTag = getClass().getSimpleName();
    public final int mUserId;

    public AbstractPerUserSystemService(AbstractMasterSystemService abstractMasterSystemService, Object obj, int i) {
        this.mMaster = abstractMasterSystemService;
        this.mLock = obj;
        this.mUserId = i;
        this.mSetupComplete = "1".equals(Settings.Secure.getStringForUser(abstractMasterSystemService.getContext().getContentResolver(), "user_setup_complete", i));
    }

    public void dumpLocked(PrintWriter printWriter) {
        printWriter.print("    ");
        printWriter.print("User: ");
        int i = this.mUserId;
        printWriter.println(i);
        if (this.mServiceInfo != null) {
            printWriter.print("    ");
            printWriter.print("Service Label: ");
            printWriter.println(getServiceLabelLocked());
            printWriter.print("    ");
            printWriter.print("Target SDK: ");
            ServiceInfo serviceInfo = this.mServiceInfo;
            printWriter.println(serviceInfo == null ? 0 : serviceInfo.applicationInfo.targetSdkVersion);
        }
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        if (abstractMasterSystemService.mServiceNameResolver != null) {
            printWriter.print("    ");
            printWriter.print("Name resolver: ");
            abstractMasterSystemService.mServiceNameResolver.dumpShort(i, printWriter);
            printWriter.println();
        }
        printWriter.print("    ");
        printWriter.print("Disabled by UserManager: ");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, "    ", "Setup complete: ", this.mDisabled);
        printWriter.println(this.mSetupComplete);
        if (this.mServiceInfo != null) {
            printWriter.print("    ");
            printWriter.print("Service UID: ");
            printWriter.println(this.mServiceInfo.applicationInfo.uid);
        }
        printWriter.println();
    }

    public final String getComponentNameLocked() {
        return this.mMaster.mServiceNameResolver.getServiceName(this.mUserId);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x005e A[Catch: all -> 0x0038, Exception -> 0x0090, TRY_ENTER, TryCatch #0 {Exception -> 0x0090, blocks: (B:10:0x005e, B:12:0x006a, B:19:0x0092, B:21:0x009a), top: B:8:0x005c, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0092 A[Catch: all -> 0x0038, Exception -> 0x0090, TryCatch #0 {Exception -> 0x0090, blocks: (B:10:0x005e, B:12:0x006a, B:19:0x0092, B:21:0x009a), top: B:8:0x005c, outer: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.ComponentName getServiceComponent(java.lang.String r13) {
        /*
            r12 = this;
            java.lang.String r0 = "Bad ServiceInfo for '"
            java.lang.String r1 = "Reset component for user "
            java.lang.String r2 = "Set component for user "
            java.lang.String r3 = "Error getting service info for '"
            java.lang.String r4 = "Bad service name: "
            java.lang.Object r5 = r12.mLock
            monitor-enter(r5)
            boolean r6 = android.text.TextUtils.isEmpty(r13)     // Catch: java.lang.Throwable -> L38
            r7 = 0
            if (r6 != 0) goto L5a
            android.content.ComponentName r6 = android.content.ComponentName.unflattenFromString(r13)     // Catch: java.lang.Throwable -> L38 java.lang.Throwable -> L3d
            android.content.pm.IPackageManager r8 = android.app.AppGlobals.getPackageManager()     // Catch: java.lang.Throwable -> L38 java.lang.Throwable -> L3b
            int r9 = r12.mUserId     // Catch: java.lang.Throwable -> L38 java.lang.Throwable -> L3b
            r10 = 0
            android.content.pm.ServiceInfo r8 = r8.getServiceInfo(r6, r10, r9)     // Catch: java.lang.Throwable -> L38 java.lang.Throwable -> L3b
            if (r8 != 0) goto L5c
            java.lang.String r9 = r12.mTag     // Catch: java.lang.Throwable -> L38 java.lang.Throwable -> L3b
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L38 java.lang.Throwable -> L3b
            r10.<init>(r4)     // Catch: java.lang.Throwable -> L38 java.lang.Throwable -> L3b
            r10.append(r13)     // Catch: java.lang.Throwable -> L38 java.lang.Throwable -> L3b
            java.lang.String r4 = r10.toString()     // Catch: java.lang.Throwable -> L38 java.lang.Throwable -> L3b
            android.util.Slog.e(r9, r4)     // Catch: java.lang.Throwable -> L38 java.lang.Throwable -> L3b
            goto L5c
        L38:
            r12 = move-exception
            goto Ld3
        L3b:
            r4 = move-exception
            goto L3f
        L3d:
            r4 = move-exception
            r6 = r7
        L3f:
            java.lang.String r8 = r12.mTag     // Catch: java.lang.Throwable -> L38
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L38
            r9.<init>(r3)     // Catch: java.lang.Throwable -> L38
            r9.append(r13)     // Catch: java.lang.Throwable -> L38
            java.lang.String r3 = "': "
            r9.append(r3)     // Catch: java.lang.Throwable -> L38
            r9.append(r4)     // Catch: java.lang.Throwable -> L38
            java.lang.String r3 = r9.toString()     // Catch: java.lang.Throwable -> L38
            android.util.Slog.e(r8, r3)     // Catch: java.lang.Throwable -> L38
            r8 = r7
            goto L5c
        L5a:
            r6 = r7
            r8 = r6
        L5c:
            if (r8 == 0) goto L92
            android.content.pm.ServiceInfo r1 = r12.newServiceInfoLocked(r6)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            r12.mServiceInfo = r1     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            com.android.server.infra.AbstractMasterSystemService r1 = r12.mMaster     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            boolean r1 = r1.debug     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            if (r1 == 0) goto Ld1
            java.lang.String r1 = r12.mTag     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            int r2 = r12.mUserId     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            r3.append(r2)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            java.lang.String r2 = " as "
            r3.append(r2)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            r3.append(r6)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            java.lang.String r2 = " and info as "
            r3.append(r2)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            android.content.pm.ServiceInfo r2 = r12.mServiceInfo     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            r3.append(r2)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            java.lang.String r2 = r3.toString()     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            android.util.Slog.d(r1, r2)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            goto Ld1
        L90:
            r1 = move-exception
            goto Lb6
        L92:
            r12.mServiceInfo = r7     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            com.android.server.infra.AbstractMasterSystemService r2 = r12.mMaster     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            boolean r2 = r2.debug     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            if (r2 == 0) goto Ld1
            java.lang.String r2 = r12.mTag     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            int r1 = r12.mUserId     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            r3.append(r1)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            java.lang.String r1 = ":"
            r3.append(r1)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            r3.append(r13)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            java.lang.String r1 = r3.toString()     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            android.util.Slog.d(r2, r1)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L90
            goto Ld1
        Lb6:
            java.lang.String r2 = r12.mTag     // Catch: java.lang.Throwable -> L38
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L38
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L38
            r3.append(r13)     // Catch: java.lang.Throwable -> L38
            java.lang.String r13 = "': "
            r3.append(r13)     // Catch: java.lang.Throwable -> L38
            r3.append(r1)     // Catch: java.lang.Throwable -> L38
            java.lang.String r13 = r3.toString()     // Catch: java.lang.Throwable -> L38
            android.util.Slog.e(r2, r13)     // Catch: java.lang.Throwable -> L38
            r12.mServiceInfo = r7     // Catch: java.lang.Throwable -> L38
        Ld1:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L38
            return r6
        Ld3:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L38
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.infra.AbstractPerUserSystemService.getServiceComponent(java.lang.String):android.content.ComponentName");
    }

    public final ComponentName getServiceComponentName() {
        ComponentName componentName;
        synchronized (this.mLock) {
            ServiceInfo serviceInfo = this.mServiceInfo;
            componentName = serviceInfo == null ? null : serviceInfo.getComponentName();
        }
        return componentName;
    }

    public final CharSequence getServiceLabelLocked() {
        ServiceInfo serviceInfo = this.mServiceInfo;
        if (serviceInfo == null) {
            return null;
        }
        return serviceInfo.loadSafeLabel(this.mMaster.getContext().getPackageManager(), FullScreenMagnificationGestureHandler.MAX_SCALE, 5);
    }

    public final String getServicePackageName() {
        ComponentName serviceComponentName = getServiceComponentName();
        if (serviceComponentName == null) {
            return null;
        }
        return serviceComponentName.getPackageName();
    }

    public final int getServiceUidLocked() {
        ServiceInfo serviceInfo = this.mServiceInfo;
        if (serviceInfo != null) {
            return serviceInfo.applicationInfo.uid;
        }
        if (!this.mMaster.verbose) {
            return -1;
        }
        Slog.v(this.mTag, "getServiceUidLocked(): no mServiceInfo");
        return -1;
    }

    public void handlePackageUpdateLocked(String str) {
    }

    public final boolean isEnabledLocked() {
        return (!this.mSetupComplete || this.mServiceInfo == null || this.mDisabled) ? false : true;
    }

    public abstract ServiceInfo newServiceInfoLocked(ComponentName componentName);

    public final void removeSelfFromCache() {
        synchronized (this.mMaster.mLock) {
            this.mMaster.removeCachedServiceListLocked(this.mUserId);
        }
    }

    public boolean updateLocked(boolean z) {
        boolean isEnabledLocked = isEnabledLocked();
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        boolean z2 = abstractMasterSystemService.verbose;
        int i = this.mUserId;
        String str = this.mTag;
        if (z2) {
            StringBuilder sb = new StringBuilder("updateLocked(u=");
            sb.append(i);
            sb.append("): wasEnabled=");
            sb.append(isEnabledLocked);
            sb.append(", mSetupComplete=");
            BatteryService$$ExternalSyntheticOutline0.m(sb, this.mSetupComplete, ", disabled=", z, ", mDisabled=");
            ProxyManager$$ExternalSyntheticOutline0.m(str, sb, this.mDisabled);
        }
        this.mSetupComplete = "1".equals(Settings.Secure.getStringForUser(abstractMasterSystemService.getContext().getContentResolver(), "user_setup_complete", i));
        this.mDisabled = z;
        ServiceNameBaseResolver serviceNameBaseResolver = abstractMasterSystemService.mServiceNameResolver;
        if (serviceNameBaseResolver == null || !serviceNameBaseResolver.mIsMultiple) {
            updateServiceInfoLocked();
        } else if (abstractMasterSystemService.debug) {
            Slog.d(str, "Should not end up in updateLocked when isConfiguredInMultipleMode is true");
        }
        return isEnabledLocked != isEnabledLocked();
    }

    public final ComponentName[] updateServiceInfoListLocked() {
        ServiceNameBaseResolver serviceNameBaseResolver = this.mMaster.mServiceNameResolver;
        if (serviceNameBaseResolver == null) {
            return null;
        }
        if (!serviceNameBaseResolver.mIsMultiple) {
            return new ComponentName[]{getServiceComponent(getComponentNameLocked())};
        }
        String[] serviceNameList = serviceNameBaseResolver.getServiceNameList(this.mUserId);
        if (serviceNameList == null) {
            return null;
        }
        ComponentName[] componentNameArr = new ComponentName[serviceNameList.length];
        for (int i = 0; i < serviceNameList.length; i++) {
            componentNameArr[i] = getServiceComponent(serviceNameList[i]);
        }
        return componentNameArr;
    }

    public final ComponentName updateServiceInfoLocked() {
        ComponentName[] updateServiceInfoListLocked = updateServiceInfoListLocked();
        if (updateServiceInfoListLocked == null || updateServiceInfoListLocked.length == 0) {
            return null;
        }
        return updateServiceInfoListLocked[0];
    }
}
