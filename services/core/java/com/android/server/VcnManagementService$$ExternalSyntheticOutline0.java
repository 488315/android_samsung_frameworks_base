package com.android.server;

import android.content.IntentFilter;

public abstract /* synthetic */ class VcnManagementService$$ExternalSyntheticOutline0 {
    public static IntentFilter m(String str, String str2, String str3, String str4, String str5) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(str);
        intentFilter.addAction(str2);
        intentFilter.addAction(str3);
        intentFilter.addAction(str4);
        intentFilter.addAction(str5);
        return intentFilter;
    }
}
