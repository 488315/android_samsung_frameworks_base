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
import android.content.pm.ActivityInfo;
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
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.p038wm.shell.bubbles.BubbleController;
import com.android.p038wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda2;
import com.android.p038wm.shell.bubbles.BubbleEntry;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.Rune;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationClickNotifier$onNotificationClick$1;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.NotificationLaunchAnimatorControllerProvider;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.OnUserInteractionCallback;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.policy.HeadsUpUtil;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.util.NotificationSAUtil;
import com.android.systemui.wmshell.BubblesManager;
import com.samsung.android.knox.SemPersonaManager;
import dagger.Lazy;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarNotificationActivityStarter implements NotificationActivityStarter {
    public final ActivityIntentHelper mActivityIntentHelper;
    public final ActivityLaunchAnimator mActivityLaunchAnimator;
    public final ActivityStarter mActivityStarter;
    public final Lazy mAssistManagerLazy;
    public final Optional mBubblesManagerOptional;
    public final CentralSurfaces mCentralSurfaces;
    public final NotificationClickNotifier mClickNotifier;
    public final Context mContext;
    public final IDreamManager mDreamManager;
    public final HeadsUpManagerPhone mHeadsUpManager;
    public boolean mIsCollapsingToShowActivityOverLockscreen;
    public final KeyguardManager mKeyguardManager;
    public final KeyguardStateController mKeyguardStateController;
    public final LockPatternUtils mLockPatternUtils;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final StatusBarNotificationActivityStarterLogger mLogger;
    public final Handler mMainThreadHandler;
    public final MetricsLogger mMetricsLogger;
    public final NotificationLaunchAnimatorControllerProvider mNotificationAnimationProvider;
    public final OnUserInteractionCallback mOnUserInteractionCallback;
    public final NotificationPresenter mPresenter;
    public final NotificationRemoteInputManager mRemoteInputManager;
    public final ShadeController mShadeController;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final StatusBarRemoteInputCallback mStatusBarRemoteInputCallback;
    public final Executor mUiBgExecutor;
    public final UserTracker mUserTracker;
    public final NotificationVisibilityProvider mVisibilityProvider;
    public boolean mShouldSkipFullScreenIntent = false;
    public NotificationEntry mPendingFullscreenEntry = null;
    public Boolean mIsStartFullscreenIntentWhenSubscreen = Boolean.FALSE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$2 */
    public final class C31442 implements ActivityStarter.OnDismissAction {
        public final /* synthetic */ boolean val$animate;
        public final /* synthetic */ int val$appUid;
        public final /* synthetic */ Intent val$intent;
        public final /* synthetic */ ExpandableNotificationRow val$row;

        public C31442(ExpandableNotificationRow expandableNotificationRow, boolean z, Intent intent, int i) {
            this.val$row = expandableNotificationRow;
            this.val$animate = z;
            this.val$intent = intent;
            this.val$appUid = i;
        }

        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
        public final boolean onDismiss() {
            final ExpandableNotificationRow expandableNotificationRow = this.val$row;
            final boolean z = this.val$animate;
            final Intent intent = this.val$intent;
            final int i = this.val$appUid;
            AsyncTask.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    final StatusBarNotificationActivityStarter.C31442 c31442 = StatusBarNotificationActivityStarter.C31442.this;
                    ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                    boolean z2 = z;
                    final Intent intent2 = intent;
                    final int i2 = i;
                    c31442.getClass();
                    StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = StatusBarNotificationActivityStarter.this;
                    StatusBarLaunchAnimatorController statusBarLaunchAnimatorController = new StatusBarLaunchAnimatorController(statusBarNotificationActivityStarter.mNotificationAnimationProvider.getAnimatorController(expandableNotificationRow2, null), statusBarNotificationActivityStarter.mCentralSurfaces, true);
                    String str = intent2.getPackage();
                    Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$2$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            StatusBarNotificationActivityStarter statusBarNotificationActivityStarter2 = StatusBarNotificationActivityStarter.this;
                            return Integer.valueOf(TaskStackBuilder.create(statusBarNotificationActivityStarter2.mContext).addNextIntentWithParentStack(intent2).startActivities(CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) statusBarNotificationActivityStarter2.mCentralSurfaces).mDisplayId, (RemoteAnimationAdapter) obj), new UserHandle(UserHandle.getUserId(i2))));
                        }
                    };
                    ActivityLaunchAnimator activityLaunchAnimator = statusBarNotificationActivityStarter.mActivityLaunchAnimator;
                    activityLaunchAnimator.getClass();
                    activityLaunchAnimator.startIntentWithAnimation(statusBarLaunchAnimatorController, z2, str, false, function1);
                }
            });
            return false;
        }

        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
        public final boolean willRunAnimationOnKeyguard() {
            return this.val$animate;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$3 */
    public final class C31453 implements ActivityStarter.OnDismissAction {
        public final /* synthetic */ boolean val$animate;
        public final /* synthetic */ boolean val$showHistory;
        public final /* synthetic */ View val$view;

        public C31453(boolean z, View view, boolean z2) {
            this.val$showHistory = z;
            this.val$view = view;
            this.val$animate = z2;
        }

        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
        public final boolean onDismiss() {
            final boolean z = this.val$showHistory;
            final View view = this.val$view;
            final boolean z2 = this.val$animate;
            AsyncTask.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    final StatusBarNotificationActivityStarter.C31453 c31453 = StatusBarNotificationActivityStarter.C31453.this;
                    boolean z3 = z;
                    View view2 = view;
                    boolean z4 = z2;
                    c31453.getClass();
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
                    GhostedViewLaunchAnimatorController fromView = ActivityLaunchAnimator.Controller.fromView(view2, 30);
                    StatusBarLaunchAnimatorController statusBarLaunchAnimatorController = fromView == null ? null : new StatusBarLaunchAnimatorController(fromView, statusBarNotificationActivityStarter.mCentralSurfaces, true);
                    String str = intent.getPackage();
                    Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$3$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            StatusBarNotificationActivityStarter statusBarNotificationActivityStarter2 = StatusBarNotificationActivityStarter.this;
                            return Integer.valueOf(addNextIntent.startActivities(CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) statusBarNotificationActivityStarter2.mCentralSurfaces).mDisplayId, (RemoteAnimationAdapter) obj), ((UserTrackerImpl) statusBarNotificationActivityStarter2.mUserTracker).getUserHandle()));
                        }
                    };
                    ActivityLaunchAnimator activityLaunchAnimator = statusBarNotificationActivityStarter.mActivityLaunchAnimator;
                    activityLaunchAnimator.getClass();
                    activityLaunchAnimator.startIntentWithAnimation(statusBarLaunchAnimatorController, z4, str, false, function1);
                }
            });
            return true;
        }

        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
        public final boolean willRunAnimationOnKeyguard() {
            return this.val$animate;
        }
    }

    public StatusBarNotificationActivityStarter(Context context, Handler handler, Executor executor, NotificationVisibilityProvider notificationVisibilityProvider, HeadsUpManagerPhone headsUpManagerPhone, ActivityStarter activityStarter, NotificationClickNotifier notificationClickNotifier, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardManager keyguardManager, IDreamManager iDreamManager, Optional<BubblesManager> optional, Lazy lazy, NotificationRemoteInputManager notificationRemoteInputManager, NotificationLockscreenUserManager notificationLockscreenUserManager, ShadeController shadeController, KeyguardStateController keyguardStateController, NotificationInterruptStateProvider notificationInterruptStateProvider, LockPatternUtils lockPatternUtils, StatusBarRemoteInputCallback statusBarRemoteInputCallback, ActivityIntentHelper activityIntentHelper, MetricsLogger metricsLogger, StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger, OnUserInteractionCallback onUserInteractionCallback, CentralSurfaces centralSurfaces, NotificationPresenter notificationPresenter, ShadeViewController shadeViewController, ActivityLaunchAnimator activityLaunchAnimator, NotificationLaunchAnimatorControllerProvider notificationLaunchAnimatorControllerProvider, LaunchFullScreenIntentProvider launchFullScreenIntentProvider, FeatureFlags featureFlags, UserTracker userTracker) {
        this.mContext = context;
        this.mMainThreadHandler = handler;
        this.mUiBgExecutor = executor;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mHeadsUpManager = headsUpManagerPhone;
        this.mActivityStarter = activityStarter;
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
        this.mMetricsLogger = metricsLogger;
        this.mLogger = statusBarNotificationActivityStarterLogger;
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mCentralSurfaces = centralSurfaces;
        this.mPresenter = notificationPresenter;
        this.mActivityLaunchAnimator = activityLaunchAnimator;
        this.mNotificationAnimationProvider = notificationLaunchAnimatorControllerProvider;
        this.mUserTracker = userTracker;
        launchFullScreenIntentProvider.listeners.addIfAbsent(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda0(this));
    }

    public static boolean shouldAutoCancel(StatusBarNotification statusBarNotification) {
        return (statusBarNotification.getNotification().flags & 16) == 16;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void launchFullScreenIntent(NotificationEntry notificationEntry) {
        boolean z;
        StatusBarNotification statusBarNotification;
        boolean z2 = ((StatusBarNotificationPresenter) this.mPresenter).mVrMode;
        StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger = this.mLogger;
        if (z2) {
            statusBarNotificationActivityStarterLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            C3146x3dc774f0 c3146x3dc774f0 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logFullScreenIntentSuppressedByVR$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("No Fullscreen intent: suppressed by VR mode: ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = statusBarNotificationActivityStarterLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotifActivityStarter", logLevel, c3146x3dc774f0, null);
            NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain, logBuffer, obtain);
            return;
        }
        int i = 0;
        if (NotiRune.NOTI_SUBSCREEN_PENDING_CALL_FULLSCRREN_INTENT) {
            if (this.mShouldSkipFullScreenIntent && notificationEntry != null && (statusBarNotification = notificationEntry.mSbn) != null && "call".equals(statusBarNotification.getNotification().category)) {
                if (this.mIsStartFullscreenIntentWhenSubscreen.booleanValue()) {
                    this.mIsStartFullscreenIntentWhenSubscreen = Boolean.FALSE;
                } else {
                    try {
                        if (statusBarNotification.getPackageName() == null || !ActivityTaskManager.getService().isPackageEnabledForCoverLauncher(statusBarNotification.getPackageName(), statusBarNotification.getUser().getIdentifier())) {
                            this.mPendingFullscreenEntry = notificationEntry;
                        } else {
                            this.mPendingFullscreenEntry = null;
                        }
                    } catch (RemoteException unused) {
                    }
                    z = true;
                    if (z) {
                        Log.d("StatusBarNotificationActivityStarter", "pending wx call");
                        return;
                    }
                }
            }
            z = false;
            if (z) {
            }
        }
        if (((PowerManager) this.mContext.getSystemService(PowerManager.class)).isInteractive()) {
            this.mUiBgExecutor.execute(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1(this, i));
        }
        PendingIntent pendingIntent = notificationEntry.mSbn.getNotification().fullScreenIntent;
        statusBarNotificationActivityStarterLogger.getClass();
        LogLevel logLevel2 = LogLevel.INFO;
        C3151x57d5767b c3151x57d5767b = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendingFullScreenIntent$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m32m("Notification ", logMessage.getStr1(), " has fullScreenIntent; sending fullScreenIntent ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer2 = statusBarNotificationActivityStarterLogger.buffer;
        LogMessage obtain2 = logBuffer2.obtain("NotifActivityStarter", logLevel2, c3151x57d5767b, null);
        obtain2.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        obtain2.setStr2(pendingIntent.getIntent().toString());
        logBuffer2.commit(obtain2);
        try {
            EventLog.writeEvent(36002, notificationEntry.mKey);
            ((CentralSurfacesImpl) this.mCentralSurfaces).wakeUpForFullScreenIntent(notificationEntry.mSbn.getPackageName());
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

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00ad, code lost:
    
        if ((r1.getPendingTargetActivityInfo(((com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl) r0).mCurrentUserId, r6) == null) == false) goto L49;
     */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00f1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onNotificationClicked(final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow) {
        boolean z;
        boolean z2;
        int identifier;
        KeyguardStateControllerImpl keyguardStateControllerImpl;
        boolean z3;
        ActivityInfo pendingTargetActivityInfo;
        CharSequence text;
        StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger = this.mLogger;
        statusBarNotificationActivityStarterLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        C3154xbe0e6f79 c3154xbe0e6f79 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logStartingActivityFromClick$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("(1/5) onNotificationClicked: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = statusBarNotificationActivityStarterLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotifActivityStarter", logLevel, c3154xbe0e6f79, null);
        NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain, logBuffer, obtain);
        NotificationRemoteInputManager notificationRemoteInputManager = this.mRemoteInputManager;
        RemoteInputController remoteInputController = notificationRemoteInputManager.mRemoteInputController;
        if (remoteInputController != null && remoteInputController.pruneWeakThenRemoveAndContains(notificationEntry, null, null)) {
            NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
            RemoteInputView remoteInputView = notificationContentView.mExpandedRemoteInput;
            if (remoteInputView == null || !remoteInputView.isActive()) {
                RemoteInputView remoteInputView2 = notificationContentView.mHeadsUpRemoteInput;
                text = (remoteInputView2 == null || !remoteInputView2.isActive()) ? null : notificationContentView.mHeadsUpRemoteInput.getText();
            } else {
                text = notificationContentView.mExpandedRemoteInput.getText();
            }
            if (!TextUtils.isEmpty(text)) {
                notificationRemoteInputManager.closeRemoteInputs(false);
                return;
            }
        }
        Notification notification2 = notificationEntry.mSbn.getNotification();
        PendingIntent pendingIntent = notification2.contentIntent;
        if (pendingIntent == null) {
            pendingIntent = notification2.fullScreenIntent;
        }
        boolean isBubble = notificationEntry.isBubble();
        CentralSurfaces centralSurfaces = this.mCentralSurfaces;
        if (pendingIntent == null && !isBubble) {
            LogMessage obtain2 = logBuffer.obtain("NotifActivityStarter", LogLevel.ERROR, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logNonClickableNotification$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("onNotificationClicked called for non-clickable notification! ", ((LogMessage) obj).getStr1());
                }
            }, null);
            NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain2, logBuffer, obtain2);
            if (LsRune.SECURITY_SWIPE_BOUNCER) {
                ((CentralSurfacesImpl) centralSurfaces).setShowSwipeBouncer(false);
                return;
            }
            return;
        }
        boolean z4 = (pendingIntent == null || !pendingIntent.isActivity() || isBubble) ? false : true;
        NotificationLockscreenUserManager notificationLockscreenUserManager = this.mLockscreenUserManager;
        ActivityIntentHelper activityIntentHelper = this.mActivityIntentHelper;
        if (z4) {
        }
        activityIntentHelper.getClass();
        if (pendingIntent != null && pendingIntent.getCreatorUserHandle() != null && (identifier = pendingIntent.getCreatorUserHandle().getIdentifier()) != 0 && activityIntentHelper.mLpu.isSeparateProfileChallengeEnabled(identifier)) {
            KeyguardManager keyguardManager = activityIntentHelper.mKm;
            if (keyguardManager.isDeviceLocked(identifier) && keyguardManager.createConfirmDeviceCredentialIntent(null, null, identifier) != null) {
                z = true;
                if (!z) {
                    z2 = false;
                    if (!z2) {
                        centralSurfaces.getClass();
                    }
                    final boolean z5 = false;
                    keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
                    if (keyguardStateControllerImpl.mShowing && pendingIntent != null) {
                        pendingTargetActivityInfo = activityIntentHelper.getPendingTargetActivityInfo(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId, pendingIntent);
                        if (pendingTargetActivityInfo == null && (pendingTargetActivityInfo.flags & 8389632) > 0) {
                            z3 = true;
                            final PendingIntent pendingIntent2 = pendingIntent;
                            final boolean z6 = z4;
                            final boolean z7 = z3;
                            ActivityStarter.OnDismissAction onDismissAction = new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.1
                                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                                public final boolean onDismiss() {
                                    final NotificationEntry notificationEntry2 = notificationEntry;
                                    final ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                                    final PendingIntent pendingIntent3 = pendingIntent2;
                                    final boolean z8 = z6;
                                    final boolean z9 = z5;
                                    final StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = StatusBarNotificationActivityStarter.this;
                                    StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger2 = statusBarNotificationActivityStarter.mLogger;
                                    statusBarNotificationActivityStarterLogger2.getClass();
                                    LogLevel logLevel2 = LogLevel.DEBUG;
                                    C3147x5700bdb1 c3147x5700bdb1 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logHandleClickAfterKeyguardDismissed$2
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj) {
                                            return KeyAttributes$$ExternalSyntheticOutline0.m21m("(2/5) handleNotificationClickAfterKeyguardDismissed: ", ((LogMessage) obj).getStr1());
                                        }
                                    };
                                    LogBuffer logBuffer2 = statusBarNotificationActivityStarterLogger2.buffer;
                                    LogMessage obtain3 = logBuffer2.obtain("NotifActivityStarter", logLevel2, c3147x5700bdb1, null);
                                    obtain3.setStr1(NotificationUtilsKt.getLogKey(notificationEntry2));
                                    logBuffer2.commit(obtain3);
                                    Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda2
                                        /* JADX WARN: Can't wrap try/catch for region: R(26:0|1|(2:2|3)|4|(2:8|(2:14|(2:16|17)(2:19|20)))|21|(1:23)(1:103)|24|(3:26|(1:101)(1:30)|(14:32|33|(1:100)(5:36|(1:38)|(1:99)(1:42)|43|(1:47))|48|(3:50|(2:52|(1:54)(1:55))|56)(8:79|80|(2:83|(2:85|86)(5:87|88|89|90|91))|96|88|89|90|91)|(1:78)|59|(9:61|(1:63)(1:76)|73|(1:75)|66|67|68|69|70)(1:77)|65|66|67|68|69|70))|102|33|(0)|100|48|(0)(0)|(0)|78|59|(0)(0)|65|66|67|68|69|70|(1:(0))) */
                                        /* JADX WARN: Code restructure failed: missing block: B:64:0x01fb, code lost:
                                        
                                            if (r18.isNotificationKeptForRemoteInputHistory(r1) != false) goto L90;
                                         */
                                        /* JADX WARN: Removed duplicated region for block: B:50:0x0110  */
                                        /* JADX WARN: Removed duplicated region for block: B:58:0x01d4 A[ADDED_TO_REGION] */
                                        /* JADX WARN: Removed duplicated region for block: B:61:0x01eb  */
                                        /* JADX WARN: Removed duplicated region for block: B:77:0x0222  */
                                        /* JADX WARN: Removed duplicated region for block: B:79:0x016a  */
                                        @Override // java.lang.Runnable
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final void run() {
                                            Intent intent;
                                            boolean z10;
                                            boolean z11;
                                            String str;
                                            boolean canBubble;
                                            Handler handler;
                                            NotificationRemoteInputManager notificationRemoteInputManager2;
                                            final Intent intent2;
                                            String str2;
                                            boolean z12;
                                            final StatusBarNotificationActivityStarter statusBarNotificationActivityStarter2 = StatusBarNotificationActivityStarter.this;
                                            final NotificationEntry notificationEntry3 = notificationEntry2;
                                            final ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow2;
                                            final PendingIntent pendingIntent4 = pendingIntent3;
                                            boolean z13 = z8;
                                            boolean z14 = z9;
                                            statusBarNotificationActivityStarter2.getClass();
                                            String str3 = notificationEntry3.mKey;
                                            StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger3 = statusBarNotificationActivityStarter2.mLogger;
                                            statusBarNotificationActivityStarterLogger3.getClass();
                                            LogLevel logLevel3 = LogLevel.DEBUG;
                                            C3148xe49d9e41 c3148xe49d9e41 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logHandleClickAfterPanelCollapsed$2
                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj) {
                                                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("(3/5) handleNotificationClickAfterPanelCollapsed: ", ((LogMessage) obj).getStr1());
                                                }
                                            };
                                            LogBuffer logBuffer3 = statusBarNotificationActivityStarterLogger3.buffer;
                                            LogMessage obtain4 = logBuffer3.obtain("NotifActivityStarter", logLevel3, c3148xe49d9e41, null);
                                            NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry3, obtain4, logBuffer3, obtain4);
                                            try {
                                                ActivityManager.getService().resumeAppSwitches();
                                            } catch (RemoteException unused) {
                                            }
                                            ShadeController shadeController = statusBarNotificationActivityStarter2.mShadeController;
                                            Handler handler2 = statusBarNotificationActivityStarter2.mMainThreadHandler;
                                            if ((z13 && StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) || !z13) {
                                                int identifier2 = pendingIntent4.getCreatorUserHandle().getIdentifier();
                                                if (statusBarNotificationActivityStarter2.mLockPatternUtils.isSeparateProfileChallengeEnabled(identifier2) && statusBarNotificationActivityStarter2.mKeyguardManager.isDeviceLocked(identifier2) && statusBarNotificationActivityStarter2.mStatusBarRemoteInputCallback.startWorkChallengeIfNecessary(identifier2, pendingIntent4.getIntentSender(), str3)) {
                                                    statusBarNotificationActivityStarter2.removeHunAfterClick(expandableNotificationRow3);
                                                    if (Looper.getMainLooper().isCurrentThread()) {
                                                        ((ShadeControllerImpl) shadeController).collapseShade();
                                                        return;
                                                    } else {
                                                        Objects.requireNonNull(shadeController);
                                                        handler2.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1(shadeController, 1));
                                                        return;
                                                    }
                                                }
                                            }
                                            CharSequence charSequence = !TextUtils.isEmpty(notificationEntry3.remoteInputText) ? notificationEntry3.remoteInputText : null;
                                            boolean isEmpty = TextUtils.isEmpty(charSequence);
                                            NotificationRemoteInputManager notificationRemoteInputManager3 = statusBarNotificationActivityStarter2.mRemoteInputManager;
                                            if (!isEmpty) {
                                                RemoteInputController remoteInputController2 = notificationRemoteInputManager3.mRemoteInputController;
                                                if (!(remoteInputController2 != null && remoteInputController2.mSpinning.containsKey(str3))) {
                                                    intent = new Intent().putExtra("android.remoteInputDraft", charSequence.toString());
                                                    z10 = Rune.SYSUI_APPLOCK;
                                                    if (z10 || !z13) {
                                                        z11 = z14;
                                                        str = str3;
                                                    } else {
                                                        ComponentName component = pendingIntent4.getIntent() != null ? pendingIntent4.getIntent().getComponent() : null;
                                                        String targetPackage = (component == null || component.getPackageName() == null) ? pendingIntent4.getTargetPackage() : component.getPackageName();
                                                        int identifier3 = pendingIntent4.getCreatorUserHandle().getIdentifier();
                                                        str = str3;
                                                        z11 = z14;
                                                        if (((ActivityManager) statusBarNotificationActivityStarter2.mContext.getSystemService("activity")).isAppLockedPackage(targetPackage) && !SemPersonaManager.isKnoxId(identifier3)) {
                                                            notificationRemoteInputManager3.startAppLockCheckService(targetPackage);
                                                        }
                                                    }
                                                    canBubble = notificationEntry3.canBubble();
                                                    if (canBubble) {
                                                        LogMessage obtain5 = logBuffer3.obtain("NotifActivityStarter", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logStartNotificationIntent$2
                                                            @Override // kotlin.jvm.functions.Function1
                                                            public final Object invoke(Object obj) {
                                                                return KeyAttributes$$ExternalSyntheticOutline0.m21m("(4/5) startNotificationIntent: ", ((LogMessage) obj).getStr1());
                                                            }
                                                        }, null);
                                                        NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry3, obtain5, logBuffer3, obtain5);
                                                        try {
                                                            try {
                                                                if (z10 && z13) {
                                                                    if (intent != null) {
                                                                        intent.putExtra("LAUNCH_FROM_NOTIFICATION", 1);
                                                                    } else {
                                                                        intent2 = new Intent().putExtra("LAUNCH_FROM_NOTIFICATION", 1);
                                                                        handler = handler2;
                                                                        notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                                                        statusBarNotificationActivityStarter2.mActivityLaunchAnimator.startPendingIntentWithAnimation(new StatusBarLaunchAnimatorController(statusBarNotificationActivityStarter2.mNotificationAnimationProvider.getAnimatorController(expandableNotificationRow3, null), statusBarNotificationActivityStarter2.mCentralSurfaces, z13), z11, pendingIntent4.getCreatorPackage(), new ActivityLaunchAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4
                                                                            @Override // com.android.systemui.animation.ActivityLaunchAnimator.PendingIntentStarter
                                                                            public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                                                                                Bundle activityOptions;
                                                                                PendingIntent pendingIntent5 = pendingIntent4;
                                                                                Intent intent3 = intent2;
                                                                                StatusBarNotificationActivityStarter statusBarNotificationActivityStarter3 = StatusBarNotificationActivityStarter.this;
                                                                                statusBarNotificationActivityStarter3.getClass();
                                                                                ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow3;
                                                                                long j = expandableNotificationRow4.mLastActionUpTime;
                                                                                expandableNotificationRow4.mLastActionUpTime = 0L;
                                                                                CentralSurfaces centralSurfaces2 = statusBarNotificationActivityStarter3.mCentralSurfaces;
                                                                                if (j > 0) {
                                                                                    int i = ((CentralSurfacesImpl) centralSurfaces2).mDisplayId;
                                                                                    boolean z15 = ((KeyguardStateControllerImpl) statusBarNotificationActivityStarter3.mKeyguardStateController).mShowing;
                                                                                    ActivityOptions defaultActivityOptions = CentralSurfaces.getDefaultActivityOptions(remoteAnimationAdapter);
                                                                                    defaultActivityOptions.setSourceInfo(z15 ? 3 : 2, j);
                                                                                    defaultActivityOptions.setLaunchDisplayId(i);
                                                                                    defaultActivityOptions.setCallerDisplayId(i);
                                                                                    defaultActivityOptions.setPendingIntentBackgroundActivityLaunchAllowed(true);
                                                                                    activityOptions = defaultActivityOptions.toBundle();
                                                                                } else {
                                                                                    activityOptions = CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) centralSurfaces2).mDisplayId, remoteAnimationAdapter);
                                                                                }
                                                                                int sendAndReturnResult = pendingIntent5.sendAndReturnResult(statusBarNotificationActivityStarter3.mContext, 0, intent3, null, null, null, activityOptions);
                                                                                StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger4 = statusBarNotificationActivityStarter3.mLogger;
                                                                                statusBarNotificationActivityStarterLogger4.getClass();
                                                                                LogLevel logLevel4 = LogLevel.INFO;
                                                                                C3150xf7e3e395 c3150xf7e3e395 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendPendingIntent$2
                                                                                    @Override // kotlin.jvm.functions.Function1
                                                                                    public final Object invoke(Object obj) {
                                                                                        LogMessage logMessage = (LogMessage) obj;
                                                                                        String str22 = logMessage.getStr2();
                                                                                        String str1 = logMessage.getStr1();
                                                                                        int int1 = logMessage.getInt1();
                                                                                        StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("(5/5) Started intent ", str22, " for notification ", str1, " with result code ");
                                                                                        m87m.append(int1);
                                                                                        return m87m.toString();
                                                                                    }
                                                                                };
                                                                                LogBuffer logBuffer4 = statusBarNotificationActivityStarterLogger4.buffer;
                                                                                LogMessage obtain6 = logBuffer4.obtain("NotifActivityStarter", logLevel4, c3150xf7e3e395, null);
                                                                                obtain6.setStr1(NotificationUtilsKt.getLogKey(notificationEntry3));
                                                                                obtain6.setStr2(pendingIntent5.getIntent().toString());
                                                                                obtain6.setInt1(sendAndReturnResult);
                                                                                logBuffer4.commit(obtain6);
                                                                                return sendAndReturnResult;
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                                statusBarNotificationActivityStarter2.mActivityLaunchAnimator.startPendingIntentWithAnimation(new StatusBarLaunchAnimatorController(statusBarNotificationActivityStarter2.mNotificationAnimationProvider.getAnimatorController(expandableNotificationRow3, null), statusBarNotificationActivityStarter2.mCentralSurfaces, z13), z11, pendingIntent4.getCreatorPackage(), new ActivityLaunchAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4
                                                                    @Override // com.android.systemui.animation.ActivityLaunchAnimator.PendingIntentStarter
                                                                    public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                                                                        Bundle activityOptions;
                                                                        PendingIntent pendingIntent5 = pendingIntent4;
                                                                        Intent intent3 = intent2;
                                                                        StatusBarNotificationActivityStarter statusBarNotificationActivityStarter3 = StatusBarNotificationActivityStarter.this;
                                                                        statusBarNotificationActivityStarter3.getClass();
                                                                        ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow3;
                                                                        long j = expandableNotificationRow4.mLastActionUpTime;
                                                                        expandableNotificationRow4.mLastActionUpTime = 0L;
                                                                        CentralSurfaces centralSurfaces2 = statusBarNotificationActivityStarter3.mCentralSurfaces;
                                                                        if (j > 0) {
                                                                            int i = ((CentralSurfacesImpl) centralSurfaces2).mDisplayId;
                                                                            boolean z15 = ((KeyguardStateControllerImpl) statusBarNotificationActivityStarter3.mKeyguardStateController).mShowing;
                                                                            ActivityOptions defaultActivityOptions = CentralSurfaces.getDefaultActivityOptions(remoteAnimationAdapter);
                                                                            defaultActivityOptions.setSourceInfo(z15 ? 3 : 2, j);
                                                                            defaultActivityOptions.setLaunchDisplayId(i);
                                                                            defaultActivityOptions.setCallerDisplayId(i);
                                                                            defaultActivityOptions.setPendingIntentBackgroundActivityLaunchAllowed(true);
                                                                            activityOptions = defaultActivityOptions.toBundle();
                                                                        } else {
                                                                            activityOptions = CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) centralSurfaces2).mDisplayId, remoteAnimationAdapter);
                                                                        }
                                                                        int sendAndReturnResult = pendingIntent5.sendAndReturnResult(statusBarNotificationActivityStarter3.mContext, 0, intent3, null, null, null, activityOptions);
                                                                        StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger4 = statusBarNotificationActivityStarter3.mLogger;
                                                                        statusBarNotificationActivityStarterLogger4.getClass();
                                                                        LogLevel logLevel4 = LogLevel.INFO;
                                                                        C3150xf7e3e395 c3150xf7e3e395 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendPendingIntent$2
                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            public final Object invoke(Object obj) {
                                                                                LogMessage logMessage = (LogMessage) obj;
                                                                                String str22 = logMessage.getStr2();
                                                                                String str1 = logMessage.getStr1();
                                                                                int int1 = logMessage.getInt1();
                                                                                StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("(5/5) Started intent ", str22, " for notification ", str1, " with result code ");
                                                                                m87m.append(int1);
                                                                                return m87m.toString();
                                                                            }
                                                                        };
                                                                        LogBuffer logBuffer4 = statusBarNotificationActivityStarterLogger4.buffer;
                                                                        LogMessage obtain6 = logBuffer4.obtain("NotifActivityStarter", logLevel4, c3150xf7e3e395, null);
                                                                        obtain6.setStr1(NotificationUtilsKt.getLogKey(notificationEntry3));
                                                                        obtain6.setStr2(pendingIntent5.getIntent().toString());
                                                                        obtain6.setInt1(sendAndReturnResult);
                                                                        logBuffer4.commit(obtain6);
                                                                        return sendAndReturnResult;
                                                                    }
                                                                });
                                                            } catch (PendingIntent.CanceledException e) {
                                                                e = e;
                                                                LogMessage obtain6 = logBuffer3.obtain("NotifActivityStarter", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendingIntentFailed$2
                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public final Object invoke(Object obj) {
                                                                        return KeyAttributes$$ExternalSyntheticOutline0.m21m("Sending contentIntentFailed: ", ((LogMessage) obj).getStr1());
                                                                    }
                                                                }, null);
                                                                obtain6.setStr1(e.toString());
                                                                logBuffer3.commit(obtain6);
                                                                if (!z13) {
                                                                }
                                                                ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                                                                NotificationVisibility obtain7 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                                                                if (!canBubble) {
                                                                }
                                                                z12 = false;
                                                                NotificationClickNotifier notificationClickNotifier = statusBarNotificationActivityStarter2.mClickNotifier;
                                                                notificationClickNotifier.getClass();
                                                                notificationClickNotifier.barService.onNotificationClick(str2, obtain7);
                                                                notificationClickNotifier.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier, str2));
                                                                statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                                                            }
                                                            intent2 = intent;
                                                            handler = handler2;
                                                            notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                                        } catch (PendingIntent.CanceledException e2) {
                                                            e = e2;
                                                            handler = handler2;
                                                            notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                                        }
                                                    } else {
                                                        LogMessage obtain8 = logBuffer3.obtain("NotifActivityStarter", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logExpandingBubble$2
                                                            @Override // kotlin.jvm.functions.Function1
                                                            public final Object invoke(Object obj) {
                                                                return PathParser$$ExternalSyntheticOutline0.m29m("Expanding bubble for ", ((LogMessage) obj).getStr1(), " (rather than firing intent)");
                                                            }
                                                        }, null);
                                                        obtain8.setStr1(NotificationUtilsKt.getLogKey(notificationEntry3));
                                                        logBuffer3.commit(obtain8);
                                                        statusBarNotificationActivityStarter2.removeHunAfterClick(expandableNotificationRow3);
                                                        Optional optional = statusBarNotificationActivityStarter2.mBubblesManagerOptional;
                                                        if (optional.isPresent()) {
                                                            int i = 2;
                                                            if (Looper.getMainLooper().isCurrentThread()) {
                                                                BubblesManager bubblesManager = (BubblesManager) optional.get();
                                                                BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry3);
                                                                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                                                                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda2(bubblesImpl, notifToBubbleEntry, i));
                                                                ((ShadeControllerImpl) shadeController).collapseShade();
                                                            } else {
                                                                handler2.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3(statusBarNotificationActivityStarter2, notificationEntry3, i));
                                                            }
                                                        }
                                                        handler = handler2;
                                                        notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                                    }
                                                    if (!z13 || canBubble) {
                                                        ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                                                    }
                                                    NotificationVisibility obtain72 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                                                    if (!canBubble) {
                                                        if (StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) {
                                                            str2 = str;
                                                        } else {
                                                            str2 = str;
                                                        }
                                                        z12 = false;
                                                        z12 = false;
                                                        handler.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3(statusBarNotificationActivityStarter2, ((OnUserInteractionCallbackImpl) statusBarNotificationActivityStarter2.mOnUserInteractionCallback).registerFutureDismissal(notificationEntry3, 1), z12 ? 1 : 0));
                                                        if (StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) {
                                                            NotificationSAUtil.sendCancelLog(notificationEntry3, "QPNE0005");
                                                        }
                                                        NotificationClickNotifier notificationClickNotifier2 = statusBarNotificationActivityStarter2.mClickNotifier;
                                                        notificationClickNotifier2.getClass();
                                                        notificationClickNotifier2.barService.onNotificationClick(str2, obtain72);
                                                        notificationClickNotifier2.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier2, str2));
                                                        statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                                                    }
                                                    str2 = str;
                                                    z12 = false;
                                                    NotificationClickNotifier notificationClickNotifier22 = statusBarNotificationActivityStarter2.mClickNotifier;
                                                    notificationClickNotifier22.getClass();
                                                    notificationClickNotifier22.barService.onNotificationClick(str2, obtain72);
                                                    notificationClickNotifier22.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier22, str2));
                                                    statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                                                }
                                            }
                                            intent = null;
                                            z10 = Rune.SYSUI_APPLOCK;
                                            if (z10) {
                                            }
                                            z11 = z14;
                                            str = str3;
                                            canBubble = notificationEntry3.canBubble();
                                            if (canBubble) {
                                            }
                                            if (!z13) {
                                            }
                                            ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                                            NotificationVisibility obtain722 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                                            if (!canBubble) {
                                            }
                                            z12 = false;
                                            NotificationClickNotifier notificationClickNotifier222 = statusBarNotificationActivityStarter2.mClickNotifier;
                                            notificationClickNotifier222.getClass();
                                            notificationClickNotifier222.barService.onNotificationClick(str2, obtain722);
                                            notificationClickNotifier222.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier222, str2));
                                            statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                                        }
                                    };
                                    boolean z10 = z7;
                                    ShadeController shadeController = statusBarNotificationActivityStarter.mShadeController;
                                    if (z10) {
                                        ShadeControllerImpl shadeControllerImpl = (ShadeControllerImpl) shadeController;
                                        shadeControllerImpl.mPostCollapseRunnables.add(runnable);
                                        shadeControllerImpl.collapseShade(true);
                                        return false;
                                    }
                                    if (!((KeyguardStateControllerImpl) statusBarNotificationActivityStarter.mKeyguardStateController).mShowing || !((CentralSurfacesImpl) statusBarNotificationActivityStarter.mCentralSurfaces).isOccluded()) {
                                        runnable.run();
                                        return false;
                                    }
                                    statusBarNotificationActivityStarter.mStatusBarKeyguardViewManager.addAfterKeyguardGoneRunnable(runnable);
                                    ((ShadeControllerImpl) shadeController).collapseShade();
                                    return false;
                                }

                                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                                public final boolean willRunAnimationOnKeyguard() {
                                    return z5;
                                }
                            };
                            KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_NOTIFICATION);
                            if (!z3) {
                                this.mIsCollapsingToShowActivityOverLockscreen = true;
                                onDismissAction.onDismiss();
                                return;
                            }
                            this.mActivityStarter.dismissKeyguardThenExecute(onDismissAction, null, z2);
                            if (keyguardStateControllerImpl.mShowing && this.mStatusBarKeyguardViewManager.isSecure() && keyguardStateControllerImpl.mOccluded) {
                                ((ShadeControllerImpl) this.mShadeController).closeShadeIfOpen();
                                return;
                            }
                            return;
                        }
                    }
                    z3 = false;
                    final PendingIntent pendingIntent22 = pendingIntent;
                    final boolean z62 = z4;
                    final boolean z72 = z3;
                    ActivityStarter.OnDismissAction onDismissAction2 = new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.1
                        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                        public final boolean onDismiss() {
                            final NotificationEntry notificationEntry2 = notificationEntry;
                            final ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                            final PendingIntent pendingIntent3 = pendingIntent22;
                            final boolean z8 = z62;
                            final boolean z9 = z5;
                            final StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = StatusBarNotificationActivityStarter.this;
                            StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger2 = statusBarNotificationActivityStarter.mLogger;
                            statusBarNotificationActivityStarterLogger2.getClass();
                            LogLevel logLevel2 = LogLevel.DEBUG;
                            C3147x5700bdb1 c3147x5700bdb1 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logHandleClickAfterKeyguardDismissed$2
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("(2/5) handleNotificationClickAfterKeyguardDismissed: ", ((LogMessage) obj).getStr1());
                                }
                            };
                            LogBuffer logBuffer2 = statusBarNotificationActivityStarterLogger2.buffer;
                            LogMessage obtain3 = logBuffer2.obtain("NotifActivityStarter", logLevel2, c3147x5700bdb1, null);
                            obtain3.setStr1(NotificationUtilsKt.getLogKey(notificationEntry2));
                            logBuffer2.commit(obtain3);
                            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda2
                                /* JADX WARN: Can't wrap try/catch for region: R(26:0|1|(2:2|3)|4|(2:8|(2:14|(2:16|17)(2:19|20)))|21|(1:23)(1:103)|24|(3:26|(1:101)(1:30)|(14:32|33|(1:100)(5:36|(1:38)|(1:99)(1:42)|43|(1:47))|48|(3:50|(2:52|(1:54)(1:55))|56)(8:79|80|(2:83|(2:85|86)(5:87|88|89|90|91))|96|88|89|90|91)|(1:78)|59|(9:61|(1:63)(1:76)|73|(1:75)|66|67|68|69|70)(1:77)|65|66|67|68|69|70))|102|33|(0)|100|48|(0)(0)|(0)|78|59|(0)(0)|65|66|67|68|69|70|(1:(0))) */
                                /* JADX WARN: Code restructure failed: missing block: B:64:0x01fb, code lost:
                                
                                    if (r18.isNotificationKeptForRemoteInputHistory(r1) != false) goto L90;
                                 */
                                /* JADX WARN: Removed duplicated region for block: B:50:0x0110  */
                                /* JADX WARN: Removed duplicated region for block: B:58:0x01d4 A[ADDED_TO_REGION] */
                                /* JADX WARN: Removed duplicated region for block: B:61:0x01eb  */
                                /* JADX WARN: Removed duplicated region for block: B:77:0x0222  */
                                /* JADX WARN: Removed duplicated region for block: B:79:0x016a  */
                                @Override // java.lang.Runnable
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final void run() {
                                    Intent intent;
                                    boolean z10;
                                    boolean z11;
                                    String str;
                                    boolean canBubble;
                                    Handler handler;
                                    NotificationRemoteInputManager notificationRemoteInputManager2;
                                    final Intent intent2;
                                    String str2;
                                    boolean z12;
                                    final StatusBarNotificationActivityStarter statusBarNotificationActivityStarter2 = StatusBarNotificationActivityStarter.this;
                                    final NotificationEntry notificationEntry3 = notificationEntry2;
                                    final ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow2;
                                    final PendingIntent pendingIntent4 = pendingIntent3;
                                    boolean z13 = z8;
                                    boolean z14 = z9;
                                    statusBarNotificationActivityStarter2.getClass();
                                    String str3 = notificationEntry3.mKey;
                                    StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger3 = statusBarNotificationActivityStarter2.mLogger;
                                    statusBarNotificationActivityStarterLogger3.getClass();
                                    LogLevel logLevel3 = LogLevel.DEBUG;
                                    C3148xe49d9e41 c3148xe49d9e41 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logHandleClickAfterPanelCollapsed$2
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj) {
                                            return KeyAttributes$$ExternalSyntheticOutline0.m21m("(3/5) handleNotificationClickAfterPanelCollapsed: ", ((LogMessage) obj).getStr1());
                                        }
                                    };
                                    LogBuffer logBuffer3 = statusBarNotificationActivityStarterLogger3.buffer;
                                    LogMessage obtain4 = logBuffer3.obtain("NotifActivityStarter", logLevel3, c3148xe49d9e41, null);
                                    NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry3, obtain4, logBuffer3, obtain4);
                                    try {
                                        ActivityManager.getService().resumeAppSwitches();
                                    } catch (RemoteException unused) {
                                    }
                                    ShadeController shadeController = statusBarNotificationActivityStarter2.mShadeController;
                                    Handler handler2 = statusBarNotificationActivityStarter2.mMainThreadHandler;
                                    if ((z13 && StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) || !z13) {
                                        int identifier2 = pendingIntent4.getCreatorUserHandle().getIdentifier();
                                        if (statusBarNotificationActivityStarter2.mLockPatternUtils.isSeparateProfileChallengeEnabled(identifier2) && statusBarNotificationActivityStarter2.mKeyguardManager.isDeviceLocked(identifier2) && statusBarNotificationActivityStarter2.mStatusBarRemoteInputCallback.startWorkChallengeIfNecessary(identifier2, pendingIntent4.getIntentSender(), str3)) {
                                            statusBarNotificationActivityStarter2.removeHunAfterClick(expandableNotificationRow3);
                                            if (Looper.getMainLooper().isCurrentThread()) {
                                                ((ShadeControllerImpl) shadeController).collapseShade();
                                                return;
                                            } else {
                                                Objects.requireNonNull(shadeController);
                                                handler2.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1(shadeController, 1));
                                                return;
                                            }
                                        }
                                    }
                                    CharSequence charSequence = !TextUtils.isEmpty(notificationEntry3.remoteInputText) ? notificationEntry3.remoteInputText : null;
                                    boolean isEmpty = TextUtils.isEmpty(charSequence);
                                    NotificationRemoteInputManager notificationRemoteInputManager3 = statusBarNotificationActivityStarter2.mRemoteInputManager;
                                    if (!isEmpty) {
                                        RemoteInputController remoteInputController2 = notificationRemoteInputManager3.mRemoteInputController;
                                        if (!(remoteInputController2 != null && remoteInputController2.mSpinning.containsKey(str3))) {
                                            intent = new Intent().putExtra("android.remoteInputDraft", charSequence.toString());
                                            z10 = Rune.SYSUI_APPLOCK;
                                            if (z10 || !z13) {
                                                z11 = z14;
                                                str = str3;
                                            } else {
                                                ComponentName component = pendingIntent4.getIntent() != null ? pendingIntent4.getIntent().getComponent() : null;
                                                String targetPackage = (component == null || component.getPackageName() == null) ? pendingIntent4.getTargetPackage() : component.getPackageName();
                                                int identifier3 = pendingIntent4.getCreatorUserHandle().getIdentifier();
                                                str = str3;
                                                z11 = z14;
                                                if (((ActivityManager) statusBarNotificationActivityStarter2.mContext.getSystemService("activity")).isAppLockedPackage(targetPackage) && !SemPersonaManager.isKnoxId(identifier3)) {
                                                    notificationRemoteInputManager3.startAppLockCheckService(targetPackage);
                                                }
                                            }
                                            canBubble = notificationEntry3.canBubble();
                                            if (canBubble) {
                                                LogMessage obtain5 = logBuffer3.obtain("NotifActivityStarter", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logStartNotificationIntent$2
                                                    @Override // kotlin.jvm.functions.Function1
                                                    public final Object invoke(Object obj) {
                                                        return KeyAttributes$$ExternalSyntheticOutline0.m21m("(4/5) startNotificationIntent: ", ((LogMessage) obj).getStr1());
                                                    }
                                                }, null);
                                                NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry3, obtain5, logBuffer3, obtain5);
                                                try {
                                                    try {
                                                        if (z10 && z13) {
                                                            if (intent != null) {
                                                                intent.putExtra("LAUNCH_FROM_NOTIFICATION", 1);
                                                            } else {
                                                                intent2 = new Intent().putExtra("LAUNCH_FROM_NOTIFICATION", 1);
                                                                handler = handler2;
                                                                notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                                                statusBarNotificationActivityStarter2.mActivityLaunchAnimator.startPendingIntentWithAnimation(new StatusBarLaunchAnimatorController(statusBarNotificationActivityStarter2.mNotificationAnimationProvider.getAnimatorController(expandableNotificationRow3, null), statusBarNotificationActivityStarter2.mCentralSurfaces, z13), z11, pendingIntent4.getCreatorPackage(), new ActivityLaunchAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4
                                                                    @Override // com.android.systemui.animation.ActivityLaunchAnimator.PendingIntentStarter
                                                                    public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                                                                        Bundle activityOptions;
                                                                        PendingIntent pendingIntent5 = pendingIntent4;
                                                                        Intent intent3 = intent2;
                                                                        StatusBarNotificationActivityStarter statusBarNotificationActivityStarter3 = StatusBarNotificationActivityStarter.this;
                                                                        statusBarNotificationActivityStarter3.getClass();
                                                                        ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow3;
                                                                        long j = expandableNotificationRow4.mLastActionUpTime;
                                                                        expandableNotificationRow4.mLastActionUpTime = 0L;
                                                                        CentralSurfaces centralSurfaces2 = statusBarNotificationActivityStarter3.mCentralSurfaces;
                                                                        if (j > 0) {
                                                                            int i = ((CentralSurfacesImpl) centralSurfaces2).mDisplayId;
                                                                            boolean z15 = ((KeyguardStateControllerImpl) statusBarNotificationActivityStarter3.mKeyguardStateController).mShowing;
                                                                            ActivityOptions defaultActivityOptions = CentralSurfaces.getDefaultActivityOptions(remoteAnimationAdapter);
                                                                            defaultActivityOptions.setSourceInfo(z15 ? 3 : 2, j);
                                                                            defaultActivityOptions.setLaunchDisplayId(i);
                                                                            defaultActivityOptions.setCallerDisplayId(i);
                                                                            defaultActivityOptions.setPendingIntentBackgroundActivityLaunchAllowed(true);
                                                                            activityOptions = defaultActivityOptions.toBundle();
                                                                        } else {
                                                                            activityOptions = CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) centralSurfaces2).mDisplayId, remoteAnimationAdapter);
                                                                        }
                                                                        int sendAndReturnResult = pendingIntent5.sendAndReturnResult(statusBarNotificationActivityStarter3.mContext, 0, intent3, null, null, null, activityOptions);
                                                                        StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger4 = statusBarNotificationActivityStarter3.mLogger;
                                                                        statusBarNotificationActivityStarterLogger4.getClass();
                                                                        LogLevel logLevel4 = LogLevel.INFO;
                                                                        C3150xf7e3e395 c3150xf7e3e395 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendPendingIntent$2
                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            public final Object invoke(Object obj) {
                                                                                LogMessage logMessage = (LogMessage) obj;
                                                                                String str22 = logMessage.getStr2();
                                                                                String str1 = logMessage.getStr1();
                                                                                int int1 = logMessage.getInt1();
                                                                                StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("(5/5) Started intent ", str22, " for notification ", str1, " with result code ");
                                                                                m87m.append(int1);
                                                                                return m87m.toString();
                                                                            }
                                                                        };
                                                                        LogBuffer logBuffer4 = statusBarNotificationActivityStarterLogger4.buffer;
                                                                        LogMessage obtain6 = logBuffer4.obtain("NotifActivityStarter", logLevel4, c3150xf7e3e395, null);
                                                                        obtain6.setStr1(NotificationUtilsKt.getLogKey(notificationEntry3));
                                                                        obtain6.setStr2(pendingIntent5.getIntent().toString());
                                                                        obtain6.setInt1(sendAndReturnResult);
                                                                        logBuffer4.commit(obtain6);
                                                                        return sendAndReturnResult;
                                                                    }
                                                                });
                                                            }
                                                        }
                                                        statusBarNotificationActivityStarter2.mActivityLaunchAnimator.startPendingIntentWithAnimation(new StatusBarLaunchAnimatorController(statusBarNotificationActivityStarter2.mNotificationAnimationProvider.getAnimatorController(expandableNotificationRow3, null), statusBarNotificationActivityStarter2.mCentralSurfaces, z13), z11, pendingIntent4.getCreatorPackage(), new ActivityLaunchAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4
                                                            @Override // com.android.systemui.animation.ActivityLaunchAnimator.PendingIntentStarter
                                                            public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                                                                Bundle activityOptions;
                                                                PendingIntent pendingIntent5 = pendingIntent4;
                                                                Intent intent3 = intent2;
                                                                StatusBarNotificationActivityStarter statusBarNotificationActivityStarter3 = StatusBarNotificationActivityStarter.this;
                                                                statusBarNotificationActivityStarter3.getClass();
                                                                ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow3;
                                                                long j = expandableNotificationRow4.mLastActionUpTime;
                                                                expandableNotificationRow4.mLastActionUpTime = 0L;
                                                                CentralSurfaces centralSurfaces2 = statusBarNotificationActivityStarter3.mCentralSurfaces;
                                                                if (j > 0) {
                                                                    int i = ((CentralSurfacesImpl) centralSurfaces2).mDisplayId;
                                                                    boolean z15 = ((KeyguardStateControllerImpl) statusBarNotificationActivityStarter3.mKeyguardStateController).mShowing;
                                                                    ActivityOptions defaultActivityOptions = CentralSurfaces.getDefaultActivityOptions(remoteAnimationAdapter);
                                                                    defaultActivityOptions.setSourceInfo(z15 ? 3 : 2, j);
                                                                    defaultActivityOptions.setLaunchDisplayId(i);
                                                                    defaultActivityOptions.setCallerDisplayId(i);
                                                                    defaultActivityOptions.setPendingIntentBackgroundActivityLaunchAllowed(true);
                                                                    activityOptions = defaultActivityOptions.toBundle();
                                                                } else {
                                                                    activityOptions = CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) centralSurfaces2).mDisplayId, remoteAnimationAdapter);
                                                                }
                                                                int sendAndReturnResult = pendingIntent5.sendAndReturnResult(statusBarNotificationActivityStarter3.mContext, 0, intent3, null, null, null, activityOptions);
                                                                StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger4 = statusBarNotificationActivityStarter3.mLogger;
                                                                statusBarNotificationActivityStarterLogger4.getClass();
                                                                LogLevel logLevel4 = LogLevel.INFO;
                                                                C3150xf7e3e395 c3150xf7e3e395 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendPendingIntent$2
                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public final Object invoke(Object obj) {
                                                                        LogMessage logMessage = (LogMessage) obj;
                                                                        String str22 = logMessage.getStr2();
                                                                        String str1 = logMessage.getStr1();
                                                                        int int1 = logMessage.getInt1();
                                                                        StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("(5/5) Started intent ", str22, " for notification ", str1, " with result code ");
                                                                        m87m.append(int1);
                                                                        return m87m.toString();
                                                                    }
                                                                };
                                                                LogBuffer logBuffer4 = statusBarNotificationActivityStarterLogger4.buffer;
                                                                LogMessage obtain6 = logBuffer4.obtain("NotifActivityStarter", logLevel4, c3150xf7e3e395, null);
                                                                obtain6.setStr1(NotificationUtilsKt.getLogKey(notificationEntry3));
                                                                obtain6.setStr2(pendingIntent5.getIntent().toString());
                                                                obtain6.setInt1(sendAndReturnResult);
                                                                logBuffer4.commit(obtain6);
                                                                return sendAndReturnResult;
                                                            }
                                                        });
                                                    } catch (PendingIntent.CanceledException e) {
                                                        e = e;
                                                        LogMessage obtain6 = logBuffer3.obtain("NotifActivityStarter", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendingIntentFailed$2
                                                            @Override // kotlin.jvm.functions.Function1
                                                            public final Object invoke(Object obj) {
                                                                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Sending contentIntentFailed: ", ((LogMessage) obj).getStr1());
                                                            }
                                                        }, null);
                                                        obtain6.setStr1(e.toString());
                                                        logBuffer3.commit(obtain6);
                                                        if (!z13) {
                                                        }
                                                        ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                                                        NotificationVisibility obtain722 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                                                        if (!canBubble) {
                                                        }
                                                        z12 = false;
                                                        NotificationClickNotifier notificationClickNotifier222 = statusBarNotificationActivityStarter2.mClickNotifier;
                                                        notificationClickNotifier222.getClass();
                                                        notificationClickNotifier222.barService.onNotificationClick(str2, obtain722);
                                                        notificationClickNotifier222.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier222, str2));
                                                        statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                                                    }
                                                    intent2 = intent;
                                                    handler = handler2;
                                                    notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                                } catch (PendingIntent.CanceledException e2) {
                                                    e = e2;
                                                    handler = handler2;
                                                    notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                                }
                                            } else {
                                                LogMessage obtain8 = logBuffer3.obtain("NotifActivityStarter", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logExpandingBubble$2
                                                    @Override // kotlin.jvm.functions.Function1
                                                    public final Object invoke(Object obj) {
                                                        return PathParser$$ExternalSyntheticOutline0.m29m("Expanding bubble for ", ((LogMessage) obj).getStr1(), " (rather than firing intent)");
                                                    }
                                                }, null);
                                                obtain8.setStr1(NotificationUtilsKt.getLogKey(notificationEntry3));
                                                logBuffer3.commit(obtain8);
                                                statusBarNotificationActivityStarter2.removeHunAfterClick(expandableNotificationRow3);
                                                Optional optional = statusBarNotificationActivityStarter2.mBubblesManagerOptional;
                                                if (optional.isPresent()) {
                                                    int i = 2;
                                                    if (Looper.getMainLooper().isCurrentThread()) {
                                                        BubblesManager bubblesManager = (BubblesManager) optional.get();
                                                        BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry3);
                                                        BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                                                        ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda2(bubblesImpl, notifToBubbleEntry, i));
                                                        ((ShadeControllerImpl) shadeController).collapseShade();
                                                    } else {
                                                        handler2.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3(statusBarNotificationActivityStarter2, notificationEntry3, i));
                                                    }
                                                }
                                                handler = handler2;
                                                notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                            }
                                            if (!z13 || canBubble) {
                                                ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                                            }
                                            NotificationVisibility obtain7222 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                                            if (!canBubble) {
                                                if (StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) {
                                                    str2 = str;
                                                } else {
                                                    str2 = str;
                                                }
                                                z12 = false;
                                                z12 = false;
                                                handler.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3(statusBarNotificationActivityStarter2, ((OnUserInteractionCallbackImpl) statusBarNotificationActivityStarter2.mOnUserInteractionCallback).registerFutureDismissal(notificationEntry3, 1), z12 ? 1 : 0));
                                                if (StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) {
                                                    NotificationSAUtil.sendCancelLog(notificationEntry3, "QPNE0005");
                                                }
                                                NotificationClickNotifier notificationClickNotifier2222 = statusBarNotificationActivityStarter2.mClickNotifier;
                                                notificationClickNotifier2222.getClass();
                                                notificationClickNotifier2222.barService.onNotificationClick(str2, obtain7222);
                                                notificationClickNotifier2222.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier2222, str2));
                                                statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                                            }
                                            str2 = str;
                                            z12 = false;
                                            NotificationClickNotifier notificationClickNotifier22222 = statusBarNotificationActivityStarter2.mClickNotifier;
                                            notificationClickNotifier22222.getClass();
                                            notificationClickNotifier22222.barService.onNotificationClick(str2, obtain7222);
                                            notificationClickNotifier22222.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier22222, str2));
                                            statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                                        }
                                    }
                                    intent = null;
                                    z10 = Rune.SYSUI_APPLOCK;
                                    if (z10) {
                                    }
                                    z11 = z14;
                                    str = str3;
                                    canBubble = notificationEntry3.canBubble();
                                    if (canBubble) {
                                    }
                                    if (!z13) {
                                    }
                                    ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                                    NotificationVisibility obtain72222 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                                    if (!canBubble) {
                                    }
                                    z12 = false;
                                    NotificationClickNotifier notificationClickNotifier222222 = statusBarNotificationActivityStarter2.mClickNotifier;
                                    notificationClickNotifier222222.getClass();
                                    notificationClickNotifier222222.barService.onNotificationClick(str2, obtain72222);
                                    notificationClickNotifier222222.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier222222, str2));
                                    statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                                }
                            };
                            boolean z10 = z72;
                            ShadeController shadeController = statusBarNotificationActivityStarter.mShadeController;
                            if (z10) {
                                ShadeControllerImpl shadeControllerImpl = (ShadeControllerImpl) shadeController;
                                shadeControllerImpl.mPostCollapseRunnables.add(runnable);
                                shadeControllerImpl.collapseShade(true);
                                return false;
                            }
                            if (!((KeyguardStateControllerImpl) statusBarNotificationActivityStarter.mKeyguardStateController).mShowing || !((CentralSurfacesImpl) statusBarNotificationActivityStarter.mCentralSurfaces).isOccluded()) {
                                runnable.run();
                                return false;
                            }
                            statusBarNotificationActivityStarter.mStatusBarKeyguardViewManager.addAfterKeyguardGoneRunnable(runnable);
                            ((ShadeControllerImpl) shadeController).collapseShade();
                            return false;
                        }

                        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                        public final boolean willRunAnimationOnKeyguard() {
                            return z5;
                        }
                    };
                    KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_NOTIFICATION);
                    if (!z3) {
                    }
                }
                z2 = true;
                if (!z2) {
                }
                final boolean z52 = false;
                keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
                if (keyguardStateControllerImpl.mShowing) {
                    pendingTargetActivityInfo = activityIntentHelper.getPendingTargetActivityInfo(((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId, pendingIntent);
                    if (pendingTargetActivityInfo == null && (pendingTargetActivityInfo.flags & 8389632) > 0) {
                    }
                }
                z3 = false;
                final PendingIntent pendingIntent222 = pendingIntent;
                final boolean z622 = z4;
                final boolean z722 = z3;
                ActivityStarter.OnDismissAction onDismissAction22 = new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.1
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        final NotificationEntry notificationEntry2 = notificationEntry;
                        final ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                        final PendingIntent pendingIntent3 = pendingIntent222;
                        final boolean z8 = z622;
                        final boolean z9 = z52;
                        final StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = StatusBarNotificationActivityStarter.this;
                        StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger2 = statusBarNotificationActivityStarter.mLogger;
                        statusBarNotificationActivityStarterLogger2.getClass();
                        LogLevel logLevel2 = LogLevel.DEBUG;
                        C3147x5700bdb1 c3147x5700bdb1 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logHandleClickAfterKeyguardDismissed$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return KeyAttributes$$ExternalSyntheticOutline0.m21m("(2/5) handleNotificationClickAfterKeyguardDismissed: ", ((LogMessage) obj).getStr1());
                            }
                        };
                        LogBuffer logBuffer2 = statusBarNotificationActivityStarterLogger2.buffer;
                        LogMessage obtain3 = logBuffer2.obtain("NotifActivityStarter", logLevel2, c3147x5700bdb1, null);
                        obtain3.setStr1(NotificationUtilsKt.getLogKey(notificationEntry2));
                        logBuffer2.commit(obtain3);
                        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda2
                            /* JADX WARN: Can't wrap try/catch for region: R(26:0|1|(2:2|3)|4|(2:8|(2:14|(2:16|17)(2:19|20)))|21|(1:23)(1:103)|24|(3:26|(1:101)(1:30)|(14:32|33|(1:100)(5:36|(1:38)|(1:99)(1:42)|43|(1:47))|48|(3:50|(2:52|(1:54)(1:55))|56)(8:79|80|(2:83|(2:85|86)(5:87|88|89|90|91))|96|88|89|90|91)|(1:78)|59|(9:61|(1:63)(1:76)|73|(1:75)|66|67|68|69|70)(1:77)|65|66|67|68|69|70))|102|33|(0)|100|48|(0)(0)|(0)|78|59|(0)(0)|65|66|67|68|69|70|(1:(0))) */
                            /* JADX WARN: Code restructure failed: missing block: B:64:0x01fb, code lost:
                            
                                if (r18.isNotificationKeptForRemoteInputHistory(r1) != false) goto L90;
                             */
                            /* JADX WARN: Removed duplicated region for block: B:50:0x0110  */
                            /* JADX WARN: Removed duplicated region for block: B:58:0x01d4 A[ADDED_TO_REGION] */
                            /* JADX WARN: Removed duplicated region for block: B:61:0x01eb  */
                            /* JADX WARN: Removed duplicated region for block: B:77:0x0222  */
                            /* JADX WARN: Removed duplicated region for block: B:79:0x016a  */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void run() {
                                Intent intent;
                                boolean z10;
                                boolean z11;
                                String str;
                                boolean canBubble;
                                Handler handler;
                                NotificationRemoteInputManager notificationRemoteInputManager2;
                                final Intent intent2;
                                String str2;
                                boolean z12;
                                final StatusBarNotificationActivityStarter statusBarNotificationActivityStarter2 = StatusBarNotificationActivityStarter.this;
                                final NotificationEntry notificationEntry3 = notificationEntry2;
                                final ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow2;
                                final PendingIntent pendingIntent4 = pendingIntent3;
                                boolean z13 = z8;
                                boolean z14 = z9;
                                statusBarNotificationActivityStarter2.getClass();
                                String str3 = notificationEntry3.mKey;
                                StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger3 = statusBarNotificationActivityStarter2.mLogger;
                                statusBarNotificationActivityStarterLogger3.getClass();
                                LogLevel logLevel3 = LogLevel.DEBUG;
                                C3148xe49d9e41 c3148xe49d9e41 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logHandleClickAfterPanelCollapsed$2
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        return KeyAttributes$$ExternalSyntheticOutline0.m21m("(3/5) handleNotificationClickAfterPanelCollapsed: ", ((LogMessage) obj).getStr1());
                                    }
                                };
                                LogBuffer logBuffer3 = statusBarNotificationActivityStarterLogger3.buffer;
                                LogMessage obtain4 = logBuffer3.obtain("NotifActivityStarter", logLevel3, c3148xe49d9e41, null);
                                NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry3, obtain4, logBuffer3, obtain4);
                                try {
                                    ActivityManager.getService().resumeAppSwitches();
                                } catch (RemoteException unused) {
                                }
                                ShadeController shadeController = statusBarNotificationActivityStarter2.mShadeController;
                                Handler handler2 = statusBarNotificationActivityStarter2.mMainThreadHandler;
                                if ((z13 && StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) || !z13) {
                                    int identifier2 = pendingIntent4.getCreatorUserHandle().getIdentifier();
                                    if (statusBarNotificationActivityStarter2.mLockPatternUtils.isSeparateProfileChallengeEnabled(identifier2) && statusBarNotificationActivityStarter2.mKeyguardManager.isDeviceLocked(identifier2) && statusBarNotificationActivityStarter2.mStatusBarRemoteInputCallback.startWorkChallengeIfNecessary(identifier2, pendingIntent4.getIntentSender(), str3)) {
                                        statusBarNotificationActivityStarter2.removeHunAfterClick(expandableNotificationRow3);
                                        if (Looper.getMainLooper().isCurrentThread()) {
                                            ((ShadeControllerImpl) shadeController).collapseShade();
                                            return;
                                        } else {
                                            Objects.requireNonNull(shadeController);
                                            handler2.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1(shadeController, 1));
                                            return;
                                        }
                                    }
                                }
                                CharSequence charSequence = !TextUtils.isEmpty(notificationEntry3.remoteInputText) ? notificationEntry3.remoteInputText : null;
                                boolean isEmpty = TextUtils.isEmpty(charSequence);
                                NotificationRemoteInputManager notificationRemoteInputManager3 = statusBarNotificationActivityStarter2.mRemoteInputManager;
                                if (!isEmpty) {
                                    RemoteInputController remoteInputController2 = notificationRemoteInputManager3.mRemoteInputController;
                                    if (!(remoteInputController2 != null && remoteInputController2.mSpinning.containsKey(str3))) {
                                        intent = new Intent().putExtra("android.remoteInputDraft", charSequence.toString());
                                        z10 = Rune.SYSUI_APPLOCK;
                                        if (z10 || !z13) {
                                            z11 = z14;
                                            str = str3;
                                        } else {
                                            ComponentName component = pendingIntent4.getIntent() != null ? pendingIntent4.getIntent().getComponent() : null;
                                            String targetPackage = (component == null || component.getPackageName() == null) ? pendingIntent4.getTargetPackage() : component.getPackageName();
                                            int identifier3 = pendingIntent4.getCreatorUserHandle().getIdentifier();
                                            str = str3;
                                            z11 = z14;
                                            if (((ActivityManager) statusBarNotificationActivityStarter2.mContext.getSystemService("activity")).isAppLockedPackage(targetPackage) && !SemPersonaManager.isKnoxId(identifier3)) {
                                                notificationRemoteInputManager3.startAppLockCheckService(targetPackage);
                                            }
                                        }
                                        canBubble = notificationEntry3.canBubble();
                                        if (canBubble) {
                                            LogMessage obtain5 = logBuffer3.obtain("NotifActivityStarter", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logStartNotificationIntent$2
                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj) {
                                                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("(4/5) startNotificationIntent: ", ((LogMessage) obj).getStr1());
                                                }
                                            }, null);
                                            NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry3, obtain5, logBuffer3, obtain5);
                                            try {
                                                try {
                                                    if (z10 && z13) {
                                                        if (intent != null) {
                                                            intent.putExtra("LAUNCH_FROM_NOTIFICATION", 1);
                                                        } else {
                                                            intent2 = new Intent().putExtra("LAUNCH_FROM_NOTIFICATION", 1);
                                                            handler = handler2;
                                                            notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                                            statusBarNotificationActivityStarter2.mActivityLaunchAnimator.startPendingIntentWithAnimation(new StatusBarLaunchAnimatorController(statusBarNotificationActivityStarter2.mNotificationAnimationProvider.getAnimatorController(expandableNotificationRow3, null), statusBarNotificationActivityStarter2.mCentralSurfaces, z13), z11, pendingIntent4.getCreatorPackage(), new ActivityLaunchAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4
                                                                @Override // com.android.systemui.animation.ActivityLaunchAnimator.PendingIntentStarter
                                                                public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                                                                    Bundle activityOptions;
                                                                    PendingIntent pendingIntent5 = pendingIntent4;
                                                                    Intent intent3 = intent2;
                                                                    StatusBarNotificationActivityStarter statusBarNotificationActivityStarter3 = StatusBarNotificationActivityStarter.this;
                                                                    statusBarNotificationActivityStarter3.getClass();
                                                                    ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow3;
                                                                    long j = expandableNotificationRow4.mLastActionUpTime;
                                                                    expandableNotificationRow4.mLastActionUpTime = 0L;
                                                                    CentralSurfaces centralSurfaces2 = statusBarNotificationActivityStarter3.mCentralSurfaces;
                                                                    if (j > 0) {
                                                                        int i = ((CentralSurfacesImpl) centralSurfaces2).mDisplayId;
                                                                        boolean z15 = ((KeyguardStateControllerImpl) statusBarNotificationActivityStarter3.mKeyguardStateController).mShowing;
                                                                        ActivityOptions defaultActivityOptions = CentralSurfaces.getDefaultActivityOptions(remoteAnimationAdapter);
                                                                        defaultActivityOptions.setSourceInfo(z15 ? 3 : 2, j);
                                                                        defaultActivityOptions.setLaunchDisplayId(i);
                                                                        defaultActivityOptions.setCallerDisplayId(i);
                                                                        defaultActivityOptions.setPendingIntentBackgroundActivityLaunchAllowed(true);
                                                                        activityOptions = defaultActivityOptions.toBundle();
                                                                    } else {
                                                                        activityOptions = CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) centralSurfaces2).mDisplayId, remoteAnimationAdapter);
                                                                    }
                                                                    int sendAndReturnResult = pendingIntent5.sendAndReturnResult(statusBarNotificationActivityStarter3.mContext, 0, intent3, null, null, null, activityOptions);
                                                                    StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger4 = statusBarNotificationActivityStarter3.mLogger;
                                                                    statusBarNotificationActivityStarterLogger4.getClass();
                                                                    LogLevel logLevel4 = LogLevel.INFO;
                                                                    C3150xf7e3e395 c3150xf7e3e395 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendPendingIntent$2
                                                                        @Override // kotlin.jvm.functions.Function1
                                                                        public final Object invoke(Object obj) {
                                                                            LogMessage logMessage = (LogMessage) obj;
                                                                            String str22 = logMessage.getStr2();
                                                                            String str1 = logMessage.getStr1();
                                                                            int int1 = logMessage.getInt1();
                                                                            StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("(5/5) Started intent ", str22, " for notification ", str1, " with result code ");
                                                                            m87m.append(int1);
                                                                            return m87m.toString();
                                                                        }
                                                                    };
                                                                    LogBuffer logBuffer4 = statusBarNotificationActivityStarterLogger4.buffer;
                                                                    LogMessage obtain6 = logBuffer4.obtain("NotifActivityStarter", logLevel4, c3150xf7e3e395, null);
                                                                    obtain6.setStr1(NotificationUtilsKt.getLogKey(notificationEntry3));
                                                                    obtain6.setStr2(pendingIntent5.getIntent().toString());
                                                                    obtain6.setInt1(sendAndReturnResult);
                                                                    logBuffer4.commit(obtain6);
                                                                    return sendAndReturnResult;
                                                                }
                                                            });
                                                        }
                                                    }
                                                    statusBarNotificationActivityStarter2.mActivityLaunchAnimator.startPendingIntentWithAnimation(new StatusBarLaunchAnimatorController(statusBarNotificationActivityStarter2.mNotificationAnimationProvider.getAnimatorController(expandableNotificationRow3, null), statusBarNotificationActivityStarter2.mCentralSurfaces, z13), z11, pendingIntent4.getCreatorPackage(), new ActivityLaunchAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4
                                                        @Override // com.android.systemui.animation.ActivityLaunchAnimator.PendingIntentStarter
                                                        public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                                                            Bundle activityOptions;
                                                            PendingIntent pendingIntent5 = pendingIntent4;
                                                            Intent intent3 = intent2;
                                                            StatusBarNotificationActivityStarter statusBarNotificationActivityStarter3 = StatusBarNotificationActivityStarter.this;
                                                            statusBarNotificationActivityStarter3.getClass();
                                                            ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow3;
                                                            long j = expandableNotificationRow4.mLastActionUpTime;
                                                            expandableNotificationRow4.mLastActionUpTime = 0L;
                                                            CentralSurfaces centralSurfaces2 = statusBarNotificationActivityStarter3.mCentralSurfaces;
                                                            if (j > 0) {
                                                                int i = ((CentralSurfacesImpl) centralSurfaces2).mDisplayId;
                                                                boolean z15 = ((KeyguardStateControllerImpl) statusBarNotificationActivityStarter3.mKeyguardStateController).mShowing;
                                                                ActivityOptions defaultActivityOptions = CentralSurfaces.getDefaultActivityOptions(remoteAnimationAdapter);
                                                                defaultActivityOptions.setSourceInfo(z15 ? 3 : 2, j);
                                                                defaultActivityOptions.setLaunchDisplayId(i);
                                                                defaultActivityOptions.setCallerDisplayId(i);
                                                                defaultActivityOptions.setPendingIntentBackgroundActivityLaunchAllowed(true);
                                                                activityOptions = defaultActivityOptions.toBundle();
                                                            } else {
                                                                activityOptions = CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) centralSurfaces2).mDisplayId, remoteAnimationAdapter);
                                                            }
                                                            int sendAndReturnResult = pendingIntent5.sendAndReturnResult(statusBarNotificationActivityStarter3.mContext, 0, intent3, null, null, null, activityOptions);
                                                            StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger4 = statusBarNotificationActivityStarter3.mLogger;
                                                            statusBarNotificationActivityStarterLogger4.getClass();
                                                            LogLevel logLevel4 = LogLevel.INFO;
                                                            C3150xf7e3e395 c3150xf7e3e395 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendPendingIntent$2
                                                                @Override // kotlin.jvm.functions.Function1
                                                                public final Object invoke(Object obj) {
                                                                    LogMessage logMessage = (LogMessage) obj;
                                                                    String str22 = logMessage.getStr2();
                                                                    String str1 = logMessage.getStr1();
                                                                    int int1 = logMessage.getInt1();
                                                                    StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("(5/5) Started intent ", str22, " for notification ", str1, " with result code ");
                                                                    m87m.append(int1);
                                                                    return m87m.toString();
                                                                }
                                                            };
                                                            LogBuffer logBuffer4 = statusBarNotificationActivityStarterLogger4.buffer;
                                                            LogMessage obtain6 = logBuffer4.obtain("NotifActivityStarter", logLevel4, c3150xf7e3e395, null);
                                                            obtain6.setStr1(NotificationUtilsKt.getLogKey(notificationEntry3));
                                                            obtain6.setStr2(pendingIntent5.getIntent().toString());
                                                            obtain6.setInt1(sendAndReturnResult);
                                                            logBuffer4.commit(obtain6);
                                                            return sendAndReturnResult;
                                                        }
                                                    });
                                                } catch (PendingIntent.CanceledException e) {
                                                    e = e;
                                                    LogMessage obtain6 = logBuffer3.obtain("NotifActivityStarter", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendingIntentFailed$2
                                                        @Override // kotlin.jvm.functions.Function1
                                                        public final Object invoke(Object obj) {
                                                            return KeyAttributes$$ExternalSyntheticOutline0.m21m("Sending contentIntentFailed: ", ((LogMessage) obj).getStr1());
                                                        }
                                                    }, null);
                                                    obtain6.setStr1(e.toString());
                                                    logBuffer3.commit(obtain6);
                                                    if (!z13) {
                                                    }
                                                    ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                                                    NotificationVisibility obtain72222 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                                                    if (!canBubble) {
                                                    }
                                                    z12 = false;
                                                    NotificationClickNotifier notificationClickNotifier222222 = statusBarNotificationActivityStarter2.mClickNotifier;
                                                    notificationClickNotifier222222.getClass();
                                                    notificationClickNotifier222222.barService.onNotificationClick(str2, obtain72222);
                                                    notificationClickNotifier222222.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier222222, str2));
                                                    statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                                                }
                                                intent2 = intent;
                                                handler = handler2;
                                                notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                            } catch (PendingIntent.CanceledException e2) {
                                                e = e2;
                                                handler = handler2;
                                                notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                            }
                                        } else {
                                            LogMessage obtain8 = logBuffer3.obtain("NotifActivityStarter", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logExpandingBubble$2
                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj) {
                                                    return PathParser$$ExternalSyntheticOutline0.m29m("Expanding bubble for ", ((LogMessage) obj).getStr1(), " (rather than firing intent)");
                                                }
                                            }, null);
                                            obtain8.setStr1(NotificationUtilsKt.getLogKey(notificationEntry3));
                                            logBuffer3.commit(obtain8);
                                            statusBarNotificationActivityStarter2.removeHunAfterClick(expandableNotificationRow3);
                                            Optional optional = statusBarNotificationActivityStarter2.mBubblesManagerOptional;
                                            if (optional.isPresent()) {
                                                int i = 2;
                                                if (Looper.getMainLooper().isCurrentThread()) {
                                                    BubblesManager bubblesManager = (BubblesManager) optional.get();
                                                    BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry3);
                                                    BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                                                    ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda2(bubblesImpl, notifToBubbleEntry, i));
                                                    ((ShadeControllerImpl) shadeController).collapseShade();
                                                } else {
                                                    handler2.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3(statusBarNotificationActivityStarter2, notificationEntry3, i));
                                                }
                                            }
                                            handler = handler2;
                                            notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                        }
                                        if (!z13 || canBubble) {
                                            ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                                        }
                                        NotificationVisibility obtain722222 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                                        if (!canBubble) {
                                            if (StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) {
                                                str2 = str;
                                            } else {
                                                str2 = str;
                                            }
                                            z12 = false;
                                            z12 = false;
                                            handler.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3(statusBarNotificationActivityStarter2, ((OnUserInteractionCallbackImpl) statusBarNotificationActivityStarter2.mOnUserInteractionCallback).registerFutureDismissal(notificationEntry3, 1), z12 ? 1 : 0));
                                            if (StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) {
                                                NotificationSAUtil.sendCancelLog(notificationEntry3, "QPNE0005");
                                            }
                                            NotificationClickNotifier notificationClickNotifier2222222 = statusBarNotificationActivityStarter2.mClickNotifier;
                                            notificationClickNotifier2222222.getClass();
                                            notificationClickNotifier2222222.barService.onNotificationClick(str2, obtain722222);
                                            notificationClickNotifier2222222.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier2222222, str2));
                                            statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                                        }
                                        str2 = str;
                                        z12 = false;
                                        NotificationClickNotifier notificationClickNotifier22222222 = statusBarNotificationActivityStarter2.mClickNotifier;
                                        notificationClickNotifier22222222.getClass();
                                        notificationClickNotifier22222222.barService.onNotificationClick(str2, obtain722222);
                                        notificationClickNotifier22222222.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier22222222, str2));
                                        statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                                    }
                                }
                                intent = null;
                                z10 = Rune.SYSUI_APPLOCK;
                                if (z10) {
                                }
                                z11 = z14;
                                str = str3;
                                canBubble = notificationEntry3.canBubble();
                                if (canBubble) {
                                }
                                if (!z13) {
                                }
                                ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                                NotificationVisibility obtain7222222 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                                if (!canBubble) {
                                }
                                z12 = false;
                                NotificationClickNotifier notificationClickNotifier222222222 = statusBarNotificationActivityStarter2.mClickNotifier;
                                notificationClickNotifier222222222.getClass();
                                notificationClickNotifier222222222.barService.onNotificationClick(str2, obtain7222222);
                                notificationClickNotifier222222222.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier222222222, str2));
                                statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                            }
                        };
                        boolean z10 = z722;
                        ShadeController shadeController = statusBarNotificationActivityStarter.mShadeController;
                        if (z10) {
                            ShadeControllerImpl shadeControllerImpl = (ShadeControllerImpl) shadeController;
                            shadeControllerImpl.mPostCollapseRunnables.add(runnable);
                            shadeControllerImpl.collapseShade(true);
                            return false;
                        }
                        if (!((KeyguardStateControllerImpl) statusBarNotificationActivityStarter.mKeyguardStateController).mShowing || !((CentralSurfacesImpl) statusBarNotificationActivityStarter.mCentralSurfaces).isOccluded()) {
                            runnable.run();
                            return false;
                        }
                        statusBarNotificationActivityStarter.mStatusBarKeyguardViewManager.addAfterKeyguardGoneRunnable(runnable);
                        ((ShadeControllerImpl) shadeController).collapseShade();
                        return false;
                    }

                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean willRunAnimationOnKeyguard() {
                        return z52;
                    }
                };
                KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_NOTIFICATION);
                if (!z3) {
                }
            }
        }
        z = false;
        if (!z) {
        }
        z2 = true;
        if (!z2) {
        }
        final boolean z522 = false;
        keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (keyguardStateControllerImpl.mShowing) {
        }
        z3 = false;
        final PendingIntent pendingIntent2222 = pendingIntent;
        final boolean z6222 = z4;
        final boolean z7222 = z3;
        ActivityStarter.OnDismissAction onDismissAction222 = new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.1
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                final NotificationEntry notificationEntry2 = notificationEntry;
                final ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                final PendingIntent pendingIntent3 = pendingIntent2222;
                final boolean z8 = z6222;
                final boolean z9 = z522;
                final StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = StatusBarNotificationActivityStarter.this;
                StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger2 = statusBarNotificationActivityStarter.mLogger;
                statusBarNotificationActivityStarterLogger2.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                C3147x5700bdb1 c3147x5700bdb1 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logHandleClickAfterKeyguardDismissed$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return KeyAttributes$$ExternalSyntheticOutline0.m21m("(2/5) handleNotificationClickAfterKeyguardDismissed: ", ((LogMessage) obj).getStr1());
                    }
                };
                LogBuffer logBuffer2 = statusBarNotificationActivityStarterLogger2.buffer;
                LogMessage obtain3 = logBuffer2.obtain("NotifActivityStarter", logLevel2, c3147x5700bdb1, null);
                obtain3.setStr1(NotificationUtilsKt.getLogKey(notificationEntry2));
                logBuffer2.commit(obtain3);
                Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda2
                    /* JADX WARN: Can't wrap try/catch for region: R(26:0|1|(2:2|3)|4|(2:8|(2:14|(2:16|17)(2:19|20)))|21|(1:23)(1:103)|24|(3:26|(1:101)(1:30)|(14:32|33|(1:100)(5:36|(1:38)|(1:99)(1:42)|43|(1:47))|48|(3:50|(2:52|(1:54)(1:55))|56)(8:79|80|(2:83|(2:85|86)(5:87|88|89|90|91))|96|88|89|90|91)|(1:78)|59|(9:61|(1:63)(1:76)|73|(1:75)|66|67|68|69|70)(1:77)|65|66|67|68|69|70))|102|33|(0)|100|48|(0)(0)|(0)|78|59|(0)(0)|65|66|67|68|69|70|(1:(0))) */
                    /* JADX WARN: Code restructure failed: missing block: B:64:0x01fb, code lost:
                    
                        if (r18.isNotificationKeptForRemoteInputHistory(r1) != false) goto L90;
                     */
                    /* JADX WARN: Removed duplicated region for block: B:50:0x0110  */
                    /* JADX WARN: Removed duplicated region for block: B:58:0x01d4 A[ADDED_TO_REGION] */
                    /* JADX WARN: Removed duplicated region for block: B:61:0x01eb  */
                    /* JADX WARN: Removed duplicated region for block: B:77:0x0222  */
                    /* JADX WARN: Removed duplicated region for block: B:79:0x016a  */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void run() {
                        Intent intent;
                        boolean z10;
                        boolean z11;
                        String str;
                        boolean canBubble;
                        Handler handler;
                        NotificationRemoteInputManager notificationRemoteInputManager2;
                        final Intent intent2;
                        String str2;
                        boolean z12;
                        final StatusBarNotificationActivityStarter statusBarNotificationActivityStarter2 = StatusBarNotificationActivityStarter.this;
                        final NotificationEntry notificationEntry3 = notificationEntry2;
                        final ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow2;
                        final PendingIntent pendingIntent4 = pendingIntent3;
                        boolean z13 = z8;
                        boolean z14 = z9;
                        statusBarNotificationActivityStarter2.getClass();
                        String str3 = notificationEntry3.mKey;
                        StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger3 = statusBarNotificationActivityStarter2.mLogger;
                        statusBarNotificationActivityStarterLogger3.getClass();
                        LogLevel logLevel3 = LogLevel.DEBUG;
                        C3148xe49d9e41 c3148xe49d9e41 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logHandleClickAfterPanelCollapsed$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return KeyAttributes$$ExternalSyntheticOutline0.m21m("(3/5) handleNotificationClickAfterPanelCollapsed: ", ((LogMessage) obj).getStr1());
                            }
                        };
                        LogBuffer logBuffer3 = statusBarNotificationActivityStarterLogger3.buffer;
                        LogMessage obtain4 = logBuffer3.obtain("NotifActivityStarter", logLevel3, c3148xe49d9e41, null);
                        NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry3, obtain4, logBuffer3, obtain4);
                        try {
                            ActivityManager.getService().resumeAppSwitches();
                        } catch (RemoteException unused) {
                        }
                        ShadeController shadeController = statusBarNotificationActivityStarter2.mShadeController;
                        Handler handler2 = statusBarNotificationActivityStarter2.mMainThreadHandler;
                        if ((z13 && StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) || !z13) {
                            int identifier2 = pendingIntent4.getCreatorUserHandle().getIdentifier();
                            if (statusBarNotificationActivityStarter2.mLockPatternUtils.isSeparateProfileChallengeEnabled(identifier2) && statusBarNotificationActivityStarter2.mKeyguardManager.isDeviceLocked(identifier2) && statusBarNotificationActivityStarter2.mStatusBarRemoteInputCallback.startWorkChallengeIfNecessary(identifier2, pendingIntent4.getIntentSender(), str3)) {
                                statusBarNotificationActivityStarter2.removeHunAfterClick(expandableNotificationRow3);
                                if (Looper.getMainLooper().isCurrentThread()) {
                                    ((ShadeControllerImpl) shadeController).collapseShade();
                                    return;
                                } else {
                                    Objects.requireNonNull(shadeController);
                                    handler2.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1(shadeController, 1));
                                    return;
                                }
                            }
                        }
                        CharSequence charSequence = !TextUtils.isEmpty(notificationEntry3.remoteInputText) ? notificationEntry3.remoteInputText : null;
                        boolean isEmpty = TextUtils.isEmpty(charSequence);
                        NotificationRemoteInputManager notificationRemoteInputManager3 = statusBarNotificationActivityStarter2.mRemoteInputManager;
                        if (!isEmpty) {
                            RemoteInputController remoteInputController2 = notificationRemoteInputManager3.mRemoteInputController;
                            if (!(remoteInputController2 != null && remoteInputController2.mSpinning.containsKey(str3))) {
                                intent = new Intent().putExtra("android.remoteInputDraft", charSequence.toString());
                                z10 = Rune.SYSUI_APPLOCK;
                                if (z10 || !z13) {
                                    z11 = z14;
                                    str = str3;
                                } else {
                                    ComponentName component = pendingIntent4.getIntent() != null ? pendingIntent4.getIntent().getComponent() : null;
                                    String targetPackage = (component == null || component.getPackageName() == null) ? pendingIntent4.getTargetPackage() : component.getPackageName();
                                    int identifier3 = pendingIntent4.getCreatorUserHandle().getIdentifier();
                                    str = str3;
                                    z11 = z14;
                                    if (((ActivityManager) statusBarNotificationActivityStarter2.mContext.getSystemService("activity")).isAppLockedPackage(targetPackage) && !SemPersonaManager.isKnoxId(identifier3)) {
                                        notificationRemoteInputManager3.startAppLockCheckService(targetPackage);
                                    }
                                }
                                canBubble = notificationEntry3.canBubble();
                                if (canBubble) {
                                    LogMessage obtain5 = logBuffer3.obtain("NotifActivityStarter", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logStartNotificationIntent$2
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj) {
                                            return KeyAttributes$$ExternalSyntheticOutline0.m21m("(4/5) startNotificationIntent: ", ((LogMessage) obj).getStr1());
                                        }
                                    }, null);
                                    NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry3, obtain5, logBuffer3, obtain5);
                                    try {
                                        try {
                                            if (z10 && z13) {
                                                if (intent != null) {
                                                    intent.putExtra("LAUNCH_FROM_NOTIFICATION", 1);
                                                } else {
                                                    intent2 = new Intent().putExtra("LAUNCH_FROM_NOTIFICATION", 1);
                                                    handler = handler2;
                                                    notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                                    statusBarNotificationActivityStarter2.mActivityLaunchAnimator.startPendingIntentWithAnimation(new StatusBarLaunchAnimatorController(statusBarNotificationActivityStarter2.mNotificationAnimationProvider.getAnimatorController(expandableNotificationRow3, null), statusBarNotificationActivityStarter2.mCentralSurfaces, z13), z11, pendingIntent4.getCreatorPackage(), new ActivityLaunchAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4
                                                        @Override // com.android.systemui.animation.ActivityLaunchAnimator.PendingIntentStarter
                                                        public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                                                            Bundle activityOptions;
                                                            PendingIntent pendingIntent5 = pendingIntent4;
                                                            Intent intent3 = intent2;
                                                            StatusBarNotificationActivityStarter statusBarNotificationActivityStarter3 = StatusBarNotificationActivityStarter.this;
                                                            statusBarNotificationActivityStarter3.getClass();
                                                            ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow3;
                                                            long j = expandableNotificationRow4.mLastActionUpTime;
                                                            expandableNotificationRow4.mLastActionUpTime = 0L;
                                                            CentralSurfaces centralSurfaces2 = statusBarNotificationActivityStarter3.mCentralSurfaces;
                                                            if (j > 0) {
                                                                int i = ((CentralSurfacesImpl) centralSurfaces2).mDisplayId;
                                                                boolean z15 = ((KeyguardStateControllerImpl) statusBarNotificationActivityStarter3.mKeyguardStateController).mShowing;
                                                                ActivityOptions defaultActivityOptions = CentralSurfaces.getDefaultActivityOptions(remoteAnimationAdapter);
                                                                defaultActivityOptions.setSourceInfo(z15 ? 3 : 2, j);
                                                                defaultActivityOptions.setLaunchDisplayId(i);
                                                                defaultActivityOptions.setCallerDisplayId(i);
                                                                defaultActivityOptions.setPendingIntentBackgroundActivityLaunchAllowed(true);
                                                                activityOptions = defaultActivityOptions.toBundle();
                                                            } else {
                                                                activityOptions = CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) centralSurfaces2).mDisplayId, remoteAnimationAdapter);
                                                            }
                                                            int sendAndReturnResult = pendingIntent5.sendAndReturnResult(statusBarNotificationActivityStarter3.mContext, 0, intent3, null, null, null, activityOptions);
                                                            StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger4 = statusBarNotificationActivityStarter3.mLogger;
                                                            statusBarNotificationActivityStarterLogger4.getClass();
                                                            LogLevel logLevel4 = LogLevel.INFO;
                                                            C3150xf7e3e395 c3150xf7e3e395 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendPendingIntent$2
                                                                @Override // kotlin.jvm.functions.Function1
                                                                public final Object invoke(Object obj) {
                                                                    LogMessage logMessage = (LogMessage) obj;
                                                                    String str22 = logMessage.getStr2();
                                                                    String str1 = logMessage.getStr1();
                                                                    int int1 = logMessage.getInt1();
                                                                    StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("(5/5) Started intent ", str22, " for notification ", str1, " with result code ");
                                                                    m87m.append(int1);
                                                                    return m87m.toString();
                                                                }
                                                            };
                                                            LogBuffer logBuffer4 = statusBarNotificationActivityStarterLogger4.buffer;
                                                            LogMessage obtain6 = logBuffer4.obtain("NotifActivityStarter", logLevel4, c3150xf7e3e395, null);
                                                            obtain6.setStr1(NotificationUtilsKt.getLogKey(notificationEntry3));
                                                            obtain6.setStr2(pendingIntent5.getIntent().toString());
                                                            obtain6.setInt1(sendAndReturnResult);
                                                            logBuffer4.commit(obtain6);
                                                            return sendAndReturnResult;
                                                        }
                                                    });
                                                }
                                            }
                                            statusBarNotificationActivityStarter2.mActivityLaunchAnimator.startPendingIntentWithAnimation(new StatusBarLaunchAnimatorController(statusBarNotificationActivityStarter2.mNotificationAnimationProvider.getAnimatorController(expandableNotificationRow3, null), statusBarNotificationActivityStarter2.mCentralSurfaces, z13), z11, pendingIntent4.getCreatorPackage(), new ActivityLaunchAnimator.PendingIntentStarter() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda4
                                                @Override // com.android.systemui.animation.ActivityLaunchAnimator.PendingIntentStarter
                                                public final int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) {
                                                    Bundle activityOptions;
                                                    PendingIntent pendingIntent5 = pendingIntent4;
                                                    Intent intent3 = intent2;
                                                    StatusBarNotificationActivityStarter statusBarNotificationActivityStarter3 = StatusBarNotificationActivityStarter.this;
                                                    statusBarNotificationActivityStarter3.getClass();
                                                    ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow3;
                                                    long j = expandableNotificationRow4.mLastActionUpTime;
                                                    expandableNotificationRow4.mLastActionUpTime = 0L;
                                                    CentralSurfaces centralSurfaces2 = statusBarNotificationActivityStarter3.mCentralSurfaces;
                                                    if (j > 0) {
                                                        int i = ((CentralSurfacesImpl) centralSurfaces2).mDisplayId;
                                                        boolean z15 = ((KeyguardStateControllerImpl) statusBarNotificationActivityStarter3.mKeyguardStateController).mShowing;
                                                        ActivityOptions defaultActivityOptions = CentralSurfaces.getDefaultActivityOptions(remoteAnimationAdapter);
                                                        defaultActivityOptions.setSourceInfo(z15 ? 3 : 2, j);
                                                        defaultActivityOptions.setLaunchDisplayId(i);
                                                        defaultActivityOptions.setCallerDisplayId(i);
                                                        defaultActivityOptions.setPendingIntentBackgroundActivityLaunchAllowed(true);
                                                        activityOptions = defaultActivityOptions.toBundle();
                                                    } else {
                                                        activityOptions = CentralSurfaces.getActivityOptions(((CentralSurfacesImpl) centralSurfaces2).mDisplayId, remoteAnimationAdapter);
                                                    }
                                                    int sendAndReturnResult = pendingIntent5.sendAndReturnResult(statusBarNotificationActivityStarter3.mContext, 0, intent3, null, null, null, activityOptions);
                                                    StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger4 = statusBarNotificationActivityStarter3.mLogger;
                                                    statusBarNotificationActivityStarterLogger4.getClass();
                                                    LogLevel logLevel4 = LogLevel.INFO;
                                                    C3150xf7e3e395 c3150xf7e3e395 = new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendPendingIntent$2
                                                        @Override // kotlin.jvm.functions.Function1
                                                        public final Object invoke(Object obj) {
                                                            LogMessage logMessage = (LogMessage) obj;
                                                            String str22 = logMessage.getStr2();
                                                            String str1 = logMessage.getStr1();
                                                            int int1 = logMessage.getInt1();
                                                            StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("(5/5) Started intent ", str22, " for notification ", str1, " with result code ");
                                                            m87m.append(int1);
                                                            return m87m.toString();
                                                        }
                                                    };
                                                    LogBuffer logBuffer4 = statusBarNotificationActivityStarterLogger4.buffer;
                                                    LogMessage obtain6 = logBuffer4.obtain("NotifActivityStarter", logLevel4, c3150xf7e3e395, null);
                                                    obtain6.setStr1(NotificationUtilsKt.getLogKey(notificationEntry3));
                                                    obtain6.setStr2(pendingIntent5.getIntent().toString());
                                                    obtain6.setInt1(sendAndReturnResult);
                                                    logBuffer4.commit(obtain6);
                                                    return sendAndReturnResult;
                                                }
                                            });
                                        } catch (PendingIntent.CanceledException e) {
                                            e = e;
                                            LogMessage obtain6 = logBuffer3.obtain("NotifActivityStarter", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendingIntentFailed$2
                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj) {
                                                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("Sending contentIntentFailed: ", ((LogMessage) obj).getStr1());
                                                }
                                            }, null);
                                            obtain6.setStr1(e.toString());
                                            logBuffer3.commit(obtain6);
                                            if (!z13) {
                                            }
                                            ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                                            NotificationVisibility obtain7222222 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                                            if (!canBubble) {
                                            }
                                            z12 = false;
                                            NotificationClickNotifier notificationClickNotifier222222222 = statusBarNotificationActivityStarter2.mClickNotifier;
                                            notificationClickNotifier222222222.getClass();
                                            notificationClickNotifier222222222.barService.onNotificationClick(str2, obtain7222222);
                                            notificationClickNotifier222222222.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier222222222, str2));
                                            statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                                        }
                                        intent2 = intent;
                                        handler = handler2;
                                        notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                    } catch (PendingIntent.CanceledException e2) {
                                        e = e2;
                                        handler = handler2;
                                        notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                    }
                                } else {
                                    LogMessage obtain8 = logBuffer3.obtain("NotifActivityStarter", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logExpandingBubble$2
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj) {
                                            return PathParser$$ExternalSyntheticOutline0.m29m("Expanding bubble for ", ((LogMessage) obj).getStr1(), " (rather than firing intent)");
                                        }
                                    }, null);
                                    obtain8.setStr1(NotificationUtilsKt.getLogKey(notificationEntry3));
                                    logBuffer3.commit(obtain8);
                                    statusBarNotificationActivityStarter2.removeHunAfterClick(expandableNotificationRow3);
                                    Optional optional = statusBarNotificationActivityStarter2.mBubblesManagerOptional;
                                    if (optional.isPresent()) {
                                        int i = 2;
                                        if (Looper.getMainLooper().isCurrentThread()) {
                                            BubblesManager bubblesManager = (BubblesManager) optional.get();
                                            BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry3);
                                            BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                                            ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda2(bubblesImpl, notifToBubbleEntry, i));
                                            ((ShadeControllerImpl) shadeController).collapseShade();
                                        } else {
                                            handler2.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3(statusBarNotificationActivityStarter2, notificationEntry3, i));
                                        }
                                    }
                                    handler = handler2;
                                    notificationRemoteInputManager2 = notificationRemoteInputManager3;
                                }
                                if (!z13 || canBubble) {
                                    ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                                }
                                NotificationVisibility obtain72222222 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                                if (!canBubble) {
                                    if (StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) {
                                        str2 = str;
                                    } else {
                                        str2 = str;
                                    }
                                    z12 = false;
                                    z12 = false;
                                    handler.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3(statusBarNotificationActivityStarter2, ((OnUserInteractionCallbackImpl) statusBarNotificationActivityStarter2.mOnUserInteractionCallback).registerFutureDismissal(notificationEntry3, 1), z12 ? 1 : 0));
                                    if (StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry3.mSbn)) {
                                        NotificationSAUtil.sendCancelLog(notificationEntry3, "QPNE0005");
                                    }
                                    NotificationClickNotifier notificationClickNotifier2222222222 = statusBarNotificationActivityStarter2.mClickNotifier;
                                    notificationClickNotifier2222222222.getClass();
                                    notificationClickNotifier2222222222.barService.onNotificationClick(str2, obtain72222222);
                                    notificationClickNotifier2222222222.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier2222222222, str2));
                                    statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                                }
                                str2 = str;
                                z12 = false;
                                NotificationClickNotifier notificationClickNotifier22222222222 = statusBarNotificationActivityStarter2.mClickNotifier;
                                notificationClickNotifier22222222222.getClass();
                                notificationClickNotifier22222222222.barService.onNotificationClick(str2, obtain72222222);
                                notificationClickNotifier22222222222.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier22222222222, str2));
                                statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                            }
                        }
                        intent = null;
                        z10 = Rune.SYSUI_APPLOCK;
                        if (z10) {
                        }
                        z11 = z14;
                        str = str3;
                        canBubble = notificationEntry3.canBubble();
                        if (canBubble) {
                        }
                        if (!z13) {
                        }
                        ((AssistManager) statusBarNotificationActivityStarter2.mAssistManagerLazy.get()).hideAssist();
                        NotificationVisibility obtain722222222 = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter2.mVisibilityProvider).obtain(notificationEntry3);
                        if (!canBubble) {
                        }
                        z12 = false;
                        NotificationClickNotifier notificationClickNotifier222222222222 = statusBarNotificationActivityStarter2.mClickNotifier;
                        notificationClickNotifier222222222222.getClass();
                        notificationClickNotifier222222222222.barService.onNotificationClick(str2, obtain722222222);
                        notificationClickNotifier222222222222.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier222222222222, str2));
                        statusBarNotificationActivityStarter2.mIsCollapsingToShowActivityOverLockscreen = z12;
                    }
                };
                boolean z10 = z7222;
                ShadeController shadeController = statusBarNotificationActivityStarter.mShadeController;
                if (z10) {
                    ShadeControllerImpl shadeControllerImpl = (ShadeControllerImpl) shadeController;
                    shadeControllerImpl.mPostCollapseRunnables.add(runnable);
                    shadeControllerImpl.collapseShade(true);
                    return false;
                }
                if (!((KeyguardStateControllerImpl) statusBarNotificationActivityStarter.mKeyguardStateController).mShowing || !((CentralSurfacesImpl) statusBarNotificationActivityStarter.mCentralSurfaces).isOccluded()) {
                    runnable.run();
                    return false;
                }
                statusBarNotificationActivityStarter.mStatusBarKeyguardViewManager.addAfterKeyguardGoneRunnable(runnable);
                ((ShadeControllerImpl) shadeController).collapseShade();
                return false;
            }

            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean willRunAnimationOnKeyguard() {
                return z522;
            }
        };
        KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_NOTIFICATION);
        if (!z3) {
        }
    }

    public final void removeHunAfterClick(ExpandableNotificationRow expandableNotificationRow) {
        String key = expandableNotificationRow.mEntry.mSbn.getKey();
        HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
        if (headsUpManagerPhone == null || !headsUpManagerPhone.isAlerting(key)) {
            return;
        }
        if (((StatusBarNotificationPresenter) this.mPresenter).isPresenterFullyCollapsed()) {
            HeadsUpUtil.setNeedsHeadsUpDisappearAnimationAfterClick(expandableNotificationRow, true);
        }
        headsUpManagerPhone.removeNotification(key, true);
    }
}
