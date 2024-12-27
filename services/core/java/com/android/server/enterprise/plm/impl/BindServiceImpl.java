package com.android.server.enterprise.plm.impl;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.enterprise.plm.context.ProcessContext;

public final class BindServiceImpl {
    public int mAliveEvent;
    public final Context mContext;
    public int mDeathEvent;
    public final ProcessContext mProcessContext;
    public ConnectionHelper mConnectionHelper = null;
    public Handler mObserver = null;

    /* renamed from: com.android.server.enterprise.plm.impl.BindServiceImpl$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    public BindServiceImpl(Context context, ProcessContext processContext) {
        this.mContext = context;
        this.mProcessContext = processContext;
    }

    public final ConnectionHelper getConnectionHelper() {
        if (this.mConnectionHelper == null) {
            Context context = this.mContext;
            ProcessContext processContext = this.mProcessContext;
            this.mConnectionHelper =
                    new ConnectionHelper(
                            context,
                            processContext.getPackageName(),
                            processContext.getServiceName(),
                            new AnonymousClass1());
        }
        return this.mConnectionHelper;
    }

    public final boolean hasPackage(String str) {
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            if (packageManager != null) {
                packageManager.getPackageInfo(str, 0);
                StringBuilder sb = new StringBuilder("found package ");
                sb.append(str);
                sb.append(" ");
                sb.append(
                        packageManager.getApplicationEnabledSetting(str) == 1
                                ? "enabled"
                                : "disabled");
                Log.i("Utils", sb.toString());
                return true;
            }
        } catch (Throwable th) {
            Log.e("Utils", th.toString());
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "failed to find package ", str, "Utils");
        return false;
    }

    public final void setPackageEnabled(String str, boolean z) {
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            int i = z ? 1 : 2;
            if (packageManager == null || packageManager.getApplicationEnabledSetting(str) == i) {
                return;
            }
            StringBuilder sb = new StringBuilder("set package ");
            sb.append(str);
            sb.append(z ? " enabled" : " disabled");
            Log.i("Utils", sb.toString());
            packageManager.setApplicationEnabledSetting(str, i, 0);
        } catch (Throwable th) {
            Log.e("Utils", th.toString());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean start() {
        /*
            r9 = this;
            android.content.Context r0 = r9.mContext
            com.android.server.enterprise.plm.context.ProcessContext r1 = r9.mProcessContext
            java.lang.String r2 = r1.getPackageName()
            java.lang.String r3 = r1.getServiceName()
            java.lang.String r4 = "Utils"
            r5 = 0
            android.content.pm.PackageManager r0 = r0.getPackageManager()     // Catch: java.lang.Throwable -> L4c
            if (r0 == 0) goto L66
            r0.getPackageInfo(r2, r5)     // Catch: java.lang.Throwable -> L4c
            r6 = 4
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r2, r6)     // Catch: java.lang.Throwable -> L4c
            android.content.pm.ServiceInfo[] r0 = r0.services     // Catch: java.lang.Throwable -> L4c
            if (r0 == 0) goto L66
            int r2 = r0.length     // Catch: java.lang.Throwable -> L4c
            r6 = r5
        L23:
            if (r6 >= r2) goto L66
            r7 = r0[r6]     // Catch: java.lang.Throwable -> L4c
            java.lang.String r8 = r7.name     // Catch: java.lang.Throwable -> L4c
            boolean r8 = android.text.TextUtils.equals(r8, r3)     // Catch: java.lang.Throwable -> L4c
            if (r8 == 0) goto L5c
            boolean r0 = r7.isEnabled()     // Catch: java.lang.Throwable -> L4c
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4c
            r2.<init>()     // Catch: java.lang.Throwable -> L4c
            java.lang.String r6 = "found service "
            r2.append(r6)     // Catch: java.lang.Throwable -> L4c
            r2.append(r3)     // Catch: java.lang.Throwable -> L4c
            java.lang.String r6 = " "
            r2.append(r6)     // Catch: java.lang.Throwable -> L4c
            if (r0 == 0) goto L4e
            java.lang.String r6 = "enabled"
            goto L51
        L4c:
            r0 = move-exception
            goto L5f
        L4e:
            java.lang.String r6 = "disabled"
        L51:
            r2.append(r6)     // Catch: java.lang.Throwable -> L4c
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L4c
            android.util.Log.i(r4, r2)     // Catch: java.lang.Throwable -> L4c
            goto L71
        L5c:
            int r6 = r6 + 1
            goto L23
        L5f:
            java.lang.String r0 = r0.toString()
            android.util.Log.e(r4, r0)
        L66:
            java.lang.String r0 = "failed to find service "
            java.lang.String r0 = r0.concat(r3)
            android.util.Log.i(r4, r0)
            r0 = r5
        L71:
            if (r0 != 0) goto L84
            java.lang.String r9 = r1.getServiceName()
            java.lang.String r0 = "failed to find "
            java.lang.String r9 = r0.concat(r9)
            java.lang.String r0 = "BindServiceImpl"
            android.util.Log.i(r0, r9)
            return r5
        L84:
            com.android.server.enterprise.plm.impl.ConnectionHelper r9 = r9.getConnectionHelper()
            r9.getClass()
            com.android.server.enterprise.plm.impl.ConnectionHelper$$ExternalSyntheticLambda0 r0 = new com.android.server.enterprise.plm.impl.ConnectionHelper$$ExternalSyntheticLambda0
            r1 = 1
            r0.<init>(r9, r1)
            r9.post(r0)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.plm.impl.BindServiceImpl.start():boolean");
    }
}
