package com.samsung.android.server.pm.install;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class SilentUninstallerList {
    public final List allowedSilentUninstallers = Arrays.asList("com.samsung.android.sm.devicesecurity", "com.samsung.android.sm.devicesecurity.tcm", "com.samsung.android.asksmanager", "com.samsung.android.lool", "com.samsung.android.sm_cn", "com.ws.dm", "com.samsung.knox.securefolder", "com.android.managedprovisioning", "com.samsung.android.app.watchmanagerstub", "com.sec.android.AutoPreconfig", "com.sec.android.app.myfiles", "com.sec.android.app.samsungapps", "com.samsung.android.cidmanager", "com.sec.android.app.camera", "com.sec.android.mimage.photoretouching", "com.sec.android.app.vepreload");

    public boolean isCallerAllowedSilentlyInstall(int i, Function function) {
        Iterator it = this.allowedSilentUninstallers.iterator();
        while (it.hasNext()) {
            if (i == ((Integer) function.apply((String) it.next())).intValue()) {
                return true;
            }
        }
        return false;
    }
}
