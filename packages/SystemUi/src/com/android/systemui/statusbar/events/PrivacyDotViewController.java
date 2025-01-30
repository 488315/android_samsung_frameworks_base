package com.android.systemui.statusbar.events;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.animation.Animator;
import com.android.app.animation.Interpolators;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.R;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeQsExpansionListener;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsChangedListener;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.leak.RotationUtils;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PrivacyDotViewController {
    public final SystemStatusAnimationScheduler animationScheduler;
    public boolean applyDelayNextViewState;

    /* renamed from: bl */
    public View f347bl;

    /* renamed from: br */
    public View f348br;
    public ExecutorImpl.ExecutionToken cancelRunnable;
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

    /* renamed from: tl */
    public View f349tl;

    /* renamed from: tr */
    public View f350tr;
    public DelayableExecutor uiExecutor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface CreateListener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ShowingListener {
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.statusbar.events.PrivacyDotViewController$systemStatusAnimationCallback$1] */
    public PrivacyDotViewController(Executor executor, StatusBarStateController statusBarStateController, ConfigurationController configurationController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, SystemStatusAnimationScheduler systemStatusAnimationScheduler, ShadeExpansionStateManager shadeExpansionStateManager, IndicatorScaleGardener indicatorScaleGardener, PrivacyLogger privacyLogger) {
        this.mainExecutor = executor;
        this.stateController = statusBarStateController;
        this.configurationController = configurationController;
        this.contentInsetsProvider = statusBarContentInsetsProvider;
        this.animationScheduler = systemStatusAnimationScheduler;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.privacyLogger = privacyLogger;
        ViewState viewState = new ViewState(false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, 262143, null);
        this.currentViewState = viewState;
        this.nextViewState = ViewState.copy$default(viewState, false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, 262143);
        this.lock = new Object();
        statusBarContentInsetsProvider.listeners.add(new StatusBarContentInsetsChangedListener() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController.1
            @Override // com.android.systemui.statusbar.phone.StatusBarContentInsetsChangedListener
            public final void onStatusBarContentInsetsChanged() {
                PrivacyDotViewController privacyDotViewController = PrivacyDotViewController.this;
                StatusBarContentInsetsProvider statusBarContentInsetsProvider2 = privacyDotViewController.contentInsetsProvider;
                List listOf = CollectionsKt__CollectionsKt.listOf(statusBarContentInsetsProvider2.getStatusBarContentAreaForRotation(3), statusBarContentInsetsProvider2.getStatusBarContentAreaForRotation(0), statusBarContentInsetsProvider2.getStatusBarContentAreaForRotation(1), statusBarContentInsetsProvider2.getStatusBarContentAreaForRotation(2));
                synchronized (privacyDotViewController.lock) {
                    privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, false, (Rect) listOf.get(1), (Rect) listOf.get(2), (Rect) listOf.get(3), (Rect) listOf.get(0), false, 0, 0, 0, null, null, 0, 0, 0, 0, 261903));
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
                    ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController$2$onLayoutDirectionChanged$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            PrivacyDotViewController.this.setCornerVisibilities();
                            PrivacyDotViewController.C26372 c26372 = this;
                            PrivacyDotViewController privacyDotViewController2 = PrivacyDotViewController.this;
                            boolean z2 = z;
                            synchronized (c26372) {
                                privacyDotViewController2.setNextViewState(ViewState.copy$default(privacyDotViewController2.nextViewState, false, false, false, false, null, null, null, null, z2, 0, 0, 0, privacyDotViewController2.selectDesignatedCorner(privacyDotViewController2.nextViewState.rotation, z2), null, 0, 0, 0, 0, 257791));
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
        shadeExpansionStateManager.addQsExpansionListener(new ShadeQsExpansionListener() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController.4
            @Override // com.android.systemui.shade.ShadeQsExpansionListener
            public final void onQsExpansionChanged(boolean z) {
                PrivacyDotViewController privacyDotViewController = PrivacyDotViewController.this;
                synchronized (privacyDotViewController.lock) {
                    privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, z, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, 262135));
                    Unit unit = Unit.INSTANCE;
                }
            }
        });
        setNextViewState(ViewState.copy$default(this.nextViewState, false, false, false, false, null, null, null, null, false, statusBarContentInsetsProvider.context.getResources().getConfiguration().windowConfiguration.getRotation(), 0, 0, null, null, 0, 0, 0, 0, 261631));
        this.systemStatusAnimationCallback = new SystemStatusAnimationCallback() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController$systemStatusAnimationCallback$1
            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final void onHidePersistentDot(boolean z) {
                DelayableExecutor delayableExecutor;
                final PrivacyDotViewController privacyDotViewController = PrivacyDotViewController.this;
                synchronized (privacyDotViewController.lock) {
                    privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, 262141));
                    if (z && (delayableExecutor = privacyDotViewController.uiExecutor) != null) {
                        ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController$systemStatusAnimationCallback$1$onHidePersistentDot$1$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                PrivacyDotViewController.CreateListener createListener = PrivacyDotViewController.this.createListener;
                                if (createListener != null) {
                                    ScreenDecorations screenDecorations = ScreenDecorations.this;
                                    screenDecorations.mIsDotViewVisible = false;
                                    screenDecorations.setupDecorations();
                                    LogBuffer.log$default(screenDecorations.mLogger.logBuffer, "ScreenDecorationsLog", LogLevel.DEBUG, "onRemovePrivacyIndicatorOverlay", null, 8, null);
                                }
                            }
                        });
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }

            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final Animator onSystemEventAnimationBegin(boolean z) {
                return null;
            }

            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final Animator onSystemEventAnimationFinish(boolean z, boolean z2) {
                return null;
            }

            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final void onSystemStatusAnimationTransitionToPersistentDot(String str) {
                final PrivacyDotViewController privacyDotViewController = PrivacyDotViewController.this;
                synchronized (privacyDotViewController.lock) {
                    DelayableExecutor delayableExecutor = privacyDotViewController.uiExecutor;
                    if (delayableExecutor != null) {
                        ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController$systemStatusAnimationCallback$1$onSystemStatusAnimationTransitionToPersistentDot$1$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                PrivacyDotViewController.CreateListener createListener = PrivacyDotViewController.this.createListener;
                                if (createListener != null) {
                                    ScreenDecorations screenDecorations = ScreenDecorations.this;
                                    LogBuffer.log$default(screenDecorations.mLogger.logBuffer, "ScreenDecorationsLog", LogLevel.DEBUG, AbstractC0866xb1ce8deb.m86m("onCreatePrivacyIndicatorOverlay, already created=", screenDecorations.mIsDotViewVisible), null, 8, null);
                                    screenDecorations.mIsDotViewVisible = true;
                                    screenDecorations.setupDecorations();
                                }
                            }
                        });
                    }
                    privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, true, false, false, null, null, null, null, false, 0, 0, 0, null, str, 0, 0, 0, 0, 253949));
                    Unit unit = Unit.INSTANCE;
                }
            }
        };
    }

    public static final void access$updateStatusBarState(PrivacyDotViewController privacyDotViewController) {
        synchronized (privacyDotViewController.lock) {
            ViewState viewState = privacyDotViewController.nextViewState;
            StatusBarStateController statusBarStateController = privacyDotViewController.stateController;
            privacyDotViewController.setNextViewState(ViewState.copy$default(viewState, false, false, (statusBarStateController.isExpanded() && statusBarStateController.getState() == 0) || statusBarStateController.getState() == 2, false, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, 262139));
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x001f, code lost:
    
        if (r7 != false) goto L9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0034, code lost:
    
        if (r7 != false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int activeRotationForCorner(View view, boolean z) {
        View view2 = this.f350tr;
        if (view2 == null) {
            view2 = null;
        }
        if (!Intrinsics.areEqual(view, view2)) {
            View view3 = this.f349tl;
            if (view3 == null) {
                view3 = null;
            }
            if (!Intrinsics.areEqual(view, view3)) {
                View view4 = this.f348br;
                if (Intrinsics.areEqual(view, view4 != null ? view4 : null)) {
                    if (!z) {
                        return 1;
                    }
                }
                return 2;
            }
            return 3;
        }
        if (z) {
            return 1;
        }
        return 0;
    }

    public final int cornerForView(View view) {
        View view2 = this.f349tl;
        if (view2 == null) {
            view2 = null;
        }
        if (Intrinsics.areEqual(view, view2)) {
            return 0;
        }
        View view3 = this.f350tr;
        if (view3 == null) {
            view3 = null;
        }
        if (Intrinsics.areEqual(view, view3)) {
            return 1;
        }
        View view4 = this.f347bl;
        if (view4 == null) {
            view4 = null;
        }
        if (Intrinsics.areEqual(view, view4)) {
            return 3;
        }
        View view5 = this.f348br;
        if (Intrinsics.areEqual(view, view5 != null ? view5 : null)) {
            return 2;
        }
        throw new IllegalArgumentException("not a corner view");
    }

    public final Sequence getViews() {
        View view = this.f349tl;
        if (view == null) {
            return SequencesKt__SequencesKt.sequenceOf(new View[0]);
        }
        View[] viewArr = new View[4];
        if (view == null) {
            view = null;
        }
        viewArr[0] = view;
        View view2 = this.f350tr;
        if (view2 == null) {
            view2 = null;
        }
        viewArr[1] = view2;
        View view3 = this.f348br;
        if (view3 == null) {
            view3 = null;
        }
        viewArr[2] = view3;
        View view4 = this.f347bl;
        viewArr[3] = view4 != null ? view4 : null;
        return SequencesKt__SequencesKt.sequenceOf(viewArr);
    }

    public final View selectDesignatedCorner(int i, boolean z) {
        View view = this.f349tl;
        if (view == null) {
            return null;
        }
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalStateException("unknown rotation");
                    }
                    if (z) {
                        View view2 = this.f347bl;
                        if (view2 != null) {
                            return view2;
                        }
                    } else if (view != null) {
                        return view;
                    }
                } else if (z) {
                    View view3 = this.f348br;
                    if (view3 != null) {
                        return view3;
                    }
                } else {
                    View view4 = this.f347bl;
                    if (view4 != null) {
                        return view4;
                    }
                }
            } else if (z) {
                View view5 = this.f350tr;
                if (view5 != null) {
                    return view5;
                }
            } else {
                View view6 = this.f348br;
                if (view6 != null) {
                    return view6;
                }
            }
        } else if (!z) {
            View view7 = this.f350tr;
            if (view7 != null) {
                return view7;
            }
        } else if (view != null) {
            return view;
        }
        return null;
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
                    setNextViewState(ViewState.copy$default(this.nextViewState, false, false, false, false, null, null, null, null, false, i, statusBarPaddingTop, cornerForView, selectDesignatedCorner, null, 0, 0, 0, 0, 254463));
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
    }

    public final void setNextViewState(ViewState viewState) {
        ExecutorImpl.ExecutionToken executionToken;
        if (this.nextViewState.rotation != viewState.rotation) {
            this.applyDelayNextViewState = true;
        }
        this.nextViewState = viewState;
        ExecutorImpl.ExecutionToken executionToken2 = this.cancelRunnable;
        if (executionToken2 != null) {
            executionToken2.run();
        }
        DelayableExecutor delayableExecutor = this.uiExecutor;
        if (delayableExecutor != null) {
            executionToken = delayableExecutor.executeDelayed(this.applyDelayNextViewState ? 300L : 0L, new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController$scheduleUpdate$1
                @Override // java.lang.Runnable
                public final void run() {
                    ViewState copy$default;
                    int i;
                    int i2;
                    final PrivacyDotViewController privacyDotViewController = PrivacyDotViewController.this;
                    if (privacyDotViewController.applyDelayNextViewState) {
                        privacyDotViewController.applyDelayNextViewState = false;
                    }
                    synchronized (privacyDotViewController.lock) {
                        copy$default = ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 0, 0, 0, 0, 262143);
                        Unit unit = Unit.INSTANCE;
                    }
                    Objects.toString(copy$default);
                    if (copy$default.viewInitialized && !Intrinsics.areEqual(copy$default, privacyDotViewController.currentViewState)) {
                        int i3 = privacyDotViewController.currentViewState.rotation;
                        int i4 = copy$default.rotation;
                        int i5 = copy$default.paddingTop;
                        if (i4 != i3) {
                            for (View view : privacyDotViewController.getViews()) {
                                view.setPadding(0, i5, 0, 0);
                                int cornerForView = privacyDotViewController.cornerForView(view) - i4;
                                if (cornerForView < 0) {
                                    cornerForView += 4;
                                }
                                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                                if (cornerForView == 0) {
                                    i = 51;
                                } else if (cornerForView == 1) {
                                    i = 53;
                                } else if (cornerForView == 2) {
                                    i = 85;
                                } else {
                                    if (cornerForView != 3) {
                                        throw new IllegalArgumentException("Not a corner");
                                    }
                                    i = 83;
                                }
                                layoutParams.gravity = i;
                                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) view.findViewById(R.id.privacy_dot).getLayoutParams();
                                if (cornerForView != 0) {
                                    if (cornerForView == 1 || cornerForView == 2) {
                                        i2 = 19;
                                        layoutParams2.gravity = i2;
                                    } else if (cornerForView != 3) {
                                        throw new IllegalArgumentException("Not a corner");
                                    }
                                }
                                i2 = 21;
                                layoutParams2.gravity = i2;
                            }
                        }
                        ViewState viewState2 = privacyDotViewController.currentViewState;
                        int i6 = viewState2.rotation;
                        int i7 = copy$default.stableInsetRight;
                        int i8 = copy$default.stableInsetLeft;
                        int i9 = copy$default.statusBarPaddingRight;
                        int i10 = copy$default.statusBarPaddingLeft;
                        boolean z = copy$default.layoutRtl;
                        if ((i4 == i6 && z == viewState2.layoutRtl && Intrinsics.areEqual(copy$default.portraitRect, viewState2.portraitRect) && Intrinsics.areEqual(copy$default.landscapeRect, viewState2.landscapeRect) && Intrinsics.areEqual(copy$default.upsideDownRect, viewState2.upsideDownRect) && Intrinsics.areEqual(copy$default.seascapeRect, viewState2.seascapeRect) && i10 == viewState2.statusBarPaddingLeft && i9 == viewState2.statusBarPaddingRight && i8 == viewState2.stableInsetLeft && i7 == viewState2.stableInsetRight && i5 == viewState2.paddingTop) ? false : true) {
                            Point point = new Point();
                            View view2 = privacyDotViewController.f349tl;
                            if (view2 == null) {
                                view2 = null;
                            }
                            view2.getContext().getDisplay().getRealSize(point);
                            View view3 = privacyDotViewController.f349tl;
                            if (view3 == null) {
                                view3 = null;
                            }
                            RotationUtils.getExactRotation(view3.getContext());
                            View view4 = privacyDotViewController.f349tl;
                            if (view4 == null) {
                                view4 = null;
                            }
                            float f = privacyDotViewController.indicatorScaleGardener.getLatestScaleModel(view4.getContext()).ratio;
                            View view5 = privacyDotViewController.f349tl;
                            if (view5 == null) {
                                view5 = null;
                            }
                            int roundToInt = MathKt__MathJVMKt.roundToInt(view5.getContext().getResources().getDimensionPixelSize(R.dimen.privacy_dot_margin_start) * f);
                            View view6 = privacyDotViewController.f349tl;
                            if (view6 == null) {
                                view6 = null;
                            }
                            int roundToInt2 = MathKt__MathJVMKt.roundToInt(view6.getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_appops_dot_diameter) * f);
                            Iterator it = privacyDotViewController.getViews().iterator();
                            while (it.hasNext()) {
                                ViewGroup.LayoutParams layoutParams3 = ((View) it.next()).findViewById(R.id.privacy_dot).getLayoutParams();
                                layoutParams3.width = roundToInt2;
                                layoutParams3.height = roundToInt2;
                            }
                            View view7 = privacyDotViewController.f349tl;
                            if (view7 == null) {
                                view7 = null;
                            }
                            Rect contentRectForRotation = copy$default.contentRectForRotation(privacyDotViewController.activeRotationForCorner(view7, z));
                            View view8 = privacyDotViewController.f349tl;
                            if (view8 == null) {
                                view8 = null;
                            }
                            view8.setPadding(0, i5, 0, 0);
                            View view9 = privacyDotViewController.f349tl;
                            if (view9 == null) {
                                view9 = null;
                            }
                            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) view9.getLayoutParams();
                            layoutParams4.height = contentRectForRotation.height();
                            if (z) {
                                layoutParams4.width = i10 - roundToInt;
                            } else {
                                layoutParams4.width = i9 - roundToInt;
                            }
                            View view10 = privacyDotViewController.f350tr;
                            if (view10 == null) {
                                view10 = null;
                            }
                            Rect contentRectForRotation2 = copy$default.contentRectForRotation(privacyDotViewController.activeRotationForCorner(view10, z));
                            View view11 = privacyDotViewController.f350tr;
                            if (view11 == null) {
                                view11 = null;
                            }
                            view11.setPadding(0, i5, 0, 0);
                            View view12 = privacyDotViewController.f350tr;
                            if (view12 == null) {
                                view12 = null;
                            }
                            FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) view12.getLayoutParams();
                            layoutParams5.height = contentRectForRotation2.height();
                            if (z) {
                                layoutParams5.width = i10 - roundToInt;
                            } else {
                                layoutParams5.width = i9 - roundToInt;
                            }
                            View view13 = privacyDotViewController.f348br;
                            if (view13 == null) {
                                view13 = null;
                            }
                            Rect contentRectForRotation3 = copy$default.contentRectForRotation(privacyDotViewController.activeRotationForCorner(view13, z));
                            View view14 = privacyDotViewController.f348br;
                            if (view14 == null) {
                                view14 = null;
                            }
                            view14.setPadding(0, i5, 0, 0);
                            View view15 = privacyDotViewController.f348br;
                            if (view15 == null) {
                                view15 = null;
                            }
                            FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) view15.getLayoutParams();
                            layoutParams6.height = contentRectForRotation3.height();
                            if (z) {
                                layoutParams6.width = i10 - roundToInt;
                            } else {
                                layoutParams6.width = (i7 + i9) - roundToInt;
                            }
                            View view16 = privacyDotViewController.f347bl;
                            if (view16 == null) {
                                view16 = null;
                            }
                            Rect contentRectForRotation4 = copy$default.contentRectForRotation(privacyDotViewController.activeRotationForCorner(view16, z));
                            View view17 = privacyDotViewController.f347bl;
                            if (view17 == null) {
                                view17 = null;
                            }
                            view17.setPadding(0, i5, 0, 0);
                            View view18 = privacyDotViewController.f347bl;
                            if (view18 == null) {
                                view18 = null;
                            }
                            FrameLayout.LayoutParams layoutParams7 = (FrameLayout.LayoutParams) view18.getLayoutParams();
                            layoutParams7.height = contentRectForRotation4.height();
                            if (z) {
                                layoutParams7.width = (i10 + i8) - roundToInt;
                            } else {
                                layoutParams7.width = i9 - roundToInt;
                            }
                            Iterator it2 = privacyDotViewController.getViews().iterator();
                            while (it2.hasNext()) {
                                ((View) it2.next()).requestLayout();
                            }
                        }
                        View view19 = privacyDotViewController.currentViewState.designatedCorner;
                        final View view20 = copy$default.designatedCorner;
                        boolean areEqual = Intrinsics.areEqual(view20, view19);
                        String str = copy$default.contentDescription;
                        if (!areEqual) {
                            View view21 = privacyDotViewController.currentViewState.designatedCorner;
                            if (view21 != null) {
                                view21.setContentDescription(null);
                            }
                            if (view20 != null) {
                                view20.setContentDescription(str);
                            }
                            if ((!copy$default.systemPrivacyEventIsActive || copy$default.shadeExpanded || copy$default.qsExpanded) ? false : true) {
                                PrivacyDotViewController.ShowingListener showingListener = privacyDotViewController.showingListener;
                                if (showingListener != null) {
                                    ScreenDecorations.this.updateOverlayWindowVisibilityIfViewExists(view20);
                                }
                                if (view20 != null) {
                                    view20.clearAnimation();
                                    view20.setVisibility(0);
                                    view20.setAlpha(0.0f);
                                    view20.animate().alpha(1.0f).setDuration(300L).start();
                                }
                            }
                        } else if (!Intrinsics.areEqual(str, privacyDotViewController.currentViewState.contentDescription) && view20 != null) {
                            view20.setContentDescription(str);
                        }
                        boolean z2 = (!copy$default.systemPrivacyEventIsActive || copy$default.shadeExpanded || copy$default.qsExpanded) ? false : true;
                        ViewState viewState3 = privacyDotViewController.currentViewState;
                        if (z2 != ((!viewState3.systemPrivacyEventIsActive || viewState3.shadeExpanded || viewState3.qsExpanded) ? false : true)) {
                            if (z2 && view20 != null) {
                                view20.clearAnimation();
                                view20.setVisibility(0);
                                view20.setAlpha(0.0f);
                                view20.animate().alpha(1.0f).setDuration(160L).setInterpolator(Interpolators.ALPHA_IN).start();
                                PrivacyDotViewController.ShowingListener showingListener2 = privacyDotViewController.showingListener;
                                if (showingListener2 != null) {
                                    ScreenDecorations.this.updateOverlayWindowVisibilityIfViewExists(view20);
                                }
                            } else if (!z2 && view20 != null) {
                                view20.clearAnimation();
                                view20.animate().setDuration(160L).setInterpolator(Interpolators.ALPHA_OUT).alpha(0.0f).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.events.PrivacyDotViewController$hideDotView$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        view20.setVisibility(4);
                                        PrivacyDotViewController.ShowingListener showingListener3 = privacyDotViewController.showingListener;
                                        if (showingListener3 != null) {
                                            ScreenDecorations.this.updateOverlayWindowVisibilityIfViewExists(view20);
                                        }
                                    }
                                }).start();
                            }
                        }
                        if (z2) {
                            privacyDotViewController.privacyLogger.logPrivacyDotViewState(copy$default.toString());
                        }
                        privacyDotViewController.currentViewState = copy$default;
                    }
                }
            });
        } else {
            executionToken = null;
        }
        this.cancelRunnable = executionToken;
    }
}
