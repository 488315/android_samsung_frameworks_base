package com.android.server.knox.dar.ddar;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Process;
import android.os.ServiceManager;
import android.os.UserHandle;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DDCache extends IProxyAgentService {
    public static volatile DDCache _instance;
    public ISemPersonaManager semPersonaManager;

    public static void enforceCallingUser(int i) {
        if (UserHandle.getAppId(i) != 5250 && UserHandle.getAppId(i) != 1000 && UserHandle.getAppId(i) != Process.myUid()) {
            throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Can only be called by system user. callingUid: "));
        }
    }

    public static synchronized DDCache getInstance() {
        DDCache dDCache;
        synchronized (DDCache.class) {
            try {
                if (_instance == null) {
                    _instance = new DDCache();
                }
                dDCache = _instance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return dDCache;
    }

    public final String get(int i, String str) {
        try {
            return getPersonaService().getPersonaCacheValue("DUALDAR_User_" + i + "_" + str);
        } catch (Exception e) {
            e.printStackTrace();
            DDLog.e("DDCache", "get value failed", new Object[0]);
            return null;
        }
    }

    public final ISemPersonaManager getPersonaService() {
        if (this.semPersonaManager == null) {
            try {
                this.semPersonaManager = ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.semPersonaManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0088 A[Catch: Exception -> 0x003c, TRY_LEAVE, TryCatch #0 {Exception -> 0x003c, blocks: (B:3:0x0004, B:15:0x0063, B:18:0x0075, B:19:0x0088, B:20:0x0033, B:23:0x003e, B:26:0x0048), top: B:2:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle onMessage(int r9, java.lang.String r10, android.os.Bundle r11) {
        /*
            r8 = this;
            java.lang.String r0 = "onMessage() "
            r1 = 0
            java.lang.String r2 = "DDCache"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L3c
            r3.<init>(r0)     // Catch: java.lang.Exception -> L3c
            r3.append(r10)     // Catch: java.lang.Exception -> L3c
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Exception -> L3c
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch: java.lang.Exception -> L3c
            com.android.server.knox.dar.ddar.DDLog.d(r2, r0, r4)     // Catch: java.lang.Exception -> L3c
            android.os.Bundle r0 = new android.os.Bundle     // Catch: java.lang.Exception -> L3c
            r0.<init>()     // Catch: java.lang.Exception -> L3c
            int r2 = r10.hashCode()     // Catch: java.lang.Exception -> L3c
            r4 = -227631335(0xfffffffff26e9f19, float:-4.7263853E30)
            r5 = 2
            r6 = 1
            if (r2 == r4) goto L48
            r4 = 180589038(0xac391ee, float:1.883271E-32)
            if (r2 == r4) goto L3e
            r4 = 1729581666(0x67175262, float:7.14597E23)
            if (r2 == r4) goto L33
            goto L52
        L33:
            java.lang.String r2 = "SET_DATA_CMD"
            boolean r10 = r10.equals(r2)     // Catch: java.lang.Exception -> L3c
            if (r10 == 0) goto L52
            goto L53
        L3c:
            r8 = move-exception
            goto L9e
        L3e:
            java.lang.String r2 = "GET_DATA_CMD"
            boolean r10 = r10.equals(r2)     // Catch: java.lang.Exception -> L3c
            if (r10 == 0) goto L52
            r3 = r6
            goto L53
        L48:
            java.lang.String r2 = "DELETE_DATA_CMD"
            boolean r10 = r10.equals(r2)     // Catch: java.lang.Exception -> L3c
            if (r10 == 0) goto L52
            r3 = r5
            goto L53
        L52:
            r3 = -1
        L53:
            java.lang.String r10 = "DUAL_DAR_VALUE"
            java.lang.String r2 = "dual_dar_response"
            java.lang.String r4 = "DUAL_DAR_KEY"
            java.lang.String r7 = "DUAL_DAR_USER_ID"
            if (r3 == 0) goto L88
            if (r3 == r6) goto L75
            if (r3 == r5) goto L63
            goto L9d
        L63:
            enforceCallingUser(r9)     // Catch: java.lang.Exception -> L3c
            int r9 = r11.getInt(r7)     // Catch: java.lang.Exception -> L3c
            java.lang.String r10 = r11.getString(r4)     // Catch: java.lang.Exception -> L3c
            r8.set(r9, r10, r1)     // Catch: java.lang.Exception -> L3c
            r0.putBoolean(r2, r6)     // Catch: java.lang.Exception -> L3c
            goto L9d
        L75:
            int r9 = r11.getInt(r7)     // Catch: java.lang.Exception -> L3c
            java.lang.String r11 = r11.getString(r4)     // Catch: java.lang.Exception -> L3c
            java.lang.String r8 = r8.get(r9, r11)     // Catch: java.lang.Exception -> L3c
            r0.putString(r10, r8)     // Catch: java.lang.Exception -> L3c
            r0.putBoolean(r2, r6)     // Catch: java.lang.Exception -> L3c
            goto L9d
        L88:
            enforceCallingUser(r9)     // Catch: java.lang.Exception -> L3c
            int r9 = r11.getInt(r7)     // Catch: java.lang.Exception -> L3c
            java.lang.String r3 = r11.getString(r4)     // Catch: java.lang.Exception -> L3c
            java.lang.String r10 = r11.getString(r10)     // Catch: java.lang.Exception -> L3c
            r8.set(r9, r3, r10)     // Catch: java.lang.Exception -> L3c
            r0.putBoolean(r2, r6)     // Catch: java.lang.Exception -> L3c
        L9d:
            return r0
        L9e:
            r8.printStackTrace()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.ddar.DDCache.onMessage(int, java.lang.String, android.os.Bundle):android.os.Bundle");
    }

    public final void set(int i, String str, String str2) {
        try {
            getPersonaService().updatePersonaCache("DUALDAR_User_" + i + "_" + str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            DDLog.e("DDCache", "set value failed", new Object[0]);
        }
    }
}
