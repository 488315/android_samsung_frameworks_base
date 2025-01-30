package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.WallpaperManager;
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
import android.content.res.Configuration;
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
import android.view.Choreographer;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
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
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda3;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.wm.shell.keyguard.KeyguardTransitions;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.CoreStartable;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.Rune;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardService;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.settings.DisplayTrackerImpl;
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
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.uithreadmonitor.BinderCallMonitorImpl;
import com.android.systemui.user.domain.interactor.GuestUserInteractor;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.wallpaper.BackupRestoreReceiver;
import com.samsung.android.cover.CoverState;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.os.SemDvfsManager;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.text.CharsKt__CharJVMKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardViewMediator implements CoreStartable, Dumpable, StatusBarStateController.StateListener {
    public static final Intent USER_PRESENT_INTENT = new Intent("android.intent.action.USER_PRESENT").addFlags(606076928);
    public static final Bundle USER_PRESENT_INTENT_OPTIONS = BroadcastOptions.makeBasic().setDeferralPolicy(2).setDeliveryGroupPolicy(1).toBundle();
    public final Lazy mActivityLaunchAnimator;
    public AlarmManager mAlarmManager;
    public boolean mAnimatingScreenOff;
    public boolean mAodShowing;
    public boolean mBootCompleted;
    public boolean mBootSendUserPresent;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final C147711 mBroadcastReceiver;
    public CentralSurfaces mCentralSurfaces;
    public final Context mContext;
    public CharSequence mCustomMessage;
    public final C147610 mDelayedLockBroadcastReceiver;
    public int mDelayedProfileShowingSequence;
    public int mDelayedShowingSequence;
    public boolean mDeviceInteractive;
    public final DismissCallbackRegistry mDismissCallbackRegistry;
    public final DozeParameters mDozeParameters;
    public boolean mDozing;
    public final int mDreamOpenAnimationDuration;
    public final C14832 mDreamOverlayStateCallback;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final Lazy mDreamingToLockscreenTransitionViewModel;
    public final C14876 mExitAnimationRunner;
    public final FalsingCollector mFalsingCollector;
    public final FeatureFlags mFeatureFlags;
    public boolean mGoingToSleep;
    public final HandlerC147812 mHandler;
    public final KeyguardViewMediatorHelperImpl mHelper;
    public Animation mHideAnimation;
    public final KeyguardViewMediator$$ExternalSyntheticLambda2 mHideAnimationFinishedRunnable;
    public boolean mHiding;
    public boolean mInGestureNavigationMode;
    public boolean mInputRestricted;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final KeyguardDisplayManager mKeyguardDisplayManager;
    public IRemoteAnimationRunner mKeyguardExitAnimationRunner;
    public final RunnableC147913 mKeyguardGoingAwayRunnable;
    public final ArrayList mKeyguardStateCallbacks;
    public final KeyguardStateController mKeyguardStateController;
    public final C14909 mKeyguardStateControllerCallback;
    public final KeyguardTransitions mKeyguardTransitions;
    public final Lazy mKeyguardUnlockAnimationControllerLazy;
    public final Lazy mKeyguardViewControllerLazy;
    public boolean mLockLater;
    public final LockPatternUtils mLockPatternUtils;
    public int mLockSoundId;
    public SoundPool mLockSounds;
    public final CoroutineDispatcher mMainDispatcher;
    public final Lazy mNotificationShadeDepthController;
    public final Lazy mNotificationShadeWindowControllerLazy;
    final ActivityLaunchAnimator.Controller mOccludeAnimationController;
    public final C14751 mOnPropertiesChangedListener;
    public final PowerManager mPM;
    public boolean mPendingLock;
    public boolean mPendingPinLock;
    public boolean mPendingReset;
    public final float mPowerButtonY;
    public boolean mPowerGestureIntercepted;
    public RemoteAnimationTarget mRemoteAnimationTarget;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final Lazy mScrimControllerLazy;
    public final SessionTracker mSessionTracker;
    public final Lazy mShadeController;
    public boolean mShowHomeOverLockscreen;
    public PowerManager.WakeLock mShowKeyguardWakeLock;
    public boolean mShowing;
    public boolean mShuttingDown;
    public StatusBarManager mStatusBarManager;
    public final IStatusBarService mStatusBarService;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public IRemoteAnimationFinishedCallback mSurfaceBehindRemoteAnimationFinishedCallback;
    public boolean mSurfaceBehindRemoteAnimationRequested;
    public boolean mSurfaceBehindRemoteAnimationRunning;
    public final SystemPropertiesHelper mSystemPropertiesHelper;
    public boolean mSystemReady;
    public final TrustManager mTrustManager;
    public int mTrustedSoundId;
    public final Executor mUiBgExecutor;
    public final UiEventLogger mUiEventLogger;
    public int mUnlockSoundId;
    public IRemoteAnimationFinishedCallback mUnoccludeFromDreamFinishedCallback;
    public final C14843 mUpdateCallback;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final UserSwitcherController mUserSwitcherController;
    public final UserTracker mUserTracker;
    public final C14854 mViewMediatorCallback;
    public WallpaperManager mWallpaperManager;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$12 */
    public final class HandlerC147812 extends Handler {
        public HandlerC147812(Looper looper, Handler.Callback callback, boolean z) {
            super(looper, callback, z);
        }

        /* JADX WARN: Removed duplicated region for block: B:49:0x0159  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void handleMessage(Message message) {
            boolean z;
            int i;
            KeyguardViewMediator keyguardViewMediator;
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediator.this.mHelper;
            Objects.requireNonNull(keyguardViewMediatorHelperImpl);
            KeyguardViewMediator$$ExternalSyntheticLambda6 keyguardViewMediator$$ExternalSyntheticLambda6 = new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl, 5);
            boolean z2 = Rune.SYSUI_MULTI_SIM;
            keyguardViewMediator$$ExternalSyntheticLambda6.accept(message);
            int i2 = 7;
            int i3 = 6;
            int i4 = 3;
            switch (message.what) {
                case 1:
                    KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                    Bundle bundle = (Bundle) message.obj;
                    keyguardViewMediator2.getClass();
                    Trace.beginSection("KeyguardViewMediator#handleShow");
                    int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                    if (keyguardViewMediator2.mLockPatternUtils.isSecure(currentUser)) {
                        keyguardViewMediator2.mLockPatternUtils.getDevicePolicyManager().reportKeyguardSecured(currentUser);
                    }
                    synchronized (keyguardViewMediator2) {
                        if (!keyguardViewMediator2.mSystemReady) {
                            Log.m138d("KeyguardViewMediator", "ignoring handleShow because system is not ready.");
                            break;
                        } else {
                            Log.m138d("KeyguardViewMediator", "handleShow");
                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = keyguardViewMediator2.mHelper;
                            Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                            Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda7(keyguardViewMediatorHelperImpl2, i3), true);
                            keyguardViewMediator2.mKeyguardExitAnimationRunner = null;
                            keyguardViewMediator2.mWakeAndUnlocking = false;
                            keyguardViewMediator2.setUnlockAndWakeFromDream(1, false);
                            keyguardViewMediator2.setPendingLock(false);
                            keyguardViewMediator2.mHelper.setShowingOptions(bundle);
                            keyguardViewMediator2.setShowingLocked(true, keyguardViewMediator2.mHiding);
                            if (keyguardViewMediator2.mHiding) {
                                Log.m138d("KeyguardViewMediator", "Forcing setShowingLocked because mHiding=true, which means we're showing in the middle of hiding.");
                            }
                            keyguardViewMediator2.mHiding = false;
                            ((KeyguardViewController) keyguardViewMediator2.mKeyguardViewControllerLazy.get()).show(bundle);
                            keyguardViewMediator2.resetKeyguardDonePendingLocked();
                            keyguardViewMediator2.mHideAnimationRun = false;
                            keyguardViewMediator2.adjustStatusBarLocked(false, false);
                            if (LsRune.COVER_SUPPORTED) {
                                CoverState coverState = keyguardViewMediator2.mUpdateMonitor.getCoverState();
                                if (coverState == null || !coverState.attached || coverState.getSwitchState()) {
                                    keyguardViewMediator2.userActivity();
                                }
                            } else {
                                keyguardViewMediator2.userActivity();
                            }
                            keyguardViewMediator2.mUpdateMonitor.setKeyguardGoingAway(false);
                            ((KeyguardViewController) keyguardViewMediator2.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(false);
                            keyguardViewMediator2.mShowKeyguardWakeLock.release();
                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = keyguardViewMediator2.mHelper;
                            Objects.requireNonNull(keyguardViewMediatorHelperImpl3);
                            Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda7(keyguardViewMediatorHelperImpl3, i2), true);
                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = keyguardViewMediator2.mHelper;
                            Objects.requireNonNull(keyguardViewMediatorHelperImpl4);
                            Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda7(keyguardViewMediatorHelperImpl4, 8), true);
                            if (!keyguardViewMediator2.mOccluded) {
                                keyguardViewMediator2.mKeyguardDisplayManager.show();
                            }
                            keyguardViewMediator2.scheduleNonStrongBiometricIdleTimeout();
                            Trace.endSection();
                            break;
                        }
                    }
                case 2:
                    KeyguardViewMediator.this.handleHide();
                    break;
                case 3:
                    KeyguardViewMediator.m1550$$Nest$mhandleReset(KeyguardViewMediator.this, message.arg1 != 0);
                    break;
                case 4:
                    Trace.beginSection("KeyguardViewMediator#handleMessage VERIFY_UNLOCK");
                    KeyguardViewMediator keyguardViewMediator3 = KeyguardViewMediator.this;
                    keyguardViewMediator3.getClass();
                    Trace.beginSection("KeyguardViewMediator#handleVerifyUnlock");
                    synchronized (keyguardViewMediator3) {
                        Log.m138d("KeyguardViewMediator", "handleVerifyUnlock");
                        keyguardViewMediator3.setShowingLocked(true);
                        ((KeyguardViewController) keyguardViewMediator3.mKeyguardViewControllerLazy.get()).dismissAndCollapse();
                    }
                    Trace.endSection();
                    Trace.endSection();
                    break;
                case 5:
                    KeyguardViewMediator.m1547$$Nest$mhandleNotifyFinishedGoingToSleep(KeyguardViewMediator.this);
                    break;
                case 6:
                case 15:
                case 16:
                default:
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl5);
                    new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl5, i3).accept(message);
                    break;
                case 7:
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE");
                    KeyguardViewMediator.this.handleKeyguardDone();
                    Trace.endSection();
                    break;
                case 8:
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_DRAWING");
                    KeyguardViewMediator.m1546$$Nest$mhandleKeyguardDoneDrawing(KeyguardViewMediator.this);
                    Trace.endSection();
                    break;
                case 9:
                    Trace.beginSection("KeyguardViewMediator#handleMessage SET_OCCLUDED");
                    KeyguardViewMediator keyguardViewMediator4 = KeyguardViewMediator.this;
                    boolean z3 = message.arg1 != 0;
                    r1 = message.arg2 != 0;
                    Object obj = message.obj;
                    KeyguardViewMediator.m1551$$Nest$mhandleSetOccluded(keyguardViewMediator4, z3, r1, obj != null ? ((Integer) obj).intValue() : -1);
                    Trace.endSection();
                    break;
                case 10:
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator.this.doKeyguardLocked((Bundle) message.obj, false);
                    }
                    break;
                case 11:
                    DismissMessage dismissMessage = (DismissMessage) message.obj;
                    KeyguardViewMediator keyguardViewMediator5 = KeyguardViewMediator.this;
                    IKeyguardDismissCallback iKeyguardDismissCallback = dismissMessage.mCallback;
                    CharSequence charSequence = dismissMessage.mMessage;
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl6 = keyguardViewMediator5.mHelper;
                    if (keyguardViewMediatorHelperImpl6.isShowing()) {
                        boolean z4 = LsRune.SECURITY_SIM_PERM_DISABLED;
                        KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediatorHelperImpl6.updateMonitor;
                        if (z4 && keyguardUpdateMonitor.isIccBlockedPermanently()) {
                            KeyguardViewMediatorHelperImpl.logD("dismiss failed. Permanent state.");
                            z = iKeyguardDismissCallback != null;
                            i = 1;
                        } else {
                            boolean z5 = LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY;
                            DismissCallbackRegistry dismissCallbackRegistry = keyguardViewMediatorHelperImpl6.dismissCallbackRegistry;
                            if (z5 && keyguardViewMediatorHelperImpl6.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(((UserTrackerImpl) keyguardViewMediatorHelperImpl6.userTracker).getUserId()) && !keyguardViewMediatorHelperImpl6.foldControllerImpl.isFoldOpened()) {
                                if (iKeyguardDismissCallback != null) {
                                    dismissCallbackRegistry.mDismissCallbacks.add(new DismissCallbackWrapper(iKeyguardDismissCallback));
                                    keyguardViewMediatorHelperImpl6.subScreenManager.requestCoverBouncer();
                                }
                                i4 = 2;
                            } else if (!keyguardViewMediatorHelperImpl6.isKeyguardHiding()) {
                                z = false;
                                i = 0;
                            } else if (iKeyguardDismissCallback != null) {
                                dismissCallbackRegistry.mDismissCallbacks.add(new DismissCallbackWrapper(iKeyguardDismissCallback));
                            }
                            i = i4;
                            z = false;
                        }
                        if (z) {
                            try {
                                new DismissCallbackWrapper(iKeyguardDismissCallback).mCallback.onDismissError();
                            } catch (RemoteException e) {
                                android.util.Log.i("DismissCallbackWrapper", "Failed to call callback", e);
                            }
                        }
                        if (i != 0) {
                            KeyguardViewMediatorHelperImpl.logD("handleDismiss reason=" + i);
                            if (!r1) {
                                if (!keyguardViewMediator5.mShowing) {
                                    if (iKeyguardDismissCallback != null) {
                                        try {
                                            new DismissCallbackWrapper(iKeyguardDismissCallback).mCallback.onDismissError();
                                            break;
                                        } catch (RemoteException e2) {
                                            android.util.Log.i("DismissCallbackWrapper", "Failed to call callback", e2);
                                            break;
                                        }
                                    }
                                } else {
                                    if (iKeyguardDismissCallback != null) {
                                        keyguardViewMediator5.mDismissCallbackRegistry.mDismissCallbacks.add(new DismissCallbackWrapper(iKeyguardDismissCallback));
                                    }
                                    keyguardViewMediator5.mCustomMessage = charSequence;
                                    if (!keyguardViewMediator5.mHiding) {
                                        ((KeyguardViewController) keyguardViewMediator5.mKeyguardViewControllerLazy.get()).dismissAndCollapse();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    r1 = false;
                    if (!r1) {
                    }
                    break;
                case 12:
                    Trace.beginSection("KeyguardViewMediator#handleMessage START_KEYGUARD_EXIT_ANIM");
                    synchronized (KeyguardViewMediator.this) {
                        keyguardViewMediator = KeyguardViewMediator.this;
                        keyguardViewMediator.mHiding = true;
                    }
                    ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) keyguardViewMediator.mNotificationShadeWindowControllerLazy.get())).batchApplyWindowLayoutParams(new KeyguardViewMediator$$ExternalSyntheticLambda16(i4, this, (StartKeyguardExitAnimParams) message.obj));
                    Trace.endSection();
                    break;
                case 13:
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_PENDING_TIMEOUT");
                    Log.m142w("KeyguardViewMediator", "Timeout while waiting for activity drawn!");
                    Trace.endSection();
                    break;
                case 14:
                    Trace.beginSection("KeyguardViewMediator#handleMessage NOTIFY_STARTED_WAKING_UP");
                    KeyguardViewMediator.m1549$$Nest$mhandleNotifyStartedWakingUp(KeyguardViewMediator.this);
                    Trace.endSection();
                    break;
                case 17:
                    KeyguardViewMediator.m1548$$Nest$mhandleNotifyStartedGoingToSleep(KeyguardViewMediator.this);
                    break;
                case 18:
                    KeyguardViewMediator.m1552$$Nest$mhandleSystemReady(KeyguardViewMediator.this);
                    break;
                case 19:
                    Trace.beginSection("KeyguardViewMediator#handleMessage CANCEL_KEYGUARD_EXIT_ANIM");
                    KeyguardViewMediator keyguardViewMediator6 = KeyguardViewMediator.this;
                    if (keyguardViewMediator6.mPendingLock) {
                        Log.m138d("KeyguardViewMediator", "#handleCancelKeyguardExitAnimation: keyguard exit animation cancelled. There's a pending lock, so we were cancelled because the device was locked again during the unlock sequence. We should end up locked.");
                        keyguardViewMediator6.finishSurfaceBehindRemoteAnimation();
                        keyguardViewMediator6.maybeHandlePendingLock();
                    } else {
                        Log.m138d("KeyguardViewMediator", "#handleCancelKeyguardExitAnimation: keyguard exit animation cancelled. No pending lock, we should end up unlocked with the app/launcher visible.");
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl7 = keyguardViewMediator6.mHelper;
                        Objects.requireNonNull(keyguardViewMediatorHelperImpl7);
                        if (!new KeyguardViewMediator$$ExternalSyntheticLambda3(keyguardViewMediatorHelperImpl7, i4).getAsBoolean()) {
                            keyguardViewMediator6.showSurfaceBehindKeyguard();
                            keyguardViewMediator6.exitKeyguardAndFinishSurfaceBehindRemoteAnimation(true);
                        }
                    }
                    Trace.endSection();
                    break;
            }
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl8 = KeyguardViewMediator.this.mHelper;
            Objects.requireNonNull(keyguardViewMediatorHelperImpl8);
            new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl8, i2).accept(message);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$13 */
    public final class RunnableC147913 implements Runnable {
        public RunnableC147913() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x004a, code lost:
        
            if (r0.mWallpaperSupportsAmbientMode != false) goto L14;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x0105, code lost:
        
            if ((r5.isEnabled() && r5.biometricSourceType == android.hardware.biometrics.BiometricSourceType.FACE) == false) goto L52;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x0118, code lost:
        
            r4 = r13.fixedRotationMonitor;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x011c, code lost:
        
            if (r4.isMonitorStarted == false) goto L62;
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x011f, code lost:
        
            android.util.Log.d("KeyguardFixedRotation", com.samsung.android.knox.net.nap.NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
            r4.isFixedRotated = false;
            r4.windowManager.registerDisplayWindowListener(r4.displayWindowListener);
            r4.isMonitorStarted = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x0132, code lost:
        
            r13.keyguardGoingAway(r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x0139, code lost:
        
            if (r5.isFastUnlockMode() != false) goto L69;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x013f, code lost:
        
            if (r13.isFastWakeAndUnlockMode() == false) goto L70;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x0143, code lost:
        
            if (r5.isInvisibleAfterGoingAwayTransStarted == false) goto L70;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x014e, code lost:
        
            if (r0 == false) goto L76;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x0153, code lost:
        
            if (r6.foldOpenState != 3) goto L74;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x0155, code lost:
        
            r2 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x0156, code lost:
        
            if (r2 == false) goto L76;
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x0158, code lost:
        
            ((com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl) ((com.android.systemui.shade.SecNotificationShadeWindowControllerHelper) r13.shadeWindowControllerHelper$delegate.getValue())).setForceInvisible(true);
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x0165, code lost:
        
            android.os.Trace.endSection();
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x0168, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x0145, code lost:
        
            r5.getClass();
            r5.goingAwayTime = java.lang.System.nanoTime();
         */
        /* JADX WARN: Code restructure failed: missing block: B:71:0x0116, code lost:
        
            if ((!r5.isEnabled() && r5.biometricSourceType == android.hardware.biometrics.BiometricSourceType.FINGERPRINT) == false) goto L63;
         */
        /* JADX WARN: Removed duplicated region for block: B:14:0x005e  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x0073  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00bf  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x00c7  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00e9  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x00f6  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void run() {
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl;
            boolean z;
            boolean z2;
            KeyguardSysDumpTrigger keyguardSysDumpTrigger;
            boolean isEnabledFaceStayOnLock;
            Trace.beginSection("KeyguardViewMediator.mKeyGuardGoingAwayRunnable");
            Log.m138d("KeyguardViewMediator", "keyguardGoingAway");
            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).keyguardGoingAway();
            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getClass();
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            boolean z3 = false;
            int i = (!keyguardViewMediator.mWakeAndUnlocking || keyguardViewMediator.mWallpaperSupportsAmbientMode) ? 0 : 2;
            if (!((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).isGoingToNotificationShade()) {
                KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                if (keyguardViewMediator2.mWakeAndUnlocking) {
                }
                if (((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).isUnlockWithWallpaper()) {
                    i |= 4;
                }
                ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).shouldSubtleWindowAnimationsForUnlock();
                if (KeyguardViewMediator.this.mWakeAndUnlocking) {
                    KeyguardUnlockAnimationController.Companion.getClass();
                }
                KeyguardViewMediator.this.mUpdateMonitor.setKeyguardGoingAway(true);
                ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(true);
                keyguardViewMediatorHelperImpl = KeyguardViewMediator.this.mHelper;
                z = keyguardViewMediatorHelperImpl.goingAwayWithAnimation;
                SettingsHelper settingsHelper = keyguardViewMediatorHelperImpl.settingsHelper;
                Lazy lazy = keyguardViewMediatorHelperImpl.biometricUnlockControllerLazy;
                if (z || (((BiometricUnlockController) lazy.get()).isBiometricUnlock() && !settingsHelper.isEnabledBiometricUnlockVI())) {
                    i |= 2;
                }
                if (((Boolean) keyguardViewMediatorHelperImpl.getViewMediatorProvider().isWakeAndUnlocking.invoke()).booleanValue()) {
                    i |= 256;
                }
                z2 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
                KeyguardFoldControllerImpl keyguardFoldControllerImpl = keyguardViewMediatorHelperImpl.foldControllerImpl;
                if (z2) {
                    if (((BiometricUnlockController) lazy.get()).mMode != 0) {
                        i |= 512;
                    }
                    if (keyguardFoldControllerImpl.isUnlockOnFoldOpened()) {
                        i |= 32;
                    }
                }
                int[] iArr = KeyguardSysDumpTrigger.KEY;
                keyguardSysDumpTrigger = keyguardViewMediatorHelperImpl.sysDumpTrigger;
                if (keyguardSysDumpTrigger.isEnabled()) {
                    keyguardSysDumpTrigger.start(0, 4950L, -1L);
                }
                isEnabledFaceStayOnLock = settingsHelper.isEnabledFaceStayOnLock();
                KeyguardFastBioUnlockController keyguardFastBioUnlockController = keyguardViewMediatorHelperImpl.fastUnlockController;
                if (!isEnabledFaceStayOnLock) {
                }
            }
            i |= 1;
            if (((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).isUnlockWithWallpaper()) {
            }
            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).shouldSubtleWindowAnimationsForUnlock();
            if (KeyguardViewMediator.this.mWakeAndUnlocking) {
            }
            KeyguardViewMediator.this.mUpdateMonitor.setKeyguardGoingAway(true);
            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(true);
            keyguardViewMediatorHelperImpl = KeyguardViewMediator.this.mHelper;
            z = keyguardViewMediatorHelperImpl.goingAwayWithAnimation;
            SettingsHelper settingsHelper2 = keyguardViewMediatorHelperImpl.settingsHelper;
            Lazy lazy2 = keyguardViewMediatorHelperImpl.biometricUnlockControllerLazy;
            if (z) {
            }
            i |= 2;
            if (((Boolean) keyguardViewMediatorHelperImpl.getViewMediatorProvider().isWakeAndUnlocking.invoke()).booleanValue()) {
            }
            z2 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
            KeyguardFoldControllerImpl keyguardFoldControllerImpl2 = keyguardViewMediatorHelperImpl.foldControllerImpl;
            if (z2) {
            }
            int[] iArr2 = KeyguardSysDumpTrigger.KEY;
            keyguardSysDumpTrigger = keyguardViewMediatorHelperImpl.sysDumpTrigger;
            if (keyguardSysDumpTrigger.isEnabled()) {
            }
            isEnabledFaceStayOnLock = settingsHelper2.isEnabledFaceStayOnLock();
            KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = keyguardViewMediatorHelperImpl.fastUnlockController;
            if (!isEnabledFaceStayOnLock) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$16 */
    public final class C148216 extends IRemoteAnimationRunner.Stub {
        public final /* synthetic */ IRemoteAnimationRunner val$wrapped;

        public C148216(IRemoteAnimationRunner iRemoteAnimationRunner) {
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
            Log.m142w("KeyguardViewMediator", "Skipping remote animation - view root not ready");
            if (iRemoteAnimationFinishedCallback != null) {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$4 */
    public final class C14854 implements ViewMediatorCallback {
        public C14854() {
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
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediator.mUpdateMonitor;
            keyguardUpdateMonitor.getClass();
            Assert.isMainThread();
            boolean z = keyguardUpdateMonitor.mUserTrustIsUsuallyManaged.get(currentUser);
            boolean z2 = z || keyguardUpdateMonitor.isUnlockingWithBiometricsPossible(currentUser);
            KeyguardUpdateMonitor.StrongAuthTracker strongAuthTracker = keyguardUpdateMonitor.mStrongAuthTracker;
            int strongAuthForUser = strongAuthTracker.getStrongAuthForUser(currentUser);
            boolean isNonStrongBiometricAllowedAfterIdleTimeout = strongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(currentUser);
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
            if (z && (strongAuthForUser & 4) != 0) {
                return 4;
            }
            if (z && (strongAuthForUser & 256) != 0) {
                return 8;
            }
            if (z2 && ((strongAuthForUser & 8) != 0 || keyguardUpdateMonitor.isFingerprintLockedOut())) {
                return 5;
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
            KeyguardViewMediator$$ExternalSyntheticLambda6 keyguardViewMediator$$ExternalSyntheticLambda6 = new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl, 11);
            boolean z = Rune.SYSUI_MULTI_SIM;
            keyguardViewMediator$$ExternalSyntheticLambda6.accept("keyguardDone");
            if (i != KeyguardUpdateMonitor.getCurrentUser()) {
                Log.m139d("KeyguardViewMediator", "tryKeyguardDone skipped. target=%d,cur=%d", Integer.valueOf(i), Integer.valueOf(ActivityManager.getCurrentUser()));
            } else {
                Log.m138d("KeyguardViewMediator", "keyguardDone");
                keyguardViewMediator.tryKeyguardDone();
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
            Log.m138d("KeyguardViewMediator", "keyguardDonePending");
            if (i != KeyguardUpdateMonitor.getCurrentUser()) {
                Trace.endSection();
                return;
            }
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
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
            Log.m138d("KeyguardViewMediator", "keyguardGone");
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(false);
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediator.mHelper;
            Objects.requireNonNull(keyguardViewMediatorHelperImpl);
            KeyguardViewMediator$$ExternalSyntheticLambda3 keyguardViewMediator$$ExternalSyntheticLambda3 = new KeyguardViewMediator$$ExternalSyntheticLambda3(keyguardViewMediatorHelperImpl, 5);
            boolean z = Rune.SYSUI_MULTI_SIM;
            if (keyguardViewMediator$$ExternalSyntheticLambda3.getAsBoolean()) {
                keyguardViewMediator.mKeyguardDisplayManager.hide();
            }
            keyguardViewMediator.mUpdateMonitor.startBiometricWatchdog();
            if (keyguardViewMediator.mUnlockingAndWakingFromDream) {
                Log.m138d("KeyguardViewMediator", "waking from dream after unlock");
                keyguardViewMediator.setUnlockAndWakeFromDream(2, false);
                if (((KeyguardStateControllerImpl) keyguardViewMediator.mKeyguardStateController).mShowing) {
                    Log.m138d("KeyguardViewMediator", "keyguard showing after keyguardGone, dismiss");
                    ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).notifyKeyguardAuthenticated(!keyguardViewMediator.mWakeAndUnlocking);
                } else {
                    Log.m138d("KeyguardViewMediator", "keyguard gone, waking up from dream");
                    keyguardViewMediator.mPM.wakeUp(SystemClock.uptimeMillis(), keyguardViewMediator.mWakeAndUnlocking ? 17 : 4, "com.android.systemui:UNLOCK_DREAMING");
                }
            }
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void onCancelClicked() {
            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).onCancelClicked();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void playTrustedSound() {
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            keyguardViewMediator.playSound(keyguardViewMediator.mTrustedSoundId);
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void readyForKeyguardDone() {
            Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#readyForKeyguardDone");
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            if (keyguardViewMediator.mKeyguardDonePending) {
                keyguardViewMediator.mKeyguardDonePending = false;
                keyguardViewMediator.tryKeyguardDone();
            }
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public final void resetKeyguard() {
            Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
            KeyguardViewMediator.this.resetStateLocked(true);
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
            String str = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
            KeyguardViewMediator.this.userActivity();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$7 */
    public final class C14887 extends IRemoteAnimationRunner.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public ValueAnimator mOccludeByDreamAnimator;

        public C14887() {
        }

        public final void onAnimationCancelled() {
            KeyguardViewMediator.this.mContext.getMainExecutor().execute(new KeyguardViewMediator$7$$ExternalSyntheticLambda0(this, 0));
            Log.m138d("KeyguardViewMediator", "OccludeByDreamAnimator#onAnimationCancelled. Set occluded = true");
            KeyguardViewMediator.this.setOccluded(true, false);
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
                Log.m138d("KeyguardViewMediator", "No apps provided to the OccludeByDream runner; skipping occluding animation.");
            } else {
                ActivityManager.RunningTaskInfo runningTaskInfo = remoteAnimationTarget.taskInfo;
                if (runningTaskInfo != null && runningTaskInfo.topActivityType == 5) {
                    final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
                    KeyguardViewMediator.this.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$7$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            final KeyguardViewMediator.C14887 c14887 = KeyguardViewMediator.C14887.this;
                            RemoteAnimationTarget remoteAnimationTarget2 = remoteAnimationTarget;
                            SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier2 = syncRtSurfaceTransactionApplier;
                            final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                            ValueAnimator valueAnimator = c14887.mOccludeByDreamAnimator;
                            if (valueAnimator != null) {
                                valueAnimator.cancel();
                            }
                            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                            c14887.mOccludeByDreamAnimator = ofFloat;
                            ofFloat.setDuration(KeyguardViewMediator.this.mDreamOpenAnimationDuration);
                            c14887.mOccludeByDreamAnimator.setInterpolator(Interpolators.LINEAR);
                            c14887.mOccludeByDreamAnimator.addUpdateListener(new KeyguardViewMediator$$ExternalSyntheticLambda21(remoteAnimationTarget2, syncRtSurfaceTransactionApplier2, 1));
                            c14887.mOccludeByDreamAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.7.1
                                public boolean mIsCancelled = false;

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationCancel(Animator animator) {
                                    this.mIsCancelled = true;
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    try {
                                        if (!this.mIsCancelled) {
                                            KeyguardViewMediator.m1551$$Nest$mhandleSetOccluded(KeyguardViewMediator.this, true, false, -1);
                                        }
                                        iRemoteAnimationFinishedCallback2.onAnimationFinished();
                                        C14887.this.mOccludeByDreamAnimator = null;
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            c14887.mOccludeByDreamAnimator.start();
                        }
                    });
                    z = true;
                    if (z) {
                        KeyguardViewMediator.this.setOccluded(true, false);
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        return;
                    }
                    return;
                }
                Log.m142w("KeyguardViewMediator", "The occluding app isn't Dream; finishing up. Please check that the config is correct.");
            }
            z = false;
            if (z) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$8 */
    public final class C14898 extends IRemoteAnimationRunner.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public ValueAnimator mUnoccludeAnimator;
        public final Matrix mUnoccludeMatrix = new Matrix();

        public C14898() {
        }

        public final void onAnimationCancelled() {
            KeyguardViewMediator.this.mContext.getMainExecutor().execute(new KeyguardViewMediator$7$$ExternalSyntheticLambda0(this, 1));
            Log.m138d("KeyguardViewMediator", "Unocclude animation cancelled.");
            KeyguardViewMediator.this.mInteractionJankMonitor.cancel(64);
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, final RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            RemoteAnimationTarget remoteAnimationTarget;
            Log.m138d("KeyguardViewMediator", "UnoccludeAnimator#onAnimationStart. Set occluded = false.");
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            keyguardViewMediator.mInteractionJankMonitor.begin(keyguardViewMediator.createInteractionJankMonitorConf(64, null).setTag("UNOCCLUDE"));
            KeyguardViewMediator.this.setOccluded(false, true);
            if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0 || (remoteAnimationTarget = remoteAnimationTargetArr[0]) == null) {
                Log.m138d("KeyguardViewMediator", "No apps provided to unocclude runner; skipping animation and unoccluding.");
                iRemoteAnimationFinishedCallback.onAnimationFinished();
                return;
            }
            KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
            keyguardViewMediator2.mRemoteAnimationTarget = remoteAnimationTarget;
            ActivityManager.RunningTaskInfo runningTaskInfo = remoteAnimationTarget.taskInfo;
            final boolean z = runningTaskInfo != null && runningTaskInfo.topActivityType == 5;
            final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(((KeyguardViewController) keyguardViewMediator2.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
            KeyguardViewMediator.this.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    final KeyguardViewMediator.C14898 c14898 = KeyguardViewMediator.C14898.this;
                    boolean z2 = z;
                    RemoteAnimationTarget[] remoteAnimationTargetArr4 = remoteAnimationTargetArr2;
                    final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                    SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier2 = syncRtSurfaceTransactionApplier;
                    ValueAnimator valueAnimator = c14898.mUnoccludeAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.cancel();
                    }
                    if (!z2) {
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
                        c14898.mUnoccludeAnimator = ofFloat;
                        ofFloat.setDuration(250L);
                        c14898.mUnoccludeAnimator.setInterpolator(Interpolators.TOUCH_RESPONSE);
                        c14898.mUnoccludeAnimator.addUpdateListener(new KeyguardViewMediator$$ExternalSyntheticLambda21(c14898, syncRtSurfaceTransactionApplier2, 2));
                        c14898.mUnoccludeAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.8.1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                try {
                                    iRemoteAnimationFinishedCallback2.onAnimationFinished();
                                    C14898 c148982 = C14898.this;
                                    c148982.mUnoccludeAnimator = null;
                                    KeyguardViewMediator.this.mInteractionJankMonitor.end(64);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        c14898.mUnoccludeAnimator.start();
                        return;
                    }
                    Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                    for (RemoteAnimationTarget remoteAnimationTarget2 : remoteAnimationTargetArr4) {
                        if (remoteAnimationTarget2.mode == 0) {
                            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                            try {
                                transaction.setAlpha(remoteAnimationTarget2.leash, 1.0f);
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
                    KeyguardViewMediator keyguardViewMediator3 = KeyguardViewMediator.this;
                    keyguardViewMediator3.getClass();
                    new KeyguardViewMediator$$ExternalSyntheticLambda17(keyguardViewMediator3, 1).accept(Float.valueOf(0.0f));
                    ((DreamingToLockscreenTransitionViewModel) KeyguardViewMediator.this.mDreamingToLockscreenTransitionViewModel.get()).fromDreamingTransitionInteractor.startToLockscreenTransition();
                    KeyguardViewMediator.this.mUnoccludeFromDreamFinishedCallback = iRemoteAnimationFinishedCallback2;
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class ActivityLaunchRemoteAnimationRunner extends IRemoteAnimationRunner.Stub {
        public final ActivityLaunchAnimator.Controller mActivityLaunchController;
        public ActivityLaunchAnimator.Runner mRunner;

        public ActivityLaunchRemoteAnimationRunner(ActivityLaunchAnimator.Controller controller) {
            this.mActivityLaunchController = controller;
        }

        public void onAnimationCancelled() {
            ActivityLaunchAnimator.Runner runner = this.mRunner;
            if (runner != null) {
                runner.onAnimationCancelled();
            }
        }

        public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            ActivityLaunchAnimator.Runner createRunner = ((ActivityLaunchAnimator) KeyguardViewMediator.this.mActivityLaunchAnimator.get()).createRunner(this.mActivityLaunchController);
            this.mRunner = createRunner;
            createRunner.onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
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
            super(controller);
        }

        @Override // com.android.systemui.keyguard.KeyguardViewMediator.ActivityLaunchRemoteAnimationRunner
        public final void onAnimationCancelled() {
            super.onAnimationCancelled();
            Log.m138d("KeyguardViewMediator", "Occlude animation cancelled by WM.");
            KeyguardViewMediator.this.mInteractionJankMonitor.cancel(64);
        }

        @Override // com.android.systemui.keyguard.KeyguardViewMediator.ActivityLaunchRemoteAnimationRunner
        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            super.onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
            keyguardViewMediator.mInteractionJankMonitor.begin(keyguardViewMediator.createInteractionJankMonitorConf(64, null).setTag("OCCLUDE"));
            Log.m138d("KeyguardViewMediator", "OccludeAnimator#onAnimationStart. Set occluded = true.");
            KeyguardViewMediator.this.setOccluded(true, false);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public static void m1546$$Nest$mhandleKeyguardDoneDrawing(KeyguardViewMediator keyguardViewMediator) {
        keyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDoneDrawing");
        synchronized (keyguardViewMediator) {
            Log.m138d("KeyguardViewMediator", "handleKeyguardDoneDrawing");
            if (keyguardViewMediator.mWaitingUntilKeyguardVisible) {
                Log.m138d("KeyguardViewMediator", "handleKeyguardDoneDrawing: notifying mWaitingUntilKeyguardVisible");
                keyguardViewMediator.mWaitingUntilKeyguardVisible = false;
                keyguardViewMediator.notifyAll();
                keyguardViewMediator.mHandler.removeMessages(8);
            }
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mhandleNotifyFinishedGoingToSleep, reason: not valid java name */
    public static void m1547$$Nest$mhandleNotifyFinishedGoingToSleep(KeyguardViewMediator keyguardViewMediator) {
        synchronized (keyguardViewMediator) {
            Log.m138d("KeyguardViewMediator", "handleNotifyFinishedGoingToSleep");
            ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).onFinishedGoingToSleep();
        }
    }

    /* renamed from: -$$Nest$mhandleNotifyStartedGoingToSleep, reason: not valid java name */
    public static void m1548$$Nest$mhandleNotifyStartedGoingToSleep(KeyguardViewMediator keyguardViewMediator) {
        synchronized (keyguardViewMediator) {
            Log.m138d("KeyguardViewMediator", "handleNotifyStartedGoingToSleep");
            ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).onStartedGoingToSleep();
        }
    }

    /* renamed from: -$$Nest$mhandleNotifyStartedWakingUp, reason: not valid java name */
    public static void m1549$$Nest$mhandleNotifyStartedWakingUp(KeyguardViewMediator keyguardViewMediator) {
        keyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleMotifyStartedWakingUp");
        synchronized (keyguardViewMediator) {
            Log.m138d("KeyguardViewMediator", "handleNotifyWakingUp");
            ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).onStartedWakingUp();
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mhandleReset, reason: not valid java name */
    public static void m1550$$Nest$mhandleReset(KeyguardViewMediator keyguardViewMediator, boolean z) {
        synchronized (keyguardViewMediator) {
            if (keyguardViewMediator.mHideAnimationRun) {
                Log.m138d("KeyguardViewMediator", "handleReset : hideBouncer=false");
                z = false;
            } else {
                Log.m138d("KeyguardViewMediator", "handleReset");
            }
            ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).reset(z);
        }
        keyguardViewMediator.scheduleNonStrongBiometricIdleTimeout();
    }

    /* renamed from: -$$Nest$mhandleSetOccluded, reason: not valid java name */
    public static void m1551$$Nest$mhandleSetOccluded(KeyguardViewMediator keyguardViewMediator, boolean z, boolean z2, int i) {
        keyguardViewMediator.getClass();
        Trace.beginSection("KeyguardViewMediator#handleSetOccluded");
        Log.m139d("KeyguardViewMediator", "handleSetOccluded(%b) seq=%d", Boolean.valueOf(z), Integer.valueOf(i));
        EventLog.writeEvent(36080, Integer.valueOf(z ? 1 : 0), Integer.valueOf(z2 ? 1 : 0));
        keyguardViewMediator.mInteractionJankMonitor.cancel(23);
        synchronized (keyguardViewMediator) {
            if (keyguardViewMediator.mHiding && z && !keyguardViewMediator.mHandler.hasMessages(VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI)) {
                keyguardViewMediator.startKeyguardExitAnimation(0L, 0L);
            }
            int i2 = 1;
            keyguardViewMediator.mPowerGestureIntercepted = z && keyguardViewMediator.mUpdateMonitor.mSecureCameraLaunched;
            if (keyguardViewMediator.mOccluded != z) {
                keyguardViewMediator.mOccluded = z;
                ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).setOccluded(z, z2 && keyguardViewMediator.mDeviceInteractive);
                keyguardViewMediator.adjustStatusBarLocked(false, false);
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediator.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                KeyguardViewMediator$$ExternalSyntheticLambda11 keyguardViewMediator$$ExternalSyntheticLambda11 = new KeyguardViewMediator$$ExternalSyntheticLambda11(keyguardViewMediatorHelperImpl, i2);
                boolean z3 = keyguardViewMediator.mShowing;
                boolean z4 = Rune.SYSUI_MULTI_SIM;
                keyguardViewMediator$$ExternalSyntheticLambda11.accept(Boolean.valueOf(z), Boolean.valueOf(z3));
            }
            Log.m138d("KeyguardViewMediator", "isOccluded=" + z + ",mPowerGestureIntercepted=" + keyguardViewMediator.mPowerGestureIntercepted);
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mhandleSystemReady, reason: not valid java name */
    public static void m1552$$Nest$mhandleSystemReady(KeyguardViewMediator keyguardViewMediator) {
        synchronized (keyguardViewMediator) {
            try {
                Log.m138d("KeyguardViewMediator", "onSystemReady");
                int i = 1;
                keyguardViewMediator.mSystemReady = true;
                int i2 = 0;
                keyguardViewMediator.doKeyguardLocked(null, false);
                keyguardViewMediator.mUpdateMonitor.registerCallback(keyguardViewMediator.mUpdateCallback);
                keyguardViewMediator.adjustStatusBarLocked(false, false);
                keyguardViewMediator.mDreamOverlayStateController.addCallback((DreamOverlayStateController.Callback) keyguardViewMediator.mDreamOverlayStateCallback);
                ViewRootImpl viewRootImpl = ((KeyguardViewController) keyguardViewMediator.mKeyguardViewControllerLazy.get()).getViewRootImpl();
                if (viewRootImpl != null) {
                    JavaAdapterKt.collectFlow(viewRootImpl.getView(), ((DreamingToLockscreenTransitionViewModel) keyguardViewMediator.mDreamingToLockscreenTransitionViewModel.get()).dreamOverlayAlpha, new KeyguardViewMediator$$ExternalSyntheticLambda17(keyguardViewMediator, i), keyguardViewMediator.mMainDispatcher);
                    JavaAdapterKt.collectFlow(viewRootImpl.getView(), ((DreamingToLockscreenTransitionViewModel) keyguardViewMediator.mDreamingToLockscreenTransitionViewModel.get()).transitionEnded, new KeyguardViewMediator$$ExternalSyntheticLambda17(keyguardViewMediator, i2), keyguardViewMediator.mMainDispatcher);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        keyguardViewMediator.maybeSendUserPresentBroadcast();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [android.provider.DeviceConfig$OnPropertiesChangedListener, com.android.systemui.keyguard.KeyguardViewMediator$1] */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.android.systemui.keyguard.KeyguardViewMediator$2] */
    /* JADX WARN: Type inference failed for: r8v1, types: [com.android.systemui.keyguard.KeyguardViewMediator$3] */
    /* JADX WARN: Type inference failed for: r8v6, types: [com.android.systemui.keyguard.KeyguardViewMediator$9, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.keyguard.KeyguardViewMediator$6] */
    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.keyguard.KeyguardViewMediator$10] */
    /* JADX WARN: Type inference failed for: r9v3, types: [com.android.systemui.keyguard.KeyguardViewMediator$11] */
    public KeyguardViewMediator(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, Context context, UiEventLogger uiEventLogger, SessionTracker sessionTracker, UserTracker userTracker, FalsingCollector falsingCollector, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, Lazy lazy, DismissCallbackRegistry dismissCallbackRegistry, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, Executor executor, PowerManager powerManager, TrustManager trustManager, UserSwitcherController userSwitcherController, DeviceConfigProxy deviceConfigProxy, NavigationModeController navigationModeController, KeyguardDisplayManager keyguardDisplayManager, DozeParameters dozeParameters, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController, Lazy lazy2, ScreenOffAnimationController screenOffAnimationController, Lazy lazy3, ScreenOnCoordinator screenOnCoordinator, KeyguardTransitions keyguardTransitions, InteractionJankMonitor interactionJankMonitor, DreamOverlayStateController dreamOverlayStateController, Lazy lazy4, Lazy lazy5, Lazy lazy6, Lazy lazy7, FeatureFlags featureFlags, CoroutineDispatcher coroutineDispatcher, Lazy lazy8, SystemPropertiesHelper systemPropertiesHelper) {
        final int i = 0;
        final ArrayList arrayList = new ArrayList();
        this.mKeyguardStateCallbacks = arrayList;
        this.mPendingPinLock = false;
        this.mPowerGestureIntercepted = false;
        this.mSurfaceBehindRemoteAnimationRequested = false;
        ?? r7 = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("nav_bar_handle_show_over_lockscreen")) {
                    KeyguardViewMediator.this.mShowHomeOverLockscreen = properties.getBoolean("nav_bar_handle_show_over_lockscreen", true);
                }
            }
        };
        this.mOnPropertiesChangedListener = r7;
        this.mDreamOverlayStateCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.2
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onStateChanged() {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                keyguardViewMediator.mDreamOverlayStateController.isOverlayActive();
                keyguardViewMediator.getClass();
            }
        };
        this.mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.3
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                if (keyguardViewMediator.mLockPatternUtils.isSecure(currentUser)) {
                    keyguardViewMediator.mLockPatternUtils.getDevicePolicyManager().reportFailedBiometricAttempt(currentUser);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i2, BiometricSourceType biometricSourceType, boolean z) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                if (keyguardViewMediator.mLockPatternUtils.isSecure(i2)) {
                    keyguardViewMediator.mLockPatternUtils.getDevicePolicyManager().reportSuccessfulBiometricAttempt(i2);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDeviceProvisioned() {
                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                KeyguardViewMediator.this.sendUserPresentBroadcast();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                synchronized (KeyguardViewMediator.this) {
                    if (!z) {
                        if (KeyguardViewMediator.this.mPendingPinLock) {
                            Log.m141i("KeyguardViewMediator", "PIN lock requested, starting keyguard");
                            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                            keyguardViewMediator.mPendingPinLock = false;
                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = keyguardViewMediator.mHelper;
                            Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                            KeyguardViewMediator$$ExternalSyntheticLambda3 keyguardViewMediator$$ExternalSyntheticLambda3 = new KeyguardViewMediator$$ExternalSyntheticLambda3(keyguardViewMediatorHelperImpl2, 4);
                            boolean z2 = Rune.SYSUI_MULTI_SIM;
                            if (!keyguardViewMediator$$ExternalSyntheticLambda3.getAsBoolean()) {
                                KeyguardViewMediator.this.doKeyguardLocked(null, false);
                            }
                        }
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i2, int i3, int i4) {
                boolean z;
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onSimStateChanged(subId=", i2, ", slotId=", i3, ",state=");
                m45m.append(i4);
                m45m.append(")");
                Log.m138d("KeyguardViewMediator", m45m.toString());
                int size = KeyguardViewMediator.this.mKeyguardStateCallbacks.size();
                boolean isSimPinSecure = KeyguardViewMediator.this.mUpdateMonitor.isSimPinSecure();
                for (int i5 = size - 1; i5 >= 0; i5--) {
                    try {
                        ((IKeyguardStateCallback) KeyguardViewMediator.this.mKeyguardStateCallbacks.get(i5)).onSimSecureStateChanged(isSimPinSecure);
                    } catch (RemoteException e) {
                        Slog.m144w("Failed to call onSimSecureStateChanged", e);
                        if (e instanceof DeadObjectException) {
                            KeyguardViewMediator.this.mKeyguardStateCallbacks.remove(i5);
                        }
                    }
                }
                if (LsRune.SECURITY_ESIM && KeyguardViewMediator.this.mUpdateMonitor.isESimRemoveButtonClicked()) {
                    z = false;
                } else {
                    synchronized (KeyguardViewMediator.this) {
                        int i6 = KeyguardViewMediator.this.mLastSimStates.get(i3);
                        if (i6 != 2 && i6 != 3 && (!LsRune.SECURITY_SIM_PERSO_LOCK || i6 != 12)) {
                            z = false;
                            KeyguardViewMediator.this.mLastSimStates.append(i3, i4);
                        }
                        z = true;
                        KeyguardViewMediator.this.mLastSimStates.append(i3, i4);
                    }
                }
                if (i4 != 0 && i4 != 1) {
                    if (i4 == 2 || i4 == 3) {
                        synchronized (KeyguardViewMediator.this) {
                            if (!SubscriptionManager.isValidSubscriptionId(i2)) {
                                Log.m138d("KeyguardViewMediator", "Skip invalid subId SIM lock request!");
                                return;
                            }
                            KeyguardViewMediator.this.mSimWasLocked.append(i3, true);
                            KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                            if (keyguardViewMediator.mShowing) {
                                keyguardViewMediator.mHelper.removeMessage(7);
                                KeyguardViewMediator.this.mHelper.removeMessage(2);
                                KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                                if (keyguardViewMediator2.mHiding) {
                                    keyguardViewMediator2.mHiding = false;
                                }
                                if (keyguardViewMediator2.mSurfaceBehindRemoteAnimationRunning || ((KeyguardStateControllerImpl) keyguardViewMediator2.mKeyguardStateController).mKeyguardGoingAway) {
                                    Log.m138d("KeyguardViewMediator", "PendingPinLock : set true");
                                    KeyguardViewMediator.this.mPendingPinLock = true;
                                }
                                KeyguardViewMediator.this.resetStateLocked(true);
                            } else {
                                Log.m138d("KeyguardViewMediator", "INTENT_VALUE_ICC_LOCKED and keygaurd isn't showing; need to show keyguard so user can enter sim pin");
                                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediator.this.mHelper;
                                Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                                Rune.runIf(2, new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl2, 8));
                                KeyguardViewMediator.this.doKeyguardLocked(null, false);
                            }
                            return;
                        }
                    }
                    if (i4 == 5) {
                        synchronized (KeyguardViewMediator.this) {
                            Log.m138d("KeyguardViewMediator", "READY, reset state? " + KeyguardViewMediator.this.mShowing);
                            KeyguardViewMediator keyguardViewMediator3 = KeyguardViewMediator.this;
                            if (keyguardViewMediator3.mShowing && keyguardViewMediator3.mSimWasLocked.get(i3, false)) {
                                Log.m138d("KeyguardViewMediator", "SIM moved to READY when the previously was locked. Reset the state.");
                                KeyguardViewMediator.this.mSimWasLocked.append(i3, false);
                                KeyguardViewMediator.this.resetStateLocked(true);
                            }
                        }
                        return;
                    }
                    if (i4 != 6) {
                        if (i4 == 7) {
                            synchronized (KeyguardViewMediator.this) {
                                if (KeyguardViewMediator.this.mShowing) {
                                    Log.m138d("KeyguardViewMediator", "PERM_DISABLED, resetStateLocked toshow permanently disabled message in lockscreen.");
                                    if (KeyguardViewMediator.this.shouldWaitForProvisioning()) {
                                        KeyguardViewMediator.this.tryKeyguardDone();
                                    } else {
                                        KeyguardViewMediator.this.resetStateLocked(true);
                                    }
                                } else {
                                    Log.m138d("KeyguardViewMediator", "PERM_DISABLED and keygaurd isn't showing.");
                                    KeyguardViewMediator.this.doKeyguardLocked(null, false);
                                }
                            }
                            return;
                        }
                        if (i4 != 12) {
                            KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.VERBOSE, AbstractC0000x2c234b15.m0m("Unspecific state: ", i4), null);
                            return;
                        }
                        if (LsRune.SECURITY_SIM_PERSO_LOCK) {
                            synchronized (KeyguardViewMediator.this) {
                                KeyguardViewMediator.this.mSimWasLocked.append(i3, true);
                                if (KeyguardViewMediator.this.mShowing) {
                                    Log.m138d("KeyguardViewMediator", "send the handler LAUNCH_PERSO_LOCK");
                                    KeyguardViewMediator.this.mHandler.sendEmptyMessageDelayed(VolteConstants.ErrorCode.CALL_SESSION_TERMINATED, 500L);
                                } else {
                                    Log.m138d("KeyguardViewMediator", "INTENT_VALUE_ICC_LOCKED and keyguard isn't showing; need to show keyguard so user can enter sim perso");
                                    if (SubscriptionManager.isValidSubscriptionId(KeyguardViewMediator.this.mUpdateMonitor.getNextSubIdForState(12))) {
                                        KeyguardViewMediator.this.doKeyguardLocked(null, false);
                                    }
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
                    if (KeyguardViewMediator.this.shouldWaitForProvisioning()) {
                        KeyguardViewMediator keyguardViewMediator5 = KeyguardViewMediator.this;
                        if (keyguardViewMediator5.mShowing) {
                            keyguardViewMediator5.tryKeyguardDone();
                        } else {
                            Log.m138d("KeyguardViewMediator", "ICC_ABSENT isn't showing, we need to show the keyguard since the device isn't provisioned yet.");
                            KeyguardViewMediator.this.doKeyguardLocked(null, false);
                        }
                    } else if (KeyguardViewMediator.this.mShowing && i4 == 0 && SubscriptionManager.isValidSubscriptionId(i2)) {
                        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (defaultAdapter != null) {
                            int profileConnectionState = defaultAdapter.getProfileConnectionState(10);
                            Log.m138d("KeyguardViewMediator", "SAP status : " + profileConnectionState);
                            if (profileConnectionState == 2) {
                                Log.m138d("KeyguardViewMediator", "SAPConnectRequested : resetState");
                                KeyguardViewMediator.this.resetStateLocked(true);
                            }
                        } else {
                            Log.m138d("KeyguardViewMediator", "SAP status : BluetoothAdapter is null");
                        }
                    }
                    if (i4 == 1) {
                        if (z) {
                            Log.m138d("KeyguardViewMediator", "SIM moved to ABSENT when the previous state was locked. Reset the state.");
                            KeyguardViewMediator.this.resetStateLocked(true);
                        }
                        KeyguardViewMediator.this.mSimWasLocked.append(i3, false);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStrongAuthStateChanged(int i2) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                if (keyguardViewMediator.mUpdateMonitor.isUserInLockdown(KeyguardUpdateMonitor.getCurrentUser())) {
                    keyguardViewMediator.doKeyguardLocked(null, false);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTrustChanged(int i2) {
                if (i2 == KeyguardUpdateMonitor.getCurrentUser()) {
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                        keyguardViewMediator.notifyTrustedChangedLocked(keyguardViewMediator.mUpdateMonitor.getUserHasTrust(i2));
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i2) {
                UserInfo userInfo;
                Log.m138d("KeyguardViewMediator", String.format("onUserSwitchComplete %d", Integer.valueOf(i2)));
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = keyguardViewMediator.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                Rune.runIf(i2, new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl2, 9));
                if (i2 == 0 || (userInfo = UserManager.get(keyguardViewMediator.mContext).getUserInfo(i2)) == null || keyguardViewMediator.mLockPatternUtils.isSecure(i2)) {
                    return;
                }
                if (userInfo.isGuest() || userInfo.isDemo()) {
                    keyguardViewMediator.dismiss(null, null);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitching(int i2) {
                Log.m138d("KeyguardViewMediator", String.format("onUserSwitching %d", Integer.valueOf(i2)));
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediator.this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                Rune.runIf(i2, new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl2, 10));
                synchronized (KeyguardViewMediator.this) {
                    KeyguardViewMediator.this.resetKeyguardDonePendingLocked();
                    if (KeyguardViewMediator.this.mLockPatternUtils.isLockScreenDisabled(i2)) {
                        KeyguardViewMediator.this.dismiss(null, null);
                    } else {
                        KeyguardViewMediator.this.resetStateLocked(true);
                    }
                    KeyguardViewMediator.this.adjustStatusBarLocked(false, false);
                }
            }
        };
        this.mViewMediatorCallback = new C14854();
        ActivityLaunchAnimator.Controller controller = new ActivityLaunchAnimator.Controller() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.5
            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final LaunchAnimator.State createAnimatorState() {
                int width = getLaunchContainer().getWidth();
                int height = getLaunchContainer().getHeight();
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                if (keyguardViewMediator.mUpdateMonitor.mSecureCameraLaunched) {
                    float f = width;
                    float f2 = keyguardViewMediator.mPowerButtonY;
                    float f3 = (height / 3.0f) / 2.0f;
                    float f4 = keyguardViewMediator.mWindowCornerRadius;
                    return new LaunchAnimator.State((int) (f2 - f3), (int) (f2 + f3), (int) (f - (f / 3.0f)), width, f4, f4);
                }
                float f5 = height;
                float f6 = f5 / 2.0f;
                float f7 = width;
                float f8 = f7 / 2.0f;
                float f9 = f5 - f6;
                float f10 = f7 - f8;
                float f11 = keyguardViewMediator.mWindowCornerRadius;
                return new LaunchAnimator.State(((int) f9) / 2, (int) ((f9 / 2.0f) + f6), ((int) f10) / 2, (int) ((f10 / 2.0f) + f8), f11, f11);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final ViewGroup getLaunchContainer() {
                return (ViewGroup) ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView();
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
            public final void onLaunchAnimationCancelled(Boolean bool) {
                StringBuilder sb = new StringBuilder("Occlude launch animation cancelled. Occluded state is now: ");
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                sb.append(keyguardViewMediator.mOccluded);
                Log.m138d("KeyguardViewMediator", sb.toString());
                keyguardViewMediator.mOccludeAnimationPlaying = false;
                ((CentralSurfacesImpl) keyguardViewMediator.mCentralSurfaces).updateIsKeyguard();
                ((ScrimController) keyguardViewMediator.mScrimControllerLazy.get()).setOccludeAnimationPlaying(false);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void onLaunchAnimationEnd(boolean z) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                if (z) {
                    ((ShadeControllerImpl) ((ShadeController) keyguardViewMediator.mShadeController.get())).instantCollapseShade();
                }
                keyguardViewMediator.mOccludeAnimationPlaying = false;
                ((CentralSurfacesImpl) keyguardViewMediator.mCentralSurfaces).updateIsKeyguard();
                ((ScrimController) keyguardViewMediator.mScrimControllerLazy.get()).setOccludeAnimationPlaying(false);
                keyguardViewMediator.mInteractionJankMonitor.end(64);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void onLaunchAnimationStart(boolean z) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                keyguardViewMediator.mOccludeAnimationPlaying = true;
                ((ScrimController) keyguardViewMediator.mScrimControllerLazy.get()).setOccludeAnimationPlaying(true);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public final void setLaunchContainer(ViewGroup viewGroup) {
                android.util.Log.wtf("KeyguardViewMediator", "Someone tried to change the launch container for the ActivityLaunchAnimator, which should never happen.");
                KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.WTF, "Someone tried to change the launch container for the ActivityLaunchAnimator, which should never happen.", null);
            }
        };
        this.mOccludeAnimationController = controller;
        this.mExitAnimationRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.6
            public final void onAnimationCancelled() {
                KeyguardViewMediator.this.cancelKeyguardExitAnimation();
            }

            public final void onAnimationStart(int i2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                Trace.beginSection("mExitAnimationRunner.onAnimationStart#startKeyguardExitAnimation");
                KeyguardViewMediator.this.startKeyguardExitAnimation(i2, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
                Trace.endSection();
            }
        };
        new OccludeActivityLaunchRemoteAnimationRunner(controller);
        new C14887();
        new C14898();
        ?? r8 = new KeyguardStateController.Callback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.9
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onPrimaryBouncerShowingChanged() {
                synchronized (KeyguardViewMediator.this) {
                    KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                    KeyguardStateController keyguardStateController2 = keyguardViewMediator.mKeyguardStateController;
                    if (((KeyguardStateControllerImpl) keyguardStateController2).mPrimaryBouncerShowing && !((KeyguardStateControllerImpl) keyguardStateController2).mKeyguardGoingAway) {
                        keyguardViewMediator.mPendingPinLock = false;
                    }
                    keyguardViewMediator.adjustStatusBarLocked(((KeyguardStateControllerImpl) keyguardStateController2).mPrimaryBouncerShowing, false);
                }
            }
        };
        this.mKeyguardStateControllerCallback = r8;
        this.mDelayedLockBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.10
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
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("received DELAYED_KEYGUARD_ACTION with seq = ", intExtra3, ", mDelayedShowingSequence = ");
                m1m.append(KeyguardViewMediator.this.mDelayedShowingSequence);
                Log.m138d("KeyguardViewMediator", m1m.toString());
                synchronized (KeyguardViewMediator.this) {
                    boolean z = LsRune.COVER_SUPPORTED;
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediator.this.mHelper;
                    Objects.requireNonNull(keyguardViewMediatorHelperImpl2);
                    Rune.runIf(new KeyguardViewMediator$$ExternalSyntheticLambda7(keyguardViewMediatorHelperImpl2, 11), z);
                    KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                    if (keyguardViewMediator2.mDelayedShowingSequence == intExtra3) {
                        keyguardViewMediator2.doKeyguardLocked(null, false);
                    }
                }
            }
        };
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.11
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator.this.mShuttingDown = true;
                    }
                }
            }
        };
        final HandlerC147812 handlerC147812 = new HandlerC147812(Looper.myLooper(), null, true);
        this.mHandler = handlerC147812;
        this.mKeyguardGoingAwayRunnable = new RunnableC147913();
        this.mHideAnimationFinishedRunnable = new KeyguardViewMediator$$ExternalSyntheticLambda2(this, 1);
        Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i2 = 7;
        Function0 function02 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i3 = 9;
        Function0 function03 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i4 = 10;
        Function0 function04 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i5 = 11;
        Function0 function05 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i5) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i6 = 12;
        Function0 function06 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i6) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i7 = 13;
        Function0 function07 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i7) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i8 = 14;
        Function0 function08 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i8) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i9 = 15;
        Function0 function09 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i9) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i10 = 16;
        Function0 function010 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i10) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i11 = 1;
        Function0 function011 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i11) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i12 = 2;
        Function0 function012 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i12) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i13 = 3;
        Function0 function013 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i13) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i14 = 4;
        Function0 function014 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i14) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i15 = 5;
        Function0 function015 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i15) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i16 = 6;
        Function0 function016 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i16) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i17 = 0;
        Function0 function017 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i18 = i17;
                Object obj = this;
                switch (i18) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i18 = 1;
        Function0 function018 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i18;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i19 = 2;
        Function0 function019 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i19;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i20 = 3;
        Function0 function020 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i20;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i21 = 8;
        Function0 function021 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i21) {
                    case 0:
                        return 1;
                    case 1:
                        return 12;
                    case 2:
                        return 13;
                    case 3:
                        return 14;
                    case 4:
                        return 17;
                    case 5:
                        return 18;
                    case 6:
                        return 19;
                    case 7:
                        return 2;
                    case 8:
                        return KeyguardViewMediator.USER_PRESENT_INTENT;
                    case 9:
                        return 3;
                    case 10:
                        return 4;
                    case 11:
                        return 5;
                    case 12:
                        return 7;
                    case 13:
                        return 8;
                    case 14:
                        return 9;
                    case 15:
                        return 10;
                    default:
                        return 11;
                }
            }
        };
        final int i22 = 4;
        Function0 function022 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i22;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i23 = 5;
        Function0 function023 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i23;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i24 = 6;
        Function0 function024 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i24;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i25 = 7;
        Function0 function025 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i25;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i26 = 8;
        Function0 function026 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i26;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i27 = 9;
        Function0 function027 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i27;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i28 = 10;
        Function0 function028 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i28;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i29 = 11;
        Function0 function029 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i29;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i30 = 12;
        Function0 function030 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i30;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i31 = 13;
        Function0 function031 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i31;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i32 = 14;
        Function0 function032 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i32;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i33 = 15;
        Function0 function033 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i33;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i34 = 16;
        Function0 function034 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i34;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i35 = 17;
        Function0 function035 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i35;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i36 = 18;
        Function0 function036 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i36;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i37 = 19;
        Function0 function037 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i37;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i38 = 20;
        Function0 function038 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i38;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        Function2 function2 = new Function2() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                keyguardViewMediator.getClass();
                new KeyguardViewMediator$$ExternalSyntheticLambda18(keyguardViewMediator, (Boolean) obj, (Boolean) obj2, 0).run();
                return Unit.INSTANCE;
            }
        };
        final int i39 = 0;
        Function1 function1 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda15
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i40 = i39;
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                switch (i40) {
                    case 0:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(1, keyguardViewMediator, (Bundle) obj).run();
                        return Unit.INSTANCE;
                    case 1:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(0, keyguardViewMediator, (Integer) obj).run();
                        return Unit.INSTANCE;
                    case 2:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(2, keyguardViewMediator, (Boolean) obj).run();
                        return Unit.INSTANCE;
                    case 3:
                        return Long.valueOf(keyguardViewMediator.getLockTimeout(((Integer) obj).intValue()));
                    default:
                        String str = (String) obj;
                        if (str != null) {
                            keyguardViewMediator.mPhoneState = str;
                        }
                        return keyguardViewMediator.mPhoneState;
                }
            }
        };
        final int i40 = 1;
        Function1 function12 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda15
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i402 = i40;
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                switch (i402) {
                    case 0:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(1, keyguardViewMediator, (Bundle) obj).run();
                        return Unit.INSTANCE;
                    case 1:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(0, keyguardViewMediator, (Integer) obj).run();
                        return Unit.INSTANCE;
                    case 2:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(2, keyguardViewMediator, (Boolean) obj).run();
                        return Unit.INSTANCE;
                    case 3:
                        return Long.valueOf(keyguardViewMediator.getLockTimeout(((Integer) obj).intValue()));
                    default:
                        String str = (String) obj;
                        if (str != null) {
                            keyguardViewMediator.mPhoneState = str;
                        }
                        return keyguardViewMediator.mPhoneState;
                }
            }
        };
        final int i41 = 21;
        Function0 function039 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i41;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i42 = 22;
        Function0 function040 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i42;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i43 = 2;
        Function1 function13 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda15
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i402 = i43;
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                switch (i402) {
                    case 0:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(1, keyguardViewMediator, (Bundle) obj).run();
                        return Unit.INSTANCE;
                    case 1:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(0, keyguardViewMediator, (Integer) obj).run();
                        return Unit.INSTANCE;
                    case 2:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(2, keyguardViewMediator, (Boolean) obj).run();
                        return Unit.INSTANCE;
                    case 3:
                        return Long.valueOf(keyguardViewMediator.getLockTimeout(((Integer) obj).intValue()));
                    default:
                        String str = (String) obj;
                        if (str != null) {
                            keyguardViewMediator.mPhoneState = str;
                        }
                        return keyguardViewMediator.mPhoneState;
                }
            }
        };
        final int i44 = 23;
        Function0 function041 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i44;
                Object obj = this;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i45 = 24;
        Function0 function042 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i182 = i45;
                Object obj = arrayList;
                switch (i182) {
                    case 0:
                        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) obj;
                        keyguardViewMediator.getClass();
                        break;
                    case 14:
                        KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) obj;
                        keyguardViewMediator2.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator2, 5).run();
                        break;
                    case 15:
                        KeyguardViewMediator keyguardViewMediator3 = (KeyguardViewMediator) obj;
                        keyguardViewMediator3.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator3, 9).run();
                        break;
                    case 16:
                        KeyguardViewMediator keyguardViewMediator4 = (KeyguardViewMediator) obj;
                        keyguardViewMediator4.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator4, 15).run();
                        break;
                    case 17:
                        KeyguardViewMediator keyguardViewMediator5 = (KeyguardViewMediator) obj;
                        keyguardViewMediator5.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator5, 12).run();
                        break;
                    case 18:
                        KeyguardViewMediator keyguardViewMediator6 = (KeyguardViewMediator) obj;
                        keyguardViewMediator6.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator6, 14).run();
                        break;
                    case 19:
                        KeyguardViewMediator keyguardViewMediator7 = (KeyguardViewMediator) obj;
                        keyguardViewMediator7.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator7, 10).run();
                        break;
                    case 20:
                        KeyguardViewMediator keyguardViewMediator8 = (KeyguardViewMediator) obj;
                        keyguardViewMediator8.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator8, 7).run();
                        break;
                    case 21:
                        KeyguardViewMediator keyguardViewMediator9 = (KeyguardViewMediator) obj;
                        keyguardViewMediator9.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator9, 8).run();
                        break;
                    case 22:
                        KeyguardViewMediator keyguardViewMediator10 = (KeyguardViewMediator) obj;
                        keyguardViewMediator10.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator10, 13).run();
                        break;
                    case 23:
                        KeyguardViewMediator keyguardViewMediator11 = (KeyguardViewMediator) obj;
                        keyguardViewMediator11.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda2(keyguardViewMediator11, 11).run();
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        final int i46 = 3;
        Function1 function14 = new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda15
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i402 = i46;
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                switch (i402) {
                    case 0:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(1, keyguardViewMediator, (Bundle) obj).run();
                        return Unit.INSTANCE;
                    case 1:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(0, keyguardViewMediator, (Integer) obj).run();
                        return Unit.INSTANCE;
                    case 2:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(2, keyguardViewMediator, (Boolean) obj).run();
                        return Unit.INSTANCE;
                    case 3:
                        return Long.valueOf(keyguardViewMediator.getLockTimeout(((Integer) obj).intValue()));
                    default:
                        String str = (String) obj;
                        if (str != null) {
                            keyguardViewMediator.mPhoneState = str;
                        }
                        return keyguardViewMediator.mPhoneState;
                }
            }
        };
        final int i47 = 4;
        keyguardViewMediatorHelperImpl.viewMediatorProvider = new ViewMediatorProvider(function0, function02, function03, function04, function05, function06, function07, function08, function09, function010, function011, function012, function013, function014, function015, function016, function017, function018, function019, function020, function021, function022, function023, function024, function025, function026, function027, function028, function029, function030, function031, function032, function033, function034, function035, function036, function037, function038, function2, function1, function12, function039, function040, function13, function041, function042, function14, new Function1(this) { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda15
            public final /* synthetic */ KeyguardViewMediator f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i402 = i47;
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                switch (i402) {
                    case 0:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(1, keyguardViewMediator, (Bundle) obj).run();
                        return Unit.INSTANCE;
                    case 1:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(0, keyguardViewMediator, (Integer) obj).run();
                        return Unit.INSTANCE;
                    case 2:
                        keyguardViewMediator.getClass();
                        new KeyguardViewMediator$$ExternalSyntheticLambda16(2, keyguardViewMediator, (Boolean) obj).run();
                        return Unit.INSTANCE;
                    case 3:
                        return Long.valueOf(keyguardViewMediator.getLockTimeout(((Integer) obj).intValue()));
                    default:
                        String str = (String) obj;
                        if (str != null) {
                            keyguardViewMediator.mPhoneState = str;
                        }
                        return keyguardViewMediator.mPhoneState;
                }
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
        this.mSystemPropertiesHelper = systemPropertiesHelper;
        this.mStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        this.mKeyguardDisplayManager = keyguardDisplayManager;
        this.mShadeController = lazy4;
        String name = getClass().getName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, name, this);
        this.mKeyguardTransitions = keyguardTransitions;
        this.mNotificationShadeWindowControllerLazy = lazy5;
        deviceConfigProxy.getClass();
        this.mShowHomeOverLockscreen = DeviceConfig.getBoolean("systemui", "nav_bar_handle_show_over_lockscreen", true);
        DeviceConfig.addOnPropertiesChangedListener("systemui", new Executor() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda4
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                handlerC147812.post(runnable);
            }
        }, (DeviceConfig.OnPropertiesChangedListener) r7);
        this.mInGestureNavigationMode = QuickStepContract.isGesturalMode(navigationModeController.addListener(new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda5
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i48) {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                keyguardViewMediator.getClass();
                keyguardViewMediator.mInGestureNavigationMode = QuickStepContract.isGesturalMode(i48);
            }
        }));
        this.mDozeParameters = dozeParameters;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        ((StatusBarStateControllerImpl) sysuiStatusBarStateController).addCallback((StatusBarStateController.StateListener) this);
        this.mKeyguardStateController = keyguardStateController;
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(r8);
        this.mKeyguardUnlockAnimationControllerLazy = lazy2;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mActivityLaunchAnimator = lazy6;
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

    public void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback) {
        synchronized (this) {
            this.mKeyguardStateCallbacks.add(iKeyguardStateCallback);
            try {
                iKeyguardStateCallback.onSimSecureStateChanged(this.mUpdateMonitor.isSimPinSecure());
                iKeyguardStateCallback.onShowingStateChanged(this.mShowing, KeyguardUpdateMonitor.getCurrentUser());
                iKeyguardStateCallback.onInputRestrictedStateChanged(this.mInputRestricted);
                iKeyguardStateCallback.onTrustedChanged(this.mUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser()));
            } catch (RemoteException e) {
                Slog.m144w("Failed to call to IKeyguardStateCallback", e);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0140 A[Catch: RemoteException -> 0x017b, TryCatch #0 {RemoteException -> 0x017b, blocks: (B:48:0x013c, B:50:0x0140, B:51:0x014a, B:53:0x014e, B:56:0x0167, B:58:0x016f), top: B:47:0x013c }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x014e A[Catch: RemoteException -> 0x017b, TryCatch #0 {RemoteException -> 0x017b, blocks: (B:48:0x013c, B:50:0x0140, B:51:0x014a, B:53:0x014e, B:56:0x0167, B:58:0x016f), top: B:47:0x013c }] */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void adjustStatusBarLocked(boolean z, boolean z2) {
        int i;
        IStatusBarService iStatusBarService;
        StatusBarManager statusBarManager = this.mStatusBarManager;
        Context context = this.mContext;
        if (statusBarManager == null) {
            this.mStatusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        }
        if (this.mStatusBarManager == null) {
            Log.m142w("KeyguardViewMediator", "Could not get status bar manager");
            return;
        }
        boolean z3 = false;
        if (z2) {
            try {
                this.mStatusBarService.disableForUser(0, this.mStatusBarDisableToken, context.getPackageName(), ((UserTrackerImpl) this.mUserTracker).getUserId());
            } catch (RemoteException e) {
                android.util.Log.d("KeyguardViewMediator", "Failed to force clear flags", e);
                KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.DEBUG, "Failed to force clear flags", e);
            }
        }
        boolean z4 = this.mOccluded;
        boolean z5 = this.mShowHomeOverLockscreen;
        boolean z6 = this.mInGestureNavigationMode;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        boolean isShowing = keyguardViewMediatorHelperImpl.isShowing();
        boolean z7 = ((KeyguardStateControllerImpl) keyguardViewMediatorHelperImpl.stateController).mKeyguardGoingAway;
        try {
            if (!z7) {
                if (!z && !((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).isShowingAndNotOccluded()) {
                    if (isShowing) {
                        if (z4 && (!LsRune.SUBSCREEN_WATCHFACE || keyguardViewMediatorHelperImpl.foldControllerImpl.isFoldOpened())) {
                            i = 16777216;
                            if (isShowing && keyguardViewMediatorHelperImpl.updateMonitor.isRemoteLockMode()) {
                                i |= EnterpriseDeviceManager.PASSWORD_QUALITY_ALPHANUMERIC;
                            }
                            if (keyguardViewMediatorHelperImpl.disableFlags == i) {
                                int[] iArr = {com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_DEVICE_DOZING, 16777216, 262144, 65536};
                                int i2 = ((CentralSurfacesImpl) ((CentralSurfaces) keyguardViewMediatorHelperImpl.centralSurfacesLazy.get())).mDisabled1;
                                int i3 = 0;
                                while (true) {
                                    if (i3 >= 4) {
                                        z3 = true;
                                        break;
                                    }
                                    int i4 = iArr[i3];
                                    if ((i4 & i) == i4 && (i4 & i2) != i4) {
                                        CharsKt__CharJVMKt.checkRadix(16);
                                        String num = Integer.toString(i, 16);
                                        CharsKt__CharJVMKt.checkRadix(16);
                                        KeyguardViewMediatorHelperImpl.logD("isValidDisableFlags 0x" + num + " 0x" + Integer.toString(i2, 16));
                                        break;
                                    }
                                    i3++;
                                }
                                if (z3) {
                                    CharsKt__CharJVMKt.checkRadix(16);
                                    KeyguardViewMediatorHelperImpl.logD("adjustStatusBarLocked: no need to update flags=0x" + Integer.toString(i, 16) + " / showHomeOverLock=" + z5);
                                    return;
                                }
                            }
                            keyguardViewMediatorHelperImpl.disableFlags = i;
                            boolean isSecure = keyguardViewMediatorHelperImpl.isSecure();
                            CharsKt__CharJVMKt.checkRadix(16);
                            String num2 = Integer.toString(i, 16);
                            StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("adjustStatusBarLocked: goingAway=", z7, " showing=", isShowing, " occluded=");
                            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m, z4, " isSecure=", isSecure, " force=");
                            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m, z, " showHomeOverLock=", z5, " gestureNaviMode=");
                            m69m.append(z6);
                            m69m.append(" --> flags=0x");
                            m69m.append(num2);
                            KeyguardViewMediatorHelperImpl.logD(m69m.toString());
                            if (keyguardViewMediatorHelperImpl.barService == null) {
                                keyguardViewMediatorHelperImpl.barService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
                            }
                            iStatusBarService = keyguardViewMediatorHelperImpl.barService;
                            if (iStatusBarService != null) {
                                keyguardViewMediatorHelperImpl.getHandler().removeMessages(VolteConstants.ErrorCode.PPP_STATUS_CLOSE_EVENT);
                                if (((KeyguardViewController) keyguardViewMediatorHelperImpl.viewControllerLazy.get()).isLaunchEditMode() && z7) {
                                    keyguardViewMediatorHelperImpl.getHandler().sendEmptyMessage(VolteConstants.ErrorCode.PPP_STATUS_CLOSE_EVENT);
                                    return;
                                } else {
                                    iStatusBarService.disable(i, keyguardViewMediatorHelperImpl.token, keyguardViewMediatorHelperImpl.context.getPackageName());
                                    return;
                                }
                            }
                            return;
                        }
                    }
                }
                i = 18874368;
                if (isShowing) {
                    i |= EnterpriseDeviceManager.PASSWORD_QUALITY_ALPHANUMERIC;
                }
                if (keyguardViewMediatorHelperImpl.disableFlags == i) {
                }
                keyguardViewMediatorHelperImpl.disableFlags = i;
                boolean isSecure2 = keyguardViewMediatorHelperImpl.isSecure();
                CharsKt__CharJVMKt.checkRadix(16);
                String num22 = Integer.toString(i, 16);
                StringBuilder m69m2 = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("adjustStatusBarLocked: goingAway=", z7, " showing=", isShowing, " occluded=");
                KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m2, z4, " isSecure=", isSecure2, " force=");
                KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m2, z, " showHomeOverLock=", z5, " gestureNaviMode=");
                m69m2.append(z6);
                m69m2.append(" --> flags=0x");
                m69m2.append(num22);
                KeyguardViewMediatorHelperImpl.logD(m69m2.toString());
                if (keyguardViewMediatorHelperImpl.barService == null) {
                }
                iStatusBarService = keyguardViewMediatorHelperImpl.barService;
                if (iStatusBarService != null) {
                }
            }
            if (keyguardViewMediatorHelperImpl.barService == null) {
            }
            iStatusBarService = keyguardViewMediatorHelperImpl.barService;
            if (iStatusBarService != null) {
            }
        } catch (RemoteException e2) {
            Slog.m144w("adjustStatusBarLocked - disable failed", e2);
            return;
        }
        i = 0;
        if (isShowing) {
        }
        if (keyguardViewMediatorHelperImpl.disableFlags == i) {
        }
        keyguardViewMediatorHelperImpl.disableFlags = i;
        boolean isSecure22 = keyguardViewMediatorHelperImpl.isSecure();
        CharsKt__CharJVMKt.checkRadix(16);
        String num222 = Integer.toString(i, 16);
        StringBuilder m69m22 = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("adjustStatusBarLocked: goingAway=", z7, " showing=", isShowing, " occluded=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m22, z4, " isSecure=", isSecure22, " force=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m22, z, " showHomeOverLock=", z5, " gestureNaviMode=");
        m69m22.append(z6);
        m69m22.append(" --> flags=0x");
        m69m22.append(num222);
        KeyguardViewMediatorHelperImpl.logD(m69m22.toString());
    }

    public void cancelKeyguardExitAnimation() {
        Trace.beginSection("KeyguardViewMediator#cancelKeyguardExitAnimation");
        HandlerC147812 handlerC147812 = this.mHandler;
        handlerC147812.sendMessage(handlerC147812.obtainMessage(19));
        Trace.endSection();
    }

    public final InteractionJankMonitor.Configuration.Builder createInteractionJankMonitorConf(int i, String str) {
        Log.m138d("KeyguardViewMediator", str != null ? str : "null");
        InteractionJankMonitor.Configuration.Builder withView = InteractionJankMonitor.Configuration.Builder.withView(i, ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
        return str != null ? withView.setTag(str) : withView;
    }

    public void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        Log.m138d("KeyguardViewMediator", "dismiss");
        KeyguardUnlockInfo.setUnlockTriggerIfNotSet(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_EXTERNAL);
        this.mHandler.obtainMessage(11, new DismissMessage(iKeyguardDismissCallback, charSequence)).sendToTarget();
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0073, code lost:
    
        if (r3 <= 0) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void doKeyguardLaterForChildProfilesLocked() {
        for (UserInfo userInfo : ((UserTrackerImpl) this.mUserTracker).getUserProfiles()) {
            if (userInfo.isEnabled()) {
                int i = userInfo.id;
                if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
                    keyguardViewMediatorHelperImpl.getClass();
                    boolean isSecureFolderId = SemPersonaManager.isSecureFolderId(i);
                    Context context = keyguardViewMediatorHelperImpl.context;
                    long intForUser = isSecureFolderId ? Settings.System.getIntForUser(context.getContentResolver(), "knox_screen_off_timeout", -1, i) : Settings.Secure.getIntForUser(context.getContentResolver(), "knox_screen_off_timeout", -1, i);
                    long maximumTimeToLock = keyguardViewMediatorHelperImpl.lockPatternUtils.getDevicePolicyManager().getMaximumTimeToLock(null, i);
                    boolean z = maximumTimeToLock > 0;
                    if (z && intForUser > 0) {
                        intForUser = Math.min(maximumTimeToLock, intForUser);
                    } else if (z) {
                        intForUser = maximumTimeToLock;
                    }
                    if (intForUser != 0 && intForUser != -1 && intForUser != -2) {
                        intForUser = Math.max(intForUser, 5000L);
                    }
                    intForUser = Math.max(intForUser - Math.max(SystemClock.uptimeMillis() - keyguardViewMediatorHelperImpl.f297pm.getLastUserActivityTime(i), 0L), 0L);
                    if (intForUser > 0) {
                        long elapsedRealtime = SystemClock.elapsedRealtime() + intForUser;
                        Intent intent = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
                        Context context2 = this.mContext;
                        intent.setPackage(context2.getPackageName());
                        intent.putExtra("seq", this.mDelayedProfileShowingSequence);
                        intent.putExtra("android.intent.extra.USER_ID", i);
                        intent.addFlags(com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                        this.mAlarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime, PendingIntent.getBroadcast(context2, i, intent, 335544320));
                    } else if (intForUser == 0 || intForUser == -2) {
                        this.mTrustManager.setDeviceLockedForUser(i, true);
                    }
                }
            }
        }
    }

    public final void doKeyguardLaterLocked(long j) {
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
        long elapsedRealtime = SystemClock.elapsedRealtime() + j;
        Intent intent = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        Context context = this.mContext;
        intent.setPackage(context.getPackageName());
        intent.putExtra("seq", this.mDelayedShowingSequence);
        intent.addFlags(com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 335544320);
        this.mAlarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime, broadcast);
        Log.m139d("KeyguardViewMediator", "setting alarm to turn off keyguard, seq = %s, timeout = %d", Integer.valueOf(this.mDelayedShowingSequence), Long.valueOf(j));
        doKeyguardLaterForChildProfilesLocked();
        KeyguardViewMediator$$ExternalSyntheticLambda6 keyguardViewMediator$$ExternalSyntheticLambda6 = new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl, 2);
        if (z) {
            keyguardViewMediator$$ExternalSyntheticLambda6.accept(broadcast);
        }
    }

    public final boolean doKeyguardLocked(Bundle bundle, boolean z) {
        boolean z2 = this.mExternallyEnabled;
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        int i = 0;
        if (!z2 && !lockPatternUtils.isUserInLockdown(KeyguardUpdateMonitor.getCurrentUser())) {
            Objects.requireNonNull(keyguardViewMediatorHelperImpl);
            KeyguardViewMediator$$ExternalSyntheticLambda3 keyguardViewMediator$$ExternalSyntheticLambda3 = new KeyguardViewMediator$$ExternalSyntheticLambda3(keyguardViewMediatorHelperImpl, i);
            boolean z3 = Rune.SYSUI_MULTI_SIM;
            if (keyguardViewMediator$$ExternalSyntheticLambda3.getAsBoolean()) {
                Log.m138d("KeyguardViewMediator", "doKeyguard: not showing because externally disabled");
                this.mNeedToReshowWhenReenabled = true;
                return false;
            }
        }
        if (this.mShowing && ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            if (this.mPM.isInteractive() && !this.mHiding) {
                Log.m138d("KeyguardViewMediator", "doKeyguard: not showing (instead, resetting) because it is already showing, we're interactive, and we were not previously hiding. It should be safe to short-circuit here.");
                keyguardViewMediatorHelperImpl.setShowingOptions(bundle);
                resetStateLocked(false);
                return false;
            }
            Log.m140e("KeyguardViewMediator", "doKeyguard: already showing, but re-showing because we're interactive or were in the middle of hiding.");
        }
        boolean z4 = !SystemProperties.getBoolean("keyguard.no_require_sim", true);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        boolean z5 = keyguardUpdateMonitor.isSimPinSecure() || ((SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(1)) || SubscriptionManager.isValidSubscriptionId(keyguardUpdateMonitor.getNextSubIdForState(7))) && z4);
        if (!z5 && shouldWaitForProvisioning()) {
            Log.m138d("KeyguardViewMediator", "doKeyguard: not showing because device isn't provisioned and the sim is not locked or missing");
            return false;
        }
        boolean z6 = bundle != null && bundle.getBoolean("force_show", false);
        if (lockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser()) && !z5 && !z6) {
            Log.m138d("KeyguardViewMediator", "doKeyguard: not showing because lockscreen is off");
            return false;
        }
        if (!keyguardViewMediatorHelperImpl.isKeyguardDisabled(false)) {
            if (z) {
                return true;
            }
            keyguardUpdateMonitor.setUnlockingKeyguard(false);
            Log.m138d("KeyguardViewMediator", "doKeyguard: showing the lock screen");
            showLocked(bundle);
            return true;
        }
        if (keyguardViewMediatorHelperImpl.isShowing()) {
            Function2 function2 = keyguardViewMediatorHelperImpl.getViewMediatorProvider().setShowingLocked;
            Boolean bool = Boolean.FALSE;
            function2.invoke(bool, bool);
            keyguardViewMediatorHelperImpl.hidingByDisabled = true;
            KeyguardViewMediatorHelperImpl.logD("hideLocked by disabled keyguard");
            keyguardViewMediatorHelperImpl.getViewMediatorProvider().hideLocked.invoke();
        }
        return false;
    }

    public void doKeyguardTimeout(Bundle bundle) {
        HandlerC147812 handlerC147812 = this.mHandler;
        handlerC147812.removeMessages(10);
        handlerC147812.sendMessageAtFrontOfQueue(handlerC147812.obtainMessage(10, bundle));
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

    public void exitKeyguardAndFinishSurfaceBehindRemoteAnimation(final boolean z) {
        Log.m139d("KeyguardViewMediator", "onKeyguardExitRemoteAnimationFinished surfBehindRemoteAniRun=%b surfBehindRemoteAniReq=%b cancelled=%b", Boolean.valueOf(this.mSurfaceBehindRemoteAnimationRunning), Boolean.valueOf(this.mSurfaceBehindRemoteAnimationRequested), Boolean.valueOf(z));
        if (!this.mSurfaceBehindRemoteAnimationRunning && !this.mSurfaceBehindRemoteAnimationRequested) {
            StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("skip onKeyguardExitRemoteAnimationFinished cancelled=", z, " surfaceAnimationRunning=");
            m49m.append(this.mSurfaceBehindRemoteAnimationRunning);
            m49m.append(" surfaceAnimationRequested=");
            m49m.append(this.mSurfaceBehindRemoteAnimationRequested);
            Log.m138d("KeyguardViewMediator", m49m.toString());
            return;
        }
        ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).blockPanelExpansionFromCurrentTouch();
        final boolean z2 = this.mShowing;
        ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).notifyFinishedKeyguardExitAnimation(z);
        DejankUtils.setImmediate(true);
        DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                boolean z3 = z2;
                boolean z4 = z;
                keyguardViewMediator.onKeyguardExitFinished();
                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardViewMediator.mKeyguardStateController;
                boolean z5 = keyguardStateControllerImpl.mDismissingFromTouch;
                Lazy lazy = keyguardViewMediator.mKeyguardUnlockAnimationControllerLazy;
                if (z5 || z3) {
                    Log.m138d("KeyguardViewMediator", "onKeyguardExitRemoteAnimationFinished#hideKeyguardViewAfterRemoteAnimation");
                    ((KeyguardUnlockAnimationController) lazy.get()).hideKeyguardViewAfterRemoteAnimation();
                } else {
                    Log.m138d("KeyguardViewMediator", "skip hideKeyguardViewAfterRemoteAnimation dismissFromSwipe=" + keyguardStateControllerImpl.mDismissingFromTouch + " wasShowing=" + z3);
                }
                ((KeyguardUnlockAnimationController) lazy.get()).notifyFinishedKeyguardExitAnimation(z4);
                keyguardViewMediator.finishSurfaceBehindRemoteAnimation();
                keyguardViewMediator.mUpdateMonitor.mHandler.sendEmptyMessage(346);
            }
        });
        DejankUtils.setImmediate(false);
    }

    public void finishSurfaceBehindRemoteAnimation() {
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
    }

    public IRemoteAnimationRunner getExitAnimationRunner() {
        C148216 c148216 = new C148216(this.mExitAnimationRunner);
        this.mHelper.exitAnimationRunner = c148216;
        return c148216;
    }

    public final long getLockTimeout(int i) {
        long intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "lock_screen_lock_after_timeout", 5000, i);
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) this.mHelper.knoxStateMonitor).mEdmMonitor;
        int i2 = edmMonitor == null ? 0 : edmMonitor.mLockDelay;
        if (i2 >= 0) {
            LogUtil.m223d("KeyguardViewMediator", "mdmDelay=%d, lockAfterTimeout=%d", Integer.valueOf(i2), Long.valueOf(intForUser));
            intForUser = Math.min(i2 * 1000, intForUser);
        }
        long maximumTimeToLock = this.mLockPatternUtils.getDevicePolicyManager().getMaximumTimeToLock(null, i);
        return maximumTimeToLock <= 0 ? intForUser : Math.max(Math.min(maximumTimeToLock - Math.max(Settings.System.getIntForUser(r0, "screen_off_timeout", 30000, i), 0L), intForUser), 0L);
    }

    public IRemoteAnimationRunner getOccludeAnimationRunner() {
        return new C148216(this.mHelper.disabledOccluedeAnimationRunner);
    }

    public IRemoteAnimationRunner getOccludeByDreamAnimationRunner() {
        return new C148216(this.mHelper.disabledOccluedeAnimationRunner);
    }

    public IRemoteAnimationRunner getUnoccludeAnimationRunner() {
        return new C148216(this.mHelper.unoccluedAnimationRunner);
    }

    public ViewMediatorCallback getViewMediatorCallback() {
        return this.mViewMediatorCallback;
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x009e, code lost:
    
        if (r0 != false) goto L44;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleHide() {
        Trace.beginSection("KeyguardViewMediator#handleHide");
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        KeyguardViewMediator$$ExternalSyntheticLambda7 keyguardViewMediator$$ExternalSyntheticLambda7 = new KeyguardViewMediator$$ExternalSyntheticLambda7(keyguardViewMediatorHelperImpl, 5);
        boolean z = true;
        Rune.runIf((Runnable) keyguardViewMediator$$ExternalSyntheticLambda7, true);
        if (this.mAodShowing) {
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this.mHelper;
            if ((keyguardViewMediatorHelperImpl2.fastUnlockController.isFastWakeAndUnlockMode() || (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && keyguardViewMediatorHelperImpl2.foldControllerImpl.isUnlockOnFoldOpened())) ? false : true) {
                this.mPM.wakeUp(SystemClock.uptimeMillis(), 4, "com.android.systemui:BOUNCER_DOZING");
            }
        }
        synchronized (this) {
            Log.m138d("KeyguardViewMediator", "handleHide");
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this.mHelper;
            Objects.requireNonNull(keyguardViewMediatorHelperImpl3);
            if (new KeyguardViewMediator$$ExternalSyntheticLambda3(keyguardViewMediatorHelperImpl3, 2).getAsBoolean()) {
                Log.m138d("KeyguardViewMediator", "handleHide: mWakeAndUnlocking set false");
                this.mWakeAndUnlocking = false;
                return;
            }
            this.mHiding = true;
            if (!this.mWakeAndUnlocking) {
                setUnlockAndWakeFromDream(0, ((StatusBarStateControllerImpl) this.mStatusBarStateController).mIsDreaming && this.mPM.isInteractive());
            }
            if (this.mShowing) {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = this.mHelper;
                boolean z2 = this.mOccluded;
                keyguardViewMediatorHelperImpl4.getClass();
                if ((!LsRune.KEYGUARD_SUB_DISPLAY_COVER || keyguardViewMediatorHelperImpl4.foldControllerImpl.isFoldOpened()) && !z2) {
                }
                z = false;
            }
            if (!this.mUnlockingAndWakingFromDream) {
                ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) this.mNotificationShadeWindowControllerLazy.get())).batchApplyWindowLayoutParams(new KeyguardViewMediator$$ExternalSyntheticLambda2(this, 6));
                Trace.endSection();
            }
            if (this.mUnlockingAndWakingFromDream) {
                Log.m138d("KeyguardViewMediator", "hiding keyguard before waking from dream");
            }
            this.mKeyguardGoingAwayRunnable.run();
            Trace.endSection();
        }
    }

    public final void handleKeyguardDone() {
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDone");
        final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                int i = currentUser;
                LockPatternUtils lockPatternUtils = keyguardViewMediator.mLockPatternUtils;
                if (lockPatternUtils.isSecure(i)) {
                    lockPatternUtils.getDevicePolicyManager().reportKeyguardDismissed(i);
                }
            }
        });
        Log.m138d("KeyguardViewMediator", "handleKeyguardDone");
        synchronized (this) {
            resetKeyguardDonePendingLocked();
        }
        if (!this.mGoingToSleep || shouldWaitForProvisioning()) {
            setPendingLock(false);
            handleHide();
            this.mUpdateMonitor.clearBiometricRecognized(currentUser);
            Trace.endSection();
            return;
        }
        Log.m141i("KeyguardViewMediator", "Device is going to sleep, aborting keyguardDone");
        this.mUpdateMonitor.clearBiometricRecognized(currentUser);
        this.mDismissCallbackRegistry.notifyDismissCancelled();
        ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).onDismissCancelled();
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda7(keyguardViewMediatorHelperImpl, 2), true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void handleStartKeyguardExitAnimation(long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        Trace.beginSection("KeyguardViewMediator#handleStartKeyguardExitAnimation");
        Log.m138d("KeyguardViewMediator", "handleStartKeyguardExitAnimation startTime=" + j + " fadeoutDuration=" + j2);
        synchronized (this) {
            int i = 1;
            if (!this.mHiding && !this.mSurfaceBehindRemoteAnimationRequested && !((KeyguardStateControllerImpl) this.mKeyguardStateController).mFlingingToDismissKeyguardDuringSwipeGesture) {
                if (iRemoteAnimationFinishedCallback != null) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                    } catch (RemoteException e) {
                        Slog.m144w("Failed to call onAnimationFinished", e);
                    }
                }
                setShowingLocked(this.mShowing, true);
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda7(keyguardViewMediatorHelperImpl, 10), true);
                return;
            }
            this.mHiding = false;
            IRemoteAnimationRunner iRemoteAnimationRunner = this.mKeyguardExitAnimationRunner;
            this.mKeyguardExitAnimationRunner = null;
            LatencyTracker.getInstance(this.mContext).onActionEnd(11);
            if (iRemoteAnimationRunner != null && iRemoteAnimationFinishedCallback != null) {
                IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = new IRemoteAnimationFinishedCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.14
                    public final IBinder asBinder() {
                        return iRemoteAnimationFinishedCallback.asBinder();
                    }

                    public final void onAnimationFinished() {
                        try {
                            iRemoteAnimationFinishedCallback.onAnimationFinished();
                        } catch (RemoteException e2) {
                            Slog.m144w("Failed to call onAnimationFinished", e2);
                        }
                        KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                        Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                        keyguardViewMediator.onKeyguardExitFinished();
                        ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).hide(0L, 0L);
                        KeyguardViewMediator.this.mInteractionJankMonitor.end(29);
                    }
                };
                try {
                    this.mInteractionJankMonitor.begin(createInteractionJankMonitorConf(29, "RunRemoteAnimation"));
                    iRemoteAnimationRunner.onAnimationStart(7, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback2);
                } catch (RemoteException e2) {
                    Slog.m144w("Failed to call onAnimationStart", e2);
                }
            } else if (((StatusBarStateControllerImpl) this.mStatusBarStateController).mLeaveOpenOnKeyguardHide || remoteAnimationTargetArr == 0 || remoteAnimationTargetArr.length <= 0) {
                ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).hide(j, j2);
                this.mContext.getMainExecutor().execute(new KeyguardViewMediator$$ExternalSyntheticLambda18(this, iRemoteAnimationFinishedCallback, remoteAnimationTargetArr, i));
                onKeyguardExitFinished();
            } else {
                this.mSurfaceBehindRemoteAnimationFinishedCallback = iRemoteAnimationFinishedCallback;
                this.mSurfaceBehindRemoteAnimationRunning = true;
                ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).notifyStartSurfaceBehindRemoteAnimation(remoteAnimationTargetArr, (RemoteAnimationTarget[]) Arrays.stream(remoteAnimationTargetArr2).filter(new KeyguardViewMediator$$ExternalSyntheticLambda19()).toArray(new KeyguardViewMediator$$ExternalSyntheticLambda20()), j, this.mSurfaceBehindRemoteAnimationRequested);
            }
            Trace.endSection();
            return;
        }
    }

    public void hideSurfaceBehindKeyguard() {
        Log.m138d("KeyguardViewMediator", "hideSurfaceBehindKeyguard");
        this.mSurfaceBehindRemoteAnimationRequested = false;
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(false);
        if (this.mShowing) {
            setShowingLocked(true, true);
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
            Objects.requireNonNull(keyguardViewMediatorHelperImpl);
            Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda7(keyguardViewMediatorHelperImpl, 9), true);
        }
    }

    public void hideWithAnimation(IRemoteAnimationRunner iRemoteAnimationRunner) {
        if (this.mKeyguardDonePending) {
            this.mKeyguardExitAnimationRunner = iRemoteAnimationRunner;
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
        return i != -1 ? isSecure(i) : isSecure(KeyguardUpdateMonitor.getCurrentUser());
    }

    public boolean isShowingAndNotOccluded() {
        return this.mShowing && !this.mOccluded;
    }

    public void maybeHandlePendingLock() {
        if (this.mPendingLock) {
            if (this.mScreenOffAnimationController.shouldDelayKeyguardShow()) {
                Log.m138d("KeyguardViewMediator", "#maybeHandlePendingLock: not handling because the screen off animation's shouldDelayKeyguardShow() returned true. This should be handled soon by #onStartedWakingUp, or by the end actions of the screen off animation.");
            } else {
                if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway) {
                    Log.m138d("KeyguardViewMediator", "#maybeHandlePendingLock: not handling because the keyguard is going away. This should be handled shortly by StatusBar#finishKeyguardFadingAway.");
                    return;
                }
                Log.m138d("KeyguardViewMediator", "#maybeHandlePendingLock: handling pending lock; locking keyguard.");
                doKeyguardLocked(null, false);
                setPendingLock(false);
            }
        }
    }

    public final void maybeSendUserPresentBroadcast() {
        boolean z = this.mSystemReady;
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        if (z) {
            boolean isLockScreenDisabled = lockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser());
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
            if (isLockScreenDisabled || (!this.mShowing && keyguardViewMediatorHelperImpl.isKeyguardDisabledBySettings(false))) {
                sendUserPresentBroadcast();
                boolean z2 = !this.mUpdateMonitor.isForcedLock();
                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                Rune.runIf(new KeyguardViewMediator$$ExternalSyntheticLambda7(keyguardViewMediatorHelperImpl, 4), z2);
                return;
            }
        }
        if (this.mSystemReady && shouldWaitForProvisioning()) {
            lockPatternUtils.userPresent(KeyguardUpdateMonitor.getCurrentUser());
        }
    }

    public final void notifyTrustedChangedLocked(boolean z) {
        ArrayList arrayList = this.mKeyguardStateCallbacks;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            try {
                ((IKeyguardStateCallback) arrayList.get(size)).onTrustedChanged(z);
            } catch (RemoteException e) {
                Slog.m144w("Failed to call notifyTrustedChangedLocked", e);
                if (e instanceof DeadObjectException) {
                    arrayList.remove(size);
                }
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void onBootCompleted() {
        synchronized (this) {
            if (this.mContext.getResources().getBoolean(android.R.bool.config_enableWcgMode)) {
                ((GuestUserInteractor) this.mUserSwitcherController.guestUserInteractor$delegate.getValue()).onDeviceBootCompleted();
            }
            this.mBootCompleted = true;
            adjustStatusBarLocked(false, true);
            if (this.mBootSendUserPresent) {
                sendUserPresentBroadcast();
            }
            if (LsRune.SUBSCREEN_UI) {
                this.mHandler.obtainMessage(VolteConstants.ErrorCode.QOS_FAILURE).sendToTarget();
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void onConfigurationChanged(Configuration configuration) {
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        keyguardViewMediatorHelperImpl.getClass();
        if (!LsRune.SUBSCREEN_UI || LsRune.SUBSCREEN_WATCHFACE) {
            return;
        }
        PluginSubScreen pluginSubScreen = keyguardViewMediatorHelperImpl.subScreenManager.mSubScreenPlugin;
        if (pluginSubScreen == null) {
            android.util.Log.w("SubScreenManager", "onConfigurationChanged() no plugin");
        } else {
            pluginSubScreen.onConfigurationChanged(configuration);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onDozeAmountChanged(float f, float f2) {
        if (this.mAnimatingScreenOff && this.mDozing && f == 1.0f) {
            this.mAnimatingScreenOff = false;
            setShowingLocked(this.mShowing, true);
        }
    }

    public void onDreamingStarted() {
        this.mUpdateMonitor.dispatchDreamingStarted();
        synchronized (this) {
            boolean isEnabled = ((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.LOCKSCREEN_WITHOUT_SECURE_LOCK_WHEN_DREAMING);
            if (this.mDeviceInteractive && ((isEnabled || this.mLockPatternUtils.isSecure(KeyguardUpdateMonitor.getCurrentUser())) && !((DesktopManagerImpl) this.mHelper.desktopManager).isDualView())) {
                long lockTimeout = getLockTimeout(KeyguardUpdateMonitor.getCurrentUser());
                if (lockTimeout == 0) {
                    doKeyguardLocked(null, false);
                } else {
                    doKeyguardLaterLocked(lockTimeout);
                }
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
            this.mDeviceInteractive = false;
            this.mGoingToSleep = false;
            this.mWakeAndUnlocking = false;
            this.mAnimatingScreenOff = this.mDozeParameters.shouldAnimateDozingChange();
            resetKeyguardDonePendingLocked();
            this.mHideAnimationRun = false;
            this.mHandler.sendEmptyMessage(5);
            if (z) {
                ((PowerManager) this.mContext.getSystemService(PowerManager.class)).wakeUp(SystemClock.uptimeMillis(), 5, "com.android.systemui:CAMERA_GESTURE_PREVENT_LOCK");
                setPendingLock(false);
                this.mPendingReset = false;
                this.mPowerGestureIntercepted = true;
                Log.m138d("KeyguardViewMediator", "cameraGestureTriggered=" + z + ",mPowerGestureIntercepted=" + this.mPowerGestureIntercepted);
            }
            if (this.mPendingReset) {
                resetStateLocked(true);
                this.mPendingReset = false;
            }
            maybeHandlePendingLock();
            if (!this.mLockLater && !z) {
                doKeyguardLaterForChildProfilesLocked();
            }
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        synchronized (keyguardUpdateMonitor) {
            keyguardUpdateMonitor.mDeviceInteractive = false;
        }
        KeyguardUpdateMonitor.HandlerC080015 handlerC080015 = keyguardUpdateMonitor.mHandler;
        handlerC080015.sendMessage(handlerC080015.obtainMessage(320, i, 0));
    }

    public final void onKeyguardExitFinished() {
        Log.m138d("KeyguardViewMediator", "onKeyguardExitFinished()");
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(this.mPhoneState)) {
            playSound(this.mUnlockSoundId);
        }
        setShowingLocked(false);
        this.mWakeAndUnlocking = false;
        this.mDismissCallbackRegistry.notifyDismissSucceeded();
        resetKeyguardDonePendingLocked();
        this.mHideAnimationRun = false;
        adjustStatusBarLocked(false, false);
        sendUserPresentBroadcast();
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda7(keyguardViewMediatorHelperImpl, 1), true);
    }

    public void onScreenTurnedOff() {
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda7(keyguardViewMediatorHelperImpl, 3), true);
        this.mUpdateMonitor.mHandler.sendEmptyMessage(CustomDeviceManager.DESTINATION_ADDRESS);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x005c A[Catch: all -> 0x0062, TryCatch #0 {all -> 0x0062, blocks: (B:3:0x0001, B:5:0x0022, B:9:0x002e, B:11:0x003c, B:13:0x0044, B:14:0x0058, B:16:0x005c, B:17:0x0064, B:22:0x0047), top: B:2:0x0001 }] */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda9] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onStartedGoingToSleep(int i) {
        boolean z;
        synchronized (this) {
            try {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                Rune.runIf(i, new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl, 1));
                this.mDeviceInteractive = false;
                this.mPowerGestureIntercepted = false;
                this.mGoingToSleep = true;
                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                if (!this.mLockPatternUtils.getPowerButtonInstantlyLocks(currentUser) && this.mLockPatternUtils.isSecure(currentUser)) {
                    z = false;
                    final long lockTimeout = getLockTimeout(KeyguardUpdateMonitor.getCurrentUser());
                    this.mLockLater = false;
                    if (this.mShowing || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway) {
                        this.mHelper.updatePendingLock(i, lockTimeout, z, currentUser, new KeyguardViewMediator$$ExternalSyntheticLambda2(this, 2), new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda9
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                                keyguardViewMediator.doKeyguardLaterLocked(lockTimeout);
                                keyguardViewMediator.mLockLater = true;
                            }
                        });
                    } else {
                        this.mPendingReset = true;
                    }
                    if (this.mPendingLock) {
                        playSound(this.mLockSoundId);
                    }
                }
                z = true;
                final long lockTimeout2 = getLockTimeout(KeyguardUpdateMonitor.getCurrentUser());
                this.mLockLater = false;
                if (this.mShowing) {
                }
                this.mHelper.updatePendingLock(i, lockTimeout2, z, currentUser, new KeyguardViewMediator$$ExternalSyntheticLambda2(this, 2), new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                        keyguardViewMediator.doKeyguardLaterLocked(lockTimeout2);
                        keyguardViewMediator.mLockLater = true;
                    }
                });
                if (this.mPendingLock) {
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        KeyguardUpdateMonitor.HandlerC080015 handlerC080015 = this.mUpdateMonitor.mHandler;
        handlerC080015.sendMessage(handlerC080015.obtainMessage(321, i, 0));
        KeyguardUpdateMonitor.HandlerC080015 handlerC0800152 = this.mUpdateMonitor.mHandler;
        handlerC0800152.sendMessage(handlerC0800152.obtainMessage(342, Boolean.FALSE));
        this.mHandler.sendEmptyMessage(17);
    }

    public void onStartedWakingUp(int i, boolean z) {
        Trace.beginSection("KeyguardViewMediator#onStartedWakingUp");
        synchronized (this) {
            try {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                int i2 = 0;
                Rune.runIf(i, new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl, i2));
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
                KeyguardViewMediator$$ExternalSyntheticLambda6 keyguardViewMediator$$ExternalSyntheticLambda6 = new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl2, 4);
                if (z2) {
                    keyguardViewMediator$$ExternalSyntheticLambda6.accept(Boolean.FALSE);
                }
                if (z) {
                    this.mPowerGestureIntercepted = true;
                }
                Log.m138d("KeyguardViewMediator", "onStartedWakingUp, seq = " + this.mDelayedShowingSequence + ", mPowerGestureIntercepted = " + this.mPowerGestureIntercepted);
                Log.m138d("KeyguardViewMediator", "notifyStartedWakingUp");
                this.mHandler.sendEmptyMessage(14);
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this.mHelper;
                Objects.requireNonNull(keyguardViewMediatorHelperImpl3);
                Rune.runIf(new KeyguardViewMediator$$ExternalSyntheticLambda7(keyguardViewMediatorHelperImpl3, i2), z2);
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mUiEventLogger.logWithInstanceIdAndPosition(BiometricUnlockController.BiometricUiEvent.STARTED_WAKING_UP, 0, (String) null, this.mSessionTracker.getSessionId(1), i);
        this.mUpdateMonitor.dispatchStartedWakingUp(i);
        maybeSendUserPresentBroadcast();
        Trace.endSection();
    }

    public void onSystemReady() {
        this.mHandler.obtainMessage(18).sendToTarget();
    }

    @Override // com.android.systemui.CoreStartable
    public final void onTrimMemory(int i) {
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        if (keyguardViewMediatorHelperImpl.isShowing() || i < 60) {
            return;
        }
        ((KeyguardViewController) keyguardViewMediatorHelperImpl.viewControllerLazy.get()).onTrimMemory(i);
    }

    public void onWakeAndUnlocking(boolean z) {
        Trace.beginSection("KeyguardViewMediator#onWakeAndUnlocking");
        this.mWakeAndUnlocking = true;
        setUnlockAndWakeFromDream(3, z);
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        KeyguardViewMediator$$ExternalSyntheticLambda3 keyguardViewMediator$$ExternalSyntheticLambda3 = new KeyguardViewMediator$$ExternalSyntheticLambda3(keyguardViewMediatorHelperImpl, 1);
        boolean z2 = Rune.SYSUI_MULTI_SIM;
        if (keyguardViewMediator$$ExternalSyntheticLambda3.getAsBoolean()) {
            tryKeyguardDone();
        } else {
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).notifyKeyguardAuthenticated(false);
            userActivity();
        }
        Trace.endSection();
    }

    public final void playSound(final int i) {
        if (i == 0) {
            return;
        }
        final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        if (keyguardViewMediatorHelperImpl.settingsHelper.mItemLists.get("lockscreen_sounds_enabled").getIntValue() == 1) {
            keyguardViewMediatorHelperImpl.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$playSound$1
                @Override // java.lang.Runnable
                public final void run() {
                    float semGetSituationVolume;
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediatorHelperImpl.this;
                    SoundPool soundPool = keyguardViewMediatorHelperImpl2.lockSounds;
                    if (soundPool != null) {
                        soundPool.stop(keyguardViewMediatorHelperImpl2.lockSoundStreamId);
                    }
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = KeyguardViewMediatorHelperImpl.this;
                    keyguardViewMediatorHelperImpl3.uiSoundsStreamType = keyguardViewMediatorHelperImpl3.audioManager.getUiSoundsStreamType();
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = KeyguardViewMediatorHelperImpl.this;
                    if (keyguardViewMediatorHelperImpl4.audioManager.isStreamMute(keyguardViewMediatorHelperImpl4.uiSoundsStreamType)) {
                        return;
                    }
                    if (LsRune.KEYGUARD_LOCK_SITUATION_VOLUME) {
                        semGetSituationVolume = 1.0f;
                    } else {
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = KeyguardViewMediatorHelperImpl.this;
                        semGetSituationVolume = keyguardViewMediatorHelperImpl5.audioManager.semGetSituationVolume(i == keyguardViewMediatorHelperImpl5.unlockSoundId ? 7 : 4, 0);
                    }
                    float f = semGetSituationVolume;
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl6 = KeyguardViewMediatorHelperImpl.this;
                    String str = "playSound " + i;
                    keyguardViewMediatorHelperImpl6.getClass();
                    KeyguardViewMediatorHelperImpl.logD(str);
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl7 = KeyguardViewMediatorHelperImpl.this;
                    SoundPool soundPool2 = keyguardViewMediatorHelperImpl7.lockSounds;
                    if (soundPool2 != null) {
                        keyguardViewMediatorHelperImpl7.lockSoundStreamId = soundPool2.play(i, f, f, 1, 0, 1.0f);
                    }
                }
            });
        }
    }

    public boolean requestedShowSurfaceBehindKeyguard() {
        return this.mSurfaceBehindRemoteAnimationRequested;
    }

    public final void resetKeyguardDonePendingLocked() {
        this.mKeyguardDonePending = false;
        this.mHandler.removeMessages(13);
    }

    public final void resetStateLocked(boolean z) {
        Log.m140e("KeyguardViewMediator", "resetStateLocked");
        HandlerC147812 handlerC147812 = this.mHandler;
        handlerC147812.sendMessage(handlerC147812.obtainMessage(3, z ? 1 : 0, 0));
    }

    public final void scheduleNonStrongBiometricIdleTimeout() {
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        if (this.mUpdateMonitor.isUnlockWithFacePossible(currentUser)) {
            Log.m138d("KeyguardViewMediator", "scheduleNonStrongBiometricIdleTimeout: schedule an alarm for currentUser=" + currentUser);
            this.mLockPatternUtils.scheduleNonStrongBiometricIdleTimeout(currentUser);
        }
    }

    public final void sendUserPresentBroadcast() {
        synchronized (this) {
            if (this.mBootCompleted) {
                final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                final UserHandle userHandle = new UserHandle(currentUser);
                final UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                        UserManager userManager2 = userManager;
                        UserHandle userHandle2 = userHandle;
                        int i = currentUser;
                        keyguardViewMediator.getClass();
                        for (int i2 : userManager2.getProfileIdsWithDisabled(userHandle2.getIdentifier())) {
                            keyguardViewMediator.mContext.sendBroadcastAsUser(KeyguardViewMediator.USER_PRESENT_INTENT, UserHandle.of(i2), null, KeyguardViewMediator.USER_PRESENT_INTENT_OPTIONS);
                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediator.mHelper;
                            Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                            Rune.runIf(i2, new KeyguardViewMediator$$ExternalSyntheticLambda6(keyguardViewMediatorHelperImpl, 3));
                        }
                        keyguardViewMediator.mLockPatternUtils.userPresent(i);
                    }
                });
            } else {
                this.mBootSendUserPresent = true;
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
            notifyTrustedChangedLocked(this.mUpdateMonitor.getUserHasTrust(i));
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
            Log.m138d("KeyguardViewMediator", "setKeyguardEnabled(" + z + ")");
            this.mExternallyEnabled = z;
            if (z || !this.mShowing) {
                if (z && this.mNeedToReshowWhenReenabled) {
                    Log.m138d("KeyguardViewMediator", "previously hidden, reshowing, reenabling status bar expansion");
                    this.mNeedToReshowWhenReenabled = false;
                    updateInputRestrictedLocked();
                    showLocked(null);
                    this.mWaitingUntilKeyguardVisible = true;
                    this.mHandler.sendEmptyMessageDelayed(8, 2000L);
                    Log.m138d("KeyguardViewMediator", "waiting until mWaitingUntilKeyguardVisible is false");
                    while (this.mWaitingUntilKeyguardVisible) {
                        try {
                            wait();
                        } catch (InterruptedException unused) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    Log.m138d("KeyguardViewMediator", "done waiting for mWaitingUntilKeyguardVisible");
                }
            } else {
                if (this.mLockPatternUtils.isUserInLockdown(KeyguardUpdateMonitor.getCurrentUser())) {
                    Log.m138d("KeyguardViewMediator", "keyguardEnabled(false) overridden by user lockdown");
                    return;
                }
                Log.m138d("KeyguardViewMediator", "remembering to reshow, hiding keyguard, disabling status bar expansion");
                this.mNeedToReshowWhenReenabled = true;
                updateInputRestrictedLocked();
                Trace.beginSection("KeyguardViewMediator#hideLocked");
                Log.m138d("KeyguardViewMediator", "hideLocked");
                HandlerC147812 handlerC147812 = this.mHandler;
                handlerC147812.sendMessage(handlerC147812.obtainMessage(2));
                Trace.endSection();
            }
        }
    }

    public void setOccluded(boolean z, boolean z2) {
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        int andIncrement = keyguardViewMediatorHelperImpl.occludedSeq.getAndIncrement();
        KeyguardViewMediator$$ExternalSyntheticLambda2 keyguardViewMediator$$ExternalSyntheticLambda2 = new KeyguardViewMediator$$ExternalSyntheticLambda2(this, 4);
        keyguardViewMediatorHelperImpl.getHandler().post(new KeyguardViewMediatorHelperImpl$cancelAODJankMonitor$1(keyguardViewMediatorHelperImpl));
        if (!z && keyguardViewMediatorHelperImpl.getHandler().hasMessages(keyguardViewMediatorHelperImpl.getSET_OCCLUDED()) && keyguardViewMediatorHelperImpl.isKeyguardHiding()) {
            keyguardViewMediator$$ExternalSyntheticLambda2.run();
        }
        keyguardViewMediatorHelperImpl.getHandler().removeMessages(keyguardViewMediatorHelperImpl.getSET_OCCLUDED());
        keyguardViewMediatorHelperImpl.getHandler().sendMessage(keyguardViewMediatorHelperImpl.getHandler().obtainMessage(keyguardViewMediatorHelperImpl.getSET_OCCLUDED(), z ? 1 : 0, z2 ? 1 : 0, Integer.valueOf(andIncrement)));
        synchronized (keyguardViewMediatorHelperImpl.getLock()) {
            keyguardViewMediatorHelperImpl.curIsOccluded = z;
            Unit unit = Unit.INSTANCE;
        }
    }

    public void setPendingLock(boolean z) {
        this.mPendingLock = z;
        Trace.traceCounter(4096L, "pendingLock", z ? 1 : 0);
    }

    public void setShowingLocked(boolean z) {
        setShowingLocked(z, false);
    }

    public void setSwitchingUser(boolean z) {
        Log.m138d("KeyguardViewMediator", "setSwitchingUser " + z);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        keyguardUpdateMonitor.mSwitchingUser = z;
        keyguardUpdateMonitor.mHandler.post(new KeyguardUpdateMonitor$$ExternalSyntheticLambda3(keyguardUpdateMonitor, 7));
        keyguardUpdateMonitor.dispatchSecureState(4094);
    }

    public final void setUnlockAndWakeFromDream(int i, boolean z) {
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
        Log.m138d("KeyguardViewMediator", String.format("Updating waking and unlocking request to %b. description:[%s]. reason:[%s]", Boolean.valueOf(z), z2 ? "Interrupting request to wake and unlock" : z ? "Initiating request to wake and unlock" : "Fulfilling request to wake and unlock", str));
    }

    public void setWallpaperSupportsAmbientMode(boolean z) {
        this.mWallpaperSupportsAmbientMode = z;
    }

    public final void setupLocked() {
        boolean z;
        PowerManager.WakeLock newWakeLock = this.mPM.newWakeLock(1, "show keyguard");
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
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        int i = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
        synchronized (KeyguardUpdateMonitor.class) {
            KeyguardUpdateMonitor.sCurrentUser = userId;
        }
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        keyguardViewMediatorHelperImpl.getClass();
        boolean z3 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
        if (z3 || LsRune.KEYGUARD_SUB_DISPLAY_COVER) {
            keyguardViewMediatorHelperImpl.foldControllerImpl.handler = keyguardViewMediatorHelperImpl.getHandler();
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
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.samsung.internal.policy.impl.Keyguard.PCW_LOCKED");
        intentFilter3.addAction("com.samsung.internal.policy.impl.Keyguard.PCW_UNLOCKED");
        Unit unit = Unit.INSTANCE;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, keyguardViewMediatorHelperImpl$broadcastReceiver$1, intentFilter3, null, null, 0, "com.samsung.android.permission.LOCK_SECURITY_MONITOR", 28);
        BroadcastDispatcher broadcastDispatcher2 = keyguardViewMediatorHelperImpl.broadcastDispatcher;
        KeyguardViewMediatorHelperImpl$broadcastReceiver$1 keyguardViewMediatorHelperImpl$broadcastReceiver$12 = keyguardViewMediatorHelperImpl.broadcastReceiver;
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("com.sec.android.FindingLostPhonePlus.CANCEL");
        intentFilter4.addAction("com.sec.android.FindingLostPhonePlus.SUBSCRIBE");
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher2, keyguardViewMediatorHelperImpl$broadcastReceiver$12, intentFilter4, null, null, 0, null, 60);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(keyguardViewMediatorHelperImpl.context);
        KeyguardViewMediatorHelperImpl$localReceiver$1 keyguardViewMediatorHelperImpl$localReceiver$1 = keyguardViewMediatorHelperImpl.localReceiver;
        IntentFilter intentFilter5 = new IntentFilter("com.samsung.keyguard.CLEAR_LOCK");
        synchronized (localBroadcastManager.mReceivers) {
            LocalBroadcastManager.ReceiverRecord receiverRecord = new LocalBroadcastManager.ReceiverRecord(intentFilter5, keyguardViewMediatorHelperImpl$localReceiver$1);
            ArrayList arrayList2 = (ArrayList) localBroadcastManager.mReceivers.get(keyguardViewMediatorHelperImpl$localReceiver$1);
            if (arrayList2 == null) {
                arrayList2 = new ArrayList(1);
                localBroadcastManager.mReceivers.put(keyguardViewMediatorHelperImpl$localReceiver$1, arrayList2);
            }
            arrayList2.add(receiverRecord);
            for (int i2 = 0; i2 < intentFilter5.countActions(); i2++) {
                String action = intentFilter5.getAction(i2);
                ArrayList arrayList3 = (ArrayList) localBroadcastManager.mActions.get(action);
                if (arrayList3 == null) {
                    arrayList3 = new ArrayList(1);
                    localBroadcastManager.mActions.put(action, arrayList3);
                }
                arrayList3.add(receiverRecord);
            }
        }
        keyguardViewMediatorHelperImpl.updateMonitor.setupLocked();
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
        IntentFilter intentFilter6 = new IntentFilter();
        intentFilter6.addAction("com.sec.android.intent.action.REQUEST_BACKUP_LOCKSCREEN");
        intentFilter6.addAction("com.sec.android.intent.action.REQUEST_RESTORE_LOCKSCREEN");
        intentFilter6.addAction("com.sec.android.intent.action.REQUEST_BACKUP_WALLPAPER");
        intentFilter6.addAction("com.sec.android.intent.action.REQUEST_RESTORE_WALLPAPER");
        context.registerReceiver(backupRestoreReceiver.mBroadcastReceiver, intentFilter6, "android.permission.SET_WALLPAPER", null, 2);
        if (LsRune.KEYGUARD_HOMEHUB) {
            BroadcastDispatcher.registerReceiver$default(keyguardViewMediatorHelperImpl.broadcastDispatcher, keyguardViewMediatorHelperImpl.broadcastReceiver, new IntentFilter("android.intent.action.DOCK_EVENT"), null, null, 0, null, 60);
        }
        ((KeyguardUnlockAnimationController) keyguardViewMediatorHelperImpl.unlockAnimationControllerLazy.get()).setCallback(new KeyguardViewMediatorHelperImpl$setupLocked$5(keyguardViewMediatorHelperImpl));
        Context context2 = this.mContext;
        try {
            z = context2.getPackageManager().getServiceInfo(new ComponentName(context2, (Class<?>) KeyguardService.class), 0).isEnabled();
        } catch (PackageManager.NameNotFoundException unused) {
            z = true;
        }
        if (z) {
            if (!shouldWaitForProvisioning() && !this.mHelper.isKeyguardDisabled(true) && !this.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser())) {
                z2 = true;
            }
            setShowingLocked(z2, true);
        } else {
            setShowingLocked(false, true);
        }
        if (this.mWallpaperManager == null) {
            this.mWallpaperManager = (WallpaperManager) this.mContext.getSystemService(WallpaperManager.class);
        }
        boolean isLockscreenLiveWallpaperEnabled = this.mWallpaperManager.isLockscreenLiveWallpaperEnabled();
        KeyguardTransitions keyguardTransitions = this.mKeyguardTransitions;
        IRemoteAnimationRunner exitAnimationRunner = getExitAnimationRunner();
        int i3 = KeyguardService.$r8$clinit;
        keyguardTransitions.register(new KeyguardService.C14691(this, exitAnimationRunner, isLockscreenLiveWallpaperEnabled), new KeyguardService.C14691(this, getOccludeAnimationRunner(), isLockscreenLiveWallpaperEnabled), new KeyguardService.C14691(this, getOccludeByDreamAnimationRunner(), isLockscreenLiveWallpaperEnabled), new KeyguardService.C14691(this, getUnoccludeAnimationRunner(), isLockscreenLiveWallpaperEnabled));
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mDeviceInteractive = this.mPM.isInteractive();
        this.mLockSounds = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(13).setContentType(4).build()).build();
        String string = Settings.Global.getString(contentResolver, "lock_sound");
        if (string != null) {
            this.mLockSoundId = this.mLockSounds.load(string, 1);
        }
        if (string == null || this.mLockSoundId == 0) {
            Log.m142w("KeyguardViewMediator", "failed to load lock sound from " + string);
        }
        String string2 = Settings.Global.getString(contentResolver, "unlock_sound");
        if (string2 != null) {
            this.mUnlockSoundId = this.mLockSounds.load(string2, 1);
        }
        if (string2 == null || this.mUnlockSoundId == 0) {
            Log.m142w("KeyguardViewMediator", "failed to load unlock sound from " + string2);
        }
        String string3 = Settings.Global.getString(contentResolver, "trusted_sound");
        if (string3 != null) {
            this.mTrustedSoundId = this.mLockSounds.load(string3, 1);
        }
        if (string3 == null || this.mTrustedSoundId == 0) {
            Log.m142w("KeyguardViewMediator", "failed to load trusted sound from " + string3);
        }
        Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda2(this, 16), true);
        this.mHideAnimation = AnimationUtils.loadAnimation(this.mContext, android.R.anim.screen_rotate_0_exit);
        new WorkLockActivityController(this.mContext, this.mUserTracker);
    }

    public final boolean shouldWaitForProvisioning() {
        return (this.mUpdateMonitor.mDeviceProvisioned || isSecure()) ? false : true;
    }

    public final void showLocked(Bundle bundle) {
        Trace.beginSection("KeyguardViewMediator#showLocked acquiring mShowKeyguardWakeLock");
        Log.m138d("KeyguardViewMediator", "showLocked");
        this.mShowKeyguardWakeLock.acquire();
        HandlerC147812 handlerC147812 = this.mHandler;
        handlerC147812.sendMessageAtFrontOfQueue(handlerC147812.obtainMessage(1, bundle));
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
        KeyguardViewMediator$$ExternalSyntheticLambda11 keyguardViewMediator$$ExternalSyntheticLambda11 = new KeyguardViewMediator$$ExternalSyntheticLambda11(keyguardViewMediatorHelperImpl, 0);
        boolean z = Rune.SYSUI_MULTI_SIM;
        keyguardViewMediator$$ExternalSyntheticLambda11.accept(6, 4000L);
        Trace.endSection();
    }

    public void showSurfaceBehindKeyguard() {
        Log.m138d("KeyguardViewMediator", "showSurfaceBehindKeyguard");
        this.mSurfaceBehindRemoteAnimationRequested = true;
        try {
            KeyguardUnlockAnimationController.Companion.getClass();
            if (!this.mHelper.keyguardGoingAway(6)) {
                throw new RemoteException();
            }
            ((KeyguardStateControllerImpl) this.mKeyguardStateController).notifyKeyguardGoingAway(true);
        } catch (RemoteException e) {
            this.mSurfaceBehindRemoteAnimationRequested = false;
            e.printStackTrace();
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        synchronized (this) {
            setupLocked();
        }
    }

    public void startKeyguardExitAnimation(long j, long j2) {
        startKeyguardExitAnimation(0, j, j2, null, null, null, null);
    }

    public final void tryKeyguardDone() {
        boolean z;
        int i;
        KeyguardUnlockInfo.UnlockTrigger unlockTrigger;
        int ordinal;
        KeyguardUnlockInfo.SkipBouncerReason skipBouncerReason;
        SemDvfsManager semDvfsManager;
        Log.m138d("KeyguardViewMediator", "tryKeyguardDone: pending - " + this.mKeyguardDonePending + ", animRan - " + this.mHideAnimationRun + " animRunning - " + this.mHideAnimationRunning);
        boolean z2 = (this.mKeyguardDonePending || !this.mHideAnimationRun || this.mHideAnimationRunning) ? false : true;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        keyguardViewMediatorHelperImpl.getClass();
        if (Rune.SYSUI_BINDER_CALL_MONITOR) {
            ((BinderCallMonitorImpl) keyguardViewMediatorHelperImpl.binderCallMonitor).startMonitoring$1(2);
        }
        if (z2) {
            KeyguardFastBioUnlockController keyguardFastBioUnlockController = keyguardViewMediatorHelperImpl.fastUnlockController;
            if (!(keyguardFastBioUnlockController.bioUnlockBoosterEnabled && keyguardFastBioUnlockController.dvfsManager != null && keyguardFastBioUnlockController.isEnabled()) && (semDvfsManager = keyguardViewMediatorHelperImpl.dvfsManager) != null) {
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
            int i3 = i;
            KeyguardUnlockInfo.INSTANCE.getClass();
            int i4 = KeyguardUnlockInfo.WhenMappings.$EnumSwitchMapping$0[KeyguardUnlockInfo.authType.ordinal()];
            String str = i4 != 1 ? i4 != 2 ? i4 != 3 ? KeyguardUnlockInfo.authType.toString() : String.valueOf(KeyguardUnlockInfo.skipBouncerReason) : String.valueOf(KeyguardUnlockInfo.biometricSourceType) : String.valueOf(KeyguardUnlockInfo.securityMode);
            KeyguardUnlockInfo.leaveHistory(i3 + ": " + str + " " + KeyguardUnlockInfo.unlockTrigger, true);
            KeyguardUnlockInfo.reset();
            EventLog.writeEvent(70000, i3);
            KeyguardDumpLog.state$default(KeyguardDumpLog.INSTANCE, 3, false, false, false, i3, 0, 46);
            z = true;
        } else {
            z = false;
        }
        if (z) {
            handleKeyguardDone();
            return;
        }
        if (!this.mKeyguardDonePending && this.mHideAnimationRun && !this.mHideAnimationRunning) {
            handleKeyguardDone();
            return;
        }
        if (this.mSurfaceBehindRemoteAnimationRunning) {
            exitKeyguardAndFinishSurfaceBehindRemoteAnimation(false);
        } else {
            if (this.mHideAnimationRun) {
                return;
            }
            Log.m138d("KeyguardViewMediator", "tryKeyguardDone: starting pre-hide animation");
            this.mHideAnimationRun = true;
            this.mHideAnimationRunning = true;
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).startPreHideAnimation(this.mHideAnimationFinishedRunnable);
        }
    }

    public final void updateInputRestrictedLocked() {
        boolean isInputRestricted = isInputRestricted();
        if (this.mInputRestricted == isInputRestricted) {
            return;
        }
        this.mInputRestricted = isInputRestricted;
        ArrayList arrayList = this.mKeyguardStateCallbacks;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            IKeyguardStateCallback iKeyguardStateCallback = (IKeyguardStateCallback) arrayList.get(size);
            try {
                iKeyguardStateCallback.onInputRestrictedStateChanged(isInputRestricted);
            } catch (RemoteException e) {
                Slog.m144w("Failed to call onDeviceProvisioned", e);
                if (e instanceof DeadObjectException) {
                    arrayList.remove(iKeyguardStateCallback);
                }
            }
        }
    }

    public void userActivity() {
        this.mPM.userActivity(SystemClock.uptimeMillis(), false);
    }

    public void verifyUnlock(IKeyguardExitCallback iKeyguardExitCallback) {
        Trace.beginSection("KeyguardViewMediator#verifyUnlock");
        synchronized (this) {
            Log.m138d("KeyguardViewMediator", "verifyUnlock");
            if (shouldWaitForProvisioning()) {
                Log.m138d("KeyguardViewMediator", "ignoring because device isn't provisioned");
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e) {
                    Slog.m144w("Failed to call onKeyguardExitResult(false)", e);
                }
            } else if (this.mExternallyEnabled) {
                Log.m142w("KeyguardViewMediator", "verifyUnlock called when not externally disabled");
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e2) {
                    Slog.m144w("Failed to call onKeyguardExitResult(false)", e2);
                }
            } else if (isSecure()) {
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e3) {
                    Slog.m144w("Failed to call onKeyguardExitResult(false)", e3);
                }
            } else {
                this.mExternallyEnabled = true;
                this.mNeedToReshowWhenReenabled = false;
                synchronized (this) {
                    updateInputRestrictedLocked();
                    try {
                        iKeyguardExitCallback.onKeyguardExitResult(true);
                    } catch (RemoteException e4) {
                        Slog.m144w("Failed to call onKeyguardExitResult(true)", e4);
                    }
                }
            }
        }
        Trace.endSection();
    }

    public final void setShowingLocked(final boolean z, boolean z2) {
        Bundle bundle;
        int i = 0;
        boolean z3 = this.mDozing && !this.mWakeAndUnlocking;
        boolean z4 = this.mShowing;
        boolean z5 = z != z4 || z2;
        boolean z6 = (z == z4 && z3 == this.mAodShowing && !z2) ? false : true;
        boolean z7 = z4 != z;
        this.mShowing = z;
        this.mAodShowing = z3;
        if (z5) {
            DejankUtils.whitelistIpcs(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                    boolean z8 = z;
                    ArrayList arrayList = keyguardViewMediator.mKeyguardStateCallbacks;
                    int size = arrayList.size();
                    while (true) {
                        size--;
                        if (size < 0) {
                            return;
                        }
                        IKeyguardStateCallback iKeyguardStateCallback = (IKeyguardStateCallback) arrayList.get(size);
                        try {
                            iKeyguardStateCallback.onShowingStateChanged(z8, KeyguardUpdateMonitor.getCurrentUser());
                        } catch (RemoteException e) {
                            Slog.m144w("Failed to call onShowingStateChanged", e);
                            if (e instanceof DeadObjectException) {
                                arrayList.remove(iKeyguardStateCallback);
                            }
                        }
                    }
                }
            });
            updateInputRestrictedLocked();
            if (z7) {
                this.mUiBgExecutor.execute(new KeyguardViewMediator$$ExternalSyntheticLambda2(this, i));
            }
        }
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        if (z6) {
            KeyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1 keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1 = keyguardViewMediatorHelperImpl.setLockScreenShownRunnable;
            Handler handler = keyguardViewMediatorHelperImpl.getHandler();
            if (handler.hasCallbacks(keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1)) {
                handler.removeCallbacks(keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1);
            }
            keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1.showing = z;
            keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1.aodShowing = z3;
            if (z && (bundle = keyguardViewMediatorHelperImpl.showingOptions) != null) {
                if (bundle.getBoolean("LockShownDelay", false)) {
                    Log.m141i("KeyguardViewMediator", "updateActivityLockScreenState " + z + " " + z3 + " after 300ms");
                    keyguardViewMediatorHelperImpl.getHandler().postDelayed(keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1, 300L);
                }
            }
            keyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1.run();
        }
        keyguardViewMediatorHelperImpl.setShowingOptions(null);
    }

    public void startKeyguardExitAnimation(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        startKeyguardExitAnimation(i, 0L, 0L, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
    }

    public final void startKeyguardExitAnimation(int i, long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        boolean z;
        RemoteAnimationTarget remoteAnimationTarget;
        Trace.beginSection("KeyguardViewMediator#startKeyguardExitAnimation");
        Message obtainMessage = this.mHandler.obtainMessage(VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, new StartKeyguardExitAnimParams(i, j, j2, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback, 0));
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mHelper;
        keyguardViewMediatorHelperImpl.getHandler().post(new KeyguardViewMediatorHelperImpl$cancelAODJankMonitor$1(keyguardViewMediatorHelperImpl));
        StartKeyguardExitAnimParams startKeyguardExitAnimParams = (StartKeyguardExitAnimParams) obtainMessage.obj;
        int i2 = 1;
        if (Debug.semIsProductDev() || LogUtil.isDebugLevelMid || LogUtil.isDebugLevelHigh) {
            String[] strArr = {"app", "nonApp", "wallpaper"};
            RemoteAnimationTarget[][] remoteAnimationTargetArr4 = {startKeyguardExitAnimParams.mApps, startKeyguardExitAnimParams.mNonApps, startKeyguardExitAnimParams.mWallpapers};
            int i3 = 0;
            while (i3 < 3) {
                RemoteAnimationTarget[] remoteAnimationTargetArr5 = remoteAnimationTargetArr4[i3];
                if (remoteAnimationTargetArr5 != null) {
                    if ((remoteAnimationTargetArr5.length == 0 ? i2 : 0) == 0) {
                        int length = remoteAnimationTargetArr5.length;
                        int i4 = -1;
                        int i5 = 0;
                        while (i5 < length) {
                            RemoteAnimationTarget remoteAnimationTarget2 = remoteAnimationTargetArr5[i5];
                            i4 += i2;
                            if (remoteAnimationTarget2 != null) {
                                ActivityManager.RunningTaskInfo runningTaskInfo = remoteAnimationTarget2.taskInfo;
                                ComponentName componentName = runningTaskInfo != null ? runningTaskInfo.topActivity : null;
                                String str = strArr[i3];
                                String m15m = componentName != null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(componentName.getPackageName(), "/", componentName.getClassName()) : "none";
                                boolean z2 = remoteAnimationTarget2.leash != null;
                                StringBuilder m92m = AbstractC0950x8906c950.m92m("exitAnimParam ", str, "[", i4, "]=");
                                m92m.append(m15m);
                                m92m.append(", hasLeash=");
                                m92m.append(z2);
                                KeyguardViewMediatorHelperImpl.logD(m92m.toString());
                            }
                            i5++;
                            i2 = 1;
                        }
                    }
                }
                i3++;
                i2 = 1;
            }
        }
        if ((keyguardViewMediatorHelperImpl.lastGoingAwayFlags & 2) == 2) {
            KeyguardFixedRotationMonitor keyguardFixedRotationMonitor = keyguardViewMediatorHelperImpl.fixedRotationMonitor;
            final boolean z3 = keyguardFixedRotationMonitor.isMonitorStarted && keyguardFixedRotationMonitor.isFixedRotated;
            final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = startKeyguardExitAnimParams.mFinishedCallback;
            RemoteAnimationTarget[] remoteAnimationTargetArr6 = startKeyguardExitAnimParams.mApps;
            final SurfaceControl surfaceControl = (remoteAnimationTargetArr6 == null || (remoteAnimationTarget = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(remoteAnimationTargetArr6)) == null) ? null : remoteAnimationTarget.leash;
            if (surfaceControl == null || iRemoteAnimationFinishedCallback2 == null) {
                z = false;
            } else {
                final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                keyguardViewMediatorHelperImpl.unlockAnimationExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$updateLeashVisible$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Log.m138d("KeyguardViewMediator", "updateLeashVisible");
                        SurfaceControl.Transaction transaction2 = transaction;
                        SurfaceControl surfaceControl2 = surfaceControl;
                        transaction2.setAlpha(surfaceControl2, 1.0f);
                        transaction2.setVisibility(surfaceControl2, true);
                        transaction2.apply();
                        if (z3) {
                            return;
                        }
                        Choreographer choreographer = Choreographer.getInstance();
                        final SurfaceControl.Transaction transaction3 = transaction;
                        final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback3 = iRemoteAnimationFinishedCallback2;
                        choreographer.postCallback(1, new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$updateLeashVisible$1.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                transaction3.close();
                                try {
                                    iRemoteAnimationFinishedCallback3.onAnimationFinished();
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, null);
                    }
                });
                z = true;
            }
            if (z) {
                if (!(keyguardFixedRotationMonitor.isMonitorStarted && keyguardFixedRotationMonitor.isFixedRotated) && startKeyguardExitAnimParams.mFinishedCallback != null) {
                    startKeyguardExitAnimParams.mFinishedCallback = null;
                    startKeyguardExitAnimParams.mApps = new RemoteAnimationTarget[0];
                }
            }
        }
        Handler handler = keyguardViewMediatorHelperImpl.getHandler();
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = keyguardViewMediatorHelperImpl.fastUnlockController;
        if (keyguardFastBioUnlockController.isFastUnlockMode()) {
            keyguardFastBioUnlockController.startKeyguardExitAnimationTime = System.nanoTime();
            handler.sendMessageAtFrontOfQueue(obtainMessage);
        } else if (keyguardFastBioUnlockController.isFastWakeAndUnlockMode()) {
            keyguardFastBioUnlockController.startKeyguardExitAnimationTime = System.nanoTime();
            handler.sendMessage(obtainMessage);
        } else if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && keyguardViewMediatorHelperImpl.foldControllerImpl.isUnlockOnFoldOpened()) {
            handler.sendMessageAtFrontOfQueue(obtainMessage);
        } else {
            handler.sendMessage(obtainMessage);
        }
        Trace.endSection();
    }

    public boolean isSecure(int i) {
        return this.mUpdateMonitor.isSecure(i);
    }

    public void dismissKeyguardToLaunch() {
    }

    public void onShortPowerPressedGoHome() {
    }

    public void onSystemKeyPressed() {
    }
}
