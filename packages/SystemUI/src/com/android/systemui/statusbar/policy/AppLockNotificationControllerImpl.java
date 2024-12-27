package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.ExecutorContentObserver;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.AppLockNotificationControllerImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.settings.SecureSettings;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;

public final class AppLockNotificationControllerImpl implements AppLockNotificationController {
    public ActivityManager mActivityManager;
    public final Context mContext;
    public final ListenerSet mListeners = new ListenerSet();
    public final ArrayList mAppLockActiveLockedPackages = new ArrayList();

    /* renamed from: com.android.systemui.statusbar.policy.AppLockNotificationControllerImpl$1, reason: invalid class name */
    public final class AnonymousClass1 extends ExecutorContentObserver {
        public final /* synthetic */ Handler val$mainHandler;
        public final /* synthetic */ SecureSettings val$settings;

        public AnonymousClass1(Executor executor, SecureSettings secureSettings, Handler handler) {
            super(executor);
            this.val$settings = secureSettings;
            this.val$mainHandler = handler;
        }

        public final void onChange(boolean z) {
            super.onChange(z);
            final String string = this.val$settings.getString("applock_locked_packages");
            this.val$mainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.AppLockNotificationControllerImpl$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AppLockNotificationControllerImpl.AnonymousClass1 anonymousClass1 = AppLockNotificationControllerImpl.AnonymousClass1.this;
                    String str = string;
                    AppLockNotificationControllerImpl.this.mAppLockActiveLockedPackages.clear();
                    AppLockNotificationControllerImpl.this.mAppLockActiveLockedPackages.addAll(Arrays.asList(str));
                    AppLockNotificationControllerImpl appLockNotificationControllerImpl = AppLockNotificationControllerImpl.this;
                    appLockNotificationControllerImpl.getClass();
                    Assert.isMainThread();
                    if (appLockNotificationControllerImpl.isAppLockEnabled() || appLockNotificationControllerImpl.isAppLockEnabled()) {
                        appLockNotificationControllerImpl.mListeners.forEach(new AppLockNotificationControllerImpl$$ExternalSyntheticLambda1());
                    }
                }
            });
        }
    }

    public AppLockNotificationControllerImpl(Context context, SecureSettings secureSettings, PackageManager packageManager, Handler handler, Executor executor, Lazy lazy) {
        this.mContext = context;
        this.mActivityManager = (ActivityManager) context.getSystemService("activity");
        final ?? anonymousClass1 = new AnonymousClass1(executor, secureSettings, handler);
        secureSettings.registerContentObserverSync("applock_locked_packages", (ContentObserver) anonymousClass1);
        executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.AppLockNotificationControllerImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                anonymousClass1.onChange(true);
            }
        });
    }

    public final boolean isAppLockEnabled() {
        if (this.mActivityManager == null) {
            return false;
        }
        ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
        this.mActivityManager = activityManager;
        return activityManager.isApplockEnabled();
    }

    public final boolean shouldHideNotiForAppLock(NotificationEntry notificationEntry) {
        if (this.mActivityManager == null || !isAppLockEnabled()) {
            return false;
        }
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        if (!this.mActivityManager.isAppLockedPackage(statusBarNotification.getPackageName())) {
            return false;
        }
        Log.d("ALNM", statusBarNotification.getPackageName() + " is app locked. notification hidden : " + statusBarNotification.getKey());
        return (notificationEntry.mSbn.getNotification().visibility == 0) || (notificationEntry.mRanking.getChannel() != null && notificationEntry.mRanking.getChannel().getLockscreenVisibility() == 0);
    }
}
