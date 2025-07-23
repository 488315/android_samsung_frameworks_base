package com.samsung.android.jdsms;

import android.os.Process;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes6.dex */
final class UidAllowList {
    private static final Set<String> mAllowList;

    UidAllowList() {
    }

    static {
        HashSet hashSet = new HashSet();
        hashSet.add("OEM_UID:" + Integer.toString(Process.DSMS_UID));
        hashSet.add("android.uid.dsms:" + Integer.toString(Process.DSMS_UID));
        hashSet.add("android.uid.system:" + Integer.toString(1000));
        mAllowList = Collections.unmodifiableSet(hashSet);
    }

    boolean containsUid(String str) {
        return mAllowList.contains(str);
    }
}
