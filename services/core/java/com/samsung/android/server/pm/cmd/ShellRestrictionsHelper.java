package com.samsung.android.server.pm.cmd;

import android.os.Binder;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ShellRestrictionsHelper {
    public final List mCmdBlockClearOnlyList;
    public final List mCmdBlockList;
    public final List mCmdKnoxBlockList;

    public ShellRestrictionsHelper() {
        ArrayList arrayList = new ArrayList();
        this.mCmdBlockList = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mCmdKnoxBlockList = arrayList2;
        ArrayList arrayList3 = new ArrayList();
        this.mCmdBlockClearOnlyList = arrayList3;
        arrayList.add("com.tmobile.echolocate");
        arrayList2.add("com.samsung.klmsagent");
        arrayList2.add("com.samsung.android.knox.er");
        arrayList2.add("com.samsung.android.knox.kfbp");
        arrayList2.add("com.samsung.android.knox.knnr");
        arrayList2.add("com.samsung.android.knox.sandbox");
        arrayList2.add("com.samsung.android.kgclient");
        arrayList2.add("com.sec.enterprise.knox.cloudmdm.smdms");
        arrayList2.add("com.samsung.android.knox.app.networkfilter");
        arrayList2.add("com.knox.vpn.proxyhandler");
        arrayList3.add("co.sitic.pp");
    }

    public final boolean isRestrictedPackage(int i, String str) {
        if (Binder.getCallingUid() == 0) {
            return false;
        }
        if (((i & 4) == 0 || !((ArrayList) this.mCmdBlockClearOnlyList).contains(str)) && !((ArrayList) this.mCmdKnoxBlockList).contains(str)) {
            return (i & 2) != 0 && ((ArrayList) this.mCmdBlockList).contains(str);
        }
        return true;
    }
}
