package com.android.server.media;

import android.app.ForegroundServiceDelegationOptions;
import android.app.Notification;

import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class MediaSessionRecordImpl {
    public static final AtomicInteger sNextMediaSessionRecordId = new AtomicInteger(1);
    public final int mUniqueId = sNextMediaSessionRecordId.getAndIncrement();

    public abstract boolean checkPlaybackActiveState(boolean z);

    public abstract void close();

    public abstract void dump(PrintWriter printWriter, String str);

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null
                && (obj instanceof MediaSessionRecordImpl)
                && this.mUniqueId == ((MediaSessionRecordImpl) obj).mUniqueId;
    }

    public abstract void expireTempEngaged();

    public abstract ForegroundServiceDelegationOptions getForegroundServiceDelegationOptions();

    public abstract String getPackageName();

    public abstract int getSessionPolicies();

    public abstract int getUid();

    public abstract int getUserId();

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mUniqueId));
    }

    public abstract boolean isActive();

    public abstract boolean isClosed();

    public abstract boolean isLinkedToNotification(Notification notification);

    public abstract boolean isSystemPriority();
}
