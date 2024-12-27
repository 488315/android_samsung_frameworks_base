package com.android.systemui.statusbar.events;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.log.ScreenDecorationsLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateChangeEvent;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsChangedListener;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.leak.RotationUtils;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlinx.coroutines.CoroutineScope;

public class PrivacyDotViewController {
    public final SystemStatusAnimationScheduler animationScheduler;
    public boolean applyDelayNextViewState;
    public View bl;
    public View br;
    public Runnable cancelRunnable;
    public final ConfigurationController configurationController;
    public final StatusBarContentInsetsProvider contentInsetsProvider;
    public CreateListener createListener;
    public ViewState currentViewState;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final Object lock;
    public final Executor mainExecutor;
    public ViewState nextViewState;
    public final PrivacyLogger privacyLogger;
    public ShowingListener showingListener;
    public final StatusBarStateController stateController;
    public final PrivacyDotViewController$systemStatusAnimationCallback$1 systemStatusAnimationCallback;
    public View tl;
    public View tr;
    public DelayableExecutor uiExecutor;

    public interface CreateListener {
    }

    public interface ShowingListener {
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.events.PrivacyDotViewController$systemStatusAnimationCallback$1] */
    public PrivacyDotViewController(Executor executor, CoroutineScope coroutineScope, StatusBarStateController statusBarStateController, ConfigurationController configurationController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, SystemStatusAnimationScheduler systemStatusAnimationScheduler, ShadeInteractor shadeInteractor, IndicatorScaleGardener indicatorScaleGardener, PrivacyLogger privacyLogger, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor) {
        this.mainExecutor = executor;
        this.stateController = statusBarStateController;
        this.configurationController = configurationController;
        this.contentInsetsProvider = statusBarContentInsetsProvider;
        this.animationScheduler = systemStatusAnimationScheduler;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.privacyLogger = privacyLogger;
        ViewState viewState = new ViewState(false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, false, 524287, null);
        this.currentViewState = viewState;
        this.nextViewState = ViewState.copy$default(viewState, false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, 524287);
        this.lock = new Object();
        statusBarContentInsetsProvider.listeners.add(new StatusBarContentInsetsChangedListener() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController.1
            @Override // com.android.systemui.statusbar.phone.StatusBarContentInsetsChangedListener
            public final void onStatusBarContentInsetsChanged() {
                PrivacyDotViewController privacyDotViewController = PrivacyDotViewController.this;
                StatusBarContentInsetsProvider statusBarContentInsetsProvider2 = privacyDotViewController.contentInsetsProvider;
                List listOf = CollectionsKt__CollectionsKt.listOf(statusBarContentInsetsProvider2.getStatusBarContentAreaForRotation(3), statusBarContentInsetsProvider2.getStatusBarContentAreaForRotation(0), statusBarContentInsetsProvider2.getStatusBarContentAreaForRotation(1), statusBarContentInsetsProvider2.getStatusBarContentAreaForRotation(2));
                synchronized (privacyDotViewController.lock) {
                    privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, false, (Rect) listOf.get(1), (Rect) listOf.get(2), (Rect) listOf.get(3), (Rect) listOf.get(0), false, 0, 0, 0, null, null, 0, 0, 0, 0, 524047));
                    Unit unit = Unit.INSTANCE;
                }
            }
        });
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onLayoutDirectionChanged(final boolean z) {
                final PrivacyDotViewController privacyDotViewController = PrivacyDotViewController.this;
                DelayableExecutor delayableExecutor = privacyDotViewController.uiExecutor;
                if (delayableExecutor != null) {
                    delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController$2$onLayoutDirectionChanged$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            PrivacyDotViewController.this.setCornerVisibilities();
                            PrivacyDotViewController.AnonymousClass2 anonymousClass2 = this;
                            PrivacyDotViewController privacyDotViewController2 = PrivacyDotViewController.this;
                            boolean z2 = z;
                            synchronized (anonymousClass2) {
                                privacyDotViewController2.setNextViewState(ViewState.copy$default(privacyDotViewController2.nextViewState, false, false, false, false, null, null, null, null, z2, 0, 0, 0, privacyDotViewController2.selectDesignatedCorner(privacyDotViewController2.nextViewState.rotation, z2), null, 0, 0, 0, 0, 519935));
                                Unit unit = Unit.INSTANCE;
                            }
                        }
                    });
                }
            }
        });
        statusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController.3
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onExpandedChanged(boolean z) {
                PrivacyDotViewController.access$updateStatusBarState(PrivacyDotViewController.this);
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                PrivacyDotViewController.access$updateStatusBarState(PrivacyDotViewController.this);
            }
        });
        ((CopyOnWriteArrayList) secPanelExpansionStateInteractor.expansionStateListeners$delegate.getValue()).add(new SecPanelExpansionStateListener() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController.4
            @Override // com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener
            public final void onPanelExpansionStateChanged(SecPanelExpansionStateChangeEvent secPanelExpansionStateChangeEvent) {
                Object obj;
                boolean z = secPanelExpansionStateChangeEvent.panelExpansionState != 0;
                PrivacyDotViewController privacyDotViewController = PrivacyDotViewController.this;
                Object obj2 = privacyDotViewController.lock;
                synchronized (obj2) {
                    try {
                        obj = obj2;
                    } catch (Throwable th) {
                        th = th;
                        obj = obj2;
                    }
                    try {
                        privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, z, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, 524279));
                        Unit unit = Unit.INSTANCE;
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
            }
        });
        new SecPanelExpansionStateChangeEvent(((Number) secPanelExpansionStateInteractor.getRepository().panelState.$$delegate_0.getValue()).intValue());
        this.systemStatusAnimationCallback = new SystemStatusAnimationCallback() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController$systemStatusAnimationCallback$1
            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final void onHidePersistentDot(boolean z) {
                final PrivacyDotViewController privacyDotViewController;
                DelayableExecutor delayableExecutor;
                PrivacyDotViewController privacyDotViewController2 = PrivacyDotViewController.this;
                synchronized (privacyDotViewController2.lock) {
                    privacyDotViewController2.setNextViewState(ViewState.copy$default(privacyDotViewController2.nextViewState, false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, 524285));
                    Unit unit = Unit.INSTANCE;
                }
                if (!z || (delayableExecutor = (privacyDotViewController = PrivacyDotViewController.this).uiExecutor) == null) {
                    return;
                }
                delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController$systemStatusAnimationCallback$1$onHidePersistentDot$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        PrivacyDotViewController.CreateListener createListener = PrivacyDotViewController.this.createListener;
                        if (createListener != null) {
                            ScreenDecorations screenDecorations = ScreenDecorations.this;
                            screenDecorations.mIsDotViewVisible = false;
                            screenDecorations.setupDecorations();
                            ScreenDecorationsLogger screenDecorationsLogger = screenDecorations.mLogger;
                            screenDecorationsLogger.getClass();
                            screenDecorationsLogger.logBuffer.log("ScreenDecorationsLog", LogLevel.DEBUG, "onRemovePrivacyIndicatorOverlay", null);
                        }
                    }
                });
            }

            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final void onSystemStatusAnimationTransitionToPersistentDot(String str) {
                final PrivacyDotViewController privacyDotViewController = PrivacyDotViewController.this;
                DelayableExecutor delayableExecutor = privacyDotViewController.uiExecutor;
                if (delayableExecutor != null) {
                    delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController$systemStatusAnimationCallback$1$onSystemStatusAnimationTransitionToPersistentDot$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            PrivacyDotViewController.CreateListener createListener = PrivacyDotViewController.this.createListener;
                            if (createListener != null) {
                                ScreenDecorations screenDecorations = ScreenDecorations.this;
                                ScreenDecorationsLogger screenDecorationsLogger = screenDecorations.mLogger;
                                boolean z = screenDecorations.mIsDotViewVisible;
                                screenDecorationsLogger.getClass();
                                screenDecorationsLogger.logBuffer.log("ScreenDecorationsLog", LogLevel.DEBUG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("onCreatePrivacyIndicatorOverlay, already created=", z), null);
                                screenDecorations.mIsDotViewVisible = true;
                                screenDecorations.setupDecorations();
                            }
                        }
                    });
                }
                PrivacyDotViewController privacyDotViewController2 = PrivacyDotViewController.this;
                synchronized (privacyDotViewController2.lock) {
                    privacyDotViewController2.setNextViewState(ViewState.copy$default(privacyDotViewController2.nextViewState, false, true, false, false, null, null, null, null, false, 0, 0, 0, null, str, 0, 0, 0, 0, 516093));
                    Unit unit = Unit.INSTANCE;
                }
            }
        };
    }

    public static final void access$updateStatusBarState(PrivacyDotViewController privacyDotViewController) {
        synchronized (privacyDotViewController.lock) {
            ViewState viewState = privacyDotViewController.nextViewState;
            StatusBarStateController statusBarStateController = privacyDotViewController.stateController;
            privacyDotViewController.setNextViewState(ViewState.copy$default(viewState, false, false, (statusBarStateController.isExpanded() && statusBarStateController.getState() == 0) || statusBarStateController.getState() == 2, false, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, 524283));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final int activeRotationForCorner(View view, boolean z) {
        View view2 = this.tr;
        if (view2 == null) {
            view2 = null;
        }
        if (!Intrinsics.areEqual(view, view2)) {
            View view3 = this.tl;
            if (view3 == null) {
                view3 = null;
            }
            if (!Intrinsics.areEqual(view, view3)) {
                View view4 = this.br;
                if (Intrinsics.areEqual(view, view4 != null ? view4 : null)) {
                    if (z) {
                        return 2;
                    }
                } else if (!z) {
                    return 2;
                }
            } else if (z) {
                return 0;
            }
            return 3;
        }
        if (!z) {
            return 0;
        }
        return 1;
    }

    public final int cornerForView(View view) {
        View view2 = this.tl;
        if (view2 == null) {
            view2 = null;
        }
        if (Intrinsics.areEqual(view, view2)) {
            return 0;
        }
        View view3 = this.tr;
        if (view3 == null) {
            view3 = null;
        }
        if (Intrinsics.areEqual(view, view3)) {
            return 1;
        }
        View view4 = this.bl;
        if (view4 == null) {
            view4 = null;
        }
        if (Intrinsics.areEqual(view, view4)) {
            return 3;
        }
        View view5 = this.br;
        if (Intrinsics.areEqual(view, view5 != null ? view5 : null)) {
            return 2;
        }
        throw new IllegalArgumentException("not a corner view");
    }

    public final Sequence getViews() {
        View view = this.tl;
        if (view == null) {
            return SequencesKt__SequencesKt.sequenceOf(new View[0]);
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
        return SequencesKt__SequencesKt.sequenceOf(viewArr);
    }

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
        boolean isLayoutRtl = ((ConfigurationControllerImpl) this.configurationController).isLayoutRtl();
        View view9 = this.tl;
        View selectDesignatedCorner = selectDesignatedCorner(RotationUtils.getExactRotation((view9 != null ? view9 : null).getContext()), isLayoutRtl);
        int cornerForView = selectDesignatedCorner != null ? cornerForView(selectDesignatedCorner) : -1;
        Rect statusBarContentAreaForRotation = this.contentInsetsProvider.getStatusBarContentAreaForRotation(3);
        Rect statusBarContentAreaForRotation2 = this.contentInsetsProvider.getStatusBarContentAreaForRotation(0);
        Rect statusBarContentAreaForRotation3 = this.contentInsetsProvider.getStatusBarContentAreaForRotation(1);
        Rect statusBarContentAreaForRotation4 = this.contentInsetsProvider.getStatusBarContentAreaForRotation(2);
        int statusBarPaddingTop = this.contentInsetsProvider.getStatusBarPaddingTop();
        this.currentViewState = new ViewState(false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, false, 524287, null);
        synchronized (this.lock) {
            setNextViewState(ViewState.copy$default(this.nextViewState, true, false, false, false, statusBarContentAreaForRotation2, statusBarContentAreaForRotation3, statusBarContentAreaForRotation4, statusBarContentAreaForRotation, isLayoutRtl, 0, statusBarPaddingTop, cornerForView, selectDesignatedCorner, null, 0, 0, 0, 0, 516622));
            Unit unit = Unit.INSTANCE;
        }
    }

    public int innerGravity(int i) {
        if (i == 0) {
            return 21;
        }
        if (i == 1 || i == 2) {
            return 19;
        }
        if (i == 3) {
            return 21;
        }
        throw new IllegalArgumentException("Not a corner");
    }

    public boolean needsLayout(ViewState viewState, ViewState viewState2) {
        return (viewState.rotation == viewState2.rotation && viewState.layoutRtl == viewState2.layoutRtl && Intrinsics.areEqual(viewState.portraitRect, viewState2.portraitRect) && Intrinsics.areEqual(viewState.landscapeRect, viewState2.landscapeRect) && Intrinsics.areEqual(viewState.upsideDownRect, viewState2.upsideDownRect) && Intrinsics.areEqual(viewState.seascapeRect, viewState2.seascapeRect) && viewState.statusBarPaddingLeft == viewState2.statusBarPaddingLeft && viewState.statusBarPaddingRight == viewState2.statusBarPaddingRight && viewState.stableInsetLeft == viewState2.stableInsetLeft && viewState.stableInsetRight == viewState2.stableInsetRight && viewState.paddingTop == viewState2.paddingTop) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0019, code lost:
    
        if (r0 != null) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x001c, code lost:
    
        r0 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:?, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0042, code lost:
    
        if (r0 != null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View selectDesignatedCorner(int r4, boolean r5) {
        /*
            r3 = this;
            android.view.View r0 = r3.tl
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            if (r4 == 0) goto L40
            r2 = 1
            if (r4 == r2) goto L34
            r2 = 2
            if (r4 == r2) goto L28
            r2 = 3
            if (r4 != r2) goto L1f
            if (r5 == 0) goto L19
            android.view.View r3 = r3.bl
            if (r3 == 0) goto L4a
        L17:
            r1 = r3
            goto L4a
        L19:
            if (r0 == 0) goto L1c
            goto L1d
        L1c:
            r0 = r1
        L1d:
            r1 = r0
            goto L4a
        L1f:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "unknown rotation"
            r3.<init>(r4)
            throw r3
        L28:
            if (r5 == 0) goto L2f
            android.view.View r3 = r3.br
            if (r3 == 0) goto L4a
            goto L17
        L2f:
            android.view.View r3 = r3.bl
            if (r3 == 0) goto L4a
            goto L17
        L34:
            if (r5 == 0) goto L3b
            android.view.View r3 = r3.tr
            if (r3 == 0) goto L4a
            goto L17
        L3b:
            android.view.View r3 = r3.br
            if (r3 == 0) goto L4a
            goto L17
        L40:
            if (r5 == 0) goto L45
            if (r0 == 0) goto L1c
            goto L1d
        L45:
            android.view.View r3 = r3.tr
            if (r3 == 0) goto L4a
            goto L17
        L4a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.events.PrivacyDotViewController.selectDesignatedCorner(int, boolean):android.view.View");
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
        int roundToInt = MathKt__MathJVMKt.roundToInt(view4.getContext().getResources().getDimensionPixelSize(R.dimen.privacy_dot_margin_start) * f);
        View view5 = this.tl;
        if (view5 == null) {
            view5 = null;
        }
        int roundToInt2 = MathKt__MathJVMKt.roundToInt(view5.getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_appops_dot_diameter) * f);
        Iterator it = getViews().iterator();
        while (it.hasNext()) {
            View findViewById = ((View) it.next()).findViewById(R.id.privacy_dot);
            if (findViewById != null && (layoutParams = findViewById.getLayoutParams()) != null) {
                layoutParams.width = roundToInt2;
                layoutParams.height = roundToInt2;
            }
        }
        View view6 = this.tl;
        if (view6 == null) {
            view6 = null;
        }
        boolean z = viewState.layoutRtl;
        Rect contentRectForRotation = viewState.contentRectForRotation(activeRotationForCorner(view6, z));
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
        layoutParams2.topMargin = contentRectForRotation.top;
        layoutParams2.height = contentRectForRotation.height();
        int i2 = viewState.statusBarPaddingRight;
        int i3 = viewState.statusBarPaddingLeft;
        if (z) {
            layoutParams2.width = i3 - roundToInt;
        } else {
            layoutParams2.width = i2 - roundToInt;
        }
        View view9 = this.tr;
        if (view9 == null) {
            view9 = null;
        }
        Rect contentRectForRotation2 = viewState.contentRectForRotation(activeRotationForCorner(view9, z));
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
        layoutParams3.topMargin = contentRectForRotation2.top;
        layoutParams3.height = contentRectForRotation2.height();
        if (z) {
            layoutParams3.width = i3 - roundToInt;
        } else {
            layoutParams3.width = i2 - roundToInt;
        }
        View view12 = this.br;
        if (view12 == null) {
            view12 = null;
        }
        Rect contentRectForRotation3 = viewState.contentRectForRotation(activeRotationForCorner(view12, z));
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
        layoutParams4.topMargin = contentRectForRotation3.top;
        layoutParams4.height = contentRectForRotation3.height();
        if (z) {
            layoutParams4.width = i3 - roundToInt;
        } else {
            layoutParams4.width = (viewState.stableInsetRight + i2) - roundToInt;
        }
        View view15 = this.bl;
        if (view15 == null) {
            view15 = null;
        }
        Rect contentRectForRotation4 = viewState.contentRectForRotation(activeRotationForCorner(view15, z));
        View view16 = this.bl;
        if (view16 == null) {
            view16 = null;
        }
        view16.setPadding(0, i, 0, 0);
        View view17 = this.bl;
        FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) (view17 != null ? view17 : null).getLayoutParams();
        layoutParams5.topMargin = contentRectForRotation4.top;
        layoutParams5.height = contentRectForRotation4.height();
        if (z) {
            layoutParams5.width = (i3 + viewState.stableInsetLeft) - roundToInt;
        } else {
            layoutParams5.width = i2 - roundToInt;
        }
    }

    public final void setCornerVisibilities() {
        for (View view : getViews()) {
            view.setVisibility(4);
            ShowingListener showingListener = this.showingListener;
            if (showingListener != null) {
                ScreenDecorations.this.updateOverlayWindowVisibilityIfViewExists(view);
            }
        }
    }

    public final void setNewRotation(int i) {
        Object obj;
        synchronized (this.lock) {
            ViewState viewState = this.nextViewState;
            if (i == viewState.rotation) {
                return;
            }
            boolean z = viewState.layoutRtl;
            Unit unit = Unit.INSTANCE;
            setCornerVisibilities();
            View selectDesignatedCorner = selectDesignatedCorner(i, z);
            int cornerForView = selectDesignatedCorner != null ? cornerForView(selectDesignatedCorner) : -1;
            int statusBarPaddingTop = this.contentInsetsProvider.getStatusBarPaddingTop();
            Object obj2 = this.lock;
            synchronized (obj2) {
                try {
                    obj = obj2;
                } catch (Throwable th) {
                    th = th;
                    obj = obj2;
                }
                try {
                    setNextViewState(ViewState.copy$default(this.nextViewState, false, false, false, false, null, null, null, null, false, i, statusBarPaddingTop, cornerForView, selectDesignatedCorner, null, 0, 0, 0, 0, 516607));
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
    }

    public final void setNextViewState(ViewState viewState) {
        Runnable runnable;
        if (this.nextViewState.rotation != viewState.rotation) {
            this.applyDelayNextViewState = true;
        }
        this.nextViewState = viewState;
        Runnable runnable2 = this.cancelRunnable;
        if (runnable2 != null) {
            runnable2.run();
        }
        DelayableExecutor delayableExecutor = this.uiExecutor;
        if (delayableExecutor != null) {
            runnable = delayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController$scheduleUpdate$1
                /* JADX WARN: Removed duplicated region for block: B:19:0x006e  */
                /* JADX WARN: Removed duplicated region for block: B:26:0x0090  */
                /* JADX WARN: Removed duplicated region for block: B:41:0x00f1  */
                /* JADX WARN: Removed duplicated region for block: B:53:0x014c  */
                /* JADX WARN: Removed duplicated region for block: B:57:0x00d3  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void run() {
                    /*
                        Method dump skipped, instructions count: 347
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.events.PrivacyDotViewController$scheduleUpdate$1.run():void");
                }
            }, this.applyDelayNextViewState ? 300L : 0L);
        } else {
            runnable = null;
        }
        this.cancelRunnable = runnable;
    }

    public boolean shouldShowDot(ViewState viewState) {
        return (!viewState.systemPrivacyEventIsActive || viewState.shadeExpanded || viewState.qsExpanded) ? false : true;
    }

    public void updateRotations(int i, int i2) {
        int i3;
        for (View view : getViews()) {
            view.setPadding(0, i2, 0, 0);
            int cornerForView = cornerForView(view) - i;
            if (cornerForView < 0) {
                cornerForView += 4;
            }
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            if (cornerForView == 0) {
                i3 = 51;
            } else if (cornerForView == 1) {
                i3 = 53;
            } else if (cornerForView == 2) {
                i3 = 85;
            } else {
                if (cornerForView != 3) {
                    throw new IllegalArgumentException("Not a corner");
                }
                i3 = 83;
            }
            layoutParams.gravity = i3;
            ((FrameLayout.LayoutParams) view.requireViewById(R.id.privacy_dot).getLayoutParams()).gravity = innerGravity(cornerForView);
        }
    }
}
