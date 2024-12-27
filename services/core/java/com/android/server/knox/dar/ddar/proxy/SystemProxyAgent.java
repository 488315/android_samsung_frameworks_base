package com.android.server.knox.dar.ddar.proxy;

import android.content.Context;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyAgent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemProxyAgent extends KnoxProxyAgent {
    public static SystemProxyAgent mInstance;
    public final Context mContext;

    public SystemProxyAgent(Context context) {
        this.mContext = context;
    }

    public static synchronized SystemProxyAgent getInstance(Context context) {
        SystemProxyAgent systemProxyAgent;
        synchronized (SystemProxyAgent.class) {
            try {
                if (mInstance == null) {
                    mInstance = new SystemProxyAgent(context);
                }
                systemProxyAgent = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return systemProxyAgent;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0056 A[Catch: Exception -> 0x0029, TRY_LEAVE, TryCatch #0 {Exception -> 0x0029, blocks: (B:3:0x000f, B:12:0x003f, B:15:0x0044, B:18:0x0056, B:19:0x001f, B:22:0x002b), top: B:2:0x000f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle relay(int r7, java.lang.String r8, java.lang.String r9, android.os.Bundle r10) {
        /*
            r6 = this;
            java.lang.String r0 = "relay to Service : "
            java.lang.String r0 = android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(r0, r8)
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.String r3 = "SystemProxyAgent"
            com.android.server.knox.dar.ddar.DDLog.d(r3, r0, r2)
            int r0 = r9.hashCode()     // Catch: java.lang.Exception -> L29
            r2 = 636043837(0x25e9423d, float:4.0463942E-16)
            r4 = 1
            if (r0 == r2) goto L2b
            r2 = 681038700(0x2897d36c, float:1.6856057E-14)
            if (r0 == r2) goto L1f
            goto L35
        L1f:
            java.lang.String r0 = "TERMINATE_SECURE_SESSION"
            boolean r0 = r9.equals(r0)     // Catch: java.lang.Exception -> L29
            if (r0 == 0) goto L35
            r0 = r4
            goto L36
        L29:
            r6 = move-exception
            goto L6e
        L2b:
            java.lang.String r0 = "INITIALIZE_SECURE_SESSION"
            boolean r0 = r9.equals(r0)     // Catch: java.lang.Exception -> L29
            if (r0 == 0) goto L35
            r0 = r1
            goto L36
        L35:
            r0 = -1
        L36:
            java.lang.String r2 = "dual_dar_response"
            java.lang.String r5 = "SECURE_CLIENT_ID"
            if (r0 == 0) goto L56
            if (r0 == r4) goto L44
            android.os.Bundle r6 = super.relay(r7, r8, r9, r10)     // Catch: java.lang.Exception -> L29
            goto L6d
        L44:
            java.lang.String r10 = r10.getString(r5)     // Catch: java.lang.Exception -> L29
            boolean r6 = r6.teardownSecureSession(r7, r8, r10)     // Catch: java.lang.Exception -> L29
            android.os.Bundle r7 = new android.os.Bundle     // Catch: java.lang.Exception -> L29
            r7.<init>()     // Catch: java.lang.Exception -> L29
            r7.putBoolean(r2, r6)     // Catch: java.lang.Exception -> L29
        L54:
            r6 = r7
            goto L6d
        L56:
            java.lang.String r0 = r10.getString(r5)     // Catch: java.lang.Exception -> L29
            java.lang.String r4 = "SECURE_CLIENT_PUB_KEY"
            java.lang.String r10 = r10.getString(r4)     // Catch: java.lang.Exception -> L29
            java.lang.String r6 = r6.establishSecureSession(r7, r8, r0, r10)     // Catch: java.lang.Exception -> L29
            android.os.Bundle r7 = new android.os.Bundle     // Catch: java.lang.Exception -> L29
            r7.<init>()     // Catch: java.lang.Exception -> L29
            r7.putString(r2, r6)     // Catch: java.lang.Exception -> L29
            goto L54
        L6d:
            return r6
        L6e:
            java.lang.String r7 = "RemoteException: name:"
            java.lang.String r10 = " command:"
            java.lang.String r7 = com.android.server.BootReceiver$$ExternalSyntheticOutline0.m(r7, r8, r10, r9)
            java.lang.Object[] r8 = new java.lang.Object[r1]
            com.android.server.knox.dar.ddar.DDLog.e(r3, r7, r8)
            r6.printStackTrace()
            r6 = 0
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.ddar.proxy.SystemProxyAgent.relay(int, java.lang.String, java.lang.String, android.os.Bundle):android.os.Bundle");
    }
}
