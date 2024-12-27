package com.android.systemui.shade;

import android.animation.ValueAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Debug;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.PathInterpolator;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.PanelSlideEventHandler;
import com.android.systemui.shade.data.repository.SecPanelSAStatusLogRepository;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecPanelSplitHelper implements ShadeExpansionListener, SettingsHelper.OnChangedCallback, StatusBarStateController.StateListener, LockscreenShadeTransitionController.Callback {
    public static final Companion Companion = new Companion(null);
    public static final Uri SPLIT_URI = Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL);
    public static final Uri USER_CHANGED = Uri.parse("USER_CHANGED");
    public static boolean isEnabled;
    public final Context context;
    public int currentState;
    public float draggedFraction;
    public boolean enabled;
    public final Executor executor;
    public final CopyOnWriteArrayList expansionListeners;
    public boolean isOnceOverExpanded;
    public boolean onceOverSlide;
    public float overSlideAmount;
    public boolean panelExpanded;
    public View panelRootView;
    public final SecPanelSAStatusLogInteractor panelSAStatusLogInteractor;
    public final PanelSlideEventHandler panelSlideEventHandler;
    public QS qs;
    public SecQSImplAnimatorManager qsAnimatorManager;
    public View qsScrollView;
    public final QuickSettingsControllerImpl quickSettingsControllerImpl;
    public final ShadeRepository repository;
    public final Lazy settingsHelper$delegate;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public View shadeRootView;
    public boolean shouldQsDownInLockscreen;
    public int stateOnDown;
    public int stateToChange;
    public final Lazy statusBarStateController$delegate;
    public MotionEvent synthesizedActionDown;
    public final UserTracker.Callback userChanged;
    public final UserTracker userTracker;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SecPanelSplitHelper(Context context, ShadeExpansionStateManager shadeExpansionStateManager, QuickSettingsControllerImpl quickSettingsControllerImpl, CoroutineScope coroutineScope, ShadeRepository shadeRepository, LockscreenShadeTransitionController lockscreenShadeTransitionController, UserTracker userTracker, Executor executor) {
        this.context = context;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.quickSettingsControllerImpl = quickSettingsControllerImpl;
        this.repository = shadeRepository;
        this.userTracker = userTracker;
        this.executor = executor;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecPanelSplitHelper$settingsHelper$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
            }
        });
        this.settingsHelper$delegate = lazy;
        Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecPanelSplitHelper$statusBarStateController$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
            }
        });
        this.statusBarStateController$delegate = lazy2;
        this.expansionListeners = new CopyOnWriteArrayList();
        PanelSlideEventHandler panelSlideEventHandler = new PanelSlideEventHandler(context, shadeExpansionStateManager, quickSettingsControllerImpl);
        panelSlideEventHandler.panelSlideEventCallback = new SecPanelSplitHelper$panelSlideEventHandler$1$1(this);
        this.panelSlideEventHandler = panelSlideEventHandler;
        this.panelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
        this.currentState = 3;
        this.stateToChange = 1;
        ((SettingsHelper) lazy.getValue()).registerCallback(this, SPLIT_URI);
        boolean isPanelSplit = ((SettingsHelper) lazy.getValue()).isPanelSplit();
        panelSlideEventHandler.panelSplitEnabled = isPanelSplit;
        Lazy lazy3 = panelSlideEventHandler.statusBarStateController$delegate;
        Lazy lazy4 = panelSlideEventHandler.configurationController$delegate;
        ShadeExpansionStateManager shadeExpansionStateManager2 = panelSlideEventHandler.shadeExpansionStateManager;
        if (isPanelSplit) {
            shadeExpansionStateManager2.addExpansionListener(panelSlideEventHandler);
            ((ConfigurationControllerImpl) ((ConfigurationController) lazy4.getValue())).addCallback(panelSlideEventHandler);
            ((StatusBarStateController) lazy3.getValue()).addCallback(panelSlideEventHandler);
        } else {
            shadeExpansionStateManager2.removeExpansionListener(panelSlideEventHandler);
            ((ConfigurationControllerImpl) ((ConfigurationController) lazy4.getValue())).removeCallback(panelSlideEventHandler);
            ((StatusBarStateController) lazy3.getValue()).removeCallback(panelSlideEventHandler);
        }
        setEnabled$2(isPanelSplit);
        lockscreenShadeTransitionController.addCallback(this);
        ShadeRepositoryImpl shadeRepositoryImpl = (ShadeRepositoryImpl) shadeRepository;
        FlowKt.launchIn(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(shadeRepositoryImpl.lockscreenShadeExpansion, shadeRepositoryImpl.legacyShadeExpansion, new SecPanelSplitHelper$2$1(this, null))), coroutineScope);
        shadeExpansionStateManager.addExpansionListener(this);
        ((StatusBarStateController) lazy2.getValue()).addCallback(this);
        quickSettingsControllerImpl.mNotificationStackScrollLayoutController.mPanelSplitHelper = this;
        this.userChanged = new UserTracker.Callback() { // from class: com.android.systemui.shade.SecPanelSplitHelper$userChanged$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                SecPanelSplitHelper secPanelSplitHelper = SecPanelSplitHelper.this;
                SecQSImplAnimatorManager secQSImplAnimatorManager = secPanelSplitHelper.qsAnimatorManager;
                if (secQSImplAnimatorManager != null) {
                    secQSImplAnimatorManager.onUserSwitched(-2);
                }
                secPanelSplitHelper.onChanged(SecPanelSplitHelper.USER_CHANGED);
            }
        };
    }

    public static final boolean isEnabled() {
        Companion.getClass();
        return isEnabled;
    }

    public final void addListener(PanelTransitionStateListener panelTransitionStateListener) {
        this.expansionListeners.add(panelTransitionStateListener);
        PanelTransitionStateChangeEvent panelTransitionStateChangeEvent = new PanelTransitionStateChangeEvent(this.enabled, this.draggedFraction, this.currentState);
        Iterator it = this.expansionListeners.iterator();
        while (it.hasNext()) {
            ((PanelTransitionStateListener) it.next()).onPanelTransitionStateChanged(panelTransitionStateChangeEvent);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0023, code lost:
    
        if (r3 != 3) goto L49;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handleTouch(android.view.MotionEvent r12) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.SecPanelSplitHelper.handleTouch(android.view.MotionEvent):boolean");
    }

    public final boolean isQSState() {
        return this.currentState == 0;
    }

    public final boolean isShadeState() {
        return this.currentState == 1;
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        SecQSImplAnimatorManager secQSImplAnimatorManager;
        if (uri != null) {
            if (uri.equals(SPLIT_URI) || uri.equals(USER_CHANGED)) {
                if (!this.enabled) {
                    this.panelExpanded = QsAnimatorState.panelExpanded;
                }
                if (!uri.equals(USER_CHANGED) && ((!this.panelExpanded || QsAnimatorState.state == 1) && (secQSImplAnimatorManager = this.qsAnimatorManager) != null)) {
                    secQSImplAnimatorManager.onUserSwitched(-2);
                }
                setEnabled$2(((SettingsHelper) this.settingsHelper$delegate.getValue()).isPanelSplit());
                boolean z = this.enabled;
                Lazy lazy = this.statusBarStateController$delegate;
                ShadeExpansionStateManager shadeExpansionStateManager = this.shadeExpansionStateManager;
                if (!z) {
                    shadeExpansionStateManager.removeExpansionListener(this);
                    ((StatusBarStateController) lazy.getValue()).removeCallback(this);
                } else {
                    shadeExpansionStateManager.addExpansionListener(this);
                    ((StatusBarStateController) lazy.getValue()).addCallback(this);
                    this.quickSettingsControllerImpl.mNotificationStackScrollLayoutController.mPanelSplitHelper = this;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0057, code lost:
    
        if (r12 != 3) goto L201;
     */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x012d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onIntercept(android.view.MotionEvent r17) {
        /*
            Method dump skipped, instructions count: 702
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.SecPanelSplitHelper.onIntercept(android.view.MotionEvent):boolean");
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        boolean z = this.panelExpanded;
        boolean z2 = shadeExpansionChangeEvent.expanded;
        if (z2 == z) {
            return;
        }
        this.panelExpanded = z2;
        if (z2) {
            return;
        }
        int i = this.currentState;
        int i2 = PanelTransitionState.$r8$clinit;
        if (i != 3) {
            slide$1(1);
        }
        QsAnimatorState.isSliding = false;
        this.onceOverSlide = false;
        this.isOnceOverExpanded = false;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        if (i == 1) {
            this.quickSettingsControllerImpl.closeQs();
        } else if (i == 2 && this.currentState == 2) {
            slide$1(this.stateToChange);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStatePostChange() {
        View view;
        if (this.currentState != 0 || (view = this.shadeRootView) == null) {
            return;
        }
        view.setVisibility(4);
    }

    public final void removeListener(PanelTransitionStateListener panelTransitionStateListener) {
        this.expansionListeners.remove(panelTransitionStateListener);
    }

    public final void setDraggedFraction(float f) {
        this.draggedFraction = f;
        PanelTransitionStateChangeEvent panelTransitionStateChangeEvent = new PanelTransitionStateChangeEvent(this.enabled, f, this.currentState);
        Iterator it = this.expansionListeners.iterator();
        while (it.hasNext()) {
            ((PanelTransitionStateListener) it.next()).onPanelTransitionStateChanged(panelTransitionStateChangeEvent);
        }
    }

    public final void setEnabled$2(boolean z) {
        isEnabled = z;
        this.enabled = z;
        PanelTransitionStateChangeEvent panelTransitionStateChangeEvent = new PanelTransitionStateChangeEvent(z, this.draggedFraction, this.currentState);
        Iterator it = this.expansionListeners.iterator();
        while (it.hasNext()) {
            ((PanelTransitionStateListener) it.next()).onPanelTransitionStateChanged(panelTransitionStateChangeEvent);
        }
        EmergencyButtonController$$ExternalSyntheticOutline0.m("enabled = ", ", panelExpanded = ", "SecPanelSplitHelper", this.enabled, this.panelExpanded);
        boolean z2 = this.enabled;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.quickSettingsControllerImpl;
        if (z2) {
            slide$1((!this.panelExpanded || QsAnimatorState.state == 1) ? 1 : 0);
        } else {
            slide$1(3);
            ((ShadeRepositoryImpl) this.repository)._legacyExpandImmediate.updateState(null, Boolean.FALSE);
            QS qs = quickSettingsControllerImpl.mQs;
            if (qs != null) {
                qs.setOverScrollAmount(0);
            }
        }
        quickSettingsControllerImpl.mPanelSplitEnabled = z;
        PanelSlideEventHandler panelSlideEventHandler = this.panelSlideEventHandler;
        panelSlideEventHandler.panelSplitEnabled = z;
        Lazy lazy = panelSlideEventHandler.statusBarStateController$delegate;
        Lazy lazy2 = panelSlideEventHandler.configurationController$delegate;
        ShadeExpansionStateManager shadeExpansionStateManager = panelSlideEventHandler.shadeExpansionStateManager;
        if (z) {
            shadeExpansionStateManager.addExpansionListener(panelSlideEventHandler);
            ((ConfigurationControllerImpl) ((ConfigurationController) lazy2.getValue())).addCallback(panelSlideEventHandler);
            ((StatusBarStateController) lazy.getValue()).addCallback(panelSlideEventHandler);
        } else {
            shadeExpansionStateManager.removeExpansionListener(panelSlideEventHandler);
            ((ConfigurationControllerImpl) ((ConfigurationController) lazy2.getValue())).removeCallback(panelSlideEventHandler);
            ((StatusBarStateController) lazy.getValue()).removeCallback(panelSlideEventHandler);
        }
    }

    @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
    public final void setTransitionToFullShadeAmount(float f) {
        if (this.enabled && ((StatusBarStateController) this.statusBarStateController$delegate.getValue()).getState() == 1 && this.shouldQsDownInLockscreen) {
            slide(DeviceState.getScreenWidth(this.context) * f, PanelSlideEventHandler.Direction.DOWN, false);
        }
    }

    public final boolean shouldQSDown(MotionEvent motionEvent) {
        SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$1;
        SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$12;
        if (!this.enabled) {
            return false;
        }
        MotionEvent motionEvent2 = this.synthesizedActionDown;
        if (motionEvent2 != null) {
            motionEvent = motionEvent2;
        }
        PanelSlideEventHandler panelSlideEventHandler = this.panelSlideEventHandler;
        panelSlideEventHandler.getClass();
        if (motionEvent.getActionMasked() != 0) {
            return false;
        }
        int i = QsAnimatorState.state;
        SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$13 = panelSlideEventHandler.panelSlideEventCallback;
        Integer valueOf = secPanelSplitHelper$panelSlideEventHandler$1$13 != null ? Integer.valueOf(secPanelSplitHelper$panelSlideEventHandler$1$13.this$0.currentState) : null;
        Log.d("SecPanelSplitHelper", "shouldQSDown statusBarState = " + i + ", state = " + valueOf + " y = " + motionEvent.getY() + " statusBarHeight = " + SystemBarUtils.getStatusBarHeight(panelSlideEventHandler.context));
        if (((BaseHeadsUpManager) ((HeadsUpManager) panelSlideEventHandler.headsUpManager$delegate.getValue())).mHasPinnedNotification) {
            SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$14 = panelSlideEventHandler.panelSlideEventCallback;
            if (secPanelSplitHelper$panelSlideEventHandler$1$14 != null) {
                SecPanelSplitHelper secPanelSplitHelper = secPanelSplitHelper$panelSlideEventHandler$1$14.this$0;
                if (secPanelSplitHelper.currentState == 0 && secPanelSplitHelper$panelSlideEventHandler$1$14 != null) {
                    secPanelSplitHelper.slide$1(1);
                }
            }
        } else {
            if (motionEvent.getY() > SystemBarUtils.getStatusBarHeight(panelSlideEventHandler.context)) {
                SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$15 = panelSlideEventHandler.panelSlideEventCallback;
                if (secPanelSplitHelper$panelSlideEventHandler$1$15 != null) {
                    secPanelSplitHelper$panelSlideEventHandler$1$15.this$0.slide$1(1);
                }
                Log.d("SecPanelSplitHelper", "shouldQSDown y, SHADE_STATE return false");
                return false;
            }
            if (motionEvent.getX() < panelSlideEventHandler.displayWidthOfDivider) {
                SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$16 = panelSlideEventHandler.panelSlideEventCallback;
                if (secPanelSplitHelper$panelSlideEventHandler$1$16 != null) {
                    secPanelSplitHelper$panelSlideEventHandler$1$16.this$0.slide$1(1);
                }
                Log.d("SecPanelSplitHelper", "shouldQSDown else, SHADE_STATE return false");
                return false;
            }
            if ((QsAnimatorState.state != 2 || (secPanelSplitHelper$panelSlideEventHandler$1$12 = panelSlideEventHandler.panelSlideEventCallback) == null || secPanelSplitHelper$panelSlideEventHandler$1$12.this$0.currentState != 1) && (secPanelSplitHelper$panelSlideEventHandler$1$1 = panelSlideEventHandler.panelSlideEventCallback) != null) {
                secPanelSplitHelper$panelSlideEventHandler$1$1.this$0.slide$1(0);
            }
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(panelSlideEventHandler.displayWidthOfDivider, "shouldQSDown x, displayWidthOfDivider = ", ", QS_STATE return true", "SecPanelSplitHelper");
        }
        return true;
    }

    public final void slide(float f, PanelSlideEventHandler.Direction direction, boolean z) {
        if (QsAnimatorState.isDetailOpening || QsAnimatorState.isDetailShowing || QsAnimatorState.isDetailClosing || QsAnimatorState.isCustomizerShowing) {
            KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("can't slide : ", ", ", ", ", QsAnimatorState.isDetailOpening, QsAnimatorState.isDetailShowing), QsAnimatorState.isDetailClosing, ", ", QsAnimatorState.isCustomizerShowing, "SecPanelSplitHelper");
            return;
        }
        int i = this.stateOnDown;
        if (i == 0 ? f <= 0.0f : i == 1 && (direction != PanelSlideEventHandler.Direction.DOWN ? f >= 0.0f : f <= 0.0f)) {
            setDraggedFraction(0.0f);
            this.overSlideAmount = f;
        } else {
            setDraggedFraction(this.onceOverSlide ? 0.0f : RangesKt___RangesKt.coerceAtMost(DeviceState.getScreenWidth(this.context), RangesKt___RangesKt.coerceAtLeast(0.0f, Math.abs(f))) / DeviceState.getScreenWidth(this.context));
            this.overSlideAmount = 0.0f;
        }
        float f2 = this.overSlideAmount;
        if (f2 != 0.0f) {
            this.onceOverSlide = true;
        }
        Log.d("SecPanelSplitHelper", "slide ( " + f + ", " + direction + ", " + z + " ) draggedFraction : " + this.draggedFraction + " overSlideAmount : " + f2 + " onceOverSlide : " + this.onceOverSlide);
        float f3 = this.draggedFraction;
        if (f3 == 1.0f) {
            updateState(this.stateToChange);
            QsAnimatorState.isSliding = false;
            int i2 = this.currentState;
            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = this.panelSAStatusLogInteractor;
            if (secPanelSAStatusLogInteractor != null) {
                PanelSlideEventHandler.Direction direction2 = PanelSlideEventHandler.Direction.DOWN;
                SecPanelSAStatusLogRepository secPanelSAStatusLogRepository = secPanelSAStatusLogInteractor.repository;
                if (direction == direction2 && i2 == 0) {
                    StateFlowImpl stateFlowImpl = secPanelSAStatusLogRepository._openQuickPanelFromWipeDown;
                    stateFlowImpl.updateState(null, Long.valueOf(((Number) stateFlowImpl.getValue()).longValue() + 1));
                } else if (direction == PanelSlideEventHandler.Direction.LEFT && i2 == 0) {
                    StateFlowImpl stateFlowImpl2 = secPanelSAStatusLogRepository._openQuickPanelFromHorizontalSwiping;
                    stateFlowImpl2.updateState(null, Long.valueOf(((Number) stateFlowImpl2.getValue()).longValue() + 1));
                    StateFlowImpl stateFlowImpl3 = secPanelSAStatusLogRepository._horizontalSwipingToQuickPanel;
                    stateFlowImpl3.updateState(null, Long.valueOf(((Number) stateFlowImpl3.getValue()).longValue() + 1));
                } else if (direction == PanelSlideEventHandler.Direction.RIGHT && i2 == 1) {
                    StateFlowImpl stateFlowImpl4 = secPanelSAStatusLogRepository._horizontalSwipingToNotificationPanel;
                    stateFlowImpl4.updateState(null, Long.valueOf(((Number) stateFlowImpl4.getValue()).longValue() + 1));
                }
            }
        } else if (f3 == 0.0f) {
            updateState(this.stateOnDown);
            QsAnimatorState.isSliding = false;
        } else if (this.currentState != 2) {
            updateState(2);
            QsAnimatorState.isSliding = true;
        }
        if (Math.abs(this.overSlideAmount) == DeviceState.getScreenWidth(this.context) && !z) {
            springBack$1();
        }
        SecQSImplAnimatorManager secQSImplAnimatorManager = this.qsAnimatorManager;
        if (secQSImplAnimatorManager != null) {
            secQSImplAnimatorManager.slide(this.draggedFraction, this.overSlideAmount, direction, this.stateToChange);
        }
    }

    public final void slide$1(int i) {
        MWBixbyController$$ExternalSyntheticOutline0.m("slide ( ", PanelTransitionState.toString(i), " )\n", Debug.getCallers(10, " - "), "SecPanelSplitHelper");
        updateState(i);
        SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = this.panelSAStatusLogInteractor;
        if (secPanelSAStatusLogInteractor != null) {
            if (i != 0) {
                secPanelSAStatusLogInteractor = null;
            }
            if (secPanelSAStatusLogInteractor != null) {
                StateFlowImpl stateFlowImpl = secPanelSAStatusLogInteractor.repository._openQuickPanelFromWipeDown;
                stateFlowImpl.updateState(null, Long.valueOf(((Number) stateFlowImpl.getValue()).longValue() + 1));
            }
        }
        SecQSImplAnimatorManager secQSImplAnimatorManager = this.qsAnimatorManager;
        if (secQSImplAnimatorManager != null) {
            secQSImplAnimatorManager.slide(1.0f, 0.0f, PanelSlideEventHandler.Direction.DOWN, i);
        }
    }

    public final void springBack$1() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.overSlideAmount, 0.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.SecPanelSplitHelper$springBack$animator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SecPanelSplitHelper secPanelSplitHelper = SecPanelSplitHelper.this;
                SecQSImplAnimatorManager secQSImplAnimatorManager = secPanelSplitHelper.qsAnimatorManager;
                if (secQSImplAnimatorManager != null) {
                    secQSImplAnimatorManager.slide(0.0f, floatValue, null, secPanelSplitHelper.stateToChange);
                }
            }
        });
        ofFloat.setDuration((long) ((Math.abs(this.overSlideAmount) / DeviceState.getScreenWidth(this.context)) * 200));
        ofFloat.setInterpolator(new PathInterpolator(0.42f, 0.0f, 0.58f, 1.0f));
        ofFloat.start();
    }

    public final void updatePanelVisibility() {
        Log.d("SecPanelSplitHelper", "updatePanelVisibility ".concat(PanelTransitionState.toString(this.currentState)));
        int i = this.currentState;
        if (i == 0) {
            View view = this.qsScrollView;
            if (view != null) {
                view.setVisibility(0);
            }
            View view2 = this.shadeRootView;
            if (view2 == null) {
                return;
            }
            view2.setVisibility(4);
            return;
        }
        if (i != 1) {
            View view3 = this.qsScrollView;
            if (view3 != null) {
                view3.setVisibility(0);
            }
            View view4 = this.shadeRootView;
            if (view4 == null) {
                return;
            }
            view4.setVisibility(0);
            return;
        }
        View view5 = this.qsScrollView;
        if (view5 != null) {
            view5.setVisibility(4);
        }
        View view6 = this.shadeRootView;
        if (view6 == null) {
            return;
        }
        view6.setVisibility(0);
    }

    public final void updateState(int i) {
        if (i == this.currentState) {
            return;
        }
        Log.d("SecPanelSplitHelper", "updateState ".concat(PanelTransitionState.toString(i)));
        this.currentState = i;
        PanelTransitionStateChangeEvent panelTransitionStateChangeEvent = new PanelTransitionStateChangeEvent(this.enabled, this.draggedFraction, i);
        Iterator it = this.expansionListeners.iterator();
        while (it.hasNext()) {
            ((PanelTransitionStateListener) it.next()).onPanelTransitionStateChanged(panelTransitionStateChangeEvent);
        }
        updatePanelVisibility();
        this.stateToChange = this.stateOnDown == 0 ? 1 : 0;
        this.overSlideAmount = 0.0f;
    }
}
