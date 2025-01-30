package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.WallpaperManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.graphics.Matrix;
import android.hardware.biometrics.BiometricSourceType;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Binder;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.EventLog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.app.AbstractC0147x487e7be7;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardExitCallback;
import com.android.internal.policy.IKeyguardStateCallback;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda3;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.wm.shell.keyguard.KeyguardTransitions;
import com.android.systemui.DejankUtils;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.classifier.FalsingCollectorImpl;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.keyguard.KeyguardService;
import com.android.systemui.keyguard.SafeUIKeyguardViewMediator;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.domain.interactor.GuestUserInteractor;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.sec.ims.presence.ServiceTuple;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SafeUIKeyguardViewMediator extends KeyguardViewMediator {
    public static final Intent USER_PRESENT_INTENT = new Intent("android.intent.action.USER_PRESENT").addFlags(606076928);
    public static final Bundle USER_PRESENT_INTENT_OPTIONS = BroadcastOptions.makeBasic().setDeferralPolicy(2).setDeliveryGroupPolicy(1).toBundle();
    public AlarmManager mAlarmManager;
    public boolean mAnimatingScreenOff;
    public boolean mAodShowing;
    public AudioManager mAudioManager;
    public boolean mBootCompleted;
    public boolean mBootSendUserPresent;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final C150611 mBroadcastReceiver;
    public final Context mContext;
    public CharSequence mCustomMessage;
    public final C150510 mDelayedLockBroadcastReceiver;
    public int mDelayedProfileShowingSequence;
    public int mDelayedShowingSequence;
    public boolean mDeviceInteractive;
    public final DismissCallbackRegistry mDismissCallbackRegistry;
    public final DozeParameters mDozeParameters;
    public boolean mDozing;
    public final int mDreamOpenAnimationDuration;
    public final C15102 mDreamOverlayStateCallback;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final Lazy mDreamingToLockscreenTransitionViewModel;
    public final C15146 mExitAnimationRunner;
    public boolean mExternallyEnabled;
    public final FalsingCollector mFalsingCollector;
    public final FeatureFlags mFeatureFlags;
    public boolean mGoingToSleep;
    public final HandlerC150712 mHandler;
    public Animation mHideAnimation;
    public final SafeUIKeyguardViewMediator$$ExternalSyntheticLambda1 mHideAnimationFinishedRunnable;
    public boolean mHideAnimationRun;
    public boolean mHideAnimationRunning;
    public boolean mHiding;
    public boolean mInGestureNavigationMode;
    public boolean mInputRestricted;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final KeyguardDisplayManager mKeyguardDisplayManager;
    public boolean mKeyguardDonePending;
    public final RunnableC150813 mKeyguardGoingAwayRunnable;
    public final ArrayList mKeyguardStateCallbacks;
    public final KeyguardStateController mKeyguardStateController;
    public final C15179 mKeyguardStateControllerCallback;
    public final KeyguardTransitions mKeyguardTransitions;
    public final Lazy mKeyguardUnlockAnimationControllerLazy;
    public final Lazy mKeyguardViewControllerLazy;
    public final SparseIntArray mLastSimStates;
    public boolean mLockLater;
    public final LockPatternUtils mLockPatternUtils;
    public int mLockSoundId;
    public int mLockSoundStreamId;
    public float mLockSoundVolume;
    public SoundPool mLockSounds;
    public final CoroutineDispatcher mMainDispatcher;
    public boolean mNeedToReshowWhenReenabled;
    public final Lazy mNotificationShadeDepthController;
    public final Lazy mNotificationShadeWindowControllerLazy;
    final ActivityLaunchAnimator.Controller mOccludeAnimationController;
    public boolean mOccludeAnimationPlaying;
    public final OccludeActivityLaunchRemoteAnimationRunner mOccludeAnimationRunner;
    public final C15157 mOccludeByDreamAnimationRunner;
    public boolean mOccluded;
    public final C15041 mOnPropertiesChangedListener;
    public final PowerManager mPM;
    public boolean mPendingLock;
    public boolean mPendingPinLock;
    public boolean mPendingReset;
    public final String mPhoneState;
    public final float mPowerButtonY;
    public boolean mPowerGestureIntercepted;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final Lazy mScrimControllerLazy;
    public final SessionTracker mSessionTracker;
    public final Lazy mShadeController;
    public boolean mShowHomeOverLockscreen;
    public PowerManager.WakeLock mShowKeyguardWakeLock;
    public boolean mShowing;
    public boolean mShuttingDown;
    public final SparseBooleanArray mSimWasLocked;
    public final IBinder mStatusBarDisableToken;
    public StatusBarManager mStatusBarManager;
    public final IStatusBarService mStatusBarService;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public boolean mSurfaceBehindRemoteAnimationRequested;
    public final SystemPropertiesHelper mSystemPropertiesHelper;
    public boolean mSystemReady;
    public final TrustManager mTrustManager;
    public int mTrustedSoundId;
    public final Executor mUiBgExecutor;
    public final UiEventLogger mUiEventLogger;
    public int mUiSoundsStreamType;
    public int mUnlockSoundId;
    public boolean mUnlockingAndWakingFromDream;
    public final C15168 mUnoccludeAnimationRunner;
    public final C15113 mUpdateCallback;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final UserSwitcherController mUserSwitcherController;
    public final UserTracker mUserTracker;
    public final C15124 mViewMediatorCallback;
    public boolean mWaitingUntilKeyguardVisible;
    public boolean mWakeAndUnlocking;
    public WallpaperManager mWallpaperManager;
    public boolean mWallpaperSupportsAmbientMode;
    public final float mWindowCornerRadius;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$12 */
    public final class HandlerC150712 extends Handler {
        public HandlerC150712(Looper looper, Handler.Callback callback, boolean z) {
            super(looper, callback, z);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator;
            switch (message.what) {
                case 1:
                    SafeUIKeyguardViewMediator.m1564$$Nest$mhandleShow(SafeUIKeyguardViewMediator.this, (Bundle) message.obj);
                    return;
                case 2:
                    SafeUIKeyguardViewMediator.this.handleHide();
                    return;
                case 3:
                    SafeUIKeyguardViewMediator.m1562$$Nest$mhandleReset(SafeUIKeyguardViewMediator.this, message.arg1 != 0);
                    return;
                case 4:
                    Trace.beginSection("KeyguardViewMediator#handleMessage VERIFY_UNLOCK");
                    SafeUIKeyguardViewMediator.m1567$$Nest$mhandleVerifyUnlock(SafeUIKeyguardViewMediator.this);
                    Trace.endSection();
                    return;
                case 5:
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator2 = SafeUIKeyguardViewMediator.this;
                    Intent intent = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
                    synchronized (safeUIKeyguardViewMediator2) {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "handleNotifyFinishedGoingToSleep");
                        ((KeyguardViewController) safeUIKeyguardViewMediator2.mKeyguardViewControllerLazy.get()).onFinishedGoingToSleep();
                    }
                    return;
                case 6:
                case 15:
                case 16:
                default:
                    return;
                case 7:
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE");
                    SafeUIKeyguardViewMediator.this.handleKeyguardDone();
                    Trace.endSection();
                    return;
                case 8:
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_DRAWING");
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator3 = SafeUIKeyguardViewMediator.this;
                    Intent intent2 = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
                    safeUIKeyguardViewMediator3.getClass();
                    Trace.beginSection("KeyguardViewMediator#handleKeyguardDoneDrawing");
                    synchronized (safeUIKeyguardViewMediator3) {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "handleKeyguardDoneDrawing");
                        if (safeUIKeyguardViewMediator3.mWaitingUntilKeyguardVisible) {
                            android.util.Log.d("SafeUIKeyguardViewMediator", "handleKeyguardDoneDrawing: notifying mWaitingUntilKeyguardVisible");
                            safeUIKeyguardViewMediator3.mWaitingUntilKeyguardVisible = false;
                            safeUIKeyguardViewMediator3.notifyAll();
                            safeUIKeyguardViewMediator3.mHandler.removeMessages(8);
                        }
                    }
                    Trace.endSection();
                    Trace.endSection();
                    return;
                case 9:
                    Trace.beginSection("KeyguardViewMediator#handleMessage SET_OCCLUDED");
                    SafeUIKeyguardViewMediator.m1563$$Nest$mhandleSetOccluded(SafeUIKeyguardViewMediator.this, message.arg1 != 0, message.arg2 != 0);
                    Trace.endSection();
                    return;
                case 10:
                    synchronized (SafeUIKeyguardViewMediator.this) {
                        SafeUIKeyguardViewMediator.this.doKeyguardLocked((Bundle) message.obj);
                    }
                    return;
                case 11:
                    DismissMessage dismissMessage = (DismissMessage) message.obj;
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator4 = SafeUIKeyguardViewMediator.this;
                    IKeyguardDismissCallback iKeyguardDismissCallback = dismissMessage.mCallback;
                    CharSequence charSequence = dismissMessage.mMessage;
                    if (safeUIKeyguardViewMediator4.mShowing) {
                        if (iKeyguardDismissCallback != null) {
                            safeUIKeyguardViewMediator4.mDismissCallbackRegistry.mDismissCallbacks.add(new DismissCallbackWrapper(iKeyguardDismissCallback));
                        }
                        safeUIKeyguardViewMediator4.mCustomMessage = charSequence;
                        ((KeyguardViewController) safeUIKeyguardViewMediator4.mKeyguardViewControllerLazy.get()).dismissAndCollapse();
                        return;
                    }
                    if (iKeyguardDismissCallback != null) {
                        try {
                            new DismissCallbackWrapper(iKeyguardDismissCallback).mCallback.onDismissError();
                            return;
                        } catch (RemoteException e) {
                            android.util.Log.i("DismissCallbackWrapper", "Failed to call callback", e);
                            return;
                        }
                    }
                    return;
                case 12:
                    Trace.beginSection("KeyguardViewMediator#handleMessage START_KEYGUARD_EXIT_ANIM");
                    synchronized (SafeUIKeyguardViewMediator.this) {
                        safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                        safeUIKeyguardViewMediator.mHiding = true;
                    }
                    final StartKeyguardExitAnimParams startKeyguardExitAnimParams = (StartKeyguardExitAnimParams) message.obj;
                    ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) safeUIKeyguardViewMediator.mNotificationShadeWindowControllerLazy.get())).batchApplyWindowLayoutParams(new Runnable() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$12$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SafeUIKeyguardViewMediator.HandlerC150712 handlerC150712 = SafeUIKeyguardViewMediator.HandlerC150712.this;
                            SafeUIKeyguardViewMediator.StartKeyguardExitAnimParams startKeyguardExitAnimParams2 = startKeyguardExitAnimParams;
                            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator5 = SafeUIKeyguardViewMediator.this;
                            long j = startKeyguardExitAnimParams2.startTime;
                            long j2 = startKeyguardExitAnimParams2.fadeoutDuration;
                            RemoteAnimationTarget[] remoteAnimationTargetArr = startKeyguardExitAnimParams2.mApps;
                            RemoteAnimationTarget[] remoteAnimationTargetArr2 = startKeyguardExitAnimParams2.mWallpapers;
                            RemoteAnimationTarget[] remoteAnimationTargetArr3 = startKeyguardExitAnimParams2.mNonApps;
                            safeUIKeyguardViewMediator5.handleStartKeyguardExitAnimation(j, j2, remoteAnimationTargetArr, startKeyguardExitAnimParams2.mFinishedCallback);
                            FalsingCollectorImpl falsingCollectorImpl = (FalsingCollectorImpl) SafeUIKeyguardViewMediator.this.mFalsingCollector;
                            falsingCollectorImpl.mFalsingManager.onSuccessfulUnlock();
                            falsingCollectorImpl.sessionEnd();
                        }
                    });
                    Trace.endSection();
                    return;
                case 13:
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_PENDING_TIMEOUT");
                    android.util.Log.w("SafeUIKeyguardViewMediator", "Timeout while waiting for activity drawn!");
                    Trace.endSection();
                    return;
                case 14:
                    Trace.beginSection("KeyguardViewMediator#handleMessage NOTIFY_STARTED_WAKING_UP");
                    SafeUIKeyguardViewMediator.m1561$$Nest$mhandleNotifyStartedWakingUp(SafeUIKeyguardViewMediator.this);
                    Trace.endSection();
                    return;
                case 17:
                    SafeUIKeyguardViewMediator.m1560$$Nest$mhandleNotifyStartedGoingToSleep(SafeUIKeyguardViewMediator.this);
                    return;
                case 18:
                    SafeUIKeyguardViewMediator.m1566$$Nest$mhandleSystemReady(SafeUIKeyguardViewMediator.this);
                    return;
                case 19:
                    Trace.beginSection("KeyguardViewMediator#handleMessage CANCEL_KEYGUARD_EXIT_ANIM");
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator5 = SafeUIKeyguardViewMediator.this;
                    if (safeUIKeyguardViewMediator5.mPendingLock) {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "#handleCancelKeyguardExitAnimation: keyguard exit animation cancelled. There's a pending lock, so we were cancelled because the device was locked again during the unlock sequence. We should end up locked.");
                        safeUIKeyguardViewMediator5.finishSurfaceBehindRemoteAnimation();
                        safeUIKeyguardViewMediator5.maybeHandlePendingLock();
                    } else {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "#handleCancelKeyguardExitAnimation: keyguard exit animation cancelled. No pending lock, we should end up unlocked with the app/launcher visible.");
                        safeUIKeyguardViewMediator5.showSurfaceBehindKeyguard();
                        safeUIKeyguardViewMediator5.exitKeyguardAndFinishSurfaceBehindRemoteAnimation(true);
                    }
                    Trace.endSection();
                    return;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$13 */
    public final class RunnableC150813 implements Runnable {
        public RunnableC150813() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0049, code lost:
        
            if (r0.mWallpaperSupportsAmbientMode != false) goto L14;
         */
        /* JADX WARN: Removed duplicated region for block: B:14:0x005d  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x0072  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void run() {
            Trace.beginSection("KeyguardViewMediator.mKeyGuardGoingAwayRunnable");
            android.util.Log.d("SafeUIKeyguardViewMediator", "keyguardGoingAway");
            ((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).keyguardGoingAway();
            ((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getClass();
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            final int i = (!safeUIKeyguardViewMediator.mWakeAndUnlocking || safeUIKeyguardViewMediator.mWallpaperSupportsAmbientMode) ? 0 : 2;
            if (!((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).isGoingToNotificationShade()) {
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator2 = SafeUIKeyguardViewMediator.this;
                if (safeUIKeyguardViewMediator2.mWakeAndUnlocking) {
                }
                if (((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).isUnlockWithWallpaper()) {
                    i |= 4;
                }
                ((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).shouldSubtleWindowAnimationsForUnlock();
                if (SafeUIKeyguardViewMediator.this.mWakeAndUnlocking) {
                    KeyguardUnlockAnimationController.Companion.getClass();
                }
                SafeUIKeyguardViewMediator.this.mUpdateMonitor.setKeyguardGoingAway(true);
                ((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(true);
                SafeUIKeyguardViewMediator.this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$13$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        try {
                            ActivityTaskManager.getService().keyguardGoingAway(i);
                        } catch (RemoteException e) {
                            android.util.Log.e("SafeUIKeyguardViewMediator", "Error while calling WindowManager", e);
                        }
                    }
                });
                Trace.endSection();
            }
            i |= 1;
            if (((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).isUnlockWithWallpaper()) {
            }
            ((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).shouldSubtleWindowAnimationsForUnlock();
            if (SafeUIKeyguardViewMediator.this.mWakeAndUnlocking) {
            }
            SafeUIKeyguardViewMediator.this.mUpdateMonitor.setKeyguardGoingAway(true);
            ((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(true);
            SafeUIKeyguardViewMediator.this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$13$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        ActivityTaskManager.getService().keyguardGoingAway(i);
                    } catch (RemoteException e) {
                        android.util.Log.e("SafeUIKeyguardViewMediator", "Error while calling WindowManager", e);
                    }
                }
            });
            Trace.endSection();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$16 */
    public final class C150916 extends IRemoteAnimationRunner.Stub {
        public final /* synthetic */ IRemoteAnimationRunner val$wrapped;

        public C150916(IRemoteAnimationRunner iRemoteAnimationRunner) {
            this.val$wrapped = iRemoteAnimationRunner;
        }

        public final void onAnimationCancelled() {
            this.val$wrapped.onAnimationCancelled();
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            if (((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getViewRootImpl() != null) {
                this.val$wrapped.onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
            } else {
                android.util.Log.w("SafeUIKeyguardViewMediator", "Skipping remote animation - view root not ready");
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$4 */
    public final class C15124 implements ViewMediatorCallback {
        public C15124() {
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final CharSequence consumeCustomMessage() {
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            CharSequence charSequence = safeUIKeyguardViewMediator.mCustomMessage;
            safeUIKeyguardViewMediator.mCustomMessage = null;
            return charSequence;
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final int getBouncerPromptReason() {
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            KeyguardUpdateMonitor keyguardUpdateMonitor = safeUIKeyguardViewMediator.mUpdateMonitor;
            keyguardUpdateMonitor.getClass();
            Assert.isMainThread();
            boolean z = keyguardUpdateMonitor.mUserTrustIsUsuallyManaged.get(currentUser);
            KeyguardUpdateMonitor keyguardUpdateMonitor2 = safeUIKeyguardViewMediator.mUpdateMonitor;
            boolean z2 = z || keyguardUpdateMonitor2.isUnlockingWithBiometricsPossible(currentUser);
            KeyguardUpdateMonitor.StrongAuthTracker strongAuthTracker = keyguardUpdateMonitor2.mStrongAuthTracker;
            int strongAuthForUser = strongAuthTracker.getStrongAuthForUser(currentUser);
            boolean isNonStrongBiometricAllowedAfterIdleTimeout = strongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(currentUser);
            if (z2 && !strongAuthTracker.hasUserAuthenticatedSinceBoot()) {
                safeUIKeyguardViewMediator.mSystemPropertiesHelper.getClass();
                return SystemProperties.get("sys.boot.reason.last").equals("reboot,mainline_update") ? 16 : 1;
            }
            if (z2 && (strongAuthForUser & 16) != 0) {
                return 2;
            }
            if (z2 && (strongAuthForUser & 32) != 0) {
                return 4;
            }
            if ((strongAuthForUser & 2) != 0) {
                return 3;
            }
            if (z && (strongAuthForUser & 4) != 0) {
                return 4;
            }
            if (z && (strongAuthForUser & 256) != 0) {
                return 8;
            }
            if (z2 && ((strongAuthForUser & 8) != 0 || keyguardUpdateMonitor2.isFingerprintLockedOut())) {
                return 5;
            }
            if (z2 && (strongAuthForUser & 64) != 0) {
                return 6;
            }
            if (!z2 || (strongAuthForUser & 128) == 0) {
                return (!z2 || isNonStrongBiometricAllowedAfterIdleTimeout) ? 0 : 7;
            }
            return 7;
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final boolean isScreenOn() {
            return SafeUIKeyguardViewMediator.this.mDeviceInteractive;
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void keyguardDone(int i) {
            if (i != KeyguardUpdateMonitor.getCurrentUser()) {
                return;
            }
            android.util.Log.d("SafeUIKeyguardViewMediator", "keyguardDone");
            SafeUIKeyguardViewMediator.this.tryKeyguardDone();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void keyguardDoneDrawing() {
            Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardDoneDrawing");
            SafeUIKeyguardViewMediator.this.mHandler.sendEmptyMessage(8);
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void keyguardDonePending(int i) {
            Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardDonePending");
            android.util.Log.d("SafeUIKeyguardViewMediator", "keyguardDonePending");
            if (i != KeyguardUpdateMonitor.getCurrentUser()) {
                Trace.endSection();
                return;
            }
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            safeUIKeyguardViewMediator.mKeyguardDonePending = true;
            safeUIKeyguardViewMediator.mHideAnimationRun = true;
            safeUIKeyguardViewMediator.mHideAnimationRunning = true;
            ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).startPreHideAnimation(safeUIKeyguardViewMediator.mHideAnimationFinishedRunnable);
            safeUIKeyguardViewMediator.mHandler.sendEmptyMessageDelayed(13, 3000L);
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void keyguardGone() {
            Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardGone");
            android.util.Log.d("SafeUIKeyguardViewMediator", "keyguardGone");
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(false);
            safeUIKeyguardViewMediator.mKeyguardDisplayManager.hide();
            safeUIKeyguardViewMediator.mUpdateMonitor.startBiometricWatchdog();
            if (safeUIKeyguardViewMediator.mUnlockingAndWakingFromDream) {
                android.util.Log.d("SafeUIKeyguardViewMediator", "waking from dream after unlock");
                safeUIKeyguardViewMediator.setUnlockAndWakeFromDream(2, false);
                if (((KeyguardStateControllerImpl) safeUIKeyguardViewMediator.mKeyguardStateController).mShowing) {
                    android.util.Log.d("SafeUIKeyguardViewMediator", "keyguard showing after keyguardGone, dismiss");
                    ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).notifyKeyguardAuthenticated(!safeUIKeyguardViewMediator.mWakeAndUnlocking);
                } else {
                    android.util.Log.d("SafeUIKeyguardViewMediator", "keyguard gone, waking up from dream");
                    safeUIKeyguardViewMediator.mPM.wakeUp(SystemClock.uptimeMillis(), safeUIKeyguardViewMediator.mWakeAndUnlocking ? 17 : 4, "com.android.systemui:UNLOCK_DREAMING");
                }
            }
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void onCancelClicked() {
            ((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).onCancelClicked();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void playTrustedSound() {
            SafeUIKeyguardViewMediator.m1570$$Nest$mplayTrustedSound(SafeUIKeyguardViewMediator.this);
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void readyForKeyguardDone() {
            Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#readyForKeyguardDone");
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            if (safeUIKeyguardViewMediator.mKeyguardDonePending) {
                safeUIKeyguardViewMediator.mKeyguardDonePending = false;
                safeUIKeyguardViewMediator.tryKeyguardDone();
            }
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void resetKeyguard() {
            SafeUIKeyguardViewMediator.this.resetStateLocked(true);
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void setCustomMessage(CharSequence charSequence) {
            SafeUIKeyguardViewMediator.this.mCustomMessage = charSequence;
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void setNeedsInput(boolean z) {
            ((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).setNeedsInput(z);
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void userActivity() {
            SafeUIKeyguardViewMediator.this.userActivity();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$7 */
    public final class C15157 extends IRemoteAnimationRunner.Stub {
        public ValueAnimator mOccludeByDreamAnimator;

        public C15157() {
        }

        public final void onAnimationCancelled() {
            SafeUIKeyguardViewMediator.this.mContext.getMainExecutor().execute(new SafeUIKeyguardViewMediator$7$$ExternalSyntheticLambda1(this, 0));
            android.util.Log.d("SafeUIKeyguardViewMediator", "OccludeByDreamAnimator#onAnimationCancelled. Set occluded = true");
            SafeUIKeyguardViewMediator.this.setOccluded(true, false);
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x0053  */
        /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            boolean z;
            final RemoteAnimationTarget remoteAnimationTarget;
            if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0 || (remoteAnimationTarget = remoteAnimationTargetArr[0]) == null) {
                android.util.Log.d("SafeUIKeyguardViewMediator", "No apps provided to the OccludeByDream runner; skipping occluding animation.");
            } else {
                ActivityManager.RunningTaskInfo runningTaskInfo = remoteAnimationTarget.taskInfo;
                if (runningTaskInfo != null && runningTaskInfo.topActivityType == 5) {
                    final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
                    SafeUIKeyguardViewMediator.this.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$7$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            final SafeUIKeyguardViewMediator.C15157 c15157 = SafeUIKeyguardViewMediator.C15157.this;
                            final RemoteAnimationTarget remoteAnimationTarget2 = remoteAnimationTarget;
                            final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier2 = syncRtSurfaceTransactionApplier;
                            final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                            ValueAnimator valueAnimator = c15157.mOccludeByDreamAnimator;
                            if (valueAnimator != null) {
                                valueAnimator.cancel();
                            }
                            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                            c15157.mOccludeByDreamAnimator = ofFloat;
                            ofFloat.setDuration(SafeUIKeyguardViewMediator.this.mDreamOpenAnimationDuration);
                            c15157.mOccludeByDreamAnimator.setInterpolator(Interpolators.LINEAR);
                            c15157.mOccludeByDreamAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$7$$ExternalSyntheticLambda2
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                    syncRtSurfaceTransactionApplier2.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget2.leash).withAlpha(valueAnimator2.getAnimatedFraction()).build()});
                                }
                            });
                            c15157.mOccludeByDreamAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.7.1
                                public boolean mIsCancelled = false;

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationCancel(Animator animator) {
                                    this.mIsCancelled = true;
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    try {
                                        if (!this.mIsCancelled) {
                                            SafeUIKeyguardViewMediator.m1563$$Nest$mhandleSetOccluded(SafeUIKeyguardViewMediator.this, true, false);
                                        }
                                        iRemoteAnimationFinishedCallback2.onAnimationFinished();
                                        C15157.this.mOccludeByDreamAnimator = null;
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            c15157.mOccludeByDreamAnimator.start();
                        }
                    });
                    z = true;
                    if (z) {
                        SafeUIKeyguardViewMediator.this.setOccluded(true, false);
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        return;
                    }
                    return;
                }
                android.util.Log.w("SafeUIKeyguardViewMediator", "The occluding app isn't Dream; finishing up. Please check that the config is correct.");
            }
            z = false;
            if (z) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$8 */
    public final class C15168 extends IRemoteAnimationRunner.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;

        public C15168() {
            new Matrix();
        }

        public final void onAnimationCancelled() {
            SafeUIKeyguardViewMediator.this.mContext.getMainExecutor().execute(new SafeUIKeyguardViewMediator$7$$ExternalSyntheticLambda1(this, 1));
            android.util.Log.d("SafeUIKeyguardViewMediator", "Unocclude animation cancelled.");
            SafeUIKeyguardViewMediator.this.mInteractionJankMonitor.cancel(64);
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "UnoccludeAnimator#onAnimationStart. Set occluded = false.");
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            safeUIKeyguardViewMediator.mInteractionJankMonitor.begin(SafeUIKeyguardViewMediator.m1556$$Nest$mcreateInteractionJankMonitorConf(safeUIKeyguardViewMediator).setTag("UNOCCLUDE"));
            SafeUIKeyguardViewMediator.this.setOccluded(false, true);
            if (iRemoteAnimationFinishedCallback != null) {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DismissMessage {
        public final IKeyguardDismissCallback mCallback;
        public final CharSequence mMessage;

        public DismissMessage(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
            this.mCallback = iKeyguardDismissCallback;
            this.mMessage = charSequence;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OccludeActivityLaunchRemoteAnimationRunner extends ActivityLaunchRemoteAnimationRunner {
        public OccludeActivityLaunchRemoteAnimationRunner(ActivityLaunchAnimator.Controller controller) {
            super(SafeUIKeyguardViewMediator.this, controller);
        }

        @Override // com.android.systemui.keyguard.SafeUIKeyguardViewMediator.ActivityLaunchRemoteAnimationRunner
        public final void onAnimationCancelled() {
            android.util.Log.d("SafeUIKeyguardViewMediator", "Occlude animation cancelled by WM.");
            SafeUIKeyguardViewMediator.this.mInteractionJankMonitor.cancel(64);
        }

        @Override // com.android.systemui.keyguard.SafeUIKeyguardViewMediator.ActivityLaunchRemoteAnimationRunner
        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            super.onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            safeUIKeyguardViewMediator.mInteractionJankMonitor.begin(SafeUIKeyguardViewMediator.m1556$$Nest$mcreateInteractionJankMonitorConf(safeUIKeyguardViewMediator).setTag("OCCLUDE"));
            android.util.Log.d("SafeUIKeyguardViewMediator", "OccludeAnimator#onAnimationStart. Set occluded = true.");
            SafeUIKeyguardViewMediator.this.setOccluded(true, false);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StartKeyguardExitAnimParams {
        public final long fadeoutDuration;
        public final RemoteAnimationTarget[] mApps;
        public final IRemoteAnimationFinishedCallback mFinishedCallback;
        public final RemoteAnimationTarget[] mNonApps;
        public final RemoteAnimationTarget[] mWallpapers;
        public final long startTime;

        public /* synthetic */ StartKeyguardExitAnimParams(int i, long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, int i2) {
            this(i, j, j2, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
        }

        private StartKeyguardExitAnimParams(int i, long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            this.startTime = j;
            this.fadeoutDuration = j2;
            this.mApps = remoteAnimationTargetArr;
            this.mWallpapers = remoteAnimationTargetArr2;
            this.mNonApps = remoteAnimationTargetArr3;
            this.mFinishedCallback = iRemoteAnimationFinishedCallback;
        }
    }

    public static /* synthetic */ void $r8$lambda$GIdRB5htbmIpZ8nvMvkSUjeggGI(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator) {
        safeUIKeyguardViewMediator.getClass();
        safeUIKeyguardViewMediator.handleStartKeyguardExitAnimation(safeUIKeyguardViewMediator.mHideAnimation.getStartOffset() + SystemClock.uptimeMillis(), safeUIKeyguardViewMediator.mHideAnimation.getDuration(), null, null);
    }

    public static /* synthetic */ void $r8$lambda$GNdiXm3mHNV8n3Qc7UuNKv7SQHY(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator) {
        safeUIKeyguardViewMediator.getClass();
        android.util.Log.e("SafeUIKeyguardViewMediator", "mHideAnimationFinishedRunnable#run");
        safeUIKeyguardViewMediator.mHideAnimationRunning = false;
        safeUIKeyguardViewMediator.tryKeyguardDone();
    }

    /* renamed from: $r8$lambda$qsihrj14nio_BWc-X8GQhI-rIZk, reason: not valid java name */
    public static void m1553$r8$lambda$qsihrj14nio_BWcX8GQhIrIZk(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, boolean z, boolean z2) {
        safeUIKeyguardViewMediator.onKeyguardExitFinished();
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) safeUIKeyguardViewMediator.mKeyguardStateController;
        boolean z3 = keyguardStateControllerImpl.mDismissingFromTouch;
        Lazy lazy = safeUIKeyguardViewMediator.mKeyguardUnlockAnimationControllerLazy;
        if (z3 || z) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "onKeyguardExitRemoteAnimationFinished#hideKeyguardViewAfterRemoteAnimation");
            ((KeyguardUnlockAnimationController) lazy.get()).hideKeyguardViewAfterRemoteAnimation();
        } else {
            KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(new StringBuilder("skip hideKeyguardViewAfterRemoteAnimation dismissFromSwipe="), keyguardStateControllerImpl.mDismissingFromTouch, " wasShowing=", z, "SafeUIKeyguardViewMediator");
        }
        ((KeyguardUnlockAnimationController) lazy.get()).notifyFinishedKeyguardExitAnimation(z2);
        safeUIKeyguardViewMediator.finishSurfaceBehindRemoteAnimation();
        safeUIKeyguardViewMediator.mUpdateMonitor.mHandler.sendEmptyMessage(346);
    }

    /* renamed from: -$$Nest$mcreateInteractionJankMonitorConf, reason: not valid java name */
    public static InteractionJankMonitor.Configuration.Builder m1556$$Nest$mcreateInteractionJankMonitorConf(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator) {
        return InteractionJankMonitor.Configuration.Builder.withView(64, ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
    }

    /* renamed from: -$$Nest$mhandleNotifyStartedGoingToSleep, reason: not valid java name */
    public static void m1560$$Nest$mhandleNotifyStartedGoingToSleep(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator) {
        synchronized (safeUIKeyguardViewMediator) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "handleNotifyStartedGoingToSleep");
            ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).onStartedGoingToSleep();
        }
    }

    /* renamed from: -$$Nest$mhandleNotifyStartedWakingUp, reason: not valid java name */
    public static void m1561$$Nest$mhandleNotifyStartedWakingUp(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator) {
        safeUIKeyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleMotifyStartedWakingUp");
        synchronized (safeUIKeyguardViewMediator) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "handleNotifyWakingUp");
            ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).onStartedWakingUp();
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mhandleReset, reason: not valid java name */
    public static void m1562$$Nest$mhandleReset(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, boolean z) {
        synchronized (safeUIKeyguardViewMediator) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "handleReset");
            ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).reset(z);
        }
        safeUIKeyguardViewMediator.scheduleNonStrongBiometricIdleTimeout();
    }

    /* renamed from: -$$Nest$mhandleSetOccluded, reason: not valid java name */
    public static void m1563$$Nest$mhandleSetOccluded(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, boolean z, boolean z2) {
        safeUIKeyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleSetOccluded");
        android.util.Log.d("SafeUIKeyguardViewMediator", "handleSetOccluded(" + z + ")");
        EventLog.writeEvent(36080, Integer.valueOf(z ? 1 : 0), Integer.valueOf(z2 ? 1 : 0));
        safeUIKeyguardViewMediator.mInteractionJankMonitor.cancel(23);
        synchronized (safeUIKeyguardViewMediator) {
            if (safeUIKeyguardViewMediator.mHiding && z) {
                safeUIKeyguardViewMediator.startKeyguardExitAnimation(0L, 0L);
            }
            boolean z3 = true;
            safeUIKeyguardViewMediator.mPowerGestureIntercepted = z && safeUIKeyguardViewMediator.mUpdateMonitor.mSecureCameraLaunched;
            if (safeUIKeyguardViewMediator.mOccluded != z) {
                safeUIKeyguardViewMediator.mOccluded = z;
                KeyguardViewController keyguardViewController = (KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get();
                if (!z2 || !safeUIKeyguardViewMediator.mDeviceInteractive) {
                    z3 = false;
                }
                keyguardViewController.setOccluded(z, z3);
                safeUIKeyguardViewMediator.adjustStatusBarLocked(false, false);
            }
            android.util.Log.d("SafeUIKeyguardViewMediator", "isOccluded=" + z + ",mPowerGestureIntercepted=" + safeUIKeyguardViewMediator.mPowerGestureIntercepted);
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mhandleShow, reason: not valid java name */
    public static void m1564$$Nest$mhandleShow(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, Bundle bundle) {
        safeUIKeyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleShow");
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        if (safeUIKeyguardViewMediator.mLockPatternUtils.isSecure(currentUser)) {
            safeUIKeyguardViewMediator.mLockPatternUtils.getDevicePolicyManager().reportKeyguardSecured(currentUser);
        }
        synchronized (safeUIKeyguardViewMediator) {
            if (!safeUIKeyguardViewMediator.mSystemReady) {
                android.util.Log.d("SafeUIKeyguardViewMediator", "ignoring handleShow because system is not ready.");
                return;
            }
            android.util.Log.d("SafeUIKeyguardViewMediator", "handleShow");
            safeUIKeyguardViewMediator.mWakeAndUnlocking = false;
            safeUIKeyguardViewMediator.setUnlockAndWakeFromDream(1, false);
            safeUIKeyguardViewMediator.setPendingLock(false);
            safeUIKeyguardViewMediator.setShowingLocked(true, safeUIKeyguardViewMediator.mHiding);
            if (safeUIKeyguardViewMediator.mHiding) {
                android.util.Log.d("SafeUIKeyguardViewMediator", "Forcing setShowingLocked because mHiding=true, which means we're showing in the middle of hiding.");
            }
            safeUIKeyguardViewMediator.mHiding = false;
            ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).show(bundle);
            safeUIKeyguardViewMediator.resetKeyguardDonePendingLocked();
            safeUIKeyguardViewMediator.mHideAnimationRun = false;
            safeUIKeyguardViewMediator.adjustStatusBarLocked(false, false);
            safeUIKeyguardViewMediator.userActivity();
            safeUIKeyguardViewMediator.mUpdateMonitor.setKeyguardGoingAway(false);
            ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(false);
            safeUIKeyguardViewMediator.mShowKeyguardWakeLock.release();
            safeUIKeyguardViewMediator.mKeyguardDisplayManager.show();
            safeUIKeyguardViewMediator.scheduleNonStrongBiometricIdleTimeout();
            Trace.endSection();
        }
    }

    /* renamed from: -$$Nest$mhandleSystemReady, reason: not valid java name */
    public static void m1566$$Nest$mhandleSystemReady(final SafeUIKeyguardViewMediator safeUIKeyguardViewMediator) {
        synchronized (safeUIKeyguardViewMediator) {
            try {
                android.util.Log.d("SafeUIKeyguardViewMediator", "onSystemReady");
                final int i = 1;
                safeUIKeyguardViewMediator.mSystemReady = true;
                safeUIKeyguardViewMediator.doKeyguardLocked(null);
                safeUIKeyguardViewMediator.mUpdateMonitor.registerCallback(safeUIKeyguardViewMediator.mUpdateCallback);
                final int i2 = 0;
                safeUIKeyguardViewMediator.adjustStatusBarLocked(false, false);
                safeUIKeyguardViewMediator.mDreamOverlayStateController.addCallback((DreamOverlayStateController.Callback) safeUIKeyguardViewMediator.mDreamOverlayStateCallback);
                ViewRootImpl viewRootImpl = ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).getViewRootImpl();
                if (viewRootImpl != null) {
                    JavaAdapterKt.collectFlow(viewRootImpl.getView(), ((DreamingToLockscreenTransitionViewModel) safeUIKeyguardViewMediator.mDreamingToLockscreenTransitionViewModel.get()).dreamOverlayAlpha, new Consumer(safeUIKeyguardViewMediator) { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda8
                        public final /* synthetic */ SafeUIKeyguardViewMediator f$0;

                        {
                            this.f$0 = safeUIKeyguardViewMediator;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            switch (i) {
                                case 0:
                                    this.f$0.getClass();
                                    break;
                                default:
                                    this.f$0.getClass();
                                    break;
                            }
                        }
                    }, safeUIKeyguardViewMediator.mMainDispatcher);
                    JavaAdapterKt.collectFlow(viewRootImpl.getView(), ((DreamingToLockscreenTransitionViewModel) safeUIKeyguardViewMediator.mDreamingToLockscreenTransitionViewModel.get()).transitionEnded, new Consumer(safeUIKeyguardViewMediator) { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda8
                        public final /* synthetic */ SafeUIKeyguardViewMediator f$0;

                        {
                            this.f$0 = safeUIKeyguardViewMediator;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            switch (i2) {
                                case 0:
                                    this.f$0.getClass();
                                    break;
                                default:
                                    this.f$0.getClass();
                                    break;
                            }
                        }
                    }, safeUIKeyguardViewMediator.mMainDispatcher);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        safeUIKeyguardViewMediator.maybeSendUserPresentBroadcast();
    }

    /* renamed from: -$$Nest$mhandleVerifyUnlock, reason: not valid java name */
    public static void m1567$$Nest$mhandleVerifyUnlock(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator) {
        safeUIKeyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleVerifyUnlock");
        synchronized (safeUIKeyguardViewMediator) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "handleVerifyUnlock");
            safeUIKeyguardViewMediator.setShowingLocked(true, false);
            ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).dismissAndCollapse();
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mlockProfile, reason: not valid java name */
    public static void m1568$$Nest$mlockProfile(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, int i) {
        safeUIKeyguardViewMediator.mTrustManager.setDeviceLockedForUser(i, true);
    }

    /* renamed from: -$$Nest$mplayTrustedSound, reason: not valid java name */
    public static void m1570$$Nest$mplayTrustedSound(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator) {
        safeUIKeyguardViewMediator.playSound(safeUIKeyguardViewMediator.mTrustedSoundId);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1, types: [android.provider.DeviceConfig$OnPropertiesChangedListener, com.android.systemui.keyguard.SafeUIKeyguardViewMediator$1] */
    /* JADX WARN: Type inference failed for: r5v10, types: [com.android.systemui.keyguard.SafeUIKeyguardViewMediator$9, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.keyguard.SafeUIKeyguardViewMediator$2] */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.systemui.keyguard.SafeUIKeyguardViewMediator$3] */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.systemui.keyguard.SafeUIKeyguardViewMediator$6] */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.android.systemui.keyguard.SafeUIKeyguardViewMediator$10] */
    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.systemui.keyguard.SafeUIKeyguardViewMediator$11] */
    public SafeUIKeyguardViewMediator(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, Context context, UiEventLogger uiEventLogger, SessionTracker sessionTracker, UserTracker userTracker, FalsingCollector falsingCollector, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, Lazy lazy, DismissCallbackRegistry dismissCallbackRegistry, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, Executor executor, PowerManager powerManager, TrustManager trustManager, UserSwitcherController userSwitcherController, DeviceConfigProxy deviceConfigProxy, NavigationModeController navigationModeController, KeyguardDisplayManager keyguardDisplayManager, DozeParameters dozeParameters, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController, Lazy lazy2, ScreenOffAnimationController screenOffAnimationController, Lazy lazy3, ScreenOnCoordinator screenOnCoordinator, KeyguardTransitions keyguardTransitions, InteractionJankMonitor interactionJankMonitor, DreamOverlayStateController dreamOverlayStateController, Lazy lazy4, Lazy lazy5, Lazy lazy6, Lazy lazy7, FeatureFlags featureFlags, CoroutineDispatcher coroutineDispatcher, Lazy lazy8, SystemPropertiesHelper systemPropertiesHelper) {
        super(keyguardViewMediatorHelperImpl, context, uiEventLogger, sessionTracker, userTracker, falsingCollector, lockPatternUtils, broadcastDispatcher, lazy, dismissCallbackRegistry, keyguardUpdateMonitor, dumpManager, executor, powerManager, trustManager, userSwitcherController, deviceConfigProxy, navigationModeController, keyguardDisplayManager, dozeParameters, sysuiStatusBarStateController, keyguardStateController, lazy2, screenOffAnimationController, lazy3, screenOnCoordinator, keyguardTransitions, interactionJankMonitor, dreamOverlayStateController, lazy4, lazy5, lazy6, lazy7, featureFlags, coroutineDispatcher, lazy8, systemPropertiesHelper);
        this.mStatusBarDisableToken = new Binder();
        this.mExternallyEnabled = true;
        this.mNeedToReshowWhenReenabled = false;
        this.mOccluded = false;
        this.mOccludeAnimationPlaying = false;
        this.mWakeAndUnlocking = false;
        this.mLastSimStates = new SparseIntArray();
        this.mSimWasLocked = new SparseBooleanArray();
        this.mPhoneState = TelephonyManager.EXTRA_STATE_IDLE;
        this.mWaitingUntilKeyguardVisible = false;
        this.mKeyguardDonePending = false;
        this.mUnlockingAndWakingFromDream = false;
        this.mHideAnimationRun = false;
        this.mHideAnimationRunning = false;
        this.mKeyguardStateCallbacks = new ArrayList();
        this.mPendingPinLock = false;
        this.mPowerGestureIntercepted = false;
        this.mSurfaceBehindRemoteAnimationRequested = false;
        ?? r4 = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("nav_bar_handle_show_over_lockscreen")) {
                    SafeUIKeyguardViewMediator.this.mShowHomeOverLockscreen = properties.getBoolean("nav_bar_handle_show_over_lockscreen", true);
                }
            }
        };
        this.mOnPropertiesChangedListener = r4;
        this.mDreamOverlayStateCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.2
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onStateChanged() {
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                safeUIKeyguardViewMediator.mDreamOverlayStateController.isOverlayActive();
                safeUIKeyguardViewMediator.getClass();
            }
        };
        this.mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.3
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                if (safeUIKeyguardViewMediator.mLockPatternUtils.isSecure(currentUser)) {
                    safeUIKeyguardViewMediator.mLockPatternUtils.getDevicePolicyManager().reportFailedBiometricAttempt(currentUser);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                if (safeUIKeyguardViewMediator.mLockPatternUtils.isSecure(i)) {
                    safeUIKeyguardViewMediator.mLockPatternUtils.getDevicePolicyManager().reportSuccessfulBiometricAttempt(i);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDeviceProvisioned() {
                SafeUIKeyguardViewMediator.this.sendUserPresentBroadcast();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                synchronized (SafeUIKeyguardViewMediator.this) {
                    if (!z) {
                        if (SafeUIKeyguardViewMediator.this.mPendingPinLock) {
                            android.util.Log.i("SafeUIKeyguardViewMediator", "PIN lock requested, starting keyguard");
                            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                            safeUIKeyguardViewMediator.mPendingPinLock = false;
                            safeUIKeyguardViewMediator.doKeyguardLocked(null);
                        }
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                boolean z;
                KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m71m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("onSimStateChanged(subId=", i, ", slotId=", i2, ",state="), i3, ")", "SafeUIKeyguardViewMediator");
                int size = SafeUIKeyguardViewMediator.this.mKeyguardStateCallbacks.size();
                boolean isSimPinSecure = SafeUIKeyguardViewMediator.this.mUpdateMonitor.isSimPinSecure();
                for (int i4 = size - 1; i4 >= 0; i4--) {
                    try {
                        ((IKeyguardStateCallback) SafeUIKeyguardViewMediator.this.mKeyguardStateCallbacks.get(i4)).onSimSecureStateChanged(isSimPinSecure);
                    } catch (RemoteException e) {
                        android.util.Slog.w("SafeUIKeyguardViewMediator", "Failed to call onSimSecureStateChanged", e);
                        if (e instanceof DeadObjectException) {
                            SafeUIKeyguardViewMediator.this.mKeyguardStateCallbacks.remove(i4);
                        }
                    }
                }
                synchronized (SafeUIKeyguardViewMediator.this) {
                    int i5 = SafeUIKeyguardViewMediator.this.mLastSimStates.get(i2);
                    if (i5 != 2 && i5 != 3) {
                        z = false;
                        SafeUIKeyguardViewMediator.this.mLastSimStates.append(i2, i3);
                    }
                    z = true;
                    SafeUIKeyguardViewMediator.this.mLastSimStates.append(i2, i3);
                }
                if (i3 != 0 && i3 != 1) {
                    if (i3 == 2 || i3 == 3) {
                        synchronized (SafeUIKeyguardViewMediator.this) {
                            SafeUIKeyguardViewMediator.this.mSimWasLocked.append(i2, true);
                            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                            safeUIKeyguardViewMediator.mPendingPinLock = true;
                            if (safeUIKeyguardViewMediator.mShowing) {
                                safeUIKeyguardViewMediator.resetStateLocked(true);
                            } else {
                                android.util.Log.d("SafeUIKeyguardViewMediator", "INTENT_VALUE_ICC_LOCKED and keygaurd isn't showing; need to show keyguard so user can enter sim pin");
                                SafeUIKeyguardViewMediator.this.doKeyguardLocked(null);
                            }
                        }
                        return;
                    }
                    if (i3 == 5) {
                        synchronized (SafeUIKeyguardViewMediator.this) {
                            android.util.Log.d("SafeUIKeyguardViewMediator", "READY, reset state? " + SafeUIKeyguardViewMediator.this.mShowing);
                            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator2 = SafeUIKeyguardViewMediator.this;
                            if (safeUIKeyguardViewMediator2.mShowing && safeUIKeyguardViewMediator2.mSimWasLocked.get(i2, false)) {
                                android.util.Log.d("SafeUIKeyguardViewMediator", "SIM moved to READY when the previously was locked. Reset the state.");
                                SafeUIKeyguardViewMediator.this.mSimWasLocked.append(i2, false);
                                SafeUIKeyguardViewMediator.this.resetStateLocked(true);
                            }
                        }
                        return;
                    }
                    if (i3 != 6) {
                        if (i3 != 7) {
                            return;
                        }
                        synchronized (SafeUIKeyguardViewMediator.this) {
                            if (SafeUIKeyguardViewMediator.this.mShowing) {
                                android.util.Log.d("SafeUIKeyguardViewMediator", "PERM_DISABLED, resetStateLocked toshow permanently disabled message in lockscreen.");
                                SafeUIKeyguardViewMediator.this.resetStateLocked(true);
                            } else {
                                android.util.Log.d("SafeUIKeyguardViewMediator", "PERM_DISABLED and keygaurd isn't showing.");
                                SafeUIKeyguardViewMediator.this.doKeyguardLocked(null);
                            }
                        }
                        return;
                    }
                }
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator3 = SafeUIKeyguardViewMediator.this;
                safeUIKeyguardViewMediator3.mPendingPinLock = false;
                synchronized (safeUIKeyguardViewMediator3) {
                    if (SafeUIKeyguardViewMediator.this.shouldWaitForProvisioning()) {
                        SafeUIKeyguardViewMediator safeUIKeyguardViewMediator4 = SafeUIKeyguardViewMediator.this;
                        if (safeUIKeyguardViewMediator4.mShowing) {
                            safeUIKeyguardViewMediator4.resetStateLocked(true);
                        } else {
                            android.util.Log.d("SafeUIKeyguardViewMediator", "ICC_ABSENT isn't showing, we need to show the keyguard since the device isn't provisioned yet.");
                            SafeUIKeyguardViewMediator.this.doKeyguardLocked(null);
                        }
                    }
                    if (i3 == 1) {
                        if (z) {
                            android.util.Log.d("SafeUIKeyguardViewMediator", "SIM moved to ABSENT when the previous state was locked. Reset the state.");
                            SafeUIKeyguardViewMediator.this.resetStateLocked(true);
                        }
                        SafeUIKeyguardViewMediator.this.mSimWasLocked.append(i2, false);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStrongAuthStateChanged(int i) {
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                if (safeUIKeyguardViewMediator.mLockPatternUtils.isUserInLockdown(KeyguardUpdateMonitor.getCurrentUser())) {
                    safeUIKeyguardViewMediator.doKeyguardLocked(null);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTrustChanged(int i) {
                if (i == KeyguardUpdateMonitor.getCurrentUser()) {
                    synchronized (SafeUIKeyguardViewMediator.this) {
                        SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                        safeUIKeyguardViewMediator.notifyTrustedChangedLocked(safeUIKeyguardViewMediator.mUpdateMonitor.getUserHasTrust(i));
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                android.util.Log.d("SafeUIKeyguardViewMediator", String.format("onUserSwitchComplete %d", Integer.valueOf(i)));
                if (i != 0) {
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                    UserInfo userInfo = UserManager.get(safeUIKeyguardViewMediator.mContext).getUserInfo(i);
                    if (userInfo == null || safeUIKeyguardViewMediator.mLockPatternUtils.isSecure(i)) {
                        return;
                    }
                    if (userInfo.isGuest() || userInfo.isDemo()) {
                        safeUIKeyguardViewMediator.dismiss(null, null);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitching(int i) {
                android.util.Log.d("SafeUIKeyguardViewMediator", String.format("onUserSwitching %d", Integer.valueOf(i)));
                synchronized (SafeUIKeyguardViewMediator.this) {
                    SafeUIKeyguardViewMediator.this.resetKeyguardDonePendingLocked();
                    if (SafeUIKeyguardViewMediator.this.mLockPatternUtils.isLockScreenDisabled(i)) {
                        SafeUIKeyguardViewMediator.this.dismiss(null, null);
                    } else {
                        SafeUIKeyguardViewMediator.this.resetStateLocked(true);
                    }
                    SafeUIKeyguardViewMediator.this.adjustStatusBarLocked(false, false);
                }
            }
        };
        this.mViewMediatorCallback = new C15124();
        ActivityLaunchAnimator.Controller controller = new ActivityLaunchAnimator.Controller() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.5
            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final LaunchAnimator.State createAnimatorState() {
                int width = getLaunchContainer().getWidth();
                int height = getLaunchContainer().getHeight();
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                if (safeUIKeyguardViewMediator.mUpdateMonitor.mSecureCameraLaunched) {
                    float f = width;
                    float f2 = safeUIKeyguardViewMediator.mPowerButtonY;
                    float f3 = (height / 3.0f) / 2.0f;
                    float f4 = safeUIKeyguardViewMediator.mWindowCornerRadius;
                    return new LaunchAnimator.State((int) (f2 - f3), (int) (f2 + f3), (int) (f - (f / 3.0f)), width, f4, f4);
                }
                float f5 = height;
                float f6 = f5 / 2.0f;
                float f7 = width;
                float f8 = f7 / 2.0f;
                float f9 = f5 - f6;
                float f10 = f7 - f8;
                float f11 = safeUIKeyguardViewMediator.mWindowCornerRadius;
                return new LaunchAnimator.State(((int) f9) / 2, (int) ((f9 / 2.0f) + f6), ((int) f10) / 2, (int) ((f10 / 2.0f) + f8), f11, f11);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final ViewGroup getLaunchContainer() {
                return (ViewGroup) ((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView();
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
            public final void onLaunchAnimationCancelled(Boolean bool) {
                StringBuilder sb = new StringBuilder("Occlude launch animation cancelled. Occluded state is now: ");
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, safeUIKeyguardViewMediator.mOccluded, "SafeUIKeyguardViewMediator");
                safeUIKeyguardViewMediator.mOccludeAnimationPlaying = false;
                safeUIKeyguardViewMediator.getClass();
                throw null;
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void onLaunchAnimationEnd(boolean z) {
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                if (z) {
                    ((ShadeControllerImpl) ((ShadeController) safeUIKeyguardViewMediator.mShadeController.get())).instantCollapseShade();
                }
                safeUIKeyguardViewMediator.mOccludeAnimationPlaying = false;
                throw null;
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void onLaunchAnimationStart(boolean z) {
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                safeUIKeyguardViewMediator.mOccludeAnimationPlaying = true;
                ((ScrimController) safeUIKeyguardViewMediator.mScrimControllerLazy.get()).setOccludeAnimationPlaying(true);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void setLaunchContainer(ViewGroup viewGroup) {
                android.util.Log.wtf("SafeUIKeyguardViewMediator", "Someone tried to change the launch container for the ActivityLaunchAnimator, which should never happen.");
            }
        };
        this.mOccludeAnimationController = controller;
        this.mExitAnimationRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.6
            public final void onAnimationCancelled() {
                SafeUIKeyguardViewMediator.this.cancelKeyguardExitAnimation();
            }

            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                Trace.beginSection("mExitAnimationRunner.onAnimationStart#startKeyguardExitAnimation");
                SafeUIKeyguardViewMediator.this.startKeyguardExitAnimation(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
                Trace.endSection();
            }
        };
        this.mOccludeAnimationRunner = new OccludeActivityLaunchRemoteAnimationRunner(controller);
        this.mOccludeByDreamAnimationRunner = new C15157();
        this.mUnoccludeAnimationRunner = new C15168();
        ?? r5 = new KeyguardStateController.Callback() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.9
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onPrimaryBouncerShowingChanged() {
                synchronized (SafeUIKeyguardViewMediator.this) {
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                    KeyguardStateController keyguardStateController2 = safeUIKeyguardViewMediator.mKeyguardStateController;
                    if (((KeyguardStateControllerImpl) keyguardStateController2).mPrimaryBouncerShowing && !((KeyguardStateControllerImpl) keyguardStateController2).mKeyguardGoingAway) {
                        safeUIKeyguardViewMediator.mPendingPinLock = false;
                    }
                    safeUIKeyguardViewMediator.adjustStatusBarLocked(((KeyguardStateControllerImpl) keyguardStateController2).mPrimaryBouncerShowing, false);
                }
            }
        };
        this.mKeyguardStateControllerCallback = r5;
        this.mDelayedLockBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.10
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("seq", 0);
                    RecyclerView$$ExternalSyntheticOutline0.m46m(AbstractC0000x2c234b15.m1m("received DELAYED_KEYGUARD_ACTION with seq = ", intExtra, ", mDelayedShowingSequence = "), SafeUIKeyguardViewMediator.this.mDelayedShowingSequence, "SafeUIKeyguardViewMediator");
                    synchronized (SafeUIKeyguardViewMediator.this) {
                        SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                        if (safeUIKeyguardViewMediator.mDelayedShowingSequence == intExtra) {
                            safeUIKeyguardViewMediator.doKeyguardLocked(null);
                        }
                    }
                    return;
                }
                if ("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK".equals(intent.getAction())) {
                    int intExtra2 = intent.getIntExtra("seq", 0);
                    int intExtra3 = intent.getIntExtra("android.intent.extra.USER_ID", 0);
                    if (intExtra3 != 0) {
                        synchronized (SafeUIKeyguardViewMediator.this) {
                            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator2 = SafeUIKeyguardViewMediator.this;
                            if (safeUIKeyguardViewMediator2.mDelayedProfileShowingSequence == intExtra2) {
                                SafeUIKeyguardViewMediator.m1568$$Nest$mlockProfile(safeUIKeyguardViewMediator2, intExtra3);
                            }
                        }
                    }
                }
            }
        };
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.11
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                    synchronized (SafeUIKeyguardViewMediator.this) {
                        SafeUIKeyguardViewMediator.this.mShuttingDown = true;
                    }
                }
            }
        };
        final HandlerC150712 handlerC150712 = new HandlerC150712(Looper.myLooper(), null, true);
        this.mHandler = handlerC150712;
        this.mKeyguardGoingAwayRunnable = new RunnableC150813();
        this.mHideAnimationFinishedRunnable = new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda1(this, 1);
        this.mContext = context;
        this.mUserTracker = userTracker;
        this.mFalsingCollector = falsingCollector;
        this.mLockPatternUtils = lockPatternUtils;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mKeyguardViewControllerLazy = lazy;
        this.mDismissCallbackRegistry = dismissCallbackRegistry;
        this.mNotificationShadeDepthController = lazy3;
        this.mUiBgExecutor = executor;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mPM = powerManager;
        this.mTrustManager = trustManager;
        this.mUserSwitcherController = userSwitcherController;
        this.mSystemPropertiesHelper = systemPropertiesHelper;
        this.mStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        this.mKeyguardDisplayManager = keyguardDisplayManager;
        this.mShadeController = lazy4;
        String name = SafeUIKeyguardViewMediator.class.getName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, name, this);
        this.mKeyguardTransitions = keyguardTransitions;
        this.mNotificationShadeWindowControllerLazy = lazy5;
        deviceConfigProxy.getClass();
        this.mShowHomeOverLockscreen = DeviceConfig.getBoolean("systemui", "nav_bar_handle_show_over_lockscreen", true);
        DeviceConfig.addOnPropertiesChangedListener("systemui", new Executor() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                handlerC150712.post(runnable);
            }
        }, (DeviceConfig.OnPropertiesChangedListener) r4);
        this.mInGestureNavigationMode = QuickStepContract.isGesturalMode(navigationModeController.addListener(new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda3
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i) {
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                safeUIKeyguardViewMediator.getClass();
                safeUIKeyguardViewMediator.mInGestureNavigationMode = QuickStepContract.isGesturalMode(i);
            }
        }));
        this.mDozeParameters = dozeParameters;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        ((StatusBarStateControllerImpl) sysuiStatusBarStateController).addCallback((StatusBarStateController.StateListener) this);
        this.mKeyguardStateController = keyguardStateController;
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(r5);
        this.mKeyguardUnlockAnimationControllerLazy = lazy2;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mScrimControllerLazy = lazy7;
        this.mPowerButtonY = context.getResources().getDimensionPixelSize(R.dimen.physical_power_button_center_screen_location_y);
        this.mWindowCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        this.mDreamOpenAnimationDuration = (int) LockscreenToDreamingTransitionViewModel.DREAMING_ANIMATION_DURATION_MS;
        this.mFeatureFlags = featureFlags;
        this.mUiEventLogger = uiEventLogger;
        this.mSessionTracker = sessionTracker;
        this.mMainDispatcher = coroutineDispatcher;
        this.mDreamingToLockscreenTransitionViewModel = lazy8;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustStatusBarLocked(boolean z, boolean z2) {
        StatusBarManager statusBarManager = this.mStatusBarManager;
        Context context = this.mContext;
        if (statusBarManager == null) {
            this.mStatusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        }
        if (this.mStatusBarManager == null) {
            android.util.Log.w("SafeUIKeyguardViewMediator", "Could not get status bar manager");
            return;
        }
        UserTracker userTracker = this.mUserTracker;
        IBinder iBinder = this.mStatusBarDisableToken;
        IStatusBarService iStatusBarService = this.mStatusBarService;
        if (z2) {
            try {
                iStatusBarService.disableForUser(0, iBinder, context.getPackageName(), ((UserTrackerImpl) userTracker).getUserId());
            } catch (RemoteException e) {
                android.util.Log.d("SafeUIKeyguardViewMediator", "Failed to force clear flags", e);
            }
        }
        if (z || isShowingAndNotOccluded()) {
            r5 = ((this.mShowHomeOverLockscreen && this.mInGestureNavigationMode) ? 0 : com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_DEVICE_DOZING) | 16777216;
        }
        if (this.mPowerGestureIntercepted && this.mOccluded && isSecure()) {
            r5 |= 16777216;
        }
        android.util.Log.d("SafeUIKeyguardViewMediator", "adjustStatusBarLocked: mShowing=" + this.mShowing + " mOccluded=" + this.mOccluded + " isSecure=" + isSecure() + " force=" + z + " mPowerGestureIntercepted=" + this.mPowerGestureIntercepted + " --> flags=0x" + Integer.toHexString(r5));
        try {
            iStatusBarService.disableForUser(r5, iBinder, context.getPackageName(), ((UserTrackerImpl) userTracker).getUserId());
        } catch (RemoteException e2) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "Failed to set disable flags: " + r5, e2);
        }
    }

    private void doKeyguardLaterLocked(long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime() + j;
        Intent intent = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        Context context = this.mContext;
        intent.setPackage(context.getPackageName());
        intent.putExtra("seq", this.mDelayedShowingSequence);
        intent.addFlags(com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        this.mAlarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime, PendingIntent.getBroadcast(context, 0, intent, 335544320));
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("setting alarm to turn off keyguard, seq = "), this.mDelayedShowingSequence, "SafeUIKeyguardViewMediator");
        for (UserInfo userInfo : ((UserTrackerImpl) this.mUserTracker).getUserProfiles()) {
            if (userInfo.isEnabled()) {
                int i = userInfo.id;
                if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
                    long lockTimeout = getLockTimeout(i);
                    if (lockTimeout == 0) {
                        doKeyguardForChildProfilesLocked();
                    } else {
                        long elapsedRealtime2 = SystemClock.elapsedRealtime() + lockTimeout;
                        Intent intent2 = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
                        intent2.setPackage(context.getPackageName());
                        intent2.putExtra("seq", this.mDelayedProfileShowingSequence);
                        intent2.putExtra("android.intent.extra.USER_ID", i);
                        intent2.addFlags(com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                        this.mAlarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime2, PendingIntent.getBroadcast(context, 0, intent2, 335544320));
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doKeyguardLocked(Bundle bundle) {
        boolean z = this.mExternallyEnabled;
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        if (!z && !lockPatternUtils.isUserInLockdown(KeyguardUpdateMonitor.getCurrentUser())) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "doKeyguard: not showing because externally disabled");
            this.mNeedToReshowWhenReenabled = true;
            return;
        }
        if (this.mShowing && ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            if (this.mPM.isInteractive() && !this.mHiding) {
                android.util.Log.d("SafeUIKeyguardViewMediator", "doKeyguard: not showing (instead, resetting) because it is already showing, we're interactive, and we were not previously hiding. It should be safe to short-circuit here.");
                resetStateLocked(false);
                return;
            }
            android.util.Log.e("SafeUIKeyguardViewMediator", "doKeyguard: already showing, but re-showing because we're interactive or were in the middle of hiding.");
        }
        boolean z2 = !SystemProperties.getBoolean("keyguard.no_require_sim", false);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        boolean z3 = keyguardUpdateMonitor.isSimPinSecure() || ((SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(1)) || SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(7))) && z2);
        if (!z3 && shouldWaitForProvisioning()) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "doKeyguard: not showing because device isn't provisioned and the sim is not locked or missing");
            return;
        }
        boolean z4 = bundle != null && bundle.getBoolean("force_show", false);
        if (lockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser()) && !z3 && !z4) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "doKeyguard: not showing because lockscreen is off");
            return;
        }
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (lockPatternUtils.isSecure(userId) || lockPatternUtils.isLockScreenDisabled(userId)) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "doKeyguard: showing the lock screen");
            showLocked(bundle);
        } else {
            android.util.Log.d("SafeUIKeyguardViewMediator", "doKeyguard: not showing in safe & swipe mode");
            setShowingLocked(false, true);
        }
    }

    private long getLockTimeout(int i) {
        long intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "lock_screen_lock_after_timeout", 5000, i);
        long maximumTimeToLock = this.mLockPatternUtils.getDevicePolicyManager().getMaximumTimeToLock(null, i);
        return maximumTimeToLock <= 0 ? intForUser : Math.max(Math.min(maximumTimeToLock - Math.max(Settings.System.getIntForUser(r0, "screen_off_timeout", 30000, i), 0L), intForUser), 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHide() {
        Trace.beginSection("KeyguardViewMediator#handleHide");
        if (this.mAodShowing) {
            this.mPM.wakeUp(SystemClock.uptimeMillis(), 4, "com.android.systemui:BOUNCER_DOZING");
        }
        synchronized (this) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "handleHide");
            boolean z = true;
            this.mHiding = true;
            if (!this.mWakeAndUnlocking) {
                if (!((StatusBarStateControllerImpl) this.mStatusBarStateController).mIsDreaming || !this.mPM.isInteractive()) {
                    z = false;
                }
                setUnlockAndWakeFromDream(0, z);
            }
            if ((!this.mShowing || this.mOccluded) && !this.mUnlockingAndWakingFromDream) {
                ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) this.mNotificationShadeWindowControllerLazy.get())).batchApplyWindowLayoutParams(new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda1(this, 2));
            } else {
                if (this.mUnlockingAndWakingFromDream) {
                    android.util.Log.d("SafeUIKeyguardViewMediator", "hiding keyguard before waking from dream");
                }
                this.mKeyguardGoingAwayRunnable.run();
            }
        }
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleKeyguardDone() {
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDone");
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        this.mUiBgExecutor.execute(new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda6(this, currentUser, 1));
        android.util.Log.d("SafeUIKeyguardViewMediator", "handleKeyguardDone");
        synchronized (this) {
            resetKeyguardDonePendingLocked();
        }
        if (this.mGoingToSleep) {
            this.mUpdateMonitor.clearBiometricRecognized(currentUser);
            android.util.Log.i("SafeUIKeyguardViewMediator", "Device is going to sleep, aborting keyguardDone");
        } else {
            setPendingLock(false);
            handleHide();
            this.mUpdateMonitor.clearBiometricRecognized(currentUser);
            Trace.endSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStartKeyguardExitAnimation(long j, long j2, final RemoteAnimationTarget[] remoteAnimationTargetArr, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        Trace.beginSection("KeyguardViewMediator#handleStartKeyguardExitAnimation");
        android.util.Log.d("SafeUIKeyguardViewMediator", "handleStartKeyguardExitAnimation startTime=" + j + " fadeoutDuration=" + j2);
        synchronized (this) {
            if (!this.mHiding && !this.mSurfaceBehindRemoteAnimationRequested && !((KeyguardStateControllerImpl) this.mKeyguardStateController).mFlingingToDismissKeyguardDuringSwipeGesture) {
                if (iRemoteAnimationFinishedCallback != null) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                    } catch (RemoteException e) {
                        android.util.Slog.w("SafeUIKeyguardViewMediator", "Failed to call onAnimationFinished", e);
                    }
                }
                setShowingLocked(this.mShowing, true);
                return;
            }
            this.mHiding = false;
            LatencyTracker.getInstance(this.mContext).onActionEnd(11);
            if (iRemoteAnimationFinishedCallback != null) {
                try {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
            this.mInteractionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(29, ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView()).setTag("RemoteAnimationDisabled"));
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).hide(j, j2);
            this.mContext.getMainExecutor().execute(new Runnable(iRemoteAnimationFinishedCallback, remoteAnimationTargetArr) { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                    ((KeyguardUnlockAnimationController) safeUIKeyguardViewMediator.mKeyguardUnlockAnimationControllerLazy.get()).notifyFinishedKeyguardExitAnimation(false);
                    safeUIKeyguardViewMediator.mInteractionJankMonitor.end(29);
                }
            });
            onKeyguardExitFinished();
            Trace.endSection();
            return;
        }
    }

    private void maybeSendUserPresentBroadcast() {
        boolean z = this.mSystemReady;
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        if (z && lockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser())) {
            sendUserPresentBroadcast();
        } else if (this.mSystemReady && shouldWaitForProvisioning()) {
            lockPatternUtils.userPresent(KeyguardUpdateMonitor.getCurrentUser());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTrustedChangedLocked(boolean z) {
        ArrayList arrayList = this.mKeyguardStateCallbacks;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            try {
                ((IKeyguardStateCallback) arrayList.get(size)).onTrustedChanged(z);
            } catch (RemoteException e) {
                android.util.Slog.w("SafeUIKeyguardViewMediator", "Failed to call notifyTrustedChangedLocked", e);
                if (e instanceof DeadObjectException) {
                    arrayList.remove(size);
                }
            }
        }
    }

    private void onKeyguardExitFinished() {
        android.util.Log.d("SafeUIKeyguardViewMediator", "onKeyguardExitFinished()");
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(this.mPhoneState)) {
            playSound(this.mUnlockSoundId);
        }
        setShowingLocked(false, false);
        this.mWakeAndUnlocking = false;
        this.mDismissCallbackRegistry.notifyDismissSucceeded();
        resetKeyguardDonePendingLocked();
        this.mHideAnimationRun = false;
        adjustStatusBarLocked(false, false);
        sendUserPresentBroadcast();
    }

    private void playSound(int i) {
        if (i == 0) {
            return;
        }
        Context context = this.mContext;
        if (Settings.System.getIntForUser(context.getContentResolver(), "lockscreen_sounds_enabled", 1, KeyguardUpdateMonitor.getCurrentUser()) == 1) {
            this.mLockSounds.stop(this.mLockSoundStreamId);
            if (this.mAudioManager == null) {
                AudioManager audioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                this.mAudioManager = audioManager;
                if (audioManager == null) {
                    return;
                } else {
                    this.mUiSoundsStreamType = audioManager.getUiSoundsStreamType();
                }
            }
            this.mUiBgExecutor.execute(new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda6(this, i, 0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetKeyguardDonePendingLocked() {
        this.mKeyguardDonePending = false;
        this.mHandler.removeMessages(13);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetStateLocked(boolean z) {
        android.util.Log.e("SafeUIKeyguardViewMediator", "resetStateLocked");
        HandlerC150712 handlerC150712 = this.mHandler;
        handlerC150712.sendMessage(handlerC150712.obtainMessage(3, z ? 1 : 0, 0));
    }

    private void scheduleNonStrongBiometricIdleTimeout() {
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if ((!keyguardUpdateMonitor.isFaceClass3() && keyguardUpdateMonitor.isUnlockWithFacePossible(currentUser)) || (keyguardUpdateMonitor.isFingerprintClass3() && keyguardUpdateMonitor.isUnlockWithFingerprintPossible(currentUser))) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("scheduleNonStrongBiometricIdleTimeout: schedule an alarm for currentUser=", currentUser, "SafeUIKeyguardViewMediator");
            this.mLockPatternUtils.scheduleNonStrongBiometricIdleTimeout(currentUser);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUserPresentBroadcast() {
        synchronized (this) {
            if (this.mBootCompleted) {
                final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                final UserHandle userHandle = new UserHandle(currentUser);
                final UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                        UserManager userManager2 = userManager;
                        UserHandle userHandle2 = userHandle;
                        int i = currentUser;
                        safeUIKeyguardViewMediator.getClass();
                        for (int i2 : userManager2.getProfileIdsWithDisabled(userHandle2.getIdentifier())) {
                            safeUIKeyguardViewMediator.mContext.sendBroadcastAsUser(SafeUIKeyguardViewMediator.USER_PRESENT_INTENT, UserHandle.of(i2), null, SafeUIKeyguardViewMediator.USER_PRESENT_INTENT_OPTIONS);
                        }
                        safeUIKeyguardViewMediator.mLockPatternUtils.userPresent(i);
                    }
                });
            } else {
                this.mBootSendUserPresent = true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUnlockAndWakeFromDream(int i, boolean z) {
        String str;
        if (z == this.mUnlockingAndWakingFromDream) {
            return;
        }
        if (i == 0) {
            str = "hiding keyguard";
        } else if (i == 1) {
            str = "showing keyguard";
        } else if (i == 2) {
            str = "fulfilling existing request";
        } else {
            if (i != 3) {
                throw new IllegalStateException(AbstractC0000x2c234b15.m0m("Unexpected value: ", i));
            }
            str = "waking to unlock";
        }
        boolean z2 = (z || i == 2) ? false : true;
        this.mUnlockingAndWakingFromDream = z;
        android.util.Log.d("SafeUIKeyguardViewMediator", String.format("Updating waking and unlocking request to %b. description:[%s]. reason:[%s]", Boolean.valueOf(z), z2 ? "Interrupting request to wake and unlock" : z ? "Initiating request to wake and unlock" : "Fulfilling request to wake and unlock", str));
    }

    private void setupLocked() {
        boolean z;
        PowerManager powerManager = this.mPM;
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "show keyguard");
        this.mShowKeyguardWakeLock = newWakeLock;
        boolean z2 = false;
        newWakeLock.setReferenceCounted(false);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        this.mBroadcastDispatcher.registerReceiver(intentFilter, this.mBroadcastReceiver);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        intentFilter2.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
        intentFilter2.setPriority(1000);
        this.mContext.registerReceiver(this.mDelayedLockBroadcastReceiver, intentFilter2, "com.android.systemui.permission.SELF", null, 2);
        Context context = this.mContext;
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        UserTracker userTracker = this.mUserTracker;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        int userId = userTrackerImpl.getUserId();
        int i = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
        synchronized (KeyguardUpdateMonitor.class) {
            KeyguardUpdateMonitor.sCurrentUser = userId;
        }
        try {
            z = context.getPackageManager().getServiceInfo(new ComponentName(context, (Class<?>) KeyguardService.class), 0).isEnabled();
        } catch (PackageManager.NameNotFoundException unused) {
            z = true;
        }
        if (z) {
            if (!shouldWaitForProvisioning()) {
                int userId2 = userTrackerImpl.getUserId();
                LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
                if ((lockPatternUtils.isSecure(userId2) || lockPatternUtils.isLockScreenDisabled(userTrackerImpl.getUserId())) && !lockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser())) {
                    z2 = true;
                }
            }
            setShowingLocked(z2, true);
        } else {
            setShowingLocked(false, true);
        }
        if (this.mWallpaperManager == null) {
            this.mWallpaperManager = (WallpaperManager) context.getSystemService(WallpaperManager.class);
        }
        boolean isLockscreenLiveWallpaperEnabled = this.mWallpaperManager.isLockscreenLiveWallpaperEnabled();
        IRemoteAnimationRunner exitAnimationRunner = getExitAnimationRunner();
        int i2 = KeyguardService.$r8$clinit;
        this.mKeyguardTransitions.register(new KeyguardService.C14691(this, exitAnimationRunner, isLockscreenLiveWallpaperEnabled), new KeyguardService.C14691(this, getOccludeAnimationRunner(), isLockscreenLiveWallpaperEnabled), new KeyguardService.C14691(this, getOccludeByDreamAnimationRunner(), isLockscreenLiveWallpaperEnabled), new KeyguardService.C14691(this, getUnoccludeAnimationRunner(), isLockscreenLiveWallpaperEnabled));
        ContentResolver contentResolver = context.getContentResolver();
        this.mDeviceInteractive = powerManager.isInteractive();
        this.mLockSounds = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(13).setContentType(4).build()).build();
        String string = Settings.Global.getString(contentResolver, "lock_sound");
        if (string != null) {
            this.mLockSoundId = this.mLockSounds.load(string, 1);
        }
        if (string == null || this.mLockSoundId == 0) {
            MotionLayout$$ExternalSyntheticOutline0.m23m("failed to load lock sound from ", string, "SafeUIKeyguardViewMediator");
        }
        String string2 = Settings.Global.getString(contentResolver, "unlock_sound");
        if (string2 != null) {
            this.mUnlockSoundId = this.mLockSounds.load(string2, 1);
        }
        if (string2 == null || this.mUnlockSoundId == 0) {
            MotionLayout$$ExternalSyntheticOutline0.m23m("failed to load unlock sound from ", string2, "SafeUIKeyguardViewMediator");
        }
        String string3 = Settings.Global.getString(contentResolver, "trusted_sound");
        if (string3 != null) {
            this.mTrustedSoundId = this.mLockSounds.load(string3, 1);
        }
        if (string3 == null || this.mTrustedSoundId == 0) {
            MotionLayout$$ExternalSyntheticOutline0.m23m("failed to load trusted sound from ", string3, "SafeUIKeyguardViewMediator");
        }
        this.mLockSoundVolume = (float) Math.pow(10.0d, context.getResources().getInteger(android.R.integer.config_maxShortcutTargetsPerApp) / 20.0f);
        this.mHideAnimation = AnimationUtils.loadAnimation(context, android.R.anim.screen_rotate_0_exit);
        new WorkLockActivityController(context, userTracker);
        ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).prepareSafeUIBouncer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldWaitForProvisioning() {
        return (this.mUpdateMonitor.mDeviceProvisioned || isSecure()) ? false : true;
    }

    private void showLocked(Bundle bundle) {
        Trace.beginSection("KeyguardViewMediator#showLocked acquiring mShowKeyguardWakeLock");
        android.util.Log.d("SafeUIKeyguardViewMediator", "showLocked");
        this.mShowKeyguardWakeLock.acquire();
        HandlerC150712 handlerC150712 = this.mHandler;
        handlerC150712.sendMessageAtFrontOfQueue(handlerC150712.obtainMessage(1, bundle));
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryKeyguardDone() {
        StringBuilder sb = new StringBuilder("tryKeyguardDone: pending - ");
        sb.append(this.mKeyguardDonePending);
        sb.append(", animRan - ");
        sb.append(this.mHideAnimationRun);
        sb.append(" animRunning - ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mHideAnimationRunning, "SafeUIKeyguardViewMediator");
        if (!this.mKeyguardDonePending && this.mHideAnimationRun && !this.mHideAnimationRunning) {
            handleKeyguardDone();
        } else {
            if (this.mHideAnimationRun) {
                return;
            }
            android.util.Log.d("SafeUIKeyguardViewMediator", "tryKeyguardDone: starting pre-hide animation");
            this.mHideAnimationRun = true;
            this.mHideAnimationRunning = true;
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).startPreHideAnimation(this.mHideAnimationFinishedRunnable);
        }
    }

    private void updateInputRestrictedLocked() {
        boolean isInputRestricted = isInputRestricted();
        if (this.mInputRestricted != isInputRestricted) {
            this.mInputRestricted = isInputRestricted;
            ArrayList arrayList = this.mKeyguardStateCallbacks;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                IKeyguardStateCallback iKeyguardStateCallback = (IKeyguardStateCallback) arrayList.get(size);
                try {
                    iKeyguardStateCallback.onInputRestrictedStateChanged(isInputRestricted);
                } catch (RemoteException e) {
                    android.util.Slog.w("SafeUIKeyguardViewMediator", "Failed to call onDeviceProvisioned", e);
                    if (e instanceof DeadObjectException) {
                        arrayList.remove(iKeyguardStateCallback);
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback) {
        synchronized (this) {
            this.mKeyguardStateCallbacks.add(iKeyguardStateCallback);
            try {
                iKeyguardStateCallback.onSimSecureStateChanged(this.mUpdateMonitor.isSimPinSecure());
                iKeyguardStateCallback.onShowingStateChanged(this.mShowing, KeyguardUpdateMonitor.getCurrentUser());
                iKeyguardStateCallback.onInputRestrictedStateChanged(this.mInputRestricted);
                iKeyguardStateCallback.onTrustedChanged(this.mUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser()));
            } catch (RemoteException e) {
                android.util.Slog.w("SafeUIKeyguardViewMediator", "Failed to call to IKeyguardStateCallback", e);
            }
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void cancelKeyguardExitAnimation() {
        Trace.beginSection("KeyguardViewMediator#cancelKeyguardExitAnimation");
        HandlerC150712 handlerC150712 = this.mHandler;
        handlerC150712.sendMessage(handlerC150712.obtainMessage(19));
        Trace.endSection();
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        this.mHandler.obtainMessage(11, new DismissMessage(iKeyguardDismissCallback, charSequence)).sendToTarget();
    }

    public final void doKeyguardForChildProfilesLocked() {
        for (UserInfo userInfo : ((UserTrackerImpl) this.mUserTracker).getUserProfiles()) {
            if (userInfo.isEnabled()) {
                int i = userInfo.id;
                if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
                    this.mTrustManager.setDeviceLockedForUser(i, true);
                }
            }
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void doKeyguardTimeout(Bundle bundle) {
        HandlerC150712 handlerC150712 = this.mHandler;
        handlerC150712.removeMessages(10);
        handlerC150712.sendMessageAtFrontOfQueue(handlerC150712.obtainMessage(10, bundle));
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator, com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("  mSystemReady: ");
        printWriter.println(this.mSystemReady);
        printWriter.print("  mBootCompleted: ");
        printWriter.println(this.mBootCompleted);
        printWriter.print("  mBootSendUserPresent: ");
        printWriter.println(this.mBootSendUserPresent);
        printWriter.print("  mExternallyEnabled: ");
        printWriter.println(this.mExternallyEnabled);
        printWriter.print("  mShuttingDown: ");
        printWriter.println(this.mShuttingDown);
        printWriter.print("  mNeedToReshowWhenReenabled: ");
        printWriter.println(this.mNeedToReshowWhenReenabled);
        printWriter.print("  mShowing: ");
        printWriter.println(this.mShowing);
        printWriter.print("  mInputRestricted: ");
        printWriter.println(this.mInputRestricted);
        printWriter.print("  mOccluded: ");
        printWriter.println(this.mOccluded);
        printWriter.print("  mDelayedShowingSequence: ");
        printWriter.println(this.mDelayedShowingSequence);
        printWriter.print("  mDeviceInteractive: ");
        printWriter.println(this.mDeviceInteractive);
        printWriter.print("  mGoingToSleep: ");
        printWriter.println(this.mGoingToSleep);
        printWriter.print("  mHiding: ");
        printWriter.println(this.mHiding);
        printWriter.print("  mDozing: ");
        printWriter.println(this.mDozing);
        printWriter.print("  mAodShowing: ");
        printWriter.println(this.mAodShowing);
        printWriter.print("  mWaitingUntilKeyguardVisible: ");
        printWriter.println(this.mWaitingUntilKeyguardVisible);
        printWriter.print("  mKeyguardDonePending: ");
        printWriter.println(this.mKeyguardDonePending);
        printWriter.print("  mHideAnimationRun: ");
        printWriter.println(this.mHideAnimationRun);
        printWriter.print("  mPendingReset: ");
        printWriter.println(this.mPendingReset);
        printWriter.print("  mPendingLock: ");
        printWriter.println(this.mPendingLock);
        printWriter.print("  wakeAndUnlocking: ");
        printWriter.println(this.mWakeAndUnlocking);
        printWriter.print("  mPendingPinLock: ");
        printWriter.println(this.mPendingPinLock);
        printWriter.print("  mPowerGestureIntercepted: ");
        printWriter.println(this.mPowerGestureIntercepted);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void exitKeyguardAndFinishSurfaceBehindRemoteAnimation(final boolean z) {
        android.util.Log.d("SafeUIKeyguardViewMediator", "onKeyguardExitRemoteAnimationFinished");
        if (!this.mSurfaceBehindRemoteAnimationRequested) {
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(RowView$$ExternalSyntheticOutline0.m49m("skip onKeyguardExitRemoteAnimationFinished cancelled=", z, " surfaceAnimationRunning=false surfaceAnimationRequested="), this.mSurfaceBehindRemoteAnimationRequested, "SafeUIKeyguardViewMediator");
            return;
        }
        ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).blockPanelExpansionFromCurrentTouch();
        final boolean z2 = this.mShowing;
        InteractionJankMonitor.getInstance().end(29);
        DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                SafeUIKeyguardViewMediator.m1553$r8$lambda$qsihrj14nio_BWcX8GQhIrIZk(SafeUIKeyguardViewMediator.this, z2, z);
            }
        });
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void finishSurfaceBehindRemoteAnimation() {
        this.mSurfaceBehindRemoteAnimationRequested = false;
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(false);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final IRemoteAnimationRunner getExitAnimationRunner() {
        return new C150916(this.mExitAnimationRunner);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final IRemoteAnimationRunner getOccludeAnimationRunner() {
        return new C150916(this.mOccludeAnimationRunner);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final IRemoteAnimationRunner getOccludeByDreamAnimationRunner() {
        return new C150916(this.mOccludeByDreamAnimationRunner);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final IRemoteAnimationRunner getUnoccludeAnimationRunner() {
        return new C150916(this.mUnoccludeAnimationRunner);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final ViewMediatorCallback getViewMediatorCallback() {
        return this.mViewMediatorCallback;
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void hideSurfaceBehindKeyguard() {
        this.mSurfaceBehindRemoteAnimationRequested = false;
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(false);
        if (this.mShowing) {
            setShowingLocked(true, true);
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void hideWithAnimation(IRemoteAnimationRunner iRemoteAnimationRunner) {
        if (this.mKeyguardDonePending) {
            this.mViewMediatorCallback.readyForKeyguardDone();
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final boolean isAnimatingBetweenKeyguardAndSurfaceBehind() {
        return false;
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final boolean isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe() {
        return ((KeyguardStateControllerImpl) this.mKeyguardStateController).mFlingingToDismissKeyguard;
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final boolean isAnySimPinSecure() {
        int i = 0;
        while (true) {
            SparseIntArray sparseIntArray = this.mLastSimStates;
            if (i >= sparseIntArray.size()) {
                return false;
            }
            if (KeyguardUpdateMonitor.isSimPinSecure(sparseIntArray.get(sparseIntArray.keyAt(i)))) {
                return true;
            }
            i++;
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final boolean isHiding() {
        return this.mHiding;
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final boolean isInputRestricted() {
        return this.mShowing || this.mNeedToReshowWhenReenabled;
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final boolean isOccludeAnimationPlaying() {
        return this.mOccludeAnimationPlaying;
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final boolean isSecure() {
        return isSecure(KeyguardUpdateMonitor.getCurrentUser());
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final boolean isShowingAndNotOccluded() {
        return this.mShowing && !this.mOccluded;
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void maybeHandlePendingLock() {
        if (this.mPendingLock) {
            if (this.mScreenOffAnimationController.shouldDelayKeyguardShow()) {
                android.util.Log.d("SafeUIKeyguardViewMediator", "#maybeHandlePendingLock: not handling because the screen off animation's shouldDelayKeyguardShow() returned true. This should be handled soon by #onStartedWakingUp, or by the end actions of the screen off animation.");
            } else {
                if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway) {
                    android.util.Log.d("SafeUIKeyguardViewMediator", "#maybeHandlePendingLock: not handling because the keyguard is going away. This should be handled shortly by StatusBar#finishKeyguardFadingAway.");
                    return;
                }
                android.util.Log.d("SafeUIKeyguardViewMediator", "#maybeHandlePendingLock: handling pending lock; locking keyguard.");
                doKeyguardLocked(null);
                setPendingLock(false);
            }
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator, com.android.systemui.CoreStartable
    public final void onBootCompleted() {
        synchronized (this) {
            if (this.mContext.getResources().getBoolean(android.R.bool.config_enableWcgMode)) {
                ((GuestUserInteractor) this.mUserSwitcherController.guestUserInteractor$delegate.getValue()).onDeviceBootCompleted();
            }
            this.mBootCompleted = true;
            adjustStatusBarLocked(false, true);
            if (this.mBootSendUserPresent) {
                sendUserPresentBroadcast();
            }
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator, com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozeAmountChanged(float f, float f2) {
        if (this.mAnimatingScreenOff && this.mDozing && f == 1.0f) {
            this.mAnimatingScreenOff = false;
            setShowingLocked(this.mShowing, true);
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void onDreamingStarted() {
        this.mUpdateMonitor.dispatchDreamingStarted();
        synchronized (this) {
            boolean isEnabled = ((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.LOCKSCREEN_WITHOUT_SECURE_LOCK_WHEN_DREAMING);
            if (this.mDeviceInteractive && (isEnabled || this.mLockPatternUtils.isSecure(KeyguardUpdateMonitor.getCurrentUser()))) {
                long lockTimeout = getLockTimeout(KeyguardUpdateMonitor.getCurrentUser());
                if (lockTimeout == 0) {
                    doKeyguardLocked(null);
                } else {
                    doKeyguardLaterLocked(lockTimeout);
                }
            }
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void onDreamingStopped() {
        this.mUpdateMonitor.dispatchDreamingStopped();
        synchronized (this) {
            if (this.mDeviceInteractive) {
                this.mDelayedShowingSequence++;
            }
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void onFinishedGoingToSleep(int i, boolean z) {
        AbstractC0147x487e7be7.m26m("onFinishedGoingToSleep(", i, ")", "SafeUIKeyguardViewMediator");
        synchronized (this) {
            this.mDeviceInteractive = false;
            this.mGoingToSleep = false;
            this.mWakeAndUnlocking = false;
            this.mAnimatingScreenOff = this.mDozeParameters.shouldAnimateDozingChange();
            resetKeyguardDonePendingLocked();
            this.mHideAnimationRun = false;
            android.util.Log.d("SafeUIKeyguardViewMediator", "notifyFinishedGoingToSleep");
            this.mHandler.sendEmptyMessage(5);
            if (z) {
                ((PowerManager) this.mContext.getSystemService(PowerManager.class)).wakeUp(SystemClock.uptimeMillis(), 5, "com.android.systemui:CAMERA_GESTURE_PREVENT_LOCK");
                setPendingLock(false);
                this.mPendingReset = false;
                this.mPowerGestureIntercepted = true;
                android.util.Log.d("SafeUIKeyguardViewMediator", "cameraGestureTriggered=" + z + ",mPowerGestureIntercepted=" + this.mPowerGestureIntercepted);
            }
            if (this.mPendingReset) {
                resetStateLocked(true);
                this.mPendingReset = false;
            }
            maybeHandlePendingLock();
            if (!this.mLockLater && !z) {
                doKeyguardForChildProfilesLocked();
            }
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        synchronized (keyguardUpdateMonitor) {
            keyguardUpdateMonitor.mDeviceInteractive = false;
        }
        KeyguardUpdateMonitor.HandlerC080015 handlerC080015 = keyguardUpdateMonitor.mHandler;
        handlerC080015.sendMessage(handlerC080015.obtainMessage(320, i, 0));
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void onScreenTurnedOff() {
        this.mUpdateMonitor.mHandler.sendEmptyMessage(CustomDeviceManager.DESTINATION_ADDRESS);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0067 A[Catch: all -> 0x0099, TryCatch #0 {, blocks: (B:5:0x000c, B:7:0x001f, B:11:0x002b, B:13:0x0039, B:15:0x0041, B:16:0x0063, B:18:0x0067, B:19:0x006c, B:28:0x0052, B:32:0x0058, B:34:0x0060), top: B:4:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0060 A[Catch: all -> 0x0099, TryCatch #0 {, blocks: (B:5:0x000c, B:7:0x001f, B:11:0x002b, B:13:0x0039, B:15:0x0041, B:16:0x0063, B:18:0x0067, B:19:0x006c, B:28:0x0052, B:32:0x0058, B:34:0x0060), top: B:4:0x000c }] */
    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onStartedGoingToSleep(int i) {
        boolean z;
        AbstractC0147x487e7be7.m26m("onStartedGoingToSleep(", i, ")", "SafeUIKeyguardViewMediator");
        synchronized (this) {
            this.mDeviceInteractive = false;
            this.mPowerGestureIntercepted = false;
            this.mGoingToSleep = true;
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            if (!this.mLockPatternUtils.getPowerButtonInstantlyLocks(currentUser) && this.mLockPatternUtils.isSecure(currentUser)) {
                z = false;
                long lockTimeout = getLockTimeout(KeyguardUpdateMonitor.getCurrentUser());
                this.mLockLater = false;
                if (!this.mShowing && !((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway) {
                    this.mPendingReset = true;
                } else if ((i != 3 && lockTimeout > 0) || (i == 2 && !z)) {
                    doKeyguardLaterLocked(lockTimeout);
                    this.mLockLater = true;
                } else if (!this.mLockPatternUtils.isLockScreenDisabled(currentUser)) {
                    setPendingLock(true);
                }
                if (this.mPendingLock) {
                    playSound(this.mLockSoundId);
                }
            }
            z = true;
            long lockTimeout2 = getLockTimeout(KeyguardUpdateMonitor.getCurrentUser());
            this.mLockLater = false;
            if (!this.mShowing) {
            }
            if (i != 3) {
            }
            if (!this.mLockPatternUtils.isLockScreenDisabled(currentUser)) {
            }
            if (this.mPendingLock) {
            }
        }
        KeyguardUpdateMonitor.HandlerC080015 handlerC080015 = this.mUpdateMonitor.mHandler;
        handlerC080015.sendMessage(handlerC080015.obtainMessage(321, i, 0));
        KeyguardUpdateMonitor.HandlerC080015 handlerC0800152 = this.mUpdateMonitor.mHandler;
        handlerC0800152.sendMessage(handlerC0800152.obtainMessage(342, Boolean.FALSE));
        android.util.Log.d("SafeUIKeyguardViewMediator", "notifyStartedGoingToSleep");
        this.mHandler.sendEmptyMessage(17);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void onStartedWakingUp(int i, boolean z) {
        Trace.beginSection("KeyguardViewMediator#onStartedWakingUp");
        synchronized (this) {
            this.mDeviceInteractive = true;
            if (this.mPendingLock && !z && !this.mWakeAndUnlocking) {
                doKeyguardLocked(null);
            }
            this.mAnimatingScreenOff = false;
            this.mDelayedShowingSequence++;
            this.mDelayedProfileShowingSequence++;
            if (z) {
                this.mPowerGestureIntercepted = true;
            }
            android.util.Log.d("SafeUIKeyguardViewMediator", "onStartedWakingUp, seq = " + this.mDelayedShowingSequence + ", mPowerGestureIntercepted = " + this.mPowerGestureIntercepted);
            android.util.Log.d("SafeUIKeyguardViewMediator", "notifyStartedWakingUp");
            this.mHandler.sendEmptyMessage(14);
        }
        this.mUiEventLogger.logWithInstanceIdAndPosition(BiometricUnlockController.BiometricUiEvent.STARTED_WAKING_UP, 0, (String) null, this.mSessionTracker.getSessionId(1), i);
        this.mUpdateMonitor.dispatchStartedWakingUp(i);
        maybeSendUserPresentBroadcast();
        Trace.endSection();
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void onSystemReady() {
        this.mHandler.obtainMessage(18).sendToTarget();
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void onWakeAndUnlocking(boolean z) {
        Trace.beginSection("KeyguardViewMediator#onWakeAndUnlocking");
        this.mWakeAndUnlocking = true;
        setUnlockAndWakeFromDream(3, z);
        ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).notifyKeyguardAuthenticated(false);
        userActivity();
        Trace.endSection();
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final boolean requestedShowSurfaceBehindKeyguard() {
        return this.mSurfaceBehindRemoteAnimationRequested;
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void setBlursDisabledForAppLaunch(boolean z) {
        ((NotificationShadeDepthController) this.mNotificationShadeDepthController.get()).setBlursDisabledForAppLaunch(z);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void setCurrentUser(int i) {
        int i2 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
        synchronized (KeyguardUpdateMonitor.class) {
            KeyguardUpdateMonitor.sCurrentUser = i;
        }
        synchronized (this) {
            notifyTrustedChangedLocked(this.mUpdateMonitor.getUserHasTrust(i));
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void setDozing(boolean z) {
        if (z == this.mDozing) {
            return;
        }
        this.mDozing = z;
        if (!z) {
            this.mAnimatingScreenOff = false;
        }
        if (!this.mShowing && this.mPendingLock && this.mDozeParameters.canControlUnlockedScreenOff()) {
            return;
        }
        setShowingLocked(this.mShowing, false);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void setKeyguardEnabled(boolean z) {
        synchronized (this) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "setKeyguardEnabled(" + z + ")");
            this.mExternallyEnabled = z;
            if (z || !this.mShowing) {
                if (z && this.mNeedToReshowWhenReenabled) {
                    android.util.Log.d("SafeUIKeyguardViewMediator", "previously hidden, reshowing, reenabling status bar expansion");
                    this.mNeedToReshowWhenReenabled = false;
                    updateInputRestrictedLocked();
                    showLocked(null);
                    this.mWaitingUntilKeyguardVisible = true;
                    this.mHandler.sendEmptyMessageDelayed(8, 2000L);
                    android.util.Log.d("SafeUIKeyguardViewMediator", "waiting until mWaitingUntilKeyguardVisible is false");
                    while (this.mWaitingUntilKeyguardVisible) {
                        try {
                            wait();
                        } catch (InterruptedException unused) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    android.util.Log.d("SafeUIKeyguardViewMediator", "done waiting for mWaitingUntilKeyguardVisible");
                }
            } else {
                if (this.mLockPatternUtils.isUserInLockdown(KeyguardUpdateMonitor.getCurrentUser())) {
                    android.util.Log.d("SafeUIKeyguardViewMediator", "keyguardEnabled(false) overridden by user lockdown");
                    return;
                }
                android.util.Log.d("SafeUIKeyguardViewMediator", "remembering to reshow, hiding keyguard, disabling status bar expansion");
                this.mNeedToReshowWhenReenabled = true;
                updateInputRestrictedLocked();
                Trace.beginSection("KeyguardViewMediator#hideLocked");
                android.util.Log.d("SafeUIKeyguardViewMediator", "hideLocked");
                HandlerC150712 handlerC150712 = this.mHandler;
                handlerC150712.sendMessage(handlerC150712.obtainMessage(2));
                Trace.endSection();
            }
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void setOccluded(boolean z, boolean z2) {
        android.util.Log.d("SafeUIKeyguardViewMediator", "setOccluded(" + z + ")");
        Trace.beginSection("KeyguardViewMediator#setOccluded");
        android.util.Log.d("SafeUIKeyguardViewMediator", "setOccluded " + z);
        HandlerC150712 handlerC150712 = this.mHandler;
        handlerC150712.removeMessages(9);
        handlerC150712.sendMessage(handlerC150712.obtainMessage(9, z ? 1 : 0, z2 ? 1 : 0));
        Trace.endSection();
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void setPendingLock(boolean z) {
        this.mPendingLock = z;
        Trace.traceCounter(4096L, "pendingLock", z ? 1 : 0);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void setShowingLocked(boolean z) {
        setShowingLocked(z, false);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void setSwitchingUser(boolean z) {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        keyguardUpdateMonitor.mSwitchingUser = z;
        keyguardUpdateMonitor.mHandler.post(new KeyguardUpdateMonitor$$ExternalSyntheticLambda3(keyguardUpdateMonitor, 7));
        keyguardUpdateMonitor.dispatchSecureState(4094);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void setWallpaperSupportsAmbientMode(boolean z) {
        this.mWallpaperSupportsAmbientMode = z;
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void showSurfaceBehindKeyguard() {
        this.mSurfaceBehindRemoteAnimationRequested = true;
        try {
            KeyguardUnlockAnimationController.Companion.getClass();
            ActivityTaskManager.getService().keyguardGoingAway(6);
            ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(true);
        } catch (RemoteException e) {
            this.mSurfaceBehindRemoteAnimationRequested = false;
            e.printStackTrace();
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator, com.android.systemui.CoreStartable
    public final void start() {
        synchronized (this) {
            setupLocked();
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void startKeyguardExitAnimation(long j, long j2) {
        startKeyguardExitAnimation(0, j, j2, null, null, null, null);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void userActivity() {
        this.mPM.userActivity(SystemClock.uptimeMillis(), false);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void verifyUnlock(IKeyguardExitCallback iKeyguardExitCallback) {
        Trace.beginSection("KeyguardViewMediator#verifyUnlock");
        synchronized (this) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "verifyUnlock");
            if (shouldWaitForProvisioning()) {
                android.util.Log.d("SafeUIKeyguardViewMediator", "ignoring because device isn't provisioned");
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e) {
                    android.util.Slog.w("SafeUIKeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e);
                }
            } else if (this.mExternallyEnabled) {
                android.util.Log.w("SafeUIKeyguardViewMediator", "verifyUnlock called when not externally disabled");
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e2) {
                    android.util.Slog.w("SafeUIKeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e2);
                }
            } else if (isSecure()) {
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e3) {
                    android.util.Slog.w("SafeUIKeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e3);
                }
            } else {
                this.mExternallyEnabled = true;
                this.mNeedToReshowWhenReenabled = false;
                synchronized (this) {
                    updateInputRestrictedLocked();
                    try {
                        iKeyguardExitCallback.onKeyguardExitResult(true);
                    } catch (RemoteException e4) {
                        android.util.Slog.w("SafeUIKeyguardViewMediator", "Failed to call onKeyguardExitResult(true)", e4);
                    }
                }
            }
        }
        Trace.endSection();
    }

    private void setShowingLocked(final boolean z, boolean z2) {
        boolean z3 = true;
        int i = 0;
        final boolean z4 = this.mDozing && !this.mWakeAndUnlocking;
        boolean z5 = this.mShowing;
        boolean z6 = z != z5 || z2;
        if (z == z5 && z4 == this.mAodShowing && !z2) {
            z3 = false;
        }
        this.mShowing = z;
        this.mAodShowing = z4;
        Executor executor = this.mUiBgExecutor;
        if (z6) {
            DejankUtils.whitelistIpcs(new Runnable() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                    boolean z7 = z;
                    ArrayList arrayList = safeUIKeyguardViewMediator.mKeyguardStateCallbacks;
                    int size = arrayList.size();
                    while (true) {
                        size--;
                        if (size < 0) {
                            return;
                        }
                        IKeyguardStateCallback iKeyguardStateCallback = (IKeyguardStateCallback) arrayList.get(size);
                        try {
                            iKeyguardStateCallback.onShowingStateChanged(z7, KeyguardUpdateMonitor.getCurrentUser());
                        } catch (RemoteException e) {
                            android.util.Slog.w("SafeUIKeyguardViewMediator", "Failed to call onShowingStateChanged", e);
                            if (e instanceof DeadObjectException) {
                                arrayList.remove(iKeyguardStateCallback);
                            }
                        }
                    }
                }
            });
            updateInputRestrictedLocked();
            executor.execute(new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda1(this, i));
        }
        if (z3) {
            executor.execute(new Runnable() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    boolean z7 = z;
                    boolean z8 = z4;
                    android.util.Log.d("SafeUIKeyguardViewMediator", "updateActivityLockScreenState(" + z7 + ", " + z8 + ")");
                    try {
                        ActivityTaskManager.getService().setLockScreenShown(z7, z8);
                    } catch (RemoteException unused) {
                    }
                }
            });
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final boolean isSecure(int i) {
        return this.mLockPatternUtils.isSecure(i) || this.mUpdateMonitor.isSimPinSecure();
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void startKeyguardExitAnimation(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        startKeyguardExitAnimation(i, 0L, 0L, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
    }

    private void startKeyguardExitAnimation(int i, long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        Trace.beginSection("KeyguardViewMediator#startKeyguardExitAnimation");
        this.mInteractionJankMonitor.cancel(23);
        StartKeyguardExitAnimParams startKeyguardExitAnimParams = new StartKeyguardExitAnimParams(i, j, j2, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback, 0);
        HandlerC150712 handlerC150712 = this.mHandler;
        handlerC150712.sendMessage(handlerC150712.obtainMessage(12, startKeyguardExitAnimParams));
        Trace.endSection();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class ActivityLaunchRemoteAnimationRunner extends IRemoteAnimationRunner.Stub {
        public ActivityLaunchRemoteAnimationRunner(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, ActivityLaunchAnimator.Controller controller) {
        }

        public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            if (iRemoteAnimationFinishedCallback != null) {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            }
        }

        public void onAnimationCancelled() {
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void dismissKeyguardToLaunch() {
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void onShortPowerPressedGoHome() {
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void onSystemKeyPressed() {
    }
}
