package com.android.systemui.shade;

import android.R;
import android.app.IActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.view.Display;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.Rune;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
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
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.VisibilityController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.scene.ui.view.WindowRootViewComponent;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.domain.interactor.SecNotificationShadeWindowStateInteractor;
import com.android.systemui.shade.ui.viewmodel.NotificationShadeWindowModel;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda23;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.ShadeTouchableRegionManager$$ExternalSyntheticLambda1;
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
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public CentralSurfacesImpl$$ExternalSyntheticLambda23 mListener;
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
    public NotificationShadeWindowControllerImpl(Context context, SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl, WindowRootViewComponent.Factory factory, WindowManager windowManager, IActivityManager iActivityManager, DozeParameters dozeParameters, StatusBarStateController statusBarStateController, ConfigurationController configurationController, KeyguardViewMediator keyguardViewMediator, KeyguardBypassController keyguardBypassController, Executor executor, Executor executor2, SysuiColorExtractor sysuiColorExtractor, DumpManager dumpManager, KeyguardStateController keyguardStateController, AuthController authController, IndicatorCutoutUtil indicatorCutoutUtil, Lazy lazy, ShadeWindowLogger shadeWindowLogger, Lazy lazy2, UserTracker userTracker, NotificationShadeWindowModel notificationShadeWindowModel, Lazy lazy3, WindowManager.LayoutParams layoutParams) {
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
        secNotificationShadeWindowControllerHelperImpl.provider = new SecNotificationShadeWindowControllerHelperImpl.Provider(new Supplier(this) { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda2
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
        }, new Supplier(this) { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda2
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
        }, new Supplier(this) { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda2
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
        }, new Predicate() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                return notificationShadeWindowControllerImpl.isExpanded(notificationShadeWindowControllerImpl.mCurrentState, ((Boolean) obj).booleanValue());
            }
        }, new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda6(this, i2));
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
        float f = -1.0f;
        if (context.getResources().getInteger(com.android.systemui.R.integer.config_keyguardRefreshRate) > -1.0f) {
            Display.Mode[] systemSupportedModes = context.getDisplay().getSystemSupportedModes();
            int length = systemSupportedModes.length;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Display.Mode mode = systemSupportedModes[i2];
                if (Math.abs(mode.getRefreshRate() - r0) <= 0.1d) {
                    f = mode.getRefreshRate();
                    break;
                }
                i2++;
            }
        }
        this.mKeyguardPreferredRefreshRate = f;
        this.mKeyguardMaxRefreshRate = context.getResources().getInteger(com.android.systemui.R.integer.config_keyguardMaxRefreshRate);
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = this.mHelper;
        Objects.requireNonNull(secNotificationShadeWindowControllerHelperImpl2);
        boolean z = Rune.SYSUI_MULTI_SIM;
        secNotificationShadeWindowControllerHelperImpl2.initPost();
        this.mIndicatorCutoutUtil = indicatorCutoutUtil;
        this.mSecNotificationShadeWindowStateInteractor = (SecNotificationShadeWindowStateInteractor) Dependency.sDependency.getDependencyInner(SecNotificationShadeWindowStateInteractor.class);
    }

    /* JADX WARN: Code restructure failed: missing block: B:162:0x0405, code lost:
    
        if (r8 < 10000) goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0407, code lost:
    
        r8 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x041b, code lost:
    
        if (r8 < r10) goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x042a, code lost:
    
        if (r8 < r10) goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x0319, code lost:
    
        if (r2.rotation == 2) goto L160;
     */
    /* JADX WARN: Removed duplicated region for block: B:109:0x043e  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0463  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0477  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x048c  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x04a1  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x04b3  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x04ca  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0509  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x04d4  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x04bc  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0493  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0480  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x036e  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x034c  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0385  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0391  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x03aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void apply(com.android.systemui.shade.NotificationShadeWindowState r37) {
        /*
            Method dump skipped, instructions count: 1309
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
        RingBuffer$iterator$1 ringBuffer$iterator$1 = new RingBuffer$iterator$1(ringBuffer);
        while (ringBuffer$iterator$1.hasNext()) {
            arrayList.add((List) ((NotificationShadeWindowState) ringBuffer$iterator$1.next()).asStringList$delegate.getValue());
        }
        new DumpsysTableLogger("NotificationShadeWindowController", list, arrayList).printTableData(printWriter);
        Trace.endSection();
    }

    public final boolean isExpanded(NotificationShadeWindowState notificationShadeWindowState) {
        this.mHelper.getClass();
        return isExpanded(notificationShadeWindowState, android.util.Log.isLoggable(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, 3));
    }

    public final void notifyStateChangedCallbacks() {
        for (StatusBarWindowCallback statusBarWindowCallback : (List) this.mCallbacks.stream().map(new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda7()).filter(new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda8()).collect(Collectors.toList())) {
            NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
            statusBarWindowCallback.onStateChanged(notificationShadeWindowState.keyguardShowing, notificationShadeWindowState.keyguardOccluded, notificationShadeWindowState.keyguardGoingAway, notificationShadeWindowState.bouncerShowing, notificationShadeWindowState.dozing, notificationShadeWindowState.shadeOrQsExpanded, notificationShadeWindowState.dreaming, notificationShadeWindowState.communalVisible);
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
        int i = SceneContainerFlag.$r8$clinit;
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

    public void onShadeOrQsExpanded(final Boolean bool) {
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        if (notificationShadeWindowState.shadeOrQsExpanded != bool.booleanValue()) {
            notificationShadeWindowState.shadeOrQsExpanded = bool.booleanValue();
            apply(notificationShadeWindowState);
            final IBinder windowToken = this.mWindowRootView.getWindowToken();
            if (windowToken != null) {
                this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        try {
                            WindowManagerGlobal.getWindowManagerService().onNotificationShadeExpanded(windowToken, bool.booleanValue());
                        } catch (RemoteException e) {
                            android.util.Log.e("NotificationShadeWindowController", "Failed to call onNotificationShadeExpanded", e);
                        }
                    }
                });
            }
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
        LogMessage obtain = logBuffer.obtain("systemui.shadewindow", logLevel, shadeWindowLogger$$ExternalSyntheticLambda0, null);
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
        ShadeWindowLogger$$ExternalSyntheticLambda0 shadeWindowLogger$$ExternalSyntheticLambda0 = new ShadeWindowLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = shadeWindowLogger.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shadewindow", logLevel, shadeWindowLogger$$ExternalSyntheticLambda0, null);
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
            StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("isExpanded=", i, "\n!forceCollapsed=", z2, ", keyguard=");
            ViewPager$$ExternalSyntheticOutline0.m(m, i2, ", panel=", i3, ", fadingAway=");
            ViewPager$$ExternalSyntheticOutline0.m(m, i4, ", bouncer=", i5, ", headsUp=");
            ViewPager$$ExternalSyntheticOutline0.m(m, i6, ", scrim=", i7, ", blur=");
            ViewPager$$ExternalSyntheticOutline0.m(m, i8, ", launchingActivity=", i9, ", dozing=");
            ViewPager$$ExternalSyntheticOutline0.m(m, i10, ", forceInvisible=", i11, ", forceVisibleForUnlockAnimation=");
            ViewPager$$ExternalSyntheticOutline0.m(m, i12, ", coverClosed=", i13, ", coverApp=");
            m.append(i14);
            m.append(", coverType=");
            m.append(i15);
            Log.d(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, m.toString());
            secNotificationShadeWindowControllerHelperImpl = secNotificationShadeWindowControllerHelperImpl2;
        } else {
            secNotificationShadeWindowControllerHelperImpl = secNotificationShadeWindowControllerHelperImpl2;
        }
        if (secNotificationShadeWindowControllerHelperImpl.isLastExpanded != z2) {
            Iterator it = CollectionsKt___CollectionsKt.toList(secNotificationShadeWindowControllerHelperImpl.visibilityMonitor.isExpandedChangedListeners).iterator();
            while (it.hasNext()) {
                ((Function1) it.next()).mo779invoke(Boolean.valueOf(z2));
            }
        }
        secNotificationShadeWindowControllerHelperImpl.isLastExpanded = z2;
        return z2;
    }
}
