package com.android.systemui.statusbar.policy;

import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class SmartReplyStateInflaterKt {
    public static final ThreadPoolExecutor iconTaskThreadPool = new ThreadPoolExecutor(0, 25, 1, TimeUnit.MINUTES, new SynchronousQueue());
    public static final boolean DEBUG = Log.isLoggable("SmartReplyViewInflater", 3);

    public static final boolean shouldShowSmartReplyView(NotificationEntry notificationEntry, InflatedSmartReplyState inflatedSmartReplyState) {
        if ((inflatedSmartReplyState.smartReplies == null && inflatedSmartReplyState.smartActions == null) || notificationEntry.mSbn.getNotification().extras.getBoolean("android.remoteInputSpinner", false)) {
            return false;
        }
        return !notificationEntry.mSbn.getNotification().extras.getBoolean("android.hideSmartReplies", false);
    }
}
