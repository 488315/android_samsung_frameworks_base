package com.android.server.display.notifications;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Slog;

import com.android.internal.notification.SystemNotificationChannels;
import com.android.server.display.ExternalDisplayStatsService;
import com.android.server.display.feature.DisplayManagerFlags;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DisplayNotificationManager {
    public final boolean mConnectedDisplayErrorHandlingEnabled;
    public final Context mContext;
    public final ExternalDisplayStatsService mExternalDisplayStatsService;
    public final Injector mInjector;
    public NotificationManager mNotificationManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.notifications.DisplayNotificationManager$1, reason: invalid class name */
    public final class AnonymousClass1 implements Injector {
        public final /* synthetic */ Context val$context;
        public final /* synthetic */ DisplayManagerFlags val$flags;
        public final /* synthetic */ ExternalDisplayStatsService val$statsService;

        public AnonymousClass1(
                Context context,
                DisplayManagerFlags displayManagerFlags,
                ExternalDisplayStatsService externalDisplayStatsService) {
            this.val$context = context;
            this.val$flags = displayManagerFlags;
            this.val$statsService = externalDisplayStatsService;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Injector {}

    public DisplayNotificationManager(
            DisplayManagerFlags displayManagerFlags, Context context, Injector injector) {
        this.mConnectedDisplayErrorHandlingEnabled =
                displayManagerFlags.mConnectedDisplayErrorHandlingFlagState.isEnabled();
        this.mContext = context;
        this.mInjector = injector;
        this.mExternalDisplayStatsService = ((AnonymousClass1) injector).val$statsService;
    }

    public final Notification createErrorNotification(int i, int i2) {
        int i3;
        Resources resources = this.mContext.getResources();
        CharSequence text = resources.getText(R.string.fcError);
        CharSequence text2 = resources.getText(i);
        try {
            TypedArray obtainStyledAttributes =
                    this.mContext.obtainStyledAttributes(new int[] {R.attr.colorError});
            try {
                i3 = obtainStyledAttributes.getColor(0, 0);
                try {
                    obtainStyledAttributes.close();
                } catch (Resources.NotFoundException e) {
                    e = e;
                    Slog.e(
                            "DisplayNotificationManager",
                            "colorError attribute is not found: " + e.getMessage());
                    return new Notification.Builder(
                                    this.mContext, SystemNotificationChannels.ALERTS)
                            .setGroup("DisplayNotificationManager")
                            .setSmallIcon(i2)
                            .setWhen(0L)
                            .setTimeoutAfter(30000L)
                            .setOngoing(false)
                            .setTicker(text)
                            .setColor(i3)
                            .setContentTitle(text)
                            .setContentText(text2)
                            .setVisibility(1)
                            .setCategory("err")
                            .build();
                }
            } finally {
            }
        } catch (Resources.NotFoundException e2) {
            e = e2;
            i3 = 0;
            Slog.e(
                    "DisplayNotificationManager",
                    "colorError attribute is not found: " + e.getMessage());
            return new Notification.Builder(this.mContext, SystemNotificationChannels.ALERTS)
                    .setGroup("DisplayNotificationManager")
                    .setSmallIcon(i2)
                    .setWhen(0L)
                    .setTimeoutAfter(30000L)
                    .setOngoing(false)
                    .setTicker(text)
                    .setColor(i3)
                    .setContentTitle(text)
                    .setContentText(text2)
                    .setVisibility(1)
                    .setCategory("err")
                    .build();
        }
        return new Notification.Builder(this.mContext, SystemNotificationChannels.ALERTS)
                .setGroup("DisplayNotificationManager")
                .setSmallIcon(i2)
                .setWhen(0L)
                .setTimeoutAfter(30000L)
                .setOngoing(false)
                .setTicker(text)
                .setColor(i3)
                .setContentTitle(text)
                .setContentText(text2)
                .setVisibility(1)
                .setCategory("err")
                .build();
    }

    public final void onCableNotCapableDisplayPort() {
        if (!this.mConnectedDisplayErrorHandlingEnabled) {
            Slog.d(
                    "DisplayNotificationManager",
                    "onCableNotCapableDisplayPort: mConnectedDisplayErrorHandlingEnabled is false");
        } else {
            this.mExternalDisplayStatsService.logExternalDisplayError(13);
            sendErrorNotification(createErrorNotification(R.string.fcComplete, 17304836));
        }
    }

    public final void onDisplayPortLinkTrainingFailure() {
        if (!this.mConnectedDisplayErrorHandlingEnabled) {
            Slog.d(
                    "DisplayNotificationManager",
                    "onDisplayPortLinkTrainingFailure: mConnectedDisplayErrorHandlingEnabled is"
                        + " false");
        } else {
            this.mExternalDisplayStatsService.logExternalDisplayError(12);
            sendErrorNotification(createErrorNotification(R.string.fcComplete, 17304836));
        }
    }

    public final void onHotplugConnectionError() {
        if (!this.mConnectedDisplayErrorHandlingEnabled) {
            Slog.d(
                    "DisplayNotificationManager",
                    "onHotplugConnectionError: mConnectedDisplayErrorHandlingEnabled is false");
        } else {
            this.mExternalDisplayStatsService.logExternalDisplayError(11);
            sendErrorNotification(createErrorNotification(R.string.fcComplete, 17304836));
        }
    }

    public final void sendErrorNotification(Notification notification) {
        NotificationManager notificationManager = this.mNotificationManager;
        if (notificationManager == null) {
            Slog.e(
                    "DisplayNotificationManager",
                    "Can't sendErrorNotification: NotificationManager is null");
        } else {
            notificationManager.notify("DisplayNotificationManager", 1, notification);
        }
    }
}
