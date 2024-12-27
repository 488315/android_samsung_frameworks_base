package com.android.server;

import android.content.Context;
import android.service.watchdog.IExplicitHealthCheckService;
import android.util.Slog;

import java.util.function.Consumer;

public final class ExplicitHealthCheckController {
    public AnonymousClass1 mConnection;
    public final Context mContext;
    public boolean mEnabled;
    public final Object mLock = new Object();
    public Runnable mNotifySyncRunnable;
    public Consumer mPassedConsumer;
    public IExplicitHealthCheckService mRemoteService;
    public Consumer mSupportedConsumer;

    public ExplicitHealthCheckController(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0043, code lost:

       android.util.Slog.i("ExplicitHealthCheckController", "Not binding to service, service disabled");
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [android.content.ServiceConnection, com.android.server.ExplicitHealthCheckController$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void bindService() {
        /*
            r5 = this;
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            boolean r1 = r5.mEnabled     // Catch: java.lang.Throwable -> L1f
            if (r1 == 0) goto L41
            com.android.server.ExplicitHealthCheckController$1 r2 = r5.mConnection     // Catch: java.lang.Throwable -> L1f
            if (r2 != 0) goto L41
            android.service.watchdog.IExplicitHealthCheckService r2 = r5.mRemoteService     // Catch: java.lang.Throwable -> L1f
            if (r2 == 0) goto L10
            goto L41
        L10:
            android.content.ComponentName r1 = r5.getServiceComponentNameLocked()     // Catch: java.lang.Throwable -> L1f
            if (r1 != 0) goto L21
            java.lang.String r5 = "ExplicitHealthCheckController"
            java.lang.String r1 = "Explicit health check service not found"
            android.util.Slog.wtf(r5, r1)     // Catch: java.lang.Throwable -> L1f
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1f
            return
        L1f:
            r5 = move-exception
            goto L60
        L21:
            android.content.Intent r2 = new android.content.Intent     // Catch: java.lang.Throwable -> L1f
            r2.<init>()     // Catch: java.lang.Throwable -> L1f
            r2.setComponent(r1)     // Catch: java.lang.Throwable -> L1f
            com.android.server.ExplicitHealthCheckController$1 r1 = new com.android.server.ExplicitHealthCheckController$1     // Catch: java.lang.Throwable -> L1f
            r1.<init>()     // Catch: java.lang.Throwable -> L1f
            r5.mConnection = r1     // Catch: java.lang.Throwable -> L1f
            android.content.Context r5 = r5.mContext     // Catch: java.lang.Throwable -> L1f
            android.os.UserHandle r3 = android.os.UserHandle.SYSTEM     // Catch: java.lang.Throwable -> L1f
            r4 = 1
            r5.bindServiceAsUser(r2, r1, r4, r3)     // Catch: java.lang.Throwable -> L1f
            java.lang.String r5 = "ExplicitHealthCheckController"
            java.lang.String r1 = "Explicit health check service is bound"
            android.util.Slog.i(r5, r1)     // Catch: java.lang.Throwable -> L1f
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1f
            return
        L41:
            if (r1 != 0) goto L4b
            java.lang.String r5 = "ExplicitHealthCheckController"
            java.lang.String r1 = "Not binding to service, service disabled"
            android.util.Slog.i(r5, r1)     // Catch: java.lang.Throwable -> L1f
            goto L5e
        L4b:
            android.service.watchdog.IExplicitHealthCheckService r5 = r5.mRemoteService     // Catch: java.lang.Throwable -> L1f
            if (r5 == 0) goto L57
            java.lang.String r5 = "ExplicitHealthCheckController"
            java.lang.String r1 = "Not binding to service, service already connected"
            android.util.Slog.i(r5, r1)     // Catch: java.lang.Throwable -> L1f
            goto L5e
        L57:
            java.lang.String r5 = "ExplicitHealthCheckController"
            java.lang.String r1 = "Not binding to service, service already connecting"
            android.util.Slog.i(r5, r1)     // Catch: java.lang.Throwable -> L1f
        L5e:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1f
            return
        L60:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1f
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.ExplicitHealthCheckController.bindService():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0037 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.ComponentName getServiceComponentNameLocked() {
        /*
            r5 = this;
            android.content.Context r0 = r5.mContext
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            java.lang.String r0 = r0.getServicesSystemSharedLibraryPackageName()
            java.lang.String r1 = "ExplicitHealthCheckController"
            r2 = 0
            if (r0 != 0) goto L17
            java.lang.String r5 = "no external services package!"
            android.util.Slog.w(r1, r5)
        L15:
            r5 = r2
            goto L35
        L17:
            java.lang.String r3 = "android.service.watchdog.ExplicitHealthCheckService"
            android.content.Intent r0 = com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0.m(r3, r0)
            android.content.Context r5 = r5.mContext
            android.content.pm.PackageManager r5 = r5.getPackageManager()
            r3 = 132(0x84, float:1.85E-43)
            android.content.pm.ResolveInfo r5 = r5.resolveService(r0, r3)
            if (r5 == 0) goto L2f
            android.content.pm.ServiceInfo r5 = r5.serviceInfo
            if (r5 != 0) goto L35
        L2f:
            java.lang.String r5 = "No valid components found."
            android.util.Slog.w(r1, r5)
            goto L15
        L35:
            if (r5 != 0) goto L38
            return r2
        L38:
            android.content.ComponentName r0 = new android.content.ComponentName
            java.lang.String r3 = r5.packageName
            java.lang.String r4 = r5.name
            r0.<init>(r3, r4)
            java.lang.String r3 = "android.permission.BIND_EXPLICIT_HEALTH_CHECK_SERVICE"
            java.lang.String r5 = r5.permission
            boolean r5 = r3.equals(r5)
            if (r5 != 0) goto L64
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r0 = r0.flattenToShortString()
            r5.append(r0)
            java.lang.String r0 = " does not require permission android.permission.BIND_EXPLICIT_HEALTH_CHECK_SERVICE"
            r5.append(r0)
            java.lang.String r5 = r5.toString()
            android.util.Slog.w(r1, r5)
            return r2
        L64:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.ExplicitHealthCheckController.getServiceComponentNameLocked():android.content.ComponentName");
    }

    public final boolean prepareServiceLocked(String str) {
        if (this.mRemoteService != null && this.mEnabled) {
            return true;
        }
        StringBuilder sb = new StringBuilder("Service not ready to ");
        sb.append(str);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                sb, this.mEnabled ? ". Binding..." : ". Disabled", "ExplicitHealthCheckController");
        if (!this.mEnabled) {
            return false;
        }
        bindService();
        return false;
    }

    public final void unbindService() {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteService != null) {
                    this.mContext.unbindService(this.mConnection);
                    this.mRemoteService = null;
                    this.mConnection = null;
                }
                Slog.i("ExplicitHealthCheckController", "Explicit health check service is unbound");
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
