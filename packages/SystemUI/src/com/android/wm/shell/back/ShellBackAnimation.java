package com.android.wm.shell.back;

import android.window.BackNavigationInfo;

/* loaded from: classes3.dex */
public abstract class ShellBackAnimation {
    public abstract BackAnimationRunner getRunner();

    public boolean prepareNextAnimation(BackNavigationInfo.CustomAnimationInfo customAnimationInfo, int i) {
        return false;
    }

    public void onConfigurationChanged() {
    }
}
