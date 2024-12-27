package com.android.server.accessibility.magnification;

import android.R;
import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;

public final class WindowMagnificationPromptController {
    static final String ACTION_DISMISS = "com.android.server.accessibility.magnification.action.DISMISS";
    static final String ACTION_TURN_ON_IN_SETTINGS = "com.android.server.accessibility.magnification.action.TURN_ON_IN_SETTINGS";
    public static final Uri MAGNIFICATION_WINDOW_MODE_PROMPT_URI = Settings.Secure.getUriFor("accessibility_show_window_magnification_prompt");
    public final AnonymousClass1 mContentObserver;
    public final Context mContext;
    public boolean mNeedToShowNotification;
    BroadcastReceiver mNotificationActionReceiver;
    public final NotificationManager mNotificationManager;
    public final int mUserId;

    public final class NotificationActionReceiver extends BroadcastReceiver {
        public NotificationActionReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }
            WindowMagnificationPromptController windowMagnificationPromptController = WindowMagnificationPromptController.this;
            windowMagnificationPromptController.mNeedToShowNotification = false;
            Settings.Secure.putIntForUser(windowMagnificationPromptController.mContext.getContentResolver(), "accessibility_show_window_magnification_prompt", 0, WindowMagnificationPromptController.this.mUserId);
            if (!WindowMagnificationPromptController.ACTION_TURN_ON_IN_SETTINGS.equals(action)) {
                if (WindowMagnificationPromptController.ACTION_DISMISS.equals(action)) {
                    WindowMagnificationPromptController windowMagnificationPromptController2 = WindowMagnificationPromptController.this;
                    BroadcastReceiver broadcastReceiver = windowMagnificationPromptController2.mNotificationActionReceiver;
                    if (broadcastReceiver != null) {
                        windowMagnificationPromptController2.mContext.unregisterReceiver(broadcastReceiver);
                        windowMagnificationPromptController2.mNotificationActionReceiver = null;
                    }
                    windowMagnificationPromptController2.mNotificationManager.cancel(1004);
                    return;
                }
                return;
            }
            WindowMagnificationPromptController windowMagnificationPromptController3 = WindowMagnificationPromptController.this;
            windowMagnificationPromptController3.getClass();
            Intent intent2 = new Intent("android.settings.ACCESSIBILITY_DETAILS_SETTINGS");
            intent2.addFlags(268468224);
            intent2.putExtra("android.intent.extra.COMPONENT_NAME", AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME.flattenToShortString());
            intent2.addFlags(268435456);
            windowMagnificationPromptController3.mContext.startActivityAsUser(intent2, ActivityOptions.makeBasic().setLaunchDisplayId(windowMagnificationPromptController3.mContext.getDisplayId()).toBundle(), UserHandle.of(windowMagnificationPromptController3.mUserId));
            ((StatusBarManager) windowMagnificationPromptController3.mContext.getSystemService(StatusBarManager.class)).collapsePanels();
            WindowMagnificationPromptController windowMagnificationPromptController4 = WindowMagnificationPromptController.this;
            BroadcastReceiver broadcastReceiver2 = windowMagnificationPromptController4.mNotificationActionReceiver;
            if (broadcastReceiver2 != null) {
                windowMagnificationPromptController4.mContext.unregisterReceiver(broadcastReceiver2);
                windowMagnificationPromptController4.mNotificationActionReceiver = null;
            }
            windowMagnificationPromptController4.mNotificationManager.cancel(1004);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.database.ContentObserver, com.android.server.accessibility.magnification.WindowMagnificationPromptController$1] */
    public WindowMagnificationPromptController(Context context, int i) {
        this.mContext = context;
        this.mNotificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mUserId = i;
        ?? r0 = new ContentObserver() { // from class: com.android.server.accessibility.magnification.WindowMagnificationPromptController.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                super.onChange(z);
                WindowMagnificationPromptController.this.onPromptSettingsValueChanged();
            }
        };
        this.mContentObserver = r0;
        context.getContentResolver().registerContentObserver(MAGNIFICATION_WINDOW_MODE_PROMPT_URI, false, r0, i);
        this.mNeedToShowNotification = Settings.Secure.getIntForUser(context.getContentResolver(), "accessibility_show_window_magnification_prompt", 0, i) == 1;
    }

    public final PendingIntent createPendingIntent(String str) {
        Intent intent = new Intent(str);
        intent.setPackage(this.mContext.getPackageName());
        return PendingIntent.getBroadcast(this.mContext, 0, intent, 67108864);
    }

    public void onDestroy() {
        BroadcastReceiver broadcastReceiver = this.mNotificationActionReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
            this.mNotificationActionReceiver = null;
        }
        this.mNotificationManager.cancel(1004);
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    public void onPromptSettingsValueChanged() {
        boolean z = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_show_window_magnification_prompt", 0, this.mUserId) == 1;
        if (this.mNeedToShowNotification == z) {
            return;
        }
        this.mNeedToShowNotification = z;
        if (z) {
            return;
        }
        BroadcastReceiver broadcastReceiver = this.mNotificationActionReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
            this.mNotificationActionReceiver = null;
        }
        this.mNotificationManager.cancel(1004);
    }

    public final void showNotificationIfNeeded() {
        if (this.mNeedToShowNotification) {
            Notification.Builder builder = new Notification.Builder(this.mContext, SystemNotificationChannels.ACCESSIBILITY_MAGNIFICATION);
            String string = this.mContext.getString(17043616);
            builder.setSmallIcon(R.drawable.ic_battery).setContentTitle(this.mContext.getString(17043617)).setContentText(string).setLargeIcon(Icon.createWithResource(this.mContext, R.drawable.ic_bt_misc_hid)).setTicker(this.mContext.getString(17043617)).setOnlyAlertOnce(true).setStyle(new Notification.BigTextStyle().bigText(string)).setDeleteIntent(createPendingIntent(ACTION_DISMISS)).setContentIntent(createPendingIntent(ACTION_TURN_ON_IN_SETTINGS)).setActions(new Notification.Action.Builder((Icon) null, this.mContext.getString(17043260), createPendingIntent(ACTION_TURN_ON_IN_SETTINGS)).build());
            this.mNotificationManager.notify(1004, builder.build());
            if (this.mNotificationActionReceiver != null) {
                return;
            }
            this.mNotificationActionReceiver = new NotificationActionReceiver();
            this.mContext.registerReceiver(this.mNotificationActionReceiver, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(ACTION_DISMISS, ACTION_TURN_ON_IN_SETTINGS), "android.permission.MANAGE_ACCESSIBILITY", null, 2);
        }
    }
}
