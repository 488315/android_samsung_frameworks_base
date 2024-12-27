package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
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
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Slog;
import android.view.Display;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.View;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
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
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
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
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.sensor.PickupController;
import com.android.systemui.sensor.PickupController$baseSensorListener$1;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelper;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.ShadeSurface;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
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
import com.sec.ims.configuration.DATA;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.reflect.KFunction;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardViewMediatorHelperImpl implements KeyguardViewMediatorHelper, CommandQueue.Callbacks {
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
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
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
    public int lockSoundStreamId;
    public SoundPool lockSounds;
    public int lockStaySoundId;
    public final LooperSlowLogController looperLogController;
    public final dagger.Lazy notificationShadeWindowControllerLazy;
    public final dagger.Lazy notificationsControllerLazy;
    public final KeyguardViewMediatorHelperImpl$occludeAnimationRunner$1 occludeAnimationRunner;
    public final AtomicInteger occludedSeq;
    public final PickupController pickupController;
    public final dagger.Lazy pluginAODManagerLazy;
    public final PowerManager pm;
    public int pogoPlugged;
    public final KeyguardViewMediatorHelperImpl$remoteLockMonitorCallback$1 remoteLockMonitorCallback;
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
    public final Lazy SHOW$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$SHOW$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
            if (viewMediatorProvider == null) {
                viewMediatorProvider = null;
            }
            return (Integer) viewMediatorProvider.showMsg.invoke();
        }
    });
    public final Lazy HIDE$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$HIDE$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
            if (viewMediatorProvider == null) {
                viewMediatorProvider = null;
            }
            return (Integer) viewMediatorProvider.hideMsg.invoke();
        }
    });
    public final Lazy RESET$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$RESET$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
            if (viewMediatorProvider == null) {
                viewMediatorProvider = null;
            }
            return (Integer) viewMediatorProvider.resetMsg.invoke();
        }
    });

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardViewMediatorHelperImpl.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                KeyguardTransitionInteractor$special$$inlined$filter$3 keyguardTransitionInteractor$special$$inlined$filter$3 = keyguardViewMediatorHelperImpl.keyguardTransitionInteractor.finishedKeyguardTransitionStep;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        TransitionStep transitionStep = (TransitionStep) obj2;
                        KeyguardState keyguardState = transitionStep.from;
                        KeyguardState keyguardState2 = KeyguardState.PRIMARY_BOUNCER;
                        KeyguardState keyguardState3 = transitionStep.to;
                        if ((keyguardState == keyguardState2 && keyguardState3 == KeyguardState.LOCKSCREEN) || (keyguardState == KeyguardState.OCCLUDED && keyguardState3 == KeyguardState.LOCKSCREEN)) {
                            ((KeyguardSurfaceControllerImpl) KeyguardViewMediatorHelperImpl.this.surfaceControllerLazy.get()).setKeyguardSurfaceAppearAmount(1.0f, null);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (keyguardTransitionInteractor$special$$inlined$filter$3.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$occludeAnimationRunner$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$unoccluedAnimationRunner$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$aodAppearAnimationRunner$1] */
    /* JADX WARN: Type inference failed for: r2v83, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$delayedDrawnRunnable$1] */
    /* JADX WARN: Type inference failed for: r2v88, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$remoteLockMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r2v89, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$localReceiver$1] */
    /* JADX WARN: Type inference failed for: r2v90, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$broadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r2v92, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$knoxStateCallback$1] */
    public KeyguardViewMediatorHelperImpl(Context context, BroadcastDispatcher broadcastDispatcher, Executor executor, Executor executor2, dagger.Lazy lazy, dagger.Lazy lazy2, dagger.Lazy lazy3, dagger.Lazy lazy4, dagger.Lazy lazy5, dagger.Lazy lazy6, dagger.Lazy lazy7, dagger.Lazy lazy8, KeyguardFastBioUnlockController keyguardFastBioUnlockController, KeyguardDisplayManager keyguardDisplayManager, InteractionJankMonitor interactionJankMonitor, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, KeyguardSysDumpTrigger keyguardSysDumpTrigger, UserTracker userTracker, SelectedUserInteractor selectedUserInteractor, ActivityManager activityManager, KnoxStateMonitor knoxStateMonitor, DesktopManager desktopManager, PickupController pickupController, LockPatternUtils lockPatternUtils, KeyguardStateController keyguardStateController, DismissCallbackRegistry dismissCallbackRegistry, SysuiStatusBarStateController sysuiStatusBarStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, PowerManager powerManager, dagger.Lazy lazy9, dagger.Lazy lazy10, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, LooperSlowLogController looperSlowLogController, SamsungServiceLogger samsungServiceLogger, AudioManager audioManager, SamsungServiceLogger samsungServiceLogger2, BootAnimationFinishedTrigger bootAnimationFinishedTrigger, BinderCallMonitor binderCallMonitor, SubScreenManager subScreenManager, KeyguardFoldControllerImpl keyguardFoldControllerImpl, KeyguardFixedRotationMonitor keyguardFixedRotationMonitor, KeyguardVisibilityMonitor keyguardVisibilityMonitor, dagger.Lazy lazy11, dagger.Lazy lazy12, KeyguardEditModeController keyguardEditModeController, CarLifeManager carLifeManager, CommandQueue commandQueue, dagger.Lazy lazy13, CoroutineScope coroutineScope, KeyguardTransitionInteractor keyguardTransitionInteractor, DozeParameters dozeParameters) {
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
        this.notificationsControllerLazy = lazy12;
        this.editModeController = keyguardEditModeController;
        this.commandQueue = commandQueue;
        this.briefNowBarControllerLazy = lazy13;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$NOTIFY_FINISHED_GOING_TO_SLEEP$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return (Integer) viewMediatorProvider.notifyFinishedGoingToSleepMsg.invoke();
            }
        });
        this.KEYGUARD_DONE$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$KEYGUARD_DONE$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return (Integer) viewMediatorProvider.keyguardDoneMsg.invoke();
            }
        });
        this.KEYGUARD_DONE_DRAWING$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$KEYGUARD_DONE_DRAWING$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return (Integer) viewMediatorProvider.keyguardDoneDrawingMsg.invoke();
            }
        });
        this.SET_OCCLUDED$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$SET_OCCLUDED$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return (Integer) viewMediatorProvider.setOccludedMsg.invoke();
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$KEYGUARD_TIMEOUT$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return (Integer) viewMediatorProvider.keyguardTimeoutMsg.invoke();
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$DISMISS$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return (Integer) viewMediatorProvider.dismissMsg.invoke();
            }
        });
        this.START_KEYGUARD_EXIT_ANIM$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$START_KEYGUARD_EXIT_ANIM$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return (Integer) viewMediatorProvider.startKeyguardExitAnimMsg.invoke();
            }
        });
        this.KEYGUARD_DONE_PENDING_TIMEOUT$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$KEYGUARD_DONE_PENDING_TIMEOUT$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return (Integer) viewMediatorProvider.keyguardDOnePendingTimeoutMsg.invoke();
            }
        });
        this.NOTIFY_STARTED_WAKING_UP$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$NOTIFY_STARTED_WAKING_UP$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return (Integer) viewMediatorProvider.notifyStartedWakingUoMsg.invoke();
            }
        });
        this.NOTIFY_STARTED_GOING_TO_SLEEP$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$NOTIFY_STARTED_GOING_TO_SLEEP$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return (Integer) viewMediatorProvider.notifyStartedGoingToSleepMsg.invoke();
            }
        });
        this.SYSTEM_READY$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$SYSTEM_READY$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return (Integer) viewMediatorProvider.systemReadyMsg.invoke();
            }
        });
        this.CANCEL_KEYGUARD_EXIT_ANIM$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$CANCEL_KEYGUARD_EXIT_ANIM$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return (Integer) viewMediatorProvider.cancelKeyguardExitAnimMsg.invoke();
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
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return (Handler) viewMediatorProvider.handler.invoke();
            }
        });
        this.lock$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$lock$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                return viewMediatorProvider.lock.invoke();
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
        this.disabled1 = -1;
        this.switchingUserId = -1;
        this.token = new Binder();
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$displayManager$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$refreshRateToken$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new Binder();
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$mainMaxRefreshRate$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardViewMediatorHelperImpl.this.getClass();
                int i = 0;
                if (StringsKt__StringsKt.contains("24,10,30,48,60,80,120", DATA.DM_FIELD_INDEX.RSC_ALLOC_MODE, false)) {
                    i = 80;
                } else if (StringsKt__StringsKt.contains("24,10,30,48,60,80,120", DATA.DM_FIELD_INDEX.VOLTE_ENABLED_BY_USER, false)) {
                    i = 96;
                }
                return Integer.valueOf(i);
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$subMaxRefreshRate$2
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
                StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(remoteLockType, i, "changeRemoteLockState data=", " -> ", " enableLock=");
                m.append(z);
                String sb = m.toString();
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
                if ("com.samsung.keyguard.CLEAR_LOCK".equals(action)) {
                    KeyguardViewMediatorHelperImpl.this.adjustStatusBarLocked$2();
                    KeyguardViewMediatorHelperImpl.this.resetStateLocked$2();
                }
            }
        };
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int userId = ((UserTrackerImpl) KeyguardViewMediatorHelperImpl.this.userTracker).getUserId();
                if ("com.samsung.pen.INSERT".equals(intent.getAction())) {
                    boolean booleanExtra = intent.getBooleanExtra("penInsert", true);
                    KeyguardViewMediatorHelperImpl.this.getClass();
                    KeyguardViewMediatorHelperImpl.logD("ACTION_PEN_INSERT intent is received. penInsert=" + booleanExtra);
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                    if (KeyguardViewMediatorHelperImpl.access$canBeDismissedWhenSpenDetached(keyguardViewMediatorHelperImpl, intent, keyguardViewMediatorHelperImpl.isSecure$2(), booleanExtra)) {
                        Handler handler$1 = KeyguardViewMediatorHelperImpl.this.getHandler$1();
                        final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediatorHelperImpl.this;
                        handler$1.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$broadcastReceiver$1$onReceive$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_SPEN_DETACHED);
                                ((KeyguardViewMediator) KeyguardViewMediatorHelperImpl.this.viewMediatorLazy.get()).dismiss(null, null);
                            }
                        }, 100L);
                        return;
                    }
                    return;
                }
                if ("com.samsung.internal.policy.impl.Keyguard.PCW_LOCKED".equals(intent.getAction())) {
                    KeyguardViewMediatorHelperImpl.this.updateMonitor.updateFMMLock(userId, false);
                    boolean isFMMLock = KeyguardViewMediatorHelperImpl.this.updateMonitor.isFMMLock();
                    KeyguardViewMediatorHelperImpl.this.getClass();
                    KeyguardViewMediatorHelperImpl.logD("ACTION_FMM_LOCKED is received isFMMLock : " + isFMMLock);
                    if (isFMMLock) {
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = KeyguardViewMediatorHelperImpl.this;
                        KeyguardViewMediatorHelperImpl.access$notifyRemoteLockRequested(keyguardViewMediatorHelperImpl3, keyguardViewMediatorHelperImpl3.fmmLock);
                        return;
                    }
                    return;
                }
                if ("com.samsung.internal.policy.impl.Keyguard.PCW_UNLOCKED".equals(intent.getAction())) {
                    KeyguardViewMediatorHelperImpl.this.getHandler$1().removeMessages(VolteConstants.ErrorCode.CALL_SESSION_ABORT, KeyguardViewMediatorHelperImpl.this.fmmLock);
                    KeyguardViewMediatorHelperImpl.this.updateMonitor.updateFMMLock(userId, false);
                    boolean isFMMLock2 = KeyguardViewMediatorHelperImpl.this.updateMonitor.isFMMLock();
                    KeyguardViewMediatorHelperImpl.this.getClass();
                    KeyguardViewMediatorHelperImpl.logD("ACTION_FMM_UNLOCKED is received isFMMLock : " + isFMMLock2);
                    if (isFMMLock2 || !KeyguardViewMediatorHelperImpl.this.isShowing$1()) {
                        return;
                    }
                    if (KeyguardViewMediatorHelperImpl.this.isSecure$2()) {
                        KeyguardViewMediatorHelperImpl.this.resetStateLocked$2();
                    } else {
                        KeyguardUnlockInfo.setUnlockTriggerByRemoteLock(0);
                        ((KeyguardViewMediator) r6.viewMediatorLazy.get()).mViewMediatorCallback.keyguardDone(((UserTrackerImpl) KeyguardViewMediatorHelperImpl.this.userTracker).getUserId());
                    }
                    KeyguardViewMediatorHelperImpl.this.pm.wakeUp(SystemClock.uptimeMillis());
                    return;
                }
                if ("com.sec.android.FindingLostPhonePlus.SUBSCRIBE".equals(intent.getAction())) {
                    KeyguardViewMediatorHelperImpl.this.updateMonitor.updateCarrierLock(userId);
                    boolean isCarrierLock = KeyguardViewMediatorHelperImpl.this.updateMonitor.isCarrierLock();
                    KeyguardViewMediatorHelperImpl.this.getClass();
                    KeyguardViewMediatorHelperImpl.logD("ACTION_CARRIER_LOCK_SUBSCRIBE is received isCarrierLock : " + isCarrierLock);
                    if (isCarrierLock) {
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = KeyguardViewMediatorHelperImpl.this;
                        KeyguardViewMediatorHelperImpl.access$notifyRemoteLockRequested(keyguardViewMediatorHelperImpl4, keyguardViewMediatorHelperImpl4.carrierLock);
                        return;
                    }
                    return;
                }
                if ("com.sec.android.FindingLostPhonePlus.CANCEL".equals(intent.getAction())) {
                    KeyguardViewMediatorHelperImpl.this.getHandler$1().removeMessages(VolteConstants.ErrorCode.CALL_SESSION_ABORT, KeyguardViewMediatorHelperImpl.this.carrierLock);
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
                    if (KeyguardViewMediatorHelperImpl.this.isShowing$1()) {
                        if (KeyguardViewMediatorHelperImpl.this.isSecure$2()) {
                            KeyguardViewMediatorHelperImpl.this.resetStateLocked$2();
                        } else {
                            ((KeyguardViewMediator) r6.viewMediatorLazy.get()).mViewMediatorCallback.keyguardDone(((UserTrackerImpl) KeyguardViewMediatorHelperImpl.this.userTracker).getUserId());
                        }
                        KeyguardViewMediatorHelperImpl.this.pm.wakeUp(SystemClock.uptimeMillis());
                        return;
                    }
                    return;
                }
                if (LsRune.KEYGUARD_HOMEHUB && "android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("pogo_plugged", 0);
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = KeyguardViewMediatorHelperImpl.this;
                    if (keyguardViewMediatorHelperImpl5.pogoPlugged != intExtra) {
                        keyguardViewMediatorHelperImpl5.pogoPlugged = intExtra;
                    }
                    if (keyguardViewMediatorHelperImpl5.pogoPlugged == 0 || !keyguardViewMediatorHelperImpl5.isShowing$1()) {
                        return;
                    }
                    if (!KeyguardViewMediatorHelperImpl.this.isSecure$2() || KeyguardViewMediatorHelperImpl.this.updateMonitor.getUserCanSkipBouncer(userId)) {
                        ((KeyguardViewMediator) r5.viewMediatorLazy.get()).mViewMediatorCallback.keyguardDone(((UserTrackerImpl) KeyguardViewMediatorHelperImpl.this.userTracker).getUserId());
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
                        CscRune.LOCKUI_HELP_TEXT_FOR_CHN = CscRune.VALUE_CONFIG_CARRIER_SECURITY_POLICY.contains("UseSamsungAccountAuth");
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
            public final void onFinishedGoingToSleep(int i) {
                SettingsHelper settingsHelper2;
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                settingsHelper2 = keyguardViewMediatorHelperImpl.settingsHelper;
                if (settingsHelper2.isScreenOffMemoEnabled()) {
                    keyguardViewMediatorHelperImpl.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$updateCallback$1$onFinishedGoingToSleep$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Log.i("KeyguardViewMediator", "onFinishedGoingToSleep : ACTION_SNOTE_SCREEN_OFF");
                            KeyguardViewMediatorHelperImpl.this.context.sendBroadcast(new Intent("com.samsung.android.snote.SCREEN_OFF").setPackage("com.samsung.android.app.notes"));
                        }
                    });
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPhoneStateChanged(int i) {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                String str = (String) viewMediatorProvider.updatePhoneState.invoke(null);
                String str2 = i != 0 ? i != 1 ? i != 2 ? null : TelephonyManager.EXTRA_STATE_OFFHOOK : TelephonyManager.EXTRA_STATE_RINGING : TelephonyManager.EXTRA_STATE_IDLE;
                KeyguardViewMediatorHelperImpl.logD("onPhoneStateChanged " + str + " > " + str2);
                if (str2 == null || Intrinsics.areEqual(str, str2)) {
                    return;
                }
                ViewMediatorProvider viewMediatorProvider2 = keyguardViewMediatorHelperImpl.viewMediatorProvider;
                (viewMediatorProvider2 != null ? viewMediatorProvider2 : null).updatePhoneState.invoke(str2);
                dagger.Lazy lazy14 = keyguardViewMediatorHelperImpl.pluginAODManagerLazy;
                ((PluginAODManager) lazy14.get()).mCurrentPhoneState = i;
                ((PluginAODManager) lazy14.get()).updateAnimateScreenOff();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
                KeyguardViewMediatorHelperImpl.this.adjustStatusBarLocked$2();
            }
        };
        this.knoxStateCallback = new KnoxStateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$knoxStateCallback$1
            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onDPMPasswordChanged() {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                keyguardViewMediatorHelperImpl.getClass();
                KeyguardViewMediatorHelperImpl.logD("received DevicePolicyManager.ACTION_DEVICE_POLICY_MANAGER_PASSWORD_CHANGED!!");
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
            public final void onDoKeyguard(int i) {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                keyguardViewMediatorHelperImpl.getClass();
                KeyguardViewMediatorHelperImpl.logD("received EnterpriseDeviceManager.ACTION_DO_KEYGUARD_INTERNAL!!");
                if (((UserTrackerImpl) keyguardViewMediatorHelperImpl.userTracker).getUserId() == i) {
                    keyguardViewMediatorHelperImpl.doKeyguardLocked$2(null);
                }
            }

            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onEnableUCMLock() {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                keyguardViewMediatorHelperImpl.getClass();
                KeyguardViewMediatorHelperImpl.logD("received onEnableUCMLock!!");
                if (keyguardViewMediatorHelperImpl.isShowing$1()) {
                    keyguardViewMediatorHelperImpl.resetStateLocked$2();
                } else {
                    keyguardViewMediatorHelperImpl.doKeyguardLocked$2(null);
                }
            }

            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onUpdateAdminLock() {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                keyguardViewMediatorHelperImpl.getClass();
                KeyguardViewMediatorHelperImpl.logD("received onUpdateAdminLock!!");
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
                    viewMediatorProvider.setHiding.invoke(Boolean.FALSE);
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
                        keyguardViewMediatorHelperImpl.resetStateLocked$2();
                    } else if (displayType == 0 && enabled == 2) {
                        KeyguardViewMediatorHelperImpl.logD("DeX mode disabled");
                        keyguardViewMediatorHelperImpl.resetStateLocked$2();
                    }
                }
            }
        });
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(null), 3);
        this.occludeAnimationRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$occludeAnimationRunner$1
            public final void onAnimationCancelled() {
                KeyguardViewMediatorHelperImpl.this.getClass();
                KeyguardViewMediatorHelperImpl.logD("Occlude animation cancelled by WM.");
            }

            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                String str;
                KeyguardEditModeController keyguardEditModeController2;
                RemoteAnimationTarget remoteAnimationTarget;
                ActivityManager.RunningTaskInfo runningTaskInfo;
                KeyguardViewMediatorHelperImpl.this.lastOccludedApp = (remoteAnimationTargetArr == null || (remoteAnimationTarget = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(0, remoteAnimationTargetArr)) == null || (runningTaskInfo = remoteAnimationTarget.taskInfo) == null) ? null : runningTaskInfo.topActivity;
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                ComponentName componentName = keyguardViewMediatorHelperImpl.lastOccludedApp;
                if (componentName != null) {
                    String packageName = componentName.getPackageName();
                    String className = componentName.getClassName();
                    KeyguardEditModeController keyguardEditModeController3 = keyguardViewMediatorHelperImpl.editModeController;
                    str = packageName + "/" + className + ", edit=" + (keyguardEditModeController3 != null ? Boolean.valueOf(((KeyguardEditModeControllerImpl) keyguardEditModeController3).isEditMode) : null);
                } else {
                    str = null;
                }
                KeyguardViewMediatorHelperImpl.logD("occludeAnimationRunner app=" + str);
                ComponentName componentName2 = KeyguardViewMediatorHelperImpl.this.lastOccludedApp;
                if (BriefNowBarController.SUGGESTION_ACTIVITY.equals(componentName2 != null ? componentName2.getClassName() : null)) {
                    BriefNowBarController briefNowBarController = (BriefNowBarController) KeyguardViewMediatorHelperImpl.this.briefNowBarControllerLazy.get();
                    final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediatorHelperImpl.this;
                    if (briefNowBarController.startCircleAnimation(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$occludeAnimationRunner$1$onAnimationStart$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardViewMediatorHelperImpl.this.getClass();
                            KeyguardViewMediatorHelperImpl.logD("occludeAnimationRunner finished circle animation");
                            ((KeyguardSurfaceControllerImpl) KeyguardViewMediatorHelperImpl.this.surfaceControllerLazy.get()).setKeyguardSurfaceAppearAmount(0.01f, null);
                            ((KeyguardViewMediator) KeyguardViewMediatorHelperImpl.this.viewMediatorLazy.get()).setOccluded(true, false);
                            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                            if (iRemoteAnimationFinishedCallback2 != null) {
                                iRemoteAnimationFinishedCallback2.onAnimationFinished();
                            }
                        }
                    })) {
                        return;
                    }
                    ((KeyguardViewMediator) KeyguardViewMediatorHelperImpl.this.viewMediatorLazy.get()).setOccluded(true, false);
                    if (iRemoteAnimationFinishedCallback != null) {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        return;
                    }
                    return;
                }
                if (((KeyguardViewMediator) KeyguardViewMediatorHelperImpl.this.viewMediatorLazy.get()).getViewMediatorCallback().isScreenOn()) {
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = KeyguardViewMediatorHelperImpl.this;
                    if (keyguardViewMediatorHelperImpl3.lastOccludedApp != null && ((keyguardEditModeController2 = keyguardViewMediatorHelperImpl3.editModeController) == null || !((KeyguardEditModeControllerImpl) keyguardEditModeController2).isEditMode)) {
                        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                        ((KeyguardSurfaceControllerImpl) KeyguardViewMediatorHelperImpl.this.surfaceControllerLazy.get()).setKeyguardSurfaceAppearAmount(0.01f, transaction);
                        transaction.apply();
                        transaction.close();
                    }
                }
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

            /* JADX WARN: Type inference failed for: r9v8, types: [T, android.view.SurfaceControl$Transaction] */
            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
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
                boolean z = componentName == null || componentName.equals(KeyguardViewMediatorHelperImpl.this.lastOccludedApp);
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                String str = "UnoccluedAnimationRunner app=" + (componentName != null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(componentName.getPackageName(), "/", componentName.getClassName()) : null) + " keepLeash=" + z;
                keyguardViewMediatorHelperImpl.getClass();
                KeyguardViewMediatorHelperImpl.logD(str);
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediatorHelperImpl.this;
                keyguardViewMediatorHelperImpl2.lastOccludedApp = null;
                ((KeyguardViewMediator) keyguardViewMediatorHelperImpl2.viewMediatorLazy.get()).setOccluded(false, false);
                RemoteAnimationTarget remoteAnimationTarget4 = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(0, remoteAnimationTargetArr);
                boolean z2 = ((remoteAnimationTarget4 != null ? remoteAnimationTarget4.taskInfo : null) == null || (remoteAnimationTarget = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(0, remoteAnimationTargetArr)) == null || (runningTaskInfo2 = remoteAnimationTarget.taskInfo) == null || runningTaskInfo2.topActivityType != 5) ? false : true;
                RemoteAnimationTarget remoteAnimationTarget5 = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(0, remoteAnimationTargetArr);
                if (remoteAnimationTarget5 != null) {
                    ViewMediatorProvider viewMediatorProvider = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                    if (viewMediatorProvider == null) {
                        viewMediatorProvider = null;
                    }
                    viewMediatorProvider.setRemoteAnimationTarget.invoke(remoteAnimationTarget5);
                }
                Log.d("KeyguardViewMediator", "unoccludeAnimationRunner: isDream=" + z2);
                if (z2) {
                    ViewMediatorProvider viewMediatorProvider2 = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                    if (viewMediatorProvider2 == null) {
                        viewMediatorProvider2 = null;
                    }
                    viewMediatorProvider2.initAlphaForAnimationTargets.invoke(remoteAnimationTargetArr2);
                    ((DreamViewModel) KeyguardViewMediatorHelperImpl.this.dreamViewModelLazy.get()).startTransitionFromDream();
                    if (iRemoteAnimationFinishedCallback != null) {
                        ViewMediatorProvider viewMediatorProvider3 = KeyguardViewMediatorHelperImpl.this.viewMediatorProvider;
                        (viewMediatorProvider3 != null ? viewMediatorProvider3 : null).setUnoccludeFinishedCallback.invoke(iRemoteAnimationFinishedCallback);
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
                if (!z) {
                    ?? transaction = new SurfaceControl.Transaction();
                    transaction.setAlpha(surfaceControl, 0.0f);
                    transaction.apply();
                    ref$ObjectRef.element = transaction;
                }
                KeyguardEditModeController keyguardEditModeController2 = KeyguardViewMediatorHelperImpl.this.editModeController;
                long j = (keyguardEditModeController2 == null || !((KeyguardEditModeControllerImpl) keyguardEditModeController2).isEditMode) ? 50L : 250L;
                KeyguardViewMediatorHelperImpl.logD("keepSurfaceDuration=" + j);
                KeyguardViewMediatorHelperImpl.this.getHandler$1().postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$unoccluedAnimationRunner$1$onAnimationStart$5
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
                KeyguardViewMediatorHelperImpl.this.getClass();
                KeyguardViewMediatorHelperImpl.logD("AOD Appear animation cancelled by WM.");
                ValueAnimator valueAnimator = KeyguardViewMediatorHelperImpl.this.aodAppearAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
            }

            /* JADX WARN: Type inference failed for: r10v1, types: [T, android.view.RemoteAnimationTarget] */
            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                keyguardViewMediatorHelperImpl.getClass();
                if (KeyguardViewMediatorHelperImplKt.IS_SAFE_MODE_ENABLED) {
                    Log.d("KeyguardViewMediator", "handleOnAnimationStart: return in SystemUI safe mode.");
                } else if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0) {
                    Log.d("KeyguardViewMediator", "handleOnAnimationStart: No apps provided to the Appear Animation runner; skipping appear animation.");
                } else {
                    final List mutableList = ArraysKt___ArraysKt.toMutableList(remoteAnimationTargetArr);
                    final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                    final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                    KeyguardViewMediatorHelperImplKt.aodAppearWallpaperOpeningTarget = null;
                    if (remoteAnimationTargetArr2 != 0) {
                        int length = remoteAnimationTargetArr2.length;
                        for (int i2 = 0; i2 < length; i2++) {
                            ?? r10 = remoteAnimationTargetArr2[i2];
                            Integer valueOf = r10 != 0 ? Integer.valueOf(((RemoteAnimationTarget) r10).mode) : null;
                            if (valueOf != null && valueOf.intValue() == 0) {
                                ref$ObjectRef.element = r10;
                                KeyguardViewMediatorHelperImplKt.aodAppearWallpaperOpeningTarget = r10;
                            } else if (valueOf != null && valueOf.intValue() == 1) {
                                ref$ObjectRef2.element = r10;
                            }
                        }
                    }
                    if (keyguardViewMediatorHelperImpl.unlockedScreenOffAnimationHelper.shouldPlayUnlockedScreenOffAnimation()) {
                        KeyguardViewMediatorHelperImpl.logD("aodAppearAnimationRunner handleOnAnimationStart");
                        keyguardViewMediatorHelperImpl.unlockAnimationExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$handleOnAnimationStart$2
                            @Override // java.lang.Runnable
                            public final void run() {
                                ActivityManager.RunningTaskInfo runningTaskInfo;
                                ComponentName componentName;
                                ValueAnimator valueAnimator = KeyguardViewMediatorHelperImpl.this.aodAppearAnimator;
                                if (valueAnimator != null) {
                                    valueAnimator.cancel();
                                }
                                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediatorHelperImpl.this;
                                keyguardViewMediatorHelperImpl2.aodAppearAnimationFrameCount = 0;
                                keyguardViewMediatorHelperImpl2.aodAppearAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                                final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                                RemoteAnimationTarget remoteAnimationTarget = (RemoteAnimationTarget) ref$ObjectRef2.element;
                                if (remoteAnimationTarget != null) {
                                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = KeyguardViewMediatorHelperImpl.this;
                                    List<RemoteAnimationTarget> list = mutableList;
                                    keyguardViewMediatorHelperImpl3.getClass();
                                    if (list != null) {
                                        for (RemoteAnimationTarget remoteAnimationTarget2 : list) {
                                            if (Intrinsics.areEqual((remoteAnimationTarget2 == null || (runningTaskInfo = remoteAnimationTarget2.taskInfo) == null || (componentName = runningTaskInfo.topActivity) == null) ? null : componentName.getClassName(), "com.sec.android.app.launcher.Launcher")) {
                                                break;
                                            }
                                        }
                                    }
                                    SurfaceControl surfaceControl = remoteAnimationTarget.leash;
                                    Intrinsics.checkNotNull(surfaceControl);
                                    transaction.setAlpha(surfaceControl, 0.0f);
                                    transaction.apply();
                                }
                                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = KeyguardViewMediatorHelperImpl.this;
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
                                                SurfaceControl surfaceControl2 = remoteAnimationTarget3 != null ? remoteAnimationTarget3.leash : null;
                                                Intrinsics.checkNotNull(surfaceControl2);
                                                transaction2.setAlpha(surfaceControl2, 1 - ((Float) valueAnimator3.getAnimatedValue()).floatValue());
                                            }
                                            T t = ref$ObjectRef4.element;
                                            if (t != 0) {
                                                Intrinsics.checkNotNull(t);
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
                                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = KeyguardViewMediatorHelperImpl.this;
                                            keyguardViewMediatorHelperImpl5.aodAppearAnimationFrameCount = 0;
                                            keyguardViewMediatorHelperImpl5.aodAppearAnimator = null;
                                        }

                                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                        public final void onAnimationEnd(Animator animator) {
                                            super.onAnimationEnd(animator);
                                            Log.i("KeyguardViewMediator", "aodAppearAnimator onAnimationEnd aodAppearAnimationFrameCount=" + KeyguardViewMediatorHelperImpl.this.aodAppearAnimationFrameCount);
                                            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback3 = iRemoteAnimationFinishedCallback2;
                                            if (iRemoteAnimationFinishedCallback3 != null) {
                                                iRemoteAnimationFinishedCallback3.onAnimationFinished();
                                            }
                                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = KeyguardViewMediatorHelperImpl.this;
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
        boolean isScreenOn = ((KeyguardViewMediator) lazy.get()).getViewMediatorCallback().isScreenOn();
        boolean isShowingAndNotOccluded = ((KeyguardViewMediator) lazy.get()).isShowingAndNotOccluded();
        boolean isScreenOffMemoRunning = keyguardViewMediatorHelperImpl.updateMonitor.isScreenOffMemoRunning();
        boolean hasPenDetachmentOption = keyguardViewMediatorHelperImpl.settingsHelper.hasPenDetachmentOption();
        if (isShowingAndNotOccluded && !z && !z2 && isScreenOn && !booleanExtra && !isScreenOffMemoRunning && hasPenDetachmentOption) {
            z3 = true;
        }
        if (!z3 && isShowingAndNotOccluded && !z && !z2 && isScreenOn) {
            StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("canBeDismissedWhenSpenDetached isBoot=", ", isScreenOffMemoRunning=", ", hasPenDetachOpt=", booleanExtra, isScreenOffMemoRunning);
            m.append(hasPenDetachmentOption);
            logD(m.toString());
        }
        return z3;
    }

    public static final void access$notifyRemoteLockRequested(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, Object obj) {
        keyguardViewMediatorHelperImpl.removeMessage(VolteConstants.ErrorCode.CALL_SESSION_ABORT);
        Message obtainMessage = keyguardViewMediatorHelperImpl.getHandler$1().obtainMessage(VolteConstants.ErrorCode.CALL_SESSION_ABORT, obj);
        if (obj instanceof RemoteLockInfo) {
            keyguardViewMediatorHelperImpl.getHandler$1().sendMessage(obtainMessage);
        } else {
            keyguardViewMediatorHelperImpl.getHandler$1().sendMessageDelayed(obtainMessage, 100L);
        }
    }

    public static void logD(String str) {
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
            logD("cancelLockWhenCoverIsOpened " + pendingIntent);
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
        viewMediatorProvider.doKeyguardLocked.invoke(bundle);
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

    public final SecNotificationShadeWindowControllerHelper getShadeWindowControllerHelper() {
        return (SecNotificationShadeWindowControllerHelper) this.shadeWindowControllerHelper$delegate.getValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x0147, code lost:
    
        if ((r0 & r1) == r1) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x051e, code lost:
    
        if ((r3 & r2) == r2) goto L275;
     */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x053f, code lost:
    
        logD("handleNotifyScreenTurningOn");
        notifyDrawn();
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x0535, code lost:
    
        if (r2 != false) goto L275;
     */
    /* JADX WARN: Code restructure failed: missing block: B:298:0x053d, code lost:
    
        if (r24.fastUnlockController.isFastWakeAndUnlockMode() == false) goto L275;
     */
    /* JADX WARN: Code restructure failed: missing block: B:385:0x06ea, code lost:
    
        if (r6 != false) goto L377;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:439:0x0809  */
    /* JADX WARN: Removed duplicated region for block: B:442:0x0816  */
    /* JADX WARN: Removed duplicated region for block: B:445:0x0811  */
    /* JADX WARN: Type inference failed for: r2v68, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleSecMessage(android.os.Message r25) {
        /*
            Method dump skipped, instructions count: 2562
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl.handleSecMessage(android.os.Message):void");
    }

    public final boolean hasOccludedMsg$1() {
        return getHandler$1().hasMessages(getSET_OCCLUDED());
    }

    public final boolean initAlphaForAnimationTargets(IRemoteAnimationRunner iRemoteAnimationRunner, SurfaceControl.Transaction transaction, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2) {
        if (!iRemoteAnimationRunner.equals(this.exitAnimationRunner)) {
            return false;
        }
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.fastUnlockController;
        boolean z = !(!keyguardFastBioUnlockController.isFastWakeAndUnlockMode() || this.settingsHelper.isEnabledBiometricUnlockVI() || keyguardFastBioUnlockController.needsBlankScreen) || ((this.settingsHelper.isAnimationRemoved() || this.settingsHelper.isRemoveAnimation() || this.settingsHelper.getTransitionAnimationScale() == 0.0f) && !((StatusBarStateControllerImpl) this.sysuiStatusBarStateController).mLeaveOpenOnKeyguardHide);
        if (z) {
            ArrayList arrayList = new ArrayList();
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr2) {
                if (remoteAnimationTarget.mode == 1) {
                    arrayList.add(remoteAnimationTarget);
                }
            }
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(((RemoteAnimationTarget) it.next()).leash);
            }
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                SurfaceControl surfaceControl = (SurfaceControl) it2.next();
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
            Iterator it3 = arrayList3.iterator();
            while (it3.hasNext()) {
                arrayList4.add(((RemoteAnimationTarget) it3.next()).leash);
            }
            Iterator it4 = arrayList4.iterator();
            while (it4.hasNext()) {
                SurfaceControl surfaceControl2 = (SurfaceControl) it4.next();
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
            Iterator it5 = arrayList5.iterator();
            while (it5.hasNext()) {
                arrayList6.add(((RemoteAnimationTarget) it5.next()).leash);
            }
            Iterator it6 = arrayList6.iterator();
            while (it6.hasNext()) {
                transaction.setAlpha((SurfaceControl) it6.next(), 1.0f);
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
                RemoteAnimationTarget[] remoteAnimationTargetArr = startKeyguardExitAnimParams.mApps;
                if (remoteAnimationTargetArr != null && remoteAnimationTargetArr.length > 1) {
                    i = 3;
                } else if (this.disableRemoteUnlockAnimation) {
                    i = 4;
                } else if ((this.lastGoingAwayFlags & 2) == 2) {
                    i = 5;
                } else if (this.settingsHelper.getTransitionAnimationScale() == 0.0f) {
                    i = 6;
                } else {
                    DesktopManager desktopManager = this.desktopManager;
                    i = (desktopManager.isStandalone() || desktopManager.isDesktopMode()) ? 7 : KeyguardViewMediatorHelperImplKt.DEBUG_DISABLE_REMOTE_UNLOCK_ANIMATION ? 255 : 0;
                }
            }
        }
        if (i == 0) {
            return false;
        }
        logD("isDisabledUnlockAnimation why=" + i);
        return true;
    }

    public final boolean isEnabledBiometricUnlockVI() {
        return this.settingsHelper.isEnabledBiometricUnlockVI();
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
            if (((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isForcedLock()) {
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
                Log.w("KeyguardViewMediator", "Failed to stop app pinning");
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
        if (LsRune.COVER_SUPPORTED && (coverState = keyguardUpdateMonitor.getCoverState()) != null && coverState.attached && !coverState.getSwitchState() && this.settingsHelper.isAutomaticUnlockEnabled() && !((KeyguardViewMediator) this.viewMediatorLazy.get()).isSecure() && !this.desktopManager.isDualView()) {
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
        if (!this.settingsHelper.isAccessControlEnabled()) {
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
        if (!((KeyguardUnlockAnimationController) this.unlockAnimationControllerLazy.get()).playingCannedUnlockAnimation) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.stateController;
            if (!keyguardStateControllerImpl.mKeyguardFadingAway && keyguardStateControllerImpl.mShowing && !((KeyguardViewMediator) this.viewMediatorLazy.get()).isAnimatingBetweenKeyguardAndSurfaceBehind()) {
                return false;
            }
        }
        return true;
    }

    public final boolean keyguardGoingAway(final int i) {
        boolean z;
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.fastUnlockController;
        try {
            if (!keyguardFastBioUnlockController.isFastWakeAndUnlockMode() || keyguardFastBioUnlockController.isInvisibleAfterGoingAwayTransStarted || keyguardFastBioUnlockController.needsBlankScreen || !((PluginAODManager) this.pluginAODManagerLazy.get()).mIsDifferentOrientation) {
                ActivityTaskManager.getService().keyguardGoingAway(i);
                CharsKt__CharJVMKt.checkRadix(16);
                logD("keyguardGoingAway flags=0x".concat(Integer.toString(i, 16)));
            } else {
                logD("needPendingGoingAway: fastWakeAndUnlock and different orientation");
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

    public final void onKeyguardExitFinished$2() {
        this.hidingByDisabled = false;
        ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
        if (viewMediatorProvider == null) {
            viewMediatorProvider = null;
        }
        if (((Boolean) viewMediatorProvider.isWakeAndUnlocking.invoke()).booleanValue() && this.drawnCallback != null) {
            ((KeyguardViewController) this.viewControllerLazy.get()).getViewRootImpl().setReportNextDraw(false, "BioUnlock");
            notifyDrawn();
        }
        this.desktopManager.notifyDismissKeyguard();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.updateMonitor;
        keyguardUpdateMonitor.setUnlockingKeyguard(true);
        keyguardUpdateMonitor.clearFailedUnlockAttempts(false);
        keyguardUpdateMonitor.clearFingerprintRecognized();
        keyguardUpdateMonitor.requestSessionClose();
        this.pm.userActivity(SystemClock.uptimeMillis(), 2, 0);
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
            this.foldControllerImpl.setFoldOpenState(0);
        }
        if (isSecure$2()) {
            this.uiBgExecutor.execute(new KeyguardViewMediatorHelperImpl$onSecurityPropertyUpdated$1(this));
        }
        this.disableRemoteUnlockAnimation = false;
        KeyguardFixedRotationMonitor keyguardFixedRotationMonitor = this.fixedRotationMonitor;
        if (!keyguardFixedRotationMonitor.isFixedRotated()) {
            keyguardFixedRotationMonitor.cancel();
        }
        if (LsRune.AOD_LIGHT_REVEAL && !((SecNotificationShadeWindowControllerHelperImpl) getShadeWindowControllerHelper()).getCurrentState().forceVisibleForUnlockAnimation) {
            ((CentralSurfacesImpl) ((CentralSurfaces) this.centralSurfacesLazy.get())).mLightRevealScrim.setRevealAmount(1.0f);
        }
        if (this.settingsHelper.isRemoveAnimation()) {
            ((StatusBarStateControllerImpl) this.sysuiStatusBarStateController).mLeaveOpenOnKeyguardHide = false;
        }
    }

    public final void onKeyguardGone() {
        this.keyguardDisplayManager.hideAfterKeyguardInvisible();
        if (this.isAODShowStateCbRegistered) {
            this.settingsHelper.unregisterCallback(this.aodShowStateCallback);
        }
        this.isAODShowStateCbRegistered = false;
        this.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$registerSysDumpHeap$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardViewMediatorHelperImpl.this.sysDumpTrigger.getClass();
            }
        });
    }

    public final void playSound$2(final int i) {
        if (this.settingsHelper.isLockSoundEnabled()) {
            this.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$playSound$1
                @Override // java.lang.Runnable
                public final void run() {
                    float semGetSituationVolume;
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                    SoundPool soundPool = keyguardViewMediatorHelperImpl.lockSounds;
                    if (soundPool != null) {
                        soundPool.stop(keyguardViewMediatorHelperImpl.lockSoundStreamId);
                    }
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = KeyguardViewMediatorHelperImpl.this;
                    keyguardViewMediatorHelperImpl2.uiSoundsStreamType = keyguardViewMediatorHelperImpl2.audioManager.getUiSoundsStreamType();
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = KeyguardViewMediatorHelperImpl.this;
                    if (keyguardViewMediatorHelperImpl3.audioManager.isStreamMute(keyguardViewMediatorHelperImpl3.uiSoundsStreamType)) {
                        return;
                    }
                    if (LsRune.KEYGUARD_LOCK_SITUATION_VOLUME) {
                        semGetSituationVolume = 1.0f;
                    } else {
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = KeyguardViewMediatorHelperImpl.this;
                        semGetSituationVolume = keyguardViewMediatorHelperImpl4.audioManager.semGetSituationVolume(i == keyguardViewMediatorHelperImpl4.unlockSoundId ? 7 : 4, 0);
                    }
                    float f = semGetSituationVolume;
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = KeyguardViewMediatorHelperImpl.this;
                    String str = "playSound " + i;
                    keyguardViewMediatorHelperImpl5.getClass();
                    KeyguardViewMediatorHelperImpl.logD(str);
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl6 = KeyguardViewMediatorHelperImpl.this;
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
                            KeyguardFoldControllerImpl.this.changeFoldState(bool.booleanValue());
                        }
                    }
                }));
            }
            if (z2) {
                keyguardFoldControllerImpl.addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$postHandleSystemReady$1
                    @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                    public final void onFoldStateChanged(boolean z3) {
                        final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                        keyguardViewMediatorHelperImpl.getHandler$1().post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$postHandleSystemReady$1$onFoldStateChanged$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ((KeyguardViewMediator) KeyguardViewMediatorHelperImpl.this.viewMediatorLazy.get()).getClass();
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
                removeCallbacks$1$1(this.delayedDrawnRunnable);
                synchronized (this.lock$delegate.getValue()) {
                    try {
                        IKeyguardDrawnCallback iKeyguardDrawnCallback = this.drawnCallback;
                        if (iKeyguardDrawnCallback != null) {
                            ViewMediatorProvider viewMediatorProvider = this.viewMediatorProvider;
                            if (viewMediatorProvider == null) {
                                viewMediatorProvider = null;
                            }
                            if (((Number) viewMediatorProvider.getDelayedShowingSequence.invoke()).intValue() >= 2 && (!LsRune.KEYGUARD_SUB_DISPLAY_LOCK || this.lastSleepReason != 4 || !this.foldControllerImpl.isFoldOpened())) {
                                getHandler$1().post(this.delayedDrawnRunnable);
                            }
                            notifyDrawn(iKeyguardDrawnCallback);
                            this.drawnCallback = null;
                            Unit unit = Unit.INSTANCE;
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
                if (isShowing$1()) {
                    this.desktopManager.notifyShowKeyguard();
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
                boolean isShowing$1 = isShowing$1();
                boolean isHiding = ((KeyguardViewMediator) lazy.get()).isHiding();
                boolean isSecure = ((KeyguardViewMediator) lazy.get()).isSecure();
                boolean z3 = LsRune.COVER_SUPPORTED;
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = this.updateMonitor;
                if (z3) {
                    CoverState coverState = keyguardUpdateMonitor2.getCoverState();
                    int i3 = this.switchingUserId;
                    if (this.lastWakeReason == 103 && isShowing$1 && !isHiding && coverState != null && coverState.attached && coverState.getSwitchState() && this.settingsHelper.isAutomaticUnlockEnabled() && (i3 == -1 ? !isSecure || keyguardUpdateMonitor2.getUserCanSkipBouncer(((UserTrackerImpl) this.userTracker).getUserId()) : !isSecure)) {
                        ViewMediatorProvider viewMediatorProvider2 = this.viewMediatorProvider;
                        (viewMediatorProvider2 != null ? viewMediatorProvider2 : null).handleHide.invoke();
                    }
                }
                if (!isShowing$1 && !getHandler$1().hasMessages(getSHOW())) {
                    keyguardUpdateMonitor2.requestSessionClose();
                } else if (keyguardUpdateMonitor2.isFingerprintOptionEnabled()) {
                    keyguardUpdateMonitor2.updateFingerprintListeningState(2);
                }
                this.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$postHandleNotifyStartedWakingUp$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        String str;
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardViewMediatorHelperImpl.this;
                        int i4 = keyguardViewMediatorHelperImpl.lastWakeReason;
                        KeyguardViewMediatorHelperImpl.logD("updateSALogging " + i4);
                        String str2 = i4 != 1 ? i4 != 4 ? i4 != 7 ? (i4 == 9 || i4 == 103) ? "4" : i4 != 112 ? i4 != 113 ? "5" : "7" : "2" : "6" : "3" : "1";
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
                        if (z) {
                            if (!getHandler$1().hasMessages(((Number) this.NOTIFY_STARTED_WAKING_UP$delegate.getValue()).intValue())) {
                            }
                            Unit unit2 = Unit.INSTANCE;
                        }
                        if (isKeyguardHiding() && !this.hidingByDisabled) {
                            logD("change mHiding = false");
                            ViewMediatorProvider viewMediatorProvider3 = this.viewMediatorProvider;
                            (viewMediatorProvider3 != null ? viewMediatorProvider3 : null).setHiding.invoke(Boolean.FALSE);
                        }
                        Unit unit22 = Unit.INSTANCE;
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
                this.fastUnlockController.reset();
                if (z) {
                    this.foldControllerImpl.resetFoldOpenState$1();
                    if (this.lastSleepReason == 4 && !this.foldControllerImpl.isFoldOpened()) {
                        ((SecNotificationShadeWindowControllerHelperImpl) getShadeWindowControllerHelper()).resetForceInvisible(false);
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
                boolean booleanValue = ((Boolean) (viewMediatorProvider4 != null ? viewMediatorProvider4 : null).isKeyguardDonePending.invoke()).booleanValue();
                logD("handleKeyguardDonePendingTimeout donePending=" + booleanValue);
                if (booleanValue) {
                    ((KeyguardViewMediator) this.viewMediatorLazy.get()).getViewMediatorCallback().readyForKeyguardDone();
                }
            }
        }
        LogUtil.endTime(this.handleMsgLogKey, new KeyguardViewMediatorHelperImpl$endHandleMsgTime$1(this, message.what));
        this.handleMsgLogKey = -1;
    }

    public final void removeCallbacks$1$1(Runnable runnable) {
        Handler handler$1 = getHandler$1();
        if (handler$1.hasCallbacks(runnable)) {
            handler$1.removeCallbacks(runnable);
        }
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
        boolean isSecure = ((KeyguardViewMediator) this.viewMediatorLazy.get()).isSecure();
        int i = this.switchingUserId;
        if (this.lastWakeReason == 103 && coverState != null && coverState.attached && this.settingsHelper.isAutomaticUnlockEnabled()) {
            if (i != -1) {
                if (isSecure) {
                    return;
                }
            } else if (isSecure && !keyguardUpdateMonitor.getUserCanSkipBouncer(((UserTrackerImpl) this.userTracker).getUserId())) {
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
        boolean isEnabledFaceStayOnLock = this.settingsHelper.isEnabledFaceStayOnLock();
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.fastUnlockController;
        if ((!isEnabledFaceStayOnLock && keyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED) && keyguardFastBioUnlockController.biometricSourceType == BiometricSourceType.FACE) || (keyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED) && keyguardFastBioUnlockController.biometricSourceType == BiometricSourceType.FINGERPRINT)) {
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
            ((SecNotificationShadeWindowControllerHelperImpl) getShadeWindowControllerHelper()).setForceInvisible(true);
        }
    }

    public final void setShowingOptions(Bundle bundle) {
        this.showingOptions = bundle;
        KeyguardEditModeController keyguardEditModeController = this.editModeController;
        if (bundle != null && bundle.getBoolean("KeyguardExitEditVI", false) && keyguardEditModeController != null) {
            ((KeyguardEditModeControllerImpl) keyguardEditModeController).isEditMode = true;
        }
        Bundle bundle2 = this.showingOptions;
        String string = bundle2 != null ? bundle2.getString("request_id") : null;
        if (string != null && keyguardEditModeController != null) {
            ((KeyguardEditModeControllerImpl) keyguardEditModeController).backupWallpaperRequestId = string;
        }
        Bundle bundle3 = this.showingOptions;
        ParcelFileDescriptor parcelFileDescriptor = bundle3 != null ? (ParcelFileDescriptor) bundle3.getParcelable("preview_pfd_from_preview", ParcelFileDescriptor.class) : null;
        if (keyguardEditModeController == null) {
            return;
        }
        ((KeyguardEditModeControllerImpl) keyguardEditModeController).backupWallpaperPreviewPFD = parcelFileDescriptor;
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
                    NotificationEntry entry = ((NotifPipeline) ((CommonNotifCollection) keyguardViewMediatorHelperImpl2.commonNotifCollectionLazy.get())).mNotifCollection.getEntry(str2);
                    if (entry != null) {
                        try {
                            IStatusBarService barService = keyguardViewMediatorHelperImpl2.getBarService();
                            if (barService != null) {
                                barService.onNotificationClick(str2, NotificationVisibility.obtain(str2, entry.mRanking.getRank(), ((NotificationsController) keyguardViewMediatorHelperImpl2.notificationsControllerLazy.get()).getActiveNotificationsCount(), true));
                            }
                        } catch (RemoteException unused) {
                        }
                    }
                }
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this;
                LogUtil.endTime(startTime, new LongConsumer() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$startSetPendingIntent$1.3
                    @Override // java.util.function.LongConsumer
                    public final void accept(long j) {
                        KeyguardViewMediatorHelperImpl.this.getClass();
                        KeyguardViewMediatorHelperImpl.logD("startSetPendingIntent runnable " + j + "ms");
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

    /* JADX WARN: Code restructure failed: missing block: B:38:0x006f, code lost:
    
        if (r5.lockPatternUtils.isLockScreenDisabled(r10) == false) goto L39;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePendingLock(int r6, long r7, boolean r9, int r10, com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda1 r11, com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda8 r12) {
        /*
            r5 = this;
            boolean r0 = com.android.systemui.LsRune.KEYGUARD_SUB_DISPLAY_COVER
            r1 = 4
            if (r0 == 0) goto L18
            if (r6 != r1) goto L18
            java.lang.String r0 = "net.mirrorlink.on"
            java.lang.String r2 = ""
            java.lang.String r0 = android.os.SystemProperties.get(r0, r2)
            java.lang.String r2 = "1"
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r2)
            if (r0 != 0) goto L18
            goto L71
        L18:
            r0 = 3
            r2 = 0
            r4 = 0
            if (r6 != r0) goto L22
            int r0 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r0 > 0) goto L4a
        L22:
            boolean r0 = com.android.systemui.LsRune.KEYGUARD_SUB_DISPLAY_LOCK
            if (r0 == 0) goto L45
            if (r6 != r1) goto L45
            if (r0 == 0) goto L41
            com.android.internal.widget.LockPatternUtils r0 = r5.lockPatternUtils
            boolean r0 = r0.getFolderInstantlyLocks(r10)
            if (r0 != 0) goto L45
            dagger.Lazy r0 = r5.viewMediatorLazy
            java.lang.Object r0 = r0.get()
            com.android.systemui.keyguard.KeyguardViewMediator r0 = (com.android.systemui.keyguard.KeyguardViewMediator) r0
            boolean r0 = r0.isSecure(r10)
            if (r0 != 0) goto L41
            goto L45
        L41:
            int r0 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r0 > 0) goto L4a
        L45:
            r0 = 2
            if (r6 != r0) goto L69
            if (r9 != 0) goto L69
        L4a:
            com.android.systemui.keyguard.ViewMediatorProvider r5 = r5.viewMediatorProvider
            if (r5 == 0) goto L4f
            goto L50
        L4f:
            r5 = r4
        L50:
            kotlin.jvm.functions.Function1 r5 = r5.updatePhoneState
            java.lang.Object r5 = r5.invoke(r4)
            java.lang.String r5 = (java.lang.String) r5
            java.lang.String r6 = android.telephony.TelephonyManager.EXTRA_STATE_OFFHOOK
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
            if (r5 != 0) goto L67
            int r5 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r5 > 0) goto L65
            goto L71
        L65:
            r11 = r12
            goto L71
        L67:
            r11 = r4
            goto L71
        L69:
            com.android.internal.widget.LockPatternUtils r5 = r5.lockPatternUtils
            boolean r5 = r5.isLockScreenDisabled(r10)
            if (r5 != 0) goto L67
        L71:
            if (r11 == 0) goto L76
            r11.run()
        L76:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl.updatePendingLock(int, long, boolean, int, com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda1, com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda8):void");
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
            Slog.w("KeyguardViewMediator", "Exception calling onDrawn():", e);
            KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.WARNING, "Exception calling onDrawn():", e);
        }
    }
}
