package com.android.wm.shell.sysui;

import android.content.Context;
import java.util.List;

/* loaded from: classes3.dex */
public interface UserChangeListener {
    void onUserChanged(int i, Context context);

    default void onBeforeUserSwitching(int i) {
    }

    default void onUserProfilesChanged(List list) {
    }
}
