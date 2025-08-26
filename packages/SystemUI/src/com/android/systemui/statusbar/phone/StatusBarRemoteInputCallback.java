package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.view.View;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.ActionClickLogger;
import com.android.systemui.statusbar.ActionClickLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.kotlin.JavaAdapter;
import dagger.Lazy;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class StatusBarRemoteInputCallback implements NotificationRemoteInputManager.Callback, CommandQueue.Callbacks, StatusBarStateController.StateListener {
    public final ActionClickLogger mActionClickLogger;
    public final ActivityIntentHelper mActivityIntentHelper;
    public final ActivityStarter mActivityStarter;
    public final ChallengeReceiver mChallengeReceiver;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public int mDisabled2;
    public final Executor mExecutor;
    public final GroupExpansionManager mGroupExpansionManager;
    public final KeyguardManager mKeyguardManager;
    public final KeyguardStateController mKeyguardStateController;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public View mPendingRemoteInputView;
    public View mPendingWorkRemoteInputView;
    public final ShadeController mShadeController;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final SysuiStatusBarStateController mStatusBarStateController;

    public class ChallengeReceiver extends BroadcastReceiver {
        public ChallengeReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
            if ("android.intent.action.DEVICE_LOCKED_CHANGED".equals(action)) {
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) StatusBarRemoteInputCallback.this.mLockscreenUserManager;
                if (intExtra == notificationLockscreenUserManagerImpl.mCurrentUserId || !notificationLockscreenUserManagerImpl.isCurrentProfile(intExtra)) {
                    return;
                }
                StatusBarRemoteInputCallback statusBarRemoteInputCallback = StatusBarRemoteInputCallback.this;
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl2 = (NotificationLockscreenUserManagerImpl) statusBarRemoteInputCallback.mLockscreenUserManager;
                notificationLockscreenUserManagerImpl2.updatePublicMode();
                if (statusBarRemoteInputCallback.mPendingWorkRemoteInputView == null || notificationLockscreenUserManagerImpl2.isAnyProfilePublicMode()) {
                    return;
                }
                StatusBarRemoteInputCallback$$ExternalSyntheticLambda1 statusBarRemoteInputCallback$$ExternalSyntheticLambda1 = new StatusBarRemoteInputCallback$$ExternalSyntheticLambda1(statusBarRemoteInputCallback, 1);
                ShadeController shadeController = statusBarRemoteInputCallback.mShadeController;
                shadeController.postOnShadeExpanded(statusBarRemoteInputCallback$$ExternalSyntheticLambda1);
                shadeController.instantExpandShade();
            }
        }
    }

    public StatusBarRemoteInputCallback(Context context, GroupExpansionManager groupExpansionManager, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, ActivityStarter activityStarter, ShadeController shadeController, CommandQueue commandQueue, ActionClickLogger actionClickLogger, Executor executor, Lazy lazy, Lazy lazy2, JavaAdapter javaAdapter) {
        ChallengeReceiver challengeReceiver = new ChallengeReceiver();
        this.mChallengeReceiver = challengeReceiver;
        this.mContext = context;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mShadeController = shadeController;
        this.mExecutor = executor;
        context.registerReceiverAsUser(challengeReceiver, UserHandle.ALL, new IntentFilter("android.intent.action.DEVICE_LOCKED_CHANGED"), null, null);
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mKeyguardStateController = keyguardStateController;
        SysuiStatusBarStateController sysuiStatusBarStateController = (SysuiStatusBarStateController) statusBarStateController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mActivityStarter = activityStarter;
        sysuiStatusBarStateController.addCallback(this);
        this.mKeyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        this.mCommandQueue = commandQueue;
        commandQueue.addCallback((CommandQueue.Callbacks) this);
        this.mActionClickLogger = actionClickLogger;
        this.mActivityIntentHelper = new ActivityIntentHelper(context);
        this.mGroupExpansionManager = groupExpansionManager;
        int i = SceneContainerFlag.$r8$clinit;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        if (i == this.mContext.getDisplayId()) {
            this.mDisabled2 = i3;
        }
    }

    public final boolean handleRemoteViewClick(final PendingIntent pendingIntent, boolean z, final Integer num, final NotificationRemoteInputManager.ClickHandler clickHandler) {
        if (!pendingIntent.isActivity() && !z) {
            return clickHandler.handleClick();
        }
        ActionClickLogger actionClickLogger = this.mActionClickLogger;
        actionClickLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ActionClickLogger$$ExternalSyntheticLambda0 actionClickLogger$$ExternalSyntheticLambda0 = new ActionClickLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = actionClickLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("ActionClickLogger", logLevel, actionClickLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = pendingIntent.toString();
        logMessageImpl.int1 = num != null ? num.intValue() : Integer.MIN_VALUE;
        logBuffer.commit(logMessageObtain);
        boolean z2 = this.mActivityIntentHelper.getPendingTargetActivityInfo(((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).mCurrentUserId, pendingIntent) == null;
        if (LsRune.SECURITY_SWIPE_BOUNCER) {
            this.mStatusBarKeyguardViewManager.setShowSwipeBouncer(true);
        }
        this.mActivityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback$$ExternalSyntheticLambda0
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                PendingIntent pendingIntent2 = pendingIntent;
                StatusBarRemoteInputCallback statusBarRemoteInputCallback = this.f$0;
                ActionClickLogger actionClickLogger2 = statusBarRemoteInputCallback.mActionClickLogger;
                actionClickLogger2.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                ActionClickLogger$$ExternalSyntheticLambda0 actionClickLogger$$ExternalSyntheticLambda02 = new ActionClickLogger$$ExternalSyntheticLambda0(2);
                LogBuffer logBuffer2 = actionClickLogger2.buffer;
                LogMessage logMessageObtain2 = logBuffer2.obtain("ActionClickLogger", logLevel2, actionClickLogger$$ExternalSyntheticLambda02, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                logMessageImpl2.str1 = pendingIntent2.toString();
                Integer num2 = num;
                logMessageImpl2.int1 = num2 != null ? num2.intValue() : Integer.MIN_VALUE;
                logBuffer2.commit(logMessageObtain2);
                try {
                    ActivityManager.getService().resumeAppSwitches();
                } catch (RemoteException unused) {
                }
                if (!clickHandler.handleClick()) {
                    return false;
                }
                statusBarRemoteInputCallback.mShadeController.closeShadeIfOpen();
                return false;
            }
        }, null, z2);
        return true;
    }

    public final void onLockedRemoteInput(View view, ExpandableNotificationRow expandableNotificationRow) {
        if (!expandableNotificationRow.mPinnedStatus.isPinned()) {
            ((StatusBarStateControllerImpl) this.mStatusBarStateController).setLeaveOpenOnKeyguardHide(true);
        }
        KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_REMOTE_INPUT);
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        statusBarKeyguardViewManager.getClass();
        int i = SceneContainerFlag.$r8$clinit;
        statusBarKeyguardViewManager.mAlternateBouncerInteractor.getClass();
        statusBarKeyguardViewManager.showPrimaryBouncer("StatusBarRemoteInputCallback#onLockedRemoteInput", true);
        this.mPendingRemoteInputView = view;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        if (this.mPendingRemoteInputView != null && i == 0) {
            int i2 = SceneContainerFlag.$r8$clinit;
            if (!this.mKeyguardStateController.isUnlocked() || ((StatusBarStateControllerImpl) this.mStatusBarStateController).mKeyguardRequested) {
                return;
            }
            Executor executor = this.mExecutor;
            View view = this.mPendingRemoteInputView;
            Objects.requireNonNull(view);
            executor.execute(new StatusBarRemoteInputCallback$$ExternalSyntheticLambda1(view, 0));
            this.mPendingRemoteInputView = null;
        }
    }

    public final boolean startWorkChallengeIfNecessary(int i, IntentSender intentSender, String str) {
        this.mPendingWorkRemoteInputView = null;
        Intent intentCreateConfirmDeviceCredentialIntent = this.mKeyguardManager.createConfirmDeviceCredentialIntent(null, null, i);
        if (intentCreateConfirmDeviceCredentialIntent == null) {
            return false;
        }
        Intent intent = new Intent("com.android.systemui.statusbar.work_challenge_unlocked_notification_action");
        intent.putExtra("android.intent.extra.INTENT", intentSender);
        intent.putExtra("android.intent.extra.INDEX", str);
        intent.setPackage(this.mContext.getPackageName());
        intentCreateConfirmDeviceCredentialIntent.putExtra("android.intent.extra.INTENT", PendingIntent.getBroadcast(this.mContext, 0, intent, 1409286144).getIntentSender());
        try {
            ActivityManager.getService().startConfirmDeviceCredentialIntent(intentCreateConfirmDeviceCredentialIntent, (Bundle) null);
            return true;
        } catch (RemoteException unused) {
            return true;
        }
    }
}
