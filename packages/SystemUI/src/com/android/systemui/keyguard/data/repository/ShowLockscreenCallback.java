package com.android.systemui.keyguard.data.repository;

import android.os.IRemoteCallback;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShowLockscreenCallback {
    public final IRemoteCallback remoteCallback;
    public final int userId;

    public ShowLockscreenCallback(int i, IRemoteCallback iRemoteCallback) {
        this.userId = i;
        this.remoteCallback = iRemoteCallback;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShowLockscreenCallback)) {
            return false;
        }
        ShowLockscreenCallback showLockscreenCallback = (ShowLockscreenCallback) obj;
        return this.userId == showLockscreenCallback.userId && Intrinsics.areEqual(this.remoteCallback, showLockscreenCallback.remoteCallback);
    }

    public final int hashCode() {
        return this.remoteCallback.hashCode() + (Integer.hashCode(this.userId) * 31);
    }

    public final String toString() {
        return "ShowLockscreenCallback(userId=" + this.userId + ", remoteCallback=" + this.remoteCallback + ")";
    }
}
