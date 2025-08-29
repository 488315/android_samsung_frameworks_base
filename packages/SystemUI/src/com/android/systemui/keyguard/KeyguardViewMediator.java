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
import android.content.pm.UserInfo;
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
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
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
import androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.coroutines.TrackTracer;
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
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardDisplayManager;
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
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.Rune;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.communal.ui.viewmodel.CommunalTransitionViewModel;
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
import com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionBootInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.pluginlock.PluginLockInstancePolicy;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.process.ProcessWrapper;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.settings.UserTrackerImpl$handleBeforeUserSwitching$$inlined$notifySubscribers$1;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelper;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentExt;
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
import com.samsung.android.cover.CoverState;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.sdp.internal.SdpAuthenticator;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.os.SemDvfsManager;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.text.CharsKt__CharJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes2.dex */
public class KeyguardViewMediator implements CoreStartable, StatusBarStateController.StateListener {
    public static final Intent USER_PRESENT_INTENT = new Intent("android.intent.action.USER_PRESENT").addFlags(606076928);
    public static final Bundle USER_PRESENT_INTENT_OPTIONS = BroadcastOptions.makeBasic().setDeferralPolicy(2).setDeliveryGroupPolicy(1).toBundle();
    public final Lazy mActivityTransitionAnimator;
    public AlarmManager mAlarmManager;
    public boolean mAnimatingScreenOff;
    public boolean mAodShowing;
    public final AnonymousClass8 mAppearAnimationRunner;
    public boolean mBootCompleted;
    public boolean mBootSendUserPresent;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final AnonymousClass13 mBroadcastReceiver;
    public CentralSurfacesImpl mCentralSurfaces;
    public final Lazy mCommunalTransitionViewModel;
    public final Context mContext;
    public CharSequence mCustomMessage;
    public final AnonymousClass12 mDelayedLockBroadcastReceiver;
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
    public final AnonymousClass7 mExitAnimationRunner;
    public final FalsingCollector mFalsingCollector;
    protected FoldGracePeriodProvider mFoldGracePeriodProvider;
    public boolean mGoingToSleep;
    public final AnonymousClass14 mHandler;
    public final KeyguardViewMediatorHelperImpl mHelper;
    public Animation mHideAnimation;
    public final KeyguardViewMediator$$ExternalSyntheticLambda0 mHideAnimationFinishedRunnable;
    public boolean mHiding;
    public boolean mInGestureNavigationMode;
    public boolean mInputRestricted;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final JavaAdapter mJavaAdapter;
    public final KeyguardDisplayManager mKeyguardDisplayManager;
    public ActivityTransitionAnimator.Runner mKeyguardExitAnimationRunner;
    public final AnonymousClass15 mKeyguardGoingAwayRunnable;
    public final KeyguardInteractor mKeyguardInteractor;
    public final ArrayList mKeyguardStateCallbacks;
    public final KeyguardStateController mKeyguardStateController;
    public final AnonymousClass11 mKeyguardStateControllerCallback;
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
    public final AnonymousClass9 mOccludeByDreamAnimationRunner;
    public RemoteAnimationTarget mOccludingRemoteAnimationTarget;
    public final AnonymousClass1 mOnPropertiesChangedListener;
    public final boolean mOrderUnlockAndWake;
    public final PowerManager mPM;
    public boolean mPendingLock;
    public boolean mPendingPinLock;
    public boolean mPendingReset;
    public final float mPowerButtonY;
    public boolean mPowerGestureIntercepted;
    public final ProcessWrapper mProcessWrapper;
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
    public final KeyguardTransitionBootInteractor mTransitionBootInteractor;
    public final TrustManager mTrustManager;
    public int mTrustedSoundId;
    public final Executor mUiBgExecutor;
    public final UiEventLogger mUiEventLogger;
    public int mUnlockSoundId;
    public IRemoteAnimationFinishedCallback mUnoccludeFinishedCallback;
    public KeyguardUpdateMonitorCallback mUpdateCallback;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    protected UserTracker.Callback mUserChangedCallback;
    public final UserSwitcherController mUserSwitcherController;
    public final UserTracker mUserTracker;
    public final AnonymousClass5 mViewMediatorCallback;
    public final WallpaperRepository mWallpaperRepository;
    public boolean mWallpaperSupportsAmbientMode;
    public final float mWindowCornerRadius;
    public final List mLockNowCallbacks = new ArrayList();
    public final IBinder mStatusBarDisableToken = new Binder();
    public int mGoingAwayRequestedForUserId = -1;
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
    public boolean mIsKeyguardExitAnimationCanceled = false;

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$10, reason: invalid class name */
    public class AnonymousClass10 extends IRemoteAnimationRunner.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public ValueAnimator mUnoccludeAnimator;
        public final Matrix mUnoccludeMatrix = new Matrix();

        public AnonymousClass10() {
        }

