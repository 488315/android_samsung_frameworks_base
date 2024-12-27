package com.android.systemui.aibrief.control;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat$Builder;
import androidx.core.app.NotificationManagerCompat;
import com.android.systemui.R;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BriefNotificationController {
    public static final String NOTIFICATION_CHANNEL_ID = "AI_BRIEF_CHANNEL_ID";
    public static final String NOTIFICATION_CHANNEL_NAME = "AI_BRIEF_CHANNEL_NAME";
    public static final int NOTIFICATION_ID = 123456;
    private final Context context;
    private final NotificationManagerCompat notificationManager;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BriefNotificationController(Context context, NotificationManagerCompat notificationManagerCompat) {
        this.context = context;
        this.notificationManager = notificationManagerCompat;
    }

    private final void notifyNotification(String str, String str2) {
        Intent intent = new Intent();
        intent.setClassName(BriefNowBarController.SUGGESTION_PACKAGE, BriefNowBarController.SUGGESTION_ACTIVITY);
        PendingIntent activity = PendingIntent.getActivity(this.context, 0, intent, QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING);
        NotificationCompat$Builder notificationCompat$Builder = new NotificationCompat$Builder(this.context, NOTIFICATION_CHANNEL_ID);
        notificationCompat$Builder.mNotification.icon = R.drawable.ai_brief_main_image;
        notificationCompat$Builder.mContentTitle = NotificationCompat$Builder.limitCharSequenceLength(str);
        notificationCompat$Builder.mContentText = NotificationCompat$Builder.limitCharSequenceLength(str2);
        notificationCompat$Builder.mContentIntent = activity;
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, 3);
        notificationChannel.setShowBadge(true);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(-65536);
        notificationChannel.enableVibration(true);
        try {
            this.notificationManager.mNotificationManager.createNotificationChannel(notificationChannel);
            this.notificationManager.notify(NOTIFICATION_ID, notificationCompat$Builder.build());
        } catch (Exception unused) {
        }
    }

    private final void removeNotification() {
        try {
            this.notificationManager.mNotificationManager.cancel(null, NOTIFICATION_ID);
        } catch (Exception unused) {
        }
    }

    public final void hideNotification() {
        removeNotification();
    }

    public final void showNotification() {
        notifyNotification(this.context.getResources().getString(R.string.ai_brief_basic_title), this.context.getResources().getString(R.string.ai_brief_nowbar_sub_title_morning));
    }
}
