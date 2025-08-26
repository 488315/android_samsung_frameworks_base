package com.android.launcher3.util;

import android.os.UserHandle;

/* loaded from: classes.dex */
public class UserIconInfo {
    public final int type;

    public UserIconInfo(UserHandle userHandle, int i) {
        this(userHandle, i, userHandle != null ? userHandle.hashCode() : 0L);
    }

    public UserIconInfo(UserHandle userHandle, int i, long j) {
        this.type = i;
    }
}