        public final void onAnimationCancelled() {
            KeyguardViewMediator.this.mContext.getMainExecutor().execute(new KeyguardViewMediator$$ExternalSyntheticLambda6(this, 1));
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
            KeyguardViewMediator.this.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$10$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    final KeyguardViewMediator.AnonymousClass10 anonymousClass10 = this.f$0;
                    boolean z2 = z;
                    RemoteAnimationTarget[] remoteAnimationTargetArr4 = remoteAnimationTargetArr2;
                    final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                    SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier2 = syncRtSurfaceTransactionApplier;
                    ValueAnimator valueAnimator = anonymousClass10.mUnoccludeAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.cancel();
                    }
                    if (!z2 && !KeyguardViewMediator.this.mShowCommunalWhenUnoccluding) {
                        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
                        anonymousClass10.mUnoccludeAnimator = valueAnimatorOfFloat;
                        valueAnimatorOfFloat.setDuration(250L);
                        anonymousClass10.mUnoccludeAnimator.setInterpolator(Interpolators.TOUCH_RESPONSE);
                        anonymousClass10.mUnoccludeAnimator.addUpdateListener(new KeyguardViewMediator$$ExternalSyntheticLambda84(anonymousClass10, syncRtSurfaceTransactionApplier2, 2));
                        anonymousClass10.mUnoccludeAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.10.1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                try {
                                    iRemoteAnimationFinishedCallback2.onAnimationFinished();
                                    AnonymousClass10 anonymousClass102 = AnonymousClass10.this;
                                    anonymousClass102.mUnoccludeAnimator = null;
                                    KeyguardViewMediator.this.mInteractionJankMonitor.end(64);
                                } catch (RemoteException e) {
                                    android.util.Log.e("KeyguardViewMediator", "Failed to finish transition", e);
                                }
                            }
                        });
                        anonymousClass10.mUnoccludeAnimator.start();
                        return;
                    }
                    KeyguardViewMediator.initAlphaForAnimationTargets(remoteAnimationTargetArr4);
                    if (z2) {
                        ((DreamViewModel) KeyguardViewMediator.this.mDreamViewModel.get()).startTransitionFromDream();
                    } else {
                        CommunalTransitionViewModel communalTransitionViewModel = (CommunalTransitionViewModel) KeyguardViewMediator.this.mCommunalTransitionViewModel.get();
                        communalTransitionViewModel.getClass();
                        CommunalSceneInteractor.snapToScene$default(communalTransitionViewModel.communalSceneInteractor, CommunalScenes.Communal, "transition view model", 0L, 12);
                    }
                    KeyguardViewMediator.this.mUnoccludeFinishedCallback = iRemoteAnimationFinishedCallback2;
                }
            });
        }
    }

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$14, reason: invalid class name */
    public class AnonymousClass14 extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;

        public AnonymousClass14(Looper looper, Handler.Callback callback, boolean z) {
            super(looper, callback, z);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:92:0x0293  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void handleMessage(Message message) {
            String str;
            String str2;
            Object[] objArr;
            KeyguardViewMediator keyguardViewMediator;
            int i = 0;
            int i2 = 0;
            int i3 = 2;
            int i4 = 1;
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediator.this.mHelper;
            Objects.requireNonNull(keyguardViewMediatorHelperImpl);
            KeyguardViewMediator$$ExternalSyntheticLambda63 keyguardViewMediator$$ExternalSyntheticLambda63 = new KeyguardViewMediator$$ExternalSyntheticLambda63(keyguardViewMediatorHelperImpl, i3);
            boolean z = Rune.SYSUI_MULTI_SIM;
            keyguardViewMediator$$ExternalSyntheticLambda63.accept(message);
            String str3 = "";
            switch (message.what) {
                case 1:
                    str = "SHOW";
                    KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                    Bundle bundle = (Bundle) message.obj;
                    keyguardViewMediator2.getClass();
                    Trace.beginSection("KeyguardViewMediator#handleShow");
                    try {
                        keyguardViewMediator2.handleShowInner(bundle);
                        android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediator.this.mHelper;
                        Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                        keyguardViewMediatorHelperImpl2.postHandleMsg(message);
                        return;
                    } finally {
                        Trace.endSection();
                    }
                case 2:
                    str = "HIDE";
                    KeyguardViewMediator.this.handleHide$1();
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl22 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl22);
                    keyguardViewMediatorHelperImpl22.postHandleMsg(message);
                    return;
                case 3:
                    str2 = "RESET";
                    KeyguardViewMediator.m2604$$Nest$mhandleReset(KeyguardViewMediator.this, message.arg1 != 0);
                    str = str2;
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl222);
                    keyguardViewMediatorHelperImpl222.postHandleMsg(message);
                    return;
                case 4:
                case 6:
                case 15:
                case 16:
                default:
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl3);
                    keyguardViewMediatorHelperImpl3.handleSecMessage(message);
                    str = str3;
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl2222);
                    keyguardViewMediatorHelperImpl2222.postHandleMsg(message);
                    return;
                case 5:
                    str = "NOTIFY_FINISHED_GOING_TO_SLEEP";
                    KeyguardViewMediator.m2602$$Nest$mhandleNotifyFinishedGoingToSleep(KeyguardViewMediator.this);
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl22222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl22222);
                    keyguardViewMediatorHelperImpl22222.postHandleMsg(message);
                    return;
                case 7:
                    str = "KEYGUARD_DONE";
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE");
                    KeyguardViewMediator.this.handleKeyguardDone$1();
                    Trace.endSection();
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl222222);
                    keyguardViewMediatorHelperImpl222222.postHandleMsg(message);
                    return;
                case 8:
                    str = "KEYGUARD_DONE_DRAWING";
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_DRAWING");
                    KeyguardViewMediator.m2601$$Nest$mhandleKeyguardDoneDrawing(KeyguardViewMediator.this);
                    Trace.endSection();
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl2222222);
                    keyguardViewMediatorHelperImpl2222222.postHandleMsg(message);
                    return;
                case 9:
                    str2 = "SET_OCCLUDED";
                    Trace.beginSection("KeyguardViewMediator#handleMessage SET_OCCLUDED");
                    KeyguardViewMediator keyguardViewMediator3 = KeyguardViewMediator.this;
                    boolean z2 = message.arg1 != 0;
                    boolean z3 = message.arg2 != 0;
                    Object obj = message.obj;
                    KeyguardViewMediator.m2605$$Nest$mhandleSetOccluded(keyguardViewMediator3, z2, z3, obj != null ? ((Integer) obj).intValue() : -1);
                    Trace.endSection();
                    str = str2;
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl22222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl22222222);
                    keyguardViewMediatorHelperImpl22222222.postHandleMsg(message);
                    return;
                case 10:
                    str = "KEYGUARD_TIMEOUT";
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator.this.doKeyguardLocked$1((Bundle) message.obj);
                    }
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl222222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl222222222);
                    keyguardViewMediatorHelperImpl222222222.postHandleMsg(message);
                    return;
                case 11:
                    str3 = "DISMISS";
                    DismissMessage dismissMessage = (DismissMessage) message.obj;
                    KeyguardViewMediator keyguardViewMediator4 = KeyguardViewMediator.this;
                    IKeyguardDismissCallback iKeyguardDismissCallback = dismissMessage.mCallback;
                    CharSequence charSequence = dismissMessage.mMessage;
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = keyguardViewMediator4.mHelper;
                    if (keyguardViewMediatorHelperImpl4.isShowing$1()) {
                        boolean z4 = CscRune.SECURITY_SIM_PERM_DISABLED;
                        KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediatorHelperImpl4.updateMonitor;
                        if (z4 && keyguardUpdateMonitor.isIccBlockedPermanently()) {
                            Log.d("KeyguardViewMediator", "dismiss failed. Permanent state.");
                            objArr = iKeyguardDismissCallback != null ? 1 : null;
                            i2 = 1;
                        } else {
                            boolean z5 = LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY;
                            DismissCallbackRegistry dismissCallbackRegistry = keyguardViewMediatorHelperImpl4.dismissCallbackRegistry;
                            if (!z5 || !keyguardViewMediatorHelperImpl4.isSecure$2() || keyguardUpdateMonitor.getUserCanSkipBouncer(((UserTrackerImpl) keyguardViewMediatorHelperImpl4.userTracker).getUserId()) || keyguardViewMediatorHelperImpl4.foldControllerImpl.isFoldOpened()) {
                                if (keyguardViewMediatorHelperImpl4.isKeyguardHiding()) {
                                    if (iKeyguardDismissCallback != null) {
                                        dismissCallbackRegistry.addCallback(iKeyguardDismissCallback);
                                    }
                                    i3 = 3;
                                } else {
                                    objArr = false;
                                }
                            } else if (iKeyguardDismissCallback != null) {
                                dismissCallbackRegistry.addCallback(iKeyguardDismissCallback);
                                keyguardViewMediatorHelperImpl4.subScreenManager.requestCoverBouncer();
                            }
                            int i5 = i3;
                            objArr = false;
                            i2 = i5;
                        }
                        if (objArr != false) {
                            new DismissCallbackWrapper(iKeyguardDismissCallback).notifyDismissError();
                        }
                        if (i2 != 0) {
                            KeyguardViewMediatorHelperImpl.logD$1("handleDismiss reason=" + i2);
                        }
                        str = str3;
                    } else {
                        if (keyguardViewMediator4.mShowing) {
                            KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                            if (iKeyguardDismissCallback != null) {
                                keyguardViewMediator4.mDismissCallbackRegistry.addCallback(iKeyguardDismissCallback);
                            }
                            keyguardViewMediator4.mCustomMessage = charSequence;
                            if (!keyguardViewMediator4.mHiding) {
                                ((KeyguardViewController) keyguardViewMediator4.mKeyguardViewControllerLazy.get()).dismissAndCollapse();
                            }
                        } else {
                            android.util.Log.w("KeyguardViewMediator", "Ignoring request to DISMISS because mShowing=false");
                            if (iKeyguardDismissCallback != null) {
                                new DismissCallbackWrapper(iKeyguardDismissCallback).notifyDismissError();
                            }
                        }
                        str = str3;
                    }
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2222222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl2222222222);
                    keyguardViewMediatorHelperImpl2222222222.postHandleMsg(message);
                    return;
                case 12:
                    str = "START_KEYGUARD_EXIT_ANIM";
                    Trace.beginSection("KeyguardViewMediator#handleMessage START_KEYGUARD_EXIT_ANIM");
                    synchronized (KeyguardViewMediator.this) {
                        keyguardViewMediator = KeyguardViewMediator.this;
                        keyguardViewMediator.mHiding = true;
                    }
                    final StartKeyguardExitAnimParams startKeyguardExitAnimParams = (StartKeyguardExitAnimParams) message.obj;
                    ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) keyguardViewMediator.mNotificationShadeWindowControllerLazy.get())).batchApplyWindowLayoutParams(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$14$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardViewMediator.AnonymousClass14 anonymousClass14 = this.f$0;
                            KeyguardViewMediator.StartKeyguardExitAnimParams startKeyguardExitAnimParams2 = startKeyguardExitAnimParams;
                            int i6 = KeyguardViewMediator.AnonymousClass14.$r8$clinit;
                            long j = startKeyguardExitAnimParams2.startTime;
                            long j2 = startKeyguardExitAnimParams2.fadeoutDuration;
                            RemoteAnimationTarget[] remoteAnimationTargetArr = startKeyguardExitAnimParams2.mApps;
                            RemoteAnimationTarget[] remoteAnimationTargetArr2 = startKeyguardExitAnimParams2.mWallpapers;
                            RemoteAnimationTarget[] remoteAnimationTargetArr3 = startKeyguardExitAnimParams2.mNonApps;
                            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = startKeyguardExitAnimParams2.mFinishedCallback;
                            Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                            KeyguardViewMediator keyguardViewMediator5 = KeyguardViewMediator.this;
                            keyguardViewMediator5.getClass();
                            Trace.beginSection("KeyguardViewMediator#handleStartKeyguardExitAnimation");
                            try {
                                keyguardViewMediator5.handleStartKeyguardExitAnimationInner(j, j2, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
                                Trace.endSection();
                                keyguardViewMediator5.mFalsingCollector.onSuccessfulUnlock();
                            } catch (Throwable th) {
                                Trace.endSection();
                                throw th;
                            }
                        }
                    });
                    Trace.endSection();
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl22222222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl22222222222);
                    keyguardViewMediatorHelperImpl22222222222.postHandleMsg(message);
                    return;
                case 13:
                    str = "KEYGUARD_DONE_PENDING_TIMEOUT";
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_PENDING_TIMEOUT");
                    android.util.Log.w("KeyguardViewMediator", "Timeout while waiting for activity drawn!");
                    Trace.endSection();
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl222222222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl222222222222);
                    keyguardViewMediatorHelperImpl222222222222.postHandleMsg(message);
                    return;
                case 14:
                    str = "NOTIFY_STARTED_WAKING_UP";
                    Trace.beginSection("KeyguardViewMediator#handleMessage NOTIFY_STARTED_WAKING_UP");
                    KeyguardViewMediator.m2603$$Nest$mhandleNotifyStartedWakingUp(KeyguardViewMediator.this);
                    Trace.endSection();
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2222222222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl2222222222222);
                    keyguardViewMediatorHelperImpl2222222222222.postHandleMsg(message);
                    return;
                case 17:
                    str = "NOTIFY_STARTED_GOING_TO_SLEEP";
                    KeyguardViewMediator keyguardViewMediator5 = KeyguardViewMediator.this;
                    synchronized (keyguardViewMediator5) {
                        android.util.Log.d("KeyguardViewMediator", "handleNotifyStartedGoingToSleep");
                        ((KeyguardViewController) keyguardViewMediator5.mKeyguardViewControllerLazy.get()).onStartedGoingToSleep();
                    }
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl22222222222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl22222222222222);
                    keyguardViewMediatorHelperImpl22222222222222.postHandleMsg(message);
                    return;
                case 18:
                    str2 = "SYSTEM_READY";
                    KeyguardViewMediator keyguardViewMediator6 = KeyguardViewMediator.this;
                    synchronized (keyguardViewMediator6) {
                        try {
                            android.util.Log.d("KeyguardViewMediator", "onSystemReady");
                            keyguardViewMediator6.mSystemReady = true;
                            keyguardViewMediator6.doKeyguardLocked$1(null);
                            keyguardViewMediator6.mUpdateMonitor.registerCallback(keyguardViewMediator6.mUpdateCallback);
                            keyguardViewMediator6.adjustStatusBarLocked$1(false, false);
                            keyguardViewMediator6.mDreamOverlayStateController.addCallback(keyguardViewMediator6.mDreamOverlayStateCallback);
                            keyguardViewMediator6.mHandler.obtainMessage(20).sendToTarget();
                            DreamViewModel dreamViewModel = (DreamViewModel) keyguardViewMediator6.mDreamViewModel.get();
                            CommunalTransitionViewModel communalTransitionViewModel = (CommunalTransitionViewModel) keyguardViewMediator6.mCommunalTransitionViewModel.get();
                            keyguardViewMediator6.mJavaAdapter.alwaysCollectFlow(dreamViewModel.dreamAlpha, new KeyguardViewMediator$$ExternalSyntheticLambda69(keyguardViewMediator6, 4));
                            keyguardViewMediator6.mJavaAdapter.alwaysCollectFlow(dreamViewModel.transitionEnded, new KeyguardViewMediator$$ExternalSyntheticLambda69(keyguardViewMediator6, i4));
                            keyguardViewMediator6.mJavaAdapter.alwaysCollectFlow(communalTransitionViewModel.showCommunalFromOccluded, new KeyguardViewMediator$$ExternalSyntheticLambda69(keyguardViewMediator6, z ? 1 : 0));
                            keyguardViewMediator6.mJavaAdapter.alwaysCollectFlow(communalTransitionViewModel.transitionFromOccludedEnded, new KeyguardViewMediator$$ExternalSyntheticLambda69(keyguardViewMediator6, i4));
                            UserTracker userTracker = keyguardViewMediator6.mUserTracker;
                            if (((UserTrackerImpl) userTracker).isUserSwitching) {
                                keyguardViewMediator6.mUserChangedCallback.onUserChanging(((UserTrackerImpl) userTracker).getUserId(), keyguardViewMediator6.mContext, new KeyguardViewMediator$$ExternalSyntheticLambda70());
                            }
                        } finally {
                        }
                    }
                    keyguardViewMediator6.maybeSendUserPresentBroadcast$1();
                    str = str2;
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl222222222222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl222222222222222);
                    keyguardViewMediatorHelperImpl222222222222222.postHandleMsg(message);
                    return;
                case 19:
                    str3 = "CANCEL_KEYGUARD_EXIT_ANIM";
                    Trace.beginSection("KeyguardViewMediator#handleMessage CANCEL_KEYGUARD_EXIT_ANIM");
                    KeyguardViewMediator keyguardViewMediator7 = KeyguardViewMediator.this;
                    keyguardViewMediator7.getClass();
                    KeyguardWmStateRefactor keyguardWmStateRefactor2 = KeyguardWmStateRefactor.INSTANCE;
                    int i6 = keyguardViewMediator7.mGoingAwayRequestedForUserId;
                    SelectedUserInteractor selectedUserInteractor = keyguardViewMediator7.mSelectedUserInteractor;
                    if (i6 != selectedUserInteractor.getSelectedUserId()) {
                        android.util.Log.e("KeyguardViewMediator", "Setting pendingLock = true due to userId mismatch. Requested: " + keyguardViewMediator7.mGoingAwayRequestedForUserId + ", current: " + selectedUserInteractor.getSelectedUserId());
                        keyguardViewMediator7.setPendingLock(true);
                    }
                    if (keyguardViewMediator7.mPendingLock) {
                        android.util.Log.d("KeyguardViewMediator", "#handleCancelKeyguardExitAnimation: keyguard exit animation cancelled. There's a pending lock, so we were cancelled because the device was locked again during the unlock sequence. We should end up locked.");
                        keyguardViewMediator7.mIsKeyguardExitAnimationCanceled = true;
                        keyguardViewMediator7.finishSurfaceBehindRemoteAnimation(true);
                        keyguardViewMediator7.maybeHandlePendingLock();
                    } else {
                        android.util.Log.d("KeyguardViewMediator", "#handleCancelKeyguardExitAnimation: keyguard exit animation cancelled. No pending lock, we should end up unlocked with the app/launcher visible.");
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = keyguardViewMediator7.mHelper;
                        Objects.requireNonNull(keyguardViewMediatorHelperImpl5);
                        if (keyguardViewMediatorHelperImpl5.isUnlockStartedOrFinished()) {
                            i = 1;
                        } else if (!((KeyguardViewMediator) keyguardViewMediatorHelperImpl5.viewMediatorLazy.get()).isHiding()) {
                            ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl5.viewMediatorProvider;
                            (viewMediatorProvider != null ? viewMediatorProvider : null).setShowingLocked.invoke(Boolean.valueOf(keyguardViewMediatorHelperImpl5.isShowing$1()), Boolean.TRUE, "setForceShowingLocked");
                            keyguardViewMediatorHelperImpl5.onAbortHandleStartKeyguardExitAnimation();
                            i = 2;
                        }
                        if (i != 0) {
                            KeyguardViewMediatorHelperImpl.logD$1("cancel handleCancelKeyguardExitAnimation why=" + i);
                        } else {
                            keyguardViewMediator7.showSurfaceBehindKeyguard();
                            keyguardViewMediator7.exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
                        }
                    }
                    Trace.endSection();
                    str = str3;
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2222222222222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl2222222222222222);
                    keyguardViewMediatorHelperImpl2222222222222222.postHandleMsg(message);
                    return;
                case 20:
                    str3 = "BOOT_INTERACTOR";
                    KeyguardViewMediator.this.mTransitionBootInteractor.start();
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl32 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl32);
                    keyguardViewMediatorHelperImpl32.handleSecMessage(message);
                    str = str3;
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl22222222222222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl22222222222222222);
                    keyguardViewMediatorHelperImpl22222222222222222.postHandleMsg(message);
                    return;
                case 21:
                    str = "BEFORE_USER_SWITCHING";
                    KeyguardViewMediator.this.handleBeforeUserSwitching(message.arg1, (Runnable) message.obj);
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl222222222222222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl222222222222222222);
                    keyguardViewMediatorHelperImpl222222222222222222.postHandleMsg(message);
                    return;
                case 22:
                    str = "USER_SWITCHING";
                    KeyguardViewMediator.this.handleUserSwitching(message.arg1, (Runnable) message.obj);
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2222222222222222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl2222222222222222222);
                    keyguardViewMediatorHelperImpl2222222222222222222.postHandleMsg(message);
                    return;
                case 23:
                    str = "USER_SWITCH_COMPLETE";
                    KeyguardViewMediator.this.handleUserSwitchComplete(message.arg1);
                    android.util.Log.d("KeyguardViewMediator", "KeyguardViewMediator queue processing message: ".concat(str));
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl22222222222222222222 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl22222222222222222222);
                    keyguardViewMediatorHelperImpl22222222222222222222.postHandleMsg(message);
                    return;
            }
        }
    }

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$15, reason: invalid class name */
    public class AnonymousClass15 implements Runnable {
        public AnonymousClass15() {
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x0051  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0036  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void run() {
            int i;
            int i2 = 1;
            Trace.beginSection("KeyguardViewMediator.mKeyGuardGoingAwayRunnable");
            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).keyguardGoingAway();
            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getClass();
            if (!KeyguardViewMediator.this.mHelper.isEnabledBiometricUnlockVI()) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                i = (!keyguardViewMediator.mWakeAndUnlocking || keyguardViewMediator.mWallpaperSupportsAmbientMode) ? 0 : 2;
            }
            if (!((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).isGoingToNotificationShade()) {
                KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                if (keyguardViewMediator2.mWakeAndUnlocking && keyguardViewMediator2.mWallpaperSupportsAmbientMode) {
                    i |= 1;
                }
            }
            if (((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).isUnlockWithWallpaper()) {
                i |= 4;
            }
            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).shouldSubtleWindowAnimationsForUnlock();
            KeyguardViewMediator keyguardViewMediator3 = KeyguardViewMediator.this;
            if (keyguardViewMediator3.mWakeAndUnlocking) {
                ((KeyguardUnlockAnimationController) keyguardViewMediator3.mKeyguardUnlockAnimationControllerLazy.get()).getClass();
            }
            KeyguardViewMediator.this.mUpdateMonitor.setKeyguardGoingAway(true);
            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(true);
            KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
            KeyguardViewMediator keyguardViewMediator4 = KeyguardViewMediator.this;
            keyguardViewMediator4.mGoingAwayRequestedForUserId = keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId();
            RecyclerView$$ExternalSyntheticOutline0.m(KeyguardViewMediator.this.mGoingAwayRequestedForUserId, "KeyguardViewMediator", new StringBuilder("keyguardGoingAway requested for userId: "));
            KeyguardViewMediator.this.mUiBgExecutor.execute(new KeyguardViewMediator$$ExternalSyntheticLambda65(this, i, i2));
            Trace.endSection();
        }
    }

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$18, reason: invalid class name */
    public class AnonymousClass18 extends IRemoteAnimationRunner.Stub {
        public final /* synthetic */ IRemoteAnimationRunner val$wrapped;

        public AnonymousClass18(IRemoteAnimationRunner iRemoteAnimationRunner) {
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

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$5, reason: invalid class name */
    public class AnonymousClass5 implements ViewMediatorCallback {
        public AnonymousClass5() {
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
            if (!keyguardViewMediator.mUpdateMonitor.is2StepVerification()) {
                int selectedUserId = keyguardViewMediator.mSelectedUserInteractor.getSelectedUserId();
                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediator.mUpdateMonitor;
                keyguardUpdateMonitor.getClass();
                Assert.isMainThread();
                boolean z = keyguardUpdateMonitor.mUserTrustIsUsuallyManaged.get(selectedUserId);
                boolean z2 = z || (keyguardUpdateMonitor.isUnlockWithFacePossible(selectedUserId) || keyguardUpdateMonitor.isUnlockWithFingerprintPossible(selectedUserId));
                KeyguardUpdateMonitor.StrongAuthTracker strongAuthTracker = keyguardUpdateMonitor.mStrongAuthTracker;
                int strongAuthForUser = strongAuthTracker.getStrongAuthForUser(selectedUserId);
                boolean zIsNonStrongBiometricAllowedAfterIdleTimeout = strongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(selectedUserId);
                if (z2 && !strongAuthTracker.hasUserAuthenticatedSinceBoot()) {
                    keyguardViewMediator.mSystemPropertiesHelper.getClass();
                    return Objects.equals(SystemProperties.get("sys.boot.reason.last"), "reboot,mainline_update") ? 16 : 1;
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
                if (z2 && (strongAuthForUser & 128) != 0) {
                    return 7;
                }
                if (z2 && !zIsNonStrongBiometricAllowedAfterIdleTimeout) {
                    return 17;
                }
            }
            return 0;
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
            if (i != keyguardViewMediator.mSelectedUserInteractor.getSelectedUserId()) {
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
            if (i != keyguardViewMediator.mSelectedUserInteractor.getSelectedUserId()) {
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

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$9, reason: invalid class name */
    public class AnonymousClass9 extends IRemoteAnimationRunner.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public ValueAnimator mOccludeByDreamAnimator;

        public AnonymousClass9() {
        }

        public final void onAnimationCancelled() {
            KeyguardViewMediator.this.mContext.getMainExecutor().execute(new KeyguardViewMediator$$ExternalSyntheticLambda6(this, 2));
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
                    KeyguardViewMediator.this.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$9$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            final KeyguardViewMediator.AnonymousClass9 anonymousClass9 = this.f$0;
                            RemoteAnimationTarget remoteAnimationTarget2 = remoteAnimationTarget;
                            SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier2 = syncRtSurfaceTransactionApplier;
                            final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                            ValueAnimator valueAnimator = anonymousClass9.mOccludeByDreamAnimator;
                            if (valueAnimator != null) {
                                valueAnimator.cancel();
                            }
                            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                            anonymousClass9.mOccludeByDreamAnimator = valueAnimatorOfFloat;
                            valueAnimatorOfFloat.setDuration(KeyguardViewMediator.this.mDreamOpenAnimationDuration);
                            anonymousClass9.mOccludeByDreamAnimator.addUpdateListener(new KeyguardViewMediator$$ExternalSyntheticLambda84(remoteAnimationTarget2, syncRtSurfaceTransactionApplier2, 1));
                            anonymousClass9.mOccludeByDreamAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.9.1
                                public boolean mIsCancelled = false;

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationCancel(Animator animator) {
                                    this.mIsCancelled = true;
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    try {
                                        if (!this.mIsCancelled) {
                                            KeyguardViewMediator.m2605$$Nest$mhandleSetOccluded(KeyguardViewMediator.this, true, false, -1);
                                        }
                                        iRemoteAnimationFinishedCallback2.onAnimationFinished();
                                        AnonymousClass9.this.mOccludeByDreamAnimator = null;
                                    } catch (RemoteException e) {
                                        android.util.Log.e("KeyguardViewMediator", "Failed to finish transition", e);
                                    }
                                }
                            });
                            anonymousClass9.mOccludeByDreamAnimator.start();
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
            ActivityTransitionAnimator.Runner runnerCreateEphemeralRunner = ((ActivityTransitionAnimator) KeyguardViewMediator.this.mActivityTransitionAnimator.get()).createEphemeralRunner(this.mActivityLaunchController);
            this.mRunner = runnerCreateEphemeralRunner;
            runnerCreateEphemeralRunner.onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
        }
    }

    public class DismissMessage {
        public final IKeyguardDismissCallback mCallback;
        public final CharSequence mMessage;

        public DismissMessage(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
            this.mCallback = iKeyguardDismissCallback;
            this.mMessage = charSequence;
        }
    }

    public class LockNowCallback {
        public final IRemoteCallback mRemoteCallback;
        public final int mUserId;

        public LockNowCallback(KeyguardViewMediator keyguardViewMediator, int i, IRemoteCallback iRemoteCallback) {
            this.mUserId = i;
            this.mRemoteCallback = iRemoteCallback;
        }
    }

    public class OccludeActivityLaunchRemoteAnimationRunner extends ActivityLaunchRemoteAnimationRunner {
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

    public class StartKeyguardExitAnimParams {
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
    public static void m2601$$Nest$mhandleKeyguardDoneDrawing(KeyguardViewMediator keyguardViewMediator) {
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
    public static void m2602$$Nest$mhandleNotifyFinishedGoingToSleep(KeyguardViewMediator keyguardViewMediator) {
        synchronized (keyguardViewMediator) {
            android.util.Log.d("KeyguardViewMediator", "handleNotifyFinishedGoingToSleep");
            ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).onFinishedGoingToSleep();
        }
    }

    /* renamed from: -$$Nest$mhandleNotifyStartedWakingUp, reason: not valid java name */
    public static void m2603$$Nest$mhandleNotifyStartedWakingUp(KeyguardViewMediator keyguardViewMediator) {
        keyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleMotifyStartedWakingUp");
        synchronized (keyguardViewMediator) {
            android.util.Log.d("KeyguardViewMediator", "handleNotifyWakingUp");
            ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).onStartedWakingUp();
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mhandleReset, reason: not valid java name */
    public static void m2604$$Nest$mhandleReset(KeyguardViewMediator keyguardViewMediator, boolean z) {
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x0045  */
    /* renamed from: -$$Nest$mhandleSetOccluded, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m2605$$Nest$mhandleSetOccluded(KeyguardViewMediator keyguardViewMediator, boolean z, boolean z2, int i) {
        boolean z3;
        keyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleSetOccluded");
        Log.d("KeyguardViewMediator", "handleSetOccluded(%b) seq=%d", Boolean.valueOf(z), Integer.valueOf(i));
        EventLog.writeEvent(36080, Integer.valueOf(z ? 1 : 0), Integer.valueOf(z2 ? 1 : 0));
        keyguardViewMediator.mInteractionJankMonitor.cancel(23);
        synchronized (keyguardViewMediator) {
            boolean z4 = true;
            if (z) {
                try {
                    z3 = keyguardViewMediator.mUpdateMonitor.mSecureCameraLaunched;
                } catch (Throwable th) {
                    throw th;
                }
            }
            keyguardViewMediator.mPowerGestureIntercepted = z3;
            if (keyguardViewMediator.mOccluded != z) {
                keyguardViewMediator.mOccluded = z;
                KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                KeyguardViewController keyguardViewController = (KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get();
                if (!z2 || !keyguardViewMediator.mDeviceInteractive) {
                    z4 = false;
                }
                keyguardViewController.setOccluded(z, z4);
                keyguardViewMediator.adjustStatusBarLocked$1(false, false);
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediator.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                BiConsumer biConsumer = new BiConsumer() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda68
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = keyguardViewMediatorHelperImpl;
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
                        keyguardViewMediatorHelperImpl2.getClass();
                        if (!zBooleanValue && zBooleanValue2) {
                            KeyguardUnlockInfo.reset();
                        }
                        KeyguardDumpLog.state$default(KeyguardDumpLog.INSTANCE, 4, zBooleanValue, false, false, 0, 0, 60);
                        ((KeyguardViewMediator) keyguardViewMediatorHelperImpl2.viewMediatorLazy.get()).userActivity();
                        if (zBooleanValue2) {
                            boolean z5 = LsRune.KEYGUARD_SUB_DISPLAY_COVER;
                            KeyguardDisplayManager keyguardDisplayManager = keyguardViewMediatorHelperImpl2.keyguardDisplayManager;
                            if ((z5 && !keyguardViewMediatorHelperImpl2.foldControllerImpl.isFoldOpened()) || keyguardDisplayManager.isDesktopMode() || !zBooleanValue) {
                                keyguardDisplayManager.show();
                                return;
                            }
                            KeyguardVisibilityMonitor keyguardVisibilityMonitor = keyguardDisplayManager.mKeyguardVisibilityMonitor;
                            if (keyguardVisibilityMonitor.isVisible()) {
                                keyguardVisibilityMonitor.addVisibilityChangedListener(keyguardDisplayManager.mVisibilityListener);
                            } else {
                                keyguardDisplayManager.hide();
                            }
                        }
                    }
                };
                boolean z5 = keyguardViewMediator.mShowing;
                boolean z6 = Rune.SYSUI_MULTI_SIM;
                biConsumer.accept(Boolean.valueOf(z), Boolean.valueOf(z5));
            }
            android.util.Log.d("KeyguardViewMediator", "isOccluded=" + z + ",mPowerGestureIntercepted=" + keyguardViewMediator.mPowerGestureIntercepted);
        }
        Trace.endSection();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v8, types: [com.android.systemui.keyguard.KeyguardViewMediator$11, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.keyguard.KeyguardViewMediator$7] */
    /* JADX WARN: Type inference failed for: r11v1, types: [com.android.systemui.keyguard.KeyguardViewMediator$8] */
    /* JADX WARN: Type inference failed for: r11v3, types: [com.android.systemui.keyguard.KeyguardViewMediator$12] */
    /* JADX WARN: Type inference failed for: r11v4, types: [com.android.systemui.keyguard.KeyguardViewMediator$13] */
    /* JADX WARN: Type inference failed for: r9v0, types: [android.provider.DeviceConfig$OnPropertiesChangedListener, com.android.systemui.keyguard.KeyguardViewMediator$1] */
    public KeyguardViewMediator(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, Context context, UiEventLogger uiEventLogger, SessionTracker sessionTracker, UserTracker userTracker, FalsingCollector falsingCollector, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, Lazy lazy, DismissCallbackRegistry dismissCallbackRegistry, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, Executor executor, PowerManager powerManager, TrustManager trustManager, UserSwitcherController userSwitcherController, DeviceConfigProxy deviceConfigProxy, NavigationModeController navigationModeController, KeyguardDisplayManager keyguardDisplayManager, DozeParameters dozeParameters, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController, Lazy lazy2, ScreenOffAnimationController screenOffAnimationController, Lazy lazy3, ScreenOnCoordinator screenOnCoordinator, KeyguardTransitions keyguardTransitions, InteractionJankMonitor interactionJankMonitor, DreamOverlayStateController dreamOverlayStateController, JavaAdapter javaAdapter, WallpaperRepository wallpaperRepository, Lazy lazy4, Lazy lazy5, Lazy lazy6, Lazy lazy7, IActivityTaskManager iActivityTaskManager, IStatusBarService iStatusBarService, FeatureFlags featureFlags, SecureSettings secureSettings, SystemSettings systemSettings, SystemClock systemClock, ProcessWrapper processWrapper, CoroutineDispatcher coroutineDispatcher, Lazy lazy8, Lazy lazy9, SystemPropertiesHelper systemPropertiesHelper, Lazy lazy10, SelectedUserInteractor selectedUserInteractor, KeyguardInteractor keyguardInteractor, KeyguardTransitionBootInteractor keyguardTransitionBootInteractor, Lazy lazy11, Lazy lazy12, WindowManagerOcclusionManager windowManagerOcclusionManager) {
        final ArrayList arrayList = new ArrayList();
        this.mKeyguardStateCallbacks = arrayList;
        this.mPendingPinLock = false;
        this.mPowerGestureIntercepted = false;
        this.mSurfaceBehindRemoteAnimationRequested = false;
        ?? r9 = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("nav_bar_handle_show_over_lockscreen")) {
                    KeyguardViewMediator.this.mShowHomeOverLockscreen = properties.getBoolean("nav_bar_handle_show_over_lockscreen", true);
                }
            }
        };
        this.mOnPropertiesChangedListener = r9;
        this.mDreamOverlayStateCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.2
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onStateChanged() {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                keyguardViewMediator.mDreamOverlayShowing = keyguardViewMediator.mDreamOverlayStateController.containsState(1);
            }
        };
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.3
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onBeforeUserSwitching(int i, UserTrackerImpl$handleBeforeUserSwitching$$inlined$notifySubscribers$1.AnonymousClass1 anonymousClass1) {
                AnonymousClass14 anonymousClass14 = KeyguardViewMediator.this.mHandler;
                anonymousClass14.sendMessage(anonymousClass14.obtainMessage(21, i, 0, anonymousClass1));
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                AnonymousClass14 anonymousClass14 = KeyguardViewMediator.this.mHandler;
                anonymousClass14.sendMessage(anonymousClass14.obtainMessage(23, i, 0));
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanging(int i, Context context2, Runnable runnable) {
                AnonymousClass14 anonymousClass14 = KeyguardViewMediator.this.mHandler;
                anonymousClass14.sendMessage(anonymousClass14.obtainMessage(22, i, 0, runnable));
            }
        };
        this.mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.4
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
                                        keyguardViewMediatorHelperImpl2.doKeyguardLocked$2(null);
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
                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "onSimStateChanged(subId=", ", slotId=", ",state=");
                sbM.append(TelephonyManager.simStateToString(i3));
                sbM.append(")");
                android.util.Log.d("KeyguardViewMediator", sbM.toString());
                int size = KeyguardViewMediator.this.mKeyguardStateCallbacks.size();
                boolean zIsSimPinSecure = KeyguardViewMediator.this.mUpdateMonitor.isSimPinSecure();
                for (int i4 = size - 1; i4 >= 0; i4--) {
                    try {
                        ((IKeyguardStateCallback) KeyguardViewMediator.this.mKeyguardStateCallbacks.get(i4)).onSimSecureStateChanged(zIsSimPinSecure);
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
                            z = i5 == 2 || i5 == 3 || (LsRune.SECURITY_SIM_PERSO_LOCK && i5 == 12);
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
                                if (keyguardViewMediator.mSurfaceBehindRemoteAnimationRunning || ((KeyguardStateControllerImpl) keyguardViewMediator.mKeyguardStateController).mKeyguardGoingAway) {
                                    Log.d("KeyguardViewMediator", "PendingPinLock : set true");
                                    KeyguardViewMediator.this.mPendingPinLock = true;
                                }
                                KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                                if (keyguardViewMediator2.mShowing) {
                                    keyguardViewMediator2.resetStateLocked$1(true);
                                } else {
                                    android.util.Log.d("KeyguardViewMediator", "INTENT_VALUE_ICC_LOCKED and keygaurd isn't showing; need to show keyguard so user can enter sim pin");
                                    KeyguardViewMediator.this.doKeyguardLocked$1(null);
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
                                        KeyguardViewMediator.this.doKeyguardLocked$1(null);
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
                                            KeyguardViewMediator.this.doKeyguardLocked$1(null);
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
                if (!zIsSimPinSecure) {
                    android.util.Log.i("KeyguardViewMediator", "PendingPinlock : set false");
                    KeyguardViewMediator.this.mPendingPinLock = false;
                }
                synchronized (KeyguardViewMediator.this) {
                    try {
                        if (KeyguardViewMediator.this.shouldWaitForProvisioning$1()) {
                            KeyguardViewMediator keyguardViewMediator4 = KeyguardViewMediator.this;
                            if (keyguardViewMediator4.mShowing) {
                                keyguardViewMediator4.tryKeyguardDone$1();
                            } else {
                                android.util.Log.d("KeyguardViewMediator", "ICC_ABSENT isn't showing, we need to show the keyguard since the device isn't provisioned yet.");
                                KeyguardViewMediator.this.doKeyguardLocked$1(null);
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
                    keyguardViewMediator.doKeyguardLocked$1(null);
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
            public final void onUserUnlocked() {
                KeyguardViewMediator.this.mHelper.aodAmbientWallpaperHelper.updateWonderLandWallpaperState();
            }
        };
        this.mViewMediatorCallback = new AnonymousClass5();
        ActivityTransitionAnimator.Controller controller = new ActivityTransitionAnimator.Controller() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.6
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
                return true;
            }

            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
            public final void onTransitionAnimationCancelled() {
                StringBuilder sb = new StringBuilder("Occlude launch animation cancelled. Occluded state is now: ");
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, keyguardViewMediator.mOccluded, "KeyguardViewMediator");
                keyguardViewMediator.mOccludeAnimationPlaying = false;
                keyguardViewMediator.mCentralSurfaces.updateIsKeyguard(false);
                ((ScrimController) keyguardViewMediator.mScrimControllerLazy.get()).setOccludeAnimationPlaying(false);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationEnd(boolean z) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                if (z) {
                    ((ShadeController) keyguardViewMediator.mShadeController.get()).instantCollapseShade();
                }
                keyguardViewMediator.mOccludeAnimationPlaying = false;
                keyguardViewMediator.mCentralSurfaces.updateIsKeyguard(false);
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
        this.mExitAnimationRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.7
            public final void onAnimationCancelled() {
                KeyguardViewMediator.this.cancelKeyguardExitAnimation();
                KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
            }

            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                Trace.beginSection("mExitAnimationRunner.onAnimationStart#startKeyguardExitAnimation");
                KeyguardViewMediator.this.startKeyguardExitAnimation(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
                KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                Trace.endSection();
            }
        };
        this.mAppearAnimationRunner = new IRemoteAnimationRunner.Stub(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator.8
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
        this.mOccludeByDreamAnimationRunner = new AnonymousClass9();
        new AnonymousClass10();
        this.mFoldGracePeriodProvider = new FoldGracePeriodProvider();
        ?? r10 = new KeyguardStateController.Callback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.11
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onPrimaryBouncerShowingChanged() {
                synchronized (KeyguardViewMediator.this) {
                    try {
                        KeyguardStateController keyguardStateController2 = KeyguardViewMediator.this.mKeyguardStateController;
                        if (((KeyguardStateControllerImpl) keyguardStateController2).mPrimaryBouncerShowing && !((KeyguardStateControllerImpl) keyguardStateController2).mKeyguardGoingAway) {
                            android.util.Log.i("KeyguardViewMediator", "primarybouncershowingchanged : Pendingpinlock : set false");
                            KeyguardViewMediator.this.mPendingPinLock = false;
                        }
                        KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                        keyguardViewMediator.adjustStatusBarLocked$1(((KeyguardStateControllerImpl) keyguardViewMediator.mKeyguardStateController).mPrimaryBouncerShowing, false);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mKeyguardStateControllerCallback = r10;
        this.mShowCommunalWhenUnoccluding = false;
        this.mDelayedLockBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.12
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (!"com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD".equals(intent.getAction())) {
                    if ("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK".equals(intent.getAction())) {
                        int intExtra = intent.getIntExtra("seq", 0);
                        int intExtra2 = intent.getIntExtra("android.intent.extra.USER_ID", 0);
                        if (intExtra2 != 0) {
                            synchronized (KeyguardViewMediator.this) {
                                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                                if (keyguardViewMediator.mDelayedProfileShowingSequence == intExtra) {
                                    keyguardViewMediator.mTrustManager.setDeviceLockedForUser(intExtra2, true);
                                }
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                int intExtra3 = intent.getIntExtra("seq", 0);
                RecyclerView$$ExternalSyntheticOutline0.m(KeyguardViewMediator.this.mDelayedShowingSequence, "KeyguardViewMediator", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(intExtra3, "received DELAYED_KEYGUARD_ACTION with seq = ", ", mDelayedShowingSequence = "));
                synchronized (KeyguardViewMediator.this) {
                    boolean z = LsRune.COVER_SUPPORTED;
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                    Rune.runIf(new KeyguardViewMediator$$ExternalSyntheticLambda64(keyguardViewMediatorHelperImpl2, 1), z);
                    KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                    if (keyguardViewMediator2.mDelayedShowingSequence == intExtra3) {
                        keyguardViewMediator2.doKeyguardLocked$1(null);
                    }
                }
            }
        };
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.13
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator.this.mShuttingDown = true;
                    }
                }
            }
        };
        final AnonymousClass14 anonymousClass14 = new AnonymousClass14(Looper.myLooper(), null, true);
        this.mHandler = anonymousClass14;
        this.mKeyguardGoingAwayRunnable = new AnonymousClass15();
        this.mHideAnimationFinishedRunnable = new KeyguardViewMediator$$ExternalSyntheticLambda0(this, 1);
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
        final int i2 = 6;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
                Object obj = this;
                switch (i16) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i17 = 1;
        Function0 function017 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i17) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i18 = 2;
        Function0 function018 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i18) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i19 = 3;
        Function0 function019 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i19) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i20 = 4;
        Function0 function020 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i20) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i21 = 5;
        Function0 function021 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i21) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i22 = 7;
        Function0 function022 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i22) {
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
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        return 2;
                    case 7:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
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
        final int i23 = 6;
        Function0 function023 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i23) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i24 = 7;
        Function0 function024 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i24) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i25 = 8;
        Function0 function025 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i25) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i26 = 9;
        Function0 function026 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i26) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i27 = 10;
        Function0 function027 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i27) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i28 = 11;
        Function0 function028 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i28) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i29 = 12;
        Function0 function029 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i29) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i30 = 13;
        Function0 function030 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i30) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i31 = 14;
        Function0 function031 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i31) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i32 = 15;
        Function0 function032 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i32) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i33 = 16;
        Function0 function033 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i33) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i34 = 17;
        Function0 function034 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i34) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i35 = 18;
        Function0 function035 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i35) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i36 = 19;
        Function0 function036 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i36) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i37 = 20;
        Function0 function037 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i37) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i38 = 21;
        Function0 function038 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i38) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i39 = 22;
        Function0 function039 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i39) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        Function3 function3 = new Function3() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda43
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                keyguardViewMediator.setShowingLocked((String) obj3, ((Boolean) obj).booleanValue(), ((Boolean) obj2).booleanValue());
                return Unit.INSTANCE;
            }
        };
        final int i40 = 0;
        Function1 function1 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda45
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                switch (i40) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.doKeyguardLocked$1((Bundle) obj);
                        break;
                    case 1:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        int iIntValue = ((Integer) obj).intValue();
                        if (iIntValue != 0) {
                            keyguardViewMediator.mHelper.playSound$2(iIntValue);
                        }
                        break;
                    case 2:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        int iIntValue2 = ((Integer) obj).intValue();
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        break;
                    case 4:
                        String str = (String) obj;
                        if (str != null) {
                            keyguardViewMediator.mPhoneState = str;
                        }
                        break;
                    case 5:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i41 = 1;
        Function1 function12 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda45
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                switch (i41) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.doKeyguardLocked$1((Bundle) obj);
                        break;
                    case 1:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        int iIntValue = ((Integer) obj).intValue();
                        if (iIntValue != 0) {
                            keyguardViewMediator.mHelper.playSound$2(iIntValue);
                        }
                        break;
                    case 2:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        int iIntValue2 = ((Integer) obj).intValue();
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        break;
                    case 4:
                        String str = (String) obj;
                        if (str != null) {
                            keyguardViewMediator.mPhoneState = str;
                        }
                        break;
                    case 5:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i42 = 23;
        Function0 function040 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i42) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i43 = 24;
        Function0 function041 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i43) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i44 = 2;
        Function1 function13 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda45
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                switch (i44) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.doKeyguardLocked$1((Bundle) obj);
                        break;
                    case 1:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        int iIntValue = ((Integer) obj).intValue();
                        if (iIntValue != 0) {
                            keyguardViewMediator.mHelper.playSound$2(iIntValue);
                        }
                        break;
                    case 2:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        int iIntValue2 = ((Integer) obj).intValue();
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        break;
                    case 4:
                        String str = (String) obj;
                        if (str != null) {
                            keyguardViewMediator.mPhoneState = str;
                        }
                        break;
                    case 5:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i45 = 25;
        Function0 function042 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i45) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i46 = 26;
        Function0 function043 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = arrayList;
                switch (i46) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        return keyguardViewMediator;
                    case 1:
                        return ((KeyguardViewMediator) obj).mJavaAdapter;
                    case 2:
                        return ((KeyguardViewMediator) obj).mKeyguardInteractor;
                    case 3:
                        return ((KeyguardViewMediator) obj).mHandler;
                    case 4:
                        return ((KeyguardViewMediator) obj).mAlarmManager;
                    case 5:
                        return ((KeyguardViewMediator) obj).mShowKeyguardWakeLock;
                    case 6:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mWakeAndUnlocking);
                    case 7:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mPendingLock);
                    case 8:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mShowing);
                    case 9:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mAodShowing);
                    case 10:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mExternallyEnabled);
                    case 11:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mGoingToSleep);
                    case 12:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mBootCompleted);
                    case 13:
                        return Boolean.valueOf(((KeyguardViewMediator) obj).mKeyguardDonePending);
                    case 14:
                        return Integer.valueOf(((KeyguardViewMediator) obj).mDelayedShowingSequence);
                    case 15:
                        return ((KeyguardViewMediator) obj).mSurfaceBehindRemoteAnimationFinishedCallback;
                    case 16:
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                        return Unit.INSTANCE;
                    case 17:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator3.resetStateLocked$1(true);
                        return Unit.INSTANCE;
                    case 18:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).adjustStatusBarLocked$1(false, false);
                        return Unit.INSTANCE;
                    case 19:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        long lockTimeout$1 = keyguardViewMediator4.getLockTimeout$1(keyguardViewMediator4.mSelectedUserInteractor.getSelectedUserId());
                        if (lockTimeout$1 == 0) {
                            keyguardViewMediator4.doKeyguardLocked$1(null);
                        } else {
                            keyguardViewMediator4.doKeyguardLaterLocked$1(lockTimeout$1);
                        }
                        return Unit.INSTANCE;
                    case 20:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent10 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).handleHide$1();
                        return Unit.INSTANCE;
                    case 21:
                        Intent intent11 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent12 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Trace.beginSection("KeyguardViewMediator#hideLocked");
                        android.util.Log.d("KeyguardViewMediator", "hideLocked");
                        KeyguardViewMediator.AnonymousClass14 anonymousClass142 = ((KeyguardViewMediator) obj).mHandler;
                        anonymousClass142.sendMessage(anonymousClass142.obtainMessage(2));
                        Trace.endSection();
                        return Unit.INSTANCE;
                    case 22:
                        Intent intent13 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent14 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).tryKeyguardDone$1();
                        return Unit.INSTANCE;
                    case 23:
                        Intent intent15 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent16 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        ((KeyguardViewMediator) obj).setPendingLock(false);
                        return Unit.INSTANCE;
                    case 24:
                        Intent intent17 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        keyguardViewMediator5.mPendingReset = false;
                        return Unit.INSTANCE;
                    case 25:
                        Intent intent18 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        keyguardViewMediator6.mDelayedShowingSequence++;
                        return Unit.INSTANCE;
                    default:
                        return Integer.valueOf(((ArrayList) obj).size());
                }
            }
        };
        final int i47 = 3;
        final int i48 = 4;
        final int i49 = 5;
        final int i50 = 6;
        final int i51 = 7;
        keyguardViewMediatorHelperImpl.viewMediatorProvider = new ViewMediatorProvider(function0, function02, function03, function04, function05, function06, function07, function08, function09, function010, function011, function012, function013, function014, function015, function016, function017, function018, function019, function020, function021, function022, function023, function024, function025, function026, function027, function028, function029, function030, function031, function032, function033, function034, function035, function036, function037, function038, function039, function3, function1, function12, function040, function041, function13, function042, function043, new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda45
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                switch (i47) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.doKeyguardLocked$1((Bundle) obj);
                        break;
                    case 1:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        int iIntValue = ((Integer) obj).intValue();
                        if (iIntValue != 0) {
                            keyguardViewMediator.mHelper.playSound$2(iIntValue);
                        }
                        break;
                    case 2:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        int iIntValue2 = ((Integer) obj).intValue();
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        break;
                    case 4:
                        String str = (String) obj;
                        if (str != null) {
                            keyguardViewMediator.mPhoneState = str;
                        }
                        break;
                    case 5:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        }, new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda45
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                switch (i48) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.doKeyguardLocked$1((Bundle) obj);
                        break;
                    case 1:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        int iIntValue = ((Integer) obj).intValue();
                        if (iIntValue != 0) {
                            keyguardViewMediator.mHelper.playSound$2(iIntValue);
                        }
                        break;
                    case 2:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        int iIntValue2 = ((Integer) obj).intValue();
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        break;
                    case 4:
                        String str = (String) obj;
                        if (str != null) {
                            keyguardViewMediator.mPhoneState = str;
                        }
                        break;
                    case 5:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        }, new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda45
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                switch (i49) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.doKeyguardLocked$1((Bundle) obj);
                        break;
                    case 1:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        int iIntValue = ((Integer) obj).intValue();
                        if (iIntValue != 0) {
                            keyguardViewMediator.mHelper.playSound$2(iIntValue);
                        }
                        break;
                    case 2:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        int iIntValue2 = ((Integer) obj).intValue();
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        break;
                    case 4:
                        String str = (String) obj;
                        if (str != null) {
                            keyguardViewMediator.mPhoneState = str;
                        }
                        break;
                    case 5:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        }, new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda45
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                switch (i50) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.doKeyguardLocked$1((Bundle) obj);
                        break;
                    case 1:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        int iIntValue = ((Integer) obj).intValue();
                        if (iIntValue != 0) {
                            keyguardViewMediator.mHelper.playSound$2(iIntValue);
                        }
                        break;
                    case 2:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        int iIntValue2 = ((Integer) obj).intValue();
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        break;
                    case 4:
                        String str = (String) obj;
                        if (str != null) {
                            keyguardViewMediator.mPhoneState = str;
                        }
                        break;
                    case 5:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
                        break;
                }
                return Unit.INSTANCE;
            }
        }, new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda45
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                switch (i51) {
                    case 0:
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.doKeyguardLocked$1((Bundle) obj);
                        break;
                    case 1:
                        Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        int iIntValue = ((Integer) obj).intValue();
                        if (iIntValue != 0) {
                            keyguardViewMediator.mHelper.playSound$2(iIntValue);
                        }
                        break;
                    case 2:
                        Intent intent5 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mHiding = ((Boolean) obj).booleanValue();
                        break;
                    case 3:
                        int iIntValue2 = ((Integer) obj).intValue();
                        Intent intent6 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        break;
                    case 4:
                        String str = (String) obj;
                        if (str != null) {
                            keyguardViewMediator.mPhoneState = str;
                        }
                        break;
                    case 5:
                        Intent intent7 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        KeyguardViewMediator.initAlphaForAnimationTargets((RemoteAnimationTarget[]) obj);
                        break;
                    case 6:
                        Intent intent8 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mRemoteAnimationTarget = (RemoteAnimationTarget) obj;
                        break;
                    default:
                        Intent intent9 = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        keyguardViewMediator.mUnoccludeFinishedCallback = (IRemoteAnimationFinishedCallback) obj;
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
        this.mProcessWrapper = processWrapper;
        this.mSystemPropertiesHelper = systemPropertiesHelper;
        this.mStatusBarService = iStatusBarService;
        this.mKeyguardDisplayManager = keyguardDisplayManager;
        this.mShadeController = lazy4;
        dumpManager.registerDumpable(this);
        this.mKeyguardTransitions = keyguardTransitions;
        this.mNotificationShadeWindowControllerLazy = lazy5;
        this.mShowHomeOverLockscreen = deviceConfigProxy.getBoolean("systemui", "nav_bar_handle_show_over_lockscreen", true);
        deviceConfigProxy.addOnPropertiesChangedListener("systemui", new Executor() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda3
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                anonymousClass14.post(runnable);
            }
        }, r9);
        this.mInGestureNavigationMode = QuickStepContract.isGesturalMode(navigationModeController.addListener(new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda4
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i52) {
                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                keyguardViewMediator.getClass();
                keyguardViewMediator.mInGestureNavigationMode = QuickStepContract.isGesturalMode(i52);
            }
        }));
        this.mDozeParameters = dozeParameters;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mKeyguardInteractor = keyguardInteractor;
        this.mTransitionBootInteractor = keyguardTransitionBootInteractor;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        sysuiStatusBarStateController.addCallback(this);
        this.mKeyguardStateController = keyguardStateController;
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(r10);
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
        this.mOrderUnlockAndWake = context.getResources().getBoolean(android.R.bool.config_showBuiltinWirelessChargingAnim);
        PowerManager.WakeLock wakeLockNewWakeLock = powerManager.newWakeLock(1, "show keyguard");
        this.mShowKeyguardWakeLock = wakeLockNewWakeLock;
        wakeLockNewWakeLock.setReferenceCounted(false);
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
                iKeyguardStateCallback.onShowingStateChanged(this.mShowing, this.mSelectedUserInteractor.getSelectedUserId());
                iKeyguardStateCallback.onInputRestrictedStateChanged(this.mInputRestricted);
                iKeyguardStateCallback.onTrustedChanged(this.mUpdateMonitor.getUserHasTrust(this.mSelectedUserInteractor.getSelectedUserId()));
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "Failed to call to IKeyguardStateCallback", e);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0043 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void adjustStatusBarLocked$1(boolean z, boolean z2) {
        int i;
        KeyguardEditModeController keyguardEditModeController;
        if (this.mStatusBarManager == null) {
            this.mStatusBarManager = (StatusBarManager) this.mContext.getSystemService("statusbar");
        }
        if (this.mStatusBarManager == null) {
            android.util.Log.w("KeyguardViewMediator", "Could not get status bar manager");
            return;
        }
        if (z2) {
            if (UserManager.isVisibleBackgroundUsersEnabled()) {
                ProcessWrapper processWrapper = this.mProcessWrapper;
                processWrapper.getClass();
                if (ProcessWrapper.isSystemUser() || processWrapper.mActivityManager.isProfileForeground(Process.myUserHandle())) {
                    try {
                        this.mStatusBarService.disableForUser(0, this.mStatusBarDisableToken, this.mContext.getPackageName(), this.mSelectedUserInteractor.getSelectedUserId());
                    } catch (RemoteException e) {
                        android.util.Log.d("KeyguardViewMediator", "Failed to force clear flags 0", e);
                    }
                } else {
                    android.util.Log.d("KeyguardViewMediator", "Status bar manager is disabled for visible background users");
                }
            }
        }
        boolean z3 = this.mOccluded;
        boolean z4 = this.mShowHomeOverLockscreen;
        boolean z5 = this.mInGestureNavigationMode;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        boolean zIsShowing$1 = keyguardViewMediatorHelperImpl.isShowing$1();
        boolean z6 = ((KeyguardStateControllerImpl) keyguardViewMediatorHelperImpl.stateController).mKeyguardGoingAway;
        if (!z6) {
            i = 18874368;
            if (!z && !((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).isShowingAndNotOccluded()) {
                if (!zIsShowing$1) {
                    i = 0;
                } else if (z3 && (((keyguardEditModeController = keyguardViewMediatorHelperImpl.editModeController) == null || !((KeyguardEditModeControllerImpl) keyguardEditModeController).isEditMode) && (!LsRune.SUBSCREEN_WATCHFACE || keyguardViewMediatorHelperImpl.foldControllerImpl.isFoldOpened()))) {
                    i = 16777216;
                }
            }
        }
        if (zIsShowing$1 && keyguardViewMediatorHelperImpl.updateMonitor.isRemoteLockMode()) {
            i |= EnterpriseDeviceManager.PASSWORD_QUALITY_ALPHANUMERIC;
        }
        if (keyguardViewMediatorHelperImpl.disableFlags == i) {
            int[] iArr = {2097152, 16777216, 262144, 65536};
            int i2 = keyguardViewMediatorHelperImpl.disabled1;
            for (int i3 = 0; i3 < 4; i3++) {
                int i4 = iArr[i3];
                if ((i4 & i) == i4 && (i4 & i2) != i4) {
                    CharsKt__CharJVMKt.checkRadix(16);
                    String string = Integer.toString(i, 16);
                    CharsKt__CharJVMKt.checkRadix(16);
                    KeyguardViewMediatorHelperImpl.logD$1("isValidDisableFlags 0x" + string + " 0x" + Integer.toString(i2, 16));
                }
            }
            CharsKt__CharJVMKt.checkRadix(16);
            KeyguardViewMediatorHelperImpl.logD$1("adjustStatusBarLocked: no need to update flags=0x" + Integer.toString(i, 16) + " / showHomeOverLock=" + z4);
            return;
        }
        keyguardViewMediatorHelperImpl.disableFlags = i;
        boolean zIsSecure$2 = keyguardViewMediatorHelperImpl.isSecure$2();
        CharsKt__CharJVMKt.checkRadix(16);
        String string2 = Integer.toString(i, 16);
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("adjustStatusBarLocked: goingAway=", " showing=", " occluded=", z6, zIsShowing$1);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z3, " isSecure=", zIsSecure$2, " force=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z, " showHomeOverLock=", z4, " gestureNaviMode=");
        sbM.append(z5);
        sbM.append(" --> flags=0x");
        sbM.append(string2);
        KeyguardViewMediatorHelperImpl.logD$1(sbM.toString());
        try {
            IStatusBarService barService = keyguardViewMediatorHelperImpl.getBarService();
            if (barService != null) {
                keyguardViewMediatorHelperImpl.getHandler$1().removeMessages(VolteConstants.ErrorCode.PPP_STATUS_CLOSE_EVENT);
                if (((KeyguardViewController) keyguardViewMediatorHelperImpl.viewControllerLazy.get()).isLaunchEditMode() && z6) {
                    keyguardViewMediatorHelperImpl.getHandler$1().sendEmptyMessage(VolteConstants.ErrorCode.PPP_STATUS_CLOSE_EVENT);
                } else {
                    barService.disable(i, keyguardViewMediatorHelperImpl.token, keyguardViewMediatorHelperImpl.context.getPackageName());
                }
            }
        } catch (RemoteException e2) {
            Slog.w("KeyguardViewMediator", "adjustStatusBarLocked - disable failed", e2);
            KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.WARNING, "adjustStatusBarLocked - disable failed", e2);
        }
    }

    public void cancelKeyguardExitAnimation() {
        Trace.beginSection("KeyguardViewMediator#cancelKeyguardExitAnimation");
        AnonymousClass14 anonymousClass14 = this.mHandler;
        anonymousClass14.sendMessage(anonymousClass14.obtainMessage(19));
        Trace.endSection();
    }

    public final InteractionJankMonitor.Configuration.Builder createInteractionJankMonitorConf$1(int i, String str) {
        Log.d("KeyguardViewMediator", str != null ? str : "null");
        InteractionJankMonitor.Configuration.Builder builderWithView = InteractionJankMonitor.Configuration.Builder.withView(i, ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
        return str != null ? builderWithView.setTag(str) : builderWithView;
    }

    public void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway) {
            android.util.Log.i("KeyguardViewMediator", "Ignoring dismiss because we're already going away.");
        } else {
            if (this.mUpdateMonitor.mSwitchingUser) {
                Log.d("KeyguardViewMediator", "dismiss cancelled by UserSwitching");
                return;
            }
            Log.d("KeyguardViewMediator", PopupUIUtil.EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_DISMISS);
            KeyguardUnlockInfo.setUnlockTriggerIfNotSet(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_EXTERNAL);
            this.mHandler.obtainMessage(11, new DismissMessage(iKeyguardDismissCallback, charSequence)).sendToTarget();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00dd A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a4 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void doKeyguardLaterForChildProfilesLocked$1() {
        for (UserInfo userInfo : ((UserTrackerImpl) this.mUserTracker).getUserProfiles()) {
            if (userInfo.isEnabled()) {
                int i = userInfo.id;
                if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
                    keyguardViewMediatorHelperImpl.getClass();
                    long intForUser = SemPersonaManager.isSecureFolderId(i) ? Settings.System.getIntForUser(keyguardViewMediatorHelperImpl.context.getContentResolver(), "knox_screen_off_timeout", -1, i) : Settings.Secure.getIntForUser(keyguardViewMediatorHelperImpl.context.getContentResolver(), "knox_screen_off_timeout", -1, i);
                    long maximumTimeToLock = keyguardViewMediatorHelperImpl.lockPatternUtils.getDevicePolicyManager().getMaximumTimeToLock(null, i);
                    boolean z = maximumTimeToLock > 0;
                    if (z && intForUser > 0) {
                        intForUser = Math.min(maximumTimeToLock, intForUser);
                    } else if (z) {
                        intForUser = maximumTimeToLock;
                    } else {
                        if (intForUser > 0) {
                        }
                        if (intForUser <= 0) {
                            long jElapsedRealtime = this.mSystemClock.elapsedRealtime() + intForUser;
                            Intent intent = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
                            intent.setPackage(this.mContext.getPackageName());
                            intent.putExtra("seq", this.mDelayedProfileShowingSequence);
                            intent.putExtra("android.intent.extra.USER_ID", i);
                            intent.addFlags(268435456);
                            this.mAlarmManager.setExactAndAllowWhileIdle(2, jElapsedRealtime, PendingIntent.getBroadcast(this.mContext, i, intent, 335544320));
                        } else if (intForUser == 0 || intForUser == -2) {
                            this.mTrustManager.setDeviceLockedForUser(i, true);
                        }
                    }
                    if (intForUser != 0 && intForUser != -1 && intForUser != -2) {
                        intForUser = Math.max(intForUser, 5000L);
                    }
                    intForUser = Math.max(intForUser - Math.max(android.os.SystemClock.uptimeMillis() - keyguardViewMediatorHelperImpl.pm.getLastUserActivityTime(i), 0L), 0L);
                    if (intForUser <= 0) {
                    }
                }
            }
        }
    }

    public final void doKeyguardLaterLocked$1(long j) {
        boolean zIsLockscreenDisabled;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        keyguardViewMediatorHelperImpl.getClass();
        boolean z = LsRune.COVER_SUPPORTED;
        if (!z || keyguardViewMediatorHelperImpl.doKeyguardPendingIntent == null) {
            zIsLockscreenDisabled = keyguardViewMediatorHelperImpl.updateMonitor.isLockscreenDisabled();
        } else {
            Log.d("KeyguardViewMediator", "doKeyguardLaterLocked is already in process");
            zIsLockscreenDisabled = true;
        }
        if (zIsLockscreenDisabled) {
            return;
        }
        long jElapsedRealtime = this.mSystemClock.elapsedRealtime() + j;
        Intent intent = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        intent.setPackage(this.mContext.getPackageName());
        intent.putExtra("seq", this.mDelayedShowingSequence);
        intent.addFlags(268435456);
        PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, intent, 335544320);
        this.mAlarmManager.setExactAndAllowWhileIdle(2, jElapsedRealtime, broadcast);
        Log.d("KeyguardViewMediator", "setting alarm to turn off keyguard, seq = %s, timeout = %d", Integer.valueOf(this.mDelayedShowingSequence), Long.valueOf(j));
        doKeyguardLaterForChildProfilesLocked$1();
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        if (z) {
            keyguardViewMediatorHelperImpl.doKeyguardPendingIntent = broadcast;
        }
    }

    public final boolean doKeyguardLocked(Bundle bundle, boolean z) {
        boolean z2 = this.mExternallyEnabled;
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        if (!z2 && !this.mLockPatternUtils.isUserInLockdown(selectedUserInteractor.getSelectedUserId())) {
            Objects.requireNonNull(keyguardViewMediatorHelperImpl);
            boolean z3 = Rune.SYSUI_MULTI_SIM;
            if ((!keyguardViewMediatorHelperImpl.isSecure$2() || keyguardViewMediatorHelperImpl.activityManager.getLockTaskModeState() != 0) && !keyguardViewMediatorHelperImpl.updateMonitor.isForcedLock()) {
                android.util.Log.d("KeyguardViewMediator", "doKeyguard: not showing because externally disabled");
                notifyLockNowCallback();
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
                    notifyLockNowCallback();
                } else {
                    if (!keyguardStateControllerImpl.mKeyguardGoingAway) {
                        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                        android.util.Log.d("KeyguardViewMediator", "doKeyguard: not showing (instead, resetting) because it is already showing, we're interactive, we were not previously hiding. It should be safe to short-circuit here.");
                        if (bundle != null && keyguardStateController.isVisible()) {
                            bundle.putBoolean("KeyguardExitEditVI", false);
                        }
                        keyguardViewMediatorHelperImpl.setShowingOptions(bundle);
                        resetStateLocked$1(false);
                        notifyLockNowCallback();
                        return false;
                    }
                    android.util.Log.e("KeyguardViewMediator", "doKeyguard: we're still showing, but going away. Re-show the keyguard rather than short-circuiting and resetting.");
                }
            }
        }
        boolean z4 = SystemProperties.getBoolean("keyguard.no_require_sim", true);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        boolean z5 = keyguardUpdateMonitor.isSimPinSecure() || ((SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(1)) || SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(7))) && !z4);
        if (!z5 && shouldWaitForProvisioning$1()) {
            android.util.Log.d("KeyguardViewMediator", "doKeyguard: not showing because device isn't provisioned and the sim is not locked or missing");
            notifyLockNowCallback();
            return false;
        }
        boolean z6 = bundle != null && bundle.getBoolean("force_show", false);
        if (this.mLockPatternUtils.isLockScreenDisabled(selectedUserInteractor.getSelectedUserId()) && !z5 && !z6) {
            android.util.Log.d("KeyguardViewMediator", "doKeyguard: not showing because lockscreen is off");
            notifyLockNowCallback();
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
        if (!keyguardViewMediatorHelperImpl.isShowing$1()) {
            return false;
        }
        ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl.viewMediatorProvider;
        if (viewMediatorProvider == null) {
            viewMediatorProvider = null;
        }
        Function3 function3 = viewMediatorProvider.setShowingLocked;
        Boolean bool = Boolean.FALSE;
        function3.invoke(bool, bool, "hideLockByDisabled");
        keyguardViewMediatorHelperImpl.hidingByDisabled = true;
        Log.d("KeyguardViewMediator", "hideLocked by disabled keyguard");
        ViewMediatorProvider viewMediatorProvider2 = keyguardViewMediatorHelperImpl.viewMediatorProvider;
        (viewMediatorProvider2 != null ? viewMediatorProvider2 : null).hideLocked.invoke();
        return false;
    }

    public final void doKeyguardLocked$1(Bundle bundle) {
        if (bundle != null && bundle.getBoolean("extra_trigger_hub")) {
            FromGoneTransitionInteractor fromGoneTransitionInteractor = (FromGoneTransitionInteractor) this.mKeyguardInteractor.fromGoneTransitionInteractor.get();
            if (((TransitionStep) fromGoneTransitionInteractor.transitionInteractor.startedKeyguardTransitionStep.$$delegate_0.getValue()).to == KeyguardState.GONE) {
                fromGoneTransitionInteractor.communalSettingsInteractor.isV2FlagEnabled();
            }
            this.mPM.goToSleep(android.os.SystemClock.uptimeMillis(), 4, 0);
            return;
        }
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        if (bundle != null && bundle.getBinder("onSwitchCallback") != null) {
            LockNowCallback lockNowCallback = new LockNowCallback(this, selectedUserId, IRemoteCallback.Stub.asInterface(bundle.getBinder("onSwitchCallback")));
            synchronized (this.mLockNowCallbacks) {
                ((ArrayList) this.mLockNowCallbacks).add(lockNowCallback);
            }
            RecyclerView$$ExternalSyntheticOutline0.m(lockNowCallback.mUserId, "KeyguardViewMediator", new StringBuilder("LockNowCallback required for user: "));
        }
        doKeyguardLocked(bundle, false);
    }

    public void doKeyguardTimeout(Bundle bundle) {
        AnonymousClass14 anonymousClass14 = this.mHandler;
        anonymousClass14.removeMessages(10);
        anonymousClass14.sendMessageAtFrontOfQueue(anonymousClass14.obtainMessage(10, bundle));
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
        if (this.mIsKeyguardExitAnimationCanceled) {
            android.util.Log.d("KeyguardViewMediator", "Ignoring exitKeyguardAndFinishSurfaceBehindRemoteAnimation. mIsKeyguardExitAnimationCanceled==true");
            return;
        }
        ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).blockPanelExpansionFromCurrentTouch();
        boolean z = this.mShowing;
        ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).notifyFinishedKeyguardExitAnimation(false);
        DejankUtils.setImmediate(true);
        DejankUtils.postAfterTraversal(new KeyguardViewMediator$$ExternalSyntheticLambda1(this, z, 0));
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
            this.mKeyguardInteractor.showKeyguard();
        }
    }

    public IRemoteAnimationRunner getExitAnimationRunner() {
        AnonymousClass18 anonymousClass18 = new AnonymousClass18(this.mExitAnimationRunner);
        this.mHelper.exitAnimationRunner = anonymousClass18;
        return anonymousClass18;
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
        return new AnonymousClass18(this.mHelper.occludeAnimationRunner);
    }

    public IRemoteAnimationRunner getOccludeByDreamAnimationRunner() {
        return new AnonymousClass18(this.mOccludeByDreamAnimationRunner);
    }

    public IRemoteAnimationRunner getUnoccludeAnimationRunner() {
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        return new AnonymousClass18(this.mHelper.unoccluedAnimationRunner);
    }

    public ViewMediatorCallback getViewMediatorCallback() {
        return this.mViewMediatorCallback;
    }

    public void handleBeforeUserSwitching(int i, Runnable runnable) {
        android.util.Log.d("KeyguardViewMediator", String.format("onBeforeUserSwitching %d", Integer.valueOf(i)));
        synchronized (this) {
            try {
                this.mHandler.removeMessages(11);
                notifyTrustedChangedLocked$1(this.mUpdateMonitor.getUserHasTrust(i));
                resetKeyguardDonePendingLocked$1();
                adjustStatusBarLocked$1(false, false);
                ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(false);
                if (!this.mLockPatternUtils.isSecure(i) || this.mShowing) {
                    resetStateLocked$1(true);
                } else {
                    doKeyguardLocked$1(null);
                }
                runnable.run();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0091 A[Catch: all -> 0x0054, TryCatch #0 {all -> 0x0054, blocks: (B:13:0x002f, B:15:0x0049, B:16:0x0052, B:20:0x0057, B:22:0x005c, B:24:0x0064, B:28:0x006f, B:29:0x0072, B:31:0x0076, B:33:0x007a, B:35:0x0085, B:42:0x0095, B:44:0x0099, B:45:0x00a0, B:47:0x00cd, B:49:0x00d1, B:54:0x00e8, B:51:0x00d7, B:53:0x00db, B:40:0x0091, B:46:0x00a8), top: B:59:0x002f }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0099 A[Catch: all -> 0x0054, TryCatch #0 {all -> 0x0054, blocks: (B:13:0x002f, B:15:0x0049, B:16:0x0052, B:20:0x0057, B:22:0x005c, B:24:0x0064, B:28:0x006f, B:29:0x0072, B:31:0x0076, B:33:0x007a, B:35:0x0085, B:42:0x0095, B:44:0x0099, B:45:0x00a0, B:47:0x00cd, B:49:0x00d1, B:54:0x00e8, B:51:0x00d7, B:53:0x00db, B:40:0x0091, B:46:0x00a8), top: B:59:0x002f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleHide$1() {
        Trace.beginSection("KeyguardViewMediator#handleHide");
        if (this.mAodShowing) {
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
            if (!keyguardViewMediatorHelperImpl.fastUnlockController.isFastWakeAndUnlockMode() && (!LsRune.KEYGUARD_SUB_DISPLAY_LOCK || !keyguardViewMediatorHelperImpl.foldControllerImpl.isUnlockOnFoldOpened())) {
                this.mPM.wakeUp(this.mSystemClock.uptimeMillis(), 4, "com.android.systemui:BOUNCER_DOZING");
            }
        }
        synchronized (this) {
            try {
                android.util.Log.d("KeyguardViewMediator", "handleHide");
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                BooleanSupplier booleanSupplier = new BooleanSupplier() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda72
                    @Override // java.util.function.BooleanSupplier
                    public final boolean getAsBoolean() {
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = keyguardViewMediatorHelperImpl2;
                        boolean zIsKeyguardHiding = keyguardViewMediatorHelperImpl3.isKeyguardHiding();
                        boolean zIsUnlockStartedOrFinished = keyguardViewMediatorHelperImpl3.isUnlockStartedOrFinished();
                        boolean z = false;
                        boolean z2 = !keyguardViewMediatorHelperImpl3.isShowing$1() && ((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) keyguardViewMediatorHelperImpl3.shadeWindowControllerHelper$delegate.getValue())).getCurrentState().keyguardShowing;
                        boolean z3 = !keyguardViewMediatorHelperImpl3.isShowing$1() && ((KeyguardViewMediator) keyguardViewMediatorHelperImpl3.viewMediatorLazy.get()).isInputRestricted();
                        if (zIsKeyguardHiding || (zIsUnlockStartedOrFinished && !z2 && !z3)) {
                            z = true;
                        }
                        if (z) {
                            StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("cancel handleHide ", " ", " ", zIsKeyguardHiding, zIsUnlockStartedOrFinished);
                            sbM.append(z2);
                            sbM.append(" ");
                            sbM.append(z3);
                            KeyguardViewMediatorHelperImpl.logD$1(sbM.toString());
                        }
                        if (z) {
                            Lazy lazy = keyguardViewMediatorHelperImpl3.biometricUnlockControllerLazy;
                            if (((BiometricUnlockController) lazy.get()).isWakeAndUnlock()) {
                                ((BiometricUnlockController) lazy.get()).finishKeyguardFadingAway();
                            }
                        }
                        return z;
                    }
                };
                boolean z = Rune.SYSUI_MULTI_SIM;
                if (booleanSupplier.getAsBoolean()) {
                    android.util.Log.d("KeyguardViewMediator", "handleHide: mWakeAndUnlocking set false");
                    this.mWakeAndUnlocking = false;
                    return;
                }
                if (!this.mWakeAndUnlocking) {
                    setUnlockAndWakeFromDream$1(0, this.mStatusBarStateController.isDreaming() && this.mPM.isInteractive());
                }
                if (this.mBootCompleted && this.mShowing) {
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this.mHelper;
                    boolean z2 = this.mOccluded;
                    keyguardViewMediatorHelperImpl3.getClass();
                    if ((!LsRune.KEYGUARD_SUB_DISPLAY_COVER || keyguardViewMediatorHelperImpl3.foldControllerImpl.isFoldOpened()) && !z2) {
                    }
                    if (this.mUnlockingAndWakingFromDream) {
                    }
                    this.mHiding = true;
                    this.mKeyguardGoingAwayRunnable.run();
                } else if (this.mUnlockingAndWakingFromDream) {
                    if (this.mUnlockingAndWakingFromDream) {
                        android.util.Log.d("KeyguardViewMediator", "hiding keyguard before waking from dream");
                    }
                    this.mHiding = true;
                    this.mKeyguardGoingAwayRunnable.run();
                } else {
                    KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                    ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).hide(this.mSystemClock.uptimeMillis() + this.mHideAnimation.getStartOffset(), this.mHideAnimation.getDuration());
                    onKeyguardExitFinished("Hiding keyguard while occluded. Just hide the keyguard view and exit.");
                }
                if ((this.mDreamOverlayShowing || this.mUpdateMonitor.mIsDreaming) && !this.mOrderUnlockAndWake) {
                    this.mPM.wakeUp(this.mSystemClock.uptimeMillis(), 4, "com.android.systemui:UNLOCK_DREAMING");
                }
                Trace.endSection();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void handleKeyguardDone$1() {
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDone");
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        this.mUiBgExecutor.execute(new KeyguardViewMediator$$ExternalSyntheticLambda65(this, selectedUserId, 0));
        android.util.Log.d("KeyguardViewMediator", "handleKeyguardDone");
        synchronized (this) {
            resetKeyguardDonePendingLocked$1();
        }
        if (!this.mGoingToSleep || shouldWaitForProvisioning$1()) {
            setPendingLock(false);
            handleHide$1();
            KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) this.mKeyguardInteractor.repository;
            keyguardRepositoryImpl.getClass();
            keyguardRepositoryImpl.keyguardDoneAnimationsFinished.tryEmit(Unit.INSTANCE);
            this.mUpdateMonitor.clearFingerprintRecognized(selectedUserId);
            Trace.endSection();
            return;
        }
        android.util.Log.i("KeyguardViewMediator", "Device is going to sleep, aborting keyguardDone");
        this.mUpdateMonitor.clearFingerprintRecognized(selectedUserId);
        this.mDismissCallbackRegistry.notifyDismissCancelled();
        ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).onDismissCancelled();
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        boolean z = Rune.SYSUI_MULTI_SIM;
        keyguardViewMediatorHelperImpl.onAbortKeyguardDone();
    }

    public final void handleShowInner(Bundle bundle) {
        CoverState coverState;
        boolean z = bundle != null && bundle.getBoolean("show_dismissible", false);
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        if (z) {
            this.mUpdateMonitor.setForceIsDismissibleKeyguard(true);
        } else if (this.mLockPatternUtils.isSecure(selectedUserId)) {
            this.mLockPatternUtils.getDevicePolicyManager().reportKeyguardSecured(selectedUserId);
        }
        synchronized (this) {
            try {
                if (!this.mSystemReady) {
                    android.util.Log.d("KeyguardViewMediator", "ignoring handleShow because system is not ready.");
                    notifyLockNowCallback();
                    return;
                }
                android.util.Log.d("KeyguardViewMediator", "handleShow");
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                boolean z2 = Rune.SYSUI_MULTI_SIM;
                if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_COVER) {
                    KeyguardFoldControllerImpl keyguardFoldControllerImpl = keyguardViewMediatorHelperImpl.foldControllerImpl;
                    if (keyguardFoldControllerImpl.initShowTime == 0) {
                        keyguardFoldControllerImpl.initShowTime = System.currentTimeMillis();
                    }
                }
                keyguardViewMediatorHelperImpl.fastUnlockController.reset();
                keyguardViewMediatorHelperImpl.updateMonitor.setUnlockingKeyguard(false);
                keyguardViewMediatorHelperImpl.hidingByDisabled = false;
                this.mKeyguardExitAnimationRunner = null;
                this.mWakeAndUnlocking = false;
                setUnlockAndWakeFromDream$1(1, false);
                setPendingLock(false);
                this.mHelper.setShowingOptions(bundle);
                boolean z3 = this.mHiding || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway;
                if (z3) {
                    android.util.Log.d("KeyguardViewMediator", "Forcing setShowingLocked because one of these is true:mHiding=" + this.mHiding + ", keyguardGoingAway=" + ((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway + ", which means we're showing in the middle of hiding.");
                }
                setShowingLocked("handleShowInner", true, z3);
                this.mHiding = false;
                KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).show(bundle);
                resetKeyguardDonePendingLocked$1();
                this.mHideAnimationRun = false;
                adjustStatusBarLocked$1(false, false);
                if (!LsRune.COVER_SUPPORTED || (coverState = this.mUpdateMonitor.getCoverState()) == null || !coverState.attached || coverState.getSwitchState()) {
                    userActivity();
                }
                this.mUpdateMonitor.setKeyguardGoingAway(false);
                ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(false);
                this.mShowKeyguardWakeLock.release();
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                if (!keyguardViewMediatorHelperImpl2.updateMonitor.getUserHasTrust(((UserTrackerImpl) keyguardViewMediatorHelperImpl2.userTracker).getUserId())) {
                    int selectedUserId2 = ((KnoxStateMonitorImpl) keyguardViewMediatorHelperImpl2.knoxStateMonitor).mSelectedUserInteractor.getSelectedUserId();
                    if (SemPersonaManager.isDoEnabled(selectedUserId2)) {
                        android.util.Log.d("KnoxStateMonitorImpl", "lockSdp :: Device Owner has been locked");
                        try {
                            SdpAuthenticator.getInstance().onDeviceOwnerLocked(selectedUserId2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        android.util.Log.d("KnoxStateMonitorImpl", "lockSdp :: Maybe keyguard shown as user " + selectedUserId2);
                    }
                }
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl3);
                keyguardViewMediatorHelperImpl3.onSecurityPropertyUpdated();
                if (!this.mOccluded || this.mKeyguardDisplayManager.isDesktopMode()) {
                    this.mKeyguardDisplayManager.show();
                }
                scheduleNonStrongBiometricIdleTimeout$1();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void handleStartKeyguardExitAnimationInner(long j, long j2, final RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        final int i = 2;
        final int i2 = 0;
        final int i3 = 1;
        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("handleStartKeyguardExitAnimation startTime=", j, " fadeoutDuration=");
        sbM.append(j2);
        android.util.Log.d("KeyguardViewMediator", sbM.toString());
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        if (this.mGoingAwayRequestedForUserId != selectedUserId) {
            android.util.Log.e("KeyguardViewMediator", "Not executing handleStartKeyguardExitAnimationInner() due to userId mismatch. Requested: " + this.mGoingAwayRequestedForUserId + ", current: " + selectedUserId);
            if (iRemoteAnimationFinishedCallback != null) {
                try {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                } catch (RemoteException e) {
                    Slog.w("KeyguardViewMediator", "Failed to call onAnimationFinished", e);
                }
            }
            this.mHiding = false;
            if (this.mLockPatternUtils.isSecure(selectedUserId)) {
                doKeyguardLocked$1(null);
                return;
            } else {
                resetStateLocked$1(true);
                dismiss(null, null);
                return;
            }
        }
        synchronized (this) {
            this.mIsKeyguardExitAnimationCanceled = false;
            if (!this.mHiding && !this.mSurfaceBehindRemoteAnimationRequested && !((KeyguardStateControllerImpl) this.mKeyguardStateController).mFlingingToDismissKeyguardDuringSwipeGesture) {
                if (iRemoteAnimationFinishedCallback != null) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                    } catch (RemoteException e2) {
                        Slog.w("KeyguardViewMediator", "Failed to call onAnimationFinished", e2);
                    }
                }
                setShowingLocked("handleStartKeyguardExitAnimation - canceled", this.mShowing, true);
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                boolean z = Rune.SYSUI_MULTI_SIM;
                keyguardViewMediatorHelperImpl.onAbortHandleStartKeyguardExitAnimation();
                return;
            }
            this.mHiding = false;
            ActivityTransitionAnimator.Runner runner = this.mKeyguardExitAnimationRunner;
            this.mKeyguardExitAnimationRunner = null;
            LatencyTracker.getInstance(this.mContext).onActionEnd(11);
            if (runner != null && iRemoteAnimationFinishedCallback != null) {
                IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = new IRemoteAnimationFinishedCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.16
                    public final IBinder asBinder() {
                        return iRemoteAnimationFinishedCallback.asBinder();
                    }

                    public final void onAnimationFinished() {
                        KeyguardWmStateRefactor keyguardWmStateRefactor2 = KeyguardWmStateRefactor.INSTANCE;
                        try {
                            iRemoteAnimationFinishedCallback.onAnimationFinished();
                        } catch (RemoteException e3) {
                            Slog.w("KeyguardViewMediator", "Failed to call onAnimationFinished", e3);
                        }
                        KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                        if (!keyguardViewMediator.mIsKeyguardExitAnimationCanceled) {
                            keyguardViewMediator.onKeyguardExitFinished("onRemoteAnimationFinished");
                            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).hide(0L, 0L);
                        }
                        KeyguardViewMediator.this.mInteractionJankMonitor.end(29);
                    }
                };
                try {
                    this.mInteractionJankMonitor.begin(createInteractionJankMonitorConf$1(29, "RunRemoteAnimation"));
                    runner.onAnimationStart(7, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback2);
                } catch (RemoteException e3) {
                    Slog.w("KeyguardViewMediator", "Failed to call onAnimationStart", e3);
                }
            } else if (((StatusBarStateControllerImpl) this.mStatusBarStateController).mLeaveOpenOnKeyguardHide || remoteAnimationTargetArr == null || remoteAnimationTargetArr.length <= 0) {
                ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).hide(j, j2);
                this.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda82
                    @Override // java.lang.Runnable
                    public final void run() {
                        final KeyguardViewMediator keyguardViewMediator = this.f$0;
                        final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback3 = iRemoteAnimationFinishedCallback;
                        RemoteAnimationTarget[] remoteAnimationTargetArr4 = remoteAnimationTargetArr;
                        if (iRemoteAnimationFinishedCallback3 == null) {
                            ((KeyguardUnlockAnimationController) keyguardViewMediator.mKeyguardUnlockAnimationControllerLazy.get()).notifyFinishedKeyguardExitAnimation(false);
                            return;
                        }
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.getClass();
                        if (remoteAnimationTargetArr4 == null || remoteAnimationTargetArr4.length == 0) {
                            Slog.e("KeyguardViewMediator", "Keyguard exit without a corresponding app to show.");
                            try {
                                KeyguardWmStateRefactor keyguardWmStateRefactor2 = KeyguardWmStateRefactor.INSTANCE;
                                iRemoteAnimationFinishedCallback3.onAnimationFinished();
                                return;
                            } catch (RemoteException unused) {
                                Slog.e("KeyguardViewMediator", "RemoteException");
                                return;
                            } finally {
                                keyguardViewMediator.mInteractionJankMonitor.end(29);
                            }
                        }
                        SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
                        RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr4[0];
                        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                        valueAnimatorOfFloat.setDuration(400L);
                        valueAnimatorOfFloat.setInterpolator(Interpolators.LINEAR);
                        valueAnimatorOfFloat.addUpdateListener(new KeyguardViewMediator$$ExternalSyntheticLambda84(remoteAnimationTarget, syncRtSurfaceTransactionApplier, 0));
                        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.17
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationCancel(Animator animator) {
                                try {
                                    KeyguardWmStateRefactor keyguardWmStateRefactor3 = KeyguardWmStateRefactor.INSTANCE;
                                    iRemoteAnimationFinishedCallback3.onAnimationFinished();
                                } catch (RemoteException unused2) {
                                    Slog.e("KeyguardViewMediator", "RemoteException");
                                } finally {
                                    KeyguardViewMediator.this.mInteractionJankMonitor.cancel(29);
                                }
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                try {
                                    KeyguardWmStateRefactor keyguardWmStateRefactor3 = KeyguardWmStateRefactor.INSTANCE;
                                    iRemoteAnimationFinishedCallback3.onAnimationFinished();
                                } catch (RemoteException unused2) {
                                    Slog.e("KeyguardViewMediator", "RemoteException");
                                } finally {
                                    KeyguardViewMediator.this.mInteractionJankMonitor.end(29);
                                }
                            }
                        });
                        valueAnimatorOfFloat.start();
                    }
                });
                onKeyguardExitFinished("remote animation disabled");
            } else {
                this.mSurfaceBehindRemoteAnimationFinishedCallback = iRemoteAnimationFinishedCallback;
                this.mSurfaceBehindRemoteAnimationRunning = true;
                ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).notifyStartSurfaceBehindRemoteAnimation((RemoteAnimationTarget[]) Arrays.stream(remoteAnimationTargetArr).filter(new Predicate() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda76
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        RemoteAnimationTarget remoteAnimationTarget = (RemoteAnimationTarget) obj;
                        switch (i2) {
                            case 0:
                                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                                if (remoteAnimationTarget.mode == 0) {
                                    break;
                                }
                                break;
                            case 1:
                                Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                                if (remoteAnimationTarget.mode == 0) {
                                    break;
                                }
                                break;
                            default:
                                Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                                if (remoteAnimationTarget.mode == 1) {
                                    break;
                                }
                                break;
                        }
                        return true;
                    }
                }).toArray(new IntFunction() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda77
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i4) {
                        switch (i2) {
                            case 0:
                                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                                return new RemoteAnimationTarget[i4];
                            case 1:
                                Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                                return new RemoteAnimationTarget[i4];
                            default:
                                Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                                return new RemoteAnimationTarget[i4];
                        }
                    }
                }), (RemoteAnimationTarget[]) Arrays.stream(remoteAnimationTargetArr2).filter(new Predicate() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda76
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        RemoteAnimationTarget remoteAnimationTarget = (RemoteAnimationTarget) obj;
                        switch (i3) {
                            case 0:
                                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                                if (remoteAnimationTarget.mode == 0) {
                                    break;
                                }
                                break;
                            case 1:
                                Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                                if (remoteAnimationTarget.mode == 0) {
                                    break;
                                }
                                break;
                            default:
                                Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                                if (remoteAnimationTarget.mode == 1) {
                                    break;
                                }
                                break;
                        }
                        return true;
                    }
                }).toArray(new IntFunction() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda77
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i4) {
                        switch (i3) {
                            case 0:
                                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                                return new RemoteAnimationTarget[i4];
                            case 1:
                                Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                                return new RemoteAnimationTarget[i4];
                            default:
                                Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                                return new RemoteAnimationTarget[i4];
                        }
                    }
                }), (RemoteAnimationTarget[]) Arrays.stream(remoteAnimationTargetArr2).filter(new Predicate() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda76
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        RemoteAnimationTarget remoteAnimationTarget = (RemoteAnimationTarget) obj;
                        switch (i) {
                            case 0:
                                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                                if (remoteAnimationTarget.mode == 0) {
                                    break;
                                }
                                break;
                            case 1:
                                Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                                if (remoteAnimationTarget.mode == 0) {
                                    break;
                                }
                                break;
                            default:
                                Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                                if (remoteAnimationTarget.mode == 1) {
                                    break;
                                }
                                break;
                        }
                        return true;
                    }
                }).toArray(new IntFunction() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda77
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i4) {
                        switch (i) {
                            case 0:
                                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                                return new RemoteAnimationTarget[i4];
                            case 1:
                                Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                                return new RemoteAnimationTarget[i4];
                            default:
                                Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                                return new RemoteAnimationTarget[i4];
                        }
                    }
                }), j, this.mSurfaceBehindRemoteAnimationRequested);
            }
            return;
        }
    }

    public void handleUserSwitchComplete(int i) {
        android.util.Log.d("KeyguardViewMediator", String.format("onUserSwitchComplete %d", Integer.valueOf(i)));
        if (this.mLockPatternUtils.isSecure(i)) {
            this.mHandler.postDelayed(new KeyguardViewMediator$$ExternalSyntheticLambda0(this, 0), 500L);
        }
    }

    public void handleUserSwitching(int i, Runnable runnable) {
        android.util.Log.d("KeyguardViewMediator", String.format("onUserSwitching %d", Integer.valueOf(i)));
        synchronized (this) {
            try {
                if (!this.mLockPatternUtils.isSecure(i)) {
                    dismiss(null, null);
                }
                runnable.run();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void hideSurfaceBehindKeyguard() {
        Log.d("KeyguardViewMediator", "hideSurfaceBehindKeyguard");
        this.mSurfaceBehindRemoteAnimationRequested = false;
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(false);
        if (this.mShowing) {
            setShowingLocked("hideSurfaceBehindKeyguard", true, true);
            Objects.requireNonNull(this.mHelper);
            boolean z = Rune.SYSUI_MULTI_SIM;
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

    public boolean isShowing() {
        return this.mShowing;
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
                doKeyguardLocked$1(null);
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

    public final void notifyLockNowCallback() {
        ArrayList arrayList;
        synchronized (this.mLockNowCallbacks) {
            arrayList = new ArrayList(this.mLockNowCallbacks);
            this.mLockNowCallbacks.clear();
        }
        for (int i = 0; i < arrayList.size(); i++) {
            LockNowCallback lockNowCallback = (LockNowCallback) arrayList.get(i);
            if (lockNowCallback.mUserId != this.mSelectedUserInteractor.getSelectedUserId()) {
                android.util.Log.i("KeyguardViewMediator", "Not notifying lockNowCallback due to user mismatch");
            } else {
                android.util.Log.i("KeyguardViewMediator", "Notifying lockNowCallback");
                try {
                    lockNowCallback.mRemoteCallback.sendResult((Bundle) null);
                } catch (RemoteException e) {
                    android.util.Log.e("KeyguardViewMediator", "Could not issue LockNowCallback sendResult", e);
                }
            }
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
                if (this.mContext.getResources().getBoolean(android.R.bool.config_letterboxIsSplitScreenAspectRatioForUnresizableAppsEnabled)) {
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
            setShowingLocked("onDozeAmountChanged", this.mShowing, true);
        }
    }

    public void onDreamingStarted() {
        this.mUpdateMonitor.dispatchDreamingStarted();
        synchronized (this) {
            try {
                if (this.mDeviceInteractive && !this.mHelper.keyguardDisplayManager.isExternalDesktopWindowing()) {
                    long lockTimeout$1 = getLockTimeout$1(this.mSelectedUserInteractor.getSelectedUserId());
                    if (lockTimeout$1 == 0) {
                        doKeyguardLocked$1(null);
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

    public final void onKeyguardExitFinished(String str) {
        int i;
        android.util.Log.d("KeyguardViewMediator", "onKeyguardExitFinished()");
        boolean zEquals = TelephonyManager.EXTRA_STATE_IDLE.equals(this.mPhoneState);
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        if (zEquals && (i = this.mUnlockSoundId) != 0) {
            keyguardViewMediatorHelperImpl.playSound$2(i);
        }
        setShowingLocked("onKeyguardExitFinished: ".concat(str), false, false);
        this.mWakeAndUnlocking = false;
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        this.mDismissCallbackRegistry.notifyDismissSucceeded();
        resetKeyguardDonePendingLocked$1();
        this.mHideAnimationRun = false;
        adjustStatusBarLocked$1(false, false);
        sendUserPresentBroadcast$1();
        this.mKeyguardInteractor.dismissKeyguard();
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        boolean z = Rune.SYSUI_MULTI_SIM;
        keyguardViewMediatorHelperImpl.onKeyguardExitFinished$1();
    }

    public void onScreenTurnedOff() {
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        boolean z = Rune.SYSUI_MULTI_SIM;
        if (!keyguardViewMediatorHelperImpl.fastUnlockController.isFastWakeAndUnlockMode()) {
            synchronized (keyguardViewMediatorHelperImpl.lock$delegate.getValue()) {
                keyguardViewMediatorHelperImpl.drawnCallback = null;
                Unit unit = Unit.INSTANCE;
            }
        }
        this.mUpdateMonitor.mHandler.sendEmptyMessage(CustomDeviceManager.DESTINATION_ADDRESS);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda9] */
    public void onStartedGoingToSleep(int i) {
        int i2;
        int i3;
        synchronized (this) {
            try {
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                boolean z = Rune.SYSUI_MULTI_SIM;
                keyguardViewMediatorHelperImpl.enableLooperLogController(5, 3000L);
                keyguardViewMediatorHelperImpl.lastSleepReason = i;
                if (LsRune.AOD_FULLSCREEN && keyguardViewMediatorHelperImpl.aodAmbientWallpaperHelper.isAODFullScreenMode()) {
                    keyguardViewMediatorHelperImpl.getHandler$1().post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$onStartedGoingToSleep$1
                        /* JADX WARN: Removed duplicated region for block: B:9:0x0025  */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            boolean z2;
                            SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = keyguardViewMediatorHelperImpl.unlockedScreenOffAnimationHelper;
                            if (secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mState == 0) {
                                Lazy lazy = secUnlockedScreenOffAnimationHelper.keyguardVisibilityMonitorLazy;
                                z2 = ((KeyguardVisibilityMonitor) lazy.get()).isVisible() && ((KeyguardVisibilityMonitor) lazy.get()).panelState == 2;
                            }
                            secUnlockedScreenOffAnimationHelper.isPanelOpenedOnGoingToSleep = z2;
                        }
                    });
                }
                if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                    keyguardViewMediatorHelperImpl.isScreenOnByFoldOpen = false;
                }
                this.mDeviceInteractive = false;
                this.mPowerGestureIntercepted = false;
                this.mGoingToSleep = true;
                int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
                boolean z2 = this.mLockPatternUtils.getPowerButtonInstantlyLocks(selectedUserId) || !this.mLockPatternUtils.isSecure(selectedUserId);
                final long lockTimeout$1 = getLockTimeout$1(this.mSelectedUserInteractor.getSelectedUserId());
                this.mLockLater = false;
                if (!this.mShowing || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway) {
                    i2 = i;
                    this.mHelper.updatePendingLock(i2, lockTimeout$1, z2, selectedUserId, new KeyguardViewMediator$$ExternalSyntheticLambda0(this, 4), new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardViewMediator keyguardViewMediator = this.f$0;
                            long j = lockTimeout$1;
                            Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                            keyguardViewMediator.doKeyguardLaterLocked$1(j);
                            keyguardViewMediator.mLockLater = true;
                        }
                    });
                } else {
                    this.mPendingReset = true;
                    i2 = i;
                }
                if (this.mPendingLock && (i3 = this.mLockSoundId) != 0) {
                    this.mHelper.playSound$2(i3);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mUpdateMonitor.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(321, i2, 0));
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(false);
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass162 = this.mUpdateMonitor.mHandler;
        anonymousClass162.sendMessage(anonymousClass162.obtainMessage(342, Boolean.FALSE));
        this.mHandler.sendEmptyMessage(17);
    }

    public void onStartedWakingUp(int i, boolean z) {
        boolean z2;
        int i2 = 0;
        Trace.beginSection("KeyguardViewMediator#onStartedWakingUp");
        synchronized (this) {
            try {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                KeyguardViewMediator$$ExternalSyntheticLambda63 keyguardViewMediator$$ExternalSyntheticLambda63 = new KeyguardViewMediator$$ExternalSyntheticLambda63(keyguardViewMediatorHelperImpl, i2);
                boolean z3 = Rune.SYSUI_MULTI_SIM;
                keyguardViewMediator$$ExternalSyntheticLambda63.accept(Integer.valueOf(i));
                this.mDeviceInteractive = true;
                if (this.mPendingLock && !z && !this.mWakeAndUnlocking) {
                    doKeyguardLocked$1(null);
                }
                this.mAnimatingScreenOff = false;
                this.mDelayedShowingSequence++;
                this.mDelayedProfileShowingSequence++;
                z2 = LsRune.COVER_SUPPORTED;
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                if (z2) {
                    keyguardViewMediatorHelperImpl2.cancelLockWhenCoverIsOpened(false);
                }
                if (z) {
                    this.mPowerGestureIntercepted = true;
                }
                android.util.Log.d("KeyguardViewMediator", "onStartedWakingUp, seq = " + this.mDelayedShowingSequence + ", mPowerGestureIntercepted = " + this.mPowerGestureIntercepted);
                android.util.Log.d("KeyguardViewMediator", "notifyStartedWakingUp");
                this.mHandler.sendEmptyMessage(14);
            } catch (Throwable th) {
                throw th;
            }
        }
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl3);
        Rune.runIf(new KeyguardViewMediator$$ExternalSyntheticLambda64(keyguardViewMediatorHelperImpl3, i2), z2);
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

    public void registerCentralSurfaces$1(CentralSurfacesImpl centralSurfacesImpl, ShadeLockscreenInteractor shadeLockscreenInteractor, ShadeExpansionStateManager shadeExpansionStateManager, BiometricUnlockController biometricUnlockController, View view) {
        this.mCentralSurfaces = centralSurfacesImpl;
        ViewGroup viewGroup = centralSurfacesImpl.mSecLockIconView;
        Lazy lazy = this.mKeyguardViewControllerLazy;
        ((KeyguardViewController) lazy.get()).registerLockIconContainer(viewGroup);
        ((KeyguardViewController) lazy.get()).registerCentralSurfaces(centralSurfacesImpl, shadeLockscreenInteractor, shadeExpansionStateManager, biometricUnlockController, view);
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
        android.util.Log.d("KeyguardViewMediator", "resetStateLocked");
        AnonymousClass14 anonymousClass14 = this.mHandler;
        anonymousClass14.sendMessage(anonymousClass14.obtainMessage(3, z ? 1 : 0, 0));
    }

    public final void scheduleNonStrongBiometricIdleTimeout$1() {
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
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
                    this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i = 1;
                            KeyguardViewMediator keyguardViewMediator = this.f$0;
                            UserManager userManager2 = userManager;
                            UserHandle userHandle2 = userHandle;
                            int i2 = selectedUserId;
                            Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                            keyguardViewMediator.getClass();
                            for (int i3 : userManager2.getProfileIdsWithDisabled(userHandle2.getIdentifier())) {
                                keyguardViewMediator.mContext.sendBroadcastAsUser(KeyguardViewMediator.USER_PRESENT_INTENT, UserHandle.of(i3), null, KeyguardViewMediator.USER_PRESENT_INTENT_OPTIONS);
                                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediator.mHelper;
                                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                                KeyguardViewMediator$$ExternalSyntheticLambda63 keyguardViewMediator$$ExternalSyntheticLambda63 = new KeyguardViewMediator$$ExternalSyntheticLambda63(keyguardViewMediatorHelperImpl, i);
                                boolean z = Rune.SYSUI_MULTI_SIM;
                                keyguardViewMediator$$ExternalSyntheticLambda63.accept(Integer.valueOf(i3));
                            }
                            keyguardViewMediator.mLockPatternUtils.userPresent(i2);
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

    public void setDozing(boolean z) {
        if (z == this.mDozing) {
            return;
        }
        this.mDozing = z;
        if (!z) {
            this.mAnimatingScreenOff = false;
        }
        if (this.mShowing || !this.mPendingLock || !this.mDozeParameters.canControlUnlockedScreenOff() || (LsRune.AOD_FULLSCREEN && this.mHelper.aodAmbientWallpaperHelper.isAODFullScreenMode())) {
            setShowingLocked("setDozing", this.mShowing, false);
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
                    if (this.mLockPatternUtils.isUserInLockdown(this.mSelectedUserInteractor.getSelectedUserId())) {
                        android.util.Log.d("KeyguardViewMediator", "keyguardEnabled(false) overridden by user lockdown");
                        return;
                    }
                    android.util.Log.d("KeyguardViewMediator", "remembering to reshow, hiding keyguard, disabling status bar expansion");
                    this.mNeedToReshowWhenReenabled = true;
                    updateInputRestrictedLocked$1();
                    Trace.beginSection("KeyguardViewMediator#hideLocked");
                    android.util.Log.d("KeyguardViewMediator", "hideLocked");
                    AnonymousClass14 anonymousClass14 = this.mHandler;
                    anonymousClass14.sendMessage(anonymousClass14.obtainMessage(2));
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
        TrackTracer.instantForGroup(z ? 1 : 0, "keyguard", "pendingLock");
    }

    public final void setShowingLocked(final String str, final boolean z, boolean z2) {
        int i = 1;
        final boolean z3 = this.mDozing && !this.mWakeAndUnlocking;
        boolean z4 = this.mShowing;
        boolean z5 = z != z4 || z2;
        boolean z6 = (z == z4 && z3 == this.mAodShowing && !z2) ? false : true;
        boolean z7 = z4 != z;
        this.mShowing = z;
        this.mAodShowing = z3;
        int i2 = KeyguardWmReorderAtmsCalls.$r8$clinit;
        if (z6) {
            this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardViewMediator keyguardViewMediator = this.f$0;
                    boolean z8 = z;
                    boolean z9 = z3;
                    String str2 = str;
                    Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                    keyguardViewMediator.getClass();
                    android.util.Log.d("KeyguardViewMediator", "updateActivityLockScreenState(" + z8 + ", " + z9 + ", " + str2 + ")");
                    if (z8) {
                        keyguardViewMediator.notifyLockNowCallback();
                    }
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediator.mHelper;
                    StandaloneCoroutine standaloneCoroutine = keyguardViewMediatorHelperImpl.lockShownJob;
                    if (standaloneCoroutine != null) {
                        standaloneCoroutine.cancel(null);
                    }
                    keyguardViewMediatorHelperImpl.lockShownJob = null;
                    KeyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1 keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1 = keyguardViewMediatorHelperImpl.setLockScreenShownRunnable;
                    keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1.showing = z8;
                    keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1.aodShowing = z9;
                    if (!z8 || !KeyguardViewMediatorHelperImplKt.isLockShownDelay) {
                        keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1.run();
                        return;
                    }
                    KeyguardEditModeController keyguardEditModeController = keyguardViewMediatorHelperImpl.editModeController;
                    long j = (keyguardEditModeController == null || !((KeyguardEditModeControllerImpl) keyguardEditModeController).isEditMode || keyguardViewMediatorHelperImpl.curIsOccluded) ? 300L : 1000L;
                    StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("updateActivityLockScreenState ", " ", " after ", z8, z9);
                    sbM.append(j);
                    sbM.append("ms");
                    Log.i("KeyguardViewMediator", sbM.toString());
                    KeyguardViewMediatorHelperImplKt.isLockShownDelay = false;
                    keyguardViewMediatorHelperImpl.lockShownJob = BuildersKt.launch$default(keyguardViewMediatorHelperImpl.scope, Dispatchers.Default, null, new KeyguardViewMediatorHelperImpl$updateActivityLockScreenState$1$2(j, keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1, null), 2);
                }
            });
        }
        if (z5) {
            int i3 = SceneContainerFlag.$r8$clinit;
            KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
            DejankUtils.whitelistIpcs(new KeyguardViewMediator$$ExternalSyntheticLambda1(this, z, i));
            updateInputRestrictedLocked$1();
            if (z7) {
                Executor executor = this.mUiBgExecutor;
                TrustManager trustManager = this.mTrustManager;
                Objects.requireNonNull(trustManager);
                executor.execute(new KeyguardViewMediator$$ExternalSyntheticLambda6(trustManager, 0));
            }
        }
        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("setShowingLocked: notifyDefaultDisplayCallbacks=", " showing=", z5);
        sbM.append(this.mShowing);
        sbM.append(" aodShowing=");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, this.mAodShowing, "KeyguardViewMediator");
        this.mHelper.setShowingOptions(null);
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
        boolean zIsEnabled;
        this.mBroadcastDispatcher.registerReceiver(AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0.m("android.intent.action.ACTION_SHUTDOWN"), this.mBroadcastReceiver);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        intentFilter.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
        intentFilter.setPriority(1000);
        this.mContext.registerReceiver(this.mDelayedLockBroadcastReceiver, intentFilter, "com.android.systemui.permission.SELF", null, 2);
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        keyguardViewMediatorHelperImpl.getClass();
        boolean z2 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
        if (z2 || LsRune.KEYGUARD_SUB_DISPLAY_COVER) {
            keyguardViewMediatorHelperImpl.foldControllerImpl.handler = keyguardViewMediatorHelperImpl.getHandler$1();
        }
        if (z2) {
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
        IntentFilter intentFilterM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.samsung.internal.policy.impl.Keyguard.PCW_LOCKED", "com.samsung.internal.policy.impl.Keyguard.PCW_UNLOCKED");
        Unit unit = Unit.INSTANCE;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, keyguardViewMediatorHelperImpl$broadcastReceiver$1, intentFilterM, null, null, 0, "com.samsung.android.permission.LOCK_SECURITY_MONITOR", 28);
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
                for (int i = 0; i < intentFilter2.countActions(); i++) {
                    String action = intentFilter2.getAction(i);
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
        SemDvfsManager semDvfsManagerCreateInstance = SemDvfsManager.createInstance(keyguardViewMediatorHelperImpl.context, "KEYGUARD_UNLOCK");
        if (semDvfsManagerCreateInstance != null && semDvfsManagerCreateInstance.checkHintSupported(3100)) {
            semDvfsManagerCreateInstance.setHint(3100);
            keyguardViewMediatorHelperImpl.dvfsManager = semDvfsManagerCreateInstance;
        }
        final KeyguardFastBioUnlockController keyguardFastBioUnlockController = keyguardViewMediatorHelperImpl.fastUnlockController;
        keyguardFastBioUnlockController.getClass();
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || LsRune.SECURITY_FINGERPRINT_HOME) {
            keyguardFastBioUnlockController.wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$init$1
                @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
                public final void onPostFinishedWakingUp() {
                    KeyguardFastBioUnlockController.Companion companion = KeyguardFastBioUnlockController.Companion;
                    final KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = keyguardFastBioUnlockController;
                    if (keyguardFastBioUnlockController2.isBrightnessChangedCallbackRegistered) {
                        KeyguardFastBioUnlockController.logD("unregisterBrightnessListener");
                        ((DisplayTrackerImpl) keyguardFastBioUnlockController2.displayTracker).removeCallback(keyguardFastBioUnlockController2.brightnessChangedCallback);
                        keyguardFastBioUnlockController2.isBrightnessChangedCallbackRegistered = false;
                    }
                    if (keyguardFastBioUnlockController2.isFastWakeAndUnlockMode() && keyguardFastBioUnlockController2.needsBlankScreen && keyguardFastBioUnlockController2.curIsAodBrighterThanNormal) {
                        keyguardFastBioUnlockController2.mainHandler.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$init$1$onPostFinishedWakingUp$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardFastBioUnlockController keyguardFastBioUnlockController3 = keyguardFastBioUnlockController2;
                                KeyguardFastBioUnlockController.Companion companion2 = KeyguardFastBioUnlockController.Companion;
                                keyguardFastBioUnlockController3.getClass();
                                KeyguardFastBioUnlockController.logD("cancel blank scrim");
                                ((ScrimController) keyguardFastBioUnlockController2.scrimControllerLazy.get()).onScreenTurnedOn();
                            }
                        }, 64L);
                    }
                }

                @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
                public final void onStartedGoingToSleep() {
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = keyguardFastBioUnlockController;
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
            zIsEnabled = this.mContext.getPackageManager().getServiceInfo(new ComponentName(this.mContext, (Class<?>) KeyguardService.class), 0).isEnabled();
        } catch (PackageManager.NameNotFoundException unused) {
            zIsEnabled = true;
        }
        if (zIsEnabled) {
            if (!shouldWaitForProvisioning$1() && !this.mHelper.isKeyguardDisabled(true) && !this.mLockPatternUtils.isLockScreenDisabled(this.mSelectedUserInteractor.getSelectedUserId())) {
                z = true;
            }
            setShowingLocked("setupLocked - keyguard service enabled", z, true);
        } else {
            setShowingLocked("setupLocked - keyguard service disabled", false, true);
        }
        KeyguardTransitions keyguardTransitions = this.mKeyguardTransitions;
        IRemoteAnimationRunner exitAnimationRunner = getExitAnimationRunner();
        int i2 = KeyguardService.$r8$clinit;
        keyguardTransitions.register(new KeyguardService.AnonymousClass1(this, exitAnimationRunner), new KeyguardService.AnonymousClass1(this, new AnonymousClass18(LsRune.AOD_FULLSCREEN_APPEAR_ANIMATION ? this.mHelper.aodAppearAnimationRunner : this.mAppearAnimationRunner)), new KeyguardService.AnonymousClass1(this, getOccludeAnimationRunner()), new KeyguardService.AnonymousClass1(this, getOccludeByDreamAnimationRunner()), new KeyguardService.AnonymousClass1(this, getUnoccludeAnimationRunner()));
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
        SoundPool soundPool = this.mLockSounds;
        int i3 = this.mLockSoundId;
        int i4 = this.mUnlockSoundId;
        int i5 = this.mTrustedSoundId;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this.mHelper;
        keyguardViewMediatorHelperImpl2.getClass();
        int iLoad = soundPool.load("/system/media/audio/ui/Unlock_VA_Mode.ogg", 1);
        keyguardViewMediatorHelperImpl2.lockStaySoundId = iLoad;
        if (iLoad == 0) {
            Log.w("KeyguardViewMediator", "failed to load lock stay sound from /system/media/audio/ui/Unlock_VA_Mode.ogg");
        }
        if (LsRune.KEYGUARD_LOCK_SITUATION_VOLUME) {
            soundPool.semSetSituationType(i3, "stv_lock_screen");
            soundPool.semSetSituationType(i4, "stv_unlock_screen");
            soundPool.semSetSituationType(i5, "stv_unlock_screen");
            soundPool.semSetSituationType(keyguardViewMediatorHelperImpl2.lockStaySoundId, "stv_unlock_screen");
        }
        keyguardViewMediatorHelperImpl2.lockSounds = soundPool;
        keyguardViewMediatorHelperImpl2.unlockSoundId = i4;
        this.mHideAnimation = AnimationUtils.loadAnimation(this.mContext, android.R.anim.recents_fade_out);
        new WorkLockActivityController(this.mContext, this.mUserTracker);
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) this.mUserTracker;
        if (userTrackerImpl.isUserSwitching) {
            handleBeforeUserSwitching(userTrackerImpl.getUserId(), new KeyguardViewMediator$$ExternalSyntheticLambda70());
            handleUserSwitching(((UserTrackerImpl) this.mUserTracker).getUserId(), new KeyguardViewMediator$$ExternalSyntheticLambda70());
        }
        this.mJavaAdapter.alwaysCollectFlow(((WallpaperRepositoryImpl) this.mWallpaperRepository).wallpaperSupportsAmbientMode, new KeyguardViewMediator$$ExternalSyntheticLambda69(this, 2));
        this.mJavaAdapter.alwaysCollectFlow(this.mKeyguardInteractor.dozeTimeTick, new KeyguardViewMediator$$ExternalSyntheticLambda69(this, 3));
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
        AnonymousClass14 anonymousClass14 = this.mHandler;
        anonymousClass14.sendMessageAtFrontOfQueue(anonymousClass14.obtainMessage(1, bundle));
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        boolean z = Rune.SYSUI_MULTI_SIM;
        keyguardViewMediatorHelperImpl.enableLooperLogController(6, 4000L);
        Trace.endSection();
    }

    public void showSurfaceBehindKeyguard() {
        Log.d("KeyguardViewMediator", "showSurfaceBehindKeyguard");
        this.mSurfaceBehindRemoteAnimationRequested = true;
        ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).getClass();
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(true);
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        this.mGoingAwayRequestedForUserId = this.mSelectedUserInteractor.getSelectedUserId();
        this.mUiBgExecutor.execute(new KeyguardViewMediator$$ExternalSyntheticLambda0(this, 3));
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
        int i2;
        char c;
        String strM;
        Trace.beginSection("KeyguardViewMediator#startKeyguardExitAnimation");
        Message messageObtainMessage = this.mHandler.obtainMessage(VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, new StartKeyguardExitAnimParams(i, j, j2, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback, 0));
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        keyguardViewMediatorHelperImpl.getHandler$1().post(new KeyguardViewMediatorHelperImpl$cancelAODJankMonitor$1(keyguardViewMediatorHelperImpl));
        StartKeyguardExitAnimParams startKeyguardExitAnimParams = (StartKeyguardExitAnimParams) messageObtainMessage.obj;
        int i3 = 1;
        char c2 = 2;
        if (Debug.semIsProductDev() || LogUtil.isDebugLevelMid() || LogUtil.isDebugLevelHigh()) {
            String[] strArr = {SystemUIAnalytics.QPNE_KEY_APP, "nonApp", "wallpaper"};
            RemoteAnimationTarget[][] remoteAnimationTargetArr4 = {startKeyguardExitAnimParams.mApps, startKeyguardExitAnimParams.mNonApps, startKeyguardExitAnimParams.mWallpapers};
            int i4 = 0;
            while (i4 < 3) {
                RemoteAnimationTarget[] remoteAnimationTargetArr5 = remoteAnimationTargetArr4[i4];
                if (remoteAnimationTargetArr5 != null && remoteAnimationTargetArr5.length != 0) {
                    ArrayIterator arrayIterator = new ArrayIterator(remoteAnimationTargetArr5);
                    int i5 = -1;
                    while (arrayIterator.hasNext()) {
                        RemoteAnimationTarget remoteAnimationTarget = (RemoteAnimationTarget) arrayIterator.next();
                        i5 += i3;
                        if (remoteAnimationTarget != null) {
                            ActivityManager.RunningTaskInfo runningTaskInfo = remoteAnimationTarget.taskInfo;
                            ComponentName componentName = runningTaskInfo != null ? runningTaskInfo.topActivity : null;
                            String str = strArr[i4];
                            if (componentName != null) {
                                i2 = i3;
                                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(componentName.getPackageName(), "/", componentName.getClassName());
                            } else {
                                i2 = i3;
                                strM = SignalSeverity.NONE;
                            }
                            boolean z = remoteAnimationTarget.leash != null ? i2 : 0;
                            c = c2;
                            StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(i5, "exitAnimParam ", str, "[", "]=");
                            sbM890m.append(strM);
                            sbM890m.append(", hasLeash=");
                            sbM890m.append(z);
                            KeyguardViewMediatorHelperImpl.logD$1(sbM890m.toString());
                        } else {
                            i2 = i3;
                            c = c2;
                        }
                        i3 = i2;
                        c2 = c;
                    }
                }
                i4++;
                i3 = i3;
                c2 = c2;
            }
        }
        int i6 = i3;
        char c3 = c2;
        final CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt = (CollapsedStatusBarFragmentExt) keyguardViewMediatorHelperImpl.collapsedStatusBarFragmentExt.get();
        final RemoteAnimationTarget[][] remoteAnimationTargetArr6 = new RemoteAnimationTarget[3][];
        remoteAnimationTargetArr6[0] = startKeyguardExitAnimParams.mApps;
        remoteAnimationTargetArr6[i6] = startKeyguardExitAnimParams.mNonApps;
        remoteAnimationTargetArr6[c3] = startKeyguardExitAnimParams.mWallpapers;
        collapsedStatusBarFragmentExt.getClass();
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        collapsedStatusBarFragmentExt.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentExt$notifyAppLaunchAfterUnlock$1
            @Override // java.lang.Runnable
            public final void run() {
                ComponentName componentName2;
                try {
                    int length = remoteAnimationTargetArr6.length;
                    for (int i7 = 0; i7 < length; i7++) {
                        RemoteAnimationTarget[] remoteAnimationTargetArr7 = remoteAnimationTargetArr6[i7];
                        if (remoteAnimationTargetArr7 != null && remoteAnimationTargetArr7.length != 0) {
                            int length2 = remoteAnimationTargetArr7.length;
                            int i8 = 0;
                            while (true) {
                                if (i8 >= length2) {
                                    break;
                                }
                                ActivityManager.RunningTaskInfo runningTaskInfo2 = remoteAnimationTargetArr7[i8].taskInfo;
                                String packageName = (runningTaskInfo2 == null || (componentName2 = runningTaskInfo2.topActivity) == null) ? null : componentName2.getPackageName();
                                ref$BooleanRef.element = CollapsedStatusBarFragmentExt.access$isShouldHidePackage(collapsedStatusBarFragmentExt, packageName);
                                if (ref$BooleanRef.element) {
                                    CollapsedStatusBarFragmentExt.access$updateHideIconsForNextAppWindow(collapsedStatusBarFragmentExt, packageName);
                                    break;
                                }
                                i8++;
                            }
                            if (ref$BooleanRef.element) {
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    EmergencyButton$$ExternalSyntheticOutline0.m("notifyAppLaunchAfterUnlock() ERROR - ", e, "CollapsedStatusBarFragmentExt");
                }
            }
        });
        Handler handler$1 = keyguardViewMediatorHelperImpl.getHandler$1();
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = keyguardViewMediatorHelperImpl.fastUnlockController;
        if (keyguardFastBioUnlockController.isFastUnlockMode()) {
            keyguardFastBioUnlockController.startKeyguardExitAnimationTime = System.nanoTime();
            handler$1.sendMessageAtFrontOfQueue(messageObtainMessage);
        } else if (keyguardFastBioUnlockController.isFastWakeAndUnlockMode()) {
            keyguardFastBioUnlockController.startKeyguardExitAnimationTime = System.nanoTime();
            handler$1.sendMessage(messageObtainMessage);
        } else if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && keyguardViewMediatorHelperImpl.foldControllerImpl.isUnlockOnFoldOpened()) {
            handler$1.sendMessageAtFrontOfQueue(messageObtainMessage);
        } else {
            handler$1.sendMessage(messageObtainMessage);
        }
        Trace.endSection();
    }

    public final void tryKeyguardDone$1() {
        SemDvfsManager semDvfsManager;
        int iOrdinal;
        KeyguardUnlockInfo.UnlockTrigger unlockTrigger;
        int iOrdinal2;
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
            iOrdinal = (KeyguardUnlockInfo.authType.ordinal() * 10000) + 300000;
            int i = KeyguardUnlockInfo.WhenMappings.$EnumSwitchMapping$0[KeyguardUnlockInfo.authType.ordinal()];
            if (i == 1) {
                KeyguardSecurityModel.SecurityMode securityMode = KeyguardUnlockInfo.securityMode;
                if (securityMode != null) {
                    iOrdinal2 = securityMode.ordinal();
                    iOrdinal += iOrdinal2 * 100;
                }
            } else if (i == 2) {
                BiometricSourceType biometricSourceType = KeyguardUnlockInfo.biometricSourceType;
                if (biometricSourceType != null) {
                    iOrdinal2 = biometricSourceType.ordinal();
                    iOrdinal += iOrdinal2 * 100;
                }
            } else if (i == 3 && (skipBouncerReason = KeyguardUnlockInfo.skipBouncerReason) != null) {
                iOrdinal2 = skipBouncerReason.ordinal();
                iOrdinal += iOrdinal2 * 100;
            }
        } else {
            iOrdinal = 3;
        }
        if (iOrdinal > 3 && (unlockTrigger = KeyguardUnlockInfo.unlockTrigger) != null) {
            iOrdinal += unlockTrigger.ordinal();
        }
        int i2 = iOrdinal;
        KeyguardUnlockInfo.INSTANCE.getClass();
        int i3 = KeyguardUnlockInfo.WhenMappings.$EnumSwitchMapping$0[KeyguardUnlockInfo.authType.ordinal()];
        String string = i3 != 1 ? i3 != 2 ? i3 != 3 ? KeyguardUnlockInfo.authType.toString() : String.valueOf(KeyguardUnlockInfo.skipBouncerReason) : String.valueOf(KeyguardUnlockInfo.biometricSourceType) : String.valueOf(KeyguardUnlockInfo.securityMode);
        KeyguardUnlockInfo.leaveHistory(i2 + ": " + string + " " + KeyguardUnlockInfo.unlockTrigger, true);
        KeyguardUnlockInfo.reset();
        EventLog.writeEvent(70000, i2);
        KeyguardDumpLog.state$default(KeyguardDumpLog.INSTANCE, 3, false, false, false, i2, 0, 46);
        handleKeyguardDone$1();
    }

    public final void updateInputRestrictedLocked$1() {
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        boolean zIsInputRestricted = isInputRestricted();
        if (this.mInputRestricted != zIsInputRestricted) {
            this.mInputRestricted = zIsInputRestricted;
            for (int size = this.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
                IKeyguardStateCallback iKeyguardStateCallback = (IKeyguardStateCallback) this.mKeyguardStateCallbacks.get(size);
                try {
                    iKeyguardStateCallback.onInputRestrictedStateChanged(zIsInputRestricted);
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
        this.mUiBgExecutor.execute(new KeyguardViewMediator$$ExternalSyntheticLambda0(this, 2));
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
