package com.android.systemui.statusbar.events;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import com.android.app.animation.Interpolators;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.ScreenDecorationsLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.privacy.logging.PrivacyLogger$$ExternalSyntheticLambda0;
import com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$$ExternalSyntheticOutline0;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateChangeEvent;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsChangedListener;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProviderImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.leak.RotationUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public class PrivacyDotViewControllerImpl implements PrivacyDotViewController {
    public final SystemStatusAnimationScheduler animationScheduler;
    public boolean applyDelayNextViewState;
    public View bl;
    public View br;
    public Runnable cancelRunnable;
    public final ConfigurationController configurationController;
    public final StatusBarContentInsetsProvider contentInsetsProvider;
    public PrivacyDotViewController.CreateListener createListener;
    public ViewState currentViewState;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final Object lock;
    public final Executor mainExecutor;
    public ViewState nextViewState;
    public final PrivacyLogger privacyLogger;
    public final StatusBarStateController stateController;
    public final PrivacyDotViewControllerImpl$systemStatusAnimationCallback$1 systemStatusAnimationCallback;
    public View tl;
    public View tr;
    public final DelayableExecutor uiExecutor;
    public final PrivacyDotViewControllerImpl$wakefulnessObserver$1 wakefulnessObserver;

    public interface Factory {
        PrivacyDotViewControllerImpl create(CoroutineScope coroutineScope, ConfigurationController configurationController, StatusBarContentInsetsProvider statusBarContentInsetsProvider);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$systemStatusAnimationCallback$1] */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$wakefulnessObserver$1, java.lang.Object] */
    public PrivacyDotViewControllerImpl(Executor executor, CoroutineScope coroutineScope, StatusBarStateController statusBarStateController, ConfigurationController configurationController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, SystemStatusAnimationScheduler systemStatusAnimationScheduler, ShadeInteractor shadeInteractor, DelayableExecutor delayableExecutor, IndicatorScaleGardener indicatorScaleGardener, PrivacyLogger privacyLogger, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor, WakefulnessLifecycle wakefulnessLifecycle) {
        this.mainExecutor = executor;
        this.stateController = statusBarStateController;
        this.configurationController = configurationController;
        this.contentInsetsProvider = statusBarContentInsetsProvider;
        this.animationScheduler = systemStatusAnimationScheduler;
        this.uiExecutor = delayableExecutor;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.privacyLogger = privacyLogger;
        ViewState viewState = new ViewState(false, false, false, false, null, null, null, null, false, 0, 0, null, null, null, 0, 0, 0, 0, false, null, 1048575, null);
        this.currentViewState = viewState;
        this.nextViewState = ViewState.copy$default(viewState, false, false, false, null, null, null, null, false, 0, 0, null, null, null, 0, 0, 0, 0, null, 1048575);
        this.lock = new Object();
        StatusBarContentInsetsChangedListener statusBarContentInsetsChangedListener = new StatusBarContentInsetsChangedListener() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$insetsChangedListener$1
            @Override // com.android.systemui.statusbar.layout.StatusBarContentInsetsChangedListener
            public final void onStatusBarContentInsetsChanged() {
                PrivacyDotViewControllerImpl privacyDotViewControllerImpl = this.this$0;
                StatusBarContentInsetsProviderImpl statusBarContentInsetsProviderImpl = (StatusBarContentInsetsProviderImpl) privacyDotViewControllerImpl.contentInsetsProvider;
                List listAsList = Arrays.asList(statusBarContentInsetsProviderImpl.getStatusBarContentAreaForRotation(3), statusBarContentInsetsProviderImpl.getStatusBarContentAreaForRotation(0), statusBarContentInsetsProviderImpl.getStatusBarContentAreaForRotation(1), statusBarContentInsetsProviderImpl.getStatusBarContentAreaForRotation(2));
                synchronized (privacyDotViewControllerImpl.lock) {
                    privacyDotViewControllerImpl.setNextViewState(ViewState.copy$default(privacyDotViewControllerImpl.nextViewState, false, false, false, (Rect) listAsList.get(1), (Rect) listAsList.get(2), (Rect) listAsList.get(3), (Rect) listAsList.get(0), false, 0, 0, null, null, null, 0, 0, 0, 0, null, 1048335));
                    Unit unit = Unit.INSTANCE;
                }
            }
        };
        ?? r5 = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$wakefulnessObserver$1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                final PrivacyDotViewControllerImpl privacyDotViewControllerImpl = this.this$0;
                privacyDotViewControllerImpl.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$wakefulnessObserver$1$onStartedWakingUp$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PrivacyDotViewControllerImpl privacyDotViewControllerImpl2 = privacyDotViewControllerImpl;
                        if (privacyDotViewControllerImpl2.shouldShowDot(privacyDotViewControllerImpl2.currentViewState)) {
                            PrivacyDotViewControllerImpl privacyDotViewControllerImpl3 = privacyDotViewControllerImpl;
                            View view = privacyDotViewControllerImpl3.currentViewState.designatedCorner;
                            if (view != null) {
                                privacyDotViewControllerImpl3.showDotView(view);
                            }
                        }
                    }
                });
            }
        };
        this.wakefulnessObserver = r5;
        ConfigurationController.ConfigurationListener configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onLayoutDirectionChanged(final boolean z) {
                final PrivacyDotViewControllerImpl privacyDotViewControllerImpl = this.this$0;
                privacyDotViewControllerImpl.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$configurationListener$1$onLayoutDirectionChanged$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        privacyDotViewControllerImpl.setCornerVisibilities();
                        PrivacyDotViewControllerImpl$configurationListener$1 privacyDotViewControllerImpl$configurationListener$1 = this;
                        PrivacyDotViewControllerImpl privacyDotViewControllerImpl2 = privacyDotViewControllerImpl;
                        boolean z2 = z;
                        synchronized (privacyDotViewControllerImpl$configurationListener$1) {
                            privacyDotViewControllerImpl2.setNextViewState(ViewState.copy$default(privacyDotViewControllerImpl2.nextViewState, false, false, false, null, null, null, null, z2, 0, 0, null, privacyDotViewControllerImpl2.selectDesignatedCorner(privacyDotViewControllerImpl2.nextViewState.rotation, z2), null, 0, 0, 0, 0, null, 1044223));
                            Unit unit = Unit.INSTANCE;
                        }
                    }
                });
            }
        };
        StatusBarStateController.StateListener stateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$statusBarStateListener$1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onExpandedChanged(boolean z) {
                PrivacyDotViewControllerImpl.access$updateStatusBarState(this.this$0);
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                PrivacyDotViewControllerImpl.access$updateStatusBarState(this.this$0);
            }
        };
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
            wakefulnessLifecycle.addObserver(r5);
        }
        ((StatusBarContentInsetsProviderImpl) statusBarContentInsetsProvider).addCallback(statusBarContentInsetsChangedListener);
        ((ConfigurationControllerImpl) configurationController).addCallback(configurationListener);
        statusBarStateController.addCallback(stateListener);
        secPanelExpansionStateInteractor.registerListener(new SecPanelExpansionStateListener() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl.1
            @Override // com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener
            public final void onPanelExpansionStateChanged(SecPanelExpansionStateChangeEvent secPanelExpansionStateChangeEvent) {
                boolean z = secPanelExpansionStateChangeEvent.panelExpansionState != 0;
                PrivacyDotViewControllerImpl privacyDotViewControllerImpl = PrivacyDotViewControllerImpl.this;
                synchronized (privacyDotViewControllerImpl.lock) {
                    privacyDotViewControllerImpl.setNextViewState(ViewState.copy$default(privacyDotViewControllerImpl.nextViewState, false, false, z, null, null, null, null, false, 0, 0, null, null, null, 0, 0, 0, 0, null, 1048567));
                    Unit unit = Unit.INSTANCE;
                }
            }
        });
        this.systemStatusAnimationCallback = new SystemStatusAnimationCallback() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$systemStatusAnimationCallback$1
            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final void onHidePersistentDot(boolean z) {
                final PrivacyDotViewControllerImpl privacyDotViewControllerImpl;
                DelayableExecutor delayableExecutor2;
                PrivacyDotViewControllerImpl privacyDotViewControllerImpl2 = this.this$0;
                synchronized (privacyDotViewControllerImpl2.lock) {
                    privacyDotViewControllerImpl2.setNextViewState(ViewState.copy$default(privacyDotViewControllerImpl2.nextViewState, false, false, false, null, null, null, null, false, 0, 0, null, null, null, 0, 0, 0, 0, null, 1048573));
                    Unit unit = Unit.INSTANCE;
                }
                if (!z || (delayableExecutor2 = (privacyDotViewControllerImpl = this.this$0).uiExecutor) == null) {
                    return;
                }
                delayableExecutor2.execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$systemStatusAnimationCallback$1$onHidePersistentDot$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        PrivacyDotViewController.CreateListener createListener = privacyDotViewControllerImpl.createListener;
                        if (createListener != null) {
                            ScreenDecorations screenDecorations = ScreenDecorations.this;
                            screenDecorations.mIsDotViewVisible = false;
                            screenDecorations.setupDecorations();
                            ScreenDecorationsLogger screenDecorationsLogger = screenDecorations.mLogger;
                            screenDecorationsLogger.getClass();
                            LogBuffer.log$default(screenDecorationsLogger.logBuffer, "ScreenDecorationsLog", LogLevel.DEBUG, "onRemovePrivacyIndicatorOverlay");
                        }
                    }
                });
            }

            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final void onSystemStatusAnimationTransitionToPersistentDot(String str) {
                final PrivacyDotViewControllerImpl privacyDotViewControllerImpl = this.this$0;
                DelayableExecutor delayableExecutor2 = privacyDotViewControllerImpl.uiExecutor;
                if (delayableExecutor2 != null) {
                    delayableExecutor2.execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$systemStatusAnimationCallback$1$onSystemStatusAnimationTransitionToPersistentDot$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            PrivacyDotViewController.CreateListener createListener = privacyDotViewControllerImpl.createListener;
                            if (createListener != null) {
                                ScreenDecorations screenDecorations = ScreenDecorations.this;
                                ScreenDecorationsLogger screenDecorationsLogger = screenDecorations.mLogger;
                                boolean z = screenDecorations.mIsDotViewVisible;
                                screenDecorationsLogger.getClass();
                                LogBuffer.log$default(screenDecorationsLogger.logBuffer, "ScreenDecorationsLog", LogLevel.DEBUG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("onCreatePrivacyIndicatorOverlay, already created=", z));
                                screenDecorations.mIsDotViewVisible = true;
                                screenDecorations.setupDecorations();
                            }
                        }
                    });
                }
                PrivacyDotViewControllerImpl privacyDotViewControllerImpl2 = this.this$0;
                synchronized (privacyDotViewControllerImpl2.lock) {
                    privacyDotViewControllerImpl2.setNextViewState(ViewState.copy$default(privacyDotViewControllerImpl2.nextViewState, true, false, false, null, null, null, null, false, 0, 0, null, null, str, 0, 0, 0, 0, null, 1040381));
                    Unit unit = Unit.INSTANCE;
                }
            }
        };
    }

    public static final void access$updateStatusBarState(PrivacyDotViewControllerImpl privacyDotViewControllerImpl) {
        synchronized (privacyDotViewControllerImpl.lock) {
            ViewState viewState = privacyDotViewControllerImpl.nextViewState;
            StatusBarStateController statusBarStateController = privacyDotViewControllerImpl.stateController;
            privacyDotViewControllerImpl.setNextViewState(ViewState.copy$default(viewState, false, (statusBarStateController.isExpanded() && statusBarStateController.getState() == 0) || statusBarStateController.getState() == 2, false, null, null, null, null, false, 0, 0, null, null, null, 0, 0, 0, 0, null, 1048571));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final int activeRotationForCorner(View view, boolean z) {
        View view2 = this.tr;
        if (view2 == null) {
            view2 = null;
        }
        if (Intrinsics.areEqual(view, view2)) {
            return z ? 1 : 0;
        }
        View view3 = this.tl;
        if (view3 == null) {
            view3 = null;
        }
        if (Intrinsics.areEqual(view, view3)) {
            return z ? 0 : 3;
        }
        View view4 = this.br;
        return Intrinsics.areEqual(view, view4 != null ? view4 : null) ? z ? 2 : 1 : z ? 3 : 2;
    }

    public final PrivacyDotCorner cornerForView(View view) {
        View view2 = this.tl;
        if (view2 == null) {
            view2 = null;
        }
        if (Intrinsics.areEqual(view, view2)) {
            return PrivacyDotCorner.TopLeft;
        }
        View view3 = this.tr;
        if (view3 == null) {
            view3 = null;
        }
        if (Intrinsics.areEqual(view, view3)) {
            return PrivacyDotCorner.TopRight;
        }
        View view4 = this.bl;
        if (view4 == null) {
            view4 = null;
        }
        if (Intrinsics.areEqual(view, view4)) {
            return PrivacyDotCorner.BottomLeft;
        }
        View view5 = this.br;
        if (Intrinsics.areEqual(view, view5 != null ? view5 : null)) {
            return PrivacyDotCorner.BottomRight;
        }
        throw new IllegalArgumentException("not a corner view");
    }

    public final Sequence getViews() {
        View view = this.tl;
        if (view == null) {
            return ArraysKt___ArraysKt.asSequence(new View[0]);
        }
        View[] viewArr = new View[4];
        if (view == null) {
            view = null;
        }
        viewArr[0] = view;
        View view2 = this.tr;
        if (view2 == null) {
            view2 = null;
        }
        viewArr[1] = view2;
        View view3 = this.br;
        if (view3 == null) {
            view3 = null;
        }
        viewArr[2] = view3;
        View view4 = this.bl;
        viewArr[3] = view4 != null ? view4 : null;
        return ArraysKt___ArraysKt.asSequence(viewArr);
    }

    @Override // com.android.systemui.statusbar.events.PrivacyDotViewController
    public void initialize(View view, View view2, View view3, View view4) {
        View view5 = this.tl;
        if (view5 != null && this.tr != null && this.bl != null && this.br != null && Intrinsics.areEqual(view5, view)) {
            View view6 = this.tr;
            if (view6 == null) {
                view6 = null;
            }
            if (Intrinsics.areEqual(view6, view2)) {
                View view7 = this.bl;
                if (view7 == null) {
                    view7 = null;
                }
                if (Intrinsics.areEqual(view7, view3)) {
                    View view8 = this.br;
                    if (view8 == null) {
                        view8 = null;
                    }
                    if (Intrinsics.areEqual(view8, view4)) {
                        return;
                    }
                }
            }
        }
        this.tl = view;
        this.tr = view2;
        this.bl = view3;
        this.br = view4;
        boolean zIsLayoutRtl = ((ConfigurationControllerImpl) this.configurationController).isLayoutRtl();
        View view9 = this.tl;
        if (view9 == null) {
            view9 = null;
        }
        View viewSelectDesignatedCorner = selectDesignatedCorner(RotationUtils.getExactRotation(view9.getContext()), zIsLayoutRtl);
        PrivacyDotCorner privacyDotCornerCornerForView = viewSelectDesignatedCorner != null ? cornerForView(viewSelectDesignatedCorner) : null;
        Rect statusBarContentAreaForRotation = ((StatusBarContentInsetsProviderImpl) this.contentInsetsProvider).getStatusBarContentAreaForRotation(3);
        Rect statusBarContentAreaForRotation2 = ((StatusBarContentInsetsProviderImpl) this.contentInsetsProvider).getStatusBarContentAreaForRotation(0);
        Rect statusBarContentAreaForRotation3 = ((StatusBarContentInsetsProviderImpl) this.contentInsetsProvider).getStatusBarContentAreaForRotation(1);
        Rect statusBarContentAreaForRotation4 = ((StatusBarContentInsetsProviderImpl) this.contentInsetsProvider).getStatusBarContentAreaForRotation(2);
        int statusBarPaddingTop = ((StatusBarContentInsetsProviderImpl) this.contentInsetsProvider).getStatusBarPaddingTop();
        this.currentViewState = new ViewState(false, false, false, false, null, null, null, null, false, 0, 0, null, null, null, 0, 0, 0, 0, false, null, 1048575, null);
        synchronized (this.lock) {
            setNextViewState(ViewState.copy$default(this.nextViewState, false, false, false, statusBarContentAreaForRotation2, statusBarContentAreaForRotation3, statusBarContentAreaForRotation4, statusBarContentAreaForRotation, zIsLayoutRtl, 0, statusBarPaddingTop, privacyDotCornerCornerForView, viewSelectDesignatedCorner, null, 0, 0, 0, 0, null, 1040910));
            Unit unit = Unit.INSTANCE;
        }
    }

    public boolean needsLayout(ViewState viewState, ViewState viewState2) {
        return (viewState.rotation == viewState2.rotation && viewState.layoutRtl == viewState2.layoutRtl && Intrinsics.areEqual(viewState.portraitRect, viewState2.portraitRect) && Intrinsics.areEqual(viewState.landscapeRect, viewState2.landscapeRect) && Intrinsics.areEqual(viewState.upsideDownRect, viewState2.upsideDownRect) && Intrinsics.areEqual(viewState.seascapeRect, viewState2.seascapeRect) && viewState.statusBarPaddingLeft == viewState2.statusBarPaddingLeft && viewState.statusBarPaddingRight == viewState2.statusBarPaddingRight && viewState.stableInsetLeft == viewState2.stableInsetLeft && viewState.stableInsetRight == viewState2.stableInsetRight && viewState.paddingTop == viewState2.paddingTop && Intrinsics.areEqual(viewState.displaySize, viewState2.displaySize)) ? false : true;
    }

    public final View selectDesignatedCorner(int i, boolean z) {
        View view = this.tl;
        if (view == null) {
            return null;
        }
        if (i == 0) {
            if (z) {
                if (view != null) {
                    return view;
                }
                return null;
            }
            View view2 = this.tr;
            if (view2 != null) {
                return view2;
            }
            return null;
        }
        if (i == 1) {
            if (z) {
                View view3 = this.tr;
                if (view3 != null) {
                    return view3;
                }
                return null;
            }
            View view4 = this.br;
            if (view4 != null) {
                return view4;
            }
            return null;
        }
        if (i == 2) {
            if (z) {
                View view5 = this.br;
                if (view5 != null) {
                    return view5;
                }
                return null;
            }
            View view6 = this.bl;
            if (view6 != null) {
                return view6;
            }
            return null;
        }
        if (i != 3) {
            throw new IllegalStateException("unknown rotation");
        }
        if (!z) {
            if (view != null) {
                return view;
            }
            return null;
        }
        View view7 = this.bl;
        if (view7 != null) {
            return view7;
        }
        return null;
    }

    public void setCornerSizes(ViewState viewState) {
        ViewGroup.LayoutParams layoutParams;
        Point point = new Point();
        View view = this.tl;
        if (view == null) {
            view = null;
        }
        Display display = view.getContext().getDisplay();
        if (display != null) {
            display.getRealSize(point);
        }
        View view2 = this.tl;
        if (view2 == null) {
            view2 = null;
        }
        RotationUtils.getExactRotation(view2.getContext());
        View view3 = this.tl;
        if (view3 == null) {
            view3 = null;
        }
        float f = this.indicatorScaleGardener.getLatestScaleModel(view3.getContext()).ratio;
        View view4 = this.tl;
        if (view4 == null) {
            view4 = null;
        }
        int iRoundToInt = MathKt__MathJVMKt.roundToInt(QSLayoutEditViewController$$ExternalSyntheticOutline0.m(view4, R.dimen.privacy_dot_margin_start) * f);
        View view5 = this.tl;
        if (view5 == null) {
            view5 = null;
        }
        int iRoundToInt2 = MathKt__MathJVMKt.roundToInt(QSLayoutEditViewController$$ExternalSyntheticOutline0.m(view5, R.dimen.ongoing_appops_dot_diameter) * f);
        Iterator it = getViews().iterator();
        while (it.hasNext()) {
            View viewFindViewById = ((View) it.next()).findViewById(R.id.privacy_dot);
            if (viewFindViewById != null && (layoutParams = viewFindViewById.getLayoutParams()) != null) {
                layoutParams.width = iRoundToInt2;
                layoutParams.height = iRoundToInt2;
            }
        }
        View view6 = this.tl;
        if (view6 == null) {
            view6 = null;
        }
        boolean z = viewState.layoutRtl;
        Rect rectContentRectForRotation = viewState.contentRectForRotation(activeRotationForCorner(view6, z));
        View view7 = this.tl;
        if (view7 == null) {
            view7 = null;
        }
        int i = viewState.paddingTop;
        view7.setPadding(0, i, 0, 0);
        View view8 = this.tl;
        if (view8 == null) {
            view8 = null;
        }
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) view8.getLayoutParams();
        layoutParams2.topMargin = rectContentRectForRotation.top;
        layoutParams2.height = rectContentRectForRotation.height();
        int i2 = viewState.statusBarPaddingRight;
        int i3 = viewState.statusBarPaddingLeft;
        if (z) {
            layoutParams2.width = i3 - iRoundToInt;
        } else {
            layoutParams2.width = i2 - iRoundToInt;
        }
        View view9 = this.tr;
        if (view9 == null) {
            view9 = null;
        }
        Rect rectContentRectForRotation2 = viewState.contentRectForRotation(activeRotationForCorner(view9, z));
        View view10 = this.tr;
        if (view10 == null) {
            view10 = null;
        }
        view10.setPadding(0, i, 0, 0);
        View view11 = this.tr;
        if (view11 == null) {
            view11 = null;
        }
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) view11.getLayoutParams();
        layoutParams3.topMargin = rectContentRectForRotation2.top;
        layoutParams3.height = rectContentRectForRotation2.height();
        if (z) {
            layoutParams3.width = i3 - iRoundToInt;
        } else {
            layoutParams3.width = i2 - iRoundToInt;
        }
        View view12 = this.br;
        if (view12 == null) {
            view12 = null;
        }
        Rect rectContentRectForRotation3 = viewState.contentRectForRotation(activeRotationForCorner(view12, z));
        View view13 = this.br;
        if (view13 == null) {
            view13 = null;
        }
        view13.setPadding(0, i, 0, 0);
        View view14 = this.br;
        if (view14 == null) {
            view14 = null;
        }
        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) view14.getLayoutParams();
        layoutParams4.topMargin = rectContentRectForRotation3.top;
        layoutParams4.height = rectContentRectForRotation3.height();
        if (z) {
            layoutParams4.width = i3 - iRoundToInt;
        } else {
            layoutParams4.width = (viewState.stableInsetRight + i2) - iRoundToInt;
        }
        View view15 = this.bl;
        if (view15 == null) {
            view15 = null;
        }
        Rect rectContentRectForRotation4 = viewState.contentRectForRotation(activeRotationForCorner(view15, z));
        View view16 = this.bl;
        if (view16 == null) {
            view16 = null;
        }
        view16.setPadding(0, i, 0, 0);
        View view17 = this.bl;
        FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) (view17 != null ? view17 : null).getLayoutParams();
        layoutParams5.topMargin = rectContentRectForRotation4.top;
        layoutParams5.height = rectContentRectForRotation4.height();
        if (z) {
            layoutParams5.width = (i3 + viewState.stableInsetLeft) - iRoundToInt;
        } else {
            layoutParams5.width = i2 - iRoundToInt;
        }
    }

    public final void setCornerVisibilities() {
        Iterator it = getViews().iterator();
        while (it.hasNext()) {
            ((View) it.next()).setVisibility(4);
        }
    }

    public final void setNewRotation(int i) {
        synchronized (this.lock) {
            ViewState viewState = this.nextViewState;
            if (i == viewState.rotation) {
                return;
            }
            boolean z = viewState.layoutRtl;
            Unit unit = Unit.INSTANCE;
            setCornerVisibilities();
            View viewSelectDesignatedCorner = selectDesignatedCorner(i, z);
            PrivacyDotCorner privacyDotCornerCornerForView = viewSelectDesignatedCorner != null ? cornerForView(viewSelectDesignatedCorner) : null;
            int statusBarPaddingTop = ((StatusBarContentInsetsProviderImpl) this.contentInsetsProvider).getStatusBarPaddingTop();
            synchronized (this.lock) {
                setNextViewState(ViewState.copy$default(this.nextViewState, false, false, false, null, null, null, null, false, i, statusBarPaddingTop, privacyDotCornerCornerForView, viewSelectDesignatedCorner, null, 0, 0, 0, 0, null, 1040895));
            }
        }
    }

    public final void setNextViewState(ViewState viewState) {
        ViewState viewState2 = this.nextViewState;
        if (viewState2.rotation != viewState.rotation || !Intrinsics.areEqual(viewState2.displaySize, viewState.displaySize)) {
            this.applyDelayNextViewState = true;
        }
        this.nextViewState = viewState;
        Runnable runnable = this.cancelRunnable;
        if (runnable != null) {
            runnable.run();
        }
        this.cancelRunnable = this.uiExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$scheduleUpdate$1
            @Override // java.lang.Runnable
            public final void run() {
                ViewState viewStateCopy$default;
                View view;
                final View view2;
                View view3;
                final PrivacyDotViewControllerImpl privacyDotViewControllerImpl = this.this$0;
                if (privacyDotViewControllerImpl.applyDelayNextViewState) {
                    privacyDotViewControllerImpl.applyDelayNextViewState = false;
                }
                synchronized (privacyDotViewControllerImpl.lock) {
                    viewStateCopy$default = ViewState.copy$default(privacyDotViewControllerImpl.nextViewState, false, false, false, null, null, null, null, false, 0, 0, null, null, null, 0, 0, 0, 0, null, 1048575);
                    Unit unit = Unit.INSTANCE;
                }
                if (viewStateCopy$default.viewInitialized || (privacyDotViewControllerImpl instanceof CoverPrivacyDotViewController)) {
                    if (!viewStateCopy$default.equals(privacyDotViewControllerImpl.currentViewState) || (privacyDotViewControllerImpl instanceof CoverPrivacyDotViewController)) {
                        boolean z = (Intrinsics.areEqual(viewStateCopy$default.designatedCorner, privacyDotViewControllerImpl.currentViewState.designatedCorner) && Intrinsics.areEqual(viewStateCopy$default.displaySize, privacyDotViewControllerImpl.currentViewState.displaySize)) ? false : true;
                        int i = privacyDotViewControllerImpl.currentViewState.rotation;
                        int i2 = viewStateCopy$default.rotation;
                        if (i2 != i || z) {
                            privacyDotViewControllerImpl.updateRotations(i2, viewStateCopy$default.paddingTop);
                        }
                        if (privacyDotViewControllerImpl.needsLayout(viewStateCopy$default, privacyDotViewControllerImpl.currentViewState)) {
                            privacyDotViewControllerImpl.setCornerSizes(viewStateCopy$default);
                            Iterator it = privacyDotViewControllerImpl.getViews().iterator();
                            while (it.hasNext()) {
                                ((View) it.next()).requestLayout();
                            }
                        }
                        String str = viewStateCopy$default.contentDescription;
                        if (z) {
                            View view4 = privacyDotViewControllerImpl.currentViewState.designatedCorner;
                            if (view4 != null) {
                                view4.setContentDescription(null);
                            }
                            View view5 = viewStateCopy$default.designatedCorner;
                            if (view5 != null) {
                                view5.setContentDescription(str);
                            }
                            View view6 = viewStateCopy$default.designatedCorner;
                            if (privacyDotViewControllerImpl.shouldShowDot(viewStateCopy$default) && view6 != null) {
                                view6.clearAnimation();
                                view6.setVisibility(0);
                                view6.setAlpha(0.0f);
                                view6.animate().alpha(1.0f).setDuration(300L).start();
                            }
                        } else if (!Intrinsics.areEqual(str, privacyDotViewControllerImpl.currentViewState.contentDescription) && (view = viewStateCopy$default.designatedCorner) != null) {
                            view.setContentDescription(str);
                        }
                        boolean zShouldShowDot = privacyDotViewControllerImpl.shouldShowDot(viewStateCopy$default);
                        if (zShouldShowDot != privacyDotViewControllerImpl.shouldShowDot(privacyDotViewControllerImpl.currentViewState)) {
                            if (zShouldShowDot && (view3 = viewStateCopy$default.designatedCorner) != null) {
                                privacyDotViewControllerImpl.showDotView(view3);
                            } else if (!zShouldShowDot && (view2 = viewStateCopy$default.designatedCorner) != null) {
                                view2.clearAnimation();
                                view2.animate().setDuration(160L).setInterpolator(Interpolators.ALPHA_OUT).alpha(0.0f).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl$hideDotView$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        view2.setVisibility(4);
                                        privacyDotViewControllerImpl.getClass();
                                    }
                                }).start();
                            }
                        }
                        if (zShouldShowDot) {
                            String string = viewStateCopy$default.toString();
                            PrivacyLogger privacyLogger = privacyDotViewControllerImpl.privacyLogger;
                            privacyLogger.getClass();
                            LogLevel logLevel = LogLevel.INFO;
                            PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(0);
                            LogBuffer logBuffer = privacyLogger.buffer;
                            LogMessage logMessageObtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
                            ((LogMessageImpl) logMessageObtain).str1 = string;
                            logBuffer.commit(logMessageObtain);
                        }
                        privacyDotViewControllerImpl.currentViewState = viewStateCopy$default;
                    }
                }
            }
        }, this.applyDelayNextViewState ? 300L : 0L);
    }

    public boolean shouldShowDot(ViewState viewState) {
        return (!viewState.systemPrivacyEventIsActive || viewState.shadeExpanded || viewState.qsExpanded) ? false : true;
    }

    public final void showDotView(View view) {
        view.clearAnimation();
        view.setVisibility(0);
        view.setAlpha(0.0f);
        view.animate().alpha(1.0f).setDuration(160L).setInterpolator(Interpolators.ALPHA_IN).start();
    }

    public final void updateGarden(int i, int i2, WindowInsets windowInsets) {
        synchronized (this.lock) {
            setNextViewState(ViewState.copy$default(this.nextViewState, false, false, false, null, null, null, null, false, 0, ((StatusBarContentInsetsProviderImpl) this.contentInsetsProvider).getStatusBarPaddingTop(), null, null, null, i, i2, windowInsets.getInsets(WindowInsets.Type.systemBars()).left, windowInsets.getInsets(WindowInsets.Type.systemBars()).right, null, 801791));
            Unit unit = Unit.INSTANCE;
        }
    }

    public void updateRotations(int i, int i2) {
        for (View view : getViews()) {
            view.setPadding(0, i2, 0, 0);
            PrivacyDotCorner privacyDotCornerRotatedCorner = PrivacyDotCornerKt.rotatedCorner(cornerForView(view), i);
            ((FrameLayout.LayoutParams) view.getLayoutParams()).gravity = privacyDotCornerRotatedCorner.getGravity();
            ((FrameLayout.LayoutParams) view.requireViewById(R.id.privacy_dot).getLayoutParams()).gravity = privacyDotCornerRotatedCorner.getInnerGravity();
        }
    }
}
