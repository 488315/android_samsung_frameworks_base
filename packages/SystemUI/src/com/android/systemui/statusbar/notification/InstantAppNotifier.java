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
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.NotificationChannels;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class InstantAppNotifier implements CoreStartable, CommandQueue.Callbacks, KeyguardStateController.Callback {
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final KeyguardStateController mKeyguardStateController;
    public final Executor mMainExecutor;
    public final Executor mUiBgExecutor;
    public final UserTracker mUserTracker;
    public final Handler mHandler = new Handler();
    public final ArraySet mCurrentNotifs = new ArraySet();
    public final UserTracker.Callback mUserSwitchListener = new AnonymousClass1();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.InstantAppNotifier$1, reason: invalid class name */
    class AnonymousClass1 implements UserTracker.Callback {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            InstantAppNotifier.this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.InstantAppNotifier$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
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
    public final void appTransitionStarting(long j, long j2, boolean z, int i) {
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
        Notification.Action action;
        PendingIntent pendingIntent;
        Intent intent;
        Notification.Builder builder;
        PendingIntent pendingIntent2;
        ComponentName componentName;
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", this.mContext.getString(R.string.instant_apps));
        this.mCurrentNotifs.add(new Pair(str, Integer.valueOf(i)));
        String string = this.mContext.getString(R.string.instant_apps_help_url);
        boolean z = !string.isEmpty();
        String string2 = this.mContext.getString(z ? R.string.instant_apps_message_with_help : R.string.instant_apps_message);
        UserHandle of = UserHandle.of(i);
        Notification.Action build = new Notification.Action.Builder((Icon) null, this.mContext.getString(R.string.app_info), PendingIntent.getActivityAsUser(this.mContext, 0, new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", str, null)), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY, null, of)).build();
        if (z) {
            str2 = "android.intent.action.VIEW";
            action = build;
            pendingIntent = PendingIntent.getActivityAsUser(this.mContext, 0, new Intent("android.intent.action.VIEW").setData(Uri.parse(string)), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY, null, of);
        } else {
            str2 = "android.intent.action.VIEW";
            action = build;
            pendingIntent = null;
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
            builder = builder2;
            pendingIntent2 = pendingIntent;
        } else {
            intent.setComponent(null).setPackage(null).addFlags(Integer.MIN_VALUE).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            pendingIntent2 = pendingIntent;
            PendingIntent activityAsUser = PendingIntent.getActivityAsUser(this.mContext, 0, intent, QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY, ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(1).toBundle(), of);
            try {
                componentName = AppGlobals.getPackageManager().getInstantAppInstallerComponent();
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                componentName = null;
            }
            Notification.Action build2 = new Notification.Action.Builder((Icon) null, this.mContext.getString(R.string.go_to_web), PendingIntent.getActivityAsUser(this.mContext, 0, new Intent().setComponent(componentName).setAction(str2).addCategory("android.intent.category.BROWSABLE").setIdentifier("unique:" + System.currentTimeMillis()).putExtra("android.intent.extra.PACKAGE_NAME", applicationInfo.packageName).putExtra("android.intent.extra.VERSION_CODE", applicationInfo.versionCode & Integer.MAX_VALUE).putExtra("android.intent.extra.LONG_VERSION_CODE", applicationInfo.longVersionCode).putExtra("android.intent.extra.INSTANT_APP_FAILURE", activityAsUser), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY, null, of)).build();
            builder = builder2;
            builder.addAction(build2);
        }
        Notification.Builder color = builder.addExtras(bundle).addAction(action).setContentIntent(pendingIntent2).setColor(this.mContext.getColor(R.color.instant_apps_color));
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
