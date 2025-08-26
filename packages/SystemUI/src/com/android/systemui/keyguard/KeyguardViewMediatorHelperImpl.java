package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Presentation;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.IDisplayManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Slog;
import android.view.Choreographer;
import android.view.Display;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.IKeyguardDrawnCallback;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.IRemoteLockMonitorCallback;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.RemoteLockInfo;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardConstants$KeyguardDismissActionType;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardScreenSaver;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.BootAnimationFinishedTrigger;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.Rune;
import com.android.systemui.aibrief.control.BriefNowBarController;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.dreams.ui.viewmodel.DreamViewModel;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.sensor.PickupController;
import com.android.systemui.sensor.PickupController$baseSensorListener$1;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelper;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.ShadeSurface;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.UpdateSource;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.uithreadmonitor.BinderCallMonitor;
import com.android.systemui.uithreadmonitor.BinderCallMonitorConstants;
import com.android.systemui.uithreadmonitor.BinderCallMonitorImpl;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.CarLifeManager;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.custom.SystemManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.os.SemDvfsManager;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.reflect.KFunction;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes2.dex */
public final class KeyguardViewMediatorHelperImpl implements KeyguardViewMediatorHelper, CommandQueue.Callbacks {
    public final Lazy CANCEL_KEYGUARD_EXIT_ANIM$delegate;
    public final Lazy KEYGUARD_DONE$delegate;
    public final Lazy KEYGUARD_DONE_DRAWING$delegate;
    public final Lazy KEYGUARD_DONE_PENDING_TIMEOUT$delegate;
    public final Lazy KEYGUARD_TIMEOUT$delegate;
    public final Lazy NOTIFY_STARTED_GOING_TO_SLEEP$delegate;
    public final Lazy NOTIFY_STARTED_WAKING_UP$delegate;
    public final Lazy SET_OCCLUDED$delegate;
    public final Lazy START_KEYGUARD_EXIT_ANIM$delegate;
    public final Lazy SYSTEM_READY$delegate;
    public final ActiveNotificationsInteractor activeNotificationsInteractor;
    public final ActivityManager activityManager;
    public final AODAmbientWallpaperHelper aodAmbientWallpaperHelper;
    public int aodAppearAnimationFrameCount;
    public final KeyguardViewMediatorHelperImpl$aodAppearAnimationRunner$1 aodAppearAnimationRunner;
    public ValueAnimator aodAppearAnimator;
    private final SettingsHelper.OnChangedCallback aodShowStateCallback;
    public final AudioManager audioManager;
    public IStatusBarService barService;
    public final BinderCallMonitor binderCallMonitor;
    public final dagger.Lazy biometricUnlockControllerLazy;
    public final BootAnimationFinishedTrigger bootAnimationFinishedTrigger;
    public final dagger.Lazy briefNowBarControllerLazy;
    public final BroadcastDispatcher broadcastDispatcher;
    public final KeyguardViewMediatorHelperImpl$broadcastReceiver$1 broadcastReceiver;
    public final Object carrierLock;
    public final dagger.Lazy centralSurfacesLazy;
    public final dagger.Lazy collapsedStatusBarFragmentExt;
    public final CommandQueue commandQueue;
    public final dagger.Lazy commonNotifCollectionLazy;
    public final Context context;
    public boolean curIsOccluded;
    public final KeyguardViewMediatorHelperImpl$delayedDrawnRunnable$1 delayedDrawnRunnable;
    public final DesktopManager desktopManager;
    public int disableFlags;
    public boolean disableRemoteUnlockAnimation;
    public int disabled1;
    public final DismissCallbackRegistry dismissCallbackRegistry;
    public PendingIntent doKeyguardPendingIntent;
    public IKeyguardDrawnCallback drawnCallback;
    public final dagger.Lazy dreamViewModelLazy;
    public SemDvfsManager dvfsManager;
    public final KeyguardEditModeController editModeController;
    public KeyguardViewMediator.AnonymousClass18 exitAnimationRunner;
    public Intent extraUserPresentIntent;
    public final KeyguardFastBioUnlockController fastUnlockController;
    public boolean firstKeyguardShown;
    public final KeyguardFixedRotationMonitor fixedRotationMonitor;
    public final Object fmmLock;
    public final KeyguardFoldControllerImpl foldControllerImpl;
    public boolean goingAwayWithAnimation;
    public boolean handleFoldOpenMsg;
    public int handleMsgLogKey;
    public final Lazy handler$delegate;
    public boolean hidingByDisabled;
    public final InteractionJankMonitor interactionJankMonitor;
    public boolean isAODShowStateCbRegistered;
    public boolean isScreenOnByFoldOpen;
    public boolean isTaskWithEmbeddedOrStartingWindow;
    public final KeyguardDisplayManager keyguardDisplayManager;
    public final Lazy keyguardInteractor$delegate;
    public final KeyguardVisibilityMonitor keyguardVisibilityMonitor;
    public final KeyguardViewMediatorHelperImpl$knoxStateCallback$1 knoxStateCallback;
    public final KnoxStateMonitor knoxStateMonitor;
    public int lastGoingAwayFlags;
    public ComponentName lastOccludedApp;
    public long lastShowingTime;
    public int lastSleepReason;
    public int lastWakeReason;
    public final KeyguardViewMediatorHelperImpl$localReceiver$1 localReceiver;
    public final Lazy lock$delegate;
    public final LockPatternUtils lockPatternUtils;
    public ILockSettings lockSettingsService;
    public StandaloneCoroutine lockShownJob;
    public int lockSoundStreamId;
    public SoundPool lockSounds;
    public int lockStaySoundId;
    public final LooperSlowLogController looperLogController;
    public final Executor mainExecutor;
    public boolean needKeyguardAppearAnimation;
    public final dagger.Lazy notificationShadeWindowControllerLazy;
    public final KeyguardViewMediatorHelperImpl$occludeAnimationRunner$1 occludeAnimationRunner;
    public final AtomicInteger occludedSeq;
    public final PickupController pickupController;
    public final dagger.Lazy pluginAODManagerLazy;
    public final PowerManager pm;
    public int pogoPlugged;
    public final KeyguardViewMediatorHelperImpl$remoteLockMonitorCallback$1 remoteLockMonitorCallback;
    public final CoroutineScope scope;
    public long screenTuringOnTime;
    public final dagger.Lazy scrimControllerLazy;
    public final SelectedUserInteractor selectedUserInteractor;
    public final KeyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1 setLockScreenShownRunnable;
    private final SettingsHelper settingsHelper;
    public final Lazy shadeWindowControllerHelper$delegate;
    public Bundle showingOptions;
    public final KeyguardStateController stateController;
    public final SubScreenManager subScreenManager;
    public final dagger.Lazy surfaceControllerLazy;
    public final int switchingUserId;
    public final KeyguardSysDumpTrigger sysDumpTrigger;
    public final SysuiStatusBarStateController sysuiStatusBarStateController;
    public final IBinder token;
    public final Executor uiBgExecutor;
    public int uiSoundsStreamType;
    public final dagger.Lazy unlockAnimationControllerLazy;
    public final Executor unlockAnimationExecutor;
    public int unlockSoundId;
    public final UnlockedScreenOffAnimationController unlockedScreenOffAnimationController;
    public final SecUnlockedScreenOffAnimationHelper unlockedScreenOffAnimationHelper;
    public final KeyguardViewMediatorHelperImpl$unoccluedAnimationRunner$1 unoccluedAnimationRunner;
    public final KeyguardUpdateMonitorCallback updateCallback;
    public final KeyguardUpdateMonitor updateMonitor;
    public final UserTracker userTracker;
    public final dagger.Lazy viewControllerLazy;
    public final dagger.Lazy viewMediatorLazy;
    public ViewMediatorProvider viewMediatorProvider;
    public final KFunction visibilityListener;
    public final Lazy SHOW$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 0));
    public final Lazy HIDE$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 1));
    public final Lazy RESET$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 4));

    /* JADX WARN: Type inference failed for: r2v75, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$delayedDrawnRunnable$1] */
    /* JADX WARN: Type inference failed for: r2v80, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$remoteLockMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r2v81, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$localReceiver$1] */
    /* JADX WARN: Type inference failed for: r2v82, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$broadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r2v84, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$knoxStateCallback$1] */
    /* JADX WARN: Type inference failed for: r2v86, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$occludeAnimationRunner$1] */
    /* JADX WARN: Type inference failed for: r2v87, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$unoccluedAnimationRunner$1] */
    /* JADX WARN: Type inference failed for: r2v88, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$aodAppearAnimationRunner$1] */
    public KeyguardViewMediatorHelperImpl(Context context, BroadcastDispatcher broadcastDispatcher, Executor executor, Executor executor2, dagger.Lazy lazy, dagger.Lazy lazy2, dagger.Lazy lazy3, dagger.Lazy lazy4, dagger.Lazy lazy5, dagger.Lazy lazy6, dagger.Lazy lazy7, dagger.Lazy lazy8, KeyguardFastBioUnlockController keyguardFastBioUnlockController, KeyguardDisplayManager keyguardDisplayManager, InteractionJankMonitor interactionJankMonitor, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, KeyguardSysDumpTrigger keyguardSysDumpTrigger, UserTracker userTracker, SelectedUserInteractor selectedUserInteractor, ActivityManager activityManager, KnoxStateMonitor knoxStateMonitor, DesktopManager desktopManager, PickupController pickupController, LockPatternUtils lockPatternUtils, KeyguardStateController keyguardStateController, DismissCallbackRegistry dismissCallbackRegistry, SysuiStatusBarStateController sysuiStatusBarStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, PowerManager powerManager, dagger.Lazy lazy9, Executor executor3, dagger.Lazy lazy10, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, LooperSlowLogController looperSlowLogController, SamsungServiceLogger samsungServiceLogger, AudioManager audioManager, SamsungServiceLogger samsungServiceLogger2, BootAnimationFinishedTrigger bootAnimationFinishedTrigger, BinderCallMonitor binderCallMonitor, SubScreenManager subScreenManager, KeyguardFoldControllerImpl keyguardFoldControllerImpl, KeyguardFixedRotationMonitor keyguardFixedRotationMonitor, KeyguardVisibilityMonitor keyguardVisibilityMonitor, dagger.Lazy lazy11, dagger.Lazy lazy12, ActiveNotificationsInteractor activeNotificationsInteractor, KeyguardEditModeController keyguardEditModeController, CarLifeManager carLifeManager, CommandQueue commandQueue, dagger.Lazy lazy13, PowerInteractor powerInteractor, dagger.Lazy lazy14, CoroutineScope coroutineScope, KeyguardTransitionInteractor keyguardTransitionInteractor, DozeParameters dozeParameters) {
        this.context = context;
        this.broadcastDispatcher = broadcastDispatcher;
        this.uiBgExecutor = executor;
        this.unlockAnimationExecutor = executor2;
        this.centralSurfacesLazy = lazy;
        this.viewMediatorLazy = lazy2;
        this.notificationShadeWindowControllerLazy = lazy3;
        this.biometricUnlockControllerLazy = lazy4;
        this.viewControllerLazy = lazy5;
        this.scrimControllerLazy = lazy6;
        this.surfaceControllerLazy = lazy7;
        this.unlockAnimationControllerLazy = lazy8;
        this.fastUnlockController = keyguardFastBioUnlockController;
        this.keyguardDisplayManager = keyguardDisplayManager;
        this.interactionJankMonitor = interactionJankMonitor;
        this.updateMonitor = keyguardUpdateMonitor;
        this.settingsHelper = settingsHelper;
        this.sysDumpTrigger = keyguardSysDumpTrigger;
        this.userTracker = userTracker;
        this.selectedUserInteractor = selectedUserInteractor;
        this.activityManager = activityManager;
        this.knoxStateMonitor = knoxStateMonitor;
        this.desktopManager = desktopManager;
        this.pickupController = pickupController;
        this.lockPatternUtils = lockPatternUtils;
        this.stateController = keyguardStateController;
        this.dismissCallbackRegistry = dismissCallbackRegistry;
        this.sysuiStatusBarStateController = sysuiStatusBarStateController;
        this.unlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.unlockedScreenOffAnimationHelper = secUnlockedScreenOffAnimationHelper;
        this.pm = powerManager;
        this.dreamViewModelLazy = lazy9;
        this.mainExecutor = executor3;
        this.pluginAODManagerLazy = lazy10;
        this.aodAmbientWallpaperHelper = aODAmbientWallpaperHelper;
        this.looperLogController = looperSlowLogController;
        this.audioManager = audioManager;
        this.bootAnimationFinishedTrigger = bootAnimationFinishedTrigger;
        this.binderCallMonitor = binderCallMonitor;
        this.subScreenManager = subScreenManager;
        this.foldControllerImpl = keyguardFoldControllerImpl;
        this.fixedRotationMonitor = keyguardFixedRotationMonitor;
        this.keyguardVisibilityMonitor = keyguardVisibilityMonitor;
        this.commonNotifCollectionLazy = lazy11;
        this.activeNotificationsInteractor = activeNotificationsInteractor;
        this.editModeController = keyguardEditModeController;
        this.commandQueue = commandQueue;
        this.briefNowBarControllerLazy = lazy13;
        this.collapsedStatusBarFragmentExt = lazy14;
        this.scope = coroutineScope;
        LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 5));
        this.KEYGUARD_DONE$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 6));
        this.KEYGUARD_DONE_DRAWING$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 7));
        this.SET_OCCLUDED$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 8));
        this.KEYGUARD_TIMEOUT$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 10));
        LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 11));
        this.START_KEYGUARD_EXIT_ANIM$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 12));
        this.KEYGUARD_DONE_PENDING_TIMEOUT$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 9));
        this.NOTIFY_STARTED_WAKING_UP$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 14));
        this.NOTIFY_STARTED_GOING_TO_SLEEP$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 15));
        this.SYSTEM_READY$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 16));
        this.CANCEL_KEYGUARD_EXIT_ANIM$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 17));
        this.setLockScreenShownRunnable = new KeyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1();
        this.occludedSeq = new AtomicInteger(0);
        this.keyguardInteractor$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 18));
        this.handler$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 19));
        this.lock$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 20));
        this.shadeWindowControllerHelper$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 21));
        this.disableFlags = -1;
        this.disabled1 = -1;
        this.switchingUserId = -1;
        this.token = new Binder();
        final int i = 0;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
                    default:
                        return new Binder();
                }
            }
        });
        final int i2 = 1;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
                    default:
                        return new Binder();
                }
            }
        });
        LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 2));
        LazyKt__LazyJVMKt.lazy(new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 3));
        this.goingAwayWithAnimation = true;
        this.handleMsgLogKey = -1;
        this.delayedDrawnRunnable = new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$delayedDrawnRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                this.$tmp0.notifyDrawn();
            }
        };
        this.aodShowStateCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$aodShowStateCallback$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                KeyguardViewMediatorHelperImpl.logD$1("onAODShowStateChanged: " + this.$tmp0.settingsHelper.isAODShown());
            }
        };
        this.firstKeyguardShown = true;
        this.visibilityListener = new KeyguardViewMediatorHelperImpl$visibilityListener$1(this);
        this.fmmLock = new Object();
        this.carrierLock = new Object();
        this.remoteLockMonitorCallback = new IRemoteLockMonitorCallback.Stub() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$remoteLockMonitorCallback$1
            public final void changeRemoteLockState(RemoteLockInfo remoteLockInfo) {
                int remoteLockType = this.this$0.updateMonitor.getRemoteLockType();
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                int i3 = remoteLockInfo.lockType;
                boolean z = remoteLockInfo.lockState;
                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(remoteLockType, i3, "changeRemoteLockState data=", " -> ", " enableLock=");
                sbM.append(z);
                String string = sbM.toString();
                keyguardViewMediatorHelperImpl.getClass();
                KeyguardViewMediatorHelperImpl.logD$1(string);
                this.this$0.updateMonitor.updateRemoteLockInfo(remoteLockInfo);
                KeyguardViewMediatorHelperImpl.access$notifyRemoteLockRequested(this.this$0, remoteLockInfo);
            }

            public final int checkRemoteLockPassword(byte[] bArr) {
                return 0;
            }
        };
        this.localReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$localReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                this.this$0.getClass();
                KeyguardViewMediatorHelperImpl.logD$1("onReceive: " + action);
                if ("com.samsung.keyguard.CLEAR_LOCK".equals(action)) {
                    this.this$0.adjustStatusBarLocked$2();
                    this.this$0.resetStateLocked$2();
                }
            }
        };
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int userId = ((UserTrackerImpl) this.this$0.userTracker).getUserId();
                if ("com.samsung.pen.INSERT".equals(intent.getAction())) {
                    boolean booleanExtra = intent.getBooleanExtra("penInsert", true);
                    this.this$0.getClass();
                    KeyguardViewMediatorHelperImpl.logD$1("ACTION_PEN_INSERT intent is received. penInsert=" + booleanExtra);
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                    if (KeyguardViewMediatorHelperImpl.access$canBeDismissedWhenSpenDetached(keyguardViewMediatorHelperImpl, intent, keyguardViewMediatorHelperImpl.isSecure$2(), booleanExtra)) {
                        Handler handler$1 = this.this$0.getHandler$1();
                        final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this.this$0;
                        handler$1.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$broadcastReceiver$1$onReceive$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_SPEN_DETACHED);
                                ((KeyguardViewMediator) keyguardViewMediatorHelperImpl2.viewMediatorLazy.get()).dismiss(null, null);
                            }
                        }, 100L);
                        return;
                    }
                    return;
                }
                if ("com.samsung.internal.policy.impl.Keyguard.PCW_LOCKED".equals(intent.getAction())) {
                    this.this$0.updateMonitor.updateFMMLock(userId, false);
                    boolean zIsFMMLock = this.this$0.updateMonitor.isFMMLock();
                    this.this$0.getClass();
                    KeyguardViewMediatorHelperImpl.logD$1("ACTION_FMM_LOCKED is received isFMMLock : " + zIsFMMLock);
                    if (zIsFMMLock) {
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this.this$0;
                        KeyguardViewMediatorHelperImpl.access$notifyRemoteLockRequested(keyguardViewMediatorHelperImpl3, keyguardViewMediatorHelperImpl3.fmmLock);
                        return;
                    }
                    return;
                }
                if ("com.samsung.internal.policy.impl.Keyguard.PCW_UNLOCKED".equals(intent.getAction())) {
                    this.this$0.getHandler$1().removeMessages(VolteConstants.ErrorCode.CALL_SESSION_ABORT, this.this$0.fmmLock);
                    this.this$0.updateMonitor.updateFMMLock(userId, false);
                    boolean zIsFMMLock2 = this.this$0.updateMonitor.isFMMLock();
                    this.this$0.getClass();
                    KeyguardViewMediatorHelperImpl.logD$1("ACTION_FMM_UNLOCKED is received isFMMLock : " + zIsFMMLock2);
                    if (zIsFMMLock2 || !this.this$0.isShowing$1()) {
                        return;
                    }
                    if (this.this$0.isSecure$2()) {
                        this.this$0.resetStateLocked$2();
                    } else {
                        KeyguardUnlockInfo.setUnlockTriggerByRemoteLock(0);
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = this.this$0;
                        ((KeyguardViewMediator) keyguardViewMediatorHelperImpl4.viewMediatorLazy.get()).mViewMediatorCallback.keyguardDone(((UserTrackerImpl) keyguardViewMediatorHelperImpl4.userTracker).getUserId());
                    }
                    this.this$0.pm.wakeUp(SystemClock.uptimeMillis());
                    return;
                }
                if ("com.sec.android.FindingLostPhonePlus.SUBSCRIBE".equals(intent.getAction())) {
                    this.this$0.updateMonitor.updateCarrierLock(userId);
                    boolean zIsCarrierLock = this.this$0.updateMonitor.isCarrierLock();
                    this.this$0.getClass();
                    KeyguardViewMediatorHelperImpl.logD$1("ACTION_CARRIER_LOCK_SUBSCRIBE is received isCarrierLock : " + zIsCarrierLock);
                    if (zIsCarrierLock) {
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = this.this$0;
                        KeyguardViewMediatorHelperImpl.access$notifyRemoteLockRequested(keyguardViewMediatorHelperImpl5, keyguardViewMediatorHelperImpl5.carrierLock);
                        return;
                    }
                    return;
                }
                if ("com.sec.android.FindingLostPhonePlus.CANCEL".equals(intent.getAction())) {
                    this.this$0.getHandler$1().removeMessages(VolteConstants.ErrorCode.CALL_SESSION_ABORT, this.this$0.carrierLock);
                    this.this$0.updateMonitor.updateCarrierLock(userId);
                    boolean zIsCarrierLock2 = this.this$0.updateMonitor.isCarrierLock();
                    this.this$0.getClass();
                    KeyguardViewMediatorHelperImpl.logD$1("ACTION_CARRIER_LOCK_CANCEL is received isCarrierLock : " + zIsCarrierLock2);
                    if (zIsCarrierLock2) {
                        this.this$0.getClass();
                        Log.d("KeyguardViewMediator", "Carrier Lock is enabled");
                        return;
                    }
                    this.this$0.lockPatternUtils.saveRemoteLockPassword(1, (byte[]) null, userId);
                    if (this.this$0.isShowing$1()) {
                        if (this.this$0.isSecure$2()) {
                            this.this$0.resetStateLocked$2();
                        } else {
                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl6 = this.this$0;
                            ((KeyguardViewMediator) keyguardViewMediatorHelperImpl6.viewMediatorLazy.get()).mViewMediatorCallback.keyguardDone(((UserTrackerImpl) keyguardViewMediatorHelperImpl6.userTracker).getUserId());
                        }
                        this.this$0.pm.wakeUp(SystemClock.uptimeMillis());
                        return;
                    }
                    return;
                }
                if (LsRune.KEYGUARD_HOMEHUB && "android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("pogo_plugged", 0);
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl7 = this.this$0;
                    if (keyguardViewMediatorHelperImpl7.pogoPlugged != intExtra) {
                        keyguardViewMediatorHelperImpl7.pogoPlugged = intExtra;
                    }
                    if (keyguardViewMediatorHelperImpl7.pogoPlugged == 0 || !keyguardViewMediatorHelperImpl7.isShowing$1()) {
                        return;
                    }
                    if (!this.this$0.isSecure$2() || this.this$0.updateMonitor.getUserCanSkipBouncer(userId)) {
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl8 = this.this$0;
                        ((KeyguardViewMediator) keyguardViewMediatorHelperImpl8.viewMediatorLazy.get()).mViewMediatorCallback.keyguardDone(((UserTrackerImpl) keyguardViewMediatorHelperImpl8.userTracker).getUserId());
                        return;
                    }
                    return;
                }
                if ("com.samsung.intent.action.OMC_CHANGED".equals(intent.getAction())) {
                    Log.d("KeyguardViewMediator", "Update CscFeatures");
                    String str = CscRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
                    SemCscFeature semCscFeature = SemCscFeature.getInstance();
                    android.util.Log.d("CscRune", "updateCscFeature carrier text : " + CscRune.VALUE_CONFIG_CARRIER_TEXT_POLICY + " -> " + semCscFeature.getString("CscFeature_LockScreen_ConfigCarrierTextPolicy") + "\ncarrier security : " + CscRune.VALUE_CONFIG_CARRIER_SECURITY_POLICY + " -> " + semCscFeature.getString("CscFeature_LockScreen_ConfigCarrierSecurityPolicy") + "\ncarrier emergency : " + CscRune.VALUE_CONFIG_CARRIER_EMERGENCY_POLICY + " -> " + semCscFeature.getString("CscFeature_LockScreen_ConfigEmergencyCallPolicy"));
                    if (!semCscFeature.getString("CscFeature_LockScreen_ConfigCarrierTextPolicy").equals(CscRune.VALUE_CONFIG_CARRIER_TEXT_POLICY)) {
                        String string = semCscFeature.getString("CscFeature_LockScreen_ConfigCarrierTextPolicy");
                        CscRune.VALUE_CONFIG_CARRIER_TEXT_POLICY = string;
                        CscRune.SECURITY_USE_CDMA_CARD_TEXT = string.contains("UseCdmaCardText");
                        CscRune.SECURITY_EMERGENCY_BUTTON_KOR = CscRune.isDisplayUsimText();
                        CscRune.SECURITY_KOR_USIM_TEXT = CscRune.isDisplayUsimText();
                        CscRune.LOCKUI_BOTTOM_USIM_TEXT = CscRune.isDisplayUsimText();
                        CscRune.SECURITY_SKT_USIM_TEXT = CscRune.VALUE_CONFIG_CARRIER_TEXT_POLICY.contains("UseSKTSimText");
                        CscRune.SECURITY_KTT_USIM_TEXT = CscRune.VALUE_CONFIG_CARRIER_TEXT_POLICY.contains("UseKTTSimText");
                        CscRune.SECURITY_LGU_USIM_TEXT = CscRune.VALUE_CONFIG_CARRIER_TEXT_POLICY.contains("UseLGTSimText");
                        CscRune.LOCKUI_LGU_USIM_TEXT = CscRune.VALUE_CONFIG_CARRIER_TEXT_POLICY.contains("UseLGTSimText");
                        CscRune.KEYGUARD_DCM_LIVE_UX = CscRune.VALUE_CONFIG_CARRIER_TEXT_POLICY.contains("UseDCMSimLockText");
                    }
                    if (!semCscFeature.getString("CscFeature_LockScreen_ConfigCarrierSecurityPolicy").equals(CscRune.VALUE_CONFIG_CARRIER_SECURITY_POLICY)) {
                        String string2 = semCscFeature.getString("CscFeature_LockScreen_ConfigCarrierSecurityPolicy");
                        CscRune.VALUE_CONFIG_CARRIER_SECURITY_POLICY = string2;
                        CscRune.SECURITY_WARNING_WIPE_OUT_MESSAGE = string2.contains("FactoryResetProtectionWarning");
                        CscRune.SECURITY_VZW_INSTRUCTION = CscRune.VALUE_CONFIG_CARRIER_SECURITY_POLICY.contains("FactoryResetProtectionWarning");
                        CscRune.SECURITY_SIM_PERM_DISABLED = CscRune.VALUE_CONFIG_CARRIER_SECURITY_POLICY.contains("SupportSimPermanentDisable");
                    }
                    if (semCscFeature.getString("CscFeature_LockScreen_ConfigEmergencyCallPolicy").equals(CscRune.VALUE_CONFIG_CARRIER_EMERGENCY_POLICY)) {
                        return;
                    }
                    String string3 = semCscFeature.getString("CscFeature_LockScreen_ConfigEmergencyCallPolicy");
                    CscRune.VALUE_CONFIG_CARRIER_EMERGENCY_POLICY = string3;
                    CscRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE = string3.contains("DisableEmergencyCallWhenOffline");
                    CscRune.SECURITY_DIRECT_CALL_TO_ECC = CscRune.VALUE_CONFIG_CARRIER_EMERGENCY_POLICY.contains("DirectCall");
                }
            }
        };
        this.updateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$updateCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onFinishedGoingToSleep(int i3) {
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                if (keyguardViewMediatorHelperImpl.settingsHelper.isScreenOffMemoEnabled()) {
                    keyguardViewMediatorHelperImpl.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$updateCallback$1$onFinishedGoingToSleep$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Log.i("KeyguardViewMediator", "onFinishedGoingToSleep : ACTION_SNOTE_SCREEN_OFF");
                            keyguardViewMediatorHelperImpl.context.sendBroadcast(new Intent("com.samsung.android.snote.SCREEN_OFF").setPackage("com.samsung.android.app.notes"));
                        }
                    });
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPhoneStateChanged(int i3) {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                String str = (String) viewMediatorProvider.updatePhoneState.mo781invoke(null);
                String str2 = i3 != 0 ? i3 != 1 ? i3 != 2 ? null : TelephonyManager.EXTRA_STATE_OFFHOOK : TelephonyManager.EXTRA_STATE_RINGING : TelephonyManager.EXTRA_STATE_IDLE;
                KeyguardViewMediatorHelperImpl.logD$1("onPhoneStateChanged " + str + " > " + str2);
                if (str2 == null || Intrinsics.areEqual(str, str2)) {
                    return;
                }
                ViewMediatorProvider viewMediatorProvider2 = keyguardViewMediatorHelperImpl.viewMediatorProvider;
                (viewMediatorProvider2 != null ? viewMediatorProvider2 : null).updatePhoneState.mo781invoke(str2);
                dagger.Lazy lazy15 = keyguardViewMediatorHelperImpl.pluginAODManagerLazy;
                ((PluginAODManager) lazy15.get()).mCurrentPhoneState = i3;
                ((PluginAODManager) lazy15.get()).updateAnimateScreenOff();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
                this.this$0.adjustStatusBarLocked$2();
            }
        };
        this.knoxStateCallback = new KnoxStateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$knoxStateCallback$1
            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onDPMPasswordChanged() {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                keyguardViewMediatorHelperImpl.getClass();
                Log.d("KeyguardViewMediator", "received DevicePolicyManager.ACTION_DEVICE_POLICY_MANAGER_PASSWORD_CHANGED!!");
                if (keyguardViewMediatorHelperImpl.isShowing$1()) {
                    if (!((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).isSecure(((UserTrackerImpl) keyguardViewMediatorHelperImpl.userTracker).getUserId())) {
                        ((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).mViewMediatorCallback.keyguardDone(((UserTrackerImpl) keyguardViewMediatorHelperImpl.userTracker).getUserId());
                    } else {
                        keyguardViewMediatorHelperImpl.resetStateLocked$2();
                        keyguardViewMediatorHelperImpl.adjustStatusBarLocked$2();
                    }
                }
            }

            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onDoKeyguard(int i3) {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                keyguardViewMediatorHelperImpl.getClass();
                Log.d("KeyguardViewMediator", "received EnterpriseDeviceManager.ACTION_DO_KEYGUARD_INTERNAL!!");
                if (((UserTrackerImpl) keyguardViewMediatorHelperImpl.userTracker).getUserId() == i3) {
                    keyguardViewMediatorHelperImpl.doKeyguardLocked$2(null);
                }
            }

            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onEnableUCMLock() {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                keyguardViewMediatorHelperImpl.getClass();
                Log.d("KeyguardViewMediator", "received onEnableUCMLock!!");
                if (keyguardViewMediatorHelperImpl.isShowing$1()) {
                    keyguardViewMediatorHelperImpl.resetStateLocked$2();
                } else {
                    keyguardViewMediatorHelperImpl.doKeyguardLocked$2(null);
                }
            }

            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onUpdateAdminLock() {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                keyguardViewMediatorHelperImpl.getClass();
                Log.d("KeyguardViewMediator", "received onUpdateAdminLock!!");
                if (!((KnoxStateMonitorImpl) keyguardViewMediatorHelperImpl.knoxStateMonitor).isAdminLockEnabled()) {
                    keyguardViewMediatorHelperImpl.resetStateLocked$2();
                    return;
                }
                if (!keyguardViewMediatorHelperImpl.isShowing$1()) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("force_show", true);
                    keyguardViewMediatorHelperImpl.doKeyguardLocked$2(bundle);
                    return;
                }
                keyguardViewMediatorHelperImpl.removeMessage(((Number) keyguardViewMediatorHelperImpl.KEYGUARD_DONE$delegate.getValue()).intValue());
                keyguardViewMediatorHelperImpl.removeMessage(((Number) keyguardViewMediatorHelperImpl.HIDE$delegate.getValue()).intValue());
                if (((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).isHiding()) {
                    ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl.viewMediatorProvider;
                    if (viewMediatorProvider == null) {
                        viewMediatorProvider = null;
                    }
                    viewMediatorProvider.setHiding.mo781invoke(Boolean.FALSE);
                }
                keyguardViewMediatorHelperImpl.resetStateLocked$2();
            }
        };
        KeyguardDumpLog.logger = samsungServiceLogger;
        SecurityDumpLog.logger = samsungServiceLogger2;
        desktopManager.registerCallback(new DesktopManager.Callback() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl.1
            @Override // com.android.systemui.util.DesktopManager.Callback
            public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                if (semDesktopModeState == null) {
                    keyguardViewMediatorHelperImpl.getClass();
                    return;
                }
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = keyguardViewMediatorHelperImpl.updateMonitor;
                if (keyguardUpdateMonitor2.isRemoteLockMode()) {
                    KeyguardViewMediatorHelperImpl.logD$1("Need update for remoteLock " + keyguardUpdateMonitor2.getCurrentSecurityMode() + " " + semDesktopModeState);
                    if (semDesktopModeState.getState() != 0) {
                        return;
                    }
                    int enabled = semDesktopModeState.getEnabled();
                    int displayType = semDesktopModeState.getDisplayType();
                    if (displayType == 101 && enabled == 4) {
                        Log.d("KeyguardViewMediator", "DeX standalone enabled");
                        keyguardViewMediatorHelperImpl.resetStateLocked$2();
                    } else if (displayType == 0 && enabled == 2) {
                        Log.d("KeyguardViewMediator", "DeX mode disabled");
                        keyguardViewMediatorHelperImpl.resetStateLocked$2();
                    }
                }
            }
        });
        this.occludeAnimationRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$occludeAnimationRunner$1
            public final void onAnimationCancelled() {
                this.this$0.getClass();
                Log.d("KeyguardViewMediator", "Occlude animation cancelled by WM.");
            }

            public final void onAnimationStart(int i3, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                String str;
                KeyguardEditModeController keyguardEditModeController2;
                RemoteAnimationTarget remoteAnimationTarget;
                ActivityManager.RunningTaskInfo runningTaskInfo;
                this.this$0.lastOccludedApp = (remoteAnimationTargetArr == null || (remoteAnimationTarget = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(0, remoteAnimationTargetArr)) == null || (runningTaskInfo = remoteAnimationTarget.taskInfo) == null) ? null : runningTaskInfo.topActivity;
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                ComponentName componentName = keyguardViewMediatorHelperImpl.lastOccludedApp;
                if (componentName != null) {
                    String packageName = componentName.getPackageName();
                    String className = componentName.getClassName();
                    KeyguardEditModeController keyguardEditModeController3 = keyguardViewMediatorHelperImpl.editModeController;
                    str = packageName + "/" + className + ", edit=" + (keyguardEditModeController3 != null ? Boolean.valueOf(((KeyguardEditModeControllerImpl) keyguardEditModeController3).isEditMode) : null);
                } else {
                    str = null;
                }
                KeyguardViewMediatorHelperImpl.logD$1("occludeAnimationRunner app=" + str);
                ComponentName componentName2 = this.this$0.lastOccludedApp;
                if (!BriefNowBarController.SUGGESTION_ACTIVITY.equals(componentName2 != null ? componentName2.getClassName() : null)) {
                    ComponentName componentName3 = this.this$0.lastOccludedApp;
                    if (!BriefNowBarController.SUGGESTION_ONBOARDING_ACTIVITY.equals(componentName3 != null ? componentName3.getClassName() : null)) {
                        if (this.this$0.isScreenOn()) {
                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this.this$0;
                            if ((keyguardViewMediatorHelperImpl2.lastOccludedApp != null || (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && keyguardViewMediatorHelperImpl2.isScreenOnByFoldOpen)) && (((keyguardEditModeController2 = keyguardViewMediatorHelperImpl2.editModeController) == null || !((KeyguardEditModeControllerImpl) keyguardEditModeController2).isEditMode) && !((KeyguardStateControllerImpl) keyguardViewMediatorHelperImpl2.stateController).mOccluded)) {
                                if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                                    keyguardViewMediatorHelperImpl2.isScreenOnByFoldOpen = false;
                                }
                                SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                                ((KeyguardSurfaceControllerImpl) this.this$0.surfaceControllerLazy.get()).setKeyguardSurfaceAppearAmount(transaction, true);
                                transaction.apply();
                                transaction.close();
                            }
                        }
                        ((KeyguardViewMediator) this.this$0.viewMediatorLazy.get()).setOccluded(true, false);
                        if (iRemoteAnimationFinishedCallback != null) {
                            iRemoteAnimationFinishedCallback.onAnimationFinished();
                            return;
                        }
                        return;
                    }
                }
                BriefNowBarController briefNowBarController = (BriefNowBarController) this.this$0.briefNowBarControllerLazy.get();
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this.this$0;
                if (briefNowBarController.startCircleAnimation(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$occludeAnimationRunner$1$onAnimationStart$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        keyguardViewMediatorHelperImpl3.getClass();
                        Log.d("KeyguardViewMediator", "occludeAnimationRunner finished circle animation");
                        KeyguardSurfaceControllerImpl.setKeyguardSurfaceAppearAmount$default((KeyguardSurfaceControllerImpl) keyguardViewMediatorHelperImpl3.surfaceControllerLazy.get(), null, 6);
                        ((KeyguardViewMediator) keyguardViewMediatorHelperImpl3.viewMediatorLazy.get()).setOccluded(true, false);
                        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                        if (iRemoteAnimationFinishedCallback2 != null) {
                            iRemoteAnimationFinishedCallback2.onAnimationFinished();
                        }
                    }
                })) {
                    return;
                }
                ((KeyguardViewMediator) this.this$0.viewMediatorLazy.get()).setOccluded(true, false);
                if (iRemoteAnimationFinishedCallback != null) {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                }
            }
        };
        this.unoccluedAnimationRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$unoccluedAnimationRunner$1
            public final void onAnimationCancelled() {
                this.this$0.getClass();
                Log.d("KeyguardViewMediator", "Unocclude animation cancelled.");
            }

            /* JADX WARN: Type inference failed for: r11v10, types: [T, android.view.SurfaceControl$Transaction] */
            public final void onAnimationStart(int i3, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                ComponentName componentName;
                ActivityManager.RunningTaskInfo runningTaskInfo;
                RemoteAnimationTarget remoteAnimationTarget;
                ActivityManager.RunningTaskInfo runningTaskInfo2;
                ActivityManager.RunningTaskInfo runningTaskInfo3;
                if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0 || remoteAnimationTargetArr[0] == null) {
                    Log.d("KeyguardViewMediator", "No apps provided to unocclude runner; skipping animation and unoccluding.");
                    if (iRemoteAnimationFinishedCallback != null) {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        return;
                    }
                    return;
                }
                RemoteAnimationTarget remoteAnimationTarget2 = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(0, remoteAnimationTargetArr);
                if (remoteAnimationTarget2 == null || (runningTaskInfo3 = remoteAnimationTarget2.taskInfo) == null || (componentName = runningTaskInfo3.topActivity) == null) {
                    RemoteAnimationTarget remoteAnimationTarget3 = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(0, remoteAnimationTargetArr);
                    componentName = (remoteAnimationTarget3 == null || (runningTaskInfo = remoteAnimationTarget3.taskInfo) == null) ? null : runningTaskInfo.realActivity;
                }
                boolean z = componentName == null || componentName.equals(this.this$0.lastOccludedApp);
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                String strM = componentName != null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(componentName.getPackageName(), "/", componentName.getClassName()) : null;
                boolean zIsScreenOn = this.this$0.isScreenOn();
                StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("UnoccluedAnimationRunner app=", strM, " keepLeash=", ", isScreenOn()=", z);
                sbM.append(zIsScreenOn);
                String string = sbM.toString();
                keyguardViewMediatorHelperImpl.getClass();
                KeyguardViewMediatorHelperImpl.logD$1(string);
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this.this$0;
                keyguardViewMediatorHelperImpl2.lastOccludedApp = null;
                ((KeyguardViewMediator) keyguardViewMediatorHelperImpl2.viewMediatorLazy.get()).setOccluded(false, false);
                RemoteAnimationTarget remoteAnimationTarget4 = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(0, remoteAnimationTargetArr);
                boolean z2 = ((remoteAnimationTarget4 != null ? remoteAnimationTarget4.taskInfo : null) == null || (remoteAnimationTarget = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(0, remoteAnimationTargetArr)) == null || (runningTaskInfo2 = remoteAnimationTarget.taskInfo) == null || runningTaskInfo2.topActivityType != 5) ? false : true;
                RemoteAnimationTarget remoteAnimationTarget5 = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(0, remoteAnimationTargetArr);
                if (remoteAnimationTarget5 != null) {
                    ViewMediatorProvider viewMediatorProvider = this.this$0.viewMediatorProvider;
                    if (viewMediatorProvider == null) {
                        viewMediatorProvider = null;
                    }
                    viewMediatorProvider.setRemoteAnimationTarget.mo781invoke(remoteAnimationTarget5);
                }
                Log.d("KeyguardViewMediator", "unoccludeAnimationRunner: isDream=" + z2);
                if (z2) {
                    ViewMediatorProvider viewMediatorProvider2 = this.this$0.viewMediatorProvider;
                    if (viewMediatorProvider2 == null) {
                        viewMediatorProvider2 = null;
                    }
                    viewMediatorProvider2.initAlphaForAnimationTargets.mo781invoke(remoteAnimationTargetArr2);
                    ((DreamViewModel) this.this$0.dreamViewModelLazy.get()).startTransitionFromDream();
                    if (iRemoteAnimationFinishedCallback != null) {
                        ViewMediatorProvider viewMediatorProvider3 = this.this$0.viewMediatorProvider;
                        (viewMediatorProvider3 != null ? viewMediatorProvider3 : null).setUnoccludeFinishedCallback.mo781invoke(iRemoteAnimationFinishedCallback);
                        return;
                    }
                    return;
                }
                RemoteAnimationTarget remoteAnimationTarget6 = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(0, remoteAnimationTargetArr);
                SurfaceControl surfaceControl = remoteAnimationTarget6 != null ? remoteAnimationTarget6.leash : null;
                if (surfaceControl == null || iRemoteAnimationFinishedCallback == null) {
                    if (iRemoteAnimationFinishedCallback != null) {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        return;
                    }
                    return;
                }
                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                if (!z && !this.this$0.isScreenOn()) {
                    ?? transaction = new SurfaceControl.Transaction();
                    transaction.setAlpha(surfaceControl, 0.0f);
                    transaction.apply();
                    ref$ObjectRef.element = transaction;
                }
                KeyguardEditModeController keyguardEditModeController2 = this.this$0.editModeController;
                long j = (keyguardEditModeController2 == null || !((KeyguardEditModeControllerImpl) keyguardEditModeController2).isEditMode) ? 50L : 250L;
                KeyguardViewMediatorHelperImpl.logD$1("keepSurfaceDuration=" + j);
                this.this$0.getHandler$1().postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$unoccluedAnimationRunner$1$onAnimationStart$5
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.lang.Runnable
                    public final void run() {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) ref$ObjectRef.element;
                        if (transaction2 != null) {
                            transaction2.close();
                        }
                    }
                }, j);
            }
        };
        this.aodAppearAnimationRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$aodAppearAnimationRunner$1
            public final void onAnimationCancelled() {
                this.this$0.getClass();
                Log.d("KeyguardViewMediator", "AOD Appear animation cancelled by WM.");
                ValueAnimator valueAnimator = this.this$0.aodAppearAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
            }

            /* JADX WARN: Type inference failed for: r4v0, types: [T, android.view.RemoteAnimationTarget] */
            public final void onAnimationStart(int i3, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                boolean z = keyguardViewMediatorHelperImpl.needKeyguardAppearAnimation;
                keyguardViewMediatorHelperImpl.needKeyguardAppearAnimation = false;
                if (KeyguardViewMediatorHelperImplKt.IS_SAFE_MODE_ENABLED) {
                    Log.d("KeyguardViewMediator", "handleOnAnimationStart: return in SystemUI safe mode.");
                } else if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0) {
                    Log.d("KeyguardViewMediator", "handleOnAnimationStart: No apps provided to the Appear Animation runner; skipping appear animation.");
                } else {
                    boolean zShouldPlayUnlockedScreenOffAnimation = keyguardViewMediatorHelperImpl.unlockedScreenOffAnimationHelper.shouldPlayUnlockedScreenOffAnimation();
                    Log.d("KeyguardViewMediator", "handleOnAnimationStart: shouldPlayUnlockedScreenOffAnimation=" + zShouldPlayUnlockedScreenOffAnimation + ", playKeyguardAppearAnimation=" + z);
                    if (zShouldPlayUnlockedScreenOffAnimation || z) {
                        final List mutableList = ArraysKt___ArraysKt.toMutableList(remoteAnimationTargetArr);
                        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                        final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                        KeyguardViewMediatorHelperImplKt.aodAppearWallpaperOpeningTarget = null;
                        if (remoteAnimationTargetArr2 != 0) {
                            int length = remoteAnimationTargetArr2.length;
                            for (int i4 = 0; i4 < length; i4++) {
                                ?? r4 = remoteAnimationTargetArr2[i4];
                                Integer numValueOf = r4 != 0 ? Integer.valueOf(((RemoteAnimationTarget) r4).mode) : null;
                                if (numValueOf != null && numValueOf.intValue() == 0) {
                                    ref$ObjectRef.element = r4;
                                    KeyguardViewMediatorHelperImplKt.aodAppearWallpaperOpeningTarget = r4;
                                } else if (numValueOf != null && numValueOf.intValue() == 1) {
                                    ref$ObjectRef2.element = r4;
                                }
                            }
                        }
                        Log.d("KeyguardViewMediator", "aodAppearAnimationRunner handleOnAnimationStart");
                        keyguardViewMediatorHelperImpl.unlockAnimationExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$handleOnAnimationStart$2
                            @Override // java.lang.Runnable
                            public final void run() {
                                ActivityManager.RunningTaskInfo runningTaskInfo;
                                ComponentName componentName;
                                ValueAnimator valueAnimator = keyguardViewMediatorHelperImpl.aodAppearAnimator;
                                if (valueAnimator != null) {
                                    valueAnimator.cancel();
                                }
                                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = keyguardViewMediatorHelperImpl;
                                keyguardViewMediatorHelperImpl2.aodAppearAnimationFrameCount = 0;
                                keyguardViewMediatorHelperImpl2.aodAppearAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                                final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                                RemoteAnimationTarget remoteAnimationTarget = (RemoteAnimationTarget) ref$ObjectRef2.element;
                                if (remoteAnimationTarget != null) {
                                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = keyguardViewMediatorHelperImpl;
                                    List<RemoteAnimationTarget> list = mutableList;
                                    keyguardViewMediatorHelperImpl3.getClass();
                                    if (list != null) {
                                        for (RemoteAnimationTarget remoteAnimationTarget2 : list) {
                                            if (Intrinsics.areEqual((remoteAnimationTarget2 == null || (runningTaskInfo = remoteAnimationTarget2.taskInfo) == null || (componentName = runningTaskInfo.topActivity) == null) ? null : componentName.getClassName(), "com.sec.android.app.launcher.Launcher")) {
                                                break;
                                            }
                                        }
                                        SurfaceControl surfaceControl = remoteAnimationTarget.leash;
                                        surfaceControl.getClass();
                                        transaction.setAlpha(surfaceControl, 0.0f);
                                        transaction.apply();
                                    } else {
                                        SurfaceControl surfaceControl2 = remoteAnimationTarget.leash;
                                        surfaceControl2.getClass();
                                        transaction.setAlpha(surfaceControl2, 0.0f);
                                        transaction.apply();
                                    }
                                }
                                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = keyguardViewMediatorHelperImpl;
                                ValueAnimator valueAnimator2 = keyguardViewMediatorHelperImpl4.aodAppearAnimator;
                                if (valueAnimator2 != null) {
                                    final List list2 = mutableList;
                                    final Ref$ObjectRef ref$ObjectRef3 = ref$ObjectRef;
                                    final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                                    valueAnimator2.setDuration(250L);
                                    valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$handleOnAnimationStart$2$2$1
                                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                        public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                                            SurfaceControl.Transaction transaction2 = transaction;
                                            List<RemoteAnimationTarget> list3 = list2;
                                            Ref$ObjectRef ref$ObjectRef4 = ref$ObjectRef3;
                                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = keyguardViewMediatorHelperImpl4;
                                            for (RemoteAnimationTarget remoteAnimationTarget3 : list3) {
                                                SurfaceControl surfaceControl3 = remoteAnimationTarget3 != null ? remoteAnimationTarget3.leash : null;
                                                surfaceControl3.getClass();
                                                transaction2.setAlpha(surfaceControl3, 1 - ((Float) valueAnimator3.getAnimatedValue()).floatValue());
                                            }
                                            T t = ref$ObjectRef4.element;
                                            if (t != 0) {
                                                t.getClass();
                                                transaction2.setAlpha(((RemoteAnimationTarget) t).leash, ((Float) valueAnimator3.getAnimatedValue()).floatValue());
                                            }
                                            transaction2.apply();
                                            keyguardViewMediatorHelperImpl5.aodAppearAnimationFrameCount++;
                                        }
                                    });
                                    valueAnimator2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$handleOnAnimationStart$2$2$2
                                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                        public final void onAnimationCancel(Animator animator) {
                                            super.onAnimationCancel(animator);
                                            Log.i("KeyguardViewMediator", "aodAppearAnimator onAnimationCancel");
                                            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback3 = iRemoteAnimationFinishedCallback2;
                                            if (iRemoteAnimationFinishedCallback3 != null) {
                                                iRemoteAnimationFinishedCallback3.onAnimationFinished();
                                            }
                                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = keyguardViewMediatorHelperImpl4;
                                            keyguardViewMediatorHelperImpl5.aodAppearAnimationFrameCount = 0;
                                            keyguardViewMediatorHelperImpl5.aodAppearAnimator = null;
                                        }

                                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                        public final void onAnimationEnd(Animator animator) {
                                            super.onAnimationEnd(animator);
                                            Log.i("KeyguardViewMediator", "aodAppearAnimator onAnimationEnd aodAppearAnimationFrameCount=" + keyguardViewMediatorHelperImpl4.aodAppearAnimationFrameCount);
                                            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback3 = iRemoteAnimationFinishedCallback2;
                                            if (iRemoteAnimationFinishedCallback3 != null) {
                                                iRemoteAnimationFinishedCallback3.onAnimationFinished();
                                            }
                                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = keyguardViewMediatorHelperImpl4;
                                            keyguardViewMediatorHelperImpl5.aodAppearAnimationFrameCount = 0;
                                            keyguardViewMediatorHelperImpl5.aodAppearAnimator = null;
                                        }
                                    });
                                    valueAnimator2.start();
                                }
                            }
                        });
                        return;
                    }
                    Log.d("KeyguardViewMediator", "handleOnAnimationStart: return do not play animation");
                }
                if (iRemoteAnimationFinishedCallback != null) {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                }
            }
        };
    }

    public static final boolean access$canBeDismissedWhenSpenDetached(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, Intent intent, boolean z, boolean z2) {
        keyguardViewMediatorHelperImpl.getClass();
        boolean z3 = false;
        boolean booleanExtra = intent.getBooleanExtra("isBoot", false);
        dagger.Lazy lazy = keyguardViewMediatorHelperImpl.viewMediatorLazy;
        boolean zIsScreenOn = ((KeyguardViewMediator) lazy.get()).getViewMediatorCallback().isScreenOn();
        boolean zIsShowingAndNotOccluded = ((KeyguardViewMediator) lazy.get()).isShowingAndNotOccluded();
        boolean zIsScreenOffMemoRunning = keyguardViewMediatorHelperImpl.updateMonitor.isScreenOffMemoRunning();
        boolean zHasPenDetachmentOption = keyguardViewMediatorHelperImpl.settingsHelper.hasPenDetachmentOption();
        if (zIsShowingAndNotOccluded && !z && !z2 && zIsScreenOn && !booleanExtra && !zIsScreenOffMemoRunning && zHasPenDetachmentOption) {
            z3 = true;
        }
        if (!z3 && zIsShowingAndNotOccluded && !z && !z2 && zIsScreenOn) {
            StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("canBeDismissedWhenSpenDetached isBoot=", ", isScreenOffMemoRunning=", ", hasPenDetachOpt=", booleanExtra, zIsScreenOffMemoRunning);
            sbM.append(zHasPenDetachmentOption);
            logD$1(sbM.toString());
        }
        return z3;
    }

    public static final void access$notifyRemoteLockRequested(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, Object obj) {
        keyguardViewMediatorHelperImpl.removeMessage(VolteConstants.ErrorCode.CALL_SESSION_ABORT);
        Message messageObtainMessage = keyguardViewMediatorHelperImpl.getHandler$1().obtainMessage(VolteConstants.ErrorCode.CALL_SESSION_ABORT, obj);
        if (obj instanceof RemoteLockInfo) {
            keyguardViewMediatorHelperImpl.getHandler$1().sendMessage(messageObtainMessage);
        } else {
            keyguardViewMediatorHelperImpl.getHandler$1().sendMessageDelayed(messageObtainMessage, 100L);
        }
    }

    public static void logD$1(String str) {
        if (str != null) {
            Log.d("KeyguardViewMediator", str);
        }
    }

    public final void adjustStatusBarLocked$2() {
        ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
        if (viewMediatorProvider == null) {
            viewMediatorProvider = null;
        }
        viewMediatorProvider.adjustStatusBarLocked.invoke();
    }

    public final void cancelLockWhenCoverIsOpened(boolean z) {
        PendingIntent pendingIntent = this.doKeyguardPendingIntent;
        if (pendingIntent != null) {
            logD$1("cancelLockWhenCoverIsOpened " + pendingIntent);
            ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
            if (viewMediatorProvider == null) {
                viewMediatorProvider = null;
            }
            AlarmManager alarmManager = (AlarmManager) viewMediatorProvider.alarmManager.invoke();
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
            }
            this.doKeyguardPendingIntent = null;
            if (z) {
                ViewMediatorProvider viewMediatorProvider2 = this.viewMediatorProvider;
                (viewMediatorProvider2 != null ? viewMediatorProvider2 : null).increaseDelayedShowingSeq.invoke();
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        this.disabled1 = i2;
    }

    public final void doKeyguardLocked$2(Bundle bundle) {
        ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
        if (viewMediatorProvider == null) {
            viewMediatorProvider = null;
        }
        viewMediatorProvider.doKeyguardLocked.mo781invoke(bundle);
    }

    public final void enableLooperLogController(int i, long j) {
        if (LogUtil.isDebugLevelMid() || LogUtil.isDebugLevelHigh()) {
            ((LooperSlowLogControllerImpl) this.looperLogController).enable(i, 10L, 20L, j, false, null);
        }
    }

    public final IStatusBarService getBarService() {
        if (this.barService == null) {
            this.barService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        }
        return this.barService;
    }

    public final Handler getHandler$1() {
        return (Handler) this.handler$delegate.getValue();
    }

    public final int getSET_OCCLUDED() {
        return ((Number) this.SET_OCCLUDED$delegate.getValue()).intValue();
    }

    public final int getSHOW() {
        return ((Number) this.SHOW$delegate.getValue()).intValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:323:0x0632, code lost:
    
        if (r19.fastUnlockController.isFastWakeAndUnlockMode() == false) goto L324;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:259:0x04e8  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x016f  */
    /* JADX WARN: Type inference failed for: r10v7, types: [T, android.view.SurfaceControl] */
    /* JADX WARN: Type inference failed for: r11v9, types: [T, android.view.SurfaceControl] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleSecMessage(Message message) {
        Display display;
        boolean z;
        int i;
        boolean z2;
        SurfaceControl surfaceControl;
        boolean z3;
        KeyguardFixedRotationMonitor keyguardFixedRotationMonitor;
        boolean z4;
        Pair pair;
        boolean zBooleanValue;
        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback;
        RemoteAnimationTarget[] remoteAnimationTargetArr;
        boolean z5;
        boolean z6;
        int i2 = message.what;
        if (i2 == 1101) {
            Object obj = message.obj;
            Log.d("KeyguardViewMediator", "handleRemoteLockRequested");
            if (!(obj instanceof RemoteLockInfo) || ((RemoteLockInfo) obj).lockState) {
                if (isShowing$1()) {
                    resetStateLocked$2();
                } else {
                    doKeyguardLocked$2(null);
                }
                this.pm.wakeUp(SystemClock.uptimeMillis());
                return;
            }
            if (this.updateMonitor.getRemoteLockType() == -1 && ((KeyguardViewMediator) this.viewMediatorLazy.get()).isSecure()) {
                resetStateLocked$2();
                return;
            }
            return;
        }
        if (i2 == 1102) {
            Log.d("KeyguardViewMediator", "handleLaunchPersoLock");
            getHandler$1().removeMessages(VolteConstants.ErrorCode.CALL_SESSION_TERMINATED);
            synchronized (this.lock$delegate.getValue()) {
                try {
                    if (isShowing$1()) {
                        removeMessage(((Number) this.KEYGUARD_DONE$delegate.getValue()).intValue());
                        removeMessage(((Number) this.HIDE$delegate.getValue()).intValue());
                        if (((KeyguardViewMediator) this.viewMediatorLazy.get()).isHiding()) {
                            ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
                            (viewMediatorProvider != null ? viewMediatorProvider : null).setHiding.mo781invoke(Boolean.FALSE);
                        }
                        resetStateLocked$2();
                    } else {
                        Log.d("KeyguardViewMediator", "doKeyguardLocked");
                        removeMessage(((Number) this.KEYGUARD_DONE_DRAWING$delegate.getValue()).intValue());
                        doKeyguardLocked$2(null);
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return;
        }
        if (i2 == 1201) {
            Log.d("KeyguardViewMediator", "handleBootCompleted");
            if (LsRune.SUBSCREEN_UI) {
                final SubScreenManager subScreenManager = this.subScreenManager;
                subScreenManager.getClass();
                android.util.Log.d("SubScreenManager", "onBootCompleted() ");
                if (subScreenManager.mSubDisplay == null) {
                    android.util.Log.d("SubScreenManager", "init() ");
                    Display[] displays = subScreenManager.mDisplayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
                    if (displays != null) {
                        RecyclerView$$ExternalSyntheticOutline0.m(displays.length, "SubScreenManager", new StringBuilder("getSubDisplay() : length "));
                        for (Display display2 : displays) {
                            if (display2 == null) {
                                android.util.Log.i("SubScreenManager", "Do not show SubScreen UI on null display");
                            } else {
                                if (display2.getDisplayId() == 1) {
                                    android.util.Log.i("SubScreenManager", "Show SubScreen UI on this display " + display2);
                                    display = display2;
                                    break;
                                }
                                android.util.Log.i("SubScreenManager", "Do not show SubScreen UI on this display " + display2);
                            }
                        }
                        display = null;
                    } else {
                        display = null;
                    }
                    subScreenManager.mSubDisplay = display;
                    subScreenManager.initWindow();
                    subScreenManager.mDumpManager.registerNormalDumpable("SubScreenManager", subScreenManager);
                    if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                        subScreenManager.mNotifPipeline.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.subscreen.SubScreenManager.6
                            public AnonymousClass6() {
                            }

                            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
                            public final void onEntryAdded(NotificationEntry notificationEntry) {
                                PluginSubScreen pluginSubScreen = SubScreenManager.this.mSubScreenPlugin;
                                if (pluginSubScreen == null) {
                                    Log.w("SubScreenManager", "onEntryAdded() no plugin");
                                } else {
                                    pluginSubScreen.onEntryAdded(notificationEntry.mKey, notificationEntry.mSbn);
                                }
                            }

                            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
                            public final void onEntryRemoved(NotificationEntry notificationEntry, int i3) {
                                PluginSubScreen pluginSubScreen = SubScreenManager.this.mSubScreenPlugin;
                                if (pluginSubScreen == null) {
                                    Log.w("SubScreenManager", "onEntryRemoved() no plugin");
                                } else {
                                    pluginSubScreen.onEntryRemoved(notificationEntry.mSbn, i3);
                                }
                            }

                            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
                            public final void onEntryUpdated(NotificationEntry notificationEntry, UpdateSource updateSource) {
                                PluginSubScreen pluginSubScreen = SubScreenManager.this.mSubScreenPlugin;
                                if (pluginSubScreen == null) {
                                    Log.w("SubScreenManager", " onEntryUpdated () no plugin");
                                } else {
                                    pluginSubScreen.onEntryUpdated(notificationEntry.mSbn);
                                }
                            }
                        });
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        if (i2 == 1301) {
            try {
                IStatusBarService barService = getBarService();
                if (barService != null) {
                    int i3 = this.disableFlags;
                    CharsKt__CharJVMKt.checkRadix(16);
                    logD$1("adjustStatusBarLocked - ADJUST_STATUS_BAR : flags=0x" + Integer.toString(i3, 16));
                    barService.disable(this.disableFlags, this.token, this.context.getPackageName());
                    return;
                }
                return;
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "adjustStatusBarLocked - ADJUST_STATUS_BAR - disable failed", e);
                KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.WARNING, "adjustStatusBarLocked - ADJUST_STATUS_BAR - disable failed", e);
                return;
            }
        }
        switch (i2) {
            case 1001:
                handleSetPendingIntentAfterUnlock(message.getData());
                return;
            case 1002:
                Object obj2 = message.obj;
                IKeyguardDrawnCallback iKeyguardDrawnCallback = obj2 != null ? (IKeyguardDrawnCallback) obj2 : null;
                if (iKeyguardDrawnCallback == null) {
                    if (this.fastUnlockController.isFastWakeAndUnlockMode()) {
                        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.fastUnlockController;
                        int mode = keyguardFastBioUnlockController.getMode();
                        int i4 = KeyguardFastBioUnlockController.MODE_FLAG_ENABLED;
                        if (keyguardFastBioUnlockController.isMode(i4) && (KeyguardFastBioUnlockController.MODE_FLAG_FRAME_COMMIT & mode) == 0) {
                            int i5 = KeyguardFastBioUnlockController.MODE_FLAG_FRAME_REQUEST;
                            if ((mode & i5) != i5) {
                            }
                            Log.d("KeyguardViewMediator", "handleNotifyScreenTurningOn");
                            notifyDrawn();
                            return;
                        }
                        KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = this.fastUnlockController;
                        int mode2 = keyguardFastBioUnlockController2.getMode();
                        if (keyguardFastBioUnlockController2.isMode(i4)) {
                            int i6 = KeyguardFastBioUnlockController.MODE_FLAG_FRAME_COMMIT;
                            z = (mode2 & i6) == i6;
                            if (!z) {
                            }
                            Log.d("KeyguardViewMediator", "handleNotifyScreenTurningOn");
                            notifyDrawn();
                            return;
                        }
                        if (!z) {
                        }
                        Log.d("KeyguardViewMediator", "handleNotifyScreenTurningOn");
                        notifyDrawn();
                        return;
                    }
                    break;
                }
                synchronized (this.lock$delegate.getValue()) {
                    try {
                        boolean zHasMessages = getHandler$1().hasMessages(getSHOW());
                        boolean z7 = ((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).mMode != 0;
                        long jElapsedRealtime = this.lastShowingTime != 0 ? SystemClock.elapsedRealtime() - this.lastShowingTime : 0L;
                        boolean zIsFastWakeAndUnlockMode = this.fastUnlockController.isFastWakeAndUnlockMode();
                        ViewMediatorProvider viewMediatorProvider2 = this.viewMediatorProvider;
                        if (viewMediatorProvider2 == null) {
                            viewMediatorProvider2 = null;
                        }
                        boolean zBooleanValue2 = ((Boolean) viewMediatorProvider2.hasPendingLock.invoke()).booleanValue();
                        logD$1("handleNotifyScreenTurningOn fastWakeUnlockMode=" + zIsFastWakeAndUnlockMode + ", bioUnlock=" + z7 + " hasShow=" + zHasMessages + ", pendingLock=" + zBooleanValue2 + ", hasCb=" + (iKeyguardDrawnCallback != null) + ", interval=" + jElapsedRealtime + ", hasOccluded=" + hasOccludedMsg$1());
                        if (iKeyguardDrawnCallback == null) {
                            return;
                        }
                        if ((!this.fastUnlockController.needsBlankScreen && zIsFastWakeAndUnlockMode) || (!z7 && (zHasMessages || zBooleanValue2))) {
                            this.drawnCallback = iKeyguardDrawnCallback;
                        } else if (LsRune.KEYGUARD_DELAY_NOTIFY_DRAWN_PREMIUM_WATCH && !this.foldControllerImpl.isFoldOpened() && this.settingsHelper.isPremiumWatchEnabled() && ((hasOccludedMsg$1() || !((KeyguardViewMediator) this.viewMediatorLazy.get()).isShowingAndNotOccluded()) && this.keyguardVisibilityMonitor.isVisible())) {
                            this.drawnCallback = iKeyguardDrawnCallback;
                            this.keyguardVisibilityMonitor.addVisibilityChangedListener(new KeyguardViewMediatorHelperImplKt$sam$java_util_function_IntConsumer$0((Function1) this.visibilityListener));
                            Log.d("KeyguardViewMediator", "delayed notifyDrawn caused by occluded");
                            return;
                        } else {
                            if (jElapsedRealtime < 200 && !z7) {
                                this.drawnCallback = iKeyguardDrawnCallback;
                                getHandler$1().post(this.delayedDrawnRunnable);
                                Log.d("KeyguardViewMediator", "delayed notifyDrawn");
                                return;
                            }
                            notifyDrawn(iKeyguardDrawnCallback);
                        }
                        Unit unit2 = Unit.INSTANCE;
                        return;
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
            case 1003:
                if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                    i = 1;
                    if (message.arg1 == 1) {
                        this.handleFoldOpenMsg = true;
                    }
                } else {
                    i = 1;
                }
                KeyguardFoldControllerImpl keyguardFoldControllerImpl = this.foldControllerImpl;
                keyguardFoldControllerImpl.getClass();
                boolean z8 = message.arg1 == i;
                boolean z9 = message.arg2 == 0;
                KeyguardFoldControllerDependency keyguardFoldControllerDependency = keyguardFoldControllerImpl.dependency;
                String str = "handleFoldMessage: isOpened=" + z8 + ", showing=" + keyguardFoldControllerImpl.getViewMediator().isShowingAndNotOccluded();
                ((KeyguardFoldControllerDependencyImpl) keyguardFoldControllerDependency).getClass();
                Log.d("KeyguardFoldController", str);
                if (!z9) {
                    ((KeyguardFoldControllerConfigImpl) keyguardFoldControllerImpl.foldConfig).getClass();
                    if (!LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
                        ((KeyguardFoldControllerConfigImpl) keyguardFoldControllerImpl.foldConfig).getClass();
                        if (LsRune.SUBSCREEN_WATCHFACE) {
                            ((KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) keyguardFoldControllerImpl.viewMediatorHelper$delegate.getValue())).adjustStatusBarLocked$2();
                        }
                    } else if (z8) {
                        KeyguardViewMediator viewMediator = keyguardFoldControllerImpl.getViewMediator();
                        KeyguardFoldControllerDependency keyguardFoldControllerDependency2 = keyguardFoldControllerImpl.dependency;
                        KeyguardUnlockInfo.UnlockTrigger unlockTrigger = KeyguardUnlockInfo.UnlockTrigger.TRIGGER_FOLD_OPENED;
                        ((KeyguardFoldControllerDependencyImpl) keyguardFoldControllerDependency2).getClass();
                        KeyguardUnlockInfo.setUnlockTrigger(unlockTrigger);
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = viewMediator.mHelper;
                        if (keyguardViewMediatorHelperImpl.getHandler$1().hasMessages(keyguardViewMediatorHelperImpl.getSHOW())) {
                            keyguardFoldControllerImpl.setFoldOpenState(1);
                            viewMediator.dismiss(null, null);
                        } else if (viewMediator.isShowingAndNotOccluded()) {
                            ((KeyguardFoldControllerConfigImpl) keyguardFoldControllerImpl.foldConfig).getClass();
                            if (CscRune.SECURITY_SIM_PERM_DISABLED && keyguardFoldControllerImpl.updateMonitor.isIccBlockedPermanently()) {
                                ((KeyguardFoldControllerDependencyImpl) keyguardFoldControllerImpl.dependency).getClass();
                                Log.d("KeyguardFoldController", "dismiss failed. Permanent state.");
                            } else {
                                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = (KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) keyguardFoldControllerImpl.viewMediatorHelper$delegate.getValue());
                                synchronized (keyguardViewMediatorHelperImpl2.lock$delegate.getValue()) {
                                    if (keyguardViewMediatorHelperImpl2.hasOccludedMsg$1()) {
                                        z2 = true == keyguardViewMediatorHelperImpl2.curIsOccluded;
                                    }
                                }
                                if (z2) {
                                    ((KeyguardFoldControllerDependencyImpl) keyguardFoldControllerImpl.dependency).getClass();
                                    Log.d("KeyguardFoldController", "will handle occluded");
                                } else {
                                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = (KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) keyguardFoldControllerImpl.viewMediatorHelper$delegate.getValue());
                                    keyguardViewMediatorHelperImpl3.getHandler$1().removeMessages(((Number) keyguardViewMediatorHelperImpl3.RESET$delegate.getValue()).intValue());
                                    ViewMediatorProvider viewMediatorProvider3 = keyguardViewMediatorHelperImpl3.viewMediatorProvider;
                                    if (viewMediatorProvider3 == null) {
                                        viewMediatorProvider3 = null;
                                    }
                                    viewMediatorProvider3.resetPendingReset.invoke();
                                    boolean zIsSecure = keyguardFoldControllerImpl.getViewMediator().isSecure();
                                    if (!zIsSecure) {
                                        KeyguardFoldControllerDependency keyguardFoldControllerDependency3 = keyguardFoldControllerImpl.dependency;
                                        KeyguardSecurityModel.SecurityMode securityMode = KeyguardSecurityModel.SecurityMode.None;
                                        ((KeyguardFoldControllerDependencyImpl) keyguardFoldControllerDependency3).getClass();
                                        KeyguardUnlockInfo.setAuthDetail(securityMode);
                                    }
                                    boolean userCanSkipBouncer = keyguardFoldControllerImpl.updateMonitor.getUserCanSkipBouncer(keyguardFoldControllerImpl.selectedUserInteractor.getSelectedUserId());
                                    if (!zIsSecure || userCanSkipBouncer) {
                                        keyguardFoldControllerImpl.setFoldOpenState((!keyguardFoldControllerImpl.getViewMediator().getViewMediatorCallback().isScreenOn() || keyguardFoldControllerImpl.wakeReason == 9) ? 3 : 2);
                                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = (KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) keyguardFoldControllerImpl.viewMediatorHelper$delegate.getValue());
                                        keyguardViewMediatorHelperImpl4.goingAwayWithAnimation = false;
                                        ViewMediatorProvider viewMediatorProvider4 = keyguardViewMediatorHelperImpl4.viewMediatorProvider;
                                        (viewMediatorProvider4 != null ? viewMediatorProvider4 : null).tryKeyguardDone.invoke();
                                    } else {
                                        keyguardFoldControllerImpl.setFoldOpenState(1);
                                        ((KeyguardViewController) keyguardFoldControllerImpl.viewControllerLazy.get()).folderOpenAndDismiss();
                                    }
                                }
                            }
                        } else {
                            viewMediator.maybeHandlePendingLock();
                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = viewMediator.mHelper;
                            if (keyguardViewMediatorHelperImpl5.getHandler$1().hasMessages(keyguardViewMediatorHelperImpl5.getSHOW())) {
                                keyguardFoldControllerImpl.setFoldOpenState(1);
                                viewMediator.dismiss(null, null);
                            }
                        }
                    } else {
                        KeyguardViewMediator viewMediator2 = keyguardFoldControllerImpl.getViewMediator();
                        if (viewMediator2.isShowingAndNotOccluded()) {
                            viewMediator2.getViewMediatorCallback().resetKeyguard();
                        }
                    }
                }
                keyguardFoldControllerImpl.onFoldStateChanged(keyguardFoldControllerImpl.normalRankedStateListeners, z8, z9, ((KeyguardFoldControllerConfigImpl) keyguardFoldControllerImpl.foldConfig).isDebug());
                return;
            case VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI /* 1004 */:
                KeyguardViewMediator.StartKeyguardExitAnimParams startKeyguardExitAnimParams = (KeyguardViewMediator.StartKeyguardExitAnimParams) message.obj;
                final Message messageObtainMessage = getHandler$1().obtainMessage(((Number) this.START_KEYGUARD_EXIT_ANIM$delegate.getValue()).intValue(), startKeyguardExitAnimParams);
                if (isKeyguardHiding()) {
                    boolean zIsDisabledUnlockAnimation = isDisabledUnlockAnimation(startKeyguardExitAnimParams);
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController3 = this.fastUnlockController;
                    if (zIsDisabledUnlockAnimation) {
                        if (!keyguardFastBioUnlockController3.isFastWakeAndUnlockMode() || keyguardFastBioUnlockController3.isInvisibleAfterGoingAwayTransStarted) {
                            if ((keyguardFastBioUnlockController3.isFastUnlockMode() || (keyguardFastBioUnlockController3.isFastWakeAndUnlockMode() && keyguardFastBioUnlockController3.isInvisibleAfterGoingAwayTransStarted)) && ((!this.settingsHelper.isEnabledFaceStayOnLock() && keyguardFastBioUnlockController3.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED) && keyguardFastBioUnlockController3.biometricSourceType == BiometricSourceType.FACE) || (keyguardFastBioUnlockController3.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED) && keyguardFastBioUnlockController3.biometricSourceType == BiometricSourceType.FINGERPRINT))) {
                                KeyguardFixedRotationMonitor keyguardFixedRotationMonitor2 = this.fixedRotationMonitor;
                                if (keyguardFixedRotationMonitor2.isFixedRotated()) {
                                    IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = startKeyguardExitAnimParams.mFinishedCallback;
                                    if (iRemoteAnimationFinishedCallback2 != null) {
                                        keyguardFixedRotationMonitor2.setPendingRunnable(new KeyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1(iRemoteAnimationFinishedCallback2, this));
                                        startKeyguardExitAnimParams.mFinishedCallback = null;
                                        startKeyguardExitAnimParams.mApps = null;
                                    }
                                    z3 = false;
                                } else {
                                    z3 = true;
                                }
                                String str2 = LsRune.VALUE_SUB_DISPLAY_POLICY;
                                final boolean zIsFixedRotated = keyguardFixedRotationMonitor2.isFixedRotated();
                                final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback3 = startKeyguardExitAnimParams.mFinishedCallback;
                                final ArrayList arrayList = new ArrayList();
                                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                                final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                                RemoteAnimationTarget[] remoteAnimationTargetArr2 = startKeyguardExitAnimParams.mWallpapers;
                                if (remoteAnimationTargetArr2 != null) {
                                    ArrayList arrayList2 = new ArrayList();
                                    int length = remoteAnimationTargetArr2.length;
                                    int i7 = 0;
                                    while (i7 < length) {
                                        KeyguardFixedRotationMonitor keyguardFixedRotationMonitor3 = keyguardFixedRotationMonitor2;
                                        RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr2[i7];
                                        RemoteAnimationTarget[] remoteAnimationTargetArr3 = remoteAnimationTargetArr2;
                                        if (remoteAnimationTarget.mode == 1) {
                                            arrayList2.add(remoteAnimationTarget);
                                        }
                                        i7++;
                                        keyguardFixedRotationMonitor2 = keyguardFixedRotationMonitor3;
                                        remoteAnimationTargetArr2 = remoteAnimationTargetArr3;
                                    }
                                    keyguardFixedRotationMonitor = keyguardFixedRotationMonitor2;
                                    ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                                    int size = arrayList2.size();
                                    int i8 = 0;
                                    while (i8 < size) {
                                        Object obj3 = arrayList2.get(i8);
                                        i8++;
                                        ref$ObjectRef.element = ((RemoteAnimationTarget) obj3).leash;
                                        arrayList3.add(Unit.INSTANCE);
                                    }
                                } else {
                                    keyguardFixedRotationMonitor = keyguardFixedRotationMonitor2;
                                }
                                RemoteAnimationTarget[] remoteAnimationTargetArr4 = startKeyguardExitAnimParams.mWallpapers;
                                if (remoteAnimationTargetArr4 != null) {
                                    ArrayList arrayList4 = new ArrayList();
                                    int length2 = remoteAnimationTargetArr4.length;
                                    int i9 = 0;
                                    while (i9 < length2) {
                                        RemoteAnimationTarget remoteAnimationTarget2 = remoteAnimationTargetArr4[i9];
                                        boolean z10 = z;
                                        if (remoteAnimationTarget2.mode == 0) {
                                            arrayList4.add(remoteAnimationTarget2);
                                        }
                                        i9++;
                                        z = z10;
                                    }
                                    z4 = z;
                                    ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList4, 10));
                                    int size2 = arrayList4.size();
                                    int i10 = 0;
                                    while (i10 < size2) {
                                        Object obj4 = arrayList4.get(i10);
                                        i10++;
                                        ref$ObjectRef2.element = ((RemoteAnimationTarget) obj4).leash;
                                        arrayList5.add(Unit.INSTANCE);
                                    }
                                } else {
                                    z4 = true;
                                }
                                RemoteAnimationTarget[] remoteAnimationTargetArr5 = startKeyguardExitAnimParams.mApps;
                                if (remoteAnimationTargetArr5 != null) {
                                    for (RemoteAnimationTarget remoteAnimationTarget3 : remoteAnimationTargetArr5) {
                                        arrayList.add(remoteAnimationTarget3.leash);
                                    }
                                }
                                if (!arrayList.isEmpty() && iRemoteAnimationFinishedCallback3 != null) {
                                    final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                                    this.unlockAnimationExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$updateAppLeashAndKeyguardVisibility$6
                                        /* JADX WARN: Multi-variable type inference failed */
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            Log.d("KeyguardViewMediator", "updateAppLeashAndKeyguardVisibility");
                                            this.this$0.fastUnlockController.setForceInvisible(transaction);
                                            SurfaceControl.Transaction transaction2 = transaction;
                                            ArrayList arrayList6 = arrayList;
                                            Ref$ObjectRef ref$ObjectRef3 = ref$ObjectRef;
                                            Ref$ObjectRef ref$ObjectRef4 = ref$ObjectRef2;
                                            int size3 = arrayList6.size();
                                            int i11 = 0;
                                            while (i11 < size3) {
                                                Object obj5 = arrayList6.get(i11);
                                                i11++;
                                                SurfaceControl surfaceControl2 = (SurfaceControl) obj5;
                                                transaction2.setAlpha(surfaceControl2, 1.0f);
                                                transaction2.setVisibility(surfaceControl2, true);
                                            }
                                            SurfaceControl surfaceControl3 = (SurfaceControl) ref$ObjectRef3.element;
                                            if (surfaceControl3 != null) {
                                                Log.d("KeyguardViewMediator", "updateAppLeashAndKeyguardVisibility: closingWallpaperLeash invisible");
                                                transaction2.setAlpha(surfaceControl3, 0.0f);
                                            }
                                            SurfaceControl surfaceControl4 = (SurfaceControl) ref$ObjectRef4.element;
                                            if (surfaceControl4 != null) {
                                                Log.d("KeyguardViewMediator", "updateAppLeashAndKeyguardVisibility: openingWallpaperLeash visible");
                                                transaction2.setAlpha(surfaceControl4, 1.0f);
                                            }
                                            transaction2.apply();
                                            if (zIsFixedRotated) {
                                                return;
                                            }
                                            Choreographer choreographer = Choreographer.getInstance();
                                            final SurfaceControl.Transaction transaction3 = transaction;
                                            final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback4 = iRemoteAnimationFinishedCallback3;
                                            choreographer.postCallback(1, new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$updateAppLeashAndKeyguardVisibility$6.2
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    transaction3.close();
                                                    try {
                                                        iRemoteAnimationFinishedCallback4.onAnimationFinished();
                                                    } catch (RemoteException e2) {
                                                        e2.printStackTrace();
                                                    }
                                                }
                                            }, null);
                                        }
                                    });
                                    if (!keyguardFixedRotationMonitor.isFixedRotated() && startKeyguardExitAnimParams.mFinishedCallback != null) {
                                        startKeyguardExitAnimParams.mFinishedCallback = null;
                                        startKeyguardExitAnimParams.mApps = new RemoteAnimationTarget[0];
                                    }
                                }
                                pair = new Pair(Boolean.TRUE, Boolean.valueOf(z3));
                            } else {
                                z4 = true;
                                Boolean bool = Boolean.FALSE;
                                pair = new Pair(bool, bool);
                            }
                        } else if (keyguardFastBioUnlockController3.needsBlankScreen) {
                            z5 = false;
                            pair = new Pair(Boolean.valueOf(z5), Boolean.TRUE);
                            z4 = true;
                        } else {
                            int mode3 = keyguardFastBioUnlockController3.getMode();
                            if (keyguardFastBioUnlockController3.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED)) {
                                int i11 = KeyguardFastBioUnlockController.MODE_FLAG_FRAME_COMMIT;
                                if ((mode3 & i11) == i11) {
                                }
                                pair = new Pair(Boolean.valueOf(z5), Boolean.TRUE);
                                z4 = true;
                            }
                            z5 = true;
                            pair = new Pair(Boolean.valueOf(z5), Boolean.TRUE);
                            z4 = true;
                        }
                        zBooleanValue = ((Boolean) pair.component1()).booleanValue();
                        if (((Boolean) pair.component2()).booleanValue()) {
                            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback4 = startKeyguardExitAnimParams.mFinishedCallback;
                            if (iRemoteAnimationFinishedCallback4 != null) {
                                try {
                                    iRemoteAnimationFinishedCallback4.onAnimationFinished();
                                    iRemoteAnimationFinishedCallback = null;
                                } catch (RemoteException unused) {
                                    Slog.e("KeyguardViewMediator", "RemoteException");
                                    iRemoteAnimationFinishedCallback = null;
                                    KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.ERROR, "RemoteException", null);
                                }
                                startKeyguardExitAnimParams.mFinishedCallback = iRemoteAnimationFinishedCallback;
                                remoteAnimationTargetArr = iRemoteAnimationFinishedCallback;
                            } else {
                                remoteAnimationTargetArr = 0;
                            }
                            startKeyguardExitAnimParams.mApps = remoteAnimationTargetArr;
                        }
                        if (zBooleanValue) {
                            Log.d("KeyguardViewMediator", "showForegroundImmediatelyIfNeeded returns true");
                        }
                    } else {
                        z4 = true;
                        zBooleanValue = false;
                    }
                    if (zBooleanValue) {
                        Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda23
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                this.f$0.getHandler$1().handleMessage(messageObtainMessage);
                                return Unit.INSTANCE;
                            }
                        };
                        KeyguardFastBioUnlockController.DelayedActionParams delayedActionParams = keyguardFastBioUnlockController3.delayedActionParams;
                        if (delayedActionParams != null) {
                            Handler handler = delayedActionParams.handler;
                            KeyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1 keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1 = delayedActionParams.runnableWrapper;
                            if (handler.hasCallbacks(keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1)) {
                                handler.removeCallbacks(keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1);
                            }
                            z6 = z4;
                            delayedActionParams.isDiscard = z6;
                        } else {
                            z6 = z4;
                        }
                        KeyguardFastBioUnlockController.DelayedActionParams delayedActionParams2 = new KeyguardFastBioUnlockController.DelayedActionParams(keyguardFastBioUnlockController3.mainHandler, function0, 50L);
                        delayedActionParams2.start(z6);
                        keyguardFastBioUnlockController3.delayedActionParams = delayedActionParams2;
                        return;
                    }
                }
                if (!((KeyguardViewMediator) this.viewMediatorLazy.get()).requestedShowSurfaceBehindKeyguard() && isDisabledUnlockAnimation(startKeyguardExitAnimParams) && startKeyguardExitAnimParams != null) {
                    RemoteAnimationTarget[] remoteAnimationTargetArr6 = startKeyguardExitAnimParams.mApps;
                    if (remoteAnimationTargetArr6 != null) {
                        if (!(remoteAnimationTargetArr6.length == 0)) {
                            SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                            boolean z11 = ((StatusBarStateControllerImpl) this.sysuiStatusBarStateController).mLeaveOpenOnKeyguardHide;
                            dagger.Lazy lazy = this.surfaceControllerLazy;
                            if (!z11) {
                                KeyguardSurfaceControllerImpl.setKeyguardSurfaceAppearAmount$default((KeyguardSurfaceControllerImpl) lazy.get(), transaction2, 4);
                            }
                            ArrayIterator arrayIterator = new ArrayIterator(startKeyguardExitAnimParams.mApps);
                            while (arrayIterator.hasNext()) {
                                RemoteAnimationTarget remoteAnimationTarget4 = (RemoteAnimationTarget) arrayIterator.next();
                                if (remoteAnimationTarget4 != null && (surfaceControl = remoteAnimationTarget4.leash) != null) {
                                    ((KeyguardSurfaceControllerImpl) lazy.get()).getClass();
                                    if (KeyguardSurfaceControllerImpl.isValid(surfaceControl, 1.0f)) {
                                        Log.d("KeyguardSurface", "setLeashAppearAmount amount=1.0 hasTransaction=true");
                                        try {
                                            transaction2.setAlpha(surfaceControl, 1.0f).getClass();
                                        } catch (Exception e2) {
                                            Log.d("KeyguardSurface", "setLeashAppearAmount exception");
                                            e2.printStackTrace();
                                            Unit unit3 = Unit.INSTANCE;
                                        }
                                    }
                                }
                            }
                            transaction2.apply();
                            transaction2.close();
                        }
                        startKeyguardExitAnimParams.mApps = null;
                    }
                    IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback5 = startKeyguardExitAnimParams.mFinishedCallback;
                    if (iRemoteAnimationFinishedCallback5 != null) {
                        try {
                            Log.d("KeyguardViewMediator", "keepOrDisableUnlockAnimation disabled");
                            iRemoteAnimationFinishedCallback5.onAnimationFinished();
                        } catch (RemoteException unused2) {
                        }
                        startKeyguardExitAnimParams.mFinishedCallback = null;
                    }
                }
                this.pm.userActivity(SystemClock.uptimeMillis(), 2, 0);
                if (isShowing$1() || this.hidingByDisabled) {
                    getHandler$1().handleMessage(messageObtainMessage);
                    return;
                }
                Log.w("KeyguardViewMediator", "no need to handle msg: " + messageObtainMessage.what);
                try {
                    IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback6 = startKeyguardExitAnimParams.mFinishedCallback;
                    if (iRemoteAnimationFinishedCallback6 != null) {
                        iRemoteAnimationFinishedCallback6.onAnimationFinished();
                        return;
                    }
                    return;
                } catch (RemoteException unused3) {
                    return;
                }
            case 1005:
                this.desktopManager.notifyOccluded(message.arg1 != 0);
                KeyguardDisplayManager keyguardDisplayManager = this.keyguardDisplayManager;
                boolean z12 = message.arg1 != 0;
                keyguardDisplayManager.getClass();
                android.util.Log.i("KeyguardDisplayManager", "notifyOccluded() occluded=" + z12);
                KeyguardScreenSaver keyguardScreenSaver = keyguardDisplayManager.mKeyguardScreenSaver;
                if (keyguardScreenSaver != null) {
                    keyguardScreenSaver.dismiss();
                    keyguardDisplayManager.mKeyguardScreenSaver = null;
                }
                z = !z12 && ((KeyguardStateControllerImpl) keyguardDisplayManager.mKeyguardStateController).mShowing;
                keyguardDisplayManager.mShowing = z;
                keyguardDisplayManager.mIsDexOccluded = z12;
                keyguardDisplayManager.updateDisplays(z);
                return;
            case 1006:
                if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                    SubScreenManager subScreenManager2 = this.subScreenManager;
                    boolean z13 = message.arg1 != 0;
                    subScreenManager2.getClass();
                    StringBuilder sb = new StringBuilder("setCoverOccluded ");
                    sb.append(z13);
                    sb.append(", request Cover Bouncer : ");
                    ActionBarContextView$$ExternalSyntheticOutline0.m(sb, subScreenManager2.mRequestBouncerForLauncherTask, "SubScreenManager");
                    if (z13) {
                        int i12 = 0;
                        for (ActivityManager.RunningTaskInfo runningTaskInfo : subScreenManager2.mActivityManager.getRunningTasks(1)) {
                            StringBuilder sb2 = new StringBuilder("Current running task: ");
                            sb2.append(runningTaskInfo.topActivity);
                            sb2.append(", ");
                            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(sb2, runningTaskInfo.isRunning, "SubScreenManager");
                            ComponentName componentName = runningTaskInfo.topActivity;
                            if (componentName != null && runningTaskInfo.isRunning) {
                                i12++;
                                String className = componentName.getClassName();
                                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("className  ", className, "SubScreenManager");
                                if (!((ArrayList) subScreenManager2.mOccludedApps).contains(className)) {
                                    ((ArrayList) subScreenManager2.mOccludedApps).add(className);
                                }
                            }
                        }
                        if (i12 == 0) {
                            android.util.Log.w("SubScreenManager", "no running task");
                        }
                    } else {
                        if (((ArrayList) subScreenManager2.mOccludedApps).isEmpty()) {
                            android.util.Log.w("SubScreenManager", "no prev occluded app");
                        } else {
                            ArrayList arrayList6 = (ArrayList) subScreenManager2.mOccludedApps;
                            int size3 = arrayList6.size();
                            int i13 = 0;
                            while (i13 < size3) {
                                Object obj5 = arrayList6.get(i13);
                                i13++;
                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("prev occluded app: ", (String) obj5, "SubScreenManager");
                            }
                        }
                        ((ArrayList) subScreenManager2.mOccludedApps).clear();
                        if (((KeyguardStateControllerImpl) subScreenManager2.mKeyguardStateController).mShowing && !subScreenManager2.mRequestBouncerForLauncherTask) {
                            subScreenManager2.startSubHomeActivity();
                        }
                    }
                    PluginSubScreen pluginSubScreen = subScreenManager2.mSubScreenPlugin;
                    if (pluginSubScreen == null) {
                        android.util.Log.w("SubScreenManager", "onCoverOccludedStateChanged() no plugin");
                        return;
                    } else {
                        pluginSubScreen.onCoverOccludedStateChanged(z13);
                        return;
                    }
                }
                return;
            default:
                return;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0098, code lost:
    
        if (((r1 != null ? r1.getBooleanExtra("showCoverToast", false) : false) || (r1 != null ? r1.getBooleanExtra("bio_extend_duration", false) : false) || (r1 != null ? r1.getBooleanExtra("runOnCover", false) : false)) != false) goto L52;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v9, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$handleSetPendingIntentAfterUnlock$2] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleSetPendingIntentAfterUnlock(Bundle bundle) {
        boolean z;
        String stringExtra;
        List listSplit$default;
        String str;
        final PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("PI");
        final Intent intent = (Intent) bundle.getParcelable("FI");
        boolean booleanExtra = intent != null ? intent.getBooleanExtra("ignoreKeyguardState", false) : false;
        boolean booleanExtra2 = intent != null ? intent.getBooleanExtra("ignoreUnlock", false) : false;
        String stringExtra2 = intent != null ? intent.getStringExtra("notificationKey") : null;
        boolean booleanExtra3 = intent != null ? intent.getBooleanExtra("runOnCover", false) : false;
        int intExtra = intent != null ? intent.getIntExtra("launchDisplayId", -1) : -1;
        boolean z2 = LsRune.COVER_SUPPORTED;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.updateMonitor;
        if ((z2 && keyguardUpdateMonitor.isCoverClosed()) || (LsRune.SUBSCREEN_WATCHFACE && !this.foldControllerImpl.isFoldOpened())) {
            boolean z3 = LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY;
            if ((z3 || LsRune.SUBSCREEN_WATCHFACE) && booleanExtra3) {
                keyguardUpdateMonitor.dispatchStartSubscreenBiometric(intent);
            }
            if (!z3) {
            }
            ((PluginAODManager) this.pluginAODManagerLazy.get()).showCoverToast(pendingIntent, intent);
            return;
        }
        if (booleanExtra2) {
            Log.d("KeyguardViewMediator", "handleSetPendingIntentAfterUnlock() ignoreUnlock");
            startSetPendingIntent(pendingIntent, intent);
            return;
        }
        if (!isShowing$1()) {
            if (booleanExtra) {
                startSetPendingIntent(pendingIntent, intent);
                return;
            }
            ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
            if (((Boolean) (viewMediatorProvider != null ? viewMediatorProvider : null).isExternallyEnabled.invoke()).booleanValue() || stringExtra2 == null) {
                return;
            }
            startSetPendingIntent(pendingIntent, intent);
            return;
        }
        dagger.Lazy lazy = this.viewMediatorLazy;
        boolean zIsSecure = ((KeyguardViewMediator) lazy.get()).isSecure();
        boolean booleanExtra4 = intent != null ? intent.getBooleanExtra("afterKeyguardGone", false) : false;
        int selectedUserId = (stringExtra2 == null || (listSplit$default = StringsKt__StringsKt.split$default(stringExtra2, new String[]{"|"}, 0, 6)) == null || (str = (String) CollectionsKt___CollectionsKt.getOrNull(0, listSplit$default)) == null) ? this.selectedUserInteractor.getSelectedUserId() : Integer.parseInt(str);
        if (!booleanExtra4 && ((KnoxStateMonitorImpl) this.knoxStateMonitor).isPersona(selectedUserId) && ((KeyguardViewMediator) lazy.get()).isSecure(selectedUserId)) {
            booleanExtra4 = true;
        }
        boolean userHasTrust = keyguardUpdateMonitor.getUserHasTrust(((UserTrackerImpl) this.userTracker).getUserId());
        boolean z4 = LsRune.SECURITY_SWIPE_BOUNCER;
        boolean z5 = !z4 || (zIsSecure && !userHasTrust);
        if (intent != null && (stringExtra = intent.getStringExtra("dismissType")) != null) {
            int iHashCode = stringExtra.hashCode();
            if (iHashCode != -934938715) {
                if (iHashCode != -169343402) {
                    if (iHashCode == 514447268 && stringExtra.equals("fingerprinterror")) {
                        keyguardUpdateMonitor.setDismissActionType(KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_FINGERPRINT_ERROR);
                        z5 = true;
                    }
                } else if (stringExtra.equals("shutdown")) {
                    keyguardUpdateMonitor.setDismissActionType(KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_SHUTDOWN);
                    z5 = true;
                }
            } else if (stringExtra.equals("reboot")) {
                keyguardUpdateMonitor.setDismissActionType(KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_REBOOT);
                z5 = true;
            }
        }
        KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_PENDING_INTENT);
        boolean booleanExtra5 = intent != null ? intent.getBooleanExtra("dismissIfInsecure", z5) : false;
        dagger.Lazy lazy2 = this.viewControllerLazy;
        if (booleanExtra5 || (zIsSecure && !userHasTrust)) {
            z = true;
        } else if (z4) {
            ((KeyguardViewController) lazy2.get()).setShowSwipeBouncer(true);
            z = true;
        } else {
            z = false;
        }
        this.goingAwayWithAnimation = intent != null ? intent.getBooleanExtra("withAnimation", true) : true;
        boolean booleanExtra6 = intent != null ? intent.getBooleanExtra("wakeAndUnlock", false) : false;
        boolean z6 = this.goingAwayWithAnimation;
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("handleSetPendingIntentAfterUnlock() : afterKeyguardGone=", " isInstantDismiss=", " withAnimation=", booleanExtra4, z);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z6, " wakeAndUnlock=", booleanExtra6, "launchDisplayId=");
        sbM.append(intExtra);
        Log.e("KeyguardViewMediator", sbM.toString());
        KeyguardDisplayManager keyguardDisplayManager = this.keyguardDisplayManager;
        if (!keyguardDisplayManager.isExternalDesktopWindowing() || intExtra == -1) {
            ((KeyguardViewController) lazy2.get()).dismissWithAction(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl.handleSetPendingIntentAfterUnlock.3
                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                public final boolean onDismiss() {
                    KeyguardViewMediatorHelperImpl.this.startSetPendingIntent(pendingIntent, intent);
                    return false;
                }
            }, null, booleanExtra4, z, booleanExtra6);
            return;
        }
        ((KeyguardInteractor) this.keyguardInteractor$delegate.getValue()).setDismissActionForDex(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl.handleSetPendingIntentAfterUnlock.2
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                KeyguardViewMediatorHelperImpl.this.startSetPendingIntent(pendingIntent, intent);
                return false;
            }
        });
        Integer numValueOf = Integer.valueOf(intExtra);
        if (!keyguardDisplayManager.mPresentations.contains(intExtra)) {
            android.util.Log.d("KeyguardDisplayManager", "There is no presentation for displayId=" + numValueOf);
        } else {
            Presentation presentation = (Presentation) keyguardDisplayManager.mPresentations.get(intExtra);
            if (presentation instanceof ConnectedDisplayKeyguardPresentation) {
                ((ConnectedDisplayKeyguardPresentation) presentation).showBouncer();
            } else {
                android.util.Log.d("KeyguardDisplayManager", "presentation window type casting failed!");
            }
        }
    }

    public final boolean hasOccludedMsg$1() {
        return getHandler$1().hasMessages(getSET_OCCLUDED());
    }

    public final boolean initAlphaForAnimationTargets(IRemoteAnimationRunner iRemoteAnimationRunner, SurfaceControl.Transaction transaction, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2) {
        int i = 0;
        if (!Intrinsics.areEqual(iRemoteAnimationRunner, this.exitAnimationRunner)) {
            return false;
        }
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.fastUnlockController;
        boolean z = !(!keyguardFastBioUnlockController.isFastWakeAndUnlockMode() || this.settingsHelper.isEnabledBiometricUnlockVI() || keyguardFastBioUnlockController.needsBlankScreen) || (this.settingsHelper.isAnimationDisabled() && !((StatusBarStateControllerImpl) this.sysuiStatusBarStateController).mLeaveOpenOnKeyguardHide);
        if (z) {
            ArrayList arrayList = new ArrayList();
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr2) {
                if (remoteAnimationTarget.mode == 1) {
                    arrayList.add(remoteAnimationTarget);
                }
            }
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                arrayList2.add(((RemoteAnimationTarget) obj).leash);
            }
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList2.get(i3);
                i3++;
                SurfaceControl surfaceControl = (SurfaceControl) obj2;
                Log.d("KeyguardViewMediator", "initAlphaForAnimationTargets: MODE_CLOSING wallpaper target it=" + surfaceControl);
                transaction.setAlpha(surfaceControl, 0.0f);
            }
            ArrayList arrayList3 = new ArrayList();
            for (RemoteAnimationTarget remoteAnimationTarget2 : remoteAnimationTargetArr2) {
                if (remoteAnimationTarget2.mode == 0) {
                    arrayList3.add(remoteAnimationTarget2);
                }
            }
            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
            int size3 = arrayList3.size();
            int i4 = 0;
            while (i4 < size3) {
                Object obj3 = arrayList3.get(i4);
                i4++;
                arrayList4.add(((RemoteAnimationTarget) obj3).leash);
            }
            int size4 = arrayList4.size();
            int i5 = 0;
            while (i5 < size4) {
                Object obj4 = arrayList4.get(i5);
                i5++;
                SurfaceControl surfaceControl2 = (SurfaceControl) obj4;
                Log.d("KeyguardViewMediator", "initAlphaForAnimationTargets: MODE_OPENING wallpaper target it=" + surfaceControl2);
                transaction.setAlpha(surfaceControl2, 1.0f);
            }
            ArrayList arrayList5 = new ArrayList();
            for (RemoteAnimationTarget remoteAnimationTarget3 : remoteAnimationTargetArr) {
                if (remoteAnimationTarget3.mode == 0) {
                    arrayList5.add(remoteAnimationTarget3);
                }
            }
            ArrayList arrayList6 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList5, 10));
            int size5 = arrayList5.size();
            int i6 = 0;
            while (i6 < size5) {
                Object obj5 = arrayList5.get(i6);
                i6++;
                arrayList6.add(((RemoteAnimationTarget) obj5).leash);
            }
            int size6 = arrayList6.size();
            while (i < size6) {
                Object obj6 = arrayList6.get(i);
                i++;
                transaction.setAlpha((SurfaceControl) obj6, 1.0f);
            }
            ((KeyguardSurfaceControllerImpl) this.surfaceControllerLazy.get()).setKeyguardSurfaceVisible(transaction);
        }
        return z;
    }

    public final boolean isDisabledUnlockAnimation(KeyguardViewMediator.StartKeyguardExitAnimParams startKeyguardExitAnimParams) {
        int i;
        ShadeSurface shadeSurface = ((CentralSurfacesImpl) ((CentralSurfaces) this.centralSurfacesLazy.get())).mShadeSurface;
        if (shadeSurface.isLaunchTransitionRunning() || shadeSurface.isLaunchTransitionFinished()) {
            i = 1;
        } else {
            i = 2;
            if (!((StatusBarStateControllerImpl) this.sysuiStatusBarStateController).mLeaveOpenOnKeyguardHide) {
                i = ((startKeyguardExitAnimParams != null ? startKeyguardExitAnimParams.mApps : null) == null || startKeyguardExitAnimParams.mApps.length <= 1) ? this.disableRemoteUnlockAnimation ? 4 : (this.lastGoingAwayFlags & 2) == 2 ? 5 : this.settingsHelper.getTransitionAnimationScale() == 0.0f ? 6 : this.keyguardDisplayManager.isDesktopMode() ? 7 : ((ScrimController) this.scrimControllerLazy.get()).mState == ScrimState.SHADE_LOCKED ? 8 : this.isTaskWithEmbeddedOrStartingWindow ? 9 : KeyguardViewMediatorHelperImplKt.DEBUG_DISABLE_REMOTE_UNLOCK_ANIMATION ? 255 : 0 : 3;
            }
        }
        if (i == 0) {
            return false;
        }
        logD$1("isDisabledUnlockAnimation why=" + i);
        return true;
    }

    public final boolean isEnabledBiometricUnlockVI() {
        return this.settingsHelper.isEnabledBiometricUnlockVI();
    }

    public final boolean isKeyguardDisabled(boolean z) {
        CoverState coverState;
        boolean z2;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.updateMonitor;
        if (keyguardUpdateMonitor.getRemoteLockType() != 3) {
            KnoxStateMonitor knoxStateMonitor = this.knoxStateMonitor;
            if (z) {
                ((KnoxStateMonitorImpl) knoxStateMonitor).getClass();
                if (((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isForcedLock()) {
                    z2 = false;
                } else {
                    SystemManager systemManager = SystemManager.getInstance();
                    z2 = systemManager != null && systemManager.getLockScreenOverrideMode() == 2;
                    if (z2) {
                        z2 = true;
                    }
                }
                if (!z2) {
                }
                return true;
            }
            if ((keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.isUserUnlocked$1()) || !isKeyguardDisabledBySettings(true)) {
                if (!z) {
                    if (keyguardUpdateMonitor.isForcedLock()) {
                        try {
                            ActivityTaskManager.getService().stopSystemLockTaskMode();
                        } catch (RemoteException unused) {
                            Log.w("KeyguardViewMediator", "Failed to stop app pinning");
                        }
                    }
                    BiometricUnlockController biometricUnlockController = (BiometricUnlockController) this.biometricUnlockControllerLazy.get();
                    if (biometricUnlockController.hasPendingAuthentication() && biometricUnlockController.mPendingAuthenticated.biometricSourceType == BiometricSourceType.FINGERPRINT) {
                        Log.d("KeyguardViewMediator", "keyguardDisabled: pending fingerprint auth");
                    } else {
                        KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) knoxStateMonitor;
                        knoxStateMonitorImpl.getClass();
                        if ((((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isForcedLock() || (knoxStateMonitorImpl.mCustomSdkMonitor.mKnoxCustomLockScreenOverrideMode & 2) == 0) ? false : true) {
                            Log.d("KeyguardViewMediator", "keyguardDisabled: it is disabled by Knox");
                        } else if (LsRune.COVER_SUPPORTED && (coverState = keyguardUpdateMonitor.getCoverState()) != null && coverState.attached && !coverState.getSwitchState() && this.settingsHelper.isAutomaticUnlockEnabled() && !((KeyguardViewMediator) this.viewMediatorLazy.get()).isSecure() && !this.keyguardDisplayManager.isExternalDesktopWindowing()) {
                            Log.d("KeyguardViewMediator", "doKeyguard: not showing because cover is showing");
                        } else if (LsRune.KEYGUARD_HOMEHUB && this.pogoPlugged != 0) {
                            Log.d("KeyguardViewMediator", "keyguardDisabled: it is HomeHub device and pogo is plugged");
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public final boolean isKeyguardDisabledBySettings(boolean z) {
        if (FactoryTest.isFactoryBinary()) {
            if (z) {
                Log.d("KeyguardViewMediator", "keyguardDisabled: factory binary");
                return true;
            }
        } else if (FactoryTest.isFactoryApk()) {
            if (z) {
                Log.d("KeyguardViewMediator", "keyguardDisabled: factory apk");
                return true;
            }
        } else if (FactoryTest.checkAutomationTestOption(this.context, 0)) {
            if (z) {
                Log.d("KeyguardViewMediator", "keyguardDisabled: automation test");
                return true;
            }
        } else {
            if (!this.settingsHelper.isAccessControlEnabled()) {
                return false;
            }
            if (z) {
                Log.d("KeyguardViewMediator", "keyguardDisabled: access control is enabled");
            }
        }
        return true;
    }

    public final boolean isKeyguardHiding() {
        return ((KeyguardViewMediator) this.viewMediatorLazy.get()).isHiding();
    }

    public final boolean isScreenOn() {
        return ((KeyguardViewMediator) this.viewMediatorLazy.get()).getViewMediatorCallback().isScreenOn();
    }

    public final boolean isSecure$2() {
        return ((KeyguardViewMediator) this.viewMediatorLazy.get()).isSecure();
    }

    public final boolean isShowing$1() {
        ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
        if (viewMediatorProvider == null) {
            viewMediatorProvider = null;
        }
        return ((Boolean) viewMediatorProvider.isShowing.invoke()).booleanValue();
    }

    public final boolean isUnlockStartedOrFinished() {
        if (((KeyguardUnlockAnimationController) this.unlockAnimationControllerLazy.get()).playingCannedUnlockAnimation) {
            return true;
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.stateController;
        return keyguardStateControllerImpl.mKeyguardFadingAway || !keyguardStateControllerImpl.mShowing || ((KeyguardViewMediator) this.viewMediatorLazy.get()).isAnimatingBetweenKeyguardAndSurfaceBehind();
    }

    public final boolean keyguardGoingAway(int i) {
        boolean z;
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.fastUnlockController;
        try {
            if (!keyguardFastBioUnlockController.isFastWakeAndUnlockMode() || keyguardFastBioUnlockController.isInvisibleAfterGoingAwayTransStarted || keyguardFastBioUnlockController.needsBlankScreen || !((PluginAODManager) this.pluginAODManagerLazy.get()).mIsDifferentOrientation) {
                ActivityTaskManager.getService().keyguardGoingAway(i);
                CharsKt__CharJVMKt.checkRadix(16);
                logD$1("keyguardGoingAway flags=0x" + Integer.toString(i, 16));
            } else {
                Log.d("KeyguardViewMediator", "needPendingGoingAway: fastWakeAndUnlock and different orientation");
                keyguardFastBioUnlockController.reservedKeyguardGoingAway = new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda25(this, i);
            }
            this.lastGoingAwayFlags = i;
            z = true;
        } catch (RemoteException e) {
            android.util.Log.e("KeyguardViewMediator", "Error while calling WindowManager", e);
            KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.ERROR, "Error while calling WindowManager", e);
            z = false;
        }
        KeyguardDumpLog.state$default(KeyguardDumpLog.INSTANCE, 0, z, false, false, 0, 0, 60);
        return z;
    }

    public final boolean needsCollapsePanelWithNoAnimation() {
        return ((KeyguardStateControllerImpl) ((CentralSurfacesImpl) ((CentralSurfaces) this.centralSurfacesLazy.get())).mKeyguardStateController).mOccluded || getHandler$1().hasMessages(getSET_OCCLUDED());
    }

    public final void notifyDrawn() {
        synchronized (this.lock$delegate.getValue()) {
            IKeyguardDrawnCallback iKeyguardDrawnCallback = this.drawnCallback;
            if (iKeyguardDrawnCallback != null) {
                notifyDrawn(iKeyguardDrawnCallback);
                this.drawnCallback = null;
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void onAbortHandleStartKeyguardExitAnimation() {
        ((KeyguardStateControllerImpl) this.stateController).notifyKeyguardGoingAway(false);
        dagger.Lazy lazy = this.viewControllerLazy;
        ((KeyguardViewController) lazy.get()).setKeyguardGoingAwayState(false);
        this.updateMonitor.setKeyguardGoingAway(false);
        ((KeyguardViewController) lazy.get()).onDismissCancelled();
        onAbortKeyguardDone();
    }

    public final void onAbortKeyguardDone() {
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
            ((KeyguardViewController) this.viewControllerLazy.get()).reset(true);
            this.foldControllerImpl.setFoldOpenState(0);
        }
        this.fastUnlockController.reset();
        this.disableRemoteUnlockAnimation = false;
        this.fixedRotationMonitor.cancel();
        KeyguardUnlockInfo.reset();
        this.hidingByDisabled = false;
    }

    public final void onForegroundShown() {
        Log.d("BioUnlock", "onForegroundShown hasDrawnCb=" + (this.drawnCallback != null));
        IKeyguardDrawnCallback iKeyguardDrawnCallback = this.drawnCallback;
        if (iKeyguardDrawnCallback != null) {
            synchronized (this.lock$delegate.getValue()) {
                notifyDrawn(iKeyguardDrawnCallback);
                this.drawnCallback = null;
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void onKeyguardExitFinished$1() {
        this.hidingByDisabled = false;
        ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
        if (viewMediatorProvider == null) {
            viewMediatorProvider = null;
        }
        if (((Boolean) viewMediatorProvider.isWakeAndUnlocking.invoke()).booleanValue() && this.drawnCallback != null) {
            ((KeyguardViewController) this.viewControllerLazy.get()).getViewRootImpl().setReportNextDraw(false, "BioUnlock");
            notifyDrawn();
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.updateMonitor;
        keyguardUpdateMonitor.setUnlockingKeyguard(true);
        keyguardUpdateMonitor.clearFailedUnlockAttempts(false);
        keyguardUpdateMonitor.clearFingerprintRecognized();
        keyguardUpdateMonitor.requestSessionClose();
        this.pm.userActivity(SystemClock.uptimeMillis(), 2, 0);
        boolean z = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
        KeyguardFoldControllerImpl keyguardFoldControllerImpl = this.foldControllerImpl;
        if (z) {
            keyguardFoldControllerImpl.setFoldOpenState(0);
        }
        onSecurityPropertyUpdated();
        this.disableRemoteUnlockAnimation = false;
        KeyguardFixedRotationMonitor keyguardFixedRotationMonitor = this.fixedRotationMonitor;
        if (!keyguardFixedRotationMonitor.isFixedRotated()) {
            keyguardFixedRotationMonitor.cancel();
        }
        if (LsRune.AOD_LIGHT_REVEAL && !((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) this.shadeWindowControllerHelper$delegate.getValue())).getCurrentState().forceVisibleForUnlockAnimation) {
            ((CentralSurfacesImpl) ((CentralSurfaces) this.centralSurfacesLazy.get())).mLightRevealScrim.setRevealAmount(1.0f);
        }
        if (this.settingsHelper.isRemoveAnimation()) {
            ((StatusBarStateControllerImpl) this.sysuiStatusBarStateController).setLeaveOpenOnKeyguardHide(false);
        }
        if (!LsRune.KEYGUARD_SUB_DISPLAY_COVER || keyguardFoldControllerImpl.isFoldOpened()) {
            return;
        }
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.fastUnlockController;
        if (keyguardFastBioUnlockController.isFastWakeAndUnlockMode()) {
            keyguardFastBioUnlockController.reset();
        }
    }

    public final void onKeyguardGone() {
        KeyguardDisplayManager keyguardDisplayManager = this.keyguardDisplayManager;
        KeyguardVisibilityMonitor keyguardVisibilityMonitor = keyguardDisplayManager.mKeyguardVisibilityMonitor;
        if (keyguardVisibilityMonitor.isVisible()) {
            keyguardVisibilityMonitor.addVisibilityChangedListener(keyguardDisplayManager.mVisibilityListener);
        } else {
            keyguardDisplayManager.hide();
        }
        if (this.isAODShowStateCbRegistered) {
            this.settingsHelper.unregisterCallback(this.aodShowStateCallback);
        }
        this.isAODShowStateCbRegistered = false;
        this.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$registerSysDumpHeap$1
            @Override // java.lang.Runnable
            public final void run() {
                this.this$0.sysDumpTrigger.getClass();
            }
        });
    }

    public final void onSecurityPropertyUpdated() {
        if (isSecure$2()) {
            this.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl.onSecurityPropertyUpdated.1
                @Override // java.lang.Runnable
                public final void run() {
                    SystemProperties.set("sys.locksecured", KeyguardViewMediatorHelperImpl.this.isShowing$1() ? "true" : "false");
                }
            });
        }
    }

    public final void playSound$2(final int i) {
        if (this.settingsHelper.isLockSoundEnabled()) {
            this.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$playSound$1
                @Override // java.lang.Runnable
                public final void run() {
                    float fSemGetSituationVolume;
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                    SoundPool soundPool = keyguardViewMediatorHelperImpl.lockSounds;
                    if (soundPool != null) {
                        soundPool.stop(keyguardViewMediatorHelperImpl.lockSoundStreamId);
                    }
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this.this$0;
                    keyguardViewMediatorHelperImpl2.uiSoundsStreamType = keyguardViewMediatorHelperImpl2.audioManager.getUiSoundsStreamType();
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this.this$0;
                    if (keyguardViewMediatorHelperImpl3.audioManager.isStreamMute(keyguardViewMediatorHelperImpl3.uiSoundsStreamType)) {
                        return;
                    }
                    if (LsRune.KEYGUARD_LOCK_SITUATION_VOLUME) {
                        fSemGetSituationVolume = 1.0f;
                    } else {
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = this.this$0;
                        fSemGetSituationVolume = keyguardViewMediatorHelperImpl4.audioManager.semGetSituationVolume(i == keyguardViewMediatorHelperImpl4.unlockSoundId ? 7 : 4, 0);
                    }
                    float f = fSemGetSituationVolume;
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = this.this$0;
                    String str = "playSound " + i;
                    keyguardViewMediatorHelperImpl5.getClass();
                    KeyguardViewMediatorHelperImpl.logD$1(str);
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl6 = this.this$0;
                    SoundPool soundPool2 = keyguardViewMediatorHelperImpl6.lockSounds;
                    if (soundPool2 != null) {
                        keyguardViewMediatorHelperImpl6.lockSoundStreamId = soundPool2.play(i, f, f, 1, 0, 1.0f);
                    }
                }
            });
        }
    }

    public final void postHandleMsg(Message message) {
        boolean z;
        DeviceStateManager deviceStateManager;
        int i = message.what;
        if (i == ((Number) this.SYSTEM_READY$delegate.getValue()).intValue()) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.updateCallback;
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.updateMonitor;
            keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
            boolean z2 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
            final KeyguardFoldControllerImpl keyguardFoldControllerImpl = this.foldControllerImpl;
            if ((z2 || LsRune.KEYGUARD_SUB_DISPLAY_COVER) && (deviceStateManager = (DeviceStateManager) keyguardFoldControllerImpl.context.getSystemService(DeviceStateManager.class)) != null) {
                deviceStateManager.registerCallback(Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.android.systemui.keyguard.KeyguardFoldControllerImpl$init$1
                    @Override // java.util.concurrent.ThreadFactory
                    public final Thread newThread(Runnable runnable) {
                        return new Thread(runnable, "KeyguardFoldControllerImpl");
                    }
                }), new DeviceStateManager.FoldStateListener(keyguardFoldControllerImpl.context, new Consumer() { // from class: com.android.systemui.keyguard.KeyguardFoldControllerImpl$init$2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        Boolean bool = (Boolean) obj;
                        if (bool != null) {
                            keyguardFoldControllerImpl.changeFoldState(bool.booleanValue());
                        }
                    }
                }));
            }
            if (z2) {
                keyguardFoldControllerImpl.addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$postHandleSystemReady$1
                    @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                    public final void onFoldStateChanged(boolean z3) {
                        final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                        keyguardViewMediatorHelperImpl.getHandler$1().post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$postHandleSystemReady$1$onFoldStateChanged$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).getClass();
                            }
                        });
                    }
                }, 6, true);
            }
            ((KnoxStateMonitorImpl) this.knoxStateMonitor).registerCallback(this.knoxStateCallback);
            this.commandQueue.addCallback((CommandQueue.Callbacks) this);
            try {
                if (this.lockSettingsService == null) {
                    this.lockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
                }
                ILockSettings iLockSettings = this.lockSettingsService;
                if (iLockSettings != null) {
                    iLockSettings.registerRemoteLockCallback(4, this.remoteLockMonitorCallback);
                }
            } catch (RemoteException e) {
                android.util.Log.d("KeyguardViewMediator", "RemoteLockMonitorCallback regi Failed!", e);
                KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.DEBUG, "RemoteLockMonitorCallback regi Failed!", e);
            }
            PickupController pickupController = this.pickupController;
            PickupController$baseSensorListener$1 pickupController$baseSensorListener$1 = pickupController.baseSensorListener;
            if (!pickupController.pickupListener.contains(pickupController$baseSensorListener$1)) {
                pickupController.pickupListener.add(pickupController$baseSensorListener$1);
            }
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("register listener caller=", Debug.getCallers(2), "PickupController");
            if (LsRune.KEYGUARD_FBE) {
                Log.i("KeyguardViewMediator", "postHandleSystemReady(). check FBE");
                keyguardUpdateMonitor.updateUserUnlockNotification(((UserTrackerImpl) this.userTracker).getUserId());
            }
        } else {
            if (i == getSHOW()) {
                KeyguardViewMediatorHelperImpl$delayedDrawnRunnable$1 keyguardViewMediatorHelperImpl$delayedDrawnRunnable$1 = this.delayedDrawnRunnable;
                Handler handler$1 = getHandler$1();
                if (handler$1.hasCallbacks(keyguardViewMediatorHelperImpl$delayedDrawnRunnable$1)) {
                    handler$1.removeCallbacks(keyguardViewMediatorHelperImpl$delayedDrawnRunnable$1);
                }
                synchronized (this.lock$delegate.getValue()) {
                    try {
                        IKeyguardDrawnCallback iKeyguardDrawnCallback = this.drawnCallback;
                        if (iKeyguardDrawnCallback != null) {
                            ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
                            if (viewMediatorProvider == null) {
                                viewMediatorProvider = null;
                            }
                            if (((Number) viewMediatorProvider.getDelayedShowingSequence.invoke()).intValue() < 2 || (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && this.lastSleepReason == 4 && this.foldControllerImpl.isFoldOpened())) {
                                notifyDrawn(iKeyguardDrawnCallback);
                                this.drawnCallback = null;
                                Unit unit = Unit.INSTANCE;
                            } else {
                                getHandler$1().post(this.delayedDrawnRunnable);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (((KeyguardViewMediator) this.viewMediatorLazy.get()).isShowingAndNotOccluded()) {
                    this.lastShowingTime = SystemClock.elapsedRealtime();
                }
                if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
                    this.foldControllerImpl.resetFoldOpenState$1();
                }
                this.disableRemoteUnlockAnimation = false;
                this.fixedRotationMonitor.cancel();
            } else if (i == ((Number) this.NOTIFY_STARTED_WAKING_UP$delegate.getValue()).intValue()) {
                if (Rune.SYSUI_BINDER_CALL_MONITOR) {
                    BinderCallMonitorImpl binderCallMonitorImpl = (BinderCallMonitorImpl) this.binderCallMonitor;
                    binderCallMonitorImpl.getClass();
                    binderCallMonitorImpl.startMonitoring(1, BinderCallMonitorConstants.MAX_DURATION / 1000000, 3000L);
                }
                int i2 = this.lastWakeReason;
                dagger.Lazy lazy = this.viewMediatorLazy;
                if (i2 != 10) {
                    ((KeyguardViewMediator) lazy.get()).setDozing(false);
                }
                boolean zIsShowing$1 = isShowing$1();
                boolean zIsHiding = ((KeyguardViewMediator) lazy.get()).isHiding();
                boolean zIsSecure = ((KeyguardViewMediator) lazy.get()).isSecure();
                boolean z3 = LsRune.COVER_SUPPORTED;
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = this.updateMonitor;
                if (z3) {
                    CoverState coverState = keyguardUpdateMonitor2.getCoverState();
                    if (this.lastWakeReason == 103 && zIsShowing$1 && !zIsHiding && coverState != null && coverState.attached && coverState.getSwitchState() && this.settingsHelper.isAutomaticUnlockEnabled() && (this.switchingUserId == -1 ? !zIsSecure || keyguardUpdateMonitor2.getUserCanSkipBouncer(((UserTrackerImpl) this.userTracker).getUserId()) : !zIsSecure)) {
                        ViewMediatorProvider viewMediatorProvider2 = this.viewMediatorProvider;
                        (viewMediatorProvider2 != null ? viewMediatorProvider2 : null).handleHide.invoke();
                    }
                }
                if (!zIsShowing$1 && !getHandler$1().hasMessages(getSHOW())) {
                    keyguardUpdateMonitor2.requestSessionClose();
                } else if (keyguardUpdateMonitor2.isFingerprintOptionEnabled()) {
                    keyguardUpdateMonitor2.updateFingerprintListeningState(2);
                }
                this.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$postHandleNotifyStartedWakingUp$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        String str;
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
                        int i3 = keyguardViewMediatorHelperImpl.lastWakeReason;
                        KeyguardViewMediatorHelperImpl.logD$1("updateSALogging " + i3);
                        String str2 = i3 != 1 ? i3 != 4 ? i3 != 7 ? (i3 == 9 || i3 == 103) ? "4" : i3 != 112 ? i3 != 113 ? "5" : "7" : "2" : "6" : "3" : "1";
                        boolean z4 = LsRune.SUBSCREEN_UI;
                        String str3 = SystemUIAnalytics.EID_SHOW_LOCKSCREEN;
                        if (!z4 || keyguardViewMediatorHelperImpl.foldControllerImpl.isFoldOpened()) {
                            str = "101";
                        } else if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                            str = SystemUIAnalytics.SID_SUBSCREEN_LARGE;
                        } else {
                            str = SystemUIAnalytics.SID_SUBSCREEN_NORMAL;
                            str3 = SystemUIAnalytics.EID_SHOW_SUBSCREEN;
                        }
                        SystemUIAnalytics.sendEventLog(str, str3, str2);
                    }
                });
            } else if (i == ((Number) this.NOTIFY_STARTED_GOING_TO_SLEEP$delegate.getValue()).intValue()) {
                synchronized (this.lock$delegate.getValue()) {
                    try {
                        z = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
                        if (!z || !getHandler$1().hasMessages(((Number) this.NOTIFY_STARTED_WAKING_UP$delegate.getValue()).intValue())) {
                            if (isKeyguardHiding() && !this.hidingByDisabled) {
                                Log.d("KeyguardViewMediator", "change mHiding = false");
                                ViewMediatorProvider viewMediatorProvider3 = this.viewMediatorProvider;
                                (viewMediatorProvider3 != null ? viewMediatorProvider3 : null).setHiding.mo781invoke(Boolean.FALSE);
                            }
                        }
                        Unit unit2 = Unit.INSTANCE;
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
                this.fastUnlockController.reset();
                if (z) {
                    this.foldControllerImpl.resetFoldOpenState$1();
                    if (this.lastSleepReason == 4 && !this.foldControllerImpl.isFoldOpened()) {
                        ((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) this.shadeWindowControllerHelper$delegate.getValue())).resetForceInvisible(false);
                    }
                }
                if (!this.isAODShowStateCbRegistered) {
                    this.settingsHelper.registerCallback(this.aodShowStateCallback, Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_STATE));
                }
                this.isAODShowStateCbRegistered = true;
                this.disableRemoteUnlockAnimation = false;
                this.fixedRotationMonitor.cancel();
            } else if (i == ((Number) this.KEYGUARD_DONE_PENDING_TIMEOUT$delegate.getValue()).intValue()) {
                ViewMediatorProvider viewMediatorProvider4 = this.viewMediatorProvider;
                boolean zBooleanValue = ((Boolean) (viewMediatorProvider4 != null ? viewMediatorProvider4 : null).isKeyguardDonePending.invoke()).booleanValue();
                logD$1("handleKeyguardDonePendingTimeout donePending=" + zBooleanValue);
                if (zBooleanValue) {
                    ((KeyguardViewMediator) this.viewMediatorLazy.get()).getViewMediatorCallback().readyForKeyguardDone();
                }
            } else if (i == ((Number) this.KEYGUARD_TIMEOUT$delegate.getValue()).intValue()) {
                this.needKeyguardAppearAnimation = true;
            }
        }
        LogUtil.endTime(this.handleMsgLogKey, new KeyguardViewMediatorHelperImpl$endHandleMsgTime$1(this, message.what));
        this.handleMsgLogKey = -1;
    }

    public final void removeMessage(int i) {
        Handler handler$1 = getHandler$1();
        if (handler$1.hasMessages(i)) {
            handler$1.removeMessages(i);
        }
    }

    public final void removeShowMsg() {
        Handler handler$1 = getHandler$1();
        if (handler$1.hasMessages(getSHOW())) {
            handler$1.removeMessages(getSHOW());
            ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
            if (viewMediatorProvider == null) {
                viewMediatorProvider = null;
            }
            PowerManager.WakeLock wakeLock = (PowerManager.WakeLock) viewMediatorProvider.showKeyguardWakeLock.invoke();
            if (wakeLock.isHeld()) {
                wakeLock.release();
            }
        }
    }

    public final void removeShowMsgOnCoverOpened() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.updateMonitor;
        CoverState coverState = keyguardUpdateMonitor.getCoverState();
        if (this.lastWakeReason == 103 && coverState != null && coverState.attached && this.settingsHelper.isAutomaticUnlockEnabled()) {
            int i = this.switchingUserId;
            dagger.Lazy lazy = this.viewMediatorLazy;
            if (i != -1) {
                if (((KeyguardViewMediator) lazy.get()).isSecure()) {
                    return;
                }
            } else if (((KeyguardViewMediator) lazy.get()).isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(((UserTrackerImpl) this.userTracker).getUserId())) {
                return;
            }
            removeShowMsg();
        }
    }

    public final void resetStateLocked$2() {
        ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
        if (viewMediatorProvider == null) {
            viewMediatorProvider = null;
        }
        viewMediatorProvider.resetStateLocked.invoke();
    }

    public final void setKeyguardGoingAway(int i) {
        boolean z = this.goingAwayWithAnimation;
        dagger.Lazy lazy = this.biometricUnlockControllerLazy;
        if (!z || (((BiometricUnlockController) lazy.get()).isBiometricUnlock() && !this.settingsHelper.isEnabledBiometricUnlockVI())) {
            i |= 2;
        }
        ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
        if (viewMediatorProvider == null) {
            viewMediatorProvider = null;
        }
        if (((Boolean) viewMediatorProvider.isWakeAndUnlocking.invoke()).booleanValue()) {
            i |= 256;
        }
        boolean z2 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
        KeyguardFoldControllerImpl keyguardFoldControllerImpl = this.foldControllerImpl;
        if (z2) {
            if (((BiometricUnlockController) lazy.get()).mMode != 0) {
                i |= 512;
            }
            if (keyguardFoldControllerImpl.isUnlockOnFoldOpened()) {
                i |= 32;
            }
        }
        int[] iArr = KeyguardSysDumpTrigger.KEY;
        KeyguardSysDumpTrigger keyguardSysDumpTrigger = this.sysDumpTrigger;
        if (keyguardSysDumpTrigger.isEnabled()) {
            keyguardSysDumpTrigger.start(0, 4950L, -1L);
        }
        boolean zIsEnabledFaceStayOnLock = this.settingsHelper.isEnabledFaceStayOnLock();
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.fastUnlockController;
        if ((!zIsEnabledFaceStayOnLock && keyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED) && keyguardFastBioUnlockController.biometricSourceType == BiometricSourceType.FACE) || (keyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED) && keyguardFastBioUnlockController.biometricSourceType == BiometricSourceType.FINGERPRINT)) {
            KeyguardFixedRotationMonitor keyguardFixedRotationMonitor = this.fixedRotationMonitor;
            if (!keyguardFixedRotationMonitor.isMonitorStarted) {
                android.util.Log.d("KeyguardFixedRotation", NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                keyguardFixedRotationMonitor.isFixedRotated = false;
                keyguardFixedRotationMonitor.windowManager.registerDisplayWindowListener(keyguardFixedRotationMonitor.displayWindowListener);
                keyguardFixedRotationMonitor.isMonitorStarted = true;
            }
        }
        keyguardGoingAway(i);
        if (keyguardFastBioUnlockController.isFastUnlockMode() || (keyguardFastBioUnlockController.isFastWakeAndUnlockMode() && keyguardFastBioUnlockController.isInvisibleAfterGoingAwayTransStarted)) {
            keyguardFastBioUnlockController.goingAwayTime = System.nanoTime();
        }
        if (z2 && keyguardFoldControllerImpl.foldOpenState == 3) {
            final KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0 keyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0 = new KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0(this, 13);
            if (Looper.getMainLooper().isCurrentThread()) {
                keyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0.invoke();
            } else {
                this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImplKt$sam$java_lang_Runnable$0
                    @Override // java.lang.Runnable
                    public final /* synthetic */ void run() {
                        keyguardViewMediatorHelperImpl$$ExternalSyntheticLambda0.invoke();
                    }
                });
            }
        }
    }

    public final void setShowingOptions(Bundle bundle) {
        if (isScreenOn()) {
            this.showingOptions = bundle;
            KeyguardEditModeController keyguardEditModeController = this.editModeController;
            if (bundle != null && bundle.getBoolean("KeyguardExitEditVI", false) && keyguardEditModeController != null) {
                ((KeyguardEditModeControllerImpl) keyguardEditModeController).isEditMode = true;
            }
            Bundle bundle2 = this.showingOptions;
            if (bundle2 != null && bundle2.getBoolean("LockShownDelay", false)) {
                KeyguardViewMediatorHelperImplKt.isLockShownDelay = true;
            }
            Bundle bundle3 = this.showingOptions;
            String string = bundle3 != null ? bundle3.getString("request_id") : null;
            if (string != null && keyguardEditModeController != null) {
                ((KeyguardEditModeControllerImpl) keyguardEditModeController).backupWallpaperRequestId = string;
            }
            Bundle bundle4 = this.showingOptions;
            ParcelFileDescriptor parcelFileDescriptor = bundle4 != null ? (ParcelFileDescriptor) bundle4.getParcelable("preview_pfd_from_preview", ParcelFileDescriptor.class) : null;
            if (keyguardEditModeController != null) {
                ((KeyguardEditModeControllerImpl) keyguardEditModeController).backupWallpaperPreviewPFD = parcelFileDescriptor;
            }
        }
    }

    public final void startSetPendingIntent(final PendingIntent pendingIntent, final Intent intent) {
        final String stringExtra = intent != null ? intent.getStringExtra("notificationKey") : null;
        this.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl.startSetPendingIntent.1
            @Override // java.lang.Runnable
            public final void run() throws PendingIntent.CanceledException {
                int iStartTime = LogUtil.startTime(-1);
                try {
                    PendingIntent pendingIntent2 = pendingIntent;
                    if (pendingIntent2 != null) {
                        Intent intent2 = intent;
                        String str = stringExtra;
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this;
                        if (pendingIntent2.isActivity()) {
                            ActivityManager.getService().resumeAppSwitches();
                        }
                        int intExtra = intent2 != null ? intent2.getIntExtra("launchDisplayId", -1) : -1;
                        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                        if (intExtra != -1) {
                            Log.d("KeyguardViewMediator", "startSetPendingIntent to launchDisplayId " + intExtra);
                        } else {
                            Display display = keyguardViewMediatorHelperImpl.context.getDisplay();
                            display.getClass();
                            intExtra = display.getDisplayId();
                        }
                        activityOptionsMakeBasic.setLaunchDisplayId(intExtra);
                        activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
                        if (str == null) {
                            pendingIntent2.send(keyguardViewMediatorHelperImpl.context, 0, intent2, null, null, null, activityOptionsMakeBasic.toBundle());
                        } else {
                            pendingIntent2.send(null, 0, null, null, null, null, activityOptionsMakeBasic.toBundle());
                        }
                    }
                } catch (Exception e) {
                    android.util.Log.e("KeyguardViewMediator", "Cannot send pending intent due to : ", e);
                    KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.ERROR, "Cannot send pending intent due to : ", e);
                }
                String str2 = stringExtra;
                if (str2 != null) {
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this;
                    String strConcat = "notificationKey=".concat(str2);
                    keyguardViewMediatorHelperImpl2.getClass();
                    KeyguardViewMediatorHelperImpl.logD$1(strConcat);
                    NotificationEntry entry = ((NotifPipeline) ((CommonNotifCollection) keyguardViewMediatorHelperImpl2.commonNotifCollectionLazy.get())).mNotifCollection.getEntry(str2);
                    if (entry != null) {
                        try {
                            IStatusBarService barService = keyguardViewMediatorHelperImpl2.getBarService();
                            if (barService != null) {
                                barService.onNotificationClick(str2, NotificationVisibility.obtain(str2, entry.mRanking.getRank(), keyguardViewMediatorHelperImpl2.activeNotificationsInteractor.getAllNotificationsCountValue(), true));
                            }
                        } catch (RemoteException unused) {
                        }
                    }
                }
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this;
                LogUtil.endTime(iStartTime, new LongConsumer() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl.startSetPendingIntent.1.3
                    @Override // java.util.function.LongConsumer
                    public final void accept(long j) {
                        keyguardViewMediatorHelperImpl3.getClass();
                        KeyguardViewMediatorHelperImpl.logD$1("startSetPendingIntent runnable " + j + "ms");
                    }
                });
            }
        });
        dagger.Lazy lazy = this.viewControllerLazy;
        if (((KeyguardViewController) lazy.get()).isPanelFullyCollapsed() || !((KeyguardViewController) lazy.get()).isBouncerShowing()) {
            return;
        }
        ((CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class)).animateCollapsePanels(0, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updatePendingLock(int i, long j, boolean z, int i2, KeyguardViewMediator$$ExternalSyntheticLambda0 keyguardViewMediator$$ExternalSyntheticLambda0, KeyguardViewMediator$$ExternalSyntheticLambda9 keyguardViewMediator$$ExternalSyntheticLambda9) {
        boolean z2;
        if (!LsRune.KEYGUARD_SUB_DISPLAY_COVER || i != 4 || Intrinsics.areEqual(SystemProperties.get("net.mirrorlink.on", ""), "1")) {
            if ((i == 3 && j > 0) || (((z2 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK) && i == 4 && ((!z2 || (!this.lockPatternUtils.getFolderInstantlyLocks(i2) && ((KeyguardViewMediator) this.viewMediatorLazy.get()).isSecure(i2))) && j > 0)) || (i == 2 && !z))) {
                ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                if (!Intrinsics.areEqual((String) viewMediatorProvider.updatePhoneState.mo781invoke(null), TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    if (j > 0) {
                        keyguardViewMediator$$ExternalSyntheticLambda0 = keyguardViewMediator$$ExternalSyntheticLambda9;
                    }
                }
            } else if (this.lockPatternUtils.isLockScreenDisabled(i2)) {
                keyguardViewMediator$$ExternalSyntheticLambda0 = null;
            }
        }
        if (keyguardViewMediator$$ExternalSyntheticLambda0 != null) {
            keyguardViewMediator$$ExternalSyntheticLambda0.run();
        }
    }

    public final void notifyDrawn(IKeyguardDrawnCallback iKeyguardDrawnCallback) {
        long jElapsedRealtime = SystemClock.elapsedRealtime() - this.screenTuringOnTime;
        if (jElapsedRealtime <= 0) {
            jElapsedRealtime = 0;
        }
        logD$1("notifyDrawn " + jElapsedRealtime + "ms");
        try {
            iKeyguardDrawnCallback.onDrawn();
        } catch (RemoteException e) {
            Slog.w("KeyguardViewMediator", "Exception calling onDrawn():", e);
            KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.WARNING, "Exception calling onDrawn():", e);
        }
    }
}
