package com.android.wm.shell.sysui;

import android.content.Context;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface UserChangeListener {
    void onUserChanged(int i, Context context);

    default void onUserProfilesChanged(List list) {
    }
}
