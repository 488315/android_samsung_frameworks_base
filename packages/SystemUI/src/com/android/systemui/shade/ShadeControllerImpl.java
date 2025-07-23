package com.android.systemui.shade;

import android.content.IntentFilter;
import android.os.FactoryTest;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManagerGlobal;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.blur.domain.interactor.SecCapturedBlurCollapseShaderInteractor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticOutline0;
import com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelTouchProximityInteractor;
import com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import dagger.Lazy;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeControllerImpl extends BaseShadeControllerImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy mAssistManagerLazy;
    public final SecCapturedBlurCollapseShaderInteractor mCapturedBlurCollapseShaderInteractor;
    public final CommandQueue mCommandQueue;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final int mDisplayId;
    public boolean mExpandedVisible;
    public final Lazy mGutsManager;
    public final KeyguardStateController mKeyguardStateController;
    public final Lazy mKeyguardViewMediator;
    public boolean mLockscreenOrShadeVisible;
    public final Executor mMainExecutor;
    public final Lazy mNotifShadeWindowViewController;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final Lazy mNpvc;
    public final StringBuilder mQuickPanelLogBuilder;
    public final QuickPanelLogger mQuickPanelLogger;
    public final SecShadeControllerImpl mSecShadeControllerImpl;
    public CentralSurfacesImpl.AnonymousClass4 mShadeVisibilityListener;
    public final StatusBarStateController mStatusBarStateController;
    public final StatusBarWindowControllerStore mStatusBarWindowControllerStore;
    public final WindowRootViewVisibilityInteractor mWindowRootViewVisibilityInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.shade.ShadeControllerImpl$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    public ShadeControllerImpl(Lazy lazy, CommandQueue commandQueue, Executor executor, WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, KeyguardViewController keyguardViewController, StatusBarWindowControllerStore statusBarWindowControllerStore, DeviceProvisionedController deviceProvisionedController, NotificationShadeWindowController notificationShadeWindowController, int i, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Optional<SecCapturedBlurCollapseShaderInteractor> optional) {
        super(commandQueue, keyguardViewController, notificationShadeWindowController, lazy4);
        int i2 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        this.mKeyguardViewMediator = lazy;
        this.mCommandQueue = commandQueue;
        this.mMainExecutor = executor;
        this.mWindowRootViewVisibilityInteractor = windowRootViewVisibilityInteractor;
        this.mNpvc = lazy3;
        this.mStatusBarStateController = statusBarStateController;
        this.mStatusBarWindowControllerStore = statusBarWindowControllerStore;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mGutsManager = lazy5;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mNotifShadeWindowViewController = lazy2;
        this.mDisplayId = i;
        this.mKeyguardStateController = keyguardStateController;
        this.mAssistManagerLazy = lazy4;
        this.mQuickPanelLogger = new QuickPanelLogger("SC");
        this.mQuickPanelLogBuilder = new StringBuilder();
        this.mSecShadeControllerImpl = new SecShadeControllerImpl();
        this.mCapturedBlurCollapseShaderInteractor = (QpRune.QUICK_PANEL_BLUR_MASSIVE && optional.isPresent()) ? optional.get() : null;
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void animateCollapseShade(float f, int i, boolean z, boolean z2) {
        int state = this.mStatusBarStateController.getState();
        if (!z && state != 0 && state != 2) {
            runPostCollapseActions();
            return;
        }
        if (getNotificationShadeWindowView$1() != null && getNpvc().canBeCollapsed() && (i & 4) == 0) {
            ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).setNotificationShadeFocusable(false);
            getNpvc().setMotionAborted();
            SecNotificationPanelViewController secNotificationPanelViewController = getNpvc().mSecNotificationPanelViewController;
            if (secNotificationPanelViewController != null && secNotificationPanelViewController.isTrackingSupplier.getAsBoolean()) {
                secNotificationPanelViewController.onTrackingStoppedConsumer.accept(Boolean.TRUE);
            }
            getNpvc().collapse(f, true, z2);
        }
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void cancelExpansionAndCollapseShade() {
        if (getNpvc().isTracking()) {
            ((NotificationShadeWindowViewController) this.mNotifShadeWindowViewController.get()).cancelCurrentTouch();
        }
        if (getNpvc().isPanelExpanded() && this.mStatusBarStateController.getState() == 0) {
            animateCollapseShade(0);
        }
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void closeShadeIfOpen() {
        if (getNpvc().isFullyCollapsed()) {
            return;
        }
        this.commandQueue.animateCollapsePanels(2, true);
        notifyVisibilityChanged(false);
        ((AssistManager) this.mAssistManagerLazy.get()).hideAssist();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void collapseOnMainThread() {
        if (Looper.getMainLooper().isCurrentThread()) {
            collapseShadeInternal();
        } else {
            this.mMainExecutor.execute(new ShadeControllerImpl$$ExternalSyntheticLambda0(this, 2));
        }
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void collapseShade() {
        collapseShadeInternal();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void collapseShadeForActivityStart() {
        if (!this.mExpandedVisible || this.keyguardViewController.isBouncerShowing()) {
            this.mMainExecutor.execute(new ShadeControllerImpl$$ExternalSyntheticLambda0(this, 3));
        } else if (!((KeyguardViewMediator) this.mKeyguardViewMediator.get()).mHelper.needsCollapsePanelWithNoAnimation()) {
            animateCollapseShadeForcedDelayed();
        } else {
            Log.d("CentralSurfaces", "collapseShade with no animation");
            collapseShade(false);
        }
    }

    public final boolean collapseShadeInternal() {
        if (getNpvc().isFullyCollapsed()) {
            return false;
        }
        if (SecPanelSplitHelper.isEnabled() && ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            animateCollapseShade(1.0f, 0, true, false);
        } else {
            animateCollapseShadeForcedDelayed();
        }
        notifyVisibilityChanged(false);
        return true;
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void collapseWithDuration(int i) {
        NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this.mNpvc.get();
        notificationPanelViewController.mFixedDuration = i;
        notificationPanelViewController.collapse(1.0f, false);
        notificationPanelViewController.mFixedDuration = -1;
    }

    @Override // com.android.systemui.shade.BaseShadeControllerImpl
    public final void expandToNotifications() {
        SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
        secPanelSAStatusLogInteractor.getClass();
        SecPanelSplitHelper.Companion.getClass();
        if (SecPanelSplitHelper.isEnabled) {
            StateFlowImpl stateFlowImpl = secPanelSAStatusLogInteractor.repository._openNotificationPanelFromEtc;
            LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
        }
        getNpvc().expandToNotifications();
    }

    @Override // com.android.systemui.shade.BaseShadeControllerImpl
    public final void expandToQs() {
        getNpvc().expandToQs();
    }

    public final NotificationShadeWindowView getNotificationShadeWindowView$1() {
        return ((NotificationShadeWindowViewController) this.mNotifShadeWindowViewController.get()).mView;
    }

    public final NotificationPanelViewController getNpvc() {
        return (NotificationPanelViewController) this.mNpvc.get();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void instantCollapseShade() {
        getNpvc().instantCollapse();
        runPostCollapseActions();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void instantExpandShade() {
        makeExpandedVisible(true);
        getNpvc().expand(false);
        this.commandQueue.recomputeDisableFlags(this.mDisplayId, false);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final boolean isExpandedVisible() {
        return this.mExpandedVisible;
    }

    @Override // com.android.systemui.shade.ShadeController
    public final boolean isExpandingOrCollapsing() {
        return getNpvc().isExpandingOrCollapsing();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final boolean isShadeEnabled() {
        return this.mCommandQueue.panelsEnabled() && ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).isCurrentUserSetup();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final boolean isShadeFullyOpen() {
        return getNpvc().isShadeFullyExpanded();
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void makeExpandedInvisible() {
        StringBuilder sb;
        boolean z = this.mExpandedVisible;
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (z && getNotificationShadeWindowView$1() != null) {
            if (quickPanelLogger != null) {
                quickPanelLogger.logPanelState("makeExpandedInvisible");
            }
            getNpvc().collapse(1.0f, false, false);
            this.mExpandedVisible = false;
            notifyVisibilityChanged(false);
            ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).setPanelVisible(false);
            ((StatusBarWindowControllerImpl) ((StatusBarWindowController) this.mStatusBarWindowControllerStore.getDefaultDisplay())).setForceStatusBarVisible(false);
            ((NotificationGutsManager) this.mGutsManager.get()).closeAndSaveGuts(true, true, true, true);
            runPostCollapseActions();
            this.mShadeVisibilityListener.expandedVisibleChanged(false);
            this.commandQueue.recomputeDisableFlags(this.mDisplayId, true);
            if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
                return;
            }
            WindowManagerGlobal.getInstance().trimMemory(20);
            return;
        }
        if (!this.mExpandedVisible) {
            KeyguardTouchAnimator keyguardTouchAnimator = getNpvc().mKeyguardTouchAnimator;
            if (keyguardTouchAnimator.isUnlockExecuted && keyguardTouchAnimator.notiScale != 1.0f) {
                Log.i("KeyguardTouchAnimator", "NSSL wasn't restored to original scale. Need to reset in force.");
                if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(2)) {
                    View view$frameworks__base__packages__SystemUI__android_common__SystemUI_core = keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(2);
                    view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setScaleX(1.0f);
                    view$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setScaleY(1.0f);
                }
                keyguardTouchAnimator.reset(false);
            }
        }
        if (quickPanelLogger == null || (sb = this.mQuickPanelLogBuilder) == null) {
            return;
        }
        sb.setLength(0);
        sb.append("makeExpandedInvisible canceled: ");
        sb.append("!mExpandedVisible: ");
        sb.append(!this.mExpandedVisible);
        sb.append(" || getNotificationShadeWindowView == null: ");
        sb.append(getNotificationShadeWindowView$1() == null);
        quickPanelLogger.logPanelState(sb.toString());
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void makeExpandedVisible(boolean z) {
        StringBuilder sb;
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        CommandQueue commandQueue = this.commandQueue;
        if (z || (!this.mExpandedVisible && commandQueue.panelsEnabled())) {
            if (quickPanelLogger != null) {
                quickPanelLogger.logPanelState("makeExpandedVisible: force: " + z);
            }
            this.mExpandedVisible = true;
            ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).setPanelVisible(true);
            notifyVisibilityChanged(true);
            commandQueue.recomputeDisableFlags(this.mDisplayId, !z);
            this.mShadeVisibilityListener.expandedVisibleChanged(true);
            return;
        }
        if (quickPanelLogger == null || (sb = this.mQuickPanelLogBuilder) == null) {
            return;
        }
        sb.setLength(0);
        sb.append("makeExpandedVisible canceled: ");
        sb.append("mExpandedVisible: ");
        sb.append(this.mExpandedVisible);
        sb.append(" || !getCommandQueue().panelsEnabled(): ");
        sb.append(!commandQueue.panelsEnabled());
        quickPanelLogger.logPanelState(sb.toString());
    }

    public final void notifyVisibilityChanged(boolean z) {
        this.mWindowRootViewVisibilityInteractor.windowRootViewVisibilityRepository._isLockscreenOrShadeVisible.updateState(null, Boolean.valueOf(z));
        if (this.mLockscreenOrShadeVisible != z) {
            this.mLockscreenOrShadeVisible = z;
            if (z) {
                DejankUtils.notifyRendererOfExpensiveFrame(getNotificationShadeWindowView$1(), "onShadeVisibilityChanged");
            }
        }
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void onStatusBarTouch(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (this.mExpandedVisible) {
                ((NotificationGutsManager) Dependency.sDependency.getDependencyInner(NotificationGutsManager.class)).getClass();
            }
            ((NotificationGutsManager) Dependency.sDependency.getDependencyInner(NotificationGutsManager.class)).mIsGoingGutOpenedFromLock = false;
        }
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void performHapticFeedback(int i) {
        NotificationPanelViewController npvc = getNpvc();
        npvc.mVibratorHelper.getClass();
        npvc.mView.performHapticFeedback(12);
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void postAnimateCollapseShade() {
        this.mMainExecutor.execute(new ShadeControllerImpl$$ExternalSyntheticLambda0(this, 1));
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void postAnimateForceCollapseShade() {
        this.mMainExecutor.execute(new ShadeControllerImpl$$ExternalSyntheticLambda0(this, 0));
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void postOnShadeExpanded(final StatusBarRemoteInputCallback$$ExternalSyntheticLambda1 statusBarRemoteInputCallback$$ExternalSyntheticLambda1) {
        NotificationPanelViewController npvc = getNpvc();
        npvc.mView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.shade.ShadeControllerImpl.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                ShadeControllerImpl shadeControllerImpl = ShadeControllerImpl.this;
                int i = ShadeControllerImpl.$r8$clinit;
                if (shadeControllerImpl.getNotificationShadeWindowView$1().isVisibleToUser()) {
                    ShadeControllerImpl.this.getNpvc().mView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    NotificationPanelViewController npvc2 = ShadeControllerImpl.this.getNpvc();
                    npvc2.mView.post(statusBarRemoteInputCallback$$ExternalSyntheticLambda1);
                }
            }
        });
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void setVisibilityListener(CentralSurfacesImpl.AnonymousClass4 anonymousClass4) {
        this.mShadeVisibilityListener = anonymousClass4;
    }

    @Override // com.android.systemui.shade.BaseShadeControllerImpl, com.android.systemui.CoreStartable
    public final void start() {
        SecCapturedBlurCollapseShaderInteractor secCapturedBlurCollapseShaderInteractor;
        getNpvc().mTrackingStartedListener = new ShadeControllerImpl$$ExternalSyntheticLambda4(this);
        NotificationPanelViewController npvc = getNpvc();
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        npvc.getClass();
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        npvc.mOpenCloseListener = anonymousClass2;
        SecShadeControllerImpl secShadeControllerImpl = this.mSecShadeControllerImpl;
        if (secShadeControllerImpl != null) {
            ShadeControllerImpl$$ExternalSyntheticLambda0 shadeControllerImpl$$ExternalSyntheticLambda0 = new ShadeControllerImpl$$ExternalSyntheticLambda0(this, 4);
            SecPanelTouchProximityInteractor secPanelTouchProximityInteractor = (SecPanelTouchProximityInteractor) secShadeControllerImpl.panelTouchProximityInteractor$delegate.getValue();
            if (DeviceType.isTouchProximitySupported(secPanelTouchProximityInteractor.context)) {
                secPanelTouchProximityInteractor.postAnimateForceCollapseShadeRunnable = shadeControllerImpl$$ExternalSyntheticLambda0;
                BroadcastDispatcher.registerReceiver$default(secPanelTouchProximityInteractor.broadcastDispatcher, secPanelTouchProximityInteractor.broadcastReceiver, new IntentFilter("android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY"), null, UserHandle.ALL, 0, null, 48);
                Unit unit = Unit.INSTANCE;
                Log.d("SecPanelTouchProximityInteractor", "start(): broadcastReceiver registered");
            } else {
                Log.d("SecPanelTouchProximityInteractor", "start(): touch proximity not supported");
            }
            SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor = (SecQuickSettingsAffordanceInteractor) secShadeControllerImpl.secQuickSettingsAffordanceInteractor$delegate.getValue();
            if (!secQuickSettingsAffordanceInteractor.isPanelAffordanceAvailableCount()) {
                Log.d("SecQuickSettingsAffordanceInteractor", "start: returned by isPanelAffordanceAvailableCount");
            } else if (FactoryTest.isFactoryBinary()) {
                Log.d("SecQuickSettingsAffordanceInteractor", "start: returned by isFactoryBinary");
            } else {
                SecQuickSettingsAffordance secQuickSettingsAffordance = new SecQuickSettingsAffordance(secQuickSettingsAffordanceInteractor.context, secQuickSettingsAffordanceInteractor.displayLifecycle);
                secQuickSettingsAffordanceInteractor.secQuickSettingsAffordance = secQuickSettingsAffordance;
                ((ConfigurationControllerImpl) secQuickSettingsAffordanceInteractor.configurationController).addCallback(secQuickSettingsAffordance);
                secQuickSettingsAffordanceInteractor.statusBarStateController.addCallback(secQuickSettingsAffordanceInteractor.statusBarStateListener);
                secQuickSettingsAffordanceInteractor.lockscreenShadeTransitionController.addCallback(secQuickSettingsAffordanceInteractor.transitionCallback);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.SCREEN_ON");
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                Unit unit2 = Unit.INSTANCE;
                BroadcastDispatcher.registerReceiver$default(secQuickSettingsAffordanceInteractor.broadcastDispatcher, secQuickSettingsAffordanceInteractor.broadcastReceiver, intentFilter, null, UserHandle.ALL, 0, null, 48);
                ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).registerCallback(secQuickSettingsAffordanceInteractor.updateMonitorCallback);
                Log.d("SecQuickSettingsAffordanceInteractor", "start : broadcastReceiver registered");
            }
        }
        if (!QpRune.QUICK_PANEL_BLUR_MASSIVE || (secCapturedBlurCollapseShaderInteractor = this.mCapturedBlurCollapseShaderInteractor) == null) {
            return;
        }
        JavaAdapterKt.collectFlow(getNotificationShadeWindowView$1(), secCapturedBlurCollapseShaderInteractor.collapseQsWhileScreenWakingUp, new Consumer() { // from class: com.android.systemui.shade.ShadeControllerImpl$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ShadeControllerImpl shadeControllerImpl = ShadeControllerImpl.this;
                int intValue = ((Integer) obj).intValue();
                int i2 = ShadeControllerImpl.$r8$clinit;
                if (intValue == 1) {
                    shadeControllerImpl.getNpvc().animateCollapseQs(true);
                } else {
                    shadeControllerImpl.instantCollapseShade();
                }
            }
        });
        JavaAdapterKt.collectFlow(getNotificationShadeWindowView$1(), secCapturedBlurCollapseShaderInteractor.collapseQsWhileCapturedViewInvisible, new Consumer() { // from class: com.android.systemui.shade.ShadeControllerImpl$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ShadeControllerImpl shadeControllerImpl = ShadeControllerImpl.this;
                int intValue = ((Integer) obj).intValue();
                int i2 = ShadeControllerImpl.$r8$clinit;
                if (intValue == 1) {
                    shadeControllerImpl.getNpvc().animateCollapseQs(true);
                } else {
                    shadeControllerImpl.instantCollapseShade();
                }
            }
        });
    }

    @Override // com.android.systemui.shade.ShadeController
    public final void collapseShade(boolean z) {
        if (z) {
            if (collapseShadeInternal()) {
                return;
            }
            runPostCollapseActions();
            return;
        }
        NotificationPresenter notificationPresenter = this.notifPresenter;
        if (notificationPresenter == null) {
            notificationPresenter = null;
        }
        if (((StatusBarNotificationPresenter) notificationPresenter).mPanelExpansionInteractor.isFullyCollapsed()) {
            runPostCollapseActions();
        } else {
            instantCollapseShade();
            notifyVisibilityChanged(false);
        }
    }
}
