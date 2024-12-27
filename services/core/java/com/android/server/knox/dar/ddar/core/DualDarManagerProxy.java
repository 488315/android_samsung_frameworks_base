package com.android.server.knox.dar.ddar.core;

import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;

import com.android.server.knox.dar.ddar.DDLog;

import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;

public final class DualDarManagerProxy extends IProxyAgentService {
    public static final boolean DEBUG;
    public static DualDarManagerProxy sInstance;
    public DualDarManagerImpl mDualDarManagerImpl;

    static {
        DEBUG =
                "eng".equals(SystemProperties.get("ro.build.type"))
                        || "userdebug".equals(SystemProperties.get("ro.build.type"));
    }

    public static void enforceCallingUser(int i) {
        if (UserHandle.getAppId(i) != 5250
                && UserHandle.getAppId(i) != 1000
                && UserHandle.getAppId(i) != Process.myUid()) {
            throw new SecurityException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i, "Can only be called by system user. callingUid: "));
        }
    }

    public static synchronized DualDarManagerProxy getInstance(Context context) {
        DualDarManagerProxy dualDarManagerProxy;
        synchronized (DualDarManagerProxy.class) {
            try {
                if (sInstance == null) {
                    DualDarManagerProxy dualDarManagerProxy2 = new DualDarManagerProxy();
                    DDLog.d("DualDarManagerProxy", "DualDarManagerProxy created", new Object[0]);
                    dualDarManagerProxy2.mDualDarManagerImpl =
                            new DualDarManagerImpl(new DualDarManagerImpl.Injector(context));
                    sInstance = dualDarManagerProxy2;
                }
                dualDarManagerProxy = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return dualDarManagerProxy;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x012b, code lost:

       return r0;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle onMessage(int r7, java.lang.String r8, android.os.Bundle r9) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.knox.dar.ddar.core.DualDarManagerProxy.onMessage(int,"
                    + " java.lang.String, android.os.Bundle):android.os.Bundle");
    }
}
