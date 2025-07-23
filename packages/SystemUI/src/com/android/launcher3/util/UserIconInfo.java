package com.android.launcher3.util;

import android.os.UserHandle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
