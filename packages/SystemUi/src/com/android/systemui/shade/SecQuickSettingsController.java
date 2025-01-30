package com.android.systemui.shade;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.p016qs.NonInterceptingScrollView;
import com.android.systemui.p016qs.QSContainerImpl;
import com.android.systemui.p016qs.SecQuickQSPanel;
import com.android.systemui.plugins.p013qs.InterfaceC1922QS;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.QSScrimViewSwitch;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQuickSettingsController {
    public final AmbientState ambientState;
    public int barState;
    public final SecQpBlurController blurController;
    public final Function calculateBottomPositionFunction;
    public final Function calculateTopClippingBoundFunction;
    public boolean canScrollDown;
    public boolean canScrollUp;
    public final DoubleSupplier computeExpansionFractionSupplier;
    public final DoubleSupplier edgePositionSupplier;
    public final BooleanSupplier enableClippingSupplier;
    public final BooleanSupplier expandImmediateSupplier;
    public final SecExpandQSAtOnceController expandQSAtOnceController;
    public final DoubleSupplier expansionHeightSupplier;
    public BooleanSupplier heightAnimatingSupplier;
    public boolean isBackGestureAllowed;
    public int lastDisplayTopInset;
    public int lastNavigationBarBottomHeight;
    public final DoubleSupplier maxExpansionHeightSupplier;
    public final SecQsMediaTouchHelper mediaTouchHelper;
    public final DoubleSupplier minExpansionHeightSupplier;
    public int naviBarGestureMode;
    public final NavigationBarController navigationBarController;
    public final NavigationModeController navigationModeController;
    public final NotificationStackScrollLayoutController notificationStackScrollLayoutController;
    public boolean openedByTwoFingerDragging;
    public final SecPanelExpansionStateNotifier panelExpansionStateNotifier;
    public final SecPanelLogger panelLogger;
    public final Lazy panelViewControllerLazy;
    public final QSScrimViewSwitch qSScrimViewSwitch;
    public QSContainerImpl qsContainerImpl;
    public final Supplier qsFrameLayoutSupplier;
    public NonInterceptingScrollView qsScrollView;
    public final Supplier qsSupplier;
    public SecQuickQSPanel quickQSPanel;
    public final SecTabletHorizontalPanelPositionHelper tabletHorizontalPanelPositionHelper;
    public final Consumer touchAboveFalsingThresholdConsumer;
    public final Runnable trackingRunnable;
    public final Runnable updateInitialHeightOnTouchRunnable;
    public final StringBuilder logBuilder = new StringBuilder();
    public final SecQuickSettingsController$logProvider$1 logProvider = new PanelScreenShotLogger.LogProvider() { // from class: com.android.systemui.shade.SecQuickSettingsController$logProvider$1
        public static void dumpParents(ArrayList arrayList, View view) {
            if (view == null) {
                return;
            }
            ViewParent parent = view.getParent();
            if (!Intrinsics.areEqual(parent != null ? parent.getClass().getSimpleName() : null, "ViewRootImpl")) {
                dumpParents(arrayList, (View) view.getParent());
            }
            arrayList.add(view.getClass().getSimpleName() + " : alpha: " + view.getAlpha() + ", visibility: " + view.getVisibility() + ", height: " + view.getHeight());
        }

        @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
        public final ArrayList gatherState() {
            ArrayList arrayList = new ArrayList();
            arrayList.add("SecQuickSettingsController ============================================= ");
            SecQuickSettingsController secQuickSettingsController = SecQuickSettingsController.this;
            arrayList.add(" expansionHeight: " + secQuickSettingsController.expansionHeightSupplier.getAsDouble() + " minExpansionHeight: " + secQuickSettingsController.minExpansionHeightSupplier.getAsDouble() + " maxExpansionHeight: " + secQuickSettingsController.maxExpansionHeightSupplier.getAsDouble());
            FrameLayout frameLayout = (FrameLayout) secQuickSettingsController.qsFrameLayoutSupplier.get();
            arrayList.add(" enableClipping: " + secQuickSettingsController.enableClippingSupplier.getAsBoolean() + " qsFrame[translationY: " + frameLayout.getTranslationY() + "  qsFrame.top: " + frameLayout.getTop() + "]");
            AmbientState ambientState = secQuickSettingsController.ambientState;
            float f = ambientState.mExpansionFraction;
            float f2 = ambientState.mStackY;
            int i = ambientState.mStackTopMargin;
            int i2 = ambientState.mScrollY;
            StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m(" ambientState[expansionFraction: ", f, " stackY: ", f2, " stackTopMargin: ");
            m88m.append(i);
            m88m.append(" scrollY: ");
            m88m.append(i2);
            m88m.append("]");
            arrayList.add(m88m.toString());
            float asDouble = (float) secQuickSettingsController.computeExpansionFractionSupplier.getAsDouble();
            int intValue = ((Number) secQuickSettingsController.calculateBottomPositionFunction.apply(Float.valueOf(asDouble))).intValue();
            int intValue2 = ((Number) secQuickSettingsController.calculateTopClippingBoundFunction.apply(Integer.valueOf(intValue))).intValue();
            arrayList.add(" notificationTop: " + secQuickSettingsController.edgePositionSupplier.getAsDouble() + " expansionFraction: " + asDouble + " qsPanelBottomY: " + intValue + " top: " + intValue2);
            arrayList.add("============================================================== ");
            dumpParents(arrayList, secQuickSettingsController.qsContainerImpl);
            arrayList.add("============================================================== ");
            return arrayList;
        }
    };
    public final SecQuickSettingsController$modeChangedListener$1 modeChangedListener = new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.shade.SecQuickSettingsController$modeChangedListener$1
        @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
        public final void onNavigationModeChanged(int i) {
            SecQuickSettingsController.this.naviBarGestureMode = i;
        }
    };
    public final SecPanelBlockExpandingHelper panelBlockExpandingHelper = (SecPanelBlockExpandingHelper) Dependency.get(SecPanelBlockExpandingHelper.class);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v20, types: [com.android.systemui.shade.SecQuickSettingsController$logProvider$1] */
    /* JADX WARN: Type inference failed for: r2v21, types: [com.android.systemui.shade.SecQuickSettingsController$modeChangedListener$1] */
    public SecQuickSettingsController(AmbientState ambientState, SecQpBlurController secQpBlurController, Runnable runnable, Function<Float, Integer> function, Function<Integer, Integer> function2, DoubleSupplier doubleSupplier, final Context context, DoubleSupplier doubleSupplier2, DoubleSupplier doubleSupplier3, BooleanSupplier booleanSupplier, DoubleSupplier doubleSupplier4, BooleanSupplier booleanSupplier2, DoubleSupplier doubleSupplier5, ShadeHeaderController shadeHeaderController, DoubleConsumer doubleConsumer, DoubleSupplier doubleSupplier6, DoubleConsumer doubleConsumer2, DoubleSupplier doubleSupplier7, Runnable runnable2, BooleanSupplier booleanSupplier3, BooleanSupplier booleanSupplier4, DoubleSupplier doubleSupplier8, SecMediaHost secMediaHost, DoubleSupplier doubleSupplier9, NavigationBarController navigationBarController, NavigationModeController navigationModeController, NotificationStackScrollLayoutController notificationStackScrollLayoutController, SecPanelExpansionStateNotifier secPanelExpansionStateNotifier, SecPanelLogger secPanelLogger, Lazy lazy, IntConsumer intConsumer, BooleanSupplier booleanSupplier5, Supplier<FrameLayout> supplier, QSScrimViewSwitch qSScrimViewSwitch, Supplier<InterfaceC1922QS> supplier2, Consumer<Boolean> consumer, Consumer<MotionEvent> consumer2, IntConsumer intConsumer2, IntSupplier intSupplier, Runnable runnable3, Runnable runnable4, Supplier<NotificationPanelView> supplier3) {
        this.ambientState = ambientState;
        this.blurController = secQpBlurController;
        this.calculateBottomPositionFunction = function;
        this.calculateTopClippingBoundFunction = function2;
        this.computeExpansionFractionSupplier = doubleSupplier;
        this.edgePositionSupplier = doubleSupplier3;
        this.enableClippingSupplier = booleanSupplier;
        this.expandImmediateSupplier = booleanSupplier2;
        this.expansionHeightSupplier = doubleSupplier5;
        this.maxExpansionHeightSupplier = doubleSupplier8;
        this.minExpansionHeightSupplier = doubleSupplier9;
        this.navigationBarController = navigationBarController;
        this.navigationModeController = navigationModeController;
        this.notificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.panelExpansionStateNotifier = secPanelExpansionStateNotifier;
        this.panelLogger = secPanelLogger;
        this.panelViewControllerLazy = lazy;
        this.qsFrameLayoutSupplier = supplier;
        this.qSScrimViewSwitch = qSScrimViewSwitch;
        this.qsSupplier = supplier2;
        this.touchAboveFalsingThresholdConsumer = consumer;
        this.trackingRunnable = runnable3;
        this.updateInitialHeightOnTouchRunnable = runnable4;
        this.expandQSAtOnceController = new SecExpandQSAtOnceController(context, booleanSupplier5);
        this.mediaTouchHelper = new SecQsMediaTouchHelper(runnable, doubleSupplier2, doubleConsumer, doubleSupplier6, doubleConsumer2, doubleSupplier7, runnable2, secMediaHost, notificationStackScrollLayoutController, booleanSupplier5, supplier2, consumer2, intConsumer2, intSupplier);
        this.tabletHorizontalPanelPositionHelper = new SecTabletHorizontalPanelPositionHelper(doubleSupplier4, shadeHeaderController, booleanSupplier3, booleanSupplier4, notificationStackScrollLayoutController, new IntSupplier() { // from class: com.android.systemui.shade.SecQuickSettingsController.1
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                return context.getResources().getDimensionPixelSize(R.dimen.notification_panel_min_side_margin);
            }
        }, intConsumer, supplier, supplier3, lazy);
    }

    public final void updateScrollableDirection(boolean z) {
        if (z) {
            this.canScrollUp = false;
            this.canScrollDown = false;
            return;
        }
        NonInterceptingScrollView nonInterceptingScrollView = this.qsScrollView;
        if (nonInterceptingScrollView != null) {
            this.canScrollUp = nonInterceptingScrollView.canScrollVertically(1);
            this.canScrollDown = nonInterceptingScrollView.canScrollVertically(-1);
        }
    }
}
