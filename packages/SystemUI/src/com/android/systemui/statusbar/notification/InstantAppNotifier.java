package com.android.systemui.statusbar.notification;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.util.Pair;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.InstantAppNotifier;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.NotificationChannels;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class InstantAppNotifier implements CoreStartable, CommandQueue.Callbacks, KeyguardStateController.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final KeyguardStateController mKeyguardStateController;
    public final Executor mMainExecutor;
    public final Executor mUiBgExecutor;
    public final UserTracker mUserTracker;
    public final Handler mHandler = new Handler();
    public final ArraySet mCurrentNotifs = new ArraySet();
    public final UserTracker.Callback mUserSwitchListener = new AnonymousClass1();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.InstantAppNotifier$1, reason: invalid class name */
    class AnonymousClass1 implements UserTracker.Callback {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            InstantAppNotifier.this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.InstantAppNotifier$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    InstantAppNotifier.AnonymousClass1 anonymousClass1 = InstantAppNotifier.AnonymousClass1.this;
                    anonymousClass1.getClass();
                    int i2 = InstantAppNotifier.$r8$clinit;
                    InstantAppNotifier.this.updateForegroundInstantApps();
                }
            });
        }
    }

    public InstantAppNotifier(Context context, CommandQueue commandQueue, UserTracker userTracker, Executor executor, Executor executor2, KeyguardStateController keyguardStateController) {
        this.mContext = context;
        this.mCommandQueue = commandQueue;
        this.mUserTracker = userTracker;
        this.mMainExecutor = executor;
        this.mUiBgExecutor = executor2;
        this.mKeyguardStateController = keyguardStateController;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionStarting(int i, long j, long j2, boolean z) {
        if (this.mContext.getDisplayId() == i) {
            updateForegroundInstantApps();
        }
    }

    public final void checkAndPostForStack(ActivityTaskManager.RootTaskInfo rootTaskInfo, ArraySet arraySet, NotificationManager notificationManager, IPackageManager iPackageManager) {
        ApplicationInfo applicationInfo;
        try {
            ComponentName componentName = rootTaskInfo.topActivity;
            if (componentName == null) {
                return;
            }
            String packageName = componentName.getPackageName();
            if (arraySet.remove(new Pair(packageName, Integer.valueOf(rootTaskInfo.userId))) || (applicationInfo = iPackageManager.getApplicationInfo(packageName, 8192L, rootTaskInfo.userId)) == null || !applicationInfo.isInstantApp()) {
                return;
            }
            postInstantAppNotif(packageName, rootTaskInfo.userId, applicationInfo, notificationManager, rootTaskInfo.childTaskIds[r8.length - 1]);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardShowingChanged() {
        updateForegroundInstantApps();
    }

    public final void postInstantAppNotif(String str, int i, ApplicationInfo applicationInfo, NotificationManager notificationManager, int i2) {
        String str2;
        PendingIntent pendingIntent;
        Intent intent;
        PendingIntent pendingIntent2;
        Notification.Builder builder;
        ComponentName componentName;
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", this.mContext.getString(R.string.instant_apps));
        this.mCurrentNotifs.add(new Pair(str, Integer.valueOf(i)));
        String string = this.mContext.getString(R.string.instant_apps_help_url);
        boolean isEmpty = string.isEmpty();
        String string2 = this.mContext.getString(!isEmpty ? R.string.instant_apps_message_with_help : R.string.instant_apps_message);
        UserHandle of = UserHandle.of(i);
        Notification.Action build = new Notification.Action.Builder((Icon) null, this.mContext.getString(R.string.app_info), PendingIntent.getActivityAsUser(this.mContext, 0, new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", str, null)), 67108864, null, of)).build();
        if (isEmpty) {
            str2 = "android.intent.action.VIEW";
            pendingIntent = null;
        } else {
            str2 = "android.intent.action.VIEW";
            pendingIntent = PendingIntent.getActivityAsUser(this.mContext, 0, new Intent("android.intent.action.VIEW").setData(Uri.parse(string)), 67108864, null, of);
        }
        int i3 = 0;
        List recentTasks = ActivityTaskManager.getInstance().getRecentTasks(5, 0, i);
        while (true) {
            if (i3 >= recentTasks.size()) {
                intent = null;
                break;
            } else {
                if (((ActivityManager.RecentTaskInfo) recentTasks.get(i3)).id == i2) {
                    intent = ((ActivityManager.RecentTaskInfo) recentTasks.get(i3)).baseIntent;
                    break;
                }
                i3++;
            }
        }
        Notification.Builder builder2 = new Notification.Builder(this.mContext, NotificationChannels.INSTANT);
        if (intent == null || !intent.isWebIntent()) {
            pendingIntent2 = pendingIntent;
            builder = builder2;
        } else {
            intent.setComponent(null).setPackage(null).addFlags(Integer.MIN_VALUE).addFlags(268435456);
            pendingIntent2 = pendingIntent;
            PendingIntent activityAsUser = PendingIntent.getActivityAsUser(this.mContext, 0, intent, 67108864, ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(1).toBundle(), of);
            try {
                componentName = AppGlobals.getPackageManager().getInstantAppInstallerComponent();
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                componentName = null;
            }
            Notification.Action build2 = new Notification.Action.Builder((Icon) null, this.mContext.getString(R.string.go_to_web), PendingIntent.getActivityAsUser(this.mContext, 0, new Intent().setComponent(componentName).setAction(str2).addCategory("android.intent.category.BROWSABLE").setIdentifier("unique:" + System.currentTimeMillis()).putExtra("android.intent.extra.PACKAGE_NAME", applicationInfo.packageName).putExtra("android.intent.extra.VERSION_CODE", applicationInfo.versionCode & Integer.MAX_VALUE).putExtra("android.intent.extra.LONG_VERSION_CODE", applicationInfo.longVersionCode).putExtra("android.intent.extra.INSTANT_APP_FAILURE", activityAsUser), 67108864, null, of)).build();
            builder = builder2;
            builder.addAction(build2);
        }
        Notification.Builder color = builder.addExtras(bundle).addAction(build).setContentIntent(pendingIntent2).setColor(this.mContext.getColor(R.color.instant_apps_color));
        Context context = this.mContext;
        notificationManager.notifyAsUser(str, 7, color.setContentTitle(context.getString(R.string.instant_apps_title, applicationInfo.loadLabel(context.getPackageManager()))).setLargeIcon(Icon.createWithResource(str, applicationInfo.icon)).setSmallIcon(Icon.createWithResource(this.mContext.getPackageName(), R.drawable.instant_icon)).setContentText(string2).setStyle(new Notification.BigTextStyle().bigText(string2)).setOngoing(true).build(), new UserHandle(i));
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void preloadRecentApps() {
        updateForegroundInstantApps();
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserSwitchListener, this.mMainExecutor);
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this);
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        for (StatusBarNotification statusBarNotification : notificationManager.getActiveNotifications()) {
            if (statusBarNotification.getId() == 7) {
                notificationManager.cancel(statusBarNotification.getTag(), statusBarNotification.getId());
            }
        }
    }

    public final void updateForegroundInstantApps() {
        final NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        final IPackageManager packageManager = AppGlobals.getPackageManager();
        this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.InstantAppNotifier$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                int windowingMode;
                final InstantAppNotifier instantAppNotifier = InstantAppNotifier.this;
                final NotificationManager notificationManager2 = notificationManager;
                IPackageManager iPackageManager = packageManager;
                int i = InstantAppNotifier.$r8$clinit;
                instantAppNotifier.getClass();
                ArraySet arraySet = new ArraySet(instantAppNotifier.mCurrentNotifs);
                try {
                    ActivityTaskManager.RootTaskInfo focusedRootTaskInfo = ActivityTaskManager.getService().getFocusedRootTaskInfo();
                    if (focusedRootTaskInfo != null && ((windowingMode = focusedRootTaskInfo.configuration.windowConfiguration.getWindowingMode()) == 1 || windowingMode == 6 || windowingMode == 5)) {
                        instantAppNotifier.checkAndPostForStack(focusedRootTaskInfo, arraySet, notificationManager2, iPackageManager);
                    }
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
                arraySet.forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.InstantAppNotifier$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        InstantAppNotifier instantAppNotifier2 = InstantAppNotifier.this;
                        NotificationManager notificationManager3 = notificationManager2;
                        Pair pair = (Pair) obj;
                        instantAppNotifier2.mCurrentNotifs.remove(pair);
                        notificationManager3.cancelAsUser((String) pair.first, 7, new UserHandle(((Integer) pair.second).intValue()));
                    }
                });
            }
        });
    }
}
