package com.android.systemui.statusbar.notification.logging;

import android.app.StatsManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import java.util.concurrent.Executor;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationMemoryLogger implements StatsManager.StatsPullAtomCallback {
    public final Executor backgroundExecutor;
    public final CoroutineDispatcher mainDispatcher;
    public final NotifPipeline notificationPipeline;
    public final StatsManager statsManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NotificationMemoryUseAtomBuilder {
        public int bigPictureBitmapCount;
        public int bigPictureObject;
        public int count;
        public int countWithInflatedViews;
        public int customViews;
        public int extenders;
        public int extras;
        public int largeIconBitmapCount;
        public int largeIconObject;
        public int largeIconViews;
        public int smallIconBitmapCount;
        public int smallIconObject;
        public int smallIconViews;
        public int softwareBitmaps;
        public final int style;
        public int styleViews;
        public int systemIconViews;
        public final int uid;

        public NotificationMemoryUseAtomBuilder(int i, int i2) {
            this.uid = i;
            this.style = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NotificationMemoryUseAtomBuilder)) {
                return false;
            }
            NotificationMemoryUseAtomBuilder notificationMemoryUseAtomBuilder = (NotificationMemoryUseAtomBuilder) obj;
            return this.uid == notificationMemoryUseAtomBuilder.uid && this.style == notificationMemoryUseAtomBuilder.style;
        }

        public final int hashCode() {
            return Integer.hashCode(this.style) + (Integer.hashCode(this.uid) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("NotificationMemoryUseAtomBuilder(uid=");
            sb.append(this.uid);
            sb.append(", style=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.style, ")", sb);
        }
    }

    public NotificationMemoryLogger(NotifPipeline notifPipeline, StatsManager statsManager, CoroutineDispatcher coroutineDispatcher, Executor executor) {
        this.notificationPipeline = notifPipeline;
        this.statsManager = statsManager;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
    }

    public final List getAllNotificationsOnMainThread() {
        return (List) BuildersKt.runBlocking(this.mainDispatcher, new NotificationMemoryLogger$getAllNotificationsOnMainThread$1(this, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onPullAtom(int r25, java.util.List r26) {
        /*
            Method dump skipped, instructions count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger.onPullAtom(int, java.util.List):int");
    }
}
