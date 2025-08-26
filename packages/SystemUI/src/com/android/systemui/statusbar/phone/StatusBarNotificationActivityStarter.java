package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.service.dreams.IDreamManager;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.view.RemoteAnimationAdapter;
import android.view.View;
import android.view.WindowManager;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.Rune;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.ActivityTransitionAnimator$$ExternalSyntheticLambda0;
import com.android.systemui.animation.GhostedViewTransitionAnimatorController;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
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
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.RemoteInputController;
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
import com.android.systemui.util.NotificationSAUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wmshell.BubblesManager;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda4;
import com.android.wm.shell.bubbles.BubbleEntry;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import dagger.Lazy;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

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
                    final StatusBarNotificationActivityStarter.AnonymousClass2 anonymousClass2 = this.f$0;
                    ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                    final int i3 = i2;
                    boolean z2 = z;
                    final Intent intent2 = intent;
                    final int i4 = i;
                    StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = StatusBarNotificationActivityStarter.this;
                    statusBarNotificationActivityStarter.mActivityTransitionAnimator.startIntentWithAnimation(new StatusBarTransitionAnimatorController(statusBarNotificationActivityStarter.mNotificationAnimationProvider.getAnimatorController(expandableNotificationRow2), statusBarNotificationActivityStarter.mShadeAnimationInteractor, statusBarNotificationActivityStarter.mShadeController, statusBarNotificationActivityStarter.mNotificationShadeWindowController, statusBarNotificationActivityStarter.mCommandQueue, i3, true), z2, intent2.getPackage(), false, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$2$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
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
                    final StatusBarNotificationActivityStarter.AnonymousClass3 anonymousClass3 = this.f$0;
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
                    final TaskStackBuilder taskStackBuilderAddNextIntent = TaskStackBuilder.create(statusBarNotificationActivityStarter.mContext).addNextIntent(intent2);
                    if (z3) {
                        taskStackBuilderAddNextIntent.addNextIntent(intent);
                    }
                    ActivityTransitionAnimator.Controller.Companion companion = ActivityTransitionAnimator.Controller.Companion;
                    companion.getClass();
                    GhostedViewTransitionAnimatorController ghostedViewTransitionAnimatorControllerFromView$default = ActivityTransitionAnimator.Controller.Companion.fromView$default(companion, view2, 30, 60);
                    statusBarNotificationActivityStarter.mActivityTransitionAnimator.startIntentWithAnimation(ghostedViewTransitionAnimatorControllerFromView$default == null ? null : new StatusBarTransitionAnimatorController(ghostedViewTransitionAnimatorControllerFromView$default, statusBarNotificationActivityStarter.mShadeAnimationInteractor, statusBarNotificationActivityStarter.mShadeController, statusBarNotificationActivityStarter.mNotificationShadeWindowController, statusBarNotificationActivityStarter.mCommandQueue, i2, true), z4, intent.getPackage(), false, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$3$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            TaskStackBuilder taskStackBuilder = taskStackBuilderAddNextIntent;
                            StatusBarNotificationActivityStarter.AnonymousClass3 anonymousClass32 = anonymousClass3;
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
            LogMessage logMessageObtain = logBuffer.obtain("NotifActivityStarter", logLevel, statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer.commit(logMessageObtain);
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
                    StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = this.f$0;
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
        LogMessage logMessageObtain2 = logBuffer2.obtain("NotifActivityStarter", logLevel2, statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda02, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain2;
        logMessageImpl.str1 = NotificationUtils.logKey(notificationEntry);
        Intent intent = pendingIntent.getIntent();
        logMessageImpl.str2 = intent != null ? intent.toString() : null;
        logBuffer2.commit(logMessageObtain2);
        try {
            EventLog.writeEvent(36002, notificationEntry.mKey);
            this.mPowerInteractor.wakeUpForFullScreenIntent();
            ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
            activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
            pendingIntent.sendAndReturnResult(null, 0, null, null, null, null, activityOptionsMakeBasic.toBundle());
            notificationEntry.interruption = true;
            notificationEntry.lastFullScreenIntentLaunchTime = SystemClock.elapsedRealtime();
            this.mMetricsLogger.count("note_fullscreen", 1);
            List listQueryIntentComponents = pendingIntent.queryIntentComponents(0);
            FrameworkStatsLog.write(631, pendingIntent.getCreatorUid(), (listQueryIntentComponents.size() <= 0 || listQueryIntentComponents.get(0) == null || ((ResolveInfo) listQueryIntentComponents.get(0)).activityInfo == null || ((ResolveInfo) listQueryIntentComponents.get(0)).activityInfo.name == null) ? "" : ((ResolveInfo) listQueryIntentComponents.get(0)).activityInfo.name);
        } catch (PendingIntent.CanceledException unused2) {
        }
    }

    public final void onDragSuccess(NotificationEntry notificationEntry) {
        RemoteInputCoordinator remoteInputCoordinator;
        NotificationVisibility notificationVisibilityObtain = ((NotificationVisibilityProviderImpl) this.mVisibilityProvider).obtain(notificationEntry);
        boolean zShouldAutoCancel = shouldAutoCancel(notificationEntry.mSbn);
        String str = notificationEntry.mKey;
        if (zShouldAutoCancel || ((remoteInputCoordinator = this.mRemoteInputManager.mRemoteInputListener) != null && remoteInputCoordinator.isNotificationKeptForRemoteInputHistory(str))) {
            this.mMainThreadHandler.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda6(this, ((OnUserInteractionCallbackImpl) this.mOnUserInteractionCallback).registerFutureDismissal(notificationEntry, 1), 1));
        }
        this.mClickNotifier.onNotificationClick(str, notificationVisibilityObtain);
        this.mIsCollapsingToShowActivityOverLockscreen = false;
    }

    public final void onNotificationClicked(final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow) {
        boolean zIsHeadsUpState = expandableNotificationRow.isHeadsUpState();
        boolean zIsVisible = this.mKeyguardStateController.isVisible();
        boolean z = ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mCurrentState.shadeOrQsExpanded;
        StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger = this.mLogger;
        statusBarNotificationActivityStarterLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 = new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = statusBarNotificationActivityStarterLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotifActivityStarter", logLevel, statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.bool1 = zIsHeadsUpState;
        logMessageImpl.bool2 = zIsVisible;
        logMessageImpl.bool3 = z;
        logBuffer.commit(logMessageObtain);
        performActionAfterKeyguardDismissed(notificationEntry, new OnKeyguardDismissedAction() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.OnKeyguardDismissedAction
            public final void onDismiss(final PendingIntent pendingIntent, final boolean z2, final boolean z3, boolean z4) {
                final StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = this.f$0;
                StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger2 = statusBarNotificationActivityStarter.mLogger;
                statusBarNotificationActivityStarterLogger2.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda02 = new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(6);
                LogBuffer logBuffer2 = statusBarNotificationActivityStarterLogger2.buffer;
                LogMessage logMessageObtain2 = logBuffer2.obtain("NotifActivityStarter", logLevel2, statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda02, null);
                final NotificationEntry notificationEntry2 = notificationEntry;
                ((LogMessageImpl) logMessageObtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry2);
                logBuffer2.commit(logMessageObtain2);
                final ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3
                    /* JADX WARN: Removed duplicated region for block: B:100:0x027a  */
                    /* JADX WARN: Removed duplicated region for block: B:103:0x028f  */
                    /* JADX WARN: Removed duplicated region for block: B:111:0x02bf  */
                    /* JADX WARN: Removed duplicated region for block: B:42:0x0121  */
                    /* JADX WARN: Removed duplicated region for block: B:95:0x026b  */
                    /* JADX WARN: Removed duplicated region for block: B:99:0x0278 A[ADDED_TO_REGION] */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void run() {
                        ShadeController shadeController;
                        String str;
                        LogBuffer logBuffer3;
                        String str2;
                        NotificationRemoteInputManager notificationRemoteInputManager;
                        Handler handler;
                        boolean z5;
                        RemoteInputCoordinator remoteInputCoordinator;
                        final StatusBarNotificationActivityStarter statusBarNotificationActivityStarter2 = statusBarNotificationActivityStarter;
                        final NotificationEntry notificationEntry3 = notificationEntry2;
                        final ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow2;
                        final PendingIntent pendingIntent2 = pendingIntent;
                        boolean z6 = z2;
                        boolean z7 = z3;
                        statusBarNotificationActivityStarter2.getClass();
                        String str3 = notificationEntry3.mKey;
                        StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger3 = statusBarNotificationActivityStarter2.mLogger;
                        statusBarNotificationActivityStarterLogger3.getClass();
                        LogLevel logLevel3 = LogLevel.DEBUG;
                        StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda03 = new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(7);
                        LogBuffer logBuffer4 = statusBarNotificationActivityStarterLogger3.buffer;
                        LogMessage logMessageObtain3 = logBuffer4.obtain("NotifActivityStarter", logLevel3, statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda03, null);
                        ((LogMessageImpl) logMessageObtain3).str1 = NotificationUtils.logKey(notificationEntry3);
                        logBuffer4.commit(logMessageObtain3);
                        try {
                            ActivityManager.getService().resumeAppSwitches();
                        } catch (RemoteException unused) {
                        }
                        ShadeController shadeController2 = statusBarNotificationActivityStarter2.mShadeController;
                        if ((z6 && StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) || !z6) {
                            int identifier = pendingIntent2.getCreatorUserHandle().getIdentifier();
                            if (statusBarNotificationActivityStarter2.mLockPatternUtils.isSeparateProfileChallengeEnabled(identifier) && statusBarNotificationActivityStarter2.mKeyguardManager.isDeviceLocked(identifier) && statusBarNotificationActivityStarter2.mStatusBarRemoteInputCallback.startWorkChallengeIfNecessary(identifier, pendingIntent2.getIntentSender(), str3)) {
                                statusBarNotificationActivityStarter2.removeHunAfterClick(expandableNotificationRow3);
                                shadeController2.collapseOnMainThread();
                                return;
                            }
                        }
                        CharSequence charSequence = !TextUtils.isEmpty(notificationEntry3.remoteInputText) ? notificationEntry3.remoteInputText : null;
                        boolean zIsEmpty = TextUtils.isEmpty(charSequence);
                        NotificationRemoteInputManager notificationRemoteInputManager2 = statusBarNotificationActivityStarter2.mRemoteInputManager;
                        final Intent intentPutExtra = (zIsEmpty || notificationRemoteInputManager2.isSpinning(str3)) ? null : new Intent().putExtra("android.remoteInputDraft", charSequence.toString());
                        boolean z8 = Rune.SYSUI_APPLOCK;
                        if (z8 && z6) {
                            ComponentName component = pendingIntent2.getIntent() != null ? pendingIntent2.getIntent().getComponent() : null;
                            String targetPackage = (component == null || component.getPackageName() == null) ? pendingIntent2.getTargetPackage() : component.getPackageName();
                            if (((ActivityManager) statusBarNotificationActivityStarter2.mContext.getSystemService("activity")).isAppLockedPackage(targetPackage)) {
                                notificationRemoteInputManager2.getClass();
                                Intent intent = new Intent("com.samsung.android.intent.action.CHECK_APPLOCK_SERVICE");
                                intent.setPackage("com.samsung.android.applock");
                                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                                shadeController = shadeController2;
                                layoutParams.flags |= NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                                intent.putExtra("LOCKED_PACKAGE_WINDOW_ATTRIBUTES", layoutParams);
                                intent.putExtra("LAUNCH_FROM_RESUME", true);
                                intent.putExtra("LOCKED_APP_CAN_SHOW_WHEN_LOCKED", true);
                                intent.putExtra("LOCKED_PACKAGE_NAME", targetPackage);
                                intent.putExtra("startFromNotification", true);
                                intent.putExtra("LOCKED_PACKAGE_DISPLAYID", 0);
                                notificationRemoteInputManager2.mContext.startService(intent);
                            }
                        } else {
                            shadeController = shadeController2;
                        }
                        boolean zCanBubble = notificationEntry3.mRanking.canBubble();
                        Handler handler2 = statusBarNotificationActivityStarter2.mMainThreadHandler;
                        if (zCanBubble) {
                            LogMessage logMessageObtain4 = logBuffer4.obtain("NotifActivityStarter", LogLevel.DEBUG, new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(8), null);
                            ((LogMessageImpl) logMessageObtain4).str1 = NotificationUtils.logKey(notificationEntry3);
                            logBuffer4.commit(logMessageObtain4);
                            statusBarNotificationActivityStarter2.removeHunAfterClick(expandableNotificationRow3);
                            if (statusBarNotificationActivityStarter2.mBubblesManagerOptional.isPresent()) {
                                if (Looper.getMainLooper().isCurrentThread()) {
                                    BubblesManager bubblesManager = (BubblesManager) statusBarNotificationActivityStarter2.mBubblesManagerOptional.get();
                                    BubbleEntry bubbleEntryNotifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry3);
                                    BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                                    BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda4(bubblesImpl, bubbleEntryNotifToBubbleEntry, 1));
                                    shadeController.collapseShade();
                                } else {
                                    handler2.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4(statusBarNotificationActivityStarter2, notificationEntry3, 1));
                                }
                            }
                            str = str3;
                            notificationRemoteInputManager = notificationRemoteInputManager2;
                            handler = handler2;
                        } else {
                            LogMessage logMessageObtain5 = logBuffer4.obtain("NotifActivityStarter", LogLevel.INFO, new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(10), null);
                            ((LogMessageImpl) logMessageObtain5).str1 = NotificationUtils.logKey(notificationEntry3);
                            logBuffer4.commit(logMessageObtain5);
                            final int displayId = ((ShadeDialogContextInteractorImpl) statusBarNotificationActivityStarter2.mContextInteractor).getContext().getDisplayId();
                            try {
                                if (!z8 || !z6) {
                                    z5 = true;
                                } else if (intentPutExtra != null) {
                                    z5 = true;
                                    intentPutExtra.putExtra("LAUNCH_FROM_NOTIFICATION", 1);
                                } else {
                                    z5 = true;
                                    intentPutExtra = new Intent().putExtra("LAUNCH_FROM_NOTIFICATION", 1);
                                }
                                try {
                                    try {
                                        try {
                                            try {
                                                str2 = "NotifActivityStarter";
                                                notificationRemoteInputManager = notificationRemoteInputManager2;
                                                handler = handler2;
                                                logBuffer3 = logBuffer4;
                                                try {
                                                    StatusBarTransitionAnimatorController statusBarTransitionAnimatorController = new StatusBarTransitionAnimatorController(statusBarNotificationActivityStarter2.mNotificationAnimationProvider.getAnimatorController(expandableNotificationRow3), statusBarNotificationActivityStarter2.mShadeAnimationInteractor, statusBarNotificationActivityStarter2.mShadeController, statusBarNotificationActivityStarter2.mNotificationShadeWindowController, statusBarNotificationActivityStarter2.mCommandQueue, displayId, z6);
                                                    ActivityTransitionAnimator activityTransitionAnimator = statusBarNotificationActivityStarter2.mActivityTransitionAnimator;
                                                    String creatorPackage = pendingIntent2.getCreatorPackage();
                                                    str = str3;
                                                    try {
                                                        ActivityTransitionAnimator.PendingIntentStarter pendingIntentStarter = new ActivityTransitionAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda10
                                                            @Override // com.android.systemui.animation.ActivityTransitionAnimator.PendingIntentStarter
                                                            public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                                                                Bundle activityOptions;
                                                                PendingIntent pendingIntent3 = pendingIntent2;
                                                                Intent intent2 = intentPutExtra;
                                                                StatusBarNotificationActivityStarter statusBarNotificationActivityStarter3 = statusBarNotificationActivityStarter2;
                                                                statusBarNotificationActivityStarter3.getClass();
                                                                ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow3;
                                                                long j = expandableNotificationRow4.mLastActionUpTime;
                                                                expandableNotificationRow4.mLastActionUpTime = 0L;
                                                                int i = displayId;
                                                                if (j > 0) {
                                                                    boolean z9 = ((KeyguardStateControllerImpl) statusBarNotificationActivityStarter3.mKeyguardStateController).mShowing;
                                                                    ActivityOptions defaultActivityOptions = CentralSurfaces.getDefaultActivityOptions(remoteAnimationAdapter);
                                                                    defaultActivityOptions.setSourceInfo(z9 ? 3 : 2, j);
                                                                    defaultActivityOptions.setLaunchDisplayId(i);
                                                                    defaultActivityOptions.setCallerDisplayId(i);
                                                                    defaultActivityOptions.setPendingIntentBackgroundActivityLaunchAllowed(true);
                                                                    activityOptions = defaultActivityOptions.toBundle();
                                                                } else {
                                                                    activityOptions = CentralSurfaces.getActivityOptions(i, remoteAnimationAdapter);
                                                                }
                                                                int iSendAndReturnResult = pendingIntent3.sendAndReturnResult(statusBarNotificationActivityStarter3.mContext, 0, intent2, null, null, null, activityOptions);
                                                                StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger4 = statusBarNotificationActivityStarter3.mLogger;
                                                                statusBarNotificationActivityStarterLogger4.getClass();
                                                                LogLevel logLevel4 = LogLevel.INFO;
                                                                StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda04 = new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(9);
                                                                LogBuffer logBuffer5 = statusBarNotificationActivityStarterLogger4.buffer;
                                                                LogMessage logMessageObtain6 = logBuffer5.obtain("NotifActivityStarter", logLevel4, statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda04, null);
                                                                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain6;
                                                                logMessageImpl2.str1 = NotificationUtilsKt.getLogKey(notificationEntry3);
                                                                Intent intent3 = pendingIntent3.getIntent();
                                                                logMessageImpl2.str2 = intent3 != null ? intent3.toString() : null;
                                                                logMessageImpl2.int1 = iSendAndReturnResult;
                                                                logBuffer5.commit(logMessageObtain6);
                                                                return iSendAndReturnResult;
                                                            }
                                                        };
                                                        activityTransitionAnimator.getClass();
                                                        activityTransitionAnimator.startIntentWithAnimation(statusBarTransitionAnimatorController, z7, creatorPackage, false, new ActivityTransitionAnimator$$ExternalSyntheticLambda0(pendingIntentStarter));
                                                    } catch (PendingIntent.CanceledException e) {
                                                        e = e;
                                                        LogMessage logMessageObtain6 = logBuffer3.obtain(str2, LogLevel.WARNING, new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(1), null);
                                                        ((LogMessageImpl) logMessageObtain6).str1 = e.toString();
                                                        logBuffer3.commit(logMessageObtain6);
                                                        if (notificationEntry3.isOngoingActivity()) {
                                                        }
                                                        if (z6) {
                                                        }
                                                        NotificationVisibility notificationVisibilityObtain = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                                                        if (!zCanBubble) {
                                                        }
                                                        statusBarNotificationActivityStarter2.mClickNotifier.onNotificationClick(str, notificationVisibilityObtain);
                                                        statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = false;
                                                    }
                                                } catch (PendingIntent.CanceledException e2) {
                                                    e = e2;
                                                    str = str3;
                                                }
                                            } catch (PendingIntent.CanceledException e3) {
                                                e = e3;
                                                str = str3;
                                                logBuffer3 = logBuffer4;
                                                str2 = "NotifActivityStarter";
                                                notificationRemoteInputManager = notificationRemoteInputManager2;
                                                handler = handler2;
                                            }
                                        } catch (PendingIntent.CanceledException e4) {
                                            e = e4;
                                            str = str3;
                                            logBuffer3 = logBuffer4;
                                            handler = handler2;
                                            str2 = "NotifActivityStarter";
                                            notificationRemoteInputManager = notificationRemoteInputManager2;
                                        }
                                    } catch (PendingIntent.CanceledException e5) {
                                        e = e5;
                                        str = str3;
                                        logBuffer3 = logBuffer4;
                                        notificationRemoteInputManager = notificationRemoteInputManager2;
                                        handler = handler2;
                                        str2 = "NotifActivityStarter";
                                    }
                                } catch (PendingIntent.CanceledException e6) {
                                    e = e6;
                                    str = str3;
                                    logBuffer3 = logBuffer4;
                                    str2 = "NotifActivityStarter";
                                    notificationRemoteInputManager = notificationRemoteInputManager2;
                                    handler = handler2;
                                    LogMessage logMessageObtain62 = logBuffer3.obtain(str2, LogLevel.WARNING, new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(1), null);
                                    ((LogMessageImpl) logMessageObtain62).str1 = e.toString();
                                    logBuffer3.commit(logMessageObtain62);
                                    if (notificationEntry3.isOngoingActivity()) {
                                        NotificationSAUtil.sendOALog(SystemUIAnalytics.OAID_PANEL_ONGOING_OPEN_APP, notificationEntry3);
                                    }
                                    if (z6) {
                                        ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                                    }
                                    NotificationVisibility notificationVisibilityObtain2 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                                    if (!zCanBubble) {
                                        handler.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda6(statusBarNotificationActivityStarter2, ((OnUserInteractionCallbackImpl) statusBarNotificationActivityStarter2.mOnUserInteractionCallback).registerFutureDismissal(notificationEntry3, 1), 0));
                                        if (StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) {
                                        }
                                    }
                                    statusBarNotificationActivityStarter2.mClickNotifier.onNotificationClick(str, notificationVisibilityObtain2);
                                    statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = false;
                                }
                            } catch (PendingIntent.CanceledException e7) {
                                e = e7;
                                str = str3;
                                logBuffer3 = logBuffer4;
                                str2 = "NotifActivityStarter";
                            }
                            if (notificationEntry3.isOngoingActivity() && notificationEntry3.isPromotedState()) {
                                NotificationSAUtil.sendOALog(SystemUIAnalytics.OAID_PANEL_ONGOING_OPEN_APP, notificationEntry3);
                            }
                        }
                        if (z6 || zCanBubble) {
                            ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                        }
                        NotificationVisibility notificationVisibilityObtain22 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                        if (!zCanBubble && (StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn) || ((remoteInputCoordinator = notificationRemoteInputManager.mRemoteInputListener) != null && remoteInputCoordinator.isNotificationKeptForRemoteInputHistory(str)))) {
                            handler.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda6(statusBarNotificationActivityStarter2, ((OnUserInteractionCallbackImpl) statusBarNotificationActivityStarter2.mOnUserInteractionCallback).registerFutureDismissal(notificationEntry3, 1), 0));
                            if (StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) {
                                NotificationSAUtil.sendCancelLog(SystemUIAnalytics.EID_QPNE_CANCEL_NOTIFICATION_CLICK, notificationEntry3);
                            }
                        }
                        statusBarNotificationActivityStarter2.mClickNotifier.onNotificationClick(str, notificationVisibilityObtain22);
                        statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = false;
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

    /* JADX WARN: Removed duplicated region for block: B:46:0x00c0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void performActionAfterKeyguardDismissed(NotificationEntry notificationEntry, final OnKeyguardDismissedAction onKeyguardDismissedAction) {
        boolean z;
        int identifier;
        boolean z2;
        NotificationRemoteInputManager notificationRemoteInputManager = this.mRemoteInputManager;
        RemoteInputController remoteInputController = notificationRemoteInputManager.mRemoteInputController;
        final boolean z3 = false;
        boolean z4 = remoteInputController != null && remoteInputController.pruneWeakThenRemoveAndContains(notificationEntry, null, null);
        StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger = this.mLogger;
        if (z4) {
            notificationRemoteInputManager.closeRemoteInputs(false);
            statusBarNotificationActivityStarterLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 = new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = statusBarNotificationActivityStarterLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotifActivityStarter", logLevel, statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer.commit(logMessageObtain);
            return;
        }
        Notification notification2 = notificationEntry.mSbn.getNotification();
        final PendingIntent pendingIntent = notification2.contentIntent;
        if (pendingIntent == null) {
            pendingIntent = notification2.fullScreenIntent;
        }
        boolean zIsBubble = notificationEntry.isBubble();
        if (pendingIntent == null && !zIsBubble) {
            statusBarNotificationActivityStarterLogger.getClass();
            LogLevel logLevel2 = LogLevel.ERROR;
            StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda02 = new StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0(2);
            LogBuffer logBuffer2 = statusBarNotificationActivityStarterLogger.buffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("NotifActivityStarter", logLevel2, statusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda02, null);
            ((LogMessageImpl) logMessageObtain2).str1 = NotificationUtils.logKey(notificationEntry);
            logBuffer2.commit(logMessageObtain2);
            if (LsRune.SECURITY_SWIPE_BOUNCER) {
                setShowSwipeBouncer(false);
                return;
            }
            return;
        }
        if (pendingIntent == null || !pendingIntent.isActivity() || zIsBubble) {
            z = false;
        } else {
            z = false;
            z3 = true;
        }
        NotificationLockscreenUserManager notificationLockscreenUserManager = this.mLockscreenUserManager;
        ActivityIntentHelper activityIntentHelper = this.mActivityIntentHelper;
        if (!z3 || activityIntentHelper.getPendingTargetActivityInfo(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId, pendingIntent) != null) {
            activityIntentHelper.getClass();
            z2 = (pendingIntent == null || pendingIntent.getCreatorUserHandle() == null || (identifier = pendingIntent.getCreatorUserHandle().getIdentifier()) == 0 || !activityIntentHelper.mLpu.isSeparateProfileChallengeEnabled(identifier) || !activityIntentHelper.mKm.isDeviceLocked(identifier) || activityIntentHelper.mKm.createConfirmDeviceCredentialIntent(null, null, identifier) == null) ? z : true;
        }
        ActivityStarter activityStarter = this.mActivityStarter;
        boolean z5 = (z2 || !activityStarter.shouldAnimateLaunch(z3)) ? z : true;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (keyguardStateControllerImpl.mShowing && pendingIntent != null && activityIntentHelper.wouldPendingShowOverLockscreen(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId, pendingIntent)) {
            z = true;
        }
        final boolean z6 = z5;
        final boolean z7 = z;
        ActivityStarter.OnDismissAction onDismissAction = new ActivityStarter.OnDismissAction(this) { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.1
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                onKeyguardDismissedAction.onDismiss(pendingIntent, z3, z6, z7);
                return false;
            }

            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean willRunAnimationOnKeyguard() {
                return z6;
            }
        };
        KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_NOTIFICATION);
        if (z7) {
            this.mIsCollapsingToShowActivityOverLockscreen = true;
            onDismissAction.onDismiss();
            return;
        }
        activityStarter.dismissKeyguardThenExecute(onDismissAction, null, z2);
        if (keyguardStateControllerImpl.mShowing && this.mStatusBarKeyguardViewManager.isSecure() && keyguardStateControllerImpl.mOccluded) {
            this.mShadeController.closeShadeIfOpen();
        }
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
