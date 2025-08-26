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

/* loaded from: classes4.dex */
public class NotificationMessagingUtil {
    private static final String DEFAULT_SMS_APP_SETTING = "sms_default_application";
    private final Context mContext;
    private final SparseArray<String> mDefaultSmsApp = new SparseArray<>();
    private final ContentObserver mSmsContentObserver;
    private final Object mStateLock;

    public NotificationMessagingUtil(Context context, Object obj) {
        ContentObserver contentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.internal.util.NotificationMessagingUtil.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Collection<Uri> collection, int i, int i2) {
                if (collection.contains(Settings.Secure.getUriFor("sms_default_application"))) {
                    NotificationMessagingUtil.this.cacheDefaultSmsApp(i2);
                }
            }
        };
        this.mSmsContentObserver = contentObserver;
        this.mContext = context;
        this.mStateLock = obj == null ? new Object() : obj;
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("sms_default_application"), false, contentObserver);
    }

    public boolean isImportantMessaging(StatusBarNotification statusBarNotification, int i) {
        if (i < 2) {
            return false;
        }
        if (hasMessagingStyle(statusBarNotification)) {
            return true;
        }
        return isCategoryMessage(statusBarNotification) && isDefaultMessagingApp(statusBarNotification);
    }

    public boolean isMessaging(StatusBarNotification statusBarNotification) {
        return hasMessagingStyle(statusBarNotification) || isDefaultMessagingApp(statusBarNotification) || isCategoryMessage(statusBarNotification);
    }

    private boolean isDefaultMessagingApp(StatusBarNotification statusBarNotification) {
        boolean zEquals;
        int userId = statusBarNotification.getUserId();
        if (userId == -10000 || userId == -1) {
            return false;
        }
        synchronized (this.mStateLock) {
            if (this.mDefaultSmsApp.get(userId) == null) {
                cacheDefaultSmsApp(userId);
            }
            zEquals = Objects.equals(this.mDefaultSmsApp.get(userId), statusBarNotification.getPackageName());
        }
        return zEquals;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cacheDefaultSmsApp(int i) {
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "sms_default_application", i);
        synchronized (this.mStateLock) {
            this.mDefaultSmsApp.put(i, stringForUser);
        }
    }

    private boolean hasMessagingStyle(StatusBarNotification statusBarNotification) {
        return statusBarNotification.getNotification().isStyle(Notification.MessagingStyle.class);
    }

    private boolean isCategoryMessage(StatusBarNotification statusBarNotification) {
        return Notification.CATEGORY_MESSAGE.equals(statusBarNotification.getNotification().category);
    }
}
