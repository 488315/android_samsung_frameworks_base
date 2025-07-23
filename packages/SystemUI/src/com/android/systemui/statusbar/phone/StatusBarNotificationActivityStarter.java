package com.android.systemui.statusbar.phone;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.service.dreams.IDreamManager;
import android.service.notification.StatusBarNotification;
import android.util.EventLog;
import android.util.Log;
import android.view.RemoteAnimationAdapter;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.GhostedViewTransitionAnimatorController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.NotificationLaunchAnimatorControllerProvider;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.OnUserInteractionCallback;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.wmshell.BubblesManager;
import dagger.Lazy;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StatusBarNotificationActivityStarter implements NotificationActivityStarter {
    public final ActivityIntentHelper mActivityIntentHelper;
    public final ActivityStarter mActivityStarter;
    public final ActivityTransitionAnimator mActivityTransitionAnimator;
    public final Lazy mAssistManagerLazy;
    public final Optional mBubblesManagerOptional;
    public final NotificationClickNotifier mClickNotifier;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final ShadeDialogContextInteractor mContextInteractor;
    public final IDreamManager mDreamManager;
    public final HeadsUpManager mHeadsUpManager;
    public boolean mIsCollapsingToShowActivityOverLockscreen;
    public final KeyguardManager mKeyguardManager;
    public final KeyguardStateController mKeyguardStateController;
    public final LockPatternUtils mLockPatternUtils;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final StatusBarNotificationActivityStarterLogger mLogger;
    public final Handler mMainThreadHandler;
    public final MetricsLogger mMetricsLogger;
    public final NotificationLaunchAnimatorControllerProvider mNotificationAnimationProvider;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final OnUserInteractionCallback mOnUserInteractionCallback;
    public final PowerInteractor mPowerInteractor;
    public final NotificationPresenter mPresenter;
    public final NotificationRemoteInputManager mRemoteInputManager;
    public final ShadeAnimationInteractor mShadeAnimationInteractor;
    public final ShadeController mShadeController;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final StatusBarRemoteInputCallback mStatusBarRemoteInputCallback;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final Executor mUiBgExecutor;
    public final UserTracker mUserTracker;
    public final NotificationVisibilityProvider mVisibilityProvider;
    public boolean mShouldSkipFullScreenIntent = false;
    public NotificationEntry mPendingFullscreenEntry = null;
    public Boolean mIsStartFullscreenIntentWhenSubscreen = Boolean.FALSE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$2, reason: invalid class name */
    public class AnonymousClass2 implements ActivityStarter.OnDismissAction {
        public final /* synthetic */ boolean val$animate;
        public final /* synthetic */ int val$appUid;
        public final /* synthetic */ int val$displayId;
        public final /* synthetic */ Intent val$intent;
        public final /* synthetic */ ExpandableNotificationRow val$row;

        public AnonymousClass2(ExpandableNotificationRow expandableNotificationRow, int i, boolean z, Intent intent, int i2) {
            this.val$row = expandableNotificationRow;
            this.val$displayId = i;
            this.val$animate = z;
            this.val$intent = intent;
            this.val$appUid = i2;
        }

        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
        public final boolean onDismiss() {
            final Intent intent = this.val$intent;
            final boolean z = this.val$animate;
            final int i = this.val$appUid;
            final ExpandableNotificationRow expandableNotificationRow = this.val$row;
            final int i2 = this.val$displayId;
            AsyncTask.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    final StatusBarNotificationActivityStarter.AnonymousClass2 anonymousClass2 = StatusBarNotificationActivityStarter.AnonymousClass2.this;
                    ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                    final int i3 = i2;
                    boolean z2 = z;
                    final Intent intent2 = intent;
                    final int i4 = i;
                    StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = StatusBarNotificationActivityStarter.this;
                    statusBarNotificationActivityStarter.mActivityTransitionAnimator.startIntentWithAnimation(new StatusBarTransitionAnimatorController(statusBarNotificationActivityStarter.mNotificationAnimationProvider.getAnimatorController(expandableNotificationRow2), statusBarNotificationActivityStarter.mShadeAnimationInteractor, statusBarNotificationActivityStarter.mShadeController, statusBarNotificationActivityStarter.mNotificationShadeWindowController, statusBarNotificationActivityStarter.mCommandQueue, i3, true), z2, intent2.getPackage(), false, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$2$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            return Integer.valueOf(TaskStackBuilder.create(StatusBarNotificationActivityStarter.this.mContext).addNextIntentWithParentStack(intent2).startActivities(CentralSurfaces.getActivityOptions(i3, (RemoteAnimationAdapter) obj), new UserHandle(UserHandle.getUserId(i4))));
                        }
                    });
                }
            });
            return false;
        }

        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
        public final boolean willRunAnimationOnKeyguard() {
            return this.val$animate;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$3, reason: invalid class name */
    public class AnonymousClass3 implements ActivityStarter.OnDismissAction {
        public final /* synthetic */ boolean val$animate;
        public final /* synthetic */ int val$displayId;
        public final /* synthetic */ boolean val$showHistory;
        public final /* synthetic */ View val$view;

        public AnonymousClass3(boolean z, View view, int i, boolean z2) {
            this.val$showHistory = z;
            this.val$view = view;
            this.val$displayId = i;
            this.val$animate = z2;
        }

        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
        public final boolean onDismiss() {
            final View view = this.val$view;
            final int i = this.val$displayId;
            final boolean z = this.val$animate;
            final boolean z2 = this.val$showHistory;
            AsyncTask.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    final StatusBarNotificationActivityStarter.AnonymousClass3 anonymousClass3 = StatusBarNotificationActivityStarter.AnonymousClass3.this;
                    boolean z3 = z2;
                    View view2 = view;
                    final int i2 = i;
                    boolean z4 = z;
                    Intent intent = z3 ? new Intent("android.settings.NOTIFICATION_HISTORY") : new Intent("android.settings.NOTIFICATION_SETTINGS");
                    Intent intent2 = new Intent("android.settings.ALL_APPS_NOTIFICATION_SETTINGS");
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("need_search_icon_in_action_bar", true);
                    intent2.putExtra(":settings:show_fragment_args", bundle);
                    StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = StatusBarNotificationActivityStarter.this;
                    final TaskStackBuilder addNextIntent = TaskStackBuilder.create(statusBarNotificationActivityStarter.mContext).addNextIntent(intent2);
                    if (z3) {
                        addNextIntent.addNextIntent(intent);
                    }
                    ActivityTransitionAnimator.Controller.Companion companion = ActivityTransitionAnimator.Controller.Companion;
                    companion.getClass();
                    GhostedViewTransitionAnimatorController fromView$default = ActivityTransitionAnimator.Controller.Companion.fromView$default(companion, view2, 30, 60);
                    statusBarNotificationActivityStarter.mActivityTransitionAnimator.startIntentWithAnimation(fromView$default == null ? null : new StatusBarTransitionAnimatorController(fromView$default, statusBarNotificationActivityStarter.mShadeAnimationInteractor, statusBarNotificationActivityStarter.mShadeController, statusBarNotificationActivityStarter.mNotificationShadeWindowController, statusBarNotificationActivityStarter.mCommandQueue, i2, true), z4, intent.getPackage(), false, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$3$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            TaskStackBuilder taskStackBuilder = addNextIntent;
                            StatusBarNotificationActivityStarter.AnonymousClass3 anonymousClass32 = StatusBarNotificationActivityStarter.AnonymousClass3.this;
                            anonymousClass32.getClass();
                            return Integer.valueOf(taskStackBuilder.startActivities(CentralSurfaces.getActivityOptions(i2, (RemoteAnimationAdapter) obj), ((UserTrackerImpl) StatusBarNotificationActivityStarter.this.mUserTracker).getUserHandle()));
                        }
                    });
                }
            });
            return true;
        }

        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
        public final boolean willRunAnimationOnKeyguard() {
            return this.val$animate;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnKeyguardDismissedAction {
        void onDismiss(PendingIntent pendingIntent, boolean z, boolean z2, boolean z3);
    }

    public StatusBarNotificationActivityStarter(Context context, ShadeDialogContextInteractor shadeDialogContextInteractor, Handler handler, Executor executor, NotificationVisibilityProvider notificationVisibilityProvider, HeadsUpManager headsUpManager, ActivityStarter activityStarter, CommandQueue commandQueue, NotificationClickNotifier notificationClickNotifier, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardManager keyguardManager, IDreamManager iDreamManager, Optional<BubblesManager> optional, Lazy lazy, NotificationRemoteInputManager notificationRemoteInputManager, NotificationLockscreenUserManager notificationLockscreenUserManager, ShadeController shadeController, KeyguardStateController keyguardStateController, LockPatternUtils lockPatternUtils, StatusBarRemoteInputCallback statusBarRemoteInputCallback, ActivityIntentHelper activityIntentHelper, MetricsLogger metricsLogger, StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger, OnUserInteractionCallback onUserInteractionCallback, NotificationPresenter notificationPresenter, PanelExpansionInteractor panelExpansionInteractor, NotificationShadeWindowController notificationShadeWindowController, ActivityTransitionAnimator activityTransitionAnimator, ShadeAnimationInteractor shadeAnimationInteractor, NotificationLaunchAnimatorControllerProvider notificationLaunchAnimatorControllerProvider, LaunchFullScreenIntentProvider launchFullScreenIntentProvider, PowerInteractor powerInteractor, UserTracker userTracker, SysuiStatusBarStateController sysuiStatusBarStateController) {
        this.mContext = context;
        this.mContextInteractor = shadeDialogContextInteractor;
        this.mMainThreadHandler = handler;
        this.mUiBgExecutor = executor;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mHeadsUpManager = headsUpManager;
        this.mActivityStarter = activityStarter;
        this.mCommandQueue = commandQueue;
        this.mClickNotifier = notificationClickNotifier;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mKeyguardManager = keyguardManager;
        this.mDreamManager = iDreamManager;
        this.mBubblesManagerOptional = optional;
        this.mAssistManagerLazy = lazy;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mShadeController = shadeController;
        this.mKeyguardStateController = keyguardStateController;
        this.mLockPatternUtils = lockPatternUtils;
        this.mStatusBarRemoteInputCallback = statusBarRemoteInputCallback;
        this.mActivityIntentHelper = activityIntentHelper;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mShadeAnimationInteractor = shadeAnimationInteractor;
        this.mMetricsLogger = metricsLogger;
        this.mLogger = statusBarNotificationActivityStarterLogger;
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mPresenter = notificationPresenter;
        this.mActivityTransitionAnimator = activityTransitionAnimator;
        this.mNotificationAnimationProvider = notificationLaunchAnimatorControllerProvider;
        this.mPowerInteractor = powerInteractor;
        this.mUserTracker = userTracker;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        launchFullScreenIntentProvider.listeners.addIfAbsent(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1(this));
    }

    public static boolean shouldAutoCancel(StatusBarNotification statusBarNotification) {
        return (statusBarNotification.getNotification().flags & 16) == 16;
    }

    public void launchFullScreenIntent(NotificationEntry notificationEntry) {
        StatusBarNotification statusBarNotification;
        boolean z = ((StatusBarNotificationPresenter) this.mPresenter).mVrMode;
        StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger = this.mLogger;
        if (z) {
            statusBarNotificationActivityStarterLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 = new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer = statusBarNotificationActivityStarterLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotifActivityStarter", logLevel, statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer.commit(obtain);
            return;
        }
        if (NotiRune.NOTI_SUBSCREEN_PENDING_CALL_FULLSCRREN_INTENT && this.mShouldSkipFullScreenIntent && notificationEntry != null && (statusBarNotification = notificationEntry.mSbn) != null && "call".equals(statusBarNotification.getNotification().category)) {
            if (!this.mIsStartFullscreenIntentWhenSubscreen.booleanValue()) {
                try {
                    if (statusBarNotification.getPackageName() == null || !ActivityTaskManager.getService().isPackageEnabledForCoverLauncher(statusBarNotification.getPackageName(), statusBarNotification.getUser().getIdentifier())) {
                        this.mPendingFullscreenEntry = notificationEntry;
                    } else {
                        this.mPendingFullscreenEntry = null;
                    }
                } catch (RemoteException unused) {
                }
                Log.d("StatusBarNotificationActivityStarter", "pending wx call");
                return;
            }
            this.mIsStartFullscreenIntentWhenSubscreen = Boolean.FALSE;
        }
        if (((PowerManager) this.mContext.getSystemService(PowerManager.class)).isInteractive()) {
            this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = StatusBarNotificationActivityStarter.this;
                    statusBarNotificationActivityStarter.getClass();
                    try {
                        statusBarNotificationActivityStarter.mDreamManager.awaken();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        PendingIntent pendingIntent = notificationEntry.mSbn.getNotification().fullScreenIntent;
        statusBarNotificationActivityStarterLogger.getClass();
        LogLevel logLevel2 = LogLevel.INFO;
        StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda02 = new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(5);
        LogBuffer logBuffer2 = statusBarNotificationActivityStarterLogger.buffer;
        LogMessage obtain2 = logBuffer2.obtain("NotifActivityStarter", logLevel2, statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda02, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain2;
        logMessageImpl.str1 = NotificationUtils.logKey(notificationEntry);
        Intent intent = pendingIntent.getIntent();
        logMessageImpl.str2 = intent != null ? intent.toString() : null;
        logBuffer2.commit(obtain2);
        try {
            EventLog.writeEvent(36002, notificationEntry.mKey);
            this.mPowerInteractor.wakeUpForFullScreenIntent();
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityStartMode(1);
            pendingIntent.sendAndReturnResult(null, 0, null, null, null, null, makeBasic.toBundle());
            notificationEntry.interruption = true;
            notificationEntry.lastFullScreenIntentLaunchTime = SystemClock.elapsedRealtime();
            this.mMetricsLogger.count("note_fullscreen", 1);
            List queryIntentComponents = pendingIntent.queryIntentComponents(0);
            FrameworkStatsLog.write(631, pendingIntent.getCreatorUid(), (queryIntentComponents.size() <= 0 || queryIntentComponents.get(0) == null || ((ResolveInfo) queryIntentComponents.get(0)).activityInfo == null || ((ResolveInfo) queryIntentComponents.get(0)).activityInfo.name == null) ? "" : ((ResolveInfo) queryIntentComponents.get(0)).activityInfo.name);
        } catch (PendingIntent.CanceledException unused2) {
        }
    }

    public final void onDragSuccess(NotificationEntry notificationEntry) {
        RemoteInputCoordinator remoteInputCoordinator;
        NotificationVisibility obtain = ((NotificationVisibilityProviderImpl) this.mVisibilityProvider).obtain(notificationEntry);
        boolean shouldAutoCancel = shouldAutoCancel(notificationEntry.mSbn);
        String str = notificationEntry.mKey;
        if (shouldAutoCancel || ((remoteInputCoordinator = this.mRemoteInputManager.mRemoteInputListener) != null && remoteInputCoordinator.isNotificationKeptForRemoteInputHistory(str))) {
            this.mMainThreadHandler.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda6(this, ((OnUserInteractionCallbackImpl) this.mOnUserInteractionCallback).registerFutureDismissal(notificationEntry, 1), 1));
        }
        this.mClickNotifier.onNotificationClick(str, obtain);
        this.mIsCollapsingToShowActivityOverLockscreen = false;
    }

    public final void onNotificationClicked(final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow) {
        boolean isHeadsUpState = expandableNotificationRow.isHeadsUpState();
        boolean isVisible = this.mKeyguardStateController.isVisible();
        boolean z = ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mCurrentState.shadeOrQsExpanded;
        StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger = this.mLogger;
        statusBarNotificationActivityStarterLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 = new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = statusBarNotificationActivityStarterLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotifActivityStarter", logLevel, statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.bool1 = isHeadsUpState;
        logMessageImpl.bool2 = isVisible;
        logMessageImpl.bool3 = z;
        logBuffer.commit(obtain);
        performActionAfterKeyguardDismissed(notificationEntry, new OnKeyguardDismissedAction() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.OnKeyguardDismissedAction
            public final void onDismiss(final PendingIntent pendingIntent, final boolean z2, final boolean z3, boolean z4) {
                final StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = StatusBarNotificationActivityStarter.this;
                StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger2 = statusBarNotificationActivityStarter.mLogger;
                statusBarNotificationActivityStarterLogger2.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda02 = new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(6);
                LogBuffer logBuffer2 = statusBarNotificationActivityStarterLogger2.buffer;
                LogMessage obtain2 = logBuffer2.obtain("NotifActivityStarter", logLevel2, statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda02, null);
                final NotificationEntry notificationEntry2 = notificationEntry;
                ((LogMessageImpl) obtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry2);
                logBuffer2.commit(obtain2);
                final ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3
                    /* JADX WARN: Removed duplicated region for block: B:41:0x012e  */
                    /* JADX WARN: Removed duplicated region for block: B:49:0x0278 A[ADDED_TO_REGION] */
                    /* JADX WARN: Removed duplicated region for block: B:52:0x028f  */
                    /* JADX WARN: Removed duplicated region for block: B:60:0x02bf  */
                    /* JADX WARN: Removed duplicated region for block: B:64:0x018f  */
                    /* JADX WARN: Removed duplicated region for block: B:94:0x026b  */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void run() {
                        /*
                            Method dump skipped, instructions count: 717
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3.run():void");
                    }
                };
                ShadeController shadeController = statusBarNotificationActivityStarter.mShadeController;
                if (z4) {
                    ((BaseShadeControllerImpl) shadeController).postCollapseActions.add(runnable);
                    shadeController.collapseShade(true);
                    return;
                }
                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) statusBarNotificationActivityStarter.mKeyguardStateController;
                if (!keyguardStateControllerImpl.mShowing || !keyguardStateControllerImpl.mOccluded) {
                    runnable.run();
                } else {
                    statusBarNotificationActivityStarter.mStatusBarKeyguardViewManager.addAfterKeyguardGoneRunnable(runnable);
                    shadeController.collapseShade();
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void performActionAfterKeyguardDismissed(com.android.systemui.statusbar.notification.collection.NotificationEntry r14, final com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.OnKeyguardDismissedAction r15) {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.performActionAfterKeyguardDismissed(com.android.systemui.statusbar.notification.collection.NotificationEntry, com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$OnKeyguardDismissedAction):void");
    }

    public final void removeHunAfterClick(ExpandableNotificationRow expandableNotificationRow) {
        String key = expandableNotificationRow.getKey();
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        if (headsUpManager != null) {
            HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) headsUpManager;
            if (headsUpManagerImpl.isHeadsUpEntry(key)) {
                if (((StatusBarNotificationPresenter) this.mPresenter).mPanelExpansionInteractor.isFullyCollapsed()) {
                    expandableNotificationRow.setTag(R.id.is_clicked_heads_up_tag, Boolean.TRUE);
                }
                headsUpManagerImpl.removeNotification(key, "removeHunAfterClick", true);
            }
        }
    }

    public final void setShowSwipeBouncer(boolean z) {
        StatusBarKeyguardViewManager statusBarKeyguardViewManager;
        if (!LsRune.SECURITY_SWIPE_BOUNCER || (statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager) == null) {
            return;
        }
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        if (sysuiStatusBarStateController.getState() == 2 || sysuiStatusBarStateController.getState() == 1) {
            statusBarKeyguardViewManager.setShowSwipeBouncer(z);
        }
    }

    public final void startNotificationGutsIntent(Intent intent, int i, ExpandableNotificationRow expandableNotificationRow) {
        ActivityStarter activityStarter = this.mActivityStarter;
        activityStarter.dismissKeyguardThenExecute(new AnonymousClass2(expandableNotificationRow, ((ShadeDialogContextInteractorImpl) this.mContextInteractor).getContext().getDisplayId(), activityStarter.shouldAnimateLaunch(true), intent, i), null, false);
    }
}
