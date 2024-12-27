package com.android.systemui.statusbar.notification;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.provider.DeviceConfig;
import android.service.notification.NotificationListenerService;
import android.util.SparseArray;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AssistantFeedbackController {
    public volatile boolean mFeedbackEnabled;
    public final Handler mHandler;
    public final SparseArray mIcons;
    public final AnonymousClass1 mPropertiesChangedListener;

    public AssistantFeedbackController(Handler handler, Context context, DeviceConfigProxy deviceConfigProxy) {
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.statusbar.notification.AssistantFeedbackController.1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("enable_nas_feedback")) {
                    AssistantFeedbackController.this.mFeedbackEnabled = properties.getBoolean("enable_nas_feedback", false);
                }
            }
        };
        this.mHandler = handler;
        this.mFeedbackEnabled = deviceConfigProxy.getBoolean("systemui", "enable_nas_feedback", false);
        deviceConfigProxy.addOnPropertiesChangedListener("systemui", new Executor() { // from class: com.android.systemui.statusbar.notification.AssistantFeedbackController$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                AssistantFeedbackController.this.mHandler.post(runnable);
            }
        }, onPropertiesChangedListener);
        SparseArray sparseArray = new SparseArray(4);
        this.mIcons = sparseArray;
        sparseArray.set(1, new FeedbackIcon(R.drawable.ic_launcher_android, R.string.share_remote_bugreport_action));
        sparseArray.set(2, new FeedbackIcon(R.drawable.ic_lock_airplane_mode_alpha, R.string.shareactionprovider_share_with));
        sparseArray.set(3, new FeedbackIcon(R.drawable.ic_lock_airplane_mode_off, R.string.share_remote_bugreport_notification_title));
        sparseArray.set(4, new FeedbackIcon(R.drawable.ic_lock, R.string.share_remote_bugreport_notification_message_finished));
    }

    public final int getFeedbackStatus(NotificationEntry notificationEntry) {
        if (!this.mFeedbackEnabled) {
            return 0;
        }
        NotificationListenerService.Ranking ranking = notificationEntry.mRanking;
        int importance = ranking.getChannel().getImportance();
        int importance2 = ranking.getImportance();
        if (importance < 3 && importance2 >= 3) {
            return 1;
        }
        if (importance >= 3 && importance2 < 3) {
            return 2;
        }
        if (importance < importance2 || ranking.getRankingAdjustment() == 1) {
            return 3;
        }
        return (importance > importance2 || ranking.getRankingAdjustment() == -1) ? 4 : 0;
    }
}
