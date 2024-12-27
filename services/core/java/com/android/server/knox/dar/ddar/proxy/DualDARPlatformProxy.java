package com.android.server.knox.dar.ddar.proxy;

import android.content.Context;
import com.android.server.knox.dar.ddar.DDLog;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;
import com.samsung.android.knox.ddar.IDualDARPolicy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DualDARPlatformProxy extends IProxyAgentService {
    public static DualDARPlatformProxy mInstance;
    public final Context mContext;
    public IDualDARPolicy mDualDARPolicyService = null;

    public DualDARPlatformProxy(Context context) {
        DDLog.d("DualDARPlatformProxy", "DualDARPlatformProxy() constructor", new Object[0]);
        this.mContext = context;
    }

    public static synchronized DualDARPlatformProxy getInstance(Context context) {
        DualDARPlatformProxy dualDARPlatformProxy;
        synchronized (DualDARPlatformProxy.class) {
            try {
                if (mInstance == null) {
                    mInstance = new DualDARPlatformProxy(context);
                }
                dualDARPlatformProxy = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return dualDARPlatformProxy;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x003e, code lost:
    
        if (r6.equals("SET_CLIENT_INFO") != false) goto L17;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0079 A[Catch: Exception -> 0x0036, TryCatch #0 {Exception -> 0x0036, blocks: (B:3:0x0003, B:12:0x004c, B:14:0x0050, B:15:0x005a, B:19:0x0079, B:21:0x007d, B:22:0x0087, B:24:0x002c, B:27:0x0038), top: B:2:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle onMessage(int r5, java.lang.String r6, final android.os.Bundle r7) {
        /*
            r4 = this;
            java.lang.String r5 = "onMessage() "
            java.lang.String r0 = "DualDARPlatformProxy"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L36
            r1.<init>(r5)     // Catch: java.lang.Exception -> L36
            r1.append(r6)     // Catch: java.lang.Exception -> L36
            java.lang.String r5 = r1.toString()     // Catch: java.lang.Exception -> L36
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]     // Catch: java.lang.Exception -> L36
            com.android.server.knox.dar.ddar.DDLog.d(r0, r5, r2)     // Catch: java.lang.Exception -> L36
            android.os.Bundle r5 = new android.os.Bundle     // Catch: java.lang.Exception -> L36
            r5.<init>()     // Catch: java.lang.Exception -> L36
            int r0 = r6.hashCode()     // Catch: java.lang.Exception -> L36
            r2 = -1735614427(0xffffffff988ca025, float:-3.6350856E-24)
            r3 = 1
            if (r0 == r2) goto L38
            r1 = -566780687(0xffffffffde379cf1, float:-3.3076787E18)
            if (r0 == r1) goto L2c
            goto L41
        L2c:
            java.lang.String r0 = "GET_DUALDAR_CONFIG"
            boolean r6 = r6.equals(r0)     // Catch: java.lang.Exception -> L36
            if (r6 == 0) goto L41
            r1 = r3
            goto L42
        L36:
            r4 = move-exception
            goto L99
        L38:
            java.lang.String r0 = "SET_CLIENT_INFO"
            boolean r6 = r6.equals(r0)     // Catch: java.lang.Exception -> L36
            if (r6 == 0) goto L41
            goto L42
        L41:
            r1 = -1
        L42:
            java.lang.String r6 = "DualDARPolicy"
            java.lang.String r0 = "dual_dar_response"
            if (r1 == 0) goto L79
            if (r1 == r3) goto L4c
            goto L98
        L4c:
            com.samsung.android.knox.ddar.IDualDARPolicy r5 = r4.mDualDARPolicyService     // Catch: java.lang.Exception -> L36
            if (r5 != 0) goto L5a
            android.os.IBinder r5 = android.os.ServiceManager.getService(r6)     // Catch: java.lang.Exception -> L36
            com.samsung.android.knox.ddar.IDualDARPolicy r5 = com.samsung.android.knox.ddar.IDualDARPolicy.Stub.asInterface(r5)     // Catch: java.lang.Exception -> L36
            r4.mDualDARPolicyService = r5     // Catch: java.lang.Exception -> L36
        L5a:
            com.samsung.android.knox.ddar.IDualDARPolicy r5 = r4.mDualDARPolicyService     // Catch: java.lang.Exception -> L36
            java.util.Optional r5 = java.util.Optional.ofNullable(r5)     // Catch: java.lang.Exception -> L36
            com.android.server.knox.dar.ddar.proxy.DualDARPlatformProxy$$ExternalSyntheticLambda1 r6 = new com.android.server.knox.dar.ddar.proxy.DualDARPlatformProxy$$ExternalSyntheticLambda1     // Catch: java.lang.Exception -> L36
            r6.<init>()     // Catch: java.lang.Exception -> L36
            java.util.Optional r4 = r5.map(r6)     // Catch: java.lang.Exception -> L36
            android.os.Bundle r5 = new android.os.Bundle     // Catch: java.lang.Exception -> L36
            r5.<init>()     // Catch: java.lang.Exception -> L36
            java.lang.Object r4 = r4.orElse(r5)     // Catch: java.lang.Exception -> L36
            r5 = r4
            android.os.Bundle r5 = (android.os.Bundle) r5     // Catch: java.lang.Exception -> L36
            r5.putBoolean(r0, r3)     // Catch: java.lang.Exception -> L36
            goto L98
        L79:
            com.samsung.android.knox.ddar.IDualDARPolicy r1 = r4.mDualDARPolicyService     // Catch: java.lang.Exception -> L36
            if (r1 != 0) goto L87
            android.os.IBinder r6 = android.os.ServiceManager.getService(r6)     // Catch: java.lang.Exception -> L36
            com.samsung.android.knox.ddar.IDualDARPolicy r6 = com.samsung.android.knox.ddar.IDualDARPolicy.Stub.asInterface(r6)     // Catch: java.lang.Exception -> L36
            r4.mDualDARPolicyService = r6     // Catch: java.lang.Exception -> L36
        L87:
            com.samsung.android.knox.ddar.IDualDARPolicy r6 = r4.mDualDARPolicyService     // Catch: java.lang.Exception -> L36
            java.util.Optional r6 = java.util.Optional.ofNullable(r6)     // Catch: java.lang.Exception -> L36
            com.android.server.knox.dar.ddar.proxy.DualDARPlatformProxy$$ExternalSyntheticLambda0 r1 = new com.android.server.knox.dar.ddar.proxy.DualDARPlatformProxy$$ExternalSyntheticLambda0     // Catch: java.lang.Exception -> L36
            r1.<init>()     // Catch: java.lang.Exception -> L36
            r6.ifPresent(r1)     // Catch: java.lang.Exception -> L36
            r5.putBoolean(r0, r3)     // Catch: java.lang.Exception -> L36
        L98:
            return r5
        L99:
            r4.printStackTrace()
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.ddar.proxy.DualDARPlatformProxy.onMessage(int, java.lang.String, android.os.Bundle):android.os.Bundle");
    }
}
