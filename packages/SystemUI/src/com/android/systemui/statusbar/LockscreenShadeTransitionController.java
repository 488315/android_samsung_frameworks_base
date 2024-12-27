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
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.animator.KeyguardTouchSecurityInjector;
import com.android.systemui.keyguard.domain.interactor.NaturalScrollingSettingObserver;
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
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.statusbar.LockscreenShadeKeyguardTransitionController;
import com.android.systemui.statusbar.LockscreenShadeQsTransitionController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.SingleShadeLockScreenOverScroller;
import com.android.systemui.statusbar.SplitShadeLockScreenOverScroller;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.DeviceState;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LockscreenShadeTransitionController implements Dumpable {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = "LockscreenShadeTransitionController";
    public final ActivityStarter activityStarter;
    public final AmbientState ambientState;
    public Function1 animationHandlerOnKeyguardDismiss;
    public final List callbacks;
    public CentralSurfaces centralSurfaces;
    public final Context context;
    public final NotificationShadeDepthController depthController;
    public int depthControllerTransitionDistance;
    public float dragDownAmount;
    public ValueAnimator dragDownAnimator;
    public NotificationEntry draggedDownEntry;
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
    public final kotlin.Lazy panelSplitHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$panelSplitHelper$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
        }
    });
    public final kotlin.Lazy panelTouchBlockHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$panelTouchBlockHelper$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
        }
    });
    public final SecPanelSAStatusLogInteractor panelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
        default void setTransitionToFullShadeAmount(float f) {
        }

        default void setTransitionToFullShadeAmount(float f, boolean z, long j) {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        this.touchHelper = new DragDownHelper(falsingManager, this, naturalScrollingSettingObserver, shadeRepository, keyguardUpdateMonitor, keyguardTouchSecurityInjector, context, lSShadeTransitionLogger, pluginLockMediator);
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$splitShadeOverScroller$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final LockscreenShadeTransitionController lockscreenShadeTransitionController = LockscreenShadeTransitionController.this;
                SplitShadeLockScreenOverScroller.Factory factory5 = lockscreenShadeTransitionController.splitShadeOverScrollerFactory;
                Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$splitShadeOverScroller$2.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return LockscreenShadeTransitionController.this.qS;
                    }
                };
                final LockscreenShadeTransitionController lockscreenShadeTransitionController2 = LockscreenShadeTransitionController.this;
                return factory5.create(function0, new Function0() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$splitShadeOverScroller$2.2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController = LockscreenShadeTransitionController.this.nsslController;
                        if (notificationStackScrollLayoutController == null) {
                            return null;
                        }
                        return notificationStackScrollLayoutController;
                    }
                });
            }
        });
        this.phoneShadeOverScroller$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$phoneShadeOverScroller$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                LockscreenShadeTransitionController lockscreenShadeTransitionController = LockscreenShadeTransitionController.this;
                SingleShadeLockScreenOverScroller.Factory factory5 = lockscreenShadeTransitionController.singleShadeOverScrollerFactory;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = lockscreenShadeTransitionController.nsslController;
                if (notificationStackScrollLayoutController == null) {
                    notificationStackScrollLayoutController = null;
                }
                return factory5.create(notificationStackScrollLayoutController);
            }
        });
        this.keyguardTransitionController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$keyguardTransitionController$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                LockscreenShadeTransitionController lockscreenShadeTransitionController = LockscreenShadeTransitionController.this;
                return lockscreenShadeTransitionController.keyguardTransitionControllerFactory.create((ShadeLockscreenInteractor) lockscreenShadeTransitionController.shadeLockscreenInteractorLazy.get());
            }
        });
        this.qsTransitionController = factory4.create(new Function0() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$qsTransitionController$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return LockscreenShadeTransitionController.this.qS;
            }
        });
        this.callbacks = new ArrayList();
        updateResources$15();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                Companion companion = LockscreenShadeTransitionController.Companion;
                LockscreenShadeTransitionController lockscreenShadeTransitionController = LockscreenShadeTransitionController.this;
                lockscreenShadeTransitionController.updateResources$15();
                lockscreenShadeTransitionController.touchHelper.updateResources$1(lockscreenShadeTransitionController.context);
            }
        });
        dumpManager.registerDumpable(this);
        ((StatusBarStateControllerImpl) sysuiStatusBarStateController).addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController.2
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                LockscreenShadeTransitionController.Companion.getClass();
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onDozingChanged ", LockscreenShadeTransitionController.TAG, z);
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
                    lockscreenShadeTransitionController.logger.logDragDownAmountResetWhenFullyCollapsed();
                    lockscreenShadeTransitionController.setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0.0f);
                }
                if (lockscreenShadeTransitionController.pulseHeight == 0.0f) {
                    return;
                }
                ValueAnimator valueAnimator2 = lockscreenShadeTransitionController.pulseHeightAnimator;
                if (valueAnimator2 == null || !valueAnimator2.isRunning()) {
                    lockscreenShadeTransitionController.logger.logPulseHeightNotResetWhenFullyCollapsed();
                    lockscreenShadeTransitionController.setPulseHeight(0.0f, false);
                }
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                if (i == 0) {
                    LockscreenShadeTransitionController.Companion.getClass();
                    Log.d(LockscreenShadeTransitionController.TAG, "onStateChanged SHADE and notify onExpansionFinished");
                    Iterator it = LockscreenShadeTransitionController.this.callbacks.iterator();
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

    public final boolean canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        if (((KeyguardEditModeControllerImpl) ((KeyguardEditModeController) this.editModeController.get())).getVIRunning()) {
            return false;
        }
        if (((StatusBarStateControllerImpl) this.statusBarStateController).mState != 1) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
            if (notificationStackScrollLayoutController == null) {
                notificationStackScrollLayoutController = null;
            }
            if (!notificationStackScrollLayoutController.mDynamicPrivacyController.isInLockedDownShade()) {
                return false;
            }
        }
        return isQsFullyCollapsed$1();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("LSShadeTransitionController:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("pulseHeight: " + this.pulseHeight);
        indentingPrintWriter.println("useSplitShade: false");
        indentingPrintWriter.println("dragDownAmount: " + this.dragDownAmount);
        indentingPrintWriter.println("isDragDownAnywhereEnabled: " + isDragDownAnywhereEnabled$frameworks__base__packages__SystemUI__android_common__SystemUI_core());
        indentingPrintWriter.println("isFalsingCheckNeeded: " + (((StatusBarStateControllerImpl) this.statusBarStateController).mState == 1));
        indentingPrintWriter.println("isWakingToShadeLocked: " + this.isWakingToShadeLocked);
        indentingPrintWriter.println("hasPendingHandlerOnKeyguardDismiss: " + (this.animationHandlerOnKeyguardDismiss != null));
    }

    public final void finishPulseAnimation(boolean z) {
        this.logger.logPulseExpansionFinished(z);
        if (z) {
            setPulseHeight(0.0f, true);
            return;
        }
        Iterator it = ((ArrayList) this.callbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onPulseExpansionFinished();
        }
        setPulseHeight(0.0f, false);
    }

    public final float getFractionToShade() {
        return this.fractionToShade;
    }

    public final void goToLockedShade(View view, boolean z) {
        boolean z2 = ((StatusBarStateControllerImpl) this.statusBarStateController).mState == 1;
        this.logger.logTryGoToLockedShade(z2);
        if (z2) {
            SecPanelTouchBlockHelper secPanelTouchBlockHelper = (SecPanelTouchBlockHelper) this.panelTouchBlockHelper$delegate.getValue();
            if (secPanelTouchBlockHelper == null || !secPanelTouchBlockHelper.isKeyguardPanelDisabled()) {
                goToLockedShadeInternal(view, !z ? new Function1() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$goToLockedShade$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ((ShadeLockscreenInteractor) LockscreenShadeTransitionController.this.shadeLockscreenInteractorLazy.get()).transitionToExpandedShade(((Number) obj).longValue(), false);
                        return Unit.INSTANCE;
                    }
                } : null, null);
            } else {
                Log.d("LockscreenShadeTransitionController", "goToLockedShade: returned");
            }
        }
    }

    public final void goToLockedShadeInternal(View view, Function1 function1, LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1) {
        boolean booleanValue = ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).isShadeEnabled.$$delegate_0.getValue()).booleanValue();
        LSShadeTransitionLogger lSShadeTransitionLogger = this.logger;
        if (!booleanValue) {
            if (lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 != null) {
                lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1.run();
            }
            lSShadeTransitionLogger.logShadeDisabledOnGoToLockedShade();
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
        int i = ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId;
        if (view instanceof ExpandableNotificationRow) {
            NotificationEntry notificationEntry = ((ExpandableNotificationRow) view).mEntry;
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            if (expandableNotificationRow != null) {
                expandableNotificationRow.setUserExpanded(true, true);
            }
            ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
            if (expandableNotificationRow2 != null) {
                expandableNotificationRow2.mGroupExpansionChanging = true;
            }
            i = notificationEntry.mSbn.getUserId();
        }
        notificationLockscreenUserManager.getClass();
        this.falsingCollector.getClass();
        this.keyguardBypassController.getBypassEnabled();
        CentralSurfaces centralSurfaces = this.centralSurfaces;
        if (centralSurfaces == null) {
            centralSurfaces = null;
        }
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces;
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = centralSurfacesImpl.mStatusBarKeyguardViewManager;
        if (!statusBarKeyguardViewManager.isBouncerShowing() && ((StatusBarStateControllerImpl) centralSurfacesImpl.mStatusBarStateController).mState != 0) {
            statusBarKeyguardViewManager.resetKeyguardDismissAction();
        }
        ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).isLockscreenPublicMode(i);
        lSShadeTransitionLogger.logGoingToLockedShade(function1 != null);
        SysuiStatusBarStateController sysuiStatusBarStateController = this.statusBarStateController;
        if (((StatusBarStateControllerImpl) sysuiStatusBarStateController).mIsDozing) {
            this.isWakingToShadeLocked = true;
        }
        sysuiStatusBarStateController.setState(2);
        if (function1 != null) {
            function1.invoke(0L);
        } else {
            performDefaultGoToFullShadeAnimation(0L);
        }
        this.mdmOverlayContainer.updateMdmPolicy();
    }

    public final boolean isDragDownAnywhereEnabled$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return ((StatusBarStateControllerImpl) this.statusBarStateController).mState == 1 && !this.keyguardBypassController.getBypassEnabled() && isQsFullyCollapsed$1();
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
            return ((Boolean) ((ExpandableNotificationRow) expandableView).mEntry.mSensitive.getValue()).booleanValue();
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
        LSShadeTransitionLogger lSShadeTransitionLogger = this.logger;
        lSShadeTransitionLogger.logDragDownStarted(expandableView);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        notificationStackScrollLayoutController.mView.cancelLongPress();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.nsslController;
        (notificationStackScrollLayoutController2 != null ? notificationStackScrollLayoutController2 : null).checkSnoozeLeavebehind();
        ValueAnimator valueAnimator = this.dragDownAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            lSShadeTransitionLogger.logAnimationCancelled(false);
            valueAnimator.cancel();
        }
        Log.d(TAG, "onDragDownStarted");
        Iterator it = ((ArrayList) this.callbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onExpansionStarted();
        }
    }

    public final void performDefaultGoToFullShadeAnimation(long j) {
        this.logger.logDefaultGoToFullShadeAnimation(j);
        ((ShadeLockscreenInteractor) this.shadeLockscreenInteractorLazy.get()).transitionToExpandedShade(j, false);
        this.forceApplyAmount = true;
        setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1.0f);
        setDragDownAmountAnimated(this.fullTransitionDistanceByTap, j, new Function0() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$animateAppear$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                LockscreenShadeTransitionController.this.logger.logDragDownAmountReset();
                LockscreenShadeTransitionController.this.setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0.0f);
                LockscreenShadeTransitionController.this.forceApplyAmount = false;
                return Unit.INSTANCE;
            }
        });
    }

    public final void removeCallback(Callback callback) {
        if (((ArrayList) this.callbacks).contains(callback)) {
            ((ArrayList) this.callbacks).remove(callback);
        }
    }

    public final void setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(float f) {
        if (this.dragDownAmount != f || this.forceApplyAmount) {
            this.dragDownAmount = f;
            Log.d(TAG, "dragDownAmount : " + f);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
            if (notificationStackScrollLayoutController == null) {
                notificationStackScrollLayoutController = null;
            }
            float f2 = 0.0f;
            if (!notificationStackScrollLayoutController.mDynamicPrivacyController.isInLockedDownShade() || this.dragDownAmount == 0.0f || this.forceApplyAmount) {
                float saturate = MathUtils.saturate(this.dragDownAmount / this.notificationShelfTransitionDistance);
                this.fractionToShade = saturate;
                ((ShadeRepositoryImpl) this.shadeRepository)._lockscreenShadeExpansion.updateState(null, Float.valueOf(saturate));
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.nsslController;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = notificationStackScrollLayoutController2 != null ? notificationStackScrollLayoutController2 : null;
                float f3 = this.fractionToShade;
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController3.mView;
                notificationStackScrollLayout.mAmbientState.mFractionToShade = f3;
                notificationStackScrollLayout.updateContentHeight();
                notificationStackScrollLayout.requestChildrenUpdate();
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
                    float saturate2 = MathUtils.saturate(f5 / lockscreenShadeScrimTransitionController.notificationsScrimTransitionDistance);
                    lockscreenShadeScrimTransitionController.notificationsScrimProgress = saturate2;
                    float f6 = lockscreenShadeScrimTransitionController.scrimProgress;
                    ScrimController scrimController = lockscreenShadeScrimTransitionController.scrimController;
                    if (f6 != scrimController.mTransitionToFullShadeProgress || saturate2 != scrimController.mTransitionToLockScreenFullShadeNotificationsProgress) {
                        scrimController.mTransitionToFullShadeProgress = f6;
                        scrimController.mTransitionToLockScreenFullShadeNotificationsProgress = saturate2;
                        boolean z = f6 > 0.0f || saturate2 > 0.0f;
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
                    float saturate3 = MathUtils.saturate(f / lockscreenShadeKeyguardTransitionController.alphaTransitionDistance);
                    lockscreenShadeKeyguardTransitionController.alphaProgress = saturate3;
                    float f7 = 1.0f - saturate3;
                    lockscreenShadeKeyguardTransitionController.alpha = f7;
                    ShadeLockscreenInteractor shadeLockscreenInteractor = lockscreenShadeKeyguardTransitionController.shadeLockscreenInteractor;
                    shadeLockscreenInteractor.setKeyguardTransitionProgress(f7);
                    lockscreenShadeKeyguardTransitionController.statusBarAlpha = -1.0f;
                    shadeLockscreenInteractor.setKeyguardStatusBarAlpha();
                    shadeLockscreenInteractor.onDragDownAmountChanged(lockscreenShadeKeyguardTransitionController.alphaProgress);
                }
                SingleShadeLockScreenOverScroller singleShadeLockScreenOverScroller = (SingleShadeLockScreenOverScroller) this.phoneShadeOverScroller$delegate.getValue();
                float f8 = this.dragDownAmount;
                if (f8 == singleShadeLockScreenOverScroller.expansionDragDownAmount) {
                    return;
                }
                singleShadeLockScreenOverScroller.expansionDragDownAmount = f8;
                int i = ((StatusBarStateControllerImpl) singleShadeLockScreenOverScroller.statusBarStateController).mState;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController4 = singleShadeLockScreenOverScroller.nsslController;
                if (i == 1 && !((AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class)).isNeedsToExpandLocksNoti()) {
                    float height = notificationStackScrollLayoutController4.mView.getHeight();
                    f2 = Interpolators.getOvershootInterpolation(MathUtils.saturate(singleShadeLockScreenOverScroller.expansionDragDownAmount / height), singleShadeLockScreenOverScroller.totalDistanceForFullShadeTransition / height) * singleShadeLockScreenOverScroller.maxOverScrollAmount;
                }
                float f9 = (int) f2;
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController4.mView;
                notificationStackScrollLayout2.mExtraTopInsetForFullShadeTransition = f9;
                notificationStackScrollLayout2.mAmbientState.mExtraTopInsetForFullShadeTransition = f9;
                notificationStackScrollLayout2.updateStackPosition(false);
                notificationStackScrollLayout2.requestChildrenUpdate();
            }
        }
    }

    public final void setDragDownAmountAnimated(float f, long j, final Function0 function0) {
        this.logger.logDragDownAnimation(f);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.dragDownAmount, f);
        ofFloat.setInterpolator(com.android.wm.shell.animation.Interpolators.FAST_OUT_SLOW_IN);
        ofFloat.setDuration(375L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$setDragDownAmountAnimated$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LockscreenShadeTransitionController.this.setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        if (j > 0) {
            ofFloat.setStartDelay(j);
        }
        if (function0 != null) {
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$setDragDownAmountAnimated$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    Function0.this.invoke();
                }
            });
        }
        ofFloat.start();
        this.dragDownAnimator = ofFloat;
    }

    public final void setPulseHeight(float f, boolean z) {
        if (z) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.pulseHeight, f);
            ofFloat.setInterpolator(com.android.wm.shell.animation.Interpolators.FAST_OUT_SLOW_IN);
            ofFloat.setDuration(375L);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$setPulseHeight$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LockscreenShadeTransitionController lockscreenShadeTransitionController = LockscreenShadeTransitionController.this;
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    LockscreenShadeTransitionController.Companion companion = LockscreenShadeTransitionController.Companion;
                    lockscreenShadeTransitionController.setPulseHeight(floatValue, false);
                }
            });
            ofFloat.start();
            this.pulseHeightAnimator = ofFloat;
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
            float saturate = MathUtils.saturate(f / i);
            if (notificationShadeDepthController.transitionToFullShadeProgress != saturate) {
                notificationShadeDepthController.transitionToFullShadeProgress = saturate;
            }
        } else if (notificationShadeDepthController.transitionToFullShadeProgress != 0.0f) {
            notificationShadeDepthController.transitionToFullShadeProgress = 0.0f;
        }
        ((ShadeRepositoryImpl) this.shadeRepository)._udfpsTransitionToFullShadeProgress.updateState(null, Float.valueOf(MathUtils.saturate(f / this.udfpsTransitionDistance)));
        float saturate2 = MathUtils.saturate(f / this.statusBarTransitionDistance);
        CentralSurfaces centralSurfaces = this.centralSurfaces;
        ((CentralSurfacesImpl) (centralSurfaces != null ? centralSurfaces : null)).mTransitionToFullShadeProgress = saturate2;
    }

    public final void updateResources$15() {
        int screenHeight = DeviceState.getScreenHeight(this.context) / 3;
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
