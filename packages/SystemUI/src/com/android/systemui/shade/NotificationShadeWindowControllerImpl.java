package com.android.systemui.shade;

import android.R;
import android.app.IActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.multiuser.Flags;
import android.os.Build;
import android.os.Trace;
import android.util.Log;
import android.view.Display;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowManager;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardStatusViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.Rune;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.common.buffer.RingBuffer;
import com.android.systemui.common.buffer.RingBuffer$iterator$1;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpsysTableLogger;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardSurfaceControllerImpl;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImplKt;
import com.android.systemui.keyguard.VisibilityController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.scene.ui.view.WindowRootViewComponent;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.domain.interactor.SecNotificationShadeWindowStateInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.LogUtil;
import dagger.Lazy;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationShadeWindowControllerImpl implements NotificationShadeWindowController, Dumpable, ConfigurationController.ConfigurationListener {
    public final IActivityManager mActivityManager;
    public final AuthController mAuthController;
    public final Executor mBackgroundExecutor;
    public final SysuiColorExtractor mColorExtractor;
    public final Lazy mCommunalInteractor;
    public final Context mContext;
    public int mDeferWindowLayoutParams;
    public final DozeParameters mDozeParameters;
    public StatusBarTouchableRegionManager$$ExternalSyntheticLambda0 mForcePluginOpenListener;
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
    public CentralSurfacesImpl$$ExternalSyntheticLambda0 mListener;
    public final ShadeWindowLogger mLogger;
    public WindowManager.LayoutParams mLp;
    public final WindowManager.LayoutParams mLpChanged;
    public float mScreenBrightnessDoze;
    public Consumer mScrimsVisibilityListener;
    public final SecNotificationShadeWindowStateInteractor mSecNotificationShadeWindowStateInteractor;
    public final Lazy mShadeInteractorLazy;
    public final AnonymousClass1 mStateListener;
    public final Lazy mUserInteractor;
    public final UserTracker.Callback mUserTrackerCallback;
    public final WindowManager mWindowManager;
    public WindowRootView mWindowRootView;
    public final WindowRootViewComponent.Factory mWindowRootViewComponentFactory;
    public final NotificationShadeWindowState mCurrentState = new NotificationShadeWindowState();
    public final ArrayList mCallbacks = new ArrayList();
    public final NotificationShadeWindowState.Buffer mStateBuffer = new NotificationShadeWindowState.Buffer(100);

    public NotificationShadeWindowControllerImpl(SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl, Context context, WindowRootViewComponent.Factory factory, WindowManager windowManager, IActivityManager iActivityManager, DozeParameters dozeParameters, StatusBarStateController statusBarStateController, ConfigurationController configurationController, KeyguardViewMediator keyguardViewMediator, KeyguardBypassController keyguardBypassController, Executor executor, Executor executor2, SysuiColorExtractor sysuiColorExtractor, DumpManager dumpManager, KeyguardStateController keyguardStateController, AuthController authController, IndicatorCutoutUtil indicatorCutoutUtil, Lazy lazy, ShadeWindowLogger shadeWindowLogger, Lazy lazy2, UserTracker userTracker, Lazy lazy3) {
        StatusBarStateController.StateListener stateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl.1
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
                            KeyguardViewMediatorHelperImpl.logD("setKeyguardAndWallpaperSurfaceInvisible hide aodWallpaper");
                            transaction.setVisibility(surfaceControl, false);
                        }
                        ((KeyguardSurfaceControllerImpl) keyguardViewMediatorHelperImpl.surfaceControllerLazy.get()).setKeyguardSurfaceVisible(transaction);
                        transaction.apply();
                        transaction.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        KeyguardViewMediatorHelperImpl.logD("setKeyguardAndWallpaperSurfaceInvisible trace=" + Unit.INSTANCE);
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
            public final void onStateChanged(int i) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.statusBarState = i;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            }
        };
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl.2
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onBeforeUserSwitching(int i) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                if (notificationShadeWindowState.isSwitchingUsers) {
                    return;
                }
                notificationShadeWindowState.isSwitchingUsers = true;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                if (notificationShadeWindowState.isSwitchingUsers) {
                    notificationShadeWindowState.isSwitchingUsers = false;
                    notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                }
            }
        };
        this.mUserTrackerCallback = callback;
        final int i = 0;
        Supplier supplier = new Supplier(this) { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda6
            public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i2 = i;
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = this.f$0;
                switch (i2) {
                    case 0:
                        return notificationShadeWindowControllerImpl.mCurrentState;
                    case 1:
                        return notificationShadeWindowControllerImpl.mLpChanged;
                    default:
                        return notificationShadeWindowControllerImpl.mLp;
                }
            }
        };
        final int i2 = 1;
        Supplier supplier2 = new Supplier(this) { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda6
            public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i22 = i2;
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = this.f$0;
                switch (i22) {
                    case 0:
                        return notificationShadeWindowControllerImpl.mCurrentState;
                    case 1:
                        return notificationShadeWindowControllerImpl.mLpChanged;
                    default:
                        return notificationShadeWindowControllerImpl.mLp;
                }
            }
        };
        final int i3 = 2;
        secNotificationShadeWindowControllerHelperImpl.provider = new SecNotificationShadeWindowControllerHelperImpl.Provider(supplier, supplier2, new Supplier(this) { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda6
            public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i22 = i3;
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = this.f$0;
                switch (i22) {
                    case 0:
                        return notificationShadeWindowControllerImpl.mCurrentState;
                    case 1:
                        return notificationShadeWindowControllerImpl.mLpChanged;
                    default:
                        return notificationShadeWindowControllerImpl.mLp;
                }
            }
        }, new Predicate() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                return notificationShadeWindowControllerImpl.isExpanded(notificationShadeWindowControllerImpl.mCurrentState, ((Boolean) obj).booleanValue());
            }
        }, new BooleanSupplier() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda10
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                NotificationShadeWindowControllerImpl.this.getClass();
                return Build.IS_DEBUGGABLE;
            }
        }, new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda11(this, 0));
        this.mHelper = secNotificationShadeWindowControllerHelperImpl;
        this.mContext = context;
        this.mWindowRootViewComponentFactory = factory;
        this.mWindowManager = windowManager;
        this.mActivityManager = iActivityManager;
        this.mDozeParameters = dozeParameters;
        this.mKeyguardStateController = keyguardStateController;
        this.mLogger = shadeWindowLogger;
        this.mScreenBrightnessDoze = dozeParameters.mResources.getInteger(R.integer.config_vibratorControlServiceDumpAggregationTimeMillisLimit) / 255.0f;
        this.mLpChanged = new WindowManager.LayoutParams();
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mBackgroundExecutor = executor2;
        this.mColorExtractor = sysuiColorExtractor;
        dumpManager.registerCriticalDumpable("{slow}NotificationShadeWindowControllerImpl", this);
        this.mAuthController = authController;
        this.mUserInteractor = lazy2;
        this.mCommunalInteractor = lazy3;
        this.mLastKeyguardRotationAllowed = DeviceState.shouldEnableKeyguardScreenRotation(((KeyguardStateControllerImpl) keyguardStateController).mContext);
        context.getResources().getInteger(com.android.systemui.R.integer.config_lockScreenDisplayTimeout);
        this.mShadeInteractorLazy = lazy;
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) ((SysuiStatusBarStateController) statusBarStateController);
        synchronized (statusBarStateControllerImpl.mListeners) {
            statusBarStateControllerImpl.addListenerInternalLocked(stateListener, 1);
        }
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        if (Flags.useAllCpusDuringUserSwitch()) {
            ((UserTrackerImpl) userTracker).addCallback(callback, executor);
        }
        float f = -1.0f;
        if (context.getResources().getInteger(com.android.systemui.R.integer.config_keyguardRefreshRate) > -1.0f) {
            Display.Mode[] systemSupportedModes = context.getDisplay().getSystemSupportedModes();
            int length = systemSupportedModes.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    break;
                }
                Display.Mode mode = systemSupportedModes[i4];
                if (Math.abs(mode.getRefreshRate() - r2) <= 0.1d) {
                    f = mode.getRefreshRate();
                    break;
                }
                i4++;
            }
        }
        this.mKeyguardPreferredRefreshRate = f;
        this.mKeyguardMaxRefreshRate = context.getResources().getInteger(com.android.systemui.R.integer.config_keyguardMaxRefreshRate);
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = this.mHelper;
        Objects.requireNonNull(secNotificationShadeWindowControllerHelperImpl2);
        Rune.runIf((Runnable) new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda3(secNotificationShadeWindowControllerHelperImpl2, 2), true);
        this.mIndicatorCutoutUtil = indicatorCutoutUtil;
        this.mSecNotificationShadeWindowStateInteractor = (SecNotificationShadeWindowStateInteractor) Dependency.sDependency.getDependencyInner(SecNotificationShadeWindowStateInteractor.class);
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x03aa, code lost:
    
        if (r6.isFullscreenBouncer() == false) goto L231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x03ae, code lost:
    
        if (r34.qsExpanded != false) goto L231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x03b0, code lost:
    
        r4 = (com.android.systemui.lockstar.PluginLockStarManager) r2.pluginLockStarManagerLazy.get();
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x03b8, code lost:
    
        if (r4 == null) goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x03be, code lost:
    
        if (r4.isLockStarEnabled() != true) goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x03c0, code lost:
    
        r4 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x03c5, code lost:
    
        if (r4 == false) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x03cb, code lost:
    
        if (r34.lockStarTimeOutValue <= 0) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x03cd, code lost:
    
        r3.userActivityTimeout = -1;
        r3.screenDimDuration = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x042a, code lost:
    
        if (r34.isKeyguardShowingAndNotOccluded() == false) goto L241;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x042e, code lost:
    
        if (r34.statusBarState != 1) goto L241;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0432, code lost:
    
        if (r34.qsExpanded != false) goto L241;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0436, code lost:
    
        if (r34.forceUserActivity != false) goto L241;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0438, code lost:
    
        r33.mLpChanged.inputFeatures |= 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0448, code lost:
    
        r2 = !r34.isKeyguardShowingAndNotOccluded();
        r3 = r33.mWindowRootView;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x044f, code lost:
    
        if (r3 == null) goto L247;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0455, code lost:
    
        if (r3.getFitsSystemWindows() == r2) goto L247;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0457, code lost:
    
        r33.mWindowRootView.setFitsSystemWindows(r2);
        r33.mWindowRootView.requestApplyInsets();
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0463, code lost:
    
        if (r34.headsUpNotificationShowing == false) goto L250;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0465, code lost:
    
        r33.mLpChanged.flags |= 32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0478, code lost:
    
        if (r34.forceDozeBrightness == false) goto L254;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x047a, code lost:
    
        r33.mLpChanged.screenBrightness = r33.mScreenBrightnessDoze;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x048d, code lost:
    
        if (r34.componentsForcingTopUi.isEmpty() == false) goto L261;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0493, code lost:
    
        if (isExpanded(r34) != false) goto L261;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0497, code lost:
    
        if (r34.isSwitchingUsers == false) goto L262;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x049a, code lost:
    
        r33.mHasTopUiChanged = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x049e, code lost:
    
        if (r34.windowNotTouchable == false) goto L265;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x04a0, code lost:
    
        r33.mLpChanged.flags |= 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x04b5, code lost:
    
        if (isExpanded(r34) != false) goto L269;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x04b7, code lost:
    
        r33.mLpChanged.privateFlags |= 16777216;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x04cb, code lost:
    
        com.android.systemui.Rune.runIf((java.lang.Runnable) new com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda1(r33, r34, 0), true);
        com.android.systemui.Rune.runIf((java.lang.Runnable) new com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda1(r33, r34, 1), true);
        applyWindowLayoutParams();
        r2 = r33.mHasTopUi;
        r3 = r33.mHasTopUiChanged;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x04e4, code lost:
    
        if (r2 == r3) goto L273;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x04e6, code lost:
    
        r33.mHasTopUi = r3;
        r33.mBackgroundExecutor.execute(new com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda3(r33, 0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x04f3, code lost:
    
        notifyStateChangedCallbacks();
        com.android.systemui.Rune.runIf((java.lang.Runnable) new com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda1(r33, r34, 2), true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x04ff, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x04c1, code lost:
    
        r33.mLpChanged.privateFlags &= -16777217;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x04a9, code lost:
    
        r33.mLpChanged.flags &= -17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x0499, code lost:
    
        r8 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0481, code lost:
    
        r33.mLpChanged.screenBrightness = -1.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x046e, code lost:
    
        r33.mLpChanged.flags &= -33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0440, code lost:
    
        r33.mLpChanged.inputFeatures &= -3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x03d2, code lost:
    
        r4 = r2.pluginLockMediator;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x03d4, code lost:
    
        if (r4 == null) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x03d6, code lost:
    
        r4 = r4.isDynamicLockEnabled();
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x03dc, code lost:
    
        if (r4 == false) goto L213;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x03e0, code lost:
    
        if (r34.userScreenTimeOut == false) goto L213;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x03e2, code lost:
    
        r3.userActivityTimeout = -1;
        r3.screenDimDuration = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x03e7, code lost:
    
        if (r5 != false) goto L219;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x03eb, code lost:
    
        if (r34.bouncerShowing == false) goto L219;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x03ed, code lost:
    
        r4 = r34.keyguardUserActivityTimeout;
        r9 = 10000;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x03f3, code lost:
    
        if (r4 >= 10000) goto L230;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x03f5, code lost:
    
        r4 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x041d, code lost:
    
        r3.userActivityTimeout = r4;
        r3.screenDimDuration = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0401, code lost:
    
        if (android.view.accessibility.AccessibilityManager.getInstance(r2.context).isTouchExplorationEnabled() == false) goto L224;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0403, code lost:
    
        r4 = r34.keyguardUserActivityTimeout;
        r9 = com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl.AWAKE_INTERVAL_DEFAULT_MS_WITH_ACCESSIBILITY;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x0409, code lost:
    
        if (r4 >= r9) goto L224;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x0410, code lost:
    
        if (r6.isFaceOptionEnabled() == false) goto L229;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x0412, code lost:
    
        r4 = r34.keyguardUserActivityTimeout;
        r9 = com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl.AWAKE_INTERVAL_DEFAULT_MS_WITH_FACE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0418, code lost:
    
        if (r4 >= r9) goto L229;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x041b, code lost:
    
        r4 = r34.keyguardUserActivityTimeout;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x03db, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x03c2, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0422, code lost:
    
        r3.userActivityTimeout = -1;
        r3.screenDimDuration = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0366, code lost:
    
        if (r34.communalVisible == false) goto L171;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x0368, code lost:
    
        r4.getClass();
        r4.buffer.log(r4.tag, r10, "Visibility forced to be true by communal", null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x02d6, code lost:
    
        r12 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x02c6, code lost:
    
        r10 = r34.dozing;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x02da, code lost:
    
        if (r2.isKeyguardScreenRotation == false) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x02de, code lost:
    
        if (r34.screenOrientationNoSensor != false) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x02e0, code lost:
    
        r10 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x02e3, code lost:
    
        if (r10 == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x02e9, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x02eb, code lost:
    
        r10 = r2.rotation;
        r13 = r2.displayLifecycle;
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x02f3, code lost:
    
        if (r13.getDisplay(0) != null) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x02f5, code lost:
    
        r13.addDisplay(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x02fe, code lost:
    
        if (r10 == r13.mDisplayRotationHash.get(0)) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x0304, code lost:
    
        if (r13.getDisplay(0) != null) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x0306, code lost:
    
        r13.addDisplay(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x0309, code lost:
    
        r10 = r13.mDisplayRotationHash.get(0);
        r2.rotation = r10;
        androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m(r10, "adjustScreenOrientation: rotation=", "NotificationShadeWindowController");
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x031a, code lost:
    
        if (r2.rotation != 2) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x031d, code lost:
    
        r12 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x02e2, code lost:
    
        r10 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x022d, code lost:
    
        if (com.android.systemui.statusbar.NotificationRemoteInputManager.ENABLE_REMOTE_INPUT == false) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x0231, code lost:
    
        if (r34.remoteInputActive != false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x0235, code lost:
    
        if (r34.glanceableHubShowing == false) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x0249, code lost:
    
        if (r34.isKeyguardShowingAndNotOccluded() != false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x024b, code lost:
    
        if (r5 == false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x024e, code lost:
    
        r5 = r33.mLpChanged;
        r5.flags = (r5.flags | 8) & (-131073);
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x0257, code lost:
    
        r5 = r33.mLpChanged;
        r6 = r5.flags & (-9);
        r5.flags = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x0263, code lost:
    
        if (com.android.systemui.LsRune.SECURITY_BOUNCER_WINDOW == false) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x0265, code lost:
    
        r5.flags = r6 | 131072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x026b, code lost:
    
        if (r34.keyguardNeedsInput == false) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x0271, code lost:
    
        if (r34.isKeyguardShowingAndNotOccluded() == false) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:227:0x0273, code lost:
    
        r33.mLpChanged.flags &= -131073;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x027b, code lost:
    
        r33.mLpChanged.flags |= 131072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x021c, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x0205, code lost:
    
        r5.flags |= 8192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x01f9, code lost:
    
        if (r34.securedWindow != false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x01f5, code lost:
    
        if (r6.isDebuggableSupplier.getAsBoolean() != false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x01fd, code lost:
    
        if (com.android.systemui.LsRune.KEYGUARD_EM_TOKEN_CAPTURE_WINDOW == false) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0203, code lost:
    
        if (r2.engineerModeManager.isCaptureEnabled != false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x020c, code lost:
    
        r5.flags &= -8193;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0214, code lost:
    
        if (r34.notificationShadeFocusable == false) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0218, code lost:
    
        if (r34.shadeOrQsExpanded == false) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x021a, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x021d, code lost:
    
        r9 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0221, code lost:
    
        if (r34.bouncerShowing == false) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0225, code lost:
    
        if (r34.keyguardOccluded != false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0229, code lost:
    
        if (r34.keyguardNeedsInput != false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0237, code lost:
    
        r33.mLpChanged.flags &= -131081;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0282, code lost:
    
        r5 = com.android.systemui.LsRune.SECURITY_BOUNCER_WINDOW;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0284, code lost:
    
        if (r5 == false) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0289, code lost:
    
        if (r34.bouncerShowing != false) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x028d, code lost:
    
        if (com.android.systemui.statusbar.NotificationRemoteInputManager.ENABLE_REMOTE_INPUT == false) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0291, code lost:
    
        if (r34.remoteInputActive == false) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0294, code lost:
    
        r33.mLpChanged.forciblyShownTypes &= ~android.view.WindowInsets.Type.navigationBars();
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x02a1, code lost:
    
        r33.mLpChanged.forciblyShownTypes |= android.view.WindowInsets.Type.navigationBars();
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x02ac, code lost:
    
        r6 = r2.getLayoutParamsChanged();
        r12 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x02b4, code lost:
    
        if (r34.bouncerShowing != false) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x02ba, code lost:
    
        if (r34.isKeyguardShowingAndNotOccluded() != false) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x02be, code lost:
    
        if (com.android.systemui.LsRune.COVER_SUPPORTED == false) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x02c2, code lost:
    
        if (r34.isCoverClosed == false) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x02c4, code lost:
    
        r10 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x02c8, code lost:
    
        if (r10 == false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x02cb, code lost:
    
        if (r5 != false) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x02cf, code lost:
    
        if (r2.isKeyguardScreenRotation != false) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x02d3, code lost:
    
        if (r34.bouncerShowing == false) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x031e, code lost:
    
        r6.screenOrientation = r12;
        r6 = isExpanded(r34);
        r4.getClass();
        r10 = com.android.systemui.log.core.LogLevel.DEBUG;
        r12 = com.android.systemui.shade.ShadeWindowLogger$logApplyVisibility$2.INSTANCE;
        r14 = r4.buffer;
        r12 = r14.obtain("systemui.shadewindow", r10, r12, null);
        ((com.android.systemui.log.LogMessageImpl) r12).bool1 = r6;
        r14.commit(r12);
        r12 = r34.forcePluginOpen;
        r4 = r4.$$delegate_0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0340, code lost:
    
        if (r12 == false) goto L168;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0342, code lost:
    
        r12 = r33.mListener;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0344, code lost:
    
        if (r12 == null) goto L166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0346, code lost:
    
        com.android.systemui.statusbar.phone.CentralSurfacesImpl.AnonymousClass3.this.mOverlays.forEach(new com.android.systemui.statusbar.phone.CentralSurfacesImpl$3$Callback$$ExternalSyntheticLambda2(r6));
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0356, code lost:
    
        r4.getClass();
        r4.buffer.log(r4.tag, r10, "Visibility forced to be true", null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0362, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0375, code lost:
    
        r3 = r2.visibilityMonitor;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0379, code lost:
    
        if (r3.cancelExecToken == null) goto L177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x037d, code lost:
    
        if (r6 == r3.needsExpand) goto L176;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0380, code lost:
    
        r3.cancelExecToken(true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0383, code lost:
    
        r3 = r2.notificationShadeView;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0385, code lost:
    
        if (r3 == null) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0387, code lost:
    
        if (r6 == false) goto L181;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0389, code lost:
    
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0391, code lost:
    
        r3.setVisibility(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x038d, code lost:
    
        if (r34.forceInvisible == false) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0390, code lost:
    
        r9 = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0394, code lost:
    
        r3 = r2.getLayoutParamsChanged();
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x039e, code lost:
    
        if (r34.isKeyguardShowingAndNotOccluded() == false) goto L231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x03a0, code lost:
    
        r4 = r34.statusBarState;
        r6 = r2.keyguardUpdateMonitor;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x03a4, code lost:
    
        if (r4 == 1) goto L192;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void apply(com.android.systemui.shade.NotificationShadeWindowState r34) {
        /*
            Method dump skipped, instructions count: 1280
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationShadeWindowControllerImpl.apply(com.android.systemui.shade.NotificationShadeWindowState):void");
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
        StringBuilder m = KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardStatusViewController$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "NotificationShadeWindowController:", "  mKeyguardMaxRefreshRate="), this.mKeyguardMaxRefreshRate, printWriter, "  mKeyguardPreferredRefreshRate="), this.mKeyguardPreferredRefreshRate, printWriter, "  preferredMinDisplayRefreshRate="), this.mLpChanged.preferredMinDisplayRefreshRate, printWriter, "  preferredMaxDisplayRefreshRate="), this.mLpChanged.preferredMaxDisplayRefreshRate, printWriter, "  mDeferWindowLayoutParams=");
        m.append(this.mDeferWindowLayoutParams);
        printWriter.println(m.toString());
        Rune.runIf((Runnable) new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda1(this, printWriter, 3), true);
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
        RingBuffer$iterator$1 ringBuffer$iterator$1 = new RingBuffer$iterator$1(ringBuffer);
        while (ringBuffer$iterator$1.hasNext()) {
            arrayList.add((List) ((NotificationShadeWindowState) ringBuffer$iterator$1.next()).asStringList$delegate.getValue());
        }
        new DumpsysTableLogger("NotificationShadeWindowController", list, arrayList).printTableData(printWriter);
        Trace.endSection();
    }

    public final boolean isExpanded(NotificationShadeWindowState notificationShadeWindowState) {
        this.mHelper.getClass();
        return isExpanded(notificationShadeWindowState, Log.isLoggable(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, 3));
    }

    public final void notifyStateChangedCallbacks() {
        for (StatusBarWindowCallback statusBarWindowCallback : (List) this.mCallbacks.stream().map(new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda12()).filter(new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda13()).collect(Collectors.toList())) {
            NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
            statusBarWindowCallback.onStateChanged(notificationShadeWindowState.keyguardShowing, notificationShadeWindowState.keyguardOccluded, notificationShadeWindowState.keyguardGoingAway, notificationShadeWindowState.bouncerShowing, notificationShadeWindowState.dozing, notificationShadeWindowState.shadeOrQsExpanded, notificationShadeWindowState.dreaming);
        }
    }

    public void onCommunalVisibleChanged(Boolean bool) {
        boolean booleanValue = bool.booleanValue();
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        notificationShadeWindowState.communalVisible = booleanValue;
        apply(notificationShadeWindowState);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        boolean shouldEnableKeyguardScreenRotation = DeviceState.shouldEnableKeyguardScreenRotation(((KeyguardStateControllerImpl) this.mKeyguardStateController).mContext);
        if (this.mLastKeyguardRotationAllowed != shouldEnableKeyguardScreenRotation) {
            apply(this.mCurrentState);
            this.mLastKeyguardRotationAllowed = shouldEnableKeyguardScreenRotation;
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
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onThemeChanged() {
        if (this.mWindowRootView == null) {
            return;
        }
        boolean supportsDarkText = this.mColorExtractor.mNeutralColorsLock.supportsDarkText();
        int systemUiVisibility = this.mWindowRootView.getSystemUiVisibility();
        this.mWindowRootView.setSystemUiVisibility(supportsDarkText ? systemUiVisibility | 8208 : systemUiVisibility & (-8209));
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
            StatusBarTouchableRegionManager$$ExternalSyntheticLambda0 statusBarTouchableRegionManager$$ExternalSyntheticLambda0 = this.mForcePluginOpenListener;
            if (statusBarTouchableRegionManager$$ExternalSyntheticLambda0 != null) {
                boolean z3 = notificationShadeWindowState.forcePluginOpen;
                statusBarTouchableRegionManager$$ExternalSyntheticLambda0.f$0.updateTouchableRegion();
            }
        }
    }

    public final void setKeyguardFadingAway(boolean z) {
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = this.mHelper;
        Objects.requireNonNull(secNotificationShadeWindowControllerHelperImpl);
        boolean z2 = Rune.SYSUI_MULTI_SIM;
        boolean booleanValue = Boolean.valueOf(z).booleanValue();
        if (secNotificationShadeWindowControllerHelperImpl.getCurrentState().keyguardFadingAway != booleanValue) {
            com.android.systemui.keyguard.Log.d(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("keyguardFadingAway ", booleanValue));
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
        ShadeWindowLogger$logShadeFocusable$2 shadeWindowLogger$logShadeFocusable$2 = new Function1() { // from class: com.android.systemui.shade.ShadeWindowLogger$logShadeFocusable$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Updating shade, should be focusable : ", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = shadeWindowLogger.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shadewindow", logLevel, shadeWindowLogger$logShadeFocusable$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
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
        ShadeWindowLogger$logShadeVisibleAndFocusable$2 shadeWindowLogger$logShadeVisibleAndFocusable$2 = new Function1() { // from class: com.android.systemui.shade.ShadeWindowLogger$logShadeVisibleAndFocusable$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Updating shade, should be visible and focusable: ", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = shadeWindowLogger.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shadewindow", logLevel, shadeWindowLogger$logShadeVisibleAndFocusable$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
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
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl;
        boolean z2;
        VisibilityController visibilityController;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl;
        if ((notificationShadeWindowState.forceWindowCollapsed || !(notificationShadeWindowState.isKeyguardShowingAndNotOccluded() || notificationShadeWindowState.panelVisible || notificationShadeWindowState.keyguardFadingAway || notificationShadeWindowState.bouncerShowing || notificationShadeWindowState.headsUpNotificationShowing || notificationShadeWindowState.scrimsVisibility != 0)) && notificationShadeWindowState.backgroundBlurRadius <= 0 && !notificationShadeWindowState.launchingActivityFromNotification) {
            notificationShadeWindowControllerImpl = this;
            z2 = false;
        } else {
            notificationShadeWindowControllerImpl = this;
            z2 = true;
        }
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = notificationShadeWindowControllerImpl.mHelper;
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
            int i = LogUtil.getInt(true ^ notificationShadeWindowState.forceWindowCollapsed);
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
            StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("isExpanded=", i, "\n!forceCollapsed=", z2, ", keyguard=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i2, ", panel=", i3, ", fadingAway=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i4, ", bouncer=", i5, ", headsUp=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i6, ", scrim=", i7, ", blur=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i8, ", launchingActivity=", i9, ", dozing=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i10, ", forceInvisible=", i11, ", forceVisibleForUnlockAnimation=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i12, ", coverClosed=", i13, ", coverApp=");
            m.append(i14);
            m.append(", coverType=");
            m.append(i15);
            com.android.systemui.keyguard.Log.d(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, m.toString());
            secNotificationShadeWindowControllerHelperImpl = secNotificationShadeWindowControllerHelperImpl2;
        } else {
            secNotificationShadeWindowControllerHelperImpl = secNotificationShadeWindowControllerHelperImpl2;
        }
        if (secNotificationShadeWindowControllerHelperImpl.isLastExpanded != z2) {
            Iterator it = CollectionsKt___CollectionsKt.toList(secNotificationShadeWindowControllerHelperImpl.visibilityMonitor.isExpandedChangedListeners).iterator();
            while (it.hasNext()) {
                ((Function1) it.next()).invoke(Boolean.valueOf(z2));
            }
        }
        secNotificationShadeWindowControllerHelperImpl.isLastExpanded = z2;
        return z2;
    }
}
