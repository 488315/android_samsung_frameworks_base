package com.samsung.android.jdsms;

import android.p009os.Process;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes5.dex */
final class UidAllowList {
    private static final Set<String> mAllowList;

    UidAllowList() {
    }

    static {
        HashSet<String> modifiableSet = new HashSet<>();
        modifiableSet.add("OEM_UID:" + Integer.toString(Process.DSMS_UID));
        modifiableSet.add("android.uid.dsms:" + Integer.toString(Process.DSMS_UID));
        modifiableSet.add("android.uid.system:" + Integer.toString(1000));
        mAllowList = Collections.unmodifiableSet(modifiableSet);
    }

    boolean containsUid(String uidName) {
        return mAllowList.contains(uidName);
    }
}
