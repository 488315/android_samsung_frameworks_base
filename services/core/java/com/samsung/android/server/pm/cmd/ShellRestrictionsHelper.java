package com.samsung.android.server.pm.cmd;

import android.os.Binder;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class ShellRestrictionsHelper {
    public final List mCmdBlockList = new ArrayList();
    public final List mCmdKnoxBlockList = new ArrayList();

    public ShellRestrictionsHelper() {
        buildCmdBlockList();
    }

    public String hasRestrictedPackage(List list) {
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                String str = (String) list.get(i);
                if (isRestrictedPackage(str, true, false)) {
                    return str;
                }
            }
        }
        return null;
    }

    public boolean isRestrictedPackage(String str, boolean z, boolean z2) {
        if (Binder.getCallingUid() == 0) {
            return false;
        }
        if (z && this.mCmdKnoxBlockList.contains(str)) {
            return true;
        }
        return z2 && this.mCmdBlockList.contains(str);
    }

    public final void buildCmdBlockList() {
        this.mCmdBlockList.add("com.tmobile.echolocate");
        this.mCmdKnoxBlockList.add("com.samsung.klmsagent");
        this.mCmdKnoxBlockList.add(KnoxCustomManagerService.KG_PKG_NAME);
        this.mCmdKnoxBlockList.add("com.sec.enterprise.knox.cloudmdm.smdms");
    }
}
