package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.MathUtils;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.animator.KeyguardTouchSecurityInjector;
import com.android.systemui.keyguard.domain.interactor.NaturalScrollingSettingObserver;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.mdm.MdmOverlayContainer;
import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.SecPanelTouchBlockHelper;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.statusbar.LockscreenShadeKeyguardTransitionController;
import com.android.systemui.statusbar.LockscreenShadeQsTransitionController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.SingleShadeLockScreenOverScroller;
import com.android.systemui.statusbar.SplitShadeLockScreenOverScroller;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class LockscreenShadeTransitionController implements Dumpable {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = "LockscreenShadeTransitionController";
    public final ActivityStarter activityStarter;
    public final AmbientState ambientState;
    public Function1 animationHandlerOnKeyguardDismiss;
    public final List callbacks;
    public CentralSurfacesImpl centralSurfaces;
    public final Context context;
    public final NotificationShadeDepthController depthController;
    public int depthControllerTransitionDistance;
    public float dragDownAmount;
    public ValueAnimator dragDownAnimator;
    public final Lazy editModeController;
    public final FalsingCollector falsingCollector;
    public boolean forceApplyAmount;
    public float fractionToShade;
    public int fullTransitionDistance;
    public int fullTransitionDistanceByTap;
    public boolean isKeyguardAnimatorStarted;
    public boolean isWakingToShadeLocked;
    public final KeyguardBypassController keyguardBypassController;
    public final kotlin.Lazy keyguardTransitionController$delegate;
    public final LockscreenShadeKeyguardTransitionController.Factory keyguardTransitionControllerFactory;
    public final Lazy lazyQSSceneAdapter;
    public final NotificationLockscreenUserManager lockScreenUserManager;
    public final LSShadeTransitionLogger logger;
    public final MdmOverlayContainer mdmOverlayContainer;
    public final MediaHierarchyManager mediaHierarchyManager;
    public boolean nextHideKeyguardNeedsNoAnimation;
    public int notificationShelfTransitionDistance;
    public NotificationStackScrollLayoutController nsslController;
    public float overDragAmount;
    public final kotlin.Lazy panelExpansionStateInteractor$delegate;
    public final float panelFlingOvershootAmount;
    public final SecPanelSAStatusLogInteractor panelSAStatusLogInteractor;
    public final kotlin.Lazy panelSplitHelper$delegate;
    public final kotlin.Lazy panelTouchBlockHelper$delegate;
    public final kotlin.Lazy phoneShadeOverScroller$delegate;
    public final PluginLockMediator pluginLockMediator;
    public float pulseHeight;
    public ValueAnimator pulseHeightAnimator;
    public QS qS;
    public final LockscreenShadeQsTransitionController qsTransitionController;
    public final LockscreenShadeScrimTransitionController scrimTransitionController;
    public final ShadeInteractor shadeInteractor;
    public final Lazy shadeLockscreenInteractorLazy;
    public final ShadeRepository shadeRepository;
    public final SingleShadeLockScreenOverScroller.Factory singleShadeOverScrollerFactory;
    public final SplitShadeLockScreenOverScroller.Factory splitShadeOverScrollerFactory;
    public final SplitShadeStateController splitShadeStateController;
    public final SysuiStatusBarStateController statusBarStateController;
    public int statusBarTransitionDistance;
    public final DragDownHelper touchHelper;
    public int udfpsTransitionDistance;

    public interface Callback {
        default void setTransitionToFullShadeAmount(float f) {
        }

        default void setTransitionToFullShadeAmount(float f, boolean z, long j) {
        }

        default void setOverDragAmount(float f) {
        }

        default void onExpansionFinished() {
        }

        default void onExpansionReset() {
        }

        default void onExpansionStarted() {
        }

        default void onPulseExpansionFinished() {
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public LockscreenShadeTransitionController(MdmOverlayContainer mdmOverlayContainer, Lazy lazy, SysuiStatusBarStateController sysuiStatusBarStateController, LSShadeTransitionLogger lSShadeTransitionLogger, KeyguardBypassController keyguardBypassController, NotificationLockscreenUserManager notificationLockscreenUserManager, FalsingCollector falsingCollector, AmbientState ambientState, MediaHierarchyManager mediaHierarchyManager, LockscreenShadeScrimTransitionController lockscreenShadeScrimTransitionController, LockscreenShadeKeyguardTransitionController.Factory factory, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardTouchSecurityInjector keyguardTouchSecurityInjector, NotificationShadeDepthController notificationShadeDepthController, Context context, SplitShadeLockScreenOverScroller.Factory factory2, SingleShadeLockScreenOverScroller.Factory factory3, ActivityStarter activityStarter, WakefulnessLifecycle wakefulnessLifecycle, ConfigurationController configurationController, FalsingManager falsingManager, DumpManager dumpManager, LockscreenShadeQsTransitionController.Factory factory4, ShadeRepository shadeRepository, ShadeInteractor shadeInteractor, SplitShadeStateController splitShadeStateController, Lazy lazy2, NaturalScrollingSettingObserver naturalScrollingSettingObserver, Lazy lazy3, PluginLockMediator pluginLockMediator) {
        this.mdmOverlayContainer = mdmOverlayContainer;
        this.editModeController = lazy;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.logger = lSShadeTransitionLogger;
        this.keyguardBypassController = keyguardBypassController;
        this.lockScreenUserManager = notificationLockscreenUserManager;
        this.falsingCollector = falsingCollector;
        this.ambientState = ambientState;
        this.mediaHierarchyManager = mediaHierarchyManager;
        this.scrimTransitionController = lockscreenShadeScrimTransitionController;
        this.keyguardTransitionControllerFactory = factory;
        this.depthController = notificationShadeDepthController;
        this.context = context;
        this.splitShadeOverScrollerFactory = factory2;
        this.singleShadeOverScrollerFactory = factory3;
        this.activityStarter = activityStarter;
        this.shadeRepository = shadeRepository;
        this.shadeInteractor = shadeInteractor;
        this.splitShadeStateController = splitShadeStateController;
        this.shadeLockscreenInteractorLazy = lazy2;
        this.lazyQSSceneAdapter = lazy3;
        this.pluginLockMediator = pluginLockMediator;
        final int i = 0;
        this.panelExpansionStateInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        LockscreenShadeTransitionController.Companion companion = LockscreenShadeTransitionController.Companion;
                        return (SecPanelExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecPanelExpansionStateInteractor.class);
                    case 1:
                        LockscreenShadeTransitionController.Companion companion2 = LockscreenShadeTransitionController.Companion;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    case 2:
                        LockscreenShadeTransitionController.Companion companion3 = LockscreenShadeTransitionController.Companion;
                        return (CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class);
                    default:
                        LockscreenShadeTransitionController.Companion companion4 = LockscreenShadeTransitionController.Companion;
                        return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
                }
            }
        });
        final int i2 = 1;
        this.panelSplitHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        LockscreenShadeTransitionController.Companion companion = LockscreenShadeTransitionController.Companion;
                        return (SecPanelExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecPanelExpansionStateInteractor.class);
                    case 1:
                        LockscreenShadeTransitionController.Companion companion2 = LockscreenShadeTransitionController.Companion;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    case 2:
                        LockscreenShadeTransitionController.Companion companion3 = LockscreenShadeTransitionController.Companion;
                        return (CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class);
                    default:
                        LockscreenShadeTransitionController.Companion companion4 = LockscreenShadeTransitionController.Companion;
                        return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
                }
            }
        });
        final int i3 = 2;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        LockscreenShadeTransitionController.Companion companion = LockscreenShadeTransitionController.Companion;
                        return (SecPanelExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecPanelExpansionStateInteractor.class);
                    case 1:
                        LockscreenShadeTransitionController.Companion companion2 = LockscreenShadeTransitionController.Companion;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    case 2:
                        LockscreenShadeTransitionController.Companion companion3 = LockscreenShadeTransitionController.Companion;
                        return (CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class);
                    default:
                        LockscreenShadeTransitionController.Companion companion4 = LockscreenShadeTransitionController.Companion;
                        return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
                }
            }
        });
        final int i4 = 3;
        this.panelTouchBlockHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        LockscreenShadeTransitionController.Companion companion = LockscreenShadeTransitionController.Companion;
                        return (SecPanelExpansionStateInteractor) Dependency.sDependency.getDependencyInner(SecPanelExpansionStateInteractor.class);
                    case 1:
                        LockscreenShadeTransitionController.Companion companion2 = LockscreenShadeTransitionController.Companion;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    case 2:
                        LockscreenShadeTransitionController.Companion companion3 = LockscreenShadeTransitionController.Companion;
                        return (CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class);
                    default:
                        LockscreenShadeTransitionController.Companion companion4 = LockscreenShadeTransitionController.Companion;
                        return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
                }
            }
        });
        this.panelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
        this.panelFlingOvershootAmount = context.getResources().getDimension(R.dimen.panel_overshoot_amount);
        this.touchHelper = new DragDownHelper(falsingManager, this, naturalScrollingSettingObserver, shadeRepository, keyguardUpdateMonitor, keyguardTouchSecurityInjector, context, lSShadeTransitionLogger, pluginLockMediator);
        LazyKt__LazyJVMKt.lazy(new LockscreenShadeTransitionController$$ExternalSyntheticLambda0(this, 4));
        this.phoneShadeOverScroller$delegate = LazyKt__LazyJVMKt.lazy(new LockscreenShadeTransitionController$$ExternalSyntheticLambda0(this, 5));
        this.keyguardTransitionController$delegate = LazyKt__LazyJVMKt.lazy(new LockscreenShadeTransitionController$$ExternalSyntheticLambda0(this, 6));
        this.qsTransitionController = factory4.create(new LockscreenShadeTransitionController$$ExternalSyntheticLambda0(this, 1));
        this.callbacks = new ArrayList();
        updateResources$12();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                Companion companion = LockscreenShadeTransitionController.Companion;
                LockscreenShadeTransitionController lockscreenShadeTransitionController = LockscreenShadeTransitionController.this;
                lockscreenShadeTransitionController.updateResources$12();
                lockscreenShadeTransitionController.touchHelper.updateResources$1(lockscreenShadeTransitionController.context);
            }
        });
        dumpManager.registerDumpable(this);
        sysuiStatusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController.2
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                LockscreenShadeTransitionController.Companion.getClass();
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onDozingChanged ", LockscreenShadeTransitionController.TAG, z);
                DragDownHelper dragDownHelper = LockscreenShadeTransitionController.this.touchHelper;
                if (!z) {
                    dragDownHelper.getClass();
                    return;
                }
                ValueAnimator valueAnimator = dragDownHelper.maxDragDownAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                dragDownHelper.stopDragging();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onExpandedChanged(boolean z) {
                ValueAnimator valueAnimator;
                if (z) {
                    return;
                }
                LockscreenShadeTransitionController lockscreenShadeTransitionController = LockscreenShadeTransitionController.this;
                if (lockscreenShadeTransitionController.dragDownAmount != 0.0f && ((valueAnimator = lockscreenShadeTransitionController.dragDownAnimator) == null || !valueAnimator.isRunning())) {
                    LSShadeTransitionLogger lSShadeTransitionLogger2 = lockscreenShadeTransitionController.logger;
                    lSShadeTransitionLogger2.getClass();
                    LogLevel logLevel = LogLevel.WARNING;
                    LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda0 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(8);
                    LogBuffer logBuffer = lSShadeTransitionLogger2.buffer;
                    logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$$ExternalSyntheticLambda0, null));
                    lockscreenShadeTransitionController.setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0.0f);
                }
                if (lockscreenShadeTransitionController.pulseHeight == 0.0f) {
                    return;
                }
                ValueAnimator valueAnimator2 = lockscreenShadeTransitionController.pulseHeightAnimator;
                if (valueAnimator2 == null || !valueAnimator2.isRunning()) {
                    LSShadeTransitionLogger lSShadeTransitionLogger3 = lockscreenShadeTransitionController.logger;
                    lSShadeTransitionLogger3.getClass();
                    LogLevel logLevel2 = LogLevel.WARNING;
                    LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda02 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(7);
                    LogBuffer logBuffer2 = lSShadeTransitionLogger3.buffer;
                    logBuffer2.commit(logBuffer2.obtain("LockscreenShadeTransitionController", logLevel2, lSShadeTransitionLogger$$ExternalSyntheticLambda02, null));
                    lockscreenShadeTransitionController.setPulseHeight(0.0f, false);
                }
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i5) {
                LockscreenShadeTransitionController lockscreenShadeTransitionController = LockscreenShadeTransitionController.this;
                if (i5 != 0) {
                    if (i5 != 2) {
                        return;
                    }
                    lockscreenShadeTransitionController.setOverDragAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0.0f);
                } else {
                    LockscreenShadeTransitionController.Companion.getClass();
                    Log.d(LockscreenShadeTransitionController.TAG, "onStateChanged SHADE and notify onExpansionFinished");
                    Iterator it = lockscreenShadeTransitionController.callbacks.iterator();
                    while (it.hasNext()) {
                        ((Callback) it.next()).onExpansionFinished();
                    }
                }
            }
        });
        wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController.3
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onPostFinishedWakingUp() {
                LockscreenShadeTransitionController.this.isWakingToShadeLocked = false;
            }
        });
    }

    public final void addCallback(Callback callback) {
        if (this.callbacks.contains(callback)) {
            return;
        }
        this.callbacks.add(callback);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        if (((KeyguardEditModeControllerImpl) ((KeyguardEditModeController) this.editModeController.get())).getVIRunning()) {
            return false;
        }
        if (this.statusBarStateController.getState() != 1) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
            if (notificationStackScrollLayoutController == null) {
                notificationStackScrollLayoutController = null;
            }
            if (notificationStackScrollLayoutController.mDynamicPrivacyController.isInLockedDownShade()) {
            }
        } else if (isQsFullyCollapsed$1()) {
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("LSShadeTransitionController:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("pulseHeight: " + this.pulseHeight);
        indentingPrintWriter.println("useSplitShade: false");
        indentingPrintWriter.println("dragDownAmount: " + this.dragDownAmount);
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("isDragDownAnywhereEnabled: ", isDragDownAnywhereEnabled$frameworks__base__packages__SystemUI__android_common__SystemUI_core(), indentingPrintWriter);
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("isFalsingCheckNeeded: ", this.statusBarStateController.getState() == 1, indentingPrintWriter);
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("isWakingToShadeLocked: ", this.isWakingToShadeLocked, indentingPrintWriter);
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("hasPendingHandlerOnKeyguardDismiss: ", this.animationHandlerOnKeyguardDismiss != null, indentingPrintWriter);
    }

    public final void finishPulseAnimation(boolean z) {
        LogBuffer logBuffer = this.logger.buffer;
        if (z) {
            logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", LogLevel.INFO, new LSShadeTransitionLogger$$ExternalSyntheticLambda0(12), null));
        } else {
            logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", LogLevel.INFO, new LSShadeTransitionLogger$$ExternalSyntheticLambda0(13), null));
        }
        if (z) {
            setPulseHeight(0.0f, true);
            return;
        }
        ArrayList arrayList = (ArrayList) this.callbacks;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Callback) obj).onPulseExpansionFinished();
        }
        setPulseHeight(0.0f, false);
    }

    public final float getFractionToShade() {
        return this.fractionToShade;
    }

    public final SecPanelSplitHelper getPanelSplitHelper$1() {
        return (SecPanelSplitHelper) this.panelSplitHelper$delegate.getValue();
    }

    public final void goToLockedShade(View view, boolean z) {
        boolean z2 = this.statusBarStateController.getState() == 1;
        LSShadeTransitionLogger lSShadeTransitionLogger = this.logger;
        lSShadeTransitionLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda0 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(5);
        LogBuffer logBuffer = lSShadeTransitionLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).bool1 = z2;
        logBuffer.commit(logMessageObtain);
        if (z2) {
            SecPanelTouchBlockHelper secPanelTouchBlockHelper = (SecPanelTouchBlockHelper) this.panelTouchBlockHelper$delegate.getValue();
            if (secPanelTouchBlockHelper == null || !secPanelTouchBlockHelper.isKeyguardPanelDisabled()) {
                goToLockedShadeInternal(view, !z ? new Function1() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        ((ShadeLockscreenInteractor) this.f$0.shadeLockscreenInteractorLazy.get()).transitionToExpandedShade(((Long) obj).longValue(), false);
                        return Unit.INSTANCE;
                    }
                } : null, null);
            } else {
                Log.d("LockscreenShadeTransitionController", "goToLockedShade: returned");
            }
        }
    }

    public final void goToLockedShadeInternal(View view, Function1 function1, LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1) {
        boolean zBooleanValue = ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).isShadeEnabled.$$delegate_0.getValue()).booleanValue();
        LSShadeTransitionLogger lSShadeTransitionLogger = this.logger;
        if (!zBooleanValue) {
            if (lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 != null) {
                lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1.run();
            }
            lSShadeTransitionLogger.getClass();
            LogLevel logLevel = LogLevel.WARNING;
            LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda0 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(6);
            LogBuffer logBuffer = lSShadeTransitionLogger.buffer;
            logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$$ExternalSyntheticLambda0, null));
            return;
        }
        if (!((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).mDeviceProvisioned) {
            if (lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 != null) {
                lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1.run();
            }
            Log.d("LockscreenShadeTransitionController", "not provisioned");
            return;
        }
        NotificationLockscreenUserManager notificationLockscreenUserManager = this.lockScreenUserManager;
        int userId = ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId;
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            expandableNotificationRow.setUserExpanded(true, true);
            expandableNotificationRow.mGroupExpansionChanging = true;
            userId = expandableNotificationRow.getEntryLegacy().mSbn.getUserId();
        }
        notificationLockscreenUserManager.getClass();
        this.falsingCollector.getClass();
        this.keyguardBypassController.getBypassEnabled();
        CentralSurfacesImpl centralSurfacesImpl = this.centralSurfaces;
        if (centralSurfacesImpl == null) {
            centralSurfacesImpl = null;
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = centralSurfacesImpl.mStatusBarKeyguardViewManager;
        if (!statusBarKeyguardViewManager.isBouncerShowing() && centralSurfacesImpl.mStatusBarStateController.getState() != 0) {
            statusBarKeyguardViewManager.resetKeyguardDismissAction();
        }
        ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).isLockscreenPublicMode(userId);
        final boolean z = function1 != null;
        lSShadeTransitionLogger.getClass();
        LogLevel logLevel2 = LogLevel.INFO;
        Function1 function12 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return "Going to locked shade ".concat(z ? "with" : "without a custom handler");
            }
        };
        LogBuffer logBuffer2 = lSShadeTransitionLogger.buffer;
        LogMessage logMessageObtain = logBuffer2.obtain("LockscreenShadeTransitionController", logLevel2, function12, null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        logBuffer2.commit(logMessageObtain);
        SysuiStatusBarStateController sysuiStatusBarStateController = this.statusBarStateController;
        if (sysuiStatusBarStateController.isDozing()) {
            this.isWakingToShadeLocked = true;
        }
        sysuiStatusBarStateController.setState(2);
        if (function1 != null) {
            function1.mo781invoke(0L);
        } else {
            performDefaultGoToFullShadeAnimation(0L);
        }
        this.mdmOverlayContainer.updateMdmPolicy();
    }

    public final boolean isDragDownAnywhereEnabled$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.statusBarStateController.getState() == 1 && !this.keyguardBypassController.getBypassEnabled() && isQsFullyCollapsed$1();
    }

    public final boolean isDragDownEnabledForView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(ExpandableView expandableView) {
        if (isDragDownAnywhereEnabled$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
            return true;
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        if (!notificationStackScrollLayoutController.mDynamicPrivacyController.isInLockedDownShade()) {
            return false;
        }
        if (expandableView == null) {
            return true;
        }
        if (expandableView instanceof ExpandableNotificationRow) {
            return ((Boolean) ((ExpandableNotificationRow) expandableView).getEntryLegacy().mSensitive.getValue()).booleanValue();
        }
        return false;
    }

    public final boolean isOverDraggingAllowed() {
        SecPanelSplitHelper panelSplitHelper$1;
        SecPanelSplitHelper.Companion.getClass();
        if (SecPanelSplitHelper.isEnabled) {
            return (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isNotificationAsCard() && (panelSplitHelper$1 = getPanelSplitHelper$1()) != null && panelSplitHelper$1.isShadeState()) ? false : true;
        }
        return false;
    }

    public final boolean isQsFullyCollapsed$1() {
        QS qs = this.qS;
        if (qs != null) {
            return qs.isFullyCollapsed();
        }
        QSImpl qSImpl = (QSImpl) ((QSSceneAdapterImpl) ((QSSceneAdapter) this.lazyQSSceneAdapter.get())).qsImpl.$$delegate_0.getValue();
        if (qSImpl != null) {
            return qSImpl.isFullyCollapsed();
        }
        return true;
    }

    public final void onDragDownStarted$frameworks__base__packages__SystemUI__android_common__SystemUI_core(ExpandableView expandableView) {
        String str;
        LSShadeTransitionLogger lSShadeTransitionLogger = this.logger;
        lSShadeTransitionLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda0 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(19);
        LogBuffer logBuffer = lSShadeTransitionLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$$ExternalSyntheticLambda0, null);
        ExpandableNotificationRow expandableNotificationRow = expandableView instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) expandableView : null;
        if (expandableNotificationRow == null || (str = expandableNotificationRow.mLoggingKey) == null) {
            str = "no entry";
        }
        ((LogMessageImpl) logMessageObtain).str1 = str;
        logBuffer.commit(logMessageObtain);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        notificationStackScrollLayoutController.mView.cancelLongPress();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.nsslController;
        (notificationStackScrollLayoutController2 != null ? notificationStackScrollLayoutController2 : null).checkSnoozeLeavebehind();
        ValueAnimator valueAnimator = this.dragDownAnimator;
        int i = 0;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            lSShadeTransitionLogger.logAnimationCancelled(false);
            valueAnimator.cancel();
        }
        Log.d(TAG, "onDragDownStarted");
        ArrayList arrayList = (ArrayList) this.callbacks;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Callback) obj).onExpansionStarted();
        }
    }

    public final void performDefaultGoToFullShadeAnimation(long j) {
        LSShadeTransitionLogger lSShadeTransitionLogger = this.logger;
        lSShadeTransitionLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda0 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(14);
        LogBuffer logBuffer = lSShadeTransitionLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).long1 = j;
        logBuffer.commit(logMessageObtain);
        ((ShadeLockscreenInteractor) this.shadeLockscreenInteractorLazy.get()).transitionToExpandedShade(j, false);
        this.forceApplyAmount = true;
        setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1.0f);
        setDragDownAmountAnimated(this.fullTransitionDistanceByTap, j, new LockscreenShadeTransitionController$$ExternalSyntheticLambda0(this, 0));
    }

    public final void removeCallback(Callback callback) {
        if (((ArrayList) this.callbacks).contains(callback)) {
            ((ArrayList) this.callbacks).remove(callback);
        }
    }

    public final void setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(float f) {
        float f2;
        if (this.dragDownAmount != f || this.forceApplyAmount) {
            this.dragDownAmount = f;
            Log.d(TAG, "dragDownAmount : " + f);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
            if (notificationStackScrollLayoutController == null) {
                notificationStackScrollLayoutController = null;
            }
            if (!notificationStackScrollLayoutController.mDynamicPrivacyController.isInLockedDownShade() || this.dragDownAmount == 0.0f || this.forceApplyAmount) {
                float fSaturate = MathUtils.saturate(this.dragDownAmount / this.notificationShelfTransitionDistance);
                this.fractionToShade = fSaturate;
                ((ShadeRepositoryImpl) this.shadeRepository)._lockscreenShadeExpansion.updateState(null, Float.valueOf(fSaturate));
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.nsslController;
                if (notificationStackScrollLayoutController2 == null) {
                    notificationStackScrollLayoutController2 = null;
                }
                float f3 = this.fractionToShade;
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController2.mView;
                notificationStackScrollLayout.mAmbientState.mFractionToShade = f3;
                notificationStackScrollLayout.updateContentHeight();
                notificationStackScrollLayout.requestChildrenUpdate();
                SecPanelExpansionStateInteractor secPanelExpansionStateInteractor = (SecPanelExpansionStateInteractor) this.panelExpansionStateInteractor$delegate.getValue();
                if (secPanelExpansionStateInteractor != null) {
                    secPanelExpansionStateInteractor.getRepository()._lockscreenShadeFraction.updateState(null, Float.valueOf(this.fractionToShade));
                }
                LockscreenShadeQsTransitionController lockscreenShadeQsTransitionController = this.qsTransitionController;
                if (f != lockscreenShadeQsTransitionController.dragDownAmount) {
                    lockscreenShadeQsTransitionController.dragDownAmount = f;
                    float f4 = f - lockscreenShadeQsTransitionController.qsTransitionStartDelay;
                    lockscreenShadeQsTransitionController.qsDragDownAmount = f4;
                    lockscreenShadeQsTransitionController.qsTransitionFraction = MathUtils.saturate(f4 / lockscreenShadeQsTransitionController.qsTransitionDistance);
                    lockscreenShadeQsTransitionController.qsSquishTransitionFraction = MathUtils.lerp(lockscreenShadeQsTransitionController.qsSquishStartFraction, 1.0f, MathUtils.saturate(lockscreenShadeQsTransitionController.qsDragDownAmount / lockscreenShadeQsTransitionController.qsSquishTransitionDistance));
                    lockscreenShadeQsTransitionController.isTransitioningToFullShade = f > 0.0f;
                    QS qs = (QS) lockscreenShadeQsTransitionController.qsProvider.invoke();
                    if (qs != null) {
                        qs.setTransitionToFullShadeProgress(lockscreenShadeQsTransitionController.isTransitioningToFullShade, lockscreenShadeQsTransitionController.qsTransitionFraction, lockscreenShadeQsTransitionController.qsSquishTransitionFraction);
                    }
                }
                for (Callback callback : this.callbacks) {
                    callback.setTransitionToFullShadeAmount(this.dragDownAmount, false, 0L);
                    callback.setTransitionToFullShadeAmount(this.fractionToShade);
                }
                this.mediaHierarchyManager.setTransitionToFullShadeAmount(this.dragDownAmount);
                LockscreenShadeScrimTransitionController lockscreenShadeScrimTransitionController = this.scrimTransitionController;
                if (f != lockscreenShadeScrimTransitionController.dragDownAmount) {
                    lockscreenShadeScrimTransitionController.dragDownAmount = f;
                    lockscreenShadeScrimTransitionController.scrimProgress = MathUtils.saturate(f / lockscreenShadeScrimTransitionController.scrimTransitionDistance);
                    float f5 = f - lockscreenShadeScrimTransitionController.notificationsScrimTransitionDelay;
                    lockscreenShadeScrimTransitionController.notificationsScrimDragAmount = f5;
                    float fSaturate2 = MathUtils.saturate(f5 / lockscreenShadeScrimTransitionController.notificationsScrimTransitionDistance);
                    lockscreenShadeScrimTransitionController.notificationsScrimProgress = fSaturate2;
                    float f6 = lockscreenShadeScrimTransitionController.scrimProgress;
                    ScrimController scrimController = lockscreenShadeScrimTransitionController.scrimController;
                    if (f6 != scrimController.mTransitionToFullShadeProgress || fSaturate2 != scrimController.mTransitionToLockScreenFullShadeNotificationsProgress) {
                        scrimController.mTransitionToFullShadeProgress = f6;
                        scrimController.mTransitionToLockScreenFullShadeNotificationsProgress = fSaturate2;
                        boolean z = f6 > 0.0f || fSaturate2 > 0.0f;
                        if (z != scrimController.mTransitioningToFullShade) {
                            scrimController.mTransitioningToFullShade = z;
                        }
                        scrimController.applyAndDispatchState();
                    }
                }
                transitionToShadeAmountCommon(this.dragDownAmount);
                LockscreenShadeKeyguardTransitionController lockscreenShadeKeyguardTransitionController = (LockscreenShadeKeyguardTransitionController) this.keyguardTransitionController$delegate.getValue();
                if (f != lockscreenShadeKeyguardTransitionController.dragDownAmount) {
                    lockscreenShadeKeyguardTransitionController.dragDownAmount = f;
                    float fSaturate3 = MathUtils.saturate(f / lockscreenShadeKeyguardTransitionController.alphaTransitionDistance);
                    lockscreenShadeKeyguardTransitionController.alphaProgress = fSaturate3;
                    lockscreenShadeKeyguardTransitionController.alpha = 1.0f - fSaturate3;
                    lockscreenShadeKeyguardTransitionController.statusBarAlpha = -1.0f;
                    ShadeLockscreenInteractor shadeLockscreenInteractor = lockscreenShadeKeyguardTransitionController.shadeLockscreenInteractor;
                    shadeLockscreenInteractor.setKeyguardStatusBarAlpha();
                    shadeLockscreenInteractor.onDragDownAmountChanged(lockscreenShadeKeyguardTransitionController.alphaProgress);
                }
                SingleShadeLockScreenOverScroller singleShadeLockScreenOverScroller = (SingleShadeLockScreenOverScroller) this.phoneShadeOverScroller$delegate.getValue();
                float f7 = this.dragDownAmount;
                if (f7 != singleShadeLockScreenOverScroller.expansionDragDownAmount) {
                    singleShadeLockScreenOverScroller.expansionDragDownAmount = f7;
                    int state = singleShadeLockScreenOverScroller.statusBarStateController.getState();
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = singleShadeLockScreenOverScroller.nsslController;
                    if (state != 1 || ((AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class)).isNeedsToExpandLocksNoti()) {
                        f2 = 0.0f;
                    } else {
                        float height = notificationStackScrollLayoutController3.mView.getHeight();
                        float fSaturate4 = MathUtils.saturate(singleShadeLockScreenOverScroller.expansionDragDownAmount / height);
                        float f8 = singleShadeLockScreenOverScroller.totalDistanceForFullShadeTransition / height;
                        Interpolator interpolator = Interpolators.EMPHASIZED;
                        if (f8 == 0.0f) {
                            throw new IllegalArgumentException("Invalid values for overshoot");
                        }
                        float fExp = ((float) (1.0d - Math.exp((-(((float) Math.log(2.6666665f)) / f8)) * fSaturate4))) * 1.6f;
                        if (0.0f > fExp) {
                            fExp = 0.0f;
                        }
                        f2 = fExp * singleShadeLockScreenOverScroller.maxOverScrollAmount;
                    }
                    notificationStackScrollLayoutController3.setOverScrollAmount((int) f2);
                }
                if (this.dragDownAmount > this.notificationShelfTransitionDistance && isOverDraggingAllowed()) {
                    setOverDragAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(MathUtils.saturate((this.dragDownAmount - this.notificationShelfTransitionDistance) / (DeviceState.getScreenHeight(this.context) / 3.0f)) * this.panelFlingOvershootAmount);
                }
                float f9 = this.dragDownAmount;
                if (f9 == 0.0f) {
                    setOverDragAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(f9);
                }
            }
        }
    }

    public final void setDragDownAmountAnimated(float f, long j, final LockscreenShadeTransitionController$$ExternalSyntheticLambda0 lockscreenShadeTransitionController$$ExternalSyntheticLambda0) {
        LSShadeTransitionLogger lSShadeTransitionLogger = this.logger;
        lSShadeTransitionLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda0 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(18);
        LogBuffer logBuffer = lSShadeTransitionLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).double1 = f;
        logBuffer.commit(logMessageObtain);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.dragDownAmount, f);
        valueAnimatorOfFloat.setInterpolator(com.android.wm.shell.shared.animation.Interpolators.FAST_OUT_SLOW_IN);
        valueAnimatorOfFloat.setDuration(375L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController.setDragDownAmountAnimated.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LockscreenShadeTransitionController.this.setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        if (j > 0) {
            valueAnimatorOfFloat.setStartDelay(j);
        }
        if (lockscreenShadeTransitionController$$ExternalSyntheticLambda0 != null) {
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController.setDragDownAmountAnimated.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    lockscreenShadeTransitionController$$ExternalSyntheticLambda0.invoke();
                }
            });
        }
        valueAnimatorOfFloat.start();
        this.dragDownAnimator = valueAnimatorOfFloat;
    }

    public final void setOverDragAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(float f) {
        if (this.statusBarStateController.getState() != 1) {
            this.overDragAmount = 0.0f;
        }
        if (this.overDragAmount != f || f == 0.0f) {
            this.overDragAmount = f;
            Log.d(TAG, "overDragDownAmount : " + f);
            Iterator it = this.callbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).setOverDragAmount(this.overDragAmount);
            }
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
            if (notificationStackScrollLayoutController == null) {
                notificationStackScrollLayoutController = null;
            }
            notificationStackScrollLayoutController.setOverExpansion(this.overDragAmount);
        }
    }

    public final void setPulseHeight(float f, boolean z) {
        if (z) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.pulseHeight, f);
            valueAnimatorOfFloat.setInterpolator(com.android.wm.shell.shared.animation.Interpolators.FAST_OUT_SLOW_IN);
            valueAnimatorOfFloat.setDuration(375L);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController.setPulseHeight.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LockscreenShadeTransitionController lockscreenShadeTransitionController = LockscreenShadeTransitionController.this;
                    float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    Companion companion = LockscreenShadeTransitionController.Companion;
                    lockscreenShadeTransitionController.setPulseHeight(fFloatValue, false);
                }
            });
            valueAnimatorOfFloat.start();
            this.pulseHeightAnimator = valueAnimatorOfFloat;
            return;
        }
        this.pulseHeight = f;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        ((ShadeLockscreenInteractor) this.shadeLockscreenInteractorLazy.get()).setOverStretchAmount(notificationStackScrollLayoutController.mView.setPulseHeight(f));
        if (!this.keyguardBypassController.getBypassEnabled()) {
            f = 0.0f;
        }
        transitionToShadeAmountCommon(f);
    }

    public final void transitionToShadeAmountCommon(float f) {
        int i = this.depthControllerTransitionDistance;
        NotificationShadeDepthController notificationShadeDepthController = this.depthController;
        if (i != 0) {
            float fSaturate = MathUtils.saturate(f / i);
            if (notificationShadeDepthController.transitionToFullShadeProgress != fSaturate) {
                notificationShadeDepthController.transitionToFullShadeProgress = fSaturate;
                notificationShadeDepthController.scheduleUpdate();
            }
        } else if (notificationShadeDepthController.transitionToFullShadeProgress != 0.0f) {
            notificationShadeDepthController.transitionToFullShadeProgress = 0.0f;
            notificationShadeDepthController.scheduleUpdate();
        }
        ((ShadeRepositoryImpl) this.shadeRepository)._udfpsTransitionToFullShadeProgress.updateState(null, Float.valueOf(MathUtils.saturate(f / this.udfpsTransitionDistance)));
        float fSaturate2 = MathUtils.saturate(f / this.statusBarTransitionDistance);
        CentralSurfacesImpl centralSurfacesImpl = this.centralSurfaces;
        (centralSurfacesImpl != null ? centralSurfacesImpl : null).mTransitionToFullShadeProgress = fSaturate2;
    }

    public final void updateResources$12() {
        int screenHeight = DeviceState.getScreenHeight(this.context) / 2;
        this.fullTransitionDistance = screenHeight;
        this.fullTransitionDistanceByTap = screenHeight;
        this.notificationShelfTransitionDistance = screenHeight;
        this.depthControllerTransitionDistance = screenHeight;
        this.udfpsTransitionDistance = screenHeight;
        this.statusBarTransitionDistance = screenHeight;
        this.context.getResources();
        ((SplitShadeStateControllerImpl) this.splitShadeStateController).shouldUseSplitNotificationShade();
    }

    public static /* synthetic */ void getDragDownAnimator$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getPulseHeightAnimator$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
