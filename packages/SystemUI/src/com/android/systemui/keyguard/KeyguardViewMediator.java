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
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.hardware.biometrics.BiometricSourceType;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Binder;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Debug;
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
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.foldables.FoldGracePeriodProvider;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardExitCallback;
import com.android.internal.policy.IKeyguardStateCallback;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.systemui.CoreStartable;
import com.android.systemui.CscRune;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.Rune;
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
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardService;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$dismissKeyguard$$inlined$launch$default$1;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.pluginlock.PluginLockInstancePolicy;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.settings.DisplayTrackerImpl;
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
import com.android.systemui.uithreadmonitor.BinderCallMonitorConstants;
import com.android.systemui.uithreadmonitor.BinderCallMonitorImpl;
import com.android.systemui.user.domain.interactor.GuestUserInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SystemSettings;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.wallpaper.BackupRestoreReceiver;
import com.android.systemui.wallpapers.data.repository.WallpaperRepository;
import com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl;
import com.android.wm.shell.keyguard.KeyguardTransitions;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.os.SemDvfsManager;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

public class KeyguardViewMediator implements CoreStartable, Dumpable, StatusBarStateController.StateListener {
    public static final Intent USER_PRESENT_INTENT = new Intent("android.intent.action.USER_PRESENT").addFlags(606076928);
    public static final Bundle USER_PRESENT_INTENT_OPTIONS = BroadcastOptions.makeBasic().setDeferralPolicy(2).setDeliveryGroupPolicy(1).toBundle();
    public final Lazy mActivityTransitionAnimator;
    public AlarmManager mAlarmManager;
    public boolean mAnimatingScreenOff;
    public boolean mAodShowing;
    public final AnonymousClass7 mAppearAnimationRunner;
    public boolean mBootCompleted;
    public boolean mBootSendUserPresent;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final AnonymousClass12 mBroadcastReceiver;
    public CentralSurfaces mCentralSurfaces;
    public final Lazy mCommunalTransitionViewModel;
    public final Context mContext;
    public CharSequence mCustomMessage;
    public final AnonymousClass11 mDelayedLockBroadcastReceiver;
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
    public final FalsingCollector mFalsingCollector;
    protected FoldGracePeriodProvider mFoldGracePeriodProvider;
    public boolean mGoingToSleep;
    public final AnonymousClass13 mHandler;
    public final KeyguardViewMediatorHelperImpl mHelper;
    public Animation mHideAnimation;
    public final KeyguardViewMediator$$ExternalSyntheticLambda1 mHideAnimationFinishedRunnable;
    public boolean mHiding;
    public boolean mInGestureNavigationMode;
    public boolean mInputRestricted;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final JavaAdapter mJavaAdapter;
    public final KeyguardDisplayManager mKeyguardDisplayManager;
    public IRemoteAnimationRunner mKeyguardExitAnimationRunner;
    public final AnonymousClass14 mKeyguardGoingAwayRunnable;
    public final KeyguardInteractor mKeyguardInteractor;
    public final ArrayList mKeyguardStateCallbacks;
    public final KeyguardStateController mKeyguardStateController;
    public final AnonymousClass10 mKeyguardStateControllerCallback;
    public final KeyguardTransitions mKeyguardTransitions;
    public final Lazy mKeyguardUnlockAnimationControllerLazy;
    public final Lazy mKeyguardViewControllerLazy;
    public boolean mLockLater;
    public final LockPatternUtils mLockPatternUtils;
    public int mLockSoundId;
    public SoundPool mLockSounds;
    public final Lazy mNotificationShadeDepthController;
    public final Lazy mNotificationShadeWindowControllerLazy;
    final ActivityTransitionAnimator.Controller mOccludeAnimationController;
    public final AnonymousClass8 mOccludeByDreamAnimationRunner;
    public RemoteAnimationTarget mOccludingRemoteAnimationTarget;
    public final AnonymousClass1 mOnPropertiesChangedListener;
    public final boolean mOrderUnlockAndWake;
    public final PowerManager mPM;
    public boolean mPendingLock;
    public boolean mPendingPinLock;
    public boolean mPendingReset;
    public final float mPowerButtonY;
    public boolean mPowerGestureIntercepted;
    public RemoteAnimationTarget mRemoteAnimationTarget;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final Lazy mScrimControllerLazy;
    public final SecureSettings mSecureSettings;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final SessionTracker mSessionTracker;
    public final Lazy mShadeController;
    public boolean mShowCommunalWhenUnoccluding;
    public boolean mShowHomeOverLockscreen;
    public final PowerManager.WakeLock mShowKeyguardWakeLock;
    public boolean mShowing;
    public boolean mShuttingDown;
    public StatusBarManager mStatusBarManager;
    public final IStatusBarService mStatusBarService;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public IRemoteAnimationFinishedCallback mSurfaceBehindRemoteAnimationFinishedCallback;
    public boolean mSurfaceBehindRemoteAnimationRequested;
    public boolean mSurfaceBehindRemoteAnimationRunning;
    public final SystemClock mSystemClock;
    public final SystemPropertiesHelper mSystemPropertiesHelper;
    public boolean mSystemReady;
    public final SystemSettings mSystemSettings;
    public final TrustManager mTrustManager;
    public int mTrustedSoundId;
    public final Executor mUiBgExecutor;
    public final UiEventLogger mUiEventLogger;
    public int mUnlockSoundId;
    public IRemoteAnimationFinishedCallback mUnoccludeFinishedCallback;
    public final KeyguardUpdateMonitorCallback mUpdateCallback;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final UserSwitcherController mUserSwitcherController;
    public final UserTracker mUserTracker;
    public final AnonymousClass4 mViewMediatorCallback;
    public final WallpaperRepository mWallpaperRepository;
    public boolean mWallpaperSupportsAmbientMode;
    public final float mWindowCornerRadius;
    public final IBinder mStatusBarDisableToken = new Binder();
    public boolean mExternallyEnabled = true;
    public boolean mNeedToReshowWhenReenabled = false;
    public boolean mOccluded = false;
    public boolean mOccludeAnimationPlaying = false;
    public boolean mWakeAndUnlocking = false;
    public final SparseIntArray mLastSimStates = new SparseIntArray();
    public final SparseBooleanArray mSimWasLocked = new SparseBooleanArray();
    public String mPhoneState = TelephonyManager.EXTRA_STATE_IDLE;
    public boolean mWaitingUntilKeyguardVisible = false;
    public boolean mKeyguardDonePending = false;
    public boolean mUnlockingAndWakingFromDream = false;
    public boolean mHideAnimationRun = false;
    public boolean mHideAnimationRunning = false;

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$13, reason: invalid class name */
    public final class AnonymousClass13 extends Handler {
        public AnonymousClass13(Looper looper, Handler.Callback callback, boolean z) {
            super(looper, callback, z);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            byte b;
            KeyguardViewMediator keyguardViewMediator;
            int i = 0;
            int i2 = 0;
            int i3 = 2;
            int i4 = 1;
            int i5 = 3;
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediator.this.mHelper;
            Objects.requireNonNull(keyguardViewMediatorHelperImpl);
            KeyguardViewMediator$$ExternalSyntheticLambda6 keyguardViewMediator$$ExternalSyntheticLambda6 = new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl, i5);
            boolean z = Rune.SYSUI_MULTI_SIM;
            keyguardViewMediator$$ExternalSyntheticLambda6.accept(message);
            String str = "";
            switch (message.what) {
                case 1:
                    str = "SHOW";
                    KeyguardViewMediator.m1953$$Nest$mhandleShow(KeyguardViewMediator.this, (Bundle) message.obj);
                    break;
                case 2:
                    str = "HIDE";
                    KeyguardViewMediator.this.handleHide$1();
                    break;
                case 3:
                    str = "RESET";
                    KeyguardViewMediator.m1951$$Nest$mhandleReset(KeyguardViewMediator.this, message.arg1 != 0);
                    break;
                case 4:
                case 6:
                case 15:
                case 16:
                default:
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                    keyguardViewMediatorHelperImpl2.handleSecMessage(message);
                    break;
                case 5:
                    str = "NOTIFY_FINISHED_GOING_TO_SLEEP";
                    KeyguardViewMediator.m1949$$Nest$mhandleNotifyFinishedGoingToSleep(KeyguardViewMediator.this);
                    break;
                case 7:
                    str = "KEYGUARD_DONE";
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE");
                    KeyguardViewMediator.this.handleKeyguardDone$1();
                    Trace.endSection();
                    break;
                case 8:
                    str = "KEYGUARD_DONE_DRAWING";
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_DRAWING");
                    KeyguardViewMediator.m1948$$Nest$mhandleKeyguardDoneDrawing(KeyguardViewMediator.this);
                    Trace.endSection();
                    break;
                case 9:
                    str = "SET_OCCLUDED";
                    Trace.beginSection("KeyguardViewMediator#handleMessage SET_OCCLUDED");
                    KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                    boolean z2 = message.arg1 != 0;
                    boolean z3 = message.arg2 != 0;
                    Object obj = message.obj;
                    KeyguardViewMediator.m1952$$Nest$mhandleSetOccluded(keyguardViewMediator2, z2, z3, obj != null ? ((Integer) obj).intValue() : -1);
                    Trace.endSection();
                    break;
                case 10:
                    str = "KEYGUARD_TIMEOUT";
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator.this.doKeyguardLocked((Bundle) message.obj, false);
                    }
                    break;
                case 11:
                    str = "DISMISS";
                    DismissMessage dismissMessage = (DismissMessage) message.obj;
                    KeyguardViewMediator keyguardViewMediator3 = KeyguardViewMediator.this;
                    IKeyguardDismissCallback iKeyguardDismissCallback = dismissMessage.mCallback;
                    CharSequence charSequence = dismissMessage.mMessage;
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = keyguardViewMediator3.mHelper;
                    if (keyguardViewMediatorHelperImpl3.isShowing$1()) {
                        boolean z4 = CscRune.SECURITY_SIM_PERM_DISABLED;
                        KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediatorHelperImpl3.updateMonitor;
                        if (z4 && keyguardUpdateMonitor.isIccBlockedPermanently()) {
                            KeyguardViewMediatorHelperImpl.logD("dismiss failed. Permanent state.");
                            b = iKeyguardDismissCallback != null ? (byte) 1 : (byte) 0;
                            i2 = 1;
                        } else if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && keyguardViewMediatorHelperImpl3.isSecure$2() && !keyguardUpdateMonitor.getUserCanSkipBouncer(((UserTrackerImpl) keyguardViewMediatorHelperImpl3.userTracker).getUserId()) && !keyguardViewMediatorHelperImpl3.foldControllerImpl.isFoldOpened()) {
                            b = iKeyguardDismissCallback != null ? (byte) 1 : (byte) 0;
                            i2 = 2;
                        } else if (keyguardViewMediatorHelperImpl3.isKeyguardHiding()) {
                            if (iKeyguardDismissCallback != null) {
                                keyguardViewMediatorHelperImpl3.dismissCallbackRegistry.mDismissCallbacks.add(new DismissCallbackWrapper(iKeyguardDismissCallback));
                            }
                            b = false;
                            i2 = 3;
                        } else {
                            b = false;
                        }
                        if (b != false) {
                            new DismissCallbackWrapper(iKeyguardDismissCallback).notifyDismissError();
                        }
                        if (i2 != 0) {
                            KeyguardViewMediatorHelperImpl.logD("handleDismiss reason=" + i2);
                            break;
                        }
                    }
                    if (!keyguardViewMediator3.mShowing) {
                        android.util.Log.w("KeyguardViewMediator", "Ignoring request to DISMISS because mShowing=false");
                        if (iKeyguardDismissCallback != null) {
                            new DismissCallbackWrapper(iKeyguardDismissCallback).notifyDismissError();
                            break;
                        }
                    } else {
                        if (iKeyguardDismissCallback != null) {
                            keyguardViewMediator3.mDismissCallbackRegistry.mDismissCallbacks.add(new DismissCallbackWrapper(iKeyguardDismissCallback));
                        }
                        keyguardViewMediator3.mCustomMessage = charSequence;
                        if (!keyguardViewMediator3.mHiding) {
                            ((KeyguardViewController) keyguardViewMediator3.mKeyguardViewControllerLazy.get()).dismissAndCollapse();
                            break;
                        }
                    }
                    break;
                case 12:
                    str = "START_KEYGUARD_EXIT_ANIM";
                    Trace.beginSection("KeyguardViewMediator#handleMessage START_KEYGUARD_EXIT_ANIM");
                    synchronized (KeyguardViewMediator.this) {
                        keyguardViewMediator = KeyguardViewMediator.this;
                        keyguardViewMediator.mHiding = true;
                    }
                    ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) keyguardViewMediator.mNotificationShadeWindowControllerLazy.get())).batchApplyWindowLayoutParams(new KeyguardViewMediator$$ExternalSyntheticLambda67(i3, this, (StartKeyguardExitAnimParams) message.obj));
                    Trace.endSection();
                    break;
                case 13:
                    str = "KEYGUARD_DONE_PENDING_TIMEOUT";
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_PENDING_TIMEOUT");
                    android.util.Log.w("KeyguardViewMediator", "Timeout while waiting for activity drawn!");
                    Trace.endSection();
                    break;
                case 14:
                    str = "NOTIFY_STARTED_WAKING_UP";
                    Trace.beginSection("KeyguardViewMediator#handleMessage NOTIFY_STARTED_WAKING_UP");
                    KeyguardViewMediator.m1950$$Nest$mhandleNotifyStartedWakingUp(KeyguardViewMediator.this);
                    Trace.endSection();
                    break;
                case 17:
                    str = "NOTIFY_STARTED_GOING_TO_SLEEP";
                    KeyguardViewMediator keyguardViewMediator4 = KeyguardViewMediator.this;
                    synchronized (keyguardViewMediator4) {
                        android.util.Log.d("KeyguardViewMediator", "handleNotifyStartedGoingToSleep");
                        ((KeyguardViewController) keyguardViewMediator4.mKeyguardViewControllerLazy.get()).onStartedGoingToSleep();
                    }
                    break;
                case 18:
                    str = "SYSTEM_READY";
                    KeyguardViewMediator keyguardViewMediator5 = KeyguardViewMediator.this;
                    synchronized (keyguardViewMediator5) {
                        android.util.Log.d("KeyguardViewMediator", "onSystemReady");
                        keyguardViewMediator5.mSystemReady = true;
                        keyguardViewMediator5.doKeyguardLocked(null, false);
                        keyguardViewMediator5.mUpdateMonitor.registerCallback(keyguardViewMediator5.mUpdateCallback);
                        keyguardViewMediator5.adjustStatusBarLocked$1(false, false);
                        keyguardViewMediator5.mDreamOverlayStateController.addCallback(keyguardViewMediator5.mDreamOverlayStateCallback);
                        DreamViewModel dreamViewModel = (DreamViewModel) keyguardViewMediator5.mDreamViewModel.get();
                        CommunalTransitionViewModel communalTransitionViewModel = (CommunalTransitionViewModel) keyguardViewMediator5.mCommunalTransitionViewModel.get();
                        keyguardViewMediator5.mJavaAdapter.alwaysCollectFlow(dreamViewModel.dreamAlpha, new KeyguardViewMediator$$ExternalSyntheticLambda79(keyguardViewMediator5, i5));
                        keyguardViewMediator5.mJavaAdapter.alwaysCollectFlow(dreamViewModel.transitionEnded, new KeyguardViewMediator$$ExternalSyntheticLambda79(keyguardViewMediator5, i4));
                        keyguardViewMediator5.mJavaAdapter.alwaysCollectFlow(communalTransitionViewModel.showCommunalFromOccluded, new KeyguardViewMediator$$ExternalSyntheticLambda79(keyguardViewMediator5, r0 ? 1 : 0));
                        keyguardViewMediator5.mJavaAdapter.alwaysCollectFlow(communalTransitionViewModel.transitionFromOccludedEnded, new KeyguardViewMediator$$ExternalSyntheticLambda79(keyguardViewMediator5, i4));
                    }
                    keyguardViewMediator5.maybeSendUserPresentBroadcast$1();
                    break;
                case 19:
                    str = "CANCEL_KEYGUARD_EXIT_ANIM";
                    Trace.beginSection("KeyguardViewMediator#handleMessage CANCEL_KEYGUARD_EXIT_ANIM");
                    KeyguardViewMediator keyguardViewMediator6 = KeyguardViewMediator.this;
                    if (keyguardViewMediator6.mPendingLock) {
                        android.util.Log.d("KeyguardViewMediator", "#handleCancelKeyguardExitAnimation: keyguard exit animation cancelled. There's a pending lock, so we were cancelled because the device was locked again during the unlock sequence. We should end up locked.");
                        keyguardViewMediator6.finishSurfaceBehindRemoteAnimation(true);
                        keyguardViewMediator6.maybeHandlePendingLock();
                    } else {
                        android.util.Log.d("KeyguardViewMediator", "#handleCancelKeyguardExitAnimation: keyguard exit animation cancelled. No pending lock, we should end up unlocked with the app/launcher visible.");
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = keyguardViewMediator6.mHelper;
                        Objects.requireNonNull(keyguardViewMediatorHelperImpl4);
                        if (keyguardViewMediatorHelperImpl4.isUnlockStartedOrFinished()) {
                            i = 1;
                        } else if (!((KeyguardViewMediator) keyguardViewMediatorHelperImpl4.viewMediatorLazy.get()).isHiding()) {
                            ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl4.viewMediatorProvider;
                            (viewMediatorProvider != null ? viewMediatorProvider : null).setShowingLocked.invoke(Boolean.valueOf(keyguardViewMediatorHelperImpl4.isShowing$1()), Boolean.TRUE);
                            keyguardViewMediatorHelperImpl4.onAbortHandleStartKeyguardExitAnimation();
                            i = 2;
                        }
                        if (i != 0) {
                            KeyguardViewMediatorHelperImpl.logD("cancel handleCancelKeyguardExitAnimation why=" + i);
                        } else {
                            keyguardViewMediator6.showSurfaceBehindKeyguard();
                            keyguardViewMediator6.exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
                        }
                    }
                    Trace.endSection();
                    break;
            }
            android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = KeyguardViewMediator.this.mHelper;
            Objects.requireNonNull(keyguardViewMediatorHelperImpl5);
            keyguardViewMediatorHelperImpl5.postHandleMsg(message);
        }
    }

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$14, reason: invalid class name */
    public final class AnonymousClass14 implements Runnable {
        public AnonymousClass14() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0056, code lost:
        
            if (r2.mWallpaperSupportsAmbientMode != false) goto L16;
         */
        /* JADX WARN: Removed duplicated region for block: B:10:0x004e  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0069  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x007e  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                r4 = this;
                r0 = 1
                java.lang.String r1 = "KeyguardViewMediator.mKeyGuardGoingAwayRunnable"
                android.os.Trace.beginSection(r1)
                java.lang.String r1 = "KeyguardViewMediator"
                java.lang.String r2 = "keyguardGoingAway"
                android.util.Log.d(r1, r2)
                com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                dagger.Lazy r1 = r1.mKeyguardViewControllerLazy
                java.lang.Object r1 = r1.get()
                com.android.keyguard.KeyguardViewController r1 = (com.android.keyguard.KeyguardViewController) r1
                r1.keyguardGoingAway()
                com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                dagger.Lazy r1 = r1.mKeyguardViewControllerLazy
                java.lang.Object r1 = r1.get()
                com.android.keyguard.KeyguardViewController r1 = (com.android.keyguard.KeyguardViewController) r1
                r1.getClass()
                com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl r1 = r1.mHelper
                boolean r1 = r1.isEnabledBiometricUnlockVI()
                if (r1 != 0) goto L3d
                com.android.systemui.keyguard.KeyguardViewMediator r1 = com.android.systemui.keyguard.KeyguardViewMediator.this
                boolean r2 = r1.mWakeAndUnlocking
                if (r2 == 0) goto L3d
                boolean r1 = r1.mWallpaperSupportsAmbientMode
                if (r1 != 0) goto L3d
                r1 = 2
                goto L3e
            L3d:
                r1 = 0
            L3e:
                com.android.systemui.keyguard.KeyguardViewMediator r2 = com.android.systemui.keyguard.KeyguardViewMediator.this
                dagger.Lazy r2 = r2.mKeyguardViewControllerLazy
                java.lang.Object r2 = r2.get()
                com.android.keyguard.KeyguardViewController r2 = (com.android.keyguard.KeyguardViewController) r2
                boolean r2 = r2.isGoingToNotificationShade()
                if (r2 != 0) goto L58
                com.android.systemui.keyguard.KeyguardViewMediator r2 = com.android.systemui.keyguard.KeyguardViewMediator.this
                boolean r3 = r2.mWakeAndUnlocking
                if (r3 == 0) goto L59
                boolean r2 = r2.mWallpaperSupportsAmbientMode
                if (r2 == 0) goto L59
            L58:
                r1 = r1 | r0
            L59:
                com.android.systemui.keyguard.KeyguardViewMediator r2 = com.android.systemui.keyguard.KeyguardViewMediator.this
                dagger.Lazy r2 = r2.mKeyguardViewControllerLazy
                java.lang.Object r2 = r2.get()
                com.android.keyguard.KeyguardViewController r2 = (com.android.keyguard.KeyguardViewController) r2
                boolean r2 = r2.isUnlockWithWallpaper()
                if (r2 == 0) goto L6b
                r1 = r1 | 4
            L6b:
                com.android.systemui.keyguard.KeyguardViewMediator r2 = com.android.systemui.keyguard.KeyguardViewMediator.this
                dagger.Lazy r2 = r2.mKeyguardViewControllerLazy
                java.lang.Object r2 = r2.get()
                com.android.keyguard.KeyguardViewController r2 = (com.android.keyguard.KeyguardViewController) r2
                r2.shouldSubtleWindowAnimationsForUnlock()
                com.android.systemui.keyguard.KeyguardViewMediator r2 = com.android.systemui.keyguard.KeyguardViewMediator.this
                boolean r3 = r2.mWakeAndUnlocking
                if (r3 == 0) goto L89
                dagger.Lazy r2 = r2.mKeyguardUnlockAnimationControllerLazy
                java.lang.Object r2 = r2.get()
                com.android.systemui.keyguard.KeyguardUnlockAnimationController r2 = (com.android.systemui.keyguard.KeyguardUnlockAnimationController) r2
                r2.getClass()
            L89:
                com.android.systemui.keyguard.KeyguardViewMediator r2 = com.android.systemui.keyguard.KeyguardViewMediator.this
                com.android.keyguard.KeyguardUpdateMonitor r2 = r2.mUpdateMonitor
                r2.setKeyguardGoingAway(r0)
                com.android.systemui.keyguard.KeyguardViewMediator r2 = com.android.systemui.keyguard.KeyguardViewMediator.this
                dagger.Lazy r2 = r2.mKeyguardViewControllerLazy
                java.lang.Object r2 = r2.get()
                com.android.keyguard.KeyguardViewController r2 = (com.android.keyguard.KeyguardViewController) r2
                r2.setKeyguardGoingAwayState(r0)
                com.android.systemui.keyguard.KeyguardWmStateRefactor r0 = com.android.systemui.keyguard.KeyguardWmStateRefactor.INSTANCE
                com.android.systemui.Flags.keyguardWmStateRefactor()
                com.android.systemui.keyguard.KeyguardViewMediator r4 = com.android.systemui.keyguard.KeyguardViewMediator.this
                com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl r4 = r4.mHelper
                r4.setKeyguardGoingAway(r1)
                android.os.Trace.endSection()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediator.AnonymousClass14.run():void");
        }
    }

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$17, reason: invalid class name */
    public final class AnonymousClass17 extends IRemoteAnimationRunner.Stub {
        public final /* synthetic */ IRemoteAnimationRunner val$wrapped;

        public AnonymousClass17(IRemoteAnimationRunner iRemoteAnimationRunner) {
            this.val$wrapped = iRemoteAnimationRunner;
        }

        public final void onAnimationCancelled() {
            this.val$wrapped.onAnimationCancelled();
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            if (((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getViewRootImpl() != null) {
                this.val$wrapped.onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
                return;
            }
            android.util.Log.w("KeyguardViewMediator", "Skipping remote animation - view root not ready");
            if (iRemoteAnimationFinishedCallback != null) {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            }
        }
    }

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$4, reason: invalid class name */
    public final class AnonymousClass4 implements ViewMediatorCallback {
        public AnonymousClass4() {
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final CharSequence consumeCustomMessage() {
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            CharSequence charSequence = keyguardViewMediator.mCustomMessage;
            keyguardViewMediator.mCustomMessage = null;
            return charSequence;
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final int getBouncerPromptReason() {
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            if (keyguardViewMediator.mUpdateMonitor.is2StepVerification()) {
                return 0;
            }
            int selectedUserId = keyguardViewMediator.mSelectedUserInteractor.getSelectedUserId(false);
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediator.mUpdateMonitor;
            keyguardUpdateMonitor.getClass();
            Assert.isMainThread();
            boolean z = keyguardUpdateMonitor.mUserTrustIsUsuallyManaged.get(selectedUserId);
            boolean z2 = z || (keyguardUpdateMonitor.isUnlockWithFacePossible(selectedUserId) || keyguardUpdateMonitor.isUnlockWithFingerprintPossible(selectedUserId));
            KeyguardUpdateMonitor.StrongAuthTracker strongAuthTracker = keyguardUpdateMonitor.mStrongAuthTracker;
            int strongAuthForUser = strongAuthTracker.getStrongAuthForUser(selectedUserId);
            boolean isNonStrongBiometricAllowedAfterIdleTimeout = strongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(selectedUserId);
            if (z2 && !strongAuthTracker.hasUserAuthenticatedSinceBoot()) {
                keyguardViewMediator.mSystemPropertiesHelper.getClass();
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
                return (!z2 || isNonStrongBiometricAllowedAfterIdleTimeout) ? 0 : 17;
            }
            return 7;
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final boolean isScreenOn() {
            return KeyguardViewMediator.this.mDeviceInteractive;
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void keyguardDone(int i) {
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediator.mHelper;
            Objects.requireNonNull(keyguardViewMediatorHelperImpl);
            boolean z = Rune.SYSUI_MULTI_SIM;
            keyguardViewMediatorHelperImpl.fastUnlockController.logLapTime("keyguardDone", new Object[0]);
            if (i != keyguardViewMediator.mSelectedUserInteractor.getSelectedUserId(false)) {
                Log.d("KeyguardViewMediator", "tryKeyguardDone skipped. target=%d,cur=%d", Integer.valueOf(i), Integer.valueOf(ActivityManager.getCurrentUser()));
            } else {
                android.util.Log.d("KeyguardViewMediator", "keyguardDone");
                keyguardViewMediator.tryKeyguardDone$1();
            }
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void keyguardDoneDrawing() {
            Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardDoneDrawing");
            KeyguardViewMediator.this.mHandler.sendEmptyMessage(8);
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void keyguardDonePending(int i) {
            Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardDonePending");
            android.util.Log.d("KeyguardViewMediator", "keyguardDonePending");
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            if (i != keyguardViewMediator.mSelectedUserInteractor.getSelectedUserId(false)) {
                Trace.endSection();
                return;
            }
            keyguardViewMediator.mKeyguardDonePending = true;
            keyguardViewMediator.mHideAnimationRun = true;
            keyguardViewMediator.mHideAnimationRunning = true;
            ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).startPreHideAnimation(keyguardViewMediator.mHideAnimationFinishedRunnable);
            keyguardViewMediator.mHandler.sendEmptyMessageDelayed(13, 3000L);
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void keyguardGone() {
            Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardGone");
            android.util.Log.d("KeyguardViewMediator", "keyguardGone");
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(false);
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediator.mHelper;
            Objects.requireNonNull(keyguardViewMediatorHelperImpl);
            boolean z = Rune.SYSUI_MULTI_SIM;
            keyguardViewMediatorHelperImpl.onKeyguardGone();
            keyguardViewMediator.mUpdateMonitor.startBiometricWatchdog();
            if (keyguardViewMediator.mUnlockingAndWakingFromDream) {
                android.util.Log.d("KeyguardViewMediator", "waking from dream after unlock");
                keyguardViewMediator.setUnlockAndWakeFromDream$1(2, false);
                if (((KeyguardStateControllerImpl) keyguardViewMediator.mKeyguardStateController).mShowing) {
                    android.util.Log.d("KeyguardViewMediator", "keyguard showing after keyguardGone, dismiss");
                    ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).notifyKeyguardAuthenticated(!keyguardViewMediator.mWakeAndUnlocking);
                } else {
                    android.util.Log.d("KeyguardViewMediator", "keyguard gone, waking up from dream");
                    keyguardViewMediator.mPM.wakeUp(keyguardViewMediator.mSystemClock.uptimeMillis(), keyguardViewMediator.mWakeAndUnlocking ? 17 : 4, "com.android.systemui:UNLOCK_DREAMING");
                }
            }
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void onCancelClicked() {
            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getClass();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void playTrustedSound() {
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            int i = keyguardViewMediator.mTrustedSoundId;
            if (i == 0) {
                return;
            }
            keyguardViewMediator.mHelper.playSound$2(i);
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void readyForKeyguardDone() {
            Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#readyForKeyguardDone");
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            if (keyguardViewMediator.mKeyguardDonePending) {
                keyguardViewMediator.mKeyguardDonePending = false;
                keyguardViewMediator.tryKeyguardDone$1();
            }
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void resetKeyguard() {
            Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
            KeyguardViewMediator.this.resetStateLocked$1(true);
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void setCustomMessage(CharSequence charSequence) {
            KeyguardViewMediator.this.mCustomMessage = charSequence;
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void setNeedsInput(boolean z) {
            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).setNeedsInput(z);
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void userActivity() {
            boolean z = LsRune.KEYGUARD_ADJUST_REFRESH_RATE_USER_ACTIVITY;
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            if (z) {
                keyguardViewMediator.mHelper.pm.userActivity(android.os.SystemClock.uptimeMillis(), 2, 0);
            } else {
                keyguardViewMediator.userActivity();
            }
        }
    }

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$8, reason: invalid class name */
    public final class AnonymousClass8 extends IRemoteAnimationRunner.Stub {
        public ValueAnimator mOccludeByDreamAnimator;

        public AnonymousClass8() {
        }

        public final void onAnimationCancelled() {
            KeyguardViewMediator.this.mContext.getMainExecutor().execute(new KeyguardViewMediator$$ExternalSyntheticLambda5(this, 1));
            android.util.Log.d("KeyguardViewMediator", "OccludeByDreamAnimator#onAnimationCancelled. Set occluded = true");
            KeyguardViewMediator.this.setOccluded(true, false);
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            final RemoteAnimationTarget remoteAnimationTarget;
            if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0 || (remoteAnimationTarget = remoteAnimationTargetArr[0]) == null) {
                android.util.Log.d("KeyguardViewMediator", "No apps provided to the OccludeByDream runner; skipping occluding animation.");
            } else {
                ActivityManager.RunningTaskInfo runningTaskInfo = remoteAnimationTarget.taskInfo;
                if (runningTaskInfo != null && runningTaskInfo.topActivityType == 5) {
                    final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
                    KeyguardViewMediator.this.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$8$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            final KeyguardViewMediator.AnonymousClass8 anonymousClass8 = KeyguardViewMediator.AnonymousClass8.this;
                            RemoteAnimationTarget remoteAnimationTarget2 = remoteAnimationTarget;
                            SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier2 = syncRtSurfaceTransactionApplier;
                            final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                            ValueAnimator valueAnimator = anonymousClass8.mOccludeByDreamAnimator;
                            if (valueAnimator != null) {
                                valueAnimator.cancel();
                            }
                            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                            anonymousClass8.mOccludeByDreamAnimator = ofFloat;
                            ofFloat.setDuration(KeyguardViewMediator.this.mDreamOpenAnimationDuration);
                            anonymousClass8.mOccludeByDreamAnimator.addUpdateListener(new KeyguardViewMediator$$ExternalSyntheticLambda92(remoteAnimationTarget2, syncRtSurfaceTransactionApplier2, 1));
                            anonymousClass8.mOccludeByDreamAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.8.1
                                public boolean mIsCancelled = false;

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationCancel(Animator animator) {
                                    this.mIsCancelled = true;
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    try {
                                        if (!this.mIsCancelled) {
                                            KeyguardViewMediator.m1952$$Nest$mhandleSetOccluded(KeyguardViewMediator.this, true, false, -1);
                                        }
                                        iRemoteAnimationFinishedCallback2.onAnimationFinished();
                                        AnonymousClass8.this.mOccludeByDreamAnimator = null;
                                    } catch (RemoteException e) {
                                        android.util.Log.e("KeyguardViewMediator", "Failed to finish transition", e);
                                    }
                                }
                            });
                            anonymousClass8.mOccludeByDreamAnimator.start();
                        }
                    });
                    return;
                }
                android.util.Log.w("KeyguardViewMediator", "The occluding app isn't Dream; finishing up. Please check that the config is correct.");
            }
            KeyguardViewMediator.this.setOccluded(true, false);
            iRemoteAnimationFinishedCallback.onAnimationFinished();
        }
    }

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$9, reason: invalid class name */
    public final class AnonymousClass9 extends IRemoteAnimationRunner.Stub {
        public ValueAnimator mUnoccludeAnimator;
        public final Matrix mUnoccludeMatrix = new Matrix();

        public AnonymousClass9() {
        }

        public final void onAnimationCancelled() {
            KeyguardViewMediator.this.mContext.getMainExecutor().execute(new KeyguardViewMediator$$ExternalSyntheticLambda5(this, 2));
            android.util.Log.d("KeyguardViewMediator", "Unocclude animation cancelled.");
            KeyguardViewMediator.this.mInteractionJankMonitor.cancel(64);
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, final RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            RemoteAnimationTarget remoteAnimationTarget;
            android.util.Log.d("KeyguardViewMediator", "UnoccludeAnimator#onAnimationStart. Set occluded = false.");
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            keyguardViewMediator.mInteractionJankMonitor.begin(keyguardViewMediator.createInteractionJankMonitorConf$1(64, null).setTag("UNOCCLUDE"));
            KeyguardViewMediator.this.setOccluded(false, true);
            if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0 || (remoteAnimationTarget = remoteAnimationTargetArr[0]) == null) {
                android.util.Log.d("KeyguardViewMediator", "No apps provided to unocclude runner; skipping animation and unoccluding.");
                iRemoteAnimationFinishedCallback.onAnimationFinished();
                return;
            }
            KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
            keyguardViewMediator2.mRemoteAnimationTarget = remoteAnimationTarget;
            ActivityManager.RunningTaskInfo runningTaskInfo = remoteAnimationTarget.taskInfo;
            final boolean z = runningTaskInfo != null && runningTaskInfo.topActivityType == 5;
            final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(((KeyguardViewController) keyguardViewMediator2.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
            KeyguardViewMediator.this.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$9$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    int i2 = 2;
                    final KeyguardViewMediator.AnonymousClass9 anonymousClass9 = KeyguardViewMediator.AnonymousClass9.this;
                    boolean z2 = z;
                    RemoteAnimationTarget[] remoteAnimationTargetArr4 = remoteAnimationTargetArr2;
                    final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                    SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier2 = syncRtSurfaceTransactionApplier;
                    ValueAnimator valueAnimator = anonymousClass9.mUnoccludeAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.cancel();
                    }
                    if (z2 || KeyguardViewMediator.this.mShowCommunalWhenUnoccluding) {
                        KeyguardViewMediator.initAlphaForAnimationTargets(remoteAnimationTargetArr4);
                        if (z2) {
                            ((DreamViewModel) KeyguardViewMediator.this.mDreamViewModel.get()).startTransitionFromDream();
                        }
                        KeyguardViewMediator.this.mUnoccludeFinishedCallback = iRemoteAnimationFinishedCallback2;
                        return;
                    }
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
                    anonymousClass9.mUnoccludeAnimator = ofFloat;
                    ofFloat.setDuration(250L);
                    anonymousClass9.mUnoccludeAnimator.setInterpolator(Interpolators.TOUCH_RESPONSE);
                    anonymousClass9.mUnoccludeAnimator.addUpdateListener(new KeyguardViewMediator$$ExternalSyntheticLambda92(anonymousClass9, syncRtSurfaceTransactionApplier2, i2));
                    anonymousClass9.mUnoccludeAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.9.1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            try {
                                iRemoteAnimationFinishedCallback2.onAnimationFinished();
                                AnonymousClass9 anonymousClass92 = AnonymousClass9.this;
                                anonymousClass92.mUnoccludeAnimator = null;
                                KeyguardViewMediator.this.mInteractionJankMonitor.end(64);
                            } catch (RemoteException e) {
                                android.util.Log.e("KeyguardViewMediator", "Failed to finish transition", e);
                            }
                        }
                    });
                    anonymousClass9.mUnoccludeAnimator.start();
                }
            });
        }
    }

    public class ActivityLaunchRemoteAnimationRunner extends IRemoteAnimationRunner.Stub {
        public final ActivityTransitionAnimator.Controller mActivityLaunchController;
        public ActivityTransitionAnimator.Runner mRunner;

        public ActivityLaunchRemoteAnimationRunner(ActivityTransitionAnimator.Controller controller) {
            this.mActivityLaunchController = controller;
        }

        public void onAnimationCancelled() {
            ActivityTransitionAnimator.Runner runner = this.mRunner;
            if (runner != null) {
                runner.onAnimationCancelled();
            }
        }

        public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            ActivityTransitionAnimator.Runner createRunner = ((ActivityTransitionAnimator) KeyguardViewMediator.this.mActivityTransitionAnimator.get()).createRunner(this.mActivityLaunchController);
            this.mRunner = createRunner;
            createRunner.onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
        }
    }

    public final class DismissMessage {
        public final IKeyguardDismissCallback mCallback;
        public final CharSequence mMessage;

        public DismissMessage(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
            this.mCallback = iKeyguardDismissCallback;
            this.mMessage = charSequence;
        }
    }

    public final class OccludeActivityLaunchRemoteAnimationRunner extends ActivityLaunchRemoteAnimationRunner {
        public OccludeActivityLaunchRemoteAnimationRunner(ActivityTransitionAnimator.Controller controller) {
            super(controller);
        }

        @Override // com.android.systemui.keyguard.KeyguardViewMediator.ActivityLaunchRemoteAnimationRunner
        public final void onAnimationCancelled() {
            super.onAnimationCancelled();
            android.util.Log.d("KeyguardViewMediator", "Occlude animation cancelled by WM.");
            KeyguardViewMediator.this.mInteractionJankMonitor.cancel(64);
        }

        @Override // com.android.systemui.keyguard.KeyguardViewMediator.ActivityLaunchRemoteAnimationRunner
        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            if (remoteAnimationTargetArr.length > 0) {
                KeyguardViewMediator.this.mOccludingRemoteAnimationTarget = remoteAnimationTargetArr[0];
            }
            super.onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            keyguardViewMediator.mInteractionJankMonitor.begin(keyguardViewMediator.createInteractionJankMonitorConf$1(64, null).setTag("OCCLUDE"));
            android.util.Log.d("KeyguardViewMediator", "OccludeAnimator#onAnimationStart. Set occluded = true.");
            KeyguardViewMediator.this.setOccluded(true, false);
        }
    }

    public final class StartKeyguardExitAnimParams {
        public final long fadeoutDuration;
        public RemoteAnimationTarget[] mApps;
        public IRemoteAnimationFinishedCallback mFinishedCallback;
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

    /* renamed from: -$$Nest$mhandleKeyguardDoneDrawing, reason: not valid java name */
    public static void m1948$$Nest$mhandleKeyguardDoneDrawing(KeyguardViewMediator keyguardViewMediator) {
        keyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDoneDrawing");
        synchronized (keyguardViewMediator) {
            try {
                android.util.Log.d("KeyguardViewMediator", "handleKeyguardDoneDrawing");
                if (keyguardViewMediator.mWaitingUntilKeyguardVisible) {
                    android.util.Log.d("KeyguardViewMediator", "handleKeyguardDoneDrawing: notifying mWaitingUntilKeyguardVisible");
                    keyguardViewMediator.mWaitingUntilKeyguardVisible = false;
                    keyguardViewMediator.notifyAll();
                    keyguardViewMediator.mHandler.removeMessages(8);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mhandleNotifyFinishedGoingToSleep, reason: not valid java name */
    public static void m1949$$Nest$mhandleNotifyFinishedGoingToSleep(KeyguardViewMediator keyguardViewMediator) {
        synchronized (keyguardViewMediator) {
            android.util.Log.d("KeyguardViewMediator", "handleNotifyFinishedGoingToSleep");
            ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).onFinishedGoingToSleep();
        }
    }

    /* renamed from: -$$Nest$mhandleNotifyStartedWakingUp, reason: not valid java name */
    public static void m1950$$Nest$mhandleNotifyStartedWakingUp(KeyguardViewMediator keyguardViewMediator) {
        keyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleMotifyStartedWakingUp");
        synchronized (keyguardViewMediator) {
            android.util.Log.d("KeyguardViewMediator", "handleNotifyWakingUp");
            ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).onStartedWakingUp();
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mhandleReset, reason: not valid java name */
    public static void m1951$$Nest$mhandleReset(KeyguardViewMediator keyguardViewMediator, boolean z) {
        synchronized (keyguardViewMediator) {
            try {
                if (keyguardViewMediator.mHideAnimationRun) {
                    android.util.Log.d("KeyguardViewMediator", "handleReset : hideBouncer=false");
                    z = false;
                } else {
                    android.util.Log.d("KeyguardViewMediator", "handleReset");
                }
                ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).reset(z);
            } catch (Throwable th) {
                throw th;
            }
        }
        keyguardViewMediator.scheduleNonStrongBiometricIdleTimeout$1();
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x004d A[Catch: all -> 0x0043, TryCatch #0 {all -> 0x0043, blocks: (B:38:0x003b, B:6:0x0047, B:8:0x004d, B:10:0x005e, B:13:0x0064, B:16:0x0077, B:17:0x007a, B:19:0x0094, B:21:0x009a, B:23:0x00a8, B:25:0x00a4, B:27:0x00ab, B:28:0x00c6), top: B:37:0x003b }] */
    /* renamed from: -$$Nest$mhandleSetOccluded, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m1952$$Nest$mhandleSetOccluded(com.android.systemui.keyguard.KeyguardViewMediator r9, boolean r10, boolean r11, int r12) {
        /*
            r9.getClass()
            java.lang.String r0 = "isOccluded="
            java.lang.String r1 = "KeyguardViewMediator#handleSetOccluded"
            android.os.Trace.beginSection(r1)
            java.lang.String r1 = "KeyguardViewMediator"
            java.lang.String r2 = "handleSetOccluded(%b) seq=%d"
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r10)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)
            java.lang.Object[] r12 = new java.lang.Object[]{r3, r12}
            com.android.systemui.keyguard.Log.d(r1, r2, r12)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r10)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r11)
            java.lang.Object[] r12 = new java.lang.Object[]{r12, r1}
            r1 = 36080(0x8cf0, float:5.0559E-41)
            android.util.EventLog.writeEvent(r1, r12)
            com.android.internal.jank.InteractionJankMonitor r12 = r9.mInteractionJankMonitor
            r1 = 23
            r12.cancel(r1)
            monitor-enter(r9)
            r12 = 1
            r1 = 0
            if (r10 == 0) goto L46
            com.android.keyguard.KeyguardUpdateMonitor r2 = r9.mUpdateMonitor     // Catch: java.lang.Throwable -> L43
            boolean r2 = r2.mSecureCameraLaunched     // Catch: java.lang.Throwable -> L43
            if (r2 == 0) goto L46
            r2 = r12
            goto L47
        L43:
            r10 = move-exception
            goto Lcb
        L46:
            r2 = r1
        L47:
            r9.mPowerGestureIntercepted = r2     // Catch: java.lang.Throwable -> L43
            boolean r2 = r9.mOccluded     // Catch: java.lang.Throwable -> L43
            if (r2 == r10) goto Lab
            r9.mOccluded = r10     // Catch: java.lang.Throwable -> L43
            com.android.systemui.keyguard.KeyguardWmStateRefactor r2 = com.android.systemui.keyguard.KeyguardWmStateRefactor.INSTANCE     // Catch: java.lang.Throwable -> L43
            com.android.systemui.Flags.keyguardWmStateRefactor()     // Catch: java.lang.Throwable -> L43
            dagger.Lazy r2 = r9.mKeyguardViewControllerLazy     // Catch: java.lang.Throwable -> L43
            java.lang.Object r2 = r2.get()     // Catch: java.lang.Throwable -> L43
            com.android.keyguard.KeyguardViewController r2 = (com.android.keyguard.KeyguardViewController) r2     // Catch: java.lang.Throwable -> L43
            if (r11 == 0) goto L63
            boolean r11 = r9.mDeviceInteractive     // Catch: java.lang.Throwable -> L43
            if (r11 == 0) goto L63
            goto L64
        L63:
            r12 = r1
        L64:
            r2.setOccluded(r10, r12)     // Catch: java.lang.Throwable -> L43
            r9.adjustStatusBarLocked$1(r1, r1)     // Catch: java.lang.Throwable -> L43
            com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl r11 = r9.mHelper     // Catch: java.lang.Throwable -> L43
            java.util.Objects.requireNonNull(r11)     // Catch: java.lang.Throwable -> L43
            boolean r12 = r9.mShowing     // Catch: java.lang.Throwable -> L43
            boolean r1 = com.android.systemui.Rune.SYSUI_MULTI_SIM     // Catch: java.lang.Throwable -> L43
            if (r10 != 0) goto L7a
            if (r12 == 0) goto L7a
            com.android.systemui.keyguard.KeyguardUnlockInfo.reset()     // Catch: java.lang.Throwable -> L43
        L7a:
            com.android.systemui.keyguard.KeyguardDumpLog r1 = com.android.systemui.keyguard.KeyguardDumpLog.INSTANCE     // Catch: java.lang.Throwable -> L43
            r6 = 0
            r7 = 0
            r2 = 4
            r5 = 0
            r8 = 60
            r4 = 0
            r3 = r10
            com.android.systemui.keyguard.KeyguardDumpLog.state$default(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L43
            dagger.Lazy r1 = r11.viewMediatorLazy     // Catch: java.lang.Throwable -> L43
            java.lang.Object r1 = r1.get()     // Catch: java.lang.Throwable -> L43
            com.android.systemui.keyguard.KeyguardViewMediator r1 = (com.android.systemui.keyguard.KeyguardViewMediator) r1     // Catch: java.lang.Throwable -> L43
            r1.userActivity()     // Catch: java.lang.Throwable -> L43
            if (r12 == 0) goto Lab
            boolean r12 = com.android.systemui.LsRune.KEYGUARD_SUB_DISPLAY_COVER     // Catch: java.lang.Throwable -> L43
            com.android.keyguard.KeyguardDisplayManager r1 = r11.keyguardDisplayManager     // Catch: java.lang.Throwable -> L43
            if (r12 == 0) goto La2
            com.android.systemui.keyguard.KeyguardFoldControllerImpl r11 = r11.foldControllerImpl     // Catch: java.lang.Throwable -> L43
            boolean r11 = r11.isFoldOpened()     // Catch: java.lang.Throwable -> L43
            if (r11 == 0) goto La8
        La2:
            if (r10 == 0) goto La8
            r1.hideAfterKeyguardInvisible()     // Catch: java.lang.Throwable -> L43
            goto Lab
        La8:
            r1.show()     // Catch: java.lang.Throwable -> L43
        Lab:
            java.lang.String r11 = "KeyguardViewMediator"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L43
            r12.<init>(r0)     // Catch: java.lang.Throwable -> L43
            r12.append(r10)     // Catch: java.lang.Throwable -> L43
            java.lang.String r10 = ",mPowerGestureIntercepted="
            r12.append(r10)     // Catch: java.lang.Throwable -> L43
            boolean r10 = r9.mPowerGestureIntercepted     // Catch: java.lang.Throwable -> L43
            r12.append(r10)     // Catch: java.lang.Throwable -> L43
            java.lang.String r10 = r12.toString()     // Catch: java.lang.Throwable -> L43
            android.util.Log.d(r11, r10)     // Catch: java.lang.Throwable -> L43
            monitor-exit(r9)     // Catch: java.lang.Throwable -> L43
            android.os.Trace.endSection()
            return
        Lcb:
            monitor-exit(r9)     // Catch: java.lang.Throwable -> L43
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediator.m1952$$Nest$mhandleSetOccluded(com.android.systemui.keyguard.KeyguardViewMediator, boolean, boolean, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0082 A[Catch: all -> 0x0048, TryCatch #0 {all -> 0x0048, blocks: (B:11:0x003a, B:13:0x003e, B:14:0x0045, B:18:0x004b, B:20:0x0074, B:25:0x0082, B:26:0x00a8, B:28:0x00c9, B:30:0x00d1, B:32:0x00d5, B:34:0x00e2, B:35:0x0113, B:41:0x00db, B:42:0x00df), top: B:10:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00c9 A[Catch: all -> 0x0048, TryCatch #0 {all -> 0x0048, blocks: (B:11:0x003a, B:13:0x003e, B:14:0x0045, B:18:0x004b, B:20:0x0074, B:25:0x0082, B:26:0x00a8, B:28:0x00c9, B:30:0x00d1, B:32:0x00d5, B:34:0x00e2, B:35:0x0113, B:41:0x00db, B:42:0x00df), top: B:10:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00df A[Catch: all -> 0x0048, TryCatch #0 {all -> 0x0048, blocks: (B:11:0x003a, B:13:0x003e, B:14:0x0045, B:18:0x004b, B:20:0x0074, B:25:0x0082, B:26:0x00a8, B:28:0x00c9, B:30:0x00d1, B:32:0x00d5, B:34:0x00e2, B:35:0x0113, B:41:0x00db, B:42:0x00df), top: B:10:0x003a }] */
    /* renamed from: -$$Nest$mhandleShow, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m1953$$Nest$mhandleShow(com.android.systemui.keyguard.KeyguardViewMediator r6, android.os.Bundle r7) {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediator.m1953$$Nest$mhandleShow(com.android.systemui.keyguard.KeyguardViewMediator, android.os.Bundle):void");
    }

    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.keyguard.KeyguardViewMediator$6] */
    /* JADX WARN: Type inference failed for: r11v1, types: [com.android.systemui.keyguard.KeyguardViewMediator$7] */
    /* JADX WARN: Type inference failed for: r11v3, types: [com.android.systemui.keyguard.KeyguardViewMediator$11] */
    /* JADX WARN: Type inference failed for: r11v4, types: [com.android.systemui.keyguard.KeyguardViewMediator$12] */
    public KeyguardViewMediator(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, Context context, UiEventLogger uiEventLogger, SessionTracker sessionTracker, UserTracker userTracker, FalsingCollector falsingCollector, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, Lazy lazy, DismissCallbackRegistry dismissCallbackRegistry, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, Executor executor, PowerManager powerManager, TrustManager trustManager, UserSwitcherController userSwitcherController, DeviceConfigProxy deviceConfigProxy, NavigationModeController navigationModeController, KeyguardDisplayManager keyguardDisplayManager, DozeParameters dozeParameters, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController, Lazy lazy2, ScreenOffAnimationController screenOffAnimationController, Lazy lazy3, ScreenOnCoordinator screenOnCoordinator, KeyguardTransitions keyguardTransitions, InteractionJankMonitor interactionJankMonitor, DreamOverlayStateController dreamOverlayStateController, JavaAdapter javaAdapter, WallpaperRepository wallpaperRepository, Lazy lazy4, Lazy lazy5, Lazy lazy6, Lazy lazy7, IActivityTaskManager iActivityTaskManager, FeatureFlags featureFlags, SecureSettings secureSettings, SystemSettings systemSettings, SystemClock systemClock, CoroutineDispatcher coroutineDispatcher, Lazy lazy8, Lazy lazy9, SystemPropertiesHelper systemPropertiesHelper, Lazy lazy10, SelectedUserInteractor selectedUserInteractor, KeyguardInteractor keyguardInteractor, WindowManagerOcclusionManager windowManagerOcclusionManager) {
        final ArrayList arrayList = new ArrayList();
        this.mKeyguardStateCallbacks = arrayList;
        this.mPendingPinLock = false;
        this.mPowerGestureIntercepted = false;
        this.mSurfaceBehindRemoteAnimationRequested = false;
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("nav_bar_handle_show_over_lockscreen")) {
                    KeyguardViewMediator.this.mShowHomeOverLockscreen = properties.getBoolean("nav_bar_handle_show_over_lockscreen", true);
                }
            }
        };
        this.mDreamOverlayStateCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.2
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onStateChanged() {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                keyguardViewMediator.mDreamOverlayShowing = keyguardViewMediator.mDreamOverlayStateController.isOverlayActive();
            }
        };
        this.mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.3
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                int selectedUserId = keyguardViewMediator.mSelectedUserInteractor.getSelectedUserId();
                if (keyguardViewMediator.mLockPatternUtils.isSecure(selectedUserId)) {
                    keyguardViewMediator.mLockPatternUtils.getDevicePolicyManager().reportFailedBiometricAttempt(selectedUserId);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                if (keyguardViewMediator.mLockPatternUtils.isSecure(i)) {
                    keyguardViewMediator.mLockPatternUtils.getDevicePolicyManager().reportSuccessfulBiometricAttempt(i);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDeviceProvisioned() {
                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                KeyguardViewMediator.this.sendUserPresentBroadcast$1();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                synchronized (KeyguardViewMediator.this) {
                    if (!z) {
                        try {
                            if (KeyguardViewMediator.this.mPendingPinLock) {
                                android.util.Log.i("KeyguardViewMediator", "PIN lock requested, starting keyguard");
                                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                                keyguardViewMediator.mPendingPinLock = false;
                                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = keyguardViewMediator.mHelper;
                                Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                                boolean z2 = Rune.SYSUI_MULTI_SIM;
                                keyguardViewMediatorHelperImpl2.getHandler$1().postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$doKeyguardLockedAfterUnlockAnimation$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        Log.d("KeyguardViewMediator", "PendingPinLock : doKeyguardLockedAfterUnlockAnimation");
                                        KeyguardViewMediatorHelperImpl.this.doKeyguardLocked$2(null);
                                    }
                                }, ((KeyguardUnlockAnimationController) keyguardViewMediatorHelperImpl2.unlockAnimationControllerLazy.get()).getUnlockAnimationDuration());
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
                KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "onSimStateChanged(subId=", ", slotId=", ",state="), i3, ")", "KeyguardViewMediator");
                int size = KeyguardViewMediator.this.mKeyguardStateCallbacks.size();
                boolean isSimPinSecure = KeyguardViewMediator.this.mUpdateMonitor.isSimPinSecure();
                for (int i4 = size - 1; i4 >= 0; i4--) {
                    try {
                        ((IKeyguardStateCallback) KeyguardViewMediator.this.mKeyguardStateCallbacks.get(i4)).onSimSecureStateChanged(isSimPinSecure);
                    } catch (RemoteException e) {
                        Slog.w("KeyguardViewMediator", "Failed to call onSimSecureStateChanged", e);
                        if (e instanceof DeadObjectException) {
                            KeyguardViewMediator.this.mKeyguardStateCallbacks.remove(i4);
                        }
                    }
                }
                if (LsRune.SECURITY_ESIM && KeyguardViewMediator.this.mUpdateMonitor.isESimRemoveButtonClicked()) {
                    z = false;
                } else {
                    synchronized (KeyguardViewMediator.this) {
                        try {
                            int i5 = KeyguardViewMediator.this.mLastSimStates.get(i2);
                            if (i5 != 2 && i5 != 3 && (!LsRune.SECURITY_SIM_PERSO_LOCK || i5 != 12)) {
                                z = false;
                                KeyguardViewMediator.this.mLastSimStates.append(i2, i3);
                            }
                            z = true;
                            KeyguardViewMediator.this.mLastSimStates.append(i2, i3);
                        } finally {
                        }
                    }
                }
                if (i3 != 0 && i3 != 1) {
                    if (i3 == 2 || i3 == 3) {
                        synchronized (KeyguardViewMediator.this) {
                            try {
                                if (!SubscriptionManager.isValidSubscriptionId(i)) {
                                    android.util.Log.d("KeyguardViewMediator", "Skip invalid subId SIM lock request!");
                                    return;
                                }
                                KeyguardViewMediator.this.mSimWasLocked.append(i2, true);
                                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                                if (keyguardViewMediator.mShowing) {
                                    keyguardViewMediator.mHelper.removeMessage(7);
                                    KeyguardViewMediator.this.mHelper.removeMessage(2);
                                    KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                                    if (keyguardViewMediator2.mHiding) {
                                        keyguardViewMediator2.mHiding = false;
                                    }
                                    if (keyguardViewMediator2.mSurfaceBehindRemoteAnimationRunning || ((KeyguardStateControllerImpl) keyguardViewMediator2.mKeyguardStateController).mKeyguardGoingAway) {
                                        Log.d("KeyguardViewMediator", "PendingPinLock : set true");
                                        KeyguardViewMediator.this.mPendingPinLock = true;
                                    }
                                    KeyguardViewMediator.this.resetStateLocked$1(true);
                                } else {
                                    android.util.Log.d("KeyguardViewMediator", "INTENT_VALUE_ICC_LOCKED and keygaurd isn't showing; need to show keyguard so user can enter sim pin");
                                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediator.this.mHelper;
                                    Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                                    Rune.runIf(2, new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl2, 4));
                                    KeyguardViewMediator.this.doKeyguardLocked(null, false);
                                }
                                return;
                            } finally {
                            }
                        }
                    }
                    if (i3 == 5) {
                        synchronized (KeyguardViewMediator.this) {
                            try {
                                android.util.Log.d("KeyguardViewMediator", "READY, reset state? " + KeyguardViewMediator.this.mShowing);
                                KeyguardViewMediator keyguardViewMediator3 = KeyguardViewMediator.this;
                                if (keyguardViewMediator3.mShowing && keyguardViewMediator3.mSimWasLocked.get(i2, false)) {
                                    android.util.Log.d("KeyguardViewMediator", "SIM moved to READY when the previously was locked. Reset the state.");
                                    KeyguardViewMediator.this.mSimWasLocked.append(i2, false);
                                    KeyguardViewMediator.this.resetStateLocked$1(true);
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    if (i3 != 6) {
                        if (i3 == 7) {
                            synchronized (KeyguardViewMediator.this) {
                                try {
                                    if (KeyguardViewMediator.this.mShowing) {
                                        android.util.Log.d("KeyguardViewMediator", "PERM_DISABLED, resetStateLocked toshow permanently disabled message in lockscreen.");
                                        if (KeyguardViewMediator.this.shouldWaitForProvisioning$1()) {
                                            KeyguardViewMediator.this.tryKeyguardDone$1();
                                        } else {
                                            KeyguardViewMediator.this.resetStateLocked$1(true);
                                        }
                                    } else {
                                        android.util.Log.d("KeyguardViewMediator", "PERM_DISABLED and keygaurd isn't showing.");
                                        KeyguardViewMediator.this.doKeyguardLocked(null, false);
                                    }
                                } finally {
                                }
                            }
                            return;
                        }
                        if (i3 == 12 && LsRune.SECURITY_SIM_PERSO_LOCK) {
                            synchronized (KeyguardViewMediator.this) {
                                try {
                                    KeyguardViewMediator.this.mSimWasLocked.append(i2, true);
                                    if (KeyguardViewMediator.this.mShowing) {
                                        android.util.Log.d("KeyguardViewMediator", "send the handler LAUNCH_PERSO_LOCK");
                                        KeyguardViewMediator.this.mHandler.sendEmptyMessageDelayed(VolteConstants.ErrorCode.CALL_SESSION_TERMINATED, 500L);
                                    } else {
                                        android.util.Log.d("KeyguardViewMediator", "INTENT_VALUE_ICC_LOCKED and keyguard isn't showing; need to show keyguard so user can enter sim perso");
                                        if (SubscriptionManager.isValidSubscriptionId(KeyguardViewMediator.this.mUpdateMonitor.getNextSubIdForState(12))) {
                                            KeyguardViewMediator.this.doKeyguardLocked(null, false);
                                        }
                                    }
                                } finally {
                                }
                            }
                            return;
                        }
                        return;
                    }
                }
                KeyguardViewMediator keyguardViewMediator4 = KeyguardViewMediator.this;
                keyguardViewMediator4.mPendingPinLock = false;
                synchronized (keyguardViewMediator4) {
                    try {
                        if (KeyguardViewMediator.this.shouldWaitForProvisioning$1()) {
                            KeyguardViewMediator keyguardViewMediator5 = KeyguardViewMediator.this;
                            if (keyguardViewMediator5.mShowing) {
                                keyguardViewMediator5.tryKeyguardDone$1();
                            } else {
                                android.util.Log.d("KeyguardViewMediator", "ICC_ABSENT isn't showing, we need to show the keyguard since the device isn't provisioned yet.");
                                KeyguardViewMediator.this.doKeyguardLocked(null, false);
                            }
                        } else if (KeyguardViewMediator.this.mShowing && i3 == 0 && SubscriptionManager.isValidSubscriptionId(i)) {
                            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                            if (defaultAdapter != null) {
                                int profileConnectionState = defaultAdapter.getProfileConnectionState(10);
                                android.util.Log.d("KeyguardViewMediator", "SAP status : " + profileConnectionState);
                                if (profileConnectionState == 2) {
                                    android.util.Log.d("KeyguardViewMediator", "SAPConnectRequested : resetState");
                                    KeyguardViewMediator.this.resetStateLocked$1(true);
                                }
                            } else {
                                android.util.Log.d("KeyguardViewMediator", "SAP status : BluetoothAdapter is null");
                            }
                        }
                        if (i3 == 1) {
                            if (z) {
                                android.util.Log.d("KeyguardViewMediator", "SIM moved to ABSENT when the previous state was locked. Reset the state.");
                                KeyguardViewMediator.this.resetStateLocked$1(true);
                            }
                            KeyguardViewMediator.this.mSimWasLocked.append(i2, false);
                        }
                    } finally {
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStrongAuthStateChanged(int i) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                if (keyguardViewMediator.mUpdateMonitor.isUserInLockdown(keyguardViewMediator.mSelectedUserInteractor.getSelectedUserId())) {
                    keyguardViewMediator.doKeyguardLocked(null, false);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTrustChanged(int i) {
                if (i == KeyguardViewMediator.this.mSelectedUserInteractor.getSelectedUserId()) {
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                        keyguardViewMediator.notifyTrustedChangedLocked$1(keyguardViewMediator.mUpdateMonitor.getUserHasTrust(i));
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                android.util.Log.d("KeyguardViewMediator", String.format("onUserSwitchComplete %d", Integer.valueOf(i)));
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediator.this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                Rune.runIf(i, new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl2, 6));
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitching(int i) {
                android.util.Log.d("KeyguardViewMediator", String.format("onUserSwitching %d", Integer.valueOf(i)));
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediator.this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                Rune.runIf(i, new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl2, 5));
                synchronized (KeyguardViewMediator.this) {
                    Flags.refactorGetCurrentUser();
                    KeyguardViewMediator.this.resetKeyguardDonePendingLocked$1();
                    KeyguardViewMediator.this.adjustStatusBarLocked$1(false, false);
                }
            }
        };
        this.mViewMediatorCallback = new AnonymousClass4();
        ActivityTransitionAnimator.Controller controller = new ActivityTransitionAnimator.Controller() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.5
            public final boolean mIsLaunching = true;

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final TransitionAnimator.State createAnimatorState() {
                int width = getTransitionContainer().getWidth();
                int height = getTransitionContainer().getHeight();
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                if (keyguardViewMediator.mUpdateMonitor.mSecureCameraLaunched) {
                    float f = height / 3.0f;
                    float f2 = width;
                    float f3 = f / 2.0f;
                    float f4 = keyguardViewMediator.mPowerButtonY;
                    float f5 = keyguardViewMediator.mWindowCornerRadius;
                    return new TransitionAnimator.State((int) (f4 - f3), (int) (f4 + f3), (int) (f2 - (f2 / 3.0f)), width, f5, f5);
                }
                Flags.FEATURE_FLAGS.getClass();
                RemoteAnimationTarget remoteAnimationTarget = keyguardViewMediator.mOccludingRemoteAnimationTarget;
                if (remoteAnimationTarget != null && remoteAnimationTarget.isTranslucent) {
                    return new TransitionAnimator.State(0, height, 0, width, 0.0f, 0.0f);
                }
                float f6 = height;
                float f7 = f6 / 2.0f;
                float f8 = width;
                float f9 = f8 / 2.0f;
                float f10 = f6 - f7;
                float f11 = f8 - f9;
                float f12 = keyguardViewMediator.mWindowCornerRadius;
                return new TransitionAnimator.State(((int) f10) / 2, (int) ((f10 / 2.0f) + f7), ((int) f11) / 2, (int) ((f11 / 2.0f) + f9), f12, f12);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final ViewGroup getTransitionContainer() {
                return (ViewGroup) ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView();
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final boolean isLaunching() {
                return this.mIsLaunching;
            }

            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
            public final void onTransitionAnimationCancelled(Boolean bool) {
                StringBuilder sb = new StringBuilder("Occlude launch animation cancelled. Occluded state is now: ");
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, keyguardViewMediator.mOccluded, "KeyguardViewMediator");
                keyguardViewMediator.mOccludeAnimationPlaying = false;
                ((CentralSurfacesImpl) keyguardViewMediator.mCentralSurfaces).updateIsKeyguard(false);
                ((ScrimController) keyguardViewMediator.mScrimControllerLazy.get()).setOccludeAnimationPlaying(false);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationEnd(boolean z) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                if (z) {
                    ((ShadeController) keyguardViewMediator.mShadeController.get()).instantCollapseShade();
                }
                keyguardViewMediator.mOccludeAnimationPlaying = false;
                ((CentralSurfacesImpl) keyguardViewMediator.mCentralSurfaces).updateIsKeyguard(false);
                ((ScrimController) keyguardViewMediator.mScrimControllerLazy.get()).setOccludeAnimationPlaying(false);
                keyguardViewMediator.mInteractionJankMonitor.end(64);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationStart(boolean z) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                keyguardViewMediator.mOccludeAnimationPlaying = true;
                ((ScrimController) keyguardViewMediator.mScrimControllerLazy.get()).setOccludeAnimationPlaying(true);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void setTransitionContainer(ViewGroup viewGroup) {
                android.util.Log.wtf("KeyguardViewMediator", "Someone tried to change the launch container for the ActivityTransitionAnimator, which should never happen.");
            }
        };
        this.mOccludeAnimationController = controller;
        this.mExitAnimationRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.6
            public final void onAnimationCancelled() {
                KeyguardViewMediator.this.cancelKeyguardExitAnimation();
                KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                Flags.keyguardWmStateRefactor();
            }

            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                Trace.beginSection("mExitAnimationRunner.onAnimationStart#startKeyguardExitAnimation");
                KeyguardViewMediator.this.startKeyguardExitAnimation(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
                KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                Flags.keyguardWmStateRefactor();
                Trace.endSection();
            }
        };
        this.mAppearAnimationRunner = new IRemoteAnimationRunner.Stub(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator.7
            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                try {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                } catch (RemoteException e) {
                    android.util.Log.e("KeyguardViewMediator", "Failed to finish transition", e);
                }
            }

            public final void onAnimationCancelled() {
            }
        };
        new OccludeActivityLaunchRemoteAnimationRunner(controller);
        this.mOccludeByDreamAnimationRunner = new AnonymousClass8();
        new AnonymousClass9();
        this.mFoldGracePeriodProvider = new FoldGracePeriodProvider();
        KeyguardStateController.Callback callback = new KeyguardStateController.Callback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.10
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onPrimaryBouncerShowingChanged() {
                synchronized (KeyguardViewMediator.this) {
                    KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                    KeyguardStateController keyguardStateController2 = keyguardViewMediator.mKeyguardStateController;
                    if (((KeyguardStateControllerImpl) keyguardStateController2).mPrimaryBouncerShowing && !((KeyguardStateControllerImpl) keyguardStateController2).mKeyguardGoingAway) {
                        keyguardViewMediator.mPendingPinLock = false;
                    }
                    keyguardViewMediator.adjustStatusBarLocked$1(((KeyguardStateControllerImpl) keyguardStateController2).mPrimaryBouncerShowing, false);
                }
            }
        };
        this.mShowCommunalWhenUnoccluding = false;
        this.mDelayedLockBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.11
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("seq", 0);
                    RecyclerView$$ExternalSyntheticOutline0.m(KeyguardViewMediator.this.mDelayedShowingSequence, "KeyguardViewMediator", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(intExtra, "received DELAYED_KEYGUARD_ACTION with seq = ", ", mDelayedShowingSequence = "));
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                        if (keyguardViewMediator.mDelayedShowingSequence == intExtra) {
                            keyguardViewMediator.doKeyguardLocked(null, false);
                        }
                    }
                    return;
                }
                if ("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK".equals(intent.getAction())) {
                    int intExtra2 = intent.getIntExtra("seq", 0);
                    int intExtra3 = intent.getIntExtra("android.intent.extra.USER_ID", 0);
                    if (intExtra3 != 0) {
                        synchronized (KeyguardViewMediator.this) {
                            KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                            if (keyguardViewMediator2.mDelayedProfileShowingSequence == intExtra2) {
                                keyguardViewMediator2.mTrustManager.setDeviceLockedForUser(intExtra3, true);
                            }
                        }
                    }
                }
            }
        };
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.12
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator.this.mShuttingDown = true;
                    }
                }
            }
        };
        final AnonymousClass13 anonymousClass13 = new AnonymousClass13(Looper.myLooper(), null, true);
        this.mHandler = anonymousClass13;
        this.mKeyguardGoingAwayRunnable = new AnonymousClass14();
        this.mHideAnimationFinishedRunnable = new KeyguardViewMediator$$ExternalSyntheticLambda1(this, 0);
        final int i = 0;
        Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i2 = 7;
        Function0 function02 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i3 = 8;
        Function0 function03 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i4 = 9;
        Function0 function04 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i5 = 10;
        Function0 function05 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i5) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i6 = 11;
        Function0 function06 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i6) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i7 = 12;
        Function0 function07 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i7) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i8 = 13;
        Function0 function08 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i8) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i9 = 14;
        Function0 function09 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i9) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i10 = 15;
        Function0 function010 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i10) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i11 = 1;
        Function0 function011 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i11) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i12 = 2;
        Function0 function012 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i12) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i13 = 3;
        Function0 function013 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i13) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i14 = 4;
        Function0 function014 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i14) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i15 = 5;
        Function0 function015 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i15) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i16 = 0;
        Function0 function016 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i16) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i17 = 1;
        Function0 function017 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i17) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i18 = 2;
        Function0 function018 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i18) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i19 = 3;
        Function0 function019 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i19) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i20 = 6;
        Function0 function020 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i20) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 1;
                    case 1:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 13;
                    case 2:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 14;
                    case 3:
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 17;
                    case 4:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 18;
                    case 5:
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 19;
                    case 6:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 7:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 8:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 3;
                    case 9:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 5;
                    case 10:
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 7;
                    case 11:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 8;
                    case 12:
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 9;
                    case 13:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 10;
                    case 14:
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 11;
                    default:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 12;
                }
            }
        };
        final int i21 = 4;
        Function0 function021 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i21) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i22 = 5;
        Function0 function022 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i22) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i23 = 6;
        Function0 function023 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i23) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i24 = 7;
        Function0 function024 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i24) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i25 = 8;
        Function0 function025 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i25) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i26 = 9;
        Function0 function026 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i26) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i27 = 10;
        Function0 function027 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i27) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i28 = 11;
        Function0 function028 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i28) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i29 = 12;
        Function0 function029 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i29) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i30 = 13;
        Function0 function030 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i30) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i31 = 14;
        Function0 function031 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i31) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i32 = 15;
        Function0 function032 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i32) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i33 = 16;
        Function0 function033 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i33) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i34 = 17;
        Function0 function034 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i34) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i35 = 18;
        Function0 function035 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i35) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i36 = 19;
        Function0 function036 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i36) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i37 = 20;
        Function0 function037 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i37) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        Function2 function2 = new Function2() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda41
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                keyguardViewMediator.getClass();
                new KeyguardViewMediator$$ExternalSyntheticLambda74(keyguardViewMediator, (Boolean) obj, (Boolean) obj2).run();
                return Unit.INSTANCE;
            }
        };
        final int i38 = 0;
        Function1 function1 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda42
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                switch (i38) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = this.f$0;
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(1, keyguardViewMediator, (Bundle) obj).run();
                        break;
                    case 1:
                        KeyguardViewMediator keyguardViewMediator2 = this.f$0;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(0, keyguardViewMediator2, (Integer) obj).run();
                        break;
                    case 2:
                        KeyguardViewMediator keyguardViewMediator3 = this.f$0;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        break;
                    case 4:
                        String str = (String) obj;
                        KeyguardViewMediator keyguardViewMediator4 = this.f$0;
                        if (str != null) {
                            keyguardViewMediator4.mPhoneState = str;
                        }
                        break;
                    case 5:
                        this.f$0.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        KeyguardViewMediator keyguardViewMediator5 = this.f$0;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        KeyguardViewMediator keyguardViewMediator6 = this.f$0;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i39 = 1;
        Function1 function12 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda42
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                switch (i39) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = this.f$0;
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(1, keyguardViewMediator, (Bundle) obj).run();
                        break;
                    case 1:
                        KeyguardViewMediator keyguardViewMediator2 = this.f$0;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(0, keyguardViewMediator2, (Integer) obj).run();
                        break;
                    case 2:
                        KeyguardViewMediator keyguardViewMediator3 = this.f$0;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        break;
                    case 4:
                        String str = (String) obj;
                        KeyguardViewMediator keyguardViewMediator4 = this.f$0;
                        if (str != null) {
                            keyguardViewMediator4.mPhoneState = str;
                        }
                        break;
                    case 5:
                        this.f$0.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        KeyguardViewMediator keyguardViewMediator5 = this.f$0;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        KeyguardViewMediator keyguardViewMediator6 = this.f$0;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i40 = 21;
        Function0 function038 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i40) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i41 = 22;
        Function0 function039 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i41) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i42 = 2;
        Function1 function13 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda42
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                switch (i42) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = this.f$0;
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(1, keyguardViewMediator, (Bundle) obj).run();
                        break;
                    case 1:
                        KeyguardViewMediator keyguardViewMediator2 = this.f$0;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(0, keyguardViewMediator2, (Integer) obj).run();
                        break;
                    case 2:
                        KeyguardViewMediator keyguardViewMediator3 = this.f$0;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        break;
                    case 4:
                        String str = (String) obj;
                        KeyguardViewMediator keyguardViewMediator4 = this.f$0;
                        if (str != null) {
                            keyguardViewMediator4.mPhoneState = str;
                        }
                        break;
                    case 5:
                        this.f$0.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        KeyguardViewMediator keyguardViewMediator5 = this.f$0;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        KeyguardViewMediator keyguardViewMediator6 = this.f$0;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i43 = 23;
        Function0 function040 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i43) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) this).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) this).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) this).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) this).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) this).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) this).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) this;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) this;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) this;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) this;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) this;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) this;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) this;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) this;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) this;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) this).size());
                }
            }
        };
        final int i44 = 24;
        Function0 function041 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i44) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) arrayList;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) arrayList).mHandler;
                    case 2:
                        return ((KeyguardViewMediator) arrayList).mAlarmManager;
                    case 3:
                        return ((KeyguardViewMediator) arrayList).mShowKeyguardWakeLock;
                    case 4:
                        return Boolean.valueOf(((KeyguardViewMediator) arrayList).mWakeAndUnlocking);
                    case 5:
                        return Boolean.valueOf(((KeyguardViewMediator) arrayList).mPendingLock);
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) arrayList).mShowing);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) arrayList).mAodShowing);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) arrayList).mExternallyEnabled);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) arrayList).mGoingToSleep);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) arrayList).mBootCompleted);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) arrayList).mKeyguardDonePending);
                    case 12:
                        return Integer.valueOf(((KeyguardViewMediator) arrayList).mDelayedShowingSequence);
                    case 13:
                        return ((KeyguardViewMediator) arrayList).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) arrayList;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) arrayList;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) arrayList;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator4, 3).run();
                        return Unit.INSTANCE;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) arrayList;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator5, 5).run();
                        return Unit.INSTANCE;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) arrayList;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator6, 4).run();
                        return Unit.INSTANCE;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) arrayList;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator7, 2).run();
                        return Unit.INSTANCE;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) arrayList;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda1(keyguardViewMediator8, 6).run();
                        return Unit.INSTANCE;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) arrayList;
                        keyguardViewMediator9.getClass();
                        keyguardViewMediator9.setPendingLock(false);
                        return Unit.INSTANCE;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) arrayList;
                        keyguardViewMediator10.getClass();
                        keyguardViewMediator10.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) arrayList;
                        keyguardViewMediator11.getClass();
                        keyguardViewMediator11.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) arrayList).size());
                }
            }
        };
        final int i45 = 3;
        Function1 function14 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda42
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                switch (i45) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = this.f$0;
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(1, keyguardViewMediator, (Bundle) obj).run();
                        break;
                    case 1:
                        KeyguardViewMediator keyguardViewMediator2 = this.f$0;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(0, keyguardViewMediator2, (Integer) obj).run();
                        break;
                    case 2:
                        KeyguardViewMediator keyguardViewMediator3 = this.f$0;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        break;
                    case 4:
                        String str = (String) obj;
                        KeyguardViewMediator keyguardViewMediator4 = this.f$0;
                        if (str != null) {
                            keyguardViewMediator4.mPhoneState = str;
                        }
                        break;
                    case 5:
                        this.f$0.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        KeyguardViewMediator keyguardViewMediator5 = this.f$0;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        KeyguardViewMediator keyguardViewMediator6 = this.f$0;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i46 = 4;
        Function1 function15 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda42
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                switch (i46) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = this.f$0;
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(1, keyguardViewMediator, (Bundle) obj).run();
                        break;
                    case 1:
                        KeyguardViewMediator keyguardViewMediator2 = this.f$0;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(0, keyguardViewMediator2, (Integer) obj).run();
                        break;
                    case 2:
                        KeyguardViewMediator keyguardViewMediator3 = this.f$0;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        break;
                    case 4:
                        String str = (String) obj;
                        KeyguardViewMediator keyguardViewMediator4 = this.f$0;
                        if (str != null) {
                            keyguardViewMediator4.mPhoneState = str;
                        }
                        break;
                    case 5:
                        this.f$0.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        KeyguardViewMediator keyguardViewMediator5 = this.f$0;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        KeyguardViewMediator keyguardViewMediator6 = this.f$0;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i47 = 5;
        Function1 function16 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda42
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                switch (i47) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = this.f$0;
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(1, keyguardViewMediator, (Bundle) obj).run();
                        break;
                    case 1:
                        KeyguardViewMediator keyguardViewMediator2 = this.f$0;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(0, keyguardViewMediator2, (Integer) obj).run();
                        break;
                    case 2:
                        KeyguardViewMediator keyguardViewMediator3 = this.f$0;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        break;
                    case 4:
                        String str = (String) obj;
                        KeyguardViewMediator keyguardViewMediator4 = this.f$0;
                        if (str != null) {
                            keyguardViewMediator4.mPhoneState = str;
                        }
                        break;
                    case 5:
                        this.f$0.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        KeyguardViewMediator keyguardViewMediator5 = this.f$0;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        KeyguardViewMediator keyguardViewMediator6 = this.f$0;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i48 = 6;
        Function1 function17 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda42
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                switch (i48) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = this.f$0;
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(1, keyguardViewMediator, (Bundle) obj).run();
                        break;
                    case 1:
                        KeyguardViewMediator keyguardViewMediator2 = this.f$0;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(0, keyguardViewMediator2, (Integer) obj).run();
                        break;
                    case 2:
                        KeyguardViewMediator keyguardViewMediator3 = this.f$0;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        break;
                    case 4:
                        String str = (String) obj;
                        KeyguardViewMediator keyguardViewMediator4 = this.f$0;
                        if (str != null) {
                            keyguardViewMediator4.mPhoneState = str;
                        }
                        break;
                    case 5:
                        this.f$0.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        KeyguardViewMediator keyguardViewMediator5 = this.f$0;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        KeyguardViewMediator keyguardViewMediator6 = this.f$0;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i49 = 7;
        keyguardViewMediatorHelperImpl.viewMediatorProvider = new ViewMediatorProvider(function0, function02, function03, function04, function05, function06, function07, function08, function09, function010, function011, function012, function013, function014, function015, function016, function017, function018, function019, function020, function021, function022, function023, function024, function025, function026, function027, function028, function029, function030, function031, function032, function033, function034, function035, function036, function037, function2, function1, function12, function038, function039, function13, function040, function041, function14, function15, function16, function17, new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda42
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                switch (i49) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = this.f$0;
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(1, keyguardViewMediator, (Bundle) obj).run();
                        break;
                    case 1:
                        KeyguardViewMediator keyguardViewMediator2 = this.f$0;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda67(0, keyguardViewMediator2, (Integer) obj).run();
                        break;
                    case 2:
                        KeyguardViewMediator keyguardViewMediator3 = this.f$0;
                        keyguardViewMediator3.getClass();
                        keyguardViewMediator3.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        break;
                    case 4:
                        String str = (String) obj;
                        KeyguardViewMediator keyguardViewMediator4 = this.f$0;
                        if (str != null) {
                            keyguardViewMediator4.mPhoneState = str;
                        }
                        break;
                    case 5:
                        this.f$0.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        KeyguardViewMediator keyguardViewMediator5 = this.f$0;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        KeyguardViewMediator keyguardViewMediator6 = this.f$0;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.mHelper = keyguardViewMediatorHelperImpl;
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
        deviceConfigProxy.addOnPropertiesChangedListener("systemui", new Executor() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                anonymousClass13.post(runnable);
            }
        }, onPropertiesChangedListener);
        this.mInGestureNavigationMode = QuickStepContract.isGesturalMode(navigationModeController.addListener(new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda3
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i50) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                keyguardViewMediator.getClass();
                keyguardViewMediator.mInGestureNavigationMode = QuickStepContract.isGesturalMode(i50);
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
        this.mActivityTransitionAnimator = lazy6;
        this.mScrimControllerLazy = lazy7;
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

    public static void initAlphaForAnimationTargets(RemoteAnimationTarget[] remoteAnimationTargetArr) {
        for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
            if (remoteAnimationTarget.mode == 0) {
                SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                try {
                    transaction.setAlpha(remoteAnimationTarget.leash, 1.0f);
                    transaction.apply();
                    transaction.close();
                } catch (Throwable th) {
                    try {
                        transaction.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
        }
    }

    public void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback) {
        synchronized (this) {
            this.mKeyguardStateCallbacks.add(iKeyguardStateCallback);
            try {
                iKeyguardStateCallback.onSimSecureStateChanged(this.mUpdateMonitor.isSimPinSecure());
                iKeyguardStateCallback.onShowingStateChanged(this.mShowing, this.mSelectedUserInteractor.getSelectedUserId(false));
                iKeyguardStateCallback.onInputRestrictedStateChanged(this.mInputRestricted);
                iKeyguardStateCallback.onTrustedChanged(this.mUpdateMonitor.getUserHasTrust(this.mSelectedUserInteractor.getSelectedUserId(false)));
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "Failed to call to IKeyguardStateCallback", e);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0139 A[Catch: RemoteException -> 0x015a, TryCatch #0 {RemoteException -> 0x015a, blocks: (B:51:0x0133, B:53:0x0139, B:56:0x0152, B:57:0x015c), top: B:50:0x0133 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void adjustStatusBarLocked$1(boolean r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 371
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediator.adjustStatusBarLocked$1(boolean, boolean):void");
    }

    public void cancelKeyguardExitAnimation() {
        Trace.beginSection("KeyguardViewMediator#cancelKeyguardExitAnimation");
        AnonymousClass13 anonymousClass13 = this.mHandler;
        anonymousClass13.sendMessage(anonymousClass13.obtainMessage(19));
        Trace.endSection();
    }

    public final InteractionJankMonitor.Configuration.Builder createInteractionJankMonitorConf$1(int i, String str) {
        Log.d("KeyguardViewMediator", str != null ? str : "null");
        InteractionJankMonitor.Configuration.Builder withView = InteractionJankMonitor.Configuration.Builder.withView(i, ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
        return str != null ? withView.setTag(str) : withView;
    }

    public void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        Log.d("KeyguardViewMediator", PopupUIUtil.EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_DISMISS);
        KeyguardUnlockInfo.setUnlockTriggerIfNotSet(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_EXTERNAL);
        this.mHandler.obtainMessage(11, new DismissMessage(iKeyguardDismissCallback, charSequence)).sendToTarget();
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0075, code lost:
    
        if (r3 <= 0) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void doKeyguardLaterForChildProfilesLocked$1() {
        /*
            r15 = this;
            com.android.systemui.settings.UserTracker r0 = r15.mUserTracker
            com.android.systemui.settings.UserTrackerImpl r0 = (com.android.systemui.settings.UserTrackerImpl) r0
            java.util.List r0 = r0.getUserProfiles()
            java.util.Iterator r0 = r0.iterator()
        Lc:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto Lea
            java.lang.Object r1 = r0.next()
            android.content.pm.UserInfo r1 = (android.content.pm.UserInfo) r1
            boolean r2 = r1.isEnabled()
            if (r2 != 0) goto L1f
            goto Lc
        L1f:
            int r1 = r1.id
            com.android.internal.widget.LockPatternUtils r2 = r15.mLockPatternUtils
            boolean r2 = r2.isSeparateProfileChallengeEnabled(r1)
            if (r2 == 0) goto Lc
            com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl r2 = r15.mHelper
            r2.getClass()
            boolean r3 = com.samsung.android.knox.SemPersonaManager.isSecureFolderId(r1)
            r4 = -1
            java.lang.String r5 = "knox_screen_off_timeout"
            if (r3 == 0) goto L42
            android.content.Context r3 = r2.context
            android.content.ContentResolver r3 = r3.getContentResolver()
            int r3 = android.provider.Settings.System.getIntForUser(r3, r5, r4, r1)
            goto L4c
        L42:
            android.content.Context r3 = r2.context
            android.content.ContentResolver r3 = r3.getContentResolver()
            int r3 = android.provider.Settings.Secure.getIntForUser(r3, r5, r4, r1)
        L4c:
            long r3 = (long) r3
            com.android.internal.widget.LockPatternUtils r5 = r2.lockPatternUtils
            android.app.admin.DevicePolicyManager r5 = r5.getDevicePolicyManager()
            r6 = 0
            long r5 = r5.getMaximumTimeToLock(r6, r1)
            r7 = 0
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            r10 = 1
            if (r9 <= 0) goto L61
            r9 = r10
            goto L62
        L61:
            r9 = 0
        L62:
            r11 = -2
            if (r9 == 0) goto L6f
            int r13 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r13 <= 0) goto L6f
            long r3 = java.lang.Math.min(r5, r3)
            goto L78
        L6f:
            if (r9 == 0) goto L73
            r3 = r5
            goto L78
        L73:
            int r5 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r5 > 0) goto L78
            goto La0
        L78:
            int r5 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r5 == 0) goto L8c
            r5 = -1
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 == 0) goto L8c
            int r5 = (r3 > r11 ? 1 : (r3 == r11 ? 0 : -1))
            if (r5 == 0) goto L8c
            r5 = 5000(0x1388, double:2.4703E-320)
            long r3 = java.lang.Math.max(r3, r5)
        L8c:
            long r5 = android.os.SystemClock.uptimeMillis()
            android.os.PowerManager r2 = r2.pm
            long r13 = r2.getLastUserActivityTime(r1)
            long r5 = r5 - r13
            long r5 = java.lang.Math.max(r5, r7)
            long r3 = r3 - r5
            long r3 = java.lang.Math.max(r3, r7)
        La0:
            int r2 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r2 <= 0) goto Ldd
            com.android.systemui.util.time.SystemClock r2 = r15.mSystemClock
            long r5 = r2.elapsedRealtime()
            long r5 = r5 + r3
            android.content.Intent r2 = new android.content.Intent
            java.lang.String r3 = "com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK"
            r2.<init>(r3)
            android.content.Context r3 = r15.mContext
            java.lang.String r3 = r3.getPackageName()
            r2.setPackage(r3)
            java.lang.String r3 = "seq"
            int r4 = r15.mDelayedProfileShowingSequence
            r2.putExtra(r3, r4)
            java.lang.String r3 = "android.intent.extra.USER_ID"
            r2.putExtra(r3, r1)
            r3 = 268435456(0x10000000, float:2.5243549E-29)
            r2.addFlags(r3)
            android.content.Context r3 = r15.mContext
            r4 = 335544320(0x14000000, float:6.4623485E-27)
            android.app.PendingIntent r1 = android.app.PendingIntent.getBroadcast(r3, r1, r2, r4)
            android.app.AlarmManager r2 = r15.mAlarmManager
            r3 = 2
            r2.setExactAndAllowWhileIdle(r3, r5, r1)
            goto Lc
        Ldd:
            if (r2 == 0) goto Le3
            int r2 = (r3 > r11 ? 1 : (r3 == r11 ? 0 : -1))
            if (r2 != 0) goto Lc
        Le3:
            android.app.trust.TrustManager r2 = r15.mTrustManager
            r2.setDeviceLockedForUser(r1, r10)
            goto Lc
        Lea:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediator.doKeyguardLaterForChildProfilesLocked$1():void");
    }

    public final void doKeyguardLaterLocked$1(long j) {
        boolean isLockscreenDisabled;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        keyguardViewMediatorHelperImpl.getClass();
        boolean z = LsRune.COVER_SUPPORTED;
        if (!z || keyguardViewMediatorHelperImpl.doKeyguardPendingIntent == null) {
            isLockscreenDisabled = keyguardViewMediatorHelperImpl.updateMonitor.isLockscreenDisabled();
        } else {
            KeyguardViewMediatorHelperImpl.logD("doKeyguardLaterLocked is already in process");
            isLockscreenDisabled = true;
        }
        if (isLockscreenDisabled) {
            return;
        }
        long elapsedRealtime = this.mSystemClock.elapsedRealtime() + j;
        Intent intent = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        intent.setPackage(this.mContext.getPackageName());
        intent.putExtra("seq", this.mDelayedShowingSequence);
        intent.addFlags(com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, intent, 335544320);
        this.mAlarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime, broadcast);
        Log.d("KeyguardViewMediator", "setting alarm to turn off keyguard, seq = %s, timeout = %d", Integer.valueOf(this.mDelayedShowingSequence), Long.valueOf(j));
        doKeyguardLaterForChildProfilesLocked$1();
        if (z) {
            keyguardViewMediatorHelperImpl.doKeyguardPendingIntent = broadcast;
        }
    }

    public final boolean doKeyguardLocked(Bundle bundle, boolean z) {
        boolean z2 = this.mExternallyEnabled;
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        if (!z2 && !this.mLockPatternUtils.isUserInLockdown(selectedUserInteractor.getSelectedUserId(false))) {
            Objects.requireNonNull(keyguardViewMediatorHelperImpl);
            boolean z3 = Rune.SYSUI_MULTI_SIM;
            if ((!keyguardViewMediatorHelperImpl.isSecure$2() || keyguardViewMediatorHelperImpl.activityManager.getLockTaskModeState() != 0) && !keyguardViewMediatorHelperImpl.updateMonitor.isForcedLock()) {
                android.util.Log.d("KeyguardViewMediator", "doKeyguard: not showing because externally disabled");
                this.mNeedToReshowWhenReenabled = true;
                return false;
            }
        }
        if (this.mShowing) {
            KeyguardStateController keyguardStateController = this.mKeyguardStateController;
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
            if (keyguardStateControllerImpl.mShowing) {
                if (!this.mPM.isInteractive() || this.mHiding) {
                    android.util.Log.e("KeyguardViewMediator", "doKeyguard: already showing, but re-showing because we're interactive or were in the middle of hiding.");
                } else {
                    if (!keyguardStateControllerImpl.mKeyguardGoingAway) {
                        android.util.Log.d("KeyguardViewMediator", "doKeyguard: not showing (instead, resetting) because it is already showing, we're interactive, we were not previously hiding. It should be safe to short-circuit here.");
                        if (bundle != null && keyguardStateController.isVisible()) {
                            bundle.putBoolean("KeyguardExitEditVI", false);
                        }
                        keyguardViewMediatorHelperImpl.setShowingOptions(bundle);
                        resetStateLocked$1(false);
                        return false;
                    }
                    android.util.Log.e("KeyguardViewMediator", "doKeyguard: we're still showing, but going away. Re-show the keyguard rather than short-circuiting and resetting.");
                }
            }
        }
        boolean z4 = !SystemProperties.getBoolean("keyguard.no_require_sim", true);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        boolean z5 = keyguardUpdateMonitor.isSimPinSecure() || ((SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(1)) || SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(7))) && z4);
        if (!z5 && shouldWaitForProvisioning$1()) {
            android.util.Log.d("KeyguardViewMediator", "doKeyguard: not showing because device isn't provisioned and the sim is not locked or missing");
            return false;
        }
        boolean z6 = bundle != null && bundle.getBoolean("force_show", false);
        if (this.mLockPatternUtils.isLockScreenDisabled(selectedUserInteractor.getSelectedUserId(false)) && !z5 && !z6) {
            android.util.Log.d("KeyguardViewMediator", "doKeyguard: not showing because lockscreen is off");
            return false;
        }
        if (!keyguardViewMediatorHelperImpl.isKeyguardDisabled(false)) {
            if (z) {
                return true;
            }
            keyguardUpdateMonitor.setUnlockingKeyguard(false);
            android.util.Log.d("KeyguardViewMediator", "doKeyguard: showing the lock screen");
            showKeyguard$1(bundle);
            return true;
        }
        if (keyguardViewMediatorHelperImpl.isShowing$1()) {
            ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl.viewMediatorProvider;
            if (viewMediatorProvider == null) {
                viewMediatorProvider = null;
            }
            Function2 function2 = viewMediatorProvider.setShowingLocked;
            Boolean bool = Boolean.FALSE;
            function2.invoke(bool, bool);
            keyguardViewMediatorHelperImpl.hidingByDisabled = true;
            KeyguardViewMediatorHelperImpl.logD("hideLocked by disabled keyguard");
            ViewMediatorProvider viewMediatorProvider2 = keyguardViewMediatorHelperImpl.viewMediatorProvider;
            (viewMediatorProvider2 != null ? viewMediatorProvider2 : null).hideLocked.invoke();
        }
        return false;
    }

    public void doKeyguardTimeout(Bundle bundle) {
        AnonymousClass13 anonymousClass13 = this.mHandler;
        anonymousClass13.removeMessages(10);
        anonymousClass13.sendMessageAtFrontOfQueue(anonymousClass13.obtainMessage(10, bundle));
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
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

    public void exitKeyguardAndFinishSurfaceBehindRemoteAnimation() {
        Log.d("KeyguardViewMediator", "onKeyguardExitRemoteAnimationFinished surfBehindRemoteAniRun=%b surfBehindRemoteAniReq=%b showKeyguard=%b", Boolean.valueOf(this.mSurfaceBehindRemoteAnimationRunning), Boolean.valueOf(this.mSurfaceBehindRemoteAnimationRequested), Boolean.FALSE);
        if (!this.mSurfaceBehindRemoteAnimationRunning && !this.mSurfaceBehindRemoteAnimationRequested) {
            StringBuilder sb = new StringBuilder("skip onKeyguardExitRemoteAnimationFinished showKeyguard=false surfaceAnimationRunning=");
            sb.append(this.mSurfaceBehindRemoteAnimationRunning);
            sb.append(" surfaceAnimationRequested=");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mSurfaceBehindRemoteAnimationRequested, "KeyguardViewMediator");
            return;
        }
        ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).blockPanelExpansionFromCurrentTouch();
        final boolean z = this.mShowing;
        ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).notifyFinishedKeyguardExitAnimation(false);
        DejankUtils.setImmediate(true);
        DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda0
            public final /* synthetic */ boolean f$2 = false;

            @Override // java.lang.Runnable
            public final void run() {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                boolean z2 = z;
                boolean z3 = this.f$2;
                if (keyguardViewMediator.mPM.isInteractive() || keyguardViewMediator.mPendingLock) {
                    keyguardViewMediator.onKeyguardExitFinished$1();
                    KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardViewMediator.mKeyguardStateController;
                    if (keyguardStateControllerImpl.mDismissingFromTouch || z2) {
                        android.util.Log.d("KeyguardViewMediator", "onKeyguardExitRemoteAnimationFinished#hideKeyguardViewAfterRemoteAnimation");
                        ((KeyguardUnlockAnimationController) keyguardViewMediator.mKeyguardUnlockAnimationControllerLazy.get()).hideKeyguardViewAfterRemoteAnimation();
                    } else {
                        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(new StringBuilder("skip hideKeyguardViewAfterRemoteAnimation dismissFromSwipe="), keyguardStateControllerImpl.mDismissingFromTouch, " wasShowing=", z2, "KeyguardViewMediator");
                    }
                    keyguardViewMediator.finishSurfaceBehindRemoteAnimation(z3);
                    keyguardViewMediator.mUpdateMonitor.mHandler.sendEmptyMessage(346);
                    return;
                }
                android.util.Log.e("KeyguardViewMediator", "exitKeyguardAndFinishSurfaceBehindRemoteAnimation#postAfterTraversal: mPM.isInteractive()=" + keyguardViewMediator.mPM.isInteractive() + " mPendingLock=" + keyguardViewMediator.mPendingLock + ". One of these being false means we re-locked the device during unlock. Do not proceed to finish keyguard exit and unlock.");
                keyguardViewMediator.doKeyguardLocked(null, false);
                keyguardViewMediator.finishSurfaceBehindRemoteAnimation(true);
                keyguardViewMediator.setShowingLocked$1(true, true);
            }
        });
        DejankUtils.setImmediate(false);
    }

    public void finishSurfaceBehindRemoteAnimation(boolean z) {
        ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).notifyFinishedKeyguardExitAnimation(z);
        this.mSurfaceBehindRemoteAnimationRequested = false;
        this.mSurfaceBehindRemoteAnimationRunning = false;
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(false);
        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = this.mSurfaceBehindRemoteAnimationFinishedCallback;
        if (iRemoteAnimationFinishedCallback != null) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } finally {
                try {
                } finally {
                }
            }
        }
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

    public IRemoteAnimationRunner getExitAnimationRunner() {
        AnonymousClass17 anonymousClass17 = new AnonymousClass17(this.mExitAnimationRunner);
        this.mHelper.exitAnimationRunner = anonymousClass17;
        return anonymousClass17;
    }

    public final long getLockTimeout$1(int i) {
        long intForUser = this.mSecureSettings.getIntForUser("lock_screen_lock_after_timeout", 5000, i);
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) this.mHelper.knoxStateMonitor).mEdmMonitor;
        int i2 = edmMonitor == null ? 0 : edmMonitor.mLockDelay;
        if (i2 >= 0) {
            LogUtil.d("KeyguardViewMediator", "mdmDelay=%d, lockAfterTimeout=%d", Integer.valueOf(i2), Long.valueOf(intForUser));
            intForUser = Math.min(i2 * 1000, intForUser);
        }
        long maximumTimeToLock = this.mLockPatternUtils.getDevicePolicyManager().getMaximumTimeToLock(null, i);
        return maximumTimeToLock <= 0 ? intForUser : Math.max(Math.min(maximumTimeToLock - Math.max(this.mSystemSettings.getIntForUser("screen_off_timeout", PluginLockInstancePolicy.DISABLED_BY_SUB_USER, i), 0L), intForUser), 0L);
    }

    public IRemoteAnimationRunner getOccludeAnimationRunner() {
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        Flags.keyguardWmStateRefactor();
        return new AnonymousClass17(this.mHelper.occludeAnimationRunner);
    }

    public IRemoteAnimationRunner getOccludeByDreamAnimationRunner() {
        return new AnonymousClass17(this.mOccludeByDreamAnimationRunner);
    }

    public IRemoteAnimationRunner getUnoccludeAnimationRunner() {
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        Flags.keyguardWmStateRefactor();
        return new AnonymousClass17(this.mHelper.unoccluedAnimationRunner);
    }

    public ViewMediatorCallback getViewMediatorCallback() {
        return this.mViewMediatorCallback;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0099 A[Catch: all -> 0x0054, TryCatch #0 {all -> 0x0054, blocks: (B:12:0x002f, B:14:0x0049, B:15:0x0052, B:18:0x0057, B:20:0x005c, B:22:0x0064, B:25:0x006f, B:27:0x0072, B:29:0x0076, B:31:0x007a, B:33:0x0085, B:38:0x0095, B:40:0x0099, B:41:0x00a0, B:42:0x00d5, B:44:0x00d9, B:46:0x00f0, B:49:0x00df, B:51:0x00e3, B:52:0x0091, B:54:0x00a8), top: B:11:0x002f }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00d9 A[Catch: all -> 0x0054, TryCatch #0 {all -> 0x0054, blocks: (B:12:0x002f, B:14:0x0049, B:15:0x0052, B:18:0x0057, B:20:0x005c, B:22:0x0064, B:25:0x006f, B:27:0x0072, B:29:0x0076, B:31:0x007a, B:33:0x0085, B:38:0x0095, B:40:0x0099, B:41:0x00a0, B:42:0x00d5, B:44:0x00d9, B:46:0x00f0, B:49:0x00df, B:51:0x00e3, B:52:0x0091, B:54:0x00a8), top: B:11:0x002f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleHide$1() {
        /*
            Method dump skipped, instructions count: 247
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediator.handleHide$1():void");
    }

    public final void handleKeyguardDone$1() {
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDone");
        final int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(false);
        this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda63
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                int i = selectedUserId;
                if (keyguardViewMediator.mLockPatternUtils.isSecure(i)) {
                    keyguardViewMediator.mLockPatternUtils.getDevicePolicyManager().reportKeyguardDismissed(i);
                }
            }
        });
        android.util.Log.d("KeyguardViewMediator", "handleKeyguardDone");
        synchronized (this) {
            resetKeyguardDonePendingLocked$1();
        }
        if (this.mGoingToSleep && !shouldWaitForProvisioning$1()) {
            android.util.Log.i("KeyguardViewMediator", "Device is going to sleep, aborting keyguardDone");
            this.mUpdateMonitor.clearFingerprintRecognized(selectedUserId);
            this.mDismissCallbackRegistry.notifyDismissCancelled();
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).onDismissCancelled();
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
            Objects.requireNonNull(keyguardViewMediatorHelperImpl);
            Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda10(keyguardViewMediatorHelperImpl, 2), true);
            return;
        }
        setPendingLock(false);
        handleHide$1();
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) this.mKeyguardInteractor.repository;
        keyguardRepositoryImpl.getClass();
        keyguardRepositoryImpl.keyguardDoneAnimationsFinished.tryEmit(Unit.INSTANCE);
        this.mUpdateMonitor.clearFingerprintRecognized(selectedUserId);
        Trace.endSection();
    }

    public void hideSurfaceBehindKeyguard() {
        Log.d("KeyguardViewMediator", "hideSurfaceBehindKeyguard");
        this.mSurfaceBehindRemoteAnimationRequested = false;
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(false);
        if (this.mShowing) {
            setShowingLocked$1(true, true);
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
            Objects.requireNonNull(keyguardViewMediatorHelperImpl);
            Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda10(keyguardViewMediatorHelperImpl, 4), true);
        }
    }

    public void hideWithAnimation(ActivityTransitionAnimator.Runner runner) {
        if (this.mKeyguardDonePending) {
            this.mKeyguardExitAnimationRunner = runner;
            this.mViewMediatorCallback.readyForKeyguardDone();
        }
    }

    public boolean isAnimatingBetweenKeyguardAndSurfaceBehind() {
        return this.mSurfaceBehindRemoteAnimationRunning;
    }

    public boolean isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe() {
        return this.mSurfaceBehindRemoteAnimationRunning || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mFlingingToDismissKeyguard;
    }

    public boolean isAnySimPinSecure() {
        for (int i = 0; i < this.mLastSimStates.size(); i++) {
            if (KeyguardUpdateMonitor.isSimPinSecure(this.mLastSimStates.get(this.mLastSimStates.keyAt(i)))) {
                return true;
            }
        }
        return false;
    }

    public boolean isHiding() {
        return this.mHiding;
    }

    public boolean isInputRestricted() {
        return this.mShowing || this.mNeedToReshowWhenReenabled;
    }

    public boolean isOccludeAnimationPlaying() {
        return this.mOccludeAnimationPlaying;
    }

    public boolean isSecure() {
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        boolean z = Rune.SYSUI_MULTI_SIM;
        int i = keyguardViewMediatorHelperImpl.switchingUserId;
        return i != -1 ? isSecure(i) : isSecure(this.mSelectedUserInteractor.getSelectedUserId());
    }

    public boolean isShowingAndNotOccluded() {
        return this.mShowing && !this.mOccluded;
    }

    public void launchingActivityOverLockscreen(boolean z) {
        this.mKeyguardTransitions.setLaunchingActivityOverLockscreen(z);
    }

    public void maybeHandlePendingLock() {
        if (this.mPendingLock) {
            if (this.mScreenOffAnimationController.shouldDelayKeyguardShow()) {
                android.util.Log.d("KeyguardViewMediator", "#maybeHandlePendingLock: not handling because the screen off animation's shouldDelayKeyguardShow() returned true. This should be handled soon by #onStartedWakingUp, or by the end actions of the screen off animation.");
            } else {
                if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway) {
                    android.util.Log.d("KeyguardViewMediator", "#maybeHandlePendingLock: not handling because the keyguard is going away. This should be handled shortly by StatusBar#finishKeyguardFadingAway.");
                    return;
                }
                android.util.Log.d("KeyguardViewMediator", "#maybeHandlePendingLock: handling pending lock; locking keyguard.");
                doKeyguardLocked(null, false);
                setPendingLock(false);
            }
        }
    }

    public final void maybeSendUserPresentBroadcast$1() {
        boolean z = this.mSystemReady;
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        if ((z && this.mLockPatternUtils.isLockScreenDisabled(selectedUserInteractor.getSelectedUserId())) || (!this.mShowing && this.mHelper.isKeyguardDisabledBySettings(false))) {
            sendUserPresentBroadcast$1();
        } else if (this.mSystemReady && shouldWaitForProvisioning$1()) {
            this.mLockPatternUtils.userPresent(selectedUserInteractor.getSelectedUserId());
        }
    }

    public final void notifyTrustedChangedLocked$1(boolean z) {
        for (int size = this.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
            try {
                ((IKeyguardStateCallback) this.mKeyguardStateCallbacks.get(size)).onTrustedChanged(z);
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "Failed to call notifyTrustedChangedLocked", e);
                if (e instanceof DeadObjectException) {
                    this.mKeyguardStateCallbacks.remove(size);
                }
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void onBootCompleted() {
        synchronized (this) {
            try {
                if (this.mContext.getResources().getBoolean(android.R.bool.config_imeDrawsImeNavBar)) {
                    ((GuestUserInteractor) this.mUserSwitcherController.guestUserInteractor$delegate.getValue()).onDeviceBootCompleted();
                }
                this.mBootCompleted = true;
                adjustStatusBarLocked$1(false, true);
                if (this.mBootSendUserPresent) {
                    sendUserPresentBroadcast$1();
                }
                if (LsRune.SUBSCREEN_UI) {
                    this.mHandler.obtainMessage(VolteConstants.ErrorCode.QOS_FAILURE).sendToTarget();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onDozeAmountChanged(float f, float f2) {
        if (this.mAnimatingScreenOff && this.mDozing && f == 1.0f) {
            this.mAnimatingScreenOff = false;
            setShowingLocked$1(this.mShowing, true);
        }
    }

    public void onDreamingStarted() {
        this.mUpdateMonitor.dispatchDreamingStarted();
        synchronized (this) {
            try {
                if (this.mDeviceInteractive && !this.mHelper.desktopManager.isDualView()) {
                    long lockTimeout$1 = getLockTimeout$1(this.mSelectedUserInteractor.getSelectedUserId());
                    if (lockTimeout$1 == 0) {
                        doKeyguardLocked(null, false);
                    } else {
                        doKeyguardLaterLocked$1(lockTimeout$1);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void onDreamingStopped() {
        this.mUpdateMonitor.dispatchDreamingStopped();
        synchronized (this) {
            if (this.mDeviceInteractive) {
                this.mDelayedShowingSequence++;
            }
        }
    }

    public void onFinishedGoingToSleep(int i, boolean z) {
        synchronized (this) {
            try {
                this.mDeviceInteractive = false;
                this.mGoingToSleep = false;
                this.mWakeAndUnlocking = false;
                this.mAnimatingScreenOff = this.mDozeParameters.shouldAnimateDozingChange();
                resetKeyguardDonePendingLocked$1();
                this.mHideAnimationRun = false;
                android.util.Log.d("KeyguardViewMediator", "notifyFinishedGoingToSleep");
                this.mHandler.sendEmptyMessage(5);
                if (z) {
                    ((PowerManager) this.mContext.getSystemService(PowerManager.class)).wakeUp(this.mSystemClock.uptimeMillis(), 5, "com.android.systemui:CAMERA_GESTURE_PREVENT_LOCK");
                    setPendingLock(false);
                    this.mPendingReset = false;
                    this.mPowerGestureIntercepted = true;
                    android.util.Log.d("KeyguardViewMediator", "cameraGestureTriggered=" + z + ",mPowerGestureIntercepted=" + this.mPowerGestureIntercepted);
                }
                if (this.mPendingReset) {
                    resetStateLocked$1(true);
                    this.mPendingReset = false;
                }
                maybeHandlePendingLock();
                if (!this.mLockLater && !z) {
                    doKeyguardLaterForChildProfilesLocked$1();
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

    public final void onKeyguardExitFinished$1() {
        int i;
        android.util.Log.d("KeyguardViewMediator", "onKeyguardExitFinished()");
        boolean equals = TelephonyManager.EXTRA_STATE_IDLE.equals(this.mPhoneState);
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        if (equals && (i = this.mUnlockSoundId) != 0) {
            keyguardViewMediatorHelperImpl.playSound$2(i);
        }
        setShowingLocked(false);
        this.mWakeAndUnlocking = false;
        this.mDismissCallbackRegistry.notifyDismissSucceeded();
        resetKeyguardDonePendingLocked$1();
        this.mHideAnimationRun = false;
        adjustStatusBarLocked$1(false, false);
        sendUserPresentBroadcast$1();
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        Flags.keyguardWmStateRefactor();
        FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor = (FromLockscreenTransitionInteractor) this.mKeyguardInteractor.fromLockscreenTransitionInteractor.get();
        fromLockscreenTransitionInteractor.getClass();
        BuildersKt.launch$default(fromLockscreenTransitionInteractor.scope, EmptyCoroutineContext.INSTANCE, null, new FromLockscreenTransitionInteractor$dismissKeyguard$$inlined$launch$default$1("FromLockscreenTransitionInteractor#dismissKeyguard", null, fromLockscreenTransitionInteractor), 2);
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda10(keyguardViewMediatorHelperImpl, 3), true);
    }

    public void onScreenTurnedOff() {
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda10(keyguardViewMediatorHelperImpl, 0), true);
        this.mUpdateMonitor.mHandler.sendEmptyMessage(CustomDeviceManager.DESTINATION_ADDRESS);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda8] */
    public void onStartedGoingToSleep(int i) {
        boolean z;
        int i2;
        synchronized (this) {
            try {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                Rune.runIf(i, new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl, 0));
                this.mDeviceInteractive = false;
                this.mPowerGestureIntercepted = false;
                this.mGoingToSleep = true;
                int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(false);
                if (!this.mLockPatternUtils.getPowerButtonInstantlyLocks(selectedUserId) && this.mLockPatternUtils.isSecure(selectedUserId)) {
                    z = false;
                    final long lockTimeout$1 = getLockTimeout$1(this.mSelectedUserInteractor.getSelectedUserId(false));
                    this.mLockLater = false;
                    if (this.mShowing || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway) {
                        this.mHelper.updatePendingLock(i, lockTimeout$1, z, selectedUserId, new KeyguardViewMediator$$ExternalSyntheticLambda1(this, 7), new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda8
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                                keyguardViewMediator.doKeyguardLaterLocked$1(lockTimeout$1);
                                keyguardViewMediator.mLockLater = true;
                            }
                        });
                    } else {
                        this.mPendingReset = true;
                    }
                    if (this.mPendingLock && (i2 = this.mLockSoundId) != 0) {
                        this.mHelper.playSound$2(i2);
                    }
                }
                z = true;
                final long lockTimeout$12 = getLockTimeout$1(this.mSelectedUserInteractor.getSelectedUserId(false));
                this.mLockLater = false;
                if (this.mShowing) {
                }
                this.mHelper.updatePendingLock(i, lockTimeout$12, z, selectedUserId, new KeyguardViewMediator$$ExternalSyntheticLambda1(this, 7), new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                        keyguardViewMediator.doKeyguardLaterLocked$1(lockTimeout$12);
                        keyguardViewMediator.mLockLater = true;
                    }
                });
                if (this.mPendingLock) {
                    this.mHelper.playSound$2(i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mUpdateMonitor.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(321, i, 0));
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass162 = this.mUpdateMonitor.mHandler;
        anonymousClass162.sendMessage(anonymousClass162.obtainMessage(342, Boolean.FALSE));
        this.mHandler.sendEmptyMessage(17);
    }

    public void onStartedWakingUp(int i, boolean z) {
        Trace.beginSection("KeyguardViewMediator#onStartedWakingUp");
        synchronized (this) {
            try {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                Rune.runIf(i, new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl, 1));
                this.mDeviceInteractive = true;
                if (this.mPendingLock && !z && !this.mWakeAndUnlocking) {
                    doKeyguardLocked(null, false);
                }
                this.mAnimatingScreenOff = false;
                this.mDelayedShowingSequence++;
                this.mDelayedProfileShowingSequence++;
                boolean z2 = LsRune.COVER_SUPPORTED;
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                if (z2) {
                    keyguardViewMediatorHelperImpl2.cancelLockWhenCoverIsOpened(Boolean.FALSE.booleanValue());
                }
                if (z) {
                    this.mPowerGestureIntercepted = true;
                }
                android.util.Log.d("KeyguardViewMediator", "onStartedWakingUp, seq = " + this.mDelayedShowingSequence + ", mPowerGestureIntercepted = " + this.mPowerGestureIntercepted);
                android.util.Log.d("KeyguardViewMediator", "notifyStartedWakingUp");
                this.mHandler.sendEmptyMessage(14);
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl3);
                Rune.runIf(new KeyguardViewMediator$$ExternalSyntheticLambda10(keyguardViewMediatorHelperImpl3, 1), z2);
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mUiEventLogger.logWithInstanceIdAndPosition(BiometricUnlockController.BiometricUiEvent.STARTED_WAKING_UP, 0, (String) null, this.mSessionTracker.getSessionId(1), i);
        this.mUpdateMonitor.dispatchStartedWakingUp(i);
        maybeSendUserPresentBroadcast$1();
        Trace.endSection();
    }

    public void onSystemReady() {
        this.mHandler.obtainMessage(18).sendToTarget();
    }

    @Override // com.android.systemui.CoreStartable
    public final void onTrimMemory(int i) {
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        if (keyguardViewMediatorHelperImpl.isShowing$1() || i < 40) {
            return;
        }
        ((KeyguardViewController) keyguardViewMediatorHelperImpl.viewControllerLazy.get()).onTrimMemory(i);
    }

    public void onWakeAndUnlocking(boolean z) {
        Trace.beginSection("KeyguardViewMediator#onWakeAndUnlocking");
        boolean z2 = true;
        this.mWakeAndUnlocking = true;
        setUnlockAndWakeFromDream$1(3, z);
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        boolean z3 = Rune.SYSUI_MULTI_SIM;
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = keyguardViewMediatorHelperImpl.fastUnlockController;
        keyguardFastBioUnlockController.logLapTime("onWakeAndUnlocking", new Object[0]);
        KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_BIO_WAKE_AND_UNLOCK);
        if (keyguardFastBioUnlockController.isFastWakeAndUnlockMode()) {
            String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
            keyguardViewMediatorHelperImpl.removeShowMsg();
            ((KeyguardViewController) keyguardViewMediatorHelperImpl.viewControllerLazy.get()).onWakeAndUnlock();
        } else {
            z2 = false;
        }
        if (z2) {
            tryKeyguardDone$1();
        } else {
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).notifyKeyguardAuthenticated(false);
            userActivity();
        }
        Trace.endSection();
    }

    public void registerCentralSurfaces$1(CentralSurfaces centralSurfaces, ShadeLockscreenInteractor shadeLockscreenInteractor, ShadeExpansionStateManager shadeExpansionStateManager, BiometricUnlockController biometricUnlockController, View view) {
        this.mCentralSurfaces = centralSurfaces;
        ViewGroup viewGroup = ((CentralSurfacesImpl) centralSurfaces).mSecLockIconView;
        Lazy lazy = this.mKeyguardViewControllerLazy;
        ((KeyguardViewController) lazy.get()).registerLockIconContainer(viewGroup);
        ((KeyguardViewController) lazy.get()).registerCentralSurfaces(centralSurfaces, shadeLockscreenInteractor, shadeExpansionStateManager, biometricUnlockController, view);
    }

    public boolean requestedShowSurfaceBehindKeyguard() {
        return this.mSurfaceBehindRemoteAnimationRequested;
    }

    public final void resetKeyguardDonePendingLocked$1() {
        android.util.Log.d("KeyguardViewMediator", "resetKeyguardDonePendingLocked: ");
        this.mKeyguardDonePending = false;
        this.mHandler.removeMessages(13);
    }

    public final void resetStateLocked$1(boolean z) {
        android.util.Log.e("KeyguardViewMediator", "resetStateLocked");
        AnonymousClass13 anonymousClass13 = this.mHandler;
        anonymousClass13.sendMessage(anonymousClass13.obtainMessage(3, z ? 1 : 0, 0));
    }

    public final void scheduleNonStrongBiometricIdleTimeout$1() {
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(false);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if ((keyguardUpdateMonitor.isFaceClass3() || !keyguardUpdateMonitor.isUnlockWithFacePossible(selectedUserId)) && !(keyguardUpdateMonitor.isFingerprintClass3() && keyguardUpdateMonitor.isUnlockWithFingerprintPossible(selectedUserId))) {
            return;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(selectedUserId, "scheduleNonStrongBiometricIdleTimeout: schedule an alarm for currentUser=", "KeyguardViewMediator");
        this.mLockPatternUtils.scheduleNonStrongBiometricIdleTimeout(selectedUserId);
    }

    public final void sendUserPresentBroadcast$1() {
        synchronized (this) {
            try {
                if (this.mBootCompleted) {
                    final int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
                    final UserHandle userHandle = new UserHandle(selectedUserId);
                    final UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                    this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                            UserManager userManager2 = userManager;
                            UserHandle userHandle2 = userHandle;
                            int i = selectedUserId;
                            keyguardViewMediator.getClass();
                            for (int i2 : userManager2.getProfileIdsWithDisabled(userHandle2.getIdentifier())) {
                                keyguardViewMediator.mContext.sendBroadcastAsUser(KeyguardViewMediator.USER_PRESENT_INTENT, UserHandle.of(i2), null, KeyguardViewMediator.USER_PRESENT_INTENT_OPTIONS);
                                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediator.mHelper;
                                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                                Rune.runIf(i2, new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl, 2));
                            }
                            keyguardViewMediator.mLockPatternUtils.userPresent(i);
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

    public void setBlursDisabledForAppLaunch(boolean z) {
        ((NotificationShadeDepthController) this.mNotificationShadeDepthController.get()).setBlursDisabledForAppLaunch(z);
    }

    public void setCurrentUser(int i) {
        int i2 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
        synchronized (KeyguardUpdateMonitor.class) {
            KeyguardUpdateMonitor.sCurrentUser = i;
        }
        synchronized (this) {
            notifyTrustedChangedLocked$1(this.mUpdateMonitor.getUserHasTrust(i));
        }
    }

    public void setDozing(boolean z) {
        if (z == this.mDozing) {
            return;
        }
        this.mDozing = z;
        if (!z) {
            this.mAnimatingScreenOff = false;
        }
        if (this.mShowing || !this.mPendingLock || !this.mDozeParameters.canControlUnlockedScreenOff() || (LsRune.AOD_FULLSCREEN && this.mHelper.aodAmbientWallpaperHelper.isAODFullScreenMode())) {
            setShowingLocked(this.mShowing);
        }
    }

    public void setKeyguardEnabled(boolean z) {
        synchronized (this) {
            try {
                android.util.Log.d("KeyguardViewMediator", "setKeyguardEnabled(" + z + ")");
                this.mExternallyEnabled = z;
                if (z || !this.mShowing) {
                    if (z && this.mNeedToReshowWhenReenabled) {
                        android.util.Log.d("KeyguardViewMediator", "previously hidden, reshowing, reenabling status bar expansion");
                        this.mNeedToReshowWhenReenabled = false;
                        updateInputRestrictedLocked$1();
                        showKeyguard$1(null);
                        this.mWaitingUntilKeyguardVisible = true;
                        this.mHandler.sendEmptyMessageDelayed(8, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
                        android.util.Log.d("KeyguardViewMediator", "waiting until mWaitingUntilKeyguardVisible is false");
                        while (this.mWaitingUntilKeyguardVisible) {
                            try {
                                wait();
                            } catch (InterruptedException unused) {
                                Thread.currentThread().interrupt();
                            }
                        }
                        android.util.Log.d("KeyguardViewMediator", "done waiting for mWaitingUntilKeyguardVisible");
                    }
                } else {
                    if (this.mLockPatternUtils.isUserInLockdown(this.mSelectedUserInteractor.getSelectedUserId(false))) {
                        android.util.Log.d("KeyguardViewMediator", "keyguardEnabled(false) overridden by user lockdown");
                        return;
                    }
                    android.util.Log.d("KeyguardViewMediator", "remembering to reshow, hiding keyguard, disabling status bar expansion");
                    this.mNeedToReshowWhenReenabled = true;
                    updateInputRestrictedLocked$1();
                    Trace.beginSection("KeyguardViewMediator#hideLocked");
                    android.util.Log.d("KeyguardViewMediator", "hideLocked");
                    AnonymousClass13 anonymousClass13 = this.mHandler;
                    anonymousClass13.sendMessage(anonymousClass13.obtainMessage(2));
                    Trace.endSection();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setOccluded(boolean z, boolean z2) {
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        int andIncrement = keyguardViewMediatorHelperImpl.occludedSeq.getAndIncrement();
        keyguardViewMediatorHelperImpl.getHandler$1().post(new KeyguardViewMediatorHelperImpl$cancelAODJankMonitor$1(keyguardViewMediatorHelperImpl));
        if (!z && keyguardViewMediatorHelperImpl.getHandler$1().hasMessages(keyguardViewMediatorHelperImpl.getSET_OCCLUDED()) && keyguardViewMediatorHelperImpl.isKeyguardHiding()) {
            startKeyguardExitAnimation(0L, 0L);
        }
        keyguardViewMediatorHelperImpl.getHandler$1().removeMessages(keyguardViewMediatorHelperImpl.getSET_OCCLUDED());
        keyguardViewMediatorHelperImpl.getHandler$1().sendMessage(keyguardViewMediatorHelperImpl.getHandler$1().obtainMessage(keyguardViewMediatorHelperImpl.getSET_OCCLUDED(), z ? 1 : 0, z2 ? 1 : 0, Integer.valueOf(andIncrement)));
        synchronized (keyguardViewMediatorHelperImpl.lock$delegate.getValue()) {
            keyguardViewMediatorHelperImpl.curIsOccluded = z;
            Unit unit = Unit.INSTANCE;
        }
    }

    public void setPendingLock(boolean z) {
        this.mPendingLock = z;
        Trace.traceCounter(4096L, "pendingLock", z ? 1 : 0);
    }

    public void setShowingLocked(boolean z) {
        setShowingLocked$1(z, false);
    }

    public final void setShowingLocked$1(final boolean z, boolean z2) {
        Bundle bundle;
        boolean z3 = this.mDozing && !this.mWakeAndUnlocking;
        boolean z4 = this.mShowing;
        boolean z5 = z != z4 || z2;
        boolean z6 = (z == z4 && z3 == this.mAodShowing && !z2) ? false : true;
        boolean z7 = z4 != z;
        this.mShowing = z;
        this.mAodShowing = z3;
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("setShowingLocked: notifyDefaultDisplayCallbacks=", " showing=", z5);
        m.append(this.mShowing);
        m.append(" aodShowing=");
        ActionBarContextView$$ExternalSyntheticOutline0.m(m, this.mAodShowing, "KeyguardViewMediator");
        if (z5) {
            DejankUtils.whitelistIpcs(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                    boolean z8 = z;
                    SelectedUserInteractor selectedUserInteractor = keyguardViewMediator.mSelectedUserInteractor;
                    for (int size = keyguardViewMediator.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
                        IKeyguardStateCallback iKeyguardStateCallback = (IKeyguardStateCallback) keyguardViewMediator.mKeyguardStateCallbacks.get(size);
                        try {
                            android.util.Log.d("KeyguardViewMediator", "notifyDefaultDisplayCallbacks: showing=" + z8 + " userId=" + selectedUserInteractor.getSelectedUserId());
                            iKeyguardStateCallback.onShowingStateChanged(z8, selectedUserInteractor.getSelectedUserId());
                        } catch (RemoteException e) {
                            Slog.w("KeyguardViewMediator", "Failed to call onShowingStateChanged", e);
                            if (e instanceof DeadObjectException) {
                                keyguardViewMediator.mKeyguardStateCallbacks.remove(iKeyguardStateCallback);
                            }
                        }
                    }
                }
            });
            updateInputRestrictedLocked$1();
            if (z7) {
                Executor executor = this.mUiBgExecutor;
                TrustManager trustManager = this.mTrustManager;
                Objects.requireNonNull(trustManager);
                executor.execute(new KeyguardViewMediator$$ExternalSyntheticLambda5(trustManager, 0));
            }
        }
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        if (z6) {
            KeyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1 keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1 = keyguardViewMediatorHelperImpl.setLockScreenShownRunnable;
            keyguardViewMediatorHelperImpl.removeCallbacks$1$1(keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1);
            keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1.showing = z;
            keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1.aodShowing = z3;
            if (z && (bundle = keyguardViewMediatorHelperImpl.showingOptions) != null && bundle.getBoolean("LockShownDelay", false)) {
                Log.i("KeyguardViewMediator", "updateActivityLockScreenState " + z + " " + z3 + " after 300ms");
                keyguardViewMediatorHelperImpl.getHandler$1().postDelayed(keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1, 300L);
            } else {
                keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1.run();
            }
        }
        keyguardViewMediatorHelperImpl.setShowingOptions(null);
    }

    public void setSwitchingUser(boolean z) {
        Log.d("KeyguardViewMediator", "setSwitchingUser " + z);
        this.mUpdateMonitor.setSwitchingUser(z);
    }

    public final void setUnlockAndWakeFromDream$1(int i, boolean z) {
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
            ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "]", "KeyguardViewMediator");
        }
    }

    public final void setupLocked$1() {
        boolean z;
        boolean z2;
        this.mBroadcastDispatcher.registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("android.intent.action.ACTION_SHUTDOWN"), this.mBroadcastReceiver);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        intentFilter.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
        intentFilter.setPriority(1000);
        this.mContext.registerReceiver(this.mDelayedLockBroadcastReceiver, intentFilter, "com.android.systemui.permission.SELF", null, 2);
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        Flags.refactorGetCurrentUser();
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        int i = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
        synchronized (KeyguardUpdateMonitor.class) {
            KeyguardUpdateMonitor.sCurrentUser = userId;
        }
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        keyguardViewMediatorHelperImpl.getClass();
        boolean z3 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
        if (z3 || LsRune.KEYGUARD_SUB_DISPLAY_COVER) {
            keyguardViewMediatorHelperImpl.foldControllerImpl.handler = keyguardViewMediatorHelperImpl.getHandler$1();
        }
        if (z3) {
            KeyguardVisibilityMonitor keyguardVisibilityMonitor = keyguardViewMediatorHelperImpl.keyguardVisibilityMonitor;
            KeyguardViewMediatorHelperImpl$setupLocked$1 keyguardViewMediatorHelperImpl$setupLocked$1 = new KeyguardViewMediatorHelperImpl$setupLocked$1(keyguardViewMediatorHelperImpl);
            ArrayList arrayList = (ArrayList) keyguardVisibilityMonitor.panelStateChangedListeners;
            if (!arrayList.contains(keyguardViewMediatorHelperImpl$setupLocked$1)) {
                arrayList.add(keyguardViewMediatorHelperImpl$setupLocked$1);
            }
        }
        if (DeviceType.isSupportPenDetachmentOption(keyguardViewMediatorHelperImpl.context)) {
            BroadcastDispatcher.registerReceiver$default(keyguardViewMediatorHelperImpl.broadcastDispatcher, keyguardViewMediatorHelperImpl.broadcastReceiver, new IntentFilter("com.samsung.pen.INSERT"), null, null, 0, null, 60);
        }
        BroadcastDispatcher broadcastDispatcher = keyguardViewMediatorHelperImpl.broadcastDispatcher;
        KeyguardViewMediatorHelperImpl$broadcastReceiver$1 keyguardViewMediatorHelperImpl$broadcastReceiver$1 = keyguardViewMediatorHelperImpl.broadcastReceiver;
        IntentFilter m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.samsung.internal.policy.impl.Keyguard.PCW_LOCKED", "com.samsung.internal.policy.impl.Keyguard.PCW_UNLOCKED");
        Unit unit = Unit.INSTANCE;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, keyguardViewMediatorHelperImpl$broadcastReceiver$1, m, null, null, 0, "com.samsung.android.permission.LOCK_SECURITY_MONITOR", 28);
        BroadcastDispatcher.registerReceiver$default(keyguardViewMediatorHelperImpl.broadcastDispatcher, keyguardViewMediatorHelperImpl.broadcastReceiver, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.sec.android.FindingLostPhonePlus.CANCEL", "com.sec.android.FindingLostPhonePlus.SUBSCRIBE"), null, null, 0, null, 60);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(keyguardViewMediatorHelperImpl.context);
        KeyguardViewMediatorHelperImpl$localReceiver$1 keyguardViewMediatorHelperImpl$localReceiver$1 = keyguardViewMediatorHelperImpl.localReceiver;
        IntentFilter intentFilter2 = new IntentFilter("com.samsung.keyguard.CLEAR_LOCK");
        synchronized (localBroadcastManager.mReceivers) {
            try {
                LocalBroadcastManager.ReceiverRecord receiverRecord = new LocalBroadcastManager.ReceiverRecord(intentFilter2, keyguardViewMediatorHelperImpl$localReceiver$1);
                ArrayList arrayList2 = (ArrayList) localBroadcastManager.mReceivers.get(keyguardViewMediatorHelperImpl$localReceiver$1);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList(1);
                    localBroadcastManager.mReceivers.put(keyguardViewMediatorHelperImpl$localReceiver$1, arrayList2);
                }
                arrayList2.add(receiverRecord);
                z = false;
                for (int i2 = 0; i2 < intentFilter2.countActions(); i2++) {
                    String action = intentFilter2.getAction(i2);
                    ArrayList arrayList3 = (ArrayList) localBroadcastManager.mActions.get(action);
                    if (arrayList3 == null) {
                        arrayList3 = new ArrayList(1);
                        localBroadcastManager.mActions.put(action, arrayList3);
                    }
                    arrayList3.add(receiverRecord);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        keyguardViewMediatorHelperImpl.updateMonitor.setupLocked$2();
        SemDvfsManager createInstance = SemDvfsManager.createInstance(keyguardViewMediatorHelperImpl.context, "KEYGUARD_UNLOCK");
        if (createInstance != null && createInstance.checkHintSupported(3100)) {
            createInstance.setHint(3100);
            keyguardViewMediatorHelperImpl.dvfsManager = createInstance;
        }
        final KeyguardFastBioUnlockController keyguardFastBioUnlockController = keyguardViewMediatorHelperImpl.fastUnlockController;
        keyguardFastBioUnlockController.getClass();
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
            keyguardFastBioUnlockController.wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$init$1
                @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
                public final void onPostFinishedWakingUp() {
                    KeyguardFastBioUnlockController.Companion companion = KeyguardFastBioUnlockController.Companion;
                    final KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = KeyguardFastBioUnlockController.this;
                    if (keyguardFastBioUnlockController2.isBrightnessChangedCallbackRegistered) {
                        KeyguardFastBioUnlockController.logD("unregisterBrightnessListener");
                        ((DisplayTrackerImpl) keyguardFastBioUnlockController2.displayTracker).removeCallback(keyguardFastBioUnlockController2.brightnessChangedCallback);
                        keyguardFastBioUnlockController2.isBrightnessChangedCallbackRegistered = false;
                    }
                    if (keyguardFastBioUnlockController2.isFastWakeAndUnlockMode() && keyguardFastBioUnlockController2.needsBlankScreen && keyguardFastBioUnlockController2.curIsAodBrighterThanNormal) {
                        keyguardFastBioUnlockController2.mainHandler.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$init$1$onPostFinishedWakingUp$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardFastBioUnlockController keyguardFastBioUnlockController3 = KeyguardFastBioUnlockController.this;
                                KeyguardFastBioUnlockController.Companion companion2 = KeyguardFastBioUnlockController.Companion;
                                keyguardFastBioUnlockController3.getClass();
                                KeyguardFastBioUnlockController.logD("cancel blank scrim");
                                ((ScrimController) KeyguardFastBioUnlockController.this.scrimControllerLazy.get()).onScreenTurnedOn();
                            }
                        }, 64L);
                    }
                }

                @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
                public final void onStartedGoingToSleep() {
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = KeyguardFastBioUnlockController.this;
                    if (((KeyguardUpdateMonitor) keyguardFastBioUnlockController2.updateMonitorLazy.get()).isFingerprintOptionEnabled() && ((KeyguardUpdateMonitor) keyguardFastBioUnlockController2.updateMonitorLazy.get()).isEnabledWof() && !keyguardFastBioUnlockController2.isBrightnessChangedCallbackRegistered) {
                        KeyguardFastBioUnlockController.logD("registerBrightnessListener");
                        DisplayTrackerImpl displayTrackerImpl = (DisplayTrackerImpl) keyguardFastBioUnlockController2.displayTracker;
                        displayTrackerImpl.addBrightnessChangeCallback(keyguardFastBioUnlockController2.brightnessChangedCallback, keyguardFastBioUnlockController2.executor);
                        displayTrackerImpl.addDisplayChangeCallback(keyguardFastBioUnlockController2.brightnessChangedCallback, keyguardFastBioUnlockController2.executor);
                        keyguardFastBioUnlockController2.isBrightnessChangedCallbackRegistered = true;
                    }
                }
            });
        }
        BackupRestoreReceiver backupRestoreReceiver = new BackupRestoreReceiver();
        Context context = keyguardViewMediatorHelperImpl.context;
        android.util.Log.d("WallpaperBackupRestoreReceiver", "registerBackupRestoreReceiver");
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.sec.android.intent.action.REQUEST_BACKUP_LOCKSCREEN");
        intentFilter3.addAction("com.sec.android.intent.action.REQUEST_RESTORE_LOCKSCREEN");
        intentFilter3.addAction("com.sec.android.intent.action.REQUEST_BACKUP_WALLPAPER");
        intentFilter3.addAction("com.sec.android.intent.action.REQUEST_RESTORE_WALLPAPER");
        context.registerReceiver(backupRestoreReceiver.mBroadcastReceiver, intentFilter3, "android.permission.SET_WALLPAPER", null, 2);
        if (LsRune.KEYGUARD_HOMEHUB) {
            BroadcastDispatcher.registerReceiver$default(keyguardViewMediatorHelperImpl.broadcastDispatcher, keyguardViewMediatorHelperImpl.broadcastReceiver, new IntentFilter("android.intent.action.DOCK_EVENT"), null, null, 0, null, 60);
        }
        ((KeyguardUnlockAnimationController) keyguardViewMediatorHelperImpl.unlockAnimationControllerLazy.get()).setCallback(new KeyguardViewMediatorHelperImpl$setupLocked$5(keyguardViewMediatorHelperImpl));
        BroadcastDispatcher.registerReceiver$default(keyguardViewMediatorHelperImpl.broadcastDispatcher, keyguardViewMediatorHelperImpl.broadcastReceiver, new IntentFilter("com.samsung.intent.action.OMC_CHANGED"), null, null, 0, null, 60);
        try {
            z2 = this.mContext.getPackageManager().getServiceInfo(new ComponentName(this.mContext, (Class<?>) KeyguardService.class), 0).isEnabled();
        } catch (PackageManager.NameNotFoundException unused) {
            z2 = true;
        }
        if (z2) {
            if (!shouldWaitForProvisioning$1() && !this.mHelper.isKeyguardDisabled(true) && !this.mLockPatternUtils.isLockScreenDisabled(this.mSelectedUserInteractor.getSelectedUserId(false))) {
                z = true;
            }
            setShowingLocked$1(z, true);
        } else {
            setShowingLocked$1(false, true);
        }
        KeyguardTransitions keyguardTransitions = this.mKeyguardTransitions;
        IRemoteAnimationRunner exitAnimationRunner = getExitAnimationRunner();
        int i3 = KeyguardService.$r8$clinit;
        keyguardTransitions.register(new KeyguardService.AnonymousClass1(this, exitAnimationRunner), new KeyguardService.AnonymousClass1(this, new AnonymousClass17(LsRune.AOD_FULLSCREEN_APPEAR_ANIMATION ? this.mHelper.aodAppearAnimationRunner : this.mAppearAnimationRunner)), new KeyguardService.AnonymousClass1(this, getOccludeAnimationRunner()), new KeyguardService.AnonymousClass1(this, getOccludeByDreamAnimationRunner()), new KeyguardService.AnonymousClass1(this, getUnoccludeAnimationRunner()));
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mDeviceInteractive = this.mPM.isInteractive();
        this.mLockSounds = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(13).setContentType(4).build()).build();
        String string = Settings.Global.getString(contentResolver, "lock_sound");
        if (string != null) {
            this.mLockSoundId = this.mLockSounds.load(string, 1);
        }
        if (string == null || this.mLockSoundId == 0) {
            MotionLayout$$ExternalSyntheticOutline0.m("failed to load lock sound from ", string, "KeyguardViewMediator");
        }
        String string2 = Settings.Global.getString(contentResolver, "unlock_sound");
        if (string2 != null) {
            this.mUnlockSoundId = this.mLockSounds.load(string2, 1);
        }
        if (string2 == null || this.mUnlockSoundId == 0) {
            MotionLayout$$ExternalSyntheticOutline0.m("failed to load unlock sound from ", string2, "KeyguardViewMediator");
        }
        String string3 = Settings.Global.getString(contentResolver, "trusted_sound");
        if (string3 != null) {
            this.mTrustedSoundId = this.mLockSounds.load(string3, 1);
        }
        if (string3 == null || this.mTrustedSoundId == 0) {
            MotionLayout$$ExternalSyntheticOutline0.m("failed to load trusted sound from ", string3, "KeyguardViewMediator");
        }
        Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda1(this, 8), true);
        this.mHideAnimation = AnimationUtils.loadAnimation(this.mContext, android.R.anim.recents_fade_out);
        new WorkLockActivityController(this.mContext, this.mUserTracker);
        this.mJavaAdapter.alwaysCollectFlow(((WallpaperRepositoryImpl) this.mWallpaperRepository).wallpaperSupportsAmbientMode, new KeyguardViewMediator$$ExternalSyntheticLambda79(this, 2));
    }

    public final boolean shouldWaitForProvisioning$1() {
        return (this.mUpdateMonitor.mDeviceProvisioned || isSecure()) ? false : true;
    }

    public void showDismissibleKeyguard() {
        if (!this.mFoldGracePeriodProvider.isEnabled()) {
            android.util.Log.e("KeyguardViewMediator", "fold grace period feature isn't enabled, but showKeyguard() method is being called", new Throwable());
        } else {
            if (!this.mUpdateMonitor.mDeviceProvisioned) {
                android.util.Log.d("KeyguardViewMediator", "Device not provisioned, so ignore request to show keyguard.");
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putBoolean("show_dismissible", true);
            showKeyguard$1(bundle);
        }
    }

    public final void showKeyguard$1(Bundle bundle) {
        Trace.beginSection("KeyguardViewMediator#showKeyguard acquiring mShowKeyguardWakeLock");
        android.util.Log.d("KeyguardViewMediator", "showKeyguard");
        this.mShowKeyguardWakeLock.acquire();
        AnonymousClass13 anonymousClass13 = this.mHandler;
        anonymousClass13.sendMessageAtFrontOfQueue(anonymousClass13.obtainMessage(1, bundle));
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        boolean z = Rune.SYSUI_MULTI_SIM;
        Integer num = 6;
        Long l = 4000L;
        keyguardViewMediatorHelperImpl.enableLooperLogController(num.intValue(), l.longValue());
        Trace.endSection();
    }

    public void showSurfaceBehindKeyguard() {
        Log.d("KeyguardViewMediator", "showSurfaceBehindKeyguard");
        this.mSurfaceBehindRemoteAnimationRequested = true;
        try {
            ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).getClass();
            ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(true);
            if (this.mHelper.keyguardGoingAway(6)) {
            } else {
                throw new RemoteException();
            }
        } catch (RemoteException e) {
            this.mSurfaceBehindRemoteAnimationRequested = false;
            android.util.Log.e("KeyguardViewMediator", "Failed to report keyguardGoingAway", e);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        synchronized (this) {
            setupLocked$1();
        }
    }

    public void startKeyguardExitAnimation(long j, long j2) {
        startKeyguardExitAnimation$1(0, j, j2, null, null, null, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void startKeyguardExitAnimation$1(int i, long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        Trace.beginSection("KeyguardViewMediator#startKeyguardExitAnimation");
        Message obtainMessage = this.mHandler.obtainMessage(VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, new StartKeyguardExitAnimParams(i, j, j2, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback, 0));
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        keyguardViewMediatorHelperImpl.getHandler$1().post(new KeyguardViewMediatorHelperImpl$cancelAODJankMonitor$1(keyguardViewMediatorHelperImpl));
        StartKeyguardExitAnimParams startKeyguardExitAnimParams = (StartKeyguardExitAnimParams) obtainMessage.obj;
        if (Debug.semIsProductDev() || LogUtil.isDebugLevelMid() || LogUtil.isDebugLevelHigh()) {
            String[] strArr = {SystemUIAnalytics.QPNE_KEY_APP, "nonApp", "wallpaper"};
            int i2 = 3;
            int i3 = 0;
            RemoteAnimationTarget[][] remoteAnimationTargetArr4 = {startKeyguardExitAnimParams.mApps, startKeyguardExitAnimParams.mNonApps, startKeyguardExitAnimParams.mWallpapers};
            int i4 = 0;
            while (i4 < i2) {
                RemoteAnimationTarget[] remoteAnimationTargetArr5 = remoteAnimationTargetArr4[i4];
                if (remoteAnimationTargetArr5 != null && remoteAnimationTargetArr5.length != 0) {
                    int length = remoteAnimationTargetArr5.length;
                    int i5 = -1;
                    int i6 = i3;
                    while (i6 < length) {
                        RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr5[i6];
                        i5++;
                        if (remoteAnimationTarget != null) {
                            ActivityManager.RunningTaskInfo runningTaskInfo = remoteAnimationTarget.taskInfo;
                            ComponentName componentName = runningTaskInfo != null ? runningTaskInfo.topActivity : null;
                            String str = strArr[i4];
                            String m = componentName != null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(componentName.getPackageName(), "/", componentName.getClassName()) : SignalSeverity.NONE;
                            boolean z = remoteAnimationTarget.leash != null ? 1 : i3;
                            StringBuilder m2 = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(i5, "exitAnimParam ", str, "[", "]=");
                            m2.append(m);
                            m2.append(", hasLeash=");
                            m2.append(z);
                            KeyguardViewMediatorHelperImpl.logD(m2.toString());
                        }
                        i6++;
                        i3 = 0;
                    }
                }
                i4++;
                i2 = 3;
                i3 = 0;
            }
        }
        Handler handler$1 = keyguardViewMediatorHelperImpl.getHandler$1();
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = keyguardViewMediatorHelperImpl.fastUnlockController;
        if (keyguardFastBioUnlockController.isFastUnlockMode()) {
            keyguardFastBioUnlockController.startKeyguardExitAnimationTime = System.nanoTime();
            handler$1.sendMessageAtFrontOfQueue(obtainMessage);
        } else if (keyguardFastBioUnlockController.isFastWakeAndUnlockMode()) {
            keyguardFastBioUnlockController.startKeyguardExitAnimationTime = System.nanoTime();
            handler$1.sendMessage(obtainMessage);
        } else if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && keyguardViewMediatorHelperImpl.foldControllerImpl.isUnlockOnFoldOpened()) {
            handler$1.sendMessageAtFrontOfQueue(obtainMessage);
        } else {
            handler$1.sendMessage(obtainMessage);
        }
        Trace.endSection();
    }

    public final void tryKeyguardDone$1() {
        SemDvfsManager semDvfsManager;
        int i;
        KeyguardUnlockInfo.UnlockTrigger unlockTrigger;
        int ordinal;
        KeyguardUnlockInfo.SkipBouncerReason skipBouncerReason;
        StringBuilder sb = new StringBuilder("tryKeyguardDone: pending - ");
        sb.append(this.mKeyguardDonePending);
        sb.append(", animRan - ");
        sb.append(this.mHideAnimationRun);
        sb.append(" animRunning - ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mHideAnimationRunning, "KeyguardViewMediator");
        boolean z = (this.mKeyguardDonePending || !this.mHideAnimationRun || this.mHideAnimationRunning) ? false : true;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        keyguardViewMediatorHelperImpl.getClass();
        if (Rune.SYSUI_BINDER_CALL_MONITOR) {
            BinderCallMonitorImpl binderCallMonitorImpl = (BinderCallMonitorImpl) keyguardViewMediatorHelperImpl.binderCallMonitor;
            binderCallMonitorImpl.getClass();
            binderCallMonitorImpl.startMonitoring(2, BinderCallMonitorConstants.MAX_DURATION / 1000000, 8000L);
        }
        if (!z) {
            if (!this.mKeyguardDonePending && this.mHideAnimationRun && !this.mHideAnimationRunning) {
                handleKeyguardDone$1();
                return;
            }
            if (this.mSurfaceBehindRemoteAnimationRunning) {
                exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
                return;
            } else {
                if (this.mHideAnimationRun) {
                    return;
                }
                android.util.Log.d("KeyguardViewMediator", "tryKeyguardDone: starting pre-hide animation");
                this.mHideAnimationRun = true;
                this.mHideAnimationRunning = true;
                ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).startPreHideAnimation(this.mHideAnimationFinishedRunnable);
                return;
            }
        }
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = keyguardViewMediatorHelperImpl.fastUnlockController;
        if ((!keyguardFastBioUnlockController.bioUnlockBoosterEnabled || keyguardFastBioUnlockController.dvfsManager == null || !keyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED)) && (semDvfsManager = keyguardViewMediatorHelperImpl.dvfsManager) != null) {
            semDvfsManager.acquire(1000);
        }
        if (KeyguardUnlockInfo.authType != KeyguardUnlockInfo.AuthType.AUTH_UNKNOWN) {
            i = (KeyguardUnlockInfo.authType.ordinal() * 10000) + 300000;
            int i2 = KeyguardUnlockInfo.WhenMappings.$EnumSwitchMapping$0[KeyguardUnlockInfo.authType.ordinal()];
            if (i2 == 1) {
                KeyguardSecurityModel.SecurityMode securityMode = KeyguardUnlockInfo.securityMode;
                if (securityMode != null) {
                    ordinal = securityMode.ordinal();
                    i += ordinal * 100;
                }
            } else if (i2 == 2) {
                BiometricSourceType biometricSourceType = KeyguardUnlockInfo.biometricSourceType;
                if (biometricSourceType != null) {
                    ordinal = biometricSourceType.ordinal();
                    i += ordinal * 100;
                }
            } else if (i2 == 3 && (skipBouncerReason = KeyguardUnlockInfo.skipBouncerReason) != null) {
                ordinal = skipBouncerReason.ordinal();
                i += ordinal * 100;
            }
        } else {
            i = 3;
        }
        if (i > 3 && (unlockTrigger = KeyguardUnlockInfo.unlockTrigger) != null) {
            i += unlockTrigger.ordinal();
        }
        KeyguardUnlockInfo.INSTANCE.getClass();
        int i3 = KeyguardUnlockInfo.WhenMappings.$EnumSwitchMapping$0[KeyguardUnlockInfo.authType.ordinal()];
        String str = i3 != 1 ? i3 != 2 ? i3 != 3 ? KeyguardUnlockInfo.authType.toString() : String.valueOf(KeyguardUnlockInfo.skipBouncerReason) : String.valueOf(KeyguardUnlockInfo.biometricSourceType) : String.valueOf(KeyguardUnlockInfo.securityMode);
        KeyguardUnlockInfo.leaveHistory(i + ": " + str + " " + KeyguardUnlockInfo.unlockTrigger, true);
        KeyguardUnlockInfo.reset();
        EventLog.writeEvent(70000, i);
        KeyguardDumpLog.state$default(KeyguardDumpLog.INSTANCE, 3, false, false, false, i, 0, 46);
        handleKeyguardDone$1();
    }

    public final void updateInputRestrictedLocked$1() {
        boolean isInputRestricted = isInputRestricted();
        if (this.mInputRestricted != isInputRestricted) {
            this.mInputRestricted = isInputRestricted;
            for (int size = this.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
                IKeyguardStateCallback iKeyguardStateCallback = (IKeyguardStateCallback) this.mKeyguardStateCallbacks.get(size);
                try {
                    iKeyguardStateCallback.onInputRestrictedStateChanged(isInputRestricted);
                } catch (RemoteException e) {
                    Slog.w("KeyguardViewMediator", "Failed to call onDeviceProvisioned", e);
                    if (e instanceof DeadObjectException) {
                        this.mKeyguardStateCallbacks.remove(iKeyguardStateCallback);
                    }
                }
            }
        }
    }

    public void userActivity() {
        Flags.FEATURE_FLAGS.getClass();
        this.mUiBgExecutor.execute(new KeyguardViewMediator$$ExternalSyntheticLambda1(this, 1));
    }

    public void verifyUnlock(IKeyguardExitCallback iKeyguardExitCallback) {
        Trace.beginSection("KeyguardViewMediator#verifyUnlock");
        synchronized (this) {
            android.util.Log.d("KeyguardViewMediator", "verifyUnlock");
            if (shouldWaitForProvisioning$1()) {
                android.util.Log.d("KeyguardViewMediator", "ignoring because device isn't provisioned");
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e) {
                    Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e);
                }
            } else if (this.mExternallyEnabled) {
                android.util.Log.w("KeyguardViewMediator", "verifyUnlock called when not externally disabled");
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e2) {
                    Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e2);
                }
            } else if (isSecure()) {
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e3) {
                    Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e3);
                }
            } else {
                this.mExternallyEnabled = true;
                this.mNeedToReshowWhenReenabled = false;
                synchronized (this) {
                    updateInputRestrictedLocked$1();
                    try {
                        iKeyguardExitCallback.onKeyguardExitResult(true);
                    } catch (RemoteException e4) {
                        Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(true)", e4);
                    }
                }
            }
        }
        Trace.endSection();
    }

    public void startKeyguardExitAnimation(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        startKeyguardExitAnimation$1(i, 0L, 0L, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
    }

    public boolean isSecure(int i) {
        return this.mUpdateMonitor.isSecure(i);
    }
}
