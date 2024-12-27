package com.android.internal.util;

import android.app.Notification;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.SparseArray;

import java.util.Collection;
import java.util.Objects;

public class NotificationMessagingUtil {
    private static final String DEFAULT_SMS_APP_SETTING = "sms_default_application";
    private final Context mContext;
    private final SparseArray<String> mDefaultSmsApp = new SparseArray<>();
    private final ContentObserver mSmsContentObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.android.internal.util.NotificationMessagingUtil.1
                @Override // android.database.ContentObserver
                public void onChange(
                        boolean selfChange, Collection<Uri> uris, int flags, int userId) {
                    if (uris.contains(Settings.Secure.getUriFor("sms_default_application"))) {
                        NotificationMessagingUtil.this.cacheDefaultSmsApp(userId);
                    }
                }
            };
    private final Object mStateLock;

    public NotificationMessagingUtil(Context context, Object stateLock) {
        this.mContext = context;
        this.mStateLock = stateLock != null ? stateLock : new Object();
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("sms_default_application"),
                        false,
                        this.mSmsContentObserver);
    }

    public boolean isImportantMessaging(StatusBarNotification sbn, int importance) {
        if (importance < 2) {
            return false;
        }
        return hasMessagingStyle(sbn) || (isCategoryMessage(sbn) && isDefaultMessagingApp(sbn));
    }

    public boolean isMessaging(StatusBarNotification sbn) {
        return hasMessagingStyle(sbn) || isDefaultMessagingApp(sbn) || isCategoryMessage(sbn);
    }

    private boolean isDefaultMessagingApp(StatusBarNotification sbn) {
        boolean equals;
        int userId = sbn.getUserId();
        if (userId == -10000 || userId == -1) {
            return false;
        }
        synchronized (this.mStateLock) {
            if (this.mDefaultSmsApp.get(userId) == null) {
                cacheDefaultSmsApp(userId);
            }
            equals = Objects.equals(this.mDefaultSmsApp.get(userId), sbn.getPackageName());
        }
        return equals;
    }

    public void cacheDefaultSmsApp(int userId) {
        String smsApp =
                Settings.Secure.getStringForUser(
                        this.mContext.getContentResolver(), "sms_default_application", userId);
        synchronized (this.mStateLock) {
            this.mDefaultSmsApp.put(userId, smsApp);
        }
    }

    private boolean hasMessagingStyle(StatusBarNotification sbn) {
        return sbn.getNotification().isStyle(Notification.MessagingStyle.class);
    }

    private boolean isCategoryMessage(StatusBarNotification sbn) {
        return Notification.CATEGORY_MESSAGE.equals(sbn.getNotification().category);
    }
}
