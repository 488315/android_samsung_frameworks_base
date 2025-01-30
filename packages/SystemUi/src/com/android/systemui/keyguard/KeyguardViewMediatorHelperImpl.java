package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.display.IDisplayManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.View;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.IKeyguardDrawnCallback;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.IRemoteLockMonitorCallback;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.RemoteLockInfo;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.BootAnimationFinishedTrigger;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.sensor.PickupController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.uithreadmonitor.BinderCallMonitor;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl;
import com.android.systemui.util.CarLifeManager;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.android.knox.custom.SystemManager;
import com.samsung.android.os.SemDvfsManager;
import com.sec.ims.configuration.DATA;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.LongConsumer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.reflect.KFunction;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardViewMediatorHelperImpl implements KeyguardViewMediatorHelper {
    public final Lazy CANCEL_KEYGUARD_EXIT_ANIM$delegate;
    public final Lazy KEYGUARD_DONE$delegate;
    public final Lazy KEYGUARD_DONE_DRAWING$delegate;
    public final Lazy KEYGUARD_DONE_PENDING_TIMEOUT$delegate;
    public final Lazy NOTIFY_STARTED_GOING_TO_SLEEP$delegate;
    public final Lazy NOTIFY_STARTED_WAKING_UP$delegate;
    public final Lazy SET_OCCLUDED$delegate;
    public final Lazy START_KEYGUARD_EXIT_ANIM$delegate;
    public final Lazy SYSTEM_READY$delegate;
    public final ActivityManager activityManager;
    public final AODAmbientWallpaperHelper aodAmbientWallpaperHelper;
    public final KeyguardViewMediatorHelperImpl$aodShowStateCallback$1 aodShowStateCallback;
    public final AudioManager audioManager;
    public IStatusBarService barService;
    public final BinderCallMonitor binderCallMonitor;
    public final dagger.Lazy biometricUnlockControllerLazy;
    public final BootAnimationFinishedTrigger bootAnimationFinishedTrigger;
    public final BroadcastDispatcher broadcastDispatcher;
    public final KeyguardViewMediatorHelperImpl$broadcastReceiver$1 broadcastReceiver;
    public final Object carrierLock;
    public final dagger.Lazy centralSurfacesLazy;
    public final dagger.Lazy commonNotifCollectionLazy;
    public final Context context;
    public boolean curIsOccluded;
    public int curMaxRefresRate;
    public final KeyguardViewMediatorHelperImpl$delayedDrawnRunnable$1 delayedDrawnRunnable;
    public final DesktopManager desktopManager;
    public int disableFlags;
    public boolean disableRemoteUnlockAnimation;
    public final KeyguardViewMediatorHelperImpl$disabledOccluedeAnimationRunner$1 disabledOccluedeAnimationRunner;
    public final DismissCallbackRegistry dismissCallbackRegistry;
    public final Lazy displayManager$delegate;
    public PendingIntent doKeyguardPendingIntent;
    public IKeyguardDrawnCallback drawnCallback;
    public SemDvfsManager dvfsManager;
    public final KeyguardEditModeController editModeController;
    public IRemoteAnimationRunner exitAnimationRunner;
    public Intent extraUserPresentIntent;
    public final KeyguardFastBioUnlockController fastUnlockController;
    public boolean firstKeyguardShown;
    public final KeyguardFixedRotationMonitor fixedRotationMonitor;
    public final Object fmmLock;
    public final KeyguardFoldControllerImpl foldControllerImpl;
    public boolean goingAwayWithAnimation;
    public int handleMsgLogKey;
    public final Lazy handler$delegate;
    public boolean hidingByDisabled;
    public final InteractionJankMonitor interactionJankMonitor;
    public boolean isAODShowStateCbRegistered;
    public final KeyguardDisplayManager keyguardDisplayManager;
    public final KeyguardVisibilityMonitor keyguardVisibilityMonitor;
    public final KeyguardWallpaper keyguardWallpaper;
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
    public int lockSoundStreamId;
    public SoundPool lockSounds;
    public int lockStaySoundId;
    public final LooperSlowLogController looperLogController;
    public final Lazy mainMaxRefreshRate$delegate;
    public final dagger.Lazy notificationShadeWindowControllerLazy;
    public final NotificationsController notificationsController;
    public final AtomicInteger occludedSeq;
    public final PickupController pickupController;
    public final dagger.Lazy pluginAODManagerLazy;

    /* renamed from: pm */
    public final PowerManager f297pm;
    public int pogoPlugged;
    public final Lazy refreshRateToken$delegate;
    public IRefreshRateToken refreshRateTokenMaxLimitToken;
    public final KeyguardViewMediatorHelperImpl$remoteLockMonitorCallback$1 remoteLockMonitorCallback;
    public long screenTuringOnTime;
    public final dagger.Lazy scrimControllerLazy;
    public final KeyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1 setLockScreenShownRunnable;
    public final SettingsHelper settingsHelper;
    public final Lazy shadeWindowControllerHelper$delegate;
    public Bundle showingOptions;
    public final KeyguardStateController stateController;
    public final Lazy subMaxRefreshRate$delegate;
    public final SubScreenManager subScreenManager;
    public final dagger.Lazy surfaceControllerLazy;
    public int switchingUserId;
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
    public final KeyguardWallpaperController wallpaperController;
    public final Lazy SHOW$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$SHOW$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().showMsg.invoke();
        }
    });
    public final Lazy HIDE$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$HIDE$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().hideMsg.invoke();
        }
    });
    public final Lazy RESET$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$RESET$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().resetMsg.invoke();
        }
    });

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$disabledOccluedeAnimationRunner$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$unoccluedAnimationRunner$1] */
    /* JADX WARN: Type inference failed for: r2v81, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$delayedDrawnRunnable$1] */
    /* JADX WARN: Type inference failed for: r2v82, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$aodShowStateCallback$1] */
    /* JADX WARN: Type inference failed for: r2v86, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$remoteLockMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r2v87, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$localReceiver$1] */
    /* JADX WARN: Type inference failed for: r2v88, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$broadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r2v90, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$knoxStateCallback$1] */
    public KeyguardViewMediatorHelperImpl(Context context, BroadcastDispatcher broadcastDispatcher, Executor executor, Executor executor2, dagger.Lazy lazy, dagger.Lazy lazy2, dagger.Lazy lazy3, dagger.Lazy lazy4, dagger.Lazy lazy5, dagger.Lazy lazy6, dagger.Lazy lazy7, dagger.Lazy lazy8, KeyguardFastBioUnlockController keyguardFastBioUnlockController, KeyguardDisplayManager keyguardDisplayManager, InteractionJankMonitor interactionJankMonitor, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, KeyguardSysDumpTrigger keyguardSysDumpTrigger, UserTracker userTracker, ActivityManager activityManager, KnoxStateMonitor knoxStateMonitor, DesktopManager desktopManager, PickupController pickupController, LockPatternUtils lockPatternUtils, KeyguardStateController keyguardStateController, DismissCallbackRegistry dismissCallbackRegistry, SysuiStatusBarStateController sysuiStatusBarStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, KeyguardWallpaperController keyguardWallpaperController, PowerManager powerManager, dagger.Lazy lazy9, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, KeyguardWallpaper keyguardWallpaper, LooperSlowLogController looperSlowLogController, SamsungServiceLogger samsungServiceLogger, AudioManager audioManager, SamsungServiceLogger samsungServiceLogger2, BootAnimationFinishedTrigger bootAnimationFinishedTrigger, BinderCallMonitor binderCallMonitor, SubScreenManager subScreenManager, KeyguardFoldControllerImpl keyguardFoldControllerImpl, KeyguardFixedRotationMonitor keyguardFixedRotationMonitor, KeyguardVisibilityMonitor keyguardVisibilityMonitor, dagger.Lazy lazy10, NotificationsController notificationsController, KeyguardEditModeController keyguardEditModeController, CarLifeManager carLifeManager) {
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
        this.wallpaperController = keyguardWallpaperController;
        this.f297pm = powerManager;
        this.pluginAODManagerLazy = lazy9;
        this.aodAmbientWallpaperHelper = aODAmbientWallpaperHelper;
        this.keyguardWallpaper = keyguardWallpaper;
        this.looperLogController = looperSlowLogController;
        this.audioManager = audioManager;
        this.bootAnimationFinishedTrigger = bootAnimationFinishedTrigger;
        this.binderCallMonitor = binderCallMonitor;
        this.subScreenManager = subScreenManager;
        this.foldControllerImpl = keyguardFoldControllerImpl;
        this.fixedRotationMonitor = keyguardFixedRotationMonitor;
        this.keyguardVisibilityMonitor = keyguardVisibilityMonitor;
        this.commonNotifCollectionLazy = lazy10;
        this.notificationsController = notificationsController;
        this.editModeController = keyguardEditModeController;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$VERIFY_UNLOCK$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().verityUnlockMsg.invoke();
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$NOTIFY_FINISHED_GOING_TO_SLEEP$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().notifyFinishedGoingToSleepMsg.invoke();
            }
        });
        this.KEYGUARD_DONE$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$KEYGUARD_DONE$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().keyguardDoneMsg.invoke();
            }
        });
        this.KEYGUARD_DONE_DRAWING$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$KEYGUARD_DONE_DRAWING$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().keyguardDoneDrawingMsg.invoke();
            }
        });
        this.SET_OCCLUDED$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$SET_OCCLUDED$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().setOccludedMsg.invoke();
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$KEYGUARD_TIMEOUT$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().keyguardTimeoutMsg.invoke();
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$DISMISS$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().dismissMsg.invoke();
            }
        });
        this.START_KEYGUARD_EXIT_ANIM$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$START_KEYGUARD_EXIT_ANIM$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().startKeyguardExitAnimMsg.invoke();
            }
        });
        this.KEYGUARD_DONE_PENDING_TIMEOUT$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$KEYGUARD_DONE_PENDING_TIMEOUT$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().keyguardDOnePendingTimeoutMsg.invoke();
            }
        });
        this.NOTIFY_STARTED_WAKING_UP$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$NOTIFY_STARTED_WAKING_UP$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().notifyStartedWakingUoMsg.invoke();
            }
        });
        this.NOTIFY_STARTED_GOING_TO_SLEEP$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$NOTIFY_STARTED_GOING_TO_SLEEP$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().notifyStartedGoingToSleepMsg.invoke();
            }
        });
        this.SYSTEM_READY$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$SYSTEM_READY$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().systemReadyMsg.invoke();
            }
        });
        this.CANCEL_KEYGUARD_EXIT_ANIM$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$CANCEL_KEYGUARD_EXIT_ANIM$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Integer) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().cancelKeyguardExitAnimMsg.invoke();
            }
        });
        this.setLockScreenShownRunnable = new KeyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1();
        this.occludedSeq = new AtomicInteger(0);
        this.handler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$handler$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (Handler) KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().handler.invoke();
            }
        });
        this.lock$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$lock$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().lock.invoke();
            }
        });
        this.shadeWindowControllerHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$shadeWindowControllerHelper$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) KeyguardViewMediatorHelperImpl.this.notificationShadeWindowControllerLazy.get())).mHelper;
            }
        });
        this.disableFlags = -1;
        this.switchingUserId = -1;
        this.token = new Binder();
        this.displayManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$displayManager$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
            }
        });
        this.refreshRateToken$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$refreshRateToken$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new Binder();
            }
        });
        this.mainMaxRefreshRate$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$mainMaxRefreshRate$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardViewMediatorHelperImpl.this.getClass();
                int i = 0;
                if (StringsKt__StringsKt.contains("30,60,120", DATA.DM_FIELD_INDEX.RSC_ALLOC_MODE, false)) {
                    i = 80;
                } else if (StringsKt__StringsKt.contains("30,60,120", DATA.DM_FIELD_INDEX.VOLTE_ENABLED_BY_USER, false)) {
                    i = 96;
                }
                return Integer.valueOf(i);
            }
        });
        this.subMaxRefreshRate$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$subMaxRefreshRate$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardViewMediatorHelperImpl.this.getClass();
                int i = 0;
                if (StringsKt__StringsKt.contains("", DATA.DM_FIELD_INDEX.RSC_ALLOC_MODE, false)) {
                    i = 80;
                } else if (StringsKt__StringsKt.contains("", DATA.DM_FIELD_INDEX.VOLTE_ENABLED_BY_USER, false)) {
                    i = 96;
                }
                return Integer.valueOf(i);
            }
        });
        this.goingAwayWithAnimation = true;
        this.handleMsgLogKey = -1;
        this.delayedDrawnRunnable = new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$delayedDrawnRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardViewMediatorHelperImpl.this.notifyDrawn();
            }
        };
        this.aodShowStateCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$aodShowStateCallback$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                KeyguardViewMediatorHelperImpl.logD("onAODShowStateChanged: " + KeyguardViewMediatorHelperImpl.this.settingsHelper.isAODShown());
            }
        };
        this.firstKeyguardShown = true;
        this.visibilityListener = new KeyguardViewMediatorHelperImpl$visibilityListener$1(this);
        this.fmmLock = new Object();
        this.carrierLock = new Object();
        this.remoteLockMonitorCallback = new IRemoteLockMonitorCallback.Stub() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$remoteLockMonitorCallback$1
            public final void changeRemoteLockState(RemoteLockInfo remoteLockInfo) {
                int remoteLockType = KeyguardViewMediatorHelperImpl.this.updateMonitor.getRemoteLockType();
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                int i = remoteLockInfo.lockType;
                boolean z = remoteLockInfo.lockState;
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("changeRemoteLockState data=", remoteLockType, " -> ", i, " enableLock=");
                m45m.append(z);
                String sb = m45m.toString();
                keyguardViewMediatorHelperImpl.getClass();
                KeyguardViewMediatorHelperImpl.logD(sb);
                KeyguardViewMediatorHelperImpl.this.updateMonitor.updateRemoteLockInfo(remoteLockInfo);
                KeyguardViewMediatorHelperImpl.access$notifyRemoteLockRequested(KeyguardViewMediatorHelperImpl.this, remoteLockInfo);
            }

            public final int checkRemoteLockPassword(byte[] bArr) {
                return 0;
            }
        };
        this.localReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$localReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                KeyguardViewMediatorHelperImpl.this.getClass();
                KeyguardViewMediatorHelperImpl.logD("onReceive: " + action);
                if (Intrinsics.areEqual("com.samsung.keyguard.CLEAR_LOCK", action)) {
                    KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().adjustStatusBarLocked.invoke();
                    KeyguardViewMediatorHelperImpl.this.resetStateLocked();
                }
            }
        };
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int userId = ((UserTrackerImpl) KeyguardViewMediatorHelperImpl.this.userTracker).getUserId();
                if (Intrinsics.areEqual("com.samsung.pen.INSERT", intent.getAction())) {
                    boolean booleanExtra = intent.getBooleanExtra("penInsert", true);
                    KeyguardViewMediatorHelperImpl.this.getClass();
                    KeyguardViewMediatorHelperImpl.logD("ACTION_PEN_INSERT intent is received. penInsert=" + booleanExtra);
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                    boolean isSecure = keyguardViewMediatorHelperImpl.isSecure();
                    boolean booleanExtra2 = intent.getBooleanExtra("isBoot", false);
                    dagger.Lazy lazy11 = keyguardViewMediatorHelperImpl.viewMediatorLazy;
                    boolean isScreenOn = ((KeyguardViewMediator) lazy11.get()).getViewMediatorCallback().isScreenOn();
                    boolean isShowingAndNotOccluded = ((KeyguardViewMediator) lazy11.get()).isShowingAndNotOccluded();
                    boolean isScreenOffMemoRunning = keyguardViewMediatorHelperImpl.updateMonitor.isScreenOffMemoRunning();
                    SettingsHelper settingsHelper2 = keyguardViewMediatorHelperImpl.settingsHelper;
                    boolean z = DeviceType.isSupportPenDetachmentOption(settingsHelper2.mContext) && settingsHelper2.mItemLists.get("pen_detachment_option").getIntValue() != 0;
                    boolean z2 = (!isShowingAndNotOccluded || isSecure || booleanExtra || !isScreenOn || booleanExtra2 || isScreenOffMemoRunning || !z) ? false : true;
                    if (!z2 && isShowingAndNotOccluded && !isSecure && !booleanExtra && isScreenOn) {
                        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("canBeDismissedWhenSpenDetached isBoot=", booleanExtra2, ", isScreenOffMemoRunning=", isScreenOffMemoRunning, ", hasPenDetachOpt=");
                        m69m.append(z);
                        KeyguardViewMediatorHelperImpl.logD(m69m.toString());
                    }
                    if (z2) {
                        KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_SPEN_DETACHED);
                        ((KeyguardViewMediator) KeyguardViewMediatorHelperImpl.this.viewMediatorLazy.get()).dismiss(null, null);
                        return;
                    }
                    return;
                }
                if (Intrinsics.areEqual("com.samsung.internal.policy.impl.Keyguard.PCW_LOCKED", intent.getAction())) {
                    KeyguardViewMediatorHelperImpl.this.updateMonitor.updateFMMLock(userId, false);
                    boolean isFMMLock = KeyguardViewMediatorHelperImpl.this.updateMonitor.isFMMLock();
                    KeyguardViewMediatorHelperImpl.this.getClass();
                    KeyguardViewMediatorHelperImpl.logD("ACTION_FMM_LOCKED is received isFMMLock : " + isFMMLock);
                    if (isFMMLock) {
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediatorHelperImpl.this;
                        KeyguardViewMediatorHelperImpl.access$notifyRemoteLockRequested(keyguardViewMediatorHelperImpl2, keyguardViewMediatorHelperImpl2.fmmLock);
                        return;
                    }
                    return;
                }
                if (Intrinsics.areEqual("com.samsung.internal.policy.impl.Keyguard.PCW_UNLOCKED", intent.getAction())) {
                    KeyguardViewMediatorHelperImpl.this.getHandler().removeMessages(VolteConstants.ErrorCode.CALL_SESSION_ABORT, KeyguardViewMediatorHelperImpl.this.fmmLock);
                    KeyguardViewMediatorHelperImpl.this.updateMonitor.updateFMMLock(userId, false);
                    boolean isFMMLock2 = KeyguardViewMediatorHelperImpl.this.updateMonitor.isFMMLock();
                    KeyguardViewMediatorHelperImpl.this.getClass();
                    KeyguardViewMediatorHelperImpl.logD("ACTION_FMM_UNLOCKED is received isFMMLock : " + isFMMLock2);
                    if (isFMMLock2 || !KeyguardViewMediatorHelperImpl.this.isShowing()) {
                        return;
                    }
                    if (KeyguardViewMediatorHelperImpl.this.isSecure()) {
                        KeyguardViewMediatorHelperImpl.this.resetStateLocked();
                    } else {
                        KeyguardUnlockInfo.setUnlockTriggerByRemoteLock(0);
                        ((KeyguardViewMediator) r10.viewMediatorLazy.get()).mViewMediatorCallback.keyguardDone(((UserTrackerImpl) KeyguardViewMediatorHelperImpl.this.userTracker).getUserId());
                    }
                    KeyguardViewMediatorHelperImpl.this.f297pm.wakeUp(SystemClock.uptimeMillis());
                    return;
                }
                if (Intrinsics.areEqual("com.sec.android.FindingLostPhonePlus.SUBSCRIBE", intent.getAction())) {
                    KeyguardViewMediatorHelperImpl.this.updateMonitor.updateCarrierLock(userId);
                    boolean isCarrierLock = KeyguardViewMediatorHelperImpl.this.updateMonitor.isCarrierLock();
                    KeyguardViewMediatorHelperImpl.this.getClass();
                    KeyguardViewMediatorHelperImpl.logD("ACTION_CARRIER_LOCK_SUBSCRIBE is received isCarrierLock : " + isCarrierLock);
                    if (isCarrierLock) {
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = KeyguardViewMediatorHelperImpl.this;
                        KeyguardViewMediatorHelperImpl.access$notifyRemoteLockRequested(keyguardViewMediatorHelperImpl3, keyguardViewMediatorHelperImpl3.carrierLock);
                        return;
                    }
                    return;
                }
                if (!Intrinsics.areEqual("com.sec.android.FindingLostPhonePlus.CANCEL", intent.getAction())) {
                    if (LsRune.KEYGUARD_HOMEHUB && Intrinsics.areEqual("android.intent.action.DOCK_EVENT", intent.getAction())) {
                        int intExtra = intent.getIntExtra("pogo_plugged", 0);
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = KeyguardViewMediatorHelperImpl.this;
                        if (keyguardViewMediatorHelperImpl4.pogoPlugged != intExtra) {
                            keyguardViewMediatorHelperImpl4.pogoPlugged = intExtra;
                        }
                        if (keyguardViewMediatorHelperImpl4.pogoPlugged == 0 || !keyguardViewMediatorHelperImpl4.isShowing()) {
                            return;
                        }
                        if (!KeyguardViewMediatorHelperImpl.this.isSecure() || KeyguardViewMediatorHelperImpl.this.updateMonitor.getUserCanSkipBouncer(userId)) {
                            ((KeyguardViewMediator) r9.viewMediatorLazy.get()).mViewMediatorCallback.keyguardDone(((UserTrackerImpl) KeyguardViewMediatorHelperImpl.this.userTracker).getUserId());
                            return;
                        }
                        return;
                    }
                    return;
                }
                KeyguardViewMediatorHelperImpl.this.getHandler().removeMessages(VolteConstants.ErrorCode.CALL_SESSION_ABORT, KeyguardViewMediatorHelperImpl.this.carrierLock);
                KeyguardViewMediatorHelperImpl.this.updateMonitor.updateCarrierLock(userId);
                boolean isCarrierLock2 = KeyguardViewMediatorHelperImpl.this.updateMonitor.isCarrierLock();
                KeyguardViewMediatorHelperImpl.this.getClass();
                KeyguardViewMediatorHelperImpl.logD("ACTION_CARRIER_LOCK_CANCEL is received isCarrierLock : " + isCarrierLock2);
                if (isCarrierLock2) {
                    KeyguardViewMediatorHelperImpl.this.getClass();
                    KeyguardViewMediatorHelperImpl.logD("Carrier Lock is enabled");
                    return;
                }
                KeyguardViewMediatorHelperImpl.this.lockPatternUtils.saveRemoteLockPassword(1, (byte[]) null, userId);
                if (KeyguardViewMediatorHelperImpl.this.isShowing()) {
                    if (KeyguardViewMediatorHelperImpl.this.isSecure()) {
                        KeyguardViewMediatorHelperImpl.this.resetStateLocked();
                    } else {
                        ((KeyguardViewMediator) r10.viewMediatorLazy.get()).mViewMediatorCallback.keyguardDone(((UserTrackerImpl) KeyguardViewMediatorHelperImpl.this.userTracker).getUserId());
                    }
                    KeyguardViewMediatorHelperImpl.this.f297pm.wakeUp(SystemClock.uptimeMillis());
                }
            }
        };
        this.updateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$updateCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onFinishedGoingToSleep(int i) {
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                if (keyguardViewMediatorHelperImpl.settingsHelper.isScreenOffMemoEnabled()) {
                    keyguardViewMediatorHelperImpl.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$updateCallback$1$onFinishedGoingToSleep$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Log.m141i("KeyguardViewMediator", "onFinishedGoingToSleep : ACTION_SNOTE_SCREEN_OFF");
                            KeyguardViewMediatorHelperImpl.this.context.sendBroadcast(new Intent("com.samsung.android.snote.SCREEN_OFF").setPackage("com.samsung.android.app.notes"));
                        }
                    });
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPhoneStateChanged(int i) {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                String str = null;
                String str2 = (String) keyguardViewMediatorHelperImpl.getViewMediatorProvider().updatePhoneState.invoke(null);
                if (i == 0) {
                    str = TelephonyManager.EXTRA_STATE_IDLE;
                } else if (i == 1) {
                    str = TelephonyManager.EXTRA_STATE_RINGING;
                } else if (i == 2) {
                    str = TelephonyManager.EXTRA_STATE_OFFHOOK;
                }
                KeyguardViewMediatorHelperImpl.logD("onPhoneStateChanged " + str2 + " > " + str);
                if (str == null || Intrinsics.areEqual(str2, str)) {
                    return;
                }
                keyguardViewMediatorHelperImpl.getViewMediatorProvider().updatePhoneState.invoke(str);
                dagger.Lazy lazy11 = keyguardViewMediatorHelperImpl.pluginAODManagerLazy;
                ((PluginAODManager) lazy11.get()).mCurrentPhoneState = i;
                ((PluginAODManager) lazy11.get()).updateAnimateScreenOff();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
                KeyguardViewMediatorHelperImpl.this.getViewMediatorProvider().adjustStatusBarLocked.invoke();
            }
        };
        this.knoxStateCallback = new KnoxStateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$knoxStateCallback$1
            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onDPMPasswordChanged() {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                keyguardViewMediatorHelperImpl.getClass();
                KeyguardViewMediatorHelperImpl.logD("received DevicePolicyManager.ACTION_DEVICE_POLICY_MANAGER_PASSWORD_CHANGED!!");
                if (keyguardViewMediatorHelperImpl.isShowing()) {
                    if (!((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).isSecure(((UserTrackerImpl) keyguardViewMediatorHelperImpl.userTracker).getUserId())) {
                        ((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).mViewMediatorCallback.keyguardDone(((UserTrackerImpl) keyguardViewMediatorHelperImpl.userTracker).getUserId());
                    } else {
                        keyguardViewMediatorHelperImpl.resetStateLocked();
                        keyguardViewMediatorHelperImpl.getViewMediatorProvider().adjustStatusBarLocked.invoke();
                    }
                }
            }

            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onDoKeyguard(int i) {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                keyguardViewMediatorHelperImpl.getClass();
                KeyguardViewMediatorHelperImpl.logD("received EnterpriseDeviceManager.ACTION_DO_KEYGUARD_INTERNAL!!");
                if (((UserTrackerImpl) keyguardViewMediatorHelperImpl.userTracker).getUserId() == i) {
                    keyguardViewMediatorHelperImpl.doKeyguardLocked(null);
                }
            }

            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onEnableUCMLock() {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                keyguardViewMediatorHelperImpl.getClass();
                KeyguardViewMediatorHelperImpl.logD("received onEnableUCMLock!!");
                if (keyguardViewMediatorHelperImpl.isShowing()) {
                    keyguardViewMediatorHelperImpl.resetStateLocked();
                } else {
                    keyguardViewMediatorHelperImpl.doKeyguardLocked(null);
                }
            }

            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onUpdateAdminLock() {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                keyguardViewMediatorHelperImpl.getClass();
                KeyguardViewMediatorHelperImpl.logD("received onUpdateAdminLock!!");
                if (!((KnoxStateMonitorImpl) keyguardViewMediatorHelperImpl.knoxStateMonitor).isAdminLockEnabled()) {
                    keyguardViewMediatorHelperImpl.resetStateLocked();
                    return;
                }
                if (!keyguardViewMediatorHelperImpl.isShowing()) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("force_show", true);
                    keyguardViewMediatorHelperImpl.doKeyguardLocked(bundle);
                } else {
                    keyguardViewMediatorHelperImpl.removeMessage(((Number) keyguardViewMediatorHelperImpl.KEYGUARD_DONE$delegate.getValue()).intValue());
                    keyguardViewMediatorHelperImpl.removeMessage(((Number) keyguardViewMediatorHelperImpl.HIDE$delegate.getValue()).intValue());
                    if (((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).isHiding()) {
                        keyguardViewMediatorHelperImpl.getViewMediatorProvider().setHiding.invoke(Boolean.FALSE);
                    }
                    keyguardViewMediatorHelperImpl.resetStateLocked();
                }
            }
        };
        KeyguardDumpLog.logger = samsungServiceLogger;
        SecurityDumpLog.logger = samsungServiceLogger2;
        ((DesktopManagerImpl) desktopManager).registerCallback(new DesktopManager.Callback() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl.1
            @Override // com.android.systemui.util.DesktopManager.Callback
            public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = keyguardViewMediatorHelperImpl.updateMonitor;
                if (keyguardUpdateMonitor2.isRemoteLockMode()) {
                    KeyguardViewMediatorHelperImpl.logD("Need update for remoteLock " + keyguardUpdateMonitor2.getCurrentSecurityMode() + " " + semDesktopModeState);
                    if (semDesktopModeState.getState() != 0) {
                        return;
                    }
                    int enabled = semDesktopModeState.getEnabled();
                    int displayType = semDesktopModeState.getDisplayType();
                    if (displayType == 101 && enabled == 4) {
                        KeyguardViewMediatorHelperImpl.logD("DeX standalone enabled");
                        keyguardViewMediatorHelperImpl.resetStateLocked();
                    } else if (displayType == 0 && enabled == 2) {
                        KeyguardViewMediatorHelperImpl.logD("DeX mode disabled");
                        keyguardViewMediatorHelperImpl.resetStateLocked();
                    }
                }
            }
        });
        this.disabledOccluedeAnimationRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$disabledOccluedeAnimationRunner$1
            public final void onAnimationCancelled() {
                KeyguardViewMediatorHelperImpl.this.getClass();
                KeyguardViewMediatorHelperImpl.logD("Occlude animation cancelled by WM.");
            }

            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                RemoteAnimationTarget remoteAnimationTarget;
                ActivityManager.RunningTaskInfo runningTaskInfo;
                KeyguardViewMediatorHelperImpl.this.lastOccludedApp = (remoteAnimationTargetArr == null || (remoteAnimationTarget = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(remoteAnimationTargetArr)) == null || (runningTaskInfo = remoteAnimationTarget.taskInfo) == null) ? null : runningTaskInfo.topActivity;
                ComponentName componentName = KeyguardViewMediatorHelperImpl.this.lastOccludedApp;
                KeyguardViewMediatorHelperImpl.logD("OccluedeAnimationRunner app=" + (componentName != null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(componentName.getPackageName(), "/", componentName.getClassName()) : null));
                ((KeyguardViewMediator) KeyguardViewMediatorHelperImpl.this.viewMediatorLazy.get()).setOccluded(true, false);
                if (iRemoteAnimationFinishedCallback != null) {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                }
            }
        };
        this.unoccluedAnimationRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$unoccluedAnimationRunner$1
            public final void onAnimationCancelled() {
                KeyguardViewMediatorHelperImpl.this.getClass();
                KeyguardViewMediatorHelperImpl.logD("Unocclude animation cancelled.");
            }

            /* JADX WARN: Type inference failed for: r7v11, types: [T, android.view.SurfaceControl$Transaction] */
            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                RemoteAnimationTarget remoteAnimationTarget;
                RemoteAnimationTarget remoteAnimationTarget2;
                ActivityManager.RunningTaskInfo runningTaskInfo;
                SurfaceControl surfaceControl = null;
                ComponentName componentName = (remoteAnimationTargetArr == null || (remoteAnimationTarget2 = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(remoteAnimationTargetArr)) == null || (runningTaskInfo = remoteAnimationTarget2.taskInfo) == null) ? null : runningTaskInfo.topActivity;
                boolean z = componentName == null || Intrinsics.areEqual(componentName, KeyguardViewMediatorHelperImpl.this.lastOccludedApp);
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                String str = "UnoccluedAnimationRunner app=" + (componentName != null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(componentName.getPackageName(), "/", componentName.getClassName()) : null) + " keepLeash=" + z;
                keyguardViewMediatorHelperImpl.getClass();
                KeyguardViewMediatorHelperImpl.logD(str);
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediatorHelperImpl.this;
                keyguardViewMediatorHelperImpl2.lastOccludedApp = null;
                ((KeyguardViewMediator) keyguardViewMediatorHelperImpl2.viewMediatorLazy.get()).setOccluded(false, false);
                if (remoteAnimationTargetArr != null && (remoteAnimationTarget = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(remoteAnimationTargetArr)) != null) {
                    surfaceControl = remoteAnimationTarget.leash;
                }
                if (surfaceControl == null || iRemoteAnimationFinishedCallback == null) {
                    if (iRemoteAnimationFinishedCallback != null) {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        return;
                    }
                    return;
                }
                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                if (!z) {
                    ?? transaction = new SurfaceControl.Transaction();
                    transaction.setAlpha(surfaceControl, 0.0f);
                    transaction.apply();
                    ref$ObjectRef.element = transaction;
                }
                KeyguardViewMediatorHelperImpl.this.getHandler().postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$unoccluedAnimationRunner$1$onAnimationStart$3
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.lang.Runnable
                    public final void run() {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) ref$ObjectRef.element;
                        if (transaction2 != null) {
                            transaction2.close();
                        }
                    }
                }, 50L);
            }
        };
    }

    public static final void access$notifyRemoteLockRequested(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, Object obj) {
        keyguardViewMediatorHelperImpl.removeMessage(VolteConstants.ErrorCode.CALL_SESSION_ABORT);
        Message obtainMessage = keyguardViewMediatorHelperImpl.getHandler().obtainMessage(VolteConstants.ErrorCode.CALL_SESSION_ABORT, obj);
        if (obj instanceof RemoteLockInfo) {
            keyguardViewMediatorHelperImpl.getHandler().sendMessage(obtainMessage);
        } else {
            keyguardViewMediatorHelperImpl.getHandler().sendMessageDelayed(obtainMessage, 100L);
        }
    }

    public static void logD(String str) {
        if (str != null) {
            Log.m138d("KeyguardViewMediator", str);
        }
    }

    public final void cancelLockWhenCoverIsOpened(boolean z) {
        PendingIntent pendingIntent = this.doKeyguardPendingIntent;
        if (pendingIntent != null) {
            logD("cancelLockWhenCoverIsOpened " + pendingIntent);
            AlarmManager alarmManager = (AlarmManager) getViewMediatorProvider().alarmManager.invoke();
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
            }
            this.doKeyguardPendingIntent = null;
            if (z) {
                getViewMediatorProvider().increaseDelayedShowingSeq.invoke();
            }
        }
    }

    public final void doKeyguardLocked(Bundle bundle) {
        getViewMediatorProvider().doKeyguardLocked.invoke(bundle);
    }

    public final void enableLooperLogController(int i, long j) {
        if (LogUtil.isDebugLevelMid || LogUtil.isDebugLevelHigh) {
            ((LooperSlowLogControllerImpl) this.looperLogController).enable(i, 10L, 20L, j, false, null);
        }
    }

    public final Handler getHandler() {
        return (Handler) this.handler$delegate.getValue();
    }

    public final Object getLock() {
        return this.lock$delegate.getValue();
    }

    public final int getSET_OCCLUDED() {
        return ((Number) this.SET_OCCLUDED$delegate.getValue()).intValue();
    }

    public final int getSHOW() {
        return ((Number) this.SHOW$delegate.getValue()).intValue();
    }

    public final ViewMediatorProvider getViewMediatorProvider() {
        ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
        if (viewMediatorProvider != null) {
            return viewMediatorProvider;
        }
        return null;
    }

    public final boolean hasOccludedMsg$1() {
        return getHandler().hasMessages(getSET_OCCLUDED());
    }

    public final boolean hasShowMsg() {
        return getHandler().hasMessages(getSHOW());
    }

    public final boolean isFastWakeAndUnlockMode() {
        return this.fastUnlockController.isFastWakeAndUnlockMode();
    }

    public final boolean isKeyguardDisabled(boolean z) {
        CoverState coverState;
        boolean z2;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.updateMonitor;
        if (keyguardUpdateMonitor.getRemoteLockType() == 3) {
            return false;
        }
        KnoxStateMonitor knoxStateMonitor = this.knoxStateMonitor;
        if (z) {
            ((KnoxStateMonitorImpl) knoxStateMonitor).getClass();
            if (((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isForcedLock()) {
                z2 = false;
            } else {
                SystemManager systemManager = SystemManager.getInstance();
                z2 = systemManager != null && systemManager.getLockScreenOverrideMode() == 2;
                if (z2) {
                    z2 = true;
                }
            }
            if (z2) {
                return true;
            }
        }
        if ((!keyguardUpdateMonitor.isSecure() || keyguardUpdateMonitor.isUserUnlocked()) && isKeyguardDisabledBySettings(true)) {
            return true;
        }
        if (z) {
            return false;
        }
        if (keyguardUpdateMonitor.isForcedLock()) {
            try {
                ActivityTaskManager.getService().stopSystemLockTaskMode();
            } catch (RemoteException unused) {
                Log.m142w("KeyguardViewMediator", "Failed to stop app pinning");
            }
        }
        BiometricUnlockController biometricUnlockController = (BiometricUnlockController) this.biometricUnlockControllerLazy.get();
        if (biometricUnlockController.hasPendingAuthentication() && biometricUnlockController.mPendingAuthenticated.biometricSourceType == BiometricSourceType.FINGERPRINT) {
            logD("keyguardDisabled: pending fingerprint auth");
            return true;
        }
        if (((KnoxStateMonitorImpl) knoxStateMonitor).isLockScreenDisabledbyKNOX()) {
            logD("keyguardDisabled: it is disabled by Knox");
            return true;
        }
        if (LsRune.COVER_SUPPORTED && (coverState = keyguardUpdateMonitor.getCoverState()) != null && coverState.attached && !coverState.getSwitchState() && this.settingsHelper.isAutomaticUnlockEnabled() && !((KeyguardViewMediator) this.viewMediatorLazy.get()).isSecure() && !((DesktopManagerImpl) this.desktopManager).isDualView()) {
            logD("doKeyguard: not showing because cover is showing");
            return true;
        }
        if (!LsRune.KEYGUARD_HOMEHUB || this.pogoPlugged == 0) {
            return false;
        }
        logD("keyguardDisabled: it is HomeHub device and pogo is plugged");
        return true;
    }

    public final boolean isKeyguardDisabledBySettings(boolean z) {
        if (FactoryTest.isFactoryBinary()) {
            if (z) {
                logD("keyguardDisabled: factory binary");
            }
            return true;
        }
        if (FactoryTest.checkAutomationTestOption(this.context, 0)) {
            if (z) {
                logD("keyguardDisabled: automation test");
            }
            return true;
        }
        if (!(this.settingsHelper.mItemLists.get("access_control_enabled").getIntValue() == 1)) {
            return false;
        }
        if (z) {
            logD("keyguardDisabled: access control is enabled");
        }
        return true;
    }

    public final boolean isKeyguardHiding() {
        return ((KeyguardViewMediator) this.viewMediatorLazy.get()).isHiding();
    }

    public final boolean isSecure() {
        return ((KeyguardViewMediator) this.viewMediatorLazy.get()).isSecure();
    }

    public final boolean isShowing() {
        return ((Boolean) getViewMediatorProvider().isShowing.invoke()).booleanValue();
    }

    public final boolean isUnlockStartedOrFinished() {
        if (!((KeyguardUnlockAnimationController) this.unlockAnimationControllerLazy.get()).playingCannedUnlockAnimation) {
            KeyguardStateController keyguardStateController = this.stateController;
            if (!((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAway && ((KeyguardStateControllerImpl) keyguardStateController).mShowing && !((KeyguardViewMediator) this.viewMediatorLazy.get()).isAnimatingBetweenKeyguardAndSurfaceBehind()) {
                return false;
            }
        }
        return true;
    }

    public final boolean keyguardGoingAway(final int i) {
        boolean z;
        boolean z2;
        try {
            boolean isFastWakeAndUnlockMode = isFastWakeAndUnlockMode();
            KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.fastUnlockController;
            if (!isFastWakeAndUnlockMode || keyguardFastBioUnlockController.isInvisibleAfterGoingAwayTransStarted || keyguardFastBioUnlockController.needsBlankScreen || !((PluginAODManager) this.pluginAODManagerLazy.get()).mIsDifferentOrientation) {
                z2 = false;
            } else {
                logD("needPendingGoingAway: fastWakeAndUnlock and different orientation");
                z2 = true;
            }
            if (z2) {
                keyguardFastBioUnlockController.reservedKeyguardGoingAway = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$keyguardGoingAway$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        View view = ((KeyguardViewController) KeyguardViewMediatorHelperImpl.this.viewControllerLazy.get()).getViewRootImpl().getView();
                        final int i2 = i;
                        final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                        view.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$keyguardGoingAway$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                try {
                                    ActivityTaskManager.getService().keyguardGoingAway(i2);
                                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = keyguardViewMediatorHelperImpl;
                                    int i3 = i2;
                                    CharsKt__CharJVMKt.checkRadix(16);
                                    String concat = "keyguardGoingAway flags=0x".concat(Integer.toString(i3, 16));
                                    keyguardViewMediatorHelperImpl2.getClass();
                                    KeyguardViewMediatorHelperImpl.logD(concat);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        return Unit.INSTANCE;
                    }
                };
            } else {
                ActivityTaskManager.getService().keyguardGoingAway(i);
                CharsKt__CharJVMKt.checkRadix(16);
                logD("keyguardGoingAway flags=0x".concat(Integer.toString(i, 16)));
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

    public final void notifyDrawn() {
        synchronized (getLock()) {
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
            this.foldControllerImpl.resetFoldOpenState(false);
        }
        boolean z = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z) {
            KeyguardWallpaperController keyguardWallpaperController = (KeyguardWallpaperController) this.keyguardWallpaper;
            keyguardWallpaperController.getClass();
            if (z) {
                ((WallpaperLoggerImpl) keyguardWallpaperController.mWallpaperLogger).log("KeyguardWallpaperController", "setKeyguardDismissCancelled() showing = " + keyguardWallpaperController.mIsKeyguardShowing + " , pending type changed = " + keyguardWallpaperController.mIsPendingTypeChange);
                if (keyguardWallpaperController.mRunnableCleanUp != null) {
                    android.util.Log.i("KeyguardWallpaperController", "setKeyguardDismissCancelled, remove clean-up runnable");
                    keyguardWallpaperController.mMainHandler.removeCallbacks(keyguardWallpaperController.mRunnableCleanUp);
                }
                if (keyguardWallpaperController.mIsKeyguardShowing && keyguardWallpaperController.mIsPendingTypeChange) {
                    keyguardWallpaperController.handleWallpaperTypeChanged(keyguardWallpaperController.getLockWallpaperType(true), true);
                    keyguardWallpaperController.mDismissCancelled = false;
                } else {
                    keyguardWallpaperController.mDismissCancelled = true;
                }
            }
        }
        this.fastUnlockController.reset();
        this.disableRemoteUnlockAnimation = false;
        this.fixedRotationMonitor.cancel();
        KeyguardUnlockInfo.reset();
        this.hidingByDisabled = false;
    }

    public final void removeMessage(int i) {
        Handler handler = getHandler();
        if (handler.hasMessages(i)) {
            handler.removeMessages(i);
        }
    }

    public final void removeShowMsg() {
        Handler handler = getHandler();
        if (handler.hasMessages(getSHOW())) {
            handler.removeMessages(getSHOW());
            PowerManager.WakeLock wakeLock = (PowerManager.WakeLock) getViewMediatorProvider().showKeyguardWakeLock.invoke();
            if (wakeLock.isHeld()) {
                wakeLock.release();
            }
        }
    }

    public final void resetStateLocked() {
        getViewMediatorProvider().resetStateLocked.invoke();
    }

    public final void setShowingOptions(Bundle bundle) {
        this.showingOptions = bundle;
        boolean z = false;
        if (bundle != null && bundle.getBoolean("KeyguardExitEditVI", false)) {
            z = true;
        }
        KeyguardEditModeController keyguardEditModeController = this.editModeController;
        if (z && keyguardEditModeController != null) {
            ((KeyguardEditModeControllerImpl) keyguardEditModeController).isEditMode = true;
        }
        Bundle bundle2 = this.showingOptions;
        String string = bundle2 != null ? bundle2.getString("request_id") : null;
        if (string != null && keyguardEditModeController != null) {
            ((KeyguardEditModeControllerImpl) keyguardEditModeController).backupWallpaperRequestId = string;
        }
        Bundle bundle3 = this.showingOptions;
        ParcelFileDescriptor parcelFileDescriptor = bundle3 != null ? (ParcelFileDescriptor) bundle3.getParcelable("preview_pfd_from_preview", ParcelFileDescriptor.class) : null;
        if (keyguardEditModeController != null) {
            ((KeyguardEditModeControllerImpl) keyguardEditModeController).backupWallpaperPreviewPFD = parcelFileDescriptor;
        }
        Bundle bundle4 = this.showingOptions;
        if (bundle4 != null) {
            Log.m141i("KeyguardViewMediator", "setShowingOptions : " + bundle4);
            int i = bundle4.getInt("VideoStartPosition");
            SystemUIWallpaperBase systemUIWallpaperBase = ((KeyguardWallpaperController) this.keyguardWallpaper).mWallpaperView;
            if (systemUIWallpaperBase != null) {
                systemUIWallpaperBase.setStartPosition(i);
            }
        }
    }

    public final void startSetPendingIntent(final PendingIntent pendingIntent, final Intent intent) {
        final String stringExtra = intent != null ? intent.getStringExtra("notificationKey") : null;
        this.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$startSetPendingIntent$1
            @Override // java.lang.Runnable
            public final void run() {
                int startTime = LogUtil.startTime(-1);
                try {
                    PendingIntent pendingIntent2 = pendingIntent;
                    if (pendingIntent2 != null) {
                        String str = stringExtra;
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this;
                        Intent intent2 = intent;
                        if (pendingIntent2.isActivity()) {
                            ActivityManager.getService().resumeAppSwitches();
                        }
                        ActivityOptions makeBasic = ActivityOptions.makeBasic();
                        Display display = keyguardViewMediatorHelperImpl.context.getDisplay();
                        Intrinsics.checkNotNull(display);
                        makeBasic.setLaunchDisplayId(display.getDisplayId());
                        makeBasic.setPendingIntentBackgroundActivityStartMode(1);
                        if (str == null) {
                            pendingIntent2.send(keyguardViewMediatorHelperImpl.context, 0, intent2, null, null, null, makeBasic.toBundle());
                        } else {
                            pendingIntent2.send(null, 0, null, null, null, null, makeBasic.toBundle());
                        }
                    }
                } catch (Exception e) {
                    android.util.Log.e("KeyguardViewMediator", "Cannot send pending intent due to : ", e);
                    KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.ERROR, "Cannot send pending intent due to : ", e);
                }
                String str2 = stringExtra;
                if (str2 != null) {
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this;
                    String concat = "notificationKey=".concat(str2);
                    keyguardViewMediatorHelperImpl2.getClass();
                    KeyguardViewMediatorHelperImpl.logD(concat);
                    NotificationEntry entry = ((NotifPipeline) ((CommonNotifCollection) keyguardViewMediatorHelperImpl2.commonNotifCollectionLazy.get())).getEntry(str2);
                    if (entry != null) {
                        try {
                            ((IStatusBarService) Dependency.get(IStatusBarService.class)).onNotificationClick(str2, NotificationVisibility.obtain(str2, entry.mRanking.getRank(), keyguardViewMediatorHelperImpl2.notificationsController.getActiveNotificationsCount(), true));
                        } catch (RemoteException unused) {
                        }
                    }
                }
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this;
                LogUtil.internalEndTime(startTime, 0, new LongConsumer() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$startSetPendingIntent$1.3
                    @Override // java.util.function.LongConsumer
                    public final void accept(long j) {
                        KeyguardViewMediatorHelperImpl.this.getClass();
                        KeyguardViewMediatorHelperImpl.logD("startSetPendingIntent runnable " + j + "ms");
                    }
                }, null, null, new Object[0]);
            }
        });
        dagger.Lazy lazy = this.viewControllerLazy;
        if (((KeyguardViewController) lazy.get()).isPanelFullyCollapsed() || !((KeyguardViewController) lazy.get()).isBouncerShowing()) {
            return;
        }
        ((CommandQueue) Dependency.get(CommandQueue.class)).animateCollapsePanels(0, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0048, code lost:
    
        if (r8 > 0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x006e, code lost:
    
        if (r5.isLockScreenDisabled(r11) == false) goto L38;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updatePendingLock(int i, long j, boolean z, int i2, KeyguardViewMediator$$ExternalSyntheticLambda2 keyguardViewMediator$$ExternalSyntheticLambda2, KeyguardViewMediator$$ExternalSyntheticLambda9 keyguardViewMediator$$ExternalSyntheticLambda9) {
        if (!LsRune.KEYGUARD_SUB_DISPLAY_COVER || i != 4 || Intrinsics.areEqual(SystemProperties.get("net.mirrorlink.on", ""), "1")) {
            if (i != 3 || j <= 0) {
                boolean z2 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
                LockPatternUtils lockPatternUtils = this.lockPatternUtils;
                if (z2 && i == 4) {
                    if (!(z2 && (lockPatternUtils.getFolderInstantlyLocks(i2) || !((KeyguardViewMediator) this.viewMediatorLazy.get()).isSecure(i2)))) {
                    }
                }
                if (i != 2 || z) {
                }
            }
            if (!Intrinsics.areEqual((String) getViewMediatorProvider().updatePhoneState.invoke(null), TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                if (j > 0) {
                    keyguardViewMediator$$ExternalSyntheticLambda2 = keyguardViewMediator$$ExternalSyntheticLambda9;
                }
            }
            keyguardViewMediator$$ExternalSyntheticLambda2 = null;
        }
        if (keyguardViewMediator$$ExternalSyntheticLambda2 != null) {
            keyguardViewMediator$$ExternalSyntheticLambda2.run();
        }
    }

    public final void updateRefreshRate() {
        IRefreshRateToken iRefreshRateToken;
        boolean z = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
        Lazy lazy = this.mainMaxRefreshRate$delegate;
        if (z || ((Number) lazy.getValue()).intValue() != 0) {
            int i = 0;
            boolean z2 = z && !this.foldControllerImpl.isFoldOpened();
            int intValue = z2 ? ((Number) this.subMaxRefreshRate$delegate.getValue()).intValue() : ((Number) lazy.getValue()).intValue();
            int refreshRateMode = this.settingsHelper.getRefreshRateMode(z2);
            if (refreshRateMode == 1 || refreshRateMode == 2) {
                if (this.curMaxRefresRate != intValue && (iRefreshRateToken = this.refreshRateTokenMaxLimitToken) != null) {
                    try {
                        iRefreshRateToken.release();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    this.refreshRateTokenMaxLimitToken = null;
                }
                if (intValue <= 0 || !((KeyguardViewMediator) this.viewMediatorLazy.get()).isShowingAndNotOccluded() || isUnlockStartedOrFinished()) {
                    IRefreshRateToken iRefreshRateToken2 = this.refreshRateTokenMaxLimitToken;
                    if (iRefreshRateToken2 != null) {
                        try {
                            iRefreshRateToken2.release();
                        } catch (RemoteException e2) {
                            e2.printStackTrace();
                        }
                        this.refreshRateTokenMaxLimitToken = null;
                    }
                } else if (this.refreshRateTokenMaxLimitToken == null) {
                    try {
                        this.refreshRateTokenMaxLimitToken = ((IDisplayManager) this.displayManager$delegate.getValue()).acquireRefreshRateMaxLimitToken((IBinder) this.refreshRateToken$delegate.getValue(), intValue, "KeyguardViewMediator");
                    } catch (RemoteException e3) {
                        e3.printStackTrace();
                    }
                    if (this.refreshRateTokenMaxLimitToken == null) {
                        logD("failed to acquire a RefreshRateMaxLimitToken");
                    }
                }
            } else {
                IRefreshRateToken iRefreshRateToken3 = this.refreshRateTokenMaxLimitToken;
                if (iRefreshRateToken3 != null) {
                    try {
                        iRefreshRateToken3.release();
                    } catch (RemoteException e4) {
                        e4.printStackTrace();
                    }
                    this.refreshRateTokenMaxLimitToken = null;
                }
            }
            if (this.refreshRateTokenMaxLimitToken != null) {
                logD("updateRefreshRate enabled / " + this.curMaxRefresRate + "Hz");
                i = intValue;
            } else {
                logD("updateRefreshRate disabled");
            }
            this.curMaxRefresRate = i;
        }
    }

    public final void notifyDrawn(IKeyguardDrawnCallback iKeyguardDrawnCallback) {
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.screenTuringOnTime;
        if (elapsedRealtime <= 0) {
            elapsedRealtime = 0;
        }
        logD("notifyDrawn " + elapsedRealtime + "ms");
        try {
            iKeyguardDrawnCallback.onDrawn();
        } catch (RemoteException e) {
            Slog.m144w("Exception calling onDrawn():", e);
        }
    }
}
