package com.android.systemui.shade;

import android.R;
import android.app.IActivityManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.IBinder;
import android.os.Process;
import android.os.Trace;
import android.os.UserHandle;
import android.view.Display;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.Rune;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.common.buffer.RingBuffer;
import com.android.systemui.common.buffer.RingBuffer.AnonymousClass1;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpsysTableLogger;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardSurfaceControllerImpl;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImplKt;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.VisibilityController;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.OverlayPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.scene.ui.view.WindowRootViewComponent;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.data.repository.SecNotificationShadeWindowStateRepository;
import com.android.systemui.shade.domain.interactor.SecNotificationShadeWindowStateInteractor;
import com.android.systemui.shade.ui.viewmodel.NotificationShadeWindowModel;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda24;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.ShadeTouchableRegionManager$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import dagger.Lazy;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public class NotificationShadeWindowControllerImpl implements NotificationShadeWindowController, Dumpable, ConfigurationController.ConfigurationListener {
    public final IActivityManager mActivityManager;
    public final AuthController mAuthController;
    public final Executor mBackgroundExecutor;
    public final SysuiColorExtractor mColorExtractor;
    public final Lazy mCommunalInteractor;
    public final Context mContext;
    public int mDeferWindowLayoutParams;
    public final DozeParameters mDozeParameters;
    public ShadeTouchableRegionManager$$ExternalSyntheticLambda1 mForcePluginOpenListener;
    public boolean mHasTopUi;
    public boolean mHasTopUiChanged;
    public final SecNotificationShadeWindowControllerHelperImpl mHelper;
    public final IndicatorCutoutUtil mIndicatorCutoutUtil;
    public final KeyguardBypassController mKeyguardBypassController;
    public final float mKeyguardMaxRefreshRate;
    public final float mKeyguardPreferredRefreshRate;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public boolean mLastKeyguardRotationAllowed;
    public CentralSurfacesImpl$$ExternalSyntheticLambda24 mListener;
    public final ShadeWindowLogger mLogger;
    public WindowManager.LayoutParams mLp;
    public final WindowManager.LayoutParams mLpChanged;
    public final NotificationShadeWindowModel mNotificationShadeWindowModel;
    public float mScreenBrightnessDoze;
    public NotificationShadeDepthController.AnonymousClass1 mScrimsVisibilityListener;
    public final SecNotificationShadeWindowStateInteractor mSecNotificationShadeWindowStateInteractor;
    public final Lazy mShadeInteractorLazy;
    public final WindowManager.LayoutParams mShadeWindowLayoutParams;
    public final AnonymousClass1 mStateListener;
    public final Lazy mUserInteractor;
    public final UserTracker.Callback mUserTrackerCallback;
    public final WindowManager mWindowManager;
    public WindowRootView mWindowRootView;
    public final WindowRootViewComponent.Factory mWindowRootViewComponentFactory;
    public final NotificationShadeWindowState mCurrentState = new NotificationShadeWindowState();
    public final ArrayList mCallbacks = new ArrayList();
    public final NotificationShadeWindowState.Buffer mStateBuffer = new NotificationShadeWindowState.Buffer(100);

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.plugins.statusbar.StatusBarStateController$StateListener, com.android.systemui.shade.NotificationShadeWindowControllerImpl$1] */
    public NotificationShadeWindowControllerImpl(Context context, SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl, WindowRootViewComponent.Factory factory, WindowManager windowManager, IActivityManager iActivityManager, DozeParameters dozeParameters, StatusBarStateController statusBarStateController, ConfigurationController configurationController, KeyguardViewMediator keyguardViewMediator, KeyguardBypassController keyguardBypassController, Executor executor, Executor executor2, SysuiColorExtractor sysuiColorExtractor, DumpManager dumpManager, KeyguardStateController keyguardStateController, AuthController authController, IndicatorCutoutUtil indicatorCutoutUtil, Lazy lazy, ShadeWindowLogger shadeWindowLogger, Lazy lazy2, UserTracker userTracker, NotificationShadeWindowModel notificationShadeWindowModel, Lazy lazy3, WindowManager.LayoutParams layoutParams) throws Resources.NotFoundException {
        final int i = 1;
        final int i2 = 0;
        ?? r5 = new StatusBarStateController.StateListener() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.dozing = z;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                boolean z2 = LsRune.AOD_FULLSCREEN;
                if (!z2 || z) {
                    return;
                }
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = (KeyguardViewMediatorHelperImpl) notificationShadeWindowControllerImpl.mHelper.keyguardViewMediatorHelper;
                keyguardViewMediatorHelperImpl.getClass();
                if (z2 && keyguardViewMediatorHelperImpl.updateMonitor.isLockscreenDisabled() && keyguardViewMediatorHelperImpl.aodAmbientWallpaperHelper.isAODFullScreenAndShowing()) {
                    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    try {
                        RemoteAnimationTarget remoteAnimationTarget = KeyguardViewMediatorHelperImplKt.aodAppearWallpaperOpeningTarget;
                        SurfaceControl surfaceControl = remoteAnimationTarget != null ? remoteAnimationTarget.leash : null;
                        if (surfaceControl != null && surfaceControl.isValid()) {
                            Log.d("KeyguardViewMediator", "setKeyguardAndWallpaperSurfaceInvisible hide aodWallpaper");
                            transaction.setVisibility(surfaceControl, false);
                        }
                        ((KeyguardSurfaceControllerImpl) keyguardViewMediatorHelperImpl.surfaceControllerLazy.get()).setKeyguardSurfaceVisible(transaction);
                        transaction.apply();
                        transaction.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        KeyguardViewMediatorHelperImpl.logD$1("setKeyguardAndWallpaperSurfaceInvisible trace=" + Unit.INSTANCE);
                    }
                }
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDreamingChanged(boolean z) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.dreaming = z;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i3) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.statusBarState = i3;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            }
        };
        this.mStateListener = r5;
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl.2
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onBeforeUserSwitching(int i3) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                if (notificationShadeWindowState.isSwitchingUsers) {
                    return;
                }
                notificationShadeWindowState.isSwitchingUsers = true;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i3, Context context2) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                if (notificationShadeWindowState.isSwitchingUsers) {
                    notificationShadeWindowState.isSwitchingUsers = false;
                    notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                }
            }
        };
        this.mUserTrackerCallback = callback;
        final int i3 = 2;
        secNotificationShadeWindowControllerHelperImpl.provider = new SecNotificationShadeWindowControllerHelperImpl.Provider(new Supplier(this) { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i4 = i2;
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = this.f$0;
                switch (i4) {
                    case 0:
                        return notificationShadeWindowControllerImpl.mCurrentState;
                    case 1:
                        return notificationShadeWindowControllerImpl.mLpChanged;
                    default:
                        return notificationShadeWindowControllerImpl.mLp;
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i4 = i;
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = this.f$0;
                switch (i4) {
                    case 0:
                        return notificationShadeWindowControllerImpl.mCurrentState;
                    case 1:
                        return notificationShadeWindowControllerImpl.mLpChanged;
                    default:
                        return notificationShadeWindowControllerImpl.mLp;
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i4 = i3;
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = this.f$0;
                switch (i4) {
                    case 0:
                        return notificationShadeWindowControllerImpl.mCurrentState;
                    case 1:
                        return notificationShadeWindowControllerImpl.mLpChanged;
                    default:
                        return notificationShadeWindowControllerImpl.mLp;
                }
            }
        }, new Predicate() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = this.f$0;
                return notificationShadeWindowControllerImpl.isExpanded(notificationShadeWindowControllerImpl.mCurrentState, ((Boolean) obj).booleanValue());
            }
        }, new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda7(this, i2));
        secNotificationShadeWindowControllerHelperImpl.isSystemUser = Intrinsics.areEqual(Process.myUserHandle(), UserHandle.SYSTEM);
        this.mHelper = secNotificationShadeWindowControllerHelperImpl;
        this.mContext = context;
        this.mWindowRootViewComponentFactory = factory;
        this.mWindowManager = windowManager;
        this.mActivityManager = iActivityManager;
        this.mDozeParameters = dozeParameters;
        this.mKeyguardStateController = keyguardStateController;
        this.mLogger = shadeWindowLogger;
        this.mShadeWindowLayoutParams = layoutParams;
        this.mScreenBrightnessDoze = dozeParameters.mResources.getInteger(R.integer.device_idle_light_idle_maintenance_min_budget_ms) / 255.0f;
        this.mLpChanged = new WindowManager.LayoutParams();
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mBackgroundExecutor = executor2;
        this.mColorExtractor = sysuiColorExtractor;
        this.mNotificationShadeWindowModel = notificationShadeWindowModel;
        dumpManager.registerCriticalDumpable("{slow}NotificationShadeWindowControllerImpl", this);
        this.mAuthController = authController;
        this.mUserInteractor = lazy2;
        this.mCommunalInteractor = lazy3;
        this.mLastKeyguardRotationAllowed = DeviceState.shouldEnableKeyguardScreenRotation(((KeyguardStateControllerImpl) keyguardStateController).mContext);
        context.getResources().getInteger(com.android.systemui.R.integer.config_lockScreenDisplayTimeout);
        this.mShadeInteractorLazy = lazy;
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) ((SysuiStatusBarStateController) statusBarStateController);
        synchronized (statusBarStateControllerImpl.mListeners) {
            statusBarStateControllerImpl.addListenerInternalLocked(r5, 1);
        }
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        ((UserTrackerImpl) userTracker).addCallback(callback, executor);
        float refreshRate = -1.0f;
        if (context.getResources().getInteger(com.android.systemui.R.integer.config_keyguardRefreshRate) > -1.0f) {
            Display.Mode[] systemSupportedModes = context.getDisplay().getSystemSupportedModes();
            int length = systemSupportedModes.length;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Display.Mode mode = systemSupportedModes[i2];
                if (Math.abs(mode.getRefreshRate() - r0) <= 0.1d) {
                    refreshRate = mode.getRefreshRate();
                    break;
                }
                i2++;
            }
        }
        this.mKeyguardPreferredRefreshRate = refreshRate;
        this.mKeyguardMaxRefreshRate = context.getResources().getInteger(com.android.systemui.R.integer.config_keyguardMaxRefreshRate);
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = this.mHelper;
        Objects.requireNonNull(secNotificationShadeWindowControllerHelperImpl2);
        boolean z = Rune.SYSUI_MULTI_SIM;
        secNotificationShadeWindowControllerHelperImpl2.initPost();
        this.mIndicatorCutoutUtil = indicatorCutoutUtil;
        this.mSecNotificationShadeWindowStateInteractor = (SecNotificationShadeWindowStateInteractor) Dependency.sDependency.getDependencyInner(SecNotificationShadeWindowStateInteractor.class);
    }

    /* JADX WARN: Removed duplicated region for block: B:135:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x031c  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0391  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x03aa  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x03b7  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0407 A[PHI: r10
      0x0407: PHI (r10v8 long) = (r10v6 long), (r10v7 long), (r10v9 long) binds: [B:229:0x042a, B:224:0x041b, B:219:0x0405] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:226:0x041e  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0452  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0477  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0480  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x048c  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0493  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x04b3  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x04bc  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x04ca  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x04d4  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x0509  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x01a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void apply(NotificationShadeWindowState notificationShadeWindowState) {
        KeyguardVisibilityMonitor keyguardVisibilityMonitor;
        WindowRootView windowRootView;
        int i;
        boolean z;
        WindowRootView windowRootView2;
        boolean z2;
        boolean z3;
        KeyguardUpdateMonitor keyguardUpdateMonitor;
        long j;
        long j2;
        List list;
        boolean z4 = notificationShadeWindowState.keyguardShowing;
        boolean z5 = notificationShadeWindowState.keyguardOccluded;
        boolean z6 = notificationShadeWindowState.keyguardNeedsInput;
        boolean z7 = notificationShadeWindowState.panelVisible;
        boolean z8 = notificationShadeWindowState.shadeOrQsExpanded;
        boolean z9 = notificationShadeWindowState.notificationShadeFocusable;
        boolean z10 = notificationShadeWindowState.glanceableHubShowing;
        boolean z11 = notificationShadeWindowState.glanceableHubOrientationAware;
        boolean z12 = notificationShadeWindowState.bouncerShowing;
        boolean z13 = notificationShadeWindowState.keyguardFadingAway;
        boolean z14 = notificationShadeWindowState.keyguardGoingAway;
        boolean z15 = notificationShadeWindowState.qsExpanded;
        boolean z16 = notificationShadeWindowState.headsUpNotificationShowing;
        boolean z17 = notificationShadeWindowState.lightRevealScrimOpaque;
        boolean z18 = notificationShadeWindowState.isSwitchingUsers;
        boolean z19 = notificationShadeWindowState.forceWindowCollapsed;
        boolean z20 = notificationShadeWindowState.forceDozeBrightness;
        boolean z21 = notificationShadeWindowState.forceUserActivity;
        boolean z22 = notificationShadeWindowState.launchingActivityFromNotification;
        boolean z23 = notificationShadeWindowState.mediaBackdropShowing;
        boolean z24 = notificationShadeWindowState.windowNotTouchable;
        Set set = notificationShadeWindowState.componentsForcingTopUi;
        Set set2 = notificationShadeWindowState.forceOpenTokens;
        int i2 = notificationShadeWindowState.statusBarState;
        boolean z25 = notificationShadeWindowState.remoteInputActive;
        boolean z26 = notificationShadeWindowState.forcePluginOpen;
        boolean z27 = notificationShadeWindowState.dozing;
        int i3 = notificationShadeWindowState.scrimsVisibility;
        int i4 = notificationShadeWindowState.backgroundBlurRadius;
        boolean z28 = notificationShadeWindowState.communalVisible;
        long j3 = notificationShadeWindowState.keyguardUserActivityTimeout;
        boolean z29 = notificationShadeWindowState.searchGridTileShowing;
        NotificationShadeWindowState notificationShadeWindowState2 = (NotificationShadeWindowState) this.mStateBuffer.buffer.advance();
        notificationShadeWindowState2.keyguardShowing = z4;
        notificationShadeWindowState2.keyguardOccluded = z5;
        notificationShadeWindowState2.keyguardNeedsInput = z6;
        notificationShadeWindowState2.panelVisible = z7;
        notificationShadeWindowState2.shadeOrQsExpanded = z8;
        notificationShadeWindowState2.notificationShadeFocusable = z9;
        notificationShadeWindowState2.glanceableHubShowing = z10;
        notificationShadeWindowState2.glanceableHubOrientationAware = z11;
        notificationShadeWindowState2.bouncerShowing = z12;
        notificationShadeWindowState2.keyguardFadingAway = z13;
        notificationShadeWindowState2.keyguardGoingAway = z14;
        notificationShadeWindowState2.qsExpanded = z15;
        notificationShadeWindowState2.headsUpNotificationShowing = z16;
        notificationShadeWindowState2.lightRevealScrimOpaque = z17;
        notificationShadeWindowState2.isSwitchingUsers = z18;
        notificationShadeWindowState2.forceWindowCollapsed = z19;
        notificationShadeWindowState2.forceDozeBrightness = z20;
        notificationShadeWindowState2.forceUserActivity = z21;
        notificationShadeWindowState2.launchingActivityFromNotification = z22;
        notificationShadeWindowState2.mediaBackdropShowing = z23;
        notificationShadeWindowState2.windowNotTouchable = z24;
        notificationShadeWindowState2.componentsForcingTopUi.clear();
        notificationShadeWindowState2.componentsForcingTopUi.addAll(set);
        notificationShadeWindowState2.forceOpenTokens.clear();
        notificationShadeWindowState2.forceOpenTokens.addAll(set2);
        notificationShadeWindowState2.statusBarState = i2;
        notificationShadeWindowState2.remoteInputActive = z25;
        notificationShadeWindowState2.forcePluginOpen = z26;
        notificationShadeWindowState2.dozing = z27;
        notificationShadeWindowState2.scrimsVisibility = i3;
        notificationShadeWindowState2.backgroundBlurRadius = i4;
        notificationShadeWindowState2.communalVisible = z28;
        notificationShadeWindowState2.keyguardUserActivityTimeout = j3;
        notificationShadeWindowState2.searchGridTileShowing = z29;
        boolean z30 = notificationShadeWindowState.keyguardShowing;
        ShadeWindowLogger shadeWindowLogger = this.mLogger;
        if ((!z30 && (!notificationShadeWindowState.dozing || !this.mDozeParameters.getAlwaysOn())) || notificationShadeWindowState.mediaBackdropShowing || notificationShadeWindowState.lightRevealScrimOpaque) {
            this.mLpChanged.flags &= -1048577;
        } else {
            this.mLpChanged.flags |= 1048576;
            WindowRootView windowRootView3 = this.mWindowRootView;
            if (windowRootView3 == null || windowRootView3.getWindowToken() == null) {
                ConstantStringsLoggerImpl constantStringsLoggerImpl = shadeWindowLogger.$$delegate_0;
                constantStringsLoggerImpl.getClass();
                LogBuffer.log$default(constantStringsLoggerImpl.buffer, constantStringsLoggerImpl.tag, LogLevel.DEBUG, "Cannot set wallpaper offset. mWindowRootView or it's token is null");
            } else {
                ((WallpaperManager) this.mContext.getSystemService("wallpaper")).setWallpaperOffsets(this.mWindowRootView.getWindowToken(), 0.5f, 0.5f);
            }
        }
        if (notificationShadeWindowState.dozing) {
            this.mLpChanged.privateFlags |= NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        } else {
            this.mLpChanged.privateFlags &= -524289;
        }
        float f = this.mKeyguardPreferredRefreshRate;
        boolean z31 = true;
        if (f <= 0.0f) {
            float f2 = this.mKeyguardMaxRefreshRate;
            if (f2 > 0.0f) {
                boolean z32 = this.mKeyguardBypassController.getBypassEnabled() && notificationShadeWindowState.statusBarState == 1 && !notificationShadeWindowState.keyguardFadingAway && !notificationShadeWindowState.keyguardGoingAway;
                if (notificationShadeWindowState.dozing || z32) {
                    this.mLpChanged.preferredMaxDisplayRefreshRate = f2;
                } else {
                    this.mLpChanged.preferredMaxDisplayRefreshRate = 0.0f;
                }
                Trace.setCounter("display_max_refresh_rate", (long) this.mLpChanged.preferredMaxDisplayRefreshRate);
            }
        } else if (notificationShadeWindowState.statusBarState != 1 || notificationShadeWindowState.keyguardFadingAway || notificationShadeWindowState.keyguardGoingAway) {
            WindowManager.LayoutParams layoutParams = this.mLpChanged;
            layoutParams.preferredMaxDisplayRefreshRate = 0.0f;
            layoutParams.preferredMinDisplayRefreshRate = 0.0f;
            Trace.setCounter("display_set_preferred_refresh_rate", (long) this.mLpChanged.preferredMaxDisplayRefreshRate);
        } else {
            int selectedUserId = ((SelectedUserInteractor) this.mUserInteractor.get()).getSelectedUserId();
            AuthController authController = this.mAuthController;
            if (authController.isUdfpsEnrolled(selectedUserId) && (list = authController.mUdfpsProps) != null && ((FingerprintSensorPropertiesInternal) list.get(0)).sensorType == 3) {
                WindowManager.LayoutParams layoutParams2 = this.mLpChanged;
                layoutParams2.preferredMaxDisplayRefreshRate = f;
                layoutParams2.preferredMinDisplayRefreshRate = f;
            }
            Trace.setCounter("display_set_preferred_refresh_rate", (long) this.mLpChanged.preferredMaxDisplayRefreshRate);
        }
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = this.mHelper;
        WindowManager.LayoutParams layoutParamsChanged = secNotificationShadeWindowControllerHelperImpl.getLayoutParamsChanged();
        boolean z33 = notificationShadeWindowState.bouncerShowing;
        if ((z33 || notificationShadeWindowState.securedWindow) && !(LsRune.KEYGUARD_EM_TOKEN_CAPTURE_WINDOW && secNotificationShadeWindowControllerHelperImpl.engineerModeManager.isCaptureEnabled)) {
            layoutParamsChanged.flags |= 8192;
        } else {
            layoutParamsChanged.flags &= -8193;
        }
        boolean z34 = notificationShadeWindowState.notificationShadeFocusable && notificationShadeWindowState.shadeOrQsExpanded;
        int i5 = 8;
        if ((z33 && (notificationShadeWindowState.keyguardOccluded || notificationShadeWindowState.keyguardNeedsInput)) || ((NotificationRemoteInputManager.ENABLE_REMOTE_INPUT && notificationShadeWindowState.remoteInputActive) || notificationShadeWindowState.glanceableHubShowing)) {
            this.mLpChanged.flags &= -131081;
        } else if (notificationShadeWindowState.isKeyguardShowingAndNotOccluded() || z34) {
            WindowManager.LayoutParams layoutParams3 = this.mLpChanged;
            int i6 = layoutParams3.flags & (-9);
            layoutParams3.flags = i6;
            if (LsRune.SECURITY_BOUNCER_WINDOW) {
                layoutParams3.flags = i6 | 131072;
            } else if (notificationShadeWindowState.keyguardNeedsInput && notificationShadeWindowState.isKeyguardShowingAndNotOccluded()) {
                this.mLpChanged.flags &= -131073;
            } else {
                this.mLpChanged.flags |= 131072;
            }
        } else {
            WindowManager.LayoutParams layoutParams4 = this.mLpChanged;
            layoutParams4.flags = (layoutParams4.flags | 8) & (-131073);
        }
        boolean z35 = LsRune.SECURITY_BOUNCER_WINDOW;
        if (!z35) {
            if (notificationShadeWindowState.bouncerShowing || (NotificationRemoteInputManager.ENABLE_REMOTE_INPUT && notificationShadeWindowState.remoteInputActive)) {
                this.mLpChanged.forciblyShownTypes |= WindowInsets.Type.navigationBars();
            } else {
                this.mLpChanged.forciblyShownTypes &= ~WindowInsets.Type.navigationBars();
            }
        }
        WindowManager.LayoutParams layoutParamsChanged2 = secNotificationShadeWindowControllerHelperImpl.getLayoutParamsChanged();
        int i7 = 5;
        if (notificationShadeWindowState.bouncerShowing || notificationShadeWindowState.isKeyguardShowingAndNotOccluded()) {
            if (secNotificationShadeWindowControllerHelperImpl.isKeyguardScreenRotation && !notificationShadeWindowState.screenOrientationNoSensor) {
                if (!DeviceType.isTablet()) {
                    int i8 = secNotificationShadeWindowControllerHelperImpl.rotation;
                    DisplayLifecycle displayLifecycle = secNotificationShadeWindowControllerHelperImpl.displayLifecycle;
                    if (displayLifecycle.getDisplay(0) == null) {
                        displayLifecycle.addDisplay(0);
                    }
                    if (i8 != displayLifecycle.mDisplayRotationHash.get(0)) {
                        if (displayLifecycle.getDisplay(0) == null) {
                            displayLifecycle.addDisplay(0);
                        }
                        int i9 = displayLifecycle.mDisplayRotationHash.get(0);
                        secNotificationShadeWindowControllerHelperImpl.rotation = i9;
                        ListPopupWindow$$ExternalSyntheticOutline0.m(i9, "adjustScreenOrientation: rotation=", "NotificationShadeWindowController");
                    }
                    if (secNotificationShadeWindowControllerHelperImpl.rotation != 2) {
                        i7 = 2;
                    }
                }
            } else if (LsRune.KEYGUARD_FIX_ROTATION_FOR_FACTORY) {
                i7 = 1;
            }
        } else {
            if (!((LsRune.COVER_SUPPORTED && notificationShadeWindowState.isCoverClosed) ? false : notificationShadeWindowState.dozing)) {
                if (z35 || secNotificationShadeWindowControllerHelperImpl.isKeyguardScreenRotation || !notificationShadeWindowState.bouncerShowing) {
                    i7 = -1;
                }
            }
        }
        layoutParamsChanged2.screenOrientation = i7;
        final boolean zIsExpanded = isExpanded(notificationShadeWindowState);
        shadeWindowLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ShadeWindowLogger$$ExternalSyntheticLambda0 shadeWindowLogger$$ExternalSyntheticLambda0 = new ShadeWindowLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = shadeWindowLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("systemui.shadewindow", logLevel, shadeWindowLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).bool1 = zIsExpanded;
        logBuffer.commit(logMessageObtain);
        boolean z36 = notificationShadeWindowState.forcePluginOpen;
        ConstantStringsLoggerImpl constantStringsLoggerImpl2 = shadeWindowLogger.$$delegate_0;
        if (!z36) {
            if (notificationShadeWindowState.communalVisible) {
                constantStringsLoggerImpl2.getClass();
                LogBuffer.log$default(constantStringsLoggerImpl2.buffer, constantStringsLoggerImpl2.tag, logLevel, "Visibility forced to be true by communal");
            }
            keyguardVisibilityMonitor = secNotificationShadeWindowControllerHelperImpl.visibilityMonitor;
            if (keyguardVisibilityMonitor.cancelExecToken != null && zIsExpanded == keyguardVisibilityMonitor.needsExpand) {
                keyguardVisibilityMonitor.cancelExecToken(true);
            }
            windowRootView = secNotificationShadeWindowControllerHelperImpl.notificationShadeView;
            if (windowRootView != null) {
                if (zIsExpanded) {
                    i5 = 0;
                } else if (!notificationShadeWindowState.forceInvisible) {
                    i5 = 4;
                }
                windowRootView.setVisibility(i5);
            }
            WindowManager.LayoutParams layoutParamsChanged3 = secNotificationShadeWindowControllerHelperImpl.getLayoutParamsChanged();
            if (!notificationShadeWindowState.isKeyguardShowingAndNotOccluded()) {
                int i10 = notificationShadeWindowState.statusBarState;
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = secNotificationShadeWindowControllerHelperImpl.keyguardUpdateMonitor;
                if ((i10 == 1 || keyguardUpdateMonitor2.isFullscreenBouncer()) && !notificationShadeWindowState.qsExpanded) {
                    PluginLockStarManager pluginLockStarManager = (PluginLockStarManager) secNotificationShadeWindowControllerHelperImpl.pluginLockStarManagerLazy.get();
                    if (pluginLockStarManager != null && pluginLockStarManager.isLockStarEnabled()) {
                        i = 2;
                        keyguardUpdateMonitor = keyguardUpdateMonitor2;
                        if (notificationShadeWindowState.lockStarTimeOutValue > 0) {
                            layoutParamsChanged3.userActivityTimeout = -1L;
                            layoutParamsChanged3.screenDimDuration = -1L;
                        }
                    } else {
                        i = 2;
                        keyguardUpdateMonitor = keyguardUpdateMonitor2;
                    }
                    PluginLockMediator pluginLockMediator = secNotificationShadeWindowControllerHelperImpl.pluginLockMediator;
                    if ((pluginLockMediator != null ? pluginLockMediator.isDynamicLockEnabled() : false) && notificationShadeWindowState.userScreenTimeOut) {
                        layoutParamsChanged3.userActivityTimeout = -1L;
                        layoutParamsChanged3.screenDimDuration = -1L;
                    } else if (!z35 && notificationShadeWindowState.bouncerShowing) {
                        j = notificationShadeWindowState.keyguardUserActivityTimeout;
                        j2 = 10000;
                        if (j < 10000) {
                        }
                        layoutParamsChanged3.userActivityTimeout = j;
                        layoutParamsChanged3.screenDimDuration = 0L;
                    } else if (AccessibilityManager.getInstance(secNotificationShadeWindowControllerHelperImpl.context).isTouchExplorationEnabled()) {
                        long j4 = notificationShadeWindowState.keyguardUserActivityTimeout;
                        j2 = SecNotificationShadeWindowControllerHelperImpl.AWAKE_INTERVAL_DEFAULT_MS_WITH_ACCESSIBILITY;
                        if (j4 < j2) {
                            j = j2;
                            layoutParamsChanged3.userActivityTimeout = j;
                            layoutParamsChanged3.screenDimDuration = 0L;
                        } else {
                            if (keyguardUpdateMonitor.isFaceOptionEnabled()) {
                                long j5 = notificationShadeWindowState.keyguardUserActivityTimeout;
                                j2 = SecNotificationShadeWindowControllerHelperImpl.AWAKE_INTERVAL_DEFAULT_MS_WITH_FACE;
                                if (j5 < j2) {
                                }
                                layoutParamsChanged3.userActivityTimeout = j;
                                layoutParamsChanged3.screenDimDuration = 0L;
                            }
                            j = notificationShadeWindowState.keyguardUserActivityTimeout;
                            layoutParamsChanged3.userActivityTimeout = j;
                            layoutParamsChanged3.screenDimDuration = 0L;
                        }
                    }
                } else {
                    i = 2;
                    layoutParamsChanged3.userActivityTimeout = -1L;
                    layoutParamsChanged3.screenDimDuration = -1L;
                }
            }
            if (notificationShadeWindowState.isKeyguardShowingAndNotOccluded() || notificationShadeWindowState.statusBarState != 1 || notificationShadeWindowState.qsExpanded || notificationShadeWindowState.forceUserActivity) {
                this.mLpChanged.inputFeatures &= -3;
            } else {
                this.mLpChanged.inputFeatures |= i;
            }
            z = !notificationShadeWindowState.isKeyguardShowingAndNotOccluded();
            windowRootView2 = this.mWindowRootView;
            if (windowRootView2 != null && windowRootView2.getFitsSystemWindows() != z) {
                this.mWindowRootView.setFitsSystemWindows(z);
                this.mWindowRootView.requestApplyInsets();
            }
            if (notificationShadeWindowState.headsUpNotificationShowing) {
                this.mLpChanged.flags &= -33;
            } else {
                this.mLpChanged.flags |= 32;
            }
            if (notificationShadeWindowState.forceDozeBrightness) {
                this.mLpChanged.screenBrightness = -1.0f;
            } else {
                this.mLpChanged.screenBrightness = this.mScreenBrightnessDoze;
            }
            if (notificationShadeWindowState.componentsForcingTopUi.isEmpty() && !isExpanded(notificationShadeWindowState) && !notificationShadeWindowState.isSwitchingUsers) {
                z31 = false;
            }
            this.mHasTopUiChanged = z31;
            if (notificationShadeWindowState.windowNotTouchable) {
                this.mLpChanged.flags &= -17;
            } else {
                this.mLpChanged.flags |= 16;
            }
            if (isExpanded(notificationShadeWindowState)) {
                this.mLpChanged.privateFlags |= 16777216;
            } else {
                this.mLpChanged.privateFlags &= -16777217;
            }
            secNotificationShadeWindowControllerHelperImpl.applyHelper(notificationShadeWindowState);
            SecNotificationShadeWindowStateRepository secNotificationShadeWindowStateRepository = this.mSecNotificationShadeWindowStateInteractor.repository;
            secNotificationShadeWindowStateRepository._state.setValue(notificationShadeWindowState);
            secNotificationShadeWindowStateRepository._shadeOrQsExpanded.updateState(null, Boolean.valueOf(notificationShadeWindowState.shadeOrQsExpanded));
            secNotificationShadeWindowStateRepository._statusBarState.updateState(null, Integer.valueOf(notificationShadeWindowState.statusBarState));
            applyWindowLayoutParams();
            z2 = this.mHasTopUi;
            z3 = this.mHasTopUiChanged;
            if (z2 != z3) {
                this.mHasTopUi = z3;
                this.mBackgroundExecutor.execute(new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda1(this, 0));
            }
            notifyStateChangedCallbacks();
            new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0(1, this, notificationShadeWindowState).run();
        }
        CentralSurfacesImpl$$ExternalSyntheticLambda24 centralSurfacesImpl$$ExternalSyntheticLambda24 = this.mListener;
        if (centralSurfacesImpl$$ExternalSyntheticLambda24 != null) {
            CentralSurfacesImpl.AnonymousClass3.this.mOverlays.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$3$Callback$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((OverlayPlugin) obj).setCollapseDesired(zIsExpanded);
                }
            });
        }
        constantStringsLoggerImpl2.getClass();
        LogBuffer.log$default(constantStringsLoggerImpl2.buffer, constantStringsLoggerImpl2.tag, logLevel, "Visibility forced to be true");
        zIsExpanded = true;
        keyguardVisibilityMonitor = secNotificationShadeWindowControllerHelperImpl.visibilityMonitor;
        if (keyguardVisibilityMonitor.cancelExecToken != null) {
            keyguardVisibilityMonitor.cancelExecToken(true);
        }
        windowRootView = secNotificationShadeWindowControllerHelperImpl.notificationShadeView;
        if (windowRootView != null) {
        }
        WindowManager.LayoutParams layoutParamsChanged32 = secNotificationShadeWindowControllerHelperImpl.getLayoutParamsChanged();
        if (!notificationShadeWindowState.isKeyguardShowingAndNotOccluded()) {
        }
        if (notificationShadeWindowState.isKeyguardShowingAndNotOccluded()) {
            this.mLpChanged.inputFeatures &= -3;
        }
        z = !notificationShadeWindowState.isKeyguardShowingAndNotOccluded();
        windowRootView2 = this.mWindowRootView;
        if (windowRootView2 != null) {
            this.mWindowRootView.setFitsSystemWindows(z);
            this.mWindowRootView.requestApplyInsets();
        }
        if (notificationShadeWindowState.headsUpNotificationShowing) {
        }
        if (notificationShadeWindowState.forceDozeBrightness) {
        }
        if (notificationShadeWindowState.componentsForcingTopUi.isEmpty()) {
            z31 = false;
        }
        this.mHasTopUiChanged = z31;
        if (notificationShadeWindowState.windowNotTouchable) {
        }
        if (isExpanded(notificationShadeWindowState)) {
        }
        secNotificationShadeWindowControllerHelperImpl.applyHelper(notificationShadeWindowState);
        SecNotificationShadeWindowStateRepository secNotificationShadeWindowStateRepository2 = this.mSecNotificationShadeWindowStateInteractor.repository;
        secNotificationShadeWindowStateRepository2._state.setValue(notificationShadeWindowState);
        secNotificationShadeWindowStateRepository2._shadeOrQsExpanded.updateState(null, Boolean.valueOf(notificationShadeWindowState.shadeOrQsExpanded));
        secNotificationShadeWindowStateRepository2._statusBarState.updateState(null, Integer.valueOf(notificationShadeWindowState.statusBarState));
        applyWindowLayoutParams();
        z2 = this.mHasTopUi;
        z3 = this.mHasTopUiChanged;
        if (z2 != z3) {
        }
        notifyStateChangedCallbacks();
        new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0(1, this, notificationShadeWindowState).run();
    }

    public final void applyWindowLayoutParams() {
        WindowManager.LayoutParams layoutParams;
        if (this.mDeferWindowLayoutParams != 0 || (layoutParams = this.mLp) == null || layoutParams.copyFrom(this.mLpChanged) == 0) {
            return;
        }
        Trace.beginSection("updateViewLayout");
        this.mWindowManager.updateViewLayout(this.mWindowRootView, this.mLp);
        Trace.endSection();
    }

    public final void batchApplyWindowLayoutParams(Runnable runnable) {
        this.mDeferWindowLayoutParams++;
        runnable.run();
        this.mDeferWindowLayoutParams--;
        applyWindowLayoutParams();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        MagnificationImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "NotificationShadeWindowController:", "  mKeyguardMaxRefreshRate="), this.mKeyguardMaxRefreshRate, printWriter, "  mKeyguardPreferredRefreshRate="), this.mKeyguardPreferredRefreshRate, printWriter, "  preferredMinDisplayRefreshRate="), this.mLpChanged.preferredMinDisplayRefreshRate, printWriter, "  preferredMaxDisplayRefreshRate="), this.mLpChanged.preferredMaxDisplayRefreshRate, printWriter, "  mDeferWindowLayoutParams="), this.mDeferWindowLayoutParams, printWriter);
        boolean z = Rune.SYSUI_MULTI_SIM;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = this.mHelper;
        secNotificationShadeWindowControllerHelperImpl.getClass();
        if (LsRune.KEYGUARD_EM_TOKEN_CAPTURE_WINDOW) {
            ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  EMM=", secNotificationShadeWindowControllerHelperImpl.engineerModeManager.isCaptureEnabled);
        }
        printWriter.println(this.mCurrentState);
        WindowRootView windowRootView = this.mWindowRootView;
        if (windowRootView != null && windowRootView.getViewRootImpl() != null) {
            Trace.beginSection("mWindowRootView.dump()");
            this.mWindowRootView.getViewRootImpl().dump("  ", printWriter);
            Trace.endSection();
        }
        Trace.beginSection("Table<State>");
        List list = NotificationShadeWindowState.TABLE_HEADERS;
        NotificationShadeWindowState.Buffer buffer = this.mStateBuffer;
        buffer.getClass();
        RingBuffer ringBuffer = buffer.buffer;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(ringBuffer, 10));
        ringBuffer.getClass();
        RingBuffer.AnonymousClass1 anonymousClass1 = ringBuffer.new AnonymousClass1();
        while (anonymousClass1.hasNext()) {
            arrayList.add((List) ((NotificationShadeWindowState) anonymousClass1.next()).asStringList$delegate.getValue());
        }
        new DumpsysTableLogger("NotificationShadeWindowController", list, arrayList).printTableData(printWriter);
        Trace.endSection();
    }

    public final boolean isExpanded(NotificationShadeWindowState notificationShadeWindowState) {
        this.mHelper.getClass();
        return isExpanded(notificationShadeWindowState, android.util.Log.isLoggable(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, 3));
    }

    public final void notifyStateChangedCallbacks() {
        for (StatusBarWindowCallback statusBarWindowCallback : (List) this.mCallbacks.stream().map(new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda8()).filter(new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda9()).collect(Collectors.toList())) {
            NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
            statusBarWindowCallback.onStateChanged(notificationShadeWindowState.keyguardShowing, notificationShadeWindowState.keyguardOccluded, notificationShadeWindowState.keyguardGoingAway, notificationShadeWindowState.bouncerShowing, notificationShadeWindowState.dozing, notificationShadeWindowState.shadeOrQsExpanded, notificationShadeWindowState.dreaming, notificationShadeWindowState.communalVisible);
        }
    }

    public void onCommunalVisibleChanged(Boolean bool) {
        boolean zBooleanValue = bool.booleanValue();
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        notificationShadeWindowState.communalVisible = zBooleanValue;
        apply(notificationShadeWindowState);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        int i = SceneContainerFlag.$r8$clinit;
        boolean zShouldEnableKeyguardScreenRotation = DeviceState.shouldEnableKeyguardScreenRotation(((KeyguardStateControllerImpl) this.mKeyguardStateController).mContext);
        if (this.mLastKeyguardRotationAllowed != zShouldEnableKeyguardScreenRotation) {
            apply(this.mCurrentState);
            this.mLastKeyguardRotationAllowed = zShouldEnableKeyguardScreenRotation;
        }
    }

    @Override // com.android.systemui.statusbar.RemoteInputController.Callback
    public final void onRemoteInputActive(boolean z) {
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        notificationShadeWindowState.remoteInputActive = z;
        apply(notificationShadeWindowState);
    }

    public void onShadeOrQsExpanded(Boolean bool) {
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        if (notificationShadeWindowState.shadeOrQsExpanded != bool.booleanValue()) {
            notificationShadeWindowState.shadeOrQsExpanded = bool.booleanValue();
            apply(notificationShadeWindowState);
            IBinder windowToken = this.mWindowRootView.getWindowToken();
            if (windowToken != null) {
                this.mBackgroundExecutor.execute(new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0(0, windowToken, bool));
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onThemeChanged() {
        if (this.mWindowRootView == null) {
            return;
        }
        boolean zSupportsDarkText = this.mColorExtractor.mNeutralColorsLock.supportsDarkText();
        int systemUiVisibility = this.mWindowRootView.getSystemUiVisibility();
        this.mWindowRootView.setSystemUiVisibility(zSupportsDarkText ? systemUiVisibility | 8208 : systemUiVisibility & (-8209));
    }

    public final void registerCallback(StatusBarWindowCallback statusBarWindowCallback) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            if (((WeakReference) this.mCallbacks.get(i)).get() == statusBarWindowCallback) {
                return;
            }
        }
        this.mCallbacks.add(new WeakReference(statusBarWindowCallback));
    }

    public final void setForcePluginOpen(Object obj, boolean z) {
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        if (z) {
            notificationShadeWindowState.forceOpenTokens.add(obj);
        } else {
            notificationShadeWindowState.forceOpenTokens.remove(obj);
        }
        boolean z2 = notificationShadeWindowState.forcePluginOpen;
        notificationShadeWindowState.forcePluginOpen = !notificationShadeWindowState.forceOpenTokens.isEmpty();
        if (z2 != notificationShadeWindowState.forcePluginOpen) {
            apply(notificationShadeWindowState);
            ShadeTouchableRegionManager$$ExternalSyntheticLambda1 shadeTouchableRegionManager$$ExternalSyntheticLambda1 = this.mForcePluginOpenListener;
            if (shadeTouchableRegionManager$$ExternalSyntheticLambda1 != null) {
                boolean z3 = notificationShadeWindowState.forcePluginOpen;
                shadeTouchableRegionManager$$ExternalSyntheticLambda1.f$0.updateTouchableRegion();
            }
        }
    }

    public final void setKeyguardFadingAway(boolean z) {
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = this.mHelper;
        Objects.requireNonNull(secNotificationShadeWindowControllerHelperImpl);
        boolean z2 = Rune.SYSUI_MULTI_SIM;
        if (secNotificationShadeWindowControllerHelperImpl.getCurrentState().keyguardFadingAway != z) {
            Log.d(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("keyguardFadingAway ", z));
        }
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        notificationShadeWindowState.keyguardFadingAway = z;
        apply(notificationShadeWindowState);
    }

    public final void setKeyguardShowing(boolean z) {
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        notificationShadeWindowState.keyguardShowing = z;
        apply(notificationShadeWindowState);
    }

    public final void setNotificationShadeFocusable(boolean z) {
        ShadeWindowLogger shadeWindowLogger = this.mLogger;
        shadeWindowLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ShadeWindowLogger$$ExternalSyntheticLambda0 shadeWindowLogger$$ExternalSyntheticLambda0 = new ShadeWindowLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = shadeWindowLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("systemui.shadewindow", logLevel, shadeWindowLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        logBuffer.commit(logMessageObtain);
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        notificationShadeWindowState.notificationShadeFocusable = z;
        apply(notificationShadeWindowState);
    }

    public final void setPanelVisible(boolean z) {
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        if (notificationShadeWindowState.panelVisible == z && notificationShadeWindowState.notificationShadeFocusable == z) {
            return;
        }
        ShadeWindowLogger shadeWindowLogger = this.mLogger;
        shadeWindowLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ShadeWindowLogger$$ExternalSyntheticLambda0 shadeWindowLogger$$ExternalSyntheticLambda0 = new ShadeWindowLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = shadeWindowLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("systemui.shadewindow", logLevel, shadeWindowLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        logBuffer.commit(logMessageObtain);
        notificationShadeWindowState.panelVisible = z;
        notificationShadeWindowState.notificationShadeFocusable = z;
        apply(notificationShadeWindowState);
    }

    public final void setRequestTopUi(String str, boolean z) {
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        if (z) {
            notificationShadeWindowState.componentsForcingTopUi.add(str);
        } else {
            notificationShadeWindowState.componentsForcingTopUi.remove(str);
        }
        apply(notificationShadeWindowState);
    }

    public final boolean isExpanded(NotificationShadeWindowState notificationShadeWindowState, boolean z) {
        VisibilityController visibilityController;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl;
        boolean z2 = (!notificationShadeWindowState.forceWindowCollapsed && (notificationShadeWindowState.isKeyguardShowingAndNotOccluded() || notificationShadeWindowState.panelVisible || notificationShadeWindowState.keyguardFadingAway || notificationShadeWindowState.bouncerShowing || notificationShadeWindowState.headsUpNotificationShowing || notificationShadeWindowState.scrimsVisibility != 0)) || (notificationShadeWindowState.backgroundBlurRadius > 0) || notificationShadeWindowState.launchingActivityFromNotification;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = this.mHelper;
        secNotificationShadeWindowControllerHelperImpl2.getClass();
        boolean z3 = notificationShadeWindowState.forceInvisible;
        boolean z4 = notificationShadeWindowState.forceVisibleForUnlockAnimation;
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = secNotificationShadeWindowControllerHelperImpl2.fastUnlockController;
        keyguardFastBioUnlockController.getClass();
        if (z4) {
            z2 = true;
        } else if (z2) {
            if (keyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED) && (visibilityController = keyguardFastBioUnlockController.curVisibilityController) != null && visibilityController.needToBeInvisibleWindow() && z3) {
                z2 = false;
            }
        } else if (z3) {
            if (keyguardFastBioUnlockController.needsBlankScreen) {
                VisibilityController visibilityController2 = keyguardFastBioUnlockController.curVisibilityController;
                if (visibilityController2 != null) {
                    visibilityController2.resetForceInvisible(false);
                }
            } else {
                keyguardFastBioUnlockController.reset();
            }
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && !keyguardFastBioUnlockController.isFastUnlockMode() && !keyguardFastBioUnlockController.isFastWakeAndUnlockMode()) {
            if (z2 && notificationShadeWindowState.forceInvisible) {
                z2 = false;
            } else if (!z2) {
                secNotificationShadeWindowControllerHelperImpl2.resetForceInvisible(false);
            }
        }
        if (LsRune.COVER_SUPPORTED && notificationShadeWindowState.isCoverClosed && (!notificationShadeWindowState.coverAppShowing || z2 || DeviceState.isClearSideViewCoverType(notificationShadeWindowState.coverType))) {
            z2 = notificationShadeWindowState.dozing || !DeviceState.isCoverUIType(notificationShadeWindowState.coverType);
        }
        if (z || secNotificationShadeWindowControllerHelperImpl2.isLastExpanded != z2) {
            int i = LogUtil.getInt(!notificationShadeWindowState.forceWindowCollapsed);
            int i2 = LogUtil.getInt(notificationShadeWindowState.isKeyguardShowingAndNotOccluded());
            int i3 = LogUtil.getInt(notificationShadeWindowState.panelVisible);
            int i4 = LogUtil.getInt(notificationShadeWindowState.keyguardFadingAway);
            int i5 = LogUtil.getInt(notificationShadeWindowState.bouncerShowing);
            int i6 = LogUtil.getInt(notificationShadeWindowState.headsUpNotificationShowing);
            int i7 = notificationShadeWindowState.scrimsVisibility;
            int i8 = notificationShadeWindowState.backgroundBlurRadius;
            int i9 = LogUtil.getInt(notificationShadeWindowState.launchingActivityFromNotification);
            int i10 = LogUtil.getInt(notificationShadeWindowState.dozing);
            int i11 = LogUtil.getInt(notificationShadeWindowState.forceInvisible);
            int i12 = LogUtil.getInt(notificationShadeWindowState.forceVisibleForUnlockAnimation);
            int i13 = LogUtil.getInt(notificationShadeWindowState.isCoverClosed);
            int i14 = LogUtil.getInt(notificationShadeWindowState.coverAppShowing);
            int i15 = notificationShadeWindowState.coverType;
            StringBuilder sbM = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("isExpanded=", i, "\n!forceCollapsed=", z2, ", keyguard=");
            ViewPager$$ExternalSyntheticOutline0.m(sbM, i2, ", panel=", i3, ", fadingAway=");
            ViewPager$$ExternalSyntheticOutline0.m(sbM, i4, ", bouncer=", i5, ", headsUp=");
            ViewPager$$ExternalSyntheticOutline0.m(sbM, i6, ", scrim=", i7, ", blur=");
            ViewPager$$ExternalSyntheticOutline0.m(sbM, i8, ", launchingActivity=", i9, ", dozing=");
            ViewPager$$ExternalSyntheticOutline0.m(sbM, i10, ", forceInvisible=", i11, ", forceVisibleForUnlockAnimation=");
            ViewPager$$ExternalSyntheticOutline0.m(sbM, i12, ", coverClosed=", i13, ", coverApp=");
            sbM.append(i14);
            sbM.append(", coverType=");
            sbM.append(i15);
            Log.d(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, sbM.toString());
            secNotificationShadeWindowControllerHelperImpl = secNotificationShadeWindowControllerHelperImpl2;
        } else {
            secNotificationShadeWindowControllerHelperImpl = secNotificationShadeWindowControllerHelperImpl2;
        }
        if (secNotificationShadeWindowControllerHelperImpl.isLastExpanded != z2) {
            Iterator it = CollectionsKt___CollectionsKt.toList(secNotificationShadeWindowControllerHelperImpl.visibilityMonitor.isExpandedChangedListeners).iterator();
            while (it.hasNext()) {
                ((Function1) it.next()).mo781invoke(Boolean.valueOf(z2));
            }
        }
        secNotificationShadeWindowControllerHelperImpl.isLastExpanded = z2;
        return z2;
    }
}
