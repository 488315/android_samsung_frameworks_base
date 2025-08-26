package com.android.systemui.appops;

/* loaded from: classes.dex */
public interface AppOpsController {

    public interface Callback {
        void onActiveStateChanged(String str, int i, int i2, boolean z);
    }
}
