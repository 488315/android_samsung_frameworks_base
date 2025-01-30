package com.android.systemui.statusbar.policy;

import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
