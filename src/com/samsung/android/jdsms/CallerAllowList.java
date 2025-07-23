package com.samsung.android.jdsms;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes6.dex */
final class CallerAllowList {
    private static final Set<String> mAllowList;

    CallerAllowList() {
    }

    static {
        HashSet hashSet = new HashSet();
        hashSet.add("com.android.server.ReactiveService$1.onReceive");
        hashSet.add("com.android.server.StorageManagerService.prepareUserStorageInternal");
        hashSet.add("com.android.server.devicepolicy.DevicePolicyManagerService.SendlogDSMS");
        hashSet.add("com.samsung.android.jdsms.DsmsService.sendMessage");
        mAllowList = Collections.unmodifiableSet(hashSet);
    }

    boolean contains(String str) {
        return mAllowList.contains(str);
    }
}
