package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.BroadcastOptions;
import android.app.IActivityTaskManager;
import android.app.PendingIntent;
import android.app.StatusBarManager;
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
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.EventLog;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.foldables.FoldGracePeriodProvider;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardExitCallback;
import com.android.internal.policy.IKeyguardStateCallback;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.systemui.DejankUtils;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.communal.ui.viewmodel.CommunalTransitionViewModel;
import com.android.systemui.deviceentry.shared.DeviceEntryUdfpsRefactor;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.ui.viewmodel.DreamViewModel;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.keyguard.KeyguardService;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.SafeUIKeyguardViewMediator;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.pluginlock.PluginLockInstancePolicy;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.domain.interactor.GuestUserInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SystemSettings;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.wallpapers.data.repository.WallpaperRepository;
import com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl;
import com.android.wm.shell.keyguard.KeyguardTransitions;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.sec.ims.presence.ServiceTuple;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SafeUIKeyguardViewMediator extends KeyguardViewMediator {
    public static final Intent USER_PRESENT_INTENT = new Intent("android.intent.action.USER_PRESENT").addFlags(606076928);
    public static final Bundle USER_PRESENT_INTENT_OPTIONS = BroadcastOptions.makeBasic().setDeferralPolicy(2).setDeliveryGroupPolicy(1).toBundle();
    public final IActivityTaskManager mActivityTaskManagerService;
    public AlarmManager mAlarmManager;
    public boolean mAnimatingScreenOff;
    public boolean mAodShowing;
    public AudioManager mAudioManager;
    public boolean mBootCompleted;
    public boolean mBootSendUserPresent;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final AnonymousClass11 mBroadcastReceiver;
    public CentralSurfaces mCentralSurfaces;
    public final Lazy mCommunalTransitionViewModel;
    public final Context mContext;
    public CharSequence mCustomMessage;
    public final AnonymousClass10 mDelayedLockBroadcastReceiver;
    public int mDelayedProfileShowingSequence;
    public int mDelayedShowingSequence;
    public boolean mDeviceInteractive;
    public final DismissCallbackRegistry mDismissCallbackRegistry;
    public final DozeParameters mDozeParameters;
    public boolean mDozing;
    public final int mDreamOpenAnimationDuration;
    public boolean mDreamOverlayShowing;
    public final DreamOverlayStateController.Callback mDreamOverlayStateCallback;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final Lazy mDreamViewModel;
    public final AnonymousClass6 mExitAnimationRunner;
    public boolean mExternallyEnabled;
    public final FalsingCollector mFalsingCollector;
    protected FoldGracePeriodProvider mFoldGracePeriodProvider;
    public boolean mGoingToSleep;
    public final AnonymousClass12 mHandler;
    public Animation mHideAnimation;
    public final SafeUIKeyguardViewMediator$$ExternalSyntheticLambda0 mHideAnimationFinishedRunnable;
    public boolean mHideAnimationRun;
    public boolean mHideAnimationRunning;
    public boolean mHiding;
    public boolean mInGestureNavigationMode;
    public boolean mInputRestricted;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final JavaAdapter mJavaAdapter;
    public final KeyguardDisplayManager mKeyguardDisplayManager;
    public boolean mKeyguardDonePending;
    public final AnonymousClass13 mKeyguardGoingAwayRunnable;
    public final KeyguardInteractor mKeyguardInteractor;
    public final ArrayList mKeyguardStateCallbacks;
    public final KeyguardStateController mKeyguardStateController;
    public final AnonymousClass9 mKeyguardStateControllerCallback;
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
    public boolean mNeedToReshowWhenReenabled;
    public final Lazy mNotificationShadeDepthController;
    public final Lazy mNotificationShadeWindowControllerLazy;
    final ActivityTransitionAnimator.Controller mOccludeAnimationController;
    public boolean mOccludeAnimationPlaying;
    public final OccludeActivityLaunchRemoteAnimationRunner mOccludeAnimationRunner;
    public final AnonymousClass7 mOccludeByDreamAnimationRunner;
    public boolean mOccluded;
    public final AnonymousClass1 mOnPropertiesChangedListener;
    public final boolean mOrderUnlockAndWake;
    public final PowerManager mPM;
    public boolean mPendingLock;
    public boolean mPendingPinLock;
    public boolean mPendingReset;
    public final String mPhoneState;
    public final float mPowerButtonY;
    public boolean mPowerGestureIntercepted;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final Lazy mScrimControllerLazy;
    public final SecureSettings mSecureSettings;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final SessionTracker mSessionTracker;
    public final Lazy mShadeController;
    public boolean mShowHomeOverLockscreen;
    public final PowerManager.WakeLock mShowKeyguardWakeLock;
    public boolean mShowing;
    public boolean mShuttingDown;
    public final SparseBooleanArray mSimWasLocked;
    public final IBinder mStatusBarDisableToken;
    public StatusBarManager mStatusBarManager;
    public final IStatusBarService mStatusBarService;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public boolean mSurfaceBehindRemoteAnimationRequested;
    public final SystemClock mSystemClock;
    public final SystemPropertiesHelper mSystemPropertiesHelper;
    public boolean mSystemReady;
    public final SystemSettings mSystemSettings;
    public final TrustManager mTrustManager;
    public int mTrustedSoundId;
    public final Executor mUiBgExecutor;
    public final UiEventLogger mUiEventLogger;
    public int mUiSoundsStreamType;
    public int mUnlockSoundId;
    public boolean mUnlockingAndWakingFromDream;
    public final AnonymousClass8 mUnoccludeAnimationRunner;
    public final KeyguardUpdateMonitorCallback mUpdateCallback;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final UserSwitcherController mUserSwitcherController;
    public final UserTracker mUserTracker;
    public final AnonymousClass4 mViewMediatorCallback;
    public boolean mWaitingUntilKeyguardVisible;
    public boolean mWakeAndUnlocking;
    public final WallpaperRepository mWallpaperRepository;
    public boolean mWallpaperSupportsAmbientMode;
    public final float mWindowCornerRadius;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$12, reason: invalid class name */
    public final class AnonymousClass12 extends Handler {
        public AnonymousClass12(Looper looper, Handler.Callback callback, boolean z) {
            super(looper, callback, z);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator;
            String str = "";
            switch (message.what) {
                case 1:
                    str = "SHOW";
                    SafeUIKeyguardViewMediator.m1959$$Nest$mhandleShow(SafeUIKeyguardViewMediator.this, (Bundle) message.obj);
                    break;
                case 2:
                    str = "HIDE";
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator2 = SafeUIKeyguardViewMediator.this;
                    Intent intent = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
                    safeUIKeyguardViewMediator2.handleHide();
                    break;
                case 3:
                    str = "RESET";
                    SafeUIKeyguardViewMediator.m1957$$Nest$mhandleReset(SafeUIKeyguardViewMediator.this, message.arg1 != 0);
                    break;
                case 5:
                    str = "NOTIFY_FINISHED_GOING_TO_SLEEP";
                    SafeUIKeyguardViewMediator.m1955$$Nest$mhandleNotifyFinishedGoingToSleep(SafeUIKeyguardViewMediator.this);
                    break;
                case 7:
                    str = "KEYGUARD_DONE";
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE");
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator3 = SafeUIKeyguardViewMediator.this;
                    Intent intent2 = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
                    safeUIKeyguardViewMediator3.handleKeyguardDone();
                    Trace.endSection();
                    break;
                case 8:
                    str = "KEYGUARD_DONE_DRAWING";
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_DRAWING");
                    SafeUIKeyguardViewMediator.m1954$$Nest$mhandleKeyguardDoneDrawing(SafeUIKeyguardViewMediator.this);
                    Trace.endSection();
                    break;
                case 9:
                    str = "SET_OCCLUDED";
                    Trace.beginSection("KeyguardViewMediator#handleMessage SET_OCCLUDED");
                    SafeUIKeyguardViewMediator.m1958$$Nest$mhandleSetOccluded(SafeUIKeyguardViewMediator.this, message.arg1 != 0, message.arg2 != 0);
                    Trace.endSection();
                    break;
                case 10:
                    str = "KEYGUARD_TIMEOUT";
                    synchronized (SafeUIKeyguardViewMediator.this) {
                        SafeUIKeyguardViewMediator safeUIKeyguardViewMediator4 = SafeUIKeyguardViewMediator.this;
                        Bundle bundle = (Bundle) message.obj;
                        Intent intent3 = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
                        safeUIKeyguardViewMediator4.doKeyguardLocked(bundle);
                    }
                    break;
                case 11:
                    str = "DISMISS";
                    DismissMessage dismissMessage = (DismissMessage) message.obj;
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator5 = SafeUIKeyguardViewMediator.this;
                    IKeyguardDismissCallback iKeyguardDismissCallback = dismissMessage.mCallback;
                    CharSequence charSequence = dismissMessage.mMessage;
                    if (!safeUIKeyguardViewMediator5.mShowing) {
                        if (iKeyguardDismissCallback != null) {
                            new DismissCallbackWrapper(iKeyguardDismissCallback).notifyDismissError();
                            break;
                        }
                    } else {
                        if (iKeyguardDismissCallback != null) {
                            safeUIKeyguardViewMediator5.mDismissCallbackRegistry.mDismissCallbacks.add(new DismissCallbackWrapper(iKeyguardDismissCallback));
                        }
                        safeUIKeyguardViewMediator5.mCustomMessage = charSequence;
                        ((KeyguardViewController) safeUIKeyguardViewMediator5.mKeyguardViewControllerLazy.get()).dismissAndCollapse();
                        break;
                    }
                    break;
                case 12:
                    str = "START_KEYGUARD_EXIT_ANIM";
                    Trace.beginSection("KeyguardViewMediator#handleMessage START_KEYGUARD_EXIT_ANIM");
                    synchronized (SafeUIKeyguardViewMediator.this) {
                        safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                        safeUIKeyguardViewMediator.mHiding = true;
                    }
                    final StartKeyguardExitAnimParams startKeyguardExitAnimParams = (StartKeyguardExitAnimParams) message.obj;
                    ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) safeUIKeyguardViewMediator.mNotificationShadeWindowControllerLazy.get())).batchApplyWindowLayoutParams(new Runnable() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$12$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SafeUIKeyguardViewMediator.AnonymousClass12 anonymousClass12 = SafeUIKeyguardViewMediator.AnonymousClass12.this;
                            SafeUIKeyguardViewMediator.StartKeyguardExitAnimParams startKeyguardExitAnimParams2 = startKeyguardExitAnimParams;
                            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator6 = SafeUIKeyguardViewMediator.this;
                            long j = startKeyguardExitAnimParams2.startTime;
                            long j2 = startKeyguardExitAnimParams2.fadeoutDuration;
                            RemoteAnimationTarget[] remoteAnimationTargetArr = startKeyguardExitAnimParams2.mApps;
                            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = startKeyguardExitAnimParams2.mFinishedCallback;
                            Intent intent4 = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
                            safeUIKeyguardViewMediator6.getClass();
                            Trace.beginSection("KeyguardViewMediator#handleStartKeyguardExitAnimation");
                            android.util.Log.d("SafeUIKeyguardViewMediator", "handleStartKeyguardExitAnimation startTime=" + j + " fadeoutDuration=" + j2);
                            synchronized (safeUIKeyguardViewMediator6) {
                                if (safeUIKeyguardViewMediator6.mHiding || safeUIKeyguardViewMediator6.mSurfaceBehindRemoteAnimationRequested || ((KeyguardStateControllerImpl) safeUIKeyguardViewMediator6.mKeyguardStateController).mFlingingToDismissKeyguardDuringSwipeGesture) {
                                    safeUIKeyguardViewMediator6.mHiding = false;
                                    LatencyTracker.getInstance(safeUIKeyguardViewMediator6.mContext).onActionEnd(11);
                                    if (iRemoteAnimationFinishedCallback != null) {
                                        try {
                                            iRemoteAnimationFinishedCallback.onAnimationFinished();
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    safeUIKeyguardViewMediator6.mInteractionJankMonitor.begin(safeUIKeyguardViewMediator6.createInteractionJankMonitorConf(29, "RemoteAnimationDisabled"));
                                    KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                                    Flags.keyguardWmStateRefactor();
                                    ((KeyguardViewController) safeUIKeyguardViewMediator6.mKeyguardViewControllerLazy.get()).hide(j, j2);
                                    safeUIKeyguardViewMediator6.mContext.getMainExecutor().execute(new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda0(safeUIKeyguardViewMediator6, iRemoteAnimationFinishedCallback, remoteAnimationTargetArr));
                                    safeUIKeyguardViewMediator6.onKeyguardExitFinished();
                                    Trace.endSection();
                                } else {
                                    if (iRemoteAnimationFinishedCallback != null) {
                                        KeyguardWmStateRefactor keyguardWmStateRefactor2 = KeyguardWmStateRefactor.INSTANCE;
                                        Flags.keyguardWmStateRefactor();
                                        try {
                                            iRemoteAnimationFinishedCallback.onAnimationFinished();
                                        } catch (RemoteException e2) {
                                            Slog.w("SafeUIKeyguardViewMediator", "Failed to call onAnimationFinished", e2);
                                        }
                                    }
                                    safeUIKeyguardViewMediator6.setShowingLocked(safeUIKeyguardViewMediator6.mShowing, true);
                                }
                            }
                            SafeUIKeyguardViewMediator.this.mFalsingCollector.onSuccessfulUnlock();
                        }
                    });
                    Trace.endSection();
                    break;
                case 13:
                    str = "KEYGUARD_DONE_PENDING_TIMEOUT";
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_PENDING_TIMEOUT");
                    android.util.Log.w("SafeUIKeyguardViewMediator", "Timeout while waiting for activity drawn!");
                    Trace.endSection();
                    break;
                case 14:
                    str = "NOTIFY_STARTED_WAKING_UP";
                    Trace.beginSection("KeyguardViewMediator#handleMessage NOTIFY_STARTED_WAKING_UP");
                    SafeUIKeyguardViewMediator.m1956$$Nest$mhandleNotifyStartedWakingUp(SafeUIKeyguardViewMediator.this);
                    Trace.endSection();
                    break;
                case 17:
                    str = "NOTIFY_STARTED_GOING_TO_SLEEP";
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator6 = SafeUIKeyguardViewMediator.this;
                    Intent intent4 = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
                    synchronized (safeUIKeyguardViewMediator6) {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "handleNotifyStartedGoingToSleep");
                        ((KeyguardViewController) safeUIKeyguardViewMediator6.mKeyguardViewControllerLazy.get()).onStartedGoingToSleep();
                    }
                    break;
                case 18:
                    str = "SYSTEM_READY";
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator7 = SafeUIKeyguardViewMediator.this;
                    Intent intent5 = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
                    synchronized (safeUIKeyguardViewMediator7) {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "onSystemReady");
                        safeUIKeyguardViewMediator7.mSystemReady = true;
                        safeUIKeyguardViewMediator7.doKeyguardLocked(null);
                        safeUIKeyguardViewMediator7.mUpdateMonitor.registerCallback(safeUIKeyguardViewMediator7.mUpdateCallback);
                        safeUIKeyguardViewMediator7.adjustStatusBarLocked(false, false);
                        safeUIKeyguardViewMediator7.mDreamOverlayStateController.addCallback(safeUIKeyguardViewMediator7.mDreamOverlayStateCallback);
                        DreamViewModel dreamViewModel = (DreamViewModel) safeUIKeyguardViewMediator7.mDreamViewModel.get();
                        CommunalTransitionViewModel communalTransitionViewModel = (CommunalTransitionViewModel) safeUIKeyguardViewMediator7.mCommunalTransitionViewModel.get();
                        safeUIKeyguardViewMediator7.mJavaAdapter.alwaysCollectFlow(dreamViewModel.dreamAlpha, new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda10(safeUIKeyguardViewMediator7, 2));
                        safeUIKeyguardViewMediator7.mJavaAdapter.alwaysCollectFlow(dreamViewModel.transitionEnded, new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda10(safeUIKeyguardViewMediator7, 3));
                        safeUIKeyguardViewMediator7.mJavaAdapter.alwaysCollectFlow(communalTransitionViewModel.showCommunalFromOccluded, new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda10(safeUIKeyguardViewMediator7, 1));
                        safeUIKeyguardViewMediator7.mJavaAdapter.alwaysCollectFlow(communalTransitionViewModel.transitionFromOccludedEnded, new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda10(safeUIKeyguardViewMediator7, 3));
                    }
                    safeUIKeyguardViewMediator7.maybeSendUserPresentBroadcast();
                    break;
                case 19:
                    str = "CANCEL_KEYGUARD_EXIT_ANIM";
                    Trace.beginSection("KeyguardViewMediator#handleMessage CANCEL_KEYGUARD_EXIT_ANIM");
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator8 = SafeUIKeyguardViewMediator.this;
                    if (safeUIKeyguardViewMediator8.mPendingLock) {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "#handleCancelKeyguardExitAnimation: keyguard exit animation cancelled. There's a pending lock, so we were cancelled because the device was locked again during the unlock sequence. We should end up locked.");
                        safeUIKeyguardViewMediator8.finishSurfaceBehindRemoteAnimation(true);
                        safeUIKeyguardViewMediator8.maybeHandlePendingLock();
                    } else {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "#handleCancelKeyguardExitAnimation: keyguard exit animation cancelled. No pending lock, we should end up unlocked with the app/launcher visible.");
                        safeUIKeyguardViewMediator8.showSurfaceBehindKeyguard();
                        safeUIKeyguardViewMediator8.exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
                    }
                    Trace.endSection();
                    break;
            }
            android.util.Log.d("SafeUIKeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$13, reason: invalid class name */
    public final class AnonymousClass13 implements Runnable {
        public AnonymousClass13() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x004b, code lost:
        
            if (r2.mWallpaperSupportsAmbientMode != false) goto L14;
         */
        /* JADX WARN: Removed duplicated region for block: B:14:0x005e  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x0073  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                r5 = this;
                r0 = 1
                r1 = 2
                java.lang.String r2 = "KeyguardViewMediator.mKeyGuardGoingAwayRunnable"
                android.os.Trace.beginSection(r2)
                java.lang.String r2 = "SafeUIKeyguardViewMediator"
                java.lang.String r3 = "keyguardGoingAway"
                android.util.Log.d(r2, r3)
                com.android.systemui.keyguard.SafeUIKeyguardViewMediator r2 = com.android.systemui.keyguard.SafeUIKeyguardViewMediator.this
                dagger.Lazy r2 = r2.mKeyguardViewControllerLazy
                java.lang.Object r2 = r2.get()
                com.android.keyguard.KeyguardViewController r2 = (com.android.keyguard.KeyguardViewController) r2
                r2.keyguardGoingAway()
                com.android.systemui.keyguard.SafeUIKeyguardViewMediator r2 = com.android.systemui.keyguard.SafeUIKeyguardViewMediator.this
                dagger.Lazy r2 = r2.mKeyguardViewControllerLazy
                java.lang.Object r2 = r2.get()
                com.android.keyguard.KeyguardViewController r2 = (com.android.keyguard.KeyguardViewController) r2
                r2.getClass()
                com.android.systemui.keyguard.SafeUIKeyguardViewMediator r2 = com.android.systemui.keyguard.SafeUIKeyguardViewMediator.this
                boolean r3 = r2.mWakeAndUnlocking
                if (r3 == 0) goto L34
                boolean r3 = r2.mWallpaperSupportsAmbientMode
                if (r3 != 0) goto L34
                r3 = r1
                goto L35
            L34:
                r3 = 0
            L35:
                dagger.Lazy r2 = r2.mKeyguardViewControllerLazy
                java.lang.Object r2 = r2.get()
                com.android.keyguard.KeyguardViewController r2 = (com.android.keyguard.KeyguardViewController) r2
                boolean r2 = r2.isGoingToNotificationShade()
                if (r2 != 0) goto L4d
                com.android.systemui.keyguard.SafeUIKeyguardViewMediator r2 = com.android.systemui.keyguard.SafeUIKeyguardViewMediator.this
                boolean r4 = r2.mWakeAndUnlocking
                if (r4 == 0) goto L4e
                boolean r2 = r2.mWallpaperSupportsAmbientMode
                if (r2 == 0) goto L4e
            L4d:
                r3 = r3 | r0
            L4e:
                com.android.systemui.keyguard.SafeUIKeyguardViewMediator r2 = com.android.systemui.keyguard.SafeUIKeyguardViewMediator.this
                dagger.Lazy r2 = r2.mKeyguardViewControllerLazy
                java.lang.Object r2 = r2.get()
                com.android.keyguard.KeyguardViewController r2 = (com.android.keyguard.KeyguardViewController) r2
                boolean r2 = r2.isUnlockWithWallpaper()
                if (r2 == 0) goto L60
                r3 = r3 | 4
            L60:
                com.android.systemui.keyguard.SafeUIKeyguardViewMediator r2 = com.android.systemui.keyguard.SafeUIKeyguardViewMediator.this
                dagger.Lazy r2 = r2.mKeyguardViewControllerLazy
                java.lang.Object r2 = r2.get()
                com.android.keyguard.KeyguardViewController r2 = (com.android.keyguard.KeyguardViewController) r2
                r2.shouldSubtleWindowAnimationsForUnlock()
                com.android.systemui.keyguard.SafeUIKeyguardViewMediator r2 = com.android.systemui.keyguard.SafeUIKeyguardViewMediator.this
                boolean r4 = r2.mWakeAndUnlocking
                if (r4 == 0) goto L7e
                dagger.Lazy r2 = r2.mKeyguardUnlockAnimationControllerLazy
                java.lang.Object r2 = r2.get()
                com.android.systemui.keyguard.KeyguardUnlockAnimationController r2 = (com.android.systemui.keyguard.KeyguardUnlockAnimationController) r2
                r2.getClass()
            L7e:
                com.android.systemui.keyguard.SafeUIKeyguardViewMediator r2 = com.android.systemui.keyguard.SafeUIKeyguardViewMediator.this
                com.android.keyguard.KeyguardUpdateMonitor r2 = r2.mUpdateMonitor
                r2.setKeyguardGoingAway(r0)
                com.android.systemui.keyguard.SafeUIKeyguardViewMediator r2 = com.android.systemui.keyguard.SafeUIKeyguardViewMediator.this
                dagger.Lazy r2 = r2.mKeyguardViewControllerLazy
                java.lang.Object r2 = r2.get()
                com.android.keyguard.KeyguardViewController r2 = (com.android.keyguard.KeyguardViewController) r2
                r2.setKeyguardGoingAwayState(r0)
                com.android.systemui.keyguard.KeyguardWmStateRefactor r0 = com.android.systemui.keyguard.KeyguardWmStateRefactor.INSTANCE
                com.android.systemui.Flags.keyguardWmStateRefactor()
                com.android.systemui.keyguard.SafeUIKeyguardViewMediator r0 = com.android.systemui.keyguard.SafeUIKeyguardViewMediator.this
                java.util.concurrent.Executor r0 = r0.mUiBgExecutor
                com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda8 r2 = new com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda8
                r2.<init>(r5, r3, r1)
                r0.execute(r2)
                android.os.Trace.endSection()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.AnonymousClass13.run():void");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$16, reason: invalid class name */
    public final class AnonymousClass16 extends IRemoteAnimationRunner.Stub {
        public final /* synthetic */ IRemoteAnimationRunner val$wrapped;

        public AnonymousClass16(IRemoteAnimationRunner iRemoteAnimationRunner) {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$3, reason: invalid class name */
    class AnonymousClass3 extends KeyguardUpdateMonitorCallback {
        public AnonymousClass3() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            int selectedUserId = safeUIKeyguardViewMediator.mSelectedUserInteractor.getSelectedUserId();
            if (safeUIKeyguardViewMediator.mLockPatternUtils.isSecure(selectedUserId)) {
                safeUIKeyguardViewMediator.mLockPatternUtils.getDevicePolicyManager().reportFailedBiometricAttempt(selectedUserId);
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
            Intent intent = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
            SafeUIKeyguardViewMediator.this.sendUserPresentBroadcast();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardVisibilityChanged(boolean z) {
            synchronized (SafeUIKeyguardViewMediator.this) {
                if (!z) {
                    try {
                        if (SafeUIKeyguardViewMediator.this.mPendingPinLock) {
                            android.util.Log.i("SafeUIKeyguardViewMediator", "PIN lock requested, starting keyguard");
                            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                            safeUIKeyguardViewMediator.mPendingPinLock = false;
                            safeUIKeyguardViewMediator.doKeyguardLocked(null);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSimStateChanged(int i, int i2, int i3) {
            boolean z;
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "onSimStateChanged(subId=", ", slotId=", ",state="), i3, ")", "SafeUIKeyguardViewMediator");
            int size = SafeUIKeyguardViewMediator.this.mKeyguardStateCallbacks.size();
            boolean isSimPinSecure = SafeUIKeyguardViewMediator.this.mUpdateMonitor.isSimPinSecure();
            for (int i4 = size - 1; i4 >= 0; i4--) {
                try {
                    ((IKeyguardStateCallback) SafeUIKeyguardViewMediator.this.mKeyguardStateCallbacks.get(i4)).onSimSecureStateChanged(isSimPinSecure);
                } catch (RemoteException e) {
                    Slog.w("SafeUIKeyguardViewMediator", "Failed to call onSimSecureStateChanged", e);
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
                        try {
                            SafeUIKeyguardViewMediator.this.mSimWasLocked.append(i2, true);
                            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                            safeUIKeyguardViewMediator.mPendingPinLock = true;
                            if (safeUIKeyguardViewMediator.mShowing) {
                                safeUIKeyguardViewMediator.resetStateLocked(true);
                            } else {
                                android.util.Log.d("SafeUIKeyguardViewMediator", "INTENT_VALUE_ICC_LOCKED and keygaurd isn't showing; need to show keyguard so user can enter sim pin");
                                SafeUIKeyguardViewMediator.this.doKeyguardLocked(null);
                            }
                        } finally {
                        }
                    }
                    return;
                }
                if (i3 == 5) {
                    synchronized (SafeUIKeyguardViewMediator.this) {
                        try {
                            android.util.Log.d("SafeUIKeyguardViewMediator", "READY, reset state? " + SafeUIKeyguardViewMediator.this.mShowing);
                            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator2 = SafeUIKeyguardViewMediator.this;
                            if (safeUIKeyguardViewMediator2.mShowing && safeUIKeyguardViewMediator2.mSimWasLocked.get(i2, false)) {
                                android.util.Log.d("SafeUIKeyguardViewMediator", "SIM moved to READY when the previously was locked. Reset the state.");
                                SafeUIKeyguardViewMediator.this.mSimWasLocked.append(i2, false);
                                SafeUIKeyguardViewMediator.this.resetStateLocked(true);
                            }
                        } finally {
                        }
                    }
                    return;
                }
                if (i3 != 6) {
                    if (i3 != 7) {
                        return;
                    }
                    synchronized (SafeUIKeyguardViewMediator.this) {
                        try {
                            if (SafeUIKeyguardViewMediator.this.mShowing) {
                                android.util.Log.d("SafeUIKeyguardViewMediator", "PERM_DISABLED, resetStateLocked toshow permanently disabled message in lockscreen.");
                                SafeUIKeyguardViewMediator.this.resetStateLocked(true);
                            } else {
                                android.util.Log.d("SafeUIKeyguardViewMediator", "PERM_DISABLED and keygaurd isn't showing.");
                                SafeUIKeyguardViewMediator.this.doKeyguardLocked(null);
                            }
                        } finally {
                        }
                    }
                    return;
                }
            }
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator3 = SafeUIKeyguardViewMediator.this;
            safeUIKeyguardViewMediator3.mPendingPinLock = false;
            synchronized (safeUIKeyguardViewMediator3) {
                try {
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
                } finally {
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStrongAuthStateChanged(int i) {
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            if (safeUIKeyguardViewMediator.mLockPatternUtils.isUserInLockdown(safeUIKeyguardViewMediator.mSelectedUserInteractor.getSelectedUserId())) {
                safeUIKeyguardViewMediator.doKeyguardLocked(null);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustChanged(int i) {
            if (i == SafeUIKeyguardViewMediator.this.mSelectedUserInteractor.getSelectedUserId()) {
                synchronized (SafeUIKeyguardViewMediator.this) {
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                    safeUIKeyguardViewMediator.notifyTrustedChangedLocked(safeUIKeyguardViewMediator.mUpdateMonitor.getUserHasTrust(i));
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            android.util.Log.d("SafeUIKeyguardViewMediator", String.format("onUserSwitchComplete %d", Integer.valueOf(i)));
            SafeUIKeyguardViewMediator.this.mHandler.postDelayed(new SafeUIKeyguardViewMediator$3$$ExternalSyntheticLambda0(this, 0), 500L);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitching(int i) {
            android.util.Log.d("SafeUIKeyguardViewMediator", String.format("onUserSwitching %d", Integer.valueOf(i)));
            synchronized (SafeUIKeyguardViewMediator.this) {
                Flags.refactorGetCurrentUser();
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                Intent intent = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
                safeUIKeyguardViewMediator.resetKeyguardDonePendingLocked();
                SafeUIKeyguardViewMediator.this.dismiss(null, null);
                SafeUIKeyguardViewMediator.this.adjustStatusBarLocked(false, false);
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$4, reason: invalid class name */
    public final class AnonymousClass4 implements ViewMediatorCallback {
        public AnonymousClass4() {
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
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            int selectedUserId = safeUIKeyguardViewMediator.mSelectedUserInteractor.getSelectedUserId(false);
            KeyguardUpdateMonitor keyguardUpdateMonitor = safeUIKeyguardViewMediator.mUpdateMonitor;
            keyguardUpdateMonitor.getClass();
            Assert.isMainThread();
            boolean z = keyguardUpdateMonitor.mUserTrustIsUsuallyManaged.get(selectedUserId);
            boolean z2 = z || (keyguardUpdateMonitor.isUnlockWithFacePossible(selectedUserId) || keyguardUpdateMonitor.isUnlockWithFingerprintPossible(selectedUserId));
            KeyguardUpdateMonitor.StrongAuthTracker strongAuthTracker = keyguardUpdateMonitor.mStrongAuthTracker;
            int strongAuthForUser = strongAuthTracker.getStrongAuthForUser(selectedUserId);
            boolean isNonStrongBiometricAllowedAfterIdleTimeout = strongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(selectedUserId);
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
            if (z2 && ((strongAuthForUser & 8) != 0 || keyguardUpdateMonitor.isFingerprintLockedOut())) {
                return 5;
            }
            if ((strongAuthForUser & 512) != 0) {
                return 9;
            }
            if (z && (strongAuthForUser & 4) != 0) {
                return 4;
            }
            if (z && (strongAuthForUser & 256) != 0) {
                return 8;
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
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            if (i != safeUIKeyguardViewMediator.mSelectedUserInteractor.getSelectedUserId()) {
                return;
            }
            android.util.Log.d("SafeUIKeyguardViewMediator", "keyguardDone");
            safeUIKeyguardViewMediator.tryKeyguardDone();
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
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            if (i != safeUIKeyguardViewMediator.mSelectedUserInteractor.getSelectedUserId(false)) {
                Trace.endSection();
                return;
            }
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
                    safeUIKeyguardViewMediator.mPM.wakeUp(safeUIKeyguardViewMediator.mSystemClock.uptimeMillis(), safeUIKeyguardViewMediator.mWakeAndUnlocking ? 17 : 4, "com.android.systemui:UNLOCK_DREAMING");
                }
            }
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void onCancelClicked() {
            ((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getClass();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void playTrustedSound() {
            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
            safeUIKeyguardViewMediator.playSound(safeUIKeyguardViewMediator.mTrustedSoundId);
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
            Intent intent = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$7, reason: invalid class name */
    public final class AnonymousClass7 extends IRemoteAnimationRunner.Stub {
        public ValueAnimator mOccludeByDreamAnimator;

        public AnonymousClass7() {
        }

        public final void onAnimationCancelled() {
            SafeUIKeyguardViewMediator.this.mContext.getMainExecutor().execute(new SafeUIKeyguardViewMediator$3$$ExternalSyntheticLambda0(this, 1));
            android.util.Log.d("SafeUIKeyguardViewMediator", "OccludeByDreamAnimator#onAnimationCancelled. Set occluded = true");
            SafeUIKeyguardViewMediator.this.setOccluded(true, false);
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
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
                            final SafeUIKeyguardViewMediator.AnonymousClass7 anonymousClass7 = SafeUIKeyguardViewMediator.AnonymousClass7.this;
                            final RemoteAnimationTarget remoteAnimationTarget2 = remoteAnimationTarget;
                            final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier2 = syncRtSurfaceTransactionApplier;
                            final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                            ValueAnimator valueAnimator = anonymousClass7.mOccludeByDreamAnimator;
                            if (valueAnimator != null) {
                                valueAnimator.cancel();
                            }
                            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                            anonymousClass7.mOccludeByDreamAnimator = ofFloat;
                            ofFloat.setDuration(SafeUIKeyguardViewMediator.this.mDreamOpenAnimationDuration);
                            anonymousClass7.mOccludeByDreamAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$7$$ExternalSyntheticLambda2
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                    syncRtSurfaceTransactionApplier2.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget2.leash).withAlpha(valueAnimator2.getAnimatedFraction()).build()});
                                }
                            });
                            anonymousClass7.mOccludeByDreamAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.7.1
                                public boolean mIsCancelled = false;

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationCancel(Animator animator) {
                                    this.mIsCancelled = true;
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    try {
                                        if (!this.mIsCancelled) {
                                            SafeUIKeyguardViewMediator.m1958$$Nest$mhandleSetOccluded(SafeUIKeyguardViewMediator.this, true, false);
                                        }
                                        iRemoteAnimationFinishedCallback2.onAnimationFinished();
                                        AnonymousClass7.this.mOccludeByDreamAnimator = null;
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            anonymousClass7.mOccludeByDreamAnimator.start();
                        }
                    });
                    return;
                }
                android.util.Log.w("SafeUIKeyguardViewMediator", "The occluding app isn't Dream; finishing up. Please check that the config is correct.");
            }
            SafeUIKeyguardViewMediator.this.setOccluded(true, false);
            iRemoteAnimationFinishedCallback.onAnimationFinished();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DismissMessage {
        public final IKeyguardDismissCallback mCallback;
        public final CharSequence mMessage;

        public DismissMessage(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
            this.mCallback = iKeyguardDismissCallback;
            this.mMessage = charSequence;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class OccludeActivityLaunchRemoteAnimationRunner extends ActivityLaunchRemoteAnimationRunner {
        public OccludeActivityLaunchRemoteAnimationRunner(ActivityTransitionAnimator.Controller controller) {
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
            safeUIKeyguardViewMediator.mInteractionJankMonitor.begin(safeUIKeyguardViewMediator.createInteractionJankMonitorConf(64, null).setTag("OCCLUDE"));
            android.util.Log.d("SafeUIKeyguardViewMediator", "OccludeAnimator#onAnimationStart. Set occluded = true.");
            SafeUIKeyguardViewMediator.this.setOccluded(true, false);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class StartKeyguardExitAnimParams {
        public final long fadeoutDuration;
        public final RemoteAnimationTarget[] mApps;
        public final IRemoteAnimationFinishedCallback mFinishedCallback;
        public final long startTime;

        public /* synthetic */ StartKeyguardExitAnimParams(int i, long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, int i2) {
            this(i, j, j2, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
        }

        private StartKeyguardExitAnimParams(int i, long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            this.startTime = j;
            this.fadeoutDuration = j2;
            this.mApps = remoteAnimationTargetArr;
            this.mFinishedCallback = iRemoteAnimationFinishedCallback;
        }
    }

    /* renamed from: -$$Nest$mhandleKeyguardDoneDrawing, reason: not valid java name */
    public static void m1954$$Nest$mhandleKeyguardDoneDrawing(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator) {
        safeUIKeyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDoneDrawing");
        synchronized (safeUIKeyguardViewMediator) {
            try {
                android.util.Log.d("SafeUIKeyguardViewMediator", "handleKeyguardDoneDrawing");
                if (safeUIKeyguardViewMediator.mWaitingUntilKeyguardVisible) {
                    android.util.Log.d("SafeUIKeyguardViewMediator", "handleKeyguardDoneDrawing: notifying mWaitingUntilKeyguardVisible");
                    safeUIKeyguardViewMediator.mWaitingUntilKeyguardVisible = false;
                    safeUIKeyguardViewMediator.notifyAll();
                    safeUIKeyguardViewMediator.mHandler.removeMessages(8);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mhandleNotifyFinishedGoingToSleep, reason: not valid java name */
    public static void m1955$$Nest$mhandleNotifyFinishedGoingToSleep(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator) {
        synchronized (safeUIKeyguardViewMediator) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "handleNotifyFinishedGoingToSleep");
            ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).onFinishedGoingToSleep();
        }
    }

    /* renamed from: -$$Nest$mhandleNotifyStartedWakingUp, reason: not valid java name */
    public static void m1956$$Nest$mhandleNotifyStartedWakingUp(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator) {
        safeUIKeyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleMotifyStartedWakingUp");
        synchronized (safeUIKeyguardViewMediator) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "handleNotifyWakingUp");
            ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).onStartedWakingUp();
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mhandleReset, reason: not valid java name */
    public static void m1957$$Nest$mhandleReset(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, boolean z) {
        synchronized (safeUIKeyguardViewMediator) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "handleReset");
            ((KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get()).reset(z);
        }
        safeUIKeyguardViewMediator.scheduleNonStrongBiometricIdleTimeout();
    }

    /* renamed from: -$$Nest$mhandleSetOccluded, reason: not valid java name */
    public static void m1958$$Nest$mhandleSetOccluded(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, boolean z, boolean z2) {
        safeUIKeyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleSetOccluded");
        android.util.Log.d("SafeUIKeyguardViewMediator", "handleSetOccluded(" + z + ")");
        EventLog.writeEvent(36080, Integer.valueOf(z ? 1 : 0), Integer.valueOf(z2 ? 1 : 0));
        safeUIKeyguardViewMediator.mInteractionJankMonitor.cancel(23);
        synchronized (safeUIKeyguardViewMediator) {
            try {
                if (safeUIKeyguardViewMediator.mHiding && z) {
                    safeUIKeyguardViewMediator.startKeyguardExitAnimation(0L, 0L);
                }
                boolean z3 = true;
                safeUIKeyguardViewMediator.mPowerGestureIntercepted = z && safeUIKeyguardViewMediator.mUpdateMonitor.mSecureCameraLaunched;
                if (safeUIKeyguardViewMediator.mOccluded != z) {
                    safeUIKeyguardViewMediator.mOccluded = z;
                    KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                    Flags.keyguardWmStateRefactor();
                    KeyguardViewController keyguardViewController = (KeyguardViewController) safeUIKeyguardViewMediator.mKeyguardViewControllerLazy.get();
                    if (!z2 || !safeUIKeyguardViewMediator.mDeviceInteractive) {
                        z3 = false;
                    }
                    keyguardViewController.setOccluded(z, z3);
                    safeUIKeyguardViewMediator.adjustStatusBarLocked(false, false);
                }
                android.util.Log.d("SafeUIKeyguardViewMediator", "isOccluded=" + z + ",mPowerGestureIntercepted=" + safeUIKeyguardViewMediator.mPowerGestureIntercepted);
            } catch (Throwable th) {
                throw th;
            }
        }
        Trace.endSection();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x006c A[Catch: all -> 0x0048, TryCatch #0 {all -> 0x0048, blocks: (B:11:0x003a, B:13:0x003e, B:14:0x0045, B:18:0x004b, B:20:0x005e, B:25:0x006c, B:26:0x0092, B:27:0x00c7), top: B:10:0x003a }] */
    /* renamed from: -$$Nest$mhandleShow, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m1959$$Nest$mhandleShow(com.android.systemui.keyguard.SafeUIKeyguardViewMediator r6, android.os.Bundle r7) {
        /*
            r6.getClass()
            java.lang.String r0 = "Forcing setShowingLocked because one of these is true:mHiding="
            java.lang.String r1 = "KeyguardViewMediator#handleShow"
            android.os.Trace.beginSection(r1)
            r1 = 0
            r2 = 1
            if (r7 == 0) goto L19
            java.lang.String r3 = "show_dismissible"
            boolean r3 = r7.getBoolean(r3, r1)
            if (r3 == 0) goto L19
            r3 = r2
            goto L1a
        L19:
            r3 = r1
        L1a:
            com.android.systemui.user.domain.interactor.SelectedUserInteractor r4 = r6.mSelectedUserInteractor
            int r4 = r4.getSelectedUserId(r1)
            if (r3 == 0) goto L28
            com.android.keyguard.KeyguardUpdateMonitor r3 = r6.mUpdateMonitor
            r3.setForceIsDismissibleKeyguard(r2)
            goto L39
        L28:
            com.android.internal.widget.LockPatternUtils r3 = r6.mLockPatternUtils
            boolean r3 = r3.isSecure(r4)
            if (r3 == 0) goto L39
            com.android.internal.widget.LockPatternUtils r3 = r6.mLockPatternUtils
            android.app.admin.DevicePolicyManager r3 = r3.getDevicePolicyManager()
            r3.reportKeyguardSecured(r4)
        L39:
            monitor-enter(r6)
            boolean r3 = r6.mSystemReady     // Catch: java.lang.Throwable -> L48
            if (r3 != 0) goto L4b
            java.lang.String r7 = "SafeUIKeyguardViewMediator"
            java.lang.String r0 = "ignoring handleShow because system is not ready."
            android.util.Log.d(r7, r0)     // Catch: java.lang.Throwable -> L48
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L48
            goto Ld3
        L48:
            r7 = move-exception
            goto Ld4
        L4b:
            java.lang.String r3 = "SafeUIKeyguardViewMediator"
            java.lang.String r4 = "handleShow"
            android.util.Log.d(r3, r4)     // Catch: java.lang.Throwable -> L48
            r6.mWakeAndUnlocking = r1     // Catch: java.lang.Throwable -> L48
            r6.setUnlockAndWakeFromDream(r2, r1)     // Catch: java.lang.Throwable -> L48
            r6.setPendingLock(r1)     // Catch: java.lang.Throwable -> L48
            boolean r3 = r6.mHiding     // Catch: java.lang.Throwable -> L48
            if (r3 != 0) goto L69
            com.android.systemui.statusbar.policy.KeyguardStateController r3 = r6.mKeyguardStateController     // Catch: java.lang.Throwable -> L48
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r3 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r3     // Catch: java.lang.Throwable -> L48
            boolean r3 = r3.mKeyguardGoingAway     // Catch: java.lang.Throwable -> L48
            if (r3 == 0) goto L67
            goto L69
        L67:
            r3 = r1
            goto L6a
        L69:
            r3 = r2
        L6a:
            if (r3 == 0) goto L92
            java.lang.String r4 = "SafeUIKeyguardViewMediator"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L48
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L48
            boolean r0 = r6.mHiding     // Catch: java.lang.Throwable -> L48
            r5.append(r0)     // Catch: java.lang.Throwable -> L48
            java.lang.String r0 = ", keyguardGoingAway="
            r5.append(r0)     // Catch: java.lang.Throwable -> L48
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = r6.mKeyguardStateController     // Catch: java.lang.Throwable -> L48
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r0 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r0     // Catch: java.lang.Throwable -> L48
            boolean r0 = r0.mKeyguardGoingAway     // Catch: java.lang.Throwable -> L48
            r5.append(r0)     // Catch: java.lang.Throwable -> L48
            java.lang.String r0 = ", which means we're showing in the middle of hiding."
            r5.append(r0)     // Catch: java.lang.Throwable -> L48
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> L48
            android.util.Log.d(r4, r0)     // Catch: java.lang.Throwable -> L48
        L92:
            r6.setShowingLocked(r2, r3)     // Catch: java.lang.Throwable -> L48
            r6.mHiding = r1     // Catch: java.lang.Throwable -> L48
            com.android.systemui.keyguard.KeyguardWmStateRefactor r0 = com.android.systemui.keyguard.KeyguardWmStateRefactor.INSTANCE     // Catch: java.lang.Throwable -> L48
            com.android.systemui.Flags.keyguardWmStateRefactor()     // Catch: java.lang.Throwable -> L48
            dagger.Lazy r0 = r6.mKeyguardViewControllerLazy     // Catch: java.lang.Throwable -> L48
            java.lang.Object r0 = r0.get()     // Catch: java.lang.Throwable -> L48
            com.android.keyguard.KeyguardViewController r0 = (com.android.keyguard.KeyguardViewController) r0     // Catch: java.lang.Throwable -> L48
            r0.show(r7)     // Catch: java.lang.Throwable -> L48
            r6.resetKeyguardDonePendingLocked()     // Catch: java.lang.Throwable -> L48
            r6.mHideAnimationRun = r1     // Catch: java.lang.Throwable -> L48
            r6.adjustStatusBarLocked(r1, r1)     // Catch: java.lang.Throwable -> L48
            r6.userActivity()     // Catch: java.lang.Throwable -> L48
            com.android.keyguard.KeyguardUpdateMonitor r7 = r6.mUpdateMonitor     // Catch: java.lang.Throwable -> L48
            r7.setKeyguardGoingAway(r1)     // Catch: java.lang.Throwable -> L48
            dagger.Lazy r7 = r6.mKeyguardViewControllerLazy     // Catch: java.lang.Throwable -> L48
            java.lang.Object r7 = r7.get()     // Catch: java.lang.Throwable -> L48
            com.android.keyguard.KeyguardViewController r7 = (com.android.keyguard.KeyguardViewController) r7     // Catch: java.lang.Throwable -> L48
            r7.setKeyguardGoingAwayState(r1)     // Catch: java.lang.Throwable -> L48
            android.os.PowerManager$WakeLock r7 = r6.mShowKeyguardWakeLock     // Catch: java.lang.Throwable -> L48
            r7.release()     // Catch: java.lang.Throwable -> L48
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L48
            com.android.keyguard.KeyguardDisplayManager r7 = r6.mKeyguardDisplayManager
            r7.show()
            r6.scheduleNonStrongBiometricIdleTimeout()
            android.os.Trace.endSection()
        Ld3:
            return
        Ld4:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L48
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.m1959$$Nest$mhandleShow(com.android.systemui.keyguard.SafeUIKeyguardViewMediator, android.os.Bundle):void");
    }

    /* JADX WARN: Type inference failed for: r8v5, types: [com.android.systemui.keyguard.SafeUIKeyguardViewMediator$8] */
    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.keyguard.SafeUIKeyguardViewMediator$6] */
    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.keyguard.SafeUIKeyguardViewMediator$10] */
    /* JADX WARN: Type inference failed for: r9v3, types: [com.android.systemui.keyguard.SafeUIKeyguardViewMediator$11] */
    public SafeUIKeyguardViewMediator(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, Context context, UiEventLogger uiEventLogger, SessionTracker sessionTracker, UserTracker userTracker, FalsingCollector falsingCollector, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, Lazy lazy, DismissCallbackRegistry dismissCallbackRegistry, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, Executor executor, PowerManager powerManager, TrustManager trustManager, UserSwitcherController userSwitcherController, DeviceConfigProxy deviceConfigProxy, NavigationModeController navigationModeController, KeyguardDisplayManager keyguardDisplayManager, DozeParameters dozeParameters, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController, Lazy lazy2, ScreenOffAnimationController screenOffAnimationController, Lazy lazy3, ScreenOnCoordinator screenOnCoordinator, KeyguardTransitions keyguardTransitions, InteractionJankMonitor interactionJankMonitor, DreamOverlayStateController dreamOverlayStateController, JavaAdapter javaAdapter, WallpaperRepository wallpaperRepository, Lazy lazy4, Lazy lazy5, Lazy lazy6, Lazy lazy7, IActivityTaskManager iActivityTaskManager, FeatureFlags featureFlags, SecureSettings secureSettings, SystemSettings systemSettings, SystemClock systemClock, CoroutineDispatcher coroutineDispatcher, Lazy lazy8, Lazy lazy9, SystemPropertiesHelper systemPropertiesHelper, Lazy lazy10, SelectedUserInteractor selectedUserInteractor, KeyguardInteractor keyguardInteractor, WindowManagerOcclusionManager windowManagerOcclusionManager) {
        super(keyguardViewMediatorHelperImpl, context, uiEventLogger, sessionTracker, userTracker, falsingCollector, lockPatternUtils, broadcastDispatcher, lazy, dismissCallbackRegistry, keyguardUpdateMonitor, dumpManager, executor, powerManager, trustManager, userSwitcherController, deviceConfigProxy, navigationModeController, keyguardDisplayManager, dozeParameters, sysuiStatusBarStateController, keyguardStateController, lazy2, screenOffAnimationController, lazy3, screenOnCoordinator, keyguardTransitions, interactionJankMonitor, dreamOverlayStateController, javaAdapter, wallpaperRepository, lazy4, lazy5, lazy6, lazy7, iActivityTaskManager, featureFlags, secureSettings, systemSettings, systemClock, coroutineDispatcher, lazy8, lazy9, systemPropertiesHelper, lazy10, selectedUserInteractor, keyguardInteractor, windowManagerOcclusionManager);
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
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("nav_bar_handle_show_over_lockscreen")) {
                    SafeUIKeyguardViewMediator.this.mShowHomeOverLockscreen = properties.getBoolean("nav_bar_handle_show_over_lockscreen", true);
                }
            }
        };
        this.mDreamOverlayStateCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.2
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onStateChanged() {
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                safeUIKeyguardViewMediator.mDreamOverlayShowing = safeUIKeyguardViewMediator.mDreamOverlayStateController.isOverlayActive();
            }
        };
        this.mUpdateCallback = new AnonymousClass3();
        this.mViewMediatorCallback = new AnonymousClass4();
        ActivityTransitionAnimator.Controller controller = new ActivityTransitionAnimator.Controller() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.5
            public final boolean mIsLaunching = true;

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final TransitionAnimator.State createAnimatorState() {
                int width = getTransitionContainer().getWidth();
                int height = getTransitionContainer().getHeight();
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                if (safeUIKeyguardViewMediator.mUpdateMonitor.mSecureCameraLaunched) {
                    float f = width;
                    float f2 = safeUIKeyguardViewMediator.mPowerButtonY;
                    float f3 = (height / 3.0f) / 2.0f;
                    float f4 = safeUIKeyguardViewMediator.mWindowCornerRadius;
                    return new TransitionAnimator.State((int) (f2 - f3), (int) (f2 + f3), (int) (f - (f / 3.0f)), width, f4, f4);
                }
                float f5 = height;
                float f6 = f5 / 2.0f;
                float f7 = width;
                float f8 = f7 / 2.0f;
                float f9 = f5 - f6;
                float f10 = f7 - f8;
                float f11 = safeUIKeyguardViewMediator.mWindowCornerRadius;
                return new TransitionAnimator.State(((int) f9) / 2, (int) ((f9 / 2.0f) + f6), ((int) f10) / 2, (int) ((f10 / 2.0f) + f8), f11, f11);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final ViewGroup getTransitionContainer() {
                return (ViewGroup) ((KeyguardViewController) SafeUIKeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView();
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final boolean isLaunching() {
                return this.mIsLaunching;
            }

            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
            public final void onTransitionAnimationCancelled(Boolean bool) {
                StringBuilder sb = new StringBuilder("Occlude launch animation cancelled. Occluded state is now: ");
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, safeUIKeyguardViewMediator.mOccluded, "SafeUIKeyguardViewMediator");
                safeUIKeyguardViewMediator.mOccludeAnimationPlaying = false;
                ((CentralSurfacesImpl) safeUIKeyguardViewMediator.mCentralSurfaces).updateIsKeyguard(false);
                ((ScrimController) safeUIKeyguardViewMediator.mScrimControllerLazy.get()).setOccludeAnimationPlaying(false);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationEnd(boolean z) {
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                if (z) {
                    ((ShadeController) safeUIKeyguardViewMediator.mShadeController.get()).instantCollapseShade();
                }
                safeUIKeyguardViewMediator.mOccludeAnimationPlaying = false;
                ((CentralSurfacesImpl) safeUIKeyguardViewMediator.mCentralSurfaces).updateIsKeyguard(false);
                ((ScrimController) safeUIKeyguardViewMediator.mScrimControllerLazy.get()).setOccludeAnimationPlaying(false);
                safeUIKeyguardViewMediator.mInteractionJankMonitor.end(64);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationStart(boolean z) {
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                safeUIKeyguardViewMediator.mOccludeAnimationPlaying = true;
                ((ScrimController) safeUIKeyguardViewMediator.mScrimControllerLazy.get()).setOccludeAnimationPlaying(true);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void setTransitionContainer(ViewGroup viewGroup) {
                android.util.Log.wtf("SafeUIKeyguardViewMediator", "Someone tried to change the launch container for the ActivityTransitionAnimator, which should never happen.");
            }
        };
        this.mOccludeAnimationController = controller;
        this.mExitAnimationRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.6
            public final void onAnimationCancelled() {
                SafeUIKeyguardViewMediator.this.cancelKeyguardExitAnimation();
                KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                Flags.keyguardWmStateRefactor();
            }

            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                Trace.beginSection("mExitAnimationRunner.onAnimationStart#startKeyguardExitAnimation");
                SafeUIKeyguardViewMediator.this.startKeyguardExitAnimation(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
                KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                Flags.keyguardWmStateRefactor();
                Trace.endSection();
            }
        };
        this.mOccludeAnimationRunner = new OccludeActivityLaunchRemoteAnimationRunner(controller);
        this.mOccludeByDreamAnimationRunner = new AnonymousClass7();
        this.mUnoccludeAnimationRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.8
            {
                new Matrix();
            }

            public final void onAnimationCancelled() {
                SafeUIKeyguardViewMediator.this.mContext.getMainExecutor().execute(new SafeUIKeyguardViewMediator$3$$ExternalSyntheticLambda0(this, 2));
                android.util.Log.d("SafeUIKeyguardViewMediator", "Unocclude animation cancelled.");
                SafeUIKeyguardViewMediator.this.mInteractionJankMonitor.cancel(64);
            }

            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                android.util.Log.d("SafeUIKeyguardViewMediator", "UnoccludeAnimator#onAnimationStart. Set occluded = false.");
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                safeUIKeyguardViewMediator.mInteractionJankMonitor.begin(safeUIKeyguardViewMediator.createInteractionJankMonitorConf(64, null).setTag("UNOCCLUDE"));
                SafeUIKeyguardViewMediator.this.setOccluded(false, true);
                if (iRemoteAnimationFinishedCallback != null) {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                }
            }
        };
        this.mFoldGracePeriodProvider = new FoldGracePeriodProvider();
        KeyguardStateController.Callback callback = new KeyguardStateController.Callback() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.9
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
        this.mDelayedLockBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.10
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("seq", 0);
                    RecyclerView$$ExternalSyntheticOutline0.m(SafeUIKeyguardViewMediator.this.mDelayedShowingSequence, "SafeUIKeyguardViewMediator", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(intExtra, "received DELAYED_KEYGUARD_ACTION with seq = ", ", mDelayedShowingSequence = "));
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
                                safeUIKeyguardViewMediator2.mTrustManager.setDeviceLockedForUser(intExtra3, true);
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
        final AnonymousClass12 anonymousClass12 = new AnonymousClass12(Looper.myLooper(), null, true);
        this.mHandler = anonymousClass12;
        this.mKeyguardGoingAwayRunnable = new AnonymousClass13();
        this.mHideAnimationFinishedRunnable = new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda0(this, 2);
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
        this.mSecureSettings = secureSettings;
        this.mSystemSettings = systemSettings;
        this.mSystemClock = systemClock;
        this.mSystemPropertiesHelper = systemPropertiesHelper;
        this.mStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        this.mKeyguardDisplayManager = keyguardDisplayManager;
        this.mShadeController = lazy4;
        dumpManager.registerDumpable(this);
        this.mKeyguardTransitions = keyguardTransitions;
        this.mNotificationShadeWindowControllerLazy = lazy5;
        this.mShowHomeOverLockscreen = deviceConfigProxy.getBoolean("systemui", "nav_bar_handle_show_over_lockscreen", true);
        deviceConfigProxy.addOnPropertiesChangedListener("systemui", new Executor() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda3
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                anonymousClass12.post(runnable);
            }
        }, onPropertiesChangedListener);
        this.mInGestureNavigationMode = QuickStepContract.isGesturalMode(navigationModeController.addListener(new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda4
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i) {
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                safeUIKeyguardViewMediator.getClass();
                safeUIKeyguardViewMediator.mInGestureNavigationMode = QuickStepContract.isGesturalMode(i);
            }
        }));
        this.mDozeParameters = dozeParameters;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mKeyguardInteractor = keyguardInteractor;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        ((StatusBarStateControllerImpl) sysuiStatusBarStateController).addCallback((StatusBarStateController.StateListener) this);
        this.mKeyguardStateController = keyguardStateController;
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(callback);
        this.mKeyguardUnlockAnimationControllerLazy = lazy2;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mJavaAdapter = javaAdapter;
        this.mWallpaperRepository = wallpaperRepository;
        this.mScrimControllerLazy = lazy7;
        this.mActivityTaskManagerService = iActivityTaskManager;
        this.mPowerButtonY = context.getResources().getDimensionPixelSize(R.dimen.physical_power_button_center_screen_location_y);
        this.mWindowCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        this.mDreamOpenAnimationDuration = (int) LockscreenToDreamingTransitionViewModel.DREAMING_ANIMATION_DURATION_MS;
        this.mUiEventLogger = uiEventLogger;
        this.mSessionTracker = sessionTracker;
        this.mDreamViewModel = lazy8;
        this.mCommunalTransitionViewModel = lazy9;
        this.mOrderUnlockAndWake = context.getResources().getBoolean(android.R.bool.config_profcollectReportUploaderEnabled);
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "show keyguard");
        this.mShowKeyguardWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback) {
        synchronized (this) {
            this.mKeyguardStateCallbacks.add(iKeyguardStateCallback);
            try {
                iKeyguardStateCallback.onSimSecureStateChanged(this.mUpdateMonitor.isSimPinSecure());
                iKeyguardStateCallback.onShowingStateChanged(this.mShowing, this.mSelectedUserInteractor.getSelectedUserId(false));
                iKeyguardStateCallback.onInputRestrictedStateChanged(this.mInputRestricted);
                iKeyguardStateCallback.onTrustedChanged(this.mUpdateMonitor.getUserHasTrust(this.mSelectedUserInteractor.getSelectedUserId(false)));
            } catch (RemoteException e) {
                Slog.w("SafeUIKeyguardViewMediator", "Failed to call to IKeyguardStateCallback", e);
            }
        }
    }

    public final void adjustStatusBarLocked(boolean z, boolean z2) {
        if (this.mStatusBarManager == null) {
            this.mStatusBarManager = (StatusBarManager) this.mContext.getSystemService("statusbar");
        }
        if (this.mStatusBarManager == null) {
            android.util.Log.w("SafeUIKeyguardViewMediator", "Could not get status bar manager");
            return;
        }
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        if (z2) {
            try {
                this.mStatusBarService.disableForUser(0, this.mStatusBarDisableToken, this.mContext.getPackageName(), selectedUserInteractor.getSelectedUserId(true));
            } catch (RemoteException e) {
                android.util.Log.d("SafeUIKeyguardViewMediator", "Failed to force clear flags", e);
            }
        }
        if (z || isShowingAndNotOccluded()) {
            r3 = ((this.mShowHomeOverLockscreen && this.mInGestureNavigationMode) ? 0 : com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_DEVICE_DOZING) | 16777216;
        }
        if (this.mPowerGestureIntercepted && this.mOccluded && isSecure() && this.mUpdateMonitor.isFaceEnabledAndEnrolled()) {
            r3 |= 16777216;
        }
        android.util.Log.d("SafeUIKeyguardViewMediator", "adjustStatusBarLocked: mShowing=" + this.mShowing + " mOccluded=" + this.mOccluded + " isSecure=" + isSecure() + " force=" + z + " mPowerGestureIntercepted=" + this.mPowerGestureIntercepted + " --> flags=0x" + Integer.toHexString(r3));
        try {
            this.mStatusBarService.disableForUser(r3, this.mStatusBarDisableToken, this.mContext.getPackageName(), selectedUserInteractor.getSelectedUserId(true));
        } catch (RemoteException e2) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "Failed to set disable flags: " + r3, e2);
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void cancelKeyguardExitAnimation() {
        Trace.beginSection("KeyguardViewMediator#cancelKeyguardExitAnimation");
        AnonymousClass12 anonymousClass12 = this.mHandler;
        anonymousClass12.sendMessage(anonymousClass12.obtainMessage(19));
        Trace.endSection();
    }

    public final InteractionJankMonitor.Configuration.Builder createInteractionJankMonitorConf(int i, String str) {
        InteractionJankMonitor.Configuration.Builder withView = InteractionJankMonitor.Configuration.Builder.withView(i, ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
        return str != null ? withView.setTag(str) : withView;
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

    public final void doKeyguardLaterLocked(long j) {
        SystemClock systemClock = this.mSystemClock;
        long elapsedRealtime = systemClock.elapsedRealtime() + j;
        Intent intent = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        intent.setPackage(this.mContext.getPackageName());
        intent.putExtra("seq", this.mDelayedShowingSequence);
        intent.addFlags(com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        this.mAlarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime, PendingIntent.getBroadcast(this.mContext, 0, intent, 335544320));
        RecyclerView$$ExternalSyntheticOutline0.m(this.mDelayedShowingSequence, "SafeUIKeyguardViewMediator", new StringBuilder("setting alarm to turn off keyguard, seq = "));
        for (UserInfo userInfo : ((UserTrackerImpl) this.mUserTracker).getUserProfiles()) {
            if (userInfo.isEnabled()) {
                int i = userInfo.id;
                if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
                    long lockTimeout = getLockTimeout(i);
                    if (lockTimeout == 0) {
                        doKeyguardForChildProfilesLocked();
                    } else {
                        long elapsedRealtime2 = systemClock.elapsedRealtime() + lockTimeout;
                        Intent intent2 = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
                        intent2.setPackage(this.mContext.getPackageName());
                        intent2.putExtra("seq", this.mDelayedProfileShowingSequence);
                        intent2.putExtra("android.intent.extra.USER_ID", i);
                        intent2.addFlags(com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                        this.mAlarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime2, PendingIntent.getBroadcast(this.mContext, 0, intent2, 335544320));
                    }
                }
            }
        }
    }

    public final void doKeyguardLocked(Bundle bundle) {
        boolean z = this.mExternallyEnabled;
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        if (!z && !this.mLockPatternUtils.isUserInLockdown(selectedUserInteractor.getSelectedUserId())) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "doKeyguard: not showing because externally disabled");
            this.mNeedToReshowWhenReenabled = true;
            return;
        }
        if (this.mShowing) {
            KeyguardStateController keyguardStateController = this.mKeyguardStateController;
            if (((KeyguardStateControllerImpl) keyguardStateController).mShowing) {
                if (!this.mPM.isInteractive() || this.mHiding) {
                    android.util.Log.e("SafeUIKeyguardViewMediator", "doKeyguard: already showing, but re-showing because we're interactive or were in the middle of hiding.");
                } else {
                    if (!((KeyguardStateControllerImpl) keyguardStateController).mKeyguardGoingAway) {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "doKeyguard: not showing (instead, resetting) because it is already showing, we're interactive, we were not previously hiding. It should be safe to short-circuit here.");
                        resetStateLocked(false);
                        return;
                    }
                    android.util.Log.e("SafeUIKeyguardViewMediator", "doKeyguard: we're still showing, but going away. Re-show the keyguard rather than short-circuiting and resetting.");
                }
            }
        }
        boolean z2 = !SystemProperties.getBoolean("keyguard.no_require_sim", false);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        boolean z3 = keyguardUpdateMonitor.isSimPinSecure() || ((SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(1)) || SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(7))) && z2);
        if (!z3 && shouldWaitForProvisioning()) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "doKeyguard: not showing because device isn't provisioned and the sim is not locked or missing");
            return;
        }
        boolean z4 = bundle != null && bundle.getBoolean("force_show", false);
        if (this.mLockPatternUtils.isLockScreenDisabled(selectedUserInteractor.getSelectedUserId()) && !z3 && !z4) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "doKeyguard: not showing because lockscreen is off");
            return;
        }
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (this.mLockPatternUtils.isSecure(userId) || this.mLockPatternUtils.isLockScreenDisabled(userId)) {
            android.util.Log.d("SafeUIKeyguardViewMediator", "doKeyguard: showing the lock screen");
            showKeyguard(bundle);
        } else {
            android.util.Log.d("SafeUIKeyguardViewMediator", "doKeyguard: not showing in safe & swipe mode");
            setShowingLocked(false, true);
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void doKeyguardTimeout(Bundle bundle) {
        AnonymousClass12 anonymousClass12 = this.mHandler;
        anonymousClass12.removeMessages(10);
        anonymousClass12.sendMessageAtFrontOfQueue(anonymousClass12.obtainMessage(10, bundle));
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
    public final void exitKeyguardAndFinishSurfaceBehindRemoteAnimation() {
        android.util.Log.d("SafeUIKeyguardViewMediator", "exitKeyguardAndFinishSurfaceBehindRemoteAnimation");
        if (!this.mSurfaceBehindRemoteAnimationRequested) {
            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("skip onKeyguardExitRemoteAnimationFinished showKeyguard=false surfaceAnimationRunning=false surfaceAnimationRequested="), this.mSurfaceBehindRemoteAnimationRequested, "SafeUIKeyguardViewMediator");
            return;
        }
        ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).blockPanelExpansionFromCurrentTouch();
        boolean z = this.mShowing;
        InteractionJankMonitor.getInstance().end(29);
        DejankUtils.postAfterTraversal(new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda1(this, z));
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void finishSurfaceBehindRemoteAnimation(boolean z) {
        ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).notifyFinishedKeyguardExitAnimation(z);
        this.mSurfaceBehindRemoteAnimationRequested = false;
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(false);
        if (z) {
            KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
            Flags.keyguardWmStateRefactor();
            MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
            Flags.migrateClocksToBlueprint();
            DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
            Flags.deviceEntryUdfpsRefactor();
            this.mKeyguardInteractor.showKeyguard();
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final IRemoteAnimationRunner getExitAnimationRunner() {
        return new AnonymousClass16(this.mExitAnimationRunner);
    }

    public final long getLockTimeout(int i) {
        long intForUser = this.mSecureSettings.getIntForUser("lock_screen_lock_after_timeout", 5000, i);
        long maximumTimeToLock = this.mLockPatternUtils.getDevicePolicyManager().getMaximumTimeToLock(null, i);
        return maximumTimeToLock <= 0 ? intForUser : Math.max(Math.min(maximumTimeToLock - Math.max(this.mSystemSettings.getIntForUser("screen_off_timeout", PluginLockInstancePolicy.DISABLED_BY_SUB_USER, i), 0L), intForUser), 0L);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final IRemoteAnimationRunner getOccludeAnimationRunner() {
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        Flags.keyguardWmStateRefactor();
        return new AnonymousClass16(this.mOccludeAnimationRunner);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final IRemoteAnimationRunner getOccludeByDreamAnimationRunner() {
        return new AnonymousClass16(this.mOccludeByDreamAnimationRunner);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final IRemoteAnimationRunner getUnoccludeAnimationRunner() {
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        Flags.keyguardWmStateRefactor();
        return new AnonymousClass16(this.mUnoccludeAnimationRunner);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final ViewMediatorCallback getViewMediatorCallback() {
        return this.mViewMediatorCallback;
    }

    public final void handleHide() {
        Trace.beginSection("KeyguardViewMediator#handleHide");
        if (this.mAodShowing) {
            this.mPM.wakeUp(this.mSystemClock.uptimeMillis(), 4, "com.android.systemui:BOUNCER_DOZING");
        }
        synchronized (this) {
            try {
                android.util.Log.d("SafeUIKeyguardViewMediator", "handleHide");
                if (!this.mWakeAndUnlocking) {
                    setUnlockAndWakeFromDream(0, ((StatusBarStateControllerImpl) this.mStatusBarStateController).mIsDreaming && this.mPM.isInteractive());
                }
                if ((!this.mShowing || this.mOccluded) && !this.mUnlockingAndWakingFromDream) {
                    android.util.Log.d("SafeUIKeyguardViewMediator", "Hiding keyguard while occluded. Just hide the keyguard view and exit.");
                    KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                    Flags.keyguardWmStateRefactor();
                    ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).hide(this.mSystemClock.uptimeMillis() + this.mHideAnimation.getStartOffset(), this.mHideAnimation.getDuration());
                    onKeyguardExitFinished();
                } else {
                    if (this.mUnlockingAndWakingFromDream) {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "hiding keyguard before waking from dream");
                    }
                    this.mHiding = true;
                    this.mKeyguardGoingAwayRunnable.run();
                }
                if ((this.mDreamOverlayShowing || this.mUpdateMonitor.mIsDreaming) && !this.mOrderUnlockAndWake) {
                    this.mPM.wakeUp(this.mSystemClock.uptimeMillis(), 4, "com.android.systemui:UNLOCK_DREAMING");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Trace.endSection();
    }

    public final void handleKeyguardDone() {
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDone");
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(false);
        this.mUiBgExecutor.execute(new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda8(this, selectedUserId, 1));
        android.util.Log.d("SafeUIKeyguardViewMediator", "handleKeyguardDone");
        synchronized (this) {
            resetKeyguardDonePendingLocked();
        }
        if (this.mGoingToSleep) {
            this.mUpdateMonitor.clearFingerprintRecognized(selectedUserId);
            android.util.Log.i("SafeUIKeyguardViewMediator", "Device is going to sleep, aborting keyguardDone");
            return;
        }
        setPendingLock(false);
        handleHide();
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) this.mKeyguardInteractor.repository;
        keyguardRepositoryImpl.getClass();
        keyguardRepositoryImpl.keyguardDoneAnimationsFinished.tryEmit(Unit.INSTANCE);
        this.mUpdateMonitor.clearFingerprintRecognized(selectedUserId);
        Trace.endSection();
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
    public final void hideWithAnimation(ActivityTransitionAnimator.Runner runner) {
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
        for (int i = 0; i < this.mLastSimStates.size(); i++) {
            if (KeyguardUpdateMonitor.isSimPinSecure(this.mLastSimStates.get(this.mLastSimStates.keyAt(i)))) {
                return true;
            }
        }
        return false;
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
        return isSecure(this.mSelectedUserInteractor.getSelectedUserId());
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final boolean isShowingAndNotOccluded() {
        return this.mShowing && !this.mOccluded;
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void launchingActivityOverLockscreen(boolean z) {
        this.mKeyguardTransitions.setLaunchingActivityOverLockscreen(z);
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

    public final void maybeSendUserPresentBroadcast() {
        boolean z = this.mSystemReady;
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        if (z && this.mLockPatternUtils.isLockScreenDisabled(selectedUserInteractor.getSelectedUserId())) {
            sendUserPresentBroadcast();
        } else if (this.mSystemReady && shouldWaitForProvisioning()) {
            this.mLockPatternUtils.userPresent(selectedUserInteractor.getSelectedUserId());
        }
    }

    public final void notifyTrustedChangedLocked(boolean z) {
        for (int size = this.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
            try {
                ((IKeyguardStateCallback) this.mKeyguardStateCallbacks.get(size)).onTrustedChanged(z);
            } catch (RemoteException e) {
                Slog.w("SafeUIKeyguardViewMediator", "Failed to call notifyTrustedChangedLocked", e);
                if (e instanceof DeadObjectException) {
                    this.mKeyguardStateCallbacks.remove(size);
                }
            }
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator, com.android.systemui.CoreStartable
    public final void onBootCompleted() {
        synchronized (this) {
            try {
                if (this.mContext.getResources().getBoolean(android.R.bool.config_imeDrawsImeNavBar)) {
                    ((GuestUserInteractor) this.mUserSwitcherController.guestUserInteractor$delegate.getValue()).onDeviceBootCompleted();
                }
                this.mBootCompleted = true;
                adjustStatusBarLocked(false, true);
                if (this.mBootSendUserPresent) {
                    sendUserPresentBroadcast();
                }
            } catch (Throwable th) {
                throw th;
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
            if (this.mDeviceInteractive) {
                long lockTimeout = getLockTimeout(this.mSelectedUserInteractor.getSelectedUserId(false));
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
        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "onFinishedGoingToSleep(", ")", "SafeUIKeyguardViewMediator");
        synchronized (this) {
            try {
                this.mDeviceInteractive = false;
                this.mGoingToSleep = false;
                this.mWakeAndUnlocking = false;
                this.mAnimatingScreenOff = this.mDozeParameters.shouldAnimateDozingChange();
                resetKeyguardDonePendingLocked();
                this.mHideAnimationRun = false;
                android.util.Log.d("SafeUIKeyguardViewMediator", "notifyFinishedGoingToSleep");
                this.mHandler.sendEmptyMessage(5);
                if (z) {
                    ((PowerManager) this.mContext.getSystemService(PowerManager.class)).wakeUp(this.mSystemClock.uptimeMillis(), 5, "com.android.systemui:CAMERA_GESTURE_PREVENT_LOCK");
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
            } catch (Throwable th) {
                throw th;
            }
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        synchronized (keyguardUpdateMonitor) {
            keyguardUpdateMonitor.mDeviceInteractive = false;
        }
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = keyguardUpdateMonitor.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(320, i, 0));
    }

    public final void onKeyguardExitFinished() {
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

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void onScreenTurnedOff() {
        this.mUpdateMonitor.mHandler.sendEmptyMessage(CustomDeviceManager.DESTINATION_ADDRESS);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x006d A[Catch: all -> 0x002b, TryCatch #0 {all -> 0x002b, blocks: (B:5:0x000b, B:7:0x0020, B:11:0x002f, B:13:0x003f, B:15:0x0047, B:16:0x0069, B:18:0x006d, B:19:0x0072, B:28:0x0058, B:32:0x005e, B:34:0x0066), top: B:4:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0066 A[Catch: all -> 0x002b, TryCatch #0 {all -> 0x002b, blocks: (B:5:0x000b, B:7:0x0020, B:11:0x002f, B:13:0x003f, B:15:0x0047, B:16:0x0069, B:18:0x006d, B:19:0x0072, B:28:0x0058, B:32:0x005e, B:34:0x0066), top: B:4:0x000b }] */
    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onStartedGoingToSleep(int r9) {
        /*
            r8 = this;
            java.lang.String r0 = "SafeUIKeyguardViewMediator"
            java.lang.String r1 = "onStartedGoingToSleep("
            java.lang.String r2 = ")"
            androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(r9, r1, r2, r0)
            monitor-enter(r8)
            r0 = 0
            r8.mDeviceInteractive = r0     // Catch: java.lang.Throwable -> L2b
            r8.mPowerGestureIntercepted = r0     // Catch: java.lang.Throwable -> L2b
            r1 = 1
            r8.mGoingToSleep = r1     // Catch: java.lang.Throwable -> L2b
            com.android.systemui.user.domain.interactor.SelectedUserInteractor r2 = r8.mSelectedUserInteractor     // Catch: java.lang.Throwable -> L2b
            int r2 = r2.getSelectedUserId(r0)     // Catch: java.lang.Throwable -> L2b
            com.android.internal.widget.LockPatternUtils r3 = r8.mLockPatternUtils     // Catch: java.lang.Throwable -> L2b
            boolean r3 = r3.getPowerButtonInstantlyLocks(r2)     // Catch: java.lang.Throwable -> L2b
            if (r3 != 0) goto L2e
            com.android.internal.widget.LockPatternUtils r3 = r8.mLockPatternUtils     // Catch: java.lang.Throwable -> L2b
            boolean r3 = r3.isSecure(r2)     // Catch: java.lang.Throwable -> L2b
            if (r3 != 0) goto L29
            goto L2e
        L29:
            r3 = r0
            goto L2f
        L2b:
            r9 = move-exception
            goto L9e
        L2e:
            r3 = r1
        L2f:
            com.android.systemui.user.domain.interactor.SelectedUserInteractor r4 = r8.mSelectedUserInteractor     // Catch: java.lang.Throwable -> L2b
            int r4 = r4.getSelectedUserId(r0)     // Catch: java.lang.Throwable -> L2b
            long r4 = r8.getLockTimeout(r4)     // Catch: java.lang.Throwable -> L2b
            r8.mLockLater = r0     // Catch: java.lang.Throwable -> L2b
            boolean r6 = r8.mShowing     // Catch: java.lang.Throwable -> L2b
            if (r6 == 0) goto L4a
            com.android.systemui.statusbar.policy.KeyguardStateController r6 = r8.mKeyguardStateController     // Catch: java.lang.Throwable -> L2b
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r6 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r6     // Catch: java.lang.Throwable -> L2b
            boolean r6 = r6.mKeyguardGoingAway     // Catch: java.lang.Throwable -> L2b
            if (r6 != 0) goto L4a
            r8.mPendingReset = r1     // Catch: java.lang.Throwable -> L2b
            goto L69
        L4a:
            r6 = 3
            if (r9 != r6) goto L53
            r6 = 0
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 > 0) goto L58
        L53:
            r6 = 2
            if (r9 != r6) goto L5e
            if (r3 != 0) goto L5e
        L58:
            r8.doKeyguardLaterLocked(r4)     // Catch: java.lang.Throwable -> L2b
            r8.mLockLater = r1     // Catch: java.lang.Throwable -> L2b
            goto L69
        L5e:
            com.android.internal.widget.LockPatternUtils r3 = r8.mLockPatternUtils     // Catch: java.lang.Throwable -> L2b
            boolean r2 = r3.isLockScreenDisabled(r2)     // Catch: java.lang.Throwable -> L2b
            if (r2 != 0) goto L69
            r8.setPendingLock(r1)     // Catch: java.lang.Throwable -> L2b
        L69:
            boolean r1 = r8.mPendingLock     // Catch: java.lang.Throwable -> L2b
            if (r1 == 0) goto L72
            int r1 = r8.mLockSoundId     // Catch: java.lang.Throwable -> L2b
            r8.playSound(r1)     // Catch: java.lang.Throwable -> L2b
        L72:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L2b
            com.android.keyguard.KeyguardUpdateMonitor r1 = r8.mUpdateMonitor
            com.android.keyguard.KeyguardUpdateMonitor$16 r1 = r1.mHandler
            r2 = 321(0x141, float:4.5E-43)
            android.os.Message r9 = r1.obtainMessage(r2, r9, r0)
            r1.sendMessage(r9)
            com.android.keyguard.KeyguardUpdateMonitor r9 = r8.mUpdateMonitor
            com.android.keyguard.KeyguardUpdateMonitor$16 r9 = r9.mHandler
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            r1 = 342(0x156, float:4.79E-43)
            android.os.Message r0 = r9.obtainMessage(r1, r0)
            r9.sendMessage(r0)
            java.lang.String r9 = "SafeUIKeyguardViewMediator"
            java.lang.String r0 = "notifyStartedGoingToSleep"
            android.util.Log.d(r9, r0)
            com.android.systemui.keyguard.SafeUIKeyguardViewMediator$12 r8 = r8.mHandler
            r9 = 17
            r8.sendEmptyMessage(r9)
            return
        L9e:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L2b
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.SafeUIKeyguardViewMediator.onStartedGoingToSleep(int):void");
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void onStartedWakingUp(int i, boolean z) {
        Trace.beginSection("KeyguardViewMediator#onStartedWakingUp");
        synchronized (this) {
            try {
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
            } catch (Throwable th) {
                throw th;
            }
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

    public final void playSound(int i) {
        if (i == 0) {
            return;
        }
        if (this.mSystemSettings.getIntForUser("lockscreen_sounds_enabled", 1, this.mSelectedUserInteractor.getSelectedUserId()) == 1) {
            this.mLockSounds.stop(this.mLockSoundStreamId);
            if (this.mAudioManager == null) {
                AudioManager audioManager = (AudioManager) this.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                this.mAudioManager = audioManager;
                if (audioManager == null) {
                    return;
                } else {
                    this.mUiSoundsStreamType = audioManager.getUiSoundsStreamType();
                }
            }
            this.mUiBgExecutor.execute(new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda8(this, i, 0));
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void registerCentralSurfaces$1(CentralSurfaces centralSurfaces, ShadeLockscreenInteractor shadeLockscreenInteractor, ShadeExpansionStateManager shadeExpansionStateManager, BiometricUnlockController biometricUnlockController, View view) {
        this.mCentralSurfaces = centralSurfaces;
        Lazy lazy = this.mKeyguardViewControllerLazy;
        ((KeyguardViewController) lazy.get()).registerCentralSurfaces(centralSurfaces, shadeLockscreenInteractor, shadeExpansionStateManager, biometricUnlockController, view);
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final boolean requestedShowSurfaceBehindKeyguard() {
        return this.mSurfaceBehindRemoteAnimationRequested;
    }

    public final void resetKeyguardDonePendingLocked() {
        this.mKeyguardDonePending = false;
        this.mHandler.removeMessages(13);
    }

    public final void resetStateLocked(boolean z) {
        android.util.Log.e("SafeUIKeyguardViewMediator", "resetStateLocked");
        AnonymousClass12 anonymousClass12 = this.mHandler;
        anonymousClass12.sendMessage(anonymousClass12.obtainMessage(3, z ? 1 : 0, 0));
    }

    public final void scheduleNonStrongBiometricIdleTimeout() {
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(false);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if ((keyguardUpdateMonitor.isFaceClass3() || !keyguardUpdateMonitor.isUnlockWithFacePossible(selectedUserId)) && !(keyguardUpdateMonitor.isFingerprintClass3() && keyguardUpdateMonitor.isUnlockWithFingerprintPossible(selectedUserId))) {
            return;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(selectedUserId, "scheduleNonStrongBiometricIdleTimeout: schedule an alarm for currentUser=", "SafeUIKeyguardViewMediator");
        this.mLockPatternUtils.scheduleNonStrongBiometricIdleTimeout(selectedUserId);
    }

    public final void sendUserPresentBroadcast() {
        synchronized (this) {
            try {
                if (this.mBootCompleted) {
                    final int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
                    final UserHandle userHandle = new UserHandle(selectedUserId);
                    final UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                    this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                            UserManager userManager2 = userManager;
                            UserHandle userHandle2 = userHandle;
                            int i = selectedUserId;
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
            } catch (Throwable th) {
                throw th;
            }
        }
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
            try {
                android.util.Log.d("SafeUIKeyguardViewMediator", "setKeyguardEnabled(" + z + ")");
                this.mExternallyEnabled = z;
                if (z || !this.mShowing) {
                    if (z && this.mNeedToReshowWhenReenabled) {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "previously hidden, reshowing, reenabling status bar expansion");
                        this.mNeedToReshowWhenReenabled = false;
                        updateInputRestrictedLocked();
                        showKeyguard(null);
                        this.mWaitingUntilKeyguardVisible = true;
                        this.mHandler.sendEmptyMessageDelayed(8, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
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
                    if (this.mLockPatternUtils.isUserInLockdown(this.mSelectedUserInteractor.getSelectedUserId(false))) {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "keyguardEnabled(false) overridden by user lockdown");
                        return;
                    }
                    android.util.Log.d("SafeUIKeyguardViewMediator", "remembering to reshow, hiding keyguard, disabling status bar expansion");
                    this.mNeedToReshowWhenReenabled = true;
                    updateInputRestrictedLocked();
                    Trace.beginSection("KeyguardViewMediator#hideLocked");
                    android.util.Log.d("SafeUIKeyguardViewMediator", "hideLocked");
                    AnonymousClass12 anonymousClass12 = this.mHandler;
                    anonymousClass12.sendMessage(anonymousClass12.obtainMessage(2));
                    Trace.endSection();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void setOccluded(boolean z, boolean z2) {
        android.util.Log.d("SafeUIKeyguardViewMediator", "setOccluded(" + z + ")");
        Trace.beginSection("KeyguardViewMediator#setOccluded");
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setOccluded ", "SafeUIKeyguardViewMediator", z);
        AnonymousClass12 anonymousClass12 = this.mHandler;
        anonymousClass12.removeMessages(9);
        anonymousClass12.sendMessage(anonymousClass12.obtainMessage(9, z ? 1 : 0, z2 ? 1 : 0));
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
        this.mUpdateMonitor.setSwitchingUser(z);
    }

    public final void setUnlockAndWakeFromDream(int i, boolean z) {
        String str;
        if (this.mOrderUnlockAndWake && z != this.mUnlockingAndWakingFromDream) {
            if (i == 0) {
                str = "hiding keyguard";
            } else if (i == 1) {
                str = "showing keyguard";
            } else if (i == 2) {
                str = "fulfilling existing request";
            } else {
                if (i != 3) {
                    throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unexpected value: "));
                }
                str = "waking to unlock";
            }
            boolean z2 = (z || i == 2) ? false : true;
            this.mUnlockingAndWakingFromDream = z;
            String str2 = z2 ? "Interrupting request to wake and unlock" : z ? "Initiating request to wake and unlock" : "Fulfilling request to wake and unlock";
            StringBuilder sb = new StringBuilder("Updating waking and unlocking request to ");
            sb.append(z);
            sb.append(". description:[");
            sb.append(str2);
            sb.append("]. reason:[");
            ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "]", "SafeUIKeyguardViewMediator");
        }
    }

    public final void setupLocked() {
        boolean z;
        this.mBroadcastDispatcher.registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("android.intent.action.ACTION_SHUTDOWN"), this.mBroadcastReceiver);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        intentFilter.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
        intentFilter.setPriority(1000);
        this.mContext.registerReceiver(this.mDelayedLockBroadcastReceiver, intentFilter, "com.android.systemui.permission.SELF", null, 2);
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        Flags.refactorGetCurrentUser();
        UserTracker userTracker = this.mUserTracker;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        int userId = userTrackerImpl.getUserId();
        int i = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
        synchronized (KeyguardUpdateMonitor.class) {
            KeyguardUpdateMonitor.sCurrentUser = userId;
        }
        try {
            z = this.mContext.getPackageManager().getServiceInfo(new ComponentName(this.mContext, (Class<?>) KeyguardService.class), 0).isEnabled();
        } catch (PackageManager.NameNotFoundException unused) {
            z = true;
        }
        if (z) {
            setShowingLocked(!shouldWaitForProvisioning() && (this.mLockPatternUtils.isSecure(userTrackerImpl.getUserId()) || this.mLockPatternUtils.isLockScreenDisabled(userTrackerImpl.getUserId())) && !this.mLockPatternUtils.isLockScreenDisabled(this.mSelectedUserInteractor.getSelectedUserId(false)), true);
        } else {
            setShowingLocked(false, true);
        }
        IRemoteAnimationRunner exitAnimationRunner = getExitAnimationRunner();
        int i2 = KeyguardService.$r8$clinit;
        this.mKeyguardTransitions.register(new KeyguardService.AnonymousClass1(this, exitAnimationRunner), new KeyguardService.AnonymousClass1(this, new KeyguardViewMediator.AnonymousClass17(LsRune.AOD_FULLSCREEN_APPEAR_ANIMATION ? this.mHelper.aodAppearAnimationRunner : this.mAppearAnimationRunner)), new KeyguardService.AnonymousClass1(this, getOccludeAnimationRunner()), new KeyguardService.AnonymousClass1(this, getOccludeByDreamAnimationRunner()), new KeyguardService.AnonymousClass1(this, getUnoccludeAnimationRunner()));
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mDeviceInteractive = this.mPM.isInteractive();
        this.mLockSounds = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(13).setContentType(4).build()).build();
        String string = Settings.Global.getString(contentResolver, "lock_sound");
        if (string != null) {
            this.mLockSoundId = this.mLockSounds.load(string, 1);
        }
        if (string == null || this.mLockSoundId == 0) {
            MotionLayout$$ExternalSyntheticOutline0.m("failed to load lock sound from ", string, "SafeUIKeyguardViewMediator");
        }
        String string2 = Settings.Global.getString(contentResolver, "unlock_sound");
        if (string2 != null) {
            this.mUnlockSoundId = this.mLockSounds.load(string2, 1);
        }
        if (string2 == null || this.mUnlockSoundId == 0) {
            MotionLayout$$ExternalSyntheticOutline0.m("failed to load unlock sound from ", string2, "SafeUIKeyguardViewMediator");
        }
        String string3 = Settings.Global.getString(contentResolver, "trusted_sound");
        if (string3 != null) {
            this.mTrustedSoundId = this.mLockSounds.load(string3, 1);
        }
        if (string3 == null || this.mTrustedSoundId == 0) {
            MotionLayout$$ExternalSyntheticOutline0.m("failed to load trusted sound from ", string3, "SafeUIKeyguardViewMediator");
        }
        this.mLockSoundVolume = (float) Math.pow(10.0d, this.mContext.getResources().getInteger(android.R.integer.config_networkDefaultDailyMultipathQuotaBytes) / 20.0f);
        this.mHideAnimation = AnimationUtils.loadAnimation(this.mContext, android.R.anim.recents_fade_out);
        new WorkLockActivityController(this.mContext, userTracker);
        this.mJavaAdapter.alwaysCollectFlow(((WallpaperRepositoryImpl) this.mWallpaperRepository).wallpaperSupportsAmbientMode, new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda10(this, 0));
        ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).prepareSafeUIBouncer();
    }

    public final boolean shouldWaitForProvisioning() {
        return (this.mUpdateMonitor.mDeviceProvisioned || isSecure()) ? false : true;
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void showDismissibleKeyguard() {
        if (!this.mFoldGracePeriodProvider.isEnabled()) {
            android.util.Log.e("SafeUIKeyguardViewMediator", "fold grace period feature isn't enabled, but showKeyguard() method is being called", new Throwable());
        } else {
            if (!this.mUpdateMonitor.mDeviceProvisioned) {
                android.util.Log.d("SafeUIKeyguardViewMediator", "Device not provisioned, so ignore request to show keyguard.");
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putBoolean("show_dismissible", true);
            showKeyguard(bundle);
        }
    }

    public final void showKeyguard(Bundle bundle) {
        Trace.beginSection("KeyguardViewMediator#showKeyguard acquiring mShowKeyguardWakeLock");
        android.util.Log.d("SafeUIKeyguardViewMediator", "showKeyguard");
        this.mShowKeyguardWakeLock.acquire();
        AnonymousClass12 anonymousClass12 = this.mHandler;
        anonymousClass12.sendMessageAtFrontOfQueue(anonymousClass12.obtainMessage(1, bundle));
        Trace.endSection();
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void showSurfaceBehindKeyguard() {
        this.mSurfaceBehindRemoteAnimationRequested = true;
        try {
            ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).getClass();
            ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(true);
            KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
            Flags.keyguardWmStateRefactor();
            this.mActivityTaskManagerService.keyguardGoingAway(6);
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

    public final void tryKeyguardDone() {
        StringBuilder sb = new StringBuilder("tryKeyguardDone: pending - ");
        sb.append(this.mKeyguardDonePending);
        sb.append(", animRan - ");
        sb.append(this.mHideAnimationRun);
        sb.append(" animRunning - ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mHideAnimationRunning, "SafeUIKeyguardViewMediator");
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

    public final void updateInputRestrictedLocked() {
        boolean isInputRestricted = isInputRestricted();
        if (this.mInputRestricted != isInputRestricted) {
            this.mInputRestricted = isInputRestricted;
            for (int size = this.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
                IKeyguardStateCallback iKeyguardStateCallback = (IKeyguardStateCallback) this.mKeyguardStateCallbacks.get(size);
                try {
                    iKeyguardStateCallback.onInputRestrictedStateChanged(isInputRestricted);
                } catch (RemoteException e) {
                    Slog.w("SafeUIKeyguardViewMediator", "Failed to call onDeviceProvisioned", e);
                    if (e instanceof DeadObjectException) {
                        this.mKeyguardStateCallbacks.remove(iKeyguardStateCallback);
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void userActivity() {
        Flags.FEATURE_FLAGS.getClass();
        this.mUiBgExecutor.execute(new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda0(this, 0));
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
                    Slog.w("SafeUIKeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e);
                }
            } else if (this.mExternallyEnabled) {
                android.util.Log.w("SafeUIKeyguardViewMediator", "verifyUnlock called when not externally disabled");
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e2) {
                    Slog.w("SafeUIKeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e2);
                }
            } else if (isSecure()) {
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e3) {
                    Slog.w("SafeUIKeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e3);
                }
            } else {
                this.mExternallyEnabled = true;
                this.mNeedToReshowWhenReenabled = false;
                synchronized (this) {
                    updateInputRestrictedLocked();
                    try {
                        iKeyguardExitCallback.onKeyguardExitResult(true);
                    } catch (RemoteException e4) {
                        Slog.w("SafeUIKeyguardViewMediator", "Failed to call onKeyguardExitResult(true)", e4);
                    }
                }
            }
        }
        Trace.endSection();
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final boolean isSecure(int i) {
        return this.mLockPatternUtils.isSecure(i) || this.mUpdateMonitor.isSimPinSecure();
    }

    public final void setShowingLocked(final boolean z, boolean z2) {
        boolean z3 = this.mDozing && !this.mWakeAndUnlocking;
        boolean z4 = this.mShowing;
        boolean z5 = z != z4 || z2;
        boolean z6 = (z == z4 && z3 == this.mAodShowing && !z2) ? false : true;
        this.mShowing = z;
        this.mAodShowing = z3;
        if (z5) {
            DejankUtils.whitelistIpcs(new Runnable() { // from class: com.android.systemui.keyguard.SafeUIKeyguardViewMediator$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = SafeUIKeyguardViewMediator.this;
                    boolean z7 = z;
                    for (int size = safeUIKeyguardViewMediator.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
                        IKeyguardStateCallback iKeyguardStateCallback = (IKeyguardStateCallback) safeUIKeyguardViewMediator.mKeyguardStateCallbacks.get(size);
                        try {
                            iKeyguardStateCallback.onShowingStateChanged(z7, safeUIKeyguardViewMediator.mSelectedUserInteractor.getSelectedUserId());
                        } catch (RemoteException e) {
                            Slog.w("SafeUIKeyguardViewMediator", "Failed to call onShowingStateChanged", e);
                            if (e instanceof DeadObjectException) {
                                safeUIKeyguardViewMediator.mKeyguardStateCallbacks.remove(iKeyguardStateCallback);
                            }
                        }
                    }
                }
            });
            updateInputRestrictedLocked();
            Executor executor = this.mUiBgExecutor;
            TrustManager trustManager = this.mTrustManager;
            Objects.requireNonNull(trustManager);
            executor.execute(new KeyguardViewMediator$$ExternalSyntheticLambda5(trustManager, 0));
        }
        if (z6) {
            this.mUiBgExecutor.execute(new SafeUIKeyguardViewMediator$$ExternalSyntheticLambda1(this, z, z3));
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardViewMediator
    public final void startKeyguardExitAnimation(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        startKeyguardExitAnimation(i, 0L, 0L, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
    }

    public final void startKeyguardExitAnimation(int i, long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        Trace.beginSection("KeyguardViewMediator#startKeyguardExitAnimation");
        this.mInteractionJankMonitor.cancel(23);
        StartKeyguardExitAnimParams startKeyguardExitAnimParams = new StartKeyguardExitAnimParams(i, j, j2, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback, 0);
        AnonymousClass12 anonymousClass12 = this.mHandler;
        anonymousClass12.sendMessage(anonymousClass12.obtainMessage(12, startKeyguardExitAnimParams));
        Trace.endSection();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class ActivityLaunchRemoteAnimationRunner extends IRemoteAnimationRunner.Stub {
        public ActivityLaunchRemoteAnimationRunner(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, ActivityTransitionAnimator.Controller controller) {
        }

        public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            if (iRemoteAnimationFinishedCallback != null) {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            }
        }

        public void onAnimationCancelled() {
        }
    }
}
